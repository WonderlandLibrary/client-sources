package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.event.player.EventMove;
import dev.darkmoon.client.event.player.EventStartElytraFlying;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.combat.TargetStrafe;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@ModuleAnnotation(name = "ElytraFirework", category = Category.MOVEMENT)
public class ElytraFirework extends Module {
    private final NumberSetting speedXZ = new NumberSetting("Speed XZ", 1.15F, 0.5F, 3.0F, 0.01F);
    private final NumberSetting speedY = new NumberSetting("Speed Y", 0.1F, 0.1F, 1.0F, 0.01F);
    private final NumberSetting shaking = new NumberSetting("Shaking", 0.2F, 0.1F, 2.0F, 0.01F);
    private final NumberSetting fireworkDelay = new NumberSetting("Firework Delay", 1.6F, 0.5F, 3.0F, 0.1F);
    private final NumberSetting fireworkSlot = new NumberSetting("Firework Slot", 9.0F, 1.0F, 9.0F, 1.0F);
    private final BooleanSetting keepFlying = new BooleanSetting("Keep Flying", true);
    private ItemStack prevArmorItemCopy;
    private Item prevArmorItem;
    private int prevElytraSlot;
    private ItemStack getStackInSlotCopy;
    private Item prevItemInHand;
    private int slotWithFireWorks;
    private boolean elytraEquiped;
    private long lastFireworkTime;
    private boolean flying;
    private int currentSpeed;
    private int ticksInAir;
    private boolean startFallFlying;
    private boolean starting;

    public ElytraFirework() {
        this.prevArmorItem = Items.AIR;
        this.prevElytraSlot = -1;
        this.prevItemInHand = Items.AIR;
        this.slotWithFireWorks = -1;
    }

    @EventTarget
    public void onElytraFall(EventStartElytraFlying event) {
        this.fly(false);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        boolean isInsideAir = mc.player.isInsideOfMaterial(Material.AIR);
        boolean isAboveLiquid = isAboveLiquid(0.1F) && isInsideAir && mc.player.motionY < 0.0;
        boolean shouldEquip = mc.player.fallDistance > 0.0F && isInsideAir || isAboveLiquid;
        if (shouldEquip) {
            this.equipElytra();
        } else if (mc.player.onGround) {
            this.startFallFlying = false;
            this.ticksInAir = 0;
            if (!this.isAboveWater()) {
                mc.gameSettings.keyBindJump.pressed = false;
                mc.player.jump();
            }

            return;
        }

        if (mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F) {
            this.currentSpeed = 0;
        }

        if (this.canFly()) {
            if (!mc.player.isElytraFlying() && !this.startFallFlying && mc.player.motionY < 0.0) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                this.startFallFlying = true;
            }

            if (mc.player.getTicksElytraFlying() < 4) {
                mc.gameSettings.keyBindJump.pressed = false;
            }

            this.fly(true);
        }
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if (mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F && mc.player.movementInput.jump && mc.player.isElytraFlying() && this.flying) {
            event.setPitch(-90.0F);
        }

        if (mc.player.getTicksElytraFlying() < 5) {
            event.setPitch(-90.0F);
            this.starting = true;
        } else {
            this.starting = false;
        }

    }

    private boolean canControl() {
        return !DarkMoon.getInstance().getModuleManager().getModule(TargetStrafe.class).isEnabled();
    }

    @EventTarget
    public void onMove(EventMove event) {
        if (mc.player.isElytraFlying() && this.flying) {
            if (mc.player.getTicksElytraFlying() < 4) {
                event.motion().y = 1.0;
            }

            float spXZ = this.speedXZ.get() - 0.017F;
            float spY = this.speedY.get();
            Vec3d var10000 = event.motion();
            Vec3d var10001;
            double var4;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                var10001 = event.motion();
                var4 = var10001.y += (double)spY;
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                var10001 = event.motion();
                var4 = var10001.y -= (double)spY;
            } else {
                var4 = mc.player.ticksExisted % 2 == 0 ? (double)this.shaking.get() : (double)(-this.shaking.get());
            }

            var10000.y = var4;
            mc.player.motionY = event.motion().y * (double)spY;
            if (this.canControl()) {
                strafe(event, spXZ * Math.min((float)(this.currentSpeed += 9) / 100.0F, 1.0F));
            }
        }

    }

    public static void strafe(EventMove event, float f2) {
        float rotYaw = mc.player.rotationYaw;
        float moveForward = mc.player.movementInput.moveForward;
        float moveStrafe = mc.player.movementInput.moveStrafe;
        if (moveForward != 0.0F) {
            if (moveStrafe > 0.0F) {
                rotYaw += (float)(moveForward > 0.0F ? -45 : 45);
            } else if (moveStrafe < 0.0F) {
                rotYaw += (float)(moveForward > 0.0F ? 45 : -45);
            }

            moveStrafe = 0.0F;
            if (moveForward > 0.0F) {
                moveForward = 1.0F;
            } else if (moveForward < 0.0F) {
                moveForward = -1.0F;
            }
        }

        double d2 = Math.cos(Math.toRadians((double)(rotYaw + 90.0F)));
        double d3 = Math.sin(Math.toRadians((double)(rotYaw + 90.0F)));
        event.motion().x = (double)(moveForward * f2) * d2 + (double)(moveStrafe * f2) * d3;
        event.motion().z = (double)(moveForward * f2) * d3 - (double)(moveStrafe * f2) * d2;
    }

    public boolean isAboveWater() {
        return mc.player.isInWater() || mc.world.isMaterialInBB(mc.player.getEntityBoundingBox().expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.WATER);
    }

    private void pickPrevElytraSlot() {
        if (this.prevElytraSlot != -1) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, this.prevElytraSlot, 0, ClickType.PICKUP, mc.player);
        }

    }

    private void equipElytra() {
        int elytraSlot = getElytraSlot();
        if (elytraSlot == -1 && mc.player.inventory.getItemStack().getItem() != Items.ELYTRA) {
            this.noElytra();
        } else if (this.shouldSwapToElytra()) {
            if (this.prevElytraSlot == -1) {
                ItemStack is = mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                this.prevElytraSlot = elytraSlot;
                this.prevArmorItem = is.getItem();
                this.prevArmorItemCopy = is.copy();
            }

            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, elytraSlot, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 1, ClickType.PICKUP, mc.player);
            this.pickPrevElytraSlot();
            this.elytraEquiped = true;
        }
    }

    public static int getElytraSlot() {
        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            return -2;
        } else if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiContainerCreative)) {
            return -1;
        } else {
            for(int i = 0; i < 45; ++i) {
                ItemStack is = mc.player.inventory.getStackInSlot(i);
                if (is.getItem() == Items.ELYTRA && ItemElytra.isUsable(is)) {
                    return i < 9 ? i + 36 : i;
                }
            }

            return -1;
        }
    }

    private void fly(boolean started) {
        if (!started || !((float)(System.currentTimeMillis() - this.lastFireworkTime) < this.fireworkDelay.get() * 1000.0F)) {
            if (!started || mc.player.isElytraFlying()) {
                if (started || this.ticksInAir <= 1) {
                    int fireworksSlot = this.getFireworks();
                    if (fireworksSlot == -1) {
                        this.slotWithFireWorks = -1;
                    } else {
                        this.slotWithFireWorks = fireworksSlot;
                        boolean isFireworks = mc.player.getHeldItemOffhand().getItem() == Items.FIREWORKS;
                        if (!isFireworks) {
                            mc.player.connection.sendPacket(new CPacketHeldItemChange(fireworksSlot));
                        }

                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(isFireworks ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
                        if (!isFireworks) {
                            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                        }

                        ++this.ticksInAir;
                        this.flying = true;
                        this.lastFireworkTime = System.currentTimeMillis();
                    }
                }
            }
        }
    }

    private boolean canFly() {
        if (this.shouldSwapToElytra()) {
            return false;
        } else {
            return this.getFireworks() != -1;
        }
    }

    private boolean shouldSwapToElytra() {
        ItemStack is = mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (is.getItem() != Items.ELYTRA) {
            return true;
        } else {
            return !ItemElytra.isUsable(is);
        }
    }

    private int getFireWorks(boolean hotbar) {
        return InventoryUtility.getItemSlot(Items.FIREWORKS, hotbar);
    }

    private void noFireworks() {
        ChatUtility.addChatMessage("Нет фейерверков в инвентаре!");
        this.setToggled(false);
        this.flying = false;
        this.ticksInAir = 0;
    }

    private void noElytra() {
        ChatUtility.addChatMessage("Нет элитры в инвентаре!");
        this.setToggled(false);
        this.flying = false;
        this.ticksInAir = 0;
    }

    public static boolean isAboveLiquid(float offset) {
        return mc.player == null ? false : mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - (double)offset, mc.player.posZ)).getBlock() instanceof BlockLiquid;
    }

    private int getFireworks() {
        if (mc.player.getHeldItemOffhand().getItem() == Items.FIREWORKS) {
            return -2;
        } else {
            int hotbarFireworks = this.getFireWorks(true);
            int fireworks = this.getFireWorks(false);
            if (fireworks == -1) {
                this.noFireworks();
                return -1;
            } else if (hotbarFireworks == -1) {
                this.moveFireworksToHotbar(fireworks);
                return this.fireworkSlot.getInt() - 1;
            } else {
                return hotbarFireworks;
            }
        }
    }

    private void moveFireworksToHotbar(int n2) {
        mc.playerController.windowClick(0, n2, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, this.fireworkSlot.getInt() - 1 + 36, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, n2, 0, ClickType.PICKUP, mc.player);
    }

    private void reset() {
        this.slotWithFireWorks = -1;
        this.prevItemInHand = Items.AIR;
        this.getStackInSlotCopy = null;
        this.starting = false;
        this.ticksInAir = 0;
    }

    private void resetPrevItems() {
        this.prevElytraSlot = -1;
        this.prevArmorItem = Items.AIR;
        this.prevArmorItemCopy = null;
    }

    public static int findEmpty(boolean hotbar) {
        for(int i = hotbar ? 0 : 9; i < (hotbar ? 9 : 45); ++i) {
            if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    public static int findInInventory(ItemStack stack, Item item) {
        if (stack == null) {
            return -1;
        } else {
            for(int i = 0; i < 45; ++i) {
                ItemStack is = mc.player.inventory.getStackInSlot(i);
                if (ItemStack.areItemsEqual(is, stack) && is.getItem() == item) {
                    return i;
                }
            }

            return -1;
        }
    }

    private void returnChestPlate() {
        if (this.prevElytraSlot != -1 && this.prevArmorItem != Items.AIR) {
            if (!this.elytraEquiped) {
                return;
            }

            ItemStack is = mc.player.inventoryContainer.getSlot(this.prevElytraSlot).getStack();
            boolean bl2 = is != ItemStack.EMPTY && !ItemStack.areItemsEqual(is, this.prevArmorItemCopy);
            int n2 = findInInventory(this.prevArmorItemCopy, this.prevArmorItem);
            n2 = n2 < 9 && n2 != -1 ? n2 + 36 : n2;
            int n3 = mc.player.inventoryContainer.windowId;
            if (mc.player.inventory.getItemStack().getItem() != Items.AIR) {
                mc.playerController.windowClick(n3, 6, 0, ClickType.PICKUP, mc.player);
                this.pickPrevElytraSlot();
                return;
            }

            if (n2 == -1) {
                return;
            }

            mc.playerController.windowClick(n3, n2, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(n3, 6, 0, ClickType.PICKUP, mc.player);
            if (!bl2) {
                mc.playerController.windowClick(n3, n2, 0, ClickType.PICKUP, mc.player);
            } else {
                int n4 = findEmpty(false);
                if (n4 != -1) {
                    mc.playerController.windowClick(n3, n4, 0, ClickType.PICKUP, mc.player);
                }
            }
        }

        this.resetPrevItems();
    }

    private void returnItem() {
        if (this.slotWithFireWorks != -1 && this.getStackInSlotCopy != null && this.prevItemInHand != Items.FIREWORKS && this.prevItemInHand != Items.AIR) {
            int n2 = findInInventory(this.getStackInSlotCopy, this.prevItemInHand);
            n2 = n2 < 9 && n2 != -1 ? n2 + 36 : n2;
            mc.playerController.windowClick(0, n2, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, this.fireworkSlot.getInt() - 1 + 36, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, n2, 0, ClickType.PICKUP, mc.player);
        }
    }

    public void onEnable() {
        super.onEnable();
        if (mc.player != null) {
            if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA && mc.player.inventory.getItemStack().getItem() != Items.ELYTRA && getElytraSlot() == -1) {
                this.noElytra();
            } else if (this.getFireWorks(false) == -1) {
                this.noFireworks();
            } else if (this.getFireWorks(true) == -1) {
                int slot = this.fireworkSlot.getInt() - 1;
                this.getStackInSlotCopy = mc.player.inventory.getStackInSlot(slot).copy();
                this.prevItemInHand = mc.player.inventory.getStackInSlot(slot).getItem();
            }
        }
    }

    public void onDisable() {
        if (mc.player != null) {
            this.currentSpeed = 0;
            this.startFallFlying = false;
            (new Thread(() -> {
                this.returnItem();
                this.reset();

                try {
                    Thread.sleep(200L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }

                this.returnChestPlate();
                this.resetPrevItems();
            })).start();
            if (!this.keepFlying.get() && mc.player.isElytraFlying()) {
                mc.player.setVelocity(0.0, 0.0, 0.0);
                mc.player.blockAllMoves(true, true);
                (new Thread(() -> {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException var1) {
                        var1.printStackTrace();
                    }

                    mc.player.setVelocity(0.0, 0.0, 0.0);
                    mc.player.blockAllMoves(false, false);
                })).start();
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        }

        super.onDisable();
    }
}
