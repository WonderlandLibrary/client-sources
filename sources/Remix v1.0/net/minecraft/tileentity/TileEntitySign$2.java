package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;

class TileEntitySign$2 implements ICommandSender {
    final /* synthetic */ EntityPlayer val$p_174882_1_;
    
    @Override
    public String getName() {
        return this.val$p_174882_1_.getName();
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.val$p_174882_1_.getDisplayName();
    }
    
    @Override
    public void addChatMessage(final IChatComponent message) {
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
        return true;
    }
    
    @Override
    public BlockPos getPosition() {
        return TileEntitySign.this.pos;
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(TileEntitySign.this.pos.getX() + 0.5, TileEntitySign.this.pos.getY() + 0.5, TileEntitySign.this.pos.getZ() + 0.5);
    }
    
    @Override
    public World getEntityWorld() {
        return this.val$p_174882_1_.getEntityWorld();
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return this.val$p_174882_1_;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return false;
    }
    
    @Override
    public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
        TileEntitySign.access$000(TileEntitySign.this).func_179672_a(this, p_174794_1_, p_174794_2_);
    }
}