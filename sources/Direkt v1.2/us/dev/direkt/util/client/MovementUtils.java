package us.dev.direkt.util.client;

import net.minecraft.entity.Entity;
import us.dev.direkt.Wrapper;

public class MovementUtils {
	public static boolean isMoving(Entity e) {
		return e.posX != e.lastTickPosX || e.posY != e.lastTickPosY || e.posZ != e.lastTickPosZ
                || (e == Wrapper.getPlayer() && (Wrapper.getGameSettings().keyBindForward.isPressed()
                    || Wrapper.getGameSettings().keyBindBack.isPressed()
                    || Wrapper.getGameSettings().keyBindRight.isPressed()
                    || Wrapper.getGameSettings().keyBindLeft.isPressed()
                    || Wrapper.getGameSettings().keyBindJump.isPressed()));
	}
}
