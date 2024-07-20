/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventRender2D;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AutoApple;
import ru.govno.client.module.modules.Crosshair;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.PlayerHelper;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class OffHand
extends Module {
    public static TimerHelper timerDelay = new TimerHelper();
    public static TimerHelper timer3 = new TimerHelper();
    public static TimerHelper timer4 = new TimerHelper();
    public static boolean doTotem;
    public static boolean doBackSlot;
    public static boolean totemBackward;
    public static boolean totemTaken;
    public static boolean callNotSave;
    public static boolean fall;
    public static boolean clientSwap;
    public static Item saveSlot;
    public static Item oldSlot;
    public static Item prevOldSlot;
    public static OffHand get;
    private final Settings CanHotbarSwap;
    private final Settings TotemBackward;
    private final Settings PutBecauseLack;
    public static AnimationUtils scaleAnim;
    public static AnimationUtils popAnim;

    public OffHand() {
        super("OffHand", 0, Module.Category.PLAYER);
        get = this;
        this.CanHotbarSwap = new Settings("CanHotbarSwap", true, (Module)this);
        this.settings.add(this.CanHotbarSwap);
        this.PutBecauseLack = new Settings("PutBecauseLack", false, (Module)this);
        this.settings.add(this.PutBecauseLack);
        this.TotemBackward = new Settings("TotemBackward", true, (Module)this);
        this.settings.add(this.TotemBackward);
    }

    private void updateEmptyHandFix() {
        if (this.PutBecauseLack.bValue) {
            return;
        }
        ItemStack offStack = Minecraft.player.getHeldItemOffhand();
        if (offStack.getItem() instanceof ItemAir && !totemTaken && !doBackSlot && oldSlot != null && this.getSlotByItem(prevOldSlot) == -1) {
            List<Item> samples = Arrays.asList(Items.POTIONITEM, Items.BOW, Items.SKULL, Items.SHIELD, Items.END_CRYSTAL, Items.GOLDEN_APPLE);
            if ((samples = samples.stream().filter(sample -> !(sample == prevOldSlot || sample == Items.SHIELD && Minecraft.player.getCooldownTracker().getCooldown(Items.SHIELD, 0.0f) > 0.0f || sample == Items.GOLDEN_APPLE && (PlayerHelper.get.actived && PlayerHelper.checkApple || Minecraft.player.getCooldownTracker().getCooldown(Items.GOLDEN_APPLE, 0.0f) > 0.0f) || sample == Items.POTIONITEM && (Minecraft.player.getActivePotionEffect(MobEffects.REGENERATION) == null || Minecraft.player.getAbsorptionAmount() < 1.0f))).collect(Collectors.toList())).isEmpty()) {
                return;
            }
            if ((samples = samples.stream().filter(sample -> this.getSlotByItem((Item)sample) != -1).collect(Collectors.toList())).isEmpty()) {
                return;
            }
            Collections.reverse(samples);
            oldSlot = samples.get(0);
            doBackSlot = true;
        }
    }

    private boolean haveShar() {
        return this.stackIsBall(Minecraft.player.getHeldItemOffhand()) || this.stackIsBall(Minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
    }

    private float smartTriggerHP() {
        float hp = 5.0f;
        EntityPlayer p = OffHand.getMe();
        float absorbDT = 1.0f;
        for (int i = 0; i < 4; ++i) {
            if (BlockUtils.isArmor(p, BlockUtils.armorElementByInt(i))) continue;
            hp += 1.5f;
            absorbDT += 0.5f;
        }
        if (p.getActivePotionEffect(Potion.getPotionById(10)) != null) {
            hp -= 0.5f;
        }
        if (p.isHandActive() && p.getActiveItemStack().getItem() instanceof ItemAppleGold && p.getItemInUseMaxCount() > 25) {
            hp -= 1.0f;
        }
        if (p.getAbsorptionAmount() > 0.0f) {
            hp -= p.getAbsorptionAmount() / MathUtils.clamp(absorbDT, 1.0f, 2.0f);
        }
        if (p.inventory.armorItemInSlot(2).getItem() instanceof ItemElytra) {
            hp += 3.0f;
        }
        return MathUtils.clamp(hp, 2.0f, 20.0f);
    }

    public static boolean crystalWarn(float isInRange) {
        CopyOnWriteArrayList<EntityEnderCrystal> enderCrystals = new CopyOnWriteArrayList<EntityEnderCrystal>();
        if (OffHand.mc.world != null) {
            for (Entity e : OffHand.mc.world.getLoadedEntityList()) {
                if (e == null || !(e instanceof EntityEnderCrystal) || !(OffHand.getMe().getSmartDistanceToAABB(RotationUtil.getLookRots(OffHand.getMe(), e), e) < (double)isInRange)) continue;
                enderCrystals.add((EntityEnderCrystal)e);
            }
        }
        int balls = 0;
        for (int i = 0; i < 4; ++i) {
            if (!BlockUtils.isArmor(OffHand.getMe(), BlockUtils.armorElementByInt(i))) continue;
            ++balls;
        }
        boolean isFullArmor = balls == 4;
        for (EntityEnderCrystal crystal : enderCrystals) {
            boolean pardon;
            if (crystal == null || OffHand.getMe().getSmartDistanceToAABB(RotationUtil.getLookRots(OffHand.getMe(), crystal), crystal) >= (double)isInRange || !BlockUtils.canPosBeSeenEntity(new Vec3d(crystal.posX, crystal.posY - (double)0.15f, crystal.posZ), (Entity)OffHand.getMe(), BlockUtils.bodyElement.FEET) && (isFullArmor || !BlockUtils.canPosBeSeenEntity(new Vec3d(crystal.posX, crystal.posY - (double)0.15f, crystal.posZ), (Entity)OffHand.getMe(), BlockUtils.bodyElement.HEAD))) continue;
            float rangePardon = 9.0f - (float)balls * 2.0f;
            boolean bl = pardon = crystal.posY >= Minecraft.player.posY + 0.8 && Minecraft.player.getDistanceToEntity(crystal) > rangePardon || !BlockUtils.canPosBeSeenEntity(BlockUtils.getEntityBlockPos(crystal), (Entity)OffHand.getMe(), BlockUtils.bodyElement.LEGS) && !BlockUtils.canPosBeSeenEntity(BlockUtils.getEntityBlockPos(crystal), (Entity)OffHand.getMe(), BlockUtils.bodyElement.FEET) && isFullArmor;
            if (OffHand.getMe().getHealth() + OffHand.getMe().getAbsorptionAmount() > 24.0f && isFullArmor) {
                pardon = true;
            }
            return !pardon;
        }
        enderCrystals.clear();
        return false;
    }

    private boolean tntWarn(float isInRange) {
        CopyOnWriteArrayList<Entity> tntS = new CopyOnWriteArrayList<Entity>();
        if (OffHand.mc.world != null) {
            for (Entity e : OffHand.mc.world.getLoadedEntityList()) {
                if (e == null || !(e instanceof EntityTNTPrimed) && !(e instanceof EntityMinecartTNT) || !(OffHand.getMe().getDistanceToEntity(e) < isInRange)) continue;
                tntS.add(e);
            }
        }
        for (Entity tnt : tntS) {
            if (tnt == null || OffHand.getMe().getDistanceToEntity(tnt) >= isInRange) continue;
            return BlockUtils.canPosBeSeenEntity(new Vec3d(tnt.posX, tnt.posY, tnt.posZ), (Entity)OffHand.getMe(), BlockUtils.bodyElement.LEGS);
        }
        return false;
    }

    private double getDistanceToTileEntityAtEntity(Entity entity, TileEntity tileEtity) {
        return entity.getDistanceToBlockPos(tileEtity.getPos());
    }

    private boolean bedWarn(float isInRange) {
        if (Minecraft.player.dimension != 0) {
            CopyOnWriteArrayList<TileEntityBed> bedTiles = new CopyOnWriteArrayList<TileEntityBed>();
            if (OffHand.mc.world != null && Minecraft.player.dimension != 0) {
                for (TileEntity t : OffHand.mc.world.getLoadedTileEntityList()) {
                    if (t == null || !(t instanceof TileEntityBed) || !(this.getDistanceToTileEntityAtEntity(OffHand.getMe(), t) < (double)isInRange)) continue;
                    bedTiles.add((TileEntityBed)t);
                }
            }
            for (TileEntityBed bed : bedTiles) {
                if (!BlockUtils.canPosBeSeenEntity(new Vec3d((double)bed.getPos().getX() + 0.5, (double)bed.getPos().getY() + 0.4, (double)bed.getPos().getZ() + 0.5), (Entity)OffHand.getMe(), BlockUtils.bodyElement.LEGS)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean fallWarn() {
        return Minecraft.player.fallDistance > 20.0f;
    }

    private boolean isFallWarning() {
        return fall;
    }

    private void updatefallWarn() {
        int ping = 0;
        if (Minecraft.player != null && Minecraft.player.ticksExisted > 100 && mc.getConnection().getPlayerInfo(Minecraft.player.getUniqueID()) != null) {
            try {
                ping = MathUtils.clamp(mc.getConnection().getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime(), 0, 1000);
            } catch (Exception e) {
                System.out.println("Module-OffHand: Vegaline failled check ping");
            }
        }
        if (this.fallWarn()) {
            fall = true;
            timer3.reset();
        } else if (fall && timer3.hasReached(100 + ping)) {
            fall = false;
            timer3.reset();
        }
    }

    private boolean healthWarn() {
        float health = this.smartTriggerHP();
        return Minecraft.player.getHealth() <= health;
    }

    private boolean deathWarned() {
        boolean warn = false;
        if (this.getSlotByItem(Item.getItemById(449)) != -1 || Minecraft.player.getHeldItemOffhand().getItem() == Item.getItemById(449)) {
            this.updatefallWarn();
            if (this.healthWarn()) {
                warn = true;
            }
            if (!this.haveShar()) {
                if (OffHand.crystalWarn(6.656f)) {
                    warn = true;
                    totemBackward = false;
                }
                if (this.tntWarn(4.87f)) {
                    warn = true;
                    totemBackward = false;
                }
                if (this.bedWarn(7.92f)) {
                    warn = true;
                    totemBackward = false;
                }
            }
            this.updatefallWarn();
            if (this.isFallWarning()) {
                warn = true;
            }
        }
        return Minecraft.player.getHeldItemMainhand().getItem() != Items.TOTEM && !Minecraft.player.isCreative() && warn;
    }

    private boolean canUseItemMainHand() {
        return Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemBlock && (OffHand.mc.objectMouseOver.typeOfHit == null || OffHand.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK);
    }

    private static EntityPlayer getMe() {
        return FreeCam.fakePlayer != null && FreeCam.get.actived ? FreeCam.fakePlayer : Minecraft.player;
    }

    @Override
    public String getDisplayName() {
        int count = Minecraft.player == null ? 0 : this.getTotemCount();
        return count > 0 ? this.getDisplayByInt(count) + "T" : this.getName();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onUpdate() {
        block10: {
            block11: {
                block9: {
                    if (Minecraft.player == null || Minecraft.player.getHealth() == 0.0f || Minecraft.player.isDead || OffHand.mc.currentScreen instanceof GuiContainer && !(OffHand.mc.currentScreen instanceof GuiInventory)) {
                        return;
                    }
                    v0 = OffHand.totemTaken = this.deathWarned() != false && OffHand.totemBackward == false;
                    if (OffHand.timer4.hasReached(2100.0) || !this.TotemBackward.bValue) {
                        OffHand.totemBackward = false;
                    }
                    if (!OffHand.callNotSave) break block9;
                    OffHand.callNotSave = false;
                    break block10;
                }
                if (!Keyboard.isKeyDown(OffHand.mc.gameSettings.keyBindSwapHands.getKeyCode())) break block11;
                Minecraft.player.getHeldItemMainhand().getItem();
                if (Item.getItemById(449) != null) ** GOTO lbl-1000
            }
            if (GuiContainer.draggedStack != null && OffHand.mc.currentScreen instanceof GuiInventory && Mouse.isButtonDown(0)) lbl-1000:
            // 2 sources

            {
                OffHand.oldSlot = null;
            } else if (!(Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemAir) && Minecraft.player.getHeldItemOffhand().getItem() != Items.TOTEM) {
                OffHand.oldSlot = Minecraft.player.getHeldItemOffhand().getItem();
            }
        }
        this.updateOffHandHelps(true, true, true);
        this.updateEmptyHandFix();
        if (!(!this.deathWarned() || this.getSlotByItem(Items.TOTEM) == -1 && Minecraft.player.getHeldItemOffhand().getItem() != Items.TOTEM || OffHand.doBackSlot || OffHand.totemBackward)) {
            if (Minecraft.player.getHeldItemOffhand().getItem() != Items.TOTEM) {
                OffHand.doTotem = true;
            }
        } else if (OffHand.oldSlot != null && this.getSlotByItem(OffHand.oldSlot) != -1 && Minecraft.player.getHeldItemOffhand().getItem() != OffHand.oldSlot) {
            OffHand.doBackSlot = Minecraft.player.isHandActive() == false || Minecraft.player.getActiveHand() != EnumHand.MAIN_HAND || Minecraft.player.getActiveItemStack().getItem() != OffHand.oldSlot;
        }
        this.doItem(45, this.CanHotbarSwap.bValue);
    }

    private boolean haveItem(Item itemIn) {
        return this.getSlotByItem(itemIn) != -1 || Minecraft.player.inventoryContainer.getSlot(45).getStack().getItem() == itemIn;
    }

    boolean isBadOver() {
        if (OffHand.mc.objectMouseOver != null && OffHand.mc.objectMouseOver.getBlockPos() != null) {
            Block block = OffHand.mc.world.getBlockState(OffHand.mc.objectMouseOver.getBlockPos()).getBlock();
            List<Integer> badBlockIDs = Arrays.asList(96, 167, 54, 130, 146, 58, 64, 71, 193, 194, 195, 196, 197, 324, 330, 427, 428, 429, 430, 431, 154, 61, 23, 158, 145, 69, 107, 187, 186, 185, 184, 183, 107, 116, 84, 356, 404, 151, 25, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 389, 379, 380, 138, 321, 323, 77, 143, 379);
            boolean interact = !Minecraft.player.isSneaking() && OffHand.mc.objectMouseOver != null && OffHand.mc.objectMouseOver.getBlockPos() != null && block != null && badBlockIDs.stream().anyMatch(id -> Block.getIdFromBlock(block) == id);
            return interact;
        }
        return false;
    }

    boolean stackIsBall(ItemStack stack) {
        if (stack == null || stack.getDisplayName().isEmpty()) {
            return false;
        }
        List<String> BALL_SAMPLES = Arrays.asList("\u0448\u0430\u0440", "\u0441\u0444\u0435\u0440\u0430", "ball", "\u0440\u0443\u043d\u0430", "\u0442\u0430\u043b\u0438\u0441\u043c\u0430\u043d", "\u0442\u0430\u043b\u0438\u043a", "\u043c\u044f\u0447", "\u0430\u0443\u0440\u0430", "\u0430\u043c\u0443\u043b\u0435\u0442", "\u043a\u043e\u043b\u043e\u0431\u043e\u043a");
        String stackName = stack.getDisplayName();
        List<String> CHAR_SAMPLES = Arrays.asList("\u00a71", "\u00a72", "\u00a73", "\u00a74", "\u00a75", "\u00a76", "\u00a77", "\u00a78", "\u00a79", "\u00a70", "\u00a7c", "\u00a7e", "\u00a7a", "\u00a7b", "\u00a7d", "\u00a7f", "\u00a7r", "\u00a7l", "\u00a7k", "\u00a7o", "\u00a7m", "\u00a7n");
        for (String sample2 : CHAR_SAMPLES) {
            stackName = stackName.replace(sample2, "");
        }
        String finalStackName = stackName.toLowerCase();
        return stack.getItem() == Items.SKULL && BALL_SAMPLES.stream().anyMatch(sample -> finalStackName.contains((CharSequence)sample));
    }

    private void updateOffHandHelps(boolean crystalApple, boolean shieldApple, boolean ballApple) {
        boolean ballTrigger;
        boolean bad = this.isBadOver() || OffHand.mc.currentScreen != null && !(OffHand.mc.currentScreen instanceof GuiIngameMenu);
        boolean pcm = Mouse.isButtonDown(1) && OffHand.mc.currentScreen == null;
        Item toSwapItem = Items.GOLDEN_APPLE;
        boolean mainTeadled = Minecraft.player.isHandActive() && Minecraft.player.getActiveHand() == EnumHand.MAIN_HAND || Minecraft.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemPotion || (Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemBlock || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) && OffHand.mc.objectMouseOver != null && OffHand.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK;
        boolean crystalTrigger = crystalApple && Minecraft.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL && this.haveItem(toSwapItem) && saveSlot == null && !this.canUseItemMainHand() && Minecraft.player.getHeldItemMainhand().getItem() != toSwapItem && (Minecraft.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL || oldSlot == toSwapItem) && !mainTeadled && pcm && !bad;
        boolean shieldTrigger = shieldApple && (Minecraft.player.getHeldItemOffhand().getItem() == Items.SHIELD || Minecraft.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) && this.haveItem(toSwapItem) && Minecraft.player.getHeldItemMainhand().getItem() != toSwapItem && (Minecraft.player.getCooldownTracker().getCooldown(Items.SHIELD, 0.0f) > 0.1f && Minecraft.player.getCooldownTracker().getCooldown(Items.SHIELD, 0.0f) < 0.97f || Minecraft.player.getHealth() + Minecraft.player.getAbsorptionAmount() <= 11.0f && Minecraft.player.getActiveHand() == EnumHand.OFF_HAND && Minecraft.player.isBlocking()) && !bad;
        boolean bl = ballTrigger = ballApple && this.stackIsBall(Minecraft.player.getHeldItemOffhand()) && this.haveItem(toSwapItem) && Minecraft.player.getHeldItemMainhand().getItem() != toSwapItem && !mainTeadled && Minecraft.player.getHealth() + Minecraft.player.getAbsorptionAmount() <= 15.0f && pcm && !bad;
        Item current = crystalTrigger ? Items.END_CRYSTAL : (shieldTrigger ? Items.SHIELD : (ballTrigger ? Items.SKULL : oldSlot));
        boolean isTriggered = crystalTrigger || shieldTrigger || ballTrigger;
        boolean resetTrigger = false;
        if (isTriggered) {
            saveSlot = current;
        } else if (Minecraft.player.getHeldItemOffhand().getItem() == saveSlot) {
            resetTrigger = true;
        }
        if (crystalTrigger || shieldTrigger || ballTrigger) {
            clientSwap = true;
        } else if (resetTrigger) {
            clientSwap = false;
            saveSlot = null;
        }
        if (clientSwap && !resetTrigger && saveSlot != null) {
            Item i;
            Item item = isTriggered ? toSwapItem : (i = pcm ? toSwapItem : saveSlot);
            if (Minecraft.player.getHeldItemMainhand().getItem() != i && Minecraft.player.getHeldItemOffhand().getItem() != i) {
                oldSlot = i;
            }
        }
    }

    public void invClick(int slotId, boolean pcm) {
        ItemStack itemstack = Minecraft.player.inventoryContainer.slotClick(slotId, !pcm ? 0 : 1, ClickType.PICKUP, Minecraft.player);
        Minecraft.player.connection.sendPacket(new CPacketClickWindow(Minecraft.player.inventoryContainer != null ? Minecraft.player.inventoryContainer.windowId : 0, slotId, !pcm ? 0 : 1, ClickType.PICKUP, itemstack, Minecraft.player.inventoryContainer.getNextTransactionID(Minecraft.player.inventory)));
    }

    public void doItem(int slotIn, boolean canHotbarSwap) {
        boolean stackCurrentIsLite;
        int currentItem = -1200;
        int n = doBackSlot ? this.getSlotByItem(oldSlot) : (currentItem = doTotem ? this.getSlotByItem(Items.TOTEM) : 0);
        if (currentItem < 36 || currentItem > 44) {
            canHotbarSwap = false;
        }
        if (!doTotem && !doBackSlot || !timerDelay.hasReached(canHotbarSwap ? 50.0 : 150.0)) {
            return;
        }
        if (currentItem == -1200) {
            return;
        }
        boolean stackIsLite = currentItem != -1 && Minecraft.player.inventory.getStackInSlot(currentItem) != null && Minecraft.player.inventory.getStackInSlot((int)currentItem).stackSize == 1;
        boolean bl = stackCurrentIsLite = Minecraft.player.inventory.getStackInSlot((int)slotIn).stackSize == 1;
        if (currentItem >= 36 && currentItem <= 44 && !this.isBadOver() && OffHand.mc.currentScreen == null) {
            if ((Minecraft.player.inventory.getStackInSlot(currentItem - 36) == Minecraft.player.inventory.getCurrentItem() || !(Minecraft.player.inventoryContainer instanceof ContainerPlayer) || canHotbarSwap) && slotIn == 45) {
                if (Minecraft.player.inventory.currentItem != currentItem - 36) {
                    Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(currentItem - 36));
                }
                Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
                Minecraft.player.setHeldItem(EnumHand.OFF_HAND, Minecraft.player.inventory.getStackInSlot(currentItem - 36));
                if (Minecraft.player.inventory.currentItem != currentItem - 36) {
                    Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
                }
            } else if (slotIn != currentItem - 36) {
                OffHand.mc.playerController.windowClick(Minecraft.player.inventoryContainer.windowId, slotIn, currentItem - 36, ClickType.SWAP, Minecraft.player);
            }
            if (slotIn == 45) {
                Minecraft.player.setHeldItem(EnumHand.OFF_HAND, Minecraft.player.getHeldItemOffhand());
            }
        } else if (slotIn != currentItem) {
            if (slotIn != 45 || !(Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemAir)) {
                this.invClick(slotIn, Minecraft.player.getHeldItemOffhand().stackSize == 1);
            }
            this.invClick(currentItem, stackIsLite && !this.isBadOver() && OffHand.mc.currentScreen == null);
            this.invClick(slotIn, false);
        }
        if (slotIn == 45 && !(Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemAir)) {
            Minecraft.player.setHeldItem(EnumHand.OFF_HAND, Minecraft.player.getHeldItemOffhand());
        }
        doBackSlot = false;
        doTotem = false;
        timerDelay.reset();
        callNotSave = true;
    }

    public int getSlotByItem(Item itemIn) {
        for (int i = Minecraft.player.inventoryContainer.inventorySlots.size() - 1; i > 0; --i) {
            try {
                ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
                if (itemIn == null || itemStack == null || !itemStack.getItem().equals(itemIn)) continue;
                return i;
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        float scaleAnimVal;
        int totemCount = this.getTotemCount();
        if (totemTaken) {
            OffHand.scaleAnim.to = 1.05f;
        }
        if ((scaleAnimVal = scaleAnim.getAnim()) > 1.0f) {
            scaleAnim.setAnim(1.0f);
            scaleAnimVal = 1.0f;
        }
        if (OffHand.scaleAnim.to == 0.0f && (double)scaleAnimVal < 0.1) {
            scaleAnim.setAnim(0.0f);
        }
        float popAnimVal = popAnim.getAnim();
        if (!totemTaken && !(popAnimVal > 0.0f) && OffHand.scaleAnim.to != 0.0f) {
            OffHand.scaleAnim.to = 0.0f;
        }
        if (OffHand.popAnim.to == 0.0f && (double)popAnimVal < 0.03) {
            popAnim.setAnim(0.0f);
        }
        OffHand.popAnim.speed = 0.02f;
        if (scaleAnimVal == 0.0f) {
            return;
        }
        float x = (float)event.getResolution().getScaledWidth() / 2.0f + (OffHand.mc.gameSettings.thirdPersonView != 0 ? -8.0f + 20.0f * AutoApple.get.scaleAnimation.anim : 12.0f);
        float y = (float)event.getResolution().getScaledHeight() / 2.0f - 8.0f;
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(2929);
        GL11.glTranslatef(x += Crosshair.get.crossPosMotions[0], y += Crosshair.get.crossPosMotions[1], 0.0f);
        RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, scaleAnimVal);
        float popAnimPC = 1.0f - popAnimVal;
        if (popAnimVal * 4.0f * 255.0f >= 33.0f) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, popAnimPC);
            RenderUtils.customRotatedObject2D(5.0f + popAnimPC * 20.0f, 8.0f - popAnimPC * popAnimPC * popAnimPC * popAnimPC * 8.0f, 0.0f, 0.0f, -180.0f - popAnimPC * -180.0f);
            Fonts.noise_24.drawStringWithShadow("-1", 2.0f + popAnimPC * 20.0f, 4.0f - popAnimPC * popAnimPC * popAnimPC * popAnimPC * 8.0f, ColorUtils.getColor(255, 0, 0, MathUtils.clamp(popAnimVal * 4.0f * 255.0f, 33.0f, 255.0f)));
            GL11.glPopMatrix();
        }
        RenderUtils.customRotatedObject2D(0.0f, 0.0f, 16.0f, 16.0f, popAnimVal * popAnimVal * popAnimVal * 20.0f);
        RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, 1.0f + popAnimVal * popAnimVal * popAnimVal);
        ItemStack stack = new ItemStack(Items.TOTEM);
        if (popAnimVal != 0.0f) {
            float popAnimValMM = 1.0f - MathUtils.clamp(popAnimVal, 0.0f, 1.0f);
            float popAnimPC2 = ((double)popAnimValMM > 0.5 ? 1.0f - popAnimValMM : popAnimValMM) * 3.0f;
            popAnimPC2 = popAnimPC2 > 1.0f ? 1.0f : popAnimPC2;
            GL11.glPushMatrix();
            StencilUtil.initStencilToWrite();
            GL11.glTranslated(popAnimPC2 * 16.0f, -popAnimPC2 * 6.0f, 0.0);
            RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, 0.5f + popAnimPC2 * popAnimVal);
            RenderUtils.customRotatedObject2D(0.0f, 0.0f, 16.0f, 16.0f, 270.0f * -popAnimPC * popAnimPC * popAnimPC * popAnimPC * popAnimPC);
            mc.getRenderItem().renderItemIntoGUI(stack, 0, 0);
            StencilUtil.readStencilBuffer(1);
            mc.getRenderItem().renderItemIntoGUI(stack, 0, 0);
            RenderUtils.drawAlphedRect(-24.0, -24.0, 48.0, 48.0, ColorUtils.getColor(255, 255, 255, popAnimVal * 255.0f));
            StencilUtil.uninitStencilBuffer();
            GL11.glPopMatrix();
        }
        if (popAnimVal != 0.0f) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, MathUtils.clamp(popAnimPC * 1.5f, 0.0f, 1.0f));
            mc.getRenderItem().renderItemIntoGUI(stack, 0, 0);
            GL11.glPopMatrix();
        } else {
            mc.getRenderItem().renderItemIntoGUI(stack, 0, 0);
        }
        int c = totemCount == 0 ? ColorUtils.fadeColor(ColorUtils.getColor(255, 80, 50, 80.0f * scaleAnimVal), ColorUtils.getColor(255, 80, 50, 255.0f * scaleAnimVal), 1.5f) : ColorUtils.getColor(255, 255, 255, 255.0f * scaleAnimVal);
        (totemCount == 0 ? Fonts.noise_20 : Fonts.mntsb_12).drawStringWithShadow(totemCount + "x", totemCount == 0 ? 14.0 : 12.0, totemCount == 0 ? 9.0 : 13.5, c);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            totemTaken = false;
        }
        super.onToggled(actived);
    }

    private int getTotemCount() {
        int totemCount = 0;
        for (int i = 0; i <= 45; ++i) {
            ItemStack is = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() != Items.TOTEM) continue;
            totemCount += is.stackSize;
        }
        return totemCount;
    }

    static {
        scaleAnim = new AnimationUtils(0.0f, 0.0f, 0.07f);
        popAnim = new AnimationUtils(0.0f, 0.0f, 0.03f);
    }
}

