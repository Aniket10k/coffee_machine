package com.dunzo.coffee_machine.model;

/**
 * The Class Item.
 */
public class Item {

	/** The item name. */
	private String itemName;

	/** The item quantity. */
	private int itemQuantity;

	/**
	 * Instantiates a new item.
	 *
	 * @param itemName the item name
	 * @param itemQuentity the item quantity
	 */
	public Item(String itemName, int itemQuantity) {
		super();
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Gets the item quantity.
	 *
	 * @return the item quantity
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}

}
