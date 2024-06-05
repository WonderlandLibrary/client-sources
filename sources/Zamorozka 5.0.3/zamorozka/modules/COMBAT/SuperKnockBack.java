package zamorozka.modules.COMBAT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketEntityAction;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class SuperKnockBack extends Module {

	public SuperKnockBack() {
		super("SuperKnockBack", 0, Category.COMBAT);
	}

	@EventTarget
	public void onAttack(AttackEvent event) {
        Entity entity = event.getTargetEntity();
        if (entity instanceof EntityLivingBase) {
            if (mc.player.isSprinting())
                mc.player.setSprinting(false);
                    
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    
            mc.player.setSprinting(true);
            mc.player.serverSprintState = true;
        }
    }
}