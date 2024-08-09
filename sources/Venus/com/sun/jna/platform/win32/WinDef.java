/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import java.awt.Rectangle;

public interface WinDef
extends StdCallLibrary {
    public static final int MAX_PATH = 260;

    public static class DWORDLONG
    extends IntegerType {
        public DWORDLONG() {
            this(0L);
        }

        public DWORDLONG(long l) {
            super(8, l);
        }
    }

    public static class ULONGLONG
    extends IntegerType {
        public ULONGLONG() {
            this(0L);
        }

        public ULONGLONG(long l) {
            super(8, l);
        }
    }

    public static class RECT
    extends Structure {
        public int left;
        public int top;
        public int right;
        public int bottom;

        public Rectangle toRectangle() {
            return new Rectangle(this.left, this.top, this.right - this.left, this.bottom - this.top);
        }

        public String toString() {
            return "[(" + this.left + "," + this.top + ")(" + this.right + "," + this.bottom + ")]";
        }
    }

    public static class WPARAM
    extends UINT_PTR {
        public WPARAM() {
            this(0L);
        }

        public WPARAM(long l) {
            super(l);
        }
    }

    public static class UINT_PTR
    extends IntegerType {
        public UINT_PTR() {
            super(Pointer.SIZE);
        }

        public UINT_PTR(long l) {
            super(Pointer.SIZE, l);
        }

        public Pointer toPointer() {
            return Pointer.createConstant(this.longValue());
        }
    }

    public static class LRESULT
    extends BaseTSD.LONG_PTR {
        public LRESULT() {
            this(0L);
        }

        public LRESULT(long l) {
            super(l);
        }
    }

    public static class LPARAM
    extends BaseTSD.LONG_PTR {
        public LPARAM() {
            this(0L);
        }

        public LPARAM(long l) {
            super(l);
        }
    }

    public static class HFONT
    extends WinNT.HANDLE {
        public HFONT() {
        }

        public HFONT(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HMODULE
    extends HINSTANCE {
    }

    public static class HINSTANCE
    extends WinNT.HANDLE {
    }

    public static class HWND
    extends WinNT.HANDLE {
        public HWND() {
        }

        public HWND(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HRGN
    extends WinNT.HANDLE {
        public HRGN() {
        }

        public HRGN(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HBITMAP
    extends WinNT.HANDLE {
        public HBITMAP() {
        }

        public HBITMAP(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HPALETTE
    extends WinNT.HANDLE {
        public HPALETTE() {
        }

        public HPALETTE(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HRSRC
    extends WinNT.HANDLE {
        public HRSRC() {
        }

        public HRSRC(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HPEN
    extends WinNT.HANDLE {
        public HPEN() {
        }

        public HPEN(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HMENU
    extends WinNT.HANDLE {
        public HMENU() {
        }

        public HMENU(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HCURSOR
    extends HICON {
        public HCURSOR() {
        }

        public HCURSOR(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HICON
    extends WinNT.HANDLE {
        public HICON() {
        }

        public HICON(Pointer pointer) {
            super(pointer);
        }
    }

    public static class HDC
    extends WinNT.HANDLE {
        public HDC() {
        }

        public HDC(Pointer pointer) {
            super(pointer);
        }
    }

    public static class LONG
    extends IntegerType {
        public LONG() {
            this(0L);
        }

        public LONG(long l) {
            super(Native.LONG_SIZE, l);
        }
    }

    public static class DWORD
    extends IntegerType {
        public DWORD() {
            this(0L);
        }

        public DWORD(long l) {
            super(4, l, true);
        }

        public WORD getLow() {
            return new WORD(this.longValue() & 0xFFL);
        }

        public WORD getHigh() {
            return new WORD(this.longValue() >> 16 & 0xFFL);
        }
    }

    public static class WORD
    extends IntegerType {
        public WORD() {
            this(0L);
        }

        public WORD(long l) {
            super(2, l);
        }
    }
}

