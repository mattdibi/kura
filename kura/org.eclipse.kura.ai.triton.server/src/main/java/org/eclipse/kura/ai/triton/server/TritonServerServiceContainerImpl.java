package org.eclipse.kura.ai.triton.server;

import org.eclipse.kura.container.orchestration.ContainerOrchestrationService;
import org.eclipse.kura.executor.CommandExecutorService;

public class TritonServerServiceContainerImpl extends TritonServerServiceAbs {

    @Override
    TritonServerInstanceManager createInstanceManager(TritonServerServiceOptions options,
            CommandExecutorService executorService, ContainerOrchestrationService containerOrchestrationService,
            String decryptionFolderPath) {
        return new TritonServerContainerManager(options, containerOrchestrationService, decryptionFolderPath);
    }

    @Override
    boolean isConfigurationValid() {
        return !isNullOrEmpty(this.options.getModelRepositoryPath());
    }

    @Override
    boolean isModelEncryptionEnabled() {
        return false; // Temporarily disabled
    }

    @Override
    String getServerAddress() {
        return "localhost";
    }
}