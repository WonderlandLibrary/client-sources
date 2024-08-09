/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.java.games.input.Controller;
import net.java.games.input.ElementType;
import net.java.games.input.GenericDesktopUsage;
import net.java.games.input.OSXEvent;
import net.java.games.input.OSXHIDElement;
import net.java.games.input.OSXHIDQueue;
import net.java.games.input.Usage;
import net.java.games.input.UsagePage;
import net.java.games.input.UsagePair;

final class OSXHIDDevice {
    private static final Logger log = Logger.getLogger((class$net$java$games$input$OSXHIDDevice == null ? (class$net$java$games$input$OSXHIDDevice = OSXHIDDevice.class$("net.java.games.input.OSXHIDDevice")) : class$net$java$games$input$OSXHIDDevice).getName());
    private static final int AXIS_DEFAULT_MIN_VALUE = 0;
    private static final int AXIS_DEFAULT_MAX_VALUE = 65536;
    private static final String kIOHIDTransportKey = "Transport";
    private static final String kIOHIDVendorIDKey = "VendorID";
    private static final String kIOHIDVendorIDSourceKey = "VendorIDSource";
    private static final String kIOHIDProductIDKey = "ProductID";
    private static final String kIOHIDVersionNumberKey = "VersionNumber";
    private static final String kIOHIDManufacturerKey = "Manufacturer";
    private static final String kIOHIDProductKey = "Product";
    private static final String kIOHIDSerialNumberKey = "SerialNumber";
    private static final String kIOHIDCountryCodeKey = "CountryCode";
    private static final String kIOHIDLocationIDKey = "LocationID";
    private static final String kIOHIDDeviceUsageKey = "DeviceUsage";
    private static final String kIOHIDDeviceUsagePageKey = "DeviceUsagePage";
    private static final String kIOHIDDeviceUsagePairsKey = "DeviceUsagePairs";
    private static final String kIOHIDPrimaryUsageKey = "PrimaryUsage";
    private static final String kIOHIDPrimaryUsagePageKey = "PrimaryUsagePage";
    private static final String kIOHIDMaxInputReportSizeKey = "MaxInputReportSize";
    private static final String kIOHIDMaxOutputReportSizeKey = "MaxOutputReportSize";
    private static final String kIOHIDMaxFeatureReportSizeKey = "MaxFeatureReportSize";
    private static final String kIOHIDElementKey = "Elements";
    private static final String kIOHIDElementCookieKey = "ElementCookie";
    private static final String kIOHIDElementTypeKey = "Type";
    private static final String kIOHIDElementCollectionTypeKey = "CollectionType";
    private static final String kIOHIDElementUsageKey = "Usage";
    private static final String kIOHIDElementUsagePageKey = "UsagePage";
    private static final String kIOHIDElementMinKey = "Min";
    private static final String kIOHIDElementMaxKey = "Max";
    private static final String kIOHIDElementScaledMinKey = "ScaledMin";
    private static final String kIOHIDElementScaledMaxKey = "ScaledMax";
    private static final String kIOHIDElementSizeKey = "Size";
    private static final String kIOHIDElementReportSizeKey = "ReportSize";
    private static final String kIOHIDElementReportCountKey = "ReportCount";
    private static final String kIOHIDElementReportIDKey = "ReportID";
    private static final String kIOHIDElementIsArrayKey = "IsArray";
    private static final String kIOHIDElementIsRelativeKey = "IsRelative";
    private static final String kIOHIDElementIsWrappingKey = "IsWrapping";
    private static final String kIOHIDElementIsNonLinearKey = "IsNonLinear";
    private static final String kIOHIDElementHasPreferredStateKey = "HasPreferredState";
    private static final String kIOHIDElementHasNullStateKey = "HasNullState";
    private static final String kIOHIDElementUnitKey = "Unit";
    private static final String kIOHIDElementUnitExponentKey = "UnitExponent";
    private static final String kIOHIDElementNameKey = "Name";
    private static final String kIOHIDElementValueLocationKey = "ValueLocation";
    private static final String kIOHIDElementDuplicateIndexKey = "DuplicateIndex";
    private static final String kIOHIDElementParentCollectionKey = "ParentCollection";
    private final long device_address;
    private final long device_interface_address;
    private final Map properties;
    private boolean released;
    static Class class$net$java$games$input$OSXHIDDevice;

    public OSXHIDDevice(long l, long l2) throws IOException {
        this.device_address = l;
        this.device_interface_address = l2;
        this.properties = this.getDeviceProperties();
        this.open();
    }

    public final Controller.PortType getPortType() {
        String string = (String)this.properties.get(kIOHIDTransportKey);
        if (string == null) {
            return Controller.PortType.UNKNOWN;
        }
        if (string.equals("USB")) {
            return Controller.PortType.USB;
        }
        return Controller.PortType.UNKNOWN;
    }

    public final String getProductName() {
        return (String)this.properties.get(kIOHIDProductKey);
    }

    private final OSXHIDElement createElementFromElementProperties(Map map) {
        long l = OSXHIDDevice.getLongFromProperties(map, kIOHIDElementCookieKey);
        int n = OSXHIDDevice.getIntFromProperties(map, kIOHIDElementTypeKey);
        ElementType elementType = ElementType.map(n);
        int n2 = (int)OSXHIDDevice.getLongFromProperties(map, kIOHIDElementMinKey, 0L);
        int n3 = (int)OSXHIDDevice.getLongFromProperties(map, kIOHIDElementMaxKey, 65536L);
        UsagePair usagePair = this.getUsagePair();
        boolean bl = usagePair != null && (usagePair.getUsage() == GenericDesktopUsage.POINTER || usagePair.getUsage() == GenericDesktopUsage.MOUSE);
        boolean bl2 = OSXHIDDevice.getBooleanFromProperties(map, kIOHIDElementIsRelativeKey, bl);
        int n4 = OSXHIDDevice.getIntFromProperties(map, kIOHIDElementUsageKey);
        int n5 = OSXHIDDevice.getIntFromProperties(map, kIOHIDElementUsagePageKey);
        UsagePair usagePair2 = OSXHIDDevice.createUsagePair(n5, n4);
        if (usagePair2 == null || elementType != ElementType.INPUT_MISC && elementType != ElementType.INPUT_BUTTON && elementType != ElementType.INPUT_AXIS) {
            return null;
        }
        return new OSXHIDElement(this, usagePair2, l, elementType, n2, n3, bl2);
    }

    private final void addElements(List list, Map map) {
        Object[] objectArray = (Object[])map.get(kIOHIDElementKey);
        if (objectArray == null) {
            return;
        }
        for (int i = 0; i < objectArray.length; ++i) {
            Map map2 = (Map)objectArray[i];
            OSXHIDElement oSXHIDElement = this.createElementFromElementProperties(map2);
            if (oSXHIDElement != null) {
                list.add(oSXHIDElement);
            }
            this.addElements(list, map2);
        }
    }

    public final List getElements() {
        ArrayList arrayList = new ArrayList();
        this.addElements(arrayList, this.properties);
        return arrayList;
    }

    private static final long getLongFromProperties(Map map, String string, long l) {
        Long l2 = (Long)map.get(string);
        if (l2 == null) {
            return l;
        }
        return l2;
    }

    private static final boolean getBooleanFromProperties(Map map, String string, boolean bl) {
        return OSXHIDDevice.getLongFromProperties(map, string, bl ? 1L : 0L) != 0L;
    }

    private static final int getIntFromProperties(Map map, String string) {
        return (int)OSXHIDDevice.getLongFromProperties(map, string);
    }

    private static final long getLongFromProperties(Map map, String string) {
        Long l = (Long)map.get(string);
        return l;
    }

    private static final UsagePair createUsagePair(int n, int n2) {
        Usage usage;
        UsagePage usagePage = UsagePage.map(n);
        if (usagePage != null && (usage = usagePage.mapUsage(n2)) != null) {
            return new UsagePair(usagePage, usage);
        }
        return null;
    }

    public final UsagePair getUsagePair() {
        int n = OSXHIDDevice.getIntFromProperties(this.properties, kIOHIDPrimaryUsagePageKey);
        int n2 = OSXHIDDevice.getIntFromProperties(this.properties, kIOHIDPrimaryUsageKey);
        return OSXHIDDevice.createUsagePair(n, n2);
    }

    private final void dumpProperties() {
        log.info(this.toString());
        OSXHIDDevice.dumpMap("", this.properties);
    }

    private static final void dumpArray(String string, Object[] objectArray) {
        log.info(string + "{");
        for (int i = 0; i < objectArray.length; ++i) {
            OSXHIDDevice.dumpObject(string + "\t", objectArray[i]);
            log.info(string + ",");
        }
        log.info(string + "}");
    }

    private static final void dumpMap(String string, Map map) {
        Iterator iterator2 = map.keySet().iterator();
        while (iterator2.hasNext()) {
            Object k = iterator2.next();
            Object v = map.get(k);
            OSXHIDDevice.dumpObject(string, k);
            OSXHIDDevice.dumpObject(string + "\t", v);
        }
    }

    private static final void dumpObject(String string, Object object) {
        if (object instanceof Long) {
            Long l = (Long)object;
            log.info(string + "0x" + Long.toHexString(l));
        } else if (object instanceof Map) {
            OSXHIDDevice.dumpMap(string, (Map)object);
        } else if (object.getClass().isArray()) {
            OSXHIDDevice.dumpArray(string, (Object[])object);
        } else {
            log.info(string + object);
        }
    }

    private final Map getDeviceProperties() throws IOException {
        return OSXHIDDevice.nGetDeviceProperties(this.device_address);
    }

    private static final native Map nGetDeviceProperties(long var0) throws IOException;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void release() throws IOException {
        try {
            this.close();
        } finally {
            this.released = true;
            OSXHIDDevice.nReleaseDevice(this.device_address, this.device_interface_address);
        }
    }

    private static final native void nReleaseDevice(long var0, long var2);

    public final synchronized void getElementValue(long l, OSXEvent oSXEvent) throws IOException {
        this.checkReleased();
        OSXHIDDevice.nGetElementValue(this.device_interface_address, l, oSXEvent);
    }

    private static final native void nGetElementValue(long var0, long var2, OSXEvent var4) throws IOException;

    public final synchronized OSXHIDQueue createQueue(int n) throws IOException {
        this.checkReleased();
        long l = OSXHIDDevice.nCreateQueue(this.device_interface_address);
        return new OSXHIDQueue(l, n);
    }

    private static final native long nCreateQueue(long var0) throws IOException;

    private final void open() throws IOException {
        OSXHIDDevice.nOpen(this.device_interface_address);
    }

    private static final native void nOpen(long var0) throws IOException;

    private final void close() throws IOException {
        OSXHIDDevice.nClose(this.device_interface_address);
    }

    private static final native void nClose(long var0) throws IOException;

    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException();
        }
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

