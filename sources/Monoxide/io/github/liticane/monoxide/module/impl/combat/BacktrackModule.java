package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.util.render.RenderUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.event.minecraft.render.Render3DEvent;
import io.github.liticane.monoxide.listener.event.minecraft.world.WorldLoadEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.VecUtil;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.util.player.combat.FightUtil;
import io.github.liticane.monoxide.util.world.entities.EntitiesUtil;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

@ModuleData(name = "Backtrack", description = "Delay packets to get higher reach", category = ModuleCategory.COMBAT)
public class BacktrackModule extends Module {

    private final NumberValue<Float> minRange = new NumberValue<>("Min Range", this, 2.9f, 0f, 6f, 1);
    private final NumberValue<Float> maxStartRange = new NumberValue<>("Max Start Range", this, 3.2f, 2f, 4f, 1);
    private final NumberValue<Float> maxActiveRange = new NumberValue<>("Max Active Range", this, 5f, 2f, 6f, 1);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", this, 100, 0, 500, 0);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", this, 200, 0, 1000, 1);
    private final NumberValue<Integer> maxHurtTime = new NumberValue<>("Max Hurt Time", this, 6, 0, 10, 0);
    private final BooleanValue syncHurtTime = new BooleanValue("Sync HT with Ping",this, true);
    private final NumberValue<Float> minReleaseRange = new NumberValue<>("Min Release Range", this, 3.2F, 2f, 6f, 1);
    private final BooleanValue onlyKillAura = new BooleanValue("Only KillAura",this, true);
    private final BooleanValue resetOnVelocity = new BooleanValue("Release on Velocity",this, true);
    private final BooleanValue resetOnLagging = new BooleanValue("Release on Flag", this, true);

    public BooleanValue players = new BooleanValue("Players", this, true);
    public BooleanValue animals = new BooleanValue("Animals", this, true);
    public BooleanValue monsters = new BooleanValue("Monsters", this, true);
    public BooleanValue invisible = new BooleanValue("Invisible", this, true);

    // New
    private final ArrayList<Packet<INetHandler>> storedPackets = new ArrayList<>();
    private final ArrayList<Entity> targets = new ArrayList<>();

    private KillAuraModule killAura;

    private final TimeHelper freezeTimer = new TimeHelper();
    private Entity targetEntity = null;
    private boolean freezingNeeded = false;

    @Override
    public String getSuffix() {
        return this.minDelay.getValue() + "-" + this.maxDelay.getValue() + " MS";
    }

    @Listen
    public void onPacket(PacketEvent event) {
        if (killAura == null)
            killAura = ModuleManager.getInstance().getModule(KillAuraModule.class);

        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        Packet<?> packet = event.getPacket();
        WorldClient theWorld = mc.theWorld;

        if (event.getType() == PacketEvent.Type.INCOMING) {
            if (packet instanceof S14PacketEntity) {
                handleS14PacketEntity(packet, theWorld, event);
            } else {
                handleNonS14Packet(packet, event);
            }
        } else if (packet instanceof C02PacketUseEntity) {
            handleC02PacketUseEntity(packet, theWorld);
        }
    }

    private void handleS14PacketEntity(Packet<?> packet, WorldClient theWorld, PacketEvent event) {
        S14PacketEntity entityPacket = (S14PacketEntity) packet;
        Entity entity = entityPacket.getEntity(theWorld);

        if (!(entity instanceof EntityLivingBase)) {
            return;
        }

        entity.serverPosX += entityPacket.func_149062_c();
        entity.serverPosY += entityPacket.func_149061_d();
        entity.serverPosZ += entityPacket.func_149064_e();

        double x = entity.serverPosX / 32.0;
        double y = entity.serverPosY / 32.0;
        double z = entity.serverPosZ / 32.0;

        boolean isValidTarget = (!onlyKillAura.getValue() || killAura.isEnabled() || freezingNeeded)
                && FightUtil.isValidWithPlayer(entity, 100, invisible.getValue(), players.getValue(), animals.getValue(), monsters.getValue());

        if (isValidTarget) {
            double afterRange = calculateAfterRange(x, y, z);
            double beforeRange = calculateBeforeRange(entity);

            if (beforeRange <= maxStartRange.getValue() && isInRange(afterRange, minRange.getValue(), maxActiveRange.getValue()) && afterRange > beforeRange + 0.02 && ((EntityLivingBase) entity).hurtTime <= calculateMaxHurtTime()) {
                handleValidTarget(entity, event);
                return;
            }
        }

        if (freezingNeeded) {
            handleFreezing(entity, event);
            return;
        }

        handleNonCancelled(entity, x, y, z, entityPacket, event);
    }

    @SuppressWarnings("unchecked")
    private void handleNonS14Packet(Packet<?> packet, PacketEvent event) {
        if ((packet instanceof S12PacketEntityVelocity && resetOnVelocity.getValue()) || (packet instanceof S08PacketPlayerPosLook && resetOnLagging.getValue())) {
            storedPackets.add((Packet<INetHandler>) packet);
            event.setCancelled(true);
            releasePackets();
        } else if (freezingNeeded && !event.isCancelled()) {
            if (packet instanceof S19PacketEntityStatus) {
                if (((S19PacketEntityStatus) packet).logicOpcode == (byte) 2) {
                    return;
                }
            }
            storedPackets.add((Packet<INetHandler>) packet);
            event.setCancelled(true);
        }
    }

    private void handleC02PacketUseEntity(Packet<?> packet, WorldClient theWorld) {
        C02PacketUseEntity useEntityPacket = (C02PacketUseEntity) packet;
        if (useEntityPacket.getAction() == C02PacketUseEntity.Action.ATTACK && freezingNeeded) {
            targetEntity = useEntityPacket.getEntityFromWorld(theWorld);
        }
    }

    private double calculateAfterRange(double x, double y, double z) {
        AxisAlignedBB afterBB = new AxisAlignedBB(x - 0.4, y - 0.1, z - 0.4, x + 0.4, y + 1.9, z + 0.4);
        Vec3 eyes = mc.thePlayer.getPositionEyes(1F);
        return VecUtil.getNearestPointBB(eyes, afterBB).distanceTo(eyes);
    }

    private double calculateBeforeRange(Entity entity) {
        return EntitiesUtil.getDistanceToEntityBox(mc.thePlayer, entity);
    }

    private boolean isInRange(double value, double minValue, double maxValue) {
        return value >= minValue && value <= maxValue;
    }

    private void handleValidTarget(Entity entity, PacketEvent event) {
        if (!freezingNeeded) {
            freezeTimer.reset();
            freezingNeeded = true;
        }
        if (!targets.contains(entity)) {
            targets.add(entity);
        }
        event.setCancelled(true);
    }

    private void handleFreezing(Entity entity, PacketEvent event) {
        if (!targets.contains(entity)) {
            targets.add(entity);
        }
        event.setCancelled(true);
    }

    private void handleNonCancelled(Entity entity, double x, double y, double z, S14PacketEntity entityPacket, PacketEvent event) {
        float f = entityPacket.func_149060_h() ? (entityPacket.func_149066_f() * 360) / 256.0f : entity.rotationYaw;
        float f1 = entityPacket.func_149060_h() ? (entityPacket.func_149063_g() * 360) / 256.0f : entity.rotationPitch;

        entity.setPositionAndRotation2(x, y, z, f, f1, 3, false);
        entity.onGround = entityPacket.onGround;
        event.setCancelled(true);
    }

    @Listen
    public void onRender3D(Render3DEvent event) {
        if (!freezingNeeded) {
            return;
        }

        GL11.glPushMatrix();
        GlStateManager.disableAlpha();

        try {
            for (Entity entity : targets) {
                renderFrozenEntity(entity, event);
            }
        } catch (ConcurrentModificationException ignored) {
            // ignored
        }

        GlStateManager.enableAlpha();
        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    private void renderFrozenEntity(Entity entity, Render3DEvent event) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return;
        }

        EntityOtherPlayerMP mp = new EntityOtherPlayerMP(mc.theWorld, ((EntityOtherPlayerMP) entity).getGameProfile());
        mp.posX = entity.serverPosX / 32.0;
        mp.posY = entity.serverPosY / 32.0;
        mp.posZ = entity.serverPosZ / 32.0;
        mp.prevPosX = mp.posX;
        mp.prevPosY = mp.posY;
        mp.prevPosZ = mp.posZ;
        mp.lastTickPosX = mp.posX;
        mp.lastTickPosY = mp.posY;
        mp.lastTickPosZ = mp.posZ;
        mp.rotationYaw = entity.rotationYaw;
        mp.rotationPitch = entity.rotationPitch;
        mp.rotationYawHead = ((EntityOtherPlayerMP) entity).rotationYawHead;
        mp.prevRotationYaw = mp.rotationYaw;
        mp.prevRotationPitch = mp.rotationPitch;
        mp.prevRotationYawHead = mp.rotationYawHead;
        mp.swingProgress = ((EntityOtherPlayerMP) entity).swingProgress;
        mp.swingProgressInt = ((EntityOtherPlayerMP) entity).swingProgressInt;
        mc.renderManager.renderEntitySimple(mp, event.getPartialTicks());
    }

    @Listen
    public void onMotion(UpdateMotionEvent event) {
        if (event.getType() != UpdateMotionEvent.Type.POST) {
            return;
        }

        if (!freezingNeeded)
            return;

        if (freezeTimer.hasReached(maxDelay.getValue())) {
            releasePackets();
            return;
        }

        if (!targets.isEmpty()) {
            boolean shouldRelease = false;

            for (Entity entity : targets) {
                double x = entity.serverPosX / 32.0;
                double y = entity.serverPosY / 32.0;
                double z = entity.serverPosZ / 32.0;

                AxisAlignedBB entityBB = new AxisAlignedBB(x - 0.4, y - 0.1, z - 0.4, x + 0.4, y + 1.9, z + 0.4);

                double range = entityBB.getLookingTargetRange(mc.thePlayer);

                if (range == Double.MAX_VALUE) {
                    Vec3 eyes = mc.thePlayer.getPositionEyes(1F);
                    range = VecUtil.getNearestPointBB(eyes, entityBB).distanceTo(eyes) + 0.075;
                }

                if (range <= minRange.getValue()) {
                    shouldRelease = true;
                    break;
                }

                Entity entity1 = targetEntity;
                if (entity1 != entity) {
                    continue;
                }

                if (freezeTimer.hasReached(minDelay.getValue())) {
                    if (range >= minReleaseRange.getValue() && !targets.isEmpty()) {
                        shouldRelease = true;
                        break;
                    }
                }
            }

            if (shouldRelease) {
                releasePackets();
            }
        }
    }

    @Listen
    public void onWorld(WorldLoadEvent event) {
        targetEntity = null;
        targets.clear();

        if (event.getWorldClient() == null) {
            storedPackets.clear();
        }
    }

    public void releasePackets() {
        targetEntity = null;
        INetHandlerPlayClient netHandler = mc.getNetHandler();

        if (storedPackets.isEmpty()) {
            return;
        }

        while (!storedPackets.isEmpty()) {
            Packet<INetHandler> packet = storedPackets.remove(0);

            try {
                try {
                    packet.processPacket(netHandler);
                } catch (Exception ignored) {
                    // Ignored
                }
            } catch (ThreadQuickExitException ignored) {
                // Ignored
            }
        }

        while (!targets.isEmpty()) {
            Entity entity = targets.remove(0);

            if (!entity.isDead) {
                double x = entity.serverPosX / 32.0;
                double y = entity.serverPosY / 32.0;
                double z = entity.serverPosZ / 32.0;

                entity.setPosition(x, y, z);
            }
        }

        freezingNeeded = false;
    }

    private int calculateMaxHurtTime() {
        int ping = EntitiesUtil.getPing(mc.thePlayer);

        return maxHurtTime.getValue() + (syncHurtTime.getValue() ? (int) Math.ceil(ping / 50.0) : 0);
    }

}