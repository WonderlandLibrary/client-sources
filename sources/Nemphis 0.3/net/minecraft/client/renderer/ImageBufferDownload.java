/*
 * Decompiled with CFR 0_118.
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
        int k = 1;
        while (this.imageWidth < srcWidth || this.imageHeight < srcHeight) {
            this.imageWidth *= 2;
            this.imageHeight *= 2;
            k *= 2;
        }
        BufferedImage var2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics var3 = var2.getGraphics();
        var3.drawImage(p_78432_1_, 0, 0, null);
        if (p_78432_1_.getHeight() == 32 * k) {
            var3.drawImage(var2, 24 * k, 48 * k, 20 * k, 52 * k, 4 * k, 16 * k, 8 * k, 20 * k, null);
            var3.drawImage(var2, 28 * k, 48 * k, 24 * k, 52 * k, 8 * k, 16 * k, 12 * k, 20 * k, null);
            var3.drawImage(var2, 20 * k, 52 * k, 16 * k, 64 * k, 8 * k, 20 * k, 12 * k, 32 * k, null);
            var3.drawImage(var2, 24 * k, 52 * k, 20 * k, 64 * k, 4 * k, 20 * k, 8 * k, 32 * k, null);
            var3.drawImage(var2, 28 * k, 52 * k, 24 * k, 64 * k, 0 * k, 20 * k, 4 * k, 32 * k, null);
            var3.drawImage(var2, 32 * k, 52 * k, 28 * k, 64 * k, 12 * k, 20 * k, 16 * k, 32 * k, null);
            var3.drawImage(var2, 40 * k, 48 * k, 36 * k, 52 * k, 44 * k, 16 * k, 48 * k, 20 * k, null);
            var3.drawImage(var2, 44 * k, 48 * k, 40 * k, 52 * k, 48 * k, 16 * k, 52 * k, 20 * k, null);
            var3.drawImage(var2, 36 * k, 52 * k, 32 * k, 64 * k, 48 * k, 20 * k, 52 * k, 32 * k, null);
            var3.drawImage(var2, 40 * k, 52 * k, 36 * k, 64 * k, 44 * k, 20 * k, 48 * k, 32 * k, null);
            var3.drawImage(var2, 44 * k, 52 * k, 40 * k, 64 * k, 40 * k, 20 * k, 44 * k, 32 * k, null);
            var3.drawImage(var2, 48 * k, 52 * k, 44 * k, 64 * k, 52 * k, 20 * k, 56 * k, 32 * k, null);
        }
        var3.dispose();
        this.imageData = ((DataBufferInt)var2.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque(0, 0, 32 * k, 16 * k);
        this.setAreaTransparent(32 * k, 0, 64 * k, 32 * k);
        this.setAreaOpaque(0, 16 * k, 64 * k, 32 * k);
        this.setAreaTransparent(0, 32 * k, 16 * k, 48 * k);
        this.setAreaTransparent(16 * k, 32 * k, 40 * k, 48 * k);
        this.setAreaTransparent(40 * k, 32 * k, 56 * k, 48 * k);
        this.setAreaTransparent(0, 48 * k, 16 * k, 64 * k);
        this.setAreaOpaque(16 * k, 48 * k, 48 * k, 64 * k);
        this.setAreaTransparent(48 * k, 48 * k, 64 * k, 64 * k);
        return var2;
    }

    @Override
    public void func_152634_a() {
    }

    private void setAreaTransparent(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_) {
        if (!this.hasTransparency(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_)) {
            int var5 = p_78434_1_;
            while (var5 < p_78434_3_) {
                int var6 = p_78434_2_;
                while (var6 < p_78434_4_) {
                    int[] arrn = this.imageData;
                    int n = var5 + var6 * this.imageWidth;
                    arrn[n] = arrn[n] & 16777215;
                    ++var6;
                }
                ++var5;
            }
        }
    }

    private void setAreaOpaque(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
        int var5 = p_78433_1_;
        while (var5 < p_78433_3_) {
            int var6 = p_78433_2_;
            while (var6 < p_78433_4_) {
                int[] arrn = this.imageData;
                int n = var5 + var6 * this.imageWidth;
                arrn[n] = arrn[n] | -16777216;
                ++var6;
            }
            ++var5;
        }
    }

    private boolean hasTransparency(int p_78435_1_, int p_78435_2_, int p_78435_3_, int p_78435_4_) {
        int var5 = p_78435_1_;
        while (var5 < p_78435_3_) {
            int var6 = p_78435_2_;
            while (var6 < p_78435_4_) {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 255) < 128) {
                    return true;
                }
                ++var6;
            }
            ++var5;
        }
        return false;
    }
}

