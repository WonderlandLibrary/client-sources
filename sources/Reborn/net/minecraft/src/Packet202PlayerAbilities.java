package net.minecraft.src;

import java.io.*;

public class Packet202PlayerAbilities extends Packet
{
    private boolean disableDamage;
    private boolean isFlying;
    private boolean allowFlying;
    private boolean isCreativeMode;
    private float flySpeed;
    private float walkSpeed;
    
    public Packet202PlayerAbilities() {
        this.disableDamage = false;
        this.isFlying = false;
        this.allowFlying = false;
        this.isCreativeMode = false;
    }
    
    public Packet202PlayerAbilities(final PlayerCapabilities par1PlayerCapabilities) {
        this.disableDamage = false;
        this.isFlying = false;
        this.allowFlying = false;
        this.isCreativeMode = false;
        this.setDisableDamage(par1PlayerCapabilities.disableDamage);
        this.setFlying(par1PlayerCapabilities.isFlying);
        this.setAllowFlying(par1PlayerCapabilities.allowFlying);
        this.setCreativeMode(par1PlayerCapabilities.isCreativeMode);
        this.setFlySpeed(par1PlayerCapabilities.getFlySpeed());
        this.setWalkSpeed(par1PlayerCapabilities.getWalkSpeed());
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        final byte var2 = par1DataInputStream.readByte();
        this.setDisableDamage((var2 & 0x1) > 0);
        this.setFlying((var2 & 0x2) > 0);
        this.setAllowFlying((var2 & 0x4) > 0);
        this.setCreativeMode((var2 & 0x8) > 0);
        this.setFlySpeed(par1DataInputStream.readByte() / 255.0f);
        this.setWalkSpeed(par1DataInputStream.readByte() / 255.0f);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        byte var2 = 0;
        if (this.getDisableDamage()) {
            var2 |= 0x1;
        }
        if (this.getFlying()) {
            var2 |= 0x2;
        }
        if (this.getAllowFlying()) {
            var2 |= 0x4;
        }
        if (this.isCreativeMode()) {
            var2 |= 0x8;
        }
        par1DataOutputStream.writeByte(var2);
        par1DataOutputStream.writeByte((int)(this.flySpeed * 255.0f));
        par1DataOutputStream.writeByte((int)(this.walkSpeed * 255.0f));
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handlePlayerAbilities(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2;
    }
    
    public boolean getDisableDamage() {
        return this.disableDamage;
    }
    
    public void setDisableDamage(final boolean par1) {
        this.disableDamage = par1;
    }
    
    public boolean getFlying() {
        return this.isFlying;
    }
    
    public void setFlying(final boolean par1) {
        this.isFlying = par1;
    }
    
    public boolean getAllowFlying() {
        return this.allowFlying;
    }
    
    public void setAllowFlying(final boolean par1) {
        this.allowFlying = par1;
    }
    
    public boolean isCreativeMode() {
        return this.isCreativeMode;
    }
    
    public void setCreativeMode(final boolean par1) {
        this.isCreativeMode = par1;
    }
    
    public float getFlySpeed() {
        return this.flySpeed;
    }
    
    public void setFlySpeed(final float par1) {
        this.flySpeed = par1;
    }
    
    public float getWalkSpeed() {
        return this.walkSpeed;
    }
    
    public void setWalkSpeed(final float par1) {
        this.walkSpeed = par1;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        return true;
    }
}
