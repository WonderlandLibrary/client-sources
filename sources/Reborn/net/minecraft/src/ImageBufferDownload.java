package net.minecraft.src;

import java.awt.image.*;
import java.awt.*;

public class ImageBufferDownload implements IImageBuffer
{
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;
    
    @Override
    public BufferedImage parseUserSkin(final BufferedImage par1BufferedImage) {
        if (par1BufferedImage == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 32;
        while (this.imageWidth < par1BufferedImage.getWidth() || this.imageHeight < par1BufferedImage.getHeight()) {
            this.imageWidth *= 2;
            this.imageHeight *= 2;
        }
        final BufferedImage var3 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        final Graphics var4 = var3.getGraphics();
        var4.drawImage(par1BufferedImage, 0, 0, null);
        var4.dispose();
        this.imageData = ((DataBufferInt)var3.getRaster().getDataBuffer()).getData();
        final int var5 = this.imageWidth;
        final int var6 = this.imageHeight;
        this.setAreaOpaque(0, 0, var5 / 2, var6 / 2);
        this.setAreaTransparent(var5 / 2, 0, var5, var6);
        this.setAreaOpaque(0, var6 / 2, var5, var6);
        boolean var7 = false;
        for (int var8 = var5 / 2; var8 < var5; ++var8) {
            for (int var9 = 0; var9 < var6 / 2; ++var9) {
                final int var10 = this.imageData[var8 + var9 * var5];
                if ((var10 >> 24 & 0xFF) < 128) {
                    var7 = true;
                }
            }
        }
        if (!var7) {
            for (int var8 = var5 / 2; var8 < var5; ++var8) {
                for (int var9 = 0; var9 < var6 / 2; ++var9) {
                    final int var10 = this.imageData[var8 + var9 * var5];
                    if ((var10 >> 24 & 0xFF) < 128) {}
                }
            }
        }
        return var3;
    }
    
    private void setAreaTransparent(final int par1, final int par2, final int par3, final int par4) {
        if (!this.hasTransparency(par1, par2, par3, par4)) {
            for (int var5 = par1; var5 < par3; ++var5) {
                for (int var6 = par2; var6 < par4; ++var6) {
                    final int[] imageData = this.imageData;
                    final int n = var5 + var6 * this.imageWidth;
                    imageData[n] &= 0xFFFFFF;
                }
            }
        }
    }
    
    private void setAreaOpaque(final int par1, final int par2, final int par3, final int par4) {
        for (int var5 = par1; var5 < par3; ++var5) {
            for (int var6 = par2; var6 < par4; ++var6) {
                final int[] imageData = this.imageData;
                final int n = var5 + var6 * this.imageWidth;
                imageData[n] |= 0xFF000000;
            }
        }
    }
    
    private boolean hasTransparency(final int par1, final int par2, final int par3, final int par4) {
        for (int var5 = par1; var5 < par3; ++var5) {
            for (int var6 = par2; var6 < par4; ++var6) {
                final int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 0xFF) < 128) {
                    return true;
                }
            }
        }
        return false;
    }
}
