package net.java.games.input;

import java.util.*;

public class DirectAndRawInputEnvironmentPlugin extends ControllerEnvironment
{
    private RawInputEnvironmentPlugin rawPlugin;
    private DirectInputEnvironmentPlugin dinputPlugin;
    private Controller[] controllers;
    
    public DirectAndRawInputEnvironmentPlugin() {
        this.controllers = null;
        this.dinputPlugin = new DirectInputEnvironmentPlugin();
        this.rawPlugin = new RawInputEnvironmentPlugin();
    }
    
    @Override
    public Controller[] getControllers() {
        if (this.controllers == null) {
            boolean rawKeyboardFound = false;
            boolean rawMouseFound = false;
            final List<Controller> tempControllers = new ArrayList<Controller>();
            final Controller[] dinputControllers = this.dinputPlugin.getControllers();
            final Controller[] rawControllers = this.rawPlugin.getControllers();
            for (int i = 0; i < rawControllers.length; ++i) {
                tempControllers.add(rawControllers[i]);
                if (rawControllers[i].getType() == Controller.Type.KEYBOARD) {
                    rawKeyboardFound = true;
                }
                else if (rawControllers[i].getType() == Controller.Type.MOUSE) {
                    rawMouseFound = true;
                }
            }
            for (int i = 0; i < dinputControllers.length; ++i) {
                if (dinputControllers[i].getType() == Controller.Type.KEYBOARD) {
                    if (!rawKeyboardFound) {
                        tempControllers.add(dinputControllers[i]);
                    }
                }
                else if (dinputControllers[i].getType() == Controller.Type.MOUSE) {
                    if (!rawMouseFound) {
                        tempControllers.add(dinputControllers[i]);
                    }
                }
                else {
                    tempControllers.add(dinputControllers[i]);
                }
            }
            this.controllers = tempControllers.toArray(new Controller[0]);
        }
        return this.controllers;
    }
    
    @Override
    public boolean isSupported() {
        return this.rawPlugin.isSupported() || this.dinputPlugin.isSupported();
    }
}
