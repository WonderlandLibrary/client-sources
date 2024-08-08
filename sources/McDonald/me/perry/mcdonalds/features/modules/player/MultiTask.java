// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import me.perry.mcdonalds.features.modules.Module;

public class MultiTask extends Module
{
    private static MultiTask INSTANCE;
    
    public MultiTask() {
        super("MultiTask", "Allows you to eat while mining.", Category.PLAYER, false, true, false);
        this.setInstance();
    }
    
    public static MultiTask getInstance() {
        if (MultiTask.INSTANCE == null) {
            MultiTask.INSTANCE = new MultiTask();
        }
        return MultiTask.INSTANCE;
    }
    
    private void setInstance() {
        MultiTask.INSTANCE = this;
    }
    
    static {
        MultiTask.INSTANCE = new MultiTask();
    }
}
