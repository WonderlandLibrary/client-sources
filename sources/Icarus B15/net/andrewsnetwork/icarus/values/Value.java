package net.andrewsnetwork.icarus.values;

import net.andrewsnetwork.icarus.module.*;
import net.andrewsnetwork.icarus.*;

public class Value<T>
{
    private T value;
    private T defaultValue;
    private Module module;
    private String name;
    private String commandName;
    
    public Value(final String name, final String commandName, final T value, final Module module) {
        this.name = name;
        this.commandName = commandName;
        this.value = value;
        this.defaultValue = value;
        this.module = module;
        Icarus.getValueManager().addValue(this);
    }
    
    public T getValue() {
        return this.value;
    }
    
    public T getDefaultValue() {
        return this.defaultValue;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getCommandName() {
        return this.commandName;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setValue(final T value) {
        this.value = value;
        Icarus.getFileManager().getFileByName("valuesconfiguration").saveFile();
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setCommandName(final String commandName) {
        this.commandName = commandName;
    }
}
