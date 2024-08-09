package im.expensive.ui.display.impl;

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
import im.expensive.utils.shader.ShaderUtil;
import java.awt.Color;
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

public class TargetInfoRenderer implements ElementRenderer {
    private final StopWatch stopWatch = new StopWatch();
    private final Dragging drag;
    private LivingEntity entity = null;
    private boolean allow;
    private final Animation animation = new EaseBackIn(132, 1.0, 1.0F);
    private float healthAnimation = 0.0F;
    private float absorptionAnimation = 0.0F;
    private float hurtAnimation = 0.0F;
    private float second = 0.0F;
    private float third = 0.0F;

    public void render(EventDisplay eventDisplay) {
        this.entity = this.getTarget(this.entity);
        float rounding = 0F;
        boolean out = !this.allow || this.stopWatch.isReached(420L);
        this.animation.setDuration(out ? 400 : 300);
        this.animation.setDirection(out ? Direction.BACKWARDS : Direction.FORWARDS);
        if (this.animation.getOutput() == 0.0) {
            this.entity = null;
        }

        if (this.entity != null) {
            String name = this.entity.getName().getString();
            float posX = this.drag.getX();
            float posY = this.drag.getY();
            float headSize = 28.0F;
            float spacing = 5.0F;
            float width = 100.0F;
            float height = 35.0F;
            this.drag.setWidth(width);
            this.drag.setHeight(height);
            float shrinking = 1.5F;
            Score score = mc.world.getScoreboard().getOrCreateScore(this.entity.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
            float hp = this.entity.getHealth();
            float maxHp = this.entity.getMaxHealth();
            String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("анархия") || header.contains("гриферский")) && this.entity instanceof PlayerEntity) {
                hp = (float)score.getScorePoints();
                maxHp = 20.0F;
            }

            this.healthAnimation = MathUtil.fast(this.healthAnimation, MathHelper.clamp(hp / maxHp, 0.0F, 1.0F), 10.0F);
            this.absorptionAnimation = MathUtil.fast(this.absorptionAnimation, MathHelper.clamp(this.entity.getAbsorptionAmount() / maxHp, 0.0F, 1.0F), 10.0F);
            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("анархия") || header.contains("гриферский")) && this.entity instanceof PlayerEntity) {
                hp = (float)score.getScorePoints();
                maxHp = 20.0F;
            }

            float animationValue = (float)this.animation.getOutput();
            this.hurtAnimation = MathUtil.fast(this.hurtAnimation, this.entity.hurtTime > 0 ? 208.0F : 0.0F, 15.0F);
            this.second = MathUtil.fast(this.second, this.entity.hurtTime > 0 ? 77.0F : 0.0F, 15.0F);
            this.third = MathUtil.fast(this.third, this.entity.hurtTime > 0 ? 77.0F : 0.0F, 15.0F);
            float halfAnimationValueRest = (1.0F - animationValue) / 2.0F;
            GlStateManager.pushMatrix();
            Style style = Expensive.getInstance().getStyleManager().getCurrentStyle();
            sizeAnimation((double)(posX + width / 2.0F), (double)(posY + height / 2.0F), this.animation.getOutput());
            DisplayUtils.drawRoundedRect(posX, posY, width, height / 2.0F, new Vector4f(0F, 0F, 0F, 0F), (new Color(0, 0, 0)).getRGB());
            DisplayUtils.drawShadow(posX - 2f, posY -0f, 3, 35, 7, ColorUtils.getColor(0));
            DisplayUtils.drawRoundedRect(posX - 2f, posY - 0f, 2.5f, 35f, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F)));
            DisplayUtils.drawRoundedRect(posX, posY + height / 2.0F, width, height / 2.0F, new Vector4f(0F, 0F, 0F, 0F), (new Color(0, 0, 0, 180)).getRGB());
            DisplayUtils.drawRoundedRect(posX + 5.0F, posY + height / 2.0F + height / 4.0F - 2.0F, width - 10.0F, 3.0F, new Vector4f(0F, 0F, 0F, 0F), (new Color(21, 21, 21)).getRGB());
            DisplayUtils.drawShadow(posX + 5.0F, posY + height / 2.0F + height / 4.0F - 2.0F, (width - 10.0F) * this.healthAnimation, 3.0F, 0, (new Color(0, 0, 0)).getRGB());
            DisplayUtils.drawRoundedRect(posX + 5.0F, posY + height / 2.0F + height / 4.0F - 2.0F, (width - 10.0F) * this.healthAnimation, 3.0F, new Vector4f(0F, 0F, 0F, 0F), new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB()));
            DisplayUtils.drawRoundedRect(posX + 5.0F, posY + height / 2.0F + height / 4.0F - 2.0F, (width - 10.0F) * this.absorptionAnimation, 3.0F, new Vector4f(0F, 0F, 0F, 0F), (new Color(255, 200, 0)).getRGB());
            DisplayUtils.drawRoundedRect(posX, posY, 16.0F, height / 2.0F, new Vector4f(0F, 0F, 0F, 0F), (new Color(0, 0, 0)).getRGB());
            Scissor.push();
            Scissor.setFromComponentCoordinates((double)posX, (double)posY, (double)width, (double)height);
            Fonts.sfbold.drawText(eventDisplay.getMatrixStack(), this.entity.getName().getString(), posX + 25.0F, posY + height / 4.0F - 4.0F, -1, 8.0F);
            Scissor.pop();
            int anim = (int)this.hurtAnimation;
            int anim2 = (int)this.second;
            int anim3 = (int)this.third;
            GlStateManager.popMatrix();
        }

    }

    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Expensive.getInstance().getFunctionRegistry().getKillAura().getTarget();
        LivingEntity target = nullTarget;
        if (auraTarget != null) {
            this.stopWatch.reset();
            this.allow = true;
            target = auraTarget;
        } else if (mc.currentScreen instanceof ChatScreen) {
            this.stopWatch.reset();
            this.allow = true;
            target = mc.player;
        } else {
            this.allow = false;
        }

        return (LivingEntity)target;
    }

    public void drawTargetHead(LivingEntity entity, float x, float y, float width, float height) {
        if (entity != null) {
            EntityRenderer<? super LivingEntity> rendererManager = mc.getRenderManager().getRenderer(entity);
            this.drawFace(rendererManager.getEntityTexture(entity), x, y, 0F, 0F, 0F, 0F, width, height, 64.0F, 64.0F, entity);
        }

    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0.0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0.0);
    }

    public void drawFace(ResourceLocation res, float d, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight, LivingEntity target) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        mc.getTextureManager().bindTexture(res);
        float hurtPercent = ((float)target.hurtTime - (target.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0F)) / 10.0F;
        GL11.glColor4f(1.0F, 1.0F - hurtPercent, 1.0F - hurtPercent, 1.0F);
        AbstractGui.drawScaledCustomSizeModalRect(d, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius, int alpha) {
        Style style = Expensive.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(18, 18, 18, alpha));
    }

    public TargetInfoRenderer(Dragging drag) {
        this.drag = drag;
    }
}