package us.dev.direkt.module.internal.player;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import org.lwjgl.input.Keyboard;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.gui.minecraft.override.GuiDirektChat;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.util.Set;

@ModData(label = "Inventory Walk", aliases = { "inventorywalk", "invwalk", "invmove" }, category = ModCategory.PLAYER)
public class InventoryWalk extends ToggleableModule {
	private static final Set<Class<? extends GuiScreen>> blacklisted = new ImmutableSet.Builder<Class<? extends GuiScreen>>()
            .add(GuiChat.class)
            .add(GuiDirektChat.class)
            .add(GuiCommandBlock.class)
            .add(GuiControls.class)
            .add(GuiEditSign.class)
            .add(GuiRepair.class)
            .add(GuiScreenBook.class)
            .add(GuiSleepMP.class)
            .build();
	private KeyBinding[] movementKeys;

    private boolean wasRotating;

    @Exposed(description = "Allow rotation inside of windows")
	private Property<Boolean> rotate = new Property<>("Rotate", true);

	public InventoryWalk() {
		GameSettings settings = Wrapper.getGameSettings();
		this.movementKeys = new KeyBinding[] {settings.keyBindForward, settings.keyBindRight, settings.keyBindBack, settings.keyBindLeft, settings.keyBindJump, settings.keyBindSprint};
	}

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		GuiScreen currentScreen = Wrapper.getMinecraft().currentScreen;
		if (currentScreen != null && !blacklisted.contains(currentScreen.getClass())) {
			boolean abort = false;
			if (currentScreen instanceof GuiContainerCreative) {
				GuiContainerCreative creative = (GuiContainerCreative) currentScreen;
				if (CreativeTabs.CREATIVE_TAB_ARRAY[creative.getSelectedTabIndex()] == CreativeTabs.SEARCH) {
					abort = true;
				}
			}
			if (abort) {
				for (KeyBinding keybind : this.movementKeys) {
					int keyCode = keybind.getKeyCode();
					KeyBinding.setKeyBindState(keyCode, false);
				}
				return;
			}
			for (KeyBinding keybind : this.movementKeys) {
				int keyCode = keybind.getKeyCode();
				KeyBinding.setKeyBindState(keyCode, Keyboard.isKeyDown(keyCode));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_R) && this.rotate.getValue()) {
				wasRotating = false;
				Wrapper.getMinecraft().mouseHelper.mouseXYChange();
				float sensitivity = Wrapper.getGameSettings().mouseSensitivity * 0.6F + 0.2F;
				float modifier = 1;//sensitivity * sensitivity * sensitivity * 8.0F;
				float motionX = (float)Wrapper.getMinecraft().mouseHelper.deltaX * modifier;
				float motionY = (float)Wrapper.getMinecraft().mouseHelper.deltaY * modifier;
				Wrapper.getMinecraft().mouseHelper.ungrabMouseCursor();
				Wrapper.getMinecraft().mouseHelper.grabMouseCursor();
				Wrapper.getPlayer().setAngles(motionX, motionY * (Wrapper.getGameSettings().invertMouse ? -1 : 1));
			} else {
				if (!wasRotating) {
					Wrapper.getMinecraft().mouseHelper.ungrabMouseCursor();
					wasRotating = true;
				}
			}
		}
	});

}
