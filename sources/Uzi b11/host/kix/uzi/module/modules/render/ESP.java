package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.module.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

/**
 * Created by Kix on 5/26/2017.
 * Made for the eclipse project.
 */
public class ESP extends Module {

    public ESP() {
        super("Esp", 0, Category.RENDER);
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldEvent event) {
        mc.theWorld.playerEntities
                .stream()
                .filter(player -> player != mc.thePlayer)
                .filter(Objects::nonNull)
                .forEach((EntityPlayer player) -> {
                    double posX = (player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks);
                    double posY = (player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks);
                    double posZ = (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks);
                    Gui.draw2D(player, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, determineColorBasedUponHealth(player.getHealth()));
                });
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    private int determineColorBasedUponHealth(float health) {
        if (health < 5) {
            return new Color(255, 0, 0).getRGB();
        }
        if (health > 5 && health < 10) {
            return new Color(255, 109, 26).getRGB();
        }
        if (health > 10 && health < 15) {
            return new Color(255, 222, 30).getRGB();
        }
        return new Color(0, 255, 0).getRGB();
    }

}
