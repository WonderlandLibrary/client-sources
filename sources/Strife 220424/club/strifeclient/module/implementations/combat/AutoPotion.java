package club.strifeclient.module.implementations.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.MultiSelectSetting;
import club.strifeclient.util.player.ChatUtil;
import club.strifeclient.util.player.InventoryUtil;
import club.strifeclient.util.player.MovementUtil;
import club.strifeclient.util.player.PlayerUtil;
import club.strifeclient.util.system.Stopwatch;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.function.Predicate;

@ModuleInfo(name = "AutoPotion", description = "Automatically pots you.", category = Category.COMBAT)
public final class AutoPotion extends Module {

    private final MultiSelectSetting<Potions> potionsSetting = new MultiSelectSetting<>("Potions", Potions.HEALTH);
    private static final DoubleSetting healthSetting = new DoubleSetting("Health", 10, 0.5, 20, 0.5);
    private final BooleanSetting jumpSetting = new BooleanSetting("Jump", true);

    private final Stopwatch potTimer = new Stopwatch();
    private int stage, lastSlot, bestPotionSlot;
    private boolean jump;

    public AutoPotion() {
        healthSetting.setDependency(() -> potionsSetting.isSelected(Potions.HEALTH));
    }

    @Override
    protected void onDisable() {
        stage = 0;
        bestPotionSlot = -1;
        potTimer.reset();
        super.onDisable();
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if (!e.ground && !jump) return;
        try {
            if (potTimer.hasElapsed(450)) {
                switch (stage) {
                    case 0: {
                        bestPotionSlot = getBestPotionSlot();
                        if (bestPotionSlot != -1 && bestPotionSlot < InventoryUtil.HOTBAR) {
                            final int bestSwapSlot = InventoryUtil.getNextEmptySlot();
                            InventoryUtil.windowClick(bestPotionSlot,
                                    bestSwapSlot - InventoryUtil.HOTBAR,
                                    InventoryUtil.ClickType.SWAP_WITH_HOTBAR);
                            bestPotionSlot = bestSwapSlot;
                        }
                        if (bestPotionSlot == -1) return;
                        bestPotionSlot -= InventoryUtil.HOTBAR;
                        lastSlot = mc.thePlayer.inventory.currentItem;
                        mc.thePlayer.inventory.currentItem = bestPotionSlot;
                        stage = 1;
                        break;
                    }
                    case 1: {
                        if (e.isPre()) {
                            final float dynamicPitch = getDynamicPitch(e);
                            if (dynamicPitch != -1) {
                                ChatUtil.sendMessage(dynamicPitch);
                                e.pitch = dynamicPitch;
                                if (dynamicPitch == -90)
                                    jump = true;
                                stage = 2;
                            }
                        }
                        break;
                    }
                    case 2: {
                        if (e.isPost()) {
                            if (!MovementUtil.isMoving() && jump) {
                                mc.thePlayer.jump();
                                jump = false;
                            }
                            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(bestPotionSlot));
                            mc.playerController.onStoppedUsingItem(mc.thePlayer);
                            stage = 3;
                        }
                        break;
                    }
                    case 3: {
                        mc.thePlayer.inventory.currentItem = lastSlot;
                        stage = 0;
                        bestPotionSlot = -1;
                        potTimer.reset();
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    };

    private float getDynamicPitch(MotionEvent e) {
        final double bps = PlayerUtil.getBPS();
        final boolean movingForward = mc.thePlayer.movementInput.moveForward > 0 && mc.thePlayer.isPotionActive(Potion.moveSpeed.id) && bps > 5;
        if (e.ground) {
            if (movingForward)
                return 0;
            else return (jumpSetting.getValue() && !MovementUtil.isMoving() ? -90 : 85);
        }
        return -1;
    }

    private boolean isPotionValid(ItemStack stack, Predicate<PotionEffect> validation) {
        if (stack == null) return false;
        if (ItemPotion.isSplash(stack.getMetadata()) && stack.getItem() instanceof ItemPotion) {
            if (mc.thePlayer.getActivePotionEffects().stream().anyMatch(validation)) return false;
            final ItemPotion itemPotion = (ItemPotion) stack.getItem();
            return itemPotion.getEffects(stack).stream().anyMatch(validation);
        }
        return false;
    }

    private int getBestPotionSlot() {
        for (int i = InventoryUtil.EXCLUDE_ARMOR; i < InventoryUtil.END; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            for (Potions enumPotion : Potions.values()) {
                if (potionsSetting.isSelected(enumPotion)) {
                    if (isPotionValid(stack, enumPotion.getValidation())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public enum Potions implements SerializableEnum {
        HEALTH("Health", p -> p.getPotionID() == Potion.heal.id && mc.thePlayer.getHealth() < healthSetting.getValue()),
        REGEN("Regen", p -> p.getPotionID() == Potion.regeneration.id),
        SPEED("Speed", p -> p.getPotionID() == Potion.moveSpeed.id),
        JUMP("Jump", p -> p.getPotionID() == Potion.jump.id),
        RESIST("Resist", p -> p.getPotionID() == Potion.resistance.id),
        FIRE("Fire", p -> p.getPotionID() == Potion.fireResistance.id);
        final String name;
        final Predicate<PotionEffect> validation;
        Potions(final String name, final Predicate<PotionEffect> validation) {
            this.name = name;
            this.validation = validation;
        }
        @Override
        public String getName() {
            return name;
        }
        public Predicate<PotionEffect> getValidation() {
            return validation;
        }
    }
}
