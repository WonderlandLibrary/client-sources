package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

import static com.mojang.blaze3d.platform.GlStateManager.GL_QUADS;
import static com.mojang.blaze3d.platform.GlStateManager.disableBlend;
import static org.lwjgl.opengl.GL11C.GL_ONE;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;

public class RenderHelper
{
    private static final Vector3f DEFAULT_LIGHTING = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
    private static final Vector3f DIFFUSE_LIGHTING = Util.make(new Vector3f(-0.2F, 1.0F, 0.7F), Vector3f::normalize);
    private static final Vector3f GUI_FLAT_DIFFUSE_LIGHTING = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
    private static final Vector3f GUI_3D_DIFFUSE_LIGHTING = Util.make(new Vector3f(-0.2F, -1.0F, 0.7F), Vector3f::normalize);

    public static void enableStandardItemLighting()
    {
        RenderSystem.enableLighting();
        RenderSystem.enableColorMaterial();
        RenderSystem.colorMaterial(1032, 5634);
    }

    /**
     * Disables the OpenGL lighting properties enabled by enableStandardItemLighting
     */
    public static void disableStandardItemLighting()
    {
        RenderSystem.disableLighting();
        RenderSystem.disableColorMaterial();
    }

    public static void setupDiffuseGuiLighting(Matrix4f matrix)
    {
        RenderSystem.setupLevelDiffuseLighting(GUI_FLAT_DIFFUSE_LIGHTING, GUI_3D_DIFFUSE_LIGHTING, matrix);
    }

    public static void setupLevelDiffuseLighting(Matrix4f matrixIn)
    {
        RenderSystem.setupLevelDiffuseLighting(DEFAULT_LIGHTING, DIFFUSE_LIGHTING, matrixIn);
    }

    public static void setupGuiFlatDiffuseLighting()
    {
        RenderSystem.setupGuiFlatDiffuseLighting(DEFAULT_LIGHTING, DIFFUSE_LIGHTING);
    }

    public static void setupGui3DDiffuseLighting()
    {
        RenderSystem.setupGui3DDiffuseLighting(DEFAULT_LIGHTING, DIFFUSE_LIGHTING);
    }

    public static void drawImage1(MatrixStack stack, ResourceLocation image, double x, double y, double z, double width, double height, int color1, int color2, int color3, int color4) {
        Minecraft minecraft = Minecraft.getInstance();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        minecraft.getTextureManager().bindTexture(image);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        bufferBuilder.pos(stack.getLast().getMatrix(), (float) x, (float) (y + height), (float) (z)).color((color1 >> 16) & 0xFF, (color1 >> 8) & 0xFF, color1 & 0xFF, color1 >>> 24).tex(0, 1 - 0.01f).lightmap(0, 240).endVertex();
        bufferBuilder.pos(stack.getLast().getMatrix(), (float) (x + width), (float) (y + height), (float) (z)).color((color2 >> 16) & 0xFF, (color2 >> 8) & 0xFF, color2 & 0xFF, color2 >>> 24).tex(1, 1 - 0.01f).lightmap(0, 240).endVertex();
        bufferBuilder.pos(stack.getLast().getMatrix(), (float) (x + width), (float) y, (float) z).color((color3 >> 16) & 0xFF, (color3 >> 8) & 0xFF, color3 & 0xFF, color3 >>> 24).tex(1, 0).lightmap(0, 240).endVertex();
        bufferBuilder.pos(stack.getLast().getMatrix(), (float) x, (float) y, (float) z).color((color4 >> 16) & 0xFF, (color4 >> 8) & 0xFF, color4 & 0xFF, color4 >>> 24).tex(0, 0).lightmap(0, 240).endVertex();

        tessellator.draw();
        disableBlend();
    }
}
