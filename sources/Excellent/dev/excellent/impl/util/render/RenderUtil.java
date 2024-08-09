package dev.excellent.impl.util.render;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.friend.Friend;
import dev.excellent.client.module.impl.misc.NameProtect;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@UtilityClass
public class RenderUtil implements IRenderAccess {
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

    public Vector2d project2D(net.minecraft.util.math.vector.Vector3d vec) {
        return project2D(vec.x, vec.y, vec.z);
    }

    public Vector2d project2D(double x, double y, double z) {
        if (mc.getRenderManager().info == null) return new Vector2d();
        net.minecraft.util.math.vector.Vector3d cameraPosition = mc.getRenderManager().info.getProjectedView();
        Quaternion cameraRotation = mc.getRenderManager().getCameraOrientation().copy();
        cameraRotation.conjugate();

        Vector3f relativePosition = new Vector3f((float) (cameraPosition.x - x), (float) (cameraPosition.y - y), (float) (cameraPosition.z - z));
        relativePosition.transform(cameraRotation);

        if (mc.gameSettings.viewBobbing) {
            Entity renderViewEntity = mc.getRenderViewEntity();
            if (renderViewEntity instanceof PlayerEntity playerEntity) {
                float walkedDistance = playerEntity.distanceWalkedModified;

                float deltaDistance = walkedDistance - playerEntity.prevDistanceWalkedModified;
                float interpolatedDistance = -(walkedDistance + deltaDistance * mc.getRenderPartialTicks());
                float cameraYaw = MathHelper.lerp(mc.getRenderPartialTicks(), playerEntity.prevCameraYaw, playerEntity.cameraYaw);

                Quaternion bobQuaternionX = new Quaternion(Vector3f.XP, Math.abs(MathHelper.cos(interpolatedDistance * (float) Math.PI - 0.2F) * cameraYaw) * 5.0F, true);
                bobQuaternionX.conjugate();
                relativePosition.transform(bobQuaternionX);

                Quaternion bobQuaternionZ = new Quaternion(Vector3f.ZP, MathHelper.sin(interpolatedDistance * (float) Math.PI) * cameraYaw * 3.0F, true);
                bobQuaternionZ.conjugate();
                relativePosition.transform(bobQuaternionZ);

                Vector3f bobTranslation = new Vector3f((MathHelper.sin(interpolatedDistance * (float) Math.PI) * cameraYaw * 0.5F), (-Math.abs(MathHelper.cos(interpolatedDistance * (float) Math.PI) * cameraYaw)), 0.0f);
                bobTranslation.setY(-bobTranslation.getY());
                relativePosition.add(bobTranslation);
            }
        }

        double fieldOfView = (float) mc.gameRenderer.getFOVModifier(mc.getRenderManager().info, mc.getRenderPartialTicks(), true);

        float halfHeight = (float) mc.getMainWindow().getScaledHeight() / 2.0F;
        float scaleFactor = halfHeight / (relativePosition.getZ() * (float) Math.tan(Math.toRadians(fieldOfView / 2.0F)));

        if (relativePosition.getZ() < 0.0F) {
            return new Vector2d(-relativePosition.getX() * scaleFactor + (float) (mc.getMainWindow().getScaledWidth() / 2), (float) (mc.getMainWindow().getScaledHeight() / 2) - relativePosition.getY() * scaleFactor);
        }
        return null;
    }


    public static void drawImage(MatrixStack matrix, ResourceLocation image, float x, float y, float width, float height, int color) {
        RenderSystem.enableDepthTest();
        mc.getTextureManager().bindTexture(image);
        RectUtil.drawRect(matrix, x, y, x + width, y + height, color);
        RenderSystem.disableDepthTest();
        GlStateManager.clearCurrentColor();
    }

    public void allocRect(MatrixStack matrix, float xStart, float yStart, float xEnd, float yEnd) {
        Matrix4f matrix4f = matrix.getLast().getMatrix();
        BUFFER.begin(GlStateManager.GL_QUADS, DefaultVertexFormats.POSITION);
        BUFFER.pos(matrix4f, xStart, yEnd, 0.0F).endVertex();
        BUFFER.pos(matrix4f, xEnd, yEnd, 0.0F).endVertex();
        BUFFER.pos(matrix4f, xEnd, yStart, 0.0F).endVertex();
        BUFFER.pos(matrix4f, xStart, yStart, 0.0F).endVertex();
        TESSELLATOR.draw();
    }

    public static void drawImage(MatrixStack stack, double x, double y, double z, double width, double height, int color1, int color2, int color3, int color4) {
        boolean blend = glIsEnabled(GL_BLEND);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
        glShadeModel(GL_SMOOTH);
        glAlphaFunc(GL_GREATER, 0);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        BUFFER.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        BUFFER.pos(stack.getLast().getMatrix(), (float) x, (float) (y + height), (float) (z)).color((color1 >> 16) & 0xFF, (color1 >> 8) & 0xFF, color1 & 0xFF, color1 >>> 24).tex(0, 1 - 0.01f).lightmap(0, 240).endVertex();
        BUFFER.pos(stack.getLast().getMatrix(), (float) (x + width), (float) (y + height), (float) (z)).color((color2 >> 16) & 0xFF, (color2 >> 8) & 0xFF, color2 & 0xFF, color2 >>> 24).tex(1, 1 - 0.01f).lightmap(0, 240).endVertex();
        BUFFER.pos(stack.getLast().getMatrix(), (float) (x + width), (float) y, (float) z).color((color3 >> 16) & 0xFF, (color3 >> 8) & 0xFF, color3 & 0xFF, color3 >>> 24).tex(1, 0).lightmap(0, 240).endVertex();
        BUFFER.pos(stack.getLast().getMatrix(), (float) x, (float) y, (float) z).color((color4 >> 16) & 0xFF, (color4 >> 8) & 0xFF, color4 & 0xFF, color4 >>> 24).tex(0, 0).lightmap(0, 240).endVertex();
        TESSELLATOR.draw();
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

        int color1 = excellent.getThemeManager().getTheme().getClientColor(0, alphaPC / 255F);
        int color2 = excellent.getThemeManager().getTheme().getClientColor(90, alphaPC / 255F);
        int color3 = excellent.getThemeManager().getTheme().getClientColor(180, alphaPC / 255F);
        int color4 = excellent.getThemeManager().getTheme().getClientColor(270, alphaPC / 255F);
        boolean bloom = true;
        float round = excellent.getThemeManager().getTheme().round();
        float shadow = excellent.getThemeManager().getTheme().shadow();
        int zeroC1 = ColorUtil.multAlpha(color1, 0.F), zeroC2 = ColorUtil.multAlpha(color2, 0.F), zeroC3 = ColorUtil.multAlpha(color3, 0.F), zeroC4 = ColorUtil.multAlpha(color4, 0.F);
        int halfC1 = ColorUtil.multAlpha(color1, .5F), halfC2 = ColorUtil.multAlpha(color2, .5F), halfC3 = ColorUtil.multAlpha(color3, .5F), halfC4 = ColorUtil.multAlpha(color4, .5F);
        int overY1C = ColorUtil.overCol(color1, color2), overY2C = ColorUtil.overCol(color4, color3), overX1C = ColorUtil.overCol(color1, color4), overX2C = ColorUtil.overCol(color2, color3);
        RectUtil.drawRoundedRectShadowed(matrix, x, y, x + width, y + height, round, shadow, halfC1, halfC2, halfC3, halfC4, bloom, true, false, true);
        float dark = 0.35F;
        RectUtil.drawRoundedRectShadowed(matrix, x, y, x + width, y + height, round, .5F, ColorUtil.multDark(color1, dark), ColorUtil.multDark(color2, dark), ColorUtil.multDark(color3, dark), ColorUtil.multDark(color4, dark), false, false, true, true);
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

    public void drawITextComponent(MatrixStack matrix, ITextComponent textComponent, Font font, double x, double y, int color, boolean shadow) {
        float offset = 0;
        ITextComponent newComponent = new StringTextComponent("");

        for (ITextComponent component : textComponent.getSiblings()) {
            String replacedComponent = TextFormatting.getTextWithoutFormattingCodes(replaceSymbols(component.getString()));
            for (Friend friend : Excellent.getInst().getFriendManager()) {
                if (NameProtect.singleton.get().isEnabled() && component.getString().contains(friend.getName())) {
                    component = newComponent.deepCopy().appendString(component.getString().replaceAll(friend.getName(), NameProtect.singleton.get().name.getValue()));
                }
            }
            if (NameProtect.singleton.get().isEnabled() && component.getString().contains(mc.player.getNameClear())) {
                component = newComponent.deepCopy().appendString(component.getString().replaceAll(mc.player.getNameClear(), NameProtect.singleton.get().name.getValue()));
            }
            if (!component.getSiblings().isEmpty()) {
                float charoff = 0;
                for (ITextComponent charComponent : component.getSiblings()) {
                    String replacedCharComponent = TextFormatting.getTextWithoutFormattingCodes(replaceSymbols(charComponent.getString()));

                    if (shadow)
                        font.drawShadow(matrix, replacedCharComponent, x + offset + charoff, y, charComponent.getStyle().getColor() != null ? charComponent.getStyle().getColor().getColor() : color);
                    else
                        font.draw(matrix, replacedCharComponent, x + offset + charoff, y, charComponent.getStyle().getColor() != null ? charComponent.getStyle().getColor().getColor() : color);
                    charoff += font.getWidth(replacedCharComponent);
                }
            } else {
                if (shadow)
                    font.drawShadow(matrix, replacedComponent, x + offset, y, component.getStyle().getColor() != null ? component.getStyle().getColor().getColor() : color);
                else
                    font.draw(matrix, replacedComponent, x + offset, y, component.getStyle().getColor() != null ? component.getStyle().getColor().getColor() : color);
            }
            offset += font.getWidth(replacedComponent);
        }
    }

    public float getComponentWidth(ITextComponent textComponent, Font font) {
        float offset = 0;
        for (ITextComponent component : textComponent.getSiblings()) {
            String replacedComponent = replaceSymbols(component.getString());
            offset += font.getWidth(replacedComponent);
        }
        return offset;
    }

    private String replaceSymbols(String string) {
        return string
                .replaceAll("⚡", "")
                .replaceAll("ᴀ", "a")
                .replaceAll("ʙ", "b")
                .replaceAll("ᴄ", "c")
                .replaceAll("ᴅ", "d")
                .replaceAll("ᴇ", "e")
                .replaceAll("ғ", "f")
                .replaceAll("ɢ", "g")
                .replaceAll("ʜ", "h")
                .replaceAll("ɪ", "i")
                .replaceAll("ᴊ", "j")
                .replaceAll("ᴋ", "k")
                .replaceAll("ʟ", "l")
                .replaceAll("ᴍ", "m")
                .replaceAll("ɴ", "n")
                .replaceAll("ᴏ", "o")
                .replaceAll("ᴘ", "p")
                .replaceAll("ǫ", "q")
                .replaceAll("ʀ", "r")
                .replaceAll("s", "s")
                .replaceAll("ᴛ", "t")
                .replaceAll("ᴜ", "u")
                .replaceAll("ᴠ", "v")
                .replaceAll("ᴡ", "w")
                .replaceAll("x", "x")
                .replaceAll("ʏ", "y")
                .replaceAll("ᴢ", "z");
    }

    public void roundedRect(MatrixStack matrixStack, float x, float y, float width, float height, float radius, int color) {
        IShader.ROUNDED.draw(matrixStack, x, y, width, height, radius, color);
    }

    @UtilityClass
    public static class Render3D {

        public void drawBlockBox(MatrixStack matrix, BlockPos blockPos, int color) {
            drawBox(matrix, new AxisAlignedBB(blockPos), color);
        }

        public void drawBox(MatrixStack matrix, AxisAlignedBB bb, int color) {
            ActiveRenderInfo camera = mc.gameRenderer.getActiveRenderInfo();
            net.minecraft.util.math.vector.Vector3d camPos = camera.getProjectedView();
            bb = bb.offset(camPos.mul(-1, -1, -1));
            Matrix4f matrix4f = matrix.getLast().getMatrix();

            matrix.push();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL_DEPTH_TEST);
            GL11.glEnable(GL_LINE_SMOOTH);
            GL11.glLineWidth(1);

            float[] rgb = ColorUtil.getRGBAf(color);
            GlStateManager.clearCurrentColor();
            GlStateManager.color4f(rgb[0], rgb[1], rgb[2], rgb[3]);

            // bottom face
            drawFace(matrix4f, (float) bb.minX, (float) bb.minY, (float) bb.minZ, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ, rgb);

            // top face
            drawFace(matrix4f, (float) bb.minX, (float) bb.maxY, (float) bb.minZ, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ, rgb);

            // vertical edges
            drawEdge(matrix4f, (float) bb.minX, (float) bb.minY, (float) bb.minZ, (float) bb.minX, (float) bb.maxY, (float) bb.minZ, rgb);
            drawEdge(matrix4f, (float) bb.maxX, (float) bb.minY, (float) bb.minZ, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ, rgb);
            drawEdge(matrix4f, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ, rgb);
            drawEdge(matrix4f, (float) bb.minX, (float) bb.minY, (float) bb.maxZ, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ, rgb);

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL_DEPTH_TEST);
            GL11.glDisable(GL_LINE_SMOOTH);
            matrix.pop();
        }

        private void drawFace(Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, float[] rgb) {
            BUFFER.begin(3, DefaultVertexFormats.POSITION);
            BUFFER.pos(matrix4f, x1, y1, z1).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            BUFFER.pos(matrix4f, x2, y1, z1).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            BUFFER.pos(matrix4f, x2, y1, z2).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            BUFFER.pos(matrix4f, x1, y1, z2).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            BUFFER.pos(matrix4f, x1, y1, z1).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            TESSELLATOR.draw();
        }

        private void drawEdge(Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, float[] rgb) {
            BUFFER.begin(3, DefaultVertexFormats.POSITION);
            BUFFER.pos(matrix4f, x1, y1, z1).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            BUFFER.pos(matrix4f, x2, y2, z2).color(rgb[0], rgb[1], rgb[2], rgb[3]).endVertex();
            TESSELLATOR.draw();
        }

        public void setupWorldRenderer(MatrixStack stack) {
            stack.push();
            glDisable(GL_LIGHTING);
            glEnable(GL_BLEND);
            GlStateManager.enableAlphaTest();
            glDisable(GL_ALPHA_TEST);
            stack.translate(-mc.getRenderManager().renderPosX(), -mc.getRenderManager().renderPosY(), -mc.getRenderManager().renderPosZ());
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_TEXTURE_2D);
        }

        public void cleanupWorldRenderer(MatrixStack stack) {
            glEnable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
            glEnable(GL_ALPHA_TEST);
            stack.translate(mc.getRenderManager().renderPosX(), mc.getRenderManager().renderPosY(), mc.getRenderManager().renderPosZ());
            stack.pop();
        }
    }

    @UtilityClass
    public static class FrameBuffer {
        public Framebuffer createFrameBuffer(Framebuffer framebuffer) {
            return createFrameBuffer(framebuffer, false);
        }

        public Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
            if (needsNewFramebuffer(framebuffer)) {
                if (framebuffer != null) {
                    framebuffer.deleteFramebuffer();
                }
                int frameBufferWidth = mc.getMainWindow().getFramebufferWidth();
                int frameBufferHeight = mc.getMainWindow().getFramebufferHeight();
                return new Framebuffer(frameBufferWidth, frameBufferHeight, depth);
            }
            return framebuffer;
        }

        public boolean needsNewFramebuffer(Framebuffer framebuffer) {
            return framebuffer == null || framebuffer.framebufferWidth != mc.getMainWindow().getFramebufferWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getFramebufferHeight();
        }
    }

}
