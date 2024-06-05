package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.module.impl.movement.Scaffold;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.InventoryUtils;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

@ModuleInfo(label = "Auto Potion", category = ModuleCategory.COMBAT)
public final class AutoPotion extends Module {

    private static final byte HEALTH_BELOW = 1;
    private static final byte BETTER_THAN_CURRENT = 2;

    private static final PotionType[] VALID_POTIONS = {PotionType.HEALTH, PotionType.REGEN, PotionType.SPEED};

    private final C08PacketPlayerBlockPlacement THROW_POTION_PACKET = new C08PacketPlayerBlockPlacement(
            new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);

    private final DoubleProperty healthProperty = new DoubleProperty("Health",
            6.0, 1.0, 10.0, 0.5);
    private final DoubleProperty potionAndSoupSlotProperty = new DoubleProperty("Slot",
            7.0, 1.0, 9.0, 1);
    private final DoubleProperty delayProperty = new DoubleProperty("Delay",
            400.0, 0.0, 1000.0, 50);

    private final TimerUtil interactionTimer = new TimerUtil();
    private int prevSlot;
    private boolean potting;
    private String potionCounter;

    private boolean jumpNextTick;

    public AutoPotion() {
        setSuffix(() -> potionCounter);
    }

    @Override
    public void onEnable() {
        potionCounter = "0";
        prevSlot = -1;
        potting = false;
    }

    @Listener(Priority.HIGH)
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (Wrapper.getMinecraft().currentScreen != null)
            return;
        if (ModuleManager.getInstance(Scaffold.class).isEnabled())
            return;
        if (event.isPre()) {
            if (jumpNextTick) {
                Wrapper.getPlayer().setPosition(
                        Wrapper.getPlayer().posX,
                        Wrapper.getPlayer().posY + 1.2492F,
                        Wrapper.getPlayer().posZ);
                event.setPosY(event.getPosY() + 1.2492F);
                jumpNextTick = false;
            }

            potionCounter = Integer.toString(getValidPotionsInInv());
            if (interactionTimer.hasElapsed(delayProperty.getValue().longValue())) {
                for (int slot = 9; slot < 45; slot++) {
                    ItemStack stack = Wrapper.getStackInSlot(slot);
                    if (stack != null && stack.getItem() instanceof ItemPotion &&
                            ItemPotion.isSplash(stack.getMetadata()) && InventoryUtils.isBuffPotion(stack)) {
                        ItemPotion itemPotion = (ItemPotion) stack.getItem();
                        boolean validEffects = false;
                        // Use ItemPotion#getEffects(int) Note: The int parameter, the method is considerably faster
                        for (PotionEffect effect : itemPotion.getEffects(stack.getMetadata())) {
                            for (PotionType potionType : VALID_POTIONS) {
                                if (potionType.potionId == effect.getPotionID()) {
                                    validEffects = true;
                                    if (hasFlag(potionType.requirementFlags, HEALTH_BELOW))
                                        validEffects = Wrapper.getPlayer().getHealth() < healthProperty.getValue() * 2.0F;
                                    boolean orIsLesserPresent = hasFlag(potionType.requirementFlags, BETTER_THAN_CURRENT);
                                    PotionEffect activePotionEffect = Wrapper.getPlayer().getActivePotionEffect(potionType.potionId);
                                    if (orIsLesserPresent)
                                        if (activePotionEffect != null)
                                            validEffects &= activePotionEffect.getAmplifier() < effect.getAmplifier();
                                }
                            }

                        }

                        if (validEffects) {
                            if (MovementUtils.isOverVoid())
                                return;

                            prevSlot = Wrapper.getPlayer().inventory.currentItem;

                            double xDist = Wrapper.getPlayer().posX - Wrapper.getPlayer().lastTickPosX;
                            double zDist = Wrapper.getPlayer().posZ - Wrapper.getPlayer().lastTickPosZ;

                            double speed = Math.sqrt(xDist * xDist + zDist * zDist);

                            boolean shouldPredict = speed > 0.38D;
                            boolean shouldJump = speed < MovementUtils.WALK_SPEED;
                            boolean onGround = MovementUtils.isOnGround();

                            if (shouldJump && onGround && !MovementUtils.isBlockAbove() && MovementUtils.getJumpBoostModifier() == 0) {
                                Wrapper.getPlayer().motionX = 0.0D;
                                Wrapper.getPlayer().motionZ = 0.0D;
                                event.setPitch(-90.0F);
//                                jumpNextTick = true;
                                Wrapper.getPlayer().jump();
                            } else if (shouldPredict || onGround) {
                                event.setYaw(MovementUtils.getMovementDirection());
                                event.setPitch(shouldPredict ? 0.0F : 45.0F);
                            } else return;

                            final int potSlot;
                            KillAura.getInstance().waitTicks = 2;
                            if (slot >= 36) { // In hotbar
                                potSlot = slot - 36;
                            } else { // Get it from inventory
                                int potionSlot = potionAndSoupSlotProperty.getValue().intValue() - 1;
                                InventoryUtils.windowClick(slot, potionSlot,
                                        InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                                potSlot = potionSlot;
                            }
                            Wrapper.sendPacketDirect(new C09PacketHeldItemChange(potSlot));
                            potting = true;
                            break;
                        }
                    }
                }
            }
        } else if (potting && prevSlot != -1) {
            Wrapper.sendPacketDirect(THROW_POTION_PACKET);
            Wrapper.sendPacketDirect(new C09PacketHeldItemChange(prevSlot));
            interactionTimer.reset();
            prevSlot = -1;
            potting = false;
        }
    }

    private int getValidPotionsInInv() {
        int count = 0;
        for (int i = 9; i < 45; i++) {
            ItemStack stack = Wrapper.getStackInSlot(i);

            if (stack != null && stack.getItem() instanceof ItemPotion &&
                    ItemPotion.isSplash(stack.getMetadata()) && InventoryUtils.isBuffPotion(stack)) {
                ItemPotion itemPotion = (ItemPotion) stack.getItem();
                for (PotionEffect effect : itemPotion.getEffects(stack.getMetadata())) {
                    boolean breakOuter = false;
                    for (PotionType type : VALID_POTIONS) {
                        if (type.potionId == effect.getPotionID()) {
                            count++;
                            breakOuter = true;
                            break;
                        }
                    }
                    if (breakOuter) break;
                }
            }
        }

        return count;
    }

    private boolean hasFlag(int flags, int flagToCheck) {
        return (flags & flagToCheck) == flagToCheck;
    }

    private enum Items {
        HEADS, POTIONS, SOUPS
    }

    private enum PotionType {
        SPEED(Potion.moveSpeed.id, BETTER_THAN_CURRENT),
        REGEN(Potion.regeneration.id, BETTER_THAN_CURRENT | HEALTH_BELOW),
        HEALTH(Potion.heal.id, HEALTH_BELOW);

        private final int potionId;
        private final int requirementFlags;

        PotionType(int potionId, int requirementFlags) {
            this.potionId = potionId;
            this.requirementFlags = requirementFlags;
        }
    }
}
