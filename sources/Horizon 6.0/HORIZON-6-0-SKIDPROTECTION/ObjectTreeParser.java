package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ObjectTreeParser
{
    private HashMap HorizonCode_Horizon_È;
    private String Â;
    private ArrayList Ý;
    private String Ø­áŒŠá;
    
    public ObjectTreeParser() {
        this.HorizonCode_Horizon_È = new HashMap();
        this.Ý = new ArrayList();
        this.Ø­áŒŠá = "add";
    }
    
    public ObjectTreeParser(final String defaultPackage) {
        this.HorizonCode_Horizon_È = new HashMap();
        this.Ý = new ArrayList();
        this.Ø­áŒŠá = "add";
        this.Â = defaultPackage;
    }
    
    public void HorizonCode_Horizon_È(final String elementName, final Class elementClass) {
        this.HorizonCode_Horizon_È.put(elementName, elementClass);
    }
    
    public void HorizonCode_Horizon_È(final String elementName) {
        this.Ý.add(elementName);
    }
    
    public void Â(final String methodName) {
        this.Ø­áŒŠá = methodName;
    }
    
    public void Ý(final String defaultPackage) {
        this.Â = defaultPackage;
    }
    
    public Object Ø­áŒŠá(final String ref) throws SlickXMLException {
        return this.HorizonCode_Horizon_È(ref, ResourceLoader.HorizonCode_Horizon_È(ref));
    }
    
    public Object HorizonCode_Horizon_È(final String name, final InputStream in) throws SlickXMLException {
        final XMLParser parser = new XMLParser();
        final XMLElement root = parser.HorizonCode_Horizon_È(name, in);
        return this.HorizonCode_Horizon_È(root);
    }
    
    public Object HorizonCode_Horizon_È(final String ref, final Object target) throws SlickXMLException {
        return this.HorizonCode_Horizon_È(ref, ResourceLoader.HorizonCode_Horizon_È(ref), target);
    }
    
    public Object HorizonCode_Horizon_È(final String name, final InputStream in, final Object target) throws SlickXMLException {
        final XMLParser parser = new XMLParser();
        final XMLElement root = parser.HorizonCode_Horizon_È(name, in);
        return this.HorizonCode_Horizon_È(root, target);
    }
    
    private Class Âµá€(final String name) {
        final Class clazz = this.HorizonCode_Horizon_È.get(name);
        if (clazz != null) {
            return clazz;
        }
        if (this.Â != null) {
            try {
                return Class.forName(String.valueOf(this.Â) + "." + name);
            }
            catch (ClassNotFoundException ex) {}
        }
        return null;
    }
    
    private Object HorizonCode_Horizon_È(final XMLElement current) throws SlickXMLException {
        return this.HorizonCode_Horizon_È(current, null);
    }
    
    private Object HorizonCode_Horizon_È(final XMLElement current, Object instance) throws SlickXMLException {
        final String name = current.Â();
        if (this.Ý.contains(name)) {
            return null;
        }
        Class clazz;
        if (instance == null) {
            clazz = this.Âµá€(name);
        }
        else {
            clazz = instance.getClass();
        }
        if (clazz == null) {
            throw new SlickXMLException("Unable to map element " + name + " to a class, define the mapping");
        }
        try {
            if (instance == null) {
                instance = clazz.newInstance();
                final Method elementNameMethod = this.HorizonCode_Horizon_È(clazz, "setXMLElementName", new Class[] { String.class });
                if (elementNameMethod != null) {
                    this.HorizonCode_Horizon_È(elementNameMethod, instance, new Object[] { name });
                }
                final Method contentMethod = this.HorizonCode_Horizon_È(clazz, "setXMLElementContent", new Class[] { String.class });
                if (contentMethod != null) {
                    this.HorizonCode_Horizon_È(contentMethod, instance, new Object[] { current.Ý() });
                }
            }
            final String[] attrs = current.HorizonCode_Horizon_È();
            for (int i = 0; i < attrs.length; ++i) {
                final String methodName = "set" + attrs[i];
                final Method method = this.Â(clazz, methodName);
                if (method == null) {
                    final Field field = this.HorizonCode_Horizon_È(clazz, attrs[i]);
                    if (field != null) {
                        final String value = current.HorizonCode_Horizon_È(attrs[i]);
                        final Object typedValue = this.Â(value, field.getType());
                        this.HorizonCode_Horizon_È(field, instance, typedValue);
                    }
                    else {
                        Log.Ý("Unable to find property on: " + clazz + " for attribute: " + attrs[i]);
                    }
                }
                else {
                    final String value2 = current.HorizonCode_Horizon_È(attrs[i]);
                    final Object typedValue2 = this.Â(value2, method.getParameterTypes()[0]);
                    this.HorizonCode_Horizon_È(method, instance, new Object[] { typedValue2 });
                }
            }
            final XMLElementList children = current.Ø­áŒŠá();
            for (int j = 0; j < children.HorizonCode_Horizon_È(); ++j) {
                final XMLElement element = children.HorizonCode_Horizon_È(j);
                final Object child = this.HorizonCode_Horizon_È(element);
                if (child != null) {
                    final String methodName2 = this.Ø­áŒŠá;
                    final Method method2 = this.HorizonCode_Horizon_È(clazz, methodName2, child.getClass());
                    if (method2 == null) {
                        Log.Ý("Unable to find method to add: " + child + " to " + clazz);
                    }
                    else {
                        this.HorizonCode_Horizon_È(method2, instance, new Object[] { child });
                    }
                }
            }
            return instance;
        }
        catch (InstantiationException e) {
            throw new SlickXMLException("Unable to instance " + clazz + " for element " + name + ", no zero parameter constructor?", e);
        }
        catch (IllegalAccessException e2) {
            throw new SlickXMLException("Unable to instance " + clazz + " for element " + name + ", no zero parameter constructor?", e2);
        }
    }
    
    private Object Â(final String value, Class clazz) throws SlickXMLException {
        if (clazz == String.class) {
            return value;
        }
        try {
            clazz = this.HorizonCode_Horizon_È(clazz);
            return clazz.getConstructor(String.class).newInstance(value);
        }
        catch (Exception e) {
            throw new SlickXMLException("Failed to convert: " + value + " to the expected primitive type: " + clazz, e);
        }
    }
    
    private Class HorizonCode_Horizon_È(final Class clazz) {
        if (clazz == Integer.TYPE) {
            return Integer.class;
        }
        if (clazz == Double.TYPE) {
            return Double.class;
        }
        if (clazz == Float.TYPE) {
            return Float.class;
        }
        if (clazz == Boolean.TYPE) {
            return Boolean.class;
        }
        if (clazz == Long.TYPE) {
            return Long.class;
        }
        throw new RuntimeException("Unsupported primitive: " + clazz);
    }
    
    private Field HorizonCode_Horizon_È(final Class clazz, final String name) {
        final Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].getName().equalsIgnoreCase(name)) {
                if (fields[i].getType().isPrimitive()) {
                    return fields[i];
                }
                if (fields[i].getType() == String.class) {
                    return fields[i];
                }
            }
        }
        return null;
    }
    
    private Method Â(final Class clazz, final String name) {
        final Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equalsIgnoreCase(name)) {
                final Method method = methods[i];
                final Class[] params = method.getParameterTypes();
                if (params.length == 1) {
                    return method;
                }
            }
        }
        return null;
    }
    
    private Method HorizonCode_Horizon_È(final Class clazz, final String name, final Class parameter) {
        final Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equalsIgnoreCase(name)) {
                final Method method = methods[i];
                final Class[] params = method.getParameterTypes();
                if (params.length == 1 && method.getParameterTypes()[0].isAssignableFrom(parameter)) {
                    return method;
                }
            }
        }
        return null;
    }
    
    private void HorizonCode_Horizon_È(final Field field, final Object instance, final Object value) throws SlickXMLException {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        }
        catch (IllegalArgumentException e) {
            throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", e);
        }
        catch (IllegalAccessException e2) {
            throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", e2);
        }
        finally {
            field.setAccessible(false);
        }
        field.setAccessible(false);
    }
    
    private void HorizonCode_Horizon_È(final Method method, final Object instance, final Object[] params) throws SlickXMLException {
        try {
            method.setAccessible(true);
            method.invoke(instance, params);
        }
        catch (IllegalArgumentException e) {
            throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
        }
        catch (IllegalAccessException e2) {
            throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e2);
        }
        catch (InvocationTargetException e3) {
            throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e3);
        }
        finally {
            method.setAccessible(false);
        }
        method.setAccessible(false);
    }
    
    private Method HorizonCode_Horizon_È(final Class clazz, final String name, final Class[] params) {
        try {
            return clazz.getMethod(name, (Class[])params);
        }
        catch (SecurityException e) {
            return null;
        }
        catch (NoSuchMethodException e2) {
            return null;
        }
    }
}
