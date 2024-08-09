/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Event;
import net.java.games.input.WinTabButtonComponent;
import net.java.games.input.WinTabContext;
import net.java.games.input.WinTabCursorComponent;
import net.java.games.input.WinTabPacket;

public class WinTabComponent
extends AbstractComponent {
    public static final int XAxis = 1;
    public static final int YAxis = 2;
    public static final int ZAxis = 3;
    public static final int NPressureAxis = 4;
    public static final int TPressureAxis = 5;
    public static final int OrientationAxis = 6;
    public static final int RotationAxis = 7;
    private int min;
    private int max;
    protected float lastKnownValue;
    private boolean analog;
    static Class class$net$java$games$input$Component$Identifier$Button;

    protected WinTabComponent(WinTabContext winTabContext, int n, String string, Component.Identifier identifier, int n2, int n3) {
        super(string, identifier);
        this.min = n2;
        this.max = n3;
        this.analog = true;
    }

    protected WinTabComponent(WinTabContext winTabContext, int n, String string, Component.Identifier identifier) {
        super(string, identifier);
        this.min = 0;
        this.max = 1;
        this.analog = false;
    }

    protected float poll() throws IOException {
        return this.lastKnownValue;
    }

    public boolean isAnalog() {
        return this.analog;
    }

    public boolean isRelative() {
        return true;
    }

    public static List createComponents(WinTabContext winTabContext, int n, int n2, int[] nArray) {
        ArrayList<WinTabComponent> arrayList = new ArrayList<WinTabComponent>();
        switch (n2) {
            case 1: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.X;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                break;
            }
            case 2: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.Y;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                break;
            }
            case 3: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.Z;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                break;
            }
            case 4: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.X_FORCE;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                break;
            }
            case 5: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.Y_FORCE;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                break;
            }
            case 6: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.RX;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                axis = Component.Identifier.Axis.RY;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[2], nArray[3]));
                axis = Component.Identifier.Axis.RZ;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[4], nArray[5]));
                break;
            }
            case 7: {
                Component.Identifier.Axis axis = Component.Identifier.Axis.RX;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[0], nArray[1]));
                axis = Component.Identifier.Axis.RY;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[2], nArray[3]));
                axis = Component.Identifier.Axis.RZ;
                arrayList.add(new WinTabComponent(winTabContext, n, axis.getName(), axis, nArray[4], nArray[5]));
            }
        }
        return arrayList;
    }

    public static Collection createButtons(WinTabContext winTabContext, int n, int n2) {
        ArrayList<WinTabButtonComponent> arrayList = new ArrayList<WinTabButtonComponent>();
        for (int i = 0; i < n2; ++i) {
            try {
                Class clazz = class$net$java$games$input$Component$Identifier$Button == null ? WinTabComponent.class$("net.java.games.input.Component$Identifier$Button") : class$net$java$games$input$Component$Identifier$Button;
                Field field = clazz.getField("_" + i);
                Component.Identifier identifier = (Component.Identifier)field.get(null);
                arrayList.add(new WinTabButtonComponent(winTabContext, n, identifier.getName(), identifier, i));
                continue;
            } catch (SecurityException securityException) {
                securityException.printStackTrace();
                continue;
            } catch (NoSuchFieldException noSuchFieldException) {
                noSuchFieldException.printStackTrace();
                continue;
            } catch (IllegalArgumentException illegalArgumentException) {
                illegalArgumentException.printStackTrace();
                continue;
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        return arrayList;
    }

    public Event processPacket(WinTabPacket winTabPacket) {
        float f = this.lastKnownValue;
        if (this.getIdentifier() == Component.Identifier.Axis.X) {
            f = this.normalise(winTabPacket.PK_X);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Y) {
            f = this.normalise(winTabPacket.PK_Y);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Z) {
            f = this.normalise(winTabPacket.PK_Z);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.X_FORCE) {
            f = this.normalise(winTabPacket.PK_NORMAL_PRESSURE);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Y_FORCE) {
            f = this.normalise(winTabPacket.PK_TANGENT_PRESSURE);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.RX) {
            f = this.normalise(winTabPacket.PK_ORIENTATION_ALT);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.RY) {
            f = this.normalise(winTabPacket.PK_ORIENTATION_AZ);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.RZ) {
            f = this.normalise(winTabPacket.PK_ORIENTATION_TWIST);
        }
        if (f != this.getPollData()) {
            this.lastKnownValue = f;
            Event event = new Event();
            event.set(this, f, winTabPacket.PK_TIME * 1000L);
            return event;
        }
        return null;
    }

    private float normalise(float f) {
        if (this.max == this.min) {
            return f;
        }
        float f2 = this.max - this.min;
        return (f - (float)this.min) / f2;
    }

    public static Collection createCursors(WinTabContext winTabContext, int n, String[] stringArray) {
        ArrayList<WinTabCursorComponent> arrayList = new ArrayList<WinTabCursorComponent>();
        for (int i = 0; i < stringArray.length; ++i) {
            Component.Identifier.Button button = stringArray[i].matches("Puck") ? Component.Identifier.Button.TOOL_FINGER : (stringArray[i].matches("Eraser.*") ? Component.Identifier.Button.TOOL_RUBBER : Component.Identifier.Button.TOOL_PEN);
            arrayList.add(new WinTabCursorComponent(winTabContext, n, button.getName(), button, i));
        }
        return arrayList;
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

