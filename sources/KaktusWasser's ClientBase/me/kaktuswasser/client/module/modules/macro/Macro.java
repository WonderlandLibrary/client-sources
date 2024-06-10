// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module.modules.macro;

public class Macro
{
    private final String command;
    private final int key;
    
    public Macro(final String command, final int key) {
        this.command = command;
        this.key = key;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public int getKey() {
        return this.key;
    }
}
