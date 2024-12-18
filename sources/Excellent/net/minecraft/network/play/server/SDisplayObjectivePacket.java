package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScoreObjective;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

public class SDisplayObjectivePacket implements IPacket<IClientPlayNetHandler>
{
    private int position;
    private String scoreName;

    public SDisplayObjectivePacket()
    {
    }

    public SDisplayObjectivePacket(int positionIn, @Nullable ScoreObjective objective)
    {
        this.position = positionIn;

        if (objective == null)
        {
            this.scoreName = "";
        }
        else
        {
            this.scoreName = objective.getName();
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.position = buf.readByte();
        this.scoreName = buf.readString(16);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.position);
        buf.writeString(this.scoreName);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleDisplayObjective(this);
    }

    public int getPosition()
    {
        return this.position;
    }

    @Nullable
    public String getName()
    {
        return Objects.equals(this.scoreName, "") ? null : this.scoreName;
    }
}
