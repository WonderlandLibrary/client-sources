package org.newdawn.slick.util.xml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;




































public class ObjectTreeParser
{
  private HashMap nameToClass = new HashMap();
  
  private String defaultPackage;
  
  private ArrayList ignored = new ArrayList();
  
  private String addMethod = "add";
  





  public ObjectTreeParser() {}
  




  public ObjectTreeParser(String defaultPackage)
  {
    this.defaultPackage = defaultPackage;
  }
  





  public void addElementMapping(String elementName, Class elementClass)
  {
    nameToClass.put(elementName, elementClass);
  }
  




  public void addIgnoredElement(String elementName)
  {
    ignored.add(elementName);
  }
  






  public void setAddMethodName(String methodName)
  {
    addMethod = methodName;
  }
  





  public void setDefaultPackage(String defaultPackage)
  {
    this.defaultPackage = defaultPackage;
  }
  







  public Object parse(String ref)
    throws SlickXMLException
  {
    return parse(ref, ResourceLoader.getResourceAsStream(ref));
  }
  







  public Object parse(String name, InputStream in)
    throws SlickXMLException
  {
    XMLParser parser = new XMLParser();
    XMLElement root = parser.parse(name, in);
    
    return traverse(root);
  }
  








  public Object parseOnto(String ref, Object target)
    throws SlickXMLException
  {
    return parseOnto(ref, ResourceLoader.getResourceAsStream(ref), target);
  }
  








  public Object parseOnto(String name, InputStream in, Object target)
    throws SlickXMLException
  {
    XMLParser parser = new XMLParser();
    XMLElement root = parser.parse(name, in);
    
    return traverse(root, target);
  }
  






  private Class getClassForElementName(String name)
  {
    Class clazz = (Class)nameToClass.get(name);
    if (clazz != null) {
      return clazz;
    }
    
    if (defaultPackage != null) {
      try {
        return Class.forName(defaultPackage + "." + name);
      }
      catch (ClassNotFoundException localClassNotFoundException) {}
    }
    

    return null;
  }
  






  private Object traverse(XMLElement current)
    throws SlickXMLException
  {
    return traverse(current, null);
  }
  







  private Object traverse(XMLElement current, Object instance)
    throws SlickXMLException
  {
    String name = current.getName();
    if (ignored.contains(name)) {
      return null;
    }
    
    Class clazz;
    Class clazz;
    if (instance == null) {
      clazz = getClassForElementName(name);
    } else {
      clazz = instance.getClass();
    }
    
    if (clazz == null) {
      throw new SlickXMLException("Unable to map element " + name + " to a class, define the mapping");
    }
    try
    {
      if (instance == null) {
        instance = clazz.newInstance();
        
        Method elementNameMethod = getMethod(clazz, "setXMLElementName", new Class[] { String.class });
        if (elementNameMethod != null) {
          invoke(elementNameMethod, instance, new Object[] { name });
        }
        Method contentMethod = getMethod(clazz, "setXMLElementContent", new Class[] { String.class });
        if (contentMethod != null) {
          invoke(contentMethod, instance, new Object[] { current.getContent() });
        }
      }
      
      String[] attrs = current.getAttributeNames();
      for (int i = 0; i < attrs.length; i++) {
        String methodName = "set" + attrs[i];
        
        Method method = findMethod(clazz, methodName);
        
        if (method == null) {
          Field field = findField(clazz, attrs[i]);
          if (field != null) {
            String value = current.getAttribute(attrs[i]);
            Object typedValue = typeValue(value, field.getType());
            setField(field, instance, typedValue);
          } else {
            Log.info("Unable to find property on: " + clazz + " for attribute: " + attrs[i]);
          }
        } else {
          String value = current.getAttribute(attrs[i]);
          Object typedValue = typeValue(value, method.getParameterTypes()[0]);
          invoke(method, instance, new Object[] { typedValue });
        }
      }
      
      XMLElementList children = current.getChildren();
      for (int i = 0; i < children.size(); i++) {
        XMLElement element = children.get(i);
        
        Object child = traverse(element);
        if (child != null) {
          String methodName = addMethod;
          
          Method method = findMethod(clazz, methodName, child.getClass());
          if (method == null) {
            Log.info("Unable to find method to add: " + child + " to " + clazz);
          } else {
            invoke(method, instance, new Object[] { child });
          }
        }
      }
      
      return instance;
    } catch (InstantiationException e) {
      throw new SlickXMLException("Unable to instance " + clazz + " for element " + name + ", no zero parameter constructor?", e);
    } catch (IllegalAccessException e) {
      throw new SlickXMLException("Unable to instance " + clazz + " for element " + name + ", no zero parameter constructor?", e);
    }
  }
  






  private Object typeValue(String value, Class clazz)
    throws SlickXMLException
  {
    if (clazz == String.class) {
      return value;
    }
    try
    {
      clazz = mapPrimitive(clazz);
      return clazz.getConstructor(new Class[] { String.class }).newInstance(new Object[] { value });
    } catch (Exception e) {
      throw new SlickXMLException("Failed to convert: " + value + " to the expected primitive type: " + clazz, e);
    }
  }
  





  private Class mapPrimitive(Class clazz)
  {
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
  








  private Field findField(Class clazz, String name)
  {
    Field[] fields = clazz.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
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
  








  private Method findMethod(Class clazz, String name)
  {
    Method[] methods = clazz.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equalsIgnoreCase(name)) {
        Method method = methods[i];
        Class[] params = method.getParameterTypes();
        
        if (params.length == 1) {
          return method;
        }
      }
    }
    
    return null;
  }
  







  private Method findMethod(Class clazz, String name, Class parameter)
  {
    Method[] methods = clazz.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equalsIgnoreCase(name)) {
        Method method = methods[i];
        Class[] params = method.getParameterTypes();
        
        if ((params.length == 1) && 
          (method.getParameterTypes()[0].isAssignableFrom(parameter))) {
          return method;
        }
      }
    }
    

    return null;
  }
  





  private void setField(Field field, Object instance, Object value)
    throws SlickXMLException
  {
    try
    {
      field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalArgumentException e) {
      throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", e);
    } catch (IllegalAccessException e) {
      throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", e);
    } finally {
      field.setAccessible(false);
    }
  }
  





  private void invoke(Method method, Object instance, Object[] params)
    throws SlickXMLException
  {
    try
    {
      method.setAccessible(true);
      method.invoke(instance, params);
    } catch (IllegalArgumentException e) {
      throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
    } catch (IllegalAccessException e) {
      throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
    } catch (InvocationTargetException e) {
      throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", e);
    } finally {
      method.setAccessible(false);
    }
  }
  







  private Method getMethod(Class clazz, String name, Class[] params)
  {
    try
    {
      return clazz.getMethod(name, params);
    } catch (SecurityException e) {
      return null;
    } catch (NoSuchMethodException e) {}
    return null;
  }
}
