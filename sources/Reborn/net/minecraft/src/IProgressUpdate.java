package net.minecraft.src;

public interface IProgressUpdate
{
    void displayProgressMessage(final String p0);
    
    void resetProgressAndMessage(final String p0);
    
    void resetProgresAndWorkingMessage(final String p0);
    
    void setLoadingProgress(final int p0);
    
    void onNoMoreProgress();
}
