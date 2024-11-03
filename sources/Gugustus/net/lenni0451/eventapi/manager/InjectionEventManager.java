package net.lenni0451.eventapi.manager;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.injection.IInjectionPipeline;
import net.lenni0451.eventapi.injection.IReflectedListener;
import net.lenni0451.eventapi.listener.IErrorListener;
import net.lenni0451.eventapi.listener.IEventListener;
import net.lenni0451.eventapi.reflection.EventTarget;

public class InjectionEventManager {
   private static final Map<Class<? extends IEvent>, IInjectionPipeline> EVENT_PIPELINE = new ConcurrentHashMap<>();
   private static final Map<Class<? extends IEvent>, IEventListener[]> EVENT_LISTENER = new ConcurrentHashMap<>();
   private static final List<IErrorListener> ERROR_LISTENER = new CopyOnWriteArrayList<>();

   public static IEventListener[] getListener(Class<? extends IEvent> eventType) {
      return EVENT_LISTENER.get(eventType);
   }

   public static void call(IEvent event) {
      if (event != null && EVENT_PIPELINE.containsKey(event.getClass())) {
         try {
            EVENT_PIPELINE.get(event.getClass()).call(event);
            if (EVENT_PIPELINE.containsKey(IEvent.class)) {
               EVENT_PIPELINE.get(IEvent.class).call(event);
            }
         } catch (Throwable var2) {
            if (ERROR_LISTENER.isEmpty()) {
               throw new RuntimeException(var2);
            }

            ERROR_LISTENER.forEach(errorListener -> errorListener.catchException(var2));
         }
      }
   }

   public static <T extends IEventListener> void register(T listener) {
      register(IEvent.class, listener);
   }

   public static void register(Object listener) {
      ClassPool cp = ClassPool.getDefault();

      Method[] var5;
      for(Method method : var5 = listener.getClass().getMethods()) {
         if (method.isAnnotationPresent(EventTarget.class)) {
            Class[] methodArguments = method.getParameterTypes();
            if (methodArguments.length == 1
               && IEvent.class.isAssignableFrom(methodArguments[0])
               && Modifier.isPublic(method.getModifiers())
               && !Modifier.isStatic(method.getModifiers())) {
               method.setAccessible(true);
               Class<? extends IEvent> eventType = methodArguments[0];
               String methodName = method.getName();
               CtClass newListener = cp.makeClass("InjectionListener_" + System.nanoTime());

               try {
                  newListener.addInterface(cp.get(IReflectedListener.class.getName()));
               } catch (Throwable var20) {
                  throw new RuntimeException("Class could not implement IReflectedListener", var20);
               }

               try {
                  newListener.addField(CtField.make("private final " + listener.getClass().getName() + " instance;", newListener));
               } catch (Exception var19) {
                  throw new RuntimeException("Could not add global variables to class", var19);
               }

               try {
                  CtConstructor construct = CtNewConstructor.make(
                     "public " + newListener.getName() + "(" + listener.getClass().getName() + " ob) {this.instance = ob;}", newListener
                  );
                  newListener.addConstructor(construct);
               } catch (Throwable var18) {
                  throw new RuntimeException("Could not create new constructor", var18);
               }

               StringBuilder sourceBuilder = new StringBuilder().append("{");
               sourceBuilder.append("this.instance." + methodName + "((" + eventType.getName() + ") $1);");
               sourceBuilder.append("}");

               try {
                  CtMethod onEventMethod = CtNewMethod.make(
                     CtClass.voidType,
                     cp.get(IEventListener.class.getName()).getDeclaredMethods()[0].getName(),
                     new CtClass[]{cp.get(IEvent.class.getName())},
                     new CtClass[]{cp.get(Throwable.class.getName())},
                     sourceBuilder.toString(),
                     newListener
                  );
                  newListener.addMethod(onEventMethod);
               } catch (Throwable var17) {
                  throw new RuntimeException("Could not create new on event method", var17);
               }

               try {
                  CtMethod onEventMethod = CtNewMethod.make(
                     cp.get(Object.class.getName()),
                     cp.get(IReflectedListener.class.getName()).getDeclaredMethods()[0].getName(),
                     new CtClass[0],
                     new CtClass[0],
                     "{return this.instance;}",
                     newListener
                  );
                  newListener.addMethod(onEventMethod);
               } catch (Exception var16) {
                  throw new RuntimeException("Could not create new get instance method", var16);
               }

               Class<?> newListenerClass;
               try {
                  newListenerClass = newListener.toClass();
               } catch (Throwable var15) {
                  throw new RuntimeException("Could not compile class", var15);
               }

               Object listenerObject;
               try {
                  listenerObject = newListenerClass.getConstructors()[0].newInstance(listener);
               } catch (Throwable var14) {
                  throw new RuntimeException("Could not instantiate new class", var14);
               }

               register(eventType, (IEventListener)listenerObject);
            }
         }
      }
   }

   public static <T extends IEventListener> void register(Class<? extends IEvent> eventType, T listener) {
      IEventListener[] eventListener = (IEventListener[])EVENT_LISTENER.computeIfAbsent(eventType, c -> new IEventListener[0]);
      IEventListener[] newEventListener = new IEventListener[eventListener.length + 1];
      EVENT_LISTENER.put(eventType, newEventListener);

      for(int i = 0; i <= eventListener.length; ++i) {
         if (i != eventListener.length) {
            newEventListener[i] = eventListener[i];
         } else {
            newEventListener[i] = listener;
         }
      }

      EVENT_PIPELINE.put(eventType, rebuildPipeline(newEventListener));
   }

   public static void unregister(Object listener) {
      for(Entry<Class<? extends IEvent>, IEventListener[]> entry : EVENT_LISTENER.entrySet()) {
         IEventListener[] eventListener = (IEventListener[])EVENT_LISTENER.computeIfAbsent(entry.getKey(), c -> new IEventListener[0]);
         IEventListener[] newEventListener = new IEventListener[eventListener.length - 1];
         entry.setValue(newEventListener);
         int i = 0;

         for(int x = 0; i < eventListener.length; ++i) {
            if (!eventListener[i].equals(listener)
               && eventListener[i] instanceof IReflectedListener
               && !((IReflectedListener)eventListener[i]).getInstance().equals(listener)) {
               newEventListener[x] = eventListener[i];
               ++x;
            }
         }

         EVENT_PIPELINE.put(entry.getKey(), rebuildPipeline(newEventListener));
      }
   }

   public static void addErrorListener(IErrorListener errorListener) {
      if (!ERROR_LISTENER.contains(errorListener)) {
         ERROR_LISTENER.add(errorListener);
      }
   }

   public static boolean removeErrorListener(IErrorListener errorListener) {
      return ERROR_LISTENER.remove(errorListener);
   }

   private static IInjectionPipeline rebuildPipeline(IEventListener[] eventListener) {
      ClassPool cp = ClassPool.getDefault();
      String methodName = null;

      try {
         CtMethod[] pipelineClass;
         for(CtMethod method : pipelineClass = cp.get(InjectionEventManager.class.getName()).getDeclaredMethods()) {
            if (method.getReturnType().getSimpleName().equals(cp.get(IEventListener[].class.getName()).getSimpleName())) {
               methodName = method.getName();
               break;
            }
         }

         if (methodName == null) {
            throw new NullPointerException();
         }
      } catch (Throwable var15) {
         throw new IllegalStateException("Could not find method name to get listener array", var15);
      }

      StringBuilder sourceBuilder = new StringBuilder().append("{");
      sourceBuilder.append(IEventListener.class.getName() + "[] listener = " + InjectionEventManager.class.getName() + "." + methodName + "($1.getClass());");

      for(int i = 0; i < eventListener.length; ++i) {
         try {
            sourceBuilder.append("listener[" + i + "]." + cp.get(IEventListener.class.getName()).getDeclaredMethods()[0].getName() + "($1);");
         } catch (NotFoundException var14) {
            if (ERROR_LISTENER.isEmpty()) {
               throw new RuntimeException(var14);
            }

            ERROR_LISTENER.forEach(errorListener -> errorListener.catchException(var14));
         }
      }

      sourceBuilder.append("}");
      CtClass newPipeline = cp.makeClass("InjectionPipeline_" + System.nanoTime());

      try {
         newPipeline.addInterface(cp.get(IInjectionPipeline.class.getName()));
      } catch (Throwable var13) {
         throw new RuntimeException("Class could not implement IInjectionPipeline", var13);
      }

      CtMethod method;
      try {
         method = CtNewMethod.make(
            CtClass.voidType,
            cp.get(IInjectionPipeline.class.getName()).getDeclaredMethods()[0].getName(),
            new CtClass[]{cp.get(IEvent.class.getName())},
            new CtClass[]{cp.get(Throwable.class.getName())},
            sourceBuilder.toString(),
            newPipeline
         );
      } catch (Throwable var12) {
         throw new RuntimeException("Could not create new call method", var12);
      }

      try {
         newPipeline.addMethod(method);
      } catch (Throwable var11) {
         throw new RuntimeException("Could not add call method to class", var11);
      }

      Class<? extends IInjectionPipeline> pipelineClass;
      try {
         pipelineClass = (Class<? extends IInjectionPipeline>) newPipeline.toClass();
      } catch (Throwable var10) {
         throw new RuntimeException("Could not compile class", var10);
      }

      try {
         return pipelineClass.newInstance();
      } catch (Throwable var9) {
         throw new RuntimeException("Could not instantiate new class", var9);
      }
   }
}
