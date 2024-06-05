package koks.utilities.value;

import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:09
 */
public abstract class Value {

    private String name;
    private Module module;
    private boolean visible = true, shouldSave = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public boolean shouldSave() {
        return shouldSave;
    }

    public void setShouldSave(boolean shouldSave) {
        this.shouldSave = shouldSave;
    }

}
