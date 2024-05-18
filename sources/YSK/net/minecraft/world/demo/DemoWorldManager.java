package net.minecraft.world.demo;

import net.minecraft.server.management.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class DemoWorldManager extends ItemInWorldManager
{
    private int field_73102_f;
    private boolean field_73105_c;
    private boolean demoTimeExpired;
    private static final String[] I;
    private int field_73104_e;
    
    @Override
    public void onBlockClicked(final BlockPos blockPos, final EnumFacing enumFacing) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            super.onBlockClicked(blockPos, enumFacing);
        }
    }
    
    public DemoWorldManager(final World world) {
        super(world);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateBlockRemoving() {
        super.updateBlockRemoving();
        this.field_73102_f += " ".length();
        final long totalWorldTime = this.theWorld.getTotalWorldTime();
        final long n = totalWorldTime / 24000L + 1L;
        if (!this.field_73105_c && this.field_73102_f > (0x6E ^ 0x7A)) {
            this.field_73105_c = (" ".length() != 0);
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x87 ^ 0x82, 0.0f));
        }
        int demoTimeExpired;
        if (totalWorldTime > 120500L) {
            demoTimeExpired = " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            demoTimeExpired = "".length();
        }
        this.demoTimeExpired = (demoTimeExpired != 0);
        if (this.demoTimeExpired) {
            this.field_73104_e += " ".length();
        }
        if (totalWorldTime % 24000L == 500L) {
            if (n <= 6L) {
                this.thisPlayerMP.addChatMessage(new ChatComponentTranslation(DemoWorldManager.I["".length()] + n, new Object["".length()]));
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
        }
        else if (n == 1L) {
            if (totalWorldTime == 100L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x93 ^ 0x96, 101.0f));
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else if (totalWorldTime == 175L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x29 ^ 0x2C, 102.0f));
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (totalWorldTime == 250L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x5B ^ 0x5E, 103.0f));
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
        }
        else if (n == 5L && totalWorldTime % 24000L == 22000L) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation(DemoWorldManager.I[" ".length()], new Object["".length()]));
        }
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(").\u0017\"V)*\u0003c", "MKzMx");
        DemoWorldManager.I[" ".length()] = I("\u0007/\u0003?a\u0007+\u0017~8\u00028\u00009!\u0004", "cJnPO");
        DemoWorldManager.I["  ".length()] = I("(3+;z>3+=:(34", "LVFTT");
    }
    
    private void sendDemoReminder() {
        if (this.field_73104_e > (0x7D ^ 0x19)) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation(DemoWorldManager.I["  ".length()], new Object["".length()]));
            this.field_73104_e = "".length();
        }
    }
    
    @Override
    public boolean tryUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return "".length() != 0;
        }
        return super.tryUseItem(entityPlayer, world, itemStack);
    }
    
    @Override
    public void blockRemoving(final BlockPos blockPos) {
        if (!this.demoTimeExpired) {
            super.blockRemoving(blockPos);
        }
    }
    
    @Override
    public boolean activateBlockOrUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return "".length() != 0;
        }
        return super.activateBlockOrUseItem(entityPlayer, world, itemStack, blockPos, enumFacing, n, n2, n3);
    }
    
    @Override
    public boolean tryHarvestBlock(final BlockPos blockPos) {
        int n;
        if (this.demoTimeExpired) {
            n = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n = (super.tryHarvestBlock(blockPos) ? 1 : 0);
        }
        return n != 0;
    }
    
    static {
        I();
    }
}
