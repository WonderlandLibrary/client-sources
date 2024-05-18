package net.minecraft.network;

import net.minecraft.item.*;
import java.nio.charset.*;
import java.nio.*;
import java.util.*;
import io.netty.util.*;
import java.nio.channels.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import io.netty.handler.codec.*;

public class PacketBuffer extends ByteBuf
{
    private final ByteBuf buf;
    private static final String[] I;
    
    public ByteBuf setIndex(final int n, final int n2) {
        return this.buf.setIndex(n, n2);
    }
    
    public int readInt() {
        return this.buf.readInt();
    }
    
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.buf.setBytes(n, byteBuf, n2, n3);
    }
    
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.buf.getBytes(n, byteBuf, n2);
    }
    
    public byte[] array() {
        return this.buf.array();
    }
    
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(n, n2, byteBufProcessor);
    }
    
    public boolean equals(final Object o) {
        return this.buf.equals(o);
    }
    
    public void writeByteArray(final byte[] array) {
        this.writeVarIntToBuffer(array.length);
        this.writeBytes(array);
    }
    
    public int readerIndex() {
        return this.buf.readerIndex();
    }
    
    public int hashCode() {
        return this.buf.hashCode();
    }
    
    public int ensureWritable(final int n, final boolean b) {
        return this.buf.ensureWritable(n, b);
    }
    
    public ByteBuf setBoolean(final int n, final boolean b) {
        return this.buf.setBoolean(n, b);
    }
    
    public long memoryAddress() {
        return this.buf.memoryAddress();
    }
    
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        return this.buf.setBytes(n, inputStream, n2);
    }
    
    public int compareTo(final ByteBuf byteBuf) {
        return this.buf.compareTo(byteBuf);
    }
    
    public boolean isDirect() {
        return this.buf.isDirect();
    }
    
    public ByteBuf setZero(final int n, final int n2) {
        return this.buf.setZero(n, n2);
    }
    
    public void writeItemStackToBuffer(final ItemStack itemStack) {
        if (itemStack == null) {
            this.writeShort(-" ".length());
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            this.writeShort(Item.getIdFromItem(itemStack.getItem()));
            this.writeByte(itemStack.stackSize);
            this.writeShort(itemStack.getMetadata());
            NBTTagCompound tagCompound = null;
            if (itemStack.getItem().isDamageable() || itemStack.getItem().getShareTag()) {
                tagCompound = itemStack.getTagCompound();
            }
            this.writeNBTTagCompoundToBuffer(tagCompound);
        }
    }
    
    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }
    
    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }
    
    public ByteBuf order(final ByteOrder byteOrder) {
        return this.buf.order(byteOrder);
    }
    
    public String toString(final int n, final int n2, final Charset charset) {
        return this.buf.toString(n, n2, charset);
    }
    
    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }
    
    public ByteBuf setLong(final int n, final long n2) {
        return this.buf.setLong(n, n2);
    }
    
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return this.buf.readBytes(byteBuf, n);
    }
    
    public double getDouble(final int n) {
        return this.buf.getDouble(n);
    }
    
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.buf.getBytes(n, byteBuf, n2, n3);
    }
    
    public ByteBuf capacity(final int n) {
        return this.buf.capacity(n);
    }
    
    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }
    
    public ByteBuf setByte(final int n, final int n2) {
        return this.buf.setByte(n, n2);
    }
    
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return this.buf.writeBytes(array, n, n2);
    }
    
    public char readChar() {
        return this.buf.readChar();
    }
    
    public void writeNBTTagCompoundToBuffer(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            this.writeByte("".length());
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            try {
                CompressedStreamTools.write(nbtTagCompound, (DataOutput)new ByteBufOutputStream((ByteBuf)this));
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            catch (IOException ex) {
                throw new EncoderException((Throwable)ex);
            }
        }
    }
    
    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }
    
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.buf.setBytes(n, byteBuf, n2);
    }
    
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }
    
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(byteBufProcessor);
    }
    
    public int getInt(final int n) {
        return this.buf.getInt(n);
    }
    
    public String toString(final Charset charset) {
        return this.buf.toString(charset);
    }
    
    public ByteBuf clear() {
        return this.buf.clear();
    }
    
    public void writeEnumValue(final Enum<?> enum1) {
        this.writeVarIntToBuffer(enum1.ordinal());
    }
    
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return this.buf.writeBytes(byteBuffer);
    }
    
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, n);
    }
    
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        return this.buf.readBytes(outputStream, n);
    }
    
    public int writableBytes() {
        return this.buf.writableBytes();
    }
    
    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }
    
    public int refCnt() {
        return this.buf.refCnt();
    }
    
    public PacketBuffer(final ByteBuf buf) {
        this.buf = buf;
    }
    
    public float readFloat() {
        return this.buf.readFloat();
    }
    
    public long readVarLong() {
        long n = 0L;
        int length = "".length();
        byte byte1;
        do {
            byte1 = this.readByte();
            n |= (byte1 & 75 + 63 - 46 + 35) << length++ * (0x8E ^ 0x89);
            if (length > (0x8 ^ 0x2)) {
                throw new RuntimeException(PacketBuffer.I[" ".length()]);
            }
        } while ((byte1 & 92 + 76 - 135 + 95) == 13 + 99 - 93 + 109);
        return n;
    }
    
    public ByteBuf readerIndex(final int n) {
        return this.buf.readerIndex(n);
    }
    
    static {
        I();
    }
    
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        return this.buf.getBytes(n, byteBuffer);
    }
    
    public int readableBytes() {
        return this.buf.readableBytes();
    }
    
    public float getFloat(final int n) {
        return this.buf.getFloat(n);
    }
    
    public int bytesBefore(final int n, final byte b) {
        return this.buf.bytesBefore(n, b);
    }
    
    public boolean hasArray() {
        return this.buf.hasArray();
    }
    
    public ByteBuf readBytes(final int n) {
        return this.buf.readBytes(n);
    }
    
    public ByteBuf getBytes(final int n, final byte[] array) {
        return this.buf.getBytes(n, array);
    }
    
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }
    
    public ByteBuf slice(final int n, final int n2) {
        return this.buf.slice(n, n2);
    }
    
    public ByteBuf setMedium(final int n, final int n2) {
        return this.buf.setMedium(n, n2);
    }
    
    public <T extends Enum<T>> T readEnumValue(final Class<T> clazz) {
        return clazz.getEnumConstants()[this.readVarIntFromBuffer()];
    }
    
    public void writeUuid(final UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }
    
    public ByteBuf readBytes(final byte[] array) {
        return this.buf.readBytes(array);
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ByteBuf copy() {
        return this.buf.copy();
    }
    
    public ByteBuf ensureWritable(final int n) {
        return this.buf.ensureWritable(n);
    }
    
    public ByteBuf writeInt(final int n) {
        return this.buf.writeInt(n);
    }
    
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }
    
    public int bytesBefore(final int n, final int n2, final byte b) {
        return this.buf.bytesBefore(n, n2, b);
    }
    
    public boolean isWritable(final int n) {
        return this.buf.isWritable(n);
    }
    
    public boolean release() {
        return this.buf.release();
    }
    
    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }
    
    public short getUnsignedByte(final int n) {
        return this.buf.getUnsignedByte(n);
    }
    
    public ByteBuf writeLong(final long n) {
        return this.buf.writeLong(n);
    }
    
    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }
    
    public byte[] readByteArray() {
        final byte[] array = new byte[this.readVarIntFromBuffer()];
        this.readBytes(array);
        return array;
    }
    
    public boolean getBoolean(final int n) {
        return this.buf.getBoolean(n);
    }
    
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.buf.writeBytes(byteBuf, n, n2);
    }
    
    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }
    
    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }
    
    public int bytesBefore(final byte b) {
        return this.buf.bytesBefore(b);
    }
    
    public ByteBuf writeMedium(final int n) {
        return this.buf.writeMedium(n);
    }
    
    public ByteBuf writeByte(final int n) {
        return this.buf.writeByte(n);
    }
    
    public ByteBuf retain(final int n) {
        return this.buf.retain(n);
    }
    
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        return this.buf.setBytes(n, byteBuffer);
    }
    
    public ByteBuf setBytes(final int n, final byte[] array) {
        return this.buf.setBytes(n, array);
    }
    
    public String toString() {
        return this.buf.toString();
    }
    
    public ByteOrder order() {
        return this.buf.order();
    }
    
    public boolean isWritable() {
        return this.buf.isWritable();
    }
    
    public int getMedium(final int n) {
        return this.buf.getMedium(n);
    }
    
    public IChatComponent readChatComponent() throws IOException {
        return IChatComponent.Serializer.jsonToComponent(this.readStringFromBuffer(4654 + 26150 - 16792 + 18755));
    }
    
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return this.buf.writeBytes(byteBuf, n);
    }
    
    public UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }
    
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }
    
    public int indexOf(final int n, final int n2, final byte b) {
        return this.buf.indexOf(n, n2, b);
    }
    
    public ReferenceCounted retain(final int n) {
        return (ReferenceCounted)this.retain(n);
    }
    
    public ByteBuf retain() {
        return this.buf.retain();
    }
    
    public ByteBuf copy(final int n, final int n2) {
        return this.buf.copy(n, n2);
    }
    
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        return this.buf.readBytes(array, n, n2);
    }
    
    public void writeVarLong(long n) {
        "".length();
        if (false) {
            throw null;
        }
        while ((n & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
            this.writeByte((int)(n & 0x7FL) | 37 + 36 + 9 + 46);
            n >>>= (0x4B ^ 0x4C);
        }
        this.writeByte((int)n);
    }
    
    public void writeChatComponent(final IChatComponent chatComponent) throws IOException {
        this.writeString(IChatComponent.Serializer.componentToJson(chatComponent));
    }
    
    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }
    
    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }
    
    public byte getByte(final int n) {
        return this.buf.getByte(n);
    }
    
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.buf.setBytes(n, array, n2, n3);
    }
    
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, n);
    }
    
    public int writerIndex() {
        return this.buf.writerIndex();
    }
    
    public ByteBuf writeBoolean(final boolean b) {
        return this.buf.writeBoolean(b);
    }
    
    public ByteBuf setChar(final int n, final int n2) {
        return this.buf.setChar(n, n2);
    }
    
    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        final int readerIndex = this.readerIndex();
        if (this.readByte() == 0) {
            return null;
        }
        this.readerIndex(readerIndex);
        return CompressedStreamTools.read((DataInput)new ByteBufInputStream((ByteBuf)this), new NBTSizeTracker(2097152L));
    }
    
    public ByteBuf slice() {
        return this.buf.slice();
    }
    
    private static void I() {
        (I = new String[0x20 ^ 0x2C])["".length()] = I("0\u0015\u001e>\u001c\u0012T\u0018\u0018\u001dF\u0016\u0005\u0010", "ftlwr");
        PacketBuffer.I[" ".length()] = I("\u001d\u0007\u0007>\u001c%\u0001U\u0006\u001c$F\u0017\u001b\u0014", "Kfurs");
        PacketBuffer.I["  ".length()] = I("\u0011\u0000\u0010O\u0010 \u000b\u0010\u0006\u0014 \fU\n\f&\u0007\u0011\n\u0006e\u001b\u0001\u001d\u000b+\u000fU\r\u0017#\u000e\u0010\u001dB)\r\u001b\b\u0016-H\u001c\u001cB)\u0007\u001b\b\u00077H\u0001\u0007\u0003+H\u0018\u000e\u001a,\u0005\u0000\u0002B$\u0004\u0019\u0000\u0015 \fUG", "Ehuob");
        PacketBuffer.I["   ".length()] = I("tOa", "TqAZc");
        PacketBuffer.I[0x21 ^ 0x25] = I("Y", "pyaQu");
        PacketBuffer.I[0xBE ^ 0xBB] = I("\u001705c\u0019&;5*\u001d&<p&\u0005 74&\u000fc+$1\u0002-?p!\u001e%>51K/=>$\u001f+x90K/=#0K701-K9=\",Jc\u000f5*\u0019'x#7\u0019*67b", "CXPCk");
        PacketBuffer.I[0x8E ^ 0x88] = I("\u0007#3u&6(3<\"6/v& !\"82t?.82 ;k?&t?$821!k\"=5=k;4,:&#8t2'::#6/v}", "SKVUT");
        PacketBuffer.I[0x8F ^ 0x88] = I("gzU", "GDuHh");
        PacketBuffer.I[0x18 ^ 0x10] = I("`", "IGzZI");
        PacketBuffer.I[0x67 ^ 0x6E] = I("<;\n\u0003\u0017\bo\f\u0005\u0016O-\u0011\rYG8\u0019\u0019Y", "oOxjy");
        PacketBuffer.I[0x33 ^ 0x39] = I("m2\u0011\u001d'>p\r\u0007!\"4\r\rnm=\t\u0011b", "MPhiB");
        PacketBuffer.I[0x6A ^ 0x61] = I("F", "oqrqv");
    }
    
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        return this.buf.getBytes(n, outputStream, n2);
    }
    
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        return this.buf.setBytes(n, scatteringByteChannel, n2);
    }
    
    public short getShort(final int n) {
        return this.buf.getShort(n);
    }
    
    public int getUnsignedMedium(final int n) {
        return this.buf.getUnsignedMedium(n);
    }
    
    public double readDouble() {
        return this.buf.readDouble();
    }
    
    public ByteBuf writeFloat(final float n) {
        return this.buf.writeFloat(n);
    }
    
    public ByteBuf writerIndex(final int n) {
        return this.buf.writerIndex(n);
    }
    
    public ByteBuf readSlice(final int n) {
        return this.buf.readSlice(n);
    }
    
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.buf.getBytes(n, gatheringByteChannel, n2);
    }
    
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(n, n2, byteBufProcessor);
    }
    
    public boolean release(final int n) {
        return this.buf.release(n);
    }
    
    public ByteBuf writeZero(final int n) {
        return this.buf.writeZero(n);
    }
    
    public void writeBlockPos(final BlockPos blockPos) {
        this.writeLong(blockPos.toLong());
    }
    
    public int readMedium() {
        return this.buf.readMedium();
    }
    
    public long getLong(final int n) {
        return this.buf.getLong(n);
    }
    
    public short readShort() {
        return this.buf.readShort();
    }
    
    public int capacity() {
        return this.buf.capacity();
    }
    
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }
    
    public int getUnsignedShort(final int n) {
        return this.buf.getUnsignedShort(n);
    }
    
    public ByteBuf writeChar(final int n) {
        return this.buf.writeChar(n);
    }
    
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        return this.buf.readBytes(byteBuf);
    }
    
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return this.buf.internalNioBuffer(n, n2);
    }
    
    public ByteBuf setFloat(final int n, final float n2) {
        return this.buf.setFloat(n, n2);
    }
    
    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }
    
    public PacketBuffer writeString(final String s) {
        final byte[] bytes = s.getBytes(Charsets.UTF_8);
        if (bytes.length > 29444 + 20382 - 37910 + 20851) {
            throw new EncoderException(PacketBuffer.I[0x9D ^ 0x94] + s.length() + PacketBuffer.I[0xCC ^ 0xC6] + (25382 + 5169 - 27857 + 30073) + PacketBuffer.I[0x2F ^ 0x24]);
        }
        this.writeVarIntToBuffer(bytes.length);
        this.writeBytes(bytes);
        return this;
    }
    
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        return this.buf.readBytes(byteBuffer);
    }
    
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return this.buf.nioBuffers(n, n2);
    }
    
    public ReferenceCounted retain() {
        return (ReferenceCounted)this.retain();
    }
    
    public ByteBuf writeBytes(final byte[] array) {
        return this.buf.writeBytes(array);
    }
    
    public boolean isReadable() {
        return this.buf.isReadable();
    }
    
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        return this.buf.writeBytes(byteBuf);
    }
    
    public boolean isReadable(final int n) {
        return this.buf.isReadable(n);
    }
    
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.buf.readBytes(byteBuf, n, n2);
    }
    
    public BlockPos readBlockPos() {
        return BlockPos.fromLong(this.readLong());
    }
    
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.buf.getBytes(n, array, n2, n3);
    }
    
    public char getChar(final int n) {
        return this.buf.getChar(n);
    }
    
    public ByteBuf setDouble(final int n, final double n2) {
        return this.buf.setDouble(n, n2);
    }
    
    public long readLong() {
        return this.buf.readLong();
    }
    
    public String readStringFromBuffer(final int n) {
        final int varIntFromBuffer = this.readVarIntFromBuffer();
        if (varIntFromBuffer > n * (0x9C ^ 0x98)) {
            throw new DecoderException(PacketBuffer.I["  ".length()] + varIntFromBuffer + PacketBuffer.I["   ".length()] + n * (0xA8 ^ 0xAC) + PacketBuffer.I[0x87 ^ 0x83]);
        }
        if (varIntFromBuffer < 0) {
            throw new DecoderException(PacketBuffer.I[0xF ^ 0xA]);
        }
        final String s = new String(this.readBytes(varIntFromBuffer).array(), Charsets.UTF_8);
        if (s.length() > n) {
            throw new DecoderException(PacketBuffer.I[0x25 ^ 0x23] + varIntFromBuffer + PacketBuffer.I[0x34 ^ 0x33] + n + PacketBuffer.I[0x3E ^ 0x36]);
        }
        return s;
    }
    
    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }
    
    public void writeVarIntToBuffer(int n) {
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while ((n & -(47 + 61 - 54 + 74)) != 0x0) {
            this.writeByte((n & 14 + 54 + 21 + 38) | 73 + 71 - 29 + 13);
            n >>>= (0x3A ^ 0x3D);
        }
        this.writeByte(n);
    }
    
    public ByteBuf setInt(final int n, final int n2) {
        return this.buf.setInt(n, n2);
    }
    
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return this.buf.getBytes(n, byteBuf);
    }
    
    public ByteBuf setShort(final int n, final int n2) {
        return this.buf.setShort(n, n2);
    }
    
    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack itemStack = null;
        final short short1 = this.readShort();
        if (short1 >= 0) {
            itemStack = new ItemStack(Item.getItemById(short1), this.readByte(), this.readShort());
            itemStack.setTagCompound(this.readNBTTagCompoundFromBuffer());
        }
        return itemStack;
    }
    
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        return this.buf.setBytes(n, byteBuf);
    }
    
    public int readVarIntFromBuffer() {
        int length = "".length();
        int length2 = "".length();
        byte byte1;
        do {
            byte1 = this.readByte();
            length |= (byte1 & 27 + 14 + 55 + 31) << length2++ * (0x81 ^ 0x86);
            if (length2 > (0x37 ^ 0x32)) {
                throw new RuntimeException(PacketBuffer.I["".length()]);
            }
        } while ((byte1 & 119 + 3 - 118 + 124) == 32 + 90 - 63 + 69);
        return length;
    }
    
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }
    
    public long getUnsignedInt(final int n) {
        return this.buf.getUnsignedInt(n);
    }
    
    public ByteBuf writeShort(final int n) {
        return this.buf.writeShort(n);
    }
    
    public byte readByte() {
        return this.buf.readByte();
    }
    
    public static int getVarIntSize(final int n) {
        int i = " ".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < (0xC2 ^ 0xC7)) {
            if ((n & -" ".length() << i * (0x9 ^ 0xE)) == 0x0) {
                return i;
            }
            ++i;
        }
        return 0x6F ^ 0x6A;
    }
    
    public ByteBuf skipBytes(final int n) {
        return this.buf.skipBytes(n);
    }
    
    public ByteBuffer nioBuffer(final int n, final int n2) {
        return this.buf.nioBuffer(n, n2);
    }
    
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(byteBufProcessor);
    }
    
    public int writeBytes(final InputStream inputStream, final int n) throws IOException {
        return this.buf.writeBytes(inputStream, n);
    }
    
    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }
    
    public ByteBuf writeDouble(final double n) {
        return this.buf.writeDouble(n);
    }
}
