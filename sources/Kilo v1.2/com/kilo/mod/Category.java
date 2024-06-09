package com.kilo.mod;

public enum Category {
	ALL(-1),
	BUILD(0),
	COMBAT(1),
	DISPLAY(2),
	MISC(3),
	MOVEMENT(4),
	PLAYER(5);
	
	public int index;
	
	Category(int i) {
		index = i;
	}
}
