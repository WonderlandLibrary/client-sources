package net.minecraft.tileentity;

import net.minecraft.command.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.entity.*;

class TileEntityCommandBlock$1 extends CommandBlockLogic {
    @Override
    public BlockPos getPosition() {
        return TileEntityCommandBlock.this.pos;
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(TileEntityCommandBlock.this.pos.getX() + 0.5, TileEntityCommandBlock.this.pos.getY() + 0.5, TileEntityCommandBlock.this.pos.getZ() + 0.5);
    }
    
    @Override
    public World getEntityWorld() {
        return TileEntityCommandBlock.this.getWorld();
    }
    
    @Override
    public void setCommand(final String p_145752_1_) {
        super.setCommand(p_145752_1_);
        TileEntityCommandBlock.this.markDirty();
    }
    
    @Override
    public void func_145756_e() {
        TileEntityCommandBlock.this.getWorld().markBlockForUpdate(TileEntityCommandBlock.this.pos);
    }
    
    @Override
    public int func_145751_f() {
        return 0;
    }
    
    @Override
    public void func_145757_a(final ByteBuf p_145757_1_) {
        p_145757_1_.writeInt(TileEntityCommandBlock.this.pos.getX());
        p_145757_1_.writeInt(TileEntityCommandBlock.this.pos.getY());
        p_145757_1_.writeInt(TileEntityCommandBlock.this.pos.getZ());
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
}