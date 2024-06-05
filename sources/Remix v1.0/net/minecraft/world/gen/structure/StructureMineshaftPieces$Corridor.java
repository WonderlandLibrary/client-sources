package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;

public static class Corridor extends StructureComponent
{
    private boolean hasRails;
    private boolean hasSpiders;
    private boolean spawnerPlaced;
    private int sectionCount;
    
    public Corridor() {
    }
    
    public Corridor(final int p_i45625_1_, final Random p_i45625_2_, final StructureBoundingBox p_i45625_3_, final EnumFacing p_i45625_4_) {
        super(p_i45625_1_);
        this.coordBaseMode = p_i45625_4_;
        this.boundingBox = p_i45625_3_;
        this.hasRails = (p_i45625_2_.nextInt(3) == 0);
        this.hasSpiders = (!this.hasRails && p_i45625_2_.nextInt(23) == 0);
        if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
            this.sectionCount = p_i45625_3_.getXSize() / 5;
        }
        else {
            this.sectionCount = p_i45625_3_.getZSize() / 5;
        }
    }
    
    public static StructureBoundingBox func_175814_a(final List p_175814_0_, final Random p_175814_1_, final int p_175814_2_, final int p_175814_3_, final int p_175814_4_, final EnumFacing p_175814_5_) {
        final StructureBoundingBox var6 = new StructureBoundingBox(p_175814_2_, p_175814_3_, p_175814_4_, p_175814_2_, p_175814_3_ + 2, p_175814_4_);
        int var7;
        for (var7 = p_175814_1_.nextInt(3) + 2; var7 > 0; --var7) {
            final int var8 = var7 * 5;
            switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[p_175814_5_.ordinal()]) {
                case 1: {
                    var6.maxX = p_175814_2_ + 2;
                    var6.minZ = p_175814_4_ - (var8 - 1);
                    break;
                }
                case 2: {
                    var6.maxX = p_175814_2_ + 2;
                    var6.maxZ = p_175814_4_ + (var8 - 1);
                    break;
                }
                case 3: {
                    var6.minX = p_175814_2_ - (var8 - 1);
                    var6.maxZ = p_175814_4_ + 2;
                    break;
                }
                case 4: {
                    var6.maxX = p_175814_2_ + (var8 - 1);
                    var6.maxZ = p_175814_4_ + 2;
                    break;
                }
            }
            if (StructureComponent.findIntersecting(p_175814_0_, var6) == null) {
                break;
            }
        }
        return (var7 > 0) ? var6 : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        p_143012_1_.setBoolean("hr", this.hasRails);
        p_143012_1_.setBoolean("sc", this.hasSpiders);
        p_143012_1_.setBoolean("hps", this.spawnerPlaced);
        p_143012_1_.setInteger("Num", this.sectionCount);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        this.hasRails = p_143011_1_.getBoolean("hr");
        this.hasSpiders = p_143011_1_.getBoolean("sc");
        this.spawnerPlaced = p_143011_1_.getBoolean("hps");
        this.sectionCount = p_143011_1_.getInteger("Num");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        final int var4 = this.getComponentType();
        final int var5 = p_74861_3_.nextInt(4);
        if (this.coordBaseMode != null) {
            switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    if (var5 <= 1) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, var4);
                        break;
                    }
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, var4);
                    break;
                }
                case 2: {
                    if (var5 <= 1) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, var4);
                        break;
                    }
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, var4);
                    break;
                }
                case 3: {
                    if (var5 <= 1) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
                        break;
                    }
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
                    break;
                }
                case 4: {
                    if (var5 <= 1) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
                        break;
                    }
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
                    break;
                }
            }
        }
        if (var4 < 8) {
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
                for (int var6 = this.boundingBox.minX + 3; var6 + 3 <= this.boundingBox.maxX; var6 += 5) {
                    final int var7 = p_74861_3_.nextInt(5);
                    if (var7 == 0) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, var6, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4 + 1);
                    }
                    else if (var7 == 1) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, var6, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4 + 1);
                    }
                }
            }
            else {
                for (int var6 = this.boundingBox.minZ + 3; var6 + 3 <= this.boundingBox.maxZ; var6 += 5) {
                    final int var7 = p_74861_3_.nextInt(5);
                    if (var7 == 0) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, var6, EnumFacing.WEST, var4 + 1);
                    }
                    else if (var7 == 1) {
                        StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, var6, EnumFacing.EAST, var4 + 1);
                    }
                }
            }
        }
    }
    
    @Override
    protected boolean func_180778_a(final World worldIn, final StructureBoundingBox p_180778_2_, final Random p_180778_3_, final int p_180778_4_, final int p_180778_5_, final int p_180778_6_, final List p_180778_7_, final int p_180778_8_) {
        final BlockPos var9 = new BlockPos(this.getXWithOffset(p_180778_4_, p_180778_6_), this.getYWithOffset(p_180778_5_), this.getZWithOffset(p_180778_4_, p_180778_6_));
        if (p_180778_2_.func_175898_b(var9) && worldIn.getBlockState(var9).getBlock().getMaterial() == Material.air) {
            final int var10 = p_180778_3_.nextBoolean() ? 1 : 0;
            worldIn.setBlockState(var9, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, var10)), 2);
            final EntityMinecartChest var11 = new EntityMinecartChest(worldIn, var9.getX() + 0.5f, var9.getY() + 0.5f, var9.getZ() + 0.5f);
            WeightedRandomChestContent.generateChestContents(p_180778_3_, p_180778_7_, var11, p_180778_8_);
            worldIn.spawnEntityInWorld(var11);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        final boolean var4 = false;
        final boolean var5 = true;
        final boolean var6 = false;
        final boolean var7 = true;
        final int var8 = this.sectionCount * 5 - 1;
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 2, 1, var8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175805_a(worldIn, p_74875_3_, p_74875_2_, 0.8f, 0, 2, 0, 2, 2, var8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        if (this.hasSpiders) {
            this.func_175805_a(worldIn, p_74875_3_, p_74875_2_, 0.6f, 0, 0, 0, 2, 1, var8, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        for (int var9 = 0; var9 < this.sectionCount; ++var9) {
            final int var10 = 2 + var9 * 5;
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, var10, 0, 1, var10, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 0, var10, 2, 1, var10, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (p_74875_2_.nextInt(4) == 0) {
                this.func_175804_a(worldIn, p_74875_3_, 0, 2, var10, 0, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 2, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 0, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 0, 2, var10 - 1, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 2, 2, var10 - 1, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 0, 2, var10 + 1, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 2, 2, var10 + 1, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05f, 0, 2, var10 - 2, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05f, 2, 2, var10 - 2, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05f, 0, 2, var10 + 2, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05f, 2, 2, var10 + 2, Blocks.web.getDefaultState());
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05f, 1, 2, var10 - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
            this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05f, 1, 2, var10 + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
            if (p_74875_2_.nextInt(100) == 0) {
                this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 2, 0, var10 - 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$100(), Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 3 + p_74875_2_.nextInt(4));
            }
            if (p_74875_2_.nextInt(100) == 0) {
                this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 0, 0, var10 + 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$100(), Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 3 + p_74875_2_.nextInt(4));
            }
            if (this.hasSpiders && !this.spawnerPlaced) {
                final int var11 = this.getYWithOffset(0);
                int var12 = var10 - 1 + p_74875_2_.nextInt(3);
                final int var13 = this.getXWithOffset(1, var12);
                var12 = this.getZWithOffset(1, var12);
                final BlockPos var14 = new BlockPos(var13, var11, var12);
                if (p_74875_3_.func_175898_b(var14)) {
                    this.spawnerPlaced = true;
                    worldIn.setBlockState(var14, Blocks.mob_spawner.getDefaultState(), 2);
                    final TileEntity var15 = worldIn.getTileEntity(var14);
                    if (var15 instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)var15).getSpawnerBaseLogic().setEntityName("CaveSpider");
                    }
                }
            }
        }
        for (int var9 = 0; var9 <= 2; ++var9) {
            for (int var10 = 0; var10 <= var8; ++var10) {
                final byte var16 = -1;
                final IBlockState var17 = this.func_175807_a(worldIn, var9, var16, var10, p_74875_3_);
                if (var17.getBlock().getMaterial() == Material.air) {
                    final byte var18 = -1;
                    this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), var9, var18, var10, p_74875_3_);
                }
            }
        }
        if (this.hasRails) {
            for (int var9 = 0; var9 <= var8; ++var9) {
                final IBlockState var19 = this.func_175807_a(worldIn, 1, -1, var9, p_74875_3_);
                if (var19.getBlock().getMaterial() != Material.air && var19.getBlock().isFullBlock()) {
                    this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.7f, 1, 0, var9, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, 0)));
                }
            }
        }
        return true;
    }
}
