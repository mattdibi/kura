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
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenCompareGetPropertiesToPassedProperties();
	}

	@Test
	public void shouldReturnStringWithGet() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenGetReturnsExpectedObjectWithKey("testKey1");
	}

	@Test
	public void shouldReturnStringWithGetOpt() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedObjectWithKey("testKey1");
	}

	@Test
	public void shouldReturnEmptyGetOptEmpty() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedEmptyWithKey("testKeyEmpty");
	}

	@Test
	public void shouldReturnEmptyGetOptNull() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedEmptyWithKey("testKeyNull");
	}

	@Test
	public void shouldReturnEmptyGetOptEmptyKey() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenGetOptReturnsExpectedEmptyWithKey("");
	}

	@Test
	public void shouldReturnStringListGetStringListWithCommaSeperatedString() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnStringListFromGetStringList("testKey-comma-seperated");
	}

	@Test
	public void shouldReturnStringListGetStringListWithRegularString() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnStringFromGetStringList("testKey1");
	}

	@Test
	public void shouldReturnStringListGetStringListWithNull() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnEmptyListFromGetStringList("testKeyNull");
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithCommaSeperatedString() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnOptStringListOptFromGetStringList("testKey-comma-seperated");
	}

	@Test
	public void shouldReturnOptionalStringListGetStringListOptWithString() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnOptStringOptFromGetStringList("testKey1");
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNull() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnOptEmptyOptFromGetStringList("testKeyNull");
	}

	@Test
	public void shouldReturnEmptyGetStringListOptWithNoKey() {
		givenTheTestProperties();
		whenNetworkPropsIsCreated();
		thenReturnOptEmptyOptFromGetStringList("");
	}

	// Given //
	public void givenTheFollowingProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void givenTheTestProperties() {

		properties = new HashMap<String, Object>();
		properties.put("testKey1", "testString1");
		properties.put("testKey-comma-seperated", "commaSeperated1,commaSeperated2,commaSeperated3");
		properties.put("testKey3", 345);
		properties.put("testKey3", 345);
		properties.put("testKeyEmpty", "");
		properties.put("testKeyNull", null);
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
