//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import src.Wiksi.command.friends.FriendStorage;
import src.Wiksi.events.WorldEvent;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import java.util.Iterator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.optifine.shaders.ShadowUtils;
import org.lwjgl.opengl.GL11;

import static org.json.XMLTokener.entity;


@FunctionRegister(
        name = "China Hat",
        type = Category.Render
)
public class ChinaHat extends Function {
    final ModeSetting correctionType = new ModeSetting("Мод", "Шляпа", new String[]{"Шляпа", "Новый"});
    private final SliderSetting radius = (new SliderSetting("Радиус", 0.60F, 0.30F, 1.20F, 0.1f)).setVisible(() -> {
        return !correctionType.is("Новый");
    });
    private final SliderSetting offsetY = (new SliderSetting("Высота", 1.53F, 0.60F, 2.20F, 0.1f)).setVisible(() -> {
        return !correctionType.is("Новый");
    });
    private final ChinaHat chinaHat;
    private ModeSetting type;

    public ChinaHat(ChinaHat chinaHat) {
        this.chinaHat = chinaHat;
        this.addSettings(new Setting[]{this.correctionType, this.radius, this.offsetY});
    }

    @Subscribe
    private void onRender(WorldEvent e) {
        float radius = (Float) this.radius.get();
        if (this.correctionType.is("Шляпа")) {
            int hatColor = ColorUtils.setAlpha(HUD.getColor(0, 1.0F), 128);
            Iterator var4 = mc.world.getPlayers().iterator();

            label99:
            while (true) {
                PlayerEntity player;
                boolean isCurrentPlayer;
                do {
                    do {
                        if (!var4.hasNext()) {
                            break label99;
                        }

                        player = (PlayerEntity) var4.next();
                        isCurrentPlayer = player.equals(mc.player);
                    } while (isCurrentPlayer && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON);
                } while (!FriendStorage.isFriend(player.getGameProfile().getName()) && !isCurrentPlayer);

                float playerYaw = player.rotationYaw;
                float playerPitch = player.rotationPitch;
                boolean isSneaking = player.isSneaking();
                boolean isSwimming = player.isSwimming();
                GlStateManager.pushMatrix();
                RenderSystem.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
                Vector3d interpolated = MathUtil.interpolate(player.getPositionVec(), new Vector3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ), e.getPartialTicks());
                double yOffset = (double) (player.getHeight() * (float) (isSneaking ? 40 : 1));
                if (isSwimming) {
                    yOffset = (double) player.getHeight() * 0.5;

                }

                interpolated = new Vector3d(interpolated.x, interpolated.y + yOffset, interpolated.z);
                RenderSystem.translated(interpolated.x, interpolated.y, interpolated.z);
                RenderSystem.rotatef(playerYaw, 0.0F, -1.0F, 0.0F);
                RenderSystem.rotatef(playerPitch, 1.0F, 0.0F, 0.0F);
                float offsetY = player.getEyeHeight() - (Float) this.offsetY.get();
                if (isSneaking) {
                    offsetY -= 0.25F;
                }

                if (isSwimming) {
                    offsetY = 0.25F;
                }

                float offsetZ = MathHelper.sin((float) Math.toRadians((double) playerPitch)) * 0.2F;
                RenderSystem.translated(0.0, (double) offsetY, 0.0);
                RenderSystem.rotatef(-playerPitch, 1.0F, 0.0F, 0.0F);
                RenderSystem.translated(0.0, 0.0, (double) offsetZ);
                RenderSystem.translated(-interpolated.x, -interpolated.y, -interpolated.z);
                RenderSystem.enableBlend();
                RenderSystem.depthMask(false);
                RenderSystem.disableTexture();
                RenderSystem.disableCull();
                RenderSystem.blendFunc(770, 771);
                RenderSystem.shadeModel(7425);
                RenderSystem.lineWidth(3.0F);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(interpolated.x, interpolated.y + 0.3, interpolated.z).color(hatColor).endVertex();

                int i;
                float x;
                float z;
                for (i = 0; i <= 360; ++i) {
                    x = (float) (interpolated.x + (double) (MathHelper.sin((float) Math.toRadians((double) i)) * radius));
                    z = (float) (interpolated.z + (double) (-MathHelper.cos((float) Math.toRadians((double) i)) * radius));
                    buffer.pos((double) x, interpolated.y, (double) z).color(hatColor).endVertex();
                }

                tessellator.draw();
                buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);

                for (i = 0; i <= 360; ++i) {
                    x = (float) (interpolated.x + (double) (MathHelper.sin((float) Math.toRadians((double) i)) * radius));
                    z = (float) (interpolated.z + (double) (-MathHelper.cos((float) Math.toRadians((double) i)) * radius));
                    buffer.pos((double) x, interpolated.y, (double) z).color(hatColor).endVertex();
                }

                tessellator.draw();
                GL11.glHint(3154, 4352);
                GL11.glDisable(2848);
                RenderSystem.enableTexture();
                RenderSystem.disableBlend();
                RenderSystem.enableCull();
                RenderSystem.depthMask(true);
                RenderSystem.shadeModel(7424);
                GlStateManager.popMatrix();
            }
        }

        if (this.correctionType.is("Новый")) {
            if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                return;
            }

            radius = 0.5F;
            GlStateManager.pushMatrix();
            RenderSystem.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
            Vector3d interpolated = MathUtil.interpolate(mc.player.getPositionVec(), new Vector3d(mc.player.lastTickPosX, mc.player.lastTickPosY, mc.player.lastTickPosZ), e.getPartialTicks());
            interpolated.y += 0.07000000029802322;
            RenderSystem.translated(interpolated.x, interpolated.y + (double)mc.player.getHeight(), interpolated.z);
            double pitch = (double)mc.getRenderManager().info.getPitch();
            double yaw = (double)mc.getRenderManager().info.getYaw();
            GL11.glRotatef(140.0F, 0.0F, 1.0F, 0.0F);
            RenderSystem.translated(-interpolated.x, -(interpolated.y + (double)mc.player.getHeight()), -interpolated.z);
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.disableTexture();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.shadeModel(7425);
            RenderSystem.lineWidth(3.0F);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(interpolated.x, interpolated.y, interpolated.z).color(ColorUtils.setAlpha(HUD.getColor(0, 1.0F), 60)).endVertex();

            int i;
            float x;
            float z;
            for(i = 0; i <= 360; ++i) {
                x = (float)(interpolated.x + (double)(MathHelper.sin((float)Math.toRadians((double)i)) * radius));
                z = (float)(interpolated.z + (double)(-MathHelper.cos((float)Math.toRadians((double)i)) * radius));
            }

            tessellator.draw();
            buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);

            for(i = 0; i <= 360; ++i) {
                x = (float)(interpolated.x + (double)(MathHelper.sin((float)Math.toRadians((double)i)) * radius));
                z = (float)(interpolated.z + (double)(-MathHelper.cos((float)Math.toRadians((double)i)) * radius));
                buffer.pos((double)x, interpolated.y + (double)mc.player.getHeight(), (double)z).color(ColorUtils.setAlpha(HUD.getColor(i, 1.0F), 255)).endVertex();
            }

            tessellator.draw();
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
            RenderSystem.shadeModel(7424);
            GlStateManager.popMatrix();
        }

    }
}
