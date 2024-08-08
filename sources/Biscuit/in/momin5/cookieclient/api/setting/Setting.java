package in.momin5.cookieclient.api.setting;

import in.momin5.cookieclient.api.module.Module;

public abstract class Setting{
    public String name;
    public Module parent;
    public boolean focused;

    public Setting(String name, Module parent){
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Module getParent(){
        return parent;
    }

}
