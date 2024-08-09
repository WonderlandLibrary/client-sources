package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.player.AttackEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.DragValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

// TODO: сделать партиклы, золотые хп, несколько модов тхуда
@ModuleInfo(name = "Target Hud", description = "Отображает информацию о цели.", category = Category.RENDER)
public class TargetInfo extends Module {
    public final DragValue drag = new DragValue("Position", this, new Vector2d(200, 200));
    public final ModeValue mode = new ModeValue("Режим", this)
            .add(SubMode.of("Обычный", "Простой"));

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    public final BooleanValue follow = new BooleanValue("Следовать", this, false);
    public final BooleanValue ftHealth = new BooleanValue("Здоровье на FT", this, true);
    public Vector2d position = new Vector2d(0, 0);
    public TimerUtil timer = TimerUtil.create();
    public boolean inWorld;
    public LivingEntity target;
    private final Animation openingAnimation = new Animation(Easing.LINEAR, 500);
    private final Animation healthAnimation = new Animation(Easing.EASE_OUT_CUBIC, 350);
    private final Animation prevHealthAnimation = new Animation(Easing.EASE_OUT_CUBIC, 750);

    public final Listener<Render2DEvent> onRender2D = event -> {
        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null && KillAura.singleton.get().getTarget() instanceof AbstractClientPlayerEntity) {
            this.target = KillAura.singleton.get().getTarget();
            this.timer.reset();
        }
        if (mc.currentScreen instanceof ChatScreen) {
            this.target = mc.player;
            this.timer.reset();
        }
        if (target == null) {
            this.inWorld = false;
            return;
        }
        this.inWorld = StreamSupport.stream(mc.world.getAllEntities().spliterator(), true).filter(entity -> entity.equals(target)).isParallel();

        setPosition(target);

        boolean out = (!this.inWorld || this.timer.hasReached(1000));
        this.openingAnimation.setEasing(out ? Easing.EASE_IN_BACK : Easing.EASE_OUT_BACK);
        this.openingAnimation.run(out ? 0 : 1);
        if (this.openingAnimation.getValue() <= 0) return;

        if (mode.is("Обычный")) {
            drawDefault(event);
        }
        if (mode.is("Простой")) {
            drawSimple(event);
        }
    };

    public final Listener<AttackEvent> onAttack = event -> {
        if (event.getTarget() instanceof LivingEntity entity) {
            this.target = entity;
            this.timer.reset();
        }
    };

    private void setPosition(Entity target) {
        if (target != null) {
            if (!follow.getValue()) {
                this.position.x = drag.position.x;
                this.position.y = drag.position.y;
                return;
            }
            if (target.equals(mc.player)) {
                this.position.x = (scaled().x / 2F) - (drag.size.x / 2F);
                this.position.y = (scaled().y / 2F) + (drag.size.y * 2F);
            } else {
                Vector3d iposition = RenderUtil.interpolate(target, mc.getRenderPartialTicks());
                double posX = iposition.x;
                double posY = iposition.y;
                double posZ = iposition.z;
                Vector2d position = RenderUtil.project2D(posX, posY + ((target.getHeight() + 0.4F) * 0.5F), posZ);
                if (position == null)
                    return;
                this.position.x = position.x - (drag.size.x / 2F);
                this.position.y = position.y;
            }
        }
    }

    public void drawDefault(Render2DEvent event) {
        MatrixStack matrix = event.getMatrix();
        if (this.target != null) {
            Font font = Fonts.INTER_SEMIBOLD.get(18);
            boolean isPlayer = this.target instanceof AbstractClientPlayerEntity;
            float stackSize = 16;

            List<ItemStack> items = new ArrayList<>();
            float health;
            if (this.target instanceof AbstractClientPlayerEntity player) {

                for (ItemStack stack : player.getArmorInventoryList()) {
                    if (!stack.isEmpty()) {
                        items.add(stack);
                    }
                }
                ItemStack offStack = player.getHeldItemOffhand();
                if (!offStack.isEmpty()) {
                    items.add(offStack);
                }
                ItemStack mainStack = player.getHeldItemMainhand();
                if (!mainStack.isEmpty()) {
                    items.add(mainStack);
                }

                Collections.reverse(items);

                health = !this.inWorld ? 0 : (float) Mathf.round(this.ftHealth.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[0] : player.getHealth(), 1);
            } else {
                health = !this.inWorld ? 0 : (float) Mathf.round(this.target.getHealth(), 1);
            }

            String name = this.target.getName().getString() + TextFormatting.GRAY + " - " + TextFormatting.RESET + (int) (health) + "hp";
            double nameWidth = font.getWidth(name);

            float x = (float) this.position.x;
            float y = (float) this.position.y;

            float margin = 5;
            float barHeight = 3;
            float height = (isPlayer) ? 48 : 28;
            float headSize = ((height - margin * 2 - barHeight) - margin);

            float width = (float) Math.max(100, Math.max(margin + headSize + margin + nameWidth + margin, margin + headSize + margin + (items.size() * stackSize) + margin));

            float barWidth = width - (margin * 2);
            float barY = y + height - margin - barHeight;
            float barX = x + margin;

            this.drag.size.set(width, height);
            float scale = (float) this.openingAnimation.getValue();
            matrix.push();
            matrix.translate((x + width / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            matrix.scale(scale, scale, 0);

            float alphaPC = 0.5F;
            float darkPC = 0.5F;
            int color1 = ColorUtil.multDark(getTheme().getClientColor(0, alphaPC), darkPC);
            int color2 = ColorUtil.multDark(getTheme().getClientColor(90, alphaPC), darkPC);
            int color3 = ColorUtil.multDark(getTheme().getClientColor(180, alphaPC), darkPC);
            int color4 = ColorUtil.multDark(getTheme().getClientColor(270, alphaPC), darkPC);

            float round = 4;
            float shadow = 4;

            drawBackground(matrix, x, y, width, height, round, shadow, color1, color2, color3, color4);

            drawHead(matrix, this.target, x + margin, y + margin, headSize);

            if (isPlayer) {
                float itemX = x + margin + headSize + margin;
                float itemY = y + margin + headSize - stackSize;

                GLUtils.scaleStart((x + width / 2), (y + height / 2), scale);

                for (ItemStack item : items) {
                    mc.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(item, itemX, itemY);
                    mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, item, itemX, itemY, item.getCount() != 1 ? String.valueOf(item.getCount()) : "");
                    itemX += stackSize;
                }
                GLUtils.scaleEnd();
            }

            font.drawShadow(matrix, name, x + margin + headSize + margin, (y + margin + (isPlayer ? 0 : (headSize / 2F) - (font.getHeight() / 2F))), ColorUtil.getColor(200));

            healthUpdate();

            int black = ColorUtil.getColor(20, 128);
            int red = ColorUtil.getColor(255, 0, 0, 255);
            int redA = ColorUtil.getColor(255, 0, 0, 16);
            int healthColor = ColorUtil.overCol(red, ColorUtil.getColor(0, 255, 0, 255), Mathf.clamp01((float) healthAnimation.getValue()));
            int healthColorA = ColorUtil.overCol(red, ColorUtil.getColor(0, 255, 0, 16), Mathf.clamp01((float) healthAnimation.getValue()));

            RectUtil.drawRoundedRectShadowed(matrix, barX, barY, barX + barWidth, barY + barHeight, 0.5F, 1, black, black, black, black, false, false, true, true);
            RectUtil.drawRoundedRectShadowed(matrix, barX, barY, (float) (barX + Mathf.clamp(Mathf.clamp01((float) healthAnimation.getValue()) * barWidth, barWidth, Mathf.clamp01((float) prevHealthAnimation.getValue()) * barWidth)), barY + barHeight, Mathf.clamp01((float) prevHealthAnimation.getValue()) * 0.5F, 1, redA, healthColorA, healthColorA, redA, false, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrix, barX, barY, barX + Mathf.clamp01((float) healthAnimation.getValue()) * barWidth, barY + barHeight, Mathf.clamp01((float) healthAnimation.getValue()) * 0.5F, 2, red, healthColor, healthColor, red, true, true, true, true);

            matrix.pop();
        } else {
            healthAnimation.setValue(1);
            prevHealthAnimation.setValue(1);
        }
    }

    public void drawSimple(Render2DEvent event) {
        MatrixStack matrix = event.getMatrix();
        if (this.target != null) {
            Font font = Fonts.INTER_SEMIBOLD.get(18);
            boolean isPlayer = this.target instanceof AbstractClientPlayerEntity;
            float stackSize = 16;

            List<ItemStack> items = new ArrayList<>();
            float health;
            if (this.target instanceof AbstractClientPlayerEntity player) {

                for (ItemStack stack : player.getArmorInventoryList()) {
                    if (!stack.isEmpty()) {
                        items.add(stack);
                    }
                }
                ItemStack offStack = player.getHeldItemOffhand();
                if (!offStack.isEmpty()) {
                    items.add(offStack);
                }
                ItemStack mainStack = player.getHeldItemMainhand();
                if (!mainStack.isEmpty()) {
                    items.add(mainStack);
                }

                Collections.reverse(items);

                health = !this.inWorld ? 0 : (float) Mathf.round(this.ftHealth.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[0] : player.getHealth(), 1);
            } else {
                health = !this.inWorld ? 0 : (float) Mathf.round(this.target.getHealth(), 1);
            }

            String name = this.target.getName().getString();
            double nameWidth = font.getWidth(name);

            float x = (float) this.position.x;
            float y = (float) this.position.y;

            float margin = 5;
            float barHeight = 5;
            float height = (isPlayer) ? 48 : 28;
            float headSize = ((height - margin * 2 - barHeight) - margin);

            float barWidth = (float) (nameWidth * 1.5);

            float barY = y + height - 30;
            float barX = (float) (x + (nameWidth - barWidth) / 2);

            this.drag.size.set(nameWidth, height);
            float scale = (float) this.openingAnimation.getValue();
            matrix.push();
            matrix.translate((x + nameWidth / 2) * (1 - scale), (y + height / 2) * (1 - scale), 0);
            matrix.scale(scale, scale, 0);

            font.drawShadow(matrix, name, x, (y + margin + (isPlayer ? 0 : (headSize / 2F) - (font.getHeight() / 2F))), ColorUtil.getColor(200));

            healthUpdate();

            int black = ColorUtil.getColor(20, 128);
            int red = ColorUtil.getColor(255, 0, 0, 255);
            int redA = ColorUtil.getColor(255, 0, 0, 16);
            int healthColor = ColorUtil.overCol(red, ColorUtil.getColor(0, 255, 0, 255), Mathf.clamp01((float) healthAnimation.getValue()));
            int healthColorA = ColorUtil.overCol(red, ColorUtil.getColor(0, 255, 0, 16), Mathf.clamp01((float) healthAnimation.getValue()));

            RectUtil.drawRoundedRectShadowed(matrix, barX - 0.5f, barY, barX + barWidth, barY + barHeight + 0.5f, 0.F, 0.3f, black, black, black, black, false, false, true, true);
            RectUtil.drawRoundedRectShadowed(matrix, barX, barY, (float) (barX + Mathf.clamp(Mathf.clamp01((float) healthAnimation.getValue()) * barWidth, barWidth, Mathf.clamp01((float) prevHealthAnimation.getValue()) * barWidth)), barY + barHeight, Mathf.clamp01((float) prevHealthAnimation.getValue()) * 0.5F, 1, redA, healthColorA, healthColorA, redA, false, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrix, barX, barY, barX + Mathf.clamp01((float) healthAnimation.getValue()) * barWidth, barY + barHeight, Mathf.clamp01((float) healthAnimation.getValue()) * 0.5F, 0, red, healthColor, healthColor, red, true, true, true, true);

            matrix.pop();
        } else {
            healthAnimation.setValue(1);
            prevHealthAnimation.setValue(1);
        }
    }


    private void drawHead(MatrixStack matrix, final Entity entity, final double x, final double y, final float size) {
        if (entity instanceof AbstractClientPlayerEntity player) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderSystem.alphaFunc(GL11.GL_GREATER, 0.0F);
            RenderSystem.enableTexture();
            mc.getTextureManager().bindTexture(player.getLocationSkin());
            RenderSystem.color4f(1F, 1F, 1F, 1F);
            AbstractGui.blit(matrix, (float) x, (float) y, size, size, 4F, 4F, 4F, 4F, 32F, 32F);
            GLUtils.scaleStart((float) (x + size / 2F), (float) (y + size / 2F), 1.1F);
            AbstractGui.blit(matrix, (float) x, (float) y, size, size, 20, 4, 4, 4, 32, 32);
            GLUtils.scaleEnd();
            RenderSystem.disableBlend();
        } else {
            Font font = Fonts.INTER_BOLD.get(size * 2);
            int color = ColorUtil.getColor(20, 128);
            RectUtil.drawRoundedRectShadowed(matrix, (float) x, (float) y, (float) (x + size), (float) (y + size), 2F, 1, color, color, color, color, false, false, true, true);
            font.drawCenter(matrix, "?", x + (size / 2F), y + (size / 2F) - (font.getHeight() / 2F), -1);
        }
    }

    private void healthUpdate() {
        float health;
        float maxHealth;
        if (this.target instanceof AbstractClientPlayerEntity player) {
            health = !this.inWorld ? 0 : (float) Mathf.round(this.ftHealth.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[0] : player.getHealth(), 1);
            maxHealth = this.ftHealth.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[1] : player.getMaxHealth();
        } else {
            health = !this.inWorld ? 0 : (float) Mathf.round(this.target.getHealth(), 1);
            maxHealth = this.target.getMaxHealth();
        }

        float healthBar = (health / maxHealth);
        this.healthAnimation.run(Mathf.clamp01(healthBar));
        this.prevHealthAnimation.run(Mathf.clamp01(healthBar));
    }

    private void drawBackground(MatrixStack matrix, float x, float y, float width, float height, float round, float shadow, int color1, int color2, int color3, int color4) {
        boolean bloom = true;
        RectUtil.drawRoundedRectShadowed(matrix, x, y, x + width, y + height, round, shadow, color1, color2, color3, color4, bloom, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrix, x + 0.5F, y + 0.5F, x + width - 0.5F, y + height - 0.5F, round - 1, 1F, color1, color2, color3, color4, false, false, true, true);

        int zeroC1 = ColorUtil.multAlpha(color1, 0F),
                zeroC2 = ColorUtil.multAlpha(color2, 0F),
                zeroC3 = ColorUtil.multAlpha(color3, 0F),
                zeroC4 = ColorUtil.multAlpha(color4, 0F);
        int overY1C = ColorUtil.overCol(color1, color2),
                overY2C = ColorUtil.overCol(color4, color3),
                overX1C = ColorUtil.overCol(color1, color4),
                overX2C = ColorUtil.overCol(color2, color3);

        List<RectUtil.Vec2fColored> list = new ArrayList<>();
        float lineOff = 0;
        GL12.glLineWidth(2);
        list.add(new RectUtil.Vec2fColored(x + round, y + lineOff, zeroC1));
        list.add(new RectUtil.Vec2fColored(x + width / 2F, y + lineOff, overY1C));
        list.add(new RectUtil.Vec2fColored(x + width - round, y + lineOff, zeroC2));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + round, y + height - lineOff, zeroC4));
        list.add(new RectUtil.Vec2fColored(x + width / 2F, y + height - lineOff, overY2C));
        list.add(new RectUtil.Vec2fColored(x + width - round, y + height - lineOff, zeroC3));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + lineOff, y + round, zeroC1));
        list.add(new RectUtil.Vec2fColored(x + lineOff, y + height / 2F, overX1C));
        list.add(new RectUtil.Vec2fColored(x + lineOff, y + height - round, zeroC4));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        list.clear();
        list.add(new RectUtil.Vec2fColored(x + width - lineOff, y + round, zeroC2));
        list.add(new RectUtil.Vec2fColored(x + width - lineOff, y + height / 2F, overX2C));
        list.add(new RectUtil.Vec2fColored(x + width - lineOff, y + height - round, zeroC3));
        RectUtil.drawVertexesList(matrix, list, GL_LINE_STRIP, false, bloom);
        GL12.glLineWidth(1);
    }
}
