package net.minecraft.server;

import net.minecraft.util.*;

class MinecraftServer$1 implements IProgressUpdate {
    private long startTime = System.currentTimeMillis();
    
    @Override
    public void displaySavingString(final String message) {
    }
    
    @Override
    public void resetProgressAndMessage(final String p_73721_1_) {
    }
    
    @Override
    public void setLoadingProgress(final int progress) {
        if (System.currentTimeMillis() - this.startTime >= 1000L) {
            this.startTime = System.currentTimeMillis();
            MinecraftServer.access$000().info("Converting... " + progress + "%");
        }
    }
    
    @Override
    public void setDoneWorking() {
    }
    
    @Override
    public void displayLoadingString(final String message) {
    }
}