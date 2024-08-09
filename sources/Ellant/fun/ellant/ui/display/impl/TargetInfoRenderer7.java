package  fun.ellant.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.*;
import fun.ellant.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TargetInfoRenderer7 implements ElementRenderer {
    final StopWatch stopWatch = new StopWatch();
    final Dragging drag;
    LivingEntity entity = null;
    boolean allow;
    final Animation animation = new EaseBackIn(400, 1, 1);
    float healthAnimation = 0.0f;
    float absorptionAnimation = 0.0f;

    @Override
    public void render(EventDisplay eventDisplay) {
        entity = getTarget(entity);

        float rounding = 6;
        boolean out = !allow || stopWatch.isReached(1000);
        animation.setDuration(out ? 400 : 300);
        animation.setDirection(out ? Direction.BACKWARDS : Direction.FORWARDS);

        if (animation.getOutput() == 0.0f) {
            entity = null;
        }

        if (entity != null) {
            String name = entity.getName().getString();

            float posX = drag.getX();
            float posY = drag.getY();

            float headSize = 28;
            float spacing = 5;

            float width = 234 / 1.75f;
            float height = 93.75f / 1.75f;
            drag.setWidth(width);
            drag.setHeight(height);
            float shrinking = 1.5f;
            Score score = mc.world.getScoreboard().getOrCreateScore(entity.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));


            float hp = entity.getHealth();
            float maxHp = entity.getMaxHealth();
            String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();

            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime")
                    && (header.contains("анархия") || header.contains("гриферский")) && entity instanceof PlayerEntity) {
                hp = score.getScorePoints();
                maxHp = 20;
            }
            healthAnimation = MathUtil.fast(healthAnimation, MathHelper.clamp(hp / maxHp, 0, 1), 10);
            absorptionAnimation = MathUtil.fast(absorptionAnimation, MathHelper.clamp(entity.getAbsorptionAmount() / maxHp, 0, 1), 10);


            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime")
                    && (header.contains("анархия") || header.contains("гриферский")) && entity instanceof PlayerEntity) {
                hp = score.getScorePoints();
                maxHp = 20;
            }



            float animationValue = (float) animation.getOutput();

            float halfAnimationValueRest = (1 - animationValue) / 2f;

            float testX = posX + (width * halfAnimationValueRest);
            float testY = posY + (height * halfAnimationValueRest);
            float testW = width * animationValue;
            float testH = height * animationValue;
            int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());

            GlStateManager.pushMatrix();

            sizeAnimation(posX + (width / 2), posY + (height / 2), animation.getOutput());
            float finalPosY = posY;
            drawStyledRect(posX, finalPosY, width, height, 8);

            DisplayUtils.drawShadow(posX, posY, width, height, 15, ColorUtils.rgba(21,24,40,165));


            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(posX + spacing + 77, posY + spacing, 75 / 1.75f, 75 / 1.75f,8, ColorUtils.rgba(25,26,40,165));
            Stencil.readStencilBuffer(1);
            drawTargetHead(entity, posX + spacing + 77, posY + spacing, 75 / 1.75f, 75 / 1.75f);
            Stencil.uninitStencilBuffer();
            Scissor.push();

            Scissor.setFromComponentCoordinates(testX, testY, testW - 6, testH);

            Fonts.sfui.drawText(eventDisplay.getMatrixStack(), entity.getName().getString(), posX + headSize + spacing + spacing - 28, posY + spacing + 12, -1, 8);
            Fonts.sfMedium.drawText(eventDisplay.getMatrixStack(), "HP: " + ((int) hp + (int) mc.player.getAbsorptionAmount()), posX + headSize + spacing + spacing - 28,
                    posY + spacing + 22, ColorUtils.rgb(200, 200, 200), 7);
            Scissor.unset();
            Scissor.pop();

            DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing - 28, posY + height - spacing * 2 - 3, (width - 66), 4, new Vector4f(4, 4, 4, 4), ColorUtils.rgb(32, 32, 32));

            drawArmor(eventDisplay, posX + headSize + spacing + spacing - 28, posY + spacing + 32);
            int healthColor = interpolateColor(ColorUtils.rgb(255, 0, 0), ColorUtils.rgb(0, 255, 0), healthAnimation);

            DisplayUtils.drawShadow(posX + headSize + spacing + spacing - 28, posY + height - spacing * 2 - 3, (width - 66) * healthAnimation, 4, 8, healthColor);
            DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing - 28, posY + height - spacing * 2 - 3, (width - 66) * healthAnimation, 4, new Vector4f(4, 4, 4, 4), healthColor);

            GlStateManager.popMatrix();
        }
    }


    private int interpolateColor(int startColor, int endColor, float fraction) {
        int startAlpha = (startColor >> 24) & 0xff;
        int startRed = (startColor >> 16) & 0xff;
        int startGreen = (startColor >> 8) & 0xff;
        int startBlue = startColor & 0xff;

        int endAlpha = (endColor >> 24) & 0xff;
        int endRed = (endColor >> 16) & 0xff;
        int endGreen = (endColor >> 8) & 0xff;
        int endBlue = endColor & 0xff;

        int alpha = (int) (startAlpha + (endAlpha - startAlpha) * fraction);
        int red = (int) (startRed + (endRed - startRed) * fraction);
        int green = (int) (startGreen + (endGreen - startGreen) * fraction);
        int blue = (int) (startBlue + (endBlue - startBlue) * fraction);

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }




    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Ellant.getInstance().getFunctionRegistry().getKillAura().getTarget();
        LivingEntity target = nullTarget;
        if (auraTarget != null) {
            stopWatch.reset();
            allow = true;
            target = auraTarget;
        } else if (mc.currentScreen instanceof ChatScreen) {
            stopWatch.reset();
            allow = true;
            target = mc.player;
        } else {
            allow = false;
        }
        return target;
    }

    public void drawTargetHead(LivingEntity entity, float x, float y, float width, float height) {
        if (entity != null) {
            EntityRenderer<? super LivingEntity> rendererManager = mc.getRenderManager().getRenderer(entity);
            drawFace(rendererManager.getEntityTexture(entity), x, y, 8F, 8F, 8F, 8F, width, height, 64F, 64F, entity);
        }
    }


    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0);
    }

    public void drawFace(ResourceLocation res, float d,
                         float y,
                         float u,
                         float v,
                         float uWidth,
                         float vHeight,
                         float width,
                         float height,
                         float tileWidth,
                         float tileHeight,
                         LivingEntity target) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        mc.getTextureManager().bindTexture(res);
        float hurtPercent = (target.hurtTime - (target.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f)) / 10.0f;
        GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
        AbstractGui.drawScaledCustomSizeModalRect(d, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 90));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 24, 40, 90));
    }
    private void drawArmor(EventDisplay eventDisplay, float x, float y) {
        float spacing = 11.5f;
        float scale = 0.7f;
        float offsetX = 0;

        ItemStack helmet = entity.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);
        ItemStack leggings = entity.getItemStackFromSlot(EquipmentSlotType.LEGS);
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        ItemStack heldItem = entity.getHeldItemOffhand();

        boolean allEmpty = (helmet == null || helmet.isEmpty()) &&
                (chestplate == null || chestplate.isEmpty()) &&
                (leggings == null || leggings.isEmpty()) &&
                (boots == null || boots.isEmpty()) &&
                (heldItem == null || heldItem.isEmpty());

        if (allEmpty) {
            float textX = x - 0;
            float textY = y - 30;
            Fonts.sfui.drawText(eventDisplay.getMatrixStack(), "", textX, textY, -1, 5.8f);
        } else {
            if (helmet != null && !helmet.isEmpty()) {
                renderItemStack(eventDisplay, helmet, x + offsetX, y - 33, scale);
                offsetX += spacing;
            }
            if (chestplate != null && !chestplate.isEmpty()) {
                renderItemStack(eventDisplay, chestplate, x + offsetX, y - 33, scale);
                offsetX += spacing;
            }
            if (leggings != null && !leggings.isEmpty()) {
                renderItemStack(eventDisplay, leggings, x + offsetX, y - 33, scale);
                offsetX += spacing;
            }
            if (boots != null && !boots.isEmpty()) {
                renderItemStack(eventDisplay, boots, x + offsetX, y - 33, scale);
                offsetX += spacing;
            }
            if (heldItem != null && !heldItem.isEmpty()) {
                renderItemStack(eventDisplay, heldItem, x + offsetX, y - 31f, scale * 0.63f);
            }
        }
    }

    private void renderItemStack(EventDisplay eventDisplay, ItemStack stack, float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.enableDepthTest();
        GlStateManager.scalef(scale, scale, scale);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, (int) (x / scale), (int) (y / scale));
        mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, stack, (int) (x / scale), (int) (y / scale),
                stack.getCount() > 1 ? String.valueOf(stack.getCount()) : "");
        GlStateManager.disableDepthTest();
        GlStateManager.popMatrix();
    }

}