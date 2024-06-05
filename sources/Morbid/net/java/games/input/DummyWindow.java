package net.java.games.input;

import java.io.*;

final class DummyWindow
{
    private final long hwnd_address;
    
    public DummyWindow() throws IOException {
        this.hwnd_address = createWindow();
    }
    
    private static final native long createWindow() throws IOException;
    
    public final void destroy() throws IOException {
        nDestroy(this.hwnd_address);
    }
    
    private static final native void nDestroy(final long p0) throws IOException;
    
    public final long getHwnd() {
        return this.hwnd_address;
    }
}
