//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.animations.Animation;
import src.Wiksi.utils.animations.Direction;
import src.Wiksi.utils.animations.impl.EaseBackIn;
import src.Wiksi.utils.client.ClientUtil;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.Stencil;
import src.Wiksi.utils.render.font.Fonts;
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
    private final Animation animation = new EaseBackIn(400, 1.0, 1.0F);
    private float healthAnimation = 0.0F;
    private float absorptionAnimation = 0.0F;

    public void render(EventDisplay eventDisplay) {
        this.entity = this.getTarget(this.entity);
        float rounding = 6.0F;
        boolean out = !this.allow || this.stopWatch.isReached(1000L);
        this.animation.setDuration(out ? 400 : 300);
        this.animation.setDirection(out ? Direction.BACKWARDS : Direction.FORWARDS);
        if (this.animation.getOutput() == 0.0) {
            this.entity = null;
        }

        if (this.entity != null) {
            String name = this.entity.getName().getString();
            float posX = this.drag.getX();
            float posY = this.drag.getY();
            float headSize = 22.0F;
            float spacing = 5.0F;
            float width = 114.666664F;
            float height = 26.666666F;
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
            float halfAnimationValueRest = (1.0F - animationValue) / 2.0F;
            float testX = posX + width * halfAnimationValueRest;
            float testY = posY + height * halfAnimationValueRest;
            float testW = width * animationValue;
            float testH = height * animationValue;
            int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
            GlStateManager.pushMatrix();
            Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();
            sizeAnimation((double)(posX + width / 2.0F), (double)(posY + height / 2.0F), this.animation.getOutput());
            DisplayUtils.drawShadow(posX, posY, width, height, 9, ColorUtils.rgba(0, 0, 0, 0));
            KawaseBlur.blur.updateBlur(1.5F, 3);
            KawaseBlur.blur.render(() -> {
                this.drawStyledRect(posX, posY, width, height, 7.0F, 255);
            });
            this.drawStyledRect(posX, posY, width, height, rounding, 255);
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(posX + -2.0F + spacing, posY + spacing + -3.0F, headSize, headSize, 6.0F, style.getSecondColor().getRGB());
            Stencil.readStencilBuffer(1);
            this.drawTargetHead(this.entity, posX + -2.0F + spacing, posY + spacing + -3.0F, headSize, headSize);
            Stencil.uninitStencilBuffer();
            Scissor.push();
            Scissor.setFromComponentCoordinates((double)testX, (double)testY, (double)(testW - 6.0F), (double)testH);
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), this.entity.getName().getString(), posX + headSize + spacing + spacing, posY + spacing + 1.0F, -1, 7.5F);
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), "Health: " + ((int)hp + (int)mc.player.getAbsorptionAmount()), posX + headSize + spacing + spacing, posY + spacing + 1.0F + spacing + spacing, ColorUtils.rgb(200, 200, 200), 5.5F);
            Scissor.unset();
            Scissor.pop();
            Vector4i vector4i = new Vector4i(ColorUtils.rgba(0, 255, 0, 255), ColorUtils.rgba(0, 255, 0, 255), ColorUtils.rgba(0, 255, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
            DisplayUtils.drawRoundedRect(posX + 75.0F + headSize + spacing + spacing, posY + height - spacing * 4.7F, 2.0F, 20.0F, new Vector4f(4.0F, 4.0F, 4.0F, 4.0F), ColorUtils.rgb(32, 32, 32));
            DisplayUtils.drawShadow(posX + 75.0F + headSize + spacing + spacing, posY + height - spacing * 4.7F, 2.0F, 20.0F * this.healthAnimation, 8, ColorUtils.rgba(0, 255, 0, 255));
            DisplayUtils.drawRoundedRect(posX + 75.0F + headSize + spacing + spacing, posY + height - spacing * 4.7F, 2.0F, 20.0F * this.healthAnimation, new Vector4f(4.0F, 4.0F, 4.0F, 4.0F), vector4i);
            GlStateManager.popMatrix();
        }

    }

    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Wiksi.getInstance().getFunctionRegistry().getKillAura().getTarget();
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
            this.drawFace(rendererManager.getEntityTexture(entity), x, y, 8.0F, 8.0F, 8.0F, 8.0F, width, height, 64.0F, 64.0F, entity);
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
        Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawRoundedRect(x, y, width, height, radius + 4.0F, ColorUtils.rgba(16, 4, 16, 170));
        DisplayUtils.drawRoundedRect(x + 28.0F, y + 4.0F, width + -114.0F, 20.0F, 0.0F, ColorUtils.rgba(255, 255, 255, 255));
    }

    public TargetInfoRenderer(Dragging drag) {
        this.drag = drag;
    }
}
