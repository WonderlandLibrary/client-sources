package net.minecraft.tileentity;

import net.minecraft.command.server.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.command.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class TileEntityCommandBlock extends TileEntity
{
    private final CommandBlockLogic commandBlockLogic;
    
    @Override
    public boolean func_183000_F() {
        return " ".length() != 0;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, "  ".length(), nbtTagCompound);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.commandBlockLogic.writeDataToNBT(nbtTagCompound);
    }
    
    public CommandResultStats getCommandResultStats() {
        return this.commandBlockLogic.getCommandResultStats();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.commandBlockLogic.readDataFromNBT(nbtTagCompound);
    }
    
    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }
    
    public TileEntityCommandBlock() {
        this.commandBlockLogic = new CommandBlockLogic() {
            final TileEntityCommandBlock this$0;
            
            @Override
            public void setCommand(final String command) {
                super.setCommand(command);
                this.this$0.markDirty();
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
                    if (!true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.pos.getX());
                byteBuf.writeInt(this.this$0.pos.getY());
                byteBuf.writeInt(this.this$0.pos.getZ());
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public int func_145751_f() {
                return "".length();
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public void updateCommand() {
                this.this$0.getWorld().markBlockForUpdate(this.this$0.pos);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.getWorld();
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
        };
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
