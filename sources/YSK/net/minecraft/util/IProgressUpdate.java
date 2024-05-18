package net.minecraft.util;

public interface IProgressUpdate
{
    void displaySavingString(final String p0);
    
    void resetProgressAndMessage(final String p0);
    
    void setDoneWorking();
    
    void displayLoadingString(final String p0);
    
    void setLoadingProgress(final int p0);
}
