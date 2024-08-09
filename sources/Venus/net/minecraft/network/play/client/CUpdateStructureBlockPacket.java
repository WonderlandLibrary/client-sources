/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CUpdateStructureBlockPacket
implements IPacket<IServerPlayNetHandler> {
    private BlockPos pos;
    private StructureBlockTileEntity.UpdateCommand field_210392_b;
    private StructureMode mode;
    private String name;
    private BlockPos field_210395_e;
    private BlockPos size;
    private Mirror mirror;
    private Rotation rotation;
    private String field_210399_i;
    private boolean field_210400_j;
    private boolean field_210401_k;
    private boolean field_210402_l;
    private float integrity;
    private long seed;

    public CUpdateStructureBlockPacket() {
    }

    public CUpdateStructureBlockPacket(BlockPos blockPos, StructureBlockTileEntity.UpdateCommand updateCommand, StructureMode structureMode, String string, BlockPos blockPos2, BlockPos blockPos3, Mirror mirror, Rotation rotation, String string2, boolean bl, boolean bl2, boolean bl3, float f, long l) {
        this.pos = blockPos;
        this.field_210392_b = updateCommand;
        this.mode = structureMode;
        this.name = string;
        this.field_210395_e = blockPos2;
        this.size = blockPos3;
        this.mirror = mirror;
        this.rotation = rotation;
        this.field_210399_i = string2;
        this.field_210400_j = bl;
        this.field_210401_k = bl2;
        this.field_210402_l = bl3;
        this.integrity = f;
        this.seed = l;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.field_210392_b = packetBuffer.readEnumValue(StructureBlockTileEntity.UpdateCommand.class);
        this.mode = packetBuffer.readEnumValue(StructureMode.class);
        this.name = packetBuffer.readString(Short.MAX_VALUE);
        int n = 48;
        this.field_210395_e = new BlockPos(MathHelper.clamp(packetBuffer.readByte(), -48, 48), MathHelper.clamp(packetBuffer.readByte(), -48, 48), MathHelper.clamp(packetBuffer.readByte(), -48, 48));
        int n2 = 48;
        this.size = new BlockPos(MathHelper.clamp(packetBuffer.readByte(), 0, 48), MathHelper.clamp(packetBuffer.readByte(), 0, 48), MathHelper.clamp(packetBuffer.readByte(), 0, 48));
        this.mirror = packetBuffer.readEnumValue(Mirror.class);
        this.rotation = packetBuffer.readEnumValue(Rotation.class);
        this.field_210399_i = packetBuffer.readString(12);
        this.integrity = MathHelper.clamp(packetBuffer.readFloat(), 0.0f, 1.0f);
        this.seed = packetBuffer.readVarLong();
        byte by = packetBuffer.readByte();
        this.field_210400_j = (by & 1) != 0;
        this.field_210401_k = (by & 2) != 0;
        this.field_210402_l = (by & 4) != 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        packetBuffer.writeEnumValue(this.field_210392_b);
        packetBuffer.writeEnumValue(this.mode);
        packetBuffer.writeString(this.name);
        packetBuffer.writeByte(this.field_210395_e.getX());
        packetBuffer.writeByte(this.field_210395_e.getY());
        packetBuffer.writeByte(this.field_210395_e.getZ());
        packetBuffer.writeByte(this.size.getX());
        packetBuffer.writeByte(this.size.getY());
        packetBuffer.writeByte(this.size.getZ());
        packetBuffer.writeEnumValue(this.mirror);
        packetBuffer.writeEnumValue(this.rotation);
        packetBuffer.writeString(this.field_210399_i);
        packetBuffer.writeFloat(this.integrity);
        packetBuffer.writeVarLong(this.seed);
        int n = 0;
        if (this.field_210400_j) {
            n |= 1;
        }
        if (this.field_210401_k) {
            n |= 2;
        }
        if (this.field_210402_l) {
            n |= 4;
        }
        packetBuffer.writeByte(n);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processUpdateStructureBlock(this);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public StructureBlockTileEntity.UpdateCommand func_210384_b() {
        return this.field_210392_b;
    }

    public StructureMode getMode() {
        return this.mode;
    }

    public String getName() {
        return this.name;
    }

    public BlockPos getPosition() {
        return this.field_210395_e;
    }

    public BlockPos getSize() {
        return this.size;
    }

    public Mirror getMirror() {
        return this.mirror;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public String getMetadata() {
        return this.field_210399_i;
    }

    public boolean shouldIgnoreEntities() {
        return this.field_210400_j;
    }

    public boolean shouldShowAir() {
        return this.field_210401_k;
    }

    public boolean shouldShowBoundingBox() {
        return this.field_210402_l;
    }

    public float getIntegrity() {
        return this.integrity;
    }

    public long getSeed() {
        return this.seed;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

