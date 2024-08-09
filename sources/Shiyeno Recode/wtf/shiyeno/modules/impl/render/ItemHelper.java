package wtf.shiyeno.modules.impl.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Iterator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.SkullItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ColorSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.render.ColorUtil;

@FunctionAnnotation(
        name = "ItemHelper",
        type = Type.Render
)
public class ItemHelper extends Function {
    public ColorSetting color = new ColorSetting("Цвет", ColorUtil.rgba(255, 255, 255, 255));
    public SliderSetting width = new SliderSetting("Ширина", 1.0F, 0.2F, 10.0F, 0.1F);
    public SliderSetting alpha = new SliderSetting("Прозрачность", 255.0F, 0.0F, 255.0F, 1.0F);

    public ItemHelper() {
        this.addSettings(new Setting[]{this.color, this.width, this.alpha});
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender eventRender) {
            if (eventRender.isRender3D()) {
                GL11.glPushMatrix();
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glLineWidth(this.width.getValue().floatValue());
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                Vector3d vec = (new Vector3d(0.0, 0.0, 150.0)).rotatePitch((float)(-Math.toRadians((double)mc.player.rotationPitch))).rotateYaw((float)(-Math.toRadians((double)mc.player.rotationYaw)));
                BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                float alpha = (float)((int)this.alpha.getValue().floatValue()) / 255.0F;
                Iterator var6 = mc.world.getAllEntities().iterator();

                while(true) {
                    Entity entity;
                    do {
                        do {
                            do {
                                if (!var6.hasNext()) {
                                    GL11.glHint(3154, 4352);
                                    GL11.glDisable(2848);
                                    GL11.glEnable(3553);
                                    GL11.glEnable(2929);
                                    GL11.glDepthMask(true);
                                    GL11.glDisable(3042);
                                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                                    GL11.glPopMatrix();
                                    return;
                                }

                                entity = (Entity)var6.next();
                            } while(!(entity instanceof ItemEntity));
                        } while(mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON);
                    } while(!(((ItemEntity)entity).getItem().getItem() instanceof SkullItem) && !(((ItemEntity)entity).getItem().getItem() instanceof SpawnEggItem) && !(((ItemEntity)entity).getItem().getItem() instanceof ElytraItem));

                    if (!entity.isGlowing()) {
                        entity.setGlowing(true);
                    }

                    double x = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getX();
                    double y = entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getY();
                    double z = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getZ();
                    bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
                    bufferBuilder.pos(vec.x, vec.y, vec.z).endVertex();
                    bufferBuilder.pos(x, y, z).endVertex();
                    Tessellator.getInstance().draw();
                }
            }
        }
    }

    public void onDisable() {
        Iterator var1 = mc.world.getAllEntities().iterator();

        while(var1.hasNext()) {
            Entity entity = (Entity)var1.next();
            entity.setGlowing(false);
        }
    }
}