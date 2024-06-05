package zamorozka.ui;

import com.google.common.reflect.ClassPath;

import java.util.ArrayList;

public class ReflectionUtils {
	
    public static ArrayList<Class<?>> getClasses(final String packageName){
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getAllClasses()) {
                if (info.getName().startsWith(packageName)) {
                    final Class<?> clazz = info.load();
                    classes.add(clazz);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return classes;
    }
    
    public static ArrayList<Class<?>> getClassesOfType(final String packageName, Class superClass){
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getAllClasses()) {
                if (info.getName().startsWith(packageName)) {
                    final Class<?> clazz = info.load();
                    classes.add(clazz);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return classes;
    }
}