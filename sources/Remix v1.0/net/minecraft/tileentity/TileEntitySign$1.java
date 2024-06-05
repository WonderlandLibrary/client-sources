package net.minecraft.tileentity;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;

class TileEntitySign$1 implements ICommandSender {
    @Override
    public String getName() {
        return "Sign";
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
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
        return TileEntitySign.this.worldObj;
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return false;
    }
    
    @Override
    public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
    }
}