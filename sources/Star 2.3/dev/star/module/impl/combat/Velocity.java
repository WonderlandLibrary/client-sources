package dev.star.module.impl.combat;

import dev.star.event.impl.game.WorldEvent;
import dev.star.event.impl.network.PacketReceiveEvent;
import dev.star.event.impl.network.PacketSendEvent;
import dev.star.event.impl.player.MoveEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.Setting;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.utils.misc.MathUtils;
import dev.star.utils.player.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet", "Packet", "Watchdog","Matrix", "Tick", "Stack", "C0F Cancel");
    private final NumberSetting horizontal = new NumberSetting("Horizontal", 0, 100, 0, 1);
    private final NumberSetting vertical = new NumberSetting("Vertical", 0, 100, 0, 1);
    private final NumberSetting chance = new NumberSetting("Chance", 100, 100, 0, 1);
    private final BooleanSetting onlyWhileMoving = new BooleanSetting("Only while moving", false);
    private final BooleanSetting staffCheck = new BooleanSetting("Staff check", false);
    private final BooleanSetting dmgboost = new BooleanSetting("Damage Boost", false);


    private long lastDamageTimestamp, lastAlertTimestamp;
    private boolean cancel;
    private int ticks;

    public Velocity() {
        super("Velocity", Category.COMBAT, "Reduces your knockback");
        Setting.addParent(mode, m -> m.is("Packet"), horizontal, vertical, staffCheck);
        Setting.addParent(mode, m -> m.is("Watchdog"), dmgboost);

        this.addSettings(mode, dmgboost,horizontal, vertical, chance, onlyWhileMoving, staffCheck);
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent event) {
        if (mode.is("C0F Cancel")) {
            if (event.getPacket() instanceof C0FPacketConfirmTransaction && mc.thePlayer.hurtTime > 0) {
                event.cancel();
            }
        }
    }

    @Override
    public void onPacketReceiveEvent(PacketReceiveEvent e) {
        this.setSuffix(mode.getMode());
        if ((onlyWhileMoving.isEnabled() && !MovementUtils.isMoving()) || (chance.getValue() != 100 && MathUtils.getRandomInRange(0, 100) > chance.getValue()))
            return;
        Packet<?> packet = e.getPacket();
        switch (mode.getMode()) {
            case "Packet":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) e.getPacket();
                    if (mc.thePlayer != null && s12.getEntityID() == mc.thePlayer.getEntityId()) {
                        if (cancel(e)) return;
                        s12.motionX *= horizontal.getValue() / 100.0;
                        s12.motionZ *= horizontal.getValue() / 100.0;
                        s12.motionY *= vertical.getValue() / 100.0;
                    }
                } else if (packet instanceof S27PacketExplosion) {
                    if (cancel(e)) return;
                    S27PacketExplosion s27 = (S27PacketExplosion) e.getPacket();
                    s27.motionX *= horizontal.getValue() / 100.0;
                    s27.motionZ *= horizontal.getValue() / 100.0;
                    s27.motionY *= vertical.getValue() / 100.0;
                } else if (e.getPacket() instanceof S19PacketEntityStatus) {
                    S19PacketEntityStatus s19 = (S19PacketEntityStatus) e.getPacket();
                    if (mc.thePlayer != null && s19.getEntityId() == mc.thePlayer.getEntityId() && s19.getOpCode() == 2) {
                        lastDamageTimestamp = System.currentTimeMillis();
                    }
                }
                break;
            case "Watchdog":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) e.getPacket();
                    if (mc.thePlayer != null && s12.getEntityID() == mc.thePlayer.getEntityId()) {
                        if(MovementUtils.isMoving() && !mc.thePlayer.onGround) {

                            ticks = 3;
                        } else {
                            s12.motionX *= 0 / 100.0;
                            s12.motionZ *= 0/ 100.0;
                        }
                        break;
                    }
                } else if (packet instanceof S27PacketExplosion) {
                    S27PacketExplosion s27 = (S27PacketExplosion) e.getPacket();
                    s27.motionX *= 20;
                    s27.motionZ *= 100;
                    s27.motionY *= 20;
                } else if (e.getPacket() instanceof S19PacketEntityStatus) {
                    S19PacketEntityStatus s19 = (S19PacketEntityStatus) e.getPacket();
                    if (mc.thePlayer != null && s19.getEntityId() == mc.thePlayer.getEntityId() && s19.getOpCode() == 2) {
                        lastDamageTimestamp = System.currentTimeMillis();
                    }
                }
                break;
            case "C0F Cancel":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) e.getPacket();
                    if (mc.thePlayer != null && s12.getEntityID() == mc.thePlayer.getEntityId()) {
                        e.cancel();
                    }
                }
                if (packet instanceof S27PacketExplosion) {
                    e.cancel();
                }
                break;
            case "Stack":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) packet;
                    cancel = !cancel;
                    if (cancel) {
                        e.cancel();
                    }
                }
                if (packet instanceof S27PacketExplosion) {
                    e.cancel();
                }
                break;
            case "Matrix":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) e.getPacket();
                    if (mc.thePlayer != null && s12.getEntityID() == mc.thePlayer.getEntityId()) {
                        s12.motionX *= 5 / 100.0;
                        s12.motionZ *= 5 / 100.0;
                        s12.motionY *= 100 / 100.0;
                    }
                }
                break;
            case "Tick":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) e.getPacket();
                    if (mc.thePlayer != null && s12.getEntityID() == mc.thePlayer.getEntityId() && mc.thePlayer.ticksExisted % 3 == 0) {
                        s12.motionX *= 5 / 100.0;
                        s12.motionZ *= 5 / 100.0;
                        s12.motionY *= 100 / 100.0;
                    }
                }
                break;
        }
    }

    @Override
    public void onMoveEvent(MoveEvent event)
    {
        this.setSuffix(mode.getMode());

        if (mode.is("Watchdog")) {
            if (ticks > 1) {
                ticks--;


                    if(ticks == 1 && dmgboost.isEnabled()) {
                        MovementUtils.partialStrafePercent(85);
                        event.setX(mc.thePlayer.motionX = 0 / 100.0);
                        event.setZ(mc.thePlayer.motionZ = 0 / 100.0);
                    }

            }
        }
    }

    private boolean cancel(PacketReceiveEvent e) {
        if (staffCheck.isEnabled() && System.currentTimeMillis() - lastDamageTimestamp > 500) {
            if (System.currentTimeMillis() - lastAlertTimestamp > 250) {
                NotificationManager.post(NotificationType.WARNING, "Velocity", "Suspicious knockback detected!", 2);
                lastAlertTimestamp = System.currentTimeMillis();
            }
            return true;
        }
        if (horizontal.getValue() == 0 && vertical.getValue() == 0) {
            e.cancel();
            return true;
        }
        return false;
    }

}
