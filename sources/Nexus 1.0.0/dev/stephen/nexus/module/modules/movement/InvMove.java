package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.player.Blink;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import dev.stephen.nexus.utils.mc.InventoryUtils;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class InvMove extends Module {
    public static final ModeSetting invMoveMode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Watchdog Slow", "Blink");
    public static final BooleanSetting jump = new BooleanSetting("Jump", false);
    public static final BooleanSetting undetectable = new BooleanSetting("Undetectable", false);

    private boolean blinking = false;

    public InvMove() {
        super("InvMove", "", 0, ModuleCategory.MOVEMENT);
        this.addSettings(invMoveMode, jump, undetectable);
    }

    @Override
    public void onDisable() {
        if (blinking) {
            Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
            blinking = false;
        }
        super.onDisable();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        this.setSuffix(invMoveMode.getMode());
        if (isNull()) {
            if (blinking) {
                Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
                blinking = false;
            }
            return;
        }

        if (!inInventory()) {
            if (blinking) {
                Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
                blinking = false;
            }
            return;
        }

        if (!(mc.currentScreen instanceof HandledScreen<?>)) {
            return;
        }

        switch (invMoveMode.getMode()) {
            case "Watchdog Slow":
                mc.player.getVelocity().x *= 0.7;
                mc.player.getVelocity().z *= 0.7;
                break;

            case "Blink":
                if (!blinking) {
                    Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(true);
                    blinking = true;
                }
                break;
        }
    };

    public boolean inInventory() {
        if (mc.currentScreen instanceof ChatScreen || mc.currentScreen == null) {
            return false;
        }

        return !undetectable.getValue() || !(mc.currentScreen instanceof HandledScreen<?>);
    }

    public boolean shouldHandleInputs(KeyBinding keyBinding) {
        if (!isEnabled()) {
            return false;
        }

        if (mc.currentScreen instanceof ChatScreen || mc.currentScreen == null) {
            return false;
        }

        if (!(!undetectable.getValue() || !(mc.currentScreen instanceof HandledScreen<?>))) {
            return false;
        }

        if (keyBinding == mc.options.jumpKey && jump.getValue()) {
            return true;
        }
        if (keyBinding == mc.options.leftKey) {
            return true;
        }
        if (keyBinding == mc.options.forwardKey) {
            return true;
        }
        if (keyBinding == mc.options.rightKey) {
            return true;
        }
        return keyBinding == mc.options.backKey;
    }
}
