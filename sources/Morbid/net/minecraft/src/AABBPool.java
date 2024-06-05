package net.minecraft.src;

import java.util.*;

public class AABBPool
{
    private final int maxNumCleans;
    private final int numEntriesToRemove;
    private final List listAABB;
    private int nextPoolIndex;
    private int maxPoolIndex;
    private int numCleans;
    
    public AABBPool(final int par1, final int par2) {
        this.listAABB = new ArrayList();
        this.nextPoolIndex = 0;
        this.maxPoolIndex = 0;
        this.numCleans = 0;
        this.maxNumCleans = par1;
        this.numEntriesToRemove = par2;
    }
    
    public AxisAlignedBB getAABB(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        AxisAlignedBB var13;
        if (this.nextPoolIndex >= this.listAABB.size()) {
            var13 = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
            this.listAABB.add(var13);
        }
        else {
            var13 = this.listAABB.get(this.nextPoolIndex);
            var13.setBounds(par1, par3, par5, par7, par9, par11);
        }
        ++this.nextPoolIndex;
        return var13;
    }
    
    public void cleanPool() {
        if (this.nextPoolIndex > this.maxPoolIndex) {
            this.maxPoolIndex = this.nextPoolIndex;
        }
        if (this.numCleans++ == this.maxNumCleans) {
            final int var1 = Math.max(this.maxPoolIndex, this.listAABB.size() - this.numEntriesToRemove);
            while (this.listAABB.size() > var1) {
                this.listAABB.remove(var1);
            }
            this.maxPoolIndex = 0;
            this.numCleans = 0;
        }
        this.nextPoolIndex = 0;
    }
    
    public void clearPool() {
        this.nextPoolIndex = 0;
        this.listAABB.clear();
    }
    
    public int getlistAABBsize() {
        return this.listAABB.size();
    }
    
    public int getnextPoolIndex() {
        return this.nextPoolIndex;
    }
}
