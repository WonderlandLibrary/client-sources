package wtf.shiyeno.modules.impl.movement;

import net.minecraft.item.UseAction;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventDamage;
import wtf.shiyeno.events.impl.player.EventNoSlow;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.util.misc.DamageUtil;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.movement.MoveUtil;

@FunctionAnnotation(name="NoSlow", type=Type.Movement)
public class NoSlowFunction
        extends Function {
    public ModeSetting mode = new ModeSetting("Мод", "Really World", "Vanilla", "Really World", "GrimAC");
    private DamageUtil damageUtil = new DamageUtil();
    public TimerUtil timerUtil = new TimerUtil();
    public BooleanOption matrix = new BooleanOption("Фикс свапа", true);
    public BooleanOption matrix1 = new BooleanOption("Работать только в воде", true).setVisible(() -> this.mode.is("Really World"));
    public BooleanOption matrix2 = new BooleanOption("Работать только от урона", true).setVisible(() -> this.mode.is("Really World"));
    public BooleanOption matrix3 = new BooleanOption("Выключить работать от урона", true).setVisible(() -> this.mode.is("Really World"));
    boolean restart = false;
    boolean restart1 = false;

    public NoSlowFunction() {
        this.addSettings(this.mode, this.matrix, this.matrix1, this.matrix2, this.matrix3);
    }

    @Override
    public void onEvent(Event event) {
        IPacket iPacket;
        EventPacket packetEvent;
        EventPacket eventPacket;
        EventNoSlow eventNoSlow;
        if (NoSlowFunction.mc.player.isElytraFlying()) {
            return;
        }
        if (event instanceof EventNoSlow) {
            eventNoSlow = (EventNoSlow)event;
            this.handleEventUpdate(eventNoSlow);
        } else if (event instanceof EventDamage) {
            EventDamage damage = (EventDamage)event;
            this.damageUtil.processDamage(damage);
        } else if (event instanceof EventPacket && (eventPacket = (EventPacket)event).isReceivePacket()) {
            this.damageUtil.onPacketEvent(eventPacket);
        }
        if (this.matrix.get() && event instanceof EventPacket && (packetEvent = (EventPacket)event).isReceivePacket() && (iPacket = packetEvent.getPacket()) instanceof SHeldItemChangePacket) {
            SHeldItemChangePacket packetHeldItemChange = (SHeldItemChangePacket)iPacket;
            if (this.timerUtil.hasTimeElapsed(100L)) {
                NoSlowFunction.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlowFunction.mc.player.inventory.currentItem % 8 + 1));
                NoSlowFunction.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlowFunction.mc.player.inventory.currentItem));
                this.timerUtil.reset();
            }
            event.setCancel(true);
        }
        if (this.mode.is("Really World")) {
            EventPacket e;
            if (this.matrix1.get() && !NoSlowFunction.mc.player.isInWater()) {
                return;
            }
            if (event instanceof EventUpdate) {
                if (!this.restart) {
                    this.restart1 = true;
                }
                if (this.restart) {
                    if (this.restart1) {
                        this.timerUtil.reset();
                        this.restart1 = false;
                    }
                    if (this.timerUtil.hasTimeElapsed(1600L, true)) {
                        this.restart = false;
                    }
                }
            }
            if (event instanceof EventPacket && (e = (EventPacket)event).getPacket() instanceof SEntityVelocityPacket && ((SEntityVelocityPacket)e.getPacket()).getEntityID() == NoSlowFunction.mc.player.getEntityId() && !NoSlowFunction.mc.player.isElytraFlying()) {
                if (this.matrix3.get()) {
                    return;
                }
                this.restart = false;
                this.restart1 = false;
                this.restart = true;
                this.restart1 = true;
            }
            if (event instanceof EventNoSlow) {
                eventNoSlow = (EventNoSlow)event;
                if ((NoSlowFunction.mc.player.isInWater() || this.restart) && NoSlowFunction.mc.player.getActiveHand() == Hand.MAIN_HAND && NoSlowFunction.mc.player.getHeldItemOffhand().getUseAction() == UseAction.NONE) {
                    NoSlowFunction.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                    eventNoSlow.setCancel(true);
                }
                if (NoSlowFunction.mc.player.getActiveHand() == Hand.OFF_HAND) {
                    if (this.matrix2.get() && !NoSlowFunction.mc.player.isInWater() && !this.restart) {
                        return;
                    }
                    NoSlowFunction.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlowFunction.mc.player.inventory.currentItem % 8 + 1));
                    NoSlowFunction.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlowFunction.mc.player.inventory.currentItem));
                    eventNoSlow.setCancel(true);
                }
            }
        }
    }

    private void handleEventUpdate(EventNoSlow eventNoSlow) {
        if (NoSlowFunction.mc.player.isHandActive()) {
            switch (this.mode.get()) {
                case "Vanilla": {
                    eventNoSlow.setCancel(true);
                    break;
                }
                case "GrimAC": {
                    this.handleGrimACMode(eventNoSlow);
                }
            }
        }
    }

    private void handleGrimACMode(EventNoSlow noSlow) {
        if (NoSlowFunction.mc.player.getHeldItemOffhand().getUseAction() == UseAction.BLOCK && NoSlowFunction.mc.player.getActiveHand() == Hand.MAIN_HAND || NoSlowFunction.mc.player.getHeldItemOffhand().getUseAction() == UseAction.EAT && NoSlowFunction.mc.player.getActiveHand() == Hand.MAIN_HAND) {
            return;
        }
        if (NoSlowFunction.mc.player.getActiveHand() == Hand.MAIN_HAND) {
            NoSlowFunction.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
            noSlow.setCancel(true);
            return;
        }
        noSlow.setCancel(true);
        this.sendItemChangePacket();
    }

    private void sendItemChangePacket() {
        if (MoveUtil.isMoving()) {
            NoSlowFunction.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlowFunction.mc.player.inventory.currentItem % 8 + 1));
            NoSlowFunction.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlowFunction.mc.player.inventory.currentItem));
        }
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        this.restart1 = false;
        this.restart = false;
    }
}