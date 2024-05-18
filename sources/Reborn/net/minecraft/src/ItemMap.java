package net.minecraft.src;

import java.util.*;

public class ItemMap extends ItemMapBase
{
    protected ItemMap(final int par1) {
        super(par1);
        this.setHasSubtypes(true);
    }
    
    public static MapData getMPMapData(final short par0, final World par1World) {
        final String var2 = "map_" + par0;
        MapData var3 = (MapData)par1World.loadItemData(MapData.class, var2);
        if (var3 == null) {
            var3 = new MapData(var2);
            par1World.setItemData(var2, var3);
        }
        return var3;
    }
    
    public MapData getMapData(final ItemStack par1ItemStack, final World par2World) {
        String var3 = "map_" + par1ItemStack.getItemDamage();
        MapData var4 = (MapData)par2World.loadItemData(MapData.class, var3);
        if (var4 == null && !par2World.isRemote) {
            par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
            var3 = "map_" + par1ItemStack.getItemDamage();
            var4 = new MapData(var3);
            var4.scale = 3;
            final int var5 = 128 * (1 << var4.scale);
            var4.xCenter = Math.round(par2World.getWorldInfo().getSpawnX() / var5) * var5;
            var4.zCenter = Math.round(par2World.getWorldInfo().getSpawnZ() / var5) * var5;
            var4.dimension = (byte)par2World.provider.dimensionId;
            var4.markDirty();
            par2World.setItemData(var3, var4);
        }
        return var4;
    }
    
    public void updateMapData(final World par1World, final Entity par2Entity, final MapData par3MapData) {
        if (par1World.provider.dimensionId == par3MapData.dimension && par2Entity instanceof EntityPlayer) {
            final short var4 = 128;
            final short var5 = 128;
            final int var6 = 1 << par3MapData.scale;
            final int var7 = par3MapData.xCenter;
            final int var8 = par3MapData.zCenter;
            final int var9 = MathHelper.floor_double(par2Entity.posX - var7) / var6 + var4 / 2;
            final int var10 = MathHelper.floor_double(par2Entity.posZ - var8) / var6 + var5 / 2;
            int var11 = 128 / var6;
            if (par1World.provider.hasNoSky) {
                var11 /= 2;
            }
            final MapInfo func_82568_a;
            final MapInfo var12 = func_82568_a = par3MapData.func_82568_a((EntityPlayer)par2Entity);
            ++func_82568_a.field_82569_d;
            for (int var13 = var9 - var11 + 1; var13 < var9 + var11; ++var13) {
                if ((var13 & 0xF) == (var12.field_82569_d & 0xF)) {
                    int var14 = 255;
                    int var15 = 0;
                    double var16 = 0.0;
                    for (int var17 = var10 - var11 - 1; var17 < var10 + var11; ++var17) {
                        if (var13 >= 0 && var17 >= -1 && var13 < var4 && var17 < var5) {
                            final int var18 = var13 - var9;
                            final int var19 = var17 - var10;
                            final boolean var20 = var18 * var18 + var19 * var19 > (var11 - 2) * (var11 - 2);
                            final int var21 = (var7 / var6 + var13 - var4 / 2) * var6;
                            final int var22 = (var8 / var6 + var17 - var5 / 2) * var6;
                            final int[] var23 = new int[256];
                            final Chunk var24 = par1World.getChunkFromBlockCoords(var21, var22);
                            if (!var24.isEmpty()) {
                                final int var25 = var21 & 0xF;
                                final int var26 = var22 & 0xF;
                                int var27 = 0;
                                double var28 = 0.0;
                                if (par1World.provider.hasNoSky) {
                                    int var29 = var21 + var22 * 231871;
                                    var29 = var29 * var29 * 31287121 + var29 * 11;
                                    if ((var29 >> 20 & 0x1) == 0x0) {
                                        final int[] array = var23;
                                        final int blockID = Block.dirt.blockID;
                                        array[blockID] += 10;
                                    }
                                    else {
                                        final int[] array2 = var23;
                                        final int blockID2 = Block.stone.blockID;
                                        array2[blockID2] += 10;
                                    }
                                    var28 = 100.0;
                                }
                                else {
                                    for (int var29 = 0; var29 < var6; ++var29) {
                                        for (int var30 = 0; var30 < var6; ++var30) {
                                            int var31 = var24.getHeightValue(var29 + var25, var30 + var26) + 1;
                                            int var32 = 0;
                                            if (var31 > 1) {
                                                boolean var33;
                                                do {
                                                    var33 = true;
                                                    var32 = var24.getBlockID(var29 + var25, var31 - 1, var30 + var26);
                                                    if (var32 == 0) {
                                                        var33 = false;
                                                    }
                                                    else if (var31 > 0 && var32 > 0 && Block.blocksList[var32].blockMaterial.materialMapColor == MapColor.airColor) {
                                                        var33 = false;
                                                    }
                                                    if (!var33) {
                                                        if (--var31 <= 0) {
                                                            break;
                                                        }
                                                        var32 = var24.getBlockID(var29 + var25, var31 - 1, var30 + var26);
                                                    }
                                                } while (var31 > 0 && !var33);
                                                if (var31 > 0 && var32 != 0 && Block.blocksList[var32].blockMaterial.isLiquid()) {
                                                    int var34 = var31 - 1;
                                                    final boolean var35 = false;
                                                    int var36;
                                                    do {
                                                        var36 = var24.getBlockID(var29 + var25, var34--, var30 + var26);
                                                        ++var27;
                                                    } while (var34 > 0 && var36 != 0 && Block.blocksList[var36].blockMaterial.isLiquid());
                                                }
                                            }
                                            var28 += var31 / (var6 * var6);
                                            final int[] array3 = var23;
                                            final int n = var32;
                                            ++array3[n];
                                        }
                                    }
                                }
                                var27 /= var6 * var6;
                                int var29 = 0;
                                int var30 = 0;
                                for (int var31 = 0; var31 < 256; ++var31) {
                                    if (var23[var31] > var29) {
                                        var30 = var31;
                                        var29 = var23[var31];
                                    }
                                }
                                double var37 = (var28 - var16) * 4.0 / (var6 + 4) + ((var13 + var17 & 0x1) - 0.5) * 0.4;
                                byte var38 = 1;
                                if (var37 > 0.6) {
                                    var38 = 2;
                                }
                                if (var37 < -0.6) {
                                    var38 = 0;
                                }
                                int var34 = 0;
                                if (var30 > 0) {
                                    final MapColor var39 = Block.blocksList[var30].blockMaterial.materialMapColor;
                                    if (var39 == MapColor.waterColor) {
                                        var37 = var27 * 0.1 + (var13 + var17 & 0x1) * 0.2;
                                        var38 = 1;
                                        if (var37 < 0.5) {
                                            var38 = 2;
                                        }
                                        if (var37 > 0.9) {
                                            var38 = 0;
                                        }
                                    }
                                    var34 = var39.colorIndex;
                                }
                                var16 = var28;
                                if (var17 >= 0 && var18 * var18 + var19 * var19 < var11 * var11 && (!var20 || (var13 + var17 & 0x1) != 0x0)) {
                                    final byte var40 = par3MapData.colors[var13 + var17 * var4];
                                    final byte var41 = (byte)(var34 * 4 + var38);
                                    if (var40 != var41) {
                                        if (var14 > var17) {
                                            var14 = var17;
                                        }
                                        if (var15 < var17) {
                                            var15 = var17;
                                        }
                                        par3MapData.colors[var13 + var17 * var4] = var41;
                                    }
                                }
                            }
                        }
                    }
                    if (var14 <= var15) {
                        par3MapData.setColumnDirty(var13, var14, var15);
                    }
                }
            }
        }
    }
    
    @Override
    public void onUpdate(final ItemStack par1ItemStack, final World par2World, final Entity par3Entity, final int par4, final boolean par5) {
        if (!par2World.isRemote) {
            final MapData var6 = this.getMapData(par1ItemStack, par2World);
            if (par3Entity instanceof EntityPlayer) {
                final EntityPlayer var7 = (EntityPlayer)par3Entity;
                var6.updateVisiblePlayers(var7, par1ItemStack);
            }
            if (par5) {
                this.updateMapData(par2World, par3Entity, var6);
            }
        }
    }
    
    @Override
    public Packet createMapDataPacket(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final byte[] var4 = this.getMapData(par1ItemStack, par2World).getUpdatePacketData(par1ItemStack, par2World, par3EntityPlayer);
        return (var4 == null) ? null : new Packet131MapData((short)Item.map.itemID, (short)par1ItemStack.getItemDamage(), var4);
    }
    
    @Override
    public void onCreated(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().getBoolean("map_is_scaling")) {
            final MapData var4 = Item.map.getMapData(par1ItemStack, par2World);
            par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
            final MapData var5 = new MapData("map_" + par1ItemStack.getItemDamage());
            var5.scale = (byte)(var4.scale + 1);
            if (var5.scale > 4) {
                var5.scale = 4;
            }
            var5.xCenter = var4.xCenter;
            var5.zCenter = var4.zCenter;
            var5.dimension = var4.dimension;
            var5.markDirty();
            par2World.setItemData("map_" + par1ItemStack.getItemDamage(), var5);
        }
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        final MapData var5 = this.getMapData(par1ItemStack, par2EntityPlayer.worldObj);
        if (par4) {
            if (var5 == null) {
                par3List.add("Unknown map");
            }
            else {
                par3List.add("Scaling at 1:" + (1 << var5.scale));
                par3List.add("(Level " + var5.scale + "/" + 4 + ")");
            }
        }
    }
}
