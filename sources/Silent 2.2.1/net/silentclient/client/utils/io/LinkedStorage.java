package net.silentclient.client.utils.io;

import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;

public class LinkedStorage {
    private static OBJ head;

    public static void put(Object val) {
        OBJ newObj = new OBJ(val);
        OBJ it = tail(head);
        if(it == null) head = newObj;
        else it.next = newObj;
    }

    private static OBJ tail(OBJ head) {
        OBJ it;
        for (it = head; it != null && it.next != null; it = it.next);
        return it;
    }

    public static void free() {
        if(head != null) {
            OBJ it;
            for (it = head; it.next != null; it = it.next) {
                if(it.value instanceof Buffer) {
                    MemoryUtil.memFree((Buffer) it.value);
                }
            }
            head = null;
        }
    }
    private static class OBJ {
        OBJ next;
        Object value;

        OBJ(Object v) {
            value = v;
        }
    }
}