package net.minecraft.src;

import net.minecraft.server.*;

public class ConvertingProgressUpdate implements IProgressUpdate
{
    private long field_96245_b;
    final MinecraftServer mcServer;
    
    public ConvertingProgressUpdate(final MinecraftServer par1) {
        this.mcServer = par1;
        this.field_96245_b = System.currentTimeMillis();
    }
    
    @Override
    public void displayProgressMessage(final String par1Str) {
    }
    
    @Override
    public void resetProgressAndMessage(final String par1Str) {
    }
    
    @Override
    public void setLoadingProgress(final int par1) {
        if (System.currentTimeMillis() - this.field_96245_b >= 1000L) {
            this.field_96245_b = System.currentTimeMillis();
            this.mcServer.getLogAgent().logInfo("Converting... " + par1 + "%");
        }
    }
    
    @Override
    public void onNoMoreProgress() {
    }
    
    @Override
    public void resetProgresAndWorkingMessage(final String par1Str) {
    }
}
