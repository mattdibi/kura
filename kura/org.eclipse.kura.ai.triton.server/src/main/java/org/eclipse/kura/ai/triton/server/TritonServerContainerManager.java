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

import org.eclipse.kura.container.orchestration.ContainerOrchestrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TritonServerContainerManager implements TritonServerInstanceManager {

    private static final Logger logger = LoggerFactory.getLogger(TritonServerContainerManager.class);

    private TritonServerServiceOptions options;
    private ContainerOrchestrationService containerOrchestrationService;
    private String decryptionFolderPath;

    protected TritonServerContainerManager(TritonServerServiceOptions options,
            ContainerOrchestrationService containerOrchestrationService, String decryptionFolderPath) {
        this.options = options;
        this.containerOrchestrationService = containerOrchestrationService;
        this.decryptionFolderPath = decryptionFolderPath;
    }

    @Override
    public void start() {
        // TODO
    }

    @Override
    public void stop() {
        // TODO
    }

    @Override
    public void kill() {
        // TODO
    }

    @Override
    public boolean isServerRunning() {
        // TODO
        return false;
    }
}
