package net.minecraft.entity;

import net.minecraft.entity.item.*;
import net.minecraft.command.server.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class EntityMinecartCommandBlock extends EntityMinecart
{
    private final CommandBlockLogic commandBlockLogic;
    private int activatorRailCooldown;
    private static final String[] I;
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.ticksExisted - this.activatorRailCooldown >= (0x81 ^ 0x85)) {
            this.getCommandBlockLogic().trigger(this.worldObj);
            this.activatorRailCooldown = this.ticksExisted;
        }
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.command_block.getDefaultState();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        this.commandBlockLogic.writeDataToNBT(nbtTagCompound);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(0x21 ^ 0x36, EntityMinecartCommandBlock.I["".length()]);
        this.getDataWatcher().addObject(0xAF ^ 0xB7, EntityMinecartCommandBlock.I[" ".length()]);
    }
    
    public EntityMinecartCommandBlock(final World world) {
        super(world);
        this.commandBlockLogic = new CommandBlockLogic() {
            final EntityMinecartCommandBlock this$0;
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos getPosition() {
                return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ);
            }
            
            @Override
            public int func_145751_f() {
                return " ".length();
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.this$0;
            }
            
            @Override
            public void updateCommand() {
                this.this$0.getDataWatcher().updateObject(0x37 ^ 0x20, this.getCommand());
                this.this$0.getDataWatcher().updateObject(0xAA ^ 0xB2, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.getEntityId());
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
            }
        };
        this.activatorRailCooldown = "".length();
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        this.commandBlockLogic.tryOpenEditCommandBlock(entityPlayer);
        return "".length() != 0;
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.COMMAND_BLOCK;
    }
    
    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }
    
    public EntityMinecartCommandBlock(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.commandBlockLogic = new CommandBlockLogic() {
            final EntityMinecartCommandBlock this$0;
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos getPosition() {
                return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ);
            }
            
            @Override
            public int func_145751_f() {
                return " ".length();
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.this$0;
            }
            
            @Override
            public void updateCommand() {
                this.this$0.getDataWatcher().updateObject(0x37 ^ 0x20, this.getCommand());
                this.this$0.getDataWatcher().updateObject(0xAA ^ 0xB2, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.getEntityId());
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
            }
        };
        this.activatorRailCooldown = "".length();
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.commandBlockLogic.readDataFromNBT(nbtTagCompound);
        this.getDataWatcher().updateObject(0x97 ^ 0x80, this.getCommandBlockLogic().getCommand());
        this.getDataWatcher().updateObject(0x2B ^ 0x33, IChatComponent.Serializer.componentToJson(this.getCommandBlockLogic().getLastOutput()));
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("", "yDpmF");
        EntityMinecartCommandBlock.I[" ".length()] = I("", "qokTZ");
    }
    
    @Override
    public void onDataWatcherUpdate(final int n) {
        super.onDataWatcherUpdate(n);
        if (n == (0x4A ^ 0x52)) {
            try {
                this.commandBlockLogic.setLastOutput(IChatComponent.Serializer.jsonToComponent(this.getDataWatcher().getWatchableObjectString(0x7A ^ 0x62)));
                "".length();
                if (4 < 2) {
                    throw null;
                }
                return;
            }
            catch (Throwable t) {
                "".length();
                if (3 == 4) {
                    throw null;
                }
                return;
            }
        }
        if (n == (0x58 ^ 0x4F)) {
            this.commandBlockLogic.setCommand(this.getDataWatcher().getWatchableObjectString(0xA ^ 0x1D));
        }
    }
    
    static {
        I();
    }
}
