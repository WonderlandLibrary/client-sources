package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
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
import fun.ellant.utils.render.font.Font;
import fun.ellant.utils.render.font.Fonts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

public class AncientTargetHUD implements ElementRenderer {
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
            float headSize = 28.0F;
            float spacing = 5.0F;
            float width = 114.666664F;
            float height = 39.333332F;
            this.drag.setWidth(width);
            this.drag.setHeight(height);
            float shrinking = 1.5F;
            Minecraft var10000 = mc;
            Scoreboard var25 = Minecraft.world.getScoreboard();
            String var10001 = this.entity.getScoreboardName();
            Minecraft var10002 = mc;
            Score score = var25.getOrCreateScore(var10001, Minecraft.world.getScoreboard().getObjectiveInDisplaySlot(2));
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
            Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
            sizeAnimation((double)(posX + width / 2.0F), (double)(posY + height / 2.0F), this.animation.getOutput());
            this.drawStyledRect(posX, posY, width + 15.0F, height, rounding, 140);
            this.drawTargetHead(this.entity, posX + spacing, posY + spacing + 1.0F, headSize, headSize);
            Scissor.push();
            //Scissor.setFromComponentCoordinates((double)testX, (double)testY, (double)(testW - 6.0F + 20.0F), (double)testH);
            Fonts.sfui.drawText(eventDisplay.getMatrixStack(), this.entity.getName().getString(), posX + headSize + spacing + spacing, posY + spacing + 1.0F, -1, 8.0F);
            Font var26 = Fonts.sfMedium;
            MatrixStack var27 = eventDisplay.getMatrixStack();
            int var28 = (int)hp;
            Minecraft var10003 = mc;
            var26.drawCenteredText(var27, "" + (var28 + (int)Minecraft.player.getAbsorptionAmount()), posX + headSize + spacing + spacing + 103.0F, posY + spacing + 1.0F + spacing + spacing - 0.5F, ColorUtils.rgb(200, 200, 200), 9.0F);
            this.drawItemStack(posX + headSize + spacing + spacing, posY + spacing + 1.0F + spacing + spacing, 9.6F, this.entity);
            DisplayUtils.drawCircle(posX + width + 27, posY + height / 2.0F + 4.0F - 4, 0.0F, 360, 15f, 7, false, ColorUtils.rgba(21, 21, 21, 140));
            hp = (float)MathUtil.clamp((double)MathUtil.lerp(hp, this.entity.getHealth() / this.entity.getMaxHealth(), (float)(12.0 * MathUtil.deltaTime())), 0.0, 1.0);
            DisplayUtils.drawCircle(posX + width + 27, posY + height / 2.0F + 4.0F - 4, 0.0F, this.healthAnimation * 360 + 1, 15f, 7, false, ColorUtils.getColor((int)(hp * 360.0F)));
            DisplayUtils.drawShadow(posX - 3, posY, 3, 39f, 7, ColorUtils.getColor(0));
            DisplayUtils.drawRoundedRect(posX - 2, posY, 2.5f, 39, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F)));
            Scissor.unset();
            Scissor.pop();
            new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB());
            GlStateManager.popMatrix();
        }

    }

    public void renderblack(EventDisplay eventDisplay) {
    }

    public void render3(EventDisplay eventDisplay) {
    }

    private LivingEntity getTarget(LivingEntity nullTarget) {
        LivingEntity auraTarget = Ellant.getInstance().getFunctionRegistry().getKillAura().getTarget();
        LivingEntity target = nullTarget;
        if (auraTarget != null) {
            this.stopWatch.reset();
            this.allow = true;
            target = auraTarget;
        } else if (mc.currentScreen instanceof ChatScreen) {
            this.stopWatch.reset();
            this.allow = true;
            Minecraft var10000 = mc;
            target = Minecraft.player;
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

    private void drawItemStack(float x, float y, float offset, LivingEntity entity) {
        ArrayList<ItemStack> stackList = new ArrayList(Arrays.asList(this.getTarget(entity).getHeldItemMainhand(), this.getTarget(entity).getHeldItemOffhand()));
        ArrayList<ItemStack> stackList1 = new ArrayList((Collection)this.getTarget(entity).getArmorInventoryList());
        AtomicReference<Float> posX = new AtomicReference(x);
        AtomicReference<Float> posXArmor = new AtomicReference(x);
        stackList.stream().filter((stack) -> {
            return !stack.isEmpty();
        }).forEach((stack) -> {
            DisplayUtils.drawItemStack(stack, (Float)posX.getAndAccumulate(offset + 5.0F, Float::sum) - 2.0F, y + 4.0F, true, true, 0.74F);
        });
        stackList1.stream().filter((stack) -> {
            return !stack.isEmpty();
        }).forEach((stack) -> {
            DisplayUtils.drawItemStack(stack, (Float)posXArmor.getAndAccumulate(offset + 5.0F, Float::sum) + 28.0F, y + 4.0F, true, true, 0.82F);
        });
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
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        //DisplayUtils.drawRoundedRect(x - 0.5F, y - 0.5F, width + 50.0F, height + 1.0F, 0, ColorUtils.setAlpha(ColorUtils.getColor(0), alpha));
        DisplayUtils.drawRoundedRect(x, y, width + 34, height, 0, ColorUtils.rgba(0, 0, 0, 255));
    }

    public AncientTargetHUD(Dragging drag) {
        this.drag = drag;
    }
}
