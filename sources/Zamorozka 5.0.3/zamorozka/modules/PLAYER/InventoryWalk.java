package zamorozka.modules.PLAYER;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventTick;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;

import java.util.Objects;

public class InventoryWalk extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Rotate", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("InGameFocus", this, true));
	}
	
    public InventoryWalk() {
        super("InventoryWalk", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventTick event) {
        KeyBinding[] keys = new KeyBinding[]{mc.gameSettings.keyBindJump, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindSneak};
        if (mc.currentScreen != null) {
            if (mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiEditSign) {
                return;
            }
            KeyBinding[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final KeyBinding key = array[i];
                key.pressed = Keyboard.isKeyDown(key.getKeyCode());
                mc.gameSettings.keyBindSneak.pressed = false;
            }
        }
    }
    @EventTarget
    public void onRender2D(EventRender2D event) {
        if ((mc.currentScreen != null) && (!(mc.currentScreen instanceof GuiChat)) && Zamorozka.settingsManager.getSettingByName("Rotate").getValBoolean()) {
            if (Keyboard.isKeyDown(200)) {
                if (!(mc.player.rotationPitch == -90.0F))
                    pitch(mc.player.rotationPitch - 2.0F);
            }
            if (Keyboard.isKeyDown(208)) {
                if (!(mc.player.rotationPitch == 90.0F))
                    pitch(mc.player.rotationPitch + 2.0F);
            }
            if (Keyboard.isKeyDown(203)) {
                yaw(mc.player.rotationYaw - 3.0F);
            }
            if (Keyboard.isKeyDown(205)) {
                yaw(mc.player.rotationYaw + 3.0F);
            }
        }
    }

    public static void pitch(float pitch) {
        mc.player.rotationPitch = pitch;
    }
    public static void yaw(float yaw) {
        mc.player.rotationYaw = yaw;
    }
}
