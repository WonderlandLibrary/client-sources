/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Orientation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;

public enum ModelRotation implements IModelTransform
{
    X0_Y0(0, 0),
    X0_Y90(0, 90),
    X0_Y180(0, 180),
    X0_Y270(0, 270),
    X90_Y0(90, 0),
    X90_Y90(90, 90),
    X90_Y180(90, 180),
    X90_Y270(90, 270),
    X180_Y0(180, 0),
    X180_Y90(180, 90),
    X180_Y180(180, 180),
    X180_Y270(180, 270),
    X270_Y0(270, 0),
    X270_Y90(270, 90),
    X270_Y180(270, 180),
    X270_Y270(270, 270);

    private static final Map<Integer, ModelRotation> MAP_ROTATIONS;
    private final TransformationMatrix transformation;
    private final Orientation orientation;
    private final int combinedXY;

    private static int combineXY(int n, int n2) {
        return n * 360 + n2;
    }

    private ModelRotation(int n2, int n3) {
        int n4;
        this.combinedXY = ModelRotation.combineXY(n2, n3);
        Quaternion quaternion = new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), -n3, true);
        quaternion.multiply(new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), -n2, true));
        Orientation orientation = Orientation.IDENTITY;
        for (n4 = 0; n4 < n3; n4 += 90) {
            orientation = orientation.func_235527_a_(Orientation.ROT_90_Y_NEG);
        }
        for (n4 = 0; n4 < n2; n4 += 90) {
            orientation = orientation.func_235527_a_(Orientation.ROT_90_X_NEG);
        }
        this.transformation = new TransformationMatrix(null, quaternion, null, null);
        this.orientation = orientation;
    }

    @Override
    public TransformationMatrix getRotation() {
        return this.transformation;
    }

    public static ModelRotation getModelRotation(int n, int n2) {
        return MAP_ROTATIONS.get(ModelRotation.combineXY(MathHelper.normalizeAngle(n, 360), MathHelper.normalizeAngle(n2, 360)));
    }

    private static ModelRotation lambda$static$1(ModelRotation modelRotation) {
        return modelRotation;
    }

    private static Integer lambda$static$0(ModelRotation modelRotation) {
        return modelRotation.combinedXY;
    }

    static {
        MAP_ROTATIONS = Arrays.stream(ModelRotation.values()).collect(Collectors.toMap(ModelRotation::lambda$static$0, ModelRotation::lambda$static$1));
    }
}

