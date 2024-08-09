package fun.ellant.ui.display.impl;

import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.utils.math.StopWatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.Stencil;
import fun.ellant.utils.render.font.Fonts;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TargetInfoRenderer5 implements ElementRenderer {
    final StopWatch stopWatch = new StopWatch();
    final Dragging drag;
    LivingEntity entity = null;
    LivingEntity target;
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

            float headSize = 33;
            float spacing = 3;

            float width = 172 / 1.5f;
            float height = 59 / 1.5f;
            float width1 = 172 / 1.5f;
            float height2 = 89 / 1.5f;
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
            Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

            sizeAnimation(posX + (width / 2), posY + (height / 2), animation.getOutput());

//глов
            //DisplayUtils.drawShadow(posX, posY, width, height, 9, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());


            drawStyledRect(posX, posY + 1f, width, height + -2f, rounding + 4, 255);
//рендерится голова
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(posX + spacing, posY + spacing + 1, headSize, headSize, 8, Color.WHITE.getRGB());
            Stencil.readStencilBuffer(1);
            drawTargetHead(entity, posX + spacing, posY + spacing + 1, headSize, headSize);
            Stencil.uninitStencilBuffer();

            Scissor.push();
            Scissor.setFromComponentCoordinates(testX, testY, testW - 6, testH);
            Fonts.sfbold.drawText(eventDisplay.getMatrixStack(), entity.getName().getString(), posX + headSize + spacing + spacing, posY + 2 + spacing + 1, -1, 8);
            Fonts.sfbold.drawText(eventDisplay.getMatrixStack(), "HP: " + (int) hp, posX + headSize + spacing + spacing,
                    posY + 3 + spacing + 7 + spacing + spacing, ColorUtils.rgb(200, 200, 200), 7);
            Scissor.unset();
            Scissor.pop();

            Vector4i vector4i = new Vector4i(Color.GREEN.getRGB(), Color.GREEN.getRGB(), Color.GREEN.getRGB(), Color.GREEN.getRGB()); //style.getFirstColor().getRGB(), //style.getSecondColor().getRGB(), //style.getSecondColor().getRGB());
            Vector4i vector5i = new Vector4i(Color.YELLOW.getRGB(), Color.YELLOW.getRGB(), Color.YELLOW.getRGB(), Color.YELLOW.getRGB()); //style.getFirstColor().getRGB(), //style.getSecondColor().getRGB(), //style.getSecondColor().getRGB());
            Vector4i vector6i = new Vector4i(Color.RED.getRGB(), Color.RED.getRGB(), Color.RED.getRGB(), Color.RED.getRGB()); //style.getFirstColor().getRGB(), //style.getSecondColor().getRGB(), //style.getSecondColor().getRGB());

            DisplayUtils.drawRoundedRect(posX + 1 + headSize + spacing + spacing, posY + 3 + height - spacing * 3 - 6, (width - 42), 7, new Vector4f(4, 4, 4, 4), ColorUtils.rgb(32, 32, 32));

//глов на полоске
            //   DisplayUtils.drawShadow(posX + headSize + spacing + spacing, posY + 10 + height - spacing * 2 - 3, (width - 42) * healthAnimation, 7, 8, Color.BLACK.getRGB(), Color.BLACK.getRGB());


            if (hp >= 14) {
                DisplayUtils.drawRoundedRect(posX + -1 + headSize + spacing + spacing, posY + 3 + height - spacing * 3 - 6, (width - 42) * healthAnimation, 7, new Vector4f(4, 4, 4, 4), vector4i);
            } else if (hp >= 7) {
                DisplayUtils.drawRoundedRect(posX + -1 + headSize + spacing + spacing, posY + 3 + height - spacing * 3 - 6, (width - 42) * healthAnimation, 7, new Vector4f(4, 4, 4, 4), vector5i);
            } else if (hp >= 0) {
                DisplayUtils.drawRoundedRect(posX + -1 + headSize + spacing + spacing, posY + 3 + height - spacing * 3 - 6, (width - 42) * healthAnimation, 7, new Vector4f(4, 4, 4, 4), vector6i);
            }

//  DisplayUtils.drawShadow(posX + headSize + spacing + spacing, posY + -15 + height2 - spacing * 2 - 3, (width - 42) * healthAnimation, 7, 8, Color.BLACK.getRGB(), Color.BLACK.getRGB());

            GlStateManager.popMatrix();
        }
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
                                float radius, int alpha) {
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, alpha));
    }
}