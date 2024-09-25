/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufInputStream
 *  io.netty.buffer.ByteBufOutputStream
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import us.myles.ViaVersion.api.type.Type;
import us.myles.viaversion.libs.opennbt.NBTIO;
import us.myles.viaversion.libs.opennbt.tag.TagRegistry;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class NBTType
extends Type<CompoundTag> {
    public NBTType() {
        super(CompoundTag.class);
    }

    @Override
    public CompoundTag read(ByteBuf buffer) throws Exception {
        Preconditions.checkArgument((buffer.readableBytes() <= 0x200000 ? 1 : 0) != 0, (String)"Cannot read NBT (got %s bytes)", (Object[])new Object[]{buffer.readableBytes()});
        int readerIndex = buffer.readerIndex();
        byte b = buffer.readByte();
        if (b == 0) {
            return null;
        }
        buffer.readerIndex(readerIndex);
        return (CompoundTag)NBTIO.readTag((DataInput)new ByteBufInputStream(buffer));
    }

    @Override
    public void write(ByteBuf buffer, CompoundTag object) throws Exception {
        if (object == null) {
            buffer.writeByte(0);
        } else {
            ByteBufOutputStream bytebufStream = new ByteBufOutputStream(buffer);
            NBTIO.writeTag((DataOutput)bytebufStream, (Tag)object);
        }
    }

    static {
        TagRegistry.unregister(60);
        TagRegistry.unregister(61);
        TagRegistry.unregister(65);
    }
}

