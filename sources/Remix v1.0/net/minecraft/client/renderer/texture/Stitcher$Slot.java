package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import java.util.*;

public static class Slot
{
    private final int originX;
    private final int originY;
    private final int width;
    private final int height;
    private List subSlots;
    private Holder holder;
    
    public Slot(final int p_i1277_1_, final int p_i1277_2_, final int p_i1277_3_, final int p_i1277_4_) {
        this.originX = p_i1277_1_;
        this.originY = p_i1277_2_;
        this.width = p_i1277_3_;
        this.height = p_i1277_4_;
    }
    
    public Holder getStitchHolder() {
        return this.holder;
    }
    
    public int getOriginX() {
        return this.originX;
    }
    
    public int getOriginY() {
        return this.originY;
    }
    
    public boolean addSlot(final Holder p_94182_1_) {
        if (this.holder != null) {
            return false;
        }
        final int var2 = p_94182_1_.getWidth();
        final int var3 = p_94182_1_.getHeight();
        if (var2 > this.width || var3 > this.height) {
            return false;
        }
        if (var2 == this.width && var3 == this.height) {
            this.holder = p_94182_1_;
            return true;
        }
        if (this.subSlots == null) {
            (this.subSlots = Lists.newArrayListWithCapacity(1)).add(new Slot(this.originX, this.originY, var2, var3));
            final int var4 = this.width - var2;
            final int var5 = this.height - var3;
            if (var5 > 0 && var4 > 0) {
                final int var6 = Math.max(this.height, var4);
                final int var7 = Math.max(this.width, var5);
                if (var6 >= var7) {
                    this.subSlots.add(new Slot(this.originX, this.originY + var3, var2, var5));
                    this.subSlots.add(new Slot(this.originX + var2, this.originY, var4, this.height));
                }
                else {
                    this.subSlots.add(new Slot(this.originX + var2, this.originY, var4, var3));
                    this.subSlots.add(new Slot(this.originX, this.originY + var3, this.width, var5));
                }
            }
            else if (var4 == 0) {
                this.subSlots.add(new Slot(this.originX, this.originY + var3, var2, var5));
            }
            else if (var5 == 0) {
                this.subSlots.add(new Slot(this.originX + var2, this.originY, var4, var3));
            }
        }
        for (final Slot var9 : this.subSlots) {
            if (var9.addSlot(p_94182_1_)) {
                return true;
            }
        }
        return false;
    }
    
    public void getAllStitchSlots(final List p_94184_1_) {
        if (this.holder != null) {
            p_94184_1_.add(this);
        }
        else if (this.subSlots != null) {
            for (final Slot var3 : this.subSlots) {
                var3.getAllStitchSlots(p_94184_1_);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
    }
}
