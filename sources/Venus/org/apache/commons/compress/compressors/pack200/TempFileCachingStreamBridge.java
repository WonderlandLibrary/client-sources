/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.pack200.StreamBridge;

class TempFileCachingStreamBridge
extends StreamBridge {
    private final File f = File.createTempFile("commons-compress", "packtemp");

    TempFileCachingStreamBridge() throws IOException {
        this.f.deleteOnExit();
        this.out = new FileOutputStream(this.f);
    }

    InputStream getInputView() throws IOException {
        this.out.close();
        return new FileInputStream(this, this.f){
            final TempFileCachingStreamBridge this$0;
            {
                this.this$0 = tempFileCachingStreamBridge;
                super(file);
            }

            public void close() throws IOException {
                super.close();
                TempFileCachingStreamBridge.access$000(this.this$0).delete();
            }
        };
    }

    static File access$000(TempFileCachingStreamBridge tempFileCachingStreamBridge) {
        return tempFileCachingStreamBridge.f;
    }
}

