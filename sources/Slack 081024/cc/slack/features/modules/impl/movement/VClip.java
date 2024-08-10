package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "VClip",
        category = Category.MOVEMENT
)
public class VClip extends Module {

    private final ModeValue<String> clipmodeValue = new ModeValue<>("Clip", new String[]{"Up", "Down"});
    private final NumberValue<Double> clipValue = new NumberValue<>("Clip Value", 4.0D, 0.1D, 30.0D, 0.1D);


    public VClip() {
        addSettings(clipmodeValue, clipValue);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        switch (clipmodeValue.getValue()) {
            case "Up":
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + clipValue.getValue(), mc.thePlayer.posZ, true));
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + clipValue.getValue(), mc.thePlayer.posZ);
                break;
            case "Down":
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - clipValue.getValue(), mc.thePlayer.posZ, true));
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - clipValue.getValue(), mc.thePlayer.posZ);
                break;
        }
    }
}
