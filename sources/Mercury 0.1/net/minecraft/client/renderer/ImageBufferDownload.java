/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import net.minecraft.client.renderer.IImageBuffer;

public class ImageBufferDownload
implements IImageBuffer {
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;

    @Override
    public BufferedImage parseUserSkin(BufferedImage p_78432_1_) {
        if (p_78432_1_ == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 64;
        int srcWidth = p_78432_1_.getWidth();
        int srcHeight = p_78432_1_.getHeight();
        int k2 = 1;
        while (this.imageWidth < srcWidth || this.imageHeight < srcHeight) {
            this.imageWidth *= 2;
            this.imageHeight *= 2;
            k2 *= 2;
        }
        BufferedImage var2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics var3 = var2.getGraphics();
        var3.drawImage(p_78432_1_, 0, 0, null);
        if (p_78432_1_.getHeight() == 32 * k2) {
            var3.drawImage(var2, 24 * k2, 48 * k2, 20 * k2, 52 * k2, 4 * k2, 16 * k2, 8 * k2, 20 * k2, null);
            var3.drawImage(var2, 28 * k2, 48 * k2, 24 * k2, 52 * k2, 8 * k2, 16 * k2, 12 * k2, 20 * k2, null);
            var3.drawImage(var2, 20 * k2, 52 * k2, 16 * k2, 64 * k2, 8 * k2, 20 * k2, 12 * k2, 32 * k2, null);
            var3.drawImage(var2, 24 * k2, 52 * k2, 20 * k2, 64 * k2, 4 * k2, 20 * k2, 8 * k2, 32 * k2, null);
            var3.drawImage(var2, 28 * k2, 52 * k2, 24 * k2, 64 * k2, 0 * k2, 20 * k2, 4 * k2, 32 * k2, null);
            var3.drawImage(var2, 32 * k2, 52 * k2, 28 * k2, 64 * k2, 12 * k2, 20 * k2, 16 * k2, 32 * k2, null);
            var3.drawImage(var2, 40 * k2, 48 * k2, 36 * k2, 52 * k2, 44 * k2, 16 * k2, 48 * k2, 20 * k2, null);
            var3.drawImage(var2, 44 * k2, 48 * k2, 40 * k2, 52 * k2, 48 * k2, 16 * k2, 52 * k2, 20 * k2, null);
            var3.drawImage(var2, 36 * k2, 52 * k2, 32 * k2, 64 * k2, 48 * k2, 20 * k2, 52 * k2, 32 * k2, null);
            var3.drawImage(var2, 40 * k2, 52 * k2, 36 * k2, 64 * k2, 44 * k2, 20 * k2, 48 * k2, 32 * k2, null);
            var3.drawImage(var2, 44 * k2, 52 * k2, 40 * k2, 64 * k2, 40 * k2, 20 * k2, 44 * k2, 32 * k2, null);
            var3.drawImage(var2, 48 * k2, 52 * k2, 44 * k2, 64 * k2, 52 * k2, 20 * k2, 56 * k2, 32 * k2, null);
        }
        var3.dispose();
        this.imageData = ((DataBufferInt)var2.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque(0, 0, 32 * k2, 16 * k2);
        this.setAreaTransparent(32 * k2, 0, 64 * k2, 32 * k2);
        this.setAreaOpaque(0, 16 * k2, 64 * k2, 32 * k2);
        this.setAreaTransparent(0, 32 * k2, 16 * k2, 48 * k2);
        this.setAreaTransparent(16 * k2, 32 * k2, 40 * k2, 48 * k2);
        this.setAreaTransparent(40 * k2, 32 * k2, 56 * k2, 48 * k2);
        this.setAreaTransparent(0, 48 * k2, 16 * k2, 64 * k2);
        this.setAreaOpaque(16 * k2, 48 * k2, 48 * k2, 64 * k2);
        this.setAreaTransparent(48 * k2, 48 * k2, 64 * k2, 64 * k2);
        return var2;
    }

    @Override
    public void func_152634_a() {
    }

    private void setAreaTransparent(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_) {
        if (!this.hasTransparency(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_)) {
            for (int var5 = p_78434_1_; var5 < p_78434_3_; ++var5) {
                for (int var6 = p_78434_2_; var6 < p_78434_4_; ++var6) {
                    int[] arrn = this.imageData;
                    int n2 = var5 + var6 * this.imageWidth;
                    arrn[n2] = arrn[n2] & 16777215;
                }
            }
        }
    }

    private void setAreaOpaque(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
        for (int var5 = p_78433_1_; var5 < p_78433_3_; ++var5) {
            for (int var6 = p_78433_2_; var6 < p_78433_4_; ++var6) {
                int[] arrn = this.imageData;
                int n2 = var5 + var6 * this.imageWidth;
                arrn[n2] = arrn[n2] | -16777216;
            }
        }
    }

    private boolean hasTransparency(int p_78435_1_, int p_78435_2_, int p_78435_3_, int p_78435_4_) {
        for (int var5 = p_78435_1_; var5 < p_78435_3_; ++var5) {
            for (int var6 = p_78435_2_; var6 < p_78435_4_; ++var6) {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 255) >= 128) continue;
                return true;
            }
        }
        return false;
    }
}

