package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.lwjgl.input.Keyboard;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

import java.util.Arrays;

@ModuleMetaData(name = "Inventory Move", description = "Allows the player to move around in GUIs", category = ModuleCategoryEnum.PLAYER)
public final class InventoryMoveModule extends AbstractModule {
    private final ModeSetting<InventoryMoveMode> mode = new ModeSetting<>("Mode",InventoryMoveMode.VANILLA);

    public InventoryMoveModule() {
        this.registerSettings(mode);
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        this.setSuffix(mode.getValue().getName());

        if (event.getEventType() == EventTypeEnum.PRE && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            mc.thePlayer.movementInput.moveForward = 1;

            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) mc.thePlayer.rotationPitch += 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) mc.thePlayer.rotationPitch -= 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) mc.thePlayer.rotationYaw += 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) mc.thePlayer.rotationYaw -= 2f;

            //TODO: Append jump if not watchdog mode
            KeyBinding[] keys = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight};
            Arrays.stream(keys).forEach(key -> key.pressed = Keyboard.isKeyDown(key.getKeyCode()));

            if (this.mode.getValue() == InventoryMoveMode.WATCHDOG) {
                mc.thePlayer.setSprinting(false);
                MovementUtil.setMotion(0.04);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                if (this.mc.thePlayer.onGround && MovementUtil.isMoving()) {
                    this.mc.thePlayer.jump();
                }
            }

        }
    };

    @EventHandler()
    public final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof C16PacketClientStatus) {
            final C16PacketClientStatus packet = (C16PacketClientStatus) e.getPacket();
            if (packet.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                e.setCancelled(true);
            }
        }
    };

    enum InventoryMoveMode implements IMode {
        WATCHDOG("Watchdog"),
        VANILLA("Vanilla");

        private final String name;

        InventoryMoveMode(final String name){
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
