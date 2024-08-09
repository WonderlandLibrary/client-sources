package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleAnnotation(name = "AutoTotem", category = Category.COMBAT)
public class AutoTotem extends Module {
    public final NumberSetting health = new NumberSetting("Health", 4.0f, 1.f, 20.f, 0.5F);
    private final BooleanSetting withAbsorption = new BooleanSetting("With Absorption", false);
    private final BooleanSetting checkArmor = new BooleanSetting("Check Armor", false);
    private final BooleanSetting noBallSwitch = new BooleanSetting("No Ball Switch", false);
    private final BooleanSetting totemSwap = new BooleanSetting("Swap Enchanted Totem", false);

    private final BooleanSetting checkFall = new BooleanSetting("Check Fall", true);
    private final NumberSetting fallDistance = new NumberSetting("Fall Distance", 5.0f, 5.0f, 125.0f, 5.0f, checkFall::get);
    private final BooleanSetting checkObsidian = new BooleanSetting("Check Obsidian", false);
    private final NumberSetting radiusObsidian = new NumberSetting("Distance to Obsidian", 6, 1, 8, 1, checkObsidian::get);
    private final BooleanSetting checkExplosion = new BooleanSetting("Check Explosion", false);
    private final NumberSetting radiusExplosion = new NumberSetting("Distance to Explosion", 6, 1, 8, 1, checkExplosion::get);
    public static int swapBack = -1;

    @EventTarget
    public void onUpdate(EventUpdate event) {
        int totem = InventoryUtility.getItemSlot(Items.TOTEM_OF_UNDYING);
        float hp = mc.player.getHealth();
        float healthValue = health.get();

        if (withAbsorption.get()) hp += mc.player.getAbsorptionAmount();
        if (checkArmor.get()) {
            if (mc.player.getTotalArmorValue() <= 17) {
                if (mc.player.getTotalArmorValue() != 0) {
                    healthValue *= 2.5f;
                }
            }
            if (mc.player.getTotalArmorValue() == 0) {
                healthValue *= 4.0f;
            }
        }

        boolean totemInHand = mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING;
        boolean handNotNull = !mc.player.getHeldItemOffhand().isEmpty();
        boolean hasEnchantedTotem = InventoryUtility.hasItemWithEnchantment(mc.player.getHeldItemOffhand());

        if (hp <= healthValue && !totemInHand) {
            if (totemSwap.get()) {
                if (hasEnchantedTotem && totem >= 0) {
                    mc.playerController.windowClick(0, totem, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    if (handNotNull) {
                        mc.playerController.windowClick(0, totem, 0, ClickType.PICKUP, mc.player);
                        if (swapBack == -1) swapBack = totem;
                    }
                } else if (!hasEnchantedTotem && totem >= 0) {
                    mc.playerController.windowClick(0, totem, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    if (handNotNull) {
                        mc.playerController.windowClick(0, totem, 0, ClickType.PICKUP, mc.player);
                        if (swapBack == -1) swapBack = totem;
                    }
                }
            } else {
                if (totem >= 0) {
                    mc.playerController.windowClick(0, totem, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    if (handNotNull) {
                        mc.playerController.windowClick(0, totem, 0, ClickType.PICKUP, mc.player);
                        if (swapBack == -1) swapBack = totem;
                    }
                }
            }
        }

        if (!totemInHand && swapBack >= 0) {
            mc.playerController.windowClick(0, swapBack, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            if (handNotNull) mc.playerController.windowClick(0, swapBack, 0, ClickType.PICKUP, mc.player);
            swapBack = -1;
        }
    }
    private boolean checkFall(float fall) {
        if (!checkFall.get()) {
            return false;
        }

        return mc.player.fallDistance > fall;
    }

    private boolean checkTNT() {
        if (!checkExplosion.get() || isBall()) {
            return false;
        }

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityTNTPrimed && mc.player.getDistance(entity) <= 4) {
                return true;
            }
            if (entity instanceof EntityMinecartTNT && mc.player.getDistance(entity) <= radiusExplosion.get()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCrystal() {
        if (!checkExplosion.get() || isBall()) {
            return false;
        }

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal && mc.player.getDistance(entity) <= radiusExplosion.get()) {
                return true;
            }
            if ((entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItemMainhand().getItem() ==
                    Blocks.OBSIDIAN.getItem(null, null, null).getItem()
                    && mc.player.getDistance(entity) <= radiusExplosion.get()
                    || entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItemOffhand().getItem()
                    == Items.END_CRYSTAL && mc.player.getDistance(entity) <= radiusExplosion.get()) && !(entity instanceof EntityPlayerSP)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBall() {
        if (!noBallSwitch.get()) {
            return false;
        }
        ItemStack stack = mc.player.getHeldItemOffhand();
        return stack.getDisplayName().toLowerCase().contains("шар") || stack.getDisplayName().toLowerCase().contains("голова");
    }

    private boolean checkObsidian() {
        if (!checkObsidian.get() || isBall()) {
            return false;
        }

        BlockPos pos = getSphere(getPlayerPosLocal(), radiusObsidian.get(), 6, false, true, 0).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);
        return pos != null;
    }

    private boolean checkBed() {
        if (!checkExplosion.get() || isBall()) {
            return false;
        }
        BlockPos pos = getSphere(getPlayerPosLocal(), radiusExplosion.get(), 6, false, true, 0).stream().filter(this::IsValidBed).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);
        return pos != null;
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

    public static BlockPos getPlayerPosLocal() {
        if (mc.player == null) {
            return BlockPos.ORIGIN;
        }
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static double getDistance(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n - n4;
        final double n8 = n2 - n5;
        final double n9 = n3 - n6;
        return MathHelper.sqrt(n7 * n7 + n8 * n8 + n9 * n9);
    }

    private boolean IsValidBlockPos(BlockPos pos) {
        IBlockState state = mc.world.getBlockState(pos);
        return state.getBlock() instanceof BlockObsidian;
    }

    private boolean IsValidBed(BlockPos pos) {
        IBlockState state = mc.world.getBlockState(pos);
        return state.getBlock() instanceof BlockBed;
    }

    public static double getDistanceOfEntityToBlock(final Entity entity, final BlockPos blockPos) {
        return getDistance(entity.posX, entity.posY, entity.posZ, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
