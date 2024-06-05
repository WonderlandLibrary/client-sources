package net.minecraft.client.audio;

import net.minecraft.util.*;
import java.net.*;
import net.minecraft.client.*;
import java.io.*;

static final class SoundManager$1 extends URLStreamHandler {
    final /* synthetic */ ResourceLocation val$p_148612_0_;
    
    @Override
    protected URLConnection openConnection(final URL p_openConnection_1_) {
        return new URLConnection(p_openConnection_1_) {
            @Override
            public void connect() {
            }
            
            @Override
            public InputStream getInputStream() throws IOException {
                return Minecraft.getMinecraft().getResourceManager().getResource(URLStreamHandler.this.val$p_148612_0_).getInputStream();
            }
        };
    }
}