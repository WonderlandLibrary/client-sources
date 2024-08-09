package  src.Wiksi.ui.display.impl;

import com.jhlabs.composite.ColorDodgeComposite;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.animations.Animation;
import src.Wiksi.utils.animations.Direction;
import src.Wiksi.utils.animations.impl.EaseBackIn;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.*;
import src.Wiksi.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.Color;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TargetHUD2Renderer implements ElementRenderer {
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
    private ItemStack stack;

    public void render(EventDisplay eventDisplay) {
        this.entity = this.getTarget(this.entity);
        float rounding = 6.0F;
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
            float headSize = 36.0F;
            float spacing = 5.0F;
            float width = 111.0F;
            float height = 38.0F;
            this.drag.setWidth(width);
            this.drag.setHeight(height);
            float shrinking = 1.5F;
            Score score = mc.world.getScoreboard().getOrCreateScore(this.entity.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
            float hp = entity.getHealth();
            float maxHp = entity.getMaxHealth();
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
            this.hurtAnimation = MathUtil.fast(this.hurtAnimation, this.entity.hurtTime > 0 ? 208.0F : 0.0F, 16.0F);
            this.second = MathUtil.fast(this.second, this.entity.hurtTime > 0 ? 77.0F : 0.0F, 16.0F);
            this.third = MathUtil.fast(this.third, this.entity.hurtTime > 0 ? 77.0F : 0.0F, 16.0F);
            float halfAnimationValueRest = (1.0F - animationValue) / 2.0F;
            GlStateManager.pushMatrix();
            Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();
            sizeAnimation(posX + width / 2.0F, posY + height / 2.0F, this.animation.getOutput());
            KawaseBlur.blur.updateBlur(2,1);
            KawaseBlur.blur.render(()->{
                DisplayUtils.drawRoundedRect(posX + 37.0F, posY + 10.6f,  1.5F, 28.7F, new Vector4f(2.0F, 3.0F, 3.0F, 3.0F), (new Color(31, 31, 31, 140)).getRGB());
                DisplayUtils.drawRoundedRect(posX, posY + height / 6.0F, width, height, new Vector4f(9.0F, 10.0F, 9.0F, 9.0F), (new Color(25, 25, 25, 150)).getRGB());
            });
            DisplayUtils.drawRoundedRect(posX + 37.0F, posY + 10.6f,  1.5F, 29.7F, new Vector4f(2.0F, 3.0F, 3.0F, 3.0F), (new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB())));
            DisplayUtils.drawRoundedRect(posX, posY + height / 6.1F, width, height, new Vector4f(9.0F, 9.0F, 9.0F, 9.0F), (new Color(31, 30, 30, 150)).getRGB());

            DisplayUtils.drawRoundedRect(posX + 43.0F, posY + height / 1.41F + height / 4.0F - 2.0F, (width - 53.0F) * this.healthAnimation, 5.2F, new Vector4f(2.1F, 3.0F, 3.0F, 3.0F), new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB()));
            DisplayUtils.drawRoundedRect(posX + 43.0F, posY + height / 1.41F + height / 4.0F - 2.0F, (width - 53.0F) * this.absorptionAnimation, 5.2F, new Vector4f(2.0F, 2.0F, 2.0F, 2.0F), (new Color(255, 200, 0)).getRGB());
            DisplayUtils.drawRoundedRect(posX + 61.9F, posY + 22.8f,  0.5F, 8.4F, new Vector4f(1F, 1F, 1F, 1f), (new Color(255, 255, 255, 255)).getRGB());
            Scissor.push();
            Scissor.setFromComponentCoordinates(posX, posY, width, height);
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), "Здоровье: " , posX + 44.0F, posY + height / 1.4F - 2.40F, -1, 6F);
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), "" +  ((int) hp + (int) mc.player.getAbsorptionAmount()), posX + 83.0F, posY + height / 1.4F - 2.4F, -1, 6.5F);
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), this.entity.getName().getString(), posX + 44.0F, posY + height / 2F - 3.0F, -1, 7.1F);
            Scissor.pop();

            int anim = (int)this.hurtAnimation;
            int anim2 = (int)this.second;
            int anim3 = (int)this.third;
            drawTargetHead(entity,posX + 11.6F - 5.0F, posY + 17.4F - 5.0F, 26.5F, 26.5F);
            drawItemStack(stack, posX + 63,posY + height / 4 - 8 + Fonts.sfbold.getHeight(12f) + 10, true, 7.7f);
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

        return target;
    }

    private void drawItemStack(ItemStack stack, float x, float y, boolean b, float offset) {
        List<ItemStack> stacks = new ArrayList<>(Arrays.asList(entity.getHeldItemMainhand(), entity.getHeldItemOffhand()));
        entity.getArmorInventoryList().forEach(stacks::add);
        stacks.removeIf(w -> w.getItem() instanceof AirItem);
        Collections.reverse(stacks);
        final AtomicReference<Float> posX = new AtomicReference<>(x); 
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
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(18, 18, 18, alpha));
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }
}