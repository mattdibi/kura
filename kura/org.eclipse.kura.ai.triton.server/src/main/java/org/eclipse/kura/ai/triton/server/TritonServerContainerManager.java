package org.eclipse.kura.ai.triton.server;

import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.container.orchestration.ContainerConfiguration;
import org.eclipse.kura.container.orchestration.ContainerConfiguration.ContainerConfigurationBuilder;
import org.eclipse.kura.container.orchestration.ContainerNetworkConfiguration.ContainerNetworkConfigurationBuilder;
import org.eclipse.kura.container.orchestration.ContainerOrchestrationService;
import org.eclipse.kura.container.orchestration.ImageConfiguration.ImageConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TritonServerContainerManager implements TritonServerInstanceManager {

    private static final Logger logger = LoggerFactory.getLogger(TritonServerContainerManager.class);

    private static final int MONITOR_PERIOD = 30;

    private final String decryptionFolderPath;
    private final ContainerOrchestrationService containerOrchestrationService;
    private final TritonServerServiceOptions options;
    private String containerID = "";

    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?> scheduledFuture;

    protected TritonServerContainerManager(TritonServerServiceOptions options,
            ContainerOrchestrationService containerOrchestrationService, String decryptionFolderPath) {
        this.options = options;
        this.containerOrchestrationService = containerOrchestrationService;
        this.decryptionFolderPath = decryptionFolderPath;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void start() {
        startContainerServerMonitor();
    }

    @Override
    public void stop() {
        stopLocalServerMonitor();
        stopScheduledExecutor();
    }

    @Override
    public void kill() {
        killLocalServerMonitor();
        stopScheduledExecutor();
    }

    private void startContainerServerMonitor() {
        this.scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName(getClass().getSimpleName());
            if (!isServerRunning()) {
                startLocalServer();
            }
        }, 0, MONITOR_PERIOD, TimeUnit.SECONDS);
    }

    private void startLocalServer() {
        ContainerConfiguration containerConfiguration = createContainerConfiguration();
        try {
            this.containerID = this.containerOrchestrationService.startContainer(containerConfiguration);
        } catch (KuraException | InterruptedException e) {
            logger.info("Nvidia Triton Container not started. {}", e);
        }

        logger.info("Nvidia Triton Container started. Container ID: {}", containerID);
    }

    private void stopLocalServerMonitor() {
        stopMonitor();
        stopLocalServer();
    }

    private void killLocalServerMonitor() {
        stopMonitor();
        killLocalServer();
    }

    private void stopMonitor() {
        if (nonNull(this.scheduledFuture)) {
            this.scheduledFuture.cancel(true);
            while (!this.scheduledFuture.isDone()) {
                sleepFor(500);
            }
        }
    }

    private synchronized void stopLocalServer() {
        try {
            this.containerOrchestrationService.stopContainer(containerID);
            this.containerID = "";
        } catch (KuraException e) {
            logger.error("Can't stop {} won't stop LOL", containerID);
        }
    }

    private synchronized void killLocalServer() {
        stopLocalServer();
    }

    private void stopScheduledExecutor() {
        if (this.scheduledExecutorService != null) {
            this.scheduledExecutorService.shutdown();
            while (!this.scheduledExecutorService.isTerminated()) {
                sleepFor(500);
            }
            this.scheduledExecutorService = null;
        }
    }

    @Override
    public boolean isServerRunning() {
        return !this.containerID.isEmpty();
    }

    private static void sleepFor(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.debug(e.getMessage(), e);
        }
    }

    private ContainerConfiguration createContainerConfiguration() {
        ImageConfigurationBuilder imageConfigBuilder = new ImageConfigurationBuilder();

        // imageConfigBuilder.setImageName("nvcr.io/nvidia/tritonserver");
        // imageConfigBuilder.setImageTag("22.02-py3-min");
        imageConfigBuilder.setImageName("tritonserver");
        imageConfigBuilder.setImageTag("latest");
        imageConfigBuilder.setRegistryCredentials(Optional.empty());

        ContainerNetworkConfigurationBuilder networkConfigurationBuilder = new ContainerNetworkConfigurationBuilder();
        networkConfigurationBuilder.setNetworkMode(Optional.of("host"));

        ContainerConfigurationBuilder builder = new ContainerConfigurationBuilder();

        builder.setImageConfiguration(imageConfigBuilder.build());
        builder.setContainerNetowrkConfiguration(networkConfigurationBuilder.build());

        builder.setContainerName("tritonserver");
        builder.setFrameworkManaged(true);
        builder.setLoggingType("DEFAULT");
        builder.setInternalPorts(Arrays.asList(8000, 8001, 8002));
        builder.setExternalPorts(
                Arrays.asList(this.options.getHttpPort(), this.options.getGrpcPort(), this.options.getMetricsPort()));
        builder.setVolumes(Collections.singletonMap(this.options.getModelRepositoryPath(), "/models"));
        builder.setEntryPoint(
                Arrays.asList("tritonserver", "--model-repository=/models", "--model-control-mode=explicit"));

        return builder.build();
    }

}
