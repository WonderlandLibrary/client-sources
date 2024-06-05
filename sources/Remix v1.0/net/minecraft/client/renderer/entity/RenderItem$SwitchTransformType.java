package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.block.model.*;

static final class SwitchTransformType
{
    static final int[] field_178640_a;
    
    static {
        field_178640_a = new int[ItemCameraTransforms.TransformType.values().length];
        try {
            SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.NONE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.THIRD_PERSON.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.FIRST_PERSON.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.HEAD.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.GUI.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
