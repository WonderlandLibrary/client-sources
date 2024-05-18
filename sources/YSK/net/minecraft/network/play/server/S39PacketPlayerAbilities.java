package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class S39PacketPlayerAbilities implements Packet<INetHandlerPlayClient>
{
    private boolean invulnerable;
    private float flySpeed;
    private boolean flying;
    private boolean creativeMode;
    private boolean allowFlying;
    private float walkSpeed;
    
    public void setWalkSpeed(final float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
    
    public S39PacketPlayerAbilities() {
    }
    
    public void setFlySpeed(final float flySpeed) {
        this.flySpeed = flySpeed;
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        final byte byte1 = packetBuffer.readByte();
        int invulnerable;
        if ((byte1 & " ".length()) > 0) {
            invulnerable = " ".length();
            "".length();
            if (-1 >= 4) {
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
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            flying = "".length();
        }
        this.setFlying(flying != 0);
        int allowFlying;
        if ((byte1 & (0xA5 ^ 0xA1)) > 0) {
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
        if ((byte1 & (0x88 ^ 0x80)) > 0) {
            creativeMode = " ".length();
            "".length();
            if (2 != 2) {
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
    
    public boolean isCreativeMode() {
        return this.creativeMode;
    }
    
    public S39PacketPlayerAbilities(final PlayerCapabilities playerCapabilities) {
        this.setInvulnerable(playerCapabilities.disableDamage);
        this.setFlying(playerCapabilities.isFlying);
        this.setAllowFlying(playerCapabilities.allowFlying);
        this.setCreativeMode(playerCapabilities.isCreativeMode);
        this.setFlySpeed(playerCapabilities.getFlySpeed());
        this.setWalkSpeed(playerCapabilities.getWalkSpeed());
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }
    
    public void setInvulnerable(final boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
    
    public void setAllowFlying(final boolean allowFlying) {
        this.allowFlying = allowFlying;
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
            length = (byte)(length | (0x19 ^ 0x1D));
        }
        if (this.isCreativeMode()) {
            length = (byte)(length | (0x73 ^ 0x7B));
        }
        packetBuffer.writeByte(length);
        packetBuffer.writeFloat(this.flySpeed);
        packetBuffer.writeFloat(this.walkSpeed);
    }
    
    public boolean isAllowFlying() {
        return this.allowFlying;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isInvulnerable() {
        return this.invulnerable;
    }
    
    public void setFlying(final boolean flying) {
        this.flying = flying;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public boolean isFlying() {
        return this.flying;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerAbilities(this);
    }
}
