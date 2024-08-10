package cc.slack.features.modules.impl.other;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.PrintUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "Test",
        category = Category.OTHER
)
public class Test extends Module {
    public final ModeValue<String> testMode = new ModeValue<>("Mode", new String[]{"1", "2", "3", "4", "5"});

    @Listen
    public void onUpdate (UpdateEvent event) {
        if (testMode.getModes().equals("1")) {
            PrintUtil.message("1");
        }
        if (testMode.getModes().equals("2")) {
            PrintUtil.message("2");
        }
        if (testMode.getModes().equals("3")) {
            PrintUtil.message("3");
        }
        if (testMode.getModes().equals("4")) {
            PrintUtil.message("4");
        }
        if (testMode.getModes().equals("5")) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }
    }

}
