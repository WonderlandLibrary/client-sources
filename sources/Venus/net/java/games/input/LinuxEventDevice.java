/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.LinuxAbsInfo;
import net.java.games.input.LinuxAxisDescriptor;
import net.java.games.input.LinuxComponent;
import net.java.games.input.LinuxDevice;
import net.java.games.input.LinuxDeviceTask;
import net.java.games.input.LinuxEnvironmentPlugin;
import net.java.games.input.LinuxEvent;
import net.java.games.input.LinuxEventComponent;
import net.java.games.input.LinuxInputID;
import net.java.games.input.LinuxNativeTypesMap;
import net.java.games.input.LinuxRumbleFF;
import net.java.games.input.Rumbler;

final class LinuxEventDevice
implements LinuxDevice {
    private final Map component_map = new HashMap();
    private final Rumbler[] rumblers;
    private final long fd;
    private final String name;
    private final LinuxInputID input_id;
    private final List components;
    private final Controller.Type type;
    private boolean closed;
    private final byte[] key_states = new byte[64];
    static Class class$net$java$games$input$Component$Identifier$Axis;
    static Class class$net$java$games$input$Component$Identifier$Key;

    public LinuxEventDevice(String string) throws IOException {
        long l;
        boolean bl = true;
        try {
            l = LinuxEventDevice.nOpen(string, true);
        } catch (IOException iOException) {
            l = LinuxEventDevice.nOpen(string, false);
            bl = false;
        }
        this.fd = l;
        try {
            this.name = this.getDeviceName();
            this.input_id = this.getDeviceInputID();
            this.components = this.getDeviceComponents();
            this.rumblers = bl ? this.enumerateRumblers() : new Rumbler[0];
            this.type = this.guessType();
        } catch (IOException iOException) {
            this.close();
            throw iOException;
        }
    }

    private static final native long nOpen(String var0, boolean var1) throws IOException;

    public final Controller.Type getType() {
        return this.type;
    }

    private static final int countComponents(List list, Class clazz, boolean bl) {
        int n = 0;
        for (int i = 0; i < list.size(); ++i) {
            LinuxEventComponent linuxEventComponent = (LinuxEventComponent)list.get(i);
            if (!clazz.isInstance(linuxEventComponent.getIdentifier()) || bl != linuxEventComponent.isRelative()) continue;
            ++n;
        }
        return n;
    }

    private final Controller.Type guessType() throws IOException {
        Controller.Type type = this.guessTypeFromUsages();
        if (type == Controller.Type.UNKNOWN) {
            return this.guessTypeFromComponents();
        }
        return type;
    }

    private final Controller.Type guessTypeFromUsages() throws IOException {
        byte[] byArray = this.getDeviceUsageBits();
        if (LinuxEventDevice.isBitSet(byArray, 0)) {
            return Controller.Type.MOUSE;
        }
        if (LinuxEventDevice.isBitSet(byArray, 3)) {
            return Controller.Type.KEYBOARD;
        }
        if (LinuxEventDevice.isBitSet(byArray, 2)) {
            return Controller.Type.GAMEPAD;
        }
        if (LinuxEventDevice.isBitSet(byArray, 1)) {
            return Controller.Type.STICK;
        }
        return Controller.Type.UNKNOWN;
    }

    private final Controller.Type guessTypeFromComponents() throws IOException {
        List list = this.getComponents();
        if (list.size() == 0) {
            return Controller.Type.UNKNOWN;
        }
        int n = LinuxEventDevice.countComponents(list, class$net$java$games$input$Component$Identifier$Axis == null ? (class$net$java$games$input$Component$Identifier$Axis = LinuxEventDevice.class$("net.java.games.input.Component$Identifier$Axis")) : class$net$java$games$input$Component$Identifier$Axis, true);
        int n2 = LinuxEventDevice.countComponents(list, class$net$java$games$input$Component$Identifier$Axis == null ? (class$net$java$games$input$Component$Identifier$Axis = LinuxEventDevice.class$("net.java.games.input.Component$Identifier$Axis")) : class$net$java$games$input$Component$Identifier$Axis, false);
        int n3 = LinuxEventDevice.countComponents(list, class$net$java$games$input$Component$Identifier$Key == null ? (class$net$java$games$input$Component$Identifier$Key = LinuxEventDevice.class$("net.java.games.input.Component$Identifier$Key")) : class$net$java$games$input$Component$Identifier$Key, false);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        if (this.name.toLowerCase().indexOf("mouse") != -1) {
            ++n4;
        }
        if (this.name.toLowerCase().indexOf("keyboard") != -1) {
            ++n5;
        }
        if (this.name.toLowerCase().indexOf("joystick") != -1) {
            ++n6;
        }
        if (this.name.toLowerCase().indexOf("gamepad") != -1) {
            ++n7;
        }
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        for (int i = 0; i < list.size(); ++i) {
            LinuxEventComponent linuxEventComponent = (LinuxEventComponent)list.get(i);
            if (linuxEventComponent.getButtonTrait() == Controller.Type.MOUSE) {
                ++n9;
                continue;
            }
            if (linuxEventComponent.getButtonTrait() == Controller.Type.KEYBOARD) {
                ++n8;
                continue;
            }
            if (linuxEventComponent.getButtonTrait() == Controller.Type.GAMEPAD) {
                ++n11;
                continue;
            }
            if (linuxEventComponent.getButtonTrait() != Controller.Type.STICK) continue;
            ++n10;
        }
        if (n9 >= n8 && n9 >= n10 && n9 >= n11) {
            ++n4;
        } else if (n8 >= n9 && n8 >= n10 && n8 >= n11) {
            ++n5;
        } else if (n10 >= n8 && n10 >= n9 && n10 >= n11) {
            ++n6;
        } else if (n11 >= n8 && n11 >= n9 && n11 >= n10) {
            ++n7;
        }
        if (n >= 2) {
            ++n4;
        }
        if (n2 >= 2) {
            ++n6;
            ++n7;
        }
        if (n4 >= n5 && n4 >= n6 && n4 >= n7) {
            return Controller.Type.MOUSE;
        }
        if (n5 >= n4 && n5 >= n6 && n5 >= n7) {
            return Controller.Type.KEYBOARD;
        }
        if (n6 >= n4 && n6 >= n5 && n6 >= n7) {
            return Controller.Type.STICK;
        }
        if (n7 >= n4 && n7 >= n5 && n7 >= n6) {
            return Controller.Type.GAMEPAD;
        }
        return null;
    }

    private final Rumbler[] enumerateRumblers() {
        ArrayList<LinuxRumbleFF> arrayList = new ArrayList<LinuxRumbleFF>();
        try {
            int n = this.getNumEffects();
            if (n <= 0) {
                return arrayList.toArray(new Rumbler[0]);
            }
            byte[] byArray = this.getForceFeedbackBits();
            if (LinuxEventDevice.isBitSet(byArray, 80) && n > arrayList.size()) {
                arrayList.add(new LinuxRumbleFF(this));
            }
        } catch (IOException iOException) {
            LinuxEnvironmentPlugin.logln("Failed to enumerate rumblers: " + iOException.getMessage());
        }
        return arrayList.toArray(new Rumbler[0]);
    }

    public final Rumbler[] getRumblers() {
        return this.rumblers;
    }

    public final synchronized int uploadRumbleEffect(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) throws IOException {
        this.checkClosed();
        return LinuxEventDevice.nUploadRumbleEffect(this.fd, n, n3, n2, n4, n5, n6, n7, n8);
    }

    private static final native int nUploadRumbleEffect(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) throws IOException;

    public final synchronized int uploadConstantEffect(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) throws IOException {
        this.checkClosed();
        return LinuxEventDevice.nUploadConstantEffect(this.fd, n, n3, n2, n4, n5, n6, n7, n8, n9, n10, n11);
    }

    private static final native int nUploadConstantEffect(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) throws IOException;

    final void eraseEffect(int n) throws IOException {
        LinuxEventDevice.nEraseEffect(this.fd, n);
    }

    private static final native void nEraseEffect(long var0, int var2) throws IOException;

    public final synchronized void writeEvent(int n, int n2, int n3) throws IOException {
        this.checkClosed();
        LinuxEventDevice.nWriteEvent(this.fd, n, n2, n3);
    }

    private static final native void nWriteEvent(long var0, int var2, int var3, int var4) throws IOException;

    public final void registerComponent(LinuxAxisDescriptor linuxAxisDescriptor, LinuxComponent linuxComponent) {
        this.component_map.put(linuxAxisDescriptor, linuxComponent);
    }

    public final LinuxComponent mapDescriptor(LinuxAxisDescriptor linuxAxisDescriptor) {
        return (LinuxComponent)this.component_map.get(linuxAxisDescriptor);
    }

    public final Controller.PortType getPortType() throws IOException {
        return this.input_id.getPortType();
    }

    public final LinuxInputID getInputID() {
        return this.input_id;
    }

    private final LinuxInputID getDeviceInputID() throws IOException {
        return LinuxEventDevice.nGetInputID(this.fd);
    }

    private static final native LinuxInputID nGetInputID(long var0) throws IOException;

    public final int getNumEffects() throws IOException {
        return LinuxEventDevice.nGetNumEffects(this.fd);
    }

    private static final native int nGetNumEffects(long var0) throws IOException;

    private final int getVersion() throws IOException {
        return LinuxEventDevice.nGetVersion(this.fd);
    }

    private static final native int nGetVersion(long var0) throws IOException;

    public final synchronized boolean getNextEvent(LinuxEvent linuxEvent) throws IOException {
        this.checkClosed();
        return LinuxEventDevice.nGetNextEvent(this.fd, linuxEvent);
    }

    private static final native boolean nGetNextEvent(long var0, LinuxEvent var2) throws IOException;

    public final synchronized void getAbsInfo(int n, LinuxAbsInfo linuxAbsInfo) throws IOException {
        this.checkClosed();
        LinuxEventDevice.nGetAbsInfo(this.fd, n, linuxAbsInfo);
    }

    private static final native void nGetAbsInfo(long var0, int var2, LinuxAbsInfo var3) throws IOException;

    private final void addKeys(List list) throws IOException {
        byte[] byArray = this.getKeysBits();
        for (int i = 0; i < byArray.length * 8; ++i) {
            if (!LinuxEventDevice.isBitSet(byArray, i)) continue;
            Component.Identifier identifier = LinuxNativeTypesMap.getButtonID(i);
            list.add(new LinuxEventComponent(this, identifier, false, 1, i));
        }
    }

    private final void addAbsoluteAxes(List list) throws IOException {
        byte[] byArray = this.getAbsoluteAxesBits();
        for (int i = 0; i < byArray.length * 8; ++i) {
            if (!LinuxEventDevice.isBitSet(byArray, i)) continue;
            Component.Identifier identifier = LinuxNativeTypesMap.getAbsAxisID(i);
            list.add(new LinuxEventComponent(this, identifier, false, 3, i));
        }
    }

    private final void addRelativeAxes(List list) throws IOException {
        byte[] byArray = this.getRelativeAxesBits();
        for (int i = 0; i < byArray.length * 8; ++i) {
            if (!LinuxEventDevice.isBitSet(byArray, i)) continue;
            Component.Identifier identifier = LinuxNativeTypesMap.getRelAxisID(i);
            list.add(new LinuxEventComponent(this, identifier, true, 2, i));
        }
    }

    public final List getComponents() {
        return this.components;
    }

    private final List getDeviceComponents() throws IOException {
        ArrayList arrayList = new ArrayList();
        byte[] byArray = this.getEventTypeBits();
        if (LinuxEventDevice.isBitSet(byArray, 1)) {
            this.addKeys(arrayList);
        }
        if (LinuxEventDevice.isBitSet(byArray, 3)) {
            this.addAbsoluteAxes(arrayList);
        }
        if (LinuxEventDevice.isBitSet(byArray, 2)) {
            this.addRelativeAxes(arrayList);
        }
        return arrayList;
    }

    private final byte[] getForceFeedbackBits() throws IOException {
        byte[] byArray = new byte[16];
        LinuxEventDevice.nGetBits(this.fd, 21, byArray);
        return byArray;
    }

    private final byte[] getKeysBits() throws IOException {
        byte[] byArray = new byte[64];
        LinuxEventDevice.nGetBits(this.fd, 1, byArray);
        return byArray;
    }

    private final byte[] getAbsoluteAxesBits() throws IOException {
        byte[] byArray = new byte[8];
        LinuxEventDevice.nGetBits(this.fd, 3, byArray);
        return byArray;
    }

    private final byte[] getRelativeAxesBits() throws IOException {
        byte[] byArray = new byte[2];
        LinuxEventDevice.nGetBits(this.fd, 2, byArray);
        return byArray;
    }

    private final byte[] getEventTypeBits() throws IOException {
        byte[] byArray = new byte[4];
        LinuxEventDevice.nGetBits(this.fd, 0, byArray);
        return byArray;
    }

    private static final native void nGetBits(long var0, int var2, byte[] var3) throws IOException;

    private final byte[] getDeviceUsageBits() throws IOException {
        byte[] byArray = new byte[2];
        if (this.getVersion() >= 65537) {
            LinuxEventDevice.nGetDeviceUsageBits(this.fd, byArray);
        }
        return byArray;
    }

    private static final native void nGetDeviceUsageBits(long var0, byte[] var2) throws IOException;

    public final synchronized void pollKeyStates() throws IOException {
        LinuxEventDevice.nGetKeyStates(this.fd, this.key_states);
    }

    private static final native void nGetKeyStates(long var0, byte[] var2) throws IOException;

    public final boolean isKeySet(int n) {
        return LinuxEventDevice.isBitSet(this.key_states, n);
    }

    public static final boolean isBitSet(byte[] byArray, int n) {
        return (byArray[n / 8] & 1 << n % 8) != 0;
    }

    public final String getName() {
        return this.name;
    }

    private final String getDeviceName() throws IOException {
        return LinuxEventDevice.nGetName(this.fd);
    }

    private static final native String nGetName(long var0) throws IOException;

    public final synchronized void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        LinuxEnvironmentPlugin.execute(new LinuxDeviceTask(this){
            private final LinuxEventDevice this$0;
            {
                this.this$0 = linuxEventDevice;
            }

            protected final Object execute() throws IOException {
                LinuxEventDevice.access$100(LinuxEventDevice.access$000(this.this$0));
                return null;
            }
        });
    }

    private static final native void nClose(long var0) throws IOException;

    private final void checkClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Device is closed");
        }
    }

    protected void finalize() throws IOException {
        this.close();
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }

    static long access$000(LinuxEventDevice linuxEventDevice) {
        return linuxEventDevice.fd;
    }

    static void access$100(long l) throws IOException {
        LinuxEventDevice.nClose(l);
    }
}

