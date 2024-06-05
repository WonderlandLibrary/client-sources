package com.boomer.client.module.modules.combat;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.event.events.render.Render2DEvent;
import com.boomer.client.event.events.render.Render3DEvent;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.GLUtil;
import com.boomer.client.utils.MathUtils;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.font.Fonts;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.utils.value.impl.EnumValue;
import com.boomer.client.utils.value.impl.NumberValue;
import net.minecraft.block.*;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class KillAura extends Module {
    public EntityLivingBase target;
    private List<EntityLivingBase> targets = new ArrayList<>();
    private NumberValue<Integer> cps = new NumberValue<>("CPS", 9, 1, 20, 1);
    private NumberValue<Float> range = new NumberValue<>("Range", 4.2F, 1.0F, 7.0F, 0.1F);
    private NumberValue<Float> blockrange = new NumberValue<>("Block Range", 7.0F, 1.0F, 15.0F, 0.1F);
    private NumberValue<Integer> maxTargets = new NumberValue<>("Max Targets", 3, 1, 5, 1);
    private NumberValue<Integer> switchSpeed = new NumberValue<>("Switch Speed", 300, 100, 1000, 50);
    private BooleanValue players = new BooleanValue("Players",  true);
    private BooleanValue animals = new BooleanValue("Animals", false);
    private BooleanValue monsters = new BooleanValue("Monsters",  false);
    private BooleanValue invisibles = new BooleanValue("Invisibles",  false);
    private BooleanValue autoblock = new BooleanValue("AutoBlock",  true);
    private BooleanValue dynamic = new BooleanValue("Dynamic",  true);
    private BooleanValue targetesp = new BooleanValue("Target ESP",  true);
    private BooleanValue teams = new BooleanValue("Teams",  false);
    private EnumValue<sortmode> sortMode = new EnumValue<>("Sort Mode", sortmode.FOV);
    private EnumValue<mode> Mode = new EnumValue<>("Mode", mode.SINGLE);
    private TimerUtil timerUtil = new TimerUtil();
    private TimerUtil switchTimer = new TimerUtil();
    private long time;
    private boolean groundTicks;
    private float[] serverAngles = new float[2];
    private float[] prevRotations = new float[2];
    private int switchI;

    public KillAura() {
        super("KillAura", Category.COMBAT, new Color(0xAF0E00).getRGB());
        addValues(cps, range, blockrange, maxTargets, switchSpeed, autoblock, dynamic, teams, targetesp, players, animals, monsters, invisibles, sortMode, Mode);
        setDescription("hit those shitma users");
        setRenderlabel("Shitma Awareness");
    }

    @Override
    public void onDisable() {
        switchI = 0;
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        final Criticals criticals = (Criticals) Client.INSTANCE.getModuleManager().getModule("criticals");
        setSuffix(StringUtils.capitalize(Mode.getValue().name().toLowerCase()));
        final long ping = mc.getCurrentServerData() == null ? 0 : Math.min(50, Math.max(mc.getCurrentServerData().pingToServer, 110));
        final int pingDelay = Math.round(ping / 10);
        if (AutoHeal.doSoup || AutoHeal.healing || AutoApple.doingStuff || mc.thePlayer.isSpectator()) return;
        switch (Mode.getValue()) {
            case SINGLE:
                target = getTarget();
                if (event.isPre()) {
                    if (target != null) {
                        final float[] rots = getRotationsToEnt(target, mc.thePlayer);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                    } else timerUtil.reset();
                } else {
                    if (target != null) {
                        if (dynamic.isEnabled()) {
                            if (target.hurtResistantTime == 0) {
                                if (timerUtil.sleep(ping * 3)) attackEntity(target);
                            } else if (target.hurtResistantTime <= 9 + pingDelay) attackEntity(target);
                        } else if (timerUtil.sleep(1000 / cps.getValue())) attackEntity(target);
                    } else timerUtil.reset();
                    if (canBlock() && nearbyTargets(true))
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                }
                break;
            case SWITCH:
                final ArrayList<EntityLivingBase> targs = new ArrayList<>();
                mc.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> isTargetable((EntityLivingBase) entity, mc.thePlayer, false)).forEach(potentialTarget -> {
                    if (targs.size() < maxTargets.getValue()) {
                        targs.add((EntityLivingBase) potentialTarget);
                    }
                });
                if (switchTimer.sleep(switchSpeed.getValue()) && !targs.isEmpty()) {
                    if (switchI + 1 > targs.size() - 1 || targs.size() < 2) {
                        switchI = 0;
                    } else {
                        switchI++;
                    }
                }
                if (!targs.isEmpty()) target = targs.get(Math.min(switchI, targs.size() - 1));
                if (target != null) {
                    if (!isTargetable(target, mc.thePlayer, false)) target = null;
                }
                if (target != null && mc.thePlayer != null) {
                    if (event.isPre()) {
                        final float[] rots = getRotationsToEnt(target, mc.thePlayer);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                    } else {
                        if (dynamic.isEnabled()) {
                            if (target.hurtResistantTime == 0) {
                                if (timerUtil.sleep(ping * 3)) attackEntity(target);
                            } else if (target.hurtResistantTime <= 9 + pingDelay) attackEntity(target);
                        } else if (timerUtil.sleep(1000 / cps.getValue())) attackEntity(target);
                    }
                } else {
                    timerUtil.reset();
                }
                if (!event.isPre() && canBlock() && nearbyTargets(true)) {
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                }
                break;
            case SMOOTH:
                target = getTarget();
                if (event.isPre()) {
                    if (target != null) {
                        if (criticals.isEnabled() && mc.thePlayer.onGround && !isInsideBlock()) {
                            event.setY(event.getY() + 0.01);
                            event.setOnGround(false);
                        }
                        final float[] dstAngle = getRotationsToEnt(target, mc.thePlayer);
                        final float[] srcAngle = new float[]{serverAngles[0], serverAngles[1]};
                        serverAngles = smoothAngle(dstAngle, srcAngle);
                        event.setYaw(serverAngles[0]);
                        event.setPitch(serverAngles[1]);
                        if (getDistance(prevRotations) < 16) {
                            if (dynamic.isEnabled()) {
                                if (target.hurtResistantTime == 0) {
                                    if (timerUtil.sleep(ping * 3)) attackEntity(target);
                                } else if (target.hurtResistantTime <= 8 + pingDelay) attackEntity(target);
                            } else if (timerUtil.sleep(1000 / cps.getValue())) attackEntity(target);
                        }
                    } else {
                        serverAngles[0] = (mc.thePlayer.rotationYaw);
                        serverAngles[1] = (mc.thePlayer.rotationPitch);
                        timerUtil.reset();
                    }
                }
                break;
        }
    }

    @Handler
    public void onRender3D(Render3DEvent event) {
        if (targetesp.isEnabled() && target != null) {
            final double x = RenderUtil.interpolate(target.posX, target.lastTickPosX, event.getPartialTicks());
            final double y = RenderUtil.interpolate(target.posY, target.lastTickPosY, event.getPartialTicks());
            final double z = RenderUtil.interpolate(target.posZ, target.lastTickPosZ, event.getPartialTicks());
            drawEntityESP(x - RenderManager.renderPosX, y + target.height + 0.1 - target.height - RenderManager.renderPosY, z - RenderManager.renderPosZ, target.height, 0.65, new Color(target.hurtTime > 0 ? 0xE33726 : RenderUtil.getRainbow(4000, 0,0.85f)));
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        final Criticals criticals = (Criticals) Client.INSTANCE.getModuleManager().getModule("criticals");
        if (event.isSending() && (event.getPacket() instanceof C03PacketPlayer)) {
            if (groundTicks) {
                event.setCanceled(true);
                groundTicks = false;
            }
        }
        if (event.isSending() && event.getPacket() instanceof C03PacketPlayer) {
            prevRotations[0] = ((C03PacketPlayer) event.getPacket()).getYaw();
            prevRotations[1] = ((C03PacketPlayer) event.getPacket()).getPitch();
        }
        if (event.isSending() && (event.getPacket() instanceof C0APacketAnimation)) {
            //if (event.isSending() && (event.getPacket() instanceof C02PacketUseEntity)) {
            if (criticals.isEnabled() && target != null && criticals.Mode.getValue() != Criticals.mode.AREA51) crit();
        }
    }

    private boolean isTeammate(EntityLivingBase entityLivingBase) {
        if (teams.isEnabled()) {
            String name = entityLivingBase.getDisplayName().getFormattedText();
            StringBuilder append = new StringBuilder().append("ยง");
            if (name.startsWith(append.append(mc.thePlayer.getDisplayName().getFormattedText().charAt(1)).toString())) {
                return true;
            }
        }
        return false;
    }

    private void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        try {
            ResourceLocation skin = target.getLocationSkin();
            mc.getTextureManager().bindTexture(skin);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
            Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (Exception e) {
        }
    }

    private int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.75F) | 0xFF000000;
    }

    private void drawEntityESP(double x, double y, double z, double height, double width, Color color) {
        GL11.glPushMatrix();
        GLUtil.setGLCap(3042, true);
        GLUtil.setGLCap(3553, false);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.8f);
        GL11.glBlendFunc(770, 771);
        GLUtil.setGLCap(2848, true);
        GL11.glDepthMask(true);
        RenderUtil.BB(new AxisAlignedBB(x - width + 0.25, y, z - width + 0.25, x + width - 0.25, y + height, z + width - 0.25), new Color(color.getRed(), color.getGreen(), color.getBlue(), 120).getRGB());
        RenderUtil.OutlinedBB(new AxisAlignedBB(x - width + 0.25, y, z - width + 0.25, x + width - 0.25, y + height, z + width - 0.25), 1, color.getRGB());
        GLUtil.revertAllCaps();
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    private boolean nearbyTargets(boolean block) {
        for (Object e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase && isTargetable((EntityLivingBase) e, mc.thePlayer, block)) {
                return true;
            }
        }
        return false;
    }

    private boolean canBlock() {
        return autoblock.isEnabled() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }


    private void crit() {
        final Criticals criticals = (Criticals) Client.INSTANCE.getModuleManager().getModule("criticals");
        //final float[] HYPIXELOFFSETS = {0.069F, 0.069F, 0.00099F, 0.00099F}; // 0.039, 0.059, 0.01, 0
        //final float[] HYPIXELOFFSETS = {0.039F, 0.059F, 0.01F, 0.0f};
        //final double[] HYPIXELOFFSETS = {0.06142999976873398D, 0.012511000037193298D};
        final double[] HYPIXELOFFSETS = {0.062f + 1.0E-5F, 0.001f + 1.0E-5F, 0.062f + 1.0E-5F, 0.051f};
        final float[] NCPOFFSETS = {0.0624f, 0.0f, 1.13E-4F, 0.0f};

        if (!(MathUtils.getBlockUnderPlayer(mc.thePlayer, 0.06) instanceof BlockStairs) && canCrit() && !(MathUtils.getBlockUnderPlayer(mc.thePlayer, 0.06) instanceof BlockSlab)) {
            if (criticals.Mode.getValue() == Criticals.mode.HYPIXEL) {
                double delay = 95;
                if (target.hurtResistantTime == 0) {
                    delay = 425;
                }
                if (System.currentTimeMillis() - time >= delay && target.hurtResistantTime <= 12) {
                    for (double offset : HYPIXELOFFSETS) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                    }
                    groundTicks = true;
                    time = System.currentTimeMillis();
                }
            } else if (criticals.Mode.getValue() == Criticals.mode.NCP) {
                if (canCrit() && target.hurtResistantTime <= 13) {
                    for (double offset : NCPOFFSETS) {
                        //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                    }
                }
            }
        }
    }

    private float[] smoothAngle(float[] dst, float[] src) {
        float[] smoothedAngle = new float[2];
        smoothedAngle[0] = (src[0] - dst[0]);
        smoothedAngle[1] = (src[1] - dst[1]);
        MathUtils.constrainAngle(smoothedAngle);
        smoothedAngle[0] = (src[0] - smoothedAngle[0] / 100 * MathUtils.getRandomInRange(14, 24));
        smoothedAngle[1] = (src[1] - smoothedAngle[1] / 100 * MathUtils.getRandomInRange(3, 8));
        return smoothedAngle;
    }

    private float getDistance(float[] original) {
        final float yaw = MathHelper.wrapAngleTo180_float(serverAngles[0]) - MathHelper.wrapAngleTo180_float(original[0]);
        final float pitch = MathHelper.wrapAngleTo180_float(serverAngles[1]) - MathHelper.wrapAngleTo180_float(original[1]);
        return (float) Math.sqrt(yaw * yaw + pitch * pitch);
    }

    private void attackEntity(Entity entity) {
        if (canBlock()) {
            mc.playerController.syncCurrentPlayItem();
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));

    }

    private boolean canCrit() {
        return mc.thePlayer.onGround && !Client.INSTANCE.getModuleManager().getModule("speed").isEnabled() && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.fallDistance == 0;
    }

    private EntityLivingBase getTarget() {
        targets.clear();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) e;
                if (isTargetable(ent, mc.thePlayer, false)) {
                    targets.add(ent);
                }
            }
        }
        if (targets.isEmpty()) return null;
        switch (sortMode.getValue()) {
            case FOV:
                targets.sort(Comparator.comparingDouble(target -> yawDist((EntityLivingBase) target)));
                break;
            case HEALTH:
                targets.sort(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth()));
                break;
            case DISTANCE:
                targets.sort(Comparator.comparingDouble(target -> mc.thePlayer.getDistanceToEntity(target)));
                break;
        }
        return targets.get(0);
    }

    private double yawDist(EntityLivingBase e) {
        if (e != null) {
            final Vec3 difference = e.getPositionVector().addVector(0.0, e.getEyeHeight() / 2.0f, 0.0).subtract(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0));
            final double d = Math.abs(mc.thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f)) % 360.0f;
            return (d > 180.0f) ? (360.0f - d) : d;
        }
        return 0;
    }

    private float[] getRotationsToEnt(Entity ent, EntityPlayerSP playerSP) {
        final double differenceX = ent.posX - playerSP.posX;
        final double differenceY = (ent.posY + ent.height) - (playerSP.posY + playerSP.height);
        final double differenceZ = ent.posZ - playerSP.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, playerSP.getDistanceToEntity(ent)) * 180.0D / Math.PI);
        final float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
        final float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
        return new float[]{finishedYaw, -finishedPitch};
    }

    private boolean isTargetable(EntityLivingBase entity, EntityPlayerSP clientPlayer, boolean b) {
        return entity != clientPlayer && entity.isEntityAlive() && !isTeammate(entity) && !AntiBot.getBots().contains(entity) && !Client.INSTANCE.getFriendManager().isFriend(entity.getName()) && !(entity.isInvisible() && !invisibles.isEnabled()) && clientPlayer.getDistanceToEntity(entity) <= (b ? blockrange.getValue() : range.getValue()) && ((entity instanceof EntityPlayer && players.isEnabled()) || ((entity instanceof EntityMob || entity instanceof EntityGolem) && monsters.isEnabled()) || (entity instanceof IAnimals && animals.isEnabled()));
    }

    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z), mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if ((boundingBox != null) && (mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private enum sortmode {
        FOV, HEALTH, DISTANCE
    }

    private enum mode {
        SINGLE, SWITCH, SMOOTH
    }
}
