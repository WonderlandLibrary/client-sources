package net.minecraft.item;

import net.minecraft.entity.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.network.*;

public class ItemMap extends ItemMapBase
{
    private static final String[] I;
    
    public void updateMapData(final World world, final Entity entity, final MapData mapData) {
        if (world.provider.getDimensionId() == mapData.dimension && entity instanceof EntityPlayer) {
            final int n = " ".length() << mapData.scale;
            final int xCenter = mapData.xCenter;
            final int zCenter = mapData.zCenter;
            final int n2 = MathHelper.floor_double(entity.posX - xCenter) / n + (0xE5 ^ 0xA5);
            final int n3 = MathHelper.floor_double(entity.posZ - zCenter) / n + (0x13 ^ 0x53);
            int n4 = (28 + 3 + 18 + 79) / n;
            if (world.provider.getHasNoSky()) {
                n4 /= "  ".length();
            }
            final MapData.MapInfo mapInfo2;
            final MapData.MapInfo mapInfo = mapInfo2 = mapData.getMapInfo((EntityPlayer)entity);
            mapInfo2.field_82569_d += " ".length();
            int n5 = "".length();
            int i = n2 - n4 + " ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
            while (i < n2 + n4) {
                if ((i & (0x53 ^ 0x5C)) == (mapInfo.field_82569_d & (0x9E ^ 0x91)) || n5 != 0) {
                    n5 = "".length();
                    double n6 = 0.0;
                    int j = n3 - n4 - " ".length();
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                    while (j < n3 + n4) {
                        if (i >= 0 && j >= -" ".length() && i < 27 + 6 - 17 + 112 && j < 113 + 34 - 52 + 33) {
                            final int n7 = i - n2;
                            final int n8 = j - n3;
                            int n9;
                            if (n7 * n7 + n8 * n8 > (n4 - "  ".length()) * (n4 - "  ".length())) {
                                n9 = " ".length();
                                "".length();
                                if (4 == 1) {
                                    throw null;
                                }
                            }
                            else {
                                n9 = "".length();
                            }
                            final int n10 = n9;
                            final int n11 = (xCenter / n + i - (0x33 ^ 0x73)) * n;
                            final int n12 = (zCenter / n + j - (0x5 ^ 0x45)) * n;
                            final HashMultiset create = HashMultiset.create();
                            final Chunk chunkFromBlockCoords = world.getChunkFromBlockCoords(new BlockPos(n11, "".length(), n12));
                            if (!chunkFromBlockCoords.isEmpty()) {
                                final int n13 = n11 & (0x2D ^ 0x22);
                                final int n14 = n12 & (0x46 ^ 0x49);
                                int length = "".length();
                                double n15 = 0.0;
                                if (world.provider.getHasNoSky()) {
                                    final int n16 = n11 + n12 * (40145 + 45590 + 127783 + 18353);
                                    if ((n16 * n16 * (3588496 + 9748451 - 10009639 + 27959813) + n16 * (0xBA ^ 0xB1) >> (0x56 ^ 0x42) & " ".length()) == 0x0) {
                                        ((Multiset)create).add((Object)Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 0x6D ^ 0x67);
                                        "".length();
                                        if (2 < -1) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        ((Multiset)create).add((Object)Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE)), 0xC2 ^ 0xA6);
                                    }
                                    n15 = 100.0;
                                    "".length();
                                    if (-1 != -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                                    int k = "".length();
                                    "".length();
                                    if (-1 == 2) {
                                        throw null;
                                    }
                                    while (k < n) {
                                        int l = "".length();
                                        "".length();
                                        if (4 < 3) {
                                            throw null;
                                        }
                                        while (l < n) {
                                            int n17 = chunkFromBlockCoords.getHeightValue(k + n13, l + n14) + " ".length();
                                            IBlockState blockState = Blocks.air.getDefaultState();
                                            if (n17 > " ".length()) {
                                                do {
                                                    --n17;
                                                    blockState = chunkFromBlockCoords.getBlockState(mutableBlockPos.func_181079_c(k + n13, n17, l + n14));
                                                } while (blockState.getBlock().getMapColor(blockState) == MapColor.airColor && n17 > 0);
                                                if (n17 > 0 && blockState.getBlock().getMaterial().isLiquid()) {
                                                    int n18 = n17 - " ".length();
                                                    Block block;
                                                    do {
                                                        block = chunkFromBlockCoords.getBlock(k + n13, n18--, l + n14);
                                                        ++length;
                                                    } while (n18 > 0 && block.getMaterial().isLiquid());
                                                }
                                            }
                                            n15 += n17 / (n * n);
                                            ((Multiset)create).add((Object)blockState.getBlock().getMapColor(blockState));
                                            ++l;
                                        }
                                        ++k;
                                    }
                                }
                                final int n19 = length / (n * n);
                                final double n20 = (n15 - n6) * 4.0 / (n + (0xAC ^ 0xA8)) + ((i + j & " ".length()) - 0.5) * 0.4;
                                int n21 = " ".length();
                                if (n20 > 0.6) {
                                    n21 = "  ".length();
                                }
                                if (n20 < -0.6) {
                                    n21 = "".length();
                                }
                                final MapColor mapColor = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)create), (Object)MapColor.airColor);
                                if (mapColor == MapColor.waterColor) {
                                    final double n22 = n19 * 0.1 + (i + j & " ".length()) * 0.2;
                                    n21 = " ".length();
                                    if (n22 < 0.5) {
                                        n21 = "  ".length();
                                    }
                                    if (n22 > 0.9) {
                                        n21 = "".length();
                                    }
                                }
                                n6 = n15;
                                if (j >= 0 && n7 * n7 + n8 * n8 < n4 * n4 && (n10 == 0 || (i + j & " ".length()) != 0x0)) {
                                    final byte b = mapData.colors[i + j * (31 + 28 + 17 + 52)];
                                    final byte b2 = (byte)(mapColor.colorIndex * (0x1E ^ 0x1A) + n21);
                                    if (b != b2) {
                                        mapData.colors[i + j * (10 + 27 + 33 + 58)] = b2;
                                        mapData.updateMapData(i, j);
                                        n5 = " ".length();
                                    }
                                }
                            }
                        }
                        ++j;
                    }
                }
                ++i;
            }
        }
    }
    
    private static void I() {
        (I = new String[0xAB ^ 0xA6])["".length()] = I("\u000f\u0015\u0017\u000b", "btgTR");
        ItemMap.I[" ".length()] = I("\u0017\u0005\u0000;", "zdpdX");
        ItemMap.I["  ".length()] = I(":\u0017\u001e", "WvnWE");
        ItemMap.I["   ".length()] = I("*\f\u00055", "GmujN");
        ItemMap.I[0x3A ^ 0x3E] = I("\u000e\u000b8):\u00105;\u00152\u000f\u0003&\u0011", "cjHvS");
        ItemMap.I[0x33 ^ 0x36] = I("*)=", "GHMsY");
        ItemMap.I[0xB1 ^ 0xB7] = I("\u0003\t>&", "nhNyi");
        ItemMap.I[0x11 ^ 0x16] = I("%7\u001a6", "HVjiy");
        ItemMap.I[0x23 ^ 0x2B] = I("\f7\u0002:).7I9')", "YYiTF");
        ItemMap.I[0x21 ^ 0x28] = I("'.\t8>\u001a*H5#T|R", "tMhTW");
        ItemMap.I[0xCC ^ 0xC6] = I("_\u001d,\u00014\u001bq", "wQIwQ");
        ItemMap.I[0xAB ^ 0xA0] = I("f", "IzWcY");
        ItemMap.I[0x9 ^ 0x5] = I("Z", "skRAg");
    }
    
    @Override
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean(ItemMap.I[0x11 ^ 0x15])) {
            final MapData mapData = Items.filled_map.getMapData(itemStack, world);
            itemStack.setItemDamage(world.getUniqueDataId(ItemMap.I[0x62 ^ 0x67]));
            final MapData mapData2 = new MapData(ItemMap.I[0x64 ^ 0x62] + itemStack.getMetadata());
            mapData2.scale = (byte)(mapData.scale + " ".length());
            if (mapData2.scale > (0x63 ^ 0x67)) {
                mapData2.scale = (byte)(0x12 ^ 0x16);
            }
            mapData2.calculateMapCenter(mapData.xCenter, mapData.zCenter, mapData2.scale);
            mapData2.dimension = mapData.dimension;
            mapData2.markDirty();
            world.setItemData(ItemMap.I[0xB2 ^ 0xB5] + itemStack.getMetadata(), mapData2);
        }
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int n, final boolean b) {
        if (!world.isRemote) {
            final MapData mapData = this.getMapData(itemStack, world);
            if (entity instanceof EntityPlayer) {
                mapData.updateVisiblePlayers((EntityPlayer)entity, itemStack);
            }
            if (b) {
                this.updateMapData(world, entity, mapData);
            }
        }
    }
    
    public static MapData loadMapData(final int n, final World world) {
        final String string = ItemMap.I["".length()] + n;
        MapData mapData = (MapData)world.loadItemData(MapData.class, string);
        if (mapData == null) {
            mapData = new MapData(string);
            world.setItemData(string, mapData);
        }
        return mapData;
    }
    
    static {
        I();
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        final MapData mapData = this.getMapData(itemStack, entityPlayer.worldObj);
        if (b) {
            if (mapData == null) {
                list.add(ItemMap.I[0x30 ^ 0x38]);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                list.add(ItemMap.I[0x4 ^ 0xD] + (" ".length() << mapData.scale));
                list.add(ItemMap.I[0x11 ^ 0x1B] + mapData.scale + ItemMap.I[0x34 ^ 0x3F] + (0x4 ^ 0x0) + ItemMap.I[0x9A ^ 0x96]);
            }
        }
    }
    
    public MapData getMapData(final ItemStack itemStack, final World world) {
        MapData mapData = (MapData)world.loadItemData(MapData.class, ItemMap.I[" ".length()] + itemStack.getMetadata());
        if (mapData == null && !world.isRemote) {
            itemStack.setItemDamage(world.getUniqueDataId(ItemMap.I["  ".length()]));
            final String string = ItemMap.I["   ".length()] + itemStack.getMetadata();
            mapData = new MapData(string);
            mapData.scale = (byte)"   ".length();
            mapData.calculateMapCenter(world.getWorldInfo().getSpawnX(), world.getWorldInfo().getSpawnZ(), mapData.scale);
            mapData.dimension = (byte)world.provider.getDimensionId();
            mapData.markDirty();
            world.setItemData(string, mapData);
        }
        return mapData;
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected ItemMap() {
        this.setHasSubtypes(" ".length() != 0);
    }
    
    @Override
    public Packet createMapDataPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return this.getMapData(itemStack, world).getMapPacket(itemStack, world, entityPlayer);
    }
}
