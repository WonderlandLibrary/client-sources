/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.AbstractController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Rumbler;
import net.java.games.input.WinTabComponent;
import net.java.games.input.WinTabContext;
import net.java.games.input.WinTabEnvironmentPlugin;
import net.java.games.input.WinTabPacket;

public class WinTabDevice
extends AbstractController {
    private WinTabContext context;
    private List eventList = new ArrayList();

    private WinTabDevice(WinTabContext winTabContext, int n, String string, Component[] componentArray) {
        super(string, componentArray, new Controller[0], new Rumbler[0]);
        this.context = winTabContext;
    }

    protected boolean getNextDeviceEvent(Event event) throws IOException {
        if (this.eventList.size() > 0) {
            Event event2 = (Event)this.eventList.remove(0);
            event.set(event2);
            return false;
        }
        return true;
    }

    protected void pollDevice() throws IOException {
        this.context.processEvents();
        super.pollDevice();
    }

    public Controller.Type getType() {
        return Controller.Type.TRACKPAD;
    }

    public void processPacket(WinTabPacket winTabPacket) {
        Component[] componentArray = this.getComponents();
        for (int i = 0; i < componentArray.length; ++i) {
            Event event = ((WinTabComponent)componentArray[i]).processPacket(winTabPacket);
            if (event == null) continue;
            this.eventList.add(event);
        }
    }

    public static WinTabDevice createDevice(WinTabContext winTabContext, int n) {
        int n2;
        String string = WinTabDevice.nGetName(n);
        WinTabEnvironmentPlugin.logln("Device " + n + ", name: " + string);
        ArrayList arrayList = new ArrayList();
        int[] nArray = WinTabDevice.nGetAxisDetails(n, 1);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("ZAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("Xmin: " + nArray[0] + ", Xmax: " + nArray[1]);
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 1, nArray));
        }
        nArray = WinTabDevice.nGetAxisDetails(n, 2);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("YAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("Ymin: " + nArray[0] + ", Ymax: " + nArray[1]);
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 2, nArray));
        }
        nArray = WinTabDevice.nGetAxisDetails(n, 3);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("ZAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("Zmin: " + nArray[0] + ", Zmax: " + nArray[1]);
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 3, nArray));
        }
        nArray = WinTabDevice.nGetAxisDetails(n, 4);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("NPressureAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("NPressMin: " + nArray[0] + ", NPressMax: " + nArray[1]);
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 4, nArray));
        }
        nArray = WinTabDevice.nGetAxisDetails(n, 5);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("TPressureAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("TPressureAxismin: " + nArray[0] + ", TPressureAxismax: " + nArray[1]);
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 5, nArray));
        }
        nArray = WinTabDevice.nGetAxisDetails(n, 6);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("OrientationAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("OrientationAxis mins/maxs: " + nArray[0] + "," + nArray[1] + ", " + nArray[2] + "," + nArray[3] + ", " + nArray[4] + "," + nArray[5]);
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 6, nArray));
        }
        nArray = WinTabDevice.nGetAxisDetails(n, 7);
        if (nArray.length == 0) {
            WinTabEnvironmentPlugin.logln("RotationAxis not supported");
        } else {
            WinTabEnvironmentPlugin.logln("RotationAxis is supported (by the device, not by this plugin)");
            arrayList.addAll(WinTabComponent.createComponents(winTabContext, n, 7, nArray));
        }
        String[] stringArray = WinTabDevice.nGetCursorNames(n);
        arrayList.addAll(WinTabComponent.createCursors(winTabContext, n, stringArray));
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            WinTabEnvironmentPlugin.logln("Cursor " + n2 + "'s name: " + stringArray[n2]);
        }
        n2 = WinTabDevice.nGetMaxButtonCount(n);
        WinTabEnvironmentPlugin.logln("Device has " + n2 + " buttons");
        arrayList.addAll(WinTabComponent.createButtons(winTabContext, n, n2));
        Component[] componentArray = arrayList.toArray(new Component[0]);
        return new WinTabDevice(winTabContext, n, string, componentArray);
    }

    private static final native String nGetName(int var0);

    private static final native int[] nGetAxisDetails(int var0, int var1);

    private static final native String[] nGetCursorNames(int var0);

    private static final native int nGetMaxButtonCount(int var0);
}

