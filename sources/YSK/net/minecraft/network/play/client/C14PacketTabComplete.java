package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;
import org.apache.commons.lang3.*;

public class C14PacketTabComplete implements Packet<INetHandlerPlayServer>
{
    private String message;
    private BlockPos targetBlock;
    
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(24774 + 26854 - 42824 + 23963);
        if (packetBuffer.readBoolean()) {
            this.targetBlock = packetBuffer.readBlockPos();
        }
    }
    
    public C14PacketTabComplete(final String s) {
        this(s, null);
    }
    
    public C14PacketTabComplete() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processTabComplete(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(StringUtils.substring(this.message, "".length(), 10113 + 27721 - 29559 + 24492));
        int n;
        if (this.targetBlock != null) {
            n = " ".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        packetBuffer.writeBoolean(n2 != 0);
        if (n2 != 0) {
            packetBuffer.writeBlockPos(this.targetBlock);
        }
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public C14PacketTabComplete(final String message, final BlockPos targetBlock) {
        this.message = message;
        this.targetBlock = targetBlock;
    }
    
    public BlockPos getTargetBlock() {
        return this.targetBlock;
    }
}
