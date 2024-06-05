package net.minecraft.client.audio;

import java.net.*;
import net.minecraft.client.*;
import java.io.*;

class SoundManager$1$1 extends URLConnection {
    @Override
    public void connect() {
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(URLStreamHandler.this.val$p_148612_0_).getInputStream();
    }
}