/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.combat;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketSend;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.combat.Killaura;
import drunkclient.beta.IMPL.Module.impl.misc.Scaffold69;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.helper.Helper;
import drunkclient.beta.UTILS.world.MovementUtil;
import drunkclient.beta.UTILS.world.PacketUtil;
import drunkclient.beta.UTILS.world.PlayerUtil;
import drunkclient.beta.UTILS.world.Timer;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

public class Criticals
extends Module {
    private int groundTicks;
    Timer timer = new Timer();
    private double watchdogOffsets;
    int safeTicks;
    public static Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])CritMode.values(), (Enum)CritMode.Redesky);

    public Criticals() {
        super("Criticals", new String[]{"crits", "crit"}, Type.COMBAT, "No");
        this.setColor(new Color(235, 194, 138).getRGB());
        this.addValues(mode);
    }

    @Override
    public void onEnable() {
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        this.groundTicks = MovementUtil.isOnGround() ? this.groundTicks + 1 : 0;
        this.watchdogOffsets = new Random().nextDouble() / 30.0;
    }

    private void sendCriticalPacket(double x, double y, double z, boolean ground) {
        double x2 = Minecraft.thePlayer.posX + x;
        double y2 = Minecraft.thePlayer.posY + y;
        double z2 = Minecraft.thePlayer.posZ + z;
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(x2, y2, z2, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch, ground));
    }

    @EventHandler
    public void onPacket(EventPacketSend e) {
        if (Minecraft.thePlayer == null) {
            return;
        }
        switch (mode.getModeAsString()) {
            case "Edit": {
                if (!ModuleManager.getModuleByName("KillAura").isEnabled()) return;
                if (!(e.getPacket() instanceof C03PacketPlayer)) return;
                Killaura killaura = (Killaura)ModuleManager.getModuleByName("KillAura");
                if (Killaura.target != null) {
                    EntityLivingBase entity = Killaura.target;
                    int hurtResistantTime = entity.hurtResistantTime;
                    switch (hurtResistantTime) {
                        case 17: {
                            ((C03PacketPlayer)e.getPacket()).onGround = true;
                            ((C03PacketPlayer)e.getPacket()).setPosY(((C03PacketPlayer)e.getPacket()).getPositionY() + ThreadLocalRandom.current().nextDouble(0.001, 0.0011));
                            Helper.sendMessage("17");
                            return;
                        }
                        case 18: {
                            ((C03PacketPlayer)e.getPacket()).onGround = true;
                            ((C03PacketPlayer)e.getPacket()).setPosY(((C03PacketPlayer)e.getPacket()).getPositionY() + 0.00722435151);
                            Helper.sendMessage("18");
                            return;
                        }
                        case 19: {
                            ((C03PacketPlayer)e.getPacket()).onGround = true;
                            ((C03PacketPlayer)e.getPacket()).setPosY(((C03PacketPlayer)e.getPacket()).getPositionY() + ThreadLocalRandom.current().nextDouble(0.001, 0.0011));
                            Helper.sendMessage("19");
                            return;
                        }
                        case 20: {
                            ((C03PacketPlayer)e.getPacket()).onGround = true;
                            ((C03PacketPlayer)e.getPacket()).setPosY(((C03PacketPlayer)e.getPacket()).getPositionY() + 0.00722435151);
                            Helper.sendMessage("20");
                            return;
                        }
                    }
                }
                return;
            }
            case "Packet": {
                if (!(e.getPacket() instanceof C0APacketAnimation)) return;
                if (Killaura.target == null) return;
                if (Minecraft.thePlayer.ticksExisted % 10 != 0) return;
                this.sendCriticalPacket(0.0, 0.0325, 0.0, true);
                this.sendCriticalPacket(0.0, 0.0, 0.0, false);
                this.sendCriticalPacket(0.0, 1.0E-5, 0.0, false);
                this.sendCriticalPacket(0.0, 0.0, 0.0, false);
                Helper.sendMessage("Critical");
                return;
            }
            case "Packet2": {
                if (!(e.getPacket() instanceof C02PacketUseEntity)) return;
                C02PacketUseEntity packet = (C02PacketUseEntity)e.getPacket();
                if (packet.getAction() != C02PacketUseEntity.Action.ATTACK) return;
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.1625, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 4.0E-6, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0E-6, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                return;
            }
            case "Hypixel": {
                if (e.getPacket() instanceof C0APacketAnimation) {
                    if (!this.timer.hasElapsed(490L, true)) return;
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.0031311231111, Minecraft.thePlayer.posZ, false));
                    return;
                }
            }
            case "Verus": {
                double x = Minecraft.thePlayer.posX;
                double y = Minecraft.thePlayer.posY;
                double z = Minecraft.thePlayer.posZ;
                if (!(e.getPacket() instanceof C0APacketAnimation)) return;
                if (Killaura.target.hurtTime >= 5) return;
                if (!this.shouldCrit()) return;
                if (Killaura.target == null) return;
                if (Killaura.target.hurtTime != 0) {
                    PacketUtil.sendC04(x, y + 0.42, z, false, false);
                    PacketUtil.sendC04(x, y, z, false, false);
                    return;
                }
                if (this.safeTicks <= 4) return;
                PacketUtil.sendC04(x, y + 0.42, z, false, false);
                PacketUtil.sendC04(x, y, z, false, false);
                this.safeTicks = 0;
                return;
            }
            case "AAC4": {
                if (!(e.getPacket() instanceof C0APacketAnimation)) return;
                if (this.timer.hasElapsed(490L, true)) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.0031311231111, Minecraft.thePlayer.posZ, false));
                }
                this.timer.reset();
                return;
            }
            case "Redesky": {
                if (!(e.getPacket() instanceof C0APacketAnimation)) return;
                if (Killaura.target == null) return;
                Minecraft.thePlayer.onCriticalHit(Killaura.target);
                return;
            }
        }
    }

    public boolean shouldCrit() {
        if (!Minecraft.thePlayer.onGround) return false;
        if (!Scaffold69.getOnRealGround(Minecraft.thePlayer, 1.0E-4)) return false;
        if (!Minecraft.thePlayer.isCollidedVertically) return false;
        boolean bl = true;
        boolean isRealGround = bl;
        if (!isRealGround) return false;
        if (Minecraft.thePlayer.isInWater()) return false;
        if (Minecraft.thePlayer.isOnLadder()) return false;
        return true;
    }

    private boolean canCrit() {
        if (PlayerUtil.isInLiquid()) return false;
        if (!Minecraft.thePlayer.isSwingInProgress) return false;
        return true;
    }

    @EventHandler
    public void ChangeSuffix(EventPreUpdate e) {
        this.setSuffix(mode.getModeAsString());
        ++this.safeTicks;
    }

    public static enum CritMode {
        Hypixel,
        Redesky,
        AAC4,
        Jump,
        Packet,
        Packet2,
        Verus,
        Edit;

    }
}

