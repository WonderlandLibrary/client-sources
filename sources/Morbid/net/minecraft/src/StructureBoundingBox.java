package net.minecraft.src;

public class StructureBoundingBox
{
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;
    
    public StructureBoundingBox() {
    }
    
    public static StructureBoundingBox getNewBoundingBox() {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
    
    public static StructureBoundingBox getComponentToAddBoundingBox(final int par0, final int par1, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final int par9) {
        switch (par9) {
            case 0: {
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
            }
            case 1: {
                return new StructureBoundingBox(par0 - par8 + 1 + par5, par1 + par4, par2 + par3, par0 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);
            }
            case 2: {
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 - par8 + 1 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par5);
            }
            case 3: {
                return new StructureBoundingBox(par0 + par5, par1 + par4, par2 + par3, par0 + par8 - 1 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);
            }
            default: {
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
            }
        }
    }
    
    public StructureBoundingBox(final StructureBoundingBox par1StructureBoundingBox) {
        this.minX = par1StructureBoundingBox.minX;
        this.minY = par1StructureBoundingBox.minY;
        this.minZ = par1StructureBoundingBox.minZ;
        this.maxX = par1StructureBoundingBox.maxX;
        this.maxY = par1StructureBoundingBox.maxY;
        this.maxZ = par1StructureBoundingBox.maxZ;
    }
    
    public StructureBoundingBox(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.minX = par1;
        this.minY = par2;
        this.minZ = par3;
        this.maxX = par4;
        this.maxY = par5;
        this.maxZ = par6;
    }
    
    public StructureBoundingBox(final int par1, final int par2, final int par3, final int par4) {
        this.minX = par1;
        this.minZ = par2;
        this.maxX = par3;
        this.maxZ = par4;
        this.minY = 1;
        this.maxY = 512;
    }
    
    public boolean intersectsWith(final StructureBoundingBox par1StructureBoundingBox) {
        return this.maxX >= par1StructureBoundingBox.minX && this.minX <= par1StructureBoundingBox.maxX && this.maxZ >= par1StructureBoundingBox.minZ && this.minZ <= par1StructureBoundingBox.maxZ && this.maxY >= par1StructureBoundingBox.minY && this.minY <= par1StructureBoundingBox.maxY;
    }
    
    public boolean intersectsWith(final int par1, final int par2, final int par3, final int par4) {
        return this.maxX >= par1 && this.minX <= par3 && this.maxZ >= par2 && this.minZ <= par4;
    }
    
    public void expandTo(final StructureBoundingBox par1StructureBoundingBox) {
        this.minX = Math.min(this.minX, par1StructureBoundingBox.minX);
        this.minY = Math.min(this.minY, par1StructureBoundingBox.minY);
        this.minZ = Math.min(this.minZ, par1StructureBoundingBox.minZ);
        this.maxX = Math.max(this.maxX, par1StructureBoundingBox.maxX);
        this.maxY = Math.max(this.maxY, par1StructureBoundingBox.maxY);
        this.maxZ = Math.max(this.maxZ, par1StructureBoundingBox.maxZ);
    }
    
    public void offset(final int par1, final int par2, final int par3) {
        this.minX += par1;
        this.minY += par2;
        this.minZ += par3;
        this.maxX += par1;
        this.maxY += par2;
        this.maxZ += par3;
    }
    
    public boolean isVecInside(final int par1, final int par2, final int par3) {
        return par1 >= this.minX && par1 <= this.maxX && par3 >= this.minZ && par3 <= this.maxZ && par2 >= this.minY && par2 <= this.maxY;
    }
    
    public int getXSize() {
        return this.maxX - this.minX + 1;
    }
    
    public int getYSize() {
        return this.maxY - this.minY + 1;
    }
    
    public int getZSize() {
        return this.maxZ - this.minZ + 1;
    }
    
    public int getCenterX() {
        return this.minX + (this.maxX - this.minX + 1) / 2;
    }
    
    public int getCenterY() {
        return this.minY + (this.maxY - this.minY + 1) / 2;
    }
    
    public int getCenterZ() {
        return this.minZ + (this.maxZ - this.minZ + 1) / 2;
    }
    
    @Override
    public String toString() {
        return "(" + this.minX + ", " + this.minY + ", " + this.minZ + "; " + this.maxX + ", " + this.maxY + ", " + this.maxZ + ")";
    }
}
