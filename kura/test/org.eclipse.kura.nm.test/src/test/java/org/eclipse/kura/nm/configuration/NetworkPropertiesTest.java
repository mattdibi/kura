package org.eclipse.kura.nm.configuration;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

public class NetworkPropertiesTest {

	NetworkProperties netProps;
	Map<String, Object> properties;

	@Test(expected = NullPointerException.class)
	public void shouldFailWhenNull() {
		givenTheFollowingProperties(null);
		whenNetworkPropsIsCreated();
	}

	@Test
	public void shouldReturnPropertiesWithGetProperties() {
		givenTheMapWith("testKey1", "testString1");
		whenNetworkPropsIsCreated();
		thenCompareGetPropertiesToPassedProperties();
	}

	@Test
	public void shouldReturnStringWithGet() {
		givenTheMapWith("testKey1", "testString1");
		whenNetworkPropsIsCreated();
		thenGetReturnsExpectedObjectWithKey("testKey1");
	}

	@Test
	public void shouldReturnStringWithGetOpt() {
		givenTheMapWith("testKey1", "testString1");
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedObjectWithKey("testKey1");
	}

	@Test
	public void shouldReturnEmptyGetOptEmpty() {
		givenTheMapWith("testKeyEmpty", "");
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedEmptyWithKey("testKeyEmpty");
	}

	@Test
	public void shouldReturnEmptyGetOptNull() {
		givenTheMapWith("testKeyNull", null);
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedEmptyWithKey("testKeyNull");
	}

	@Test
	public void shouldReturnEmptyGetOptEmptyKey() {
		givenTheMapWith("testKeyNull", null);
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedEmptyWithKey("");
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedString() {
		givenTheMapWith("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		whenNetworkPropsIsCreated();
		thenReturnStringListFromGetStringList("testKey-comma-seperated");
	}

	@Test
	public void shouldReturnStringListGetStringListWithRegularString() {
		givenTheMapWith("testKey1", "testString1");
		whenNetworkPropsIsCreated();
		thenReturnStringFromGetStringList("testKey1");
	}

	@Test
	public void shouldReturnStringListGetStringListWithNull() {
		givenTheMapWith("testKeyNull", null);
		whenNetworkPropsIsCreated();
		thenReturnEmptyListFromGetStringList("testKeyNull");
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithCommaSeperatedString() {
		givenTheMapWith("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		whenNetworkPropsIsCreated();
		thenReturnOptStringListOptFromGetStringList("testKey-comma-seperated");
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithString() {
		givenTheMapWith("testKey1", "testString1");
		whenNetworkPropsIsCreated();
		thenReturnOptStringOptFromGetStringList("testKey1");
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNull() {
		givenTheMapWith("testKeyNull", null);
		whenNetworkPropsIsCreated();
		thenReturnOptEmptyOptFromGetStringList("testKeyNull");
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNoKey() {
		givenTheMapWith("testKeyNull", null);
		whenNetworkPropsIsCreated();
		thenReturnOptEmptyOptFromGetStringList("");
	}

	// Given //
	public void givenTheFollowingProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public void givenTheMapWith(String key, Object pair) {
		properties = new HashMap<String, Object>();
		properties.put(key, pair);
	}
	// End of Given //

	// When //

	public void whenNetworkPropsIsCreated() {
		netProps = new NetworkProperties(this.properties);
	}

	// End of When //

	// Then //
	public void thenGetReturnsExpectedObjectWithKey(String key) {
		assertEquals(this.netProps.get(String.class, key, ""), properties.get(key));
	}

	public void thenGetOptReturnsExpectedObjectWithKey(String key) {
		Optional<?> temp = this.netProps.getOpt(String.class, key, "");
		Optional<?> expected = Optional.of(properties.get(key));

		assertEquals(temp, expected);
	}

	public void thenGetOptReturnsExpectedEmptyWithKey(String key) {
		Optional<?> temp = this.netProps.getOpt(String.class, key, "");
		Optional<?> expected = Optional.empty();

		assertEquals(temp, expected);
	}

	public void thenCompareGetPropertiesToPassedProperties() {
		assertEquals(this.netProps.getProperties(), this.properties);
	}

	public void thenReturnStringListFromGetStringList(String key) {
		assertEquals(this.netProps.getStringList(key, ""),
				List.of("commaSeperated1", "commaSeperated2", "commaSeperated3"));
	}

	public void thenReturnStringFromGetStringList(String key) {
		assertEquals(this.netProps.getStringList(key, ""), List.of("testString1"));
	}

	public void thenReturnEmptyListFromGetStringList(String key) {
		assertEquals(this.netProps.getStringList(key, ""), List.of());
	}

	public void thenReturnOptStringListOptFromGetStringList(String key) {
		Optional<?> expected = Optional.of(List.of("commaSeperated1", "commaSeperated2", "commaSeperated3"));
		assertEquals(this.netProps.getOptStringList(key, ""), expected);
	}

	public void thenReturnOptStringOptFromGetStringList(String key) {
		Optional<?> expected = Optional.of(List.of("testString1"));
		assertEquals(this.netProps.getOptStringList(key, ""), expected);
	}

	public void thenReturnOptEmptyOptFromGetStringList(String key) {
		Optional<?> expected = Optional.empty();
		assertEquals(this.netProps.getOptStringList(key, ""), expected);
	}
	// End of Then //

}
