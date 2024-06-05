package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.vector.*;
import net.minecraft.util.*;

public class FaceBakery
{
    private static final float field_178418_a;
    private static final float field_178417_b;
    private static final String __OBFID = "CL_00002490";
    
    public BakedQuad makeBakedQuad(final Vector3f posFrom, final Vector3f posTo, final BlockPartFace face, final TextureAtlasSprite sprite, final EnumFacing facing, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final int[] aint = this.makeQuadVertexData(face, sprite, facing, this.getPositionsDiv16(posFrom, posTo), modelRotationIn, partRotation, uvLocked, shade);
        final EnumFacing enumfacing = getFacingFromVertexData(aint);
        if (uvLocked) {
            this.func_178409_a(aint, enumfacing, face.blockFaceUV, sprite);
        }
        if (partRotation == null) {
            this.func_178408_a(aint, enumfacing);
        }
        return new BakedQuad(aint, face.tintIndex, enumfacing, sprite);
    }
    
    private int[] makeQuadVertexData(final BlockPartFace partFace, final TextureAtlasSprite sprite, final EnumFacing facing, final float[] p_178405_4_, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final int[] aint = new int[28];
        for (int i = 0; i < 4; ++i) {
            this.fillVertexData(aint, i, facing, partFace, p_178405_4_, sprite, modelRotationIn, partRotation, uvLocked, shade);
        }
        return aint;
    }
    
    private int getFaceShadeColor(final EnumFacing facing) {
        final float f = this.getFaceBrightness(facing);
        final int i = MathHelper.clamp_int((int)(f * 255.0f), 0, 255);
        return 0xFF000000 | i << 16 | i << 8 | i;
    }
    
    private float getFaceBrightness(final EnumFacing facing) {
        switch (FaceBakery$1.field_178400_a[facing.ordinal()]) {
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
    
    private float[] getPositionsDiv16(final Vector3f pos1, final Vector3f pos2) {
        final float[] afloat = new float[EnumFacing.values().length];
        afloat[EnumFaceDirection.Constants.WEST_INDEX] = pos1.x / 16.0f;
        afloat[EnumFaceDirection.Constants.DOWN_INDEX] = pos1.y / 16.0f;
        afloat[EnumFaceDirection.Constants.NORTH_INDEX] = pos1.z / 16.0f;
        afloat[EnumFaceDirection.Constants.EAST_INDEX] = pos2.x / 16.0f;
        afloat[EnumFaceDirection.Constants.UP_INDEX] = pos2.y / 16.0f;
        afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = pos2.z / 16.0f;
        return afloat;
    }
    
    private void fillVertexData(final int[] faceData, final int vertexIndex, final EnumFacing facing, final BlockPartFace partFace, final float[] p_178402_5_, final TextureAtlasSprite sprite, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final EnumFacing enumfacing = modelRotationIn.rotateFace(facing);
        final int i = shade ? this.getFaceShadeColor(enumfacing) : -1;
        final EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = EnumFaceDirection.getFacing(facing).func_179025_a(vertexIndex);
        final Vector3f vector3f = new Vector3f(p_178402_5_[enumfacedirection$vertexinformation.field_179184_a], p_178402_5_[enumfacedirection$vertexinformation.field_179182_b], p_178402_5_[enumfacedirection$vertexinformation.field_179183_c]);
        this.func_178407_a(vector3f, partRotation);
        final int j = this.rotateVertex(vector3f, facing, vertexIndex, modelRotationIn, uvLocked);
        this.storeVertexData(faceData, j, vertexIndex, vector3f, i, sprite, partFace.blockFaceUV);
    }
    
    private void storeVertexData(final int[] faceData, final int storeIndex, final int vertexIndex, final Vector3f position, final int shadeColor, final TextureAtlasSprite sprite, final BlockFaceUV faceUV) {
        final int i = storeIndex * 7;
        faceData[i] = Float.floatToRawIntBits(position.x);
        faceData[i + 1] = Float.floatToRawIntBits(position.y);
        faceData[i + 2] = Float.floatToRawIntBits(position.z);
        faceData[i + 3] = shadeColor;
        faceData[i + 4] = Float.floatToRawIntBits(sprite.getInterpolatedU(faceUV.func_178348_a(vertexIndex)));
        faceData[i + 4 + 1] = Float.floatToRawIntBits(sprite.getInterpolatedV(faceUV.func_178346_b(vertexIndex)));
    }
    
    private void func_178407_a(final Vector3f p_178407_1_, final BlockPartRotation partRotation) {
        if (partRotation != null) {
            final Matrix4f matrix4f = this.getMatrixIdentity();
            final Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f);
            switch (FaceBakery$1.field_178399_b[partRotation.axis.ordinal()]) {
                case 1: {
                    Matrix4f.rotate(partRotation.angle * 0.017453292f, new Vector3f(1.0f, 0.0f, 0.0f), matrix4f, matrix4f);
                    vector3f.set(0.0f, 1.0f, 1.0f);
                    break;
                }
                case 2: {
                    Matrix4f.rotate(partRotation.angle * 0.017453292f, new Vector3f(0.0f, 1.0f, 0.0f), matrix4f, matrix4f);
                    vector3f.set(1.0f, 0.0f, 1.0f);
                    break;
                }
                case 3: {
                    Matrix4f.rotate(partRotation.angle * 0.017453292f, new Vector3f(0.0f, 0.0f, 1.0f), matrix4f, matrix4f);
                    vector3f.set(1.0f, 1.0f, 0.0f);
                    break;
                }
            }
            if (partRotation.rescale) {
                if (Math.abs(partRotation.angle) == 22.5f) {
                    vector3f.scale(FaceBakery.field_178418_a);
                }
                else {
                    vector3f.scale(FaceBakery.field_178417_b);
                }
                Vector3f.add(vector3f, new Vector3f(1.0f, 1.0f, 1.0f), vector3f);
            }
            else {
                vector3f.set(1.0f, 1.0f, 1.0f);
            }
            this.rotateScale(p_178407_1_, new Vector3f(partRotation.origin), matrix4f, vector3f);
        }
    }
    
    public int rotateVertex(final Vector3f position, final EnumFacing facing, final int vertexIndex, final ModelRotation modelRotationIn, final boolean uvLocked) {
        if (modelRotationIn == ModelRotation.X0_Y0) {
            return vertexIndex;
        }
        this.rotateScale(position, new Vector3f(0.5f, 0.5f, 0.5f), modelRotationIn.getMatrix4d(), new Vector3f(1.0f, 1.0f, 1.0f));
        return modelRotationIn.rotateVertex(facing, vertexIndex);
    }
    
    private void rotateScale(final Vector3f position, final Vector3f rotationOrigin, final Matrix4f rotationMatrix, final Vector3f scale) {
        final Vector4f vector4f = new Vector4f(position.x - rotationOrigin.x, position.y - rotationOrigin.y, position.z - rotationOrigin.z, 1.0f);
        Matrix4f.transform(rotationMatrix, vector4f, vector4f);
        final Vector4f vector4f2 = vector4f;
        vector4f2.x *= scale.x;
        final Vector4f vector4f3 = vector4f;
        vector4f3.y *= scale.y;
        final Vector4f vector4f4 = vector4f;
        vector4f4.z *= scale.z;
        position.set(vector4f.x + rotationOrigin.x, vector4f.y + rotationOrigin.y, vector4f.z + rotationOrigin.z);
    }
    
    private Matrix4f getMatrixIdentity() {
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        return matrix4f;
    }
    
    public static EnumFacing getFacingFromVertexData(final int[] faceData) {
        final Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
        final Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[7]), Float.intBitsToFloat(faceData[8]), Float.intBitsToFloat(faceData[9]));
        final Vector3f vector3f3 = new Vector3f(Float.intBitsToFloat(faceData[14]), Float.intBitsToFloat(faceData[15]), Float.intBitsToFloat(faceData[16]));
        final Vector3f vector3f4 = new Vector3f();
        final Vector3f vector3f5 = new Vector3f();
        final Vector3f vector3f6 = new Vector3f();
        Vector3f.sub(vector3f, vector3f2, vector3f4);
        Vector3f.sub(vector3f3, vector3f2, vector3f5);
        Vector3f.cross(vector3f5, vector3f4, vector3f6);
        final float f = (float)Math.sqrt(vector3f6.x * vector3f6.x + vector3f6.y * vector3f6.y + vector3f6.z * vector3f6.z);
        final Vector3f vector3f8 = vector3f6;
        vector3f8.x /= f;
        final Vector3f vector3f9 = vector3f6;
        vector3f9.y /= f;
        final Vector3f vector3f10 = vector3f6;
        vector3f10.z /= f;
        EnumFacing enumfacing = null;
        float f2 = 0.0f;
        for (final EnumFacing enumfacing2 : EnumFacing.values()) {
            final Vec3i vec3i = enumfacing2.getDirectionVec();
            final Vector3f vector3f7 = new Vector3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
            final float f3 = Vector3f.dot(vector3f6, vector3f7);
            if (f3 >= 0.0f && f3 > f2) {
                f2 = f3;
                enumfacing = enumfacing2;
            }
        }
        if (f2 < 0.719f) {
            if (enumfacing != EnumFacing.EAST && enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.SOUTH) {
                enumfacing = EnumFacing.UP;
            }
            else {
                enumfacing = EnumFacing.NORTH;
            }
        }
        return (enumfacing == null) ? EnumFacing.UP : enumfacing;
    }
    
    public void func_178409_a(final int[] p_178409_1_, final EnumFacing facing, final BlockFaceUV p_178409_3_, final TextureAtlasSprite p_178409_4_) {
        for (int i = 0; i < 4; ++i) {
            this.func_178401_a(i, p_178409_1_, facing, p_178409_3_, p_178409_4_);
        }
    }
    
    private void func_178408_a(final int[] p_178408_1_, final EnumFacing p_178408_2_) {
        final int[] aint = new int[p_178408_1_.length];
        System.arraycopy(p_178408_1_, 0, aint, 0, p_178408_1_.length);
        final float[] afloat = new float[EnumFacing.values().length];
        afloat[EnumFaceDirection.Constants.WEST_INDEX] = 999.0f;
        afloat[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0f;
        afloat[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0f;
        afloat[EnumFaceDirection.Constants.EAST_INDEX] = -999.0f;
        afloat[EnumFaceDirection.Constants.UP_INDEX] = -999.0f;
        afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0f;
        for (int j = 0; j < 4; ++j) {
            final int i = 7 * j;
            final float f1 = Float.intBitsToFloat(aint[i]);
            final float f2 = Float.intBitsToFloat(aint[i + 1]);
            final float f3 = Float.intBitsToFloat(aint[i + 2]);
            if (f1 < afloat[EnumFaceDirection.Constants.WEST_INDEX]) {
                afloat[EnumFaceDirection.Constants.WEST_INDEX] = f1;
            }
            if (f2 < afloat[EnumFaceDirection.Constants.DOWN_INDEX]) {
                afloat[EnumFaceDirection.Constants.DOWN_INDEX] = f2;
            }
            if (f3 < afloat[EnumFaceDirection.Constants.NORTH_INDEX]) {
                afloat[EnumFaceDirection.Constants.NORTH_INDEX] = f3;
            }
            if (f1 > afloat[EnumFaceDirection.Constants.EAST_INDEX]) {
                afloat[EnumFaceDirection.Constants.EAST_INDEX] = f1;
            }
            if (f2 > afloat[EnumFaceDirection.Constants.UP_INDEX]) {
                afloat[EnumFaceDirection.Constants.UP_INDEX] = f2;
            }
            if (f3 > afloat[EnumFaceDirection.Constants.SOUTH_INDEX]) {
                afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = f3;
            }
        }
        final EnumFaceDirection enumfacedirection = EnumFaceDirection.getFacing(p_178408_2_);
        for (int i2 = 0; i2 < 4; ++i2) {
            final int j2 = 7 * i2;
            final EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = enumfacedirection.func_179025_a(i2);
            final float f4 = afloat[enumfacedirection$vertexinformation.field_179184_a];
            final float f5 = afloat[enumfacedirection$vertexinformation.field_179182_b];
            final float f6 = afloat[enumfacedirection$vertexinformation.field_179183_c];
            p_178408_1_[j2] = Float.floatToRawIntBits(f4);
            p_178408_1_[j2 + 1] = Float.floatToRawIntBits(f5);
            p_178408_1_[j2 + 2] = Float.floatToRawIntBits(f6);
            for (int k = 0; k < 4; ++k) {
                final int l = 7 * k;
                final float f7 = Float.intBitsToFloat(aint[l]);
                final float f8 = Float.intBitsToFloat(aint[l + 1]);
                final float f9 = Float.intBitsToFloat(aint[l + 2]);
                if (MathHelper.epsilonEquals(f4, f7) && MathHelper.epsilonEquals(f5, f8) && MathHelper.epsilonEquals(f6, f9)) {
                    p_178408_1_[j2 + 4] = aint[l + 4];
                    p_178408_1_[j2 + 4 + 1] = aint[l + 4 + 1];
                }
            }
        }
    }
    
    private void func_178401_a(final int p_178401_1_, final int[] p_178401_2_, final EnumFacing facing, final BlockFaceUV p_178401_4_, final TextureAtlasSprite p_178401_5_) {
        final int i = 7 * p_178401_1_;
        float f = Float.intBitsToFloat(p_178401_2_[i]);
        float f2 = Float.intBitsToFloat(p_178401_2_[i + 1]);
        float f3 = Float.intBitsToFloat(p_178401_2_[i + 2]);
        if (f < -0.1f || f >= 1.1f) {
            f -= MathHelper.floor_float(f);
        }
        if (f2 < -0.1f || f2 >= 1.1f) {
            f2 -= MathHelper.floor_float(f2);
        }
        if (f3 < -0.1f || f3 >= 1.1f) {
            f3 -= MathHelper.floor_float(f3);
        }
        float f4 = 0.0f;
        float f5 = 0.0f;
        switch (FaceBakery$1.field_178400_a[facing.ordinal()]) {
            case 1: {
                f4 = f * 16.0f;
                f5 = (1.0f - f3) * 16.0f;
                break;
            }
            case 2: {
                f4 = f * 16.0f;
                f5 = f3 * 16.0f;
                break;
            }
            case 3: {
                f4 = (1.0f - f) * 16.0f;
                f5 = (1.0f - f2) * 16.0f;
                break;
            }
            case 4: {
                f4 = f * 16.0f;
                f5 = (1.0f - f2) * 16.0f;
                break;
            }
            case 5: {
                f4 = f3 * 16.0f;
                f5 = (1.0f - f2) * 16.0f;
                break;
            }
            case 6: {
                f4 = (1.0f - f3) * 16.0f;
                f5 = (1.0f - f2) * 16.0f;
                break;
            }
        }
        final int j = p_178401_4_.func_178345_c(p_178401_1_) * 7;
        p_178401_2_[j + 4] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedU(f4));
        p_178401_2_[j + 4 + 1] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedV(f5));
    }
    
    static {
        field_178418_a = 1.0f / (float)Math.cos(0.39269909262657166) - 1.0f;
        field_178417_b = 1.0f / (float)Math.cos(0.7853981633974483) - 1.0f;
    }
    
    static final class FaceBakery$1
    {
        static final int[] field_178400_a;
        static final int[] field_178399_b;
        private static final String __OBFID = "CL_00002489";
        
        static {
            field_178399_b = new int[EnumFacing.Axis.values().length];
            try {
                FaceBakery$1.field_178399_b[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                FaceBakery$1.field_178399_b[EnumFacing.Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                FaceBakery$1.field_178399_b[EnumFacing.Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_178400_a = new int[EnumFacing.values().length];
            try {
                FaceBakery$1.field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
        }
    }
}
