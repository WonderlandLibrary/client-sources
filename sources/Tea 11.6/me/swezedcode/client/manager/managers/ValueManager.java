package me.swezedcode.client.manager.managers;

import java.util.ArrayList;

import me.swezedcode.client.utils.values.Value;

public class ValueManager {

	private ArrayList<Value> values = new ArrayList<Value>();

	public static ValueManager INSTANCE = new ValueManager();
	
	public void register(Value<?> value) {
		values.add(value);
	}
	
	public ArrayList<Value> getValues(Object o) {
		ArrayList<Value> values = new ArrayList<Value>();
		for(Value v : this.values) {
			if(v.getParent() == o) {
				values.add(v);
			}
		}
		return values;
	}
	
}
