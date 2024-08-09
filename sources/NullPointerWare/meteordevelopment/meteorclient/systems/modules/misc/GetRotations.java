/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.text.Text;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public final class GetRotations extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> copyToClipboard = sgGeneral.add(new BoolSetting.Builder()
        .name("copy-to-clipboard")
        .description("Copies the pitch and yaw to the clipboard")
        .defaultValue(false)
        .build()
    );

    String yaw;
    String pitch;

    public GetRotations() {
        super(Categories.Misc, "GetRotations", "Get rotations you currently have.");
    }

    @Override
    public void onActivate() {
        assert mc.player != null;
        ChatUtils.addMessage(Text.of("pitch is " + mc.player.getPitch()));
        ChatUtils.addMessage(Text.of("yaw is " + mc.player.getYaw()));

        if (mc.currentScreen != null)
            mc.currentScreen.close();

        if (copyToClipboard.get()) {
            if (GraphicsEnvironment.isHeadless()) {
                this.toggle();
                return;
            }

            yaw = String.valueOf(mc.player.getYaw());
            pitch = String.valueOf(mc.player.getPitch());

            Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                    new StringSelection("Yaw: " + yaw + " : Pitch:" + pitch),
                    null
                );
        }
        this.toggle();
        super.onActivate();
    }
}

