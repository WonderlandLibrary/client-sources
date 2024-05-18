package net.minecraft.world.storage;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;

public class MapData extends WorldSavedData
{
    public Map<String, Vec4b> mapDecorations;
    public int xCenter;
    private static final String[] I;
    public byte dimension;
    public List<MapInfo> playersArrayList;
    public int zCenter;
    public byte scale;
    private Map<EntityPlayer, MapInfo> playersHashMap;
    public byte[] colors;
    
    public void calculateMapCenter(final double n, final double n2, final int n3) {
        final int n4 = (24 + 38 + 13 + 53) * (" ".length() << n3);
        final int floor_double = MathHelper.floor_double((n + 64.0) / n4);
        final int floor_double2 = MathHelper.floor_double((n2 + 64.0) / n4);
        this.xCenter = floor_double * n4 + n4 / "  ".length() - (0x30 ^ 0x70);
        this.zCenter = floor_double2 * n4 + n4 / "  ".length() - (0x12 ^ 0x52);
    }
    
    private void updateDecorations(int n, final World world, final String s, final double n2, final double n3, double n4) {
        final int n5 = " ".length() << this.scale;
        final float n6 = (float)(n2 - this.xCenter) / n5;
        final float n7 = (float)(n3 - this.zCenter) / n5;
        byte b = (byte)(n6 * 2.0f + 0.5);
        byte b2 = (byte)(n7 * 2.0f + 0.5);
        final int n8 = 0x6C ^ 0x53;
        int length;
        if (n6 >= -n8 && n7 >= -n8 && n6 <= n8 && n7 <= n8) {
            final double n9 = n4;
            double n10;
            if (n4 < 0.0) {
                n10 = -8.0;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                n10 = 8.0;
            }
            n4 = n9 + n10;
            length = (byte)(n4 * 16.0 / 360.0);
            if (this.dimension < 0) {
                final int n11 = (int)(world.getWorldInfo().getWorldTime() / 10L);
                length = (byte)(n11 * n11 * (21575426 + 894901 + 546155 + 11170639) + n11 * (0x7A ^ 0x3) >> (0xC8 ^ 0xC7) & (0x64 ^ 0x6B));
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
        }
        else {
            if (Math.abs(n6) >= 320.0f || Math.abs(n7) >= 320.0f) {
                this.mapDecorations.remove(s);
                return;
            }
            n = (0x61 ^ 0x67);
            length = "".length();
            if (n6 <= -n8) {
                b = (byte)(n8 * "  ".length() + 2.5);
            }
            if (n7 <= -n8) {
                b2 = (byte)(n8 * "  ".length() + 2.5);
            }
            if (n6 >= n8) {
                b = (byte)(n8 * "  ".length() + " ".length());
            }
            if (n7 >= n8) {
                b2 = (byte)(n8 * "  ".length() + " ".length());
            }
        }
        this.mapDecorations.put(s, new Vec4b((byte)n, b, b2, (byte)length));
    }
    
    public void updateMapData(final int n, final int n2) {
        super.markDirty();
        final Iterator<MapInfo> iterator = this.playersArrayList.iterator();
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().update(n, n2);
        }
    }
    
    public void updateVisiblePlayers(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        if (!this.playersHashMap.containsKey(entityPlayer)) {
            final MapInfo mapInfo = new MapInfo(entityPlayer);
            this.playersHashMap.put(entityPlayer, mapInfo);
            this.playersArrayList.add(mapInfo);
        }
        if (!entityPlayer.inventory.hasItemStack(itemStack)) {
            this.mapDecorations.remove(entityPlayer.getName());
        }
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < this.playersArrayList.size()) {
            final MapInfo mapInfo2 = this.playersArrayList.get(i);
            if (!mapInfo2.entityplayerObj.isDead && (mapInfo2.entityplayerObj.inventory.hasItemStack(itemStack) || itemStack.isOnItemFrame())) {
                if (!itemStack.isOnItemFrame() && mapInfo2.entityplayerObj.dimension == this.dimension) {
                    this.updateDecorations("".length(), mapInfo2.entityplayerObj.worldObj, mapInfo2.entityplayerObj.getName(), mapInfo2.entityplayerObj.posX, mapInfo2.entityplayerObj.posZ, mapInfo2.entityplayerObj.rotationYaw);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
            }
            else {
                this.playersHashMap.remove(mapInfo2.entityplayerObj);
                this.playersArrayList.remove(mapInfo2);
            }
            ++i;
        }
        if (itemStack.isOnItemFrame()) {
            final EntityItemFrame itemFrame = itemStack.getItemFrame();
            final BlockPos hangingPosition = itemFrame.getHangingPosition();
            this.updateDecorations(" ".length(), entityPlayer.worldObj, MapData.I[0x9F ^ 0x90] + itemFrame.getEntityId(), hangingPosition.getX(), hangingPosition.getZ(), itemFrame.facingDirection.getHorizontalIndex() * (0x4F ^ 0x15));
        }
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(MapData.I[0x6D ^ 0x7D], 0x61 ^ 0x68)) {
            final NBTTagList tagList = itemStack.getTagCompound().getTagList(MapData.I[0x76 ^ 0x67], 0x8 ^ 0x2);
            int j = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(j);
                if (!this.mapDecorations.containsKey(compoundTag.getString(MapData.I[0x39 ^ 0x2B]))) {
                    this.updateDecorations(compoundTag.getByte(MapData.I[0x0 ^ 0x13]), entityPlayer.worldObj, compoundTag.getString(MapData.I[0x7 ^ 0x13]), compoundTag.getDouble(MapData.I[0x1D ^ 0x8]), compoundTag.getDouble(MapData.I[0xB0 ^ 0xA6]), compoundTag.getDouble(MapData.I[0x55 ^ 0x42]));
                }
                ++j;
            }
        }
    }
    
    private static void I() {
        (I = new String[0xA1 ^ 0xB9])["".length()] = I("\u001d,\u0019\f\u0005\n,\u001b\u0007", "yEtik");
        MapData.I[" ".length()] = I("\"\u0013$<9?\"", "ZPARM");
        MapData.I["  ".length()] = I(",*\u00039=3\u001b", "VifWI");
        MapData.I["   ".length()] = I("6 \n;\t", "ECkWl");
        MapData.I[0x5 ^ 0x1] = I("!\u001010*", "VyUDB");
        MapData.I[0x8E ^ 0x8B] = I("'.\u0004\u000f9;", "OKmhQ");
        MapData.I[0x72 ^ 0x74] = I("\u0005\u0017\"\u0000 \u0015", "fxNoR");
        MapData.I[0xB0 ^ 0xB7] = I("+)!67;", "HFMYE");
        MapData.I[0x8C ^ 0x84] = I("\"3(3*53*8", "FZEVD");
        MapData.I[0x28 ^ 0x21] = I("\u001462*\u0006\t\u0007", "luWDr");
        MapData.I[0x37 ^ 0x3D] = I("\u0011\u0007)9\u001b\u000e6", "kDLWo");
        MapData.I[0x9B ^ 0x90] = I(" '\u000b?/", "SDjSJ");
        MapData.I[0x2E ^ 0x22] = I("\u001d%\u0011,!", "jLuXI");
        MapData.I[0x6D ^ 0x60] = I("\u0011'8=\u000e\r", "yBQZf");
        MapData.I[0x15 ^ 0x1B] = I(")$/\u000069", "JKCoD");
        MapData.I[0x7 ^ 0x8] = I("\t\u0011\u000e\"6B", "ocoOS");
        MapData.I[0xAE ^ 0xBE] = I("\u0006\u00164\u0005\u0010#\u0007>\u0005\f1", "BsWjb");
        MapData.I[0xF ^ 0x1E] = I("+\f6\u0004$\u000e\u001d<\u00048\u001c", "oiUkV");
        MapData.I[0x76 ^ 0x64] = I("3\u0000", "ZdUEr");
        MapData.I[0x8F ^ 0x9C] = I("3\f=!", "GuMDw");
        MapData.I[0x7B ^ 0x6F] = I("\u0004\u0000", "mdxOk");
        MapData.I[0xC ^ 0x19] = I(".", "VmrGU");
        MapData.I[0x14 ^ 0x2] = I("(", "RxstR");
        MapData.I[0x14 ^ 0x3] = I("\u00115$", "cZPlW");
    }
    
    public Packet getMapPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MapInfo mapInfo = this.playersHashMap.get(entityPlayer);
        Packet packet;
        if (mapInfo == null) {
            packet = null;
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            packet = mapInfo.getPacket(itemStack);
        }
        return packet;
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.dimension = nbtTagCompound.getByte(MapData.I["".length()]);
        this.xCenter = nbtTagCompound.getInteger(MapData.I[" ".length()]);
        this.zCenter = nbtTagCompound.getInteger(MapData.I["  ".length()]);
        this.scale = nbtTagCompound.getByte(MapData.I["   ".length()]);
        this.scale = (byte)MathHelper.clamp_int(this.scale, "".length(), 0x2D ^ 0x29);
        final short short1 = nbtTagCompound.getShort(MapData.I[0xBA ^ 0xBE]);
        final short short2 = nbtTagCompound.getShort(MapData.I[0x80 ^ 0x85]);
        if (short1 == 48 + 122 - 128 + 86 && short2 == 97 + 73 - 87 + 45) {
            this.colors = nbtTagCompound.getByteArray(MapData.I[0x1A ^ 0x1C]);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            final byte[] byteArray = nbtTagCompound.getByteArray(MapData.I[0x8C ^ 0x8B]);
            this.colors = new byte[13424 + 7360 - 11211 + 6811];
            final int n = (52 + 73 - 111 + 114 - short1) / "  ".length();
            final int n2 = (4 + 31 + 14 + 79 - short2) / "  ".length();
            int i = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (i < short2) {
                final short n3 = (short)(i + n2);
                if (n3 >= 0 || n3 < 72 + 30 - 29 + 55) {
                    int j = "".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    while (j < short1) {
                        final short n4 = (short)(j + n);
                        if (n4 >= 0 || n4 < 82 + 39 - 105 + 112) {
                            this.colors[n4 + n3 * (17 + 41 - 49 + 119)] = byteArray[j + i * short1];
                        }
                        ++j;
                    }
                }
                ++i;
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte(MapData.I[0x27 ^ 0x2F], this.dimension);
        nbtTagCompound.setInteger(MapData.I[0x7F ^ 0x76], this.xCenter);
        nbtTagCompound.setInteger(MapData.I[0x5E ^ 0x54], this.zCenter);
        nbtTagCompound.setByte(MapData.I[0x75 ^ 0x7E], this.scale);
        nbtTagCompound.setShort(MapData.I[0x7C ^ 0x70], (short)(20 + 82 + 16 + 10));
        nbtTagCompound.setShort(MapData.I[0xB0 ^ 0xBD], (short)(110 + 110 - 108 + 16));
        nbtTagCompound.setByteArray(MapData.I[0x68 ^ 0x66], this.colors);
    }
    
    public MapData(final String s) {
        super(s);
        this.colors = new byte[6086 + 9601 - 7153 + 7850];
        this.playersArrayList = (List<MapInfo>)Lists.newArrayList();
        this.playersHashMap = (Map<EntityPlayer, MapInfo>)Maps.newHashMap();
        this.mapDecorations = (Map<String, Vec4b>)Maps.newLinkedHashMap();
    }
    
    public MapInfo getMapInfo(final EntityPlayer entityPlayer) {
        MapInfo mapInfo = this.playersHashMap.get(entityPlayer);
        if (mapInfo == null) {
            mapInfo = new MapInfo(entityPlayer);
            this.playersHashMap.put(entityPlayer, mapInfo);
            this.playersArrayList.add(mapInfo);
        }
        return mapInfo;
    }
    
    public class MapInfo
    {
        private int maxX;
        public int field_82569_d;
        private int minX;
        final MapData this$0;
        private int maxY;
        private int minY;
        private boolean field_176105_d;
        private int field_176109_i;
        public final EntityPlayer entityplayerObj;
        
        public Packet getPacket(final ItemStack itemStack) {
            if (this.field_176105_d) {
                this.field_176105_d = ("".length() != 0);
                return new S34PacketMaps(itemStack.getMetadata(), this.this$0.scale, this.this$0.mapDecorations.values(), this.this$0.colors, this.minX, this.minY, this.maxX + " ".length() - this.minX, this.maxY + " ".length() - this.minY);
            }
            final int field_176109_i = this.field_176109_i;
            this.field_176109_i = field_176109_i + " ".length();
            Packet packet;
            if (field_176109_i % (0x44 ^ 0x41) == 0) {
                packet = new S34PacketMaps(itemStack.getMetadata(), this.this$0.scale, this.this$0.mapDecorations.values(), this.this$0.colors, "".length(), "".length(), "".length(), "".length());
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                packet = null;
            }
            return packet;
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
                if (4 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public MapInfo(final MapData this$0, final EntityPlayer entityplayerObj) {
            this.this$0 = this$0;
            this.field_176105_d = (" ".length() != 0);
            this.minX = "".length();
            this.minY = "".length();
            this.maxX = 110 + 55 - 95 + 57;
            this.maxY = 32 + 86 - 51 + 60;
            this.entityplayerObj = entityplayerObj;
        }
        
        public void update(final int n, final int n2) {
            if (this.field_176105_d) {
                this.minX = Math.min(this.minX, n);
                this.minY = Math.min(this.minY, n2);
                this.maxX = Math.max(this.maxX, n);
                this.maxY = Math.max(this.maxY, n2);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                this.field_176105_d = (" ".length() != 0);
                this.minX = n;
                this.minY = n2;
                this.maxX = n;
                this.maxY = n2;
            }
        }
    }
}
