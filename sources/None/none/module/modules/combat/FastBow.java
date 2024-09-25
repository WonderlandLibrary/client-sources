package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class FastBow extends Module{
	
	public NumberValue<Integer> packetsValue = new NumberValue<>("Packets", 20, 3, 20);
	public BooleanValue auto = new BooleanValue("AutoFire", false);
	public FastBow() {
		super("FastBow", "FastBow", Category.COMBAT, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events= EventTick.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventTick) {
			if (!mc.thePlayer.isUsingItem())
	            return;
			if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
	            for (int i = 0; i < packetsValue.getObject(); i++) {
//	            	mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
//	            	mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C05PacketPlayerLook());
	            	mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer());
	            }
//	            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
//	            mc.thePlayer.activeItemStackUseCount = 1;
	            if (auto.getObject())
	            	mc.playerController.onStoppedUsingItem(mc.thePlayer);
	        }
		}
		
	}

}
