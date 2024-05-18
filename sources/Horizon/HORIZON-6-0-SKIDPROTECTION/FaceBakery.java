package HORIZON-6-0-SKIDPROTECTION;

import javax.vecmath.Tuple3f;
import javax.vecmath.Matrix4d;
import javax.vecmath.Tuple3d;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class FaceBakery
{
    private static final double HorizonCode_Horizon_È;
    private static final double Â;
    private static final String Ý = "CL_00002490";
    
    static {
        HorizonCode_Horizon_È = 1.0 / Math.cos(0.39269908169872414) - 1.0;
        Â = 1.0 / Math.cos(0.7853981633974483) - 1.0;
    }
    
    public BakedQuad HorizonCode_Horizon_È(final Vector3f posFrom, final Vector3f posTo, final BlockPartFace face, final TextureAtlasSprite sprite, final EnumFacing facing, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final int[] var10 = this.HorizonCode_Horizon_È(face, sprite, facing, this.HorizonCode_Horizon_È(posFrom, posTo), modelRotationIn, partRotation, uvLocked, shade);
        final EnumFacing var11 = HorizonCode_Horizon_È(var10);
        if (uvLocked) {
            this.HorizonCode_Horizon_È(var10, var11, face.Âµá€, sprite);
        }
        if (partRotation == null) {
            this.HorizonCode_Horizon_È(var10, var11);
        }
        return new BakedQuad(var10, face.Ý, var11, sprite);
    }
    
    private int[] HorizonCode_Horizon_È(final BlockPartFace p_178405_1_, final TextureAtlasSprite p_178405_2_, final EnumFacing p_178405_3_, final float[] p_178405_4_, final ModelRotation p_178405_5_, final BlockPartRotation p_178405_6_, final boolean p_178405_7_, final boolean shade) {
        final int[] var9 = new int[28];
        for (int var10 = 0; var10 < 4; ++var10) {
            this.HorizonCode_Horizon_È(var9, var10, p_178405_3_, p_178405_1_, p_178405_4_, p_178405_2_, p_178405_5_, p_178405_6_, p_178405_7_, shade);
        }
        return var9;
    }
    
    private int HorizonCode_Horizon_È(final EnumFacing p_178413_1_) {
        final float var2 = this.Â(p_178413_1_);
        final int var3 = MathHelper.HorizonCode_Horizon_È((int)(var2 * 255.0f), 0, 255);
        return 0xFF000000 | var3 << 16 | var3 << 8 | var3;
    }
    
    private float Â(final EnumFacing p_178412_1_) {
        switch (FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_178412_1_.ordinal()]) {
            case 1: {
                return 0.5f;
            }
            case 2: {
                return 1.0f;
            }
            case 3:
            case 4: {
                return 0.8f;
            }
            case 5:
            case 6: {
                return 0.6f;
            }
            default: {
                return 1.0f;
            }
        }
    }
    
    private float[] HorizonCode_Horizon_È(final Vector3f pos1, final Vector3f pos2) {
        final float[] var3 = new float[EnumFacing.values().length];
        var3[EnumFaceing.HorizonCode_Horizon_È.Ó] = pos1.x / 16.0f;
        var3[EnumFaceing.HorizonCode_Horizon_È.Âµá€] = pos1.y / 16.0f;
        var3[EnumFaceing.HorizonCode_Horizon_È.Ø­áŒŠá] = pos1.z / 16.0f;
        var3[EnumFaceing.HorizonCode_Horizon_È.Ý] = pos2.x / 16.0f;
        var3[EnumFaceing.HorizonCode_Horizon_È.Â] = pos2.y / 16.0f;
        var3[EnumFaceing.HorizonCode_Horizon_È.HorizonCode_Horizon_È] = pos2.z / 16.0f;
        return var3;
    }
    
    private void HorizonCode_Horizon_È(final int[] faceData, final int vertexIndex, final EnumFacing facing, final BlockPartFace partFace, final float[] p_178402_5_, final TextureAtlasSprite sprite, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final EnumFacing var11 = modelRotationIn.HorizonCode_Horizon_È(facing);
        final int var12 = shade ? this.HorizonCode_Horizon_È(var11) : -1;
        final EnumFaceing.Â var13 = EnumFaceing.HorizonCode_Horizon_È(facing).HorizonCode_Horizon_È(vertexIndex);
        final Vector3d var14 = new Vector3d((double)p_178402_5_[var13.HorizonCode_Horizon_È], (double)p_178402_5_[var13.Â], (double)p_178402_5_[var13.Ý]);
        this.HorizonCode_Horizon_È(var14, partRotation);
        final int var15 = this.HorizonCode_Horizon_È(var14, facing, vertexIndex, modelRotationIn, uvLocked);
        this.HorizonCode_Horizon_È(faceData, var15, vertexIndex, var14, var12, sprite, partFace.Âµá€);
    }
    
    private void HorizonCode_Horizon_È(final int[] faceData, final int storeIndex, final int vertexIndex, final Vector3d position, final int shadeColor, final TextureAtlasSprite sprite, final BlockFaceUV faceUV) {
        final int var8 = storeIndex * 7;
        faceData[var8] = Float.floatToRawIntBits((float)position.x);
        faceData[var8 + 1] = Float.floatToRawIntBits((float)position.y);
        faceData[var8 + 2] = Float.floatToRawIntBits((float)position.z);
        faceData[var8 + 3] = shadeColor;
        faceData[var8 + 4] = Float.floatToRawIntBits(sprite.HorizonCode_Horizon_È((double)faceUV.HorizonCode_Horizon_È(vertexIndex)));
        faceData[var8 + 4 + 1] = Float.floatToRawIntBits(sprite.Â((double)faceUV.Â(vertexIndex)));
    }
    
    private void HorizonCode_Horizon_È(final Vector3d p_178407_1_, final BlockPartRotation p_178407_2_) {
        if (p_178407_2_ != null) {
            final Matrix4d var3 = this.HorizonCode_Horizon_È();
            final Vector3d var4 = new Vector3d(0.0, 0.0, 0.0);
            switch (FaceBakery.HorizonCode_Horizon_È.Â[p_178407_2_.Â.ordinal()]) {
                case 1: {
                    var3.mul(this.HorizonCode_Horizon_È(new AxisAngle4d(1.0, 0.0, 0.0, p_178407_2_.Ý * 0.017453292519943295)));
                    var4.set(0.0, 1.0, 1.0);
                    break;
                }
                case 2: {
                    var3.mul(this.HorizonCode_Horizon_È(new AxisAngle4d(0.0, 1.0, 0.0, p_178407_2_.Ý * 0.017453292519943295)));
                    var4.set(1.0, 0.0, 1.0);
                    break;
                }
                case 3: {
                    var3.mul(this.HorizonCode_Horizon_È(new AxisAngle4d(0.0, 0.0, 1.0, p_178407_2_.Ý * 0.017453292519943295)));
                    var4.set(1.0, 1.0, 0.0);
                    break;
                }
            }
            if (p_178407_2_.Ø­áŒŠá) {
                if (Math.abs(p_178407_2_.Ý) == 22.5f) {
                    var4.scale(FaceBakery.HorizonCode_Horizon_È);
                }
                else {
                    var4.scale(FaceBakery.Â);
                }
                var4.add((Tuple3d)new Vector3d(1.0, 1.0, 1.0));
            }
            else {
                var4.set((Tuple3d)new Vector3d(1.0, 1.0, 1.0));
            }
            this.HorizonCode_Horizon_È(p_178407_1_, new Vector3d(p_178407_2_.HorizonCode_Horizon_È), var3, var4);
        }
    }
    
    public int HorizonCode_Horizon_È(final Vector3d position, final EnumFacing facing, final int vertexIndex, final ModelRotation modelRotationIn, final boolean uvLocked) {
        if (modelRotationIn == ModelRotation.HorizonCode_Horizon_È) {
            return vertexIndex;
        }
        this.HorizonCode_Horizon_È(position, new Vector3d(0.5, 0.5, 0.5), modelRotationIn.HorizonCode_Horizon_È(), new Vector3d(1.0, 1.0, 1.0));
        return modelRotationIn.HorizonCode_Horizon_È(facing, vertexIndex);
    }
    
    private void HorizonCode_Horizon_È(final Vector3d position, final Vector3d rotationOrigin, final Matrix4d rotationMatrix, final Vector3d scale) {
        position.sub((Tuple3d)rotationOrigin);
        rotationMatrix.transform(position);
        position.x *= scale.x;
        position.y *= scale.y;
        position.z *= scale.z;
        position.add((Tuple3d)rotationOrigin);
    }
    
    private Matrix4d HorizonCode_Horizon_È(final AxisAngle4d p_178416_1_) {
        final Matrix4d var2 = this.HorizonCode_Horizon_È();
        var2.setRotation(p_178416_1_);
        return var2;
    }
    
    private Matrix4d HorizonCode_Horizon_È() {
        final Matrix4d var1 = new Matrix4d();
        var1.setIdentity();
        return var1;
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final int[] p_178410_0_) {
        final Vector3f var1 = new Vector3f(Float.intBitsToFloat(p_178410_0_[0]), Float.intBitsToFloat(p_178410_0_[1]), Float.intBitsToFloat(p_178410_0_[2]));
        final Vector3f var2 = new Vector3f(Float.intBitsToFloat(p_178410_0_[7]), Float.intBitsToFloat(p_178410_0_[8]), Float.intBitsToFloat(p_178410_0_[9]));
        final Vector3f var3 = new Vector3f(Float.intBitsToFloat(p_178410_0_[14]), Float.intBitsToFloat(p_178410_0_[15]), Float.intBitsToFloat(p_178410_0_[16]));
        final Vector3f var4 = new Vector3f();
        final Vector3f var5 = new Vector3f();
        final Vector3f var6 = new Vector3f();
        var4.sub((Tuple3f)var1, (Tuple3f)var2);
        var5.sub((Tuple3f)var3, (Tuple3f)var2);
        var6.cross(var5, var4);
        var6.normalize();
        EnumFacing var7 = null;
        float var8 = 0.719f;
        for (final EnumFacing var12 : EnumFacing.values()) {
            final Vec3i var13 = var12.ˆÏ­();
            final Vector3f var14 = new Vector3f((float)var13.HorizonCode_Horizon_È(), (float)var13.Â(), (float)var13.Ý());
            final float var15 = var6.dot(var14);
            if (var15 >= 0.0f && var15 > var8) {
                var8 = var15;
                var7 = var12;
            }
        }
        if (var7 == null) {
            return EnumFacing.Â;
        }
        return var7;
    }
    
    public void HorizonCode_Horizon_È(final int[] p_178409_1_, final EnumFacing p_178409_2_, final BlockFaceUV p_178409_3_, final TextureAtlasSprite p_178409_4_) {
        for (int var5 = 0; var5 < 4; ++var5) {
            this.HorizonCode_Horizon_È(var5, p_178409_1_, p_178409_2_, p_178409_3_, p_178409_4_);
        }
    }
    
    private void HorizonCode_Horizon_È(final int[] p_178408_1_, final EnumFacing p_178408_2_) {
        final int[] var3 = new int[p_178408_1_.length];
        System.arraycopy(p_178408_1_, 0, var3, 0, p_178408_1_.length);
        final float[] var4 = new float[EnumFacing.values().length];
        var4[EnumFaceing.HorizonCode_Horizon_È.Ó] = 999.0f;
        var4[EnumFaceing.HorizonCode_Horizon_È.Âµá€] = 999.0f;
        var4[EnumFaceing.HorizonCode_Horizon_È.Ø­áŒŠá] = 999.0f;
        var4[EnumFaceing.HorizonCode_Horizon_È.Ý] = -999.0f;
        var4[EnumFaceing.HorizonCode_Horizon_È.Â] = -999.0f;
        var4[EnumFaceing.HorizonCode_Horizon_È.HorizonCode_Horizon_È] = -999.0f;
        for (int var5 = 0; var5 < 4; ++var5) {
            final int var6 = 7 * var5;
            final float var7 = Float.intBitsToFloat(var3[var6]);
            final float var8 = Float.intBitsToFloat(var3[var6 + 1]);
            final float var9 = Float.intBitsToFloat(var3[var6 + 2]);
            if (var7 < var4[EnumFaceing.HorizonCode_Horizon_È.Ó]) {
                var4[EnumFaceing.HorizonCode_Horizon_È.Ó] = var7;
            }
            if (var8 < var4[EnumFaceing.HorizonCode_Horizon_È.Âµá€]) {
                var4[EnumFaceing.HorizonCode_Horizon_È.Âµá€] = var8;
            }
            if (var9 < var4[EnumFaceing.HorizonCode_Horizon_È.Ø­áŒŠá]) {
                var4[EnumFaceing.HorizonCode_Horizon_È.Ø­áŒŠá] = var9;
            }
            if (var7 > var4[EnumFaceing.HorizonCode_Horizon_È.Ý]) {
                var4[EnumFaceing.HorizonCode_Horizon_È.Ý] = var7;
            }
            if (var8 > var4[EnumFaceing.HorizonCode_Horizon_È.Â]) {
                var4[EnumFaceing.HorizonCode_Horizon_È.Â] = var8;
            }
            if (var9 > var4[EnumFaceing.HorizonCode_Horizon_È.HorizonCode_Horizon_È]) {
                var4[EnumFaceing.HorizonCode_Horizon_È.HorizonCode_Horizon_È] = var9;
            }
        }
        final EnumFaceing var10 = EnumFaceing.HorizonCode_Horizon_È(p_178408_2_);
        for (int var6 = 0; var6 < 4; ++var6) {
            final int var11 = 7 * var6;
            final EnumFaceing.Â var12 = var10.HorizonCode_Horizon_È(var6);
            final float var9 = var4[var12.HorizonCode_Horizon_È];
            final float var13 = var4[var12.Â];
            final float var14 = var4[var12.Ý];
            p_178408_1_[var11] = Float.floatToRawIntBits(var9);
            p_178408_1_[var11 + 1] = Float.floatToRawIntBits(var13);
            p_178408_1_[var11 + 2] = Float.floatToRawIntBits(var14);
            for (int var15 = 0; var15 < 4; ++var15) {
                final int var16 = 7 * var15;
                final float var17 = Float.intBitsToFloat(var3[var16]);
                final float var18 = Float.intBitsToFloat(var3[var16 + 1]);
                final float var19 = Float.intBitsToFloat(var3[var16 + 2]);
                if (MathHelper.HorizonCode_Horizon_È(var9, var17) && MathHelper.HorizonCode_Horizon_È(var13, var18) && MathHelper.HorizonCode_Horizon_È(var14, var19)) {
                    p_178408_1_[var11 + 4] = var3[var16 + 4];
                    p_178408_1_[var11 + 4 + 1] = var3[var16 + 4 + 1];
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_178401_1_, final int[] p_178401_2_, final EnumFacing p_178401_3_, final BlockFaceUV p_178401_4_, final TextureAtlasSprite p_178401_5_) {
        final int var6 = 7 * p_178401_1_;
        float var7 = Float.intBitsToFloat(p_178401_2_[var6]);
        float var8 = Float.intBitsToFloat(p_178401_2_[var6 + 1]);
        float var9 = Float.intBitsToFloat(p_178401_2_[var6 + 2]);
        if (var7 < -0.1f || var7 >= 1.1f) {
            var7 -= MathHelper.Ø­áŒŠá(var7);
        }
        if (var8 < -0.1f || var8 >= 1.1f) {
            var8 -= MathHelper.Ø­áŒŠá(var8);
        }
        if (var9 < -0.1f || var9 >= 1.1f) {
            var9 -= MathHelper.Ø­áŒŠá(var9);
        }
        float var10 = 0.0f;
        float var11 = 0.0f;
        switch (FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_178401_3_.ordinal()]) {
            case 1: {
                var10 = var7 * 16.0f;
                var11 = (1.0f - var9) * 16.0f;
                break;
            }
            case 2: {
                var10 = var7 * 16.0f;
                var11 = var9 * 16.0f;
                break;
            }
            case 3: {
                var10 = (1.0f - var7) * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 4: {
                var10 = var7 * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 5: {
                var10 = var9 * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 6: {
                var10 = (1.0f - var9) * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
        }
        final int var12 = p_178401_4_.Ý(p_178401_1_) * 7;
        p_178401_2_[var12 + 4] = Float.floatToRawIntBits(p_178401_5_.HorizonCode_Horizon_È((double)var10));
        p_178401_2_[var12 + 4 + 1] = Float.floatToRawIntBits(p_178401_5_.Â((double)var11));
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00002489";
        
        static {
            Â = new int[EnumFacing.HorizonCode_Horizon_È.values().length];
            try {
                FaceBakery.HorizonCode_Horizon_È.Â[EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.Â[EnumFacing.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.Â[EnumFacing.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                FaceBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
        }
    }
}
