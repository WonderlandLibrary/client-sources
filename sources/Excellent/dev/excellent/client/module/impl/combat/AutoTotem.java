package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.mojang.blaze3d.platform.GlStateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(name = "Auto Totem", description = "Автоматически активирует тотем бессмертия при низком уровне здоровья.", category = Category.COMBAT)
public class AutoTotem extends Module {
    private final NumberValue health = new NumberValue("Здоровье", this, 4F, 1F, 20F, 1F);
    private final BooleanValue drawCounter = new BooleanValue("Отображать кол-во", this, true);
    private final BooleanValue swapBack = new BooleanValue("Возвращать", this, true);

    private final BooleanValue noBallSwitch = new BooleanValue("Не брать при шаре", this, false);

    private final MultiBooleanValue mode = new MultiBooleanValue("Условие", this)
            .add(
                    new BooleanValue("Поглощение", true),
                    new BooleanValue("Обсидиан", false),
                    new BooleanValue("Кристалл", true),
                    new BooleanValue("Якорь", true),
                    new BooleanValue("Падение", true)
            );

    private final NumberValue obsidianRadius = new NumberValue("Радиус от обсы", this, 6, 1, 8, 1, () -> !mode.isEnabled("Обсидиан"));
    private final NumberValue crystalRadius = new NumberValue("Радиус от кристалла", this, 6, 1, 8, 1, () -> !mode.isEnabled("Кристалл"));
    private final NumberValue anchorRadius = new NumberValue("Радиус от якоря", this, 6, 1, 8, 1, () -> !mode.isEnabled("Якорь"));

    private int swapBackSlot = -1;
    private final ItemStack stack = new ItemStack(Items.TOTEM_OF_UNDYING);
    private final Listener<UpdateEvent> onUpdate = event -> {
        int slot = PlayerUtil.findItemSlot(Items.TOTEM_OF_UNDYING);
        boolean totemInHand = mc.player.getHeldItemOffhand().getItem().equals(Items.TOTEM_OF_UNDYING);
        boolean handNotNull = !(mc.player.getHeldItemOffhand().getItem() instanceof AirItem);

        if (condition()) {
            if (slot >= 0) {
                if (!totemInHand) {
                    mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    if (handNotNull && swapBack.getValue()) {
                        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                        if (swapBackSlot == -1) swapBackSlot = slot;
                    }
                }
            }

        } else if (swapBackSlot >= 0) {
            mc.playerController.windowClick(0, swapBackSlot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            if (handNotNull && swapBack.getValue()) {
                mc.playerController.windowClick(0, swapBackSlot, 0, ClickType.PICKUP, mc.player);
            }
            swapBackSlot = -1;
        }
    };
    private final Listener<Render2DEvent> onRender2D = event -> {
        if (!drawCounter.getValue())
            return;

        if (getTotemCount() > 0) {
            Fonts.INTER_EXTRABOLD.get(12).drawOutline(event.getMatrix(), getTotemCount() + "x", mc.getMainWindow().getScaledWidth() / 2f + 12F,
                    mc.getMainWindow().getScaledHeight() / 2f + 24, Color.WHITE.hashCode());
            GlStateManager.pushMatrix();
            GlStateManager.disableBlend();
            mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, mc.getMainWindow().getScaledWidth() / 2F - 8, mc.getMainWindow().getScaledHeight() / 2F + 20);
            GlStateManager.popMatrix();
        }
    };

    private boolean condition() {
        float health = mc.player.getHealth();
        if (mode.isEnabled("Поглощение")) {
            health += mc.player.getAbsorptionAmount();
        }

        if (this.health.getValue().floatValue() >= health) {
            return true;
        }

        if (!isBall()) {
            for (Entity entity : mc.world.getAllEntities()) {
                if (mode.isEnabled("Кристалл")) {
                    if (entity instanceof EnderCrystalEntity && mc.player.getDistanceSq(entity) <= crystalRadius.getValue().floatValue()) {
                        return true;
                    }
                }
            }

            if (mode.isEnabled("Якорь")) {
                BlockPos pos = getSphere(mc.player.getPosition(), obsidianRadius.getValue().floatValue(), 6, false, true, 0).stream().filter(this::IsValidBlockPosAnchor).min(Comparator.comparing(blockPos -> getDistanceToBlock(mc.player, blockPos))).orElse(null);
                return pos != null;
            }

            if (mode.isEnabled("Обсидиан")) {
                BlockPos pos = getSphere(mc.player.getPosition(), anchorRadius.getValue().floatValue(), 6, false, true, 0).stream().filter(this::IsValidBlockPosObisdian).min(Comparator.comparing(blockPos -> getDistanceToBlock(mc.player, blockPos))).orElse(null);
                return pos != null;
            }
            if (mode.isEnabled("Падение")) {
                return mc.player.fallDistance >= 30;
            }
        }

        return false;
    }

    public boolean isBall() {
        if (!noBallSwitch.getValue()) {
            return false;
        }
        ItemStack stack = mc.player.getHeldItemOffhand();
        return stack.getDisplayName().getString().toLowerCase().contains("шар") || stack.getDisplayName().getString().toLowerCase().contains("голова") || stack.getDisplayName().getString().toLowerCase().contains("head");
    }

    private int getTotemCount() {
        int count = 0;
        for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                count++;
            }
        }
        return count;
    }

    private boolean IsValidBlockPosObisdian(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        return state.getBlock().equals(Blocks.OBSIDIAN);
    }

    private boolean IsValidBlockPosAnchor(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        return state.getBlock().equals(Blocks.RESPAWN_ANCHOR);
    }

    private List<BlockPos> getSphere(final BlockPos blockPos, final float radius, final int height, final boolean hollow, final boolean semiHollow, final int yOffset) {
        final ArrayList<BlockPos> spherePositions = new ArrayList<>();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final int minX = x - (int) radius;
        final int maxX = x + (int) radius;
        final int minZ = z - (int) radius;
        final int maxZ = z + (int) radius;

        for (int xPos = minX; xPos <= maxX; ++xPos) {
            for (int zPos = minZ; zPos <= maxZ; ++zPos) {
                final int minY = semiHollow ? (y - (int) radius) : y;
                final int maxY = semiHollow ? (y + (int) radius) : (y + height);
                for (int yPos = minY; yPos < maxY; ++yPos) {
                    final double distance = (x - xPos) * (x - xPos) + (z - zPos) * (z - zPos) + (semiHollow ? ((y - yPos) * (y - yPos)) : 0);
                    if (distance < radius * radius && (!hollow || distance >= (radius - 1.0f) * (radius - 1.0f))) {
                        spherePositions.add(new BlockPos(xPos, yPos + yOffset, zPos));
                    }
                }
            }
        }
        return spherePositions;
    }

    private double getDistanceToBlock(Entity entity, final BlockPos blockPos) {
        return getDistance(entity.getPosX(), entity.getPosY(), entity.getPosZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double x = x1 - x2;
        final double y = y1 - y2;
        final double z = z1 - z2;
        return MathHelper.sqrt(x * x + y * y + z * z);
    }

}