/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SMapDataPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapBanner;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.MapFrame;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapData
extends WorldSavedData {
    private static final Logger LOGGER = LogManager.getLogger();
    public int xCenter;
    public int zCenter;
    public RegistryKey<World> dimension;
    public boolean trackingPosition;
    public boolean unlimitedTracking;
    public byte scale;
    public byte[] colors = new byte[16384];
    public boolean locked;
    public final List<MapInfo> playersArrayList = Lists.newArrayList();
    private final Map<PlayerEntity, MapInfo> playersHashMap = Maps.newHashMap();
    private final Map<String, MapBanner> banners = Maps.newHashMap();
    public final Map<String, MapDecoration> mapDecorations = Maps.newLinkedHashMap();
    private final Map<String, MapFrame> frames = Maps.newHashMap();

    public MapData(String string) {
        super(string);
    }

    public void initData(int n, int n2, int n3, boolean bl, boolean bl2, RegistryKey<World> registryKey) {
        this.scale = (byte)n3;
        this.calculateMapCenter(n, n2, this.scale);
        this.dimension = registryKey;
        this.trackingPosition = bl;
        this.unlimitedTracking = bl2;
        this.markDirty();
    }

    public void calculateMapCenter(double d, double d2, int n) {
        int n2 = 128 * (1 << n);
        int n3 = MathHelper.floor((d + 64.0) / (double)n2);
        int n4 = MathHelper.floor((d2 + 64.0) / (double)n2);
        this.xCenter = n3 * n2 + n2 / 2 - 64;
        this.zCenter = n4 * n2 + n2 / 2 - 64;
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        this.dimension = DimensionType.decodeWorldKey(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, compoundNBT.get("dimension"))).resultOrPartial(LOGGER::error).orElseThrow(() -> MapData.lambda$read$0(compoundNBT));
        this.xCenter = compoundNBT.getInt("xCenter");
        this.zCenter = compoundNBT.getInt("zCenter");
        this.scale = (byte)MathHelper.clamp(compoundNBT.getByte("scale"), 0, 4);
        this.trackingPosition = !compoundNBT.contains("trackingPosition", 0) || compoundNBT.getBoolean("trackingPosition");
        this.unlimitedTracking = compoundNBT.getBoolean("unlimitedTracking");
        this.locked = compoundNBT.getBoolean("locked");
        this.colors = compoundNBT.getByteArray("colors");
        if (this.colors.length != 16384) {
            this.colors = new byte[16384];
        }
        ListNBT listNBT = compoundNBT.getList("banners", 10);
        for (int i = 0; i < listNBT.size(); ++i) {
            MapBanner mapBanner = MapBanner.read(listNBT.getCompound(i));
            this.banners.put(mapBanner.getMapDecorationId(), mapBanner);
            this.updateDecorations(mapBanner.getDecorationType(), null, mapBanner.getMapDecorationId(), mapBanner.getPos().getX(), mapBanner.getPos().getZ(), 180.0, mapBanner.getName());
        }
        ListNBT listNBT2 = compoundNBT.getList("frames", 10);
        for (int i = 0; i < listNBT2.size(); ++i) {
            MapFrame mapFrame = MapFrame.read(listNBT2.getCompound(i));
            this.frames.put(mapFrame.getFrameName(), mapFrame);
            this.updateDecorations(MapDecoration.Type.FRAME, null, "frame-" + mapFrame.getEntityId(), mapFrame.getPos().getX(), mapFrame.getPos().getZ(), mapFrame.getRotation(), null);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        ResourceLocation.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.dimension.getLocation()).resultOrPartial(LOGGER::error).ifPresent(arg_0 -> MapData.lambda$write$1(compoundNBT, arg_0));
        compoundNBT.putInt("xCenter", this.xCenter);
        compoundNBT.putInt("zCenter", this.zCenter);
        compoundNBT.putByte("scale", this.scale);
        compoundNBT.putByteArray("colors", this.colors);
        compoundNBT.putBoolean("trackingPosition", this.trackingPosition);
        compoundNBT.putBoolean("unlimitedTracking", this.unlimitedTracking);
        compoundNBT.putBoolean("locked", this.locked);
        ListNBT listNBT = new ListNBT();
        for (MapBanner object : this.banners.values()) {
            listNBT.add(object.write());
        }
        compoundNBT.put("banners", listNBT);
        ListNBT listNBT2 = new ListNBT();
        for (MapFrame mapFrame : this.frames.values()) {
            listNBT2.add(mapFrame.write());
        }
        compoundNBT.put("frames", listNBT2);
        return compoundNBT;
    }

    public void copyFrom(MapData mapData) {
        this.locked = true;
        this.xCenter = mapData.xCenter;
        this.zCenter = mapData.zCenter;
        this.banners.putAll(mapData.banners);
        this.mapDecorations.putAll(mapData.mapDecorations);
        System.arraycopy(mapData.colors, 0, this.colors, 0, mapData.colors.length);
        this.markDirty();
    }

    public void updateVisiblePlayers(PlayerEntity playerEntity, ItemStack itemStack) {
        CompoundNBT compoundNBT;
        Object object;
        Object object2;
        Object object3;
        if (!this.playersHashMap.containsKey(playerEntity)) {
            MapInfo mapInfo = new MapInfo(this, playerEntity);
            this.playersHashMap.put(playerEntity, mapInfo);
            this.playersArrayList.add(mapInfo);
        }
        if (!playerEntity.inventory.hasItemStack(itemStack)) {
            this.mapDecorations.remove(playerEntity.getName().getString());
        }
        for (int i = 0; i < this.playersArrayList.size(); ++i) {
            object3 = this.playersArrayList.get(i);
            object2 = ((MapInfo)object3).player.getName().getString();
            if (!((MapInfo)object3).player.removed && (((MapInfo)object3).player.inventory.hasItemStack(itemStack) || itemStack.isOnItemFrame())) {
                if (itemStack.isOnItemFrame() || ((MapInfo)object3).player.world.getDimensionKey() != this.dimension || !this.trackingPosition) continue;
                this.updateDecorations(MapDecoration.Type.PLAYER, ((MapInfo)object3).player.world, (String)object2, ((MapInfo)object3).player.getPosX(), ((MapInfo)object3).player.getPosZ(), ((MapInfo)object3).player.rotationYaw, null);
                continue;
            }
            this.playersHashMap.remove(((MapInfo)object3).player);
            this.playersArrayList.remove(object3);
            this.mapDecorations.remove(object2);
        }
        if (itemStack.isOnItemFrame() && this.trackingPosition) {
            ItemFrameEntity itemFrameEntity = itemStack.getItemFrame();
            object3 = itemFrameEntity.getHangingPosition();
            object2 = this.frames.get(MapFrame.getFrameNameWithPos((BlockPos)object3));
            if (object2 != null && itemFrameEntity.getEntityId() != ((MapFrame)object2).getEntityId() && this.frames.containsKey(((MapFrame)object2).getFrameName())) {
                this.mapDecorations.remove("frame-" + ((MapFrame)object2).getEntityId());
            }
            object = new MapFrame((BlockPos)object3, itemFrameEntity.getHorizontalFacing().getHorizontalIndex() * 90, itemFrameEntity.getEntityId());
            this.updateDecorations(MapDecoration.Type.FRAME, playerEntity.world, "frame-" + itemFrameEntity.getEntityId(), ((Vector3i)object3).getX(), ((Vector3i)object3).getZ(), itemFrameEntity.getHorizontalFacing().getHorizontalIndex() * 90, null);
            this.frames.put(((MapFrame)object).getFrameName(), (MapFrame)object);
        }
        if ((compoundNBT = itemStack.getTag()) != null && compoundNBT.contains("Decorations", 0)) {
            object3 = compoundNBT.getList("Decorations", 10);
            for (int i = 0; i < ((ListNBT)object3).size(); ++i) {
                object = ((ListNBT)object3).getCompound(i);
                if (this.mapDecorations.containsKey(((CompoundNBT)object).getString("id"))) continue;
                this.updateDecorations(MapDecoration.Type.byIcon(((CompoundNBT)object).getByte("type")), playerEntity.world, ((CompoundNBT)object).getString("id"), ((CompoundNBT)object).getDouble("x"), ((CompoundNBT)object).getDouble("z"), ((CompoundNBT)object).getDouble("rot"), null);
            }
        }
    }

    public static void addTargetDecoration(ItemStack itemStack, BlockPos blockPos, String string, MapDecoration.Type type) {
        ListNBT listNBT;
        if (itemStack.hasTag() && itemStack.getTag().contains("Decorations", 0)) {
            listNBT = itemStack.getTag().getList("Decorations", 10);
        } else {
            listNBT = new ListNBT();
            itemStack.setTagInfo("Decorations", listNBT);
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putByte("type", type.getIcon());
        compoundNBT.putString("id", string);
        compoundNBT.putDouble("x", blockPos.getX());
        compoundNBT.putDouble("z", blockPos.getZ());
        compoundNBT.putDouble("rot", 180.0);
        listNBT.add(compoundNBT);
        if (type.hasMapColor()) {
            CompoundNBT compoundNBT2 = itemStack.getOrCreateChildTag("display");
            compoundNBT2.putInt("MapColor", type.getMapColor());
        }
    }

    private void updateDecorations(MapDecoration.Type type, @Nullable IWorld iWorld, String string, double d, double d2, double d3, @Nullable ITextComponent iTextComponent) {
        byte by;
        int n = 1 << this.scale;
        float f = (float)(d - (double)this.xCenter) / (float)n;
        float f2 = (float)(d2 - (double)this.zCenter) / (float)n;
        byte by2 = (byte)((double)(f * 2.0f) + 0.5);
        byte by3 = (byte)((double)(f2 * 2.0f) + 0.5);
        int n2 = 63;
        if (f >= -63.0f && f2 >= -63.0f && f <= 63.0f && f2 <= 63.0f) {
            by = (byte)((d3 += d3 < 0.0 ? -8.0 : 8.0) * 16.0 / 360.0);
            if (this.dimension == World.THE_NETHER && iWorld != null) {
                int n3 = (int)(iWorld.getWorldInfo().getDayTime() / 10L);
                by = (byte)(n3 * n3 * 34187121 + n3 * 121 >> 15 & 0xF);
            }
        } else {
            if (type != MapDecoration.Type.PLAYER) {
                this.mapDecorations.remove(string);
                return;
            }
            int n4 = 320;
            if (Math.abs(f) < 320.0f && Math.abs(f2) < 320.0f) {
                type = MapDecoration.Type.PLAYER_OFF_MAP;
            } else {
                if (!this.unlimitedTracking) {
                    this.mapDecorations.remove(string);
                    return;
                }
                type = MapDecoration.Type.PLAYER_OFF_LIMITS;
            }
            by = 0;
            if (f <= -63.0f) {
                by2 = -128;
            }
            if (f2 <= -63.0f) {
                by3 = -128;
            }
            if (f >= 63.0f) {
                by2 = 127;
            }
            if (f2 >= 63.0f) {
                by3 = 127;
            }
        }
        this.mapDecorations.put(string, new MapDecoration(type, by2, by3, by, iTextComponent));
    }

    @Nullable
    public IPacket<?> getMapPacket(ItemStack itemStack, IBlockReader iBlockReader, PlayerEntity playerEntity) {
        MapInfo mapInfo = this.playersHashMap.get(playerEntity);
        return mapInfo == null ? null : mapInfo.getPacket(itemStack);
    }

    public void updateMapData(int n, int n2) {
        this.markDirty();
        for (MapInfo mapInfo : this.playersArrayList) {
            mapInfo.update(n, n2);
        }
    }

    public MapInfo getMapInfo(PlayerEntity playerEntity) {
        MapInfo mapInfo = this.playersHashMap.get(playerEntity);
        if (mapInfo == null) {
            mapInfo = new MapInfo(this, playerEntity);
            this.playersHashMap.put(playerEntity, mapInfo);
            this.playersArrayList.add(mapInfo);
        }
        return mapInfo;
    }

    public void tryAddBanner(IWorld iWorld, BlockPos blockPos) {
        double d = (double)blockPos.getX() + 0.5;
        double d2 = (double)blockPos.getZ() + 0.5;
        int n = 1 << this.scale;
        double d3 = (d - (double)this.xCenter) / (double)n;
        double d4 = (d2 - (double)this.zCenter) / (double)n;
        int n2 = 63;
        boolean bl = false;
        if (d3 >= -63.0 && d4 >= -63.0 && d3 <= 63.0 && d4 <= 63.0) {
            MapBanner mapBanner = MapBanner.fromWorld(iWorld, blockPos);
            if (mapBanner == null) {
                return;
            }
            boolean bl2 = true;
            if (this.banners.containsKey(mapBanner.getMapDecorationId()) && this.banners.get(mapBanner.getMapDecorationId()).equals(mapBanner)) {
                this.banners.remove(mapBanner.getMapDecorationId());
                this.mapDecorations.remove(mapBanner.getMapDecorationId());
                bl2 = false;
                bl = true;
            }
            if (bl2) {
                this.banners.put(mapBanner.getMapDecorationId(), mapBanner);
                this.updateDecorations(mapBanner.getDecorationType(), iWorld, mapBanner.getMapDecorationId(), d, d2, 180.0, mapBanner.getName());
                bl = true;
            }
            if (bl) {
                this.markDirty();
            }
        }
    }

    public void removeStaleBanners(IBlockReader iBlockReader, int n, int n2) {
        Iterator<MapBanner> iterator2 = this.banners.values().iterator();
        while (iterator2.hasNext()) {
            MapBanner mapBanner;
            MapBanner mapBanner2 = iterator2.next();
            if (mapBanner2.getPos().getX() != n || mapBanner2.getPos().getZ() != n2 || mapBanner2.equals(mapBanner = MapBanner.fromWorld(iBlockReader, mapBanner2.getPos()))) continue;
            iterator2.remove();
            this.mapDecorations.remove(mapBanner2.getMapDecorationId());
        }
    }

    public void removeItemFrame(BlockPos blockPos, int n) {
        this.mapDecorations.remove("frame-" + n);
        this.frames.remove(MapFrame.getFrameNameWithPos(blockPos));
    }

    private static void lambda$write$1(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("dimension", iNBT);
    }

    private static IllegalArgumentException lambda$read$0(CompoundNBT compoundNBT) {
        return new IllegalArgumentException("Invalid map dimension: " + compoundNBT.get("dimension"));
    }

    public class MapInfo {
        public final PlayerEntity player;
        private boolean isDirty;
        private int minX;
        private int minY;
        private int maxX;
        private int maxY;
        private int tick;
        public int step;
        final MapData this$0;

        public MapInfo(MapData mapData, PlayerEntity playerEntity) {
            this.this$0 = mapData;
            this.isDirty = true;
            this.maxX = 127;
            this.maxY = 127;
            this.player = playerEntity;
        }

        @Nullable
        public IPacket<?> getPacket(ItemStack itemStack) {
            if (this.isDirty) {
                this.isDirty = false;
                return new SMapDataPacket(FilledMapItem.getMapId(itemStack), this.this$0.scale, this.this$0.trackingPosition, this.this$0.locked, this.this$0.mapDecorations.values(), this.this$0.colors, this.minX, this.minY, this.maxX + 1 - this.minX, this.maxY + 1 - this.minY);
            }
            return this.tick++ % 5 == 0 ? new SMapDataPacket(FilledMapItem.getMapId(itemStack), this.this$0.scale, this.this$0.trackingPosition, this.this$0.locked, this.this$0.mapDecorations.values(), this.this$0.colors, 0, 0, 0, 0) : null;
        }

        public void update(int n, int n2) {
            if (this.isDirty) {
                this.minX = Math.min(this.minX, n);
                this.minY = Math.min(this.minY, n2);
                this.maxX = Math.max(this.maxX, n);
                this.maxY = Math.max(this.maxY, n2);
            } else {
                this.isDirty = true;
                this.minX = n;
                this.minY = n2;
                this.maxX = n;
                this.maxY = n2;
            }
        }
    }
}

