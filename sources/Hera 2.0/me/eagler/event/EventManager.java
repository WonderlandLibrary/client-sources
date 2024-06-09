/*     */ package me.eagler.event;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import me.eagler.event.stuff.EventStoppable;
/*     */ import me.eagler.event.stuff.Priority;
/*     */ 
/*     */ public final class EventManager
/*     */ {
/*  15 */   private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, List<MethodData>>();
/*     */   
/*     */   public static void register(Object object) {
/*     */     byte b;
/*     */     int i;
/*     */     Method[] arrayOfMethod;
/*  21 */     for (i = (arrayOfMethod = object.getClass().getDeclaredMethods()).length, b = 0; b < i; ) {
/*  22 */       Method method = arrayOfMethod[b];
/*  23 */       if (!isMethodBad(method))
/*  24 */         register(method, object); 
/*  25 */       b = (byte)(b + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register(Object object, Class<? extends Event> eventClass) {
/*     */     byte b;
/*     */     int i;
/*     */     Method[] arrayOfMethod;
/*  33 */     for (i = (arrayOfMethod = object.getClass().getDeclaredMethods()).length, b = 0; b < i; ) {
/*  34 */       Method method = arrayOfMethod[b];
/*  35 */       if (!isMethodBad(method, eventClass))
/*  36 */         register(method, object); 
/*  37 */       b = (byte)(b + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unregister(Object object) {
/*  42 */     for (List<MethodData> dataList : REGISTRY_MAP.values()) {
/*  43 */       for (MethodData data : dataList) {
/*  44 */         if (data.getSource().equals(object))
/*  45 */           dataList.remove(data); 
/*     */       } 
/*     */     } 
/*  48 */     cleanMap(true);
/*     */   }
/*     */   
/*     */   public static void unregister(Object object, Class<? extends Event> eventClass) {
/*  52 */     if (REGISTRY_MAP.containsKey(eventClass)) {
/*  53 */       for (MethodData data : REGISTRY_MAP.get(eventClass)) {
/*  54 */         if (data.getSource().equals(object))
/*  55 */           ((List)REGISTRY_MAP.get(eventClass)).remove(data); 
/*     */       } 
/*  57 */       cleanMap(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void register(Method method, Object object) {
/*  62 */     Class<? extends Event> indexClass = (Class)method.getParameterTypes()[0];
/*  63 */     MethodData data = new MethodData(object, method, (
/*  64 */         (EventTarget)method.<EventTarget>getAnnotation(EventTarget.class)).value());
/*  65 */     if (!data.getTarget().isAccessible())
/*  66 */       data.getTarget().setAccessible(true); 
/*  67 */     if (REGISTRY_MAP.containsKey(indexClass)) {
/*  68 */       if (!((List)REGISTRY_MAP.get(indexClass)).contains(data)) {
/*  69 */         ((List<MethodData>)REGISTRY_MAP.get(indexClass)).add(data);
/*  70 */         sortListValue(indexClass);
/*     */       } 
/*     */     } else {
/*  73 */       REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>() {
/*     */             private static final long serialVersionUID = 666L;
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void removeEntry(Class<? extends Event> indexClass) {
/*  80 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*  81 */     while (mapIterator.hasNext()) {
/*  82 */       if (((Class)((Map.Entry)mapIterator.next()).getKey()).equals(indexClass)) {
/*  83 */         mapIterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void cleanMap(boolean onlyEmptyEntries) {
/*  90 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*  91 */     while (mapIterator.hasNext()) {
/*  92 */       if (!onlyEmptyEntries || ((List)((Map.Entry)mapIterator.next()).getValue()).isEmpty())
/*  93 */         mapIterator.remove(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void sortListValue(Class<? extends Event> indexClass) {
/*  98 */     List<MethodData> sortedList = new CopyOnWriteArrayList<MethodData>();
/*     */     byte b;
/*     */     int i;
/*     */     byte[] arrayOfByte;
/* 102 */     for (i = (arrayOfByte = Priority.VALUE_ARRAY).length, b = 0; b < i; ) {
/* 103 */       byte priority = arrayOfByte[b];
/* 104 */       for (MethodData data : REGISTRY_MAP.get(indexClass)) {
/* 105 */         if (data.getPriority() == priority)
/* 106 */           sortedList.add(data); 
/*     */       } 
/* 108 */       b = (byte)(b + 1);
/*     */     } 
/* 110 */     REGISTRY_MAP.put(indexClass, sortedList);
/*     */   }
/*     */   
/*     */   private static boolean isMethodBad(Method method) {
/* 114 */     return !((method.getParameterTypes()).length == 1 && method.isAnnotationPresent((Class)EventTarget.class));
/*     */   }
/*     */   
/*     */   private static boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
/* 118 */     return !(!isMethodBad(method) && method.getParameterTypes()[0].equals(eventClass));
/*     */   }
/*     */   
/*     */   public static final Event call(Event event) {
/* 122 */     List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());
/* 123 */     if (dataList != null)
/* 124 */       if (event instanceof EventStoppable) {
/* 125 */         EventStoppable stoppable = (EventStoppable)event;
/* 126 */         for (MethodData data : dataList) {
/* 127 */           invoke(data, event);
/* 128 */           if (stoppable.isStopped())
/*     */             break; 
/*     */         } 
/*     */       } else {
/* 132 */         for (MethodData data : dataList)
/* 133 */           invoke(data, event); 
/*     */       }  
/* 135 */     return event;
/*     */   }
/*     */   
/*     */   private static void invoke(MethodData data, Event argument) {
/*     */     try {
/* 140 */       data.getTarget().invoke(data.getSource(), new Object[] { argument });
/* 141 */     } catch (IllegalAccessException illegalAccessException) {
/*     */     
/* 143 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */     
/* 145 */     } catch (InvocationTargetException invocationTargetException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class MethodData
/*     */   {
/*     */     private final Object source;
/*     */     
/*     */     private final Method target;
/*     */     private final byte priority;
/*     */     
/*     */     public MethodData(Object source, Method target, byte priority) {
/* 157 */       this.source = source;
/* 158 */       this.target = target;
/* 159 */       this.priority = priority;
/*     */     }
/*     */     
/*     */     public Object getSource() {
/* 163 */       return this.source;
/*     */     }
/*     */     
/*     */     public Method getTarget() {
/* 167 */       return this.target;
/*     */     }
/*     */     
/*     */     public byte getPriority() {
/* 171 */       return this.priority;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\event\EventManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */