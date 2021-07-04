package com.dunzo.coffee_machine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dunzo.coffee_machine.service.Serve;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CoffeeMachineControllerTest.
 */
public class CoffeeMachineControllerTest {

	/** The mock mvc. */
	private static MockMvc mockMvc;

	/** The serve. */
	private static Serve serve;

	/** The controller. */
	private static CoffeeMachineController controller;

	/**
	 * Setup.
	 */
	@BeforeAll
	static void setup() {
		serve = mock(Serve.class);
		controller = new CoffeeMachineController(serve);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/**
	 * Serve coffee test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void serveCoffeeTest() throws Exception {

		String input = "{\"machine\":{\"outlets\":{\"count_n\":4},\"total_items_quantity\":{\"hot_water\":500,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":100,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"green_tea\":{\"hot_water\":100,\"ginger_syrup\":30,\"sugar_syrup\":50,\"green_mixture\":30}}}}";

		ObjectMapper mapper = new ObjectMapper();
		JsonNode inputNode = mapper.readTree(input);
		mockMvc.perform(MockMvcRequestBuilders.post("/serve").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(input)).andReturn();

		String expected = "hot_tea is prepared\n" + "hot_coffee is prepared\n"
				+ "black_tea cannot be prepared because item sugar_syrup is not sufficient\n"
				+ "green_tea cannot be prepared because green_mixture is not available\n";

		Mockito.when(serve.serveCoffee(inputNode)).thenReturn(expected);

		String actual = controller.serveCoffee(inputNode);
		assertEquals(expected, actual);
		assertNotEquals("", actual);
	}

}
