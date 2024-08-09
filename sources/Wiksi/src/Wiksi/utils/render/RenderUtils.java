package src.Wiksi.utils.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import src.Wiksi.Wiksi;
import src.Wiksi.utils.shader.ShaderHandler;
import src.Wiksi.utils.shader.ShaderUtil;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.utils.math.Interpolator;
import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import java.util.ArrayList;
import java.util.List;

import static src.Wiksi.utils.client.IMinecraft.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ZERO;

@UtilityClass
public class RenderUtils implements IRenderAccess {
    public static ShaderHandler shaderMainMenu = new ShaderHandler("shaderMainMenu");
    public void start() {
        GlStateManager.clearCurrentColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture();
        GlStateManager.disableCull();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableDepthTest();
    }
    public void stop() {
        GlStateManager.enableDepthTest();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableCull();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
        GlStateManager.clearCurrentColor();
    }
    public void bindTexture(int texture) {
        GlStateManager.bindTexture(texture);
    }

    public void bindTexture(ResourceLocation texture) {
        mc.getTextureManager().bindTexture(texture);
    }

    public void resetColor() {
        GlStateManager.clearCurrentColor();
    }
    public Vector3d interpolate(Entity entity, float partialTicks) {
        double posX = Interpolator.lerp(entity.lastTickPosX, entity.getPosX(), partialTicks);
        double posY = Interpolator.lerp(entity.lastTickPosY, entity.getPosY(), partialTicks);
        double posZ = Interpolator.lerp(entity.lastTickPosZ, entity.getPosZ(), partialTicks);
        return new Vector3d(posX, posY, posZ);
    }

    public net.minecraft.util.math.vector.Vector3d interpolateM(Entity entity, float partialTicks) {
        double posX = Interpolator.lerp(entity.lastTickPosX, entity.getPosX(), partialTicks);
        double posY = Interpolator.lerp(entity.lastTickPosY, entity.getPosY(), partialTicks);
        double posZ = Interpolator.lerp(entity.lastTickPosZ, entity.getPosZ(), partialTicks);
        return new net.minecraft.util.math.vector.Vector3d(posX, posY, posZ);
    }
    public static void drawImage(MatrixStack matrix, ResourceLocation image, float x, float y, float width, float height, int color) {
        RenderSystem.enableDepthTest();
        mc.getTextureManager().bindTexture(image);
        RectUtil.drawRect(matrix, x, y, x + width, y + height, color);
        RenderSystem.disableDepthTest();
        GlStateManager.clearCurrentColor();
    }
    public static void drawImage(MatrixStack stack, double x, double y, double z, double width, double height, int color1, int color2, int color3, int color4) {
        boolean blend = glIsEnabled(GL_BLEND);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
        glShadeModel(GL_SMOOTH);
        glAlphaFunc(GL_GREATER, 0);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        buffer.pos(stack.getLast().getMatrix(), (float) x, (float) (y + height), (float) (z)).color((color1 >> 16) & 0xFF, (color1 >> 8) & 0xFF, color1 & 0xFF, color1 >>> 24).tex(0, 1 - 0.01f).lightmap(0, 240).endVertex();
        buffer.pos(stack.getLast().getMatrix(), (float) (x + width), (float) (y + height), (float) (z)).color((color2 >> 16) & 0xFF, (color2 >> 8) & 0xFF, color2 & 0xFF, color2 >>> 24).tex(1, 1 - 0.01f).lightmap(0, 240).endVertex();
        buffer.pos(stack.getLast().getMatrix(), (float) (x + width), (float) y, (float) z).color((color3 >> 16) & 0xFF, (color3 >> 8) & 0xFF, color3 & 0xFF, color3 >>> 24).tex(1, 0).lightmap(0, 240).endVertex();
        buffer.pos(stack.getLast().getMatrix(), (float) x, (float) y, (float) z).color((color4 >> 16) & 0xFF, (color4 >> 8) & 0xFF, color4 & 0xFF, color4 >>> 24).tex(0, 0).lightmap(0, 240).endVertex();
        tessellator.draw();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        glShadeModel(GL_FLAT);
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ZERO);
        if (!blend)
            GlStateManager.disableBlend();
    }

    public static void drawImage(MatrixStack stack, ResourceLocation image, double x, double y, double z, double width, double height, int color1, int color2, int color3, int color4) {
        mc.getTextureManager().bindTexture(image);
        drawImage(stack, x, y, z, width, height, color1, color2, color3, color4);
    }

    public void renderClientRect(MatrixStack matrix, float x, float y, float width, float height, boolean expand, double expanded) {
        renderClientRect(matrix, x, y, width, height, expand, expanded, 128);
    }

    public void renderClientRect(MatrixStack matrix, float x, float y, float width, float height, boolean expand, double expanded, int alphaPC) {

        int color1 = HUD.getColor(0, alphaPC / 255f);
        int color2 = HUD.getColor(90, alphaPC / 255f);
        int color3 = HUD.getColor(180, alphaPC / 255f);
        int color4 = HUD.getColor(270, alphaPC / 255f);
        boolean bloom = true;
        float round = Wiksi.getInstance().round();
        float shadow = Wiksi.getInstance().shadow();
        int zeroC1 = ColorUtils.multAlpha(color1, 0.F), zeroC2 = ColorUtils.multAlpha(color2, 0.F), zeroC3 = ColorUtils.multAlpha(color3, 0.F), zeroC4 = ColorUtils.multAlpha(color4, 0.F);
        int halfC1 = ColorUtils.multAlpha(color1, .5F), halfC2 = ColorUtils.multAlpha(color2, .5F), halfC3 = ColorUtils.multAlpha(color3, .5F), halfC4 = ColorUtils.multAlpha(color4, .5F);
        int overY1C = ColorUtils.overCol(color1, color2), overY2C = ColorUtils.overCol(color4, color3), overX1C = ColorUtils.overCol(color1, color4), overX2C = ColorUtils.overCol(color2, color3);
        RectUtil.drawRoundedRectShadowed(matrix, x, y, x + width, y + height, round, shadow, halfC1, halfC2, halfC3, halfC4, bloom, true, false, true);
        float dark = 0.35F;
        RectUtil.drawRoundedRectShadowed(matrix, x, y, x + width, y + height, round, .5F, ColorUtils.multDark(color1, dark), ColorUtils.multDark(color2, dark), ColorUtils.multDark(color3, dark), ColorUtils.multDark(color4, dark), false, false, true, true);
        List<RectUtil.Vec2fColored> list = new ArrayList<>();
        if (expand) {
            list.add(new RectUtil.Vec2fColored(x + 2, y + (float) expanded, zeroC1));
            list.add(new RectUtil.Vec2fColored(x + width / 2F, y + (float) expanded, overY1C));
            list.add(new RectUtil.Vec2fColored(x + width - 2, y + (float) expanded, zeroC2));
            GL12.glLineWidth(2);
            RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
            GL12.glPointSize(1);
        }
        //lines
        float lineOff = 0;
        GL12.glLineWidth(2);
        //up
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + round, y + lineOff, zeroC1));
        list.add(new RectUtil.Vec2fColored(x + width / 2F, y + lineOff, overY1C));
        list.add(new RectUtil.Vec2fColored(x + width - round, y + lineOff, zeroC2));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        //down
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + round, y + height - lineOff, zeroC4));
        list.add(new RectUtil.Vec2fColored(x + width / 2F, y + height - lineOff, overY2C));
        list.add(new RectUtil.Vec2fColored(x + width - round, y + height - lineOff, zeroC3));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        //left
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + lineOff, y + round, zeroC1));
        list.add(new RectUtil.Vec2fColored(x + lineOff, y + height / 2F, overX1C));
        list.add(new RectUtil.Vec2fColored(x + lineOff, y + height - round, zeroC4));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        //right
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + width - lineOff, y + round, zeroC2));
        list.add(new RectUtil.Vec2fColored(x + width - lineOff, y + height / 2F, overX2C));
        list.add(new RectUtil.Vec2fColored(x + width - lineOff, y + height - round, zeroC3));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);

        GL12.glLineWidth(1);
    }


}
