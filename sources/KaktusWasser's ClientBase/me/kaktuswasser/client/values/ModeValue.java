// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.values;

import me.kaktuswasser.client.*;
import me.kaktuswasser.client.module.*;

public class ModeValue extends Value
{
    private String value;
    private String[] values;
    
    public ModeValue(final String name, final String commandName, final String value, final String[] values, final Module module) {
        super(name, commandName, values, module);
        this.value = value;
        this.values = values;
    }
    
    public String getStringValue() {
        return this.value;
    }
    
    public void setStringValue(final String value) {
        this.value = value;
        Client.getFileManager().getFileByName("valuesconfiguration").saveFile();
    }
    
    public String[] getStringValues() {
        return this.values;
    }
}
