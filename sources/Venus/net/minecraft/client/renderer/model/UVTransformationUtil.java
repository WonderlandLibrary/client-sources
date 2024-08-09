/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UVTransformationUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final EnumMap<Direction, TransformationMatrix> TRANSFORM_LOCAL_TO_GLOBAL = Util.make(Maps.newEnumMap(Direction.class), UVTransformationUtil::lambda$static$0);
    public static final EnumMap<Direction, TransformationMatrix> TRANSFORM_GLOBAL_TO_LOCAL = Util.make(Maps.newEnumMap(Direction.class), UVTransformationUtil::lambda$static$1);

    public static TransformationMatrix blockCenterToCorner(TransformationMatrix transformationMatrix) {
        Matrix4f matrix4f = Matrix4f.makeTranslate(0.5f, 0.5f, 0.5f);
        matrix4f.mul(transformationMatrix.getMatrix());
        matrix4f.mul(Matrix4f.makeTranslate(-0.5f, -0.5f, -0.5f));
        return new TransformationMatrix(matrix4f);
    }

    public static TransformationMatrix getUVLockTransform(TransformationMatrix transformationMatrix, Direction direction, Supplier<String> supplier) {
        Direction direction2 = Direction.rotateFace(transformationMatrix.getMatrix(), direction);
        TransformationMatrix transformationMatrix2 = transformationMatrix.inverseVanilla();
        if (transformationMatrix2 == null) {
            LOGGER.warn(supplier.get());
            return new TransformationMatrix(null, null, new Vector3f(0.0f, 0.0f, 0.0f), null);
        }
        TransformationMatrix transformationMatrix3 = TRANSFORM_GLOBAL_TO_LOCAL.get(direction).composeVanilla(transformationMatrix2).composeVanilla(TRANSFORM_LOCAL_TO_GLOBAL.get(direction2));
        return UVTransformationUtil.blockCenterToCorner(transformationMatrix3);
    }

    private static void lambda$static$1(EnumMap enumMap) {
        for (Direction direction : Direction.values()) {
            enumMap.put(direction, TRANSFORM_LOCAL_TO_GLOBAL.get(direction).inverseVanilla());
        }
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(Direction.SOUTH, TransformationMatrix.identity());
        enumMap.put(Direction.EAST, new TransformationMatrix(null, new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), 90.0f, true), null, null));
        enumMap.put(Direction.WEST, new TransformationMatrix(null, new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, true), null, null));
        enumMap.put(Direction.NORTH, new TransformationMatrix(null, new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), 180.0f, true), null, null));
        enumMap.put(Direction.UP, new TransformationMatrix(null, new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), -90.0f, true), null, null));
        enumMap.put(Direction.DOWN, new TransformationMatrix(null, new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), 90.0f, true), null, null));
    }
}

