/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.EntityPackets1_18;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.TagRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_17_1To1_18
extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final EntityPackets1_18 entityRewriter = new EntityPackets1_18(this);
    private final BlockItemPackets1_18 itemRewriter = new BlockItemPackets1_18(this);
    private final TranslatableRewriter<ClientboundPackets1_18> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_18>(this);

    public Protocol1_17_1To1_18() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.CHAT_MESSAGE);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.ACTIONBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_SUBTITLE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_18.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_18.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_18.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_18.OPEN_WINDOW);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_18.COMBAT_KILL);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter<ClientboundPackets1_18>(this);
        soundRewriter.registerSound(ClientboundPackets1_18.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_18.ENTITY_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_18.STOP_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_18.NAMED_SOUND);
        TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter<ClientboundPackets1_18>(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables");
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        this.registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, new PacketHandlers(this){
            final Protocol1_17_1To1_18 this$0;
            {
                this.this$0 = protocol1_17_1To1_18;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.create(Type.BOOLEAN, true);
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SCOREBOARD_OBJECTIVE, new PacketHandlers(this){
            final Protocol1_17_1To1_18 this$0;
            {
                this.this$0 = protocol1_17_1To1_18;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(Protocol1_17_1To1_18.access$000(this.this$0, 0, 16));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.DISPLAY_SCOREBOARD, new PacketHandlers(this){
            final Protocol1_17_1To1_18 this$0;
            {
                this.this$0 = protocol1_17_1To1_18;
            }

            @Override
            public void register() {
                this.map(Type.BYTE);
                this.map(Type.STRING);
                this.handler(Protocol1_17_1To1_18.access$000(this.this$0, 0, 16));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.TEAMS, new PacketHandlers(this){
            final Protocol1_17_1To1_18 this$0;
            {
                this.this$0 = protocol1_17_1To1_18;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(Protocol1_17_1To1_18.access$000(this.this$0, 0, 16));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.UPDATE_SCORE, new PacketHandlers(this){
            final Protocol1_17_1To1_18 this$0;
            {
                this.this$0 = protocol1_17_1To1_18;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(Protocol1_17_1To1_18.access$000(this.this$0, 0, 40));
                this.handler(Protocol1_17_1To1_18.access$000(this.this$0, 1, 16));
            }
        });
    }

    private PacketHandler cutName(int n, int n2) {
        return arg_0 -> Protocol1_17_1To1_18.lambda$cutName$0(n, n2, arg_0);
    }

    @Override
    public void init(UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_17Types.PLAYER));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_18 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_18 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_18> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public com.viaversion.viabackwards.api.data.BackwardsMappings getMappingData() {
        return this.getMappingData();
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

    private static void lambda$cutName$0(int n, int n2, PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.get(Type.STRING, n);
        if (string.length() > n2) {
            packetWrapper.set(Type.STRING, n, string.substring(0, n2));
        }
    }

    static PacketHandler access$000(Protocol1_17_1To1_18 protocol1_17_1To1_18, int n, int n2) {
        return protocol1_17_1To1_18.cutName(n, n2);
    }
}

