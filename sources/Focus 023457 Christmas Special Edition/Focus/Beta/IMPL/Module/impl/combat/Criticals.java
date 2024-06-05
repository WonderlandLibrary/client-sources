package Focus.Beta.IMPL.Module.impl.combat;

import Focus.Beta.IMPL.Module.impl.move.Fly;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.UTILS.helper.Helper;
import Focus.Beta.UTILS.world.PacketUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

import java.awt.*;
import java.util.Iterator;
import java.util.Locale.Category;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketSend;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.UTILS.world.MovementUtil;
import Focus.Beta.UTILS.world.PlayerUtil;
import net.minecraft.network.play.server.S0BPacketAnimation;

import static Focus.Beta.IMPL.Module.impl.misc.Scaffold69.getOnRealGround;


public class Criticals extends Module {
    private int groundTicks;
    Focus.Beta.UTILS.world.Timer timer = new Focus.Beta.UTILS.world.Timer();
    private double watchdogOffsets;
    int safeTicks;
    public static Mode<Enum> mode = new Mode("Mode", "Mode", CritMode.values(), CritMode.Redesky);

    public Criticals() {
        super("Criticals", new String[]{"crits", "crit"}, Type.COMBAT, "No");
        this.setColor(new Color(235, 194, 138).getRGB());
        addValues(mode);

    }

    @Override
    public void onEnable() {

    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        groundTicks = MovementUtil.isOnGround() ? groundTicks + 1 : 0;
        watchdogOffsets = new Random().nextDouble() / 30;
    }

    private void sendCriticalPacket(double x, double y, double z, boolean ground){
        double x2 = mc.thePlayer.posX + x;
        double y2 = mc.thePlayer.posY + y;
        double z2 = mc.thePlayer.posZ + z;
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(x2, y2, z2, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, ground));
    }

    @EventHandler
    public void onPacket(EventPacketSend e) {
    	if(mc.thePlayer == null)
    		return;
        switch (mode.getModeAsString()) {
            case "Edit":
            if(ModuleManager.getModuleByName("KillAura").isEnabled() && e.getPacket() instanceof C03PacketPlayer){
                Killaura killaura = (Killaura) ModuleManager.getModuleByName("KillAura");
                if(killaura.target != null){
                    Entity entity = (Entity) killaura.target;
                    int hurtResistantTime = entity.hurtResistantTime;
                    switch (hurtResistantTime){
                        case 17:
                            ((C03PacketPlayer) e.getPacket()).onGround = true;
                            ((C03PacketPlayer) e.getPacket()).setPosY(((C03PacketPlayer) e.getPacket()).getPositionY() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                            Helper.sendMessage("17");
                            break;
                        case 18:
                            ((C03PacketPlayer) e.getPacket()).onGround = true;
                            ((C03PacketPlayer) e.getPacket()).setPosY(((C03PacketPlayer) e.getPacket()).getPositionY() + 0.0722435151D);
                            Helper.sendMessage("18");
                            break;
                        case 19:
                            ((C03PacketPlayer) e.getPacket()).onGround = true;
                            ((C03PacketPlayer) e.getPacket()).setPosY(((C03PacketPlayer) e.getPacket()).getPositionY() + ThreadLocalRandom.current().nextDouble(0.001D, 0.0011D));
                            Helper.sendMessage("19");
                            break;
                        case 20:
                            ((C03PacketPlayer) e.getPacket()).onGround = true;
                            ((C03PacketPlayer) e.getPacket()).setPosY(((C03PacketPlayer) e.getPacket()).getPositionY() + 0.0722435151D);
                            Helper.sendMessage("20");
                            break;
                    }
                }

            }
            break;
            case "Packet":
                if(e.getPacket() instanceof C0APacketAnimation && Killaura.target != null) {
                    if(this.mc.thePlayer.ticksExisted % 5 == 0) {
                        sendCriticalPacket(0.0, 0.0625, 0.0, true);
                        sendCriticalPacket(0.0, 0.0, 0.0, false);
                        sendCriticalPacket(0.0, 1.1E-5, 0.0, false);
                        sendCriticalPacket(0.0, 0.0, 0.0, false);
                        Helper.sendMessage("Critical");
                    }
                }


                break;
            case "Packet2":
                if(e.getPacket() instanceof C02PacketUseEntity){
                    C02PacketUseEntity packet = (C02PacketUseEntity)e.getPacket();
                    if(packet.getAction() == C02PacketUseEntity.Action.ATTACK){
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + .1625, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                    }
                }
                break;
            case "Hypixel":
                if (e.getPacket() instanceof C0APacketAnimation) {
                    if (timer.hasElapsed(490, true)) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0031311231111, mc.thePlayer.posZ, false));

                        }
                        break;
                    }

            case "Verus":
                final double x = this.mc.thePlayer.posX;
                final double y = this.mc.thePlayer.posY;
                final double z = this.mc.thePlayer.posZ;
                if(e.getPacket() instanceof  C0APacketAnimation && Killaura.target.hurtTime < 5 && this.shouldCrit() && Killaura.target != null){
                    if(Killaura.target.hurtTime != 0){
                        PacketUtil.sendC04(x, y + 0.42, z, false ,false);
                        PacketUtil.sendC04(x, y, z, false ,false);
                        break;
                    }
                    if(safeTicks > 4){
                        PacketUtil.sendC04(x, y + 0.42, z, false ,false);
                        PacketUtil.sendC04(x, y, z, false ,false);
                        this.safeTicks = 0;
                        break;
                    }
                }
                break;
            case"AAC4":
                if (e.getPacket() instanceof C0APacketAnimation) {
                    if (timer.hasElapsed ( 490, true )) {
                        mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.0031311231111, mc.thePlayer.posZ, false ) );

                    }
                    timer.reset ();
                }
                break;
            case "Redesky":
                if (e.getPacket() instanceof C0APacketAnimation && Killaura.target != null) {
                    mc.thePlayer.onCriticalHit(Killaura.target);
                    break;
                }
        }
    }
    public boolean shouldCrit() {
        final boolean isRealGround = this.mc.thePlayer.onGround && getOnRealGround(this.mc.thePlayer, 1.0E-4) && this.mc.thePlayer.isCollidedVertically;
        return isRealGround && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isOnLadder();
    }
    private boolean canCrit(){
        return !PlayerUtil.isInLiquid() && mc.thePlayer.isSwingInProgress;
    }

    @EventHandler
    public void ChangeSuffix(EventPreUpdate e){
        this.setSuffix(mode.getModeAsString());
        ++this.safeTicks;
    }
    public enum CritMode {
        Hypixel, Redesky, AAC4, Jump, Packet, Packet2, Verus, Edit
    }
}