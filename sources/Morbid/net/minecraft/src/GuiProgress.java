package net.minecraft.src;

public class GuiProgress extends GuiScreen implements IProgressUpdate
{
    private String progressMessage;
    private String workingMessage;
    private int currentProgress;
    private boolean noMoreProgress;
    
    public GuiProgress() {
        this.progressMessage = "";
        this.workingMessage = "";
        this.currentProgress = 0;
    }
    
    @Override
    public void displayProgressMessage(final String par1Str) {
        this.resetProgressAndMessage(par1Str);
    }
    
    @Override
    public void resetProgressAndMessage(final String par1Str) {
        this.progressMessage = par1Str;
        this.resetProgresAndWorkingMessage("Working...");
    }
    
    @Override
    public void resetProgresAndWorkingMessage(final String par1Str) {
        this.workingMessage = par1Str;
        this.setLoadingProgress(0);
    }
    
    @Override
    public void setLoadingProgress(final int par1) {
        this.currentProgress = par1;
    }
    
    @Override
    public void onNoMoreProgress() {
        this.noMoreProgress = true;
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        if (this.noMoreProgress) {
            this.mc.displayGuiScreen(null);
        }
        else {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRenderer, this.progressMessage, this.width / 2, 70, 16777215);
            this.drawCenteredString(this.fontRenderer, String.valueOf(this.workingMessage) + " " + this.currentProgress + "%", this.width / 2, 90, 16777215);
            super.drawScreen(par1, par2, par3);
        }
    }
}
