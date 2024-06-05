/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.combat;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.movement.Fly;
import cc.diablo.module.impl.movement.Speed;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S2APacketParticles;

public class Criticals
extends Module {
    public ModeSetting critMode = new ModeSetting("Cricials mode", "Watchdog", "Watchdog", "Packet", "Packet2");
    public NumberSetting speed = new NumberSetting("Ticks", 6.0, 0.0, 20.0, 1.0);
    public int airTime;
    public int waitTicks;

    public Criticals() {
        super("Criticals", "Spoof body slamming faggots", 0, Category.Combat);
        this.addSettings(this.critMode, this.speed);
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        this.setDisplayName("Criticals\u00a77 " + this.critMode.getMode() + " | " + this.speed.getVal());
        if (!ModuleManager.getModule(Criticals.class).isToggled()) {
            switch (this.critMode.getMode()) {
                case "Watchdog": {
                    C02PacketUseEntity packet;
                    if (this.speed.getVal() != 0.0) {
                        C02PacketUseEntity packet2;
                        if ((double)Criticals.mc.thePlayer.ticksExisted % this.speed.getVal() != 0.0 || event.getPacket() instanceof S2APacketParticles || event.getPacket().toString().contains("S2APacketParticles") || !(event.getPacket() instanceof C02PacketUseEntity) || event.getPacket() instanceof S2APacketParticles || event.getPacket() instanceof C0APacketAnimation || (packet2 = (C02PacketUseEntity)event.getPacket()).getAction() != C02PacketUseEntity.Action.ATTACK || !Criticals.mc.thePlayer.isCollidedVertically || !this.hurtTime(packet2.getEntityFromWorld(Minecraft.theWorld)) || ModuleManager.getModule(Speed.class).toggled || ModuleManager.getModule(Fly.class).toggled) break;
                        Criticals.critHypixel();
                        break;
                    }
                    if ((double)Criticals.mc.thePlayer.ticksExisted % this.speed.getVal() != 0.0 || event.getPacket() instanceof S2APacketParticles || event.getPacket().toString().contains("S2APacketParticles") || !(event.getPacket() instanceof C02PacketUseEntity) || event.getPacket() instanceof S2APacketParticles || event.getPacket() instanceof C0APacketAnimation || (packet = (C02PacketUseEntity)event.getPacket()).getAction() != C02PacketUseEntity.Action.ATTACK || !Criticals.mc.thePlayer.isCollidedVertically || !this.hurtTime(packet.getEntityFromWorld(Minecraft.theWorld)) || ModuleManager.getModule(Speed.class).toggled || ModuleManager.getModule(Fly.class).toggled) break;
                    Criticals.critHypixel();
                    break;
                }
                case "Packet": {
                    C02PacketUseEntity packet;
                    if (event.getPacket() instanceof S2APacketParticles || event.getPacket().toString().contains("S2APacketParticles") || !(event.getPacket() instanceof C02PacketUseEntity) || event.getPacket() instanceof S2APacketParticles || event.getPacket() instanceof C0APacketAnimation || (packet = (C02PacketUseEntity)event.getPacket()).getAction() != C02PacketUseEntity.Action.ATTACK || !Criticals.mc.thePlayer.isCollidedVertically || !this.hurtTime(packet.getEntityFromWorld(Minecraft.theWorld)) || ModuleManager.getModule(Speed.class).toggled || ModuleManager.getModule(Fly.class).toggled) break;
                    Criticals.crit();
                    break;
                }
                case "Packet2": {
                    C02PacketUseEntity packet;
                    if (!(event.getPacket() instanceof C02PacketUseEntity) || (packet = (C02PacketUseEntity)event.getPacket()).getAction() != C02PacketUseEntity.Action.ATTACK) break;
                    this.airTime = 4;
                    PacketHelper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(0.0, 0.1232225, 0.0, false));
                    PacketHelper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(0.0, 1.0554E-9, 0.0, false));
                    PacketHelper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(0.0, 0.0, 0.0, true));
                }
            }
        }
    }

    private boolean hurtTime(Entity entity) {
        return entity != null && entity.hurtResistantTime <= 14;
    }

    public static void crit() {
        double[] array3;
        double[] array2 = array3 = new double[4];
        double[] array = array3;
        array[0] = 0.05f;
        array[1] = 0.0016f;
        array[2] = 0.03f;
        array[3] = 0.0016f;
        double[] array5 = array2;
        int length = array3.length;
        for (int i = 0; i < length; ++i) {
            double offset = array2[i];
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + offset, Minecraft.getMinecraft().thePlayer.posZ, false));
        }
    }

    public static void critHypixel() {
        double[] array3;
        double[] array2 = array3 = new double[4];
        double[] array = array3;
        array[0] = 0.05230430000745058;
        array[1] = 0.0018999999595806004;
        array[2] = 0.032912439999932944;
        array[3] = 0.0013234999999595805;
        double[] array5 = array2;
        int length = array3.length;
        for (int i = 0; i < length; ++i) {
            double offset = array2[i];
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + offset, Minecraft.getMinecraft().thePlayer.posZ, false));
        }
    }
}

