package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;

class CommandExecuteAt$1 implements ICommandSender {
    final /* synthetic */ Entity val$var3;
    final /* synthetic */ ICommandSender val$sender;
    final /* synthetic */ BlockPos val$var10;
    final /* synthetic */ double val$var4;
    final /* synthetic */ double val$var6;
    final /* synthetic */ double val$var8;
    
    @Override
    public String getName() {
        return this.val$var3.getName();
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.val$var3.getDisplayName();
    }
    
    @Override
    public void addChatMessage(final IChatComponent message) {
        this.val$sender.addChatMessage(message);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
        return this.val$sender.canCommandSenderUseCommand(permissionLevel, command);
    }
    
    @Override
    public BlockPos getPosition() {
        return this.val$var10;
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(this.val$var4, this.val$var6, this.val$var8);
    }
    
    @Override
    public World getEntityWorld() {
        return this.val$var3.worldObj;
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return this.val$var3;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        final MinecraftServer var1 = MinecraftServer.getServer();
        return var1 == null || var1.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
    }
    
    @Override
    public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
        this.val$var3.func_174794_a(p_174794_1_, p_174794_2_);
    }
}