/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class FireFlies
extends Module {
    Settings PickColor;
    Settings DarkImprint;
    Settings Lighting;
    Settings SpawnDelay;
    private final ArrayList<FirePart> FIRE_PARTS_LIST = new ArrayList();
    private final ResourceLocation FIRE_PART_TEX = new ResourceLocation("vegaline/modules/fireflies/firepart.png");
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();

    public FireFlies() {
        super("FireFlies", 0, Module.Category.RENDER);
        this.PickColor = new Settings("PickColor", ColorUtils.getColor(255, 70, 0), (Module)this);
        this.settings.add(this.PickColor);
        this.DarkImprint = new Settings("DarkImprint", false, (Module)this);
        this.settings.add(this.DarkImprint);
        this.Lighting = new Settings("Lighting", false, (Module)this);
        this.settings.add(this.Lighting);
        this.SpawnDelay = new Settings("SpawnDelay", 3.0f, 10.0f, 1.0f, this);
        this.settings.add(this.SpawnDelay);
    }

    private long getMaxPartAliveTime() {
        return 6000L;
    }

    private int getPartColor(FirePart part, float alphaPC) {
        int color = this.PickColor.color;
        return ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * part.getAlphaPC() * alphaPC);
    }

    private float getRandom(double min, double max) {
        return (float)MathUtils.getRandomInRange(min, max);
    }

    private Vec3d generateVecForPart(double rangeXZ, double rangeY) {
        Vec3d pos = Minecraft.player.getPositionVector().addVector(this.getRandom(-rangeXZ, rangeXZ), this.getRandom(-rangeY / 2.0, rangeY), this.getRandom(-rangeXZ, rangeXZ));
        for (int i = 0; i < 30; ++i) {
            if (!Speed.posBlock(pos.xCoord, pos.yCoord, pos.zCoord) && !(Minecraft.player.getDistanceAtEyeXZ(pos.xCoord, pos.zCoord) < rangeXZ / 3.0)) continue;
            pos = Minecraft.player.getPositionVector().addVector(this.getRandom(-rangeXZ, rangeXZ), this.getRandom(-rangeY / 2.0, rangeY), this.getRandom(-rangeXZ, rangeXZ));
        }
        return pos;
    }

    private void setupGLDrawsFireParts(Runnable partsRender, boolean tex) {
        double glX = RenderManager.viewerPosX;
        double glY = RenderManager.viewerPosY;
        double glZ = RenderManager.viewerPosZ;
        GL11.glPushMatrix();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        FireFlies.mc.entityRenderer.disableLightmap();
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        if (tex) {
            GL11.glEnable(3553);
        } else {
            GL11.glDisable(3553);
        }
        GL11.glDisable(2896);
        GL11.glShadeModel(7425);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        GL11.glTranslated(-glX, -glY, -glZ);
        partsRender.run();
        GL11.glTranslated(glX, glY, glZ);
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GlStateManager.resetColor();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glPopMatrix();
    }

    private void bindResource(ResourceLocation toBind) {
        mc.getTextureManager().bindTexture(toBind);
    }

    private void drawBindedTexture(float x, float y, float x2, float y2, int c, int c2, int c3, int c4) {
        this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        this.buffer.pos(x, y).tex(0.0, 0.0).color(c).endVertex();
        this.buffer.pos(x, y2).tex(0.0, 1.0).color(c).endVertex();
        this.buffer.pos(x2, y2).tex(1.0, 1.0).color(c).endVertex();
        this.buffer.pos(x2, y).tex(1.0, 0.0).color(c).endVertex();
        this.tessellator.draw();
    }

    private void drawBindedTexture(float x, float y, float x2, float y2, int c) {
        this.drawBindedTexture(x, y, x2, y2, c, c, c, c);
    }

    private void drawPart(FirePart part, float pTicks, float alphaPC) {
        int color = this.getPartColor(part, alphaPC);
        alphaPC *= part.getAlphaPC();
        if (this.DarkImprint.bValue) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA_SATURATE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            this.drawSparkPartsList(color, part, alphaPC, pTicks);
            this.drawTrailPartsList(color, part, alphaPC);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        } else {
            this.drawSparkPartsList(color, part, alphaPC, pTicks);
            this.drawTrailPartsList(color, part, alphaPC);
        }
        Vec3d pos = part.getRenderPosVec(pTicks);
        GL11.glPushMatrix();
        GL11.glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);
        GL11.glNormal3d(1.0, 1.0, 1.0);
        GL11.glRotated(-FireFlies.mc.getRenderManager().playerViewY, 0.0, 1.0, 0.0);
        GL11.glRotated(FireFlies.mc.getRenderManager().playerViewX, FireFlies.mc.gameSettings.thirdPersonView == 2 ? -1.0 : 1.0, 0.0, 0.0);
        GL11.glScaled(-0.1, -0.1, 0.1);
        float scale = 7.0f * alphaPC;
        this.drawBindedTexture(-scale / 2.0f, -scale / 2.0f, scale / 2.0f, scale / 2.0f, color);
        if (this.Lighting.bValue) {
            this.drawBindedTexture(-(scale *= 8.0f) / 2.0f, -scale / 2.0f, scale / 2.0f, scale / 2.0f, ColorUtils.swapAlpha(ColorUtils.swapDark(color, 0.2f), (float)ColorUtils.getAlphaFromColor(color) / 5.0f));
            this.drawBindedTexture(-(scale *= 3.0f) / 2.0f, -scale / 2.0f, scale / 2.0f, scale / 2.0f, ColorUtils.swapAlpha(ColorUtils.swapDark(color, 0.2f), (float)ColorUtils.getAlphaFromColor(color) / 7.0f));
        }
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate() {
        if (Minecraft.player != null && Minecraft.player.ticksExisted == 1) {
            this.FIRE_PARTS_LIST.forEach(part -> part.setToRemove());
        }
        this.FIRE_PARTS_LIST.forEach(FirePart::updatePart);
        this.FIRE_PARTS_LIST.removeIf(FirePart::isToRemove);
        if (Minecraft.player.ticksExisted % (int)(this.SpawnDelay.fValue + 1.0f) == 0) {
            this.FIRE_PARTS_LIST.add(new FirePart(this.generateVecForPart(10.0, 4.0), this.getMaxPartAliveTime()));
            this.FIRE_PARTS_LIST.add(new FirePart(this.generateVecForPart(6.0, 5.0), this.getMaxPartAliveTime()));
        }
    }

    @Override
    public void alwaysRender3D(float partialTicks) {
        if (!this.FIRE_PARTS_LIST.isEmpty() && this.stateAnim.getAnim() > 0.003921569f) {
            this.setupGLDrawsFireParts(() -> {
                this.bindResource(this.FIRE_PART_TEX);
                this.FIRE_PARTS_LIST.forEach(part -> this.drawPart((FirePart)part, partialTicks, this.stateAnim.getAnim()));
            }, true);
        }
    }

    @Override
    public void onToggled(boolean actived) {
        this.stateAnim.to = actived ? 1.0f : 0.0f;
        super.onToggled(actived);
    }

    private void drawSparkPartsList(int color, FirePart firePart, float alphaPC, float partialTicks) {
        if (firePart.SPARK_PARTS.size() < 2) {
            return;
        }
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glEnable(2832);
        GL11.glPointSize(1.5f + 6.0f * MathUtils.clamp(1.0f - (Minecraft.player.getSmoothDistanceToCoord((float)firePart.getPosVec().xCoord, (float)firePart.getPosVec().yCoord + 1.6f, (float)firePart.getPosVec().zCoord) - 3.0f) / 10.0f, 0.0f, 1.0f));
        GL11.glBegin(0);
        for (SparkPart spark : firePart.SPARK_PARTS) {
            int c = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, color, (float)spark.timePC()), (float)ColorUtils.getAlphaFromColor(color) * alphaPC * (1.0f - (float)spark.timePC()));
            RenderUtils.glColor(c);
            GL11.glVertex3d(spark.getRenderPosX(partialTicks), spark.getRenderPosY(partialTicks), spark.getRenderPosZ(partialTicks));
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    private void drawTrailPartsList(int color, FirePart firePart, float alphaPC) {
        if (firePart.TRAIL_PARTS.size() < 2) {
            return;
        }
        GL11.glDisable(3553);
        GL11.glLineWidth(1.0E-5f + 8.0f * MathUtils.clamp(1.0f - (Minecraft.player.getSmoothDistanceToCoord((float)firePart.getPosVec().xCoord, (float)firePart.getPosVec().yCoord + 1.6f, (float)firePart.getPosVec().zCoord) - 3.0f) / 20.0f, 0.0f, 1.0f));
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        int point = 0;
        int pointsCount = firePart.TRAIL_PARTS.size();
        GL11.glBegin(3);
        for (TrailPart trail : firePart.TRAIL_PARTS) {
            float sizePC = (float)point / (float)pointsCount;
            sizePC = ((double)sizePC > 0.5 ? 1.0f - sizePC : sizePC) * 2.0f;
            sizePC = sizePC > 1.0f ? 1.0f : (sizePC < 0.0f ? 0.0f : sizePC);
            int c = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * alphaPC * sizePC);
            RenderUtils.glColor(c);
            GL11.glVertex3d(trail.x, trail.y, trail.z);
            ++point;
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GL11.glEnable(3008);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
    }

    private class FirePart {
        List<TrailPart> TRAIL_PARTS = new ArrayList<TrailPart>();
        List<SparkPart> SPARK_PARTS = new ArrayList<SparkPart>();
        Vec3d prevPos;
        Vec3d pos;
        AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.02f);
        int msChangeSideRate = this.getMsChangeSideRate();
        float moveYawSet = FireFlies.this.getRandom(0.0, 360.0);
        float speed = FireFlies.this.getRandom(0.1, 0.25);
        float yMotion = FireFlies.this.getRandom(-0.075, 0.1);
        float moveYaw = this.moveYawSet;
        float maxAlive;
        long startTime;
        long rateTimer = this.startTime = System.currentTimeMillis();
        boolean toRemove;

        public FirePart(Vec3d pos, float maxAlive) {
            this.pos = pos;
            this.prevPos = pos;
            this.maxAlive = maxAlive;
        }

        public float getTimePC() {
            return MathUtils.clamp((float)(System.currentTimeMillis() - this.startTime) / this.maxAlive, 0.0f, 1.0f);
        }

        public void setAlphaPCTo(float to) {
            this.alphaPC.to = to;
        }

        public float getAlphaPC() {
            return this.alphaPC.getAnim();
        }

        public Vec3d getPosVec() {
            return this.pos;
        }

        public Vec3d getRenderPosVec(float pTicks) {
            Vec3d pos = this.getPosVec();
            return pos.addVector(-(this.prevPos.xCoord - pos.xCoord) * (double)pTicks, -(this.prevPos.yCoord - pos.yCoord) * (double)pTicks, -(this.prevPos.zCoord - pos.zCoord) * (double)pTicks);
        }

        public void updatePart() {
            if (System.currentTimeMillis() - this.rateTimer >= (long)this.msChangeSideRate) {
                this.msChangeSideRate = this.getMsChangeSideRate();
                this.rateTimer = System.currentTimeMillis();
                this.moveYawSet = FireFlies.this.getRandom(0.0, 360.0);
            }
            this.moveYaw = MathUtils.lerp(this.moveYaw, this.moveYawSet, 0.065f);
            float motionX = -((float)Math.sin(Math.toRadians(this.moveYaw))) * (this.speed /= 1.005f);
            float motionZ = (float)Math.cos(Math.toRadians(this.moveYaw)) * this.speed;
            this.prevPos = this.pos;
            float scaleBox = 0.1f;
            float delente = !Module.mc.world.getCollisionBoxes(null, new AxisAlignedBB(this.pos.xCoord - (double)(scaleBox / 2.0f), this.pos.yCoord, this.pos.zCoord - (double)(scaleBox / 2.0f), this.pos.xCoord + (double)(scaleBox / 2.0f), this.pos.yCoord + (double)scaleBox, this.pos.zCoord + (double)(scaleBox / 2.0f))).isEmpty() ? 0.3f : 1.0f;
            this.pos = this.pos.addVector(motionX / delente, (this.yMotion /= 1.02f) / delente, motionZ / delente);
            if (this.getTimePC() >= 1.0f) {
                this.setAlphaPCTo(0.0f);
                if (this.getAlphaPC() < 0.003921569f) {
                    this.setToRemove();
                }
            }
            this.TRAIL_PARTS.add(new TrailPart(this, 400));
            if (!this.TRAIL_PARTS.isEmpty()) {
                this.TRAIL_PARTS.removeIf(TrailPart::toRemove);
            }
            for (int i = 0; i < 2; ++i) {
                this.SPARK_PARTS.add(new SparkPart(this, 300));
            }
            this.SPARK_PARTS.forEach(SparkPart::motionSparkProcess);
            if (!this.SPARK_PARTS.isEmpty()) {
                this.SPARK_PARTS.removeIf(SparkPart::toRemove);
            }
        }

        public void setToRemove() {
            this.toRemove = true;
        }

        public boolean isToRemove() {
            return this.toRemove;
        }

        int getMsChangeSideRate() {
            return (int)FireFlies.this.getRandom(300.5, 900.5);
        }
    }

    private class SparkPart {
        double posX;
        double posY;
        double posZ;
        double prevPosX;
        double prevPosY;
        double prevPosZ;
        double speed = Math.random() / 30.0;
        double radianYaw = Math.random() * 360.0;
        double radianPitch = -90.0 + Math.random() * 180.0;
        long startTime = System.currentTimeMillis();
        int maxTime;

        SparkPart(FirePart part, int maxTime) {
            this.maxTime = maxTime;
            this.posX = part.getPosVec().xCoord;
            this.posY = part.getPosVec().yCoord;
            this.posZ = part.getPosVec().zCoord;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
        }

        double timePC() {
            return MathUtils.clamp((float)(System.currentTimeMillis() - this.startTime) / (float)this.maxTime, 0.0f, 1.0f);
        }

        boolean toRemove() {
            return this.timePC() == 1.0;
        }

        void motionSparkProcess() {
            double radYaw = Math.toRadians(this.radianYaw);
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.posX += Math.sin(radYaw) * this.speed;
            this.posY += Math.cos(Math.toRadians(this.radianPitch - 90.0)) * this.speed;
            this.posZ += Math.cos(radYaw) * this.speed;
        }

        double getRenderPosX(float partialTicks) {
            return this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks;
        }

        double getRenderPosY(float partialTicks) {
            return this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks;
        }

        double getRenderPosZ(float partialTicks) {
            return this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks;
        }
    }

    private class TrailPart {
        double x;
        double y;
        double z;
        long startTime = System.currentTimeMillis();
        int maxTime;

        public TrailPart(FirePart part, int maxTime) {
            this.maxTime = maxTime;
            this.x = part.getPosVec().xCoord;
            this.y = part.getPosVec().yCoord;
            this.z = part.getPosVec().zCoord;
        }

        public float getTimePC() {
            return MathUtils.clamp((System.currentTimeMillis() - this.startTime) / (long)this.maxTime, 0.0f, 1.0f);
        }

        public boolean toRemove() {
            return this.getTimePC() == 1.0f;
        }
    }
}

