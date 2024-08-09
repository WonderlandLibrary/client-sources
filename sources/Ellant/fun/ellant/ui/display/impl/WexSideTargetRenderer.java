package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WexSideTargetRenderer implements ElementRenderer {
    final StopWatch stopWatch = new StopWatch();
    final Dragging drag;
    LivingEntity entity = null;
    boolean allow;
    final Animation animation = new EaseBackIn(400, 1, 1);
    float healthAnimation = 0.0f;
    float absorptionAnimation = 0.0f;

    private final ResourceLocation kirka = new ResourceLocation("expensive/images/hud/head.png");

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
            float textWidth = Fonts.montserrat.getWidth(((int) hp + (int) mc.player.getAbsorptionAmount()) + "HP", 5, 0.15f);
            float nameWidth = Fonts.montserrat.getWidth((entity.getName().getString()), 7.5f, 0.07f);

            GlStateManager.pushMatrix();
            MatrixStack ms = eventDisplay.getMatrixStack();
            Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

            sizeAnimation(posX + (width / 2), posY + (height / 2), animation.getOutput());
            DisplayUtils.drawShadow(posX - 1, posY - 1, width - 10, height - 6, 10, ColorUtils.rgba(32,35,90,128));
            DisplayUtils.drawRoundedRect(posX - 1, posY - 1, width - 10, height - 6, 4, ColorUtils.rgba(0,0,0,156));
            DisplayUtils.drawShadow(posX + 23, posY + 33, width - 34, 6, 4, ColorUtils.rgba(32,35,90,128));
            DisplayUtils.drawRoundedRect(posX + 23, posY + 33, width - 34, 6, 4, ColorUtils.rgba(0,0,0,156));
            DisplayUtils.drawShadow(posX + 2, posY + 33, textWidth + 7, 6, 4, ColorUtils.rgba(32,35,90,128));
            DisplayUtils.drawRoundedRect(posX + 2, posY + 33, textWidth + 7, 6, 4, ColorUtils.rgba(0,0,0,156));
            Fonts.montserrat.drawText(ms, ((int) hp + (int) mc.player.getAbsorptionAmount()) + "HP", posX + 5, posY + 34, ColorUtils.getColor(0), 5f, 0.15f);
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(posX + spacing, posY + spacing + 1, 21, 21,10, style.getSecondColor().getRGB() );
            Stencil.readStencilBuffer(1);
            drawTargetHead(entity, posX + spacing, posY + spacing + 1, 21, 21);
            Stencil.uninitStencilBuffer();
            Scissor.push();
            Scissor.setFromComponentCoordinates(testX, testY, testW - 6, testH);
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), entity.getName().getString(), posX + headSize + 2, posY + 1f + spacing + 1, -1, 7.5f,0.07f);
            drawItemStack(posX + headSize + 36, posY + 16, -12);
            Scissor.unset();
            Scissor.pop();

            DisplayUtils.drawRoundedRect(posX + 21 + spacing, posY + 8 + height - spacing * 2 - 3, (width - 40), 3, 1, ColorUtils.rgb(70, 70, 70));

            DisplayUtils.drawRoundedRect(posX + 21 + spacing, posY + 8 + height - spacing * 2 - 3, (width - 40) * healthAnimation, 3, 1, ColorUtils.getColor(0));

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

    private void drawItemStack(float x, float y, float offset) {
        List<ItemStack> stacks = new ArrayList<>(Arrays.asList(entity.getHeldItemMainhand(), entity.getHeldItemOffhand()));
        entity.getArmorInventoryList().forEach(stacks::add);
        stacks.removeIf(w -> w.getItem() instanceof AirItem);
        Collections.reverse(stacks);
        final AtomicReference<Float> posX = new AtomicReference<>(x);

        stacks.stream()
                .filter(stack -> !stack.isEmpty())
                .forEach(stack -> drawItemStack(stack,
                        posX.getAndAccumulate(offset, Float::sum),
                        y,
                        true,
                        true, 0.67f));
    }

    public static void drawItemStack(ItemStack stack, float x, float y, boolean withoutOverlay, boolean scale, float scaleValue) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, 0);
        if (scale) GL11.glScaled(scaleValue, scaleValue, scaleValue);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, 35, 0);
        if (withoutOverlay) mc.getItemRenderer().renderItemOverlays(mc.fontRenderer, stack, 35, 0);
        RenderSystem.popMatrix();
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

        DisplayUtils.drawRoundedRect(x, y, width, height + 6, radius, ColorUtils.rgba(25, 26, 40, 205));
    }
}
