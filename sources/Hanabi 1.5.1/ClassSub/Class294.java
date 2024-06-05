package ClassSub;

import java.util.*;

public class Class294
{
    public static ArrayList<Class59> types;
    
    
    public static Class59 getTypeByName(final String s) {
        for (final Class59 class59 : Class294.types) {
            if (class59.getClientName().equals(s)) {
                return class59;
            }
        }
        return null;
    }
    
    static {
        (Class294.types = new ArrayList<Class59>()).add(new Class201());
        Class294.types.add(new Class350());
        Class294.types.add(new Class230());
    }
}
