package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventDisplay.Type;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.player.MoveUtils;
import fun.ellant.utils.player.PlayerUtils;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(
        name = "Arrows",
        type = Category.RENDER,
        desc = "Отображает ближайшие цели"
)
public class Pointers extends Function {
    public float animationStep;
    static final ModeSetting mode = new ModeSetting("Мод", "Мод 1", new String[]{"Мод 1", "Мод 2"});
    public ModeListSetting remove = new ModeListSetting("Убрать", new BooleanSetting[]{new BooleanSetting("Метры", false)});
    public SliderSetting radius = new SliderSetting("Радиус", 90.0F, 50.0F, 150.0F, 1.0F);
    Vector4i friendColors = new Vector4i(HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 0, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 90, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 180, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 270, 1.0F));
    private float lastYaw;
    private float lastPitch;
    private float animatedYaw;
    private float animatedPitch;

    public Pointers() {
        this.addSettings(new Setting[]{mode, this.remove, this.radius});
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player != null && mc.world != null && e.getType() == Type.PRE) {
            this.animatedYaw = MathUtil.fast(this.animatedYaw, mc.player.moveStrafing * 20.0F, 5.0F);
            this.animatedPitch = MathUtil.fast(this.animatedPitch, mc.player.moveForward * 20.0F, 5.0F);
            float size = (Float)this.radius.get();
            if (mc.currentScreen instanceof InventoryScreen) {
                size += 90.0F;
            }

            if (MoveUtils.isMoving()) {
                size += 20.0F;
            }

            this.animationStep = MathUtil.fast(this.animationStep, size, 6.0F);
            if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                Iterator var3 = mc.world.getPlayers().iterator();

                while(var3.hasNext()) {
                    AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)var3.next();
                    if (PlayerUtils.isNameValid(player.getNameClear()) && mc.player != player) {
                        double x = player.lastTickPosX + (player.getPosX() - player.lastTickPosX) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getX();
                        double z = player.lastTickPosZ + (player.getPosZ() - player.lastTickPosZ) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getZ();
                        double cos = (double)MathHelper.cos((float)((double)mc.getRenderManager().info.getYaw() * 0.017453292519943295D));
                        double sin = (double)MathHelper.sin((float)((double)mc.getRenderManager().info.getYaw() * 0.017453292519943295D));
                        double rotY = -(z * cos - x * sin);
                        double rotX = -(x * cos + z * sin);
                        float angle = (float)(Math.atan2(rotY, rotX) * 180.0D / 3.141592653589793D);
                        double x2 = (double)(this.animationStep * MathHelper.cos((float)Math.toRadians((double)angle)) + (float)window.getScaledWidth() / 2.0F);
                        double y2 = (double)(this.animationStep * MathHelper.sin((float)Math.toRadians((double)angle)) + (float)window.getScaledHeight() / 2.0F);
                        x2 += (double)this.animatedYaw;
                        y2 += (double)this.animatedPitch;
                        GlStateManager.pushMatrix();
                        GlStateManager.disableBlend();
                        GlStateManager.translated(x2, y2, 0.0D);
                        GlStateManager.rotatef(angle, 0.0F, 0.0F, 1.0F);
                        Vector3d playerPos = new Vector3d(player.getPosX(), player.getPosY(), player.getPosZ());
                        Vector3d myPos = mc.player.getPositionVec();
                        double distance = playerPos.distanceTo(myPos);
                        Object[] var10001 = new Object[]{distance};
                        String distanceString = String.format("%.0f", var10001) + "m";
                        GL11.glPushMatrix();
                        int color = ColorUtils.rgb(0, 255, 0);
                        GL11.glRotatef(450.0F, 0.0F, 0.0F, 1.0F);
                        if (!(Boolean)this.remove.getValueByName("Метры").get()) {
                            Fonts.sfbold.drawText(e.getMatrixStack(), distanceString, -1.8F, 4.0F, -1, 5.0F, 0.05F);
                        }

                        GL11.glEnable(3553);
                        GL11.glRotatef(-270.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glPopMatrix();
                        drawTriangle(-3.0F, 0.0F, 3.0F, 5.0F, new Color(color));
                        GlStateManager.enableBlend();
                        GlStateManager.popMatrix();
                    }
                }
            }

            this.lastYaw = mc.player.rotationYaw;
            this.lastPitch = mc.player.rotationPitch;
        }
    }

    public static void drawTriangle(float x, float y, float width, float height, Color color) {
        ResourceLocation logo;
        if (mode.is("Мод 1")) {
            logo = new ResourceLocation("expensive/images/triangle.png");
            DisplayUtils.drawImage(logo, -3.0F, -5.0F, 18.0F, 20.5F, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 200.0F), 255), ColorUtils.setAlpha(HUD.getColor(90, 20.0F), 255), ColorUtils.setAlpha(HUD.getColor(180, 200.0F), 255), ColorUtils.setAlpha(HUD.getColor(270, 200.0F), 255)));
        } else if (mode.is("Мод 2")) {
            logo = new ResourceLocation("expensive/images/triangl.png");
            DisplayUtils.drawImage(logo, -3.0F, -5.0F, 18.0F, 20.5F, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 200.0F), 255), ColorUtils.setAlpha(HUD.getColor(90, 200.0F), 255), ColorUtils.setAlpha(HUD.getColor(180, 200.0F), 255), ColorUtils.setAlpha(HUD.getColor(270, 200.0F), 255)));
        }

    }
}