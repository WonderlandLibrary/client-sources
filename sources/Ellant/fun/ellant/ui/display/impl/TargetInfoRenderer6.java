package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.shader.ShaderUtil;
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

public class TargetInfoRenderer6
        implements ElementRenderer {
    private final StopWatch stopWatch = new StopWatch();
    private final Dragging drag;
    private final ShaderUtil shaderUtil = new ShaderUtil("head");
    private LivingEntity entity = null;
    private boolean allow;
    private final Animation animation = new EaseBackIn(132, 1.0, 1.0f);
    private float healthAnimation = 0.0f;
    private float absorptionAnimation = 0.0f;
    private float hurtAnimation = 0.0f;
    private float second = 0.0f;
    private float third = 0.0f;

    @Override
    public void render(EventDisplay eventDisplay) {
        this.entity = this.getTarget(this.entity);
        float rounding = 6.0f;
        boolean out = !this.allow || this.stopWatch.isReached(420L);
        this.animation.setDuration(out ? 400 : 300);
        this.animation.setDirection(out ? Direction.BACKWARDS : Direction.FORWARDS);
        if (this.animation.getOutput() == 0.0) {
            this.entity = null;
        }
        if (this.entity != null) {
            String header;
            String name = this.entity.getName().getString();
            float posX = this.drag.getX();
            float posY = this.drag.getY();
            float headSize = 28.0f;
            float spacing = 5.0f;
            float width = 90.0f;
            float height = 28.0f;
            this.drag.setWidth(width);
            this.drag.setHeight(height);
            float shrinking = 1.5f;
            Score score = TargetInfoRenderer.mc.world.getScoreboard().getOrCreateScore(this.entity.getScoreboardName(), TargetInfoRenderer.mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
            float hp = this.entity.getHealth();
            float maxHp = this.entity.getMaxHealth();
            String string = header = TargetInfoRenderer.mc.ingameGUI.getTabList().header == null ? " " : TargetInfoRenderer.mc.ingameGUI.getTabList().header.getString().toLowerCase();
            if (mc.getCurrentServerData() != null && TargetInfoRenderer.mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("\u0430\u043d\u0430\u0440\u0445\u0438\u044f") || header.contains("\u0433\u0440\u0438\u0444\u0435\u0440\u0441\u043a\u0438\u0439")) && this.entity instanceof PlayerEntity) {
                hp = score.getScorePoints();
                maxHp = 20.0f;
            }
            this.healthAnimation = MathUtil.fast(this.healthAnimation, MathHelper.clamp(hp / maxHp, 0.0f, 1.0f), 10.0f);
            this.absorptionAnimation = MathUtil.fast(this.absorptionAnimation, MathHelper.clamp(this.entity.getAbsorptionAmount() / maxHp, 0.0f, 1.0f), 10.0f);
            if (mc.getCurrentServerData() != null && TargetInfoRenderer.mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("\u0430\u043d\u0430\u0440\u0445\u0438\u044f") || header.contains("\u0433\u0440\u0438\u0444\u0435\u0440\u0441\u043a\u0438\u0439")) && this.entity instanceof PlayerEntity) {
                hp = score.getScorePoints();
                maxHp = 20.0f;
            }
            float animationValue = (float)this.animation.getOutput();
            this.hurtAnimation = MathUtil.fast(this.hurtAnimation, this.entity.hurtTime > 0 ? 208.0f : 0.0f, 15.0f);
            this.second = MathUtil.fast(this.second, this.entity.hurtTime > 0 ? 77.0f : 0.0f, 15.0f);
            this.third = MathUtil.fast(this.third, this.entity.hurtTime > 0 ? 77.0f : 0.0f, 15.0f);
            float halfAnimationValueRest = (1.0f - animationValue) / 2.0f;
            GlStateManager.pushMatrix();
            Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
            TargetInfoRenderer.sizeAnimation(posX + width / 2.0f, posY + height / 2.0f, this.animation.getOutput());
            DisplayUtils.drawRoundedRect(posX, posY, width, height / 2.0f, new Vector4f(4.0f, 0.0f, 4.0f, 0.0f), new Color(12, 13, 13).getRGB());
            DisplayUtils.drawRoundedRect(posX, posY + height / 2.0f, width, height / 2.0f, new Vector4f(0.0f, 4.0f, 0.0f, 4.0f), new Color(25, 25, 25, 180).getRGB());
            DisplayUtils.drawRoundedRect(posX + 5.0f, posY + height / 2.0f + height / 4.0f - 2.0f, width - 10.0f, 3.0f, new Vector4f(2.0f, 2.0f, 2.0f, 2.0f), new Color(21, 21, 21).getRGB());
            DisplayUtils.drawShadow(posX + 5.0f, posY + height / 2.0f + height / 4.0f - 2.0f, (width - 10.0f) * this.healthAnimation, 3.0f, 6, new Color(0, 0, 0).getRGB());
            DisplayUtils.drawRoundedRect(posX + 5.0f, posY + height / 2.0f + height / 4.0f - 2.0f, (width - 10.0f) * this.healthAnimation, 3.0f, new Vector4f(2.0f, 2.0f, 2.0f, 2.0f), new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB()));
            DisplayUtils.drawRoundedRect(posX + 5.0f, posY + height / 2.0f + height / 4.0f - 2.0f, (width - 10.0f) * this.absorptionAnimation, 3.0f, new Vector4f(2.0f, 2.0f, 2.0f, 2.0f), new Color(255, 200, 0).getRGB());
            DisplayUtils.drawRoundedRect(posX, posY, 16.0f, height / 2.0f, new Vector4f(4.0f, 0.0f, 0.0f, 0.0f), new Color(18, 18, 18).getRGB());
            Scissor.push();
            Scissor.setFromComponentCoordinates(posX, posY, width, height);
            Fonts.sfbold.drawText(eventDisplay.getMatrixStack(), this.entity.getName().getString(), posX + 19.0f, posY + height / 4.0f - 4.0f, -1, 8.0f);
            Scissor.pop();
            int anim = (int)this.hurtAnimation;
            int anim2 = (int)this.second;
            int anim3 = (int)this.third;
            DisplayUtils.drawImage(new ResourceLocation("expensive/images/hud/user.png"), posX + 8.0f - 5.0f, posY + 7.0f - 5.0f, 10.0f, 10.0f, this.entity.hurtTime > 0 ? new Color(anim, anim2, anim3).getRGB() : new Color(255, 255, 255).getRGB());
            GlStateManager.popMatrix();
        }
    }

    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Ellant.getInstance().getFunctionRegistry().getKillAura().getTarget();
        LivingEntity target = nullTarget;
        if (auraTarget != null) {
            this.stopWatch.reset();
            this.allow = true;
            target = auraTarget;
        } else if (TargetInfoRenderer.mc.currentScreen instanceof ChatScreen) {
            this.stopWatch.reset();
            this.allow = true;
            target = TargetInfoRenderer.mc.player;
        } else {
            this.allow = false;
        }
        return target;
    }

    public void drawTargetHead(LivingEntity entity2, float x, float y, float width, float height) {
        if (entity2 != null) {
            EntityRenderer<LivingEntity> rendererManager = (EntityRenderer<LivingEntity>) mc.getRenderManager().getRenderer(entity2);
            this.drawFace(rendererManager.getEntityTexture(entity2), x, y, 8.0f, 8.0f, 8.0f, 8.0f, width, height, 64.0f, 64.0f, entity2);
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
        float hurtPercent = ((float)target.hurtTime - (target.hurtTime != 0 ? TargetInfoRenderer.mc.timer.renderPartialTicks : 0.0f)) / 10.0f;
        GL11.glColor4f(1.0f, 1.0f - hurtPercent, 1.0f - hurtPercent, 1.0f);
        AbstractGui.drawScaledCustomSizeModalRect(d, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius, int alpha) {
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(18, 18, 18, alpha));
    }

    public TargetInfoRenderer6(Dragging drag) {
        this.drag = drag;
    }
}