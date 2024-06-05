package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;

abstract static class Stronghold extends StructureComponent
{
    protected Door field_143013_d;
    
    public Stronghold() {
        this.field_143013_d = Door.OPENING;
    }
    
    protected Stronghold(final int p_i2087_1_) {
        super(p_i2087_1_);
        this.field_143013_d = Door.OPENING;
    }
    
    protected static boolean canStrongholdGoDeeper(final StructureBoundingBox p_74991_0_) {
        return p_74991_0_ != null && p_74991_0_.minY > 10;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        p_143012_1_.setString("EntryDoor", this.field_143013_d.name());
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        this.field_143013_d = Door.valueOf(p_143011_1_.getString("EntryDoor"));
    }
    
    protected void placeDoor(final World worldIn, final Random p_74990_2_, final StructureBoundingBox p_74990_3_, final Door p_74990_4_, final int p_74990_5_, final int p_74990_6_, final int p_74990_7_) {
        switch (StructureStrongholdPieces.SwitchEnumFacing.doorEnum[p_74990_4_.ordinal()]) {
            default: {
                this.func_175804_a(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                break;
            }
            case 2: {
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.oak_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.oak_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                break;
            }
            case 3: {
                this.func_175811_a(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                break;
            }
            case 4: {
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 4)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
                this.func_175811_a(worldIn, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 3)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
                break;
            }
        }
    }
    
    protected Door getRandomDoor(final Random p_74988_1_) {
        final int var2 = p_74988_1_.nextInt(5);
        switch (var2) {
            default: {
                return Door.OPENING;
            }
            case 2: {
                return Door.WOOD_DOOR;
            }
            case 3: {
                return Door.GRATES;
            }
            case 4: {
                return Door.IRON_DOOR;
            }
        }
    }
    
    protected StructureComponent getNextComponentNormal(final Stairs2 p_74986_1_, final List p_74986_2_, final Random p_74986_3_, final int p_74986_4_, final int p_74986_5_) {
        if (this.coordBaseMode != null) {
            switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return StructureStrongholdPieces.access$200(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
                }
                case 2: {
                    return StructureStrongholdPieces.access$200(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
                }
                case 3: {
                    return StructureStrongholdPieces.access$200(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, this.getComponentType());
                }
                case 4: {
                    return StructureStrongholdPieces.access$200(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, this.getComponentType());
                }
            }
        }
        return null;
    }
    
    protected StructureComponent getNextComponentX(final Stairs2 p_74989_1_, final List p_74989_2_, final Random p_74989_3_, final int p_74989_4_, final int p_74989_5_) {
        if (this.coordBaseMode != null) {
            switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return StructureStrongholdPieces.access$200(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, this.getComponentType());
                }
                case 2: {
                    return StructureStrongholdPieces.access$200(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, this.getComponentType());
                }
                case 3: {
                    return StructureStrongholdPieces.access$200(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
                case 4: {
                    return StructureStrongholdPieces.access$200(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
            }
        }
        return null;
    }
    
    protected StructureComponent getNextComponentZ(final Stairs2 p_74987_1_, final List p_74987_2_, final Random p_74987_3_, final int p_74987_4_, final int p_74987_5_) {
        if (this.coordBaseMode != null) {
            switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return StructureStrongholdPieces.access$200(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, this.getComponentType());
                }
                case 2: {
                    return StructureStrongholdPieces.access$200(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, this.getComponentType());
                }
                case 3: {
                    return StructureStrongholdPieces.access$200(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
                case 4: {
                    return StructureStrongholdPieces.access$200(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }
        }
        return null;
    }
    
    public enum Door
    {
        OPENING("OPENING", 0), 
        WOOD_DOOR("WOOD_DOOR", 1), 
        GRATES("GRATES", 2), 
        IRON_DOOR("IRON_DOOR", 3);
        
        private static final Door[] $VALUES;
        
        private Door(final String p_i2086_1_, final int p_i2086_2_) {
        }
        
        static {
            $VALUES = new Door[] { Door.OPENING, Door.WOOD_DOOR, Door.GRATES, Door.IRON_DOOR };
        }
    }
}
