package com.dunzo.coffee_machine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dunzo.coffee_machine.model.Item;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Class Serve.
 * 
 * @Architecture There are main two component. 1. parse the JSON input. It can
 *               be a variable structure based on machine. So, parse it first.
 *               Then we can store required data. 2. After that we can use the
 *               data from JSON to check availability and sufficiency of every
 *               element one by one. If we face problem with availability and
 *               sufficiency we can stop the parsing and make note of it. Which
 *               will generate required output.
 */
@Service
public class Serve {

	/** The items. */
	private Map<String, Integer> items;

	/** The bevarage items. */
	private List<Item>[] bevarageItems;

	/** The bevarage name. */
	private Map<Integer, String> bevarageName;

	/**
	 * Serve coffee.
	 * 
	 * @functionality Method to perform serve coffee operation and determine the
	 *                quantity availability and sufficiency of items w.r.t. every
	 *                Beverage.
	 * @param input the jsonNode object
	 * @return the required output string
	 */
	public String serveCoffee(JsonNode input) {
		// parsing the variable structure json input
		int noOfOutlets = jsonParser(input);
		StringBuilder output = new StringBuilder();
		// for every outlet
		for (int i = 0; i < noOfOutlets; i++) {
			// to check availability of items
			boolean isAvailable = true;
			// to check sufficiency of items
			boolean isSufficient = true;
			StringBuilder internal = new StringBuilder();
			Map<String, Integer> usedItems = new HashMap<>();
			for (Item item : bevarageItems[i]) {
				// if available
				if (items.containsKey(item.getItemName())) {
					isAvailable = true;
					// if sufficient
					if (item.getItemQuantity() <= items.get(item.getItemName())) {
						usedItems.put(item.getItemName(), items.get(item.getItemName()) - item.getItemQuantity());
						isSufficient &= true;
					}
					// if not sufficient
					else {
						isSufficient &= false;
						internal = new StringBuilder();
						internal.append("item " + item.getItemName() + " is not sufficient");
					}
				}
				// if not available
				else {
					isAvailable = false;
					internal = new StringBuilder();
					internal.append(item.getItemName() + " is not available");
					break;
				}
			}
			// Able to prepare
			if (isAvailable && isSufficient) {
				output.append(bevarageName.get(i) + " is prepared\n");
				// update items quentities
				for (Map.Entry<String, Integer> entry : items.entrySet()) {
					if (usedItems.containsKey(entry.getKey())) {
						items.put(entry.getKey(), usedItems.get(entry.getKey()));
					}
				}
			}
			// Not able to prepare
			else {
				output.append(bevarageName.get(i) + " cannot be prepared because ");
				output.append(internal + "\n");
			}
		}
		return output.toString();
	}

	/**
	 * JSON parser.
	 * 
	 * @functionalityTo parse the incoming JSON file which can be variable in it's
	 *                  structure. Example : It can have different number of
	 *                  beverages.
	 * @param input the input
	 * @return the int outlet numbers.o
	 */
	@SuppressWarnings("unchecked")
	private int jsonParser(JsonNode input) {
		// parse machine
		JsonNode machine = input.get("machine");
		// get number of outlets
		int noOfOutlets = machine.get("outlets").get("count_n").asInt();
		JsonNode itemsNode = machine.get("total_items_quantity");
		JsonNode bevaragesNode = machine.get("beverages");
		// listing names and quantities of all items in machine
		List<String> itemsNames = new ArrayList<>();
		itemsNode.fieldNames().forEachRemaining(oneInput -> itemsNames.add(oneInput));
		items = new HashMap<>();
		for (String s : itemsNames) {
			items.put(s, itemsNode.get(s).asInt());
		}
		// listing names of all beverages
		List<String> bevaragesNames = new ArrayList<>();
		bevaragesNode.fieldNames().forEachRemaining(oneInput -> bevaragesNames.add(oneInput));
		int position = 0;
		bevarageName = new HashMap<Integer, String>();
		for (String s : bevaragesNames) {
			bevarageName.put(position, s);
			position = position + 1;
		}
		Map<String, JsonNode> bevarageNode = new HashMap<>();
		bevarageItems = new ArrayList[bevaragesNames.size()];
		int index = 0;
		// listing names of items and quantities in every beverage
		for (String s : bevaragesNames) {
			bevarageNode.put(s, bevaragesNode.get(s));
			List<String> listBevarageItems = new ArrayList<>();
			bevaragesNode.get(s).fieldNames().forEachRemaining(oneInput -> listBevarageItems.add(oneInput));
			List<Item> item = new ArrayList<>();
			for (String itemName : listBevarageItems) {
				item.add(new Item(itemName, bevaragesNode.get(s).get(itemName).asInt()));
			}
			bevarageItems[index] = item;
			index = index + 1;
		}
		return noOfOutlets;
	}
}
