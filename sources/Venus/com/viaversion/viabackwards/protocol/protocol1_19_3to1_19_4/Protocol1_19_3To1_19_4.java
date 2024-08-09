/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets.BlockItemPackets1_19_4;
import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets.EntityPackets1_19_4;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_19_3To1_19_4
extends BackwardsProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_3, ServerboundPackets1_19_4, ServerboundPackets1_19_3> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.19.4", "1.19.3", Protocol1_19_4To1_19_3.class);
    private final EntityPackets1_19_4 entityRewriter = new EntityPackets1_19_4(this);
    private final BlockItemPackets1_19_4 itemRewriter = new BlockItemPackets1_19_4(this);
    private final TranslatableRewriter<ClientboundPackets1_19_4> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19_4>(this);

    public Protocol1_19_3To1_19_4() {
        super(ClientboundPackets1_19_4.class, ClientboundPackets1_19_3.class, ServerboundPackets1_19_4.class, ServerboundPackets1_19_3.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter<ClientboundPackets1_19_4>(this);
        soundRewriter.registerStopSound(ClientboundPackets1_19_4.STOP_SOUND);
        soundRewriter.register1_19_3Sound(ClientboundPackets1_19_4.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_19_4.ENTITY_SOUND);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.ACTIONBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.TITLE_SUBTITLE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_19_4.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_19_4.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19_4.TAB_LIST);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_19_4.COMBAT_KILL);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SYSTEM_CHAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.DISGUISED_CHAT);
        this.translatableRewriter.registerPing();
        new CommandRewriter<ClientboundPackets1_19_4>(this, (Protocol)this){
            final Protocol1_19_3To1_19_4 this$0;
            {
                this.this$0 = protocol1_19_3To1_19_4;
                super(protocol);
            }

            @Override
            public void handleArgument(PacketWrapper packetWrapper, String string) throws Exception {
                switch (string) {
                    case "minecraft:heightmap": {
                        packetWrapper.write(Type.VAR_INT, 0);
                        break;
                    }
                    case "minecraft:time": {
                        packetWrapper.read(Type.INT);
                        break;
                    }
                    case "minecraft:resource": 
                    case "minecraft:resource_or_tag": {
                        String string2 = packetWrapper.read(Type.STRING);
                        packetWrapper.write(Type.STRING, string2.equals("minecraft:damage_type") ? "minecraft:mob_effect" : string2);
                        break;
                    }
                    default: {
                        super.handleArgument(packetWrapper, string);
                    }
                }
            }
        }.registerDeclareCommands1_19(ClientboundPackets1_19_4.DECLARE_COMMANDS);
        TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter<ClientboundPackets1_19_4>(this);
        tagRewriter.removeTags("minecraft:damage_type");
        tagRewriter.registerGeneric(ClientboundPackets1_19_4.TAGS);
        new StatisticsRewriter<ClientboundPackets1_19_4>(this).register(ClientboundPackets1_19_4.STATISTICS);
        this.registerClientbound(ClientboundPackets1_19_4.SERVER_DATA, Protocol1_19_3To1_19_4::lambda$registerPackets$0);
        this.cancelClientbound(ClientboundPackets1_19_4.BUNDLE);
        this.cancelClientbound(ClientboundPackets1_19_4.CHUNK_BIOMES);
    }

    @Override
    public void init(UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_19_4Types.PLAYER));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public BlockItemPackets1_19_4 getItemRewriter() {
        return this.itemRewriter;
    }

    public EntityPackets1_19_4 getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_19_4> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
        packetWrapper.write(Type.OPTIONAL_COMPONENT, jsonElement);
        byte[] byArray = packetWrapper.read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
        String string = byArray != null ? "data:image/png;base64," + new String(Base64.getEncoder().encode(byArray), StandardCharsets.UTF_8) : null;
        packetWrapper.write(Type.OPTIONAL_STRING, string);
    }
}

