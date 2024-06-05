package net.java.games.input;

import java.util.*;

public abstract class ControllerEnvironment
{
    private static ControllerEnvironment defaultEnvironment;
    protected final ArrayList controllerListeners;
    static /* synthetic */ Class class$net$java$games$input$ControllerEnvironment;
    
    static void logln(final String msg) {
        log(msg + "\n");
    }
    
    static void log(final String msg) {
        System.out.print(msg);
    }
    
    protected ControllerEnvironment() {
        this.controllerListeners = new ArrayList();
    }
    
    public abstract Controller[] getControllers();
    
    public void addControllerListener(final ControllerListener l) {
        assert l != null;
        this.controllerListeners.add(l);
    }
    
    public abstract boolean isSupported();
    
    public void removeControllerListener(final ControllerListener l) {
        assert l != null;
        this.controllerListeners.remove(l);
    }
    
    protected void fireControllerAdded(final Controller c) {
        final ControllerEvent ev = new ControllerEvent(c);
        final Iterator it = this.controllerListeners.iterator();
        while (it.hasNext()) {
            it.next().controllerAdded(ev);
        }
    }
    
    protected void fireControllerRemoved(final Controller c) {
        final ControllerEvent ev = new ControllerEvent(c);
        final Iterator it = this.controllerListeners.iterator();
        while (it.hasNext()) {
            it.next().controllerRemoved(ev);
        }
    }
    
    public static ControllerEnvironment getDefaultEnvironment() {
        return ControllerEnvironment.defaultEnvironment;
    }
    
    static /* synthetic */ Class class$(final String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x) {
            throw new NoClassDefFoundError().initCause(x);
        }
    }
    
    static {
        $assertionsDisabled = !((ControllerEnvironment.class$net$java$games$input$ControllerEnvironment == null) ? (ControllerEnvironment.class$net$java$games$input$ControllerEnvironment = class$("net.java.games.input.ControllerEnvironment")) : ControllerEnvironment.class$net$java$games$input$ControllerEnvironment).desiredAssertionStatus();
        ControllerEnvironment.defaultEnvironment = new DefaultControllerEnvironment();
    }
}
