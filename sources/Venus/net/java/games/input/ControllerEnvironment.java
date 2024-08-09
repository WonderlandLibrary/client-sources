/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;
import net.java.games.input.DefaultControllerEnvironment;

public abstract class ControllerEnvironment {
    private static ControllerEnvironment defaultEnvironment;
    protected final ArrayList controllerListeners = new ArrayList();
    static Class class$net$java$games$input$ControllerEnvironment;
    static final boolean $assertionsDisabled;

    static void logln(String string) {
        ControllerEnvironment.log(string + "\n");
    }

    static void log(String string) {
        Logger.getLogger((class$net$java$games$input$ControllerEnvironment == null ? (class$net$java$games$input$ControllerEnvironment = ControllerEnvironment.class$("net.java.games.input.ControllerEnvironment")) : class$net$java$games$input$ControllerEnvironment).getName()).info(string);
    }

    protected ControllerEnvironment() {
    }

    public abstract Controller[] getControllers();

    public void addControllerListener(ControllerListener controllerListener) {
        if (!$assertionsDisabled && controllerListener == null) {
            throw new AssertionError();
        }
        this.controllerListeners.add(controllerListener);
    }

    public abstract boolean isSupported();

    public void removeControllerListener(ControllerListener controllerListener) {
        if (!$assertionsDisabled && controllerListener == null) {
            throw new AssertionError();
        }
        this.controllerListeners.remove(controllerListener);
    }

    protected void fireControllerAdded(Controller controller) {
        ControllerEvent controllerEvent = new ControllerEvent(controller);
        Iterator iterator2 = this.controllerListeners.iterator();
        while (iterator2.hasNext()) {
            ((ControllerListener)iterator2.next()).controllerAdded(controllerEvent);
        }
    }

    protected void fireControllerRemoved(Controller controller) {
        ControllerEvent controllerEvent = new ControllerEvent(controller);
        Iterator iterator2 = this.controllerListeners.iterator();
        while (iterator2.hasNext()) {
            ((ControllerListener)iterator2.next()).controllerRemoved(controllerEvent);
        }
    }

    public static ControllerEnvironment getDefaultEnvironment() {
        return defaultEnvironment;
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }

    static {
        $assertionsDisabled = !(class$net$java$games$input$ControllerEnvironment == null ? (class$net$java$games$input$ControllerEnvironment = ControllerEnvironment.class$("net.java.games.input.ControllerEnvironment")) : class$net$java$games$input$ControllerEnvironment).desiredAssertionStatus();
        defaultEnvironment = new DefaultControllerEnvironment();
    }
}

