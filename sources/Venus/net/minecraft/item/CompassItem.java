/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompassItem
extends Item
implements IVanishable {
    private static final Logger field_234666_a_ = LogManager.getLogger();

    public CompassItem(Item.Properties properties) {
        super(properties);
    }

    public static boolean func_234670_d_(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        return compoundNBT != null && (compoundNBT.contains("LodestoneDimension") || compoundNBT.contains("LodestonePos"));
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return CompassItem.func_234670_d_(itemStack) || super.hasEffect(itemStack);
    }

    public static Optional<RegistryKey<World>> func_234667_a_(CompoundNBT compoundNBT) {
        return World.CODEC.parse(NBTDynamicOps.INSTANCE, compoundNBT.get("LodestoneDimension")).result();
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity2, int n, boolean bl) {
        if (!world.isRemote && CompassItem.func_234670_d_(itemStack)) {
            CompoundNBT compoundNBT = itemStack.getOrCreateTag();
            if (compoundNBT.contains("LodestoneTracked") && !compoundNBT.getBoolean("LodestoneTracked")) {
                return;
            }
            Optional<RegistryKey<World>> optional = CompassItem.func_234667_a_(compoundNBT);
            if (optional.isPresent() && optional.get() == world.getDimensionKey() && compoundNBT.contains("LodestonePos") && !((ServerWorld)world).getPointOfInterestManager().hasTypeAtPosition(PointOfInterestType.LODESTONE, NBTUtil.readBlockPos(compoundNBT.getCompound("LodestonePos")))) {
                compoundNBT.remove("LodestonePos");
            }
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        boolean bl;
        BlockPos blockPos = itemUseContext.getPos();
        World world = itemUseContext.getWorld();
        if (!world.getBlockState(blockPos).isIn(Blocks.LODESTONE)) {
            return super.onItemUse(itemUseContext);
        }
        world.playSound(null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0f, 1.0f);
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        ItemStack itemStack = itemUseContext.getItem();
        boolean bl2 = bl = !playerEntity.abilities.isCreativeMode && itemStack.getCount() == 1;
        if (bl) {
            this.func_234669_a_(world.getDimensionKey(), blockPos, itemStack.getOrCreateTag());
        } else {
            ItemStack itemStack2 = new ItemStack(Items.COMPASS, 1);
            CompoundNBT compoundNBT = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundNBT();
            itemStack2.setTag(compoundNBT);
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            this.func_234669_a_(world.getDimensionKey(), blockPos, compoundNBT);
            if (!playerEntity.inventory.addItemStackToInventory(itemStack2)) {
                playerEntity.dropItem(itemStack2, true);
            }
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    private void func_234669_a_(RegistryKey<World> registryKey, BlockPos blockPos, CompoundNBT compoundNBT) {
        compoundNBT.put("LodestonePos", NBTUtil.writeBlockPos(blockPos));
        World.CODEC.encodeStart(NBTDynamicOps.INSTANCE, registryKey).resultOrPartial(field_234666_a_::error).ifPresent(arg_0 -> CompassItem.lambda$func_234669_a_$0(compoundNBT, arg_0));
        compoundNBT.putBoolean("LodestoneTracked", false);
    }

    @Override
    public String getTranslationKey(ItemStack itemStack) {
        return CompassItem.func_234670_d_(itemStack) ? "item.minecraft.lodestone_compass" : super.getTranslationKey(itemStack);
    }

    private static void lambda$func_234669_a_$0(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("LodestoneDimension", iNBT);
    }
}

