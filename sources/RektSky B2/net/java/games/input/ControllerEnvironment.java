package net.java.games.input;

import java.util.logging.*;
import java.util.*;

public abstract class ControllerEnvironment
{
    private static ControllerEnvironment defaultEnvironment;
    protected final ArrayList<ControllerListener> controllerListeners;
    
    static void log(final String msg) {
        Logger.getLogger(ControllerEnvironment.class.getName()).info(msg);
    }
    
    protected ControllerEnvironment() {
        this.controllerListeners = new ArrayList<ControllerListener>();
        if (System.getProperty("jinput.loglevel") != null) {
            final String loggerName = ControllerEnvironment.class.getPackage().getName();
            final Level level = Level.parse(System.getProperty("jinput.loglevel"));
            Logger.getLogger(loggerName).setLevel(level);
        }
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
        final Iterator<ControllerListener> it = this.controllerListeners.iterator();
        while (it.hasNext()) {
            it.next().controllerAdded(ev);
        }
    }
    
    protected void fireControllerRemoved(final Controller c) {
        final ControllerEvent ev = new ControllerEvent(c);
        final Iterator<ControllerListener> it = this.controllerListeners.iterator();
        while (it.hasNext()) {
            it.next().controllerRemoved(ev);
        }
    }
    
    public static ControllerEnvironment getDefaultEnvironment() {
        return ControllerEnvironment.defaultEnvironment;
    }
    
    static {
        ControllerEnvironment.defaultEnvironment = new DefaultControllerEnvironment();
    }
}
