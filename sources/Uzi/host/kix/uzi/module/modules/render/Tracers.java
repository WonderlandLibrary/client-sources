package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.utilities.minecraft.RenderingMethods;
import host.kix.uzi.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

/**
 * Created by myche on 2/20/2017.
 */
public class Tracers extends Module {

    private Color color;
    private Random random = new Random();

    public Tracers() {
        super("Tracers", Keyboard.KEY_NONE, Category.RENDER);
    }

    @SubscribeEvent()
    public void onRender(RenderWorldEvent e) {
        if (this.isEnabled()) {
            for (Object o : mc.theWorld.playerEntities) {
                if (o instanceof EntityPlayer && o != mc.thePlayer) {
                    EntityPlayer entity = (EntityPlayer) o;
                    if (mc.thePlayer.getDistanceToEntity(entity) >= 20) {
                        color = Color.GREEN;
                    } else if (mc.thePlayer.getDistanceToEntity(entity) <= 5) {
                        color = Color.RED;
                    } else if (mc.thePlayer.getDistanceToEntity(entity) <= 10) {
                        color = Color.ORANGE;
                    } else if (mc.thePlayer.getDistanceToEntity(entity) <= 15) {
                        color = Color.YELLOW;
                    }
                    GL11.glLoadIdentity();
                    Minecraft.getMinecraft().entityRenderer.orientCamera(e.getPartialTicks());
                    draw(entity);
                }
            }
        }
    }

    private void draw(EntityLivingBase e) {

        double x = e.posX - RenderManager.renderPosX;
        double y = e.posY - RenderManager.renderPosY;
        double z = e.posZ - RenderManager.renderPosZ;

        RenderingMethods.drawTracerLine(new double[]{x, y + 1.0, z}, new float[]{color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 200F / 255F}, 0.8F);
        GL11.glColor4f(255F, 255F, 255F, 255F);
    }

}
