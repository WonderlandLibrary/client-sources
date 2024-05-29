package dev.client.tenacity.utils.misc;

import dev.client.tenacity.module.Module;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static String findLongestModuleName(List<Module> modules) {
        return Collections.max(modules, Comparator.comparing(module -> (module.getName() + (module.hasMode() ? " " + module.getSuffix() : "")).length())).getName();
    }

    public static List<Module> getToggledModules(List<Module> modules) {
        return modules.stream().filter(Module::isToggled).collect(Collectors.toList());
    }

}
