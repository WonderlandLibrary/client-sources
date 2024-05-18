package net.minecraft.src;

import java.util.*;

public class ComponentMineshaftRoom extends StructureComponent
{
    private List roomsLinkedToTheRoom;
    
    public ComponentMineshaftRoom(final int par1, final Random par2Random, final int par3, final int par4) {
        super(par1);
        this.roomsLinkedToTheRoom = new LinkedList();
        this.boundingBox = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        final int var4 = this.getComponentType();
        int var5 = this.boundingBox.getYSize() - 3 - 1;
        if (var5 <= 0) {
            var5 = 1;
        }
        for (int var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
            var6 += par3Random.nextInt(this.boundingBox.getXSize());
            if (var6 + 3 > this.boundingBox.getXSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + var6, this.boundingBox.minY + par3Random.nextInt(var5) + 1, this.boundingBox.minZ - 1, 2, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.minZ, var8.maxX, var8.maxY, this.boundingBox.minZ + 1));
            }
        }
        for (int var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
            var6 += par3Random.nextInt(this.boundingBox.getXSize());
            if (var6 + 3 > this.boundingBox.getXSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + var6, this.boundingBox.minY + par3Random.nextInt(var5) + 1, this.boundingBox.maxZ + 1, 0, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.maxZ - 1, var8.maxX, var8.maxY, this.boundingBox.maxZ));
            }
        }
        for (int var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
            var6 += par3Random.nextInt(this.boundingBox.getZSize());
            if (var6 + 3 > this.boundingBox.getZSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par3Random.nextInt(var5) + 1, this.boundingBox.minZ + var6, 1, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, var8.minY, var8.minZ, this.boundingBox.minX + 1, var8.maxY, var8.maxZ));
            }
        }
        for (int var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
            var6 += par3Random.nextInt(this.boundingBox.getZSize());
            if (var6 + 3 > this.boundingBox.getZSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par3Random.nextInt(var5) + 1, this.boundingBox.minZ + var6, 3, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, var8.minY, var8.minZ, this.boundingBox.maxX, var8.maxY, var8.maxZ));
            }
        }
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Block.dirt.blockID, 0, true);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, 0, 0, false);
        for (final StructureBoundingBox var5 : this.roomsLinkedToTheRoom) {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, 0, 0, false);
        }
        this.randomlyRareFillWithBlocks(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, 0, false);
        return true;
    }
}
