package wtf.diablo.client.module.impl.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import wtf.diablo.client.gui.draggable.AbstractDraggableElement;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

@ModuleMetaData(
        name = "Radar",
        description = "Minimap type beat",
        category = ModuleCategoryEnum.RENDER
)
public final class RadarModule extends AbstractModule {
    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting animals = new BooleanSetting("Animals", false);
    private final BooleanSetting mobs = new BooleanSetting("Mobs", false);
    private final BooleanSetting invisible = new BooleanSetting("Invisibles", false);
    private final NumberSetting<Integer> size = new NumberSetting<>("Size", 125, 40, 300, 5);

    public RadarModule() {
        this.registerSettings(size, players, animals, mobs, invisible);
    }

    final Color backdrop = new Color(26, 26, 26, 179);

    final AbstractDraggableElement element = new AbstractDraggableElement("Radar", 100, 100, 0, 0, this) {
        @Override
        protected void draw() {
            final double width = element.getWidth() - 1;
            final double height = element.getHeight() - 1;
            final int line = new Color(255, 255, 255, 90).getRGB();

            Gui.drawRect(0, 0, width, height, backdrop.getRGB());
            Gui.drawRect((width / 2) - 0.5F, 1, (width / 2) + 0.5F, height, line);
            Gui.drawRect(0, height / 2 - 0.5F, width, height / 2 + 0.5F, line);
            // RenderUtil.drawOutlineRect(0, 0, width - 1.5, height - 1.5, 1.5, 0xff313131);
            Gui.drawRect(0, 0, width, 1.5, ColorModule.getColor(0));
            //Gui.drawRect(-1,-1,width + 1,height + 1,0xFF212121);

            GlStateManager.translate(width / 2f, height / 2f, 0);
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityLivingBase) {
                    int color = 0;
                    if (entity.isInvisible() && !invisible.getValue())
                        continue;
                    if (mobs.getValue())
                        if (entity instanceof EntityMob) {
                            color = 0xff4d1410;
                        }

                    if (animals.getValue())
                        if (entity instanceof EntityAnimal) {
                            color = 0xff38ffeb;
                        }

                    if (players.getValue())
                        if (entity instanceof EntityPlayer) {
                            color = 0xffff3838;
                        }

                    double x = mc.thePlayer.posX - entity.posX;
                    double y = mc.thePlayer.posZ - entity.posZ;

                    GlStateManager.rotate(-mc.thePlayer.rotationYaw, 0, 0, 1);
                    Gui.drawRect(x - 1, y - 1, x + 1, y + 1, color);
                    GlStateManager.rotate(mc.thePlayer.rotationYaw, 0, 0, 1);
                }
            }
            //GlStateManager.rotate(mc.thePlayer.rotationYaw,0,0,1);
            GlStateManager.color(1, 1, 1);
            RenderUtil.drawImage(-3, -4, 6, 6, new ResourceLocation("diablo/icons/triangle.png"));
            GlStateManager.translate(-(width / 2f), -(height / 2f), 0);
            element.setWidth(size.getValue() + 2);
            element.setHeight(size.getValue() + 2);
        }
    };
}
