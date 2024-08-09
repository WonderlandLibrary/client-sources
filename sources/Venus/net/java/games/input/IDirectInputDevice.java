/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.DIComponent;
import net.java.games.input.DIDeviceObject;
import net.java.games.input.DIDeviceObjectData;
import net.java.games.input.DIEffectInfo;
import net.java.games.input.DIIdentifierMap;
import net.java.games.input.DataQueue;
import net.java.games.input.DirectInputEnvironmentPlugin;
import net.java.games.input.DummyWindow;
import net.java.games.input.IDirectInputEffect;
import net.java.games.input.Rumbler;

final class IDirectInputDevice {
    public static final int GUID_XAxis = 1;
    public static final int GUID_YAxis = 2;
    public static final int GUID_ZAxis = 3;
    public static final int GUID_RxAxis = 4;
    public static final int GUID_RyAxis = 5;
    public static final int GUID_RzAxis = 6;
    public static final int GUID_Slider = 7;
    public static final int GUID_Button = 8;
    public static final int GUID_Key = 9;
    public static final int GUID_POV = 10;
    public static final int GUID_Unknown = 11;
    public static final int GUID_ConstantForce = 12;
    public static final int GUID_RampForce = 13;
    public static final int GUID_Square = 14;
    public static final int GUID_Sine = 15;
    public static final int GUID_Triangle = 16;
    public static final int GUID_SawtoothUp = 17;
    public static final int GUID_SawtoothDown = 18;
    public static final int GUID_Spring = 19;
    public static final int GUID_Damper = 20;
    public static final int GUID_Inertia = 21;
    public static final int GUID_Friction = 22;
    public static final int GUID_CustomForce = 23;
    public static final int DI8DEVTYPE_DEVICE = 17;
    public static final int DI8DEVTYPE_MOUSE = 18;
    public static final int DI8DEVTYPE_KEYBOARD = 19;
    public static final int DI8DEVTYPE_JOYSTICK = 20;
    public static final int DI8DEVTYPE_GAMEPAD = 21;
    public static final int DI8DEVTYPE_DRIVING = 22;
    public static final int DI8DEVTYPE_FLIGHT = 23;
    public static final int DI8DEVTYPE_1STPERSON = 24;
    public static final int DI8DEVTYPE_DEVICECTRL = 25;
    public static final int DI8DEVTYPE_SCREENPOINTER = 26;
    public static final int DI8DEVTYPE_REMOTE = 27;
    public static final int DI8DEVTYPE_SUPPLEMENTAL = 28;
    public static final int DISCL_EXCLUSIVE = 1;
    public static final int DISCL_NONEXCLUSIVE = 2;
    public static final int DISCL_FOREGROUND = 4;
    public static final int DISCL_BACKGROUND = 8;
    public static final int DISCL_NOWINKEY = 16;
    public static final int DIDFT_ALL = 0;
    public static final int DIDFT_RELAXIS = 1;
    public static final int DIDFT_ABSAXIS = 2;
    public static final int DIDFT_AXIS = 3;
    public static final int DIDFT_PSHBUTTON = 4;
    public static final int DIDFT_TGLBUTTON = 8;
    public static final int DIDFT_BUTTON = 12;
    public static final int DIDFT_POV = 16;
    public static final int DIDFT_COLLECTION = 64;
    public static final int DIDFT_NODATA = 128;
    public static final int DIDFT_FFACTUATOR = 0x1000000;
    public static final int DIDFT_FFEFFECTTRIGGER = 0x2000000;
    public static final int DIDFT_OUTPUT = 0x10000000;
    public static final int DIDFT_VENDORDEFINED = 0x4000000;
    public static final int DIDFT_ALIAS = 0x8000000;
    public static final int DIDFT_OPTIONAL = Integer.MIN_VALUE;
    public static final int DIDFT_NOCOLLECTION = 0xFFFF00;
    public static final int DIDF_ABSAXIS = 1;
    public static final int DIDF_RELAXIS = 2;
    public static final int DI_OK = 0;
    public static final int DI_NOEFFECT = 1;
    public static final int DI_PROPNOEFFECT = 1;
    public static final int DI_POLLEDDEVICE = 2;
    public static final int DI_DOWNLOADSKIPPED = 3;
    public static final int DI_EFFECTRESTARTED = 4;
    public static final int DI_TRUNCATED = 8;
    public static final int DI_SETTINGSNOTSAVED = 11;
    public static final int DI_TRUNCATEDANDRESTARTED = 12;
    public static final int DI_BUFFEROVERFLOW = 1;
    public static final int DIERR_INPUTLOST = -2147024866;
    public static final int DIERR_NOTACQUIRED = -2147024868;
    public static final int DIERR_OTHERAPPHASPRIO = -2147024891;
    public static final int DIDOI_FFACTUATOR = 1;
    public static final int DIDOI_FFEFFECTTRIGGER = 2;
    public static final int DIDOI_POLLED = 32768;
    public static final int DIDOI_ASPECTPOSITION = 256;
    public static final int DIDOI_ASPECTVELOCITY = 512;
    public static final int DIDOI_ASPECTACCEL = 768;
    public static final int DIDOI_ASPECTFORCE = 1024;
    public static final int DIDOI_ASPECTMASK = 3840;
    public static final int DIDOI_GUIDISUSAGE = 65536;
    public static final int DIEFT_ALL = 0;
    public static final int DIEFT_CONSTANTFORCE = 1;
    public static final int DIEFT_RAMPFORCE = 2;
    public static final int DIEFT_PERIODIC = 3;
    public static final int DIEFT_CONDITION = 4;
    public static final int DIEFT_CUSTOMFORCE = 5;
    public static final int DIEFT_HARDWARE = 255;
    public static final int DIEFT_FFATTACK = 512;
    public static final int DIEFT_FFFADE = 1024;
    public static final int DIEFT_SATURATION = 2048;
    public static final int DIEFT_POSNEGCOEFFICIENTS = 4096;
    public static final int DIEFT_POSNEGSATURATION = 8192;
    public static final int DIEFT_DEADBAND = 16384;
    public static final int DIEFT_STARTDELAY = 32768;
    public static final int DIEFF_OBJECTIDS = 1;
    public static final int DIEFF_OBJECTOFFSETS = 2;
    public static final int DIEFF_CARTESIAN = 16;
    public static final int DIEFF_POLAR = 32;
    public static final int DIEFF_SPHERICAL = 64;
    public static final int DIEP_DURATION = 1;
    public static final int DIEP_SAMPLEPERIOD = 2;
    public static final int DIEP_GAIN = 4;
    public static final int DIEP_TRIGGERBUTTON = 8;
    public static final int DIEP_TRIGGERREPEATINTERVAL = 16;
    public static final int DIEP_AXES = 32;
    public static final int DIEP_DIRECTION = 64;
    public static final int DIEP_ENVELOPE = 128;
    public static final int DIEP_TYPESPECIFICPARAMS = 256;
    public static final int DIEP_STARTDELAY = 512;
    public static final int DIEP_ALLPARAMS_DX5 = 511;
    public static final int DIEP_ALLPARAMS = 1023;
    public static final int DIEP_START = 0x20000000;
    public static final int DIEP_NORESTART = 0x40000000;
    public static final int DIEP_NODOWNLOAD = Integer.MIN_VALUE;
    public static final int DIEB_NOTRIGGER = -1;
    public static final int INFINITE = -1;
    public static final int DI_DEGREES = 100;
    public static final int DI_FFNOMINALMAX = 10000;
    public static final int DI_SECONDS = 1000000;
    public static final int DIPROPRANGE_NOMIN = Integer.MIN_VALUE;
    public static final int DIPROPRANGE_NOMAX = Integer.MAX_VALUE;
    private final DummyWindow window;
    private final long address;
    private final int dev_type;
    private final int dev_subtype;
    private final String instance_name;
    private final String product_name;
    private final List objects = new ArrayList();
    private final List effects = new ArrayList();
    private final List rumblers = new ArrayList();
    private final int[] device_state;
    private final Map object_to_component = new HashMap();
    private final boolean axes_in_relative_mode;
    private boolean released;
    private DataQueue queue;
    private int button_counter;
    private int current_format_offset;
    static Class class$net$java$games$input$DIDeviceObjectData;

    public IDirectInputDevice(DummyWindow dummyWindow, long l, byte[] byArray, byte[] byArray2, int n, int n2, String string, String string2) throws IOException {
        int n3;
        this.window = dummyWindow;
        this.address = l;
        this.product_name = string2;
        this.instance_name = string;
        this.dev_type = n;
        this.dev_subtype = n2;
        this.enumObjects();
        try {
            this.enumEffects();
            this.createRumblers();
        } catch (IOException iOException) {
            DirectInputEnvironmentPlugin.logln("Failed to create rumblers: " + iOException.getMessage());
        }
        boolean bl = true;
        boolean bl2 = false;
        for (n3 = 0; n3 < this.objects.size(); ++n3) {
            DIDeviceObject dIDeviceObject = (DIDeviceObject)this.objects.get(n3);
            if (!dIDeviceObject.isAxis()) continue;
            bl2 = true;
            if (dIDeviceObject.isRelative()) continue;
            bl = false;
            break;
        }
        this.axes_in_relative_mode = bl && bl2;
        n3 = bl ? 2 : 1;
        this.setDataFormat(n3);
        if (this.rumblers.size() > 0) {
            try {
                this.setCooperativeLevel(9);
            } catch (IOException iOException) {
                this.setCooperativeLevel(10);
            }
        } else {
            this.setCooperativeLevel(10);
        }
        this.setBufferSize(32);
        this.acquire();
        this.device_state = new int[this.objects.size()];
    }

    public final boolean areAxesRelative() {
        return this.axes_in_relative_mode;
    }

    public final Rumbler[] getRumblers() {
        return this.rumblers.toArray(new Rumbler[0]);
    }

    private final List createRumblers() throws IOException {
        DIDeviceObject dIDeviceObject = this.lookupObjectByGUID(1);
        if (dIDeviceObject == null) {
            return this.rumblers;
        }
        DIDeviceObject[] dIDeviceObjectArray = new DIDeviceObject[]{dIDeviceObject};
        long[] lArray = new long[]{0L};
        for (int i = 0; i < this.effects.size(); ++i) {
            DIEffectInfo dIEffectInfo = (DIEffectInfo)this.effects.get(i);
            if ((dIEffectInfo.getEffectType() & 0xFF) != 3 || (dIEffectInfo.getDynamicParams() & 4) == 0) continue;
            this.rumblers.add(this.createPeriodicRumbler(dIDeviceObjectArray, lArray, dIEffectInfo));
        }
        return this.rumblers;
    }

    private final Rumbler createPeriodicRumbler(DIDeviceObject[] dIDeviceObjectArray, long[] lArray, DIEffectInfo dIEffectInfo) throws IOException {
        int[] nArray = new int[dIDeviceObjectArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = dIDeviceObjectArray[i].getDIIdentifier();
        }
        long l = IDirectInputDevice.nCreatePeriodicEffect(this.address, dIEffectInfo.getGUID(), 17, -1, 0, 10000, -1, 0, nArray, lArray, 0, 0, 0, 0, 10000, 0, 0, 50000, 0);
        return new IDirectInputEffect(l, dIEffectInfo);
    }

    private static final native long nCreatePeriodicEffect(long var0, byte[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int[] var9, long[] var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19) throws IOException;

    private final DIDeviceObject lookupObjectByGUID(int n) {
        for (int i = 0; i < this.objects.size(); ++i) {
            DIDeviceObject dIDeviceObject = (DIDeviceObject)this.objects.get(i);
            if (n != dIDeviceObject.getGUIDType()) continue;
            return dIDeviceObject;
        }
        return null;
    }

    public final int getPollData(DIDeviceObject dIDeviceObject) {
        return this.device_state[dIDeviceObject.getFormatOffset()];
    }

    public final DIDeviceObject mapEvent(DIDeviceObjectData dIDeviceObjectData) {
        int n = dIDeviceObjectData.getFormatOffset() / 4;
        return (DIDeviceObject)this.objects.get(n);
    }

    public final DIComponent mapObject(DIDeviceObject dIDeviceObject) {
        return (DIComponent)this.object_to_component.get(dIDeviceObject);
    }

    public final void registerComponent(DIDeviceObject dIDeviceObject, DIComponent dIComponent) {
        this.object_to_component.put(dIDeviceObject, dIComponent);
    }

    public final synchronized void pollAll() throws IOException {
        this.checkReleased();
        this.poll();
        this.getDeviceState(this.device_state);
        this.queue.compact();
        this.getDeviceData(this.queue);
        this.queue.flip();
    }

    public final synchronized boolean getNextEvent(DIDeviceObjectData dIDeviceObjectData) {
        DIDeviceObjectData dIDeviceObjectData2 = (DIDeviceObjectData)this.queue.get();
        if (dIDeviceObjectData2 == null) {
            return true;
        }
        dIDeviceObjectData.set(dIDeviceObjectData2);
        return false;
    }

    private final void poll() throws IOException {
        int n = IDirectInputDevice.nPoll(this.address);
        if (n != 0 && n != 1) {
            if (n == -2147024868) {
                this.acquire();
                return;
            }
            throw new IOException("Failed to poll device (" + Integer.toHexString(n) + ")");
        }
    }

    private static final native int nPoll(long var0) throws IOException;

    private final void acquire() throws IOException {
        int n = IDirectInputDevice.nAcquire(this.address);
        if (n != 0 && n != -2147024891 && n != 1) {
            throw new IOException("Failed to acquire device (" + Integer.toHexString(n) + ")");
        }
    }

    private static final native int nAcquire(long var0);

    private final void unacquire() throws IOException {
        int n = IDirectInputDevice.nUnacquire(this.address);
        if (n != 0 && n != 1) {
            throw new IOException("Failed to unAcquire device (" + Integer.toHexString(n) + ")");
        }
    }

    private static final native int nUnacquire(long var0);

    private final boolean getDeviceData(DataQueue dataQueue) throws IOException {
        int n = IDirectInputDevice.nGetDeviceData(this.address, 0, dataQueue, dataQueue.getElements(), dataQueue.position(), dataQueue.remaining());
        if (n != 0 && n != 1) {
            if (n == -2147024868) {
                this.acquire();
                return true;
            }
            throw new IOException("Failed to get device data (" + Integer.toHexString(n) + ")");
        }
        return false;
    }

    private static final native int nGetDeviceData(long var0, int var2, DataQueue var3, Object[] var4, int var5, int var6);

    private final void getDeviceState(int[] nArray) throws IOException {
        int n = IDirectInputDevice.nGetDeviceState(this.address, nArray);
        if (n != 0) {
            if (n == -2147024868) {
                Arrays.fill(nArray, 0);
                this.acquire();
                return;
            }
            throw new IOException("Failed to get device state (" + Integer.toHexString(n) + ")");
        }
    }

    private static final native int nGetDeviceState(long var0, int[] var2);

    private final void setDataFormat(int n) throws IOException {
        DIDeviceObject[] dIDeviceObjectArray = new DIDeviceObject[this.objects.size()];
        this.objects.toArray(dIDeviceObjectArray);
        int n2 = IDirectInputDevice.nSetDataFormat(this.address, n, dIDeviceObjectArray);
        if (n2 != 0) {
            throw new IOException("Failed to set data format (" + Integer.toHexString(n2) + ")");
        }
    }

    private static final native int nSetDataFormat(long var0, int var2, DIDeviceObject[] var3);

    public final String getProductName() {
        return this.product_name;
    }

    public final int getType() {
        return this.dev_type;
    }

    public final List getObjects() {
        return this.objects;
    }

    private final void enumEffects() throws IOException {
        int n = this.nEnumEffects(this.address, 0);
        if (n != 0) {
            throw new IOException("Failed to enumerate effects (" + Integer.toHexString(n) + ")");
        }
    }

    private final native int nEnumEffects(long var1, int var3);

    private final void addEffect(byte[] byArray, int n, int n2, int n3, int n4, String string) {
        this.effects.add(new DIEffectInfo(this, byArray, n, n2, n3, n4, string));
    }

    private final void enumObjects() throws IOException {
        int n = this.nEnumObjects(this.address, 31);
        if (n != 0) {
            throw new IOException("Failed to enumerate objects (" + Integer.toHexString(n) + ")");
        }
    }

    private final native int nEnumObjects(long var1, int var3);

    public final synchronized long[] getRangeProperty(int n) throws IOException {
        this.checkReleased();
        long[] lArray = new long[2];
        int n2 = IDirectInputDevice.nGetRangeProperty(this.address, n, lArray);
        if (n2 != 0) {
            throw new IOException("Failed to get object range (" + n2 + ")");
        }
        return lArray;
    }

    private static final native int nGetRangeProperty(long var0, int var2, long[] var3);

    public final synchronized int getDeadzoneProperty(int n) throws IOException {
        this.checkReleased();
        return IDirectInputDevice.nGetDeadzoneProperty(this.address, n);
    }

    private static final native int nGetDeadzoneProperty(long var0, int var2) throws IOException;

    private final void addObject(byte[] byArray, int n, int n2, int n3, int n4, int n5, String string) throws IOException {
        Component.Identifier identifier = this.getIdentifier(n, n3, n4);
        int n6 = this.current_format_offset++;
        DIDeviceObject dIDeviceObject = new DIDeviceObject(this, identifier, byArray, n, n2, n3, n4, n5, string, n6);
        this.objects.add(dIDeviceObject);
    }

    private static final Component.Identifier.Key getKeyIdentifier(int n) {
        return DIIdentifierMap.getKeyIdentifier(n);
    }

    private final Component.Identifier.Button getNextButtonIdentifier() {
        int n = this.button_counter++;
        return DIIdentifierMap.getButtonIdentifier(n);
    }

    private final Component.Identifier getIdentifier(int n, int n2, int n3) {
        switch (n) {
            case 1: {
                return Component.Identifier.Axis.X;
            }
            case 2: {
                return Component.Identifier.Axis.Y;
            }
            case 3: {
                return Component.Identifier.Axis.Z;
            }
            case 4: {
                return Component.Identifier.Axis.RX;
            }
            case 5: {
                return Component.Identifier.Axis.RY;
            }
            case 6: {
                return Component.Identifier.Axis.RZ;
            }
            case 7: {
                return Component.Identifier.Axis.SLIDER;
            }
            case 10: {
                return Component.Identifier.Axis.POV;
            }
            case 9: {
                return IDirectInputDevice.getKeyIdentifier(n3);
            }
            case 8: {
                return this.getNextButtonIdentifier();
            }
        }
        return Component.Identifier.Axis.UNKNOWN;
    }

    public final synchronized void setBufferSize(int n) throws IOException {
        this.checkReleased();
        this.unacquire();
        int n2 = IDirectInputDevice.nSetBufferSize(this.address, n);
        if (n2 != 0 && n2 != 1 && n2 != 2) {
            throw new IOException("Failed to set buffer size (" + Integer.toHexString(n2) + ")");
        }
        this.queue = new DataQueue(n, class$net$java$games$input$DIDeviceObjectData == null ? (class$net$java$games$input$DIDeviceObjectData = IDirectInputDevice.class$("net.java.games.input.DIDeviceObjectData")) : class$net$java$games$input$DIDeviceObjectData);
        this.queue.position(this.queue.limit());
        this.acquire();
    }

    private static final native int nSetBufferSize(long var0, int var2);

    public final synchronized void setCooperativeLevel(int n) throws IOException {
        this.checkReleased();
        int n2 = IDirectInputDevice.nSetCooperativeLevel(this.address, this.window.getHwnd(), n);
        if (n2 != 0) {
            throw new IOException("Failed to set cooperative level (" + Integer.toHexString(n2) + ")");
        }
    }

    private static final native int nSetCooperativeLevel(long var0, long var2, int var4);

    public final synchronized void release() {
        if (!this.released) {
            this.released = true;
            for (int i = 0; i < this.rumblers.size(); ++i) {
                IDirectInputEffect iDirectInputEffect = (IDirectInputEffect)this.rumblers.get(i);
                iDirectInputEffect.release();
            }
            IDirectInputDevice.nRelease(this.address);
        }
    }

    private static final native void nRelease(long var0);

    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException("Device is released");
        }
    }

    protected void finalize() {
        this.release();
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

