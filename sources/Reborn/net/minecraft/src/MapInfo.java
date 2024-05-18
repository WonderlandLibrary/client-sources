package net.minecraft.src;

import java.util.*;

public class MapInfo
{
    public final EntityPlayer entityplayerObj;
    public int[] field_76209_b;
    public int[] field_76210_c;
    private int currentRandomNumber;
    private int ticksUntilPlayerLocationMapUpdate;
    private byte[] lastPlayerLocationOnMap;
    public int field_82569_d;
    private boolean field_82570_i;
    final MapData mapDataObj;
    
    public MapInfo(final MapData par1MapData, final EntityPlayer par2EntityPlayer) {
        this.mapDataObj = par1MapData;
        this.field_76209_b = new int[128];
        this.field_76210_c = new int[128];
        this.currentRandomNumber = 0;
        this.ticksUntilPlayerLocationMapUpdate = 0;
        this.field_82570_i = false;
        this.entityplayerObj = par2EntityPlayer;
        for (int var3 = 0; var3 < this.field_76209_b.length; ++var3) {
            this.field_76209_b[var3] = 0;
            this.field_76210_c[var3] = 127;
        }
    }
    
    public byte[] getPlayersOnMap(final ItemStack par1ItemStack) {
        if (!this.field_82570_i) {
            final byte[] var2 = { 2, this.mapDataObj.scale };
            this.field_82570_i = true;
            return var2;
        }
        if (--this.ticksUntilPlayerLocationMapUpdate < 0) {
            this.ticksUntilPlayerLocationMapUpdate = 4;
            final byte[] var2 = new byte[this.mapDataObj.playersVisibleOnMap.size() * 3 + 1];
            var2[0] = 1;
            int var3 = 0;
            for (final MapCoord var5 : this.mapDataObj.playersVisibleOnMap.values()) {
                var2[var3 * 3 + 1] = (byte)(var5.iconSize << 4 | (var5.iconRotation & 0xF));
                var2[var3 * 3 + 2] = var5.centerX;
                var2[var3 * 3 + 3] = var5.centerZ;
                ++var3;
            }
            boolean var6 = !par1ItemStack.isOnItemFrame();
            if (this.lastPlayerLocationOnMap != null && this.lastPlayerLocationOnMap.length == var2.length) {
                for (int var7 = 0; var7 < var2.length; ++var7) {
                    if (var2[var7] != this.lastPlayerLocationOnMap[var7]) {
                        var6 = false;
                        break;
                    }
                }
            }
            else {
                var6 = false;
            }
            if (!var6) {
                return this.lastPlayerLocationOnMap = var2;
            }
        }
        for (int var8 = 0; var8 < 1; ++var8) {
            final int var3 = this.currentRandomNumber++ * 11 % 128;
            if (this.field_76209_b[var3] >= 0) {
                final int var9 = this.field_76210_c[var3] - this.field_76209_b[var3] + 1;
                final int var7 = this.field_76209_b[var3];
                final byte[] var10 = new byte[var9 + 3];
                var10[0] = 0;
                var10[1] = (byte)var3;
                var10[2] = (byte)var7;
                for (int var11 = 0; var11 < var10.length - 3; ++var11) {
                    var10[var11 + 3] = this.mapDataObj.colors[(var11 + var7) * 128 + var3];
                }
                this.field_76210_c[var3] = -1;
                this.field_76209_b[var3] = -1;
                return var10;
            }
        }
        return null;
    }
}
