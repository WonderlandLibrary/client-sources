package ClassSub;

import java.util.*;

public class Class206
{
    public static Class309 lastAlt;
    public static ArrayList<Class309> registry;
    
    
    public ArrayList<Class309> getRegistry() {
        return Class206.registry;
    }
    
    public void setLastAlt(final Class309 lastAlt) {
        Class206.lastAlt = lastAlt;
    }
    
    static {
        Class206.registry = new ArrayList<Class309>();
    }
}
