package com.kanishk.marketo.data;

import java.util.HashMap;
import java.util.Map;

public enum Type {
	
	ID("songid"),
	TITLE("title"),
	ARTIST("artist");
	
	private String value;
	
	private static Map<String, Type> lookup = new HashMap<>(Type.values().length);
	
	static {
		Type[] types = Type.values();
		for(Type t : types) {
			lookup.put(t.value, t);
		}
	}
	
	private Type(String name) {
		this.value = name;
	}

	/**
	 * Gets the equivalent string value of this enum.
	 * @return the string value
	 */
	public static Type getType(String typeVal) {
		return lookup.get(typeVal);
	}
	
	/**
	 * Equals. To check whether the string value is of the enum type.
	 * @param sortType the sort type string
	 * @return true, if successful
	 */
	public boolean equals(String sortType) {
		return value.equals(sortType);
	}
}
