package net.minecraft.src;

import java.util.*;

public class Vec3Pool
{
    private final int truncateArrayResetThreshold;
    private final int minimumSize;
    private final List vec3Cache;
    private int nextFreeSpace;
    private int maximumSizeSinceLastTruncation;
    private int resetCount;
    
    public Vec3Pool(final int par1, final int par2) {
        this.vec3Cache = new ArrayList();
        this.nextFreeSpace = 0;
        this.maximumSizeSinceLastTruncation = 0;
        this.resetCount = 0;
        this.truncateArrayResetThreshold = par1;
        this.minimumSize = par2;
    }
    
    public Vec3 getVecFromPool(final double par1, final double par3, final double par5) {
        if (this.func_82589_e()) {
            return new Vec3(this, par1, par3, par5);
        }
        Vec3 var7;
        if (this.nextFreeSpace >= this.vec3Cache.size()) {
            var7 = new Vec3(this, par1, par3, par5);
            this.vec3Cache.add(var7);
        }
        else {
            var7 = this.vec3Cache.get(this.nextFreeSpace);
            var7.setComponents(par1, par3, par5);
        }
        ++this.nextFreeSpace;
        return var7;
    }
    
    public void clear() {
        if (!this.func_82589_e()) {
            if (this.nextFreeSpace > this.maximumSizeSinceLastTruncation) {
                this.maximumSizeSinceLastTruncation = this.nextFreeSpace;
            }
            if (this.resetCount++ == this.truncateArrayResetThreshold) {
                final int var1 = Math.max(this.maximumSizeSinceLastTruncation, this.vec3Cache.size() - this.minimumSize);
                while (this.vec3Cache.size() > var1) {
                    this.vec3Cache.remove(var1);
                }
                this.maximumSizeSinceLastTruncation = 0;
                this.resetCount = 0;
            }
            this.nextFreeSpace = 0;
        }
    }
    
    public void clearAndFreeCache() {
        if (!this.func_82589_e()) {
            this.nextFreeSpace = 0;
            this.vec3Cache.clear();
        }
    }
    
    public int getPoolSize() {
        return this.vec3Cache.size();
    }
    
    public int func_82590_d() {
        return this.nextFreeSpace;
    }
    
    private boolean func_82589_e() {
        return this.minimumSize < 0 || this.truncateArrayResetThreshold < 0;
    }
}
