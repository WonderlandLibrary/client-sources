package HORIZON-6-0-SKIDPROTECTION;

import io.netty.util.ReferenceCounted;
import java.nio.charset.Charset;
import io.netty.buffer.ByteBufProcessor;
import java.nio.channels.ScatteringByteChannel;
import java.io.InputStream;
import java.nio.channels.GatheringByteChannel;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import io.netty.buffer.ByteBufAllocator;
import com.google.common.base.Charsets;
import io.netty.handler.codec.DecoderException;
import java.io.DataInput;
import io.netty.buffer.ByteBufInputStream;
import java.io.IOException;
import io.netty.handler.codec.EncoderException;
import java.io.DataOutput;
import io.netty.buffer.ByteBufOutputStream;
import java.util.UUID;
import io.netty.buffer.ByteBuf;

public class PacketBuffer extends ByteBuf
{
    private final ByteBuf HorizonCode_Horizon_È;
    private static final String Â = "CL_00001251";
    
    public PacketBuffer(final ByteBuf wrapped) {
        this.HorizonCode_Horizon_È = wrapped;
    }
    
    public static int HorizonCode_Horizon_È(final int input) {
        for (int var1 = 1; var1 < 5; ++var1) {
            if ((input & -1 << var1 * 7) == 0x0) {
                return var1;
            }
        }
        return 5;
    }
    
    public void HorizonCode_Horizon_È(final byte[] array) {
        this.Â(array.length);
        this.writeBytes(array);
    }
    
    public byte[] HorizonCode_Horizon_È() {
        final byte[] var1 = new byte[this.Ø­áŒŠá()];
        this.readBytes(var1);
        return var1;
    }
    
    public BlockPos Â() {
        return BlockPos.HorizonCode_Horizon_È(this.readLong());
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos) {
        this.writeLong(pos.áˆºÑ¢Õ());
    }
    
    public IChatComponent Ý() {
        return IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý(32767));
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent component) {
        this.HorizonCode_Horizon_È(IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(component));
    }
    
    public Enum HorizonCode_Horizon_È(final Class enumClass) {
        return ((Enum[])enumClass.getEnumConstants())[this.Ø­áŒŠá()];
    }
    
    public void HorizonCode_Horizon_È(final Enum value) {
        this.Â(value.ordinal());
    }
    
    public int Ø­áŒŠá() {
        int var1 = 0;
        int var2 = 0;
        byte var3;
        do {
            var3 = this.readByte();
            var1 |= (var3 & 0x7F) << var2++ * 7;
            if (var2 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((var3 & 0x80) == 0x80);
        return var1;
    }
    
    public long Âµá€() {
        long var1 = 0L;
        int var2 = 0;
        byte var3;
        do {
            var3 = this.readByte();
            var1 |= (var3 & 0x7F) << var2++ * 7;
            if (var2 > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((var3 & 0x80) == 0x80);
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }
    
    public UUID Ó() {
        return new UUID(this.readLong(), this.readLong());
    }
    
    public void Â(int input) {
        while ((input & 0xFFFFFF80) != 0x0) {
            this.writeByte((input & 0x7F) | 0x80);
            input >>>= 7;
        }
        this.writeByte(input);
    }
    
    public void HorizonCode_Horizon_È(long value) {
        while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
            this.writeByte((int)(value & 0x7FL) | 0x80);
            value >>>= 7;
        }
        this.writeByte((int)value);
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        if (nbt == null) {
            this.writeByte(0);
        }
        else {
            try {
                CompressedStreamTools.HorizonCode_Horizon_È(nbt, (DataOutput)new ByteBufOutputStream((ByteBuf)this));
            }
            catch (IOException var3) {
                throw new EncoderException((Throwable)var3);
            }
        }
    }
    
    public NBTTagCompound à() throws IOException {
        final int var1 = this.readerIndex();
        final byte var2 = this.readByte();
        if (var2 == 0) {
            return null;
        }
        this.readerIndex(var1);
        return CompressedStreamTools.HorizonCode_Horizon_È((DataInput)new ByteBufInputStream((ByteBuf)this), new NBTSizeTracker(2097152L));
    }
    
    public void HorizonCode_Horizon_È(final ItemStack stack) {
        if (stack == null) {
            this.writeShort(-1);
        }
        else {
            this.writeShort(Item_1028566121.HorizonCode_Horizon_È(stack.HorizonCode_Horizon_È()));
            this.writeByte(stack.Â);
            this.writeShort(stack.Ø());
            NBTTagCompound var2 = null;
            if (stack.HorizonCode_Horizon_È().Ø­áŒŠá() || stack.HorizonCode_Horizon_È().áŒŠÆ()) {
                var2 = stack.Å();
            }
            this.HorizonCode_Horizon_È(var2);
        }
    }
    
    public ItemStack Ø() throws IOException {
        ItemStack var1 = null;
        final short var2 = this.readShort();
        if (var2 >= 0) {
            final byte var3 = this.readByte();
            final short var4 = this.readShort();
            var1 = new ItemStack(Item_1028566121.HorizonCode_Horizon_È(var2), var3, var4);
            var1.Ø­áŒŠá(this.à());
        }
        return var1;
    }
    
    public String Ý(final int maxLength) {
        final int var2 = this.Ø­áŒŠá();
        if (var2 > maxLength * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + maxLength * 4 + ")");
        }
        if (var2 < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        final String var3 = new String(this.readBytes(var2).array(), Charsets.UTF_8);
        if (var3.length() > maxLength) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + var2 + " > " + maxLength + ")");
        }
        return var3;
    }
    
    public PacketBuffer HorizonCode_Horizon_È(final String string) {
        final byte[] var2 = string.getBytes(Charsets.UTF_8);
        if (var2.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        }
        this.Â(var2.length);
        this.writeBytes(var2);
        return this;
    }
    
    public int capacity() {
        return this.HorizonCode_Horizon_È.capacity();
    }
    
    public ByteBuf capacity(final int p_capacity_1_) {
        return this.HorizonCode_Horizon_È.capacity(p_capacity_1_);
    }
    
    public int maxCapacity() {
        return this.HorizonCode_Horizon_È.maxCapacity();
    }
    
    public ByteBufAllocator alloc() {
        return this.HorizonCode_Horizon_È.alloc();
    }
    
    public ByteOrder order() {
        return this.HorizonCode_Horizon_È.order();
    }
    
    public ByteBuf order(final ByteOrder p_order_1_) {
        return this.HorizonCode_Horizon_È.order(p_order_1_);
    }
    
    public ByteBuf unwrap() {
        return this.HorizonCode_Horizon_È.unwrap();
    }
    
    public boolean isDirect() {
        return this.HorizonCode_Horizon_È.isDirect();
    }
    
    public int readerIndex() {
        return this.HorizonCode_Horizon_È.readerIndex();
    }
    
    public ByteBuf readerIndex(final int p_readerIndex_1_) {
        return this.HorizonCode_Horizon_È.readerIndex(p_readerIndex_1_);
    }
    
    public int writerIndex() {
        return this.HorizonCode_Horizon_È.writerIndex();
    }
    
    public ByteBuf writerIndex(final int p_writerIndex_1_) {
        return this.HorizonCode_Horizon_È.writerIndex(p_writerIndex_1_);
    }
    
    public ByteBuf setIndex(final int p_setIndex_1_, final int p_setIndex_2_) {
        return this.HorizonCode_Horizon_È.setIndex(p_setIndex_1_, p_setIndex_2_);
    }
    
    public int readableBytes() {
        return this.HorizonCode_Horizon_È.readableBytes();
    }
    
    public int writableBytes() {
        return this.HorizonCode_Horizon_È.writableBytes();
    }
    
    public int maxWritableBytes() {
        return this.HorizonCode_Horizon_È.maxWritableBytes();
    }
    
    public boolean isReadable() {
        return this.HorizonCode_Horizon_È.isReadable();
    }
    
    public boolean isReadable(final int p_isReadable_1_) {
        return this.HorizonCode_Horizon_È.isReadable(p_isReadable_1_);
    }
    
    public boolean isWritable() {
        return this.HorizonCode_Horizon_È.isWritable();
    }
    
    public boolean isWritable(final int p_isWritable_1_) {
        return this.HorizonCode_Horizon_È.isWritable(p_isWritable_1_);
    }
    
    public ByteBuf clear() {
        return this.HorizonCode_Horizon_È.clear();
    }
    
    public ByteBuf markReaderIndex() {
        return this.HorizonCode_Horizon_È.markReaderIndex();
    }
    
    public ByteBuf resetReaderIndex() {
        return this.HorizonCode_Horizon_È.resetReaderIndex();
    }
    
    public ByteBuf markWriterIndex() {
        return this.HorizonCode_Horizon_È.markWriterIndex();
    }
    
    public ByteBuf resetWriterIndex() {
        return this.HorizonCode_Horizon_È.resetWriterIndex();
    }
    
    public ByteBuf discardReadBytes() {
        return this.HorizonCode_Horizon_È.discardReadBytes();
    }
    
    public ByteBuf discardSomeReadBytes() {
        return this.HorizonCode_Horizon_È.discardSomeReadBytes();
    }
    
    public ByteBuf ensureWritable(final int p_ensureWritable_1_) {
        return this.HorizonCode_Horizon_È.ensureWritable(p_ensureWritable_1_);
    }
    
    public int ensureWritable(final int p_ensureWritable_1_, final boolean p_ensureWritable_2_) {
        return this.HorizonCode_Horizon_È.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
    }
    
    public boolean getBoolean(final int p_getBoolean_1_) {
        return this.HorizonCode_Horizon_È.getBoolean(p_getBoolean_1_);
    }
    
    public byte getByte(final int p_getByte_1_) {
        return this.HorizonCode_Horizon_È.getByte(p_getByte_1_);
    }
    
    public short getUnsignedByte(final int p_getUnsignedByte_1_) {
        return this.HorizonCode_Horizon_È.getUnsignedByte(p_getUnsignedByte_1_);
    }
    
    public short getShort(final int p_getShort_1_) {
        return this.HorizonCode_Horizon_È.getShort(p_getShort_1_);
    }
    
    public int getUnsignedShort(final int p_getUnsignedShort_1_) {
        return this.HorizonCode_Horizon_È.getUnsignedShort(p_getUnsignedShort_1_);
    }
    
    public int getMedium(final int p_getMedium_1_) {
        return this.HorizonCode_Horizon_È.getMedium(p_getMedium_1_);
    }
    
    public int getUnsignedMedium(final int p_getUnsignedMedium_1_) {
        return this.HorizonCode_Horizon_È.getUnsignedMedium(p_getUnsignedMedium_1_);
    }
    
    public int getInt(final int p_getInt_1_) {
        return this.HorizonCode_Horizon_È.getInt(p_getInt_1_);
    }
    
    public long getUnsignedInt(final int p_getUnsignedInt_1_) {
        return this.HorizonCode_Horizon_È.getUnsignedInt(p_getUnsignedInt_1_);
    }
    
    public long getLong(final int p_getLong_1_) {
        return this.HorizonCode_Horizon_È.getLong(p_getLong_1_);
    }
    
    public char getChar(final int p_getChar_1_) {
        return this.HorizonCode_Horizon_È.getChar(p_getChar_1_);
    }
    
    public float getFloat(final int p_getFloat_1_) {
        return this.HorizonCode_Horizon_È.getFloat(p_getFloat_1_);
    }
    
    public double getDouble(final int p_getDouble_1_) {
        return this.HorizonCode_Horizon_È.getDouble(p_getDouble_1_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuf p_getBytes_2_) {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuf p_getBytes_2_, final int p_getBytes_3_) {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuf p_getBytes_2_, final int p_getBytes_3_, final int p_getBytes_4_) {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final byte[] p_getBytes_2_) {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final byte[] p_getBytes_2_, final int p_getBytes_3_, final int p_getBytes_4_) {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuffer p_getBytes_2_) {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final OutputStream p_getBytes_2_, final int p_getBytes_3_) throws IOException {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }
    
    public int getBytes(final int p_getBytes_1_, final GatheringByteChannel p_getBytes_2_, final int p_getBytes_3_) throws IOException {
        return this.HorizonCode_Horizon_È.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }
    
    public ByteBuf setBoolean(final int p_setBoolean_1_, final boolean p_setBoolean_2_) {
        return this.HorizonCode_Horizon_È.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
    }
    
    public ByteBuf setByte(final int p_setByte_1_, final int p_setByte_2_) {
        return this.HorizonCode_Horizon_È.setByte(p_setByte_1_, p_setByte_2_);
    }
    
    public ByteBuf setShort(final int p_setShort_1_, final int p_setShort_2_) {
        return this.HorizonCode_Horizon_È.setShort(p_setShort_1_, p_setShort_2_);
    }
    
    public ByteBuf setMedium(final int p_setMedium_1_, final int p_setMedium_2_) {
        return this.HorizonCode_Horizon_È.setMedium(p_setMedium_1_, p_setMedium_2_);
    }
    
    public ByteBuf setInt(final int p_setInt_1_, final int p_setInt_2_) {
        return this.HorizonCode_Horizon_È.setInt(p_setInt_1_, p_setInt_2_);
    }
    
    public ByteBuf setLong(final int p_setLong_1_, final long p_setLong_2_) {
        return this.HorizonCode_Horizon_È.setLong(p_setLong_1_, p_setLong_2_);
    }
    
    public ByteBuf setChar(final int p_setChar_1_, final int p_setChar_2_) {
        return this.HorizonCode_Horizon_È.setChar(p_setChar_1_, p_setChar_2_);
    }
    
    public ByteBuf setFloat(final int p_setFloat_1_, final float p_setFloat_2_) {
        return this.HorizonCode_Horizon_È.setFloat(p_setFloat_1_, p_setFloat_2_);
    }
    
    public ByteBuf setDouble(final int p_setDouble_1_, final double p_setDouble_2_) {
        return this.HorizonCode_Horizon_È.setDouble(p_setDouble_1_, p_setDouble_2_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuf p_setBytes_2_) {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuf p_setBytes_2_, final int p_setBytes_3_) {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuf p_setBytes_2_, final int p_setBytes_3_, final int p_setBytes_4_) {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final byte[] p_setBytes_2_) {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final byte[] p_setBytes_2_, final int p_setBytes_3_, final int p_setBytes_4_) {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuffer p_setBytes_2_) {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_);
    }
    
    public int setBytes(final int p_setBytes_1_, final InputStream p_setBytes_2_, final int p_setBytes_3_) throws IOException {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }
    
    public int setBytes(final int p_setBytes_1_, final ScatteringByteChannel p_setBytes_2_, final int p_setBytes_3_) throws IOException {
        return this.HorizonCode_Horizon_È.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }
    
    public ByteBuf setZero(final int p_setZero_1_, final int p_setZero_2_) {
        return this.HorizonCode_Horizon_È.setZero(p_setZero_1_, p_setZero_2_);
    }
    
    public boolean readBoolean() {
        return this.HorizonCode_Horizon_È.readBoolean();
    }
    
    public byte readByte() {
        return this.HorizonCode_Horizon_È.readByte();
    }
    
    public short readUnsignedByte() {
        return this.HorizonCode_Horizon_È.readUnsignedByte();
    }
    
    public short readShort() {
        return this.HorizonCode_Horizon_È.readShort();
    }
    
    public int readUnsignedShort() {
        return this.HorizonCode_Horizon_È.readUnsignedShort();
    }
    
    public int readMedium() {
        return this.HorizonCode_Horizon_È.readMedium();
    }
    
    public int readUnsignedMedium() {
        return this.HorizonCode_Horizon_È.readUnsignedMedium();
    }
    
    public int readInt() {
        return this.HorizonCode_Horizon_È.readInt();
    }
    
    public long readUnsignedInt() {
        return this.HorizonCode_Horizon_È.readUnsignedInt();
    }
    
    public long readLong() {
        return this.HorizonCode_Horizon_È.readLong();
    }
    
    public char readChar() {
        return this.HorizonCode_Horizon_È.readChar();
    }
    
    public float readFloat() {
        return this.HorizonCode_Horizon_È.readFloat();
    }
    
    public double readDouble() {
        return this.HorizonCode_Horizon_È.readDouble();
    }
    
    public ByteBuf readBytes(final int p_readBytes_1_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readSlice(final int p_readSlice_1_) {
        return this.HorizonCode_Horizon_È.readSlice(p_readSlice_1_);
    }
    
    public ByteBuf readBytes(final ByteBuf p_readBytes_1_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readBytes(final ByteBuf p_readBytes_1_, final int p_readBytes_2_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_, p_readBytes_2_);
    }
    
    public ByteBuf readBytes(final ByteBuf p_readBytes_1_, final int p_readBytes_2_, final int p_readBytes_3_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }
    
    public ByteBuf readBytes(final byte[] p_readBytes_1_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readBytes(final byte[] p_readBytes_1_, final int p_readBytes_2_, final int p_readBytes_3_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }
    
    public ByteBuf readBytes(final ByteBuffer p_readBytes_1_) {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readBytes(final OutputStream p_readBytes_1_, final int p_readBytes_2_) throws IOException {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_, p_readBytes_2_);
    }
    
    public int readBytes(final GatheringByteChannel p_readBytes_1_, final int p_readBytes_2_) throws IOException {
        return this.HorizonCode_Horizon_È.readBytes(p_readBytes_1_, p_readBytes_2_);
    }
    
    public ByteBuf skipBytes(final int p_skipBytes_1_) {
        return this.HorizonCode_Horizon_È.skipBytes(p_skipBytes_1_);
    }
    
    public ByteBuf writeBoolean(final boolean p_writeBoolean_1_) {
        return this.HorizonCode_Horizon_È.writeBoolean(p_writeBoolean_1_);
    }
    
    public ByteBuf writeByte(final int p_writeByte_1_) {
        return this.HorizonCode_Horizon_È.writeByte(p_writeByte_1_);
    }
    
    public ByteBuf writeShort(final int p_writeShort_1_) {
        return this.HorizonCode_Horizon_È.writeShort(p_writeShort_1_);
    }
    
    public ByteBuf writeMedium(final int p_writeMedium_1_) {
        return this.HorizonCode_Horizon_È.writeMedium(p_writeMedium_1_);
    }
    
    public ByteBuf writeInt(final int p_writeInt_1_) {
        return this.HorizonCode_Horizon_È.writeInt(p_writeInt_1_);
    }
    
    public ByteBuf writeLong(final long p_writeLong_1_) {
        return this.HorizonCode_Horizon_È.writeLong(p_writeLong_1_);
    }
    
    public ByteBuf writeChar(final int p_writeChar_1_) {
        return this.HorizonCode_Horizon_È.writeChar(p_writeChar_1_);
    }
    
    public ByteBuf writeFloat(final float p_writeFloat_1_) {
        return this.HorizonCode_Horizon_È.writeFloat(p_writeFloat_1_);
    }
    
    public ByteBuf writeDouble(final double p_writeDouble_1_) {
        return this.HorizonCode_Horizon_È.writeDouble(p_writeDouble_1_);
    }
    
    public ByteBuf writeBytes(final ByteBuf p_writeBytes_1_) {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_);
    }
    
    public ByteBuf writeBytes(final ByteBuf p_writeBytes_1_, final int p_writeBytes_2_) {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }
    
    public ByteBuf writeBytes(final ByteBuf p_writeBytes_1_, final int p_writeBytes_2_, final int p_writeBytes_3_) {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }
    
    public ByteBuf writeBytes(final byte[] p_writeBytes_1_) {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_);
    }
    
    public ByteBuf writeBytes(final byte[] p_writeBytes_1_, final int p_writeBytes_2_, final int p_writeBytes_3_) {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }
    
    public ByteBuf writeBytes(final ByteBuffer p_writeBytes_1_) {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_);
    }
    
    public int writeBytes(final InputStream p_writeBytes_1_, final int p_writeBytes_2_) throws IOException {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }
    
    public int writeBytes(final ScatteringByteChannel p_writeBytes_1_, final int p_writeBytes_2_) throws IOException {
        return this.HorizonCode_Horizon_È.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }
    
    public ByteBuf writeZero(final int p_writeZero_1_) {
        return this.HorizonCode_Horizon_È.writeZero(p_writeZero_1_);
    }
    
    public int indexOf(final int p_indexOf_1_, final int p_indexOf_2_, final byte p_indexOf_3_) {
        return this.HorizonCode_Horizon_È.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
    }
    
    public int bytesBefore(final byte p_bytesBefore_1_) {
        return this.HorizonCode_Horizon_È.bytesBefore(p_bytesBefore_1_);
    }
    
    public int bytesBefore(final int p_bytesBefore_1_, final byte p_bytesBefore_2_) {
        return this.HorizonCode_Horizon_È.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
    }
    
    public int bytesBefore(final int p_bytesBefore_1_, final int p_bytesBefore_2_, final byte p_bytesBefore_3_) {
        return this.HorizonCode_Horizon_È.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
    }
    
    public int forEachByte(final ByteBufProcessor p_forEachByte_1_) {
        return this.HorizonCode_Horizon_È.forEachByte(p_forEachByte_1_);
    }
    
    public int forEachByte(final int p_forEachByte_1_, final int p_forEachByte_2_, final ByteBufProcessor p_forEachByte_3_) {
        return this.HorizonCode_Horizon_È.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
    }
    
    public int forEachByteDesc(final ByteBufProcessor p_forEachByteDesc_1_) {
        return this.HorizonCode_Horizon_È.forEachByteDesc(p_forEachByteDesc_1_);
    }
    
    public int forEachByteDesc(final int p_forEachByteDesc_1_, final int p_forEachByteDesc_2_, final ByteBufProcessor p_forEachByteDesc_3_) {
        return this.HorizonCode_Horizon_È.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
    }
    
    public ByteBuf copy() {
        return this.HorizonCode_Horizon_È.copy();
    }
    
    public ByteBuf copy(final int p_copy_1_, final int p_copy_2_) {
        return this.HorizonCode_Horizon_È.copy(p_copy_1_, p_copy_2_);
    }
    
    public ByteBuf slice() {
        return this.HorizonCode_Horizon_È.slice();
    }
    
    public ByteBuf slice(final int p_slice_1_, final int p_slice_2_) {
        return this.HorizonCode_Horizon_È.slice(p_slice_1_, p_slice_2_);
    }
    
    public ByteBuf duplicate() {
        return this.HorizonCode_Horizon_È.duplicate();
    }
    
    public int nioBufferCount() {
        return this.HorizonCode_Horizon_È.nioBufferCount();
    }
    
    public ByteBuffer nioBuffer() {
        return this.HorizonCode_Horizon_È.nioBuffer();
    }
    
    public ByteBuffer nioBuffer(final int p_nioBuffer_1_, final int p_nioBuffer_2_) {
        return this.HorizonCode_Horizon_È.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
    }
    
    public ByteBuffer internalNioBuffer(final int p_internalNioBuffer_1_, final int p_internalNioBuffer_2_) {
        return this.HorizonCode_Horizon_È.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
    }
    
    public ByteBuffer[] nioBuffers() {
        return this.HorizonCode_Horizon_È.nioBuffers();
    }
    
    public ByteBuffer[] nioBuffers(final int p_nioBuffers_1_, final int p_nioBuffers_2_) {
        return this.HorizonCode_Horizon_È.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
    }
    
    public boolean hasArray() {
        return this.HorizonCode_Horizon_È.hasArray();
    }
    
    public byte[] array() {
        return this.HorizonCode_Horizon_È.array();
    }
    
    public int arrayOffset() {
        return this.HorizonCode_Horizon_È.arrayOffset();
    }
    
    public boolean hasMemoryAddress() {
        return this.HorizonCode_Horizon_È.hasMemoryAddress();
    }
    
    public long memoryAddress() {
        return this.HorizonCode_Horizon_È.memoryAddress();
    }
    
    public String toString(final Charset p_toString_1_) {
        return this.HorizonCode_Horizon_È.toString(p_toString_1_);
    }
    
    public String toString(final int p_toString_1_, final int p_toString_2_, final Charset p_toString_3_) {
        return this.HorizonCode_Horizon_È.toString(p_toString_1_, p_toString_2_, p_toString_3_);
    }
    
    public int hashCode() {
        return this.HorizonCode_Horizon_È.hashCode();
    }
    
    public boolean equals(final Object p_equals_1_) {
        return this.HorizonCode_Horizon_È.equals(p_equals_1_);
    }
    
    public int compareTo(final ByteBuf p_compareTo_1_) {
        return this.HorizonCode_Horizon_È.compareTo(p_compareTo_1_);
    }
    
    public String toString() {
        return this.HorizonCode_Horizon_È.toString();
    }
    
    public ByteBuf retain(final int p_retain_1_) {
        return this.HorizonCode_Horizon_È.retain(p_retain_1_);
    }
    
    public ByteBuf retain() {
        return this.HorizonCode_Horizon_È.retain();
    }
    
    public int refCnt() {
        return this.HorizonCode_Horizon_È.refCnt();
    }
    
    public boolean release() {
        return this.HorizonCode_Horizon_È.release();
    }
    
    public boolean release(final int p_release_1_) {
        return this.HorizonCode_Horizon_È.release(p_release_1_);
    }
}
