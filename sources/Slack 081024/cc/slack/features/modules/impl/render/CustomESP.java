package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


@ModuleInfo(
        name = "CustomESP",
        category = Category.RENDER
)
public class CustomESP extends Module {

    private final ModeValue<String> customValue = new ModeValue<>("ESP Image", new String[]{"Wood", "Breezed", "Custom"});

    public CustomESP() {
        addSettings(customValue);
    }

    @Listen
    public void onRender (RenderEvent event) {
        if (event.getState() == RenderEvent.State.RENDER_3D) {
            for (Entity entity : mc.theWorld.getLoadedEntityList()) {
                if(entity instanceof EntityPlayer && entity != mc.thePlayer) {
                    double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
                    double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
                    double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;


                    GL11.glPushMatrix();
                    GlStateManager.translate(x, y + entity.height + .6, z);
                    GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
                    GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
                    GlStateManager.scale(-.025, -.025, .025);
                    GlStateManager.enableTexture2D();

                    GlStateManager.disableDepth();
                    GL11.glColor4f(1, 1, 1, 1);
                    switch (customValue.getValue()) {
                        case "Wood":
                            mc.getTextureManager().bindTexture(new ResourceLocation(""));
                            break;
                        case "Breezed":
                            mc.getTextureManager().bindTexture(new ResourceLocation(""));
                            break;
                        case "Custom":
                            mc.getTextureManager().bindTexture(new ResourceLocation(""));
                            break;
                    }
                    Gui.drawModalRectWithCustomSizedTexture(-25, 10, 0, 0, 48, 96, 48, 96);
                    GlStateManager.enableDepth();

                    GL11.glPopMatrix();
                }
            }
        }
    }

}
