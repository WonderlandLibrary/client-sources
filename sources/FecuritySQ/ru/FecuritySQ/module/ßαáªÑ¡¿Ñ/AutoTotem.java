package ru.FecuritySQ.module.сражение;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionNumric;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AutoTotem extends Module {

    public OptionNumric health = new OptionNumric("Жизни", 3.5f, 1.f, 20.f, 0.05f);
    OptionBoolean noBallSwitch = new OptionBoolean("Не менять шар", false);

    public OptionBoolList mode = new OptionBoolList("Настройка",
            new OptionBoolean("Доп сердца", true),
            new OptionBoolean("Кристал", true),
            new OptionBoolean("Якорь", true),
            new OptionBoolean("Обсидиан рядом", true),
            new OptionBoolean("Падение", true));
    OptionNumric radiusExplosion = new OptionNumric("Дистанция до Кристала", 6, 1, 8, 1);
    OptionNumric AnchorExplosion = new OptionNumric("Дистанция до Якоря", 6, 1, 8, 1);
    OptionNumric radiusObs = new OptionNumric("Дистация до Обсидиана", 6, 1, 8, 1);
    OptionBoolean counter = new OptionBoolean("На экране", true);
    public int swapBack = -1;
    public long delay = 0;
    public AutoTotem() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(mode);
        addOption(health);
        addOption(radiusExplosion);
        addOption(AnchorExplosion);
        addOption(radiusObs);
        addOption(noBallSwitch);
        addOption(counter);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;

        if(e instanceof EventUpdate){

            int slot = getSlotIDFromItem(Items.TOTEM_OF_UNDYING);

            boolean totemInHand = mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING;
            boolean handNotNull = !(mc.player.getHeldItemOffhand().getItem() instanceof AirItem);

            if (System.currentTimeMillis() < delay) {
                return;
            }

            if (condition()) {
                if (slot >= 0) {
                    if (!totemInHand) {
                        mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                        if (handNotNull) {
                            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                            if (swapBack == -1) swapBack = slot;
                        }
                        delay = System.currentTimeMillis() + 300;

                    }
                }

            } else if (swapBack >= 0) {
                mc.playerController.windowClick(0, swapBack, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                if (handNotNull) mc.playerController.windowClick(0, swapBack, 0, ClickType.PICKUP, mc.player);
                swapBack = -1;
                delay = System.currentTimeMillis() + 300;
            }

        }


        if(e instanceof EventHud) {
            if (counter.get()) {
                if (fountTotemCount() > 0) {
                    Fonts.mntsb16.drawCenteredString(new MatrixStack(), fountTotemCount() + "", mc.getMainWindow().getScaledWidth() / 2,
                            mc.getMainWindow().getScaledHeight() / 2 + 40, new Color(255, 255, 255).getRGB());
                    for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
                        ItemStack stack = mc.player.inventory.getStackInSlot(i);
                        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                            GlStateManager.pushMatrix();
                            GlStateManager.disableBlend();
                            mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, mc.getMainWindow().getScaledWidth() / 2 - 8, mc.getMainWindow().getScaledHeight() / 2 + 20);
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }
        }
    }

    private boolean condition() {

        float hp = mc.player.getHealth();
        if (mode.get(0)) {
            hp += mc.player.getAbsorptionAmount();
        }

        if (health.get() >= hp) {
            return true;
        }

        if (!isBall()) {

            for (Entity entity : mc.world.getAllEntities()) {
                if (mode.get(1)) {
                    if (entity instanceof EnderCrystalEntity && mc.player.getDistanceSq(entity) <= radiusExplosion.get()) {
                        return true;
                    }
                }
            }

            if (mode.get(2)) {
                BlockPos pos = getSphere(getPlayerPosLocal(), radiusObs.get(), 6, false, true, 0).stream().filter(this::IsValidBlockPosAnchor).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);
                return pos != null;
            }

            if (mode.get(3)) {
                BlockPos pos = getSphere(getPlayerPosLocal(), AnchorExplosion.get(), 6, false, true, 0).stream().filter(this::IsValidBlockPosObisdian).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);
                return pos != null;
            }
            if (mode.get(4)) {
                return mc.player.fallDistance >= 30;
            }
        }

        return false;
    }

    private int fountTotemCount() {
        int count = 0;
        for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                count++;
            }
        }
        return count;
    }

    public boolean isBall() {
        if (!noBallSwitch.get()) {
            return false;
        }
        ItemStack stack = mc.player.getHeldItemOffhand();
        return stack.getDisplayName().getString().toLowerCase().contains("шар") || stack.getDisplayName().getString().toLowerCase().contains("голова");
    }

    public static List<BlockPos> getSphere(final BlockPos blockPos, final float n, final int n2, final boolean b, final boolean b2, final int n3) {
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (int n4 = x - (int) n; n4 <= x + n; ++n4) {
            for (int n5 = z - (int) n; n5 <= z + n; ++n5) {
                for (int n6 = b2 ? (y - (int) n) : y; n6 < (b2 ? (y + n) : ((float) (y + n2))); ++n6) {
                    final double n7 = (x - n4) * (x - n4) + (z - n5) * (z - n5) + (b2 ? ((y - n6) * (y - n6)) : 0);
                    if (n7 < n * n && (!b || n7 >= (n - 1.0f) * (n - 1.0f))) {
                        list.add(new BlockPos(n4, n6 + n3, n5));
                    }
                }
            }
        }
        return list;
    }

    public BlockPos getPlayerPosLocal() {
        if (mc.player == null) {
            return BlockPos.ZERO;
        }
        return new BlockPos(Math.floor(mc.player.getPosX()), Math.floor(mc.player.getPosY()), Math.floor(mc.player.getPosZ()));
    }

    private boolean IsValidBlockPosObisdian(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        return state.getBlock() == Blocks.OBSIDIAN;
    }
    private boolean IsValidBlockPosAnchor(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        return state.getBlock() == Blocks.RESPAWN_ANCHOR;
    }

    public static double getDistanceOfEntityToBlock(Entity entity, final BlockPos blockPos) {
        return getDistance(entity.getPosX(), entity.getPosY(), entity.getPosZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static int getSlotIDFromItem(Item item) {
        int slot = -1;
        for (int i = 0; i < 36; i++) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == item) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot = slot + 36;
        }
        return slot;
    }

    public static double getDistance(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n - n4;
        final double n8 = n2 - n5;
        final double n9 = n3 - n6;
        return MathHelper.sqrt(n7 * n7 + n8 * n8 + n9 * n9);
    }

}
