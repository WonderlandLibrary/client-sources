package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.module.impl.util.TPSSync;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.render.GLUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.render.*;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StringUtils;
import net.minecraft.world.DimensionType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.util.*;
import java.util.List;

import static dev.darkmoon.client.utility.render.animation.AnimationUtility.animation;

@ModuleAnnotation(name = "Hud", category = Category.RENDER)
public class Hud extends Module {
    public static final MultiBooleanSetting elements = new MultiBooleanSetting("List of:",
            Arrays.asList("Information", "Inventory", "Potions", "Armor", "Timer-Indicator"));
    public static BooleanSetting hueInterpolate = new BooleanSetting("Interpolate-Color", false);
    public final List<Particle> particles = new ArrayList<>();
    public TimerHelper timerHelper = new TimerHelper();
    private final Draggable waterMarkDraggable = DragManager.create(this, "Water Mark", 10, 10);
    private final Draggable invViewDraggable = DragManager.create(this, "Inventory View", 10, 100);
    private final Draggable potionsDraggable = DragManager.create(this, "Potions", 10, 300);
    private final Draggable SchedulesDraggable = DragManager.create(this, "Schedules", 200, 200);
    private final Draggable targetHudDraggable = DragManager.create(this, "Target HUD", 300, 10);
    public float animHeight = 0;
    public float animWidth = 0.0F;
    public static boolean TPSSyncMessageDisplayed = false;
    private Vector4f targetVector;
    float maxWidth;
    int j;
    Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
    Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
    Color gradientColor11 = ColorUtility2.interpolateColorsBackAndForth(4, 0, color11, color22, false);
    Color gradientColor22 = ColorUtility2.interpolateColorsBackAndForth(4, 90, color11, color22, false);

    public Hud() {

        invViewDraggable.setWidth(170);
        invViewDraggable.setHeight(72);
        targetHudDraggable.setWidth(112);
        targetHudDraggable.setHeight(38);
    }

    @Override
    public void onDisable() {
        TPSSyncMessageDisplayed = false;
        super.onDisable();
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (mc.gameSettings.showDebugInfo) return;
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(event.getResolution().getScaledHeight());
        int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        int middleColor = ColorUtility.interpolateColorC(color, color2, 0.5f).getRGB();
        DarkMoon.getInstance().getScaleMath().pushScale();
        String coordsText;
        int itemOffset = waterMarkDraggable.getX();
        if (elements.get(0)) {
            Object[] var10001 = new Object[]{Math.hypot(mc.player.posX - mc.player.prevPosX, mc.player.posZ - mc.player.prevPosZ) * (double) mc.timer.timerSpeed * 20.0};
            String bpsText = "BPS: " + String.format("%.2f", var10001);
            String tpsText = "TPS: " + ChatFormatting.WHITE + String.format("%.0f", TPSSync.getTPS());
            if (!DarkMoon.getInstance().getModuleManager().getModule(TPSSync.class).isEnabled()) {
                if (!TPSSyncMessageDisplayed) {
                    ChatUtility.addChatMessage("Включите TPSSync для отображения информации о TPS.");
                    TPSSyncMessageDisplayed = true;
                }
            }
            coordsText = this.getCoordsText();
            Fonts.nunitoBold15.drawStringWithOutline(tpsText, 3.0, (double) (scaledHeight - 10 - Fonts.nunitoBold15.getStringHeight(coordsText) - Fonts.nunitoBold15.getStringHeight(bpsText)), -1);
            Fonts.nunitoBold15.drawStringWithOutline(bpsText, 3.0, (double) (scaledHeight - 2 - Fonts.nunitoBold15.getStringHeight(coordsText) - Fonts.nunitoBold15.getStringHeight(bpsText)), -1);
            Fonts.nunitoBold15.drawStringWithOutline(coordsText, 3.0, (double) (scaledHeight - 1 - Fonts.nunitoBold15.getStringHeight(coordsText)), -1);
        }

        if (elements.get(1)) {
            RenderUtility.drawDarkMoonShader((float) invViewDraggable.getX(), (float) invViewDraggable.getY(), 170f, 73f, 7);
            Fonts.nunitoBold16.drawString("                        inventory", (float) (this.invViewDraggable.getX() - 5 + Fonts.icons21.getStringWidth("n")), (float) this.invViewDraggable.getY() + 3.5, Color.WHITE.getRGB());
            for (itemOffset = 9; itemOffset < 36; ++itemOffset) {
                ItemStack itemStack = mc.player.inventory.getStackInSlot(itemOffset);
                if (!itemStack.isEmpty()) {
                    int index = itemOffset - 9;
                    RenderUtility.drawItemStack(itemStack, this.invViewDraggable.getX() + 5 + index % 9 * 18, this.invViewDraggable.getY() + 16 + index / 9 * 18);
                }
            }
        }
        if (elements.get(2)) {
            ScaledResolution rs = event.getResolution();
            ArrayList<PotionEffect> effects = new ArrayList();
            Iterator var21 = mc.player.getActivePotionEffects().iterator();

            while(var21.hasNext()) {
                PotionEffect potionEffect = (PotionEffect)var21.next();
                if (potionEffect.getDuration() != 0) {
                    effects.add(potionEffect);
                }
            }

            int i = 0;
            j = rs.getScaledHeight() / 2 - effects.size() * 24 / 2;

            for(Iterator var24 = effects.iterator(); var24.hasNext(); j += 25) {
                PotionEffect potionEffect = (PotionEffect)var24.next();
                Potion potion = potionEffect.getPotion();
                String power = "";
                if (potionEffect.getAmplifier() == 0) {
                    power = "I";
                } else if (potionEffect.getAmplifier() == 1) {
                    power = "II";
                } else if (potionEffect.getAmplifier() == 2) {
                    power = "III";
                } else if (potionEffect.getAmplifier() == 3) {
                    power = "IV";
                } else if (potionEffect.getAmplifier() == 4) {
                    power = "V";
                }

                String var28 = I18n.format(potionEffect.getPotion().getName(), new Object[0]);
                String s = var28 + " " + power;
                String s2 = "" + getDuration(potionEffect);
                float maxWidth = (float)(Math.max(Fonts.mntsb13.getStringWidth(s), Fonts.mntsb12.getStringWidth(s2)) + 32);
                RenderUtility.drawDarkMoonShader((float)(i + 2), (float)(j + 4), maxWidth, 22.0F, 5.0F);
                if (potion.hasStatusIcon()) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
                    int i1 = potion.getStatusIconIndex();
                    Gui.drawTexturedModalRect2(i + 5, j + 6, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }

                Fonts.tenacityBold16.drawString(s, (float)(i + 28), (float)j + 9F, Color.WHITE.getRGB());
                Fonts.tenacityBold13.drawString(s2, (float)(i + 28), (float)j + 17.5F, Color.WHITE.getRGB());
            }
            GlStateManager.popMatrix();
        }

        DarkMoon.getInstance().getScaleMath().popScale();
        int currentHour;
        if (elements.get(3)) {
            List<ItemStack> armor = Lists.reverse(mc.player.inventory.armorInventory);

            for (currentHour = 0; currentHour < 4; ++currentHour) {
                RenderUtility.drawItemStack((ItemStack) armor.get(currentHour), event.getResolution().getScaledWidth() / 2 + 13 + currentHour * 18, event.getResolution().getScaledHeight() - this.getArmorOffset());
            }
        }
    }
    protected void renderPlayer2D(float x, float y, float width, float height, AbstractClientPlayer player) {
        GLUtility.startBlend();
        mc.getTextureManager().bindTexture(player.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, (float) 8.0, (float) 8.0, 8, 8, width, height, 64.0F, 64.0F);
        GLUtility.endBlend();
    }

    private Pair<Float, Float> getTrackedCoords() {
        ScaledResolution sr = new ScaledResolution(mc);
        float width = targetHudDraggable.getWidth(), height = targetHudDraggable.getHeight();
        float x = targetVector.getX(), y = targetVector.getY();
        float entityWidth = (targetVector.getZ() - targetVector.getX());
        float entityHeight = (targetVector.getW() - targetVector.getY());
        float middleX = x + entityWidth / 2f - width / 2f;
        float middleY = y + entityHeight / 2f - height / 2f;
                return Pair.of(x + entityWidth - (width / 4f), middleY);
    }
    public static class Particle {
        public float x, y, adjustedX, adjustedY, deltaX, deltaY, size, opacity;
        public Color color;

        public void render2D() {
            RenderUtility.drawRound(x + adjustedX, y + adjustedY, size, size, (size / 2f) - .5f, ColorUtility.applyOpacity(color, opacity / 255f));
        }

        public void updatePosition() {
            for (int i = 1; i <= 2; i++) {
                adjustedX += deltaX;
                adjustedY += deltaY;
                deltaY *= 0.97;
                deltaX *= 0.97;
                opacity -= 1f;
                if (opacity < 1) opacity = 1;
            }
        }
        public void init(float x, float y, float deltaX, float deltaY, float size, Color color) {
            this.x = x;
            this.y = y;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.size = size;
            this.opacity = 254;
            this.color = color;
        }
    }
        private String getCoordsText() {
        if (mc.player == null) return "";
        StringBuilder coordsBuilder = new StringBuilder().append("XYZ: ");

        if (mc.player.getEntityWorld().provider.getDimensionType().equals(DimensionType.THE_END)) {
            coordsBuilder.append(ChatFormatting.WHITE).append(mc.player.getPosition().getX()).append(", ").append(mc.player.getPosition().getY()).append(", ").append(mc.player.getPosition().getZ()).append(ChatFormatting.RESET);
        } else if (mc.player.getEntityWorld().provider.getDimensionType().equals(DimensionType.NETHER)) {
            coordsBuilder.append(ChatFormatting.RED).append(mc.player.getPosition().getX()).append(", ").append(mc.player.getPosition().getY()).append(", ").append(mc.player.getPosition().getZ()).append(ChatFormatting.GREEN).append(" (").append(mc.player.getPosition().getX() * 8).append(", ").append(mc.player.getPosition().getY()).append(", ").append(mc.player.getPosition().getZ() * 8).append(")").append(ChatFormatting.RESET);
        } else {
            coordsBuilder.append(ChatFormatting.GREEN).append(mc.player.getPosition().getX()).append(", ").append(mc.player.getPosition().getY()).append(", ").append(mc.player.getPosition().getZ()).append(ChatFormatting.RED).append(" (").append(mc.player.getPosition().getX() / 8).append(", ").append(mc.player.getPosition().getY()).append(", ").append(mc.player.getPosition().getZ() / 8).append(")").append(ChatFormatting.RESET);
        }

        return coordsBuilder.toString();
    }
    public static void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 100.0F);
        GlStateManager.scale((float) -scale, (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F); // Добавлен поворот по Y на 0 градусов
        ent.renderYawOffset = 0.0F;
        ent.rotationYaw = 0.0F;
        ent.rotationPitch = 0.0F;
        ent.rotationYawHead = 0.0F;
        ent.prevRotationYawHead = 0.0F;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    public static String getPotionPower(PotionEffect potionEffect) {
        int amplifier = potionEffect.getAmplifier();
        String power;
        String colorCode;
        switch (amplifier) {
            case 0:
                power = "I";
                colorCode = "\u00A7a"; // Светло-зелёный цвет
                break;
            case 1:
                power = "II";
                colorCode = "\u00A72"; // Светло-зелёный цвет
                break;
            case 2:
                power = "III";
                colorCode = "\u00A74";
                break;
            case 3:
                power = "IV";
                colorCode = "\u00A74";
                break;
            case 4:
                power = "V";
                colorCode = "\u00A74";
                break;
            case 5:
                power = "VI";
                colorCode = "\u00A74";
                break;
            case 6:
                power = "VII";
                colorCode = "\u00A74";
                break;
            case 7:
                power = "VIII";
                colorCode = "\u00A74";
                break;
            case 8:
                power = "IX";
                colorCode = "\u00A74";
                break;
            case 9:
                power = "X";
                colorCode = "\u00A74";
                break;
            default:
                power = "";
                colorCode = "\u00A74";
                break;
        }
        return colorCode + power;
    }
    public static String getDuration(PotionEffect potionEffect) {
        if (potionEffect.getIsPotionDurationMax()) {
            return "**:**";
        } else {
            return StringUtils.ticksToElapsedTime(potionEffect.getDuration());
        }
    }

    private static int getPotionDurationColor(PotionEffect potionEffect) {
        int potionColor = -1;
        if (potionEffect.getDuration() < 200) {
            potionColor = new Color(190, 12, 12).getRGB();
        } else if (potionEffect.getDuration() < 400) {
            potionColor = new Color(166, 28, 33).getRGB();
        } else if (potionEffect.getDuration() > 400) {
            potionColor = new Color(255, 255, 255).getRGB();
        }
        return potionColor;
    }

    private int getArmorOffset() {
        int offset = 56;
        if (mc.player.isCreative()) {
            offset -= 15;
        }
        if (!mc.player.isCreative() && mc.player.isInsideOfMaterial(Material.WATER)) {
            offset += 10;
        }
        if (mc.player.getRidingEntity() != null && mc.player.getRidingEntity() instanceof EntityLiving) {
            EntityLiving entity = (EntityLiving) mc.player.getRidingEntity();
            offset = (int) (offset - 10 + Math.ceil((entity.getMaxHealth() - 1) / 20) * 10) + (mc.player.isCreative() ? 15 : 0);
        }
        return offset;
    }

    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    private static float getHurtPercent(EntityLivingBase entity) {
        return (entity.hurtTime - (entity.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f)) / 10f;
    }
}
