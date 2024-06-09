package lunadevs.luna.option;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

public final class OptionManager
{

  private static ArrayList<Option> optionList = new ArrayList();

  public static void start() {
    try {
      for (final Module mod : ModuleManager.getModules()) {
        Field[] declaredFields;
        for (int length = (declaredFields = mod.getClass().getDeclaredFields()).length, i = 0; i < length; ++i) {
          final Field field = declaredFields[i];
          field.setAccessible(true);
          if (field.isAnnotationPresent(Option.Op.class)) {
            if (!field.getType().isAssignableFrom(Float.TYPE) && !field.getType().isAssignableFrom(Double.TYPE) && !field.getType().isAssignableFrom(Integer.TYPE) && !field.getType().isAssignableFrom(Long.TYPE) && !field.getType().isAssignableFrom(Short.TYPE) && !field.getType().isAssignableFrom(Byte.TYPE)) {
              if (field.getType().isAssignableFrom(Boolean.TYPE)) {
                boolean value;
                try {
                  value = field.getBoolean(mod);
                }
                catch (IllegalArgumentException e2) {
                  value = false;
                }
                final Option.Op opAnnotation = field.getAnnotation(Option.Op.class);
                final String name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize(field.getName()) : StringUtils.capitalize(opAnnotation.name());
                OptionManager.optionList.add(new BooleanOption(field.getName(), StringUtils.capitalize(name), value, mod));
              }
              else if (field.getType().isAssignableFrom(String.class)) {
                String value2;
                try {
                  value2 = (String)field.get(mod);
                }
                catch (IllegalArgumentException e2) {
                  value2 = "";
                }
                final Option.Op opAnnotation = field.getAnnotation(Option.Op.class);
                final String name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize(field.getName()) : StringUtils.capitalize(opAnnotation.name());
                OptionManager.optionList.add(new StringOption(field.getName(), StringUtils.capitalize(name), value2, mod));
              }
            }
            else {
              Number value3;
              try {
                value3 = (Number)field.get(mod);
              }
              catch (IllegalArgumentException e2) {
                value3 = 0;
              }
              final Option.Op opAnnotation = field.getAnnotation(Option.Op.class);
              final String name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize(field.getName()) : StringUtils.capitalize(opAnnotation.name());
              final NumberOption option = new NumberOption(field.getName(), StringUtils.capitalize(name), value3, mod);
              option.setMin(opAnnotation.min());
              option.setMax(opAnnotation.max());
              option.setIncrement(opAnnotation.increment());
              OptionManager.optionList.add(option);
            }
          }
        }
        mod.options();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Option getOption(final String optionName, final String modId) {
    for (final Option option : OptionManager.optionList) {
      if ((option.getId().equalsIgnoreCase(optionName) || option.getDisplayName().equalsIgnoreCase(optionName)) && option.getModule().getName().equalsIgnoreCase(modId)) {
        return option;
      }
    }
    return null;
  }

  public static List<Option> getOptionList() {
    return OptionManager.optionList;
  }

}
