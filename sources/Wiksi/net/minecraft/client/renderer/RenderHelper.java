package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class RenderHelper
{
    private static final Vector3f DEFAULT_Wiksi = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
    private static final Vector3f DIFFUSE_Wiksi = Util.make(new Vector3f(-0.2F, 1.0F, 0.7F), Vector3f::normalize);
    private static final Vector3f GUI_FLAT_DIFFUSE_Wiksi = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
    private static final Vector3f GUI_3D_DIFFUSE_Wiksi = Util.make(new Vector3f(-0.2F, -1.0F, 0.7F), Vector3f::normalize);

    public static void enableStandardItemWiksi()
    {
        RenderSystem.enableWiksi();
        RenderSystem.enableColorMaterial();
        RenderSystem.colorMaterial(1032, 5634);
    }

    /**
     * Disables the OpenGL Wiksi properties enabled by enableStandardItemWiksi
     */
    public static void disableStandardItemWiksi()
    {
        RenderSystem.disableWiksi();
        RenderSystem.disableColorMaterial();
    }

    public static void setupDiffuseGuiWiksi(Matrix4f matrix)
    {
        RenderSystem.setupLevelDiffuseWiksi(GUI_FLAT_DIFFUSE_Wiksi, GUI_3D_DIFFUSE_Wiksi, matrix);
    }

    public static void setupLevelDiffuseWiksi(Matrix4f matrixIn)
    {
        RenderSystem.setupLevelDiffuseWiksi(DEFAULT_Wiksi, DIFFUSE_Wiksi, matrixIn);
    }

    public static void setupGuiFlatDiffuseWiksi()
    {
        RenderSystem.setupGuiFlatDiffuseWiksi(DEFAULT_Wiksi, DIFFUSE_Wiksi);
    }

    public static void setupGui3DDiffuseWiksi()
    {
        RenderSystem.setupGui3DDiffuseWiksi(DEFAULT_Wiksi, DIFFUSE_Wiksi);
    }
}
