/*******************************************************************************
 * Copyright (c) 2022 Eurotech and/or its affiliates and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Eurotech
 ******************************************************************************/
package org.eclipse.kura.ai.triton.server;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.container.orchestration.ContainerConfiguration;
import org.eclipse.kura.container.orchestration.ContainerConfiguration.ContainerConfigurationBuilder;
import org.eclipse.kura.container.orchestration.ContainerOrchestrationService;
import org.eclipse.kura.container.orchestration.ImageConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TritonServerLocalManager {

    private static final Logger logger = LoggerFactory.getLogger(TritonServerLocalManager.class);

    private final String decryptionFolderPath;
    private final ContainerOrchestrationService containerOrchestrationService;
    private final TritonServerServiceOptions options;
    private String managedContainerID = null;

    protected TritonServerLocalManager(TritonServerServiceOptions options,
            ContainerOrchestrationService containerOrchestrationService, String decryptionFolderPath) {
        this.options = options;
        this.containerOrchestrationService = containerOrchestrationService;
        this.decryptionFolderPath = decryptionFolderPath;
    }

    protected void start() {
        logger.info("Configuring Triton container");

        ImageConfiguration imgCfgBuilder = new ImageConfiguration.ImageConfigurationBuilder().setImageName("nginx")
                .setImageTag("latest").setRegistryCredentials(Optional.empty()).setImageDownloadTimeoutSeconds(0)
                .build();

        ContainerConfigurationBuilder cntCfgBuilder = ContainerConfiguration.builder()
                .setContainerName("triton_container").setImageConfiguration(imgCfgBuilder)
                .setExternalPorts(Arrays.asList(4000, 4001, 4002)).setInternalPorts(Arrays.asList(8000, 8001, 8002))
                .setVolumes(Collections.singletonMap(this.options.getModelRepositoryPath(), "/models"))
                .setFrameworkManaged(true).setLoggingType("DEFAULT");

        ContainerConfiguration containerConfiguration = cntCfgBuilder.build();

        try {
            logger.info("Starting Triton container");
            this.managedContainerID = this.containerOrchestrationService.startContainer(containerConfiguration);
        } catch (KuraException e) {
            logger.warn("Failed to start Triton container: {}", e);
        } catch (InterruptedException e) {
            logger.warn("Failed to start Triton container: {}", e);
        }
    }

    protected void stop() {
        try {
            this.containerOrchestrationService.stopContainer(this.managedContainerID);
        } catch (KuraException e) {
            logger.warn("Failed to stop Triton container: {}", e);
        }

        this.managedContainerID = null;
    }

    protected void kill() {
        stop();
    }

    protected boolean isLocalServerRunning() {
        return !(this.managedContainerID == null || this.managedContainerID.isEmpty());
    }

    protected static void sleepFor(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.debug(e.getMessage(), e);
        }
    }

}