/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.cache.CacheBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EntityTracker1_9
extends EntityTrackerBase {
    public static final String WITHER_TRANSLATABLE = "{\"translate\":\"entity.WitherBoss.name\"}";
    public static final String DRAGON_TRANSLATABLE = "{\"translate\":\"entity.EnderDragon.name\"}";
    private final Int2ObjectMap<UUID> uuidMap = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<List<Metadata>> metadataBuffer = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<Integer> vehicleMap = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<BossBar> bossBarMap = Int2ObjectSyncMap.hashmap();
    private final IntSet validBlocking = Int2ObjectSyncMap.hashset();
    private final Set<Integer> knownHolograms = Int2ObjectSyncMap.hashset();
    private final Set<Position> blockInteractions = Collections.newSetFromMap(CacheBuilder.newBuilder().maximumSize(1000L).expireAfterAccess(250L, TimeUnit.MILLISECONDS).build().asMap());
    private boolean blocking = false;
    private boolean autoTeam = false;
    private Position currentlyDigging = null;
    private boolean teamExists = false;
    private GameMode gameMode;
    private String currentTeam;
    private int heldItemSlot;
    private Item itemInSecondHand = null;

    public EntityTracker1_9(UserConnection userConnection) {
        super(userConnection, Entity1_10Types.EntityType.PLAYER);
    }

    public UUID getEntityUUID(int n) {
        UUID uUID = (UUID)this.uuidMap.get(n);
        if (uUID == null) {
            uUID = UUID.randomUUID();
            this.uuidMap.put(n, uUID);
        }
        return uUID;
    }

    public void setSecondHand(Item item) {
        this.setSecondHand(this.clientEntityId(), item);
    }

    public void setSecondHand(int n, Item item) {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, null, this.user());
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.write(Type.VAR_INT, 1);
        this.itemInSecondHand = item;
        packetWrapper.write(Type.ITEM, this.itemInSecondHand);
        try {
            packetWrapper.scheduleSend(Protocol1_9To1_8.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Item getItemInSecondHand() {
        return this.itemInSecondHand;
    }

    public void syncShieldWithSword() {
        boolean bl = this.hasSwordInHand();
        if (!bl || this.itemInSecondHand == null) {
            this.setSecondHand(bl ? new DataItem(442, 1, 0, null) : null);
        }
    }

    public boolean hasSwordInHand() {
        InventoryTracker inventoryTracker = this.user().get(InventoryTracker.class);
        int n = this.heldItemSlot + 36;
        int n2 = inventoryTracker.getItemId((short)0, (short)n);
        return Protocol1_9To1_8.isSword(n2);
    }

    @Override
    public void removeEntity(int n) {
        super.removeEntity(n);
        this.vehicleMap.remove(n);
        this.uuidMap.remove(n);
        this.validBlocking.remove(n);
        this.knownHolograms.remove(n);
        this.metadataBuffer.remove(n);
        BossBar bossBar = (BossBar)this.bossBarMap.remove(n);
        if (bossBar != null) {
            bossBar.hide();
            Via.getManager().getProviders().get(BossBarProvider.class).handleRemove(this.user(), bossBar.getId());
        }
    }

    public boolean interactedBlockRecently(int n, int n2, int n3) {
        return this.blockInteractions.contains(new Position(n, n2, n3));
    }

    public void addBlockInteraction(Position position) {
        this.blockInteractions.add(position);
    }

    public void handleMetadata(int n, List<Metadata> list) {
        EntityType entityType = this.entityType(n);
        if (entityType == null) {
            return;
        }
        for (Metadata metadata : new ArrayList<Metadata>(list)) {
            Object object;
            Object object2;
            if (entityType == Entity1_10Types.EntityType.WITHER && metadata.id() == 10) {
                list.remove(metadata);
            }
            if (entityType == Entity1_10Types.EntityType.ENDER_DRAGON && metadata.id() == 11) {
                list.remove(metadata);
            }
            if (entityType == Entity1_10Types.EntityType.SKELETON && this.getMetaByIndex(list, 12) == null) {
                list.add(new Metadata(12, MetaType1_9.Boolean, true));
            }
            if (entityType == Entity1_10Types.EntityType.HORSE && metadata.id() == 16 && (Integer)metadata.getValue() == Integer.MIN_VALUE) {
                metadata.setValue(0);
            }
            if (entityType == Entity1_10Types.EntityType.PLAYER) {
                if (metadata.id() == 0) {
                    byte by = (Byte)metadata.getValue();
                    if (n != this.getProvidedEntityId() && Via.getConfig().isShieldBlocking()) {
                        if ((by & 0x10) == 16) {
                            if (this.validBlocking.contains(n)) {
                                object2 = new DataItem(442, 1, 0, null);
                                this.setSecondHand(n, (Item)object2);
                            } else {
                                this.setSecondHand(n, null);
                            }
                        } else {
                            this.setSecondHand(n, null);
                        }
                    }
                }
                if (metadata.id() == 12 && Via.getConfig().isLeftHandedHandling()) {
                    list.add(new Metadata(13, MetaType1_9.Byte, (byte)(((Byte)metadata.getValue() & 0x80) == 0 ? 1 : 0)));
                }
            }
            if (entityType == Entity1_10Types.EntityType.ARMOR_STAND && Via.getConfig().isHologramPatch() && metadata.id() == 0 && this.getMetaByIndex(list, 10) != null) {
                Metadata metadata2;
                Metadata metadata3 = this.getMetaByIndex(list, 10);
                byte by = (Byte)metadata.getValue();
                if ((by & 0x20) == 32 && ((Byte)metadata3.getValue() & 1) == 1 && (metadata2 = this.getMetaByIndex(list, 2)) != null && !((String)metadata2.getValue()).isEmpty() && (object = this.getMetaByIndex(list, 3)) != null && ((Boolean)((Metadata)object).getValue()).booleanValue() && !this.knownHolograms.contains(n)) {
                    this.knownHolograms.add(n);
                    try {
                        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_POSITION, null, this.user());
                        packetWrapper.write(Type.VAR_INT, n);
                        packetWrapper.write(Type.SHORT, (short)0);
                        packetWrapper.write(Type.SHORT, (short)(128.0 * (Via.getConfig().getHologramYOffset() * 32.0)));
                        packetWrapper.write(Type.SHORT, (short)0);
                        packetWrapper.write(Type.BOOLEAN, true);
                        packetWrapper.scheduleSend(Protocol1_9To1_8.class);
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            if (!Via.getConfig().isBossbarPatch() || entityType != Entity1_10Types.EntityType.ENDER_DRAGON && entityType != Entity1_10Types.EntityType.WITHER) continue;
            if (metadata.id() == 2) {
                BossBar bossBar = (BossBar)this.bossBarMap.get(n);
                object2 = (String)metadata.getValue();
                Object object3 = ((String)object2).isEmpty() ? (entityType == Entity1_10Types.EntityType.ENDER_DRAGON ? DRAGON_TRANSLATABLE : WITHER_TRANSLATABLE) : (object2 = object2);
                if (bossBar == null) {
                    bossBar = Via.getAPI().legacyAPI().createLegacyBossBar((String)object2, BossColor.PINK, BossStyle.SOLID);
                    this.bossBarMap.put(n, bossBar);
                    bossBar.addConnection(this.user());
                    bossBar.show();
                    Via.getManager().getProviders().get(BossBarProvider.class).handleAdd(this.user(), bossBar.getId());
                    continue;
                }
                bossBar.setTitle((String)object2);
                continue;
            }
            if (metadata.id() != 6 || Via.getConfig().isBossbarAntiflicker()) continue;
            BossBar bossBar = (BossBar)this.bossBarMap.get(n);
            float f = entityType == Entity1_10Types.EntityType.ENDER_DRAGON ? 200.0f : 300.0f;
            float f2 = Math.max(0.0f, Math.min(((Float)metadata.getValue()).floatValue() / f, 1.0f));
            if (bossBar == null) {
                object = entityType == Entity1_10Types.EntityType.ENDER_DRAGON ? DRAGON_TRANSLATABLE : WITHER_TRANSLATABLE;
                bossBar = Via.getAPI().legacyAPI().createLegacyBossBar((String)object, f2, BossColor.PINK, BossStyle.SOLID);
                this.bossBarMap.put(n, bossBar);
                bossBar.addConnection(this.user());
                bossBar.show();
                Via.getManager().getProviders().get(BossBarProvider.class).handleAdd(this.user(), bossBar.getId());
                continue;
            }
            bossBar.setHealth(f2);
        }
    }

    public Metadata getMetaByIndex(List<Metadata> list, int n) {
        for (Metadata metadata : list) {
            if (n != metadata.id()) continue;
            return metadata;
        }
        return null;
    }

    public void sendTeamPacket(boolean bl, boolean bl2) {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.TEAMS, null, this.user());
        packetWrapper.write(Type.STRING, "viaversion");
        if (bl) {
            if (!this.teamExists) {
                packetWrapper.write(Type.BYTE, (byte)0);
                packetWrapper.write(Type.STRING, "viaversion");
                packetWrapper.write(Type.STRING, "\u00a7f");
                packetWrapper.write(Type.STRING, "");
                packetWrapper.write(Type.BYTE, (byte)0);
                packetWrapper.write(Type.STRING, "");
                packetWrapper.write(Type.STRING, "never");
                packetWrapper.write(Type.BYTE, (byte)15);
            } else {
                packetWrapper.write(Type.BYTE, (byte)3);
            }
            packetWrapper.write(Type.STRING_ARRAY, new String[]{this.user().getProtocolInfo().getUsername()});
        } else {
            packetWrapper.write(Type.BYTE, (byte)1);
        }
        this.teamExists = bl;
        try {
            if (bl2) {
                packetWrapper.send(Protocol1_9To1_8.class);
            } else {
                packetWrapper.scheduleSend(Protocol1_9To1_8.class);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addMetadataToBuffer(int n, List<Metadata> list) {
        List list2 = (List)this.metadataBuffer.get(n);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            this.metadataBuffer.put(n, list);
        }
    }

    public void sendMetadataBuffer(int n) {
        List list = (List)this.metadataBuffer.get(n);
        if (list != null) {
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, null, this.user());
            packetWrapper.write(Type.VAR_INT, n);
            packetWrapper.write(Types1_9.METADATA_LIST, list);
            Via.getManager().getProtocolManager().getProtocol(Protocol1_9To1_8.class).get(MetadataRewriter1_9To1_8.class).handleMetadata(n, list, this.user());
            this.handleMetadata(n, list);
            if (!list.isEmpty()) {
                try {
                    packetWrapper.scheduleSend(Protocol1_9To1_8.class);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            this.metadataBuffer.remove(n);
        }
    }

    public int getProvidedEntityId() {
        try {
            return Via.getManager().getProviders().get(EntityIdProvider.class).getEntityId(this.user());
        } catch (Exception exception) {
            return this.clientEntityId();
        }
    }

    public Map<Integer, UUID> getUuidMap() {
        return this.uuidMap;
    }

    public Map<Integer, List<Metadata>> getMetadataBuffer() {
        return this.metadataBuffer;
    }

    public Map<Integer, Integer> getVehicleMap() {
        return this.vehicleMap;
    }

    public Map<Integer, BossBar> getBossBarMap() {
        return this.bossBarMap;
    }

    public Set<Integer> getValidBlocking() {
        return this.validBlocking;
    }

    public Set<Integer> getKnownHolograms() {
        return this.knownHolograms;
    }

    public Set<Position> getBlockInteractions() {
        return this.blockInteractions;
    }

    public boolean isBlocking() {
        return this.blocking;
    }

    public void setBlocking(boolean bl) {
        this.blocking = bl;
    }

    public boolean isAutoTeam() {
        return this.autoTeam;
    }

    public void setAutoTeam(boolean bl) {
        this.autoTeam = bl;
    }

    public Position getCurrentlyDigging() {
        return this.currentlyDigging;
    }

    public void setCurrentlyDigging(Position position) {
        this.currentlyDigging = position;
    }

    public boolean isTeamExists() {
        return this.teamExists;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public String getCurrentTeam() {
        return this.currentTeam;
    }

    public void setCurrentTeam(String string) {
        this.currentTeam = string;
    }

    public void setHeldItemSlot(int n) {
        this.heldItemSlot = n;
    }
}

