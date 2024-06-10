package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;

public class MemoryUsageModule extends DefaultModuleRenderer
{
    public MemoryUsageModule() {
        super("Memory Usage");
    }
    
    @Override
    public Object getValue() {
        final long i = Runtime.getRuntime().maxMemory();
        final long j = Runtime.getRuntime().totalMemory();
        final long k = Runtime.getRuntime().freeMemory();
        final long l = j - k;
        return String.format("%2d%% %03d/%03dMB", l * 100L / i, l / 1024L / 1024L, i / 1024L / 1024L);
    }
}
