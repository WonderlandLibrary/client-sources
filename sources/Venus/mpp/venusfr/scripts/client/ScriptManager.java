/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.client;

import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.scripts.client.MCScript;
import mpp.venusfr.venusfr;

public class ScriptManager {
    private List<MCScript> scripts = new ArrayList<MCScript>();

    public void add(MCScript mCScript) {
        this.scripts.add(mCScript);
    }

    public void compileScripts() {
        for (MCScript mCScript : this.scripts) {
            try {
                mCScript.compile();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void addModules() {
        for (MCScript mCScript : this.scripts) {
            try {
                boolean bl = false;
                for (Function function : venusfr.getInstance().getFunctionRegistry().getFunctions()) {
                    if (!function.getName().equalsIgnoreCase(mCScript.getFunction().getName())) continue;
                    bl = true;
                    break;
                }
                if (bl) {
                    return;
                }
                venusfr.getInstance().getFunctionRegistry().getFunctions().add(mCScript.getFunction());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public List<MCScript> getScripts() {
        return this.scripts;
    }
}

