package net.minecraft.src;

import java.util.*;

public class MapData extends WorldSavedData
{
    public int xCenter;
    public int zCenter;
    public byte dimension;
    public byte scale;
    public byte[] colors;
    public List playersArrayList;
    private Map playersHashMap;
    public Map playersVisibleOnMap;
    
    public MapData(final String par1Str) {
        super(par1Str);
        this.colors = new byte[16384];
        this.playersArrayList = new ArrayList();
        this.playersHashMap = new HashMap();
        this.playersVisibleOnMap = new LinkedHashMap();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.dimension = par1NBTTagCompound.getByte("dimension");
        this.xCenter = par1NBTTagCompound.getInteger("xCenter");
        this.zCenter = par1NBTTagCompound.getInteger("zCenter");
        this.scale = par1NBTTagCompound.getByte("scale");
        if (this.scale < 0) {
            this.scale = 0;
        }
        if (this.scale > 4) {
            this.scale = 4;
        }
        final short var2 = par1NBTTagCompound.getShort("width");
        final short var3 = par1NBTTagCompound.getShort("height");
        if (var2 == 128 && var3 == 128) {
            this.colors = par1NBTTagCompound.getByteArray("colors");
        }
        else {
            final byte[] var4 = par1NBTTagCompound.getByteArray("colors");
            this.colors = new byte[16384];
            final int var5 = (128 - var2) / 2;
            final int var6 = (128 - var3) / 2;
            for (int var7 = 0; var7 < var3; ++var7) {
                final int var8 = var7 + var6;
                if (var8 >= 0 || var8 < 128) {
                    for (int var9 = 0; var9 < var2; ++var9) {
                        final int var10 = var9 + var5;
                        if (var10 >= 0 || var10 < 128) {
                            this.colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("dimension", this.dimension);
        par1NBTTagCompound.setInteger("xCenter", this.xCenter);
        par1NBTTagCompound.setInteger("zCenter", this.zCenter);
        par1NBTTagCompound.setByte("scale", this.scale);
        par1NBTTagCompound.setShort("width", (short)128);
        par1NBTTagCompound.setShort("height", (short)128);
        par1NBTTagCompound.setByteArray("colors", this.colors);
    }
    
    public void updateVisiblePlayers(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        if (!this.playersHashMap.containsKey(par1EntityPlayer)) {
            final MapInfo var3 = new MapInfo(this, par1EntityPlayer);
            this.playersHashMap.put(par1EntityPlayer, var3);
            this.playersArrayList.add(var3);
        }
        if (!par1EntityPlayer.inventory.hasItemStack(par2ItemStack)) {
            this.playersVisibleOnMap.remove(par1EntityPlayer.getCommandSenderName());
        }
        for (int var4 = 0; var4 < this.playersArrayList.size(); ++var4) {
            final MapInfo var5 = this.playersArrayList.get(var4);
            if (!var5.entityplayerObj.isDead && (var5.entityplayerObj.inventory.hasItemStack(par2ItemStack) || par2ItemStack.isOnItemFrame())) {
                if (!par2ItemStack.isOnItemFrame() && var5.entityplayerObj.dimension == this.dimension) {
                    this.func_82567_a(0, var5.entityplayerObj.worldObj, var5.entityplayerObj.getCommandSenderName(), var5.entityplayerObj.posX, var5.entityplayerObj.posZ, var5.entityplayerObj.rotationYaw);
                }
            }
            else {
                this.playersHashMap.remove(var5.entityplayerObj);
                this.playersArrayList.remove(var5);
            }
        }
        if (par2ItemStack.isOnItemFrame()) {
            this.func_82567_a(1, par1EntityPlayer.worldObj, "frame-" + par2ItemStack.getItemFrame().entityId, par2ItemStack.getItemFrame().xPosition, par2ItemStack.getItemFrame().zPosition, par2ItemStack.getItemFrame().hangingDirection * 90);
        }
    }
    
    private void func_82567_a(int par1, final World par2World, final String par3Str, final double par4, final double par6, double par8) {
        final int var10 = 1 << this.scale;
        final float var11 = (float)(par4 - this.xCenter) / var10;
        final float var12 = (float)(par6 - this.zCenter) / var10;
        byte var13 = (byte)(var11 * 2.0f + 0.5);
        byte var14 = (byte)(var12 * 2.0f + 0.5);
        final byte var15 = 63;
        byte var16;
        if (var11 >= -var15 && var12 >= -var15 && var11 <= var15 && var12 <= var15) {
            par8 += ((par8 < 0.0) ? -8.0 : 8.0);
            var16 = (byte)(par8 * 16.0 / 360.0);
            if (this.dimension < 0) {
                final int var17 = (int)(par2World.getWorldInfo().getWorldTime() / 10L);
                var16 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 0xF);
            }
        }
        else {
            if (Math.abs(var11) >= 320.0f || Math.abs(var12) >= 320.0f) {
                this.playersVisibleOnMap.remove(par3Str);
                return;
            }
            par1 = 6;
            var16 = 0;
            if (var11 <= -var15) {
                var13 = (byte)(var15 * 2 + 2.5);
            }
            if (var12 <= -var15) {
                var14 = (byte)(var15 * 2 + 2.5);
            }
            if (var11 >= var15) {
                var13 = (byte)(var15 * 2 + 1);
            }
            if (var12 >= var15) {
                var14 = (byte)(var15 * 2 + 1);
            }
        }
        this.playersVisibleOnMap.put(par3Str, new MapCoord(this, (byte)par1, var13, var14, var16));
    }
    
    public byte[] getUpdatePacketData(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final MapInfo var4 = this.playersHashMap.get(par3EntityPlayer);
        return (byte[])((var4 == null) ? null : var4.getPlayersOnMap(par1ItemStack));
    }
    
    public void setColumnDirty(final int par1, final int par2, final int par3) {
        super.markDirty();
        for (int var4 = 0; var4 < this.playersArrayList.size(); ++var4) {
            final MapInfo var5 = this.playersArrayList.get(var4);
            if (var5.field_76209_b[par1] < 0 || var5.field_76209_b[par1] > par2) {
                var5.field_76209_b[par1] = par2;
            }
            if (var5.field_76210_c[par1] < 0 || var5.field_76210_c[par1] < par3) {
                var5.field_76210_c[par1] = par3;
            }
        }
    }
    
    public void updateMPMapData(final byte[] par1ArrayOfByte) {
        if (par1ArrayOfByte[0] == 0) {
            final int var2 = par1ArrayOfByte[1] & 0xFF;
            final int var3 = par1ArrayOfByte[2] & 0xFF;
            for (int var4 = 0; var4 < par1ArrayOfByte.length - 3; ++var4) {
                this.colors[(var4 + var3) * 128 + var2] = par1ArrayOfByte[var4 + 3];
            }
            this.markDirty();
        }
        else if (par1ArrayOfByte[0] == 1) {
            this.playersVisibleOnMap.clear();
            for (int var2 = 0; var2 < (par1ArrayOfByte.length - 1) / 3; ++var2) {
                final byte var5 = (byte)(par1ArrayOfByte[var2 * 3 + 1] >> 4);
                final byte var6 = par1ArrayOfByte[var2 * 3 + 2];
                final byte var7 = par1ArrayOfByte[var2 * 3 + 3];
                final byte var8 = (byte)(par1ArrayOfByte[var2 * 3 + 1] & 0xF);
                this.playersVisibleOnMap.put("icon-" + var2, new MapCoord(this, var5, var6, var7, var8));
            }
        }
        else if (par1ArrayOfByte[0] == 2) {
            this.scale = par1ArrayOfByte[1];
        }
    }
    
    public MapInfo func_82568_a(final EntityPlayer par1EntityPlayer) {
        MapInfo var2 = this.playersHashMap.get(par1EntityPlayer);
        if (var2 == null) {
            var2 = new MapInfo(this, par1EntityPlayer);
            this.playersHashMap.put(par1EntityPlayer, var2);
            this.playersArrayList.add(var2);
        }
        return var2;
    }
}
