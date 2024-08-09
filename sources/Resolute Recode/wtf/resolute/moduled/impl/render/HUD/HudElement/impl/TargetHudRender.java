package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.item.ItemStack;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.utiled.Stencil.StencilUtil;
import wtf.resolute.utiled.animations.Animation;
import wtf.resolute.utiled.animations.Direction;
import wtf.resolute.utiled.animations.impl.EaseBackIn;
import wtf.resolute.utiled.client.ClientUtil;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.utiled.client.HudUtil;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.math.Vector4i;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.Scissor;
import wtf.resolute.utiled.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static wtf.resolute.moduled.impl.render.HUD.HUD.targethudhat;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TargetHudRender implements ElementRenderer {
    final StopWatch stopWatch = new StopWatch();
    final Dragging drag;
    PlayerEntity entity = null;
    boolean allow;
    final Animation animation = new EaseBackIn(400, 1, 1);
    float healthAnimation = 0.0f;
    float absorptionAnimation = 0.0f;

    @Override
    public void render(EventDisplay eventDisplay) {
        entity = (PlayerEntity) getTarget(entity);
        boolean out = !allow || stopWatch.isReached(1000);
        animation.setDuration(out ? 400 : 300);
        animation.setDirection(out ? Direction.BACKWARDS : Direction.FORWARDS);

        if (animation.getOutput() == 0.0f) {
            entity = null;
        }

        if (entity != null) {
            float posX = drag.getX();
            float posY = drag.getY();

            float headSize = 32;
            float spacing = 5;

            float width = 172 / 1.5f;
            float height = 59 / 1.5f;
            drag.setWidth(width);
            drag.setHeight(height);
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
            int firstColor = ColorUtils.getColorStyle(0);
            int secondColor = ColorUtils.getColorStyle(100);
            sizeAnimation(posX + (width / 2), posY + (height / 2), animation.getOutput());
            DisplayUtils.drawRoundedRect(posX, posY, width, height, 3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            StencilUtil.initStencilToWrite(); // Баффер вызывается
            DisplayUtils.drawRoundedRect(posX + spacing-1, posY + spacing - 1, headSize, headSize, 3, -1);
            StencilUtil.readStencilBuffer(1);
            if (targethudhat.is("1")) {
                DisplayUtils.drawFace(posX + spacing-1, posY + spacing - 1, 8F, 8F, 8F, 8F, headSize, headSize, 64f, 64f, (AbstractClientPlayerEntity) entity);
            }
            if (targethudhat.is("2")) {
                InventoryScreen.drawEntityOnScreen((int) (posX + spacing + 13), (int) (posY + spacing + 48), 25, -20.0f, 10.0f, (AbstractClientPlayerEntity) entity);
            }
            StencilUtil.uninitStencilBuffer();// Баффер заканчивается

            Scissor.push();
            Scissor.setFromComponentCoordinates(testX, testY, testW - 6, testH);
            Fonts.sfMedium.drawText(eventDisplay.getMatrixStack(), entity.getName().getString(), posX + headSize + spacing + spacing -2, posY + spacing + 1 - 1, -1, 8);
            Scissor.unset();
            Scissor.pop();
            drawItemStack(posX + headSize + spacing + spacing -3,
                    posY + spacing + 1 + spacing + spacing - 1, 10);
            DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing -3, posY + height - spacing * 2 - 3, (width - 42), 7, new Vector4f(4, 4, 4, 4), ColorUtils.rgb(32, 32, 32));

            Vector4i vector4i2 = new Vector4i(new Color(178, 138, 1).getRGB(),new Color(178, 138, 1).getRGB(),new Color(255, 193, 0).getRGB(),new Color(255, 193, 0).getRGB());
            Vector4i vector4i1 = new Vector4i(new Color(7, 117, 3).getRGB(),new Color(7, 117, 3).getRGB(),new Color(8, 255, 0).getRGB(),new Color(8, 255, 0).getRGB());
            Vector4i vector4i3 = new Vector4i(new Color(171, 13, 13).getRGB(),new Color(171, 13, 13).getRGB(),new Color(255, 0, 0).getRGB(),new Color(255, 0, 0).getRGB());
            if (hp < 100) {
                DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing -3, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, new Vector4f(2, 2, 2, 2), vector4i1);
            }
            if (hp < 10) {
                DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing -3, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, new Vector4f(2, 2, 2, 2), vector4i2);
            }
            if (hp < 5) {
                DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing -3, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, new Vector4f(2, 2, 2, 2), vector4i3);
            }
            Fonts.sfMedium.drawCenteredText(eventDisplay.getMatrixStack(),""  + ((int) hp + (int) mc.player.getAbsorptionAmount()),posX + headSize + spacing + spacing -3 + (width - 42) / 2,posY + height - spacing * 2 - 3,-1,7);
            GlStateManager.popMatrix();
        }
    }
    private void drawItemStack(float x, float y, float offset) {
        entity = (PlayerEntity) getTarget(entity);
        List<ItemStack> stackList = new ArrayList<>(Arrays.asList(entity.getHeldItemMainhand(), entity.getHeldItemOffhand()));
        stackList.addAll((Collection<? extends ItemStack>) entity.getArmorInventoryList());

        final AtomicReference<Float> posX = new AtomicReference<>(x);

        stackList.stream()
                .filter(stack -> !stack.isEmpty())
                .forEach(stack -> HudUtil.drawItemStack(stack,
                        posX.getAndAccumulate(offset, Float::sum),
                        y,
                        true,
                        true, 0.6f));
    }
    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = ResoluteInfo.getInstance().getFunctionRegistry().getKillAura().getTarget();
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
                                float radius, int alpha) {

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.setAlpha(ColorUtils.getColor(0), alpha)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, alpha));
    }
}
