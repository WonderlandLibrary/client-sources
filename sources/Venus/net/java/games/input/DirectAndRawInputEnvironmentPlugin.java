/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.util.ArrayList;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.DirectInputEnvironmentPlugin;
import net.java.games.input.RawInputEnvironmentPlugin;

public class DirectAndRawInputEnvironmentPlugin
extends ControllerEnvironment {
    private RawInputEnvironmentPlugin rawPlugin;
    private DirectInputEnvironmentPlugin dinputPlugin = new DirectInputEnvironmentPlugin();
    private Controller[] controllers = null;

    public DirectAndRawInputEnvironmentPlugin() {
        this.rawPlugin = new RawInputEnvironmentPlugin();
    }

    public Controller[] getControllers() {
        if (this.controllers == null) {
            int n;
            boolean bl = false;
            boolean bl2 = false;
            ArrayList<Controller> arrayList = new ArrayList<Controller>();
            Controller[] controllerArray = this.dinputPlugin.getControllers();
            Controller[] controllerArray2 = this.rawPlugin.getControllers();
            for (n = 0; n < controllerArray2.length; ++n) {
                arrayList.add(controllerArray2[n]);
                if (controllerArray2[n].getType() == Controller.Type.KEYBOARD) {
                    bl = true;
                    continue;
                }
                if (controllerArray2[n].getType() != Controller.Type.MOUSE) continue;
                bl2 = true;
            }
            for (n = 0; n < controllerArray.length; ++n) {
                if (controllerArray[n].getType() == Controller.Type.KEYBOARD) {
                    if (bl) continue;
                    arrayList.add(controllerArray[n]);
                    continue;
                }
                if (controllerArray[n].getType() == Controller.Type.MOUSE) {
                    if (bl2) continue;
                    arrayList.add(controllerArray[n]);
                    continue;
                }
                arrayList.add(controllerArray[n]);
            }
            this.controllers = arrayList.toArray(new Controller[0]);
        }
        return this.controllers;
    }

    public boolean isSupported() {
        return this.rawPlugin.isSupported() || this.dinputPlugin.isSupported();
    }
}

