package mathax.client.utils.Jebus;

import mathax.client.utils.misc.Names;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import static mathax.client.MatHax.mc;

import java.util.UUID;

public class Interactions {

    public static int lastSlot = -1;

    // mining stuff
    public static class MineInstance {
        private double progress = 0;
        private BlockPos pos;
        private boolean started;

        public MineInstance(BlockPos bp) {
            this.progress = 0;
            this.pos = bp;
            this.started = false;
        }

        public BlockPos getPos() {return this.pos;}

        public void init() {
            if (this.started) return;
            FindItemResult pick = findPick();
            if (!pick.found()) return;
            setSlot(pick.slot(), false);
            PacketManager.startPacketMine(this.pos, false, false);
            this.started = true;
        }

        public void tick() {
            FindItemResult pick = findPick();
            if (!pick.found()) return;
            this.progress += getBreakDelta(pick.slot(), mc.world.getBlockState(this.pos));
        }

        public void finish() {
            FindItemResult pick = findPick();
            if (!pick.found()) return;
            setSlot(pick.slot(), false);
            PacketManager.finishPacketMine(this.pos, true, false);
        }

        public boolean isReady() {
            return this.progress >= 1;
        }

        public boolean isValid() {
            if (BlockHelper.isAir(this.pos)) return false;
            return !(BlockHelper.distanceTo(pos) > 4.8);
        }

    }

    public static double getBlockBreakingSpeed(int slot, BlockState block) {
        double speed = mc.player.getInventory().main.get(slot).getMiningSpeedMultiplier(block);

        if (speed > 1) {
            ItemStack tool = mc.player.getInventory().getStack(slot);
            int eff = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, tool);
            if (eff > 0 && !tool.isEmpty()) speed += eff * eff + 1;
        }

        if (StatusEffectUtil.hasHaste(mc.player)) speed *= 1 + (StatusEffectUtil.getHasteAmplifier(mc.player) + 1) * 0.2F;

        if (mc.player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            float k = switch (mc.player.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) {
                case 0 -> 0.3F;
                case 1 -> 0.09F;
                case 2 -> 0.0027F;
                case 3 -> 8.1E-4F;
                default -> 8.1E-4F;
            };
            speed *= k;
        }

        if (mc.player.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(mc.player)) speed /= 5.0F;
        if (!mc.player.isOnGround()) speed /= 5.0F;
        return speed;
    }

    public static double getBreakDelta(int slot, BlockState state) {
        float hardness = state.getHardness(null, null);
        if (hardness == -1) return 0;
        else {
            return getBlockBreakingSpeed(slot, state) / hardness / (!state.isToolRequired() || mc.player.getInventory().main.get(slot).isSuitableFor(state) ? 30 : 100);
        }
    }

    public static void mine(BlockPos pos) {
        mc.interactionManager.updateBlockBreakingProgress(pos, Direction.UP);
        PacketManager.swingHand(false);
    }


    public static void mine(BlockPos pos, FindItemResult item) {
        if (pos == null || !item.found() || !item.isHotbar()) return;
        setSlot(item.slot(), false);
        mine(pos);
    }


    // Setting velocity
    public static void setHVelocity(double x, double z) {
        mc.player.setVelocity(x, mc.player.getVelocity().getY(), z);
    }

    public static void setYVelocity(double y) {
        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, y, velocity.z);
    }


    // Misc item methods
    public static int getSlot() {
        return mc.player.getInventory().selectedSlot;
    }
    public static Item getMainHandItem() { return mc.player.getInventory().getMainHandStack().getItem(); }
    public static Item getOffHandItem() {return getItemFromSlot(45);}
    public static String getItemName(Item item) {
        return Names.get(item);
    }

    public static void transfer(int from, int to, boolean hotbar) {
        if (from == -1 || to == -1) return;
        if (hotbar) InvUtils.move().from(from).toHotbar(to);
        else InvUtils.move().from(from).to(to);
    }

    public static String getCommonName(Item item) {
        if (item instanceof BedItem) return "Beds";
        if (item instanceof ExperienceBottleItem) return "XP";
        if (item instanceof EndCrystalItem) return "Crystals";
        if (item instanceof EnchantedGoldenAppleItem) return "EGaps";
        if (item instanceof EnderPearlItem) return "Pearls";
        if (item.equals(Items.TOTEM_OF_UNDYING)) return "Totems";
        if (Block.getBlockFromItem(item) == Blocks.OBSIDIAN) return "Obby";
        if (Block.getBlockFromItem(item) instanceof EnderChestBlock) return "Echests";
        return Names.get(item);
    }

    public static boolean isHolding(Item item) {return getMainHandItem().equals(item);}
    public static boolean isHolding(FindItemResult itemResult) {return isHolding(getItemFromSlot(itemResult.slot()));}
    public static boolean isHoldingBed() {return mc.player.getMainHandStack().getItem() instanceof BedItem;}

    // "Proxy" methods to check CombatHelper stuff on mc.player
    public static boolean isInHole() {return CombatHelper.isInHole(mc.player);}
    public static boolean isBurrowed() {return CombatHelper.isBurrowed(mc.player);}
    public static boolean isCitied() {return CombatHelper.isCitied(mc.player);}
    public static boolean isWebbed() {return CombatHelper.isWebbed(mc.player);}
    public static boolean isMoving() {return CombatHelper.isMoving(mc.player);}
    public static boolean isSelfTrapped() {return CombatHelper.isSelfTrapped(mc.player);}
    public static boolean isTopTrapped() {return CombatHelper.isTopTrapped(mc.player);}

    // Condensed item count methods
    public static int cryCount() {
        return InvUtils.find(Items.END_CRYSTAL).count();
    }
    public static int gapCount() {
        return InvUtils.find(Items.ENCHANTED_GOLDEN_APPLE).count();
    }
    public static int xpCount() {
        return InvUtils.find(Items.EXPERIENCE_BOTTLE).count();
    }
    public static int totemCount() {
        return InvUtils.find(Items.TOTEM_OF_UNDYING).count();
    }
    public static int bedCount() {
        return InvUtils.find(itemStack -> itemStack.getItem() instanceof BedItem).count();
    }

    // Condensed find item methods
    public static FindItemResult findShulker(boolean inventory) {
        if (inventory) return InvUtils.find(itemStack -> Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock);
        return InvUtils.findInHotbar(itemStack -> Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock);
    }
    public static FindItemResult findPick() {return InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof PickaxeItem);}
    public static FindItemResult findSword() {return InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof SwordItem);}
    public static FindItemResult findAxe() {return InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof AxeItem);}
    public static FindItemResult findAnvil() {return InvUtils.findInHotbar(itemStack -> Block.getBlockFromItem(itemStack.getItem()) instanceof AnvilBlock);}
    public static FindItemResult findButton() {return InvUtils.findInHotbar(itemStack -> Block.getBlockFromItem(itemStack.getItem()) instanceof AbstractPressurePlateBlock || Block.getBlockFromItem(itemStack.getItem()) instanceof ButtonBlock);}
    public static FindItemResult findChorus() {
        return InvUtils.findInHotbar(Items.CHORUS_FRUIT);
    }
    public static FindItemResult findEgap() {return InvUtils.findInHotbar(Items.ENCHANTED_GOLDEN_APPLE);}
    public static FindItemResult findObby() {
        return InvUtils.findInHotbar(Blocks.OBSIDIAN.asItem());
    }
    public static FindItemResult findEchest() {
        return InvUtils.findInHotbar(Blocks.ENDER_CHEST.asItem());
    }
    public static FindItemResult findCraftTable() {
        return InvUtils.findInHotbar(Blocks.CRAFTING_TABLE.asItem());
    }
    public static FindItemResult findXP() {
        return InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);
    }
    public static FindItemResult findXPinAll() {
        return InvUtils.find(Items.EXPERIENCE_BOTTLE);
    }
    public static FindItemResult findBed() {return InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof BedItem);}
    public static FindItemResult findBedInAll() {return InvUtils.find(itemStack -> itemStack.getItem() instanceof BedItem);}
    public static FindItemResult findWool() {return InvUtils.find(itemStack -> BlockHelper.wools.contains(itemStack.getItem()));}
    public static FindItemResult findPlanks() {return InvUtils.find(itemStack -> BlockHelper.planks.contains(itemStack.getItem()));}

    // misc slot stuff
    public static void setSlot(int slot, boolean packet) {
        if (slot < 0) return;
        lastSlot = mc.player.getInventory().selectedSlot;
        if (packet) {
            PacketManager.updateSlot(slot);
        } else {
            InvUtils.swap(slot, false);
        }
    }

    public static void swapBack() {
        setSlot(lastSlot, false);
    }

    public static void windowClick(ScreenHandler handler, int slot, SlotActionType action, int clickData) {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        mc.interactionManager.clickSlot(handler.syncId, slot, clickData, action, mc.player);
    }

    public static boolean isCrafting() {
        return mc.player.currentScreenHandler instanceof CraftingScreenHandler;
    }


    public static Integer getEmptySlots() {
        int emptySlots = 0;
        for (int i = 0; i < 36; i++) if (isSlotEmpty(i)) emptySlots++;
        return emptySlots;
    }

    public static Integer getEmptySlot() {
        for (int i = 0; i < 36; i++) if (isSlotEmpty(i)) return i;
        return -1;
    }

    public static boolean isInventoryFull() {
        for (int i = 0; i < 36; i++) if (isSlotEmpty(i)) return false;
        return true;
    }

    public static boolean isSlotEmpty(Integer slot) {
        ItemStack itemStack = getStackFromSlot(slot);
        if (itemStack == null) return true;
        return itemStack.getItem() instanceof AirBlockItem;
    }

    public static ItemStack getStackFromSlot(Integer slot) {
        if (slot == -1) return null;
        return mc.player.getInventory().getStack(slot);
    }

    public static Item getItemFromSlot(Integer slot) {
        if (slot == -1) return null;
        if (slot == 45) return mc.player.getOffHandStack().getItem();
        return mc.player.getInventory().getStack(slot).getItem();
    }

    // Armor stuff
    public static boolean checkThreshold(ItemStack i, double threshold) {
        return getDamage(i) <= threshold;
    }
    public static double getDamage(ItemStack i) { return (((double) (i.getMaxDamage() - i.getDamage()) / i.getMaxDamage()) * 100); }
    public static ItemStack getArmor(int slot) {return mc.player.getInventory().armor.get(slot);}
    public static Item getArmorItem(int slot) {return getArmor(slot).getItem();}

    public static boolean isInElytra() {return getArmorItem(2) == Items.ELYTRA && mc.player.isFallFlying();}


    public static boolean isHelm(ItemStack itemStack) {
        if (itemStack == null) return false;
        Item i = itemStack.getItem();
        return i == Items.NETHERITE_HELMET || i == Items.DIAMOND_HELMET || i == Items.GOLDEN_HELMET || i == Items.IRON_HELMET || i == Items.CHAINMAIL_HELMET || i == Items.LEATHER_HELMET;
    }

    public static boolean isChest(ItemStack itemStack) {
        if (itemStack == null) return false;
        Item i = itemStack.getItem();
        return i == Items.NETHERITE_CHESTPLATE || i == Items.DIAMOND_CHESTPLATE || i == Items.GOLDEN_CHESTPLATE || i == Items.IRON_CHESTPLATE || i == Items.CHAINMAIL_CHESTPLATE || i == Items.LEATHER_CHESTPLATE;
    }

    public static boolean isLegs(ItemStack itemStack) {
        if (itemStack == null) return false;
        Item i = itemStack.getItem();
        return i == Items.NETHERITE_LEGGINGS || i == Items.DIAMOND_LEGGINGS || i == Items.GOLDEN_LEGGINGS || i == Items.IRON_LEGGINGS || i == Items.CHAINMAIL_LEGGINGS || i == Items.LEATHER_LEGGINGS;
    }

    public static boolean isBoots(ItemStack itemStack) {
        if (itemStack == null) return false;
        Item i = itemStack.getItem();
        return i == Items.NETHERITE_BOOTS || i == Items.DIAMOND_BOOTS || i == Items.GOLDEN_BOOTS || i == Items.IRON_BOOTS || i == Items.CHAINMAIL_BOOTS || i == Items.LEATHER_BOOTS;
    }

    // Player parsing
    public static PlayerEntity getPlayerByUUID(String uuid) {
        return mc.world.getPlayerByUuid(UUID.fromString(uuid));
    }

    public static PlayerEntity getPlayerByName(String name) {
        PlayerEntity p = null;
        for (PlayerEntity entity : mc.world.getPlayers()) {
            if (entity.getEntityName().equalsIgnoreCase(name)) {
                p = entity;
                break;
            }
        }
        return p;
    }

    public static String getCurrentIGN() {
        return mc.getSession().getUsername();
    }

    public static String getIGNSafe() {
        return MinecraftClient.getInstance().getSession().getUsername();
    }

    public static boolean isBetaUser() {
        return false;
    }



    public static String getCurrentPing() {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (playerListEntry != null) return Integer.toString(playerListEntry.getLatency());
        return "0";
    }

    public static Integer getPing() {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (playerListEntry != null) playerListEntry.getLatency();
        return 0;
    }

    public static UUID getOwnerUUID(LivingEntity livingEntity) {
        if (livingEntity instanceof TameableEntity tameableEntity) if (tameableEntity.isTamed()) return tameableEntity.getOwnerUuid();
        if (livingEntity instanceof AbstractHorseEntity horseBaseEntity) return horseBaseEntity.getOwnerUuid();
        return null;
    }

    public boolean doesPlayerOwn(Entity entity) {
        return doesPlayerOwn(entity, mc.player);
    }

    public boolean doesPlayerOwn(Entity entity, PlayerEntity playerEntity) {
        if (entity instanceof LivingEntity) return getOwnerUUID((LivingEntity)entity) != null && getOwnerUUID((LivingEntity)entity).toString().equals(playerEntity.getUuid().toString());
        return false;
    }

    public static boolean isTeamed(PlayerEntity player1, PlayerEntity player2, boolean checkArmor) {
        String all = "0123456789abcdef";
        for (int i = 0; i < all.length(); i++) {
            char s = all.charAt(i);
            if (player1.getDisplayName().getString().toLowerCase().startsWith("§" + s) && player2.getDisplayName().getString().toLowerCase().startsWith("§" + s)) return true;
        }

        if (checkArmor) {
            ItemStack p1armor = mc.player.getInventory().getArmorStack(3);
            ItemStack p2armor = player2.getInventory().getArmorStack(3);
            if (p1armor == null || p2armor == null) return false;
            if (!(p1armor.getItem() instanceof ArmorItem p1a) || !(p2armor.getItem() instanceof ArmorItem p2a)) return false;
            if (p1a.getMaterial() == ArmorMaterials.LEATHER && p2a.getMaterial() == ArmorMaterials.LEATHER) {
                int colorP1 = ((DyeableArmorItem) p1a).getColor(p1armor);
                int colorP2 = ((DyeableArmorItem) p2a).getColor(p2armor);
                return colorP1 == colorP2;
            }
        }
        return false;
    }
}
