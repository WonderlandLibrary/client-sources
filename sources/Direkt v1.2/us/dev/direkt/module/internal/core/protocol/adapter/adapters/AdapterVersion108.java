/*package us.dev.direkt.module.internal.core.protocol.adapter.adapters;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import us.dev.direkt.event.internal.events.game.network.EventDecodePacket;
import us.dev.direkt.module.internal.core.protocol.adapter.ProtocolAdapter;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.io.IOException;

*//**
 * @author Foundry
 *//*
public class AdapterVersion108 extends ProtocolAdapter {
    public AdapterVersion108() {
        super(108, "1.9.1");
    }

    @Listener
    protected Link<EventDecodePacket> onDecodePacket = new Link<>(event -> {
        if (event.getPacket() instanceof SPacketJoinGame) {
            event.setPacket(new SPacketJoinGame() {
                @Override
                public void readPacketData(PacketBuffer buf) throws IOException {
                    this.playerId = buf.readInt();
                    int i = buf.readUnsignedByte();
                    this.hardcoreMode = (i & 8) == 8;
                    i = i & -9;
                    this.gameType = WorldSettings.GameType.getByID(i);
                    this.dimension = buf.readInt();      Read an additional 3 bytes here to advance the read position 
                    this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
                    this.maxPlayers = buf.readUnsignedByte();
                    this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));

                    if (this.worldType == null) {
                        this.worldType = WorldType.DEFAULT;
                    }

                    this.reducedDebugInfo = buf.readBoolean();
                    buf.clear();
                }
            });
        }
    });
}

*/