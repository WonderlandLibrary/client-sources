/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class RenderHelper {
    private static final Vector3f DEFAULT_LIGHTING = Util.make(new Vector3f(0.2f, 1.0f, -0.7f), Vector3f::normalize);
    private static final Vector3f DIFFUSE_LIGHTING = Util.make(new Vector3f(-0.2f, 1.0f, 0.7f), Vector3f::normalize);
    private static final Vector3f GUI_FLAT_DIFFUSE_LIGHTING = Util.make(new Vector3f(0.2f, 1.0f, -0.7f), Vector3f::normalize);
    private static final Vector3f GUI_3D_DIFFUSE_LIGHTING = Util.make(new Vector3f(-0.2f, -1.0f, 0.7f), Vector3f::normalize);

    public static void enableStandardItemLighting() {
        RenderSystem.enableLighting();
        RenderSystem.enableColorMaterial();
        RenderSystem.colorMaterial(1032, 5634);
    }

    public static void disableStandardItemLighting() {
        RenderSystem.disableLighting();
        RenderSystem.disableColorMaterial();
    }

    public static void setupDiffuseGuiLighting(Matrix4f matrix4f) {
        RenderSystem.setupLevelDiffuseLighting(GUI_FLAT_DIFFUSE_LIGHTING, GUI_3D_DIFFUSE_LIGHTING, matrix4f);
    }

    public static void setupLevelDiffuseLighting(Matrix4f matrix4f) {
        RenderSystem.setupLevelDiffuseLighting(DEFAULT_LIGHTING, DIFFUSE_LIGHTING, matrix4f);
    }

    public static void setupGuiFlatDiffuseLighting() {
        RenderSystem.setupGuiFlatDiffuseLighting(DEFAULT_LIGHTING, DIFFUSE_LIGHTING);
    }

    public static void setupGui3DDiffuseLighting() {
        RenderSystem.setupGui3DDiffuseLighting(DEFAULT_LIGHTING, DIFFUSE_LIGHTING);
    }
}

