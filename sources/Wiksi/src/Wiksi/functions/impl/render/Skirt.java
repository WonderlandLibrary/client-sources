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
import org.lwjgl.opengl.GL11;

@FunctionRegister(
        name = "Skirt",
        type = Category.Render
)
public class Skirt extends Function {
    final ModeSetting correctionType = new ModeSetting("Мод", "Обычный", new String[]{"Обычный"});
    private final SliderSetting radius = (new SliderSetting("Радиус", 0.70F, 0.40F, 1.0F, 0.1f));
    private final Skirt skirt;
    private ModeSetting type;

    public Skirt(Skirt skirt) {
        this.skirt = skirt;
        this.addSettings(new Setting[]{this.correctionType, this.radius});
    }

    @Subscribe
    private void onRender(WorldEvent e) {
        float radius = (Float)this.radius.get();
        if (this.correctionType.is("Обычный")) {
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

                interpolated = new Vector3d(interpolated.x, interpolated.y + yOffset, interpolated.z);
                RenderSystem.translated(interpolated.x, interpolated.y, interpolated.z);
                RenderSystem.rotatef(playerYaw, 0.0F, -1.0F, 0.0F);
                float offsetY = player.getEyeHeight() - 3.50F;

                float offsetZ = MathHelper.sin((float) Math.toRadians((double) playerPitch)) * 0.2F;
                RenderSystem.translated(0.0, (double) offsetY, 0.0);

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
                buffer.pos(interpolated.x, interpolated.y + 1.5, interpolated.z).color(hatColor).endVertex();

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
    }
}
