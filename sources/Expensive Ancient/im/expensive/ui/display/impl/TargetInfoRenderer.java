package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.styles.Style;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import lombok.AccessLevel;
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
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TargetInfoRenderer implements ElementRenderer {
    final StopWatch stopWatch = new StopWatch();
    final Dragging drag;
    LivingEntity entity = null;
    boolean shouldToBack;
    final Animation animation = new EaseBackIn(400, 1, 1);
    float healthAnimation = 0.0f, absorptionAnimation = 0.0f;
    float posX, posY;

    static final float width = 114.6f;
    static final float height = 39.3f;
    static final float spacing = 5;
    static final float rounding = 6;
    static final float headSize = 28;

    public TargetInfoRenderer(Dragging drag) {
        this.drag = drag;
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        posX = drag.getX();
        posY = drag.getY();
        entity = getTarget(entity);
        boolean backAnimation = !shouldToBack || stopWatch.isReached(1000);
        animation.setDuration(backAnimation ? 400 : 300);
        animation.setDirection(backAnimation ? Direction.BACKWARDS : Direction.FORWARDS);

        if (animation.getOutput() == 0.0f) {
            entity = null;
        }

        if (entity != null) {
            drag.setWidth(width);
            drag.setHeight(height);

            float health = fix1000Health(entity.getHealth());
            float maxHealth = MathHelper.clamp(entity.getMaxHealth(), 0, 20);

            healthAnimation = MathUtil.fast(healthAnimation,
                    MathHelper.clamp(health / maxHealth, 0, 1), 10);
            absorptionAnimation = MathUtil.fast(absorptionAnimation,
                    MathHelper.clamp(entity.getAbsorptionAmount() / maxHealth, 0, 1), 10);

            GlStateManager.pushMatrix();
            setSizeAnimation(animation.getOutput());
            drawBackGround(rounding);
            renderTextThatDisplaysTargetInfo(eventDisplay.getMatrixStack());
            drawTargetHead(entity, posX + spacing, posY + spacing + 1, headSize, headSize);
            renderHealthBar();
            GlStateManager.popMatrix();
        }
    }

    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Expensive.getInstance().getFunctionRegistry().getKillAura().getTarget();
        LivingEntity target = nullTarget;
        if (auraTarget != null) {
            stopWatch.reset();
            shouldToBack = true;
            target = auraTarget;
        } else if (mc.currentScreen instanceof ChatScreen) {
            stopWatch.reset();
            shouldToBack = true;
            target = mc.player;
        } else {
            shouldToBack = false;
        }
        return target;
    }

    public void drawTargetHead(LivingEntity entity, float x, float y, float width, float height) {
        if (entity != null) {
            EntityRenderer<? super LivingEntity> rendererManager = mc.getRenderManager().getRenderer(entity);
            drawFace(rendererManager.getEntityTexture(entity), x, y, 8F, 8F, 8F, 8F, width, height, 64F, 64F, entity);
        }
    }

    private void renderTextThatDisplaysTargetInfo(MatrixStack matrixStack) {
        float animationValue = (float) animation.getOutput();
        float halfAnimationValueRest = (1 - animationValue) / 2f;
        float testX = posX + (width * halfAnimationValueRest);
        float testY = posY + (height * halfAnimationValueRest);
        float testW = width * animationValue;
        float testH = height * animationValue;
        Scissor.push();
        Scissor.setFromComponentCoordinates(testX, testY, testW - 6, testH);
        String hpText = "HP: " + (entity.isInvisible() && userConnectedToFunTimeAndEntityIsPlayer()
                ? "Неизвестно" : MathUtil.round(healthAnimation * 20.0f, 0.1f));
        Fonts.sfui.drawText(matrixStack, TextFormatting.getTextWithoutFormattingCodes(entity.getName().getString()), posX + headSize + spacing + spacing, posY + spacing + 1, -1, 8);
        Fonts.sfMedium.drawText(matrixStack, hpText, posX + headSize + spacing + spacing,
                posY + spacing + 1 + spacing + spacing, ColorUtils.rgb(200, 200, 200), 7);
        Scissor.unset();
        Scissor.pop();
    }

    private void renderHealthBar() {
        Style style = Expensive.getInstance().getStyleManager().getCurrentStyle();
        Vector4i vector4i = new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB());
        DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing, posY + height - spacing * 2 - 3, (width - 42), 7, new Vector4f(4, 4, 4, 4), ColorUtils.rgb(32, 32, 32));
        DisplayUtils.drawShadow(posX + headSize + spacing + spacing, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, 8, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        DisplayUtils.drawRoundedRect(posX + headSize + spacing + spacing, posY + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, new Vector4f(4, 4, 4, 4), vector4i);
    }

    public void setSizeAnimation(double scale) {
        GlStateManager.translated(posX + (width / 2), posY + (height / 2), 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-(posX + (width / 2)), -(posY + (height / 2)), 0);
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

    private float fix1000Health(float original) {
        Score score = mc.world.getScoreboard().getOrCreateScore(entity.getScoreboardName(),
                mc.world.getScoreboard().getObjectiveInDisplaySlot(2));

        return userConnectedToFunTimeAndEntityIsPlayer() ? score.getScorePoints() : original;
    }


    private boolean userConnectedToFunTimeAndEntityIsPlayer() {
        String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
        return (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime")
                && (header.contains("анархия") || header.contains("гриферский")) && entity instanceof PlayerEntity);
    }

    private void drawBackGround(float radius) {
        Vector4i vector4i = new Vector4i(HUD.getColor(0), HUD.getColor(90), HUD.getColor(180), HUD.getColor(170));
        DisplayUtils.drawShadow(posX, posY, width, height, 10, vector4i.x, vector4i.y, vector4i.z, vector4i.w);
        DisplayUtils.drawGradientRound(posX, posY, width, height, radius + 0.5f, vector4i.x, vector4i.y, vector4i.z, vector4i.w); // outline
        DisplayUtils.drawRoundedRect(posX, posY, width, height, radius, ColorUtils.rgba(21, 21, 21, 230));
    }
}
