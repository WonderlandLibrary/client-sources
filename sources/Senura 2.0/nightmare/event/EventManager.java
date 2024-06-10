/*     */ package nightmare.event;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class EventManager
/*     */ {
/*  11 */   private Map<Class<?>, ArrayHelper<Data>> REGISTRY_MAP = new HashMap<>();
/*     */ 
/*     */   
/*     */   public void register(Object o) {
/*  15 */     for (Method method : o.getClass().getDeclaredMethods()) {
/*  16 */       if (!isMethodBad(method)) {
/*  17 */         register(method, o);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void register(Object o, Class<? extends Event> clazz) {
/*  23 */     for (Method method : o.getClass().getDeclaredMethods()) {
/*  24 */       if (!isMethodBad(method, clazz)) {
/*  25 */         register(method, o);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void register(Method method, Object o) {
/*  32 */     Class<?> clazz = method.getParameterTypes()[0];
/*  33 */     final Data methodData = new Data(o, method, ((EventTarget)method.<EventTarget>getAnnotation(EventTarget.class)).value());
/*     */     
/*  35 */     if (!methodData.target.isAccessible()) {
/*  36 */       methodData.target.setAccessible(true);
/*     */     }
/*     */     
/*  39 */     if (this.REGISTRY_MAP.containsKey(clazz)) {
/*  40 */       if (!((ArrayHelper<Data>)this.REGISTRY_MAP.get(clazz)).contains(methodData)) {
/*  41 */         ((ArrayHelper<Data>)this.REGISTRY_MAP.get(clazz)).add(methodData);
/*  42 */         sortListValue((Class)clazz);
/*     */       } 
/*     */     } else {
/*  45 */       this.REGISTRY_MAP.put(clazz, new ArrayHelper<Data>()
/*     */           {
/*     */           
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(Object o) {
/*  55 */     for (ArrayHelper<Data> flexibalArray : this.REGISTRY_MAP.values()) {
/*  56 */       for (Data methodData : flexibalArray) {
/*  57 */         if (methodData.source.equals(o)) {
/*  58 */           flexibalArray.remove(methodData);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     cleanMap(true);
/*     */   }
/*     */   
/*     */   public void unregister(Object o, Class<? extends Event> clazz) {
/*  67 */     if (this.REGISTRY_MAP.containsKey(clazz)) {
/*  68 */       for (Data methodData : this.REGISTRY_MAP.get(clazz)) {
/*  69 */         if (methodData.source.equals(o)) {
/*  70 */           ((ArrayHelper<Data>)this.REGISTRY_MAP.get(clazz)).remove(methodData);
/*     */         }
/*     */       } 
/*     */       
/*  74 */       cleanMap(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanMap(boolean b) {
/*  80 */     Iterator<Map.Entry<Class<?>, ArrayHelper<Data>>> iterator = this.REGISTRY_MAP.entrySet().iterator();
/*     */     
/*  82 */     while (iterator.hasNext()) {
/*  83 */       if (!b || ((ArrayHelper)((Map.Entry)iterator.next()).getValue()).isEmpty()) {
/*  84 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEnty(Class<? extends Event> clazz) {
/*  91 */     Iterator<Map.Entry<Class<?>, ArrayHelper<Data>>> iterator = this.REGISTRY_MAP.entrySet().iterator();
/*     */     
/*  93 */     while (iterator.hasNext()) {
/*  94 */       if (((Class)((Map.Entry)iterator.next()).getKey()).equals(clazz)) {
/*  95 */         iterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sortListValue(Class<? extends Event> clazz) {
/* 103 */     ArrayHelper<Data> flexibleArray = new ArrayHelper<>();
/*     */     
/* 105 */     for (byte b : Priority.VALUE_ARRAY) {
/* 106 */       for (Data methodData : this.REGISTRY_MAP.get(clazz)) {
/* 107 */         if (methodData.priority == b) {
/* 108 */           flexibleArray.add(methodData);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     this.REGISTRY_MAP.put(clazz, flexibleArray);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMethodBad(Method method) {
/* 118 */     return ((method.getParameterTypes()).length != 1 || !method.isAnnotationPresent((Class)EventTarget.class));
/*     */   }
/*     */   
/*     */   private boolean isMethodBad(Method method, Class<? extends Event> clazz) {
/* 122 */     return (isMethodBad(method) || method.getParameterTypes()[0].equals(clazz));
/*     */   }
/*     */   
/*     */   public ArrayHelper<Data> get(Class<? extends Event> clazz) {
/* 126 */     return this.REGISTRY_MAP.get(clazz);
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 130 */     this.REGISTRY_MAP.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */