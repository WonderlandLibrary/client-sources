package zamorozka.ui;

import java.util.HashMap;
import java.util.Map;

public class ValuesRegistry {
	
	private Map<String,Object> registry = new HashMap<String,Object>();
	
	public boolean hasValue(String name){
		return registry.get(name) != null;
	}
	
	public Object get(String name){
		return registry.get(name);
	}
	
	public int getInteger(String name){
		return (Integer) registry.get(name);
	}
	
	public double getDouble(String name){
		return (Double) registry.get(name);
	}
	
	public boolean getBoolean(String name){
		return (Boolean) registry.get(name);
	}
	
	public void set(String name, Object object){
		//We can't use Map.replace because it's only in Java 8
		registry.remove(name);
		registry.put(name, object);
	}
	
	public Map<String, Object> getRegistry() {
		return registry;
	}
	
	public void setRegistry(Map<String, Object> registry) {
		this.registry = registry;
	}
}