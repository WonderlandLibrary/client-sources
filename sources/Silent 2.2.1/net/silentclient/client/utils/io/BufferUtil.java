package net.silentclient.client.utils.io;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.system.MemoryUtil.memSlice;

public class BufferUtil {
    private static BufferUtil instance = new BufferUtil();
    public static BufferUtil get() {
        return instance;
    }
    public ByteBuffer getResourceBytes(ResourceLocation resource, int bufferSize) throws IOException {
        ByteBuffer buffer;
        InputStream source = Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream();
        ReadableByteChannel rbc = Channels.newChannel(source);

        buffer = createByteBuffer(bufferSize);

        while (true) {
            int bytes = rbc.read(buffer);

            if (bytes == -1) {
                break;
            }

            if (buffer.remaining() == 0) {
                ByteBuffer newBuffer = createByteBuffer(buffer.capacity() * 3 / 2);
                buffer.flip();
                newBuffer.put(buffer);
                buffer = newBuffer;
            }
        }

        buffer.flip();
        return memSlice(buffer);
    }
}