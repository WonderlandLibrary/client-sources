// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import me.perry.mcdonalds.features.command.Command;
import me.perry.mcdonalds.util.BurrowUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class Burrow extends Module
{
    private final Setting<Integer> offset;
    private final Setting<Boolean> rotate;
    private final Setting<Mode> mode;
    private BlockPos originalPos;
    private int oldSlot;
    Block returnBlock;
    
    public Burrow() {
        super("Burrow", "TPs you into a block", Category.PLAYER, true, false, false);
        this.offset = (Setting<Integer>)this.register(new Setting("Offset", (T)3, (T)(-5), (T)5));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.OBBY));
        this.oldSlot = -1;
        this.returnBlock = null;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.originalPos = new BlockPos(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ);
        switch (this.mode.getValue()) {
            case OBBY: {
                this.returnBlock = Blocks.OBSIDIAN;
                break;
            }
            case ECHEST: {
                this.returnBlock = Blocks.ENDER_CHEST;
                break;
            }
            case EABypass: {
                this.returnBlock = (Block)Blocks.CHEST;
                break;
            }
        }
        if (Burrow.mc.world.getBlockState(new BlockPos(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ)).getBlock().equals(this.returnBlock) || this.intersectsWithEntity(this.originalPos)) {
            this.toggle();
            return;
        }
        this.oldSlot = Burrow.mc.player.inventory.currentItem;
    }
    
    @Override
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case OBBY: {
                if (BurrowUtil.findHotbarBlock(BlockObsidian.class) == -1) {
                    Command.sendMessage("Can't find obby in hotbar!");
                    this.toggle();
                    break;
                }
                break;
            }
            case ECHEST: {
                if (BurrowUtil.findHotbarBlock(BlockEnderChest.class) == -1) {
                    Command.sendMessage("Can't find echest in hotbar!");
                    this.toggle();
                    break;
                }
                break;
            }
            case EABypass: {
                if (BurrowUtil.findHotbarBlock(BlockChest.class) == -1) {
                    Command.sendMessage("Can't find chest in hotbar!");
                    this.toggle();
                    break;
                }
                break;
            }
        }
        BurrowUtil.switchToSlot((this.mode.getValue() == Mode.OBBY) ? BurrowUtil.findHotbarBlock(BlockObsidian.class) : ((this.mode.getValue() == Mode.ECHEST) ? BurrowUtil.findHotbarBlock(BlockEnderChest.class) : BurrowUtil.findHotbarBlock(BlockChest.class)));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 0.41999998688698, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 0.7531999805211997, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 1.00133597911214, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 1.16610926093821, Burrow.mc.player.posZ, true));
        BurrowUtil.placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + this.offset.getValue(), Burrow.mc.player.posZ, false));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        Burrow.mc.player.setSneaking(false);
        BurrowUtil.switchToSlot(this.oldSlot);
        this.toggle();
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : Burrow.mc.world.loadedEntityList) {
            if (entity.equals((Object)Burrow.mc.player)) {
                continue;
            }
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public enum Mode
    {
        OBBY, 
        ECHEST, 
        EABypass;
    }
}
