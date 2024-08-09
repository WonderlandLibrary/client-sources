package wtf.shiyeno.modules.impl.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Iterator;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.ColorSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.render.ColorUtil;

@FunctionAnnotation(
        name = "Tracers",
        type = Type.Render
)
public class Tracers extends Function {
    public ColorSetting color = new ColorSetting("Цвет", ColorUtil.rgba(255, 255, 255, 255));
    public ColorSetting friendColor = new ColorSetting("Цвет друзей", ColorUtil.rgba(0, 255, 0, 255));
    public SliderSetting width = new SliderSetting("Ширина", 1.0F, 0.2F, 10.0F, 0.1F);
    public SliderSetting alpha = new SliderSetting("Прозрачность", 255.0F, 0.0F, 255.0F, 1.0F);
    public BooleanOption ignoreNaked = new BooleanOption("Игнорировать голых", true);

    public Tracers() {
        this.addSettings(new Setting[]{this.color, this.friendColor, this.width, this.alpha, this.ignoreNaked});
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender eventRender) {
            if (Managment.FUNCTION_MANAGER.freeCam.player != null) {
                return;
            }

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
                Iterator var6 = mc.world.getPlayers().iterator();

                while(var6.hasNext()) {
                    PlayerEntity entity = (PlayerEntity)var6.next();
                    if (entity instanceof RemoteClientPlayerEntity && entity.botEntity && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                        int tracersColor = Managment.FRIEND_MANAGER.isFriend(entity.getName().getString()) ? this.friendColor.get() : this.color.get();
                        double x = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getX();
                        double y = entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getY();
                        double z = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getZ();
                        ColorUtil.setColor(tracersColor);
                        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
                        bufferBuilder.pos(vec.x, vec.y, vec.z).endVertex();
                        bufferBuilder.pos(x, y, z).endVertex();
                        Tessellator.getInstance().draw();
                    }
                }

                GL11.glHint(3154, 4352);
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    }
}