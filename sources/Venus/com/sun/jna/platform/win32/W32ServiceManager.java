/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.W32Service;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.Winsvc;

public class W32ServiceManager {
    Winsvc.SC_HANDLE _handle = null;
    String _machineName = null;
    String _databaseName = null;

    public W32ServiceManager() {
    }

    public W32ServiceManager(String string, String string2) {
        this._machineName = string;
        this._databaseName = string2;
    }

    public void open(int n) {
        this.close();
        this._handle = Advapi32.INSTANCE.OpenSCManager(this._machineName, this._databaseName, n);
        if (this._handle == null) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
    }

    public void close() {
        if (this._handle != null) {
            if (!Advapi32.INSTANCE.CloseServiceHandle(this._handle)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
            this._handle = null;
        }
    }

    public W32Service openService(String string, int n) {
        Winsvc.SC_HANDLE sC_HANDLE = Advapi32.INSTANCE.OpenService(this._handle, string, n);
        if (sC_HANDLE == null) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return new W32Service(sC_HANDLE);
    }

    public Winsvc.SC_HANDLE getHandle() {
        return this._handle;
    }
}

