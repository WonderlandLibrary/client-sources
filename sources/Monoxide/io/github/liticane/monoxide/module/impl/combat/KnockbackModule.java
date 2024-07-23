package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.module.ModuleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MovingObjectPosition;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.input.ClickingEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.random.RandomUtil;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.util.player.PlayerHandler;
import io.github.liticane.monoxide.util.player.combat.FightUtil;

@ModuleData(name = "Knockback", description = "Helps you get people in combos", category = ModuleCategory.COMBAT)
public class KnockbackModule extends Module {

    public ModeValue mode = new ModeValue("Mode", this, new String[]{"Sprint Reset", "Legit", "Legit Fast", "Single Packet", "Normal Packet", "Double Packet"});
    public BooleanValue auraOnly = new BooleanValue("KillAura Only", this, true);
    public NumberValue<Long> minDelay = new NumberValue<>("Minimum Delay", this, 50L, 0L, 100L, 0);
    public NumberValue<Long> maxDelay = new NumberValue<>("Maximum Delay", this, 60L, 0L, 100L, 0);

    private EntityLivingBase target;

    private boolean isHit = false;
    private final TimeHelper attackTimer = new TimeHelper();

    private long delay = 0L;

    @Override
    public String getSuffix() {
    	return mode.getValue();
    }
    
    @Listen
    public final void onMotion(UpdateMotionEvent updateMotionEvent) {
        switch (mode.getValue()) {
            case "Legit":
                getGameSettings().keyBindSprint.pressed = true;

                if (isHit && attackTimer.hasReached(delay / 2)) {
                    isHit = false;
                    mc.thePlayer.setSprinting(false);
                }
                break;
            case "Legit Fast":
                if (isHit) {
                    mc.thePlayer.sprintingTicksLeft = 0;
                }
                break;
        }
    }

    @Listen
    public final void onAttack(ClickingEvent clickingEvent) {
        KillAuraModule killAura = ModuleManager.getInstance().getModule(KillAuraModule.class);
        boolean setTarget = false;
        if(!auraOnly.getValue() && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
            target = (EntityLivingBase) mc.objectMouseOver.entityHit;
            setTarget = true;
        }
        if(killAura.isEnabled() && KillAuraModule.currentTarget != null && FightUtil.getRange(KillAuraModule.currentTarget) <= ModuleManager.getInstance().getModule(KillAuraModule.class).rotateRange.getValue()) {
            target = KillAuraModule.currentTarget;
            setTarget = true;
        }
        if(!setTarget)
            target = null;
        if(target != null) {
            switch (mode.getValue()) {
                case "Legit":
                case "Legit Fast":
                    if (!isHit) {
                        isHit = true;
                        attackTimer.reset();
                        delay = (long) RandomUtil.randomBetween(minDelay.getValue(), maxDelay.getValue());
                    }
                    break;
                case "Sprint Reset":
                    PlayerHandler.shouldSprintReset = true;
                    break;
                case "Single Packet":
                    if (mc.thePlayer.isSprinting()) {
                        mc.thePlayer.setSprinting(false);
                    }
                    mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    mc.thePlayer.serverSprintState = true;
                    break;
                case "Normal Packet":
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    mc.thePlayer.serverSprintState = true;
                    break;
                case "Double Packet":
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    mc.thePlayer.serverSprintState = true;
                    break;
            }
        }
    }

}
