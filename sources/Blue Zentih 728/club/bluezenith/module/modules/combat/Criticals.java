package club.bluezenith.module.modules.combat;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AttackEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {

    final MillisTimer timer = new MillisTimer();
    final ModeValue mode = new ModeValue("Mode", "Packet", "Packet", "Watchdog", "Edit").setIndex(1);
    final IntegerValue hurtTime = new IntegerValue("Hurt Time", 1, 1, 20, 1).setIndex(2);
    final BooleanValue groundOnly = new BooleanValue("Ground Check", true).setIndex(3);
    final BooleanValue alwaysCrit = new BooleanValue("Always Crit", false).setIndex(4).showIf(() -> mode.is("Watchdog"));
    final FloatValue packetValue = new FloatValue("Offset", 0.00000000000001f, 0f, 1f, 0.01f).showIf(() -> mode.is("Packet")).setIndex(5);

    int editStage = -1;
    Entity lastHitentity;

    public Criticals() {
        super("Criticals", ModuleCategory.COMBAT);
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {

    }

    @Listener
    public void onAttack(AttackEvent e) {
        if(!(e.target instanceof EntityLivingBase)) return;
        switch (mode.get()) {
            case "Packet":
                if (e.isPre() && ((EntityLivingBase) e.target).hurtTime < (lastHitentity == e.target ? hurtTime.get() : 2) && (mc.thePlayer.onGround || !groundOnly.get())) {
                    PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-10, mc.thePlayer.posZ, false));
                    PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-13, mc.thePlayer.posZ, false));
                }
            break;

            case "Watchdog":
            case "Edit":
                if (e.isPre() && ((EntityLivingBase) e.target).hurtTime < (lastHitentity == e.target ? hurtTime.get() : 2) && (mc.thePlayer.onGround || !groundOnly.get()) && (editStage == -1 || mode.is("Edit"))) {
                    editStage = 0;
                }
            break;
        }
        lastHitentity = e.target;
    }
    @Override
    public String getTag() {
        return mode.get();
    }

    @Listener
    public void onPacket(PacketEvent event) {
        if(event.packet instanceof C03PacketPlayer && (mode.is("Edit") || mode.is("Watchdog")) && editStage > -1) {
            switch (editStage) {
                case 0:
                    ((C03PacketPlayer) event.packet).y += 0.00000000001f;
                    ((C03PacketPlayer) event.packet).onGround = false;
                    editStage++;
                break;

                case 1:
                    ((C03PacketPlayer) event.packet).y += 0.0000000000001f;
                    ((C03PacketPlayer) event.packet).onGround = false;
                    editStage++;
                break;

                case 2:
                    if(mode.is("Edit")) {
                        ((C03PacketPlayer) event.packet).y = mc.thePlayer.posY;
                        ((C03PacketPlayer) event.packet).onGround = false;
                        editStage++;
                    }
                    if(mode.is("Watchdog")) {
                        if(alwaysCrit.get()) {
                            editStage = -1;
                        } else editStage++;
                    }
                break;

                case 3:
                    editStage = -1;
                break;

                default:
                    editStage = -1;

            }
        }
    }
}
