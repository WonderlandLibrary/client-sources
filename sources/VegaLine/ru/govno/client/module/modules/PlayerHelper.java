/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Wrapper;

public class PlayerHelper
extends Module {
    public static PlayerHelper get;
    public Settings PearlBlockThrow;
    public Settings AutoTool;
    public Settings ToolSwapsInInv;
    public Settings SpeedMine;
    public Settings MineMode;
    public Settings MineSkeepValue;
    public Settings MineFastBlockHit;
    public Settings MineHaste;
    public Settings SkipMineMs;
    public Settings PacketMineSilentSlot;
    public Settings FastPlacing;
    public Settings FastEXPThrow;
    public Settings FastBlockPlace;
    public Settings FastInteract;
    public Settings FastPotionThrow;
    public Settings NoPlaceMissing;
    public Settings NoSurvivalCopyBlock;
    public Settings AppleEatCooldown;
    public Settings AppleTimeWait;
    public Settings PearlThrowCooldown;
    public Settings PearlTimeWait;
    public Settings ChorusUseCooldown;
    public Settings ChorusUseWait;
    public Settings CooldownsCheckKT;
    public static TimerHelper timerApple;
    public static TimerHelper timerPearl;
    public static TimerHelper timerChorus;
    public static boolean checkApple;
    public static boolean checkPearl;
    public static boolean checkChorus;
    private float sizeApple = 0.0f;
    private float sizePearl = 0.0f;
    private float sizeChorus = 0.0f;
    boolean itemBacked = false;
    static int lastSlot;
    public static BlockPos currentBlock;
    public static int ticks;
    Vec3d lastSVPos = null;
    boolean lastGr = false;
    boolean isPearlSlot = false;
    ItemStack lastOfPreMine = null;
    static List<SlotsMemory> slotMemories;
    public static BlockPos breakPos;
    boolean runPacket = false;
    float breakTicks = 0.0f;
    double lastBreakTime = 0.0;
    boolean isStartPacket;
    double progressPacket;
    int startSlot = -1;
    private final AnimationUtils alphaSelectPC = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils progressingSelect = new AnimationUtils(0.0f, 0.0f, 0.115f);
    BlockPos posRender = breakPos;

    public PlayerHelper() {
        super("PlayerHelper", 0, Module.Category.PLAYER);
        get = this;
        this.PearlBlockThrow = new Settings("PearlBlockThrow", true, (Module)this);
        this.settings.add(this.PearlBlockThrow);
        this.AutoTool = new Settings("AutoTool", true, (Module)this);
        this.settings.add(this.AutoTool);
        this.ToolSwapsInInv = new Settings("ToolSwapsInInv", true, (Module)this, () -> this.AutoTool.bValue);
        this.settings.add(this.ToolSwapsInInv);
        this.SpeedMine = new Settings("SpeedMine", true, (Module)this);
        this.settings.add(this.SpeedMine);
        this.MineMode = new Settings("MineMode", "Matrix", this, new String[]{"Matrix", "Custom", "Packet"}, () -> this.SpeedMine.bValue);
        this.settings.add(this.MineMode);
        this.MineSkeepValue = new Settings("MineSkeepValue", 0.5f, 1.0f, 0.0f, this, () -> this.MineMode.currentMode.equalsIgnoreCase("Custom") && this.SpeedMine.bValue);
        this.settings.add(this.MineSkeepValue);
        this.MineFastBlockHit = new Settings("MineFastBlockHit", true, (Module)this, () -> this.MineMode.currentMode.equalsIgnoreCase("Custom") && this.SpeedMine.bValue);
        this.settings.add(this.MineFastBlockHit);
        this.MineHaste = new Settings("MineHaste", true, (Module)this, () -> this.MineMode.currentMode.equalsIgnoreCase("Custom") && this.SpeedMine.bValue);
        this.settings.add(this.MineHaste);
        this.SkipMineMs = new Settings("SkipMineMs", 250.0f, 1000.0f, 0.0f, this, () -> this.MineMode.currentMode.equalsIgnoreCase("Packet") && this.SpeedMine.bValue);
        this.settings.add(this.SkipMineMs);
        this.PacketMineSilentSlot = new Settings("PacketMineSilentSlot", false, (Module)this, () -> this.MineMode.currentMode.equalsIgnoreCase("Packet") && this.SpeedMine.bValue && this.AutoTool.bValue);
        this.settings.add(this.PacketMineSilentSlot);
        this.FastPlacing = new Settings("FastPlacing", true, (Module)this);
        this.settings.add(this.FastPlacing);
        this.FastEXPThrow = new Settings("FastEXPThrow", true, (Module)this, () -> this.FastPlacing.bValue);
        this.settings.add(this.FastEXPThrow);
        this.FastBlockPlace = new Settings("FastBlockPlace", true, (Module)this, () -> this.FastPlacing.bValue);
        this.settings.add(this.FastBlockPlace);
        this.FastInteract = new Settings("FastInteract", false, (Module)this, () -> this.FastPlacing.bValue);
        this.settings.add(this.FastInteract);
        this.FastPotionThrow = new Settings("FastPotionThrow", true, (Module)this, () -> this.FastPlacing.bValue);
        this.settings.add(this.FastPotionThrow);
        this.NoPlaceMissing = new Settings("NoPlaceMissing", true, (Module)this, () -> this.FastPlacing.bValue && (this.FastEXPThrow.bValue || this.FastBlockPlace.bValue || this.FastInteract.bValue || this.FastPotionThrow.bValue));
        this.settings.add(this.NoPlaceMissing);
        this.NoSurvivalCopyBlock = new Settings("NoSurvivalCopyBlock", true, (Module)this);
        this.settings.add(this.NoSurvivalCopyBlock);
        this.AppleEatCooldown = new Settings("AppleEatCooldown", false, (Module)this);
        this.settings.add(this.AppleEatCooldown);
        this.AppleTimeWait = new Settings("AppleTimeWait", 2300.0f, 10000.0f, 100.0f, this, () -> this.AppleEatCooldown.bValue);
        this.settings.add(this.AppleTimeWait);
        this.PearlThrowCooldown = new Settings("PearlThrowCooldown", false, (Module)this);
        this.settings.add(this.PearlThrowCooldown);
        this.PearlTimeWait = new Settings("PearlTimeWait", 14000.0f, 30000.0f, 500.0f, this, () -> this.PearlThrowCooldown.bValue);
        this.settings.add(this.PearlTimeWait);
        this.ChorusUseCooldown = new Settings("ChorusUseCooldown", false, (Module)this);
        this.settings.add(this.ChorusUseCooldown);
        this.ChorusUseWait = new Settings("ChorusUseWait", 1500.0f, 30000.0f, 1000.0f, this, () -> this.ChorusUseCooldown.bValue);
        this.settings.add(this.ChorusUseWait);
        this.CooldownsCheckKT = new Settings("CooldownsCheckKT", true, (Module)this, () -> this.AppleEatCooldown.bValue || this.PearlThrowCooldown.bValue || this.ChorusUseCooldown.bValue);
        this.settings.add(this.CooldownsCheckKT);
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived && this.SpeedMine.bValue && this.MineMode.currentMode.equalsIgnoreCase("Custom")) {
            Minecraft.player.removeActivePotionEffect(Potion.getPotionById(3));
        }
        ticks = 0;
        super.onToggled(actived);
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        Packet packet;
        if (this.actived && (packet = event.getPacket()) instanceof CPacketPlayerTryUseItemOnBlock) {
            CPacketPlayerTryUseItemOnBlock packet2 = (CPacketPlayerTryUseItemOnBlock)packet;
            if (this.PearlBlockThrow.bValue) {
                if (Minecraft.player != null && Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemEnderPearl && ticks == 0) {
                    ticks = 10;
                    event.cancel();
                }
                if (ticks > 0) {
                    --ticks;
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        if (checkPearl || checkApple || checkChorus) {
            if (!PlayerHelper.canCooldown() || !this.AppleEatCooldown.bValue && !this.PearlThrowCooldown.bValue && !this.ChorusUseCooldown.bValue) {
                checkApple = false;
                checkPearl = false;
                checkChorus = false;
            }
            float timeApple = this.getAppleTime();
            float timePearl = this.getPearlTime();
            float timeChorus = this.getPearlTime();
            if (checkPearl && timerPearl.hasReached(timePearl)) {
                checkPearl = false;
            }
            if (checkApple && timerApple.hasReached(timeApple)) {
                checkApple = false;
            }
            if (checkChorus && timerChorus.hasReached(timeChorus)) {
                checkChorus = false;
            }
        }
        if (ticks != 0 && !(Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemEnderPearl)) {
            ticks = 0;
        }
        if (this.FastPlacing.bValue) {
            boolean fastPlace;
            boolean exp = this.FastEXPThrow.bValue && Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemExpBottle;
            boolean pot = this.FastPotionThrow.bValue && (Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemSplashPotion || Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemLingeringPotion || Minecraft.player.inventory.getCurrentItem().getItem() == Items.GLASS_BOTTLE);
            boolean interact = false;
            if (PlayerHelper.mc.objectMouseOver != null && PlayerHelper.mc.objectMouseOver.getBlockPos() != null) {
                Block block = PlayerHelper.mc.world.getBlockState(PlayerHelper.mc.objectMouseOver.getBlockPos()).getBlock();
                interact = this.FastInteract.bValue && !Minecraft.player.isSneaking() && PlayerHelper.mc.objectMouseOver != null && PlayerHelper.mc.objectMouseOver.getBlockPos() != null && block != null && Arrays.asList(96, 167, 54, 130, 146, 58, 64, 71, 193, 194, 195, 196, 197, 324, 330, 427, 428, 429, 430, 431, 154, 61, 23, 158, 145, 69, 107, 187, 186, 185, 184, 183, 107, 116, 84, 356, 404, 151, 25, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 389, 379, 380, 138, 321, 323, 77, 143, 404, 379, 323).stream().noneMatch(id -> Block.getIdFromBlock(block) == id);
            }
            boolean blocks = this.FastBlockPlace.bValue && Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemBlock && (!interact || Minecraft.player.isSneaking());
            boolean bl = fastPlace = blocks || exp || interact || pot;
            if (fastPlace && (Minecraft.player.ticksExisted % 2 == 1 || !this.NoPlaceMissing.bValue)) {
                int n = PlayerHelper.mc.rightClickDelayTimer = this.NoPlaceMissing.bValue ? 1 : 0;
            }
        }
        if (!(!this.AutoTool.bValue || this.SpeedMine.bValue && this.MineMode.currentMode.equalsIgnoreCase("Packet"))) {
            boolean isBreak;
            boolean bl = isBreak = Mouse.isButtonDown(0) || currentBlock != null;
            if (isBreak) {
                this.itemBacked = false;
                int bestSlot = -228;
                double max = 0.0;
                if (currentBlock != null) {
                    int i;
                    int n = i = this.ToolSwapsInInv.bValue ? 36 : 8;
                    while (i > 0) {
                        float speed;
                        ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
                        if (!stack.isEmpty() && (speed = stack.getStrVsBlock(PlayerHelper.mc.world.getBlockState(currentBlock))) > 1.0f) {
                            int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                            if ((double)(speed = (float)((double)speed + (eff > 0 ? Math.pow(eff, 2.0) + 1.0 : 0.0))) > max) {
                                max = speed;
                                bestSlot = i;
                            }
                            if (PlayerHelper.mc.world.getBlockState(currentBlock).getBlock() == Blocks.WEB && InventoryUtil.getItemInHotbar(Items.SHEARS) != -1) {
                                bestSlot = InventoryUtil.getItemInHotbar(Items.SHEARS);
                            }
                        }
                        --i;
                    }
                }
                if (bestSlot != -228) {
                    PlayerHelper.equip(bestSlot, false);
                    if (bestSlot > 8) {
                        lastSlot = bestSlot;
                    }
                }
            } else {
                if (!this.itemBacked && lastSlot != -1) {
                    int lastOfPreMineSlot = -1;
                    for (int i = 0; i < 36; ++i) {
                        if (Minecraft.player.inventory.getStackInSlot(i) != this.lastOfPreMine) continue;
                        lastOfPreMineSlot = i;
                    }
                    if (lastOfPreMineSlot != -1) {
                        lastSlot = lastOfPreMineSlot;
                    }
                    PlayerHelper.equip(lastSlot, true);
                    this.itemBacked = true;
                }
                lastSlot = Minecraft.player.inventory.currentItem;
                this.lastOfPreMine = Minecraft.player.inventory.getCurrentItem();
            }
            currentBlock = null;
        }
        if (this.SpeedMine.bValue) {
            if (this.MineMode.currentMode.equalsIgnoreCase("Custom")) {
                if (this.MineHaste.bValue) {
                    if (PlayerHelper.mc.playerController.getIsHittingBlock()) {
                        Minecraft.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1000, 1));
                    } else {
                        Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(3));
                    }
                }
                if (this.MineFastBlockHit.bValue) {
                    PlayerHelper.mc.playerController.blockHitDelay = 0;
                }
                if (PlayerHelper.mc.playerController.curBlockDamageMP >= 1.0f - this.MineSkeepValue.fValue) {
                    PlayerHelper.mc.playerController.curBlockDamageMP = 1.0f;
                }
            } else if (this.MineMode.currentMode.equalsIgnoreCase("Matrix")) {
                this.matrixBreak();
            } else if (this.MineMode.currentMode.equalsIgnoreCase("Packet")) {
                if (Mouse.isButtonDown(0) && PlayerHelper.mc.playerController.isHittingBlock && PlayerHelper.mc.objectMouseOver != null && PlayerHelper.mc.objectMouseOver.entityHit == null) {
                    BlockPos pos = PlayerHelper.mc.objectMouseOver.getBlockPos();
                    this.runPacketBreak(pos);
                }
                this.packetMine((int)this.SkipMineMs.fValue - 50);
            }
        }
    }

    void runPacketBreak(BlockPos pos) {
        this.breakTicks = 0.0f;
        breakPos = pos;
        this.runPacket = true;
    }

    EnumFacing getFacing(BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            RayTraceResult rayTraceResult = PlayerHelper.mc.world.rayTraceBlocks(new Vec3d(this.getMe().posX, this.getMe().posY + (double)this.getMe().getEyeHeight(), this.getMe().posZ), new Vec3d((double)pos.getX() + 0.5 + (double)facing.getDirectionVec().getX() / 2.0, (double)pos.getY() + 0.5 + (double)facing.getDirectionVec().getY() / 2.0, (double)pos.getZ() + 0.5 + (double)facing.getDirectionVec().getZ() / 2.0), false, true, false);
            if (rayTraceResult != null && (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK || !rayTraceResult.getBlockPos().equals(pos))) continue;
            return facing;
        }
        if ((double)pos.getY() > this.getMe().posY + (double)this.getMe().getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }

    public float blockBreakSpeed(IBlockState blockMaterial, ItemStack tool) {
        float mineSpeed = tool.getStrVsBlock(blockMaterial);
        int efficiencyFactor = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, tool);
        mineSpeed = (float)((double)mineSpeed > 1.0 && efficiencyFactor > 0 ? (double)((float)(efficiencyFactor * efficiencyFactor) + mineSpeed) + 1.0 : (double)mineSpeed);
        if (Minecraft.player.getActivePotionEffect(MobEffects.HASTE) != null) {
            mineSpeed *= 1.0f + (float)Objects.requireNonNull(Minecraft.player.getActivePotionEffect(MobEffects.HASTE)).getAmplifier() * 0.2f;
        }
        return mineSpeed;
    }

    public double blockBrokenTime(BlockPos pos, ItemStack tool) {
        if (pos == null || tool == null) {
            return 0.0;
        }
        IBlockState blockMaterial = PlayerHelper.mc.world.getBlockState(pos);
        float damageTicks = this.blockBreakSpeed(blockMaterial, tool) / blockMaterial.getBlockHardness(PlayerHelper.mc.world, pos) / 40.0f;
        return Math.ceil(1.0 / (double)damageTicks) * 50.0;
    }

    private EntityPlayer getMe() {
        return FreeCam.fakePlayer != null ? FreeCam.fakePlayer : Minecraft.player;
    }

    double getProggress(double ms, BlockPos pos, ItemStack stack) {
        return pos == null || stack == null ? 0.0 : MathUtils.clamp(ms / this.blockBrokenTime(pos, stack), 0.0, 1.0);
    }

    ItemStack getBestStack(BlockPos pos, boolean invUse) {
        int i;
        int bestSlot = -1;
        if (pos == null) {
            return Minecraft.player.inventory.getCurrentItem();
        }
        int n = i = invUse ? 36 : 8;
        while (i > 0) {
            float speed;
            ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
            double max = 0.0;
            if (!stack.isEmpty() && (speed = stack.getStrVsBlock(PlayerHelper.mc.world.getBlockState(pos))) > 1.0f) {
                int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                if ((double)(speed = (float)((double)speed + (eff > 0 ? Math.pow(eff, 2.0) + 1.0 : 0.0))) > max) {
                    max = speed;
                    bestSlot = i;
                }
                if (PlayerHelper.mc.world.getBlockState(pos).getBlock() == Blocks.WEB && InventoryUtil.getItemInHotbar(Items.SHEARS) != -1) {
                    bestSlot = InventoryUtil.getItemInHotbar(Items.SHEARS);
                }
            }
            --i;
        }
        return bestSlot >= 0 && bestSlot <= (invUse ? 36 : 8) ? Minecraft.player.inventory.getStackInSlot(bestSlot) : Minecraft.player.inventory.getCurrentItem();
    }

    int getSlotForStack(ItemStack stack, boolean invIse) {
        int i;
        int n = i = invIse ? 36 : 8;
        while (i > 1) {
            if (Minecraft.player.inventory.getStackInSlot(i) == stack) {
                return i;
            }
            --i;
        }
        return -1;
    }

    void packetMine(float skipMs) {
        if (breakPos != null) {
            EnumFacing face;
            if (Minecraft.player.isSneaking() && !breakPos.equals(BlockUtils.getEntityBlockPos(this.getMe()).down()) || Minecraft.player.getDistanceAtEye((double)breakPos.getX() + 0.5, (double)breakPos.getY() + 0.5, (double)breakPos.getZ() + 0.5) > 5.46) {
                this.isStartPacket = false;
                breakPos = null;
                return;
            }
            PlayerHelper.mc.gameSettings.keyBindAttack.pressed = false;
            if (PlayerHelper.mc.playerController.isHittingBlock) {
                this.breakTicks = 0.0f;
                this.isStartPacket = true;
            }
            boolean invUse = this.ToolSwapsInInv.bValue;
            ItemStack stack = this.AutoTool.bValue ? this.getBestStack(breakPos, invUse) : Minecraft.player.getHeldItemMainhand();
            double progress = this.progressPacket = this.getProggress(this.breakTicks * 50.0f + skipMs, breakPos, stack);
            this.breakTicks += Minecraft.player.onGround && !(PlayerHelper.mc.world.getBlockState(BlockUtils.getEntityBlockPos(this.getMe()).up()).getBlock() instanceof BlockLiquid) && !Minecraft.player.isInWeb ? 1.0f : 0.2f;
            int slot = this.getSlotForStack(stack, invUse);
            int handSlot = Minecraft.player.inventory.currentItem;
            if (!this.PacketMineSilentSlot.bValue && this.startSlot != -1 && slot != -1 && slot < 9) {
                Minecraft.player.inventory.currentItem = slot;
            }
            if (this.isStartPacket) {
                if (this.startSlot == -1 && slot < 9 && !this.PacketMineSilentSlot.bValue) {
                    this.startSlot = Minecraft.player.inventory.currentItem;
                }
                if (slot != -1 && (handSlot != slot || slot > 8)) {
                    if (slot > 8) {
                        this.startSlot = slot;
                        PlayerHelper.mc.playerController.windowClick(0, slot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
                    } else {
                        Minecraft.player.inventory.currentItem = slot;
                        PlayerHelper.mc.playerController.syncCurrentPlayItem();
                    }
                }
                face = EnumFacing.UP;
                if (PlayerHelper.mc.objectMouseOver != null && PlayerHelper.mc.objectMouseOver.sideHit != null) {
                    face = PlayerHelper.mc.objectMouseOver.sideHit;
                }
                mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, breakPos, face));
                if (slot != -1 && (handSlot != slot || slot > 8)) {
                    if (slot > 8) {
                        if (this.PacketMineSilentSlot.bValue) {
                            PlayerHelper.mc.playerController.windowClickMemory(0, slot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player, 50);
                        }
                    } else {
                        if (this.PacketMineSilentSlot.bValue) {
                            Minecraft.player.inventory.currentItem = handSlot;
                        }
                        PlayerHelper.mc.playerController.syncCurrentPlayItem();
                    }
                }
                this.isStartPacket = false;
            }
            if (progress == 1.0) {
                if (slot != -1 && (handSlot != slot || slot > 8)) {
                    if (slot > 8) {
                        if (!this.PacketMineSilentSlot.bValue) {
                            PlayerHelper.mc.playerController.windowClick(0, slot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
                        }
                    } else {
                        Minecraft.player.inventory.currentItem = slot;
                        PlayerHelper.mc.playerController.syncCurrentPlayItem();
                    }
                }
                face = EnumFacing.UP;
                if (PlayerHelper.mc.objectMouseOver != null && PlayerHelper.mc.objectMouseOver.sideHit != null) {
                    face = PlayerHelper.mc.objectMouseOver.sideHit;
                }
                mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, face));
                if (slot != -1 && (handSlot != slot || slot > 8)) {
                    if (slot > 8) {
                        PlayerHelper.mc.playerController.windowClickMemory(0, slot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player, 50);
                    } else {
                        if (this.PacketMineSilentSlot.bValue) {
                            Minecraft.player.inventory.currentItem = handSlot;
                        }
                        PlayerHelper.mc.playerController.syncCurrentPlayItem();
                    }
                }
                if (this.startSlot > 8) {
                    PlayerHelper.mc.playerController.windowClickMemory(0, this.startSlot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player, 50);
                }
                if (!this.PacketMineSilentSlot.bValue && this.startSlot != -1 && this.startSlot < 9) {
                    Minecraft.player.inventory.currentItem = this.startSlot;
                    PlayerHelper.mc.playerController.syncCurrentPlayItem();
                    this.startSlot = -1;
                }
                breakPos = null;
            }
        } else {
            PlayerHelper.mc.gameSettings.keyBindAttack.pressed = Mouse.isButtonDown(0) && PlayerHelper.mc.currentScreen == null;
            this.progressPacket = 0.0;
            this.breakTicks = 0.0f;
            this.isStartPacket = true;
        }
    }

    private final void drawBlockPos(AxisAlignedBB aabb, int color) {
        int c1 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 2.6f);
        int c2 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 7.2f);
        int c3 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 15.0f);
        RenderUtils.drawCanisterBox(aabb, true, true, true, c1, c2, c3);
    }

    private AxisAlignedBB getAxisExtended(BlockPos pos, AxisAlignedBB aabb, float percent, Vec3d smoothVec) {
        double x1 = aabb.minX - (double)pos.getX() + smoothVec.xCoord;
        double y1 = aabb.minY - (double)pos.getY() + smoothVec.yCoord;
        double z1 = aabb.minZ - (double)pos.getZ() + smoothVec.zCoord;
        double x2 = aabb.maxX - (double)pos.getX() + smoothVec.xCoord;
        double y2 = aabb.maxY - (double)pos.getY() + smoothVec.yCoord;
        double z2 = aabb.maxZ - (double)pos.getZ() + smoothVec.zCoord;
        double XW = x2 - x1;
        double ZW = z2 - z1;
        double YH = y2 - y1;
        Vec3d centerOFAABBox = new Vec3d(x1 + XW / 2.0, y1 + YH / 2.0, z1 + ZW / 2.0);
        AxisAlignedBB finallyAABB = new AxisAlignedBB(centerOFAABBox.xCoord - XW / 2.0 * (double)percent, centerOFAABBox.yCoord - YH / 2.0 * (double)percent, centerOFAABBox.zCoord - ZW / 2.0 * (double)percent, centerOFAABBox.xCoord + XW / 2.0 * (double)percent, centerOFAABBox.yCoord + YH / 2.0 * (double)percent, centerOFAABBox.zCoord + ZW / 2.0 * (double)percent);
        return finallyAABB == null ? aabb : finallyAABB;
    }

    @Override
    public void alwaysRender3D() {
        if (!this.actived) {
            return;
        }
        if (breakPos != null) {
            this.posRender = breakPos;
        }
        int color = ClientColors.getColor1();
        boolean isHitting = this.runPacket && breakPos != null;
        float progressUpdated01 = (float)this.progressPacket;
        this.alphaSelectPC.to = isHitting ? 1.0f : 0.0f;
        this.alphaSelectPC.speed = isHitting ? 0.1f : 0.05f;
        float alphaIn = this.alphaSelectPC.getAnim();
        this.progressingSelect.to = MathUtils.clamp(progressUpdated01 * 1.05f, 0.0f, 1.0f);
        float f = this.progressingSelect.speed = isHitting ? 0.1f : 0.145f;
        if ((double)alphaIn > 0.01) {
            RenderUtils.setup3dForBlockPos(() -> this.drawBlockPos(this.getAxisExtended(this.posRender, PlayerHelper.mc.world.getBlockState(this.posRender).getSelectedBoundingBox(PlayerHelper.mc.world, this.posRender), this.progressingSelect.getAnim(), new Vec3d(this.posRender.getX(), this.posRender.getY(), this.posRender.getZ())), ColorUtils.swapAlpha(color, MathUtils.clamp((float)ColorUtils.getAlphaFromColor(color) * alphaIn * 2.0f, 0.0f, 255.0f))), true);
        }
    }

    private ItemStack currentStack() {
        return Minecraft.player.inventory.getCurrentItem();
    }

    private Item currentItem() {
        return this.currentStack().getItem();
    }

    private int getEff(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
    }

    private int stackEff() {
        if (this.currentStack() != null && !(this.currentStack().getItem() instanceof ItemAir)) {
            return this.getEff(this.currentStack());
        }
        return 0;
    }

    private Material blockMaterial(BlockPos pos) {
        return PlayerHelper.mc.world.getBlockState(pos).getMaterial();
    }

    private void matrixBreak() {
        if (PlayerHelper.mc.objectMouseOver == null) {
            return;
        }
        if (PlayerHelper.mc.pointedEntity == null && PlayerHelper.mc.objectMouseOver.getBlockPos() != null) {
            this.matrix(PlayerHelper.mc.objectMouseOver.getBlockPos());
        }
    }

    private void matrix(BlockPos pos) {
        IBlockState state = PlayerHelper.mc.world.getBlockState(pos);
        Block block = state.getBlock();
        Material material = this.blockMaterial(pos);
        Item item = this.currentItem();
        int eff = this.stackEff();
        boolean slow = !Minecraft.player.onGround || Minecraft.player.isInWater() || Minecraft.player.isInWeb || Minecraft.player.isInLava();
        float skeep = 0.0f;
        int delay = PlayerHelper.mc.playerController.blockHitDelay;
        if (material != null && material == Material.ROCK && item instanceof ItemPickaxe) {
            String type2 = ((ItemPickaxe)item).getToolMaterialName();
            boolean isOre = block instanceof BlockOre;
            if (type2.equalsIgnoreCase("DIAMOND") && !isOre) {
                delay = 0;
                switch (eff) {
                    case 0: {
                        skeep = 0.1f;
                    }
                    case 1: {
                        skeep = 0.2f;
                    }
                    case 2: {
                        skeep = 0.3f;
                    }
                    case 3: {
                        skeep = 0.4f;
                    }
                    case 4: {
                        skeep = 0.9f;
                    }
                }
                skeep = 0.0f;
            }
        }
        if (PlayerHelper.mc.playerController.curBlockDamageMP >= 1.0f - MathUtils.clamp(skeep, 0.0f, 1.0f)) {
            PlayerHelper.mc.playerController.curBlockDamageMP = 1.0f;
        }
        if (PlayerHelper.mc.playerController.blockHitDelay != delay) {
            PlayerHelper.mc.playerController.blockHitDelay = delay;
        }
    }

    private static void equip(int slot, boolean back) {
        if (slot > 8) {
            if (!back) {
                slotMemories.add(new SlotsMemory(slot, lastSlot));
            }
            slotMemories.forEach(Islot -> PlayerHelper.mc.playerController.windowClick(0, slot, Islot.slotTo, ClickType.SWAP, Minecraft.player));
            if (back) {
                slotMemories.clear();
            }
            return;
        }
        Minecraft.player.inventory.currentItem = slot;
        PlayerHelper.mc.playerController.syncCurrentPlayItem();
    }

    float getAppleTime() {
        return this.AppleTimeWait.fValue;
    }

    float getPearlTime() {
        return this.PearlTimeWait.fValue;
    }

    float getChorusTime() {
        return this.ChorusUseWait.fValue;
    }

    public static boolean canCooldown() {
        return !(!PlayerHelper.get.actived || PlayerHelper.get.CooldownsCheckKT.bValue && (GuiBossOverlay.mapBossInfos2.isEmpty() && GuiBossOverlay.mapBossInfos2.values().stream().map(BossInfo::getName).map(ITextComponent::getFormattedText).filter(name -> name.contains("PVP")).filter(Objects::nonNull).toArray().length != 0 || PlayerHelper.mc.world.getScoreboard() != null && PlayerHelper.mc.world.getScoreboard().getTeamNames().stream().map(str -> str.toLowerCase()).anyMatch(str -> List.of((Object)"\u0442\u0435\u0440\u043a\u0430", (Object)"\u0431\u043e\u044f", (Object)"\u043f\u0440\u043e\u0442\u0438\u0432\u043d\u0438\u043a", (Object)"\u043f\u0432\u043f", (Object)"pvp").stream().anyMatch(bad -> str.contains((CharSequence)bad)))));
    }

    public static void trackApple() {
        checkApple = true;
        timerApple.reset();
    }

    public static void trackPearl() {
        checkPearl = true;
        timerPearl.reset();
    }

    public static void trackChorus() {
        checkChorus = true;
        timerChorus.reset();
    }

    @Override
    public void onRender2D(ScaledResolution sr) {
        boolean curChorus;
        boolean curPearl;
        boolean curApple;
        if (!(checkApple || checkPearl || checkChorus || (double)this.sizeApple > 0.03 || (double)this.sizePearl > 0.03 || (double)this.sizeChorus > 0.03)) {
            return;
        }
        boolean bl = curApple = checkApple;
        if (this.sizeApple != (float)curApple) {
            this.sizeApple = MathUtils.harp(this.sizeApple, (float)curApple, (float)Minecraft.frameTime * 0.0075f);
        }
        boolean bl2 = curPearl = checkPearl;
        if (this.sizePearl != (float)curPearl) {
            this.sizePearl = MathUtils.harp(this.sizePearl, (float)curPearl, (float)Minecraft.frameTime * 0.0075f);
        }
        boolean bl3 = curChorus = checkChorus;
        if (this.sizeChorus != (float)curChorus) {
            this.sizeChorus = MathUtils.harp(this.sizeChorus, (float)curChorus, (float)Minecraft.frameTime * 0.0075f);
        }
        float r = 10.0f;
        float x = (float)sr.getScaledWidth() / 2.0f - (r + 4.0f) * (this.sizeApple + this.sizePearl + this.sizeChorus);
        float y = (float)sr.getScaledHeight() - 130.0f;
        float y2 = y - (r + 4.0f) - this.sizeChorus * r;
        float timeApple = this.getAppleTime();
        float timePearl = this.getPearlTime();
        float timeChorus = this.getChorusTime();
        if ((double)this.sizeApple > 0.025) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(x += (r + 4.0f) * (1.0f + this.sizeApple) / 2.0f, y, 0.0f, 0.0f, this.sizeApple);
            RenderUtils.drawSmoothCircle(x, y, (r + 2.0f) * this.sizeApple, ColorUtils.getColor(60, 60, 60, 255.0f * this.sizeApple));
            RenderUtils.drawSmoothCircle(x, y, r * this.sizeApple, ColorUtils.getColor(11, 11, 11, 190.0f * this.sizeApple));
            RenderUtils.drawClientCircle(x, y, r + 1.25f, 362.0f - (float)timerApple.getTime() / (timeApple / 360.0f), 4.0f);
            RenderUtils.customScaledObject2D(x - 8.0f, y - 8.0f, 16.0f, 16.0f, 1.0f + this.sizeApple / 128.0f);
            RenderUtils.renderItem(new ItemStack(Items.GOLDEN_APPLE), x - 8.0f, y - 8.0f);
            GL11.glPopMatrix();
        }
        if ((double)this.sizePearl > 0.025) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(x += (r + 4.0f) * (1.0f + this.sizePearl) * (0.5f + this.sizeApple * 0.5f), y, 0.0f, 0.0f, this.sizePearl);
            RenderUtils.drawSmoothCircle(x, y, (r + 2.0f) * this.sizePearl, ColorUtils.getColor(60, 60, 60, 255.0f * this.sizePearl));
            RenderUtils.drawSmoothCircle(x, y, r * this.sizePearl, ColorUtils.getColor(11, 11, 11, 190.0f * this.sizePearl));
            RenderUtils.drawClientCircle(x, y, r + 1.25f, 362.0f - (float)timerPearl.getTime() / (timePearl / 360.0f), 4.0f);
            RenderUtils.customScaledObject2D(x - 8.0f, y - 8.0f, 16.0f, 16.0f, 1.0f + this.sizePearl / 128.0f);
            RenderUtils.renderItem(new ItemStack(Items.ENDER_PEARL), x - 8.0f, y - 8.0f);
            GL11.glPopMatrix();
        }
        if ((double)this.sizeChorus > 0.025) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(x += (r + 4.0f) * (1.0f + this.sizeChorus) * (0.5f + this.sizeApple * (1.0f - this.sizePearl) * 0.5f + this.sizePearl * 0.5f), y, 0.0f, 0.0f, this.sizeChorus);
            RenderUtils.drawSmoothCircle(x, y, (r + 2.0f) * this.sizeChorus, ColorUtils.getColor(60, 60, 60, 255.0f * this.sizeChorus));
            RenderUtils.drawSmoothCircle(x, y, r * this.sizeChorus, ColorUtils.getColor(11, 11, 11, 190.0f * this.sizeChorus));
            RenderUtils.drawClientCircle(x, y, r + 1.25f, 362.0f - (float)timerChorus.getTime() / (timeChorus / 360.0f), 4.0f);
            RenderUtils.customScaledObject2D(x - 8.0f, y - 8.0f, 16.0f, 16.0f, 1.0f + this.sizeChorus / 128.0f);
            RenderUtils.renderItem(new ItemStack(Items.CHORUS_FRUIT), x - 8.0f, y - 8.0f);
            GL11.glPopMatrix();
        }
    }

    static {
        timerApple = new TimerHelper();
        timerPearl = new TimerHelper();
        timerChorus = new TimerHelper();
        lastSlot = -1;
        ticks = 0;
        slotMemories = new ArrayList<SlotsMemory>();
        breakPos = null;
    }

    static class SlotsMemory {
        int slotAt;
        int slotTo;

        public SlotsMemory(int slotAt, int slotTo) {
            this.slotAt = slotAt;
            this.slotTo = slotTo;
        }
    }
}

