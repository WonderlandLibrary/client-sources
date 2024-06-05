package client.metaware.impl.module.render;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Ambience", renderName = "Ambience", category = Category.VISUALS, keybind = Keyboard.KEY_NONE)
public class Ambience extends Module {
    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Clear);
    public DoubleProperty time = new DoubleProperty("Time", 18000, 1000, 24000, 1000);

    public enum Mode{
        Clear, Rain, Thunder
    }

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if (this.mc.thePlayer == null)
            return;
        WorldServer[] servers = (MinecraftServer.getServer()).worldServers;
        if (servers == null) {
            set(this.mc.theWorld.getWorldInfo());
        } else {
            for (WorldServer server : servers) {
                if (server != null)
                    set(server.getWorldInfo());
            }
        }
    };

    void set(WorldInfo info) {
        info.setWorldTime(time.getValue().intValue());
        switch (this.mode.getValue()) {
            case Clear:
                info.setCleanWeatherTime(1000);
                info.setRaining(false);
                info.setThundering(false);
                info.setRainTime(0);
                info.setThunderTime(0);
                break;
            case Rain:
                info.setCleanWeatherTime(0);
                info.setRaining(true);
                info.setThundering(false);
                info.setRainTime(2500);
                info.setThunderTime(0);
                break;
            case Thunder:
                info.setCleanWeatherTime(0);
                info.setRaining(true);
                info.setThundering(true);
                info.setRainTime(2500);
                info.setThunderTime(2500);
                break;
        }
    }

    public void p(S03PacketTimeUpdate packetTimeUpdate) {
        packetTimeUpdate.worldTime = time.getValue().intValue();
    }

}
