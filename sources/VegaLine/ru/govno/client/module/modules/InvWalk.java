/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketEntityAction;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Panic;

public class InvWalk
extends Module {
    public static InvWalk get;
    public Settings AbilitySneak = new Settings("AbilitySneak", false, (Module)this);
    public Settings MouseMove;
    public Settings FlagFix;

    public InvWalk() {
        super("InvWalk", 0, Module.Category.MOVEMENT);
        this.settings.add(this.AbilitySneak);
        this.MouseMove = new Settings("MouseMove", false, (Module)this);
        this.settings.add(this.MouseMove);
        this.FlagFix = new Settings("FlagFix", true, (Module)this);
        this.settings.add(this.FlagFix);
        get = this;
    }

    private static List<Integer> keyPuts(GameSettings gs, boolean canSneak) {
        List<KeyBinding> list = Arrays.asList(gs.keyBindJump, gs.keyBindForward, gs.keyBindBack, GameSettings.keyBindLeft, GameSettings.keyBindRight);
        if (canSneak) {
            list.add(gs.keyBindSneak);
        }
        return list.stream().map(key -> key.getKeyCode()).toList();
    }

    private static boolean keyIsDown(int keyNum) {
        return Keyboard.isKeyDown(keyNum);
    }

    private static void updateKeyStates(GameSettings gameSettings, boolean canSneak) {
        gameSettings.keyBindJump.pressed = InvWalk.keyIsDown(gameSettings.keyBindJump.getKeyCode());
        gameSettings.keyBindForward.pressed = InvWalk.keyIsDown(gameSettings.keyBindForward.getKeyCode());
        gameSettings.keyBindBack.pressed = InvWalk.keyIsDown(gameSettings.keyBindBack.getKeyCode());
        GameSettings.keyBindLeft.pressed = InvWalk.keyIsDown(GameSettings.keyBindLeft.getKeyCode());
        GameSettings.keyBindRight.pressed = InvWalk.keyIsDown(GameSettings.keyBindRight.getKeyCode());
        if (canSneak) {
            gameSettings.keyBindSneak.pressed = InvWalk.keyIsDown(gameSettings.keyBindSneak.getKeyCode());
        }
    }

    private static boolean canUpdateKeys(Gui gui) {
        return !Panic.stop && gui != null && get != null && InvWalk.get.actived && !(gui instanceof GuiChat) && !(gui instanceof GuiEditSign);
    }

    public static void inInitScreen(Gui gui) {
        if (InvWalk.canUpdateKeys(gui) && gui instanceof GuiContainer && !InvWalk.get.FlagFix.bValue) {
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }

    public static boolean keysHasUpdated(Gui gui, boolean silent) {
        if (InvWalk.canUpdateKeys(gui)) {
            GameSettings gameSettings = InvWalk.mc.gameSettings;
            if (!silent) {
                InvWalk.updateKeyStates(gameSettings, InvWalk.get.AbilitySneak.bValue);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onToggled(boolean actived) {
        if (InvWalk.mc.currentScreen != null && !actived) {
            KeyBinding.unPressAllKeys();
        }
        super.onToggled(actived);
    }

    @Override
    public void onUpdate() {
        GuiScreen gui = InvWalk.mc.currentScreen;
        if (InvWalk.keysHasUpdated(gui, true) && gui instanceof GuiContainer && this.MouseMove.bValue) {
            mc.setIngameFocus();
            KeyBinding.updateKeyBindState();
            Mouse.setGrabbed(false);
        }
    }
}

