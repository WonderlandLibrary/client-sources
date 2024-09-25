package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventAttack;
import none.module.Category;
import none.module.Module;
import none.valuesystem.NumberValue;

public class SuperKnockback extends Module{

	public SuperKnockback() {
		super("SuperKnockback", "SuperKnockback", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	public NumberValue<Integer> hurttime = new NumberValue<>("Hurttime", 12, 1, 20);

	@Override
	@RegisterEvent(events = EventAttack.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventAttack) {
			EventAttack ej = (EventAttack) event;
			if (ej.getEntity() == null) return;
			if (ej.getEntity() instanceof EntityLivingBase && ej.isPreAttack()) {
				EntityLivingBase entity = (EntityLivingBase) ej.getEntity();
				if (entity.hurtTime > hurttime.getObject())
					return;
				
				if (mc.thePlayer.isSprinting())
	                mc.getConnection().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));

	            mc.getConnection().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
	            mc.getConnection().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
	            mc.getConnection().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
	            mc.thePlayer.setSprinting(true);
	            mc.thePlayer.serverSprintState = true;
			}
		}
	}

}
