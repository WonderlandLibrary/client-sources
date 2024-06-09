package com.darkmagician6.eventapi;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.EventStoppable;
import com.darkmagician6.eventapi.types.Priority;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;








public final class EventManager
{
  private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap();
  





  private EventManager() {}
  




  public static void register(Object object)
  {
    for (Method method : object.getClass().getDeclaredMethods()) {
      if (!isMethodBad(method))
      {


        register(method, object);
      }
    }
  }
  







  public static void register(Object object, Class<? extends Event> eventClass)
  {
    for (Method method : object.getClass().getDeclaredMethods()) {
      if (!isMethodBad(method, eventClass))
      {


        register(method, object);
      }
    }
  }
  


  public static void unregister(Object object)
  {
    Iterator localIterator2;
    
    for (Iterator localIterator1 = REGISTRY_MAP.values().iterator(); localIterator1.hasNext(); 
        localIterator2.hasNext())
    {
      List<MethodData> dataList = (List)localIterator1.next();
      localIterator2 = dataList.iterator(); continue;MethodData data = (MethodData)localIterator2.next();
      if (data.getSource().equals(object)) {
        dataList.remove(data);
      }
    }
    

    cleanMap(true);
  }
  







  public static void unregister(Object object, Class<? extends Event> eventClass)
  {
    if (REGISTRY_MAP.containsKey(eventClass)) {
      for (MethodData data : (List)REGISTRY_MAP.get(eventClass)) {
        if (data.getSource().equals(object)) {
          ((List)REGISTRY_MAP.get(eventClass)).remove(data);
        }
      }
      
      cleanMap(true);
    }
  }
  











  private static void register(Method method, Object object)
  {
    Class<? extends Event> indexClass = method.getParameterTypes()[0];
    
    MethodData data = new MethodData(object, method, ((EventTarget)method.getAnnotation(EventTarget.class)).value());
    

    if (!data.getTarget().isAccessible()) {
      data.getTarget().setAccessible(true);
    }
    
    if (REGISTRY_MAP.containsKey(indexClass)) {
      if (!((List)REGISTRY_MAP.get(indexClass)).contains(data)) {
        ((List)REGISTRY_MAP.get(indexClass)).add(data);
        sortListValue(indexClass);
      }
    } else {
      REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList()
      {
        private static final long serialVersionUID = 666L;
      });
    }
  }
  







  public static void removeEntry(Class<? extends Event> indexClass)
  {
    Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
    
    while (mapIterator.hasNext()) {
      if (((Class)((Map.Entry)mapIterator.next()).getKey()).equals(indexClass)) {
        mapIterator.remove();
        break;
      }
    }
  }
  






  public static void cleanMap(boolean onlyEmptyEntries)
  {
    Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
    
    while (mapIterator.hasNext()) {
      if ((!onlyEmptyEntries) || (((List)((Map.Entry)mapIterator.next()).getValue()).isEmpty())) {
        mapIterator.remove();
      }
    }
  }
  





  private static void sortListValue(Class<? extends Event> indexClass)
  {
    List<MethodData> sortedList = new CopyOnWriteArrayList();
    
    for (byte priority : Priority.VALUE_ARRAY) {
      for (MethodData data : (List)REGISTRY_MAP.get(indexClass)) {
        if (data.getPriority() == priority) {
          sortedList.add(data);
        }
      }
    }
    

    REGISTRY_MAP.put(indexClass, sortedList);
  }
  










  private static boolean isMethodBad(Method method)
  {
    return (method.getParameterTypes().length != 1) || (!method.isAnnotationPresent(EventTarget.class));
  }
  












  private static boolean isMethodBad(Method method, Class<? extends Event> eventClass)
  {
    return (isMethodBad(method)) || (!method.getParameterTypes()[0].equals(eventClass));
  }
  














  public static final Event call(Event event)
  {
    List<MethodData> dataList = (List)REGISTRY_MAP.get(event.getClass());
    
    if (dataList != null) { MethodData data;
      if ((event instanceof EventStoppable)) {
        EventStoppable stoppable = (EventStoppable)event;
        
        for (Iterator localIterator = dataList.iterator(); localIterator.hasNext();) { data = (MethodData)localIterator.next();
          invoke(data, event);
          
          if (stoppable.isStopped()) {
            break;
          }
        }
      } else {
        for (MethodData data : dataList) {
          invoke(data, event);
        }
      }
    }
    
    return event;
  }
  








  private static void invoke(MethodData data, Event argument)
  {
    try
    {
      data.getTarget().invoke(data.getSource(), new Object[] { argument });
    }
    catch (IllegalAccessException localIllegalAccessException) {}catch (IllegalArgumentException localIllegalArgumentException) {}catch (InvocationTargetException localInvocationTargetException) {}
  }
  





  private static final class MethodData
  {
    private final Object source;
    




    private final Method target;
    




    private final byte priority;
    





    public MethodData(Object source, Method target, byte priority)
    {
      this.source = source;
      this.target = target;
      this.priority = priority;
    }
    




    public Object getSource()
    {
      return source;
    }
    




    public Method getTarget()
    {
      return target;
    }
    




    public byte getPriority()
    {
      return priority;
    }
  }
}
