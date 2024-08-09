/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.Winsvc;
import com.sun.jna.ptr.IntByReference;

public class W32Service {
    Winsvc.SC_HANDLE _handle = null;

    public W32Service(Winsvc.SC_HANDLE sC_HANDLE) {
        this._handle = sC_HANDLE;
    }

    public void close() {
        if (this._handle != null) {
            if (!Advapi32.INSTANCE.CloseServiceHandle(this._handle)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
            this._handle = null;
        }
    }

    public Winsvc.SERVICE_STATUS_PROCESS queryStatus() {
        IntByReference intByReference = new IntByReference();
        Advapi32.INSTANCE.QueryServiceStatusEx(this._handle, 0, null, 0, intByReference);
        Winsvc.SERVICE_STATUS_PROCESS sERVICE_STATUS_PROCESS = new Winsvc.SERVICE_STATUS_PROCESS(intByReference.getValue());
        if (!Advapi32.INSTANCE.QueryServiceStatusEx(this._handle, 0, sERVICE_STATUS_PROCESS, sERVICE_STATUS_PROCESS.size(), intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return sERVICE_STATUS_PROCESS;
    }

    public void startService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 4) {
            return;
        }
        if (!Advapi32.INSTANCE.StartService(this._handle, 0, null)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 4) {
            throw new RuntimeException("Unable to start the service");
        }
    }

    public void stopService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 1) {
            return;
        }
        if (!Advapi32.INSTANCE.ControlService(this._handle, 1, new Winsvc.SERVICE_STATUS())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 1) {
            throw new RuntimeException("Unable to stop the service");
        }
    }

    public void continueService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 4) {
            return;
        }
        if (!Advapi32.INSTANCE.ControlService(this._handle, 3, new Winsvc.SERVICE_STATUS())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 4) {
            throw new RuntimeException("Unable to continue the service");
        }
    }

    public void pauseService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 7) {
            return;
        }
        if (!Advapi32.INSTANCE.ControlService(this._handle, 2, new Winsvc.SERVICE_STATUS())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 7) {
            throw new RuntimeException("Unable to pause the service");
        }
    }

    public void waitForNonPendingState() {
        Winsvc.SERVICE_STATUS_PROCESS sERVICE_STATUS_PROCESS = this.queryStatus();
        int n = sERVICE_STATUS_PROCESS.dwCheckPoint;
        int n2 = Kernel32.INSTANCE.GetTickCount();
        while (this.isPendingState(sERVICE_STATUS_PROCESS.dwCurrentState)) {
            if (sERVICE_STATUS_PROCESS.dwCheckPoint != n) {
                n = sERVICE_STATUS_PROCESS.dwCheckPoint;
                n2 = Kernel32.INSTANCE.GetTickCount();
            }
            if (Kernel32.INSTANCE.GetTickCount() - n2 > sERVICE_STATUS_PROCESS.dwWaitHint) {
                throw new RuntimeException("Timeout waiting for service to change to a non-pending state.");
            }
            int n3 = sERVICE_STATUS_PROCESS.dwWaitHint / 10;
            if (n3 < 1000) {
                n3 = 1000;
            } else if (n3 > 10000) {
                n3 = 10000;
            }
            try {
                Thread.sleep(n3);
            } catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException);
            }
            sERVICE_STATUS_PROCESS = this.queryStatus();
        }
    }

    private boolean isPendingState(int n) {
        switch (n) {
            case 2: 
            case 3: 
            case 5: 
            case 6: {
                return false;
            }
        }
        return true;
    }

    public Winsvc.SC_HANDLE getHandle() {
        return this._handle;
    }
}

