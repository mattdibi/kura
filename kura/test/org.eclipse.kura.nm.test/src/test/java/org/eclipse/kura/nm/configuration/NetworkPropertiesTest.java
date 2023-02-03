/*******************************************************************************
 * Copyright (c) 2023 Eurotech and/or its affiliates and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Eurotech
 *******************************************************************************/
package org.eclipse.kura.nm.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.freedesktop.dbus.errors.NotSupported;
import org.junit.Test;

public class NetworkPropertiesTest {

	NetworkProperties netProps;
	Map<String, Object> properties;
	Optional<?> optResult;
	String stringResult;
	List<String> stringListResult;
	Map<String, Object> resultMap;

	Boolean hasNullPointExceptionBeenThrown = false;

	@Test
	public void shouldFailWhenNull() {
		givenNetworkPropertiesBuiltWith(null);
		givenNetworkPropsIsCreated();
		thenAnNullPointerExceptionOccured();
	}

	@Test
	public void shouldReturnPropertiesWithGetProperties() {
		givenTheMapWith("testKey1", "testString1");
		givenNetworkPropsIsCreated();
		whenGetPropertiesIsCalled();
		thenResultEquals(Collections.singletonMap("testKey1", "testString1"));
	}

	@Test
	public void shouldReturnStringWithGet() {
		givenTheMapWith("testKey1", "testString1");
		givenNetworkPropsIsCreated();
		whenGetIsCalledWith("testKey1", String.class);
		thenResultEquals("testString1");
	}

	@Test
	public void shouldReturnStringWithGetOpt() {
		givenTheMapWith("testKey1", "testString1");
		givenNetworkPropsIsCreated();
		whenGetOptIsCalledWith("testKey1", String.class);
		thenResultEquals(Optional.of("testString1"));
	}

	@Test
	public void shouldReturnEmptyGetOptEmpty() {
		givenTheMapWith("testKeyEmpty", "");
		givenNetworkPropsIsCreated();
		whenGetOptIsCalledWith("testKeyEmpty", String.class);
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnEmptyGetOptNull() {
		givenTheMapWith("testKeyNull", null);
		givenNetworkPropsIsCreated();
		whenGetOptIsCalledWith("testKeyNull", String.class);
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnEmptyGetOptEmptyKey() {
		givenTheMapWith("testKeyNull", null);
		givenNetworkPropsIsCreated();
		whenGetOptIsCalledWith("", String.class);
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedString() {
		givenTheMapWith("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		givenNetworkPropsIsCreated();
		whenGetStringListIsCalledWith("testKey-comma-seperated");
		thenResultEquals(List.of("commaSeperated1", "commaSeperated2", "commaSeperated3"));
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedStringMalformed() {
		givenTheMapWith("testKey-comma-seperated",
				",,   ,,,commaSeperated1, ,,,,commaSeperated2,   ,,commaSeperated3,");
		givenNetworkPropsIsCreated();
		whenGetStringListIsCalledWith("testKey-comma-seperated");
		thenResultEquals(List.of("commaSeperated1", "commaSeperated2", "commaSeperated3"));
	}

	@Test
	public void shouldReturnStringListGetStringListWithRegularString() {
		givenTheMapWith("testKey1", "testString1");
		givenNetworkPropsIsCreated();
		whenGetStringListIsCalledWith("testKey1");
		thenResultEquals(List.of("testString1"));
	}

	@Test
	public void shouldReturnStringListGetStringListWithNull() {
		givenTheMapWith("testKeyNull", null);
		givenNetworkPropsIsCreated();
		whenGetStringListIsCalledWith("testKeyNull");
		thenResultEquals(List.of());
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithCommaSeperatedString() {
		givenTheMapWith("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		givenNetworkPropsIsCreated();
		whenGetStringListOptIsCalledWith("testKey-comma-seperated");
		thenResultEquals(Optional.of(List.of("commaSeperated1", "commaSeperated2", "commaSeperated3")));
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithCommaSeperatedStringMalformed() {
		givenTheMapWith("testKey-comma-seperated",
				", , ,,,,commaSeperated1, , , ,,,,,commaSeperated2,,,, ,, ,,commaSeperated3,, , ,,,, ,");
		givenNetworkPropsIsCreated();
		whenGetStringListOptIsCalledWith("testKey-comma-seperated");
		thenResultEquals(Optional.of(List.of("commaSeperated1", "commaSeperated2", "commaSeperated3")));
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithString() {
		givenTheMapWith("testKey1", "testString1");
		givenNetworkPropsIsCreated();
		whenGetStringListOptIsCalledWith("testKey1");
		thenResultEquals(Optional.of(List.of("testString1")));
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNull() {
		givenTheMapWith("testKeyNull", null);
		givenNetworkPropsIsCreated();
		whenGetStringListOptIsCalledWith("testKeyNull");
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNoKey() {
		givenTheMapWith("testKeyNull", null);
		givenNetworkPropsIsCreated();
		whenGetStringListOptIsCalledWith("");
		thenResultEquals(Optional.empty());
	}

	/*
	 * Given
	 */

	public void givenNetworkPropertiesBuiltWith(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void givenTheMapWith(String key, Object pair) {
		properties = new HashMap<String, Object>();
		properties.put(key, pair);
	}

	public void givenNetworkPropsIsCreated() {

		try {
			netProps = new NetworkProperties(this.properties);
		} catch (NullPointerException e) {
			this.hasNullPointExceptionBeenThrown = true;
		}
	}

	/*
	 * When
	 */

	public void whenGetPropertiesIsCalled() {
		this.resultMap = this.netProps.getProperties();
	}

	public void whenGetIsCalledWith(String key, Object clazz) {

		if (clazz == String.class) {
			this.stringResult = this.netProps.get(String.class, key, "");
		} else {
			throw new NotSupported("Data type is not supported with this Test");
		}
	}

	public void whenGetOptIsCalledWith(String key, Object clazz) {
		if (clazz == String.class) {
			this.optResult = this.netProps.getOpt(String.class, key, "");
		} else {
			throw new NotSupported("Data type is not supported with this Test");
		}
	}

	public void whenGetStringListIsCalledWith(String key) {
		this.stringListResult = this.netProps.getStringList(key, "");
	}

	public void whenGetStringListOptIsCalledWith(String key) {
		this.optResult = this.netProps.getOptStringList(key, "");
	}

	/*
	 * Then
	 */

	public void thenResultEquals(String result) {
		assertEquals(this.stringResult, result);
	}

	public void thenResultEquals(Map<String, Object> result) {
		assertEquals(this.resultMap, result);
	}

	public void thenResultEquals(Optional<?> result) {
		assertEquals(this.optResult, result);
	}

	public void thenResultEquals(List<String> result) {
		assertEquals(this.stringListResult, result);
	}

	public void thenAnNullPointerExceptionOccured() {
		assertTrue(this.hasNullPointExceptionBeenThrown);
	}

}
