package wtf.shiyeno.modules.impl.util;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CChatMessagePacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.ClientUtil;

@FunctionAnnotation(
        name = "AutoLeave",
        type = Type.Util
)
public class AutoLeave extends Function {
    public SliderSetting range = new SliderSetting("Дистанция", 15.0F, 5.0F, 40.0F, 1.0F);
    public ModeSetting mode = new ModeSetting("Что делать?", "/spawn", new String[]{"/spawn", "/hub", "kick"});
    public BooleanOption health = new BooleanOption("По здоровью", false);
    public SliderSetting healthSlider = (new SliderSetting("Здоровье", 10.0F, 5.0F, 20.0F, 1.0F)).setVisible(() -> {
        return this.health.get();
    });

    public AutoLeave() {
        this.addSettings(new Setting[]{this.range, this.mode, this.health, this.healthSlider});
    }

    public void onEvent(Event event) {
        if (event instanceof EventMotion e) {
            if (this.health.get()) {
                if (mc.player.getHealth() <= this.healthSlider.getValue().floatValue()) {
                    if (this.mode.is("kick")) {
                        mc.player.connection.getNetworkManager().closeChannel(ClientUtil.gradient("Вы вышли с сервера! \n Мало хп!", (new Color(121, 208, 255)).getRGB(), (new Color(96, 133, 255)).getRGB()));
                    } else {
                        mc.player.connection.sendPacket(new CChatMessagePacket(this.mode.get()));
                    }
                }

                this.setState(false);
                return;
            }

            Iterator var3 = mc.world.getPlayers().iterator();

            while(var3.hasNext()) {
                PlayerEntity player = (PlayerEntity)var3.next();
                if (player != mc.player && !player.isBot && !Managment.FRIEND_MANAGER.isFriend(player.getGameProfile().getName()) && mc.player.getDistance(player) <= this.range.getValue().floatValue()) {
                    if (this.mode.is("kick")) {
                        mc.player.connection.getNetworkManager().closeChannel(ClientUtil.gradient("Вы вышли с сервера! \n" + player.getGameProfile().getName(), (new Color(121, 208, 255)).getRGB(), (new Color(96, 133, 255)).getRGB()));
                    } else {
                        mc.player.connection.sendPacket(new CChatMessagePacket(this.mode.get()));
                    }

                    this.setState(false);
                    break;
                }
            }
        }
    }
}