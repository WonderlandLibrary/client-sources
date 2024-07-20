/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class JumpCircles
extends Module {
    public static JumpCircles get;
    List<ResourceLocation> frames = new ArrayList<ResourceLocation>();
    Settings MaxTime;
    Settings Range;
    Settings Texture;
    Settings ColorMode;
    Settings PickColor1;
    Settings PickColor2;
    private static final List<JumpRenderer> circles;
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();
    private final ResourceLocation JUMP_CIRCLE = new ResourceLocation("vegaline/modules/jumpcircles/default/circle.png");
    private final ResourceLocation JUMP_KONCHAL = new ResourceLocation("vegaline/modules/jumpcircles/default/konchal.png");
    private final ResourceLocation JUMP_INSUAL = new ResourceLocation("vegaline/modules/jumpcircles/default/inusual.png");

    public JumpCircles() {
        super("JumpCircles", 0, Module.Category.RENDER);
        get = this;
        int framesCounter = 0;
        while (framesCounter < 100) {
            ResourceLocation loc = new ResourceLocation("vegaline/modules/jumpcircles/animated/animation1/circleframe_" + ++framesCounter + ".jpeg");
            this.frames.add(loc);
            mc.getTextureManager().bindTexture(loc);
        }
        this.MaxTime = new Settings("MaxTime", 2000.0f, 5000.0f, 750.0f, this);
        this.settings.add(this.MaxTime);
        this.Range = new Settings("Range", 1.5f, 3.0f, 0.25f, this);
        this.settings.add(this.Range);
        this.Texture = new Settings("Texture", "Circle", (Module)this, new String[]{"Circle", "KonchalEbal", "Inusual", "CubicalPieces"});
        this.settings.add(this.Texture);
        this.ColorMode = new Settings("ColorMode", "Client", (Module)this, new String[]{"Client", "Picker", "PickerFade", "PickerFragmental"});
        this.settings.add(this.ColorMode);
        this.PickColor1 = new Settings("PickColor1", ColorUtils.getColor(255, 80, 0), (Module)this, () -> this.ColorMode.currentMode.contains("Picker"));
        this.settings.add(this.PickColor1);
        this.PickColor2 = new Settings("PickColor2", ColorUtils.getColor(255, 142, 0), (Module)this, () -> this.ColorMode.currentMode.endsWith("Fade") || this.ColorMode.currentMode.endsWith("Fragmental"));
        this.settings.add(this.PickColor2);
    }

    public static void onEntityMove(Entity entityIn, Vec3d prev) {
        EntityPlayer base;
        if (entityIn instanceof EntityPlayer && (base = (EntityPlayer)entityIn).isEntityAlive()) {
            double motionY = entityIn.posY - prev.yCoord;
            double[] motions = new double[]{0.42f, 0.20000004768365898};
            if (MoveMeHelp.isBlockAboveHead(entityIn)) {
                motions = new double[]{0.42f, 0.20000004768365898, 0.20000004768371582, 0.07840000152587834};
            }
            boolean spawn = false;
            double[] dArray = motions;
            int n = dArray.length;
            for (int i = 0; i < n; ++i) {
                Double cur = dArray[i];
                if (!(MathUtils.getDifferenceOf(motionY, cur) < 0.001)) continue;
                spawn = true;
            }
            if (!entityIn.onGround && entityIn.onGround != entityIn.rayGround && motionY > 0.0 || spawn) {
                JumpCircles.addCircleForEntity(entityIn);
            }
            entityIn.rayGround = entityIn.onGround;
        }
    }

    private static final void addCircleForEntity(Entity entity) {
        circles.add(new JumpRenderer(JumpCircles.getVec3dFromEntity(entity).addVector(0.0, 1.0E-4, 0.0), circles.size()));
    }

    @EventTarget
    public void onRender3d(Event3D event) {
        if (circles.size() == 0) {
            return;
        }
        circles.removeIf(circle -> (double)circle.getDeltaTime() >= 1.0);
        if (circles.isEmpty()) {
            return;
        }
        boolean preBindTex = this.Texture.currentMode.equalsIgnoreCase("CubicalPieces");
        boolean fragmentalColor = this.ColorMode.currentMode.equalsIgnoreCase("PickerFragmental");
        this.setupDraw(() -> circles.forEach(circle -> this.doCircle(circle.pos, this.Range.fValue, 1.0f - circle.getDeltaTime(), circle.getIndex() * 30, !preBindTex, fragmentalColor)), !preBindTex);
    }

    private int getColor(int index, float alphaPC, boolean fragmentalColor) {
        String colorMode = this.ColorMode.currentMode;
        int color = 0;
        switch (colorMode) {
            case "Client": {
                color = ClientColors.getColor1(index, alphaPC);
                break;
            }
            case "Picker": {
                color = ColorUtils.swapAlpha(this.PickColor1.color, (float)ColorUtils.getAlphaFromColor(this.PickColor1.color) * alphaPC);
                break;
            }
            case "PickerFade": {
                color = ColorUtils.fadeColor(ColorUtils.swapAlpha(this.PickColor1.color, (float)ColorUtils.getAlphaFromColor(this.PickColor1.color) * alphaPC), ColorUtils.swapAlpha(this.PickColor2.color, (float)ColorUtils.getAlphaFromColor(this.PickColor2.color) * alphaPC), 0.3f, (int)((float)index / 0.3f / 8.0f));
                break;
            }
            case "PickerFragmental": {
                color = fragmentalColor ? ColorUtils.swapAlpha(this.PickColor2.color, (float)ColorUtils.getAlphaFromColor(this.PickColor2.color) * alphaPC) : ColorUtils.swapAlpha(this.PickColor1.color, (float)ColorUtils.getAlphaFromColor(this.PickColor1.color) * alphaPC);
            }
        }
        return color;
    }

    private void drawRandomizedQuads(float timePCMet, double w, double h, int density, int color, int delayHover, long timeOffset, float minPC, boolean setExtOfPC) {
        if (density < 1) {
            density = 1;
        }
        boolean curIndexX = false;
        boolean curIndexY = false;
        float fragW = (float)w / (float)density;
        float fragH = (float)h / (float)density;
        GL11.glDisable(3553);
        RenderUtils.glColor(color);
        int globalIndex = 0;
        float xW = 0.0f;
        long millis = Minecraft.getSystemTime() + timeOffset;
        for (int counterX = density - 1; counterX >= 0; --counterX) {
            float pcX = (float)counterX / (float)density;
            float yH = 0.0f;
            globalIndex = (int)((double)globalIndex + (double)density * Math.sqrt(density * density) / (double)((float)density / 2.0f - 1.0f));
            for (int counterY = density - 1; counterY >= 0; --counterY) {
                float pcY = (float)counterY / (float)density;
                float timePC = (float)((int)((millis + (long)(globalIndex = (int)((double)globalIndex + (double)density * Math.sqrt(density * density)))) % (long)delayHover)) / (float)delayHover;
                float f = timePC = timePC > 0.5f ? 1.0f - timePC : timePC;
                timePC = timePC > 1.0f ? 1.0f : (timePC < 0.0f ? 0.0f : timePC);
                if ((timePC = 1.0f - timePC) < minPC) continue;
                float extPC = 1.0f;
                float x1 = xW - fragW * extPC / 2.0f;
                float y1 = yH - fragH * extPC / 2.0f;
                float x2 = x1 + fragW + fragW * extPC / 2.0f;
                float y2 = y1 + fragH + fragH * extPC / 2.0f;
                if (setExtOfPC) {
                    float ast = 0.75f;
                    x1 += fragW * timePC * ast;
                    x2 -= fragW * timePC * ast;
                    y1 += fragH * timePC * ast;
                    y2 -= fragH * timePC * ast;
                }
                float rotate = MathUtils.clamp(setExtOfPC ? timePC : 1.0f - timePC, 0.0f, 1.0f) * 270.0f;
                RenderUtils.customRotatedObject2D(x1, y1, x2 - x1, y2 - y1, rotate + 270.0f * timePCMet);
                GL11.glBegin(9);
                GL11.glVertex2f(x1, y1);
                GL11.glVertex2f(x2, y1);
                GL11.glVertex2f(x2, y2);
                GL11.glVertex2f(x1, y2);
                GL11.glEnd();
                RenderUtils.customRotatedObject2D(x1, y1, x2 - x1, y2 - y1, -rotate - 270.0f * timePCMet);
                yH += fragH;
            }
            xW += fragW;
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
    }

    private void doCircle(Vec3d pos, double r, float aPC, int index, boolean doBindTex, boolean fragmentalColor) {
        float prevAPC = aPC;
        r *= (double)(1.0f - aPC);
        r *= 2.0;
        aPC = aPC > 0.5f ? 1.0f - aPC : aPC;
        float f = (aPC *= (0.5f + aPC * 0.5f) * 3.0f) > 1.0f ? 1.0f : (aPC = aPC < 0.0f ? 0.0f : aPC);
        if (prevAPC >= 0.5f) {
            aPC *= aPC;
        }
        r = r / 2.0 + r / 2.0 * (double)prevAPC;
        if (!doBindTex) {
            mc.getTextureManager().bindTexture(this.jumpTexture(index));
        }
        GL11.glPushMatrix();
        GL11.glTranslated(pos.xCoord - r / 2.0, pos.yCoord, pos.zCoord - r / 2.0);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        this.buffer.pos(0.0, 0.0).tex(0.0, 0.0).color(this.getColor(index, aPC, false)).endVertex();
        this.buffer.pos(0.0, r).tex(0.0, 1.0).color(this.getColor((int)(324.0f + (float)index), aPC, false)).endVertex();
        this.buffer.pos(r, r).tex(1.0, 1.0).color(this.getColor((int)(648.0f + (float)index), aPC, false)).endVertex();
        this.buffer.pos(r, 0.0).tex(1.0, 0.0).color(this.getColor((int)(972.0f + (float)index), aPC, false)).endVertex();
        if (fragmentalColor) {
            StencilUtil.initStencilToWrite();
            RenderUtils.customRotatedObject2D(0.0f, 0.0f, (float)r, (float)r, prevAPC * 90.0f);
            this.drawRandomizedQuads(1.0f - aPC, r, r, 11, 0, 900, index, 0.5f, true);
            RenderUtils.customRotatedObject2D(0.0f, 0.0f, (float)r, (float)r, prevAPC * -90.0f);
            StencilUtil.readStencilBuffer(0);
        }
        this.tessellator.draw();
        if (fragmentalColor) {
            this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            this.buffer.pos(0.0, 0.0).tex(0.0, 0.0).color(this.getColor(index, aPC, true)).endVertex();
            this.buffer.pos(0.0, r).tex(0.0, 1.0).color(this.getColor((int)(324.0f + (float)index), aPC, true)).endVertex();
            this.buffer.pos(r, r).tex(1.0, 1.0).color(this.getColor((int)(648.0f + (float)index), aPC, true)).endVertex();
            this.buffer.pos(r, 0.0).tex(1.0, 0.0).color(this.getColor((int)(972.0f + (float)index), aPC, true)).endVertex();
            StencilUtil.readStencilBuffer(1);
            this.tessellator.draw(5);
            StencilUtil.uninitStencilBuffer();
        }
        GL11.glPopMatrix();
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            circles.clear();
        }
        super.onToggled(actived);
    }

    private static final Vec3d getVec3dFromEntity(Entity entityIn) {
        float PT = mc.getRenderPartialTicks();
        return new Vec3d(entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)PT, entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)PT, entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)PT);
    }

    private void setupDraw(Runnable render, boolean preBindTex) {
        RenderManager manager = mc.getRenderManager();
        EntityRenderer renderer = JumpCircles.mc.entityRenderer;
        Vec3d revert = new Vec3d(RenderManager.viewerPosX, RenderManager.viewerPosY, RenderManager.viewerPosZ);
        boolean light = GL11.glIsEnabled(2896);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDepthMask(false);
        GL11.glDisable(2884);
        if (light) {
            GL11.glDisable(2896);
        }
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 1);
        renderer.disableLightmap();
        GL11.glTranslated(-revert.xCoord, -revert.yCoord, -revert.zCoord);
        if (preBindTex) {
            mc.getTextureManager().bindTexture(this.jumpTexture(0));
        }
        render.run();
        GL11.glTranslated(revert.xCoord, revert.yCoord, revert.zCoord);
        GL11.glBlendFunc(770, 771);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glShadeModel(7424);
        if (light) {
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
        GL11.glDepthMask(true);
        GL11.glEnable(3008);
        GL11.glPopMatrix();
    }

    private ResourceLocation jumpTexture(int index) {
        String tex = this.Texture.currentMode;
        if (tex.equalsIgnoreCase("CubicalPieces")) {
            int ms = 1500;
            long time = (System.currentTimeMillis() + (long)index) % (long)ms;
            float pcs = (float)time / (float)ms;
            return this.frames.get((int)Math.min(pcs * ((float)this.frames.size() - 0.5f), 100.0f));
        }
        return tex.equalsIgnoreCase("Circle") ? this.JUMP_CIRCLE : (tex.equalsIgnoreCase("KonchalEbal") ? this.JUMP_KONCHAL : this.JUMP_INSUAL);
    }

    static {
        circles = new ArrayList<JumpRenderer>();
    }

    private static final class JumpRenderer {
        private final long time = System.currentTimeMillis();
        private final Vec3d pos;
        int index;

        private JumpRenderer(Vec3d pos, int index) {
            this.pos = pos;
            this.index = index;
        }

        private float getDeltaTime() {
            return (float)(System.currentTimeMillis() - this.time) / JumpCircles.get.MaxTime.fValue;
        }

        private int getIndex() {
            return this.index;
        }
    }
}

