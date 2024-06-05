package com.mojang.realmsclient.gui;

import com.mojang.realmsclient.gui.screens.*;
import net.minecraft.realms.*;

public abstract class LongRunningTask implements Runnable, ErrorCallback, GuiCallback
{
    protected RealmsLongRunningMcoTaskScreen longRunningMcoTaskScreen;
    
    public void setScreen(final RealmsLongRunningMcoTaskScreen longRunningMcoTaskScreen) {
        this.longRunningMcoTaskScreen = longRunningMcoTaskScreen;
    }
    
    @Override
    public void error(final String errorMessage) {
        this.longRunningMcoTaskScreen.error(errorMessage);
    }
    
    public void setTitle(final String title) {
        this.longRunningMcoTaskScreen.setTitle(title);
    }
    
    public boolean aborted() {
        return this.longRunningMcoTaskScreen.aborted();
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void buttonClicked(final RealmsButton button) {
    }
    
    public void init() {
    }
    
    public void abortTask() {
    }
}
