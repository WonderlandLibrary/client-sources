package dev.luvbeeq.baritone.utils.type;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;

/**
 * @author Brady
 * @since 12/19/2019
 */
public final class VarInt {

    private final int value;
    private final byte[] serialized;
    private final int size;

    public VarInt(int value) {
        this.value = value;
        this.serialized = serialize0(this.value);
        this.size = this.serialized.length;
    }

    /**
     * @return The integer value that is represented by this {@link VarInt}.
     */
    public final int getValue() {
        return this.value;
    }

    /**
     * @return The size of this {@link VarInt}, in bytes, once serialized.
     */
    public final int getSize() {
        return this.size;
    }

    public final byte[] serialize() {
        return this.serialized;
    }

    private static byte[] serialize0(int valueIn) {
        ByteList bytes = new ByteArrayList();

        int value = valueIn;
        while ((value & 0x80) != 0) {
            bytes.add((byte) (value & 0x7F | 0x80));
            value >>>= 7;
        }
        bytes.add((byte) (value & 0xFF));

        return bytes.toByteArray();
    }

    public static VarInt read(byte[] bytes) {
        return read(bytes, 0);
    }

    public static VarInt read(byte[] bytes, int start) {
        int value = 0;
        int size = 0;
        int index = start;

        while (true) {
            byte b = bytes[index++];
            value |= (b & 0x7F) << size++ * 7;

            if (size > 5) {
                throw new IllegalArgumentException("VarInt size cannot exceed 5 bytes");
            }

            // Most significant bit denotes another byte is to be read.
            if ((b & 0x80) == 0) {
                break;
            }
        }

        return new VarInt(value);
    }
}
