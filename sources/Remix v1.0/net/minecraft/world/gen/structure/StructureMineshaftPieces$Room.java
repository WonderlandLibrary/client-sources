package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.nbt.*;

public static class Room extends StructureComponent
{
    private List roomsLinkedToTheRoom;
    
    public Room() {
        this.roomsLinkedToTheRoom = Lists.newLinkedList();
    }
    
    public Room(final int p_i2037_1_, final Random p_i2037_2_, final int p_i2037_3_, final int p_i2037_4_) {
        super(p_i2037_1_);
        this.roomsLinkedToTheRoom = Lists.newLinkedList();
        this.boundingBox = new StructureBoundingBox(p_i2037_3_, 50, p_i2037_4_, p_i2037_3_ + 7 + p_i2037_2_.nextInt(6), 54 + p_i2037_2_.nextInt(6), p_i2037_4_ + 7 + p_i2037_2_.nextInt(6));
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        final int var4 = this.getComponentType();
        int var5 = this.boundingBox.getYSize() - 3 - 1;
        if (var5 <= 0) {
            var5 = 1;
        }
        for (int var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
            var6 += p_74861_3_.nextInt(this.boundingBox.getXSize());
            if (var6 + 3 > this.boundingBox.getXSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + var6, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.minZ, var8.maxX, var8.maxY, this.boundingBox.minZ + 1));
            }
        }
        for (int var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
            var6 += p_74861_3_.nextInt(this.boundingBox.getXSize());
            if (var6 + 3 > this.boundingBox.getXSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + var6, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.maxZ - 1, var8.maxX, var8.maxY, this.boundingBox.maxZ));
            }
        }
        for (int var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
            var6 += p_74861_3_.nextInt(this.boundingBox.getZSize());
            if (var6 + 3 > this.boundingBox.getZSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.minZ + var6, EnumFacing.WEST, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, var8.minY, var8.minZ, this.boundingBox.minX + 1, var8.maxY, var8.maxZ));
            }
        }
        for (int var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
            var6 += p_74861_3_.nextInt(this.boundingBox.getZSize());
            if (var6 + 3 > this.boundingBox.getZSize()) {
                break;
            }
            final StructureComponent var7 = StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.minZ + var6, EnumFacing.EAST, var4);
            if (var7 != null) {
                final StructureBoundingBox var8 = var7.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, var8.minY, var8.minZ, this.boundingBox.maxX, var8.maxY, var8.maxZ));
            }
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt.getDefaultState(), Blocks.air.getDefaultState(), true);
        this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        for (final StructureBoundingBox var5 : this.roomsLinkedToTheRoom) {
            this.func_175804_a(worldIn, p_74875_3_, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        this.func_180777_a(worldIn, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), false);
        return true;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        final NBTTagList var2 = new NBTTagList();
        for (final StructureBoundingBox var4 : this.roomsLinkedToTheRoom) {
            var2.appendTag(var4.func_151535_h());
        }
        p_143012_1_.setTag("Entrances", var2);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        final NBTTagList var2 = p_143011_1_.getTagList("Entrances", 11);
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var2.getIntArray(var3)));
        }
    }
}
