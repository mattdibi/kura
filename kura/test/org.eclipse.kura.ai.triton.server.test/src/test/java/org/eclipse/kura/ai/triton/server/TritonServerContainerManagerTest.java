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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.container.orchestration.ContainerConfiguration;
import org.eclipse.kura.container.orchestration.ContainerInstanceDescriptor;
import org.eclipse.kura.container.orchestration.ContainerOrchestrationService;
import org.eclipse.kura.container.orchestration.ContainerState;
import org.eclipse.kura.container.orchestration.ImageInstanceDescriptor;
import org.junit.Test;

public class TritonServerContainerManagerTest {

    private static final String TRITON_IMAGE_NAME = "tritonserver";
    private static final String TRITON_IMAGE_TAG = "latest";
    private static final String MOCK_DECRYPT_FOLDER = "testDecryptionFolder";
    private static final String TRITON_CONTAINER_NAME = "tritonserver-kura";
    private static final String TRITON_CONTAINER_ID = "tritonserver-kura-ID";

    private Map<String, Object> properties = new HashMap<>();
    private TritonServerServiceOptions options = new TritonServerServiceOptions(properties);

    private ContainerOrchestrationService orc;
    private TritonServerContainerManager manager;

    @Test
    public void isServerRunningWorksWhenNotRunning() {
        givenPropertyWith("container.image", TRITON_IMAGE_NAME);
        givenPropertyWith("container.image.tag", TRITON_IMAGE_TAG);
        givenPropertyWith("server.ports", new Integer[] { 4000, 4001, 4002 });
        givenServiceOptionsBuiltWith(properties);

        givenMockContainerOrchestrationService();
        givenTritonContainerIsNotRunning();
        givenLocalManagerBuiltWith(this.options, this.orc, MOCK_DECRYPT_FOLDER);

        thenServerIsRunningReturns(false);
    }

    @Test
    public void isServerRunningWorksWhenRunning() {
        givenPropertyWith("container.image", TRITON_IMAGE_NAME);
        givenPropertyWith("container.image.tag", TRITON_IMAGE_TAG);
        givenPropertyWith("server.ports", new Integer[] { 4000, 4001, 4002 });
        givenServiceOptionsBuiltWith(properties);

        givenMockContainerOrchestrationService();
        givenTritonContainerIsRunning();
        givenLocalManagerBuiltWith(this.options, this.orc, MOCK_DECRYPT_FOLDER);

        thenServerIsRunningReturns(true);
    }

    @Test
    public void stopMethodShouldWork() {
        givenPropertyWith("container.image", TRITON_IMAGE_NAME);
        givenPropertyWith("container.image.tag", TRITON_IMAGE_TAG);
        givenPropertyWith("server.ports", new Integer[] { 4000, 4001, 4002 });
        givenServiceOptionsBuiltWith(properties);

        givenMockContainerOrchestrationService();
        givenTritonContainerIsRunning();
        givenLocalManagerBuiltWith(this.options, this.orc, MOCK_DECRYPT_FOLDER);

        whenStopIsCalled();

        thenContainerOrchestrationStopContainerWasCalledWith(TRITON_CONTAINER_ID);
        thenContainerOrchestrationDeleteContainerWasCalledWith(TRITON_CONTAINER_ID);
    }

    @Test
    public void killMethodShouldWork() {
        givenPropertyWith("container.image", TRITON_IMAGE_NAME);
        givenPropertyWith("container.image.tag", TRITON_IMAGE_TAG);
        givenPropertyWith("server.ports", new Integer[] { 4000, 4001, 4002 });
        givenServiceOptionsBuiltWith(properties);

        givenMockContainerOrchestrationService();
        givenTritonContainerIsRunning();
        givenLocalManagerBuiltWith(this.options, this.orc, MOCK_DECRYPT_FOLDER);

        whenKillIsCalled();

        thenContainerOrchestrationStopContainerWasCalledWith(TRITON_CONTAINER_ID);
        thenContainerOrchestrationDeleteContainerWasCalledWith(TRITON_CONTAINER_ID);
    }

    @Test
    public void startMethodShouldWorkIfImageIsAvailable() {
        givenPropertyWith("container.image", TRITON_IMAGE_NAME);
        givenPropertyWith("container.image.tag", TRITON_IMAGE_TAG);
        givenPropertyWith("server.ports", new Integer[] { 4000, 4001, 4002 });
        givenServiceOptionsBuiltWith(properties);

        givenMockContainerOrchestrationService();
        givenTritonImageIsAvailable();
        givenTritonContainerIsNotRunning();
        givenLocalManagerBuiltWith(this.options, this.orc, MOCK_DECRYPT_FOLDER);

        whenStartIsCalled();

        thenContainerOrchestrationStartContainerWasCalled();
    }

    @Test
    public void startMethodShouldWorkIfImageIsNotAvailable() {
        givenPropertyWith("container.image", TRITON_IMAGE_NAME);
        givenPropertyWith("container.image.tag", TRITON_IMAGE_TAG);
        givenPropertyWith("server.ports", new Integer[] { 4000, 4001, 4002 });
        givenServiceOptionsBuiltWith(properties);

        givenMockContainerOrchestrationService();
        givenTritonImageIsAvailable();
        givenTritonContainerIsNotRunning();
        givenLocalManagerBuiltWith(this.options, this.orc, MOCK_DECRYPT_FOLDER);

        whenStartIsCalled();

        thenContainerOrchestrationStartContainerWasNotCalled();
    }

    /*
     * Given
     */
    private void givenPropertyWith(String name, Object value) {
        this.properties.put(name, value);
    }

    private void givenServiceOptionsBuiltWith(Map<String, Object> properties) {
        this.options = new TritonServerServiceOptions(properties);
    }

    private void givenMockContainerOrchestrationService() {
        this.orc = mock(ContainerOrchestrationService.class);
    }

    private void givenTritonImageIsAvailable() {
        ImageInstanceDescriptor imageDescriptor = mock(ImageInstanceDescriptor.class);
        when(imageDescriptor.getImageName()).thenReturn(TRITON_IMAGE_NAME);
        when(imageDescriptor.getImageTag()).thenReturn(TRITON_IMAGE_TAG);

        when(this.orc.listImageInstanceDescriptors()).thenReturn(Arrays.asList(imageDescriptor));
    }

    private void givenTritonContainerIsRunning() {
        ContainerInstanceDescriptor runningContainer = mock(ContainerInstanceDescriptor.class);
        when(runningContainer.getContainerName()).thenReturn(TRITON_CONTAINER_NAME);
        when(runningContainer.getContainerId()).thenReturn(TRITON_CONTAINER_ID);
        when(runningContainer.getContainerState()).thenReturn(ContainerState.ACTIVE);
        when(runningContainer.getContainerImage()).thenReturn(TRITON_IMAGE_NAME);
        when(runningContainer.getContainerImageTag()).thenReturn(TRITON_IMAGE_TAG);

        when(this.orc.listContainerDescriptors()).thenReturn(Arrays.asList(runningContainer));
    }

    private void givenTritonContainerIsNotRunning() {
        when(this.orc.listContainerDescriptors()).thenReturn(Arrays.asList());
    }

    private void givenLocalManagerBuiltWith(TritonServerServiceOptions options, ContainerOrchestrationService orc,
            String decryptionFolder) {
        this.manager = new TritonServerContainerManager(options, orc, decryptionFolder);
    }

    /*
     * When
     */
    private void whenKillIsCalled() {
        this.manager.kill();
    }

    private void whenStopIsCalled() {
        this.manager.stop();
    }

    private void whenStartIsCalled() {
        this.manager.start();

        try {
            // Wait for monitor thread to do its job
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail();
        }
    }

    /*
     * Then
     */
    private void thenServerIsRunningReturns(boolean expectedState) {
        assertEquals(expectedState, this.manager.isServerRunning());
    }

    private void thenContainerOrchestrationStopContainerWasCalledWith(String containerID) {
        try {
            verify(this.orc, times(1)).stopContainer(containerID);
        } catch (KuraException e) {
            fail();
        }
    }

    private void thenContainerOrchestrationDeleteContainerWasCalledWith(String containerID) {
        try {
            verify(this.orc, times(1)).deleteContainer(containerID);
        } catch (KuraException e) {
            fail();
        }
    }

    private void thenContainerOrchestrationStartContainerWasCalled() {
        try {
            verify(this.orc, times(1)).startContainer((ContainerConfiguration) any(Object.class));
        } catch (KuraException | InterruptedException e) {
            fail();
        }
    }

    private void thenContainerOrchestrationStartContainerWasNotCalled() {
        try {
            verify(this.orc, times(0)).startContainer((ContainerConfiguration) any(Object.class));
        } catch (KuraException | InterruptedException e) {
            fail();
        }
    }
}
