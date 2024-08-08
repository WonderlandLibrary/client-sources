package in.momin5.cookieclient.api.util.utils.misc;

public class EnumUtil {
    public static <T extends Enum<T>> T next(T value) {
        T[] enumValues = value.getDeclaringClass().getEnumConstants();
        return enumValues[(value.ordinal() + 1) % enumValues.length];
    }
}
