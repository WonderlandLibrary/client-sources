package us.dev.direkt.module.internal.movement.speed.internal.modes;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import io.netty.util.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.internal.movement.LiquidWalk;
import us.dev.direkt.module.internal.movement.speed.internal.AbstractSpeedMode;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author Foundry
 */
public class TestMode extends AbstractSpeedMode {
    private int airTicks;

	public TestMode() {
        super("BHop");
    }

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
    	if(Wrapper.getGameSettings().keyBindJump.isKeyDown() && !LiquidWalk.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox())) {
    		if(Wrapper.onGround()) {
    			this.airTicks = 0;
    	        float dir = Wrapper.getPlayer().rotationYaw;
    	        if (Wrapper.getPlayer().moveForward < 0.0F) {
    	            dir += 180.0F;
    	        }
    	        if (Wrapper.getPlayer().moveStrafing > 0.0F) {
    	            dir -= 10.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F);
    	        }
    	        if (Wrapper.getPlayer().moveStrafing < 0.0F) {
    	            dir += 10.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F);
    	        }

    	        float xD = (float) Math.cos((dir + 90.0F) * Math.PI / 180.0D);
    	        float zD = (float) Math.sin((dir + 90.0F) * Math.PI / 180.0D); // sin(x + 90) = cos(x) ? Explain?
    			Wrapper.getPlayer().motionX = xD * 0.221;
    			Wrapper.getPlayer().motionZ = zD * 0.221;
    		} else {
                if (Wrapper.getPlayer().motionY < 0 && Wrapper.getPlayer().motionY > -0.3 && (Math.abs(Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ) > 0.1 || Wrapper.getGameSettings().keyBindRight.isKeyDown() || Wrapper.getGameSettings().keyBindLeft.isKeyDown()))
                    Wrapper.getPlayer().motionY *= 3;
    			this.airTicks++;
    			if(this.airTicks < 3) {
        			if(Direkt.getInstance().getModuleManager().getModule(us.dev.direkt.module.internal.movement.Timer.class).isRunning())
        				Wrapper.getTimer().timerSpeed = 1.6F;
    				Wrapper.getPlayer().motionX /= 50;
    				Wrapper.getPlayer().motionZ /= 50;
    			}
    			if(this.airTicks == 3) {
        			if(Direkt.getInstance().getModuleManager().getModule(us.dev.direkt.module.internal.movement.Timer.class).isRunning())
        				Wrapper.getTimer().timerSpeed = 1.0F;
    				Wrapper.getPlayer().motionX *= 29;
    				Wrapper.getPlayer().motionZ *= 29;
    			}
/*    			Wrapper.getPlayer().motionX *= (2.8 - (this.airTicks + 1) * 0.6) > 1 ? (2.8 - (this.airTicks + 1) * 0.6) : 0.1;
    			Wrapper.getPlayer().motionZ *= (2.8 - (this.airTicks + 1) * 0.6) > 1 ? (2.8 - (this.airTicks + 1) * 0.6) : 0.1;
*/  
    		}
    	} else {
    		this.airTicks = 0;
   			if(Direkt.getInstance().getModuleManager().getModule(us.dev.direkt.module.internal.movement.Timer.class).isRunning())
				Wrapper.getTimer().timerSpeed = 1.1F;	
    	}
    }, Link.VERY_HIGH_PRIORITY);
}
