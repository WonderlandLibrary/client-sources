package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.mixin.AbstractSignEditScreenAccessor;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.settings.StringSetting;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;

public class AutoSign extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<String> lineOneSetting = generalSettings.add(new StringSetting.Builder()
            .name("Line 1")
            .description("Text of the first line of the sign.")
            .defaultValue("First Line")
            .build()
    );

    private final Setting<String> lineTwoSetting = generalSettings.add(new StringSetting.Builder()
            .name("Line 2")
            .description("Text of the second line of the sign.")
            .defaultValue("Second Line")
            .build()
    );

    private final Setting<String> lineThreeSetting = generalSettings.add(new StringSetting.Builder()
            .name("Line 3")
            .description("Text of the third line of the sign.")
            .defaultValue("Third Line")
            .build()
    );

    private final Setting<String> lineFourSetting = generalSettings.add(new StringSetting.Builder()
            .name("Line 4")
            .description("Text of the fourth line of the sign.")
            .defaultValue("Fourth Line")
            .build()
    );

    public AutoSign(Category category) {
        super(category, "Auto Sign", "Automatically writes signs. The first sign's text will be used.");
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        if (!(event.screen instanceof SignEditScreen)) {
            return;
        }

        SignBlockEntity sign = ((AbstractSignEditScreenAccessor) event.screen).getSign();
        mc.player.networkHandler.sendPacket(new UpdateSignC2SPacket(sign.getPos(), lineOneSetting.get(), lineTwoSetting.get(), lineThreeSetting.get(), lineFourSetting.get()));

        event.cancel();
    }
}