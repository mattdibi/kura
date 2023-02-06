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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.freedesktop.dbus.errors.NotSupported;
import org.junit.Test;

public class NetworkPropertiesTest {

	NetworkProperties netProps;
	Map<String, Object> properties = new HashMap<String, Object>();
	Optional<?> optResult;
	String stringResult;
	List<String> stringListResult;
	Map<String, Object> resultMap;

	Boolean hasNullPointExceptionBeenThrown = false;
	Boolean hasNoSuchElementExceptionBeenThrown = false;

	@Test
	public void shouldFailWhenNull() {
		givenNetworkPropertiesBuiltWith(null);
		thenANullPointerExceptionOccured();
	}

	@Test
	public void shouldFailWhenEmpty() {
		givenNetworkPropertiesBuiltWith(new HashMap<String, Object>());
		whenGetPropertiesIsCalled();
		thenNoExceptionsOccured();
		thenResultEquals(new HashMap<String, Object>());
	}

	@Test
	public void shouldReturnPropertiesWithGetProperties() {
		givenMapWith("testKey1", "testString1");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetPropertiesIsCalled();
		thenNoExceptionsOccured();
		thenResultEquals(Collections.singletonMap("testKey1", "testString1"));
	}

	@Test
	public void shouldReturnStringWithGet() {
		givenMapWith("testKey1", "testString1");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetIsCalledWith("testKey1", String.class);
		thenNoExceptionsOccured();
		thenResultEquals("testString1");
	}

	@Test
	public void shouldReturnStringWithGetNull() {
		givenMapWith("testKey1", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetIsCalledWith("testKey1", String.class);
		thenANoSuchElementExceptionOccured();
	}

	@Test
	public void shouldReturnStringWithGetMissing() {
		givenMapWith("testKey1", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetIsCalledWith("testKey1-nonExistant", String.class);
		thenANoSuchElementExceptionOccured();
	}

	@Test
	public void shouldReturnStringWithGetEmptyString() {
		givenMapWith("", "");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetIsCalledWith("", String.class);
		thenNoExceptionsOccured();
		thenResultEquals("");
	}

	@Test
	public void shouldReturnStringWithGetOpt() {
		givenMapWith("testKey1", "testString1");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetOptIsCalledWith("testKey1", String.class);
		thenNoExceptionsOccured();
		thenResultEquals(Optional.of("testString1"));
	}

	@Test
	public void shouldReturnEmptyGetOptEmpty() {
		givenMapWith("testKeyEmpty", "");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetOptIsCalledWith("testKeyEmpty", String.class);
		thenNoExceptionsOccured();
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnEmptyGetOptNull() {
		givenMapWith("testKeyNull", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetOptIsCalledWith("testKeyNull", String.class);
		thenNoExceptionsOccured();
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnEmptyGetOptEmptyKey() {
		givenMapWith("testKeyNull", null);
		givenMapWith("testKey1", "testString1");
		givenMapWith("testKeyNull2", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetOptIsCalledWith("", String.class);
		thenNoExceptionsOccured();
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedString() {
		givenMapWith("testKeyNull", null);
		givenMapWith("testKey1", "testString1");
		givenMapWith("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListIsCalledWith("testKey-comma-seperated");
		thenNoExceptionsOccured();
		thenResultEquals(Arrays.asList("commaSeperated1", "commaSeperated2", "commaSeperated3"));
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedStringMalformed() {
		givenMapWith("testKeyNull", null);
		givenMapWith("testKey1", "testString1");
		givenMapWith("testKey-comma-seperated", ",,   ,,,commaSeperated1, ,,,,commaSeperated2,   ,,commaSeperated3,");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListIsCalledWith("testKey-comma-seperated");
		thenNoExceptionsOccured();
		thenResultEquals(Arrays.asList("commaSeperated1", "commaSeperated2", "commaSeperated3"));
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedStringNull() {
		givenMapWith("testKey-comma-seperated", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListIsCalledWith("testKey-comma-seperated");
		thenANoSuchElementExceptionOccured();
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedStringMissing() {
		givenMapWith("testKey-comma-seperated", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListIsCalledWith("testKey-comma-seperated-not-existant");
		thenANoSuchElementExceptionOccured();
	}

	@Test
	public void shouldReturnStringListGetStringListWithRegularString() {
		givenMapWith("testKey1", "testString1");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListIsCalledWith("testKey1");
		thenNoExceptionsOccured();
		thenResultEquals(Arrays.asList("testString1"));
	}

	@Test
	public void shouldReturnStringListGetStringListWithNull() {
		givenMapWith("testKeyNull", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListIsCalledWith("testKeyNull");
		thenNoExceptionsOccured();
		thenResultEquals(Arrays.asList());
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithCommaSeperatedString() {
		givenMapWith("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListOptIsCalledWith("testKey-comma-seperated");
		thenNoExceptionsOccured();
		thenResultEquals(Optional.of(Arrays.asList("commaSeperated1", "commaSeperated2", "commaSeperated3")));
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithCommaSeperatedStringMalformed() {
		givenMapWith("testKey-comma-seperated",
				", , ,,,,commaSeperated1, , , ,,,,,commaSeperated2,,,, ,, ,,commaSeperated3,, , ,,,, ,");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListOptIsCalledWith("testKey-comma-seperated");
		thenNoExceptionsOccured();
		thenResultEquals(Optional.of(Arrays.asList("commaSeperated1", "commaSeperated2", "commaSeperated3")));
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithString() {
		givenMapWith("testKey1", "testString1");
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListOptIsCalledWith("testKey1");
		thenNoExceptionsOccured();
		thenResultEquals(Optional.of(Arrays.asList("testString1")));
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNull() {
		givenMapWith("testKeyNull", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListOptIsCalledWith("testKeyNull");
		thenNoExceptionsOccured();
		thenResultEquals(Optional.empty());
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNoKey() {
		givenMapWith("testKeyNull", null);
		givenNetworkPropertiesBuiltWith(this.properties);
		whenGetStringListOptIsCalledWith("");
		thenNoExceptionsOccured();
		thenResultEquals(Optional.empty());
	}

	/*
	 * Given
	 */

	public void givenMapWith(String key, Object pair) {
		properties.put(key, pair);
	}

	public void givenNetworkPropertiesBuiltWith(Map<String, Object> properties) {

		try {
			netProps = new NetworkProperties(properties);

		} catch (NullPointerException e) {
			this.hasNullPointExceptionBeenThrown = true;
		} catch (NoSuchElementException e) {
			this.hasNoSuchElementExceptionBeenThrown = true;
		}
	}

	/*
	 * When
	 */

	public void whenGetPropertiesIsCalled() {
		this.resultMap = this.netProps.getProperties();
	}

	public void whenGetIsCalledWith(String key, Object clazz) {

		try {

			if (clazz == String.class) {
				this.stringResult = this.netProps.get(String.class, key, "");
			} else {
				throw new NotSupported("Data type is not supported with this Test");
			}

		} catch (NullPointerException e) {
			this.hasNullPointExceptionBeenThrown = true;
		} catch (NoSuchElementException e) {
			this.hasNoSuchElementExceptionBeenThrown = true;
		}
	}

	public void whenGetOptIsCalledWith(String key, Object clazz) {
		try {
			if (clazz == String.class) {
				this.optResult = this.netProps.getOpt(String.class, key, "");
			} else {
				throw new NotSupported("Data type is not supported with this Test");
			}

		} catch (NullPointerException e) {
			this.hasNullPointExceptionBeenThrown = true;
		} catch (NoSuchElementException e) {
			this.hasNoSuchElementExceptionBeenThrown = true;
		}
	}

	public void whenGetStringListIsCalledWith(String key) {
		try {
			this.stringListResult = this.netProps.getStringList(key, "");

		} catch (NullPointerException e) {
			this.hasNullPointExceptionBeenThrown = true;
		} catch (NoSuchElementException e) {
			this.hasNoSuchElementExceptionBeenThrown = true;
		}
	}

	public void whenGetStringListOptIsCalledWith(String key) {
		try {
			this.optResult = this.netProps.getOptStringList(key, "");

		} catch (NullPointerException e) {
			this.hasNullPointExceptionBeenThrown = true;
		} catch (NoSuchElementException e) {
			this.hasNoSuchElementExceptionBeenThrown = true;
		}
	}

	/*
	 * Then
	 */

	public void thenResultEquals(String result) {
		assertEquals(result, this.stringResult);
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

	public void thenANullPointerExceptionOccured() {
		assertTrue(this.hasNullPointExceptionBeenThrown);
	}

	public void thenANoSuchElementExceptionOccured() {
		assertTrue(this.hasNoSuchElementExceptionBeenThrown);
	}

	public void thenNoExceptionsOccured() {
		assertFalse(this.hasNullPointExceptionBeenThrown);
		assertFalse(this.hasNoSuchElementExceptionBeenThrown);
	}

}
