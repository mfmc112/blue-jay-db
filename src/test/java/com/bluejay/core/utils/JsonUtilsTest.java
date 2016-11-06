package com.bluejay.core.utils;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;
@SuppressWarnings("unchecked")
public class JsonUtilsTest {

	@Test
	public void convertJsonToMapTest() throws FCException{
		String json = "{\"id\":123, \"name\":\"Marcos Costa\"}";
		Map<String, Object> map = JsonUtils.convertJsonToMap(json);
		Assert.assertEquals("123", map.get("id"));
		Assert.assertEquals("Marcos Costa", map.get("name"));
	}
	
	@Test
	public void convertJsonToMapWithInnerArrayTest() throws FCException{
		String json = "{\"id\":123, \"name\":\"Marcos Costa\", \"cars\":[{\"make\":\"Infiniti\", \"model\":\"G37\"},{\"make\":\"Toyota\", \"model\":\"Supra\"}]}";
		Map<String, Object> map = JsonUtils.convertJsonToMap(json);
		Assert.assertEquals("123", map.get("id"));
		Assert.assertEquals("Marcos Costa", map.get("name"));
		Assert.assertTrue(map.get("cars") instanceof List);
		
		Object[] obj = ((List<Map<String, Object>>) map.get("cars")).toArray();

		Assert.assertEquals("Infiniti", ((Map<String, Object>)obj[0]).get("cars.make"));
		Assert.assertEquals("G37", ((Map<String, Object>)obj[0]).get("cars.model"));
		Assert.assertEquals("Toyota", ((Map<String, Object>)obj[1]).get("cars.make"));
		Assert.assertEquals("Supra", ((Map<String, Object>)obj[1]).get("cars.model"));
	}
	
	@Test
	public void convertJsonToMapWithMultiLevelInnerArrayTest() throws FCException{
		String json = "{\"id\":123, \"name\":\"Marcos Costa\", \"cars\":[{\"make\":\"Infiniti\", \"model\":\"G37\", \"accessories\":[{\"type\":\"GPS\", \"installed\":\"true\"},{\"type\":\"Auto Drive\", \"installed\":\"false\"}]},{\"make\":\"Toyota\", \"model\":\"Supra\"}]}";
		Map<String, Object> map = JsonUtils.convertJsonToMap(json);
		Assert.assertEquals("123", map.get("id"));
		Assert.assertEquals("Marcos Costa", map.get("name"));
		Assert.assertTrue(map.get("cars") instanceof List);
		
		List<Map<String, Object>> cars = ((List<Map<String, Object>>) map.get("cars"));

		Assert.assertEquals("Infiniti", cars.get(0).get("cars.make"));
		Assert.assertEquals("G37", cars.get(0).get("cars.model"));
		List<Map<String, Object>> accessoriesForCar0 = ((List<Map<String, Object>>) cars.get(0).get("cars.accessories"));
		
		Assert.assertEquals("GPS", accessoriesForCar0.get(0).get("accessories.type"));
		Assert.assertEquals("true", accessoriesForCar0.get(0).get("accessories.installed"));
		Assert.assertEquals("Auto Drive", accessoriesForCar0.get(1).get("accessories.type"));
		Assert.assertEquals("false", accessoriesForCar0.get(1).get("accessories.installed"));
		
		List<Map<String, Object>> accessoriesForCar1 = ((List<Map<String, Object>>) cars.get(1).get("cars.accessories"));
		Assert.assertNull(accessoriesForCar1);
	}
	
	@Test(expected=FCException.class)
	public void convertJsonToMapNullTest() throws FCException{
		JsonUtils.convertJsonToMap(null);
		Assert.assertTrue(false);
	}
	
	@Test(expected=FCException.class)
	public void convertJsonToMapEmptyTest() throws FCException{
		String json = "{}";
		JsonUtils.convertJsonToMap(json);
		Assert.assertTrue(false);
	}
	
	@Test(expected=FCException.class)
	public void convertJsonToMapInvalidJsonTest() throws FCException{
		String json = "{\"id\":123, \"name\":\"Marcos Costa\", \"cars\":[{\"make\":\"Infiniti\", \"model\":\"G37\"},{\"make\":\"Toyota\", \"model\":\"Supra\"}}";
		JsonUtils.convertJsonToMap(json);
		Assert.assertTrue(false);
	}
	
}
