package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventPacket;
import im.expensive.events.EventSpawnEntity;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@FunctionRegister(name = "AutoTotem", type = Category.Combat)
public class AutoTotem extends Function {
    private final ModeSetting totemMode = new ModeSetting("Мод", "Обычный", "Обычный", "Funtime");
    private final SliderSetting health = new SliderSetting("Здоровье", 3.5f, 1.f, 20.f, 0.05f);
    private final BooleanSetting swapBack = new BooleanSetting("Возвращать предмет", true);
    private final BooleanSetting noBallSwitch = new BooleanSetting("Не брать если шар в руке", false);
    private final BooleanSetting saveEnchanted = new BooleanSetting("Сохранять зачарованные", true);
    int nonEnchantedTotems;


    private final ModeListSetting mode = new ModeListSetting("Учитывать", new BooleanSetting("Золотые сердца", true),
            new BooleanSetting("Кристаллы", true),
            new BooleanSetting("Якорь", false),
            new BooleanSetting("Падение", false));

    int oldItem = -1;
    public boolean isActive;
    StopWatch stopWatch = new StopWatch();


    private Item backItem = Items.AIR;
    private ItemStack backItemStack;

    public AutoTotem() {
        addSettings(totemMode, health, swapBack, saveEnchanted, noBallSwitch, mode);
    }


    private int itemInMouse = -1;
    private int totemCount = 0;
    private boolean totemIsUsed;

    @Subscribe
    private void onSpawnEntity(EventSpawnEntity spawnEntity) {
        if (spawnEntity.getEntity() instanceof EnderCrystalEntity entity && mode.getValueByName("Кристаллы").get()) {
            if (entity.getDistance(mc.player) <= 6.0F) {
                this.swapToTotem();
            }
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        totemCount = countTotems(true);
        switch (totemMode.get()) {
            case "Обычный" -> {
                this.nonEnchantedTotems = (int) IntStream.range(0, 36).mapToObj((i) -> mc.player.inventory.getStackInSlot(i)).filter((s) -> s.getItem() == Items.TOTEM_OF_UNDYING && !s.isEnchanted()).count();

                int slot = getSlotInInventory(Items.TOTEM_OF_UNDYING);

                boolean handNotNull = !(mc.player.getHeldItemOffhand().getItem() instanceof AirItem);


                if (shouldToSwapTotem()) {
                    if (slot != -1 && !isTotemInHands()) {
                        InventoryUtil.moveItem(slot, 45, handNotNull);
                        if (handNotNull && oldItem == -1) {
                            oldItem = slot;
                        }
                    }
                } else if (oldItem != -1 && swapBack.get()) {
                    InventoryUtil.moveItem(oldItem, 45, handNotNull);
                    oldItem = -1;
                }
            }
            case "Funtime" -> {
                if (shouldToSwapTotem()) {
                    if (itemIsHand(Items.TOTEM_OF_UNDYING)) {
                        return;
                    }
                    swapToTotem();
                }
                this.swapBack();
            }
        }
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        if (e.isReceive()) {
            if (e.getPacket() instanceof SEntityStatusPacket statusPacket && statusPacket.getOpCode() == 35 && statusPacket.getEntity(mc.world) == mc.player) {
                this.totemIsUsed = true;
            }
        }
    }

    private void swapBack() {
        if (this.stopWatch.isReached(400) && this.itemIsBack()) {
            this.itemInMouse = -1;
            this.backItem = Items.AIR;
            this.backItemStack = null;
            this.stopWatch.reset();
        }
    }

    private boolean itemIsBack() {
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING && this.itemInMouse != -1 && this.backItem != Items.AIR) {
            ItemStack itemStack = mc.player.container.getSlot(this.itemInMouse).getStack();
            boolean offHandAreEqual = itemStack != ItemStack.EMPTY && !ItemStack.areItemStacksEqual(itemStack, this.backItemStack);
            int oldItem = findItemSlotIndex(backItemStack, backItem);

            if (oldItem < 9 && oldItem != -1) {
                oldItem = oldItem + 36;
            }


            int containerId = mc.player.container.windowId;

            if (mc.player.inventory.getItemStack().getItem() != Items.AIR) {
                mc.playerController.windowClick(containerId, 45, 0, ClickType.PICKUP, mc.player);
                this.backItemInMouse();
                return false;
            }

            if (oldItem == -1) {
                return false;
            }

            mc.playerController.windowClick(containerId, oldItem, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(containerId, 45, 0, ClickType.PICKUP, mc.player);
            if (this.itemInMouse != -1) {
                if (!offHandAreEqual) {
                    mc.playerController.windowClick(containerId, this.itemInMouse, 0, ClickType.PICKUP, mc.player);
                } else {
                    int emptySlot = getEmptySlot(false);
                    if (emptySlot != -1) {
                        mc.playerController.windowClick(containerId, emptySlot, 0, ClickType.PICKUP, mc.player);
                    }
                }
            }
        }
        return true;
    }

    public static int getEmptySlot(boolean hotBar) {
        for (int i = hotBar ? 0 : 9; i < (hotBar ? 9 : 45); ++i) {
            if (!mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
            return i;
        }
        return -1;
    }

    public int findItemSlotIndex(ItemStack targetItemStack, Item targetItem) {
        if (targetItemStack == null) {
            return -1;
        }

        for (int i = 0; i < 45; ++i) {
            ItemStack currentStack = mc.player.inventory.getStackInSlot(i);

            if (ItemStack.areItemStacksEqual(currentStack, targetItemStack) && currentStack.getItem() == targetItem) {
                return i;
            }
        }

        return -1;
    }


    public boolean itemIsHand(Item item) {
        for (Hand enumHand : Hand.values()) {
            if (mc.player.getHeldItem(enumHand).getItem() != item) continue;
            return true;
        }
        return false;
    }

    private void swapToTotem() {
        int totemSlot = getSlotInInventory(Items.TOTEM_OF_UNDYING);
        this.stopWatch.reset();
        Item mainHandItem = mc.player.getHeldItemOffhand().getItem();

        if (mainHandItem == Items.TOTEM_OF_UNDYING) {
            return;
        }

        if (totemSlot == -1 && !isCurrentItem(Items.TOTEM_OF_UNDYING)) {
            return;
        }


        if (this.itemInMouse == -1) {
            this.itemInMouse = totemSlot;
            this.backItem = mainHandItem;
            this.backItemStack = mc.player.getHeldItemOffhand().copy();
        }
        mc.playerController.windowClick(mc.player.container.windowId, totemSlot, 1, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.container.windowId, 45, 1, ClickType.PICKUP, mc.player);

        if (this.totemCount > 1 && this.totemIsUsed) {
            this.backItemInMouse();
            this.totemIsUsed = false;
        }
        this.backItemInMouse();
    }

    public int countTotems(boolean includeEnchanted) {
        long totemCount = 0L;
        int inventorySize = AutoTotem.mc.player.inventory.getSizeInventory();

        for (int slotIndex = 0; slotIndex < inventorySize; ++slotIndex) {
            ItemStack slotStack = AutoTotem.mc.player.inventory.getStackInSlot(slotIndex);

            if (slotStack.getItem() == Items.TOTEM_OF_UNDYING && (includeEnchanted || !slotStack.isEnchanted())) {
                ++totemCount;
            }
        }

        return (int) totemCount;
    }

    private void backItemInMouse() {
        if (this.itemInMouse != -1) {
            mc.playerController.windowClick(mc.player.container.windowId, this.itemInMouse, 0, ClickType.PICKUP, mc.player);
        }
    }

    public static boolean isCurrentItem(Item item) {
        return mc.player.inventory.getItemStack().getItem() == item;
    }

    private boolean isTotemInHands() {
        Hand[] hands = Hand.values();

        for (Hand hand : hands) {
            ItemStack heldItem = mc.player.getHeldItem(hand);
            if (heldItem.getItem() == Items.TOTEM_OF_UNDYING && !this.isSaveEnchanted(heldItem)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSaveEnchanted(ItemStack itemStack) {
        return this.saveEnchanted.get() && itemStack.isEnchanted() && this.nonEnchantedTotems > 0;
    }

    private boolean shouldToSwapTotem() {
        final float absorptionAmount = mc.player.isPotionActive(Effects.ABSORPTION) ? mc.player.getAbsorptionAmount() : 0.0f;
        float currentHealth = mc.player.getHealth();

        if (mode.getValueByName("Золотые сердца").get()) {
            currentHealth += absorptionAmount;
        }


        if (!isOffhandItemBall()) {
            if (isInDangerousSituation()) {
                return true;
            }
        }


        return currentHealth <= this.health.get();
    }

    private boolean isInDangerousSituation() {
        return checkCrystal() || checkAnchor() || checkFall();
    }

    private boolean checkFall() {
        if (!this.mode.getValueByName("Падение").get()) {
            return false;
        }
        if (mc.player.isInWater()) {
            return false;
        }

        if (mc.player.isElytraFlying()) {
            return false;
        }

        return mc.player.fallDistance > 10.0f;
    }


    private boolean checkAnchor() {
        if (!mode.getValueByName("Якорь").get()) return false;

        return getBlock(6.0F, Blocks.RESPAWN_ANCHOR) != null;
    }


    private boolean checkCrystal() {
        if (!mode.getValueByName("Кристаллы").get()) {
            return false;
        }

        for (Entity entity : mc.world.getAllEntities()) {
            if (isDangerousEntityNearPlayer(entity)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOffhandItemBall() {
        boolean isFallingConditionMet = this.mode.getValueByName("Падение").get() && mc.player.fallDistance > 5.0f;

        if (isFallingConditionMet) {
            return false;
        }
        return this.noBallSwitch.get() && mc.player.getHeldItemOffhand().getItem() == Items.PLAYER_HEAD;
    }

    private boolean isDangerousEntityNearPlayer(Entity entity) {
        return (entity instanceof TNTEntity || entity instanceof TNTMinecartEntity) && mc.player.getDistance(entity) <= 6.0F;
    }

    private final BlockPos getBlock(float distance, Block block) {
        return getSphere(getPlayerPosLocal(), distance, 6, false, true, 0).stream().filter(position -> mc.world.getBlockState(position).getBlock() == block).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);
    }

    private final List<BlockPos> getSphere(final BlockPos center, final float radius, final int height, final boolean hollow, final boolean fromBottom, final int yOffset) {
        List<BlockPos> positions = new ArrayList<>();
        int centerX = center.getX();
        int centerY = center.getY();
        int centerZ = center.getZ();

        for (int x = centerX - (int) radius; x <= centerX + radius; x++) {
            for (int z = centerZ - (int) radius; z <= centerZ + radius; z++) {
                int yStart = fromBottom ? (centerY - (int) radius) : centerY;
                int yEnd = fromBottom ? (centerY + (int) radius) : (centerY + height);

                for (int y = yStart; y < yEnd; y++) {
                    if (isPositionWithinSphere(centerX, centerY, centerZ, x, y, z, radius, hollow)) {
                        positions.add(new BlockPos(x, y + yOffset, z));
                    }
                }
            }
        }

        return positions;
    }

    private final BlockPos getPlayerPosLocal() {
        if (mc.player == null) {
            return BlockPos.ZERO;
        }
        return new BlockPos(Math.floor(mc.player.getPosX()), Math.floor(mc.player.getPosY()), Math.floor(mc.player.getPosZ()));
    }

    private final double getDistanceOfEntityToBlock(final Entity entity, final BlockPos blockPos) {
        return getDistance(entity.getPosX(), entity.getPosY(), entity.getPosZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }


    private final double getDistance(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n - n4;
        final double n8 = n2 - n5;
        final double n9 = n3 - n6;
        return MathHelper.sqrt(n7 * n7 + n8 * n8 + n9 * n9);
    }

    private static boolean isPositionWithinSphere(int centerX, int centerY, int centerZ, int x, int y, int z, float radius, boolean hollow) {
        double distanceSq = Math.pow(centerX - x, 2) + Math.pow(centerZ - z, 2) + Math.pow(centerY - y, 2);
        return distanceSq < Math.pow(radius, 2) && (!hollow || distanceSq >= Math.pow(radius - 1.0f, 2));
    }

    public int getSlotInInventory(Item item) {
        int slot = -1;

        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING && !this.isSaveEnchanted(itemStack)) {
                slot = adjustSlotNumber(i);
                break;
            }
        }

        return slot;
    }

    private int adjustSlotNumber(int slot) {
        return slot < 9 ? slot + 36 : slot;
    }

    private void reset() {
        this.oldItem = -1;
    }

    @Override
    public void onDisable() {
        reset();
        super.onDisable();
    }
}