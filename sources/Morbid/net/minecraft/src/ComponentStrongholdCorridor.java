package net.minecraft.src;

import java.util.*;

public class ComponentStrongholdCorridor extends ComponentStronghold
{
    private final int field_74993_a;
    
    public ComponentStrongholdCorridor(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.boundingBox = par3StructureBoundingBox;
        this.field_74993_a = ((par4 != 2 && par4 != 0) ? par3StructureBoundingBox.getXSize() : par3StructureBoundingBox.getZSize());
    }
    
    public static StructureBoundingBox func_74992_a(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5) {
        StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
        final StructureComponent var8 = StructureComponent.findIntersecting(par0List, var7);
        if (var8 == null) {
            return null;
        }
        if (var8.getBoundingBox().minY == var7.minY) {
            for (int var9 = 3; var9 >= 1; --var9) {
                var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9 - 1, par5);
                if (!var8.getBoundingBox().intersectsWith(var7)) {
                    return StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9, par5);
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        }
        for (int var4 = 0; var4 < this.field_74993_a; ++var4) {
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, 0, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 0, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 0, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 0, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, 0, var4, par3StructureBoundingBox);
            for (int var5 = 1; var5 <= 3; ++var5) {
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, var5, var4, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, var5, var4, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, var5, var4, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, 0, 0, 3, var5, var4, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, var5, var4, par3StructureBoundingBox);
            }
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, 4, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 4, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 4, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 4, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, 4, var4, par3StructureBoundingBox);
        }
        return true;
    }
}
