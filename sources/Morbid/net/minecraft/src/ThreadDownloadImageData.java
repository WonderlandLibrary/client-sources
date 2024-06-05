package net.minecraft.src;

import java.awt.image.*;

public class ThreadDownloadImageData
{
    public BufferedImage image;
    public int referenceCount;
    public int textureName;
    public boolean textureSetupComplete;
    
    public ThreadDownloadImageData(final String par1, final IImageBuffer par2IImageBuffer) {
        this.referenceCount = 1;
        this.textureName = -1;
        this.textureSetupComplete = false;
        new ThreadDownloadImage(this, par1, par2IImageBuffer).start();
    }
}
