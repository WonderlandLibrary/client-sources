package me.kaimson.melonclient.features;

import com.google.common.collect.*;
import org.reflections.scanners.*;
import org.reflections.*;
import me.kaimson.melonclient.features.modules.utils.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public class ModuleManager
{
    public static final ModuleManager INSTANCE;
    public LinkedHashSet<Module> modules;
    
    public ModuleManager() {
        this.modules = (LinkedHashSet<Module>)Sets.newLinkedHashSet();
    }
    
    public void init() {
        final Set<Class<? extends Module>> modules = new Reflections("me.kaimson.melonclient.features.modules", new Scanner[0]).getSubTypesOf(Module.class);
        final ReflectiveOperationException ex;
        ReflectiveOperationException e;
        modules.forEach(module -> {
            if (module == IModuleRenderer.class || module == DefaultModuleRenderer.class) {
                return;
            }
            else {
                try {
                    module.newInstance();
                }
                catch (InstantiationException | IllegalAccessException ex2) {
                    e = ex;
                    e.printStackTrace();
                }
                return;
            }
        });
        this.modules = this.modules.stream().sorted((o1, o2) -> {
            if (o1.isRender() && !o2.isRender()) {
                return -1;
            }
            else if (!o1.isRender() && o2.isRender()) {
                return 1;
            }
            else {
                return o1.getKey().compareTo(o2.getKey());
            }
        }).collect((Collector<? super Object, ?, LinkedHashSet<Module>>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new));
    }
    
    static {
        INSTANCE = new ModuleManager();
    }
}
