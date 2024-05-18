package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;

public class InventoryMove extends Module {

	public InventoryMove() {
		super("InventoryMove", Keyboard.KEY_O, 0x9E9E9E, Category.Movement);
		setDisplayname("InventoryMove");
		if (mc.theWorld != null) {
			setToggled(true);
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
			KeyBinding[] moveKeys = new KeyBinding[]{mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump};
	        if (mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiScreen || mc.currentScreen instanceof GuiGameOver) {
	            KeyBinding[] arrkeyBinding = moveKeys;
	            int n = arrkeyBinding.length;
	            int n2 = 0;
	            while (n2 < n) {
	                KeyBinding bind = arrkeyBinding[n2];
	                bind.pressed = Keyboard.isKeyDown((int)bind.getKeyCode());
	                ++n2;
	            }
	        } else {
	            Minecraft.getMinecraft();
	            if (mc.currentScreen == null) {
	                KeyBinding[] arrkeyBinding = moveKeys;
	                int n = arrkeyBinding.length;
	                int n3 = 0;
	                while (n3 < n) {
	                    KeyBinding bind = arrkeyBinding[n3];
	                    if (!Keyboard.isKeyDown((int)bind.getKeyCode())) {
	                        KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)false);
	                    }
	                    ++n3;
	                }
	            }
	        }
	        if (mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiScreen || mc.currentScreen instanceof GuiGameOver) {
	            if (Keyboard.isKeyDown((int)200)) {
	                mc.thePlayer.rotationPitch -= 2.5f;
	            }
	            if (Keyboard.isKeyDown((int)208)) {
	                mc.thePlayer.rotationPitch += 2.5f;
	            }
	            if (Keyboard.isKeyDown((int)203)) {
	                mc.thePlayer.rotationYaw -= 2.5f;
	            }
	            if (Keyboard.isKeyDown((int)205)) {
	                mc.thePlayer.rotationYaw += 2.5f;
	            }
	        }
	    }
	


	@Override
	public void onEnable() {
		EventManager.register(this);
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}

}
