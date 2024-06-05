package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

abstract static class Piece extends StructureComponent
{
    protected static final List field_111019_a;
    
    public Piece() {
    }
    
    protected Piece(final int p_i2054_1_) {
        super(p_i2054_1_);
    }
    
    protected static boolean isAboveGround(final StructureBoundingBox p_74964_0_) {
        return p_74964_0_ != null && p_74964_0_.minY > 10;
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
    }
    
    private int getTotalWeight(final List p_74960_1_) {
        boolean var2 = false;
        int var3 = 0;
        for (final PieceWeight var5 : p_74960_1_) {
            if (var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d) {
                var2 = true;
            }
            var3 += var5.field_78826_b;
        }
        return var2 ? var3 : -1;
    }
    
    private Piece func_175871_a(final Start p_175871_1_, final List p_175871_2_, final List p_175871_3_, final Random p_175871_4_, final int p_175871_5_, final int p_175871_6_, final int p_175871_7_, final EnumFacing p_175871_8_, final int p_175871_9_) {
        final int var10 = this.getTotalWeight(p_175871_2_);
        final boolean var11 = var10 > 0 && p_175871_9_ <= 30;
        int var12 = 0;
        while (var12 < 5 && var11) {
            ++var12;
            int var13 = p_175871_4_.nextInt(var10);
            for (final PieceWeight var15 : p_175871_2_) {
                var13 -= var15.field_78826_b;
                if (var13 < 0) {
                    if (!var15.func_78822_a(p_175871_9_)) {
                        break;
                    }
                    if (var15 == p_175871_1_.theNetherBridgePieceWeight && !var15.field_78825_e) {
                        break;
                    }
                    final Piece var16 = StructureNetherBridgePieces.access$000(var15, p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
                    if (var16 != null) {
                        final PieceWeight pieceWeight = var15;
                        ++pieceWeight.field_78827_c;
                        p_175871_1_.theNetherBridgePieceWeight = var15;
                        if (!var15.func_78823_a()) {
                            p_175871_2_.remove(var15);
                        }
                        return var16;
                    }
                    continue;
                }
            }
        }
        return End.func_175884_a(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
    }
    
    private StructureComponent func_175870_a(final Start p_175870_1_, final List p_175870_2_, final Random p_175870_3_, final int p_175870_4_, final int p_175870_5_, final int p_175870_6_, final EnumFacing p_175870_7_, final int p_175870_8_, final boolean p_175870_9_) {
        if (Math.abs(p_175870_4_ - p_175870_1_.getBoundingBox().minX) <= 112 && Math.abs(p_175870_6_ - p_175870_1_.getBoundingBox().minZ) <= 112) {
            List var10 = p_175870_1_.primaryWeights;
            if (p_175870_9_) {
                var10 = p_175870_1_.secondaryWeights;
            }
            final Piece var11 = this.func_175871_a(p_175870_1_, var10, p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1);
            if (var11 != null) {
                p_175870_2_.add(var11);
                p_175870_1_.field_74967_d.add(var11);
            }
            return var11;
        }
        return End.func_175884_a(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_);
    }
    
    protected StructureComponent getNextComponentNormal(final Start p_74963_1_, final List p_74963_2_, final Random p_74963_3_, final int p_74963_4_, final int p_74963_5_, final boolean p_74963_6_) {
        if (this.coordBaseMode != null) {
            switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                }
                case 2: {
                    return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                }
                case 3: {
                    return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                }
                case 4: {
                    return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                }
            }
        }
        return null;
    }
    
    protected StructureComponent getNextComponentX(final Start p_74961_1_, final List p_74961_2_, final Random p_74961_3_, final int p_74961_4_, final int p_74961_5_, final boolean p_74961_6_) {
        if (this.coordBaseMode != null) {
            switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, this.getComponentType(), p_74961_6_);
                }
                case 2: {
                    return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, this.getComponentType(), p_74961_6_);
                }
                case 3: {
                    return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), p_74961_6_);
                }
                case 4: {
                    return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), p_74961_6_);
                }
            }
        }
        return null;
    }
    
    protected StructureComponent getNextComponentZ(final Start p_74965_1_, final List p_74965_2_, final Random p_74965_3_, final int p_74965_4_, final int p_74965_5_, final boolean p_74965_6_) {
        if (this.coordBaseMode != null) {
            switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, this.getComponentType(), p_74965_6_);
                }
                case 2: {
                    return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, this.getComponentType(), p_74965_6_);
                }
                case 3: {
                    return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), p_74965_6_);
                }
                case 4: {
                    return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), p_74965_6_);
                }
            }
        }
        return null;
    }
    
    static {
        field_111019_a = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2) });
    }
}
