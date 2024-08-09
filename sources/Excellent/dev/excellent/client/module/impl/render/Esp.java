package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.render.RenderNameEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.AntiBot;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.text.TextUtils;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.*;

@ModuleInfo(name = "Esp", description = "Отображает ближайших сущностей.", category = Category.RENDER)
public class Esp extends Module {
    private final MultiBooleanValue checks = new MultiBooleanValue("Элементы", this)
            .add(
                    new BooleanValue("Эффекты зелий", true),
                    new BooleanValue("Зачарования", false),
                    new BooleanValue("Квадраты", true),
                    new BooleanValue("Здоровье", true),
                    new BooleanValue("Броня", true),
                    new BooleanValue("Имя", true),
                    new BooleanValue("Предметы", false)
            );
    private final ModeValue boxColorMode = new ModeValue("Цвет квадратов", this, () -> !checks.isEnabled(
            "Квадраты"
    )).add(
            new SubMode("Клиентская"),
            new SubMode("Белая")
    );
    public final BooleanValue funTimeHP = new BooleanValue("Здоровье на FT", this, true);

    private final Font font = Fonts.INTER_BOLD.get(14);
    private final Listener<RenderNameEvent> renderNameEvent = event -> {
        if (event.getEntity() instanceof PlayerEntity && checks.isEnabled("Имя")) event.cancel();
    };
    public final Set<Entity> collectedEntities = new HashSet<>();

    private final Listener<Render2DEvent> onRender2D = event -> {
        this.collectEntities();

        MatrixStack matrix = event.getMatrix();

        for (Entity entity : collectedEntities) {
            org.joml.Vector3d iVec = RenderUtil.interpolate(entity, mc.getRenderPartialTicks());
            double posX = iVec.x;
            double posY = iVec.y;
            double posZ = iVec.z;

            double nametagWidth = entity.getWidth() / 1.5, nametagHeight = entity.getHeight() + 0.1f - (entity.isSneaking() ? 0.2f : 0.0f);

            AxisAlignedBB aabb = new AxisAlignedBB(posX - nametagWidth, posY, posZ - nametagWidth, posX + nametagWidth, posY + nametagHeight, posZ + nametagWidth);

            Vector2d min = null;
            Vector2d max = null;

            for (Vector3d vector : getVectors(aabb)) {
                Vector2d vec = RenderUtil.project2D(vector.x, vector.y, vector.z);

                if (vec != null) {
                    if (min == null) {
                        min = new Vector2d(vec.x, vec.y);
                        max = new Vector2d(vec.x, vec.y);
                    } else {
                        min.x = Math.min(min.x, vec.x);
                        min.y = Math.min(min.y, vec.y);
                        max.x = Math.max(max.x, vec.x);
                        max.y = Math.max(max.y, vec.y);
                    }
                }
            }

            //noinspection ConstantValue
            if (max != null && min != null) {
                float minX = (float) min.x;
                float minY = (float) min.y;
                float maxX = (float) max.x;
                float maxY = (float) max.y;

                int color1 = getTheme().getClientColor(0);
                int color2 = getTheme().getClientColor(90);
                int color3 = getTheme().getClientColor(180);
                int color4 = getTheme().getClientColor(270);
                int black = ColorUtil.getColor(0, 0, 0, 128);

                if (entity instanceof PlayerEntity player) {
                    if (checks.isEnabled("Квадраты")) {
                        drawBox(maxX, minX, maxY, minY, matrix, color1, color2, color3, color4);
                    }

                    if (checks.isEnabled("Имя")) {
                        String title = player.getDisplayName().getString();

                        if (title.isEmpty()) return;
                        float nameX = minX + ((maxX - minX) / 2F);
                        float nameY = minY - font.getHeight() * 1.5F;
                        float nameWidth = RenderUtil.getComponentWidth(player.getDisplayName(), font) + 2;
                        String healthText = "§7- %dHP".formatted((int) (funTimeHP.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[0] : player.getHealth()));
                        float healthWidth = font.getWidth(healthText) + 2;
                        float x = nameX - (nameWidth / 2F) - (healthWidth / 2F);
                        float y = nameY + 0;

                        nametagWidth = (nameWidth + healthWidth);
                        nametagHeight = font.getHeight() - 0.5F;
                        RenderUtil.renderClientRect(matrix, x, y, (float) (nametagWidth), (float) (nametagHeight), false, 0);
                        nameX = nameX + 1;
                        RenderUtil.drawITextComponent(event.getMatrix(), player.getDisplayName(), font, nameX - (nameWidth / 2F) - (healthWidth / 2F), nameY, -1, true);
                        font.drawShadow(event.getMatrix(), healthText, nameX + (nameWidth / 2F) - (healthWidth / 2F), nameY, -1);
                    }

                    if (checks.isEnabled("Здоровье")) {
                        float healthWidth = 0.5F;
                        int green = ColorUtil.getColor(0, 255, 0);
                        int red = ColorUtil.getColor(255, 0, 0);

                        float x = maxX + 3F + healthWidth;

                        float health = funTimeHP.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[0] : player.getHealth();
                        float healthHeight = health / Math.max(health, funTimeHP.getValue() ? PlayerUtil.getHealthFromScoreboard(player)[1] : (player.getMaxHealth())) * (maxY - minY);

                        float offset = 0.5F;

                        RectUtil.drawGradientV(matrix, x - healthWidth - offset, minY - offset, x + healthWidth + offset, maxY + offset, black, black, false);
                        RectUtil.drawGradientV(matrix, x - healthWidth, minY + ((maxY - minY) - healthHeight), x + healthWidth, maxY, green, red, false);
                    }
                    if (checks.isEnabled("Эффекты зелий")) {
                        renderEffects(matrix, player, maxX - ((maxX - minX) / 2F), maxY + 5);
                    }

                    if (checks.isEnabled("Броня")) {

                        Font font = Fonts.INTER_BOLD.get(10);

                        List<ItemStack> items = new ArrayList<>();

                        ItemStack mainStack = player.getHeldItemMainhand();

                        if (!mainStack.isEmpty()) {
                            items.add(mainStack);
                        }

                        for (ItemStack itemStack : entity.getArmorInventoryList()) {
                            if (itemStack.isEmpty()) continue;
                            items.add(itemStack);
                        }

                        ItemStack offStack = player.getHeldItemOffhand();

                        if (!offStack.isEmpty()) {
                            items.add(offStack);
                        }

                        float x = minX + ((maxX - minX) / 2F) + (-items.size() * 8);

                        float nameTagY = minY - this.font.getHeight() * 2;
                        nametagHeight = this.font.getHeight() - 0.5F;

                        float y = (float) (nameTagY - (nametagHeight + 5));

                        float stackSize = 16;
                        for (ItemStack item : items) {
                            if (item.isEmpty()) continue;
                            GLUtils.scaleStart(x + (stackSize / 2F), y + (stackSize / 2F), 0.75F);
                            drawItemStack(item, x, y, true, item.getCount() != 1 ? String.valueOf(item.getCount()) : "");
                            GLUtils.scaleEnd();
                            float enchWidth = (float) EnchantmentHelper.getEnchantments(item).entrySet().stream()
                                    .mapToDouble(enchant -> font.getWidth(getShortEnchantment(enchant)))
                                    .max()
                                    .orElse(0);
                            if (checks.isEnabled("Зачарования")) {
                                float yOffset = 0;
                                for (Map.Entry<Enchantment, Integer> enchant : EnchantmentHelper.getEnchantments(item).entrySet()) {
                                    if (!getShortEnchantment(enchant).isEmpty()) {
                                        font.drawCenter(event.getMatrix(), getShortEnchantment(enchant), x + (stackSize / 2F), y - (stackSize / 2F) + yOffset, -1);
                                        yOffset -= font.getHeight();
                                    }
                                }
                            }
                            x += Math.max(enchWidth, stackSize);
                        }
                    }
                } else if (entity instanceof ItemEntity item) {
                    if (checks.isEnabled("Предметы")) {
                        String title = (item.getDisplayName().getString() + " " + item.getItem().getCount() + "x").toLowerCase();
                        title = TextUtils.removeForbiddenCharacters(title, TextUtils.ALLOWED_TO_SESSION + " ");
                        if (title.isEmpty()) return;
                        float nameX = (minX + ((maxX - minX) / 2F));
                        float nameY = (minY - font.getHeight() * 1.5F);
                        float nameWidth = (font.getWidth(title) + 4);
                        nametagHeight = font.getHeight();
                        RectUtil.drawRect(matrix, nameX - nameWidth / 2F, nameY, nameX + nameWidth / 2F, (float) (nameY + nametagHeight - 0.5F), black);
                        font.drawCenterShadow(event.getMatrix(), title, nameX + 0.5F, nameY, ColorUtil.getColor(200, 200, 200));
                    }
                }
            }
        }

    };

    private void drawBox(float maxX, float minX, float maxY, float minY, MatrixStack matrix, int color1, int color2, int color3, int color4) {
        if (boxColorMode.is("Белая")) {
            color1 = color2 = color3 = color4 = -1;
        }
        float boxWidth = (maxX - minX);
        float boxHeight = (maxY - minY);
        float lineWidth = 1;

        float sect = Math.min(boxWidth / 4, boxHeight / 4);

        // top left
        RectUtil.drawRect(matrix, minX, minY, minX + sect, minY + lineWidth, color1, color1, color1, color1, false, false);
        RectUtil.drawRect(matrix, minX, minY, minX + lineWidth, minY + sect, color1, color1, color1, color1, false, false);

        // top right
        RectUtil.drawRect(matrix, maxX, minY, maxX - sect, minY + lineWidth, color2, color2, color2, color2, false, false);
        RectUtil.drawRect(matrix, maxX, minY, maxX + lineWidth, minY + sect, color2, color2, color2, color2, false, false);

        // bottom right
        RectUtil.drawRect(matrix, maxX, minY + boxHeight - lineWidth, maxX - sect, minY + boxHeight, color3, color3, color3, color3, false, false);
        RectUtil.drawRect(matrix, maxX, maxY, maxX + lineWidth, maxY - sect, color3, color3, color3, color3, false, false);

        // bottom left
        RectUtil.drawRect(matrix, minX, minY + boxHeight - lineWidth, minX + sect, minY + boxHeight, color4, color4, color4, color4, false, false);
        RectUtil.drawRect(matrix, minX, maxY, minX + lineWidth, maxY - sect, color4, color4, color4, color4, false, false);
    }

    private void renderEffects(MatrixStack matrix, PlayerEntity player, float x, float y) {
        EffectInstance[] effects = player.getActivePotionEffects().toArray(new EffectInstance[0]);
        for (int index = 0; index < effects.length; index++) {
            EffectInstance effect = effects[index];
            if (effect == null) continue;
            String name = I18n.format(effect.getEffectName());
            String amplifier = I18n.format("enchantment.level." + (effect.getAmplifier() + 1)).replaceAll("enchantment.level.0", "");
            String duration = EffectUtils.getPotionDurationString(effect, 1);

            String effectText = (name + " " + amplifier + TextFormatting.RED + " (" + duration + ")" + TextFormatting.RESET).toLowerCase().replace("**:**", "беск");

            Font font = Fonts.INTER_BOLD.get(10);

            font.drawCenterShadow(matrix, effectText, x, y + (index * font.getHeight()), -1);
        }
    }

    public static void drawItemStack(ItemStack stack, double x, double y, boolean withOverlay, String text) {
        RenderSystem.translated(x, y, 0);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, 0, 0);
        if (withOverlay) mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, stack, 0, 0, text);
        RenderSystem.translated(-x, -y, 0);
    }

    private String getShortEnchantment(Map.Entry<Enchantment, Integer> nbt) {
        if (nbt.getValue() < 0) return "";
        String output = nbt.getKey().getDisplayName(0).getString().substring(0, 2);

        output += " ";

        if (nbt.getValue() != 1 || nbt.getKey().getMaxLevel() != 1) {
            if (nbt.getValue() == Short.MAX_VALUE) {
                output += "∞";
            } else if (nbt.getValue() > 10) {
                output += nbt.getValue().toString();
            } else {
                output += new TranslationTextComponent("enchantment.level." + nbt.getValue()).getString();
            }
        }

        return output.replaceAll("enchantment.level.", "");
    }

    private Vector3d[] getVectors(AxisAlignedBB boundingBox) {
        return new Vector3d[]{new Vector3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ),
                new Vector3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ),
                new Vector3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ),
                new Vector3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ),
                new Vector3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ),
                new Vector3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ),
                new Vector3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ),
                new Vector3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ)};
    }

    private void collectEntities() {
        this.collectedEntities.clear();
        final Iterable<Entity> entities = mc.world.getAllEntities();
        for (final Entity entity : entities) {
            if (!this.isValid(entity)) {
                continue;
            }
            this.collectedEntities.add(entity);

        }
    }

    private boolean isValid(final Entity entity) {
        if (entity instanceof ItemEntity) return true;

        if (entity == mc.player && mc.gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON)) {
            return false;
        }
        if (!entity.isAlive()) {
            return false;
        }
        if (entity instanceof PlayerEntity wrapper) {
            if (AntiBot.contains(wrapper)) return false;
        }
        return entity instanceof PlayerEntity;
    }
}
