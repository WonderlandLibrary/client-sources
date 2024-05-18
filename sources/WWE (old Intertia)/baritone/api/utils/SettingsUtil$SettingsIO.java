package baritone.api.utils;

import java.awt.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

enum SettingsUtil$SettingsIO
{
    DOUBLE((Class<T>)Double.class, (Function<String, T>)Double::parseDouble), 
    BOOLEAN((Class<T>)Boolean.class, (Function<String, T>)Boolean::parseBoolean), 
    INTEGER((Class<T>)Integer.class, (Function<String, T>)Integer::parseInt), 
    FLOAT((Class<T>)Float.class, (Function<String, T>)Float::parseFloat), 
    LONG((Class<T>)Long.class, (Function<String, T>)Long::parseLong), 
    ITEM_LIST((Class<T>)ArrayList.class, (Function<String, T>)SettingsUtil$SettingsIO::lambda$static$0, (Function<T, String>)SettingsUtil$SettingsIO::lambda$static$1), 
    COLOR((Class<T>)Color.class, (Function<String, T>)SettingsUtil$SettingsIO::lambda$static$2, (Function<T, String>)SettingsUtil$SettingsIO::lambda$static$3);
    
    Class<?> klass;
    Function<String, Object> parser;
    Function<Object, String> toString;
    private static final SettingsUtil$SettingsIO[] $VALUES;
    
    public static SettingsUtil$SettingsIO[] values() {
        return SettingsUtil$SettingsIO.$VALUES.clone();
    }
    
    public static SettingsUtil$SettingsIO valueOf(final String s) {
        return Enum.valueOf(SettingsUtil$SettingsIO.class, s);
    }
    
    private <T> SettingsUtil$SettingsIO(final Class<T> clazz, final Function<String, T> function) {
        this(clazz, function, Object::toString);
    }
    
    private <T> SettingsUtil$SettingsIO(final Class<T> klass, final Function<String, T> function, final Function<T, String> function2) {
        this.klass = klass;
        this.parser = function::apply;
        this.toString = SettingsUtil$SettingsIO::lambda$new$4;
    }
    
    private static String lambda$new$4(final Function function, final Object o) {
        return function.apply(o);
    }
    
    private static String lambda$static$3(final Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
    
    private static Color lambda$static$2(final String s) {
        return new Color(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1]), Integer.parseInt(s.split(",")[2]));
    }
    
    private static String lambda$static$1(final ArrayList list) {
        return list.stream().map((Function<? super Object, ?>)ain.g::b).map((Function<? super Object, ?>)nf::toString).collect((Collector<? super Object, ?, String>)Collectors.joining(","));
    }
    
    private static ArrayList lambda$static$0(final String s) {
        return Stream.of(s.split(",")).map((Function<? super String, ?>)ain::b).collect((Collector<? super Object, ?, ArrayList>)Collectors.toCollection((Supplier<R>)ArrayList::new));
    }
    
    static {
        $VALUES = new SettingsUtil$SettingsIO[] { SettingsUtil$SettingsIO.DOUBLE, SettingsUtil$SettingsIO.BOOLEAN, SettingsUtil$SettingsIO.INTEGER, SettingsUtil$SettingsIO.FLOAT, SettingsUtil$SettingsIO.LONG, SettingsUtil$SettingsIO.ITEM_LIST, SettingsUtil$SettingsIO.COLOR };
    }
}
