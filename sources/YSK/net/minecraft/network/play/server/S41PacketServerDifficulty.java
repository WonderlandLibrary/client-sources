package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.*;
import java.io.*;
import net.minecraft.network.*;

public class S41PacketServerDifficulty implements Packet<INetHandlerPlayClient>
{
    private boolean difficultyLocked;
    private EnumDifficulty difficulty;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
    }
    
    public S41PacketServerDifficulty() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleServerDifficulty(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
    }
    
    public S41PacketServerDifficulty(final EnumDifficulty difficulty, final boolean difficultyLocked) {
        this.difficulty = difficulty;
        this.difficultyLocked = difficultyLocked;
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
}
