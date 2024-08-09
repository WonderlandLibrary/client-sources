package im.expensive.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Furious;
import im.expensive.events.EventDisplay;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.styles.Style;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TargetInfoRenderer implements ElementRenderer {
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

            float spacing = 5;

            float width = 172 / 1.5f;
            float height = 59 / 1.5f;
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
            Style style = Furious.getInstance().getStyleManager().getCurrentStyle();

            sizeAnimation(posX + (width / 2), posY + (height / 2), animation.getOutput());
            DisplayUtils.drawShadow(posX, posY + 10, width - 18, height - 12, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
            drawStyledRect(posX, posY + 10, width - 18, height - 12, 1, 255);
            Scissor.push();
            Scissor.setFromComponentCoordinates(testX, testY, testW - 6, testH);
            Fonts.sfui.drawText(eventDisplay.getMatrixStack(), entity.getName().getString(), posX - 5  + spacing + spacing, posY + 8 + spacing + 1, -1, 8);
            Fonts.sfMedium.drawText(eventDisplay.getMatrixStack(), "" + ((int) hp + (int) mc.player.getAbsorptionAmount()), posX + 70 + spacing + spacing,
                    posY + 10 + spacing + 1 + spacing + spacing, ColorUtils.rgb(255, 255, 255), 7);
            Scissor.unset();
            Scissor.pop();

            Vector4i vector4i = new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB());

            DisplayUtils.drawRoundedRect(posX - 5 + spacing + spacing, posY + height - spacing * 2 - 3, (width - 42), 7, new Vector4f(4, 4, 4, 4), ColorUtils.rgb(32, 32, 32));


            DisplayUtils.drawShadow(posX  - 5+ spacing + spacing, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, 8, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());

            DisplayUtils.drawRoundedRect(posX - 5 + spacing + spacing, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, new Vector4f(4, 4, 4, 4), vector4i);
            DisplayUtils.drawRoundedRect(posX  - 5+ spacing + spacing, posY + height - spacing * 2 - 3, (width - 42) * absorptionAnimation, 7, new Vector4f(4, 4, 4, 4), ColorUtils.rgb(100,255,15));

            GlStateManager.popMatrix();
        }
    }


    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Furious.getInstance().getFunctionRegistry().getKillAura().getTarget();
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
        Style style = Furious.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.setAlpha(ColorUtils.getColor(0), alpha)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, alpha));
    }
}
