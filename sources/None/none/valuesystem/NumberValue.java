/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package none.valuesystem;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.function.Predicate;

public class NumberValue<T extends Number> extends Value<T> {
    private T min;
    private T max;

    public NumberValue(String name, T defaultVal, T min, T max) {
        this(name, defaultVal, min, max, null);
    }

    public NumberValue(String name, T defaultVal, T min, T max, Predicate<T> validator) {
        super(name, defaultVal, validator == null ? val -> val.doubleValue() >= min.doubleValue() && val.doubleValue() <= max.doubleValue() : validator.and(val -> val.doubleValue() >= min.doubleValue() && val.doubleValue() <= max.doubleValue()));
        this.min = min;
        this.max = max;
    }
    
    public double getDouble() {
    	if (getObject() instanceof Double)
    		return getObject().doubleValue();
    	return getObject().doubleValue();
    }
    
    public int getInteger() {
    	if (getObject() instanceof Integer)
    		return getObject().intValue();
    	return getObject().intValue();
    }
    
    public float getFloat() {
    	if (getObject() instanceof Float)
    		return getObject().floatValue();
    	return getObject().floatValue();
    }
    
    public byte getByte() {
    	if (getObject() instanceof Byte)
    		return getObject().byteValue();
    	return getObject().byteValue();
    }
    public long getLong() {
    	if (getObject() instanceof Long)
    		return getObject().longValue();
    	return getObject().longValue();
    }
    
    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    @Override
    public void addToJsonObject(JsonObject obj) {
        obj.addProperty(getName(), getObject());
    }

    @Override
    public void fromJsonObject(JsonObject obj) {
        if (obj.has(getName())) {
            JsonElement element = obj.get(getName());

            if (element instanceof JsonPrimitive && ((JsonPrimitive) element).isNumber()) {
            	try {
                if (getObject() instanceof Integer) {
                    setObject((T) Integer.valueOf(obj.get(getName()).getAsNumber().intValue()));
                }
                if (getObject() instanceof Long) {
                    setObject((T) Long.valueOf(obj.get(getName()).getAsNumber().longValue()));
                }
                if (getObject() instanceof Float) {
                    setObject((T) Float.valueOf(obj.get(getName()).getAsNumber().floatValue()));
                }
                if (getObject() instanceof Double) {
                    setObject((T) Double.valueOf(obj.get(getName()).getAsNumber().doubleValue()));
                }
            	}catch (Exception e) {
            		e.printStackTrace();
            	}
            } else {
                throw new IllegalArgumentException("Entry '" + getName() + "' is not valid");
            }
        } else {
            throw new IllegalArgumentException("Object does not have '" + getName() + "'");
        }
    }
}
