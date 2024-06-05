package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent$Serializer;
import org.jetbrains.annotations.Nullable;

public class PacketBuffer extends ByteBuf {
   private final ByteBuf buf;

   public PacketBuffer(ByteBuf var1) {
      this.buf = var1;
   }

   public static int getVarIntSize(int var0) {
      for(int var1 = 1; var1 < 5; ++var1) {
         if((var0 & -1 << var1 * 7) == 0) {
            return var1;
         }
      }

      return 5;
   }

   public void writeByteArray(byte[] var1) {
      this.writeVarIntToBuffer(var1.length);
      this.writeBytes(var1);
   }

   public byte[] readByteArray() {
      byte[] var1 = new byte[this.readVarIntFromBuffer()];
      this.readBytes(var1);
      return var1;
   }

   public BlockPos readBlockPos() {
      return BlockPos.fromLong(this.readLong());
   }

   public PacketBuffer writeBlockPos(BlockPos var1) {
      this.writeLong(var1.toLong());
      return this;
   }

   public IChatComponent readChatComponent() throws IOException {
      return IChatComponent$Serializer.jsonToComponent(this.a(32767));
   }

   public PacketBuffer writeChatComponent(IChatComponent var1) throws IOException {
      this.writeString(IChatComponent$Serializer.componentToJson(var1));
      return this;
   }

   public Enum readEnumValue(Class var1) {
      return ((Enum[])var1.getEnumConstants())[this.readVarIntFromBuffer()];
   }

   public PacketBuffer writeEnumValue(Enum var1) {
      this.writeVarIntToBuffer(var1.ordinal());
      return this;
   }

   public int readVarIntFromBuffer() {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         byte var3 = this.readByte();
         var1 |= (var3 & 127) << var2++ * 7;
         if(var2 > 5) {
            throw new RuntimeException("VarInt too big");
         }

         if((var3 & 128) != 128) {
            break;
         }
      }

      return var1;
   }

   public long readVarLong() {
      long var1 = 0L;
      int var3 = 0;

      while(true) {
         byte var4 = this.readByte();
         var1 |= (long)(var4 & 127) << var3++ * 7;
         if(var3 > 10) {
            throw new RuntimeException("VarLong too big");
         }

         if((var4 & 128) != 128) {
            break;
         }
      }

      return var1;
   }

   public PacketBuffer writeUuid(UUID var1) {
      this.writeLong(var1.getMostSignificantBits());
      this.writeLong(var1.getLeastSignificantBits());
      return this;
   }

   public UUID readUuid() {
      return new UUID(this.readLong(), this.readLong());
   }

   public PacketBuffer writeVarIntToBuffer(int var1) {
      while((var1 & -128) != 0) {
         this.writeByte(var1 & 127 | 128);
         var1 >>>= 7;
      }

      this.writeByte(var1);
      return this;
   }

   public PacketBuffer writeVarLong(long var1) {
      while((var1 & -128L) != 0L) {
         this.writeByte((int)(var1 & 127L) | 128);
         var1 >>>= 7;
      }

      this.writeByte((int)var1);
      return this;
   }

   public PacketBuffer writeNBTTagCompoundToBuffer(NBTTagCompound var1) {
      this.writeByte(0);
      return this;
   }

   @Nullable
   public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
      int var1 = this.readerIndex();
      byte var2 = this.readByte();
      return null;
   }

   public PacketBuffer a(ItemStack var1) {
      this.writeShort(-1);
      return this;
   }

   public ItemStack readItemStackFromBuffer() throws IOException {
      ItemStack var1 = null;
      short var2 = this.readShort();
      byte var3 = this.readByte();
      short var4 = this.readShort();
      var1 = new ItemStack(Item.getItemById(var2), var3, var4);
      var1.setTagCompound(this.readNBTTagCompoundFromBuffer());
      return var1;
   }

   public String a(int var1) {
      int var2 = this.readVarIntFromBuffer();
      if(var2 > var1 * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + var1 * 4 + ")");
      } else {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      }
   }

   public PacketBuffer writeString(String var1) {
      byte[] var2 = var1.getBytes(Charsets.UTF_8);
      if(var2.length > 32767) {
         throw new EncoderException("String too big (was " + var2.length + " bytes encoded, max " + 32767 + ")");
      } else {
         this.writeVarIntToBuffer(var2.length);
         this.writeBytes(var2);
         return this;
      }
   }

   public int capacity() {
      return this.buf.capacity();
   }

   public ByteBuf capacity(int var1) {
      return this.buf.capacity(var1);
   }

   public int maxCapacity() {
      return this.buf.maxCapacity();
   }

   public ByteBufAllocator alloc() {
      return this.buf.alloc();
   }

   public ByteOrder order() {
      return this.buf.order();
   }

   public ByteBuf order(ByteOrder var1) {
      return this.buf.order(var1);
   }

   public ByteBuf unwrap() {
      return this.buf.unwrap();
   }

   public boolean isDirect() {
      return this.buf.isDirect();
   }

   public int readerIndex() {
      return this.buf.readerIndex();
   }

   public ByteBuf readerIndex(int var1) {
      return this.buf.readerIndex(var1);
   }

   public int writerIndex() {
      return this.buf.writerIndex();
   }

   public ByteBuf writerIndex(int var1) {
      return this.buf.writerIndex(var1);
   }

   public ByteBuf setIndex(int var1, int var2) {
      return this.buf.setIndex(var1, var2);
   }

   public int readableBytes() {
      return this.buf.readableBytes();
   }

   public int writableBytes() {
      return this.buf.writableBytes();
   }

   public int maxWritableBytes() {
      return this.buf.maxWritableBytes();
   }

   public boolean isReadable() {
      return this.buf.isReadable();
   }

   public boolean isReadable(int var1) {
      return this.buf.isReadable(var1);
   }

   public boolean isWritable() {
      return this.buf.isWritable();
   }

   public boolean isWritable(int var1) {
      return this.buf.isWritable(var1);
   }

   public ByteBuf clear() {
      return this.buf.clear();
   }

   public ByteBuf markReaderIndex() {
      return this.buf.markReaderIndex();
   }

   public ByteBuf resetReaderIndex() {
      return this.buf.resetReaderIndex();
   }

   public ByteBuf markWriterIndex() {
      return this.buf.markWriterIndex();
   }

   public ByteBuf resetWriterIndex() {
      return this.buf.resetWriterIndex();
   }

   public ByteBuf discardReadBytes() {
      return this.buf.discardReadBytes();
   }

   public ByteBuf discardSomeReadBytes() {
      return this.buf.discardSomeReadBytes();
   }

   public ByteBuf ensureWritable(int var1) {
      return this.buf.ensureWritable(var1);
   }

   public int ensureWritable(int var1, boolean var2) {
      return this.buf.ensureWritable(var1, var2);
   }

   public boolean getBoolean(int var1) {
      return this.buf.getBoolean(var1);
   }

   public byte getByte(int var1) {
      return this.buf.getByte(var1);
   }

   public short getUnsignedByte(int var1) {
      return this.buf.getUnsignedByte(var1);
   }

   public short getShort(int var1) {
      return this.buf.getShort(var1);
   }

   public int getUnsignedShort(int var1) {
      return this.buf.getUnsignedShort(var1);
   }

   public int getMedium(int var1) {
      return this.buf.getMedium(var1);
   }

   public int getUnsignedMedium(int var1) {
      return this.buf.getUnsignedMedium(var1);
   }

   public int getInt(int var1) {
      return this.buf.getInt(var1);
   }

   public long getUnsignedInt(int var1) {
      return this.buf.getUnsignedInt(var1);
   }

   public long getLong(int var1) {
      return this.buf.getLong(var1);
   }

   public char getChar(int var1) {
      return this.buf.getChar(var1);
   }

   public float getFloat(int var1) {
      return this.buf.getFloat(var1);
   }

   public double getDouble(int var1) {
      return this.buf.getDouble(var1);
   }

   public ByteBuf getBytes(int var1, ByteBuf var2) {
      return this.buf.getBytes(var1, var2);
   }

   public ByteBuf getBytes(int var1, ByteBuf var2, int var3) {
      return this.buf.getBytes(var1, var2, var3);
   }

   public ByteBuf getBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.buf.getBytes(var1, var2, var3, var4);
   }

   public ByteBuf getBytes(int var1, byte[] var2) {
      return this.buf.getBytes(var1, var2);
   }

   public ByteBuf getBytes(int var1, byte[] var2, int var3, int var4) {
      return this.buf.getBytes(var1, var2, var3, var4);
   }

   public ByteBuf getBytes(int var1, ByteBuffer var2) {
      return this.buf.getBytes(var1, var2);
   }

   public ByteBuf getBytes(int var1, OutputStream var2, int var3) throws IOException {
      return this.buf.getBytes(var1, var2, var3);
   }

   public int getBytes(int var1, GatheringByteChannel var2, int var3) throws IOException {
      return this.buf.getBytes(var1, var2, var3);
   }

   public ByteBuf setBoolean(int var1, boolean var2) {
      return this.buf.setBoolean(var1, var2);
   }

   public ByteBuf setByte(int var1, int var2) {
      return this.buf.setByte(var1, var2);
   }

   public ByteBuf setShort(int var1, int var2) {
      return this.buf.setShort(var1, var2);
   }

   public ByteBuf setMedium(int var1, int var2) {
      return this.buf.setMedium(var1, var2);
   }

   public ByteBuf setInt(int var1, int var2) {
      return this.buf.setInt(var1, var2);
   }

   public ByteBuf setLong(int var1, long var2) {
      return this.buf.setLong(var1, var2);
   }

   public ByteBuf setChar(int var1, int var2) {
      return this.buf.setChar(var1, var2);
   }

   public ByteBuf setFloat(int var1, float var2) {
      return this.buf.setFloat(var1, var2);
   }

   public ByteBuf setDouble(int var1, double var2) {
      return this.buf.setDouble(var1, var2);
   }

   public ByteBuf setBytes(int var1, ByteBuf var2) {
      return this.buf.setBytes(var1, var2);
   }

   public ByteBuf setBytes(int var1, ByteBuf var2, int var3) {
      return this.buf.setBytes(var1, var2, var3);
   }

   public ByteBuf setBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.buf.setBytes(var1, var2, var3, var4);
   }

   public ByteBuf setBytes(int var1, byte[] var2) {
      return this.buf.setBytes(var1, var2);
   }

   public ByteBuf setBytes(int var1, byte[] var2, int var3, int var4) {
      return this.buf.setBytes(var1, var2, var3, var4);
   }

   public ByteBuf setBytes(int var1, ByteBuffer var2) {
      return this.buf.setBytes(var1, var2);
   }

   public int setBytes(int var1, InputStream var2, int var3) throws IOException {
      return this.buf.setBytes(var1, var2, var3);
   }

   public int setBytes(int var1, ScatteringByteChannel var2, int var3) throws IOException {
      return this.buf.setBytes(var1, var2, var3);
   }

   public ByteBuf setZero(int var1, int var2) {
      return this.buf.setZero(var1, var2);
   }

   public boolean readBoolean() {
      return this.buf.readBoolean();
   }

   public byte readByte() {
      return this.buf.readByte();
   }

   public short readUnsignedByte() {
      return this.buf.readUnsignedByte();
   }

   public short readShort() {
      return this.buf.readShort();
   }

   public int readUnsignedShort() {
      return this.buf.readUnsignedShort();
   }

   public int readMedium() {
      return this.buf.readMedium();
   }

   public int readUnsignedMedium() {
      return this.buf.readUnsignedMedium();
   }

   public int readInt() {
      return this.buf.readInt();
   }

   public long readUnsignedInt() {
      return this.buf.readUnsignedInt();
   }

   public long readLong() {
      return this.buf.readLong();
   }

   public char readChar() {
      return this.buf.readChar();
   }

   public float readFloat() {
      return this.buf.readFloat();
   }

   public double readDouble() {
      return this.buf.readDouble();
   }

   public ByteBuf readBytes(int var1) {
      return this.buf.readBytes(var1);
   }

   public ByteBuf readSlice(int var1) {
      return this.buf.readSlice(var1);
   }

   public ByteBuf readBytes(ByteBuf var1) {
      return this.buf.readBytes(var1);
   }

   public ByteBuf readBytes(ByteBuf var1, int var2) {
      return this.buf.readBytes(var1, var2);
   }

   public ByteBuf readBytes(ByteBuf var1, int var2, int var3) {
      return this.buf.readBytes(var1, var2, var3);
   }

   public ByteBuf readBytes(byte[] var1) {
      return this.buf.readBytes(var1);
   }

   public ByteBuf readBytes(byte[] var1, int var2, int var3) {
      return this.buf.readBytes(var1, var2, var3);
   }

   public ByteBuf readBytes(ByteBuffer var1) {
      return this.buf.readBytes(var1);
   }

   public ByteBuf readBytes(OutputStream var1, int var2) throws IOException {
      return this.buf.readBytes(var1, var2);
   }

   public int readBytes(GatheringByteChannel var1, int var2) throws IOException {
      return this.buf.readBytes(var1, var2);
   }

   public ByteBuf skipBytes(int var1) {
      return this.buf.skipBytes(var1);
   }

   public ByteBuf writeBoolean(boolean var1) {
      return this.buf.writeBoolean(var1);
   }

   public ByteBuf writeByte(int var1) {
      return this.buf.writeByte(var1);
   }

   public ByteBuf writeShort(int var1) {
      return this.buf.writeShort(var1);
   }

   public ByteBuf writeMedium(int var1) {
      return this.buf.writeMedium(var1);
   }

   public ByteBuf writeInt(int var1) {
      return this.buf.writeInt(var1);
   }

   public ByteBuf writeLong(long var1) {
      return this.buf.writeLong(var1);
   }

   public ByteBuf writeChar(int var1) {
      return this.buf.writeChar(var1);
   }

   public ByteBuf writeFloat(float var1) {
      return this.buf.writeFloat(var1);
   }

   public ByteBuf writeDouble(double var1) {
      return this.buf.writeDouble(var1);
   }

   public ByteBuf writeBytes(ByteBuf var1) {
      return this.buf.writeBytes(var1);
   }

   public ByteBuf writeBytes(ByteBuf var1, int var2) {
      return this.buf.writeBytes(var1, var2);
   }

   public ByteBuf writeBytes(ByteBuf var1, int var2, int var3) {
      return this.buf.writeBytes(var1, var2, var3);
   }

   public ByteBuf writeBytes(byte[] var1) {
      return this.buf.writeBytes(var1);
   }

   public ByteBuf writeBytes(byte[] var1, int var2, int var3) {
      return this.buf.writeBytes(var1, var2, var3);
   }

   public ByteBuf writeBytes(ByteBuffer var1) {
      return this.buf.writeBytes(var1);
   }

   public int writeBytes(InputStream var1, int var2) throws IOException {
      return this.buf.writeBytes(var1, var2);
   }

   public int writeBytes(ScatteringByteChannel var1, int var2) throws IOException {
      return this.buf.writeBytes(var1, var2);
   }

   public ByteBuf writeZero(int var1) {
      return this.buf.writeZero(var1);
   }

   public int indexOf(int var1, int var2, byte var3) {
      return this.buf.indexOf(var1, var2, var3);
   }

   public int bytesBefore(byte var1) {
      return this.buf.bytesBefore(var1);
   }

   public int bytesBefore(int var1, byte var2) {
      return this.buf.bytesBefore(var1, var2);
   }

   public int bytesBefore(int var1, int var2, byte var3) {
      return this.buf.bytesBefore(var1, var2, var3);
   }

   public int forEachByte(ByteBufProcessor var1) {
      return this.buf.forEachByte(var1);
   }

   public int forEachByte(int var1, int var2, ByteBufProcessor var3) {
      return this.buf.forEachByte(var1, var2, var3);
   }

   public int forEachByteDesc(ByteBufProcessor var1) {
      return this.buf.forEachByteDesc(var1);
   }

   public int forEachByteDesc(int var1, int var2, ByteBufProcessor var3) {
      return this.buf.forEachByteDesc(var1, var2, var3);
   }

   public ByteBuf copy() {
      return this.buf.copy();
   }

   public ByteBuf copy(int var1, int var2) {
      return this.buf.copy(var1, var2);
   }

   public ByteBuf slice() {
      return this.buf.slice();
   }

   public ByteBuf slice(int var1, int var2) {
      return this.buf.slice(var1, var2);
   }

   public ByteBuf duplicate() {
      return this.buf.duplicate();
   }

   public int nioBufferCount() {
      return this.buf.nioBufferCount();
   }

   public ByteBuffer nioBuffer() {
      return this.buf.nioBuffer();
   }

   public ByteBuffer nioBuffer(int var1, int var2) {
      return this.buf.nioBuffer(var1, var2);
   }

   public ByteBuffer internalNioBuffer(int var1, int var2) {
      return this.buf.internalNioBuffer(var1, var2);
   }

   public ByteBuffer[] nioBuffers() {
      return this.buf.nioBuffers();
   }

   public ByteBuffer[] nioBuffers(int var1, int var2) {
      return this.buf.nioBuffers(var1, var2);
   }

   public boolean hasArray() {
      return this.buf.hasArray();
   }

   public byte[] array() {
      return this.buf.array();
   }

   public int arrayOffset() {
      return this.buf.arrayOffset();
   }

   public boolean hasMemoryAddress() {
      return this.buf.hasMemoryAddress();
   }

   public long memoryAddress() {
      return this.buf.memoryAddress();
   }

   public String toString(Charset var1) {
      return this.buf.toString(var1);
   }

   public String toString(int var1, int var2, Charset var3) {
      return this.buf.toString(var1, var2, var3);
   }

   public int hashCode() {
      return this.buf.hashCode();
   }

   public boolean equals(Object var1) {
      return this.buf.equals(var1);
   }

   public int compareTo(ByteBuf var1) {
      return this.buf.compareTo(var1);
   }

   public String toString() {
      return this.buf.toString();
   }

   public ByteBuf retain(int var1) {
      return this.buf.retain(var1);
   }

   public ByteBuf retain() {
      return this.buf.retain();
   }

   public int refCnt() {
      return this.buf.refCnt();
   }

   public boolean release() {
      return this.buf.release();
   }

   public boolean release(int var1) {
      return this.buf.release(var1);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
