/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.openal.ALC10;
import org.lwjgl.system.MemoryUtil;

public final class ALUtil {
    private ALUtil() {
    }

    @Nullable
    public static List<String> getStringList(long l, int n) {
        long l2 = ALC10.nalcGetString(l, n);
        if (l2 == 0L) {
            return null;
        }
        ByteBuffer byteBuffer = MemoryUtil.memByteBuffer(l2, Integer.MAX_VALUE);
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        while (true) {
            if (byteBuffer.get() != 0) {
                continue;
            }
            int n3 = byteBuffer.position() - 1;
            if (n3 == n2) break;
            arrayList.add(MemoryUtil.memUTF8(byteBuffer, n3 - n2, n2));
            n2 = byteBuffer.position();
        }
        return arrayList;
    }
}

