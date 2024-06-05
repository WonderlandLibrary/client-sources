package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutomaticallyBreak extends Module {

	public AutomaticallyBreak() {
		super("AutoMine", Keyboard.KEY_NONE, Category.WORLD);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (this.mc.objectMouseOver == null)
            return;
        BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
        if (blockPos == null)
            return;
        mc.gameSettings.keyBindAttack.pressed = true;
	}
	@Override
	public void onDisable() {
		if (!mc.gameSettings.isKeyDown(mc.gameSettings.keyBindAttack))
        mc.gameSettings.keyBindAttack.pressed = false;
		super.onDisable();
	}

}