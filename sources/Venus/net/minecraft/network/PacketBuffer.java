/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.extensions.IForgePacketBuffer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PacketBuffer
extends ByteBuf
implements IForgePacketBuffer {
    private final ByteBuf buf;
    private Map<String, Object> customData;

    public PacketBuffer(ByteBuf byteBuf) {
        this.buf = byteBuf;
    }

    public PacketBuffer(ByteBuf byteBuf, Map<String, Object> map) {
        this.buf = byteBuf;
        this.customData = map;
    }

    public static int getVarIntSize(int n) {
        for (int i = 1; i < 5; ++i) {
            if ((n & -1 << i * 7) != 0) continue;
            return i;
        }
        return 0;
    }

    public <T> T func_240628_a_(Codec<T> codec) throws IOException {
        CompoundNBT compoundNBT = this.func_244273_m();
        DataResult dataResult = codec.parse(NBTDynamicOps.INSTANCE, compoundNBT);
        if (dataResult.error().isPresent()) {
            throw new IOException("Failed to decode: " + dataResult.error().get().message() + " " + compoundNBT);
        }
        return (T)dataResult.result().get();
    }

    public <T> void func_240629_a_(Codec<T> codec, T t) throws IOException {
        DataResult<INBT> dataResult = codec.encodeStart(NBTDynamicOps.INSTANCE, (INBT)t);
        if (dataResult.error().isPresent()) {
            throw new IOException("Failed to encode: " + dataResult.error().get().message() + " " + t);
        }
        this.writeCompoundTag((CompoundNBT)dataResult.result().get());
    }

    public PacketBuffer writeByteArray(byte[] byArray) {
        this.writeVarInt(byArray.length);
        this.writeBytes(byArray);
        return this;
    }

    public byte[] readByteArray() {
        return this.readByteArray(this.readableBytes());
    }

    public byte[] readByteArray(int n) {
        int n2 = this.readVarInt();
        if (n2 > n) {
            throw new DecoderException("ByteArray with size " + n2 + " is bigger than allowed " + n);
        }
        byte[] byArray = new byte[n2];
        this.readBytes(byArray);
        return byArray;
    }

    public PacketBuffer writeVarIntArray(int[] nArray) {
        this.writeVarInt(nArray.length);
        for (int n : nArray) {
            this.writeVarInt(n);
        }
        return this;
    }

    public int[] readVarIntArray() {
        return this.readVarIntArray(this.readableBytes());
    }

    public int[] readVarIntArray(int n) {
        int n2 = this.readVarInt();
        if (n2 > n) {
            throw new DecoderException("VarIntArray with size " + n2 + " is bigger than allowed " + n);
        }
        int[] nArray = new int[n2];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = this.readVarInt();
        }
        return nArray;
    }

    public PacketBuffer writeLongArray(long[] lArray) {
        this.writeVarInt(lArray.length);
        for (long l : lArray) {
            this.writeLong(l);
        }
        return this;
    }

    public long[] readLongArray(@Nullable long[] lArray) {
        return this.readLongArray(lArray, this.readableBytes() / 8);
    }

    public long[] readLongArray(@Nullable long[] lArray, int n) {
        int n2 = this.readVarInt();
        if (lArray == null || lArray.length != n2) {
            if (n2 > n) {
                throw new DecoderException("LongArray with size " + n2 + " is bigger than allowed " + n);
            }
            lArray = new long[n2];
        }
        for (int i = 0; i < lArray.length; ++i) {
            lArray[i] = this.readLong();
        }
        return lArray;
    }

    public BlockPos readBlockPos() {
        return BlockPos.fromLong(this.readLong());
    }

    public PacketBuffer writeBlockPos(BlockPos blockPos) {
        this.writeLong(blockPos.toLong());
        return this;
    }

    public SectionPos readSectionPos() {
        return SectionPos.from(this.readLong());
    }

    public ITextComponent readTextComponent() {
        return ITextComponent.Serializer.getComponentFromJson(this.readString(262144));
    }

    public PacketBuffer writeTextComponent(ITextComponent iTextComponent) {
        return this.writeString(ITextComponent.Serializer.toJson(iTextComponent), 262144);
    }

    public <T extends Enum<T>> T readEnumValue(Class<T> clazz) {
        return (T)((Enum[])clazz.getEnumConstants())[this.readVarInt()];
    }

    public PacketBuffer writeEnumValue(Enum<?> enum_) {
        return this.writeVarInt(enum_.ordinal());
    }

    public int readVarInt() {
        byte by;
        int n = 0;
        int n2 = 0;
        do {
            by = this.readByte();
            n |= (by & 0x7F) << n2++ * 7;
            if (n2 <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((by & 0x80) == 128);
        return n;
    }

    public long readVarLong() {
        byte by;
        long l = 0L;
        int n = 0;
        do {
            by = this.readByte();
            l |= (long)(by & 0x7F) << n++ * 7;
            if (n <= 10) continue;
            throw new RuntimeException("VarLong too big");
        } while ((by & 0x80) == 128);
        return l;
    }

    public PacketBuffer writeUniqueId(UUID uUID) {
        this.writeLong(uUID.getMostSignificantBits());
        this.writeLong(uUID.getLeastSignificantBits());
        return this;
    }

    public UUID readUniqueId() {
        return new UUID(this.readLong(), this.readLong());
    }

    public PacketBuffer writeVarInt(int n) {
        while ((n & 0xFFFFFF80) != 0) {
            this.writeByte(n & 0x7F | 0x80);
            n >>>= 7;
        }
        this.writeByte(n);
        return this;
    }

    public PacketBuffer writeVarLong(long l) {
        while ((l & 0xFFFFFFFFFFFFFF80L) != 0L) {
            this.writeByte((int)(l & 0x7FL) | 0x80);
            l >>>= 7;
        }
        this.writeByte((int)l);
        return this;
    }

    public PacketBuffer writeCompoundTag(@Nullable CompoundNBT compoundNBT) {
        if (compoundNBT == null) {
            this.writeByte(0);
        } else {
            try {
                CompressedStreamTools.write(compoundNBT, new ByteBufOutputStream(this));
            } catch (IOException iOException) {
                throw new EncoderException(iOException);
            }
        }
        return this;
    }

    @Nullable
    public CompoundNBT readCompoundTag() {
        return this.func_244272_a(new NBTSizeTracker(0x200000L));
    }

    @Nullable
    public CompoundNBT func_244273_m() {
        return this.func_244272_a(NBTSizeTracker.INFINITE);
    }

    @Nullable
    public CompoundNBT func_244272_a(NBTSizeTracker nBTSizeTracker) {
        int n = this.readerIndex();
        byte by = this.readByte();
        if (by == 0) {
            return null;
        }
        this.readerIndex(n);
        try {
            return CompressedStreamTools.read(new ByteBufInputStream(this), nBTSizeTracker);
        } catch (IOException iOException) {
            throw new EncoderException(iOException);
        }
    }

    public PacketBuffer writeItemStack(ItemStack itemStack) {
        return this.writeItemStack(itemStack, false);
    }

    public PacketBuffer writeItemStack(ItemStack itemStack, boolean bl) {
        if (itemStack.isEmpty()) {
            this.writeBoolean(true);
        } else {
            this.writeBoolean(false);
            Item item = itemStack.getItem();
            this.writeVarInt(Item.getIdFromItem(item));
            this.writeByte(itemStack.getCount());
            CompoundNBT compoundNBT = null;
            if (ReflectorForge.isDamageable(item, itemStack) || item.shouldSyncTag()) {
                compoundNBT = bl && Reflector.IForgeItemStack_getShareTag.exists() ? (CompoundNBT)Reflector.call(itemStack, Reflector.IForgeItemStack_getShareTag, new Object[0]) : itemStack.getTag();
            }
            this.writeCompoundTag(compoundNBT);
        }
        return this;
    }

    public ItemStack readItemStack() {
        if (!this.readBoolean()) {
            return ItemStack.EMPTY;
        }
        int n = this.readVarInt();
        byte by = this.readByte();
        ItemStack itemStack = new ItemStack(Item.getItemById(n), by);
        if (Reflector.IForgeItemStack_readShareTag.exists()) {
            Reflector.call(itemStack, Reflector.IForgeItemStack_readShareTag, this.readCompoundTag());
        } else {
            itemStack.setTag(this.readCompoundTag());
        }
        return itemStack;
    }

    public String readString() {
        return this.readString(Short.MAX_VALUE);
    }

    public String readString(int n) {
        int n2 = this.readVarInt();
        if (n2 > n * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + n2 + " > " + n * 4 + ")");
        }
        if (n2 < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        String string = this.toString(this.readerIndex(), n2, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + n2);
        if (string.length() > n) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + n2 + " > " + n + ")");
        }
        return string;
    }

    public PacketBuffer writeString(String string) {
        return this.writeString(string, Short.MAX_VALUE);
    }

    public PacketBuffer writeString(String string, int n) {
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        if (byArray.length > n) {
            throw new EncoderException("String too big (was " + byArray.length + " bytes encoded, max " + n + ")");
        }
        this.writeVarInt(byArray.length);
        this.writeBytes(byArray);
        return this;
    }

    public ResourceLocation readResourceLocation() {
        return new ResourceLocation(this.readString(Short.MAX_VALUE));
    }

    public PacketBuffer writeResourceLocation(ResourceLocation resourceLocation) {
        this.writeString(resourceLocation.toString());
        return this;
    }

    public Date readTime() {
        return new Date(this.readLong());
    }

    public PacketBuffer writeTime(Date date) {
        this.writeLong(date.getTime());
        return this;
    }

    public BlockRayTraceResult readBlockRay() {
        BlockPos blockPos = this.readBlockPos();
        Direction direction = this.readEnumValue(Direction.class);
        float f = this.readFloat();
        float f2 = this.readFloat();
        float f3 = this.readFloat();
        boolean bl = this.readBoolean();
        return new BlockRayTraceResult(new Vector3d((double)blockPos.getX() + (double)f, (double)blockPos.getY() + (double)f2, (double)blockPos.getZ() + (double)f3), direction, blockPos, bl);
    }

    public void writeBlockRay(BlockRayTraceResult blockRayTraceResult) {
        BlockPos blockPos = blockRayTraceResult.getPos();
        this.writeBlockPos(blockPos);
        this.writeEnumValue(blockRayTraceResult.getFace());
        Vector3d vector3d = blockRayTraceResult.getHitVec();
        this.writeFloat((float)(vector3d.x - (double)blockPos.getX()));
        this.writeFloat((float)(vector3d.y - (double)blockPos.getY()));
        this.writeFloat((float)(vector3d.z - (double)blockPos.getZ()));
        this.writeBoolean(blockRayTraceResult.isInside());
    }

    @Override
    public int capacity() {
        return this.buf.capacity();
    }

    @Override
    public ByteBuf capacity(int n) {
        return this.buf.capacity(n);
    }

    @Override
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }

    @Override
    public ByteOrder order() {
        return this.buf.order();
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        return this.buf.order(byteOrder);
    }

    @Override
    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }

    @Override
    public boolean isDirect() {
        return this.buf.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return this.buf.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.buf.asReadOnly();
    }

    @Override
    public int readerIndex() {
        return this.buf.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int n) {
        return this.buf.readerIndex(n);
    }

    @Override
    public int writerIndex() {
        return this.buf.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int n) {
        return this.buf.writerIndex(n);
    }

    @Override
    public ByteBuf setIndex(int n, int n2) {
        return this.buf.setIndex(n, n2);
    }

    @Override
    public int readableBytes() {
        return this.buf.readableBytes();
    }

    @Override
    public int writableBytes() {
        return this.buf.writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return this.buf.isReadable();
    }

    @Override
    public boolean isReadable(int n) {
        return this.buf.isReadable(n);
    }

    @Override
    public boolean isWritable() {
        return this.buf.isWritable();
    }

    @Override
    public boolean isWritable(int n) {
        return this.buf.isWritable(n);
    }

    @Override
    public ByteBuf clear() {
        return this.buf.clear();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        return this.buf.ensureWritable(n);
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        return this.buf.ensureWritable(n, bl);
    }

    @Override
    public boolean getBoolean(int n) {
        return this.buf.getBoolean(n);
    }

    @Override
    public byte getByte(int n) {
        return this.buf.getByte(n);
    }

    @Override
    public short getUnsignedByte(int n) {
        return this.buf.getUnsignedByte(n);
    }

    @Override
    public short getShort(int n) {
        return this.buf.getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        return this.buf.getShortLE(n);
    }

    @Override
    public int getUnsignedShort(int n) {
        return this.buf.getUnsignedShort(n);
    }

    @Override
    public int getUnsignedShortLE(int n) {
        return this.buf.getUnsignedShortLE(n);
    }

    @Override
    public int getMedium(int n) {
        return this.buf.getMedium(n);
    }

    @Override
    public int getMediumLE(int n) {
        return this.buf.getMediumLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        return this.buf.getUnsignedMedium(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        return this.buf.getUnsignedMediumLE(n);
    }

    @Override
    public int getInt(int n) {
        return this.buf.getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        return this.buf.getIntLE(n);
    }

    @Override
    public long getUnsignedInt(int n) {
        return this.buf.getUnsignedInt(n);
    }

    @Override
    public long getUnsignedIntLE(int n) {
        return this.buf.getUnsignedIntLE(n);
    }

    @Override
    public long getLong(int n) {
        return this.buf.getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        return this.buf.getLongLE(n);
    }

    @Override
    public char getChar(int n) {
        return this.buf.getChar(n);
    }

    @Override
    public float getFloat(int n) {
        return this.buf.getFloat(n);
    }

    @Override
    public double getDouble(int n) {
        return this.buf.getDouble(n);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        return this.buf.getBytes(n, byteBuf);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        return this.buf.getBytes(n, byteBuf, n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.buf.getBytes(n, byteBuf, n2, n3);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray) {
        return this.buf.getBytes(n, byArray);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        return this.buf.getBytes(n, byArray, n2, n3);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        return this.buf.getBytes(n, byteBuffer);
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        return this.buf.getBytes(n, outputStream, n2);
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.buf.getBytes(n, gatheringByteChannel, n2);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.buf.getBytes(n, fileChannel, l, n2);
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        return this.buf.getCharSequence(n, n2, charset);
    }

    @Override
    public ByteBuf setBoolean(int n, boolean bl) {
        return this.buf.setBoolean(n, bl);
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        return this.buf.setByte(n, n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        return this.buf.setShort(n, n2);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        return this.buf.setShortLE(n, n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        return this.buf.setMedium(n, n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        return this.buf.setMediumLE(n, n2);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        return this.buf.setInt(n, n2);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        return this.buf.setIntLE(n, n2);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        return this.buf.setLong(n, l);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        return this.buf.setLongLE(n, l);
    }

    @Override
    public ByteBuf setChar(int n, int n2) {
        return this.buf.setChar(n, n2);
    }

    @Override
    public ByteBuf setFloat(int n, float f) {
        return this.buf.setFloat(n, f);
    }

    @Override
    public ByteBuf setDouble(int n, double d) {
        return this.buf.setDouble(n, d);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        return this.buf.setBytes(n, byteBuf);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        return this.buf.setBytes(n, byteBuf, n2);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.buf.setBytes(n, byteBuf, n2, n3);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray) {
        return this.buf.setBytes(n, byArray);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        return this.buf.setBytes(n, byArray, n2, n3);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        return this.buf.setBytes(n, byteBuffer);
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return this.buf.setBytes(n, inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        return this.buf.setBytes(n, scatteringByteChannel, n2);
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.buf.setBytes(n, fileChannel, l, n2);
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        return this.buf.setZero(n, n2);
    }

    @Override
    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        return this.buf.setCharSequence(n, charSequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    @Override
    public byte readByte() {
        return this.buf.readByte();
    }

    @Override
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return this.buf.readShort();
    }

    @Override
    public short readShortLE() {
        return this.buf.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return this.buf.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return this.buf.readMedium();
    }

    @Override
    public int readMediumLE() {
        return this.buf.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return this.buf.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return this.buf.readInt();
    }

    @Override
    public int readIntLE() {
        return this.buf.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return this.buf.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return this.buf.readLong();
    }

    @Override
    public long readLongLE() {
        return this.buf.readLongLE();
    }

    @Override
    public char readChar() {
        return this.buf.readChar();
    }

    @Override
    public float readFloat() {
        return this.buf.readFloat();
    }

    @Override
    public double readDouble() {
        return this.buf.readDouble();
    }

    @Override
    public ByteBuf readBytes(int n) {
        return this.buf.readBytes(n);
    }

    @Override
    public ByteBuf readSlice(int n) {
        return this.buf.readSlice(n);
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.buf.readRetainedSlice(n);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        return this.buf.readBytes(byteBuf);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        return this.buf.readBytes(byteBuf, n);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        return this.buf.readBytes(byteBuf, n, n2);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray) {
        return this.buf.readBytes(byArray);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        return this.buf.readBytes(byArray, n, n2);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return this.buf.readBytes(byteBuffer);
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        return this.buf.readBytes(outputStream, n);
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, n);
    }

    @Override
    public CharSequence readCharSequence(int n, Charset charset) {
        return this.buf.readCharSequence(n, charset);
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.buf.readBytes(fileChannel, l, n);
    }

    @Override
    public ByteBuf skipBytes(int n) {
        return this.buf.skipBytes(n);
    }

    @Override
    public ByteBuf writeBoolean(boolean bl) {
        return this.buf.writeBoolean(bl);
    }

    @Override
    public ByteBuf writeByte(int n) {
        return this.buf.writeByte(n);
    }

    @Override
    public ByteBuf writeShort(int n) {
        return this.buf.writeShort(n);
    }

    @Override
    public ByteBuf writeShortLE(int n) {
        return this.buf.writeShortLE(n);
    }

    @Override
    public ByteBuf writeMedium(int n) {
        return this.buf.writeMedium(n);
    }

    @Override
    public ByteBuf writeMediumLE(int n) {
        return this.buf.writeMediumLE(n);
    }

    @Override
    public ByteBuf writeInt(int n) {
        return this.buf.writeInt(n);
    }

    @Override
    public ByteBuf writeIntLE(int n) {
        return this.buf.writeIntLE(n);
    }

    @Override
    public ByteBuf writeLong(long l) {
        return this.buf.writeLong(l);
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        return this.buf.writeLongLE(l);
    }

    @Override
    public ByteBuf writeChar(int n) {
        return this.buf.writeChar(n);
    }

    @Override
    public ByteBuf writeFloat(float f) {
        return this.buf.writeFloat(f);
    }

    @Override
    public ByteBuf writeDouble(double d) {
        return this.buf.writeDouble(d);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return this.buf.writeBytes(byteBuf);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        return this.buf.writeBytes(byteBuf, n);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        return this.buf.writeBytes(byteBuf, n, n2);
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray) {
        return this.buf.writeBytes(byArray);
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        return this.buf.writeBytes(byArray, n, n2);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return this.buf.writeBytes(byteBuffer);
    }

    @Override
    public int writeBytes(InputStream inputStream, int n) throws IOException {
        return this.buf.writeBytes(inputStream, n);
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, n);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.buf.writeBytes(fileChannel, l, n);
    }

    @Override
    public ByteBuf writeZero(int n) {
        return this.buf.writeZero(n);
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.buf.writeCharSequence(charSequence, charset);
    }

    @Override
    public int indexOf(int n, int n2, byte by) {
        return this.buf.indexOf(n, n2, by);
    }

    @Override
    public int bytesBefore(byte by) {
        return this.buf.bytesBefore(by);
    }

    @Override
    public int bytesBefore(int n, byte by) {
        return this.buf.bytesBefore(n, by);
    }

    @Override
    public int bytesBefore(int n, int n2, byte by) {
        return this.buf.bytesBefore(n, n2, by);
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return this.buf.forEachByte(byteProcessor);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return this.buf.forEachByte(n, n2, byteProcessor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.buf.forEachByteDesc(byteProcessor);
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return this.buf.forEachByteDesc(n, n2, byteProcessor);
    }

    @Override
    public ByteBuf copy() {
        return this.buf.copy();
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return this.buf.copy(n, n2);
    }

    @Override
    public ByteBuf slice() {
        return this.buf.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.buf.retainedSlice();
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return this.buf.slice(n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.buf.retainedSlice(n, n2);
    }

    @Override
    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.buf.retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return this.buf.nioBuffer(n, n2);
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        return this.buf.internalNioBuffer(n, n2);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return this.buf.nioBuffers(n, n2);
    }

    @Override
    public boolean hasArray() {
        return this.buf.hasArray();
    }

    @Override
    public byte[] array() {
        return this.buf.array();
    }

    @Override
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return this.buf.memoryAddress();
    }

    @Override
    public String toString(Charset charset) {
        return this.buf.toString(charset);
    }

    @Override
    public String toString(int n, int n2, Charset charset) {
        return this.buf.toString(n, n2, charset);
    }

    @Override
    public int hashCode() {
        return this.buf.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return this.buf.equals(object);
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return this.buf.compareTo(byteBuf);
    }

    @Override
    public String toString() {
        return this.buf.toString();
    }

    @Override
    public ByteBuf retain(int n) {
        return this.buf.retain(n);
    }

    @Override
    public ByteBuf retain() {
        return this.buf.retain();
    }

    @Override
    public ByteBuf touch() {
        return this.buf.touch();
    }

    @Override
    public ByteBuf touch(Object object) {
        return this.buf.touch(object);
    }

    @Override
    public int refCnt() {
        return this.buf.refCnt();
    }

    @Override
    public boolean release() {
        return this.buf.release();
    }

    @Override
    public boolean release(int n) {
        return this.buf.release(n);
    }

    public Map<String, Object> getCustomData() {
        return this.customData;
    }

    public Object getCustomData(String string) {
        return this.customData == null ? null : this.customData.get(string);
    }

    public void setCustomData(Map<String, Object> map) {
        this.customData = map;
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ByteBuf)object);
    }
}

