/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface WinCrypt
extends StdCallLibrary {
    public static final int CRYPTPROTECT_PROMPT_ON_UNPROTECT = 1;
    public static final int CRYPTPROTECT_PROMPT_ON_PROTECT = 2;
    public static final int CRYPTPROTECT_PROMPT_RESERVED = 4;
    public static final int CRYPTPROTECT_PROMPT_STRONG = 8;
    public static final int CRYPTPROTECT_PROMPT_REQUIRE_STRONG = 16;
    public static final int CRYPTPROTECT_UI_FORBIDDEN = 1;
    public static final int CRYPTPROTECT_LOCAL_MACHINE = 4;
    public static final int CRYPTPROTECT_CRED_SYNC = 8;
    public static final int CRYPTPROTECT_AUDIT = 16;
    public static final int CRYPTPROTECT_NO_RECOVERY = 32;
    public static final int CRYPTPROTECT_VERIFY_PROTECTION = 64;
    public static final int CRYPTPROTECT_CRED_REGENERATE = 128;

    public static class CRYPTPROTECT_PROMPTSTRUCT
    extends Structure {
        public int cbSize;
        public int dwPromptFlags;
        public WinDef.HWND hwndApp;
        public String szPrompt;

        public CRYPTPROTECT_PROMPTSTRUCT() {
        }

        public CRYPTPROTECT_PROMPTSTRUCT(Pointer pointer) {
            super(pointer);
            this.read();
        }
    }

    public static class DATA_BLOB
    extends Structure {
        public int cbData;
        public Pointer pbData;

        public DATA_BLOB() {
        }

        public DATA_BLOB(Pointer pointer) {
            super(pointer);
            this.read();
        }

        public DATA_BLOB(byte[] byArray) {
            this.pbData = new Memory(byArray.length);
            this.pbData.write(0L, byArray, 0, byArray.length);
            this.cbData = byArray.length;
            this.allocateMemory();
        }

        public DATA_BLOB(String string) {
            this(Native.toByteArray(string));
        }

        public byte[] getData() {
            return this.pbData == null ? null : this.pbData.getByteArray(0L, this.cbData);
        }
    }
}

