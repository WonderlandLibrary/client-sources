package net.minecraft.entity;

import net.minecraft.command.server.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

class EntityMinecartCommandBlock$1 extends CommandBlockLogic {
    @Override
    public void func_145756_e() {
        EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, this.getCustomName());
        EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
    }
    
    @Override
    public int func_145751_f() {
        return 1;
    }
    
    @Override
    public void func_145757_a(final ByteBuf p_145757_1_) {
        p_145757_1_.writeInt(EntityMinecartCommandBlock.this.getEntityId());
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY + 0.5, EntityMinecartCommandBlock.this.posZ);
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY, EntityMinecartCommandBlock.this.posZ);
    }
    
    @Override
    public World getEntityWorld() {
        return EntityMinecartCommandBlock.this.worldObj;
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return EntityMinecartCommandBlock.this;
    }
}