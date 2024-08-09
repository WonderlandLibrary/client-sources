/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventSpawnEntity;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
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
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name="AutoTotem", type=Category.Combat)
public class AutoTotem
extends Function {
    private final ModeSetting totemMode = new ModeSetting("\u041c\u043e\u0434", "\u041e\u0431\u044b\u0447\u043d\u044b\u0439", "\u041e\u0431\u044b\u0447\u043d\u044b\u0439", "Funtime");
    private final SliderSetting health = new SliderSetting("\u0417\u0434\u043e\u0440\u043e\u0432\u044c\u0435", 3.5f, 1.0f, 20.0f, 0.05f);
    private final BooleanSetting swapBack = new BooleanSetting("\u0412\u043e\u0437\u0432\u0440\u0430\u0449\u0430\u0442\u044c \u043f\u0440\u0435\u0434\u043c\u0435\u0442", true);
    private final BooleanSetting noBallSwitch = new BooleanSetting("\u041d\u0435 \u0431\u0440\u0430\u0442\u044c \u0435\u0441\u043b\u0438 \u0448\u0430\u0440 \u0432 \u0440\u0443\u043a\u0435", false);
    private final BooleanSetting saveEnchanted = new BooleanSetting("\u0421\u043e\u0445\u0440\u0430\u043d\u044f\u0442\u044c \u0437\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0435", true);
    int nonEnchantedTotems;
    private final ModeListSetting mode = new ModeListSetting("\u0423\u0447\u0438\u0442\u044b\u0432\u0430\u0442\u044c", new BooleanSetting("\u0417\u043e\u043b\u043e\u0442\u044b\u0435 \u0441\u0435\u0440\u0434\u0446\u0430", true), new BooleanSetting("\u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u044b", true), new BooleanSetting("\u042f\u043a\u043e\u0440\u044c", false), new BooleanSetting("\u041f\u0430\u0434\u0435\u043d\u0438\u0435", false));
    int oldItem = -1;
    public boolean isActive;
    StopWatch stopWatch = new StopWatch();
    private Item backItem = Items.AIR;
    private ItemStack backItemStack;
    private int itemInMouse = -1;
    private int totemCount = 0;
    private boolean totemIsUsed;

    public AutoTotem() {
        this.addSettings(this.totemMode, this.health, this.swapBack, this.saveEnchanted, this.noBallSwitch, this.mode);
    }

    @Subscribe
    private void onSpawnEntity(EventSpawnEntity eventSpawnEntity) {
        Entity entity2 = eventSpawnEntity.getEntity();
        if (entity2 instanceof EnderCrystalEntity) {
            EnderCrystalEntity enderCrystalEntity = (EnderCrystalEntity)entity2;
            if (((Boolean)this.mode.getValueByName("\u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u044b").get()).booleanValue() && enderCrystalEntity.getDistance(AutoTotem.mc.player) <= 6.0f) {
                this.swapToTotem();
            }
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        this.totemCount = this.countTotems(false);
        switch ((String)this.totemMode.get()) {
            case "\u041e\u0431\u044b\u0447\u043d\u044b\u0439": {
                boolean bl;
                this.nonEnchantedTotems = (int)IntStream.range(0, 36).mapToObj(AutoTotem::lambda$onUpdate$0).filter(AutoTotem::lambda$onUpdate$1).count();
                int n = this.getSlotInInventory(Items.TOTEM_OF_UNDYING);
                boolean bl2 = bl = !(AutoTotem.mc.player.getHeldItemOffhand().getItem() instanceof AirItem);
                if (this.shouldToSwapTotem()) {
                    if (n == -1 || this.isTotemInHands()) break;
                    InventoryUtil.moveItem(n, 45, bl);
                    if (!bl || this.oldItem != -1) break;
                    this.oldItem = n;
                    break;
                }
                if (this.oldItem == -1 || !((Boolean)this.swapBack.get()).booleanValue()) break;
                InventoryUtil.moveItem(this.oldItem, 45, bl);
                this.oldItem = -1;
                break;
            }
            case "Funtime": {
                if (this.shouldToSwapTotem()) {
                    if (this.itemIsHand(Items.TOTEM_OF_UNDYING)) {
                        return;
                    }
                    this.swapToTotem();
                }
                this.swapBack();
            }
        }
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        SEntityStatusPacket sEntityStatusPacket;
        IPacket<?> iPacket;
        if (eventPacket.isReceive() && (iPacket = eventPacket.getPacket()) instanceof SEntityStatusPacket && (sEntityStatusPacket = (SEntityStatusPacket)iPacket).getOpCode() == 35 && sEntityStatusPacket.getEntity(AutoTotem.mc.world) == AutoTotem.mc.player) {
            this.totemIsUsed = true;
        }
    }

    private void swapBack() {
        if (this.stopWatch.isReached(400L) && this.itemIsBack()) {
            this.itemInMouse = -1;
            this.backItem = Items.AIR;
            this.backItemStack = null;
            this.stopWatch.reset();
        }
    }

    private boolean itemIsBack() {
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING && this.itemInMouse != -1 && this.backItem != Items.AIR) {
            ItemStack itemStack = AutoTotem.mc.player.container.getSlot(this.itemInMouse).getStack();
            boolean bl = itemStack != ItemStack.EMPTY && !ItemStack.areItemStacksEqual(itemStack, this.backItemStack);
            int n = this.findItemSlotIndex(this.backItemStack, this.backItem);
            if (n < 9 && n != -1) {
                n += 36;
            }
            int n2 = AutoTotem.mc.player.container.windowId;
            if (AutoTotem.mc.player.inventory.getItemStack().getItem() != Items.AIR) {
                AutoTotem.mc.playerController.windowClick(n2, 45, 0, ClickType.PICKUP, AutoTotem.mc.player);
                this.backItemInMouse();
                return true;
            }
            if (n == -1) {
                return true;
            }
            AutoTotem.mc.playerController.windowClick(n2, n, 0, ClickType.PICKUP, AutoTotem.mc.player);
            AutoTotem.mc.playerController.windowClick(n2, 45, 0, ClickType.PICKUP, AutoTotem.mc.player);
            if (this.itemInMouse != -1) {
                if (!bl) {
                    AutoTotem.mc.playerController.windowClick(n2, this.itemInMouse, 0, ClickType.PICKUP, AutoTotem.mc.player);
                } else {
                    int n3 = AutoTotem.getEmptySlot(false);
                    if (n3 != -1) {
                        AutoTotem.mc.playerController.windowClick(n2, n3, 0, ClickType.PICKUP, AutoTotem.mc.player);
                    }
                }
            }
        }
        return false;
    }

    public static int getEmptySlot(boolean bl) {
        for (int i = bl ? 0 : 9; i < (bl ? 9 : 45); ++i) {
            if (!AutoTotem.mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
            return i;
        }
        return 1;
    }

    public int findItemSlotIndex(ItemStack itemStack, Item item) {
        if (itemStack == null) {
            return 1;
        }
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack2 = AutoTotem.mc.player.inventory.getStackInSlot(i);
            if (!ItemStack.areItemStacksEqual(itemStack2, itemStack) || itemStack2.getItem() != item) continue;
            return i;
        }
        return 1;
    }

    public boolean itemIsHand(Item item) {
        for (Hand hand : Hand.values()) {
            if (AutoTotem.mc.player.getHeldItem(hand).getItem() != item) continue;
            return false;
        }
        return true;
    }

    private void swapToTotem() {
        int n = this.getSlotInInventory(Items.TOTEM_OF_UNDYING);
        this.stopWatch.reset();
        Item item = AutoTotem.mc.player.getHeldItemOffhand().getItem();
        if (item == Items.TOTEM_OF_UNDYING) {
            return;
        }
        if (n == -1 && !AutoTotem.isCurrentItem(Items.TOTEM_OF_UNDYING)) {
            return;
        }
        if (this.itemInMouse == -1) {
            this.itemInMouse = n;
            this.backItem = item;
            this.backItemStack = AutoTotem.mc.player.getHeldItemOffhand().copy();
        }
        AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.container.windowId, n, 1, ClickType.PICKUP, AutoTotem.mc.player);
        AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.container.windowId, 45, 1, ClickType.PICKUP, AutoTotem.mc.player);
        if (this.totemCount > 1 && this.totemIsUsed) {
            this.backItemInMouse();
            this.totemIsUsed = false;
        }
        this.backItemInMouse();
    }

    public int countTotems(boolean bl) {
        long l = 0L;
        int n = AutoTotem.mc.player.inventory.getSizeInventory();
        for (int i = 0; i < n; ++i) {
            ItemStack itemStack = AutoTotem.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.TOTEM_OF_UNDYING || !bl && itemStack.isEnchanted()) continue;
            ++l;
        }
        return (int)l;
    }

    private void backItemInMouse() {
        if (this.itemInMouse != -1) {
            AutoTotem.mc.playerController.windowClick(AutoTotem.mc.player.container.windowId, this.itemInMouse, 0, ClickType.PICKUP, AutoTotem.mc.player);
        }
    }

    public static boolean isCurrentItem(Item item) {
        return AutoTotem.mc.player.inventory.getItemStack().getItem() == item;
    }

    private boolean isTotemInHands() {
        Hand[] handArray;
        for (Hand hand : handArray = Hand.values()) {
            ItemStack itemStack = AutoTotem.mc.player.getHeldItem(hand);
            if (itemStack.getItem() != Items.TOTEM_OF_UNDYING || this.isSaveEnchanted(itemStack)) continue;
            return false;
        }
        return true;
    }

    private boolean isSaveEnchanted(ItemStack itemStack) {
        return (Boolean)this.saveEnchanted.get() != false && itemStack.isEnchanted() && this.nonEnchantedTotems > 0;
    }

    private boolean shouldToSwapTotem() {
        float f = AutoTotem.mc.player.isPotionActive(Effects.ABSORPTION) ? AutoTotem.mc.player.getAbsorptionAmount() : 0.0f;
        float f2 = AutoTotem.mc.player.getHealth();
        if (((Boolean)this.mode.getValueByName("\u0417\u043e\u043b\u043e\u0442\u044b\u0435 \u0441\u0435\u0440\u0434\u0446\u0430").get()).booleanValue()) {
            f2 += f;
        }
        if (!this.isOffhandItemBall() && this.isInDangerousSituation()) {
            return false;
        }
        return f2 <= ((Float)this.health.get()).floatValue();
    }

    private boolean isInDangerousSituation() {
        return this.checkCrystal() || this.checkAnchor() || this.checkFall();
    }

    private boolean checkFall() {
        if (!((Boolean)this.mode.getValueByName("\u041f\u0430\u0434\u0435\u043d\u0438\u0435").get()).booleanValue()) {
            return true;
        }
        if (AutoTotem.mc.player.isInWater()) {
            return true;
        }
        if (AutoTotem.mc.player.isElytraFlying()) {
            return true;
        }
        return AutoTotem.mc.player.fallDistance > 10.0f;
    }

    private boolean checkAnchor() {
        if (!((Boolean)this.mode.getValueByName("\u042f\u043a\u043e\u0440\u044c").get()).booleanValue()) {
            return true;
        }
        return this.getBlock(6.0f, Blocks.RESPAWN_ANCHOR) != null;
    }

    private boolean checkCrystal() {
        if (!((Boolean)this.mode.getValueByName("\u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u044b").get()).booleanValue()) {
            return true;
        }
        for (Entity entity2 : AutoTotem.mc.world.getAllEntities()) {
            if (!this.isDangerousEntityNearPlayer(entity2)) continue;
            return false;
        }
        return true;
    }

    private boolean isOffhandItemBall() {
        boolean bl;
        boolean bl2 = bl = (Boolean)this.mode.getValueByName("\u041f\u0430\u0434\u0435\u043d\u0438\u0435").get() != false && AutoTotem.mc.player.fallDistance > 5.0f;
        if (bl) {
            return true;
        }
        return (Boolean)this.noBallSwitch.get() != false && AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.PLAYER_HEAD;
    }

    private boolean isDangerousEntityNearPlayer(Entity entity2) {
        return (entity2 instanceof TNTEntity || entity2 instanceof TNTMinecartEntity) && AutoTotem.mc.player.getDistance(entity2) <= 6.0f;
    }

    private final BlockPos getBlock(float f, Block block) {
        return this.getSphere(this.getPlayerPosLocal(), f, 6, false, true, 1).stream().filter(arg_0 -> AutoTotem.lambda$getBlock$2(block, arg_0)).min(Comparator.comparing(this::lambda$getBlock$3)).orElse(null);
    }

    private final List<BlockPos> getSphere(BlockPos blockPos, float f, int n, boolean bl, boolean bl2, int n2) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        int n3 = blockPos.getX();
        int n4 = blockPos.getY();
        int n5 = blockPos.getZ();
        int n6 = n3 - (int)f;
        while ((float)n6 <= (float)n3 + f) {
            int n7 = n5 - (int)f;
            while ((float)n7 <= (float)n5 + f) {
                int n8 = bl2 ? n4 - (int)f : n4;
                int n9 = bl2 ? n4 + (int)f : n4 + n;
                for (int i = n8; i < n9; ++i) {
                    if (!AutoTotem.isPositionWithinSphere(n3, n4, n5, n6, i, n7, f, bl)) continue;
                    arrayList.add(new BlockPos(n6, i + n2, n7));
                }
                ++n7;
            }
            ++n6;
        }
        return arrayList;
    }

    private final BlockPos getPlayerPosLocal() {
        if (AutoTotem.mc.player == null) {
            return BlockPos.ZERO;
        }
        return new BlockPos(Math.floor(AutoTotem.mc.player.getPosX()), Math.floor(AutoTotem.mc.player.getPosY()), Math.floor(AutoTotem.mc.player.getPosZ()));
    }

    private final double getDistanceOfEntityToBlock(Entity entity2, BlockPos blockPos) {
        return this.getDistance(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private final double getDistance(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        return MathHelper.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
    }

    private static boolean isPositionWithinSphere(int n, int n2, int n3, int n4, int n5, int n6, float f, boolean bl) {
        double d = Math.pow(n - n4, 2.0) + Math.pow(n3 - n6, 2.0) + Math.pow(n2 - n5, 2.0);
        return d < Math.pow(f, 2.0) && (!bl || d >= Math.pow(f - 1.0f, 2.0));
    }

    public int getSlotInInventory(Item item) {
        int n = -1;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = AutoTotem.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.TOTEM_OF_UNDYING || this.isSaveEnchanted(itemStack)) continue;
            n = this.adjustSlotNumber(i);
            break;
        }
        return n;
    }

    private int adjustSlotNumber(int n) {
        return n < 9 ? n + 36 : n;
    }

    private void reset() {
        this.oldItem = -1;
    }

    @Override
    public void onDisable() {
        this.reset();
        super.onDisable();
    }

    private Double lambda$getBlock$3(BlockPos blockPos) {
        return this.getDistanceOfEntityToBlock(AutoTotem.mc.player, blockPos);
    }

    private static boolean lambda$getBlock$2(Block block, BlockPos blockPos) {
        return AutoTotem.mc.world.getBlockState(blockPos).getBlock() == block;
    }

    private static boolean lambda$onUpdate$1(ItemStack itemStack) {
        return itemStack.getItem() == Items.TOTEM_OF_UNDYING && !itemStack.isEnchanted();
    }

    private static ItemStack lambda$onUpdate$0(int n) {
        return AutoTotem.mc.player.inventory.getStackInSlot(n);
    }
}

