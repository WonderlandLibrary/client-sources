/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.libc;

import org.lwjgl.system.Library;

public class LibCErrno {
    public static final int EPERM = 1;
    public static final int ENOENT = 2;
    public static final int ESRCH = 3;
    public static final int EINTR = 4;
    public static final int EIO = 5;
    public static final int ENXIO = 6;
    public static final int E2BIG = 7;
    public static final int ENOEXEC = 8;
    public static final int EBADF = 9;
    public static final int ECHILD = 10;
    public static final int EAGAIN = 11;
    public static final int ENOMEM = 12;
    public static final int EACCES = 13;
    public static final int EFAULT = 14;
    public static final int EBUSY = 16;
    public static final int EEXIST = 17;
    public static final int EXDEV = 18;
    public static final int ENODEV = 19;
    public static final int ENOTDIR = 20;
    public static final int EISDIR = 21;
    public static final int EINVAL = 22;
    public static final int ENFILE = 23;
    public static final int EMFILE = 24;
    public static final int ENOTTY = 25;
    public static final int EFBIG = 27;
    public static final int ENOSPC = 28;
    public static final int ESPIPE = 29;
    public static final int EROFS = 30;
    public static final int EMLINK = 31;
    public static final int EPIPE = 32;
    public static final int EDOM = 33;
    public static final int ERANGE = 34;
    public static final int EDEADLK = 36;
    public static final int EDEADLOCK = 36;
    public static final int ENAMETOOLONG = 38;
    public static final int ENOLCK = 39;
    public static final int ENOSYS = 40;
    public static final int ENOTEMPTY = 41;
    public static final int EILSEQ = 42;
    public static final int STRUNCATE = 80;

    protected LibCErrno() {
        throw new UnsupportedOperationException();
    }

    public static native int errno();

    public static native int getErrno();

    static {
        Library.initialize();
    }
}

