/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface WinReg
extends StdCallLibrary {
    public static final HKEY HKEY_CLASSES_ROOT = new HKEY(Integer.MIN_VALUE);
    public static final HKEY HKEY_CURRENT_USER = new HKEY(-2147483647);
    public static final HKEY HKEY_LOCAL_MACHINE = new HKEY(-2147483646);
    public static final HKEY HKEY_USERS = new HKEY(-2147483645);
    public static final HKEY HKEY_PERFORMANCE_DATA = new HKEY(-2147483644);
    public static final HKEY HKEY_PERFORMANCE_TEXT = new HKEY(-2147483568);
    public static final HKEY HKEY_PERFORMANCE_NLSTEXT = new HKEY(-2147483552);
    public static final HKEY HKEY_CURRENT_CONFIG = new HKEY(-2147483643);
    public static final HKEY HKEY_DYN_DATA = new HKEY(-2147483642);

    public static class HKEYByReference
    extends ByReference {
        public HKEYByReference() {
            this((HKEY)null);
        }

        public HKEYByReference(HKEY hKEY) {
            super(Pointer.SIZE);
            this.setValue(hKEY);
        }

        public void setValue(HKEY hKEY) {
            this.getPointer().setPointer(0L, hKEY != null ? hKEY.getPointer() : null);
        }

        public HKEY getValue() {
            Pointer pointer = this.getPointer().getPointer(0L);
            if (pointer == null) {
                return null;
            }
            if (WinBase.INVALID_HANDLE_VALUE.getPointer().equals(pointer)) {
                return (HKEY)WinBase.INVALID_HANDLE_VALUE;
            }
            HKEY hKEY = new HKEY();
            hKEY.setPointer(pointer);
            return hKEY;
        }
    }

    public static class HKEY
    extends WinNT.HANDLE {
        public HKEY() {
        }

        public HKEY(Pointer pointer) {
            super(pointer);
        }

        public HKEY(int n) {
            super(new Pointer(n));
        }
    }
}

