/*   1:    */ package me.connorm.lib;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Map.Entry;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*  13:    */ import me.connorm.lib.event.Event;
/*  14:    */ import me.connorm.lib.event.EventStoppable;
/*  15:    */ import me.connorm.lib.types.Priority;
/*  16:    */ 
/*  17:    */ public final class EventManager
/*  18:    */ {
/*  19: 21 */   private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap();
/*  20:    */   
/*  21:    */   public static void register(Object object)
/*  22:    */   {
/*  23: 37 */     for (Method method : object.getClass().getDeclaredMethods()) {
/*  24: 39 */       if (!isMethodBad(method)) {
/*  25: 44 */         register(method, object);
/*  26:    */       }
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void register(Object object, Class<? extends Event> eventClass)
/*  31:    */   {
/*  32: 59 */     for (Method method : object.getClass().getDeclaredMethods()) {
/*  33: 61 */       if (!isMethodBad(method, eventClass)) {
/*  34: 66 */         register(method, object);
/*  35:    */       }
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void unregister(Object object)
/*  40:    */   {
/*  41:    */     Iterator localIterator2;
/*  42: 78 */     for (Iterator localIterator1 = REGISTRY_MAP.values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  43:    */     {
/*  44: 78 */       List<MethodData> dataList = (List)localIterator1.next();
/*  45:    */       
/*  46: 80 */       localIterator2 = dataList.iterator(); continue;MethodData data = (MethodData)localIterator2.next();
/*  47: 82 */       if (data.getSource().equals(object)) {
/*  48: 84 */         dataList.remove(data);
/*  49:    */       }
/*  50:    */     }
/*  51: 89 */     cleanMap(true);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void unregister(Object object, Class<? extends Event> eventClass)
/*  55:    */   {
/*  56:102 */     if (REGISTRY_MAP.containsKey(eventClass))
/*  57:    */     {
/*  58:104 */       for (MethodData data : (List)REGISTRY_MAP.get(eventClass)) {
/*  59:106 */         if (data.getSource().equals(object)) {
/*  60:108 */           ((List)REGISTRY_MAP.get(eventClass)).remove(data);
/*  61:    */         }
/*  62:    */       }
/*  63:112 */       cleanMap(true);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static void register(Method method, Object object)
/*  68:    */   {
/*  69:130 */     Class<? extends Event> indexClass = method.getParameterTypes()[0];
/*  70:    */     
/*  71:132 */     MethodData data = new MethodData(object, method, ((EventTarget)method.getAnnotation(EventTarget.class)).value());
/*  72:135 */     if (!data.getTarget().isAccessible()) {
/*  73:137 */       data.getTarget().setAccessible(true);
/*  74:    */     }
/*  75:140 */     if (REGISTRY_MAP.containsKey(indexClass))
/*  76:    */     {
/*  77:142 */       if (!((List)REGISTRY_MAP.get(indexClass)).contains(data))
/*  78:    */       {
/*  79:144 */         ((List)REGISTRY_MAP.get(indexClass)).add(data);
/*  80:145 */         sortListValue(indexClass);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     else {
/*  84:149 */       REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList()
/*  85:    */       {
/*  86:    */         private static final long serialVersionUID = 666L;
/*  87:    */       });
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static void removeEntry(Class<? extends Event> indexClass)
/*  92:    */   {
/*  93:168 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*  94:170 */     while (mapIterator.hasNext()) {
/*  95:172 */       if (((Class)((Map.Entry)mapIterator.next()).getKey()).equals(indexClass))
/*  96:    */       {
/*  97:174 */         mapIterator.remove();
/*  98:175 */         break;
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static void cleanMap(boolean onlyEmptyEntries)
/* 104:    */   {
/* 105:189 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/* 106:191 */     while (mapIterator.hasNext()) {
/* 107:193 */       if ((!onlyEmptyEntries) || (((List)((Map.Entry)mapIterator.next()).getValue()).isEmpty())) {
/* 108:195 */         mapIterator.remove();
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private static void sortListValue(Class<? extends Event> indexClass)
/* 114:    */   {
/* 115:208 */     List<MethodData> sortedList = new CopyOnWriteArrayList();
/* 116:210 */     for (byte priority : Priority.VALUE_ARRAY) {
/* 117:212 */       for (MethodData data : (List)REGISTRY_MAP.get(indexClass)) {
/* 118:214 */         if (data.getPriority() == priority) {
/* 119:216 */           sortedList.add(data);
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:222 */     REGISTRY_MAP.put(indexClass, sortedList);
/* 124:    */   }
/* 125:    */   
/* 126:    */   private static boolean isMethodBad(Method method)
/* 127:    */   {
/* 128:238 */     return (method.getParameterTypes().length != 1) || (!method.isAnnotationPresent(EventTarget.class));
/* 129:    */   }
/* 130:    */   
/* 131:    */   private static boolean isMethodBad(Method method, Class<? extends Event> eventClass)
/* 132:    */   {
/* 133:256 */     return (isMethodBad(method)) || (!method.getParameterTypes()[0].equals(eventClass));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static final Event call(Event event)
/* 137:    */   {
/* 138:276 */     List<MethodData> dataList = (List)REGISTRY_MAP.get(event.getClass());
/* 139:278 */     if (dataList != null)
/* 140:    */     {
/* 141:    */       MethodData data;
/* 142:280 */       if ((event instanceof EventStoppable))
/* 143:    */       {
/* 144:282 */         EventStoppable stoppable = (EventStoppable)event;
/* 145:284 */         for (Iterator localIterator = dataList.iterator(); localIterator.hasNext();)
/* 146:    */         {
/* 147:284 */           data = (MethodData)localIterator.next();
/* 148:    */           
/* 149:286 */           invoke(data, event);
/* 150:288 */           if (stoppable.isStopped()) {
/* 151:    */             break;
/* 152:    */           }
/* 153:    */         }
/* 154:    */       }
/* 155:    */       else
/* 156:    */       {
/* 157:295 */         for (MethodData data : dataList) {
/* 158:297 */           invoke(data, event);
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:302 */     return event;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private static void invoke(MethodData data, Event argument)
/* 166:    */   {
/* 167:    */     try
/* 168:    */     {
/* 169:319 */       data.getTarget().invoke(data.getSource(), new Object[] { argument });
/* 170:    */     }
/* 171:    */     catch (IllegalAccessException localIllegalAccessException) {}catch (IllegalArgumentException localIllegalArgumentException) {}catch (InvocationTargetException localInvocationTargetException) {}
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static final class MethodData
/* 175:    */   {
/* 176:    */     private final Object source;
/* 177:    */     private final Method target;
/* 178:    */     private final byte priority;
/* 179:    */     
/* 180:    */     public MethodData(Object source, Method target, byte priority)
/* 181:    */     {
/* 182:354 */       this.source = source;
/* 183:355 */       this.target = target;
/* 184:356 */       this.priority = priority;
/* 185:    */     }
/* 186:    */     
/* 187:    */     public Object getSource()
/* 188:    */     {
/* 189:366 */       return this.source;
/* 190:    */     }
/* 191:    */     
/* 192:    */     public Method getTarget()
/* 193:    */     {
/* 194:376 */       return this.target;
/* 195:    */     }
/* 196:    */     
/* 197:    */     public byte getPriority()
/* 198:    */     {
/* 199:386 */       return this.priority;
/* 200:    */     }
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.lib.EventManager
 * JD-Core Version:    0.7.0.1
 */