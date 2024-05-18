package net.minecraft.src;

import java.util.*;

public class StitchSlot
{
    private final int originX;
    private final int originY;
    private final int width;
    private final int height;
    private List subSlots;
    private StitchHolder holder;
    
    public StitchSlot(final int par1, final int par2, final int par3, final int par4) {
        this.originX = par1;
        this.originY = par2;
        this.width = par3;
        this.height = par4;
    }
    
    public StitchHolder getStitchHolder() {
        return this.holder;
    }
    
    public int getOriginX() {
        return this.originX;
    }
    
    public int getOriginY() {
        return this.originY;
    }
    
    public boolean func_94182_a(final StitchHolder par1StitchHolder) {
        if (this.holder != null) {
            return false;
        }
        final int var2 = par1StitchHolder.getWidth();
        final int var3 = par1StitchHolder.getHeight();
        if (var2 > this.width || var3 > this.height) {
            return false;
        }
        if (var2 == this.width && var3 == this.height) {
            this.holder = par1StitchHolder;
            return true;
        }
        if (this.subSlots == null) {
            (this.subSlots = new ArrayList(1)).add(new StitchSlot(this.originX, this.originY, var2, var3));
            final int var4 = this.width - var2;
            final int var5 = this.height - var3;
            if (var5 > 0 && var4 > 0) {
                final int var6 = Math.max(this.height, var4);
                final int var7 = Math.max(this.width, var5);
                if (var6 >= var7) {
                    this.subSlots.add(new StitchSlot(this.originX, this.originY + var3, var2, var5));
                    this.subSlots.add(new StitchSlot(this.originX + var2, this.originY, var4, this.height));
                }
                else {
                    this.subSlots.add(new StitchSlot(this.originX + var2, this.originY, var4, var3));
                    this.subSlots.add(new StitchSlot(this.originX, this.originY + var3, this.width, var5));
                }
            }
            else if (var4 == 0) {
                this.subSlots.add(new StitchSlot(this.originX, this.originY + var3, var2, var5));
            }
            else if (var5 == 0) {
                this.subSlots.add(new StitchSlot(this.originX + var2, this.originY, var4, var3));
            }
        }
        for (final StitchSlot var9 : this.subSlots) {
            if (var9.func_94182_a(par1StitchHolder)) {
                return true;
            }
        }
        return false;
    }
    
    public void getAllStitchSlots(final List par1List) {
        if (this.holder != null) {
            par1List.add(this);
        }
        else if (this.subSlots != null) {
            for (final StitchSlot var3 : this.subSlots) {
                var3.getAllStitchSlots(par1List);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
    }
}
