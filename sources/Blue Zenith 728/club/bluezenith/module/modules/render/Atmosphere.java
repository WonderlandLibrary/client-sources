package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.ModeValue;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.world.WorldServer;

public class Atmosphere extends Module {

    public final FloatValue time = AbstractBuilder.createFloat("Time").increment(0.5F).min(1F).max(24F).defaultOf(12F).index(1).build();
    public final ModeValue weather = AbstractBuilder.createMode("Weather").range("Server", "Clean", "Rain", "Snow").index(2)
            .whenChanged((pre, post) -> {
                if(post.equals("Server") && world != null) {
                    world.setRainStrength(0F);
                    world.setThunderStrength(0F);
                }
              return post;
            })
            .build();
    public Atmosphere() {
        super("Atmosphere", ModuleCategory.RENDER);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if(mc.isSingleplayer()) {
            for (WorldServer server : mc.getIntegratedServer().worldServers) {
                if(server == null) continue;
                server.setWorldTime((long) (time.get() * 1000));
            }
        }
        switch (weather.get()) {
                case "Snow":
                case "Rain":
                    world.setRainStrength(1.0F);
                    world.setThunderStrength(0.0F);
                    break;

                case "Clean":
                    world.setRainStrength(0F);
                    world.setThunderStrength(0F);
                    break;
            }
            world.setWorldTime((long) (time.get() * 1000));
        }

    @Listener
    public void onPacket(PacketEvent event) {
        if(world != null)
            world.setWorldTime((long) (time.get() * 1000));

         if(event.packet instanceof S03PacketTimeUpdate) {
            event.cancel();
        }
    }

}
