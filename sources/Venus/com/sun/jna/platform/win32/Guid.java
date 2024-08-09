/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface Guid {

    public static class GUID
    extends Structure {
        public int Data1;
        public short Data2;
        public short Data3;
        public byte[] Data4 = new byte[8];

        public GUID() {
        }

        public GUID(Pointer pointer) {
            super(pointer);
            this.read();
        }

        public GUID(byte[] byArray) {
            if (byArray.length != 16) {
                throw new IllegalArgumentException("Invalid data length: " + byArray.length);
            }
            long l = byArray[3] & 0xFF;
            l <<= 8;
            l |= (long)(byArray[2] & 0xFF);
            l <<= 8;
            l |= (long)(byArray[1] & 0xFF);
            l <<= 8;
            this.Data1 = (int)(l |= (long)(byArray[0] & 0xFF));
            int n = byArray[5] & 0xFF;
            n <<= 8;
            this.Data2 = (short)(n |= byArray[4] & 0xFF);
            int n2 = byArray[7] & 0xFF;
            n2 <<= 8;
            this.Data3 = (short)(n2 |= byArray[6] & 0xFF);
            this.Data4[0] = byArray[8];
            this.Data4[1] = byArray[9];
            this.Data4[2] = byArray[10];
            this.Data4[3] = byArray[11];
            this.Data4[4] = byArray[12];
            this.Data4[5] = byArray[13];
            this.Data4[6] = byArray[14];
            this.Data4[7] = byArray[15];
        }

        public static class ByReference
        extends GUID
        implements Structure.ByReference {
            public ByReference() {
            }

            public ByReference(GUID gUID) {
                super(gUID.getPointer());
                this.Data1 = gUID.Data1;
                this.Data2 = gUID.Data2;
                this.Data3 = gUID.Data3;
                this.Data4 = gUID.Data4;
            }

            public ByReference(Pointer pointer) {
                super(pointer);
            }
        }
    }
}

