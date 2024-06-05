package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.io.*;

static class ImputStreamLeakedResourceLogger extends InputStream
{
    private final InputStream field_177330_a;
    private final String field_177328_b;
    private boolean field_177329_c;
    
    public ImputStreamLeakedResourceLogger(final InputStream p_i46093_1_, final ResourceLocation p_i46093_2_, final String p_i46093_3_) {
        this.field_177329_c = false;
        this.field_177330_a = p_i46093_1_;
        final ByteArrayOutputStream var4 = new ByteArrayOutputStream();
        new Exception().printStackTrace(new PrintStream(var4));
        this.field_177328_b = "Leaked resource: '" + p_i46093_2_ + "' loaded from pack: '" + p_i46093_3_ + "'\n" + var4.toString();
    }
    
    @Override
    public void close() throws IOException {
        this.field_177330_a.close();
        this.field_177329_c = true;
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (!this.field_177329_c) {
            FallbackResourceManager.access$000().warn(this.field_177328_b);
        }
        super.finalize();
    }
    
    @Override
    public int read() throws IOException {
        return this.field_177330_a.read();
    }
}
