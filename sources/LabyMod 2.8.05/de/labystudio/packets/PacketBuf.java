package de.labystudio.packets;

import com.google.common.base.Charsets;
import de.labystudio.chat.LabyModPlayer;
import de.labystudio.chat.LabyModPlayerRequester;
import de.labystudio.chat.ServerInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;

public class PacketBuf extends ByteBuf
{
    private ByteBuf buf;

    public PacketBuf(ByteBuf buf)
    {
        this.buf = buf;
    }

    public ServerInfo readServerInfo()
    {
        String s = this.readString();
        int i = this.readInt();
        return this.readBoolean() ? new ServerInfo(s, i, this.readString()) : new ServerInfo(s, i);
    }

    public void writeByteArray(byte[] data)
    {
        this.writeInt(data.length);
        this.writeBytes(data);
    }

    public byte[] readByteArray()
    {
        byte[] abyte = new byte[this.readInt()];

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = this.readByte();
        }

        return abyte;
    }

    public PacketBuf writeServerInfo(ServerInfo info)
    {
        this.writeString(info.getServerIp());
        this.writeInt(info.getServerPort());

        if (info.getSpecifiedServerName() != null)
        {
            this.writeBoolean(true);
            this.writeString(info.getSpecifiedServerName());
        }
        else
        {
            this.writeBoolean(false);
        }

        return this;
    }

    public PacketBuf writeOnlineStatus(LabyModPlayer.OnlineStatus status)
    {
        this.writeByte(status.getPacketId());
        return this;
    }

    public LabyModPlayer.OnlineStatus readOnlineStatus()
    {
        return LabyModPlayer.OnlineStatus.fromPacketId(this.readByte());
    }

    public LabyModPlayer readPlayer()
    {
        String s = this.readString();
        UUID uuid = this.readUUID();
        String s1 = this.readString();
        LabyModPlayer.OnlineStatus labymodplayer$onlinestatus = this.readOnlineStatus();
        boolean flag = this.readBoolean();
        String s2 = this.readString();
        int i = this.readInt();
        long j = this.readLong();
        long k = this.readLong();
        ServerInfo serverinfo = this.readServerInfo();

        if (flag)
        {
            return new LabyModPlayerRequester(s, uuid);
        }
        else
        {
            LabyModPlayer labymodplayer = new LabyModPlayer(s, uuid, s1, labymodplayer$onlinestatus, s2, j, k, i);
            labymodplayer.updateServer(serverinfo);
            return labymodplayer;
        }
    }

    public void writeEnum(Enum<?> enume)
    {
        this.writeInt(enume.ordinal());
    }

    public void writePlayer(LabyModPlayer player)
    {
        this.writeString(player.getName());
        this.writeUUID(player.getId());
        this.writeString(player.getMotd());
        this.writeOnlineStatus(player.getStatus());
        this.writeBoolean(player.isRequest());
        this.writeString(player.getTimeZone());
        this.writeInt(player.getContactsAmount());
        this.writeLong(player.getLastOnline());
        this.writeLong(player.getJoinedFirst());
        this.writeServerInfo(player.getServerInfo());
    }

    public void writeUUID(UUID uuid)
    {
        this.writeString(uuid.toString());
    }

    public UUID readUUID()
    {
        return UUID.fromString(this.readString());
    }

    public void writeString(String string)
    {
        this.writeInt(string.getBytes(Charsets.UTF_8).length);
        this.writeBytes(string.getBytes(Charsets.UTF_8));
    }

    public String readString()
    {
        byte[] abyte = new byte[this.readInt()];

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = this.readByte();
        }

        return new String(abyte, Charsets.UTF_8);
    }

    public int refCnt()
    {
        return this.buf.refCnt();
    }

    public boolean release()
    {
        return this.buf.release();
    }

    public boolean release(int arg0)
    {
        return this.buf.release(arg0);
    }

    public ByteBufAllocator alloc()
    {
        return this.buf.alloc();
    }

    public byte[] array()
    {
        return this.buf.array();
    }

    public int arrayOffset()
    {
        return this.buf.arrayOffset();
    }

    public int bytesBefore(byte arg0)
    {
        return this.buf.bytesBefore(arg0);
    }

    public int bytesBefore(int arg0, byte arg1)
    {
        return this.buf.bytesBefore(arg0, arg1);
    }

    public int bytesBefore(int arg0, int arg1, byte arg2)
    {
        return this.buf.bytesBefore(arg0, arg1, arg2);
    }

    public int capacity()
    {
        return this.buf.capacity();
    }

    public ByteBuf capacity(int arg0)
    {
        return this.buf.capacity(arg0);
    }

    public ByteBuf clear()
    {
        return this.buf.clear();
    }

    public int compareTo(ByteBuf arg0)
    {
        return this.buf.compareTo(arg0);
    }

    public ByteBuf copy()
    {
        return this.buf.copy();
    }

    public ByteBuf copy(int arg0, int arg1)
    {
        return this.buf.copy(arg0, arg1);
    }

    public ByteBuf discardReadBytes()
    {
        return this.buf.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes()
    {
        return this.buf.discardSomeReadBytes();
    }

    public ByteBuf duplicate()
    {
        return this.buf.duplicate();
    }

    public ByteBuf ensureWritable(int arg0)
    {
        return this.buf.ensureWritable(arg0);
    }

    public int ensureWritable(int arg0, boolean arg1)
    {
        return this.buf.ensureWritable(arg0, arg1);
    }

    public boolean equals(Object arg0)
    {
        return this.buf.equals(arg0);
    }

    public int forEachByte(ByteBufProcessor arg0)
    {
        return this.buf.forEachByte(arg0);
    }

    public int forEachByte(int arg0, int arg1, ByteBufProcessor arg2)
    {
        return this.buf.forEachByte(arg0, arg1, arg2);
    }

    public int forEachByteDesc(ByteBufProcessor arg0)
    {
        return this.buf.forEachByteDesc(arg0);
    }

    public int forEachByteDesc(int arg0, int arg1, ByteBufProcessor arg2)
    {
        return this.buf.forEachByteDesc(arg0, arg1, arg2);
    }

    public boolean getBoolean(int arg0)
    {
        return this.buf.getBoolean(arg0);
    }

    public byte getByte(int arg0)
    {
        return this.buf.getByte(arg0);
    }

    public ByteBuf getBytes(int arg0, ByteBuf arg1)
    {
        return this.buf.getBytes(arg0, arg1);
    }

    public ByteBuf getBytes(int arg0, byte[] arg1)
    {
        return this.buf.getBytes(arg0, arg1);
    }

    public ByteBuf getBytes(int arg0, ByteBuffer arg1)
    {
        return this.buf.getBytes(arg0, arg1);
    }

    public ByteBuf getBytes(int arg0, ByteBuf arg1, int arg2)
    {
        return this.buf.getBytes(arg0, arg1, arg2);
    }

    public ByteBuf getBytes(int arg0, OutputStream arg1, int arg2) throws IOException
    {
        return this.buf.getBytes(arg0, arg1, arg2);
    }

    public int getBytes(int arg0, GatheringByteChannel arg1, int arg2) throws IOException
    {
        return this.buf.getBytes(arg0, arg1, arg2);
    }

    public ByteBuf getBytes(int arg0, ByteBuf arg1, int arg2, int arg3)
    {
        return this.buf.getBytes(arg0, arg1, arg2, arg3);
    }

    public ByteBuf getBytes(int arg0, byte[] arg1, int arg2, int arg3)
    {
        return this.buf.getBytes(arg0, arg1, arg2, arg3);
    }

    public char getChar(int arg0)
    {
        return this.buf.getChar(arg0);
    }

    public double getDouble(int arg0)
    {
        return this.buf.getDouble(arg0);
    }

    public float getFloat(int arg0)
    {
        return this.buf.getFloat(arg0);
    }

    public int getInt(int arg0)
    {
        return this.buf.getInt(arg0);
    }

    public long getLong(int arg0)
    {
        return this.buf.getLong(arg0);
    }

    public int getMedium(int arg0)
    {
        return this.buf.getMedium(arg0);
    }

    public short getShort(int arg0)
    {
        return this.buf.getShort(arg0);
    }

    public short getUnsignedByte(int arg0)
    {
        return this.buf.getUnsignedByte(arg0);
    }

    public long getUnsignedInt(int arg0)
    {
        return this.buf.getUnsignedInt(arg0);
    }

    public int getUnsignedMedium(int arg0)
    {
        return this.buf.getUnsignedMedium(arg0);
    }

    public int getUnsignedShort(int arg0)
    {
        return this.buf.getUnsignedShort(arg0);
    }

    public boolean hasArray()
    {
        return this.buf.hasArray();
    }

    public boolean hasMemoryAddress()
    {
        return this.buf.hasMemoryAddress();
    }

    public int hashCode()
    {
        return this.buf.hashCode();
    }

    public int indexOf(int arg0, int arg1, byte arg2)
    {
        return this.buf.indexOf(arg0, arg1, arg2);
    }

    public ByteBuffer internalNioBuffer(int arg0, int arg1)
    {
        return this.buf.internalNioBuffer(arg0, arg1);
    }

    public boolean isDirect()
    {
        return this.buf.isDirect();
    }

    public boolean isReadable()
    {
        return this.buf.isReadable();
    }

    public boolean isReadable(int arg0)
    {
        return this.buf.isReadable(arg0);
    }

    public boolean isWritable()
    {
        return this.buf.isWritable();
    }

    public boolean isWritable(int arg0)
    {
        return this.buf.isWritable(arg0);
    }

    public ByteBuf markReaderIndex()
    {
        return this.buf.markReaderIndex();
    }

    public ByteBuf markWriterIndex()
    {
        return this.buf.markWriterIndex();
    }

    public int maxCapacity()
    {
        return this.buf.maxCapacity();
    }

    public int maxWritableBytes()
    {
        return this.buf.maxWritableBytes();
    }

    public long memoryAddress()
    {
        return this.buf.memoryAddress();
    }

    public ByteBuffer nioBuffer()
    {
        return this.buf.nioBuffer();
    }

    public ByteBuffer nioBuffer(int arg0, int arg1)
    {
        return this.buf.nioBuffer(arg0, arg1);
    }

    public int nioBufferCount()
    {
        return this.buf.nioBufferCount();
    }

    public ByteBuffer[] nioBuffers()
    {
        return this.buf.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int arg0, int arg1)
    {
        return this.buf.nioBuffers(arg0, arg1);
    }

    public ByteOrder order()
    {
        return this.buf.order();
    }

    public ByteBuf order(ByteOrder arg0)
    {
        return this.buf.order(arg0);
    }

    public boolean readBoolean()
    {
        return this.buf.readBoolean();
    }

    public byte readByte()
    {
        return this.buf.readByte();
    }

    public ByteBuf readBytes(int arg0)
    {
        return this.buf.readBytes(arg0);
    }

    public ByteBuf readBytes(ByteBuf arg0)
    {
        return this.buf.readBytes(arg0);
    }

    public ByteBuf readBytes(byte[] arg0)
    {
        return this.buf.readBytes(arg0);
    }

    public ByteBuf readBytes(ByteBuffer arg0)
    {
        return this.buf.readBytes(arg0);
    }

    public ByteBuf readBytes(ByteBuf arg0, int arg1)
    {
        return this.buf.readBytes(arg0, arg1);
    }

    public ByteBuf readBytes(OutputStream arg0, int arg1) throws IOException
    {
        return this.buf.readBytes(arg0, arg1);
    }

    public int readBytes(GatheringByteChannel arg0, int arg1) throws IOException
    {
        return this.buf.readBytes(arg0, arg1);
    }

    public ByteBuf readBytes(ByteBuf arg0, int arg1, int arg2)
    {
        return this.buf.readBytes(arg0, arg1, arg2);
    }

    public ByteBuf readBytes(byte[] arg0, int arg1, int arg2)
    {
        return this.buf.readBytes(arg0, arg1, arg2);
    }

    public char readChar()
    {
        return this.buf.readChar();
    }

    public double readDouble()
    {
        return this.buf.readDouble();
    }

    public float readFloat()
    {
        return this.buf.readFloat();
    }

    public int readInt()
    {
        return this.buf.readInt();
    }

    public long readLong()
    {
        return this.buf.readLong();
    }

    public int readMedium()
    {
        return this.buf.readMedium();
    }

    public short readShort()
    {
        return this.buf.readShort();
    }

    public ByteBuf readSlice(int arg0)
    {
        return this.buf.readSlice(arg0);
    }

    public short readUnsignedByte()
    {
        return this.buf.readUnsignedByte();
    }

    public long readUnsignedInt()
    {
        return this.buf.readUnsignedInt();
    }

    public int readUnsignedMedium()
    {
        return this.buf.readUnsignedMedium();
    }

    public int readUnsignedShort()
    {
        return this.buf.readUnsignedShort();
    }

    public int readableBytes()
    {
        return this.buf.readableBytes();
    }

    public int readerIndex()
    {
        return this.buf.readerIndex();
    }

    public ByteBuf readerIndex(int arg0)
    {
        return this.buf.readerIndex(arg0);
    }

    public ByteBuf resetReaderIndex()
    {
        return this.buf.resetReaderIndex();
    }

    public ByteBuf resetWriterIndex()
    {
        return this.buf.resetWriterIndex();
    }

    public ByteBuf retain()
    {
        return this.buf.retain();
    }

    public ByteBuf retain(int arg0)
    {
        return this.buf.retain(arg0);
    }

    public ByteBuf setBoolean(int arg0, boolean arg1)
    {
        return this.buf.setBoolean(arg0, arg1);
    }

    public ByteBuf setByte(int arg0, int arg1)
    {
        return this.buf.setByte(arg0, arg1);
    }

    public ByteBuf setBytes(int arg0, ByteBuf arg1)
    {
        return this.buf.setBytes(arg0, arg1);
    }

    public ByteBuf setBytes(int arg0, byte[] arg1)
    {
        return this.buf.setBytes(arg0, arg1);
    }

    public ByteBuf setBytes(int arg0, ByteBuffer arg1)
    {
        return this.buf.setBytes(arg0, arg1);
    }

    public ByteBuf setBytes(int arg0, ByteBuf arg1, int arg2)
    {
        return this.buf.setBytes(arg0, arg1, arg2);
    }

    public int setBytes(int arg0, InputStream arg1, int arg2) throws IOException
    {
        return this.buf.setBytes(arg0, arg1, arg2);
    }

    public int setBytes(int arg0, ScatteringByteChannel arg1, int arg2) throws IOException
    {
        return this.buf.setBytes(arg0, arg1, arg2);
    }

    public ByteBuf setBytes(int arg0, ByteBuf arg1, int arg2, int arg3)
    {
        return this.buf.setBytes(arg0, arg1, arg2, arg3);
    }

    public ByteBuf setBytes(int arg0, byte[] arg1, int arg2, int arg3)
    {
        return this.buf.setBytes(arg0, arg1, arg2, arg3);
    }

    public ByteBuf setChar(int arg0, int arg1)
    {
        return this.buf.setChar(arg0, arg1);
    }

    public ByteBuf setDouble(int arg0, double arg1)
    {
        return this.buf.setDouble(arg0, arg1);
    }

    public ByteBuf setFloat(int arg0, float arg1)
    {
        return this.buf.setFloat(arg0, arg1);
    }

    public ByteBuf setIndex(int arg0, int arg1)
    {
        return this.buf.setIndex(arg0, arg1);
    }

    public ByteBuf setInt(int arg0, int arg1)
    {
        return this.buf.setInt(arg0, arg1);
    }

    public ByteBuf setLong(int arg0, long arg1)
    {
        return this.buf.setLong(arg0, arg1);
    }

    public ByteBuf setMedium(int arg0, int arg1)
    {
        return this.buf.setMedium(arg0, arg1);
    }

    public ByteBuf setShort(int arg0, int arg1)
    {
        return this.buf.setShort(arg0, arg1);
    }

    public ByteBuf setZero(int arg0, int arg1)
    {
        return this.buf.setZero(arg0, arg1);
    }

    public ByteBuf skipBytes(int arg0)
    {
        return this.buf.skipBytes(arg0);
    }

    public ByteBuf slice()
    {
        return this.buf.slice();
    }

    public ByteBuf slice(int arg0, int arg1)
    {
        return this.buf.slice(arg0, arg1);
    }

    public String toString()
    {
        return this.buf.toString();
    }

    public String toString(Charset arg0)
    {
        return this.buf.toString(arg0);
    }

    public String toString(int arg0, int arg1, Charset arg2)
    {
        return this.buf.toString(arg0, arg1, arg2);
    }

    public ByteBuf unwrap()
    {
        return this.buf.unwrap();
    }

    public int writableBytes()
    {
        return this.buf.writableBytes();
    }

    public ByteBuf writeBoolean(boolean arg0)
    {
        return this.buf.writeBoolean(arg0);
    }

    public ByteBuf writeByte(int arg0)
    {
        return this.buf.writeByte(arg0);
    }

    public ByteBuf writeBytes(ByteBuf arg0)
    {
        return this.buf.writeBytes(arg0);
    }

    public ByteBuf writeBytes(byte[] arg0)
    {
        return this.buf.writeBytes(arg0);
    }

    public ByteBuf writeBytes(ByteBuffer arg0)
    {
        return this.buf.writeBytes(arg0);
    }

    public ByteBuf writeBytes(ByteBuf arg0, int arg1)
    {
        return this.buf.writeBytes(arg0, arg1);
    }

    public int writeBytes(InputStream arg0, int arg1) throws IOException
    {
        return this.buf.writeBytes(arg0, arg1);
    }

    public int writeBytes(ScatteringByteChannel arg0, int arg1) throws IOException
    {
        return this.buf.writeBytes(arg0, arg1);
    }

    public ByteBuf writeBytes(ByteBuf arg0, int arg1, int arg2)
    {
        return this.buf.writeBytes(arg0, arg1, arg2);
    }

    public ByteBuf writeBytes(byte[] arg0, int arg1, int arg2)
    {
        return this.buf.writeBytes(arg0, arg1, arg2);
    }

    public ByteBuf writeChar(int arg0)
    {
        return this.buf.writeChar(arg0);
    }

    public ByteBuf writeDouble(double arg0)
    {
        return this.buf.writeDouble(arg0);
    }

    public ByteBuf writeFloat(float arg0)
    {
        return this.buf.writeFloat(arg0);
    }

    public ByteBuf writeInt(int arg0)
    {
        return this.buf.writeInt(arg0);
    }

    public ByteBuf writeLong(long arg0)
    {
        return this.buf.writeLong(arg0);
    }

    public ByteBuf writeMedium(int arg0)
    {
        return this.buf.writeMedium(arg0);
    }

    public ByteBuf writeShort(int arg0)
    {
        return this.buf.writeShort(arg0);
    }

    public ByteBuf writeZero(int arg0)
    {
        return this.buf.writeZero(arg0);
    }

    public int writerIndex()
    {
        return this.buf.writerIndex();
    }

    public ByteBuf writerIndex(int arg0)
    {
        return this.buf.writerIndex(arg0);
    }

    public static int getVarIntSize(int input)
    {
        for (int i = 1; i < 5; ++i)
        {
            if ((input & -1 << i * 7) == 0)
            {
                return i;
            }
        }

        return 5;
    }

    public int readVarIntFromBuffer()
    {
        int i = 0;
        int j = 0;

        while (true)
        {
            byte b0 = this.readByte();
            i |= (b0 & 127) << j++ * 7;

            if (j > 5)
            {
                throw new RuntimeException("VarInt too big");
            }

            if ((b0 & 128) != 128)
            {
                break;
            }
        }

        return i;
    }

    public void writeVarIntToBuffer(int input)
    {
        while ((input & -128) != 0)
        {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        this.writeByte(input);
    }
}
