package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.entity.player.*;

public class C13PacketPlayerAbilities implements Packet<INetHandlerPlayServer>
{
    private boolean allowFlying;
    private boolean flying;
    private float flySpeed;
    private boolean creativeMode;
    private float walkSpeed;
    private boolean invulnerable;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }
    
    public boolean isCreativeMode() {
        return this.creativeMode;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        final byte byte1 = packetBuffer.readByte();
        int invulnerable;
        if ((byte1 & " ".length()) > 0) {
            invulnerable = " ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            invulnerable = "".length();
        }
        this.setInvulnerable(invulnerable != 0);
        int flying;
        if ((byte1 & "  ".length()) > 0) {
            flying = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            flying = "".length();
        }
        this.setFlying(flying != 0);
        int allowFlying;
        if ((byte1 & (0x87 ^ 0x83)) > 0) {
            allowFlying = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            allowFlying = "".length();
        }
        this.setAllowFlying(allowFlying != 0);
        int creativeMode;
        if ((byte1 & (0x19 ^ 0x11)) > 0) {
            creativeMode = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            creativeMode = "".length();
        }
        this.setCreativeMode(creativeMode != 0);
        this.setFlySpeed(packetBuffer.readFloat());
        this.setWalkSpeed(packetBuffer.readFloat());
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerAbilities(this);
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public C13PacketPlayerAbilities(final PlayerCapabilities playerCapabilities) {
        this.setInvulnerable(playerCapabilities.disableDamage);
        this.setFlying(playerCapabilities.isFlying);
        this.setAllowFlying(playerCapabilities.allowFlying);
        this.setCreativeMode(playerCapabilities.isCreativeMode);
        this.setFlySpeed(playerCapabilities.getFlySpeed());
        this.setWalkSpeed(playerCapabilities.getWalkSpeed());
    }
    
    public void setInvulnerable(final boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
    
    public void setWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    public boolean isFlying() {
        return this.flying;
    }
    
    public boolean isInvulnerable() {
        return this.invulnerable;
    }
    
    public void setAllowFlying(final boolean allowFlying) {
        this.allowFlying = allowFlying;
    }
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
    }
    
    public void setFlying(final boolean flying) {
        this.flying = flying;
    }
    
    public C13PacketPlayerAbilities() {
    }
    
    public boolean isAllowFlying() {
        return this.allowFlying;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        int length = "".length();
        if (this.isInvulnerable()) {
            length = (byte)(length | " ".length());
        }
        if (this.isFlying()) {
            length = (byte)(length | "  ".length());
        }
        if (this.isAllowFlying()) {
            length = (byte)(length | (0x8A ^ 0x8E));
        }
        if (this.isCreativeMode()) {
            length = (byte)(length | (0x9D ^ 0x95));
        }
        packetBuffer.writeByte(length);
        packetBuffer.writeFloat(this.flySpeed);
        packetBuffer.writeFloat(this.walkSpeed);
    }
}
