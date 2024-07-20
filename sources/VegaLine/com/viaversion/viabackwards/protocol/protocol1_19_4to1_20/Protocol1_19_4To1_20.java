/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets.BlockItemPackets1_20;
import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets.EntityPackets1_20;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.Arrays;

public final class Protocol1_19_4To1_20
extends BackwardsProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_4, ServerboundPackets1_19_4, ServerboundPackets1_19_4> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final TranslatableRewriter<ClientboundPackets1_19_4> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19_4>(this);
    private final EntityPackets1_20 entityRewriter = new EntityPackets1_20(this);
    private final BlockItemPackets1_20 itemRewriter = new BlockItemPackets1_20(this);

    public Protocol1_19_4To1_20() {
        super(ClientboundPackets1_19_4.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_4.class, ServerboundPackets1_19_4.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter<ClientboundPackets1_19_4>(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:replaceable_plants");
        tagRewriter.registerGeneric(ClientboundPackets1_19_4.TAGS);
        SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter<ClientboundPackets1_19_4>(this);
        soundRewriter.registerStopSound(ClientboundPackets1_19_4.STOP_SOUND);
        soundRewriter.register1_19_3Sound(ClientboundPackets1_19_4.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_19_4.ENTITY_SOUND);
        new StatisticsRewriter<ClientboundPackets1_19_4>(this).register(ClientboundPackets1_19_4.STATISTICS);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.ACTIONBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.TITLE_SUBTITLE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_19_4.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_19_4.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19_4.TAB_LIST);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SYSTEM_CHAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.DISGUISED_CHAT);
        this.translatableRewriter.registerPing();
        this.registerClientbound(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES, (PacketWrapper wrapper) -> {
            String[] enabledFeatures = wrapper.read(Type.STRING_ARRAY);
            int length = enabledFeatures.length;
            enabledFeatures = Arrays.copyOf(enabledFeatures, length + 1);
            enabledFeatures[length] = "minecraft:update_1_20";
            wrapper.write(Type.STRING_ARRAY, enabledFeatures);
        });
        this.registerClientbound(ClientboundPackets1_19_4.COMBAT_END, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.INT, -1);
        });
        this.registerClientbound(ClientboundPackets1_19_4.COMBAT_KILL, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.INT, -1);
            this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT));
        });
    }

    @Override
    public void init(UserConnection user) {
        this.addEntityTracker(user, new EntityTrackerBase(user, Entity1_19_4Types.PLAYER));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_20 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_20 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_19_4> getTranslatableRewriter() {
        return this.translatableRewriter;
    }
}

