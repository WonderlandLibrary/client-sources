package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public static class MonumentBuilding extends Piece
{
    private RoomDefinition field_175845_o;
    private RoomDefinition field_175844_p;
    private List field_175843_q;
    
    public MonumentBuilding() {
        this.field_175843_q = Lists.newArrayList();
    }
    
    public MonumentBuilding(final Random p_i45599_1_, final int p_i45599_2_, final int p_i45599_3_, final EnumFacing p_i45599_4_) {
        super(0);
        this.field_175843_q = Lists.newArrayList();
        this.coordBaseMode = p_i45599_4_;
        switch (StructureOceanMonumentPieces.SwitchEnumFacing.field_175971_a[this.coordBaseMode.ordinal()]) {
            case 1:
            case 2: {
                this.boundingBox = new StructureBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
                break;
            }
            default: {
                this.boundingBox = new StructureBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
                break;
            }
        }
        final List var5 = this.func_175836_a(p_i45599_1_);
        this.field_175845_o.field_175963_d = true;
        this.field_175843_q.add(new EntryRoom(this.coordBaseMode, this.field_175845_o));
        this.field_175843_q.add(new MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, p_i45599_1_));
        final ArrayList var6 = Lists.newArrayList();
        var6.add(new XYDoubleRoomFitHelper(null));
        var6.add(new YZDoubleRoomFitHelper(null));
        var6.add(new ZDoubleRoomFitHelper(null));
        var6.add(new XDoubleRoomFitHelper(null));
        var6.add(new YDoubleRoomFitHelper(null));
        var6.add(new FitSimpleRoomTopHelper(null));
        var6.add(new FitSimpleRoomHelper(null));
        for (final RoomDefinition var8 : var5) {
            if (!var8.field_175963_d && !var8.func_175961_b()) {
                for (final MonumentRoomFitHelper var10 : var6) {
                    if (var10.func_175969_a(var8)) {
                        this.field_175843_q.add(var10.func_175968_a(this.coordBaseMode, var8, p_i45599_1_));
                        break;
                    }
                }
            }
        }
        final int var11 = this.boundingBox.minY;
        final int var12 = this.getXWithOffset(9, 22);
        final int var13 = this.getZWithOffset(9, 22);
        for (final Piece var15 : this.field_175843_q) {
            var15.getBoundingBox().offset(var12, var11, var13);
        }
        final StructureBoundingBox var16 = StructureBoundingBox.func_175899_a(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
        final StructureBoundingBox var17 = StructureBoundingBox.func_175899_a(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
        final StructureBoundingBox var18 = StructureBoundingBox.func_175899_a(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
        int var19 = p_i45599_1_.nextInt();
        this.field_175843_q.add(new WingRoom(this.coordBaseMode, var16, var19++));
        this.field_175843_q.add(new WingRoom(this.coordBaseMode, var17, var19++));
        this.field_175843_q.add(new Penthouse(this.coordBaseMode, var18));
    }
    
    private List func_175836_a(final Random p_175836_1_) {
        final RoomDefinition[] var2 = new RoomDefinition[75];
        for (int var3 = 0; var3 < 5; ++var3) {
            for (int var4 = 0; var4 < 4; ++var4) {
                final byte var5 = 0;
                final int var6 = Piece.func_175820_a(var3, var5, var4);
                var2[var6] = new RoomDefinition(var6);
            }
        }
        for (int var3 = 0; var3 < 5; ++var3) {
            for (int var4 = 0; var4 < 4; ++var4) {
                final byte var5 = 1;
                final int var6 = Piece.func_175820_a(var3, var5, var4);
                var2[var6] = new RoomDefinition(var6);
            }
        }
        for (int var3 = 1; var3 < 4; ++var3) {
            for (int var4 = 0; var4 < 2; ++var4) {
                final byte var5 = 2;
                final int var6 = Piece.func_175820_a(var3, var5, var4);
                var2[var6] = new RoomDefinition(var6);
            }
        }
        this.field_175845_o = var2[MonumentBuilding.field_175823_g];
        for (int var3 = 0; var3 < 5; ++var3) {
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var7 = 0; var7 < 3; ++var7) {
                    final int var6 = Piece.func_175820_a(var3, var7, var4);
                    if (var2[var6] != null) {
                        for (final EnumFacing var11 : EnumFacing.values()) {
                            final int var12 = var3 + var11.getFrontOffsetX();
                            final int var13 = var7 + var11.getFrontOffsetY();
                            final int var14 = var4 + var11.getFrontOffsetZ();
                            if (var12 >= 0 && var12 < 5 && var14 >= 0 && var14 < 5 && var13 >= 0 && var13 < 3) {
                                final int var15 = Piece.func_175820_a(var12, var13, var14);
                                if (var2[var15] != null) {
                                    if (var14 != var4) {
                                        var2[var6].func_175957_a(var11.getOpposite(), var2[var15]);
                                    }
                                    else {
                                        var2[var6].func_175957_a(var11, var2[var15]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final RoomDefinition var16;
        var2[MonumentBuilding.field_175831_h].func_175957_a(EnumFacing.UP, var16 = new RoomDefinition(1003));
        final RoomDefinition var17;
        var2[MonumentBuilding.field_175832_i].func_175957_a(EnumFacing.SOUTH, var17 = new RoomDefinition(1001));
        final RoomDefinition var18;
        var2[MonumentBuilding.field_175829_j].func_175957_a(EnumFacing.SOUTH, var18 = new RoomDefinition(1002));
        var16.field_175963_d = true;
        var17.field_175963_d = true;
        var18.field_175963_d = true;
        this.field_175845_o.field_175964_e = true;
        this.field_175844_p = var2[Piece.func_175820_a(p_175836_1_.nextInt(4), 0, 2)];
        this.field_175844_p.field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
        this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
        final ArrayList var19 = Lists.newArrayList();
        final RoomDefinition[] var20 = var2;
        for (int var9 = var2.length, var10 = 0; var10 < var9; ++var10) {
            final RoomDefinition var21 = var20[var10];
            if (var21 != null) {
                var21.func_175958_a();
                var19.add(var21);
            }
        }
        var16.func_175958_a();
        Collections.shuffle(var19, p_175836_1_);
        int var22 = 1;
        for (final RoomDefinition var24 : var19) {
            int var25 = 0;
            int var12 = 0;
            while (var25 < 2 && var12 < 5) {
                ++var12;
                final int var13 = p_175836_1_.nextInt(6);
                if (var24.field_175966_c[var13]) {
                    final int var14 = EnumFacing.getFront(var13).getOpposite().getIndex();
                    var24.field_175966_c[var13] = false;
                    var24.field_175965_b[var13].field_175966_c[var14] = false;
                    if (var24.func_175959_a(var22++) && var24.field_175965_b[var13].func_175959_a(var22++)) {
                        ++var25;
                    }
                    else {
                        var24.field_175966_c[var13] = true;
                        var24.field_175965_b[var13].field_175966_c[var14] = true;
                    }
                }
            }
        }
        var19.add(var16);
        var19.add(var17);
        var19.add(var18);
        return var19;
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175840_a(false, 0, worldIn, p_74875_2_, p_74875_3_);
        this.func_175840_a(true, 33, worldIn, p_74875_2_, p_74875_3_);
        this.func_175839_b(worldIn, p_74875_2_, p_74875_3_);
        this.func_175837_c(worldIn, p_74875_2_, p_74875_3_);
        this.func_175841_d(worldIn, p_74875_2_, p_74875_3_);
        this.func_175835_e(worldIn, p_74875_2_, p_74875_3_);
        this.func_175842_f(worldIn, p_74875_2_, p_74875_3_);
        this.func_175838_g(worldIn, p_74875_2_, p_74875_3_);
        for (int var4 = 0; var4 < 7; ++var4) {
            int var5 = 0;
            while (var5 < 7) {
                if (var5 == 0 && var4 == 3) {
                    var5 = 6;
                }
                final int var6 = var4 * 9;
                final int var7 = var5 * 9;
                for (int var8 = 0; var8 < 4; ++var8) {
                    for (int var9 = 0; var9 < 4; ++var9) {
                        this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, var6 + var8, 0, var7 + var9, p_74875_3_);
                        this.func_175808_b(worldIn, MonumentBuilding.field_175826_b, var6 + var8, -1, var7 + var9, p_74875_3_);
                    }
                }
                if (var4 != 0 && var4 != 6) {
                    var5 += 6;
                }
                else {
                    ++var5;
                }
            }
        }
        for (int var4 = 0; var4 < 5; ++var4) {
            this.func_175804_a(worldIn, p_74875_3_, -1 - var4, 0 + var4 * 2, -1 - var4, -1 - var4, 23, 58 + var4, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 58 + var4, 0 + var4 * 2, -1 - var4, 58 + var4, 23, 58 + var4, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 0 - var4, 0 + var4 * 2, -1 - var4, 57 + var4, 23, -1 - var4, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 0 - var4, 0 + var4 * 2, 58 + var4, 57 + var4, 23, 58 + var4, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
        }
        for (final Piece var11 : this.field_175843_q) {
            if (var11.getBoundingBox().intersectsWith(p_74875_3_)) {
                var11.addComponentParts(worldIn, p_74875_2_, p_74875_3_);
            }
        }
        return true;
    }
    
    private void func_175840_a(final boolean p_175840_1_, final int p_175840_2_, final World worldIn, final Random p_175840_4_, final StructureBoundingBox p_175840_5_) {
        final boolean var6 = true;
        if (this.func_175818_a(p_175840_5_, p_175840_2_, 0, p_175840_2_ + 23, 20)) {
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 0, 0, 0, p_175840_2_ + 24, 0, 20, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 0, 1, 0, p_175840_2_ + 24, 10, 20, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            for (int var7 = 0; var7 < 4; ++var7) {
                this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + var7, var7 + 1, var7, p_175840_2_ + var7, var7 + 1, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + var7 + 7, var7 + 5, var7 + 7, p_175840_2_ + var7 + 7, var7 + 5, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 17 - var7, var7 + 5, var7 + 7, p_175840_2_ + 17 - var7, var7 + 5, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 24 - var7, var7 + 1, var7, p_175840_2_ + 24 - var7, var7 + 1, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + var7 + 1, var7 + 1, var7, p_175840_2_ + 23 - var7, var7 + 1, var7, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + var7 + 8, var7 + 5, var7 + 7, p_175840_2_ + 16 - var7, var7 + 5, var7 + 7, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 4, 4, 4, p_175840_2_ + 6, 4, 20, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 7, 4, 4, p_175840_2_ + 17, 4, 6, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 18, 4, 4, p_175840_2_ + 20, 4, 20, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 11, 8, 11, p_175840_2_ + 13, 8, 20, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, p_175840_2_ + 12, 9, 12, p_175840_5_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, p_175840_2_ + 12, 9, 15, p_175840_5_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, p_175840_2_ + 12, 9, 18, p_175840_5_);
            int var7 = p_175840_1_ ? (p_175840_2_ + 19) : (p_175840_2_ + 5);
            final int var8 = p_175840_1_ ? (p_175840_2_ + 5) : (p_175840_2_ + 19);
            for (int var9 = 20; var9 >= 5; var9 -= 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var7, 5, var9, p_175840_5_);
            }
            for (int var9 = 19; var9 >= 7; var9 -= 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var8, 5, var9, p_175840_5_);
            }
            for (int var9 = 0; var9 < 4; ++var9) {
                final int var10 = p_175840_1_ ? (p_175840_2_ + (24 - (17 - var9 * 3))) : (p_175840_2_ + 17 - var9 * 3);
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var10, 5, 5, p_175840_5_);
            }
            this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var8, 5, 5, p_175840_5_);
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 11, 1, 12, p_175840_2_ + 13, 7, 12, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175840_5_, p_175840_2_ + 12, 1, 11, p_175840_2_ + 12, 7, 13, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
        }
    }
    
    private void func_175839_b(final World worldIn, final Random p_175839_2_, final StructureBoundingBox p_175839_3_) {
        if (this.func_175818_a(p_175839_3_, 22, 5, 35, 17)) {
            this.func_175804_a(worldIn, p_175839_3_, 25, 0, 0, 32, 8, 20, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175839_3_, 24, 2, 5 + var4 * 4, 24, 4, 5 + var4 * 4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175839_3_, 22, 4, 5 + var4 * 4, 23, 4, 5 + var4 * 4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 25, 5, 5 + var4 * 4, p_175839_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 26, 6, 5 + var4 * 4, p_175839_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175825_e, 26, 5, 5 + var4 * 4, p_175839_3_);
                this.func_175804_a(worldIn, p_175839_3_, 33, 2, 5 + var4 * 4, 33, 4, 5 + var4 * 4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175839_3_, 34, 4, 5 + var4 * 4, 35, 4, 5 + var4 * 4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 32, 5, 5 + var4 * 4, p_175839_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 31, 6, 5 + var4 * 4, p_175839_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175825_e, 31, 5, 5 + var4 * 4, p_175839_3_);
                this.func_175804_a(worldIn, p_175839_3_, 27, 6, 5 + var4 * 4, 30, 6, 5 + var4 * 4, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            }
        }
    }
    
    private void func_175837_c(final World worldIn, final Random p_175837_2_, final StructureBoundingBox p_175837_3_) {
        if (this.func_175818_a(p_175837_3_, 15, 20, 42, 21)) {
            this.func_175804_a(worldIn, p_175837_3_, 15, 0, 21, 42, 0, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 26, 1, 21, 31, 3, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 21, 12, 21, 36, 12, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 17, 11, 21, 40, 11, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 16, 10, 21, 41, 10, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 15, 7, 21, 42, 9, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 16, 6, 21, 41, 6, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 17, 5, 21, 40, 5, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 21, 4, 21, 36, 4, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 22, 3, 21, 26, 3, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 31, 3, 21, 35, 3, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 23, 2, 21, 25, 2, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 32, 2, 21, 34, 2, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175837_3_, 28, 4, 20, 29, 4, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 27, 3, 21, p_175837_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 30, 3, 21, p_175837_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 26, 2, 21, p_175837_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 31, 2, 21, p_175837_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 25, 1, 21, p_175837_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 32, 1, 21, p_175837_3_);
            for (int var4 = 0; var4 < 7; ++var4) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 28 - var4, 6 + var4, 21, p_175837_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 29 + var4, 6 + var4, 21, p_175837_3_);
            }
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 28 - var4, 9 + var4, 21, p_175837_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 29 + var4, 9 + var4, 21, p_175837_3_);
            }
            this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 28, 12, 21, p_175837_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 29, 12, 21, p_175837_3_);
            for (int var4 = 0; var4 < 3; ++var4) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 22 - var4 * 2, 8, 21, p_175837_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 22 - var4 * 2, 9, 21, p_175837_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 35 + var4 * 2, 8, 21, p_175837_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175827_c, 35 + var4 * 2, 9, 21, p_175837_3_);
            }
            this.func_175804_a(worldIn, p_175837_3_, 15, 13, 21, 42, 15, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 15, 1, 21, 15, 6, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 16, 1, 21, 16, 5, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 17, 1, 21, 20, 4, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 21, 1, 21, 21, 3, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 22, 1, 21, 22, 2, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 23, 1, 21, 24, 1, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 42, 1, 21, 42, 6, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 41, 1, 21, 41, 5, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 37, 1, 21, 40, 4, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 36, 1, 21, 36, 3, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 35, 1, 21, 35, 2, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175837_3_, 33, 1, 21, 34, 1, 21, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
        }
    }
    
    private void func_175841_d(final World worldIn, final Random p_175841_2_, final StructureBoundingBox p_175841_3_) {
        if (this.func_175818_a(p_175841_3_, 21, 21, 36, 36)) {
            this.func_175804_a(worldIn, p_175841_3_, 21, 0, 22, 36, 0, 36, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175841_3_, 21, 1, 22, 36, 23, 36, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175841_3_, 21 + var4, 13 + var4, 21 + var4, 36 - var4, 13 + var4, 21 + var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175841_3_, 21 + var4, 13 + var4, 36 - var4, 36 - var4, 13 + var4, 36 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175841_3_, 21 + var4, 13 + var4, 22 + var4, 21 + var4, 13 + var4, 35 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.func_175804_a(worldIn, p_175841_3_, 36 - var4, 13 + var4, 22 + var4, 36 - var4, 13 + var4, 35 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            this.func_175804_a(worldIn, p_175841_3_, 25, 16, 25, 32, 16, 32, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175841_3_, 25, 17, 25, 25, 19, 25, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.func_175804_a(worldIn, p_175841_3_, 32, 17, 25, 32, 19, 25, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.func_175804_a(worldIn, p_175841_3_, 25, 17, 32, 25, 19, 32, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.func_175804_a(worldIn, p_175841_3_, 32, 17, 32, 32, 19, 32, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 26, 20, 26, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 27, 21, 27, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175825_e, 27, 20, 27, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 26, 20, 31, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 27, 21, 30, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175825_e, 27, 20, 30, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 31, 20, 31, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 30, 21, 30, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175825_e, 30, 20, 30, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 31, 20, 26, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175826_b, 30, 21, 27, p_175841_3_);
            this.func_175811_a(worldIn, MonumentBuilding.field_175825_e, 30, 20, 27, p_175841_3_);
            this.func_175804_a(worldIn, p_175841_3_, 28, 21, 27, 29, 21, 27, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175841_3_, 27, 21, 28, 27, 21, 29, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175841_3_, 28, 21, 30, 29, 21, 30, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175841_3_, 30, 21, 28, 30, 21, 29, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
        }
    }
    
    private void func_175835_e(final World worldIn, final Random p_175835_2_, final StructureBoundingBox p_175835_3_) {
        if (this.func_175818_a(p_175835_3_, 0, 21, 6, 58)) {
            this.func_175804_a(worldIn, p_175835_3_, 0, 0, 21, 6, 0, 57, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175835_3_, 0, 1, 21, 6, 7, 57, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175835_3_, 4, 4, 21, 6, 4, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175835_3_, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 23; var4 < 53; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 5, 5, var4, p_175835_3_);
            }
            this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 5, 5, 52, p_175835_3_);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175835_3_, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            this.func_175804_a(worldIn, p_175835_3_, 4, 1, 52, 6, 3, 52, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175835_3_, 5, 1, 51, 5, 3, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
        }
        if (this.func_175818_a(p_175835_3_, 51, 21, 58, 58)) {
            this.func_175804_a(worldIn, p_175835_3_, 51, 0, 21, 57, 0, 57, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175835_3_, 51, 1, 21, 57, 7, 57, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175835_3_, 51, 4, 21, 53, 4, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175835_3_, 57 - var4, var4 + 1, 21, 57 - var4, var4 + 1, 57 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 23; var4 < 53; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 52, 5, var4, p_175835_3_);
            }
            this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 52, 5, 52, p_175835_3_);
            this.func_175804_a(worldIn, p_175835_3_, 51, 1, 52, 53, 3, 52, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175835_3_, 52, 1, 51, 52, 3, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
        }
        if (this.func_175818_a(p_175835_3_, 0, 51, 57, 57)) {
            this.func_175804_a(worldIn, p_175835_3_, 7, 0, 51, 50, 0, 57, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175835_3_, 7, 1, 51, 50, 10, 57, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175835_3_, var4 + 1, var4 + 1, 57 - var4, 56 - var4, var4 + 1, 57 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
        }
    }
    
    private void func_175842_f(final World worldIn, final Random p_175842_2_, final StructureBoundingBox p_175842_3_) {
        if (this.func_175818_a(p_175842_3_, 7, 21, 13, 50)) {
            this.func_175804_a(worldIn, p_175842_3_, 7, 0, 21, 13, 0, 50, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175842_3_, 7, 1, 21, 13, 10, 50, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175842_3_, 11, 8, 21, 13, 8, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175842_3_, var4 + 7, var4 + 5, 21, var4 + 7, var4 + 5, 54, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 21; var4 <= 45; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 12, 9, var4, p_175842_3_);
            }
        }
        if (this.func_175818_a(p_175842_3_, 44, 21, 50, 54)) {
            this.func_175804_a(worldIn, p_175842_3_, 44, 0, 21, 50, 0, 50, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175842_3_, 44, 1, 21, 50, 10, 50, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175842_3_, 44, 8, 21, 46, 8, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175842_3_, 50 - var4, var4 + 5, 21, 50 - var4, var4 + 5, 54, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 21; var4 <= 45; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 45, 9, var4, p_175842_3_);
            }
        }
        if (this.func_175818_a(p_175842_3_, 8, 44, 49, 54)) {
            this.func_175804_a(worldIn, p_175842_3_, 14, 0, 44, 43, 0, 50, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175842_3_, 14, 1, 44, 43, 10, 50, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            for (int var4 = 12; var4 <= 45; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 9, 45, p_175842_3_);
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 9, 52, p_175842_3_);
                if (var4 == 12 || var4 == 18 || var4 == 24 || var4 == 33 || var4 == 39 || var4 == 45) {
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 9, 47, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 9, 50, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 10, 45, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 10, 46, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 10, 51, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 10, 52, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 11, 47, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 11, 50, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 12, 48, p_175842_3_);
                    this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 12, 49, p_175842_3_);
                }
            }
            for (int var4 = 0; var4 < 3; ++var4) {
                this.func_175804_a(worldIn, p_175842_3_, 8 + var4, 5 + var4, 54, 49 - var4, 5 + var4, 54, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            }
            this.func_175804_a(worldIn, p_175842_3_, 11, 8, 54, 46, 8, 54, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.func_175804_a(worldIn, p_175842_3_, 14, 8, 44, 43, 8, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
        }
    }
    
    private void func_175838_g(final World worldIn, final Random p_175838_2_, final StructureBoundingBox p_175838_3_) {
        if (this.func_175818_a(p_175838_3_, 14, 21, 20, 43)) {
            this.func_175804_a(worldIn, p_175838_3_, 14, 0, 21, 20, 0, 43, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175838_3_, 14, 1, 22, 20, 14, 43, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175838_3_, 18, 12, 22, 20, 12, 39, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175838_3_, 18, 12, 21, 20, 12, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175838_3_, var4 + 14, var4 + 9, 21, var4 + 14, var4 + 9, 43 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 23; var4 <= 39; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 19, 13, var4, p_175838_3_);
            }
        }
        if (this.func_175818_a(p_175838_3_, 37, 21, 43, 43)) {
            this.func_175804_a(worldIn, p_175838_3_, 37, 0, 21, 43, 0, 43, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175838_3_, 37, 1, 22, 43, 14, 43, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175838_3_, 37, 12, 22, 39, 12, 39, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175838_3_, 37, 12, 21, 39, 12, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175838_3_, 43 - var4, var4 + 9, 21, 43 - var4, var4 + 9, 43 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 23; var4 <= 39; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, 38, 13, var4, p_175838_3_);
            }
        }
        if (this.func_175818_a(p_175838_3_, 15, 37, 42, 43)) {
            this.func_175804_a(worldIn, p_175838_3_, 21, 0, 37, 36, 0, 43, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_175804_a(worldIn, p_175838_3_, 21, 1, 37, 36, 14, 43, MonumentBuilding.field_175822_f, MonumentBuilding.field_175822_f, false);
            this.func_175804_a(worldIn, p_175838_3_, 21, 12, 37, 36, 12, 39, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_175838_3_, 15 + var4, var4 + 9, 43 - var4, 42 - var4, var4 + 9, 43 - var4, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            }
            for (int var4 = 21; var4 <= 36; var4 += 3) {
                this.func_175811_a(worldIn, MonumentBuilding.field_175824_d, var4, 13, 38, p_175838_3_);
            }
        }
    }
}
