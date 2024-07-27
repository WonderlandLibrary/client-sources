package dev.nexus.modules.impl.player;

import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventStrafe;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

public class Eagle extends Module {
    public Eagle() {
        super("Eagle", Keyboard.KEY_F, ModuleCategory.PLAYER);
    }

    @EventLink
    public final Listener<EventStrafe> eventStrafeListener = event -> {
        if (isNull()) {
            return;
        }
        setShift(playerOverAir() && mc.thePlayer.onGround);
    };

    public static boolean playerOverAir() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY - 1.0D;
        double z = mc.thePlayer.posZ;
        return mc.theWorld.isAirBlock(new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z)));
    }

    @Override
    public void onDisable() {
        setShift(false);
        super.onDisable();
    }

    private void setShift(boolean sh) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
    }
}
