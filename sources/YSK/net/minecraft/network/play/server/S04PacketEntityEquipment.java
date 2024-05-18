package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;

public class S04PacketEntityEquipment implements Packet<INetHandlerPlayClient>
{
    private ItemStack itemStack;
    private int entityID;
    private int equipmentSlot;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.equipmentSlot = packetBuffer.readShort();
        this.itemStack = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeShort(this.equipmentSlot);
        packetBuffer.writeItemStackToBuffer(this.itemStack);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityEquipment(this);
    }
    
    public int getEquipmentSlot() {
        return this.equipmentSlot;
    }
    
    public S04PacketEntityEquipment() {
    }
    
    public S04PacketEntityEquipment(final int entityID, final int equipmentSlot, final ItemStack itemStack) {
        this.entityID = entityID;
        this.equipmentSlot = equipmentSlot;
        ItemStack copy;
        if (itemStack == null) {
            copy = null;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            copy = itemStack.copy();
        }
        this.itemStack = copy;
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
    
    public int getEntityID() {
        return this.entityID;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
