/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.IOException;
import java.util.function.BiConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.chunk.ChunkSection;

public class SMultiBlockChangePacket
implements IPacket<IClientPlayNetHandler> {
    private SectionPos field_244305_a;
    private short[] field_244306_b;
    private BlockState[] field_244307_c;
    private boolean field_244308_d;

    public SMultiBlockChangePacket() {
    }

    public SMultiBlockChangePacket(SectionPos sectionPos, ShortSet shortSet, ChunkSection chunkSection, boolean bl) {
        this.field_244305_a = sectionPos;
        this.field_244308_d = bl;
        this.func_244309_a(shortSet.size());
        int n = 0;
        ShortIterator shortIterator = shortSet.iterator();
        while (shortIterator.hasNext()) {
            short s;
            this.field_244306_b[n] = s = ((Short)shortIterator.next()).shortValue();
            this.field_244307_c[n] = chunkSection.getBlockState(SectionPos.func_243641_a(s), SectionPos.func_243642_b(s), SectionPos.func_243643_c(s));
            ++n;
        }
    }

    private void func_244309_a(int n) {
        this.field_244306_b = new short[n];
        this.field_244307_c = new BlockState[n];
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_244305_a = SectionPos.from(packetBuffer.readLong());
        this.field_244308_d = packetBuffer.readBoolean();
        int n = packetBuffer.readVarInt();
        this.func_244309_a(n);
        for (int i = 0; i < this.field_244306_b.length; ++i) {
            long l = packetBuffer.readVarLong();
            this.field_244306_b[i] = (short)(l & 0xFFFL);
            this.field_244307_c[i] = Block.BLOCK_STATE_IDS.getByValue((int)(l >>> 12));
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.field_244305_a.asLong());
        packetBuffer.writeBoolean(this.field_244308_d);
        packetBuffer.writeVarInt(this.field_244306_b.length);
        for (int i = 0; i < this.field_244306_b.length; ++i) {
            packetBuffer.writeVarLong(Block.getStateId(this.field_244307_c[i]) << 12 | this.field_244306_b[i]);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleMultiBlockChange(this);
    }

    public void func_244310_a(BiConsumer<BlockPos, BlockState> biConsumer) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < this.field_244306_b.length; ++i) {
            short s = this.field_244306_b[i];
            mutable.setPos(this.field_244305_a.func_243644_d(s), this.field_244305_a.func_243645_e(s), this.field_244305_a.func_243646_f(s));
            biConsumer.accept(mutable, this.field_244307_c[i]);
        }
    }

    public boolean func_244311_b() {
        return this.field_244308_d;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

