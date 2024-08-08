package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.GeometryMasks;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;


import java.awt.*;

@Module.Info(name = "PearlESP", category = Module.Category.RENDER)
public class PearlESP extends Module {

    private Setting<Boolean> players = register(Settings.b("Players", false));
    private Setting<Integer> a = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(50).build());
    private Setting<Integer> r = this.register(Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> g = this.register(Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> b = this.register(Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(255).build());
    private Setting<Boolean> epearls = register(Settings.b("Pearl", true));

    @Override
    public void onWorldRender(RenderEvent event) {
        Color c = new Color(r.getValue(), g.getValue(), b.getValue(), a.getValue());
        Color friend = new  Color(0, 0, 255, a.getValue());
        Color enemy = new Color(255, 0, 0, a.getValue());

        Color finalC = c;
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    KamiTessellator.prepare(GL11.GL_QUADS);
                    if (players.getValue() && e instanceof EntityPlayer) {
                        if (Friends.isFriend(e.getName()))
                            KamiTessellator.drawBox(e.getRenderBoundingBox(), friend.getRGB(), GeometryMasks.Quad.ALL);
                        else KamiTessellator.drawBox(e.getRenderBoundingBox(), enemy.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(epearls.getValue() && e instanceof EntityEnderPearl){
                        KamiTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }

                    KamiTessellator.release();
                });
    }
}