package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.other.PotionUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.KeyValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.impl.StringValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@ModuleInfo(name = "Auto Fix", description = "Автоматически чинит предмет", category = Category.MISC)
public class AutoFix extends Module {
    private final ModeValue mode = new ModeValue("Чинить", this)
            .add(SubMode.of("Командой", "Пузырьками"));

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private final StringValue fixCommand = new StringValue("Команда починки", this, "/fix", () -> !mode.is("Командой"));
    private final KeyValue key = new KeyValue("Кнопка починки", this, -1, () -> !mode.is("Пузырьками"));
    private final NumberValue delay = new NumberValue("Задержка выбрасывания", this, 50, 0, 500, 10, () -> !mode.is("Пузырьками"));
    private final TimerUtil timerUtil = TimerUtil.create();
    private final TimerUtil throwDelay = TimerUtil.create();
    private float previousPitch;
    private int selectedSlot;
    private final PotionUtil potionUtil = new PotionUtil();
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mode.is("Командой")) {
            if (timerUtil.hasReached(1000) && checkFix(mc.player.getHeldItemMainhand())) {
                ChatUtil.sendText(fixCommand.getValue());
                timerUtil.reset();
            }
        }
        if (mode.is("Пузырьками")) {
            if (mc.currentScreen != null) return;
            if (isNotPressed()) return;
            if (!checkFixInv().equals(ItemStack.EMPTY)) {
                if (!this.isNotThrowing() && previousPitch == mc.player.lastReportedPitch) {
                    if (throwDelay.hasReached(delay.getValue().doubleValue())) {
                        int oldItem = mc.player.inventory.currentItem;
                        this.selectedSlot = -1;

                        int slot = this.setSlotAndUseItem();
                        if (this.selectedSlot == -1) {
                            this.selectedSlot = slot;
                        }

                        if (this.selectedSlot > 8) {
                            mc.playerController.pickItem(this.selectedSlot);
                        }

                        mc.player.connection.sendPacket(new CHeldItemChangePacket(oldItem));
                        throwDelay.reset();
                    }
                }

                if (timerUtil.hasReached(500L)) {
                    try {
                        this.selectedSlot = -2;
                    } catch (Exception ignored) {
                    }
                }

                this.potionUtil.changeItemSlot(this.selectedSlot == -2);
            }
        }
    };
    private final Listener<MotionEvent> onMotion = event -> {
        if (!mode.is("Пузырьками")) return;

        if (mc.currentScreen != null) return;
        if (isNotPressed()) return;

        if (this.isNotThrowing()) {
            return;
        }
        if (checkFixInv().equals(ItemStack.EMPTY) || (getPotionIndexInv() == -1 && getPotionIndexHb() == -1)) {
            return;
        }

        this.previousPitch = 90.0F;
        event.setPitch(this.previousPitch);
    };

    private boolean isNotPressed() {
        return !Keyboard.isKeyDown(key.getValue());
    }

    private int setSlotAndUseItem() {
        int hbSlot = this.getPotionIndexHb();
        if (hbSlot != -1) {
            this.potionUtil.setPreviousSlot(mc.player.inventory.currentItem);
            mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            PotionUtil.useItem(Hand.MAIN_HAND);
            timerUtil.reset();
            return hbSlot;
        } else {
            int invSlot = this.getPotionIndexInv();
            if (invSlot != -1) {
                this.potionUtil.setPreviousSlot(mc.player.inventory.currentItem);
                mc.playerController.pickItem(invSlot);
                PotionUtil.useItem(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                timerUtil.reset();
                return invSlot;
            } else {
                return -1;
            }
        }
    }

    public boolean isNotThrowing() {
        return !(mc.player.isOnGround() || mc.world.getBlockState(new BlockPos(mc.player.getPosX(), mc.player.getBoundingBox().minY - 0.3f, mc.player.getPosZ())).isSolid()) || mc.player.isOnLadder() || mc.player.getRidingEntity() != null || mc.player.abilities.isFlying || mc.player.isInLiquid();
    }


    private int getPotionIndexHb() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.EXPERIENCE_BOTTLE) return i;
        }

        return -1;
    }

    private int getPotionIndexInv() {
        for (int i = 9; i < 36; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.EXPERIENCE_BOTTLE) return i;
        }

        return -1;
    }

    private boolean checkFix(ItemStack item) {
        return item.getMaxDamage() != 0 && ((item.getMaxDamage() - item.getDamage()) <= 3);
    }

    private ItemStack checkFixInv() {
        for (ItemStack stack : mc.player.getArmorInventoryList()) {
            if (stack.isEmpty()) continue;
            boolean mending = false;
            if (stack.isEnchanted()) {
                for (int j = 0; j < stack.getEnchantmentTagList().size(); j++) {
                    String stackItemEnchant = stack.getEnchantmentTagList().getCompound(j).getString("id").replaceAll("minecraft:", "");
                    if (stackItemEnchant.equalsIgnoreCase("mending")) {
                        mending = true;
                        break;
                    }
                }
            }
            if (stack.getMaxDamage() != 0 && stack.getDamage() != 0 && mending) return stack;
        }
        for (int i = 0; i < 36; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            boolean mending = false;
            if (stack.isEnchanted()) {
                for (int j = 0; j < stack.getEnchantmentTagList().size(); j++) {
                    String stackItemEnchant = stack.getEnchantmentTagList().getCompound(j).getString("id").replaceAll("minecraft:", "");
                    if (stackItemEnchant.equalsIgnoreCase("mending")) {
                        mending = true;
                        break;
                    }
                }
            }
            if (stack.getMaxDamage() != 0 && stack.getDamage() != 0 && mending) return stack;
        }
        return ItemStack.EMPTY;
    }

}
