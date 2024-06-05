package net.minecraft.network.play.client;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C10PacketCreativeInventoryAction implements Packet
{
    private int slotId;
    private ItemStack stack;
    
    public C10PacketCreativeInventoryAction() {
    }
    
    public C10PacketCreativeInventoryAction(final int p_i45263_1_, final ItemStack p_i45263_2_) {
        this.slotId = p_i45263_1_;
        this.stack = ((p_i45263_2_ != null) ? p_i45263_2_.copy() : null);
    }
    
    public void func_180767_a(final INetHandlerPlayServer p_180767_1_) {
        p_180767_1_.processCreativeInventoryAction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.slotId = data.readShort();
        this.stack = data.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeShort(this.slotId);
        data.writeItemStackToBuffer(this.stack);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180767_a((INetHandlerPlayServer)handler);
    }
}
