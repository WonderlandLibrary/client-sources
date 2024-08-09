/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.win32.StdCallLibrary;

public interface Sspi
extends StdCallLibrary {
    public static final int MAX_TOKEN_SIZE = 12288;
    public static final int SECPKG_CRED_INBOUND = 1;
    public static final int SECPKG_CRED_OUTBOUND = 2;
    public static final int SECURITY_NATIVE_DREP = 16;
    public static final int ISC_REQ_ALLOCATE_MEMORY = 256;
    public static final int ISC_REQ_CONFIDENTIALITY = 16;
    public static final int ISC_REQ_CONNECTION = 2048;
    public static final int ISC_REQ_DELEGATE = 1;
    public static final int ISC_REQ_EXTENDED_ERROR = 16384;
    public static final int ISC_REQ_INTEGRITY = 65536;
    public static final int ISC_REQ_MUTUAL_AUTH = 2;
    public static final int ISC_REQ_REPLAY_DETECT = 4;
    public static final int ISC_REQ_SEQUENCE_DETECT = 8;
    public static final int ISC_REQ_STREAM = 32768;
    public static final int SECBUFFER_VERSION = 0;
    public static final int SECBUFFER_EMPTY = 0;
    public static final int SECBUFFER_DATA = 1;
    public static final int SECBUFFER_TOKEN = 2;

    public static class SecPkgInfo
    extends Structure {
        public NativeLong fCapabilities = new NativeLong(0L);
        public short wVersion = 1;
        public short wRPCID = 0;
        public NativeLong cbMaxToken = new NativeLong(0L);
        public WString Name;
        public WString Comment;

        public static class ByReference
        extends SecPkgInfo
        implements Structure.ByReference {
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PSecPkgInfo
    extends Structure {
        public SecPkgInfo.ByReference pPkgInfo;

        public SecPkgInfo.ByReference[] toArray(int n) {
            return (SecPkgInfo.ByReference[])this.pPkgInfo.toArray(n);
        }

        public Structure[] toArray(int n) {
            return this.toArray(n);
        }

        public static class ByReference
        extends PSecPkgInfo
        implements Structure.ByReference {
            public Structure[] toArray(int n) {
                return super.toArray(n);
            }
        }
    }

    public static class TimeStamp
    extends SECURITY_INTEGER {
    }

    public static class SECURITY_INTEGER
    extends Structure {
        public NativeLong dwLower = new NativeLong(0L);
        public NativeLong dwUpper = new NativeLong(0L);
    }

    public static class SecBufferDesc
    extends Structure {
        public NativeLong ulVersion = new NativeLong(0L);
        public NativeLong cBuffers = new NativeLong(1L);
        public SecBuffer.ByReference[] pBuffers;

        public SecBufferDesc() {
            SecBuffer.ByReference byReference = new SecBuffer.ByReference();
            this.pBuffers = (SecBuffer.ByReference[])byReference.toArray(1);
            this.allocateMemory();
        }

        public SecBufferDesc(int n, byte[] byArray) {
            SecBuffer.ByReference byReference = new SecBuffer.ByReference(n, byArray);
            this.pBuffers = (SecBuffer.ByReference[])byReference.toArray(1);
            this.allocateMemory();
        }

        public SecBufferDesc(int n, int n2) {
            SecBuffer.ByReference byReference = new SecBuffer.ByReference(n, n2);
            this.pBuffers = (SecBuffer.ByReference[])byReference.toArray(1);
            this.allocateMemory();
        }

        public byte[] getBytes() {
            if (this.pBuffers == null || this.cBuffers == null) {
                throw new RuntimeException("pBuffers | cBuffers");
            }
            if (this.cBuffers.intValue() == 1) {
                return this.pBuffers[0].getBytes();
            }
            throw new RuntimeException("cBuffers > 1");
        }
    }

    public static class SecBuffer
    extends Structure {
        public NativeLong cbBuffer;
        public NativeLong BufferType;
        public Pointer pvBuffer;

        public SecBuffer() {
            this.cbBuffer = new NativeLong(0L);
            this.pvBuffer = null;
            this.BufferType = new NativeLong(0L);
        }

        public SecBuffer(int n, int n2) {
            this.cbBuffer = new NativeLong((long)n2);
            this.pvBuffer = new Memory(n2);
            this.BufferType = new NativeLong((long)n);
            this.allocateMemory();
        }

        public SecBuffer(int n, byte[] byArray) {
            this.cbBuffer = new NativeLong((long)byArray.length);
            this.pvBuffer = new Memory(byArray.length);
            this.pvBuffer.write(0L, byArray, 0, byArray.length);
            this.BufferType = new NativeLong((long)n);
            this.allocateMemory();
        }

        public byte[] getBytes() {
            return this.pvBuffer.getByteArray(0L, this.cbBuffer.intValue());
        }

        public static class ByReference
        extends SecBuffer
        implements Structure.ByReference {
            public ByReference() {
            }

            public ByReference(int n, int n2) {
                super(n, n2);
            }

            public ByReference(int n, byte[] byArray) {
                super(n, byArray);
            }

            public byte[] getBytes() {
                return super.getBytes();
            }
        }
    }

    public static class CtxtHandle
    extends SecHandle {
    }

    public static class CredHandle
    extends SecHandle {
    }

    public static class PSecHandle
    extends Structure {
        public SecHandle.ByReference secHandle;

        public PSecHandle() {
        }

        public PSecHandle(SecHandle secHandle) {
            super(secHandle.getPointer());
            this.read();
        }

        public static class ByReference
        extends PSecHandle
        implements Structure.ByReference {
        }
    }

    public static class SecHandle
    extends Structure {
        public Pointer dwLower = null;
        public Pointer dwUpper = null;

        public boolean isNull() {
            return this.dwLower == null && this.dwUpper == null;
        }

        public static class ByReference
        extends SecHandle
        implements Structure.ByReference {
        }
    }
}

