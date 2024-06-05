package net.minecraft.src;

import java.util.*;

public abstract class StructureStart
{
    protected LinkedList components;
    protected StructureBoundingBox boundingBox;
    
    public StructureStart() {
        this.components = new LinkedList();
    }
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    public LinkedList getComponents() {
        return this.components;
    }
    
    public void generateStructure(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        final Iterator var4 = this.components.iterator();
        while (var4.hasNext()) {
            final StructureComponent var5 = var4.next();
            if (var5.getBoundingBox().intersectsWith(par3StructureBoundingBox) && !var5.addComponentParts(par1World, par2Random, par3StructureBoundingBox)) {
                var4.remove();
            }
        }
    }
    
    protected void updateBoundingBox() {
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();
        for (final StructureComponent var2 : this.components) {
            this.boundingBox.expandTo(var2.getBoundingBox());
        }
    }
    
    protected void markAvailableHeight(final World par1World, final Random par2Random, final int par3) {
        final int var4 = 63 - par3;
        int var5 = this.boundingBox.getYSize() + 1;
        if (var5 < var4) {
            var5 += par2Random.nextInt(var4 - var5);
        }
        final int var6 = var5 - this.boundingBox.maxY;
        this.boundingBox.offset(0, var6, 0);
        for (final StructureComponent var8 : this.components) {
            var8.getBoundingBox().offset(0, var6, 0);
        }
    }
    
    protected void setRandomHeight(final World par1World, final Random par2Random, final int par3, final int par4) {
        final int var5 = par4 - par3 + 1 - this.boundingBox.getYSize();
        final boolean var6 = true;
        int var7;
        if (var5 > 1) {
            var7 = par3 + par2Random.nextInt(var5);
        }
        else {
            var7 = par3;
        }
        final int var8 = var7 - this.boundingBox.minY;
        this.boundingBox.offset(0, var8, 0);
        for (final StructureComponent var10 : this.components) {
            var10.getBoundingBox().offset(0, var8, 0);
        }
    }
    
    public boolean isSizeableStructure() {
        return true;
    }
}
