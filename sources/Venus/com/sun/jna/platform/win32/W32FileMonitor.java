/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.platform.FileMonitor;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class W32FileMonitor
extends FileMonitor {
    private static final int BUFFER_SIZE = 4096;
    private Thread watcher;
    private WinNT.HANDLE port;
    private final Map<File, FileInfo> fileMap = new HashMap<File, FileInfo>();
    private final Map<WinNT.HANDLE, FileInfo> handleMap = new HashMap<WinNT.HANDLE, FileInfo>();
    private boolean disposing = false;
    private static int watcherThreadID;

    private void handleChanges(FileInfo fileInfo) throws IOException {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        WinNT.FILE_NOTIFY_INFORMATION fILE_NOTIFY_INFORMATION = fileInfo.info;
        fILE_NOTIFY_INFORMATION.read();
        do {
            FileMonitor.FileEvent fileEvent = null;
            File file = new File(fileInfo.file, fILE_NOTIFY_INFORMATION.getFilename());
            switch (fILE_NOTIFY_INFORMATION.Action) {
                case 0: {
                    break;
                }
                case 3: {
                    fileEvent = new FileMonitor.FileEvent(this, file, 4);
                    break;
                }
                case 1: {
                    fileEvent = new FileMonitor.FileEvent(this, file, 1);
                    break;
                }
                case 2: {
                    fileEvent = new FileMonitor.FileEvent(this, file, 2);
                    break;
                }
                case 4: {
                    fileEvent = new FileMonitor.FileEvent(this, file, 16);
                    break;
                }
                case 5: {
                    fileEvent = new FileMonitor.FileEvent(this, file, 32);
                    break;
                }
                default: {
                    System.err.println("Unrecognized file action '" + fILE_NOTIFY_INFORMATION.Action + "'");
                }
            }
            if (fileEvent == null) continue;
            this.notify(fileEvent);
        } while ((fILE_NOTIFY_INFORMATION = fILE_NOTIFY_INFORMATION.next()) != null);
        if (!fileInfo.file.exists()) {
            this.unwatch(fileInfo.file);
            return;
        }
        if (!kernel32.ReadDirectoryChangesW(fileInfo.handle, fileInfo.info, fileInfo.info.size(), fileInfo.recursive, fileInfo.notifyMask, fileInfo.infoLength, fileInfo.overlapped, null) && !this.disposing) {
            int n = kernel32.GetLastError();
            throw new IOException("ReadDirectoryChangesW failed on " + fileInfo.file + ": '" + Kernel32Util.formatMessageFromLastErrorCode(n) + "' (" + n + ")");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FileInfo waitForChange() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        IntByReference intByReference = new IntByReference();
        BaseTSD.ULONG_PTRByReference uLONG_PTRByReference = new BaseTSD.ULONG_PTRByReference();
        PointerByReference pointerByReference = new PointerByReference();
        kernel32.GetQueuedCompletionStatus(this.port, intByReference, uLONG_PTRByReference, pointerByReference, -1);
        W32FileMonitor w32FileMonitor = this;
        synchronized (w32FileMonitor) {
            return this.handleMap.get(uLONG_PTRByReference.getValue());
        }
    }

    private int convertMask(int n) {
        int n2 = 0;
        if ((n & 1) != 0) {
            n2 |= 0x40;
        }
        if ((n & 2) != 0) {
            n2 |= 3;
        }
        if ((n & 4) != 0) {
            n2 |= 0x10;
        }
        if ((n & 0x30) != 0) {
            n2 |= 3;
        }
        if ((n & 0x40) != 0) {
            n2 |= 8;
        }
        if ((n & 8) != 0) {
            n2 |= 0x20;
        }
        if ((n & 0x80) != 0) {
            n2 |= 4;
        }
        if ((n & 0x100) != 0) {
            n2 |= 0x100;
        }
        return n2;
    }

    protected synchronized void watch(File file, int n, boolean bl) throws IOException {
        File file2 = file;
        if (!file2.isDirectory()) {
            bl = false;
            file2 = file.getParentFile();
        }
        while (file2 != null && !file2.exists()) {
            bl = true;
            file2 = file2.getParentFile();
        }
        if (file2 == null) {
            throw new FileNotFoundException("No ancestor found for " + file);
        }
        Kernel32 kernel32 = Kernel32.INSTANCE;
        int n2 = 7;
        int n3 = 0x42000000;
        WinNT.HANDLE hANDLE = kernel32.CreateFile(file.getAbsolutePath(), 1, n2, null, 3, n3, null);
        if (WinBase.INVALID_HANDLE_VALUE.equals(hANDLE)) {
            throw new IOException("Unable to open " + file + " (" + kernel32.GetLastError() + ")");
        }
        int n4 = this.convertMask(n);
        FileInfo fileInfo = new FileInfo(this, file, hANDLE, n4, bl);
        this.fileMap.put(file, fileInfo);
        this.handleMap.put(hANDLE, fileInfo);
        this.port = kernel32.CreateIoCompletionPort(hANDLE, this.port, hANDLE.getPointer(), 0);
        if (WinBase.INVALID_HANDLE_VALUE.equals(this.port)) {
            throw new IOException("Unable to create/use I/O Completion port for " + file + " (" + kernel32.GetLastError() + ")");
        }
        if (!kernel32.ReadDirectoryChangesW(hANDLE, fileInfo.info, fileInfo.info.size(), bl, n4, fileInfo.infoLength, fileInfo.overlapped, null)) {
            int n5 = kernel32.GetLastError();
            throw new IOException("ReadDirectoryChangesW failed on " + fileInfo.file + ", handle " + hANDLE + ": '" + Kernel32Util.formatMessageFromLastErrorCode(n5) + "' (" + n5 + ")");
        }
        if (this.watcher == null) {
            this.watcher = new Thread(this, "W32 File Monitor-" + watcherThreadID++){
                final W32FileMonitor this$0;
                {
                    this.this$0 = w32FileMonitor;
                    super(string);
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void run() {
                    while (true) {
                        FileInfo fileInfo;
                        if ((fileInfo = W32FileMonitor.access$000(this.this$0)) == null) {
                            W32FileMonitor w32FileMonitor = this.this$0;
                            synchronized (w32FileMonitor) {
                                if (W32FileMonitor.access$100(this.this$0).isEmpty()) {
                                    W32FileMonitor.access$202(this.this$0, null);
                                    break;
                                }
                            }
                        }
                        try {
                            W32FileMonitor.access$300(this.this$0, fileInfo);
                        } catch (IOException iOException) {
                            iOException.printStackTrace();
                        }
                    }
                }
            };
            this.watcher.setDaemon(false);
            this.watcher.start();
        }
    }

    protected synchronized void unwatch(File file) {
        FileInfo fileInfo = this.fileMap.remove(file);
        if (fileInfo != null) {
            this.handleMap.remove(fileInfo.handle);
            Kernel32 kernel32 = Kernel32.INSTANCE;
            kernel32.CloseHandle(fileInfo.handle);
        }
    }

    public synchronized void dispose() {
        this.disposing = true;
        int n = 0;
        Object object = this.fileMap.keySet().toArray();
        while (!this.fileMap.isEmpty()) {
            this.unwatch((File)object[n++]);
        }
        object = Kernel32.INSTANCE;
        object.PostQueuedCompletionStatus(this.port, 0, null, null);
        object.CloseHandle(this.port);
        this.port = null;
        this.watcher = null;
    }

    static FileInfo access$000(W32FileMonitor w32FileMonitor) {
        return w32FileMonitor.waitForChange();
    }

    static Map access$100(W32FileMonitor w32FileMonitor) {
        return w32FileMonitor.fileMap;
    }

    static Thread access$202(W32FileMonitor w32FileMonitor, Thread thread2) {
        w32FileMonitor.watcher = thread2;
        return w32FileMonitor.watcher;
    }

    static void access$300(W32FileMonitor w32FileMonitor, FileInfo fileInfo) throws IOException {
        w32FileMonitor.handleChanges(fileInfo);
    }

    private class FileInfo {
        public final File file;
        public final WinNT.HANDLE handle;
        public final int notifyMask;
        public final boolean recursive;
        public final WinNT.FILE_NOTIFY_INFORMATION info;
        public final IntByReference infoLength;
        public final WinBase.OVERLAPPED overlapped;
        final W32FileMonitor this$0;

        public FileInfo(W32FileMonitor w32FileMonitor, File file, WinNT.HANDLE hANDLE, int n, boolean bl) {
            this.this$0 = w32FileMonitor;
            this.info = new WinNT.FILE_NOTIFY_INFORMATION(4096);
            this.infoLength = new IntByReference();
            this.overlapped = new WinBase.OVERLAPPED();
            this.file = file;
            this.handle = hANDLE;
            this.notifyMask = n;
            this.recursive = bl;
        }
    }
}

