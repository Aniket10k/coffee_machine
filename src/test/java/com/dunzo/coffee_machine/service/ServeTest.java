package com.dunzo.coffee_machine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ServeTest.
 */
public class ServeTest {

	/** The serve. */
	private static Serve serve;

	/**
	 * Sets the up.
	 */
	@BeforeAll
	static void setUp() {
		serve = new Serve();
	}

	/**
	 * Serve coffee test.
	 *
	 * @throws JsonMappingException    the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	public void serveCoffeeTest() throws JsonMappingException, JsonProcessingException {

		String input = "{\"machine\":{\"outlets\":{\"count_n\":4},\"total_items_quantity\":{\"hot_water\":500,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":100,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"green_tea\":{\"hot_water\":100,\"ginger_syrup\":30,\"sugar_syrup\":50,\"green_mixture\":30}}}}";

		ObjectMapper mapper = new ObjectMapper();
		JsonNode inputNode = mapper.readTree(input);

		String expected = "hot_tea is prepared\n" + "hot_coffee is prepared\n"
				+ "black_tea cannot be prepared because item sugar_syrup is not sufficient\n"
				+ "green_tea cannot be prepared because green_mixture is not available\n";
		String actual = serve.serveCoffee(inputNode);

		assertEquals(expected, actual);
		assertNotEquals("", actual);

	}

}
