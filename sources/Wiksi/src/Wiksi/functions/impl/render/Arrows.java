package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.player.MoveUtils;
import src.Wiksi.utils.player.PlayerUtils;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static src.Wiksi.functions.impl.render.HUD.getColor;

@FunctionRegister(name = "Arrows", type = Category.Render)
public class Arrows extends Function {
    public float animationStep;
    static final ModeSetting mode = new ModeSetting("Мод", "Мод1", "Мод1", "Мод2");
    public ModeListSetting remove = new ModeListSetting("Убрать", new BooleanSetting("Метры", false));
    public SliderSetting radius = new SliderSetting("Радиус", 90, 50, 150, 1.0f);
    Vector4i friendColors = new Vector4i(HUD.getColor(ColorUtils.rgb(-(-((0x462E | 0xF38 | 0x7B41) ^ 0x7FEF)), -(-((0x6DD2 | 0x1E71 | 0x5925) ^ 0x7F19)), -(-((0x4973 | 0x3B26 | 0x35BE) ^ 0x7F6F))), ColorUtils.rgb(0, -(-((0x16E2 | 0x279E | 0xE61) ^ 0x3F74)), 0), 0, 1.0f), HUD.getColor(ColorUtils.rgb(-(-((0x23D5 | 0x446D | 0x3D7A) ^ 0x7F6F)), -(-((0x348 | 0x30DB | 0x5679) ^ 0x7715)), -(-((0x3985 | 0x46D4 | 0x27CA) ^ 0x7F4F))), ColorUtils.rgb(0, -(-((0x1723 | 0x4BD0 | 0x330A) ^ 0x7F70)), 0), -(-((0xA | 0xFFFFFFC5 | 0x5B) ^ 0xFFFFFF85)), 1.0f), HUD.getColor(ColorUtils.rgb(-(-((0x33C9 | 0x64B0 | 0x39EC) ^ 0x7F6D)), -(-((0x7A5F | 0x62F8 | 0x7E30) ^ 0x7E11)), -(-((0x10DD | 0x74D7 | 0x620A) ^ 0x764F))), ColorUtils.rgb(0, -(-((0x3FFC | 0x188D | 0x1E67) ^ 0x3F74)), 0), -(-((0x3EF2 | 0x5AAB | 0x348E) ^ 0x7E4B)), 1.0f), HUD.getColor(ColorUtils.rgb(-(-((0x3E3D | 0x2EB | 0x5FF4) ^ 0x7F6F)), -(-((0x720A | 0x6FF8 | 0x6CA3) ^ 0x7F15)), -(-((0x2A72 | 0x48DB | 0x4D8C) ^ 0x6F6F))), ColorUtils.rgb(0, -(-((0x4D58 | 0x309D | 0x6239) ^ 0x7F76)), 0), -(-((0x6E26 | 0x3BC9 | 0x2538) ^ 0x7EF1)), 1.0f));
    public Arrows() {
        this.addSettings(mode, remove, radius);
    }

    private float lastYaw;
    private float lastPitch;

    private float animatedYaw;
    private float animatedPitch;

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.PRE) {
            return;
        }
        animatedYaw = MathUtil.fast(animatedYaw, (mc.player.moveStrafing) * 20, 5);

        animatedPitch = MathUtil.fast(animatedPitch, (mc.player.moveForward) * 20, 5);

        float size = radius.get();

        if (mc.currentScreen instanceof InventoryScreen) {
            size += 90;
        }

        if (MoveUtils.isMoving()) {
            size += 20;
        }
        animationStep = MathUtil.fast(animationStep, size, 6);
        if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {
                if (!PlayerUtils.isNameValid(player.getNameClear()) || mc.player == player)
                    continue;

                double x = player.lastTickPosX + (player.getPosX() - player.lastTickPosX) * mc.getRenderPartialTicks()
                        - mc.getRenderManager().info.getProjectedView().getX();
                double z = player.lastTickPosZ + (player.getPosZ() - player.lastTickPosZ) * mc.getRenderPartialTicks()
                        - mc.getRenderManager().info.getProjectedView().getZ();

                double cos = MathHelper.cos((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
                double sin = MathHelper.sin((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
                double rotY = -(z * cos - x * sin);
                double rotX = -(x * cos + z * sin);

                float angle = (float) (Math.atan2(rotY, rotX) * 180 / Math.PI);

                double x2 = animationStep * MathHelper.cos((float) Math.toRadians(angle)) + window.getScaledWidth() / 2f;
                double y2 = animationStep * MathHelper.sin((float) Math.toRadians(angle)) + window.getScaledHeight() / 2f;

                x2 += animatedYaw;
                y2 += animatedPitch;

                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                GlStateManager.translated(x2, y2, 0);
                GlStateManager.rotatef(angle, 0, 0, 1);

                Vector3d playerPos = new Vector3d(player.getPosX(), player.getPosY(), player.getPosZ());
                Vector3d myPos = Arrows.mc.player.getPositionVec();
                double distance = playerPos.distanceTo(myPos);
                String distanceString = String.format("%.0f", distance) + "m";
                GL11.glPushMatrix();
                int color = ColorUtils.rgb(0, 255, 0);
                GL11.glRotatef(450.0f, 0.0f, 0.0f, 1.0f);

                if (!((Boolean) remove.getValueByName("Метры").get()).booleanValue()) {
                    Fonts.sfbold.drawText(e.getMatrixStack(), distanceString, -1.8f, 4.0f, -1, 5.0f, 0.05f);
                }

                GL11.glEnable(-(-((0x776B | 0x41A6 | 0x6931) ^ 0x721E)));
                GL11.glRotatef(-270.0f, 0.0f, 0.0f, 1.0f);
                GL11.glPopMatrix();
                Arrows.drawTriangle(-3.0f, 0.0f, 3.0f, 5.0f, new Color(color));
                GlStateManager.enableBlend();
                GlStateManager.popMatrix();

            }
        }
        lastYaw = mc.player.rotationYaw;
        lastPitch = mc.player.rotationPitch;
    }

    public static void drawTriangle(float x, float y, float width, float height, Color color) {
        if (mode.is("Мод1")) {
            ResourceLocation logo = new ResourceLocation("Wiksi/images/triangle.png");
            DisplayUtils.drawImage(logo, -3.0f, -5.0f, 18.0f, 20.5f, new Vector4i(
                    ColorUtils.setAlpha(getColor(0, 200), 255),
                    ColorUtils.setAlpha(getColor(90, 20), 255),
                    ColorUtils.setAlpha(getColor(180, 200), 255),
                    ColorUtils.setAlpha(getColor(270, 200), 255)
            ));

        } else if (mode.is("Мод2")) {
            ResourceLocation logo = new ResourceLocation("Wiksi/images/triangl.png");
            DisplayUtils.drawImage(logo, -3.0f, -5.0f, 18.0f, 20.5f, new Vector4i(
                    ColorUtils.setAlpha(getColor(0, 200), 255),
                    ColorUtils.setAlpha(getColor(90, 200), 255),
                    ColorUtils.setAlpha(getColor(180, 200), 255),
                    ColorUtils.setAlpha(getColor(270, 200), 255)
            ));
        }
    }
}


