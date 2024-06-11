/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package Hydro.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import Hydro.event.Event;
import Hydro.event.events.EventMotion;
import Hydro.event.events.EventRenderGUI;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.script.runtime.events.ScriptMotionUpdateEvent;

public class ScriptModule extends Module {
    private ScriptEngine engine;

    ScriptModule(String name, int key, boolean visible, Category category) {
        super(name, key, visible, category, "");
    }

    public void setScriptEngine(ScriptEngine scriptEngine) {
        engine = scriptEngine;
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventRenderGUI){
            try {
                ((Invocable) engine).invokeFunction("onRender2D");
            } catch (ScriptException ee) {
                ee.printStackTrace();
            } catch (NoSuchMethodException ignored) {
            }
        }

        if(e instanceof EventMotion){
            ScriptMotionUpdateEvent ev = new ScriptMotionUpdateEvent(e.getType(), ((EventMotion) e).getX(), ((EventMotion) e).getY(), ((EventMotion) e).getZ(), ((EventMotion) e).getYaw(), ((EventMotion) e).getPitch(), ((EventMotion) e).isOnGround());

            try {
                ((Invocable) engine).invokeFunction("onMotionUpdate", ev);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception ee) {
                ee.printStackTrace();
            }

            ev.apply((EventMotion) e);
        }
    }

}
