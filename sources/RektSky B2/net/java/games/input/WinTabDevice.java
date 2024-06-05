package net.java.games.input;

import java.io.*;
import java.util.*;

public class WinTabDevice extends AbstractController
{
    public static final int XAxis = 1;
    public static final int YAxis = 2;
    public static final int ZAxis = 3;
    public static final int NPressureAxis = 4;
    public static final int TPressureAxis = 5;
    public static final int OrientationAxis = 6;
    public static final int RotationAxis = 7;
    private WinTabContext context;
    private List<Event> eventList;
    
    private WinTabDevice(final WinTabContext context, final int index, final String name, final Component[] components) {
        super(name, components, new Controller[0], new Rumbler[0]);
        this.eventList = new ArrayList<Event>();
        this.context = context;
    }
    
    @Override
    protected boolean getNextDeviceEvent(final Event event) throws IOException {
        if (this.eventList.size() > 0) {
            final Event ourEvent = this.eventList.remove(0);
            event.set(ourEvent);
            return true;
        }
        return false;
    }
    
    @Override
    protected void pollDevice() throws IOException {
        this.context.processEvents();
        super.pollDevice();
    }
    
    @Override
    public Controller.Type getType() {
        return Controller.Type.TRACKPAD;
    }
    
    public void processPacket(final WinTabPacket packet) {
        final Component[] components = this.getComponents();
        for (int i = 0; i < components.length; ++i) {
            final Event event = ((WinTabComponent)components[i]).processPacket(packet);
            if (event != null) {
                this.eventList.add(event);
            }
        }
    }
    
    public static WinTabDevice createDevice(final WinTabContext context, final int deviceIndex) {
        final String name = nGetName(deviceIndex);
        ControllerEnvironment.log("Device " + deviceIndex + ", name: " + name);
        final List<WinTabComponent> componentsList = new ArrayList<WinTabComponent>();
        int[] axisDetails = nGetAxisDetails(deviceIndex, 1);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("ZAxis not supported");
        }
        else {
            ControllerEnvironment.log("Xmin: " + axisDetails[0] + ", Xmax: " + axisDetails[1]);
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 1, axisDetails));
        }
        axisDetails = nGetAxisDetails(deviceIndex, 2);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("YAxis not supported");
        }
        else {
            ControllerEnvironment.log("Ymin: " + axisDetails[0] + ", Ymax: " + axisDetails[1]);
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 2, axisDetails));
        }
        axisDetails = nGetAxisDetails(deviceIndex, 3);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("ZAxis not supported");
        }
        else {
            ControllerEnvironment.log("Zmin: " + axisDetails[0] + ", Zmax: " + axisDetails[1]);
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 3, axisDetails));
        }
        axisDetails = nGetAxisDetails(deviceIndex, 4);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("NPressureAxis not supported");
        }
        else {
            ControllerEnvironment.log("NPressMin: " + axisDetails[0] + ", NPressMax: " + axisDetails[1]);
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 4, axisDetails));
        }
        axisDetails = nGetAxisDetails(deviceIndex, 5);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("TPressureAxis not supported");
        }
        else {
            ControllerEnvironment.log("TPressureAxismin: " + axisDetails[0] + ", TPressureAxismax: " + axisDetails[1]);
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 5, axisDetails));
        }
        axisDetails = nGetAxisDetails(deviceIndex, 6);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("OrientationAxis not supported");
        }
        else {
            ControllerEnvironment.log("OrientationAxis mins/maxs: " + axisDetails[0] + "," + axisDetails[1] + ", " + axisDetails[2] + "," + axisDetails[3] + ", " + axisDetails[4] + "," + axisDetails[5]);
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 6, axisDetails));
        }
        axisDetails = nGetAxisDetails(deviceIndex, 7);
        if (axisDetails.length == 0) {
            ControllerEnvironment.log("RotationAxis not supported");
        }
        else {
            ControllerEnvironment.log("RotationAxis is supported (by the device, not by this plugin)");
            componentsList.addAll(WinTabComponent.createComponents(context, deviceIndex, 7, axisDetails));
        }
        final String[] cursorNames = nGetCursorNames(deviceIndex);
        componentsList.addAll(WinTabComponent.createCursors(context, deviceIndex, cursorNames));
        for (int i = 0; i < cursorNames.length; ++i) {
            ControllerEnvironment.log("Cursor " + i + "'s name: " + cursorNames[i]);
        }
        final int numberOfButtons = nGetMaxButtonCount(deviceIndex);
        ControllerEnvironment.log("Device has " + numberOfButtons + " buttons");
        componentsList.addAll(WinTabComponent.createButtons(context, deviceIndex, numberOfButtons));
        final Component[] components = componentsList.toArray(new Component[0]);
        return new WinTabDevice(context, deviceIndex, name, components);
    }
    
    private static final native String nGetName(final int p0);
    
    private static final native int[] nGetAxisDetails(final int p0, final int p1);
    
    private static final native String[] nGetCursorNames(final int p0);
    
    private static final native int nGetMaxButtonCount(final int p0);
}
