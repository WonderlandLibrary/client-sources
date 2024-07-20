/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventPostPlace;
import ru.govno.client.event.events.EventRotationJump;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.OffHand;
import ru.govno.client.module.modules.Surround;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.BlockUtils;

public class AutoCrystal
extends Module {
    Settings CanUseInventory = new Settings("CanUseInventory", false, (Module)this);
    Settings NoSuicide;
    BlockPos pos;
    int ticks;
    float moveYaw = -1.2312312E8f;

    public AutoCrystal() {
        super("AutoCrystal", 0, Module.Category.COMBAT);
        this.settings.add(this.CanUseInventory);
        this.NoSuicide = new Settings("NoSuicide", true, (Module)this);
        this.settings.add(this.NoSuicide);
    }

    @Override
    public String getDisplayName() {
        return this.name + this.getSuff() + (this.CanUseInventory.bValue ? "INV" : "HOT");
    }

    boolean cancelPos(BlockPos pos) {
        return pos != null && this.NoSuicide.bValue && (OffHand.totemTaken || pos.equals(BlockUtils.getEntityBlockPos(this.getMe()).down()) || BlockUtils.canPosBeSeenEntity(pos, (Entity)this.getMe(), BlockUtils.bodyElement.FEET) && (double)pos.getY() < this.getMe().posY - 0.6 + this.getMe().motionY * 1.37) || !Surround.toPlacePoses.isEmpty() && Surround.toPlacePoses.stream().anyMatch(pos2 -> pos2.equals(pos));
    }

    boolean switchAndPlace(BlockPos pos) {
        if (pos == null) {
            return false;
        }
        EnumHand hand = null;
        if (Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            hand = EnumHand.OFF_HAND;
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, hand));
            return true;
        }
        int crystalInvSlot = InventoryUtil.getItemInInv(Items.END_CRYSTAL);
        ItemStack crystalStack = Minecraft.player.inventory.getStackInSlot(crystalInvSlot);
        hand = EnumHand.MAIN_HAND;
        if (crystalInvSlot != -1) {
            if (crystalInvSlot < 9) {
                int oldSlot = Minecraft.player.inventory.currentItem;
                Minecraft.player.inventory.currentItem = InventoryUtil.getItemInHotbar(Items.END_CRYSTAL);
                AutoCrystal.mc.playerController.processRightClickBlock(Minecraft.player, AutoCrystal.mc.world, pos, EnumFacing.UP, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), hand);
                Minecraft.player.inventory.currentItem = oldSlot;
                return true;
            }
            if (this.CanUseInventory.bValue) {
                AutoCrystal.mc.playerController.windowClick(0, crystalInvSlot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
                Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, hand));
                AutoCrystal.mc.playerController.windowClick(0, crystalInvSlot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
                return true;
            }
        }
        return false;
    }

    @EventTarget
    public void onPlacePost(EventPostPlace event) {
        if (!this.actived) {
            return;
        }
        if ((event.getStack().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN) || event.getStack().getItem() == Item.getItemFromBlock(Blocks.BEDROCK)) && !this.cancelPos(event.getPosition()) && this.pos == null && this.switchAndPlace(event.getPosition())) {
            this.pos = event.getPosition();
            this.ticks = 10;
        }
    }

    @EventTarget
    public void onUpdatePlayer(EventPlayerMotionUpdate event) {
        if (this.pos == null) {
            return;
        }
        if (this.ticks > 0) {
            --this.ticks;
        } else {
            this.pos = null;
            this.moveYaw = -1.2312312E8f;
            return;
        }
        this.moveYaw = -1.2312312E8f;
        EntityEnderCrystal crystal = AutoCrystal.mc.world.getLoadedEntityList().stream().filter(e -> e instanceof EntityEnderCrystal).map(e -> (EntityEnderCrystal)e).filter(e -> this.getMe().getDistanceToEntity((Entity)e) < 4.0f).filter(Objects::nonNull).filter(e -> BlockUtils.getEntityBlockPos(e).equals(this.pos.up())).findAny().orElse(null);
        if (crystal == null) {
            return;
        }
        if (crystal.ticksExisted <= 1) {
            float[] rotate = RotationUtil.getNeededFacing(crystal.getPositionVector().addVector(0.0, 1.0, 0.0), false, this.getMe(), false);
            event.setYaw(rotate[0]);
            event.setPitch(rotate[1]);
            Minecraft.player.rotationYawHead = event.getYaw();
            Minecraft.player.renderYawOffset = event.getYaw();
            Minecraft.player.rotationPitchHead = event.getPitch();
            this.moveYaw = event.getYaw();
            if (crystal.ticksExisted == 1) {
                AutoCrystal.mc.playerController.attackEntity(Minecraft.player, crystal);
                Minecraft.player.swingArm();
                this.pos = null;
            }
        }
    }

    @EventTarget
    public void onSilentStrafe(EventRotationStrafe event) {
        if (this.moveYaw != -1.2312312E8f && this.pos != null && this.ticks > 0) {
            event.setYaw(this.moveYaw);
        }
    }

    @EventTarget
    public void onSilentJump(EventRotationJump event) {
        if (this.moveYaw != -1.2312312E8f && this.pos != null && this.ticks > 0) {
            event.setYaw(this.moveYaw);
        }
    }

    private final EntityPlayer getMe() {
        return FreeCam.fakePlayer != null && FreeCam.get.actived ? FreeCam.fakePlayer : Minecraft.player;
    }
}

