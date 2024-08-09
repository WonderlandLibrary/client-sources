/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.BlockPartRotation;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.UVTransformationUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.Config;
import net.optifine.model.BlockModelUtils;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public class FaceBakery {
    private static final float SCALE_ROTATION_22_5 = 1.0f / (float)Math.cos(0.3926991f) - 1.0f;
    private static final float SCALE_ROTATION_GENERAL = 1.0f / (float)Math.cos(0.7853981852531433) - 1.0f;

    public BakedQuad bakeQuad(Vector3f vector3f, Vector3f vector3f2, BlockPartFace blockPartFace, TextureAtlasSprite textureAtlasSprite, Direction direction, IModelTransform iModelTransform, @Nullable BlockPartRotation blockPartRotation, boolean bl, ResourceLocation resourceLocation) {
        BlockFaceUV blockFaceUV = blockPartFace.blockFaceUV;
        if (iModelTransform.isUvLock()) {
            blockFaceUV = FaceBakery.updateFaceUV(blockPartFace.blockFaceUV, direction, iModelTransform.getRotation(), resourceLocation);
        }
        float[] fArray = new float[blockFaceUV.uvs.length];
        System.arraycopy(blockFaceUV.uvs, 0, fArray, 0, fArray.length);
        float f = textureAtlasSprite.getUvShrinkRatio();
        float f2 = (blockFaceUV.uvs[0] + blockFaceUV.uvs[0] + blockFaceUV.uvs[2] + blockFaceUV.uvs[2]) / 4.0f;
        float f3 = (blockFaceUV.uvs[1] + blockFaceUV.uvs[1] + blockFaceUV.uvs[3] + blockFaceUV.uvs[3]) / 4.0f;
        blockFaceUV.uvs[0] = MathHelper.lerp(f, blockFaceUV.uvs[0], f2);
        blockFaceUV.uvs[2] = MathHelper.lerp(f, blockFaceUV.uvs[2], f2);
        blockFaceUV.uvs[1] = MathHelper.lerp(f, blockFaceUV.uvs[1], f3);
        blockFaceUV.uvs[3] = MathHelper.lerp(f, blockFaceUV.uvs[3], f3);
        boolean bl2 = Reflector.ForgeHooksClient_fillNormal.exists() ? false : bl;
        int[] nArray = this.makeQuadVertexData(blockFaceUV, textureAtlasSprite, direction, this.getPositionsDiv16(vector3f, vector3f2), iModelTransform.getRotation(), blockPartRotation, bl2);
        Direction direction2 = FaceBakery.getFacingFromVertexData(nArray);
        System.arraycopy(fArray, 0, blockFaceUV.uvs, 0, fArray.length);
        if (blockPartRotation == null) {
            this.applyFacing(nArray, direction2);
        }
        if (Reflector.ForgeHooksClient_fillNormal.exists()) {
            ReflectorForge.fillNormal(nArray, direction2);
            return new BakedQuad(nArray, blockPartFace.tintIndex, direction2, textureAtlasSprite, bl);
        }
        return new BakedQuad(nArray, blockPartFace.tintIndex, direction2, textureAtlasSprite, bl);
    }

    public static BlockFaceUV updateFaceUV(BlockFaceUV blockFaceUV, Direction direction, TransformationMatrix transformationMatrix, ResourceLocation resourceLocation) {
        float f;
        float f2;
        float f3;
        float f4;
        Matrix4f matrix4f = UVTransformationUtil.getUVLockTransform(transformationMatrix, direction, () -> FaceBakery.lambda$updateFaceUV$0(resourceLocation)).getMatrix();
        float f5 = blockFaceUV.getVertexU(blockFaceUV.getVertexRotatedRev(0));
        float f6 = blockFaceUV.getVertexV(blockFaceUV.getVertexRotatedRev(0));
        Vector4f vector4f = new Vector4f(f5 / 16.0f, f6 / 16.0f, 0.0f, 1.0f);
        vector4f.transform(matrix4f);
        float f7 = 16.0f * vector4f.getX();
        float f8 = 16.0f * vector4f.getY();
        float f9 = blockFaceUV.getVertexU(blockFaceUV.getVertexRotatedRev(2));
        float f10 = blockFaceUV.getVertexV(blockFaceUV.getVertexRotatedRev(2));
        Vector4f vector4f2 = new Vector4f(f9 / 16.0f, f10 / 16.0f, 0.0f, 1.0f);
        vector4f2.transform(matrix4f);
        float f11 = 16.0f * vector4f2.getX();
        float f12 = 16.0f * vector4f2.getY();
        if (Math.signum(f9 - f5) == Math.signum(f11 - f7)) {
            f4 = f7;
            f3 = f11;
        } else {
            f4 = f11;
            f3 = f7;
        }
        if (Math.signum(f10 - f6) == Math.signum(f12 - f8)) {
            f2 = f8;
            f = f12;
        } else {
            f2 = f12;
            f = f8;
        }
        float f13 = (float)Math.toRadians(blockFaceUV.rotation);
        Vector3f vector3f = new Vector3f(MathHelper.cos(f13), MathHelper.sin(f13), 0.0f);
        Matrix3f matrix3f = new Matrix3f(matrix4f);
        vector3f.transform(matrix3f);
        int n = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2(vector3f.getY(), vector3f.getX())) / 90.0)) * 90, 360);
        return new BlockFaceUV(new float[]{f4, f2, f3, f}, n);
    }

    private int[] makeQuadVertexData(BlockFaceUV blockFaceUV, TextureAtlasSprite textureAtlasSprite, Direction direction, float[] fArray, TransformationMatrix transformationMatrix, @Nullable BlockPartRotation blockPartRotation, boolean bl) {
        int n = Config.isShaders() ? DefaultVertexFormats.BLOCK_SHADERS_SIZE : DefaultVertexFormats.BLOCK_VANILLA_SIZE;
        int[] nArray = new int[n];
        for (int i = 0; i < 4; ++i) {
            this.fillVertexData(nArray, i, direction, blockFaceUV, fArray, textureAtlasSprite, transformationMatrix, blockPartRotation, bl);
        }
        return nArray;
    }

    private float[] getPositionsDiv16(Vector3f vector3f, Vector3f vector3f2) {
        float[] fArray = new float[Direction.values().length];
        fArray[FaceDirection.Constants.WEST_INDEX] = vector3f.getX() / 16.0f;
        fArray[FaceDirection.Constants.DOWN_INDEX] = vector3f.getY() / 16.0f;
        fArray[FaceDirection.Constants.NORTH_INDEX] = vector3f.getZ() / 16.0f;
        fArray[FaceDirection.Constants.EAST_INDEX] = vector3f2.getX() / 16.0f;
        fArray[FaceDirection.Constants.UP_INDEX] = vector3f2.getY() / 16.0f;
        fArray[FaceDirection.Constants.SOUTH_INDEX] = vector3f2.getZ() / 16.0f;
        return fArray;
    }

    private void fillVertexData(int[] nArray, int n, Direction direction, BlockFaceUV blockFaceUV, float[] fArray, TextureAtlasSprite textureAtlasSprite, TransformationMatrix transformationMatrix, @Nullable BlockPartRotation blockPartRotation, boolean bl) {
        FaceDirection.VertexInformation vertexInformation = FaceDirection.getFacing(direction).getVertexInformation(n);
        Vector3f vector3f = new Vector3f(fArray[vertexInformation.xIndex], fArray[vertexInformation.yIndex], fArray[vertexInformation.zIndex]);
        this.rotatePart(vector3f, blockPartRotation);
        this.rotateVertex(vector3f, transformationMatrix);
        BlockModelUtils.snapVertexPosition(vector3f);
        this.fillVertexData(nArray, n, vector3f, textureAtlasSprite, blockFaceUV);
    }

    private void fillVertexData(int[] nArray, int n, Vector3f vector3f, TextureAtlasSprite textureAtlasSprite, BlockFaceUV blockFaceUV) {
        int n2 = nArray.length / 4;
        int n3 = n * n2;
        nArray[n3] = Float.floatToRawIntBits(vector3f.getX());
        nArray[n3 + 1] = Float.floatToRawIntBits(vector3f.getY());
        nArray[n3 + 2] = Float.floatToRawIntBits(vector3f.getZ());
        nArray[n3 + 3] = -1;
        nArray[n3 + 4] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(blockFaceUV.getVertexU(n)));
        nArray[n3 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(blockFaceUV.getVertexV(n)));
    }

    private void rotatePart(Vector3f vector3f, @Nullable BlockPartRotation blockPartRotation) {
        if (blockPartRotation != null) {
            Vector3f vector3f2;
            Vector3f vector3f3 = switch (1.$SwitchMap$net$minecraft$util$Direction$Axis[blockPartRotation.axis.ordinal()]) {
                case 1 -> {
                    vector3f2 = new Vector3f(1.0f, 0.0f, 0.0f);
                    yield new Vector3f(0.0f, 1.0f, 1.0f);
                }
                case 2 -> {
                    vector3f2 = new Vector3f(0.0f, 1.0f, 0.0f);
                    yield new Vector3f(1.0f, 0.0f, 1.0f);
                }
                case 3 -> {
                    vector3f2 = new Vector3f(0.0f, 0.0f, 1.0f);
                    yield new Vector3f(1.0f, 1.0f, 0.0f);
                }
                default -> throw new IllegalArgumentException("There are only 3 axes");
            };
            Quaternion quaternion = new Quaternion(vector3f2, blockPartRotation.angle, true);
            if (blockPartRotation.rescale) {
                if (Math.abs(blockPartRotation.angle) == 22.5f) {
                    vector3f3.mul(SCALE_ROTATION_22_5);
                } else {
                    vector3f3.mul(SCALE_ROTATION_GENERAL);
                }
                vector3f3.add(1.0f, 1.0f, 1.0f);
            } else {
                vector3f3.set(1.0f, 1.0f, 1.0f);
            }
            this.rotateScale(vector3f, blockPartRotation.origin.copy(), new Matrix4f(quaternion), vector3f3);
        }
    }

    public void rotateVertex(Vector3f vector3f, TransformationMatrix transformationMatrix) {
        if (transformationMatrix != TransformationMatrix.identity()) {
            this.rotateScale(vector3f, new Vector3f(0.5f, 0.5f, 0.5f), transformationMatrix.getMatrix(), new Vector3f(1.0f, 1.0f, 1.0f));
        }
    }

    private void rotateScale(Vector3f vector3f, Vector3f vector3f2, Matrix4f matrix4f, Vector3f vector3f3) {
        Vector4f vector4f = new Vector4f(vector3f.getX() - vector3f2.getX(), vector3f.getY() - vector3f2.getY(), vector3f.getZ() - vector3f2.getZ(), 1.0f);
        vector4f.transform(matrix4f);
        vector4f.scale(vector3f3);
        vector3f.set(vector4f.getX() + vector3f2.getX(), vector4f.getY() + vector3f2.getY(), vector4f.getZ() + vector3f2.getZ());
    }

    public static Direction getFacingFromVertexData(int[] nArray) {
        int n = nArray.length / 4;
        int n2 = n * 2;
        Vector3f vector3f = new Vector3f(Float.intBitsToFloat(nArray[0]), Float.intBitsToFloat(nArray[1]), Float.intBitsToFloat(nArray[2]));
        Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(nArray[n]), Float.intBitsToFloat(nArray[n + 1]), Float.intBitsToFloat(nArray[n + 2]));
        Vector3f vector3f3 = new Vector3f(Float.intBitsToFloat(nArray[n2]), Float.intBitsToFloat(nArray[n2 + 1]), Float.intBitsToFloat(nArray[n2 + 2]));
        Vector3f vector3f4 = vector3f.copy();
        vector3f4.sub(vector3f2);
        Vector3f vector3f5 = vector3f3.copy();
        vector3f5.sub(vector3f2);
        Vector3f vector3f6 = vector3f5.copy();
        vector3f6.cross(vector3f4);
        vector3f6.normalize();
        Direction direction = null;
        float f = 0.0f;
        for (Direction direction2 : Direction.values()) {
            Vector3i vector3i = direction2.getDirectionVec();
            Vector3f vector3f7 = new Vector3f(vector3i.getX(), vector3i.getY(), vector3i.getZ());
            float f2 = vector3f6.dot(vector3f7);
            if (!(f2 >= 0.0f) || !(f2 > f)) continue;
            f = f2;
            direction = direction2;
        }
        return direction == null ? Direction.UP : direction;
    }

    private void applyFacing(int[] nArray, Direction direction) {
        float f;
        int n;
        int[] nArray2 = new int[nArray.length];
        System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
        float[] fArray = new float[Direction.values().length];
        fArray[FaceDirection.Constants.WEST_INDEX] = 999.0f;
        fArray[FaceDirection.Constants.DOWN_INDEX] = 999.0f;
        fArray[FaceDirection.Constants.NORTH_INDEX] = 999.0f;
        fArray[FaceDirection.Constants.EAST_INDEX] = -999.0f;
        fArray[FaceDirection.Constants.UP_INDEX] = -999.0f;
        fArray[FaceDirection.Constants.SOUTH_INDEX] = -999.0f;
        int n2 = nArray.length / 4;
        for (int i = 0; i < 4; ++i) {
            n = n2 * i;
            float f2 = Float.intBitsToFloat(nArray2[n]);
            float f3 = Float.intBitsToFloat(nArray2[n + 1]);
            f = Float.intBitsToFloat(nArray2[n + 2]);
            if (f2 < fArray[FaceDirection.Constants.WEST_INDEX]) {
                fArray[FaceDirection.Constants.WEST_INDEX] = f2;
            }
            if (f3 < fArray[FaceDirection.Constants.DOWN_INDEX]) {
                fArray[FaceDirection.Constants.DOWN_INDEX] = f3;
            }
            if (f < fArray[FaceDirection.Constants.NORTH_INDEX]) {
                fArray[FaceDirection.Constants.NORTH_INDEX] = f;
            }
            if (f2 > fArray[FaceDirection.Constants.EAST_INDEX]) {
                fArray[FaceDirection.Constants.EAST_INDEX] = f2;
            }
            if (f3 > fArray[FaceDirection.Constants.UP_INDEX]) {
                fArray[FaceDirection.Constants.UP_INDEX] = f3;
            }
            if (!(f > fArray[FaceDirection.Constants.SOUTH_INDEX])) continue;
            fArray[FaceDirection.Constants.SOUTH_INDEX] = f;
        }
        FaceDirection faceDirection = FaceDirection.getFacing(direction);
        for (n = 0; n < 4; ++n) {
            int n3 = n2 * n;
            FaceDirection.VertexInformation vertexInformation = faceDirection.getVertexInformation(n);
            f = fArray[vertexInformation.xIndex];
            float f4 = fArray[vertexInformation.yIndex];
            float f5 = fArray[vertexInformation.zIndex];
            nArray[n3] = Float.floatToRawIntBits(f);
            nArray[n3 + 1] = Float.floatToRawIntBits(f4);
            nArray[n3 + 2] = Float.floatToRawIntBits(f5);
            for (int i = 0; i < 4; ++i) {
                int n4 = n2 * i;
                float f6 = Float.intBitsToFloat(nArray2[n4]);
                float f7 = Float.intBitsToFloat(nArray2[n4 + 1]);
                float f8 = Float.intBitsToFloat(nArray2[n4 + 2]);
                if (!MathHelper.epsilonEquals(f, f6) || !MathHelper.epsilonEquals(f4, f7) || !MathHelper.epsilonEquals(f5, f8)) continue;
                nArray[n3 + 4] = nArray2[n4 + 4];
                nArray[n3 + 4 + 1] = nArray2[n4 + 4 + 1];
            }
        }
    }

    private static String lambda$updateFaceUV$0(ResourceLocation resourceLocation) {
        return "Unable to resolve UVLock for model: " + resourceLocation;
    }
}

