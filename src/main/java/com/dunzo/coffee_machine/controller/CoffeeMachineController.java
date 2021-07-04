package com.dunzo.coffee_machine.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dunzo.coffee_machine.service.Serve;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Class CoffeeMachineController.
 */
@RestController
public class CoffeeMachineController {
	
	/** The serve. */
	private Serve serve;
	
	/**
	 * Instantiates a new coffee machine controller.
	 *
	 * @param serve the serve
	 */
	public CoffeeMachineController(Serve serve) {
		super();
		this.serve = serve;
	}

	/**
	 * Serve coffee.
	 *
	 * @param input the input
	 * @return the string
	 */
	@PostMapping("/serve")
	public String serveCoffee(@RequestBody(required = true) JsonNode input) {
		
		String response = serve.serveCoffee(input);
		
		return response;
	}
	
}
