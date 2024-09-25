/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9;

import com.google.common.collect.ImmutableSet;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.ScoreboardPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import de.gerrygames.viarewind.utils.Ticker;
import java.util.Timer;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class Protocol1_8TO1_9
extends Protocol {
    public static final Timer TIMER = new Timer("ViaRewind-1_8TO1_9", true);
    public static final ImmutableSet<Object> VALID_ATTRIBUTES;
    public static final ValueTransformer<Double, Integer> TO_OLD_INT;
    public static final ValueTransformer<Float, Byte> DEGREES_TO_ANGLE;

    @Override
    protected void registerPackets() {
        EntityPackets.register(this);
        InventoryPackets.register(this);
        PlayerPackets.register(this);
        ScoreboardPackets.register(this);
        SpawnPackets.register(this);
        WorldPackets.register(this);
        this.registerOutgoing(State.PLAY, 31, 0);
        this.registerIncoming(State.PLAY, 11, 0);
    }

    @Override
    public void init(UserConnection userConnection) {
        Ticker.init();
        userConnection.put(new Windows(userConnection));
        userConnection.put(new EntityTracker(userConnection));
        userConnection.put(new Levitation(userConnection));
        userConnection.put(new PlayerPosition(userConnection));
        userConnection.put(new Cooldown(userConnection));
        userConnection.put(new BlockPlaceDestroyTracker(userConnection));
        userConnection.put(new BossBarStorage(userConnection));
        userConnection.put(new ClientWorld(userConnection));
    }

    static {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        builder.add((Object)"generic.maxHealth");
        builder.add((Object)"generic.followRange");
        builder.add((Object)"generic.knockbackResistance");
        builder.add((Object)"generic.movementSpeed");
        builder.add((Object)"generic.attackDamage");
        builder.add((Object)"horse.jumpStrength");
        builder.add((Object)"zombie.spawnReinforcements");
        VALID_ATTRIBUTES = builder.build();
        TO_OLD_INT = new ValueTransformer<Double, Integer>(Type.INT){

            @Override
            public Integer transform(PacketWrapper wrapper, Double inputValue) {
                return (int)(inputValue * 32.0);
            }
        };
        DEGREES_TO_ANGLE = new ValueTransformer<Float, Byte>(Type.BYTE){

            @Override
            public Byte transform(PacketWrapper packetWrapper, Float degrees) throws Exception {
                return (byte)(degrees.floatValue() / 360.0f * 256.0f);
            }
        };
    }
}

