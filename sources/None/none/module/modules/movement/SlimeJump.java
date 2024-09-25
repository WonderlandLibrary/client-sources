package none.module.modules.movement;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockSlime;
import net.minecraft.util.BlockPos;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;

public class SlimeJump extends Module{

	public SlimeJump() {
		super("SlimeJump", "SlimeJump", Category.MOVEMENT, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				BlockPos pos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.ceil(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ));
		        
		        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockSlime && mc.thePlayer.onGround) {
		            mc.thePlayer.motionY = 1.51;
		        }
			}
		}
	}

}
