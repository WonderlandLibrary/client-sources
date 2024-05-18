package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class S29PacketSoundEffect implements Packet<INetHandlerPlayClient>
{
    private static final String[] I;
    private float soundVolume;
    private int posX;
    private int posZ;
    private int posY;
    private String soundName;
    private int soundPitch;
    
    public float getVolume() {
        return this.soundVolume;
    }
    
    public double getX() {
        return this.posX / 8.0f;
    }
    
    public String getSoundName() {
        return this.soundName;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.soundName);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeFloat(this.soundVolume);
        packetBuffer.writeByte(this.soundPitch);
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0018\t\u001a\u001c", "vhwym");
    }
    
    public double getZ() {
        return this.posZ / 8.0f;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.soundName = packetBuffer.readStringFromBuffer(150 + 149 - 214 + 171);
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.soundVolume = packetBuffer.readFloat();
        this.soundPitch = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSoundEffect(this);
    }
    
    public double getY() {
        return this.posY / 8.0f;
    }
    
    static {
        I();
    }
    
    public float getPitch() {
        return this.soundPitch / 63.0f;
    }
    
    public S29PacketSoundEffect(final String soundName, final double n, final double n2, final double n3, final float soundVolume, float clamp_float) {
        this.posY = 15187646 + 948729535 + 793440115 + 390126351;
        Validate.notNull((Object)soundName, S29PacketSoundEffect.I["".length()], new Object["".length()]);
        this.soundName = soundName;
        this.posX = (int)(n * 8.0);
        this.posY = (int)(n2 * 8.0);
        this.posZ = (int)(n3 * 8.0);
        this.soundVolume = soundVolume;
        this.soundPitch = (int)(clamp_float * 63.0f);
        clamp_float = MathHelper.clamp_float(clamp_float, 0.0f, 255.0f);
    }
    
    public S29PacketSoundEffect() {
        this.posY = 1970972704 + 694249450 - 660403467 + 142664960;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
