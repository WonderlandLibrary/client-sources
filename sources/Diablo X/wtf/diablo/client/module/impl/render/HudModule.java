package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSettings;
import wtf.diablo.auth.DiabloSession;
import wtf.diablo.client.core.api.BuildTypeEnum;
import wtf.diablo.client.core.api.ClientInformation;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.gui.draggable.AbstractDraggableElement;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.MultiModeSetting;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.text.DecimalFormat;

@ModuleMetaData(
        name = "HUD",
        description = "Renders the HUD overlay",
        category = ModuleCategoryEnum.RENDER
)
public final class HudModule extends AbstractModule {
    private final MultiModeSetting<HudAdditions> hudAdditions = new MultiModeSetting<>("HudAdditions", HudAdditions.values(), HudAdditions.COORDS);
    private final BooleanSetting potionEffects = new BooleanSetting("Potion Effects", true);
    private final BooleanSetting displayArmor = new BooleanSetting("Display Armor", true);
    private final ModeSetting<WatermarkMode> watermarkMode = new ModeSetting<>("Watermark Mode", WatermarkMode.DEFAULT);

    public HudModule() {
        this.toggle(true);
        this.registerSettings(hudAdditions, potionEffects, displayArmor, watermarkMode);
    }

    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @EventHandler
    private final Listener<OverlayEvent> overlayEvent = event -> {
        final BuildTypeEnum buildTypeEnum = Diablo.getInstance().getBuildType();

        /*
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.4F);
        if (buildTypeEnum != BuildTypeEnum.RELEASE) {
            final String watermarkText = "This is a " + buildTypeEnum.name() + " build of Diablo X. This is a unfinished product";

            mc.fontRendererObj.drawString(watermarkText, event.getScaledResolution().getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(watermarkText) / 2, event.getScaledResolution().getScaledHeight() / 6, new Color(227, 225, 225, 64).getRGB());
        }
        GlStateManager.popMatrix();
         */

        int currentY = 0;
        for (final HudAdditions addition : hudAdditions.getValue()) {
            currentY += mc.fontRendererObj.FONT_HEIGHT;

            String additionString = addition.getName();
            switch (addition) {
                case FPS:
                    additionString = String.format("FPS%s: %s", ChatFormatting.WHITE, Minecraft.getDebugFPS());
                    break;

                case BPS:
                    additionString = String.format("BPS%s: %s", ChatFormatting.WHITE, decimalFormat.format(getBPS()));
                    break;

                case COORDS:
                    final BlockPos position = mc.thePlayer.getPosition();
                    additionString = String.format("XYZ%s: %s, %s, %s", ChatFormatting.WHITE, position.getX(), position.getY(), position.getZ());
                    break;
            }

            mc.fontRendererObj.drawStringWithShadow(additionString, 2, event.getScaledResolution().getScaledHeight() - 1 - currentY, ColorModule.getColor(0));
        }

        if (potionEffects.getValue())
            drawPotionEffects(event);

        if (displayArmor.getValue())
            drawArmor(event);

        /*
        final int numPolygons = 100;
        final float lineWidth = 1.0f;

        GlStateManager.pushMatrix();

        // Calculate the radius based on FOV setting
        final double radius = 80;

        // Draw circle outline on screen for fov
        GlStateManager.translate(event.getScaledResolution().getScaledWidth() / 2.0, event.getScaledResolution().getScaledHeight() / 2.0, 0);
        GlStateManager.rotate(mc.gameSettings.fovSetting, 0, 0, 1);
        GlStateManager.translate(-event.getScaledResolution().getScaledWidth() / 2.0, -event.getScaledResolution().getScaledHeight() / 2.0, 0);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        // Draw the circle outline with the specified number of polygons
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int i = 0; i <= numPolygons; i++) {
            double angle = Math.PI * 2 * i / numPolygons;
            GL11.glVertex2d(event.getScaledResolution().getScaledWidth() / 2.0 + Math.sin(angle) * radius, event.getScaledResolution().getScaledHeight() / 2.0 + Math.cos(angle) * radius);
        }
        GL11.glEnd();

        GlStateManager.popMatrix();

         */

        final ClientInformation clientInformation = Diablo.getInstance().getClientInformation();
        final DiabloSession diabloSession = Diablo.getInstance().getDiabloSession();

        final String formattedUserText = ChatFormatting.BOLD.toString() + ChatFormatting.DARK_GRAY + clientInformation.getVersion().name() + ChatFormatting.RESET + ChatFormatting.GRAY + " | " + ChatFormatting.WHITE + diabloSession.getUsername() + ChatFormatting.WHITE +" - " + diabloSession.getUid();

        final int userTextX = event.getScaledResolution().getScaledWidth() - mc.fontRendererObj.getStringWidth(formattedUserText) - 2;
        final int userTextY = event.getScaledResolution().getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 2;

        mc.fontRendererObj.drawStringWithShadow(formattedUserText, userTextX, userTextY, ColorModule.getColor(0));
    };

    private void drawArmor(final OverlayEvent event) {
        final int yOff = mc.theWorld.getWorldInfo().getGameType().equals(WorldSettings.GameType.SURVIVAL) ? 58 : 38;
        int baseX = event.getScaledResolution().getScaledWidth() / 2;

        baseX += 20;
        for (int i = 3; i >= 0; i--) {
            final ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
            if (itemStack != null) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, baseX, event.getScaledResolution().getScaledHeight() - yOff);
                baseX += 18;
            }
        }
    }

    private void drawPotionEffects(final OverlayEvent event) {
        int currentY = event.getScaledResolution().getScaledHeight() - 14;
        for (final PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {
            currentY -= mc.fontRendererObj.FONT_HEIGHT;

            final int color = Potion.potionTypes[potionEffect.getPotionID()].getLiquidColor();

            final String potionString = String.format("%s %S: %s%s", I18n.format(potionEffect.getEffectName()), potionEffect.getAmplifier() + 1, ChatFormatting.GRAY, Potion.getDurationString(potionEffect));

            mc.fontRendererObj.drawStringWithShadow(potionString, event.getScaledResolution().getScaledWidth() - mc.fontRendererObj.getStringWidth(potionString) - 2, currentY, color);
        }
    }

    private double getBPS() {
        return mc.thePlayer.getDistance(mc.thePlayer.prevPosX, mc.thePlayer.posY, mc.thePlayer.prevPosZ) * mc.getTimer().timerSpeed * mc.getTimer().ticksPerSecond;
    }

    final AbstractDraggableElement watermarkElement = new AbstractDraggableElement("Watermark", 4, 4, 0, 0, this) {
        @Override
        protected void draw() {
            final String clientName = Diablo.getInstance().getClientInformation().getDisplayName();
            final String watermark = clientName.charAt(0) + (watermarkMode.getValue() == WatermarkMode.DEFAULT ? ChatFormatting.GRAY.toString() : "") + clientName.substring(1);

            final String diabloWatermark = clientName.charAt(0) + ChatFormatting.GRAY.toString() + clientName.substring(1);

            setWidth(mc.fontRendererObj.getStringWidth(watermark));
            setHeight(mc.fontRendererObj.FONT_HEIGHT);

            int color = ColorModule.getColor(0);

            if (watermarkMode.getValue() == WatermarkMode.SOLID)
                color = ColorModule.getPrimaryColor().getRGB();

            switch (watermarkMode.getValue()) {
                case DEFAULT:
                case SOLID:
                    mc.fontRendererObj.drawStringWithShadow(watermark, 0, 0, color);
                    break;
            }

        }
    };

    enum HudAdditions implements IMode {
        FPS("FPS"),
        BPS("BPS"),
        COORDS("Coords");

        private final String name;

        HudAdditions(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    enum WatermarkMode implements IMode {
        DEFAULT("Default"),
        SOLID("Solid"),
        DIABLO("Diablo")
        ;

        private final String name;

        WatermarkMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}