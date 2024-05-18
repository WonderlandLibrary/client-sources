package net.minecraft.tileentity;

import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class TileEntityMobSpawner extends TileEntity implements ITickable
{
    private static final String[] I;
    private final MobSpawnerBaseLogic spawnerLogic;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("6*\u0005\u001f\u001b55\u0010\r\u001b\u00113\u0005\u0004\u0006", "eZdhu");
    }
    
    @Override
    public void update() {
        this.spawnerLogic.updateSpawner();
    }
    
    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return this.spawnerLogic;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        nbtTagCompound.removeTag(TileEntityMobSpawner.I["".length()]);
        return new S35PacketUpdateTileEntity(this.pos, " ".length(), nbtTagCompound);
    }
    
    @Override
    public boolean func_183000_F() {
        return " ".length() != 0;
    }
    
    static {
        I();
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.spawnerLogic.writeToNBT(nbtTagCompound);
    }
    
    public TileEntityMobSpawner() {
        this.spawnerLogic = new MobSpawnerBaseLogic() {
            final TileEntityMobSpawner this$0;
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.addBlockEvent(this.this$0.pos, Blocks.mob_spawner, n, "".length());
            }
            
            @Override
            public BlockPos getSpawnerPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public void setRandomEntity(final WeightedRandomMinecart randomEntity) {
                super.setRandomEntity(randomEntity);
                if (this.getSpawnerWorld() != null) {
                    this.getSpawnerWorld().markBlockForUpdate(this.this$0.pos);
                }
            }
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
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
                    if (0 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.spawnerLogic.readFromNBT(nbtTagCompound);
    }
    
    @Override
    public boolean receiveClientEvent(final int delayToMin, final int n) {
        int n2;
        if (this.spawnerLogic.setDelayToMin(delayToMin)) {
            n2 = " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n2 = (super.receiveClientEvent(delayToMin, n) ? 1 : 0);
        }
        return n2 != 0;
    }
}
