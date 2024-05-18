package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.GuiScreenEvent;
import pw.latematt.xiv.event.events.InventoryClosedEvent;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.movement.Sprint;

/**
 * @author Matthew
 * @author TehNeon
 */
public class InventoryPlus extends Mod {
    private final Listener sendPacketListener, motionUpdateListener, inventoryClosedListener;

    public InventoryPlus() {
        super("InventoryPlus", ModType.MISCELLANEOUS);
        setDisplayName("Inventory+");

        inventoryClosedListener = new Listener<InventoryClosedEvent>() {
            @Override
            public void onEventCalled(InventoryClosedEvent event) {
                event.setCancelled(true);
            }
        };

        sendPacketListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (event.getPacket() instanceof C0DPacketCloseWindow) {
                    C0DPacketCloseWindow closeWindow = (C0DPacketCloseWindow) event.getPacket();
                    event.setCancelled(closeWindow.getWindowId() == 0);
                }
            }
        };

        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (mc.currentScreen == null)
                    return;
                if (mc.currentScreen instanceof GuiChat)
                    return;

                if(event.wasSprinting()) {
                    mc.thePlayer.setSprinting(true);
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_UP))
                    mc.thePlayer.rotationPitch = MathHelper.clamp_float(mc.thePlayer.rotationPitch - 4, -90.0F, 90.0F);
                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    mc.thePlayer.rotationPitch = MathHelper.clamp_float(mc.thePlayer.rotationPitch + 4, -90.0F, 90.0F);
                if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                    mc.thePlayer.rotationYaw -= 4;
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                    mc.thePlayer.rotationYaw += 4;

                if (mc.currentScreen instanceof GuiEditSign || mc.currentScreen instanceof GuiScreenBook || mc.currentScreen instanceof GuiRepair)
                    return;

                KeyBinding[] keyBindings = {
                        mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight,
                        mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint};

                for (KeyBinding keyBinding : keyBindings) {
                    KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
                }
            }
        };
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(sendPacketListener, motionUpdateListener, inventoryClosedListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(sendPacketListener, motionUpdateListener, inventoryClosedListener);
    }
}
