package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;

public class PingDisplayModule extends DefaultModuleRenderer
{
    public PingDisplayModule() {
        super("Ping Display", 18);
    }
    
    @Override
    public Object getValue() {
        return (this.mc.u() != null && this.mc.u().a(this.mc.h.aK()) != null) ? this.mc.u().a(this.mc.h.aK()).c() : -1;
    }
    
    @Override
    public String getFormat() {
        return "[%value% ms]";
    }
}
