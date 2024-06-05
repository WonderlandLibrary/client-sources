package com.sun.jna.platform.mac;

import com.sun.jna.ptr.*;
import java.nio.*;
import com.sun.jna.*;

public interface Carbon
{
    public static final Carbon INSTANCE = Native.loadLibrary("Carbon", Carbon.class);
    public static final int cmdKey = 256;
    public static final int shiftKey = 512;
    public static final int optionKey = 2048;
    public static final int controlKey = 4096;
    
    Pointer GetEventDispatcherTarget();
    
    int InstallEventHandler(final Pointer p0, final EventHandlerProcPtr p1, final int p2, final EventTypeSpec[] p3, final Pointer p4, final PointerByReference p5);
    
    int RegisterEventHotKey(final int p0, final int p1, final EventHotKeyID.ByValue p2, final Pointer p3, final int p4, final PointerByReference p5);
    
    int GetEventParameter(final Pointer p0, final int p1, final int p2, final Pointer p3, final int p4, final IntBuffer p5, final EventHotKeyID p6);
    
    int RemoveEventHandler(final Pointer p0);
    
    int UnregisterEventHotKey(final Pointer p0);
    
    public static class EventTypeSpec extends Structure
    {
        public int eventClass;
        public int eventKind;
    }
    
    public static class EventHotKeyID extends Structure
    {
        public int signature;
        public int id;
        
        public static class ByValue extends EventHotKeyID implements Structure.ByValue
        {
        }
    }
    
    public interface EventHandlerProcPtr extends Callback
    {
        int callback(final Pointer p0, final Pointer p1, final Pointer p2);
    }
}
