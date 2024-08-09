/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.optifine.Config;

public enum ModelVariableType {
    POS_X("tx"),
    POS_Y("ty"),
    POS_Z("tz"),
    ANGLE_X("rx"),
    ANGLE_Y("ry"),
    ANGLE_Z("rz"),
    SCALE_X("sx"),
    SCALE_Y("sy"),
    SCALE_Z("sz");

    private String name;
    public static ModelVariableType[] VALUES;

    private ModelVariableType(String string2) {
        this.name = string2;
    }

    public String getName() {
        return this.name;
    }

    public float getFloat(ModelRenderer modelRenderer) {
        switch (1.$SwitchMap$net$optifine$entity$model$anim$ModelVariableType[this.ordinal()]) {
            case 1: {
                return modelRenderer.rotationPointX;
            }
            case 2: {
                return modelRenderer.rotationPointY;
            }
            case 3: {
                return modelRenderer.rotationPointZ;
            }
            case 4: {
                return modelRenderer.rotateAngleX;
            }
            case 5: {
                return modelRenderer.rotateAngleY;
            }
            case 6: {
                return modelRenderer.rotateAngleZ;
            }
            case 7: {
                return modelRenderer.scaleX;
            }
            case 8: {
                return modelRenderer.scaleY;
            }
            case 9: {
                return modelRenderer.scaleZ;
            }
        }
        Config.warn("GetFloat not supported for: " + this);
        return 0.0f;
    }

    public void setFloat(ModelRenderer modelRenderer, float f) {
        switch (1.$SwitchMap$net$optifine$entity$model$anim$ModelVariableType[this.ordinal()]) {
            case 1: {
                modelRenderer.rotationPointX = f;
                return;
            }
            case 2: {
                modelRenderer.rotationPointY = f;
                return;
            }
            case 3: {
                modelRenderer.rotationPointZ = f;
                return;
            }
            case 4: {
                modelRenderer.rotateAngleX = f;
                return;
            }
            case 5: {
                modelRenderer.rotateAngleY = f;
                return;
            }
            case 6: {
                modelRenderer.rotateAngleZ = f;
                return;
            }
            case 7: {
                modelRenderer.scaleX = f;
                return;
            }
            case 8: {
                modelRenderer.scaleY = f;
                return;
            }
            case 9: {
                modelRenderer.scaleZ = f;
                return;
            }
        }
        Config.warn("SetFloat not supported for: " + this);
    }

    public static ModelVariableType parse(String string) {
        for (int i = 0; i < VALUES.length; ++i) {
            ModelVariableType modelVariableType = VALUES[i];
            if (!modelVariableType.getName().equals(string)) continue;
            return modelVariableType;
        }
        return null;
    }

    static {
        VALUES = ModelVariableType.values();
    }
}

