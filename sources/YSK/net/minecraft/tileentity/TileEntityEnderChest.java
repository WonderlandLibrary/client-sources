package net.minecraft.tileentity;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class TileEntityEnderChest extends TileEntity implements ITickable
{
    public float lidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;
    private static final String[] I;
    public float prevLidAngle;
    
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int numPlayersUsing) {
        if (n == " ".length()) {
            this.numPlayersUsing = numPlayersUsing;
            return " ".length() != 0;
        }
        return super.receiveClientEvent(n, numPlayersUsing);
    }
    
    public void closeChest() {
        this.numPlayersUsing -= " ".length();
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, " ".length(), this.numPlayersUsing);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001a\u0014\f\u0006\u000b\u0005[\u0001\n\u0001\u001b\u0001\r\u0012\u0001\u0006", "hubbd");
        TileEntityEnderChest.I[" ".length()] = I("5\u0005\u001f\t6*J\u0012\u0005<4\u0010\u0012\u000164\u0001\u0015", "GdqmY");
    }
    
    @Override
    public void update() {
        final int ticksSinceSync = this.ticksSinceSync + " ".length();
        this.ticksSinceSync = ticksSinceSync;
        if (ticksSinceSync % (0x79 ^ 0x6D) * (0x5F ^ 0x5B) == 0) {
            this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, " ".length(), this.numPlayersUsing);
        }
        this.prevLidAngle = this.lidAngle;
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        final float n = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TileEntityEnderChest.I["".length()], 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0f) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0f)) {
            final float lidAngle = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += n;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                this.lidAngle -= n;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float n2 = 0.5f;
            if (this.lidAngle < n2 && lidAngle >= n2) {
                this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TileEntityEnderChest.I[" ".length()], 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
    
    public void openChest() {
        this.numPlayersUsing += " ".length();
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, " ".length(), this.numPlayersUsing);
    }
    
    public boolean canBeUsed(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
}
