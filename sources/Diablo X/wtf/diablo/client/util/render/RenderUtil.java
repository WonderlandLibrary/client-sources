package wtf.diablo.client.util.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.shader.shaders.OutlineRoundedRectShaderImpl;
import wtf.diablo.client.shader.shaders.RoundedRectangleShader;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public final class RenderUtil {
    private final static Minecraft mc = Minecraft.getMinecraft();
    private static final RoundedRectangleShader ROUNDED_RECT_SHADER = new RoundedRectangleShader();
    private static final OutlineRoundedRectShaderImpl OUTLINE_ROUNDED_RECT_SHADER = new OutlineRoundedRectShaderImpl();

    private RenderUtil() {}

    public static void drawRect(final int x, final int y, final int width, final int height, final int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    public static void drawRectWidth(float posX, float posY, float width, float height, int color) {
        Gui.drawRect(posX, posY, posX + width, posY + height, color);
    }

    public static void drawBoundFaceWithLayer(float posX, float posY, int width, int height) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        Gui.drawScaledCustomSizeModalRect((int) posX, (int) posY, 8F, 8F, 8, 8, width, height, 64F, 64F);
        Gui.drawScaledCustomSizeModalRect((int) (posX - 1), (int) (posY - 1), 40F, 8F, 8, 8, width + 2, height + 2, 64F, 64F);
    }


    public static void drawBorderRect(final int x, final int y, final int width, final int height, final int thickness, final int color){
        Gui.drawRect(x, y, x + width, y + thickness, color);
        Gui.drawRect(x, y, x + thickness, y + height, color);
        Gui.drawRect(x + width - thickness, y, x + width, y + height, color);
        Gui.drawRect(x, y + height - thickness, x + width, y + height, color);
    }

    public static void drawRectOutlineWidth(float x, float y, float width, float height, int colour, float thickness) {
        Gui.drawRect(x - thickness, y - thickness, x + width + thickness, y, colour);
        Gui.drawRect(x - thickness, y, x, y + height, colour);
        Gui.drawRect(x + width + thickness, y + height + thickness, x - thickness, y + height, colour);
        Gui.drawRect(x + width + thickness, y + height, x + width, y, colour);
    }

    public static void drawBorderRect(final int x, final int y, final int width, final int height, final int thickness, final int inColor, final int color){
        Gui.drawRect(x, y, x + width, y + thickness, color);
        Gui.drawRect(x, y, x + thickness, y + height, color);
        Gui.drawRect(x + width - thickness, y, x + width, y + height, color);
        Gui.drawRect(x, y + height - thickness, x + width, y + height, color);
        Gui.drawRect(x + thickness, y + thickness, x + width - thickness, y + height - thickness, inColor);
    }

    public static void drawBorderRect(final int x, final int y, final int width, final int height, final int thickness, final int color, final int borderColor, final BorderDirectionEnum... borderDirections) {
        final List<BorderDirectionEnum> borderDirectionList = Arrays.asList(borderDirections);

        for(final BorderDirectionEnum direction : borderDirectionList) {
            switch (direction) {
                case TOP:
                    drawRect(x, y, width, thickness, borderColor);
                    break;
                case BOTTOM:
                    drawRect(x, y + height - thickness, width, thickness, borderColor);
                    break;
                case LEFT:
                    if(borderDirectionList.contains(BorderDirectionEnum.TOP) && borderDirectionList.contains(BorderDirectionEnum.BOTTOM))
                        drawRect(x, y, thickness, height, borderColor);
                    else
                        drawRect(x, y + thickness, thickness, height - thickness * 2, borderColor);
                    break;
                case RIGHT:
                    if(borderDirectionList.contains(BorderDirectionEnum.TOP) && borderDirectionList.contains(BorderDirectionEnum.BOTTOM))
                        drawRect(x + width - thickness, y, thickness, height, borderColor);
                    else
                        drawRect(x + width - thickness, y + thickness, thickness, height - thickness * 2, borderColor);
                    break;
            }
        }
        drawRect(x + thickness, y + thickness, width - thickness * 2, height - thickness * 2, color);
    }

    public static boolean isHovered(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return mouseX > x && mouseX < x2 && mouseY > y && mouseY < y2;
    }

    public static void prepareScissorBox(double x, double y, double x2, double y2) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x * (float) factor), (int) (((float) scale.getScaledHeight() - y2) * (float) factor), (int) ((x2 - x) * (float) factor), (int) ((y2 - y) * (float) factor));
    }

    public static void drawCircle(final Render3DEvent event, final double radius, final Color color)
    {

        double ticks = event.getPartialTicks();
        final double x = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * ticks
                - mc.getRenderManager().viewerPosX;
        final double y = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * ticks
                - mc.getRenderManager().viewerPosY;
        final double z = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * ticks
                - mc.getRenderManager().viewerPosZ;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.5f);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        for (int i = 0; i < 360; i++)
        {
            ColorUtil.setColor(color);
            GL11.glVertex3f((float) (x + StrictMath.sin(i * StrictMath.PI / 180.0) * radius), (float) (y + 0.1),
                    (float) (z + StrictMath.cos(i * StrictMath.PI / 180.0) * radius));
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void renderItemStack(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, y);
        mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

    public static void drawImage(double x, double y, int width, int height, ResourceLocation rec, float opacity) {
        GlStateManager.pushMatrix();
        GlStateManager.color(opacity, opacity, opacity);
        Minecraft.getMinecraft().getTextureManager().bindTexture(rec);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, 0, 0, width, height, width, height);
        GlStateManager.popMatrix();
    }

    public static void drawImage(double x, double y, int width, int height, ResourceLocation rec) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(rec);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, 0, 0, width, height, width, height);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawLineToPosition(double x, double y, double z, float size, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        double renderPosXDelta = x - mc.getRenderManager().viewerPosX;
        double renderPosYDelta = y - mc.getRenderManager().viewerPosY;
        double renderPosZDelta = z - mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(size);
        float f = (float) (color >> 16 & 0xFF) / 255.0f;
        float f2 = (float) (color >> 8 & 0xFF) / 255.0f;
        float f3 = (float) (color & 0xFF) / 255.0f;
        float f4 = (float) (color >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(f, f2, f3, f4);
        GL11.glLoadIdentity();
        boolean previousState = mc.gameSettings.viewBobbing;
        mc.gameSettings.viewBobbing = false;
        mc.entityRenderer.orientCamera(mc.getTimer().renderPartialTicks);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
        GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta);
        GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta);
        GL11.glEnd();
        mc.gameSettings.viewBobbing = previousState;
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }



    public static void drawLineToPosition(final double x, final double y, final double z, final double x2, final double y2, final double z2, float size, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        double renderPosXDelta = x2 - x;
        double renderPosYDelta = y2 - y;
        double renderPosZDelta = z2 - z;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(size);
        float f = (float) (color >> 16 & 0xFF) / 255.0f;
        float f2 = (float) (color >> 8 & 0xFF) / 255.0f;
        float f3 = (float) (color & 0xFF) / 255.0f;
        float f4 = (float) (color >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(f, f2, f3, f4);
        GL11.glLoadIdentity();
        boolean previousState = mc.gameSettings.viewBobbing;
        mc.gameSettings.viewBobbing = false;
        mc.entityRenderer.orientCamera(mc.getTimer().renderPartialTicks);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
        GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta);
        GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta);
        GL11.glEnd();
        mc.gameSettings.viewBobbing = previousState;
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawOutlineRect(double x, double y, double x2, double y2, double scale, int color) {
        Gui.drawRect(x, y, x + scale, y2, color);
        Gui.drawRect(x, y, x2, y + scale, color);
        Gui.drawRect(x2, y, x2 + scale, y2, color);
        Gui.drawRect(x, y2, x2 + scale, y2 + scale, color);
    }


    public enum BorderDirectionEnum {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public static void drawSkeleton(Render3DEvent event, EntityPlayer e, ModelPlayer m, double width, Color color) {
        final float[][] entPos = getEntityPoints(m);
        final double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks()) - mc.getRenderManager().getRenderPosX();
        final double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks()) - mc.getRenderManager().getRenderPosY();
        final double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks()) - mc.getRenderManager().getRenderPosZ();

        if (!e.isInvisible()) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth((float) width);
            GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
            GL11.glTranslated(x, y, z);
            float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
            float yOff = e.isSneaking() ? 0.6F : 0.75F;
            GL11.glRotatef(-xOff, 0.0F, 1.0F, 0.0F);
            GL11.glTranslated(0.0D, 0.0D, e.isSneaking() ? -0.235D : 0.0D);
            drawLinesToHitpoint(HitPointEnum.RIGHT_LEG, entPos, yOff);
            drawLinesToHitpoint(HitPointEnum.LEFT_LEG, entPos, yOff);
            GL11.glTranslated(0.0D, 0.0D, e.isSneaking() ? 0.25D : 0.0D);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, e.isSneaking() ? -0.05D : 0.0D, e.isSneaking() ? -0.01725D : 0.0D);
            drawLinesToHitpoint(HitPointEnum.LEFT_ARM, entPos, yOff + 0.55);
            drawLinesToHitpoint(HitPointEnum.RIGHT_ARM, entPos, yOff + 0.55);
            GL11.glRotatef(xOff - e.rotationYawHead, 0.0F, 1.0F, 0.0F);
            drawLinesToHitpoint(HitPointEnum.HEAD, entPos, yOff + 0.55);
            GL11.glPopMatrix();
            GL11.glRotatef(e.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(0.0D, e.isSneaking() ? -0.16175D : 0.0D, e.isSneaking() ? -0.48025D : 0.0D);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, yOff, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
            GL11.glVertex3d(0.125D, 0.0D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, yOff, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, 0.55D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, yOff + 0.55D, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.35D, 0.0D, 0.0D);
            GL11.glVertex3d(0.35D, 0.0D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    private static void drawLinesToHitpoint(HitPointEnum pointEnum, float[][] entityPoints, double yOff) {
        GL11.glPushMatrix();
        double xOff = 0;
        double vertexY = 0;
        switch (pointEnum.getPoint()) {
            case 6:
                xOff = 0;
                vertexY = 0;
                break;
            case 4:
                xOff = 0.125D;
                vertexY = (-yOff);
                break;
            case 3:
                xOff = -0.125D;
                vertexY = (-yOff);
                break;
            case 2:
                xOff = 0.35D;
                vertexY = -0.5;
                break;
            case 1:
                xOff = -0.35D;
                vertexY = -0.5;
                break;
            case 0:
                vertexY = 0.3D;
                break;
        }

        final float[] positions = entityPoints[pointEnum.getPoint()];
        GL11.glTranslated(xOff, yOff, 0.0D);

        for (final int points : pointEnum.getPoints()) {
            if (positions[points] != 0.0F) {
                float angle = positions[points] * 57.3F;
                if ((pointEnum == HitPointEnum.LEFT_ARM || pointEnum == HitPointEnum.RIGHT_ARM) && points == 2)
                    angle = -positions[points] * 57.3F;

                GL11.glRotatef(angle, points == 0 ? 1.0F : 0.0F, points == 1 ? 1.0F : 0.0F, points == 2 ? 1.0F : 0.0F);
            }
        }

        GL11.glBegin(3);
        GL11.glVertex3d(0, 0.0D, 0.0D);
        GL11.glVertex3d(0, vertexY, 0.0D);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    /**
     * Draws a arrow.
     *
     * @param x The X coordinate of the arrow.
     * @param y The Y coordinate of the arrow.
     * @param lineWidth The width of the arrow.
     * @param length The length of the arrow.
     */
    public static void drawArrow(
            final double x,
            final double y,
            final int lineWidth,
            final double length
    ) {
        // enable blend and blend func source alpha and one minus source alpha
        GL11.glEnable(GL11.GL_BLEND);
        // disable gl texture 2d
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        // enable line smoothness
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        // push matrix
        GlStateManager.pushMatrix();
        // set the line width to the line width param
        GL11.glLineWidth(lineWidth);
        // bind the color to white
        GL11.glColor4f(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), Color.WHITE.getAlpha());
        // begin drawing lines
        GL11.glBegin(GL11.GL_LINE_STRIP);
        // left side of arrow
        GL11.glVertex2d(x, y);
        // bends arrow down
        GL11.glVertex2d(x + 3, y + length);
        // right side of arrow
        GL11.glVertex2d(x + 6, y);
        // end line strip.
        GL11.glEnd();
        // pop matrix
        GlStateManager.popMatrix();
        // disable texture 2d
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // disable line smooth
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        // disable blend
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawRoundedRectangle(final double x, final double y, final double width, final double height, final double radius, final int color) {
        ROUNDED_RECT_SHADER.render(x, y, width, height, radius, color);
    }

    public static void drawRoundedRectangle(final double x, final double y, final double width, final double height, final double topLeft, final double topRight, final double bottomRight, final double bottomLeft, final int color) {
        ROUNDED_RECT_SHADER.render(x, y, width, height, topLeft, topRight, bottomRight, bottomLeft, color);
    }

    public static void drawOutlineRoundedRectangle(final double x, final double y, final double width, final double height, final double radius, final float outlineWidth, final int outlineColor,  final int color) {
        OUTLINE_ROUNDED_RECT_SHADER.render(x, y, width, height, radius, outlineWidth, outlineColor, color);
    }

    private static float[][] getEntityPoints(ModelPlayer model) {
        return new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}};
    }

    public static void drawBlockESP(final BlockPos pos, final Color color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glColor4d(color.getRed() / 255D, color.getGreen() / 255D, color.getBlue() / 255D, color.getAlpha() / 255D);


        GlStateManager.depthMask(false);


        final Block b = mc.theWorld.getBlockState(pos).getBlock();

        b.setBlockBoundsBasedOnState(mc.theWorld, pos);
        double var8 = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)mc.getTimer().renderPartialTicks;
        double var10 = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)mc.getTimer().renderPartialTicks;
        double var12 = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks;

        final AxisAlignedBB axis = b.getSelectedBoundingBox(mc.theWorld, pos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-var8, -var10, -var12);

        RenderGlobal.func_181563_a(axis, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        GL11.glPopMatrix();
    }

    private enum HitPointEnum {
        HEAD(0, new int[]{0}),
        BACK(5, new int[]{0}),
        PELVIS(6, new int[]{0}),
        SHOULDER(7, new int[]{0, 1}),
        LEFT_ARM(2, new int[]{0, 1, 2}),
        RIGHT_ARM(1, new int[]{0, 1, 2}),
        LEFT_LEG(3, new int[]{0, 1, 2}),
        RIGHT_LEG(4, new int[]{0, 1, 2});

        private final int point;
        private final int[] points;

        HitPointEnum(int point, int[] points) {
            this.point = point;
            this.points = points;
        }

        public int getPoint() {
            return point;
        }

        public int[] getPoints() {
            return points;
        }
    }
}
