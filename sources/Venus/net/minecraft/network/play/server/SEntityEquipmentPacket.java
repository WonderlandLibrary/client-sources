/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SEntityEquipmentPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityID;
    private final List<Pair<EquipmentSlotType, ItemStack>> field_241789_b_;

    public SEntityEquipmentPacket() {
        this.field_241789_b_ = Lists.newArrayList();
    }

    public SEntityEquipmentPacket(int n, List<Pair<EquipmentSlotType, ItemStack>> list) {
        this.entityID = n;
        this.field_241789_b_ = list;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        byte by;
        this.entityID = packetBuffer.readVarInt();
        EquipmentSlotType[] equipmentSlotTypeArray = EquipmentSlotType.values();
        do {
            by = packetBuffer.readByte();
            EquipmentSlotType equipmentSlotType = equipmentSlotTypeArray[by & 0x7F];
            ItemStack itemStack = packetBuffer.readItemStack();
            this.field_241789_b_.add(Pair.of(equipmentSlotType, itemStack));
        } while ((by & 0xFFFFFF80) != 0);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityID);
        int n = this.field_241789_b_.size();
        for (int i = 0; i < n; ++i) {
            Pair<EquipmentSlotType, ItemStack> pair = this.field_241789_b_.get(i);
            EquipmentSlotType equipmentSlotType = pair.getFirst();
            boolean bl = i != n - 1;
            int n2 = equipmentSlotType.ordinal();
            packetBuffer.writeByte(bl ? n2 | 0xFFFFFF80 : n2);
            packetBuffer.writeItemStack(pair.getSecond());
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityEquipment(this);
    }

    public int getEntityID() {
        return this.entityID;
    }

    public List<Pair<EquipmentSlotType, ItemStack>> func_241790_c_() {
        return this.field_241789_b_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

