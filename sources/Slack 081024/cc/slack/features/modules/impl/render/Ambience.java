// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.world.World;

@ModuleInfo(
        name = "Ambience",
        category = Category.RENDER
)
public class Ambience extends Module {

    public ModeValue<String> timemode = new ModeValue<>("Time", new String[]{"None","Sun", "Night", "Custom"});
    private final NumberValue<Integer> customtime = new NumberValue<>("Custom Time", 5, 1, 24, 1);
    public ModeValue<String> weathermode = new ModeValue<>("Weather", new String[]{"None", "Clear", "Rain", "Thunder"});
    private final NumberValue<Float> weatherstrength = new NumberValue<>("Weather Strength", 1F, 0F, 1F,  0.01F);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});



    public Ambience() {
        super();
        addSettings(timemode, customtime, weathermode, weatherstrength, displayMode);
    }

    @Listen
    @SuppressWarnings("unused")
    public void onUpdate(UpdateEvent event) {
        World world = mc.theWorld;

        String timeMode = timemode.getValue();
        if ("Sun".equals(timeMode)) {
            world.setWorldTime(6000);
        } else if ("Night".equals(timeMode)) {
            world.setWorldTime(15000);
        } else if ("Custom".equals(timeMode)) {
            world.setWorldTime(customtime.getValue() * 1000);
        }

        String weatherMode = weathermode.getValue();
        float weatherStrengthValue = weatherstrength.getValue();
        if ("Clear".equals(weatherMode)) {
            world.setRainStrength(0F);
            world.setThunderStrength(0F);
        } else if ("Rain".equals(weatherMode)) {
            world.setRainStrength(weatherStrengthValue);
            world.setThunderStrength(0F);
        } else if ("Thunder".equals(weatherMode)) {
            world.setRainStrength(weatherStrengthValue);
            world.setThunderStrength(weatherStrengthValue);
        }
    }

    @Listen
    public void onPacket (PacketEvent event) {
        if (!timemode.getValue().contains("None") && event.getPacket() instanceof S03PacketTimeUpdate) {
            event.cancel();
        }

        if (!weathermode.getValue().contains("None") && event.getPacket() instanceof S2BPacketChangeGameState) {
            if (((S2BPacketChangeGameState) event.getPacket()).getGameState() >= 7 && ((S2BPacketChangeGameState) event.getPacket()).getGameState() <= 8) {
                event.cancel();
            }
        }
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return timemode.getValue();
        }
        return null;
    }
}
