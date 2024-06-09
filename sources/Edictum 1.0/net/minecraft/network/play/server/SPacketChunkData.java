package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class SPacketChunkData implements Packet<INetHandlerPlayClient>
{
    private int chunkX;
    private int chunkZ;
    private int availableSections;
    private byte[] buffer;
    private List<NBTTagCompound> field_189557_e;
    private boolean loadChunk;

    public SPacketChunkData()
    {
    }

    public SPacketChunkData(Chunk p_i47124_1_, int p_i47124_2_)
    {
        this.chunkX = p_i47124_1_.xPosition;
        this.chunkZ = p_i47124_1_.zPosition;
        this.loadChunk = p_i47124_2_ == 65535;
        boolean flag = !p_i47124_1_.getWorld().provider.getHasNoSky();
        this.buffer = new byte[this.func_189556_a(p_i47124_1_, flag, p_i47124_2_)];
        this.availableSections = this.func_189555_a(new PacketBuffer(this.getWriteBuffer()), p_i47124_1_, flag, p_i47124_2_);
        this.field_189557_e = Lists.<NBTTagCompound>newArrayList();

        for (Entry<BlockPos, TileEntity> entry : p_i47124_1_.getTileEntityMap().entrySet())
        {
            BlockPos blockpos = (BlockPos)entry.getKey();
            TileEntity tileentity = (TileEntity)entry.getValue();
            int i = blockpos.getY() >> 4;

            if (this.doChunkLoad() || (p_i47124_2_ & 1 << i) != 0)
            {
                NBTTagCompound nbttagcompound = tileentity.func_189517_E_();
                this.field_189557_e.add(nbttagcompound);
            }
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.chunkX = buf.readInt();
        this.chunkZ = buf.readInt();
        this.loadChunk = buf.readBoolean();
        this.availableSections = buf.readVarIntFromBuffer();
        int i = buf.readVarIntFromBuffer();

        if (i > 2097152)
        {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
        }
        else
        {
            this.buffer = new byte[i];
            buf.readBytes(this.buffer);
            int j = buf.readVarIntFromBuffer();
            this.field_189557_e = Lists.<NBTTagCompound>newArrayList();

            for (int k = 0; k < j; ++k)
            {
                this.field_189557_e.add(buf.readNBTTagCompoundFromBuffer());
            }
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.chunkX);
        buf.writeInt(this.chunkZ);
        buf.writeBoolean(this.loadChunk);
        buf.writeVarIntToBuffer(this.availableSections);
        buf.writeVarIntToBuffer(this.buffer.length);
        buf.writeBytes(this.buffer);
        buf.writeVarIntToBuffer(this.field_189557_e.size());

        for (NBTTagCompound nbttagcompound : this.field_189557_e)
        {
            buf.writeNBTTagCompoundToBuffer(nbttagcompound);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleChunkData(this);
    }

    public PacketBuffer getReadBuffer()
    {
        return new PacketBuffer(Unpooled.wrappedBuffer(this.buffer));
    }

    private ByteBuf getWriteBuffer()
    {
        ByteBuf bytebuf = Unpooled.wrappedBuffer(this.buffer);
        bytebuf.writerIndex(0);
        return bytebuf;
    }

    public int func_189555_a(PacketBuffer p_189555_1_, Chunk p_189555_2_, boolean p_189555_3_, int p_189555_4_)
    {
        int i = 0;
        ExtendedBlockStorage[] aextendedblockstorage = p_189555_2_.getBlockStorageArray();
        int j = 0;

        for (int k = aextendedblockstorage.length; j < k; ++j)
        {
            ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];

            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && (!this.doChunkLoad() || !extendedblockstorage.isEmpty()) && (p_189555_4_ & 1 << j) != 0)
            {
                i |= 1 << j;
                extendedblockstorage.getData().write(p_189555_1_);
                p_189555_1_.writeBytes(extendedblockstorage.getBlocklightArray().getData());

                if (p_189555_3_)
                {
                    p_189555_1_.writeBytes(extendedblockstorage.getSkylightArray().getData());
                }
            }
        }

        if (this.doChunkLoad())
        {
            p_189555_1_.writeBytes(p_189555_2_.getBiomeArray());
        }

        return i;
    }

    protected int func_189556_a(Chunk p_189556_1_, boolean p_189556_2_, int p_189556_3_)
    {
        int i = 0;
        ExtendedBlockStorage[] aextendedblockstorage = p_189556_1_.getBlockStorageArray();
        int j = 0;

        for (int k = aextendedblockstorage.length; j < k; ++j)
        {
            ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];

            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && (!this.doChunkLoad() || !extendedblockstorage.isEmpty()) && (p_189556_3_ & 1 << j) != 0)
            {
                i = i + extendedblockstorage.getData().getSerializedSize();
                i = i + extendedblockstorage.getBlocklightArray().getData().length;

                if (p_189556_2_)
                {
                    i += extendedblockstorage.getSkylightArray().getData().length;
                }
            }
        }

        if (this.doChunkLoad())
        {
            i += p_189556_1_.getBiomeArray().length;
        }

        return i;
    }

    public int getChunkX()
    {
        return this.chunkX;
    }

    public int getChunkZ()
    {
        return this.chunkZ;
    }

    public int getExtractedSize()
    {
        return this.availableSections;
    }

    public boolean doChunkLoad()
    {
        return this.loadChunk;
    }

    public List<NBTTagCompound> func_189554_f()
    {
        return this.field_189557_e;
    }
}
