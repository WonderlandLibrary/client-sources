/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.option;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.thekirkayt.client.command.CommandManager;
import me.thekirkayt.client.command.commands.OptionCommand;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.client.option.types.NumberOption;
import me.thekirkayt.client.option.types.StringOption;
import me.thekirkayt.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

public final class OptionManager {
    private static final File OPTION_DIR = FileUtils.getConfigFile("Options");
    public static CopyOnWriteArrayList<Option> optionList = new CopyOnWriteArrayList();

    public static void start() {
        try {
            for (Module mod : ModuleManager.getModules()) {
                mod.preInitialize();
                for (Field field : mod.getClass().getDeclaredFields()) {
                    Option.Op opAnnotation;
                    Number value3;
                    String name;
                    field.setAccessible(true);
                    if (!field.isAnnotationPresent(Option.Op.class)) continue;
                    if (!(field.getType().isAssignableFrom(Float.TYPE) || field.getType().isAssignableFrom(Double.TYPE) || field.getType().isAssignableFrom(Integer.TYPE) || field.getType().isAssignableFrom(Long.TYPE) || field.getType().isAssignableFrom(Short.TYPE) || field.getType().isAssignableFrom(Byte.TYPE))) {
                        String value2;
                        if (field.getType().isAssignableFrom(Boolean.TYPE)) {
                            boolean value;
                            try {
                                value = field.getBoolean(mod);
                            }
                            catch (IllegalArgumentException e2) {
                                value = false;
                            }
                            opAnnotation = field.getAnnotation(Option.Op.class);
                            name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize((String)field.getName()) : StringUtils.capitalize((String)opAnnotation.name());
                            optionList.add(new BooleanOption(field.getName(), StringUtils.capitalize((String)name), value, mod, false));
                            continue;
                        }
                        if (!field.getType().isAssignableFrom(String.class)) continue;
                        try {
                            value2 = (String)field.get(mod);
                        }
                        catch (IllegalArgumentException e2) {
                            value2 = "";
                        }
                        opAnnotation = field.getAnnotation(Option.Op.class);
                        name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize((String)field.getName()) : StringUtils.capitalize((String)opAnnotation.name());
                        optionList.add(new StringOption(field.getName(), StringUtils.capitalize((String)name), value2, mod));
                        continue;
                    }
                    try {
                        value3 = (Number)field.get(mod);
                    }
                    catch (IllegalArgumentException e2) {
                        value3 = 0;
                    }
                    opAnnotation = field.getAnnotation(Option.Op.class);
                    name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize((String)field.getName()) : StringUtils.capitalize((String)opAnnotation.name());
                    NumberOption option = new NumberOption(field.getName(), StringUtils.capitalize((String)name), value3, mod);
                    option.setMin(opAnnotation.min());
                    option.setMax(opAnnotation.max());
                    option.setIncrement(opAnnotation.increment());
                    optionList.add(option);
                }
                ArrayList<String> nameList = new ArrayList<String>();
                if (CommandManager.optionCommand.getNames() != null) {
                    for (String name2 : CommandManager.optionCommand.getNames()) {
                        nameList.add(name2);
                    }
                }
                nameList.add(mod.getId());
                nameList.add(mod.getDisplayName());
                CommandManager.optionCommand.setNames(nameList.toArray(new String[0]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        OptionManager.load();
        OptionManager.save();
    }

    public static void load() {
        List<String> fileContent = FileUtils.read(OPTION_DIR);
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String optionId = split[0];
                String optionValue = split[1];
                String modId = split[2];
                Option option = OptionManager.getOption(optionId, split[2]);
                if (option == null) continue;
                if (option instanceof NumberOption) {
                    ((NumberOption)option).setValue(optionValue);
                    continue;
                }
                if (option instanceof BooleanOption) {
                    ((BooleanOption)option).setValueHard(Boolean.parseBoolean(optionValue));
                    continue;
                }
                if (!(option instanceof StringOption)) continue;
                option.setValue(optionValue);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Option option : optionList) {
            String optionId = option.getId();
            String optionVal = option.getValue().toString();
            Module mod = option.getModule();
            fileContent.add(String.format("%s:%s:%s", optionId, optionVal, mod.getId()));
        }
        FileUtils.write(OPTION_DIR, fileContent, true);
    }

    public static Option getOption(String optionName, String modId) {
        for (Option option : optionList) {
            if (!option.getId().equalsIgnoreCase(optionName) && !option.getDisplayName().equalsIgnoreCase(optionName) || !option.getModule().getId().equalsIgnoreCase(modId)) continue;
            return option;
        }
        return null;
    }

    public static List<Option> getOptionList() {
        return optionList;
    }
}

