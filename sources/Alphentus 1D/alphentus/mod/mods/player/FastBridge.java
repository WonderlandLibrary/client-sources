package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 12.08.2020.
 */
public class FastBridge extends Mod {

    public FastBridge() {
        super("FastBridge", Keyboard.KEY_NONE, true, ModCategory.PLAYER);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.TICKUPDATE)
            return;
        if (!getState())
            return;

        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
            mc.gameSettings.keyBindSneak.pressed = true;
        } else {
            mc.gameSettings.keyBindSneak.pressed = false;
        }
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }
}