/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Fly
extends Module {
    public static Fly get;
    public Settings Mode;
    public Settings UpWard;
    public Settings DownWard;
    public Settings AutoUp;
    public Settings SmoothSpeed;
    public Settings NoVanillaKick;
    public Settings SpeedY;
    public Settings SpeedXZ;
    public Settings DisableOnFlag;
    public Settings SpeedCrate;
    public Settings StepCrate;
    public Settings Visualization;
    final TimerHelper ticker = new TimerHelper();
    final TimerHelper ticker2 = new TimerHelper();
    final TimerHelper timeFlying = new TimerHelper();
    int tickerFinalling;
    private boolean enableGround = false;
    public static double flySpeed;
    private final AnimationUtils alphed = new AnimationUtils(0.0f, 0.0f, 0.075f);

    public Fly() {
        super("Fly", 0, Module.Category.MOVEMENT);
        this.Mode = new Settings("Mode", "MatrixChunk", (Module)this, new String[]{"MatrixChunk", "MatrixOld", "NCP", "StormHVH", "Jartex", "AAC", "Rage", "Motion", "Grim"});
        this.settings.add(this.Mode);
        this.UpWard = new Settings("UpWard", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk"));
        this.settings.add(this.UpWard);
        this.DownWard = new Settings("DownWard", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk"));
        this.settings.add(this.DownWard);
        this.AutoUp = new Settings("AutoUp", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk"));
        this.settings.add(this.AutoUp);
        this.SmoothSpeed = new Settings("SmoothSpeed", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk"));
        this.settings.add(this.SmoothSpeed);
        this.NoVanillaKick = new Settings("NoVanillaKick", false, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk") || this.Mode.currentMode.equalsIgnoreCase("Rage"));
        this.settings.add(this.NoVanillaKick);
        this.SpeedY = new Settings("SpeedY", 1.0f, 10.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("StormHVH") || this.Mode.currentMode.equalsIgnoreCase("Motion"));
        this.settings.add(this.SpeedY);
        this.SpeedXZ = new Settings("SpeedXZ", 4.5f, 10.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk") || this.Mode.currentMode.equalsIgnoreCase("") || this.Mode.currentMode.equalsIgnoreCase("Motion"));
        this.settings.add(this.SpeedXZ);
        this.DisableOnFlag = new Settings("DisableOnFlag", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("MatrixChunk") || this.Mode.currentMode.equalsIgnoreCase("MatrixOld"));
        this.settings.add(this.DisableOnFlag);
        this.SpeedCrate = new Settings("SpeedCrate", 6.0f, 10.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Rage"));
        this.settings.add(this.SpeedCrate);
        this.StepCrate = new Settings("StepCrate", 3.0f, 10.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Rage"));
        this.settings.add(this.StepCrate);
        this.Visualization = new Settings("Visualization", true, (Module)this, () -> !this.Mode.currentMode.equalsIgnoreCase("Jartex") && !this.Mode.currentMode.equalsIgnoreCase("NCP"));
        this.settings.add(this.Visualization);
        get = this;
    }

    @Override
    public void alwaysRender3D() {
        this.render3d(this.alphed.getAnim());
    }

    void render3d(float alphaPC) {
        if (alphaPC > 0.005f) {
            GL11.glPushMatrix();
            Fly.mc.entityRenderer.disableLightmap();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(1.0E-5f);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(3008);
            GL11.glShadeModel(7425);
            GL11.glEnable(2848);
            float ext = Minecraft.player.width / 2.0f - 0.01f;
            GL11.glBegin(9);
            RenderUtils.setupColor(ClientColors.getColor1(), 20.0f * alphaPC);
            GL11.glVertex3d(-ext, 0.0, -ext);
            RenderUtils.setupColor(ClientColors.getColor1(100), 20.0f * alphaPC);
            GL11.glVertex3d(-ext, 0.0, ext);
            RenderUtils.setupColor(ClientColors.getColor1(200), 20.0f * alphaPC);
            GL11.glVertex3d(ext, 0.0, ext);
            RenderUtils.setupColor(ClientColors.getColor1(300), 20.0f * alphaPC);
            GL11.glVertex3d(ext, 0.0, -ext);
            GL11.glEnd();
            int endCC = Minecraft.player.isCollided ? ColorUtils.getColor(255, 40, 40) : -1;
            GL11.glBegin(3);
            RenderUtils.setupColor(endCC, 170.0f * alphaPC);
            GL11.glVertex3d(-ext, 0.0, -ext);
            GL11.glVertex3d(-ext, 0.0, ext);
            GL11.glVertex3d(ext, 0.0, ext);
            GL11.glVertex3d(ext, 0.0, -ext);
            GL11.glVertex3d(-ext, 0.0, -ext);
            GL11.glEnd();
            GL11.glLineWidth(1.0f);
            GL11.glShadeModel(7424);
            GL11.glEnable(3553);
            GlStateManager.resetColor();
            float sizeXZ = 0.003f;
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glTranslated(sizeXZ, 0.0, sizeXZ);
            GL11.glRotated(180.0, 0.0, 0.0, 1.0);
            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glRotated(180.0, 0.0, 1.0, 0.0);
            GL11.glTranslated(0.0, 0.0, -1.0E-5);
            GL11.glRotated((float)((int)((Minecraft.player.rotationYaw - 22.0f) / 45.0f)) * 45.0f - 180.0f, 0.0, 0.0, 1.0);
            GL11.glScaled(sizeXZ, sizeXZ, sizeXZ);
            float yaw = Minecraft.player.lastReportedYaw % 360.0f + (float)(Minecraft.player.rotationYaw < 0.0f ? 360 : 0);
            String dir = yaw > 0.0f && yaw <= 22.0f ? "z+" : (yaw > 22.0f && yaw <= 67.0f ? "x-z+" : (yaw > 67.0f && yaw < 112.0f ? "x-" : (yaw > 112.0f && yaw < 157.0f ? "x-z-" : (yaw > 157.0f && yaw < 202.0f ? "z-" : (yaw > 202.0f && yaw < 247.0f ? "x+z-" : (yaw > 247.0f && yaw < 292.0f ? "x+" : (yaw > 292.0f && yaw < 337.0f ? "x+z+" : "z+")))))));
            String speed = "\u00a76Speed: \u00a7e" + String.format("%.1f", MoveMeHelp.getCuttingSpeed()) + "\u00a7b dir " + dir;
            if (alphaPC * 255.0f >= 34.0f) {
                Fonts.mntsb_36.drawStringWithShadow(speed, -Fonts.mntsb_36.getStringWidth(speed) / 2, -3.0, ColorUtils.swapAlpha(-1, alphaPC * 255.0f));
            }
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }

    void render2d(float alphaPC, ScaledResolution sr) {
        if (alphaPC * 255.0f >= 34.0f) {
            ArrayList<CallSite> strs = new ArrayList<CallSite>();
            strs.add((CallSite)((Object)(TextFormatting.AQUA + "Speed" + TextFormatting.GRAY + " = " + TextFormatting.WHITE + (String)(MoveMeHelp.getCuttingSpeed() == 0.0 ? "0" : String.format("%.2f", MoveMeHelp.getCuttingSpeed()).replace("0.00", "0").replace("0.", ".") + " | " + String.format("%.2f", MoveMeHelp.getCuttingSpeed() * 15.3571428571).replace("0.00", "0").replace("0.", ".")))));
            strs.add((CallSite)((Object)(TextFormatting.AQUA + "Ymotion" + TextFormatting.GRAY + " = " + TextFormatting.WHITE + String.format("%.2f", Entity.Getmotiony).replace("0.00", "0").replace("0.", "."))));
            strs.add((CallSite)((Object)(TextFormatting.AQUA + "Ground" + TextFormatting.GRAY + " = " + TextFormatting.WHITE + (Minecraft.player.onGround ? "YES" : "NO"))));
            strs.add((CallSite)((Object)(TextFormatting.AQUA + "Fall dst" + TextFormatting.GRAY + " = " + TextFormatting.WHITE + String.format("%.1f", Float.valueOf(Minecraft.player.fallDistance)).replace("0.00", "0").replace("0.", "."))));
            strs.add((CallSite)((Object)(TextFormatting.AQUA + "Hurt" + TextFormatting.GRAY + " = " + TextFormatting.WHITE + "M=" + (EntityLivingBase.isMatrixDamaged ? "T" : "F") + ",N=" + (EntityLivingBase.isNcpDamaged ? "T" : "F") + ",S=" + (EntityLivingBase.isSunRiseDamaged ? "T" : "F"))));
            long ms = this.timeFlying.getTime();
            float sec = (float)ms / 1000.0f;
            int mins = (int)sec / 60;
            int hors = mins / 60;
            sec -= (float)(mins * 60);
            String timeString = ms < 100L ? "0s" : (String)(hors > 0 ? hors + "h" : "") + (String)(mins > 0 ? (mins -= hors * 60) + "m" : "") + (String)(sec > 0.0f ? String.format("%.2f", Float.valueOf(sec)) + "s" : "");
            strs.add((CallSite)((Object)(TextFormatting.AQUA + "Fly time" + TextFormatting.GRAY + " = " + TextFormatting.WHITE + timeString)));
            int texC = ColorUtils.getColor(255, (int)(255.0f * alphaPC));
            GL11.glPushMatrix();
            float step = 9.0f;
            float x = (float)sr.getScaledWidth() / 2.0f - 16.0f;
            float y = (float)sr.getScaledHeight() / 2.0f;
            float w = 0.0f;
            for (String string : strs) {
                if (!(w < (float)Fonts.noise_14.getStringWidth(string))) continue;
                w = Fonts.noise_14.getStringWidth(string);
            }
            RenderUtils.customScaledObject2D(x, y, 0.0f, Fonts.noise_14.getHeight(), alphaPC);
            y -= step * (float)strs.size() / 2.0f;
            int i = 0;
            for (String string : strs) {
                Fonts.noise_14.drawString(string, (double)(x - w) + MathUtils.getDifferenceOf(y + step / 2.0f, (float)sr.getScaledHeight() / 2.0f) / 4.0, y + 1.5f, texC);
                y += step;
                ++i;
            }
            float f = w - 6.0f;
            RenderUtils.drawCroneShadow(x - w + f, (float)sr.getScaledHeight() / 2.0f, 245, 315, f, 4.0f, ColorUtils.getColor(10, 180, 255, 0), ColorUtils.getColor(255, (int)(100.0f * alphaPC)), true);
            RenderUtils.drawCroneShadow(x - w + f, (float)sr.getScaledHeight() / 2.0f, 245, 315, f + 4.0f, 1.0f, ColorUtils.getColor(255, (int)(100.0f * alphaPC)), 0, true);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        boolean rule = this.actived && this.Visualization.bValue && !this.Mode.currentMode.equalsIgnoreCase("Jartex") && !this.Mode.currentMode.equalsIgnoreCase("NCP");
        this.alphed.to = rule ? 1.0f : 0.0f;
        this.alphed.speed = rule ? 0.06f : 0.04f;
        this.render2d(this.alphed.getAnim(), sr);
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Mode.currentMode);
    }

    @Override
    public void onUpdate() {
        if (this.Mode.currentMode.equalsIgnoreCase("Grim")) {
            Minecraft.player.multiplyMotionXZ(0.0f);
            int packets = 10;
            float ySpeed = 0.02f * (float)packets;
            Minecraft.player.motionY = Minecraft.player.isJumping() ? (double)ySpeed : (Minecraft.player.isSneaking() ? (double)(-ySpeed) : 0.0);
            float speed = 0.05f * (float)packets;
            MoveMeHelp.setSpeed(speed);
            for (int i = 0; i < packets; ++i) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Rage")) {
            for (int crate = 0; crate < (int)this.StepCrate.fValue; ++crate) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("AAC")) {
            double motY = Minecraft.player.motionY;
            if (!Minecraft.player.isJumping() && !Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 0.044, Minecraft.player.posZ)) {
                this.enableGround = false;
            }
            double setedMotY = Minecraft.player.isJumping() ? (this.enableGround ? (double)Minecraft.player.getJumpUpwardsMotion() * 1.1 : 0.0) : (Minecraft.player.isSneaking() ? motY : (MathUtils.getDifferenceOf(motY, 0.0) > 0.1 ? motY / 3.0 + 0.02 : 0.0));
            double speed = setedMotY != 0.0 ? 0.1 : 0.35;
            Minecraft.player.motionY = setedMotY;
            MoveMeHelp.setSpeed(speed);
            MoveMeHelp.setCuttingSpeed(speed);
        }
        if (this.Mode.currentMode.equalsIgnoreCase("MatrixOld")) {
            boolean ground = !Fly.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.offsetMinDown(0.999f).expand(0.999f, 0.0, 0.999f)).isEmpty();
            Minecraft.player.onGround = false;
            Minecraft.player.jump();
            MoveMeHelp.setSpeed(MathUtils.clamp((double)((float)(MoveMeHelp.getSpeed() * (double)(Minecraft.player.onGround ? 0.0f : 1.1f))), 0.1, 5.0));
            double d = Fly.mc.gameSettings.keyBindSneak.pressed ? Minecraft.player.motionY * (double)(Minecraft.player.motionY > 0.0 ? -1 : 1) - 0.42 : (Minecraft.player.isJumping() ? (this.enableGround ? 0.2 + Minecraft.player.motionY : 0.42) : (Minecraft.player.motionY = Speed.canMatrixBoost() ? -0.2 : 0.0));
            if (ground) {
                this.ticker.reset();
            }
            if (this.ticker.hasReached(1500 - (this.enableGround ? 50 : 350))) {
                this.toggle(false);
                this.ticker.reset();
            }
            flySpeed = MathUtils.clamp((double)((float)(MoveMeHelp.getCuttingSpeed() * (double)(Minecraft.player.onGround ? 0.0f : 1.1f))), 0.1, 5.0);
        }
        if (this.Mode.currentMode.equalsIgnoreCase("NCP")) {
            boolean up = Minecraft.player.isJumping();
            boolean down = Minecraft.player.isSneaking();
            boolean move = MoveMeHelp.moveKeysPressed();
            double speed = up || down ? 0.0 : (move ? MoveMeHelp.getSpeedByBPS(4.010000000000001) : 0.0);
            float moveYaw = (float)Math.toRadians(MoveMeHelp.moveYaw(Minecraft.player.rotationYaw));
            double sinSpeed = -Math.sin(moveYaw) * speed;
            double cosSpeed = Math.cos(moveYaw) * speed;
            double ySpeed = up ? 0.062 : (down ? -0.102 : (move ? 0.0 : (Minecraft.player.ticksExisted % 2 == 0 && !Minecraft.player.onGround ? 0.01 : -0.01)));
            double pX = Minecraft.player.posX + sinSpeed;
            double pY = Minecraft.player.posY + ySpeed;
            double pZ = Minecraft.player.posZ + cosSpeed;
            MoveMeHelp.setMotionSpeed(true, false, 0.0);
            MoveMeHelp.setSpeed(-speed / 5.0);
            Entity.motiony = 1.0E-45;
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(pX, pY, pZ, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(0.0, pY + ySpeed, 0.0, true));
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Jartex")) {
            double speed = 2.0;
            MoveMeHelp.setSpeed(0.0);
            MoveMeHelp.setCuttingSpeed(0.0);
            Minecraft.player.motionY = Minecraft.player.ticksExisted % 2 == 0 ? 0.1 : -0.1;
            Minecraft.player.jumpMovementFactor = 0.0f;
            if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                Minecraft.player.motionY = 0.425f;
            } else {
                MoveMeHelp.setCuttingSpeed(Minecraft.player.ticksExisted % 2 == 0 ? speed / 1.06 : 0.0);
            }
            flySpeed = speed;
        }
    }

    @Override
    public void onMovement() {
        if (this.Mode.currentMode.equalsIgnoreCase("Motion")) {
            Minecraft.player.multiplyMotionXZ(0.0f);
            MoveMeHelp.setCuttingSpeed((double)(9.953f * (this.SpeedXZ.fValue / 10.0f)) / 1.06);
            Minecraft.player.motionY = 0.0;
            Entity.motiony = Float.MIN_VALUE * (float)(Minecraft.player.ticksExisted % 2 == 0 ? 1 : -1) + this.SpeedY.fValue * (float)(Minecraft.player.isSneaking() ? -1 : (Minecraft.player.isJumping() ? 1 : 0));
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Rage")) {
            float crate = MathUtils.clamp((int)this.StepCrate.fValue, 1, 10);
            float speed = 9.953f / (this.NoVanillaKick.bValue ? 1.024f : 1.0f) * (this.SpeedCrate.fValue / 10.0f) * crate;
            MoveMeHelp.setSpeed(speed);
            MoveMeHelp.setCuttingSpeed((double)speed / 1.06);
            Minecraft.player.motionY = 0.0;
            Entity.motiony = this.NoVanillaKick.bValue ? (double)(Minecraft.player.ticksExisted % 2 != 0 ? 0.05f : -0.05f) : (double)1.4E-45f;
        }
        if (this.Mode.currentMode.equalsIgnoreCase("MatrixChunk") && Minecraft.player.fallDistance != 0.0f) {
            double speed;
            float curSpeed = this.SpeedXZ.fValue / 10.0f;
            double d = speed = this.SmoothSpeed.bValue ? MoveMeHelp.getCuttingSpeed() / 1.3 : 0.0;
            if (Minecraft.player.fallDistance != 0.0f || !this.AutoUp.bValue) {
                speed = (Minecraft.player.isJumping() && this.UpWard.bValue ? 8.2675f : 9.4675f) * curSpeed;
            }
            if (MoveMeHelp.moveKeysPressed() || !this.SmoothSpeed.bValue) {
                MoveMeHelp.setSpeed(speed);
                MoveMeHelp.setCuttingSpeed(speed / 1.06);
            } else {
                MoveMeHelp.setSpeed(speed, 0.6f);
            }
            flySpeed = speed;
        }
        if (this.Mode.currentMode.equalsIgnoreCase("StormHVH")) {
            double motionY;
            float curMotionY = this.SpeedY.fValue;
            double speed = this.SpeedXZ.fValue / 10.0f;
            double d = motionY = Minecraft.player.ticksExisted % 2 == 0 ? 1.0E-10 : -1.0E-10;
            if (Minecraft.player.isJumping() || Minecraft.player.isSneaking()) {
                if (this.ticker.hasReached(50.0)) {
                    ++this.tickerFinalling;
                    this.ticker.reset();
                }
            } else {
                this.tickerFinalling = 0;
            }
            if (this.tickerFinalling < 15) {
                motionY = Minecraft.player.isJumping() ? (double)curMotionY : (Minecraft.player.isSneaking() ? (double)(-curMotionY) : motionY);
                this.ticker2.reset();
            } else if (this.ticker2.hasReached(300.0)) {
                this.tickerFinalling = 0;
                this.ticker2.reset();
            }
            Entity.motiony = motionY;
            Minecraft.player.motionY = motionY;
            if (!TargetStrafe.goStrafe() || MoveMeHelp.moveKeysPressed()) {
                MoveMeHelp.setSpeed(speed * 9.7675);
                MoveMeHelp.setCuttingSpeed(speed / 1.06 * 9.7675);
            }
            flySpeed = speed * 9.7675;
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            this.timeFlying.reset();
            if (this.Mode.currentMode.equalsIgnoreCase("MatrixOld")) {
                this.enableGround = Minecraft.player.onGround;
                this.ticker.reset();
            }
            if (this.Mode.currentMode.equalsIgnoreCase("AAC")) {
                this.enableGround = Minecraft.player.onGround;
            }
            if (this.Mode.currentMode.equalsIgnoreCase("StormHVH")) {
                Minecraft.player.jump();
                if (!MoveMeHelp.isBlockAboveHead()) {
                    Minecraft.player.setPosY(Minecraft.player.posY + 0.4);
                }
            }
            if (this.Mode.currentMode.equalsIgnoreCase("MatrixChunk")) {
                this.tickerFinalling = 0;
            }
        } else {
            if (this.Mode.currentMode.equalsIgnoreCase("MatrixChunk")) {
                MoveMeHelp.setSpeed(this.SmoothSpeed.bValue ? MoveMeHelp.getSpeed() / 5.0 : MoveMeHelp.getSpeed() / 30.0, this.SmoothSpeed.bValue ? 0.3f : 0.0f);
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Jartex")) {
                Minecraft.player.motionY = -0.023214;
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(0.0);
                Minecraft.player.jumpMovementFactor = 0.0f;
            }
            if (this.Mode.currentMode.equalsIgnoreCase("StormHVH")) {
                MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() / 4.0);
                if (Minecraft.player.motionY > 0.0) {
                    Minecraft.player.motionY /= 3.0;
                }
            }
        }
        super.onToggled(actived);
    }

    @EventTarget
    public void onPlayerUpdate(EventPlayerMotionUpdate e) {
        if (this.actived) {
            if (this.Mode.currentMode.equalsIgnoreCase("MatrixChunk")) {
                if (Minecraft.player.onGround && Minecraft.player.fallDistance == 0.0f && this.AutoUp.bValue) {
                    MoveMeHelp.setSpeed(0.0);
                    Minecraft.player.jumpMovementFactor = 0.0f;
                    if (!Minecraft.player.isJumping()) {
                        Minecraft.player.motionY = Minecraft.player.getJumpUpwardsMotion();
                    }
                    return;
                }
                if (Minecraft.player.fallDistance != 0.0f) {
                    ++this.tickerFinalling;
                    float curSpeed = this.SpeedXZ.fValue / 10.0f;
                    if (Minecraft.player.isJumping() && this.UpWard.bValue && MoveMeHelp.moveKeysPressed() && MoveMeHelp.getCuttingSpeed() > 0.1) {
                        double d = Minecraft.player.fallDistance != 0.0f ? 0.42 : (Minecraft.player.motionY = this.NoVanillaKick.bValue ? -0.02 : 0.0);
                        if (this.tickerFinalling % 2 == 0) {
                            Entity.motiony = this.NoVanillaKick.bValue ? -0.05 : -1.0E-6;
                        }
                    } else if (!Minecraft.player.isSneaking() || !this.DownWard.bValue) {
                        if (this.NoVanillaKick.bValue && this.timeFlying.hasReached(50.0)) {
                            float yport = 0.035f;
                            Minecraft.player.motionY = yport * (float)(this.tickerFinalling % 2 != 1 ? -1 : 1);
                        } else {
                            Minecraft.player.motionY = 0.0;
                            Entity.motiony = -1.0E-6;
                        }
                    }
                } else {
                    this.tickerFinalling = 0;
                    this.timeFlying.reset();
                }
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Jartex")) {
                e.ground = false;
            }
            if (this.Mode.currentMode.equalsIgnoreCase("StormHVH")) {
                e.ground = false;
                float yport = 0.1f;
                double y = e.getPosY();
                if (Speed.posBlock(Minecraft.player.posX, e.getPosY() - 0.11, Minecraft.player.posZ) && !MoveMeHelp.isBlockAboveHead()) {
                    y += 0.1;
                } else if (MoveMeHelp.isBlockAboveHead()) {
                    y -= (double)0.3f;
                    yport = (float)((double)yport + 0.1);
                }
                y += (double)(Minecraft.player.ticksExisted % 2 == 0 ? yport : -yport);
                if (Minecraft.player.ticksExisted % 2 != 0) {
                    Minecraft.player.fallDistance += yport * 2.0f;
                }
                if (Minecraft.player.fallDistance > 19.0f) {
                    Minecraft.player.fallDistance = 19.0f;
                }
                e.setPosY(y);
            }
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (Minecraft.player != null && Fly.mc.world != null && this.actived && event.getPacket() instanceof CPacketConfirmTeleport && this.DisableOnFlag.bValue) {
            if (this.Mode.currentMode.equalsIgnoreCase("MatrixChunk")) {
                this.toggle(false);
                MoveMeHelp.setSpeed(0.0);
                Minecraft.player.jumpMovementFactor = 0.0f;
                if (Minecraft.player.motionY == 0.0) {
                    Minecraft.player.motionY = -0.0078;
                }
            }
            if (this.Mode.currentMode.equalsIgnoreCase("MatrixOld")) {
                this.toggle(false);
                if (Minecraft.player.motionY >= 0.0) {
                    Minecraft.player.motionY = -0.023214;
                }
            }
        }
    }

    static {
        flySpeed = 0.0;
    }
}

