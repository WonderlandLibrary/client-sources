/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Crosshair;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class FreeCam
extends Module {
    public static Module get;
    private float yaw;
    private float pitch;
    private float yawHead;
    private float gamma;
    private EntityPlayer other;
    private float old;
    public static EntityPlayer fakePlayer;
    private double oldX;
    private double oldY;
    private double oldZ;
    private double newX;
    private double newY;
    private double newZ;
    private double oldYaw;
    private double oldPitch;
    private boolean isSneaking = false;
    public static String coords;
    float scale = 0.0f;
    float lqExtend = 0.0f;
    float scaledAlpha = 0.0f;

    public FreeCam() {
        super("FreeCam", 0, Module.Category.PLAYER);
        get = this;
        this.settings.add(new Settings("Speed", 0.5f, 1.0f, 0.1f, this));
        this.settings.add(new Settings("PosRender", true, (Module)this));
        this.settings.add(new Settings("LiquidPort", true, (Module)this));
        this.settings.add(new Settings("PortMode", "Matrix", this, new String[]{"Vanilla", "Matrix"}, () -> this.currentBooleanValue("LiquidPort")));
        this.settings.add(new Settings("NoFlightKick", true, (Module)this));
        this.settings.add(new Settings("NoDessaturate", false, (Module)this));
    }

    public static void matrixTp(double x, double y, double z, boolean canElytra) {
        boolean elytraEquiped;
        int de = (int)MathUtils.clamp(Minecraft.player.getDistance(x, y, z) / 11.0, 1.0, 17.0);
        int de2 = (int)(Math.abs(y / 11.0) + Math.abs(Minecraft.player.getDistance(x, Minecraft.player.posY, z) / 2.5));
        boolean bl = elytraEquiped = Minecraft.player.inventory.armorInventory.get(2).getItem() == Items.ELYTRA;
        if (canElytra) {
            for (int i = 0; i < MathUtils.clamp(de2, 1, 17); ++i) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
            if (elytraEquiped) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
            } else {
                int elytra = InventoryUtil.getElytra();
                if (elytra != -1) {
                    FreeCam.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Minecraft.player);
                    FreeCam.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Minecraft.player);
                }
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
                if (elytra != -1) {
                    FreeCam.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Minecraft.player);
                    FreeCam.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Minecraft.player);
                }
            }
            Minecraft.player.setPositionAndUpdate(x, y, z);
        } else {
            for (int i = 0; i < MathUtils.clamp(de2, 0, 19); ++i) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
            Minecraft.player.setPositionAndUpdate(x, y, z);
        }
    }

    void port(double x, double y, double z, String mode) {
        if (mode.equalsIgnoreCase("Vanilla")) {
            Minecraft.player.setPositionAndUpdate(this.oldX, this.oldY, this.oldZ);
        } else {
            FreeCam.matrixTp(x, y, z, InventoryUtil.getElytra() != -1);
        }
    }

    private void toggleFakePlayer(boolean spawn) {
        if (FreeCam.mc.world == null) {
            return;
        }
        if (spawn) {
            fakePlayer = new EntityOtherPlayerMP(FreeCam.mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), "\u00a76" + Minecraft.player.getName() + "\u00a7f > \u00a7cNPC\u00a7r"));
            FreeCam.fakePlayer.inventory.currentItem = Minecraft.player.inventory.currentItem;
            fakePlayer.setPosition(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
            FreeCam.fakePlayer.rotationYaw = Minecraft.player.rotationYaw;
            FreeCam.fakePlayer.rotationPitch = Minecraft.player.rotationPitch;
            FreeCam.fakePlayer.rotationYawHead = Minecraft.player.rotationYawHead;
            FreeCam.fakePlayer.rotationPitchHead = Minecraft.player.rotationPitchHead;
            FreeCam.fakePlayer.renderYawOffset = FreeCam.fakePlayer.rotationYaw;
            BlockPos pt = new BlockPos(FreeCam.fakePlayer.posX, FreeCam.fakePlayer.posY - 0.9999, FreeCam.fakePlayer.posZ);
            FreeCam.fakePlayer.onGround = FreeCam.mc.world.getBlockState(pt) != null && FreeCam.mc.world.getBlockState(pt).getCollisionBoundingBox(FreeCam.mc.world, pt) != null;
            FreeCam.fakePlayer.fallDistance = Minecraft.player.fallDistance;
            FreeCam.mc.world.addEntityToWorld(462462999, fakePlayer);
        } else {
            if (fakePlayer != null) {
                FreeCam.mc.world.removeEntityFromWorld(462462999);
            }
            fakePlayer = null;
        }
    }

    @Override
    public void onToggled(boolean actived) {
        ResourceLocation shader = new ResourceLocation("shaders/post/desaturate.json");
        if (actived) {
            if (!this.currentBooleanValue("NoDessaturate")) {
                FreeCam.mc.entityRenderer.loadShader(shader);
            }
            this.isSneaking = Minecraft.player.isSneaking();
            Minecraft.player.motionY = 0.0;
            Minecraft.player.setNoGravity(true);
            this.oldX = Minecraft.player.posX;
            this.oldY = Minecraft.player.posY;
            this.oldZ = Minecraft.player.posZ;
            this.oldYaw = Minecraft.player.rotationYaw;
            this.oldPitch = Minecraft.player.rotationPitch;
            this.toggleFakePlayer(true);
        } else {
            block9: {
                if (FreeCam.mc.entityRenderer.isShaderActive()) {
                    FreeCam.mc.entityRenderer.theShaderGroup = null;
                }
                Minecraft.player.setNoGravity(false);
                Minecraft.player.motionY = -0.0031f;
                Minecraft.player.jumpMovementFactor = 0.02f;
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed() / (double)1.06f);
                if (this.currentBooleanValue("LiquidPort")) {
                    try {
                        if (this.doTeleport()) {
                            this.port(this.newX, this.newY, this.newZ, this.currentMode("PortMode"));
                            break block9;
                        }
                        Minecraft.player.setPositionAndUpdate(this.oldX, this.oldY, this.oldZ);
                        Minecraft.player.rotationYaw = (float)this.oldYaw;
                        Minecraft.player.rotationPitch = (float)this.oldPitch;
                        FreeCam.mc.renderGlobal.loadRenderers();
                    } catch (Exception formatException) {
                        Client.msg("\u0427\u0442\u043e-\u0442\u043e \u043f\u043e\u0448\u043b\u043e \u043d\u0435 \u0442\u0430\u043a.", true);
                        System.out.println(this.name + " \u0427\u0442\u043e-\u0442\u043e \u043f\u043e\u0448\u043b\u043e \u043d\u0435 \u0442\u0430\u043a.");
                    }
                } else {
                    Minecraft.player.setPositionAndUpdate(this.oldX, this.oldY, this.oldZ);
                }
            }
            this.toggleFakePlayer(false);
        }
        super.onToggled(actived);
    }

    @EventTarget
    public void onMotionUpdate(EventPlayerMotionUpdate motionUpdate) {
        if (Minecraft.player != null && FreeCam.mc.world != null && !Minecraft.player.isDead && this.actived && Minecraft.player.ticksExisted > 4 && fakePlayer != null) {
            float prevRPH = Minecraft.player.rotationPitchHead;
            motionUpdate.setYaw(FreeCam.fakePlayer.rotationYaw);
            motionUpdate.setPitch(FreeCam.fakePlayer.rotationPitch);
            motionUpdate.setX(FreeCam.fakePlayer.posX);
            motionUpdate.setY(FreeCam.fakePlayer.posY);
            motionUpdate.setZ(FreeCam.fakePlayer.posZ);
            motionUpdate.setGround(FreeCam.fakePlayer.onGround);
            Minecraft.player.rotationPitchHead = prevRPH;
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (Minecraft.player != null && FreeCam.mc.world != null && !Minecraft.player.isDead && this.actived && Minecraft.player.ticksExisted > 4 && this.currentBooleanValue("NoFlightKick") && (event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketConfirmTransaction || event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.PositionRotation || event.getPacket() instanceof CPacketEntityAction || event.getPacket() instanceof CPacketConfirmTeleport)) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (Minecraft.player != null && FreeCam.mc.world != null && !Minecraft.player.isDead && this.actived && Minecraft.player.ticksExisted > 4 && (event.getPacket() instanceof SPacketConfirmTransaction || event.getPacket() instanceof SPacketPlayerPosLook)) {
            Packet packet = event.getPacket();
            if (packet instanceof SPacketPlayerPosLook) {
                SPacketPlayerPosLook look = (SPacketPlayerPosLook)packet;
                if (fakePlayer != null) {
                    Vec3d vec3d = new Vec3d(look.getX(), look.getY(), look.getZ());
                    if (fakePlayer.getDistanceToVec3d(vec3d) > 0.2) {
                        FreeCam.fakePlayer.rotationYaw = look.getYaw();
                        FreeCam.fakePlayer.rotationYaw = look.getPitch();
                        fakePlayer.setPosition(look.getX(), look.getY(), look.getZ());
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    @Override
    public void onUpdate() {
        Minecraft.player.fallDistance = 0.0f;
        Minecraft.player.isInWeb = false;
        if (fakePlayer != null) {
            fakePlayer.setHealth(Minecraft.player.getHealth());
            fakePlayer.setAbsorptionAmount(Minecraft.player.getAbsorptionAmount());
            FreeCam.fakePlayer.entityCollisionReduction = 1.0f;
            FreeCam.fakePlayer.hurtTime = Minecraft.player.hurtTime;
            fakePlayer.setPrimaryHand(Minecraft.player.getPrimaryHand());
            FreeCam.fakePlayer.openContainer = Minecraft.player.openContainer;
            if (Minecraft.player.getActiveHand() != null && Minecraft.player.isHandActive()) {
                fakePlayer.setActiveHand(Minecraft.player.getActiveHand());
            } else {
                fakePlayer.resetActiveHand();
            }
            fakePlayer.setBurning(Minecraft.player.isBurning());
            FreeCam.fakePlayer.inventory = Minecraft.player.inventory;
            FreeCam.fakePlayer.isSwingInProgress = Minecraft.player.isSwingInProgress;
            FreeCam.fakePlayer.swingingHand = Minecraft.player.swingingHand;
            fakePlayer.setSneaking(this.isSneaking);
        }
        Minecraft.player.noClip = true;
        this.newX = Minecraft.player.posX;
        this.newY = Minecraft.player.posY;
        this.newZ = Minecraft.player.posZ;
        float totalSpeed = this.currentFloatValue("Speed") * 2.0f;
        double motion = Entity.Getmotiony;
        if (Minecraft.player.isJumping()) {
            motion += 1.5 * (double)totalSpeed;
        }
        if (Minecraft.player.isSneaking()) {
            motion -= 1.5 * (double)totalSpeed;
        }
        Minecraft.player.motionY += motion;
        Minecraft.player.motionY = MathUtils.clamp(Minecraft.player.motionY / 3.0, (double)(-totalSpeed), (double)totalSpeed);
        double speed2 = MathUtils.clamp(Math.sqrt(Entity.Getmotionx * Entity.Getmotionx + Entity.Getmotionz * Entity.Getmotionz) * 1.4, (double)(0.5f * totalSpeed), (double)(1.5f * totalSpeed));
        MoveMeHelp.setSpeed(speed2, 0.8f);
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByDouble(this.currentFloatValue("Speed"));
    }

    @EventTarget
    public void onMove(EventMove2 move) {
        if (this.actived) {
            if (MoveMeHelp.isMoving()) {
                move.ignoreHorizontal = true;
            }
            if (Minecraft.player.isJumping() || Minecraft.player.isSneaking()) {
                move.ignoreVertical = true;
            }
        }
    }

    @EventTarget
    public void onEvent3D(Event3D event) {
        if (this.actived && this.currentBooleanValue("PosRender") && (double)this.scale > 0.9 && fakePlayer != null) {
            EntityPlayer entity = fakePlayer;
            if (entity == null) {
                return;
            }
            boolean old = FreeCam.mc.gameSettings.viewBobbing;
            FreeCam.mc.gameSettings.viewBobbing = false;
            FreeCam.mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
            FreeCam.mc.gameSettings.viewBobbing = old;
            FreeCam.mc.entityRenderer.disableLightmap();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(0.01f);
            GL11.glEnable(2848);
            GlStateManager.shadeModel(7425);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            FreeCam.mc.entityRenderer.disableLightmap();
            float py = entity.height;
            assert (mc.getRenderViewEntity() != null);
            Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0);
            vec3d = vec3d.rotatePitch(-((float)Math.toRadians(Minecraft.player.rotationPitch)));
            Vec3d vec3d2 = vec3d.rotateYaw(-((float)Math.toRadians(Minecraft.player.rotationYaw)));
            RenderUtils.setupColor(ColorUtils.getColor(168, 62, 62), MathUtils.clamp(Minecraft.player.getDistanceToEntity(entity) * 3.0f, 26.0f, 255.0f));
            GL11.glLineWidth(0.001f);
            GL11.glBegin(1);
            GL11.glVertex3d(vec3d2.xCoord, (double)Minecraft.player.getEyeHeight() + vec3d2.yCoord, vec3d2.zCoord);
            GL11.glVertex3d(this.oldX - RenderManager.viewerPosX, this.oldY - RenderManager.viewerPosY, this.oldZ - RenderManager.viewerPosZ);
            GL11.glEnd();
            GlStateManager.resetColor();
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GlStateManager.shadeModel(7424);
            GlStateManager.resetColor();
        }
    }

    boolean doTeleport() {
        if (this.scale > 0.0f && this.currentBooleanValue("LiquidPort") && !this.isInsideBlock()) {
            return Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || FreeCam.mc.world.getBlockState(new BlockPos(this.oldX, this.oldY, this.oldZ)).getBlock() == Blocks.WATER || FreeCam.mc.world.getBlockState(new BlockPos(this.oldX, this.oldY, this.oldZ)).getBlock() == Blocks.LAVA || FreeCam.mc.world.getBlockState(new BlockPos(this.oldX, this.oldY, this.oldZ)).getBlock() == Blocks.WEB;
        }
        return false;
    }

    boolean isInsideBlock() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        float i = 0.0f;
        while (true) {
            double d = i;
            double d2 = Minecraft.player.isSneaking() ? 1.6 : 1.8;
            if (!(d < d2)) break;
            if (Speed.posBlock(x, y + (double)i, z) || Speed.posBlock(x + (double)0.275f, y + (double)i, z + (double)0.275f) || Speed.posBlock(x - (double)0.275f, y + (double)i, z - (double)0.275f) || Speed.posBlock(x + (double)0.275f, y + (double)i, z) || Speed.posBlock(x - (double)0.275f, y + (double)i, z) || Speed.posBlock(x, y + (double)i, z + (double)0.275f) || Speed.posBlock(x, y + (double)i, z - (double)0.275f) || Speed.posBlock(x + (double)0.275f, y + (double)i, z - (double)0.275f) || Speed.posBlock(x - (double)0.275f, y + (double)i, z + (double)0.275f)) {
                return true;
            }
            i += 0.2f;
        }
        return false;
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        boolean PosRender;
        if (this.actived && FreeCam.mc.currentScreen != null && FreeCam.mc.currentScreen instanceof GuiDownloadTerrain) {
            Minecraft.player.closeScreen();
        }
        if (this.actived && this.scale != 1.0f || !this.actived && this.scale != 0.0f) {
            this.scale = MathUtils.harp(this.scale, this.actived ? 1.0f : 0.0f, (float)Minecraft.frameTime * 0.0075f);
        }
        boolean bl = PosRender = this.currentBooleanValue("PosRender") && this.scale != 0.0f;
        if (this.doTeleport() && this.lqExtend != 1.0f || !this.doTeleport() && this.lqExtend != 0.0f || !this.actived && this.lqExtend != 0.0f) {
            this.lqExtend = MathUtils.harp(this.lqExtend, this.actived && this.doTeleport() ? 1.0f : 0.0f, (float)Minecraft.frameTime * 0.01f);
        }
        if (this.lqExtend == 1.0f && this.scaledAlpha != 1.0f || this.lqExtend != 1.0f && this.scaledAlpha != 0.0f || !this.actived && this.scaledAlpha != 0.0f) {
            this.scaledAlpha = MathUtils.harp(this.lqExtend, this.actived && this.doTeleport() && this.scaledAlpha == 1.0f ? 1.0f : 0.0f, (float)Minecraft.frameTime * 2.5E-4f);
        }
        if (PosRender) {
            coords = "Ydiff: \u00a7c" + Math.round(Minecraft.player.posY - this.oldY) + "\u00a7r DistXZ: \u00a7c" + Math.round(Minecraft.player.getSmoothDistanceToEntityXZ(fakePlayer)) + "\u00a7r";
            String lqTp = "Port on disable";
            CFontRenderer font = Fonts.noise_14;
            float extendX = 2.0f;
            float extendY = 1.0f;
            float w = (float)font.getStringWidth(coords) + extendX;
            float h = font.getHeight();
            float x = (float)(sr.getScaledWidth() / 2) - w / 2.0f;
            float x2 = (float)(sr.getScaledWidth() / 2) + w / 2.0f;
            float y = (float)(sr.getScaledHeight() / 2) + h * this.scale * 3.0f - extendY;
            float y2 = (float)(sr.getScaledHeight() / 2) + h * this.scale * 4.0f + extendY;
            float[] MxMy = Crosshair.get.crossPosMotions;
            x += MxMy[0];
            y += MxMy[1];
            x2 += MxMy[0];
            y2 += MxMy[1];
            float extLine = 1.5f;
            int bgColor = ColorUtils.swapAlpha(Integer.MAX_VALUE, (float)ColorUtils.getAlphaFromColor(Integer.MAX_VALUE) * this.scale);
            int bColor = ColorUtils.getColor(168, 62, 62, 255.0f * this.scale);
            GlStateManager.pushMatrix();
            RenderUtils.customScaledObject2D(x, y + 20.0f, x2 - x, 1.0f, this.scale);
            RenderUtils.drawBloomedFullShadowFullGradientRectBool(x - extLine, y, x2 + extLine, y2 + (h + extendY * 1.5f) * this.lqExtend, 3.0f, bgColor, bgColor, bgColor, bgColor, 70, 20, true, true, true);
            RenderUtils.resetBlender();
            RenderUtils.drawAlphedRect(x - extLine, y, x, y2 + (h + extendY * 1.5f) * this.lqExtend, bColor);
            RenderUtils.drawAlphedRect(x2, y, x2 + extLine, y2 + (h + extendY * 1.5f) * this.lqExtend, bColor);
            RenderUtils.drawAlphedRect(x + (float)font.getStringWidth("Ydiff: ") + 0.5f, y, x + (float)font.getStringWidth("Ydiff: ") + (float)font.getStringWidth("" + Math.round(Minecraft.player.posY - this.oldY)) + 2.5f, y2, Integer.MIN_VALUE);
            RenderUtils.drawAlphedRect(x + (float)font.getStringWidth(coords) - (float)font.getStringWidth("" + Math.round(Minecraft.player.getSmoothDistanceToEntityXZ(fakePlayer))) + 0.5f, y, x + (float)font.getStringWidth(coords) + 2.0f, y2, Integer.MIN_VALUE);
            font.drawString(coords, x + extendX / 2.0f, y + extendY * 2.0f, bgColor);
            if (this.scaledAlpha != 0.0f && this.scaledAlpha * 255.0f >= 26.0f) {
                font.drawString(lqTp, x + extendX / 2.0f, y2 + (h + extendY * 1.5f) * this.lqExtend - h, ColorUtils.swapAlpha(Integer.MIN_VALUE, (float)ColorUtils.getAlphaFromColor(Integer.MIN_VALUE) * this.scaledAlpha * this.scale));
            }
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.popMatrix();
        }
    }

    static {
        fakePlayer = null;
        coords = "";
    }
}

