/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.TriplePermutation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;

public enum Orientation implements IStringSerializable
{
    IDENTITY("identity", TriplePermutation.P123, false, false, false),
    ROT_180_FACE_XY("rot_180_face_xy", TriplePermutation.P123, true, true, false),
    ROT_180_FACE_XZ("rot_180_face_xz", TriplePermutation.P123, true, false, true),
    ROT_180_FACE_YZ("rot_180_face_yz", TriplePermutation.P123, false, true, true),
    ROT_120_NNN("rot_120_nnn", TriplePermutation.P231, false, false, false),
    ROT_120_NNP("rot_120_nnp", TriplePermutation.P312, true, false, true),
    ROT_120_NPN("rot_120_npn", TriplePermutation.P312, false, true, true),
    ROT_120_NPP("rot_120_npp", TriplePermutation.P231, true, false, true),
    ROT_120_PNN("rot_120_pnn", TriplePermutation.P312, true, true, false),
    ROT_120_PNP("rot_120_pnp", TriplePermutation.P231, true, true, false),
    ROT_120_PPN("rot_120_ppn", TriplePermutation.P231, false, true, true),
    ROT_120_PPP("rot_120_ppp", TriplePermutation.P312, false, false, false),
    ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", TriplePermutation.P213, true, true, true),
    ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", TriplePermutation.P213, false, false, true),
    ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", TriplePermutation.P321, true, true, true),
    ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", TriplePermutation.P321, false, true, false),
    ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", TriplePermutation.P132, true, true, true),
    ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", TriplePermutation.P132, true, false, false),
    ROT_90_X_NEG("rot_90_x_neg", TriplePermutation.P132, false, false, true),
    ROT_90_X_POS("rot_90_x_pos", TriplePermutation.P132, false, true, false),
    ROT_90_Y_NEG("rot_90_y_neg", TriplePermutation.P321, true, false, false),
    ROT_90_Y_POS("rot_90_y_pos", TriplePermutation.P321, false, false, true),
    ROT_90_Z_NEG("rot_90_z_neg", TriplePermutation.P213, false, true, false),
    ROT_90_Z_POS("rot_90_z_pos", TriplePermutation.P213, true, false, false),
    INVERSION("inversion", TriplePermutation.P123, true, true, true),
    INVERT_X("invert_x", TriplePermutation.P123, true, false, false),
    INVERT_Y("invert_y", TriplePermutation.P123, false, true, false),
    INVERT_Z("invert_z", TriplePermutation.P123, false, false, true),
    ROT_60_REF_NNN("rot_60_ref_nnn", TriplePermutation.P312, true, true, true),
    ROT_60_REF_NNP("rot_60_ref_nnp", TriplePermutation.P231, true, false, false),
    ROT_60_REF_NPN("rot_60_ref_npn", TriplePermutation.P231, false, false, true),
    ROT_60_REF_NPP("rot_60_ref_npp", TriplePermutation.P312, false, false, true),
    ROT_60_REF_PNN("rot_60_ref_pnn", TriplePermutation.P231, false, true, false),
    ROT_60_REF_PNP("rot_60_ref_pnp", TriplePermutation.P312, true, false, false),
    ROT_60_REF_PPN("rot_60_ref_ppn", TriplePermutation.P312, false, true, false),
    ROT_60_REF_PPP("rot_60_ref_ppp", TriplePermutation.P231, true, true, true),
    SWAP_XY("swap_xy", TriplePermutation.P213, false, false, false),
    SWAP_YZ("swap_yz", TriplePermutation.P132, false, false, false),
    SWAP_XZ("swap_xz", TriplePermutation.P321, false, false, false),
    SWAP_NEG_XY("swap_neg_xy", TriplePermutation.P213, true, true, false),
    SWAP_NEG_YZ("swap_neg_yz", TriplePermutation.P132, false, true, true),
    SWAP_NEG_XZ("swap_neg_xz", TriplePermutation.P321, true, false, true),
    ROT_90_REF_X_NEG("rot_90_ref_x_neg", TriplePermutation.P132, true, false, true),
    ROT_90_REF_X_POS("rot_90_ref_x_pos", TriplePermutation.P132, true, true, false),
    ROT_90_REF_Y_NEG("rot_90_ref_y_neg", TriplePermutation.P321, true, true, false),
    ROT_90_REF_Y_POS("rot_90_ref_y_pos", TriplePermutation.P321, false, true, true),
    ROT_90_REF_Z_NEG("rot_90_ref_z_neg", TriplePermutation.P213, false, true, true),
    ROT_90_REF_Z_POS("rot_90_ref_z_pos", TriplePermutation.P213, true, false, true);

    private final Matrix3f field_235517_W_;
    private final String field_235518_X_;
    @Nullable
    private Map<Direction, Direction> field_235519_Y_;
    private final boolean field_235520_Z_;
    private final boolean field_235521_aa_;
    private final boolean field_235522_ab_;
    private final TriplePermutation field_235523_ac_;
    private static final Orientation[][] field_235524_ad_;
    private static final Orientation[] field_235525_ae_;

    private Orientation(String string2, TriplePermutation triplePermutation, boolean bl, boolean bl2, boolean bl3) {
        this.field_235518_X_ = string2;
        this.field_235520_Z_ = bl;
        this.field_235521_aa_ = bl2;
        this.field_235522_ab_ = bl3;
        this.field_235523_ac_ = triplePermutation;
        this.field_235517_W_ = new Matrix3f();
        this.field_235517_W_.m00 = bl ? -1.0f : 1.0f;
        this.field_235517_W_.m11 = bl2 ? -1.0f : 1.0f;
        this.field_235517_W_.m22 = bl3 ? -1.0f : 1.0f;
        this.field_235517_W_.mul(triplePermutation.func_239186_a_());
    }

    private BooleanList func_235533_b_() {
        return new BooleanArrayList(new boolean[]{this.field_235520_Z_, this.field_235521_aa_, this.field_235522_ab_});
    }

    public Orientation func_235527_a_(Orientation orientation) {
        return field_235524_ad_[this.ordinal()][orientation.ordinal()];
    }

    public String toString() {
        return this.field_235518_X_;
    }

    @Override
    public String getString() {
        return this.field_235518_X_;
    }

    public Direction func_235530_a_(Direction direction) {
        if (this.field_235519_Y_ == null) {
            this.field_235519_Y_ = Maps.newEnumMap(Direction.class);
            for (Direction direction2 : Direction.values()) {
                Direction.Axis axis = direction2.getAxis();
                Direction.AxisDirection axisDirection = direction2.getAxisDirection();
                Direction.Axis axis2 = Direction.Axis.values()[this.field_235523_ac_.func_239187_a_(axis.ordinal())];
                Direction.AxisDirection axisDirection2 = this.isOnAxis(axis2) ? axisDirection.inverted() : axisDirection;
                Direction direction3 = Direction.getFacingFromAxisDirection(axis2, axisDirection2);
                this.field_235519_Y_.put(direction2, direction3);
            }
        }
        return this.field_235519_Y_.get(direction);
    }

    public boolean isOnAxis(Direction.Axis axis) {
        switch (1.$SwitchMap$net$minecraft$util$Direction$Axis[axis.ordinal()]) {
            case 1: {
                return this.field_235520_Z_;
            }
            case 2: {
                return this.field_235521_aa_;
            }
        }
        return this.field_235522_ab_;
    }

    public JigsawOrientation func_235531_a_(JigsawOrientation jigsawOrientation) {
        return JigsawOrientation.func_239641_a_(this.func_235530_a_(jigsawOrientation.func_239642_b_()), this.func_235530_a_(jigsawOrientation.func_239644_c_()));
    }

    private static Orientation[] lambda$static$5(int n) {
        return new Orientation[n];
    }

    private static Orientation lambda$static$4(Orientation orientation) {
        return Arrays.stream(Orientation.values()).filter(arg_0 -> Orientation.lambda$static$3(orientation, arg_0)).findAny().get();
    }

    private static boolean lambda$static$3(Orientation orientation, Orientation orientation2) {
        return orientation.func_235527_a_(orientation2) == IDENTITY;
    }

    private static void lambda$static$2(Orientation[][] orientationArray) {
        Map<Pair, Orientation> map = Arrays.stream(Orientation.values()).collect(Collectors.toMap(Orientation::lambda$static$0, Orientation::lambda$static$1));
        for (Orientation orientation : Orientation.values()) {
            for (Orientation orientation2 : Orientation.values()) {
                BooleanList booleanList = orientation.func_235533_b_();
                BooleanList booleanList2 = orientation2.func_235533_b_();
                TriplePermutation triplePermutation = orientation2.field_235523_ac_.func_239188_a_(orientation.field_235523_ac_);
                BooleanArrayList booleanArrayList = new BooleanArrayList(3);
                for (int i = 0; i < 3; ++i) {
                    booleanArrayList.add(booleanList.getBoolean(i) ^ booleanList2.getBoolean(orientation.field_235523_ac_.func_239187_a_(i)));
                }
                orientationArray[orientation.ordinal()][orientation2.ordinal()] = map.get(Pair.of(triplePermutation, booleanArrayList));
            }
        }
    }

    private static Orientation lambda$static$1(Orientation orientation) {
        return orientation;
    }

    private static Pair lambda$static$0(Orientation orientation) {
        return Pair.of(orientation.field_235523_ac_, orientation.func_235533_b_());
    }

    static {
        field_235524_ad_ = Util.make(new Orientation[Orientation.values().length][Orientation.values().length], Orientation::lambda$static$2);
        field_235525_ae_ = (Orientation[])Arrays.stream(Orientation.values()).map(Orientation::lambda$static$4).toArray(Orientation::lambda$static$5);
    }
}

