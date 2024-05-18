// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.management.option;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.triton.management.module.Module;

public class Option<T>
{
    private String id;
    private String name;
    private T value;
    private Module module;
    private static List<Option> optionList;
    
    public Option(final String id, final String name, final T value, final Module module) {
        this.optionList = new ArrayList<Option>();
        this.id = id;
        this.name = name;
        this.value = value;
        this.module = module;
    }
    
    public Option(String valType, T type, String name, String selected, String[] options) {
		this.optionList = new ArrayList<Option>();
		this.name = name;
		this.value = (T) selected;
	}

	public void setValue(final T value) {
        this.value = value;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getDisplayName() {
        return this.name;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public static List<Option> getOptions() {
        return optionList;
    }
    
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Op {
        String name() default "null";
        
        double min() default 1.0;
        
        double max() default 10.0;
        
        double increment() default 1.0;
    }
}
