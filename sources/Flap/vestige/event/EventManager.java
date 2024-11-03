package vestige.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import vestige.Flap;

public class EventManager {
   private final ArrayList<Object> listeningObjects = new ArrayList();
   private final CopyOnWriteArrayList<EventManager.ListeningMethod> listeningMethods = new CopyOnWriteArrayList();

   public void register(Object o) {
      if (!this.listeningObjects.contains(o)) {
         this.listeningObjects.add(o);
      }

      this.updateListeningMethods();
   }

   public void unregister(Object o) {
      if (this.listeningObjects.contains(o)) {
         this.listeningObjects.remove(o);
      }

      this.updateListeningMethods();
   }

   private void updateListeningMethods() {
      this.listeningMethods.clear();
      this.listeningObjects.forEach((o) -> {
         Arrays.stream(o.getClass().getMethods()).filter((m) -> {
            return m.isAnnotationPresent(Listener.class) && m.getParameters().length == 1;
         }).forEach((m) -> {
            this.listeningMethods.add(new EventManager.ListeningMethod(m, o));
         });
      });
      this.listeningMethods.sort(Comparator.comparingInt((m) -> {
         return ((Listener)m.method.getAnnotation(Listener.class)).value();
      }));
   }

   public void pre(Event e) {
      if (!Flap.instance.isDestructed()) {
         this.listeningMethods.forEach((m) -> {
            Arrays.stream(m.method.getParameters()).filter((p) -> {
               return p.getType().equals(e.getClass());
            }).forEach((p) -> {
               try {
                  m.method.invoke(m.instance, e);
               } catch (InvocationTargetException | IllegalAccessException var4) {
                  throw new RuntimeException(var4);
               }
            });
         });
      }
   }

   public void post(Event e) {
      if (!Flap.instance.isDestructed()) {
         this.listeningMethods.forEach((m) -> {
            Arrays.stream(m.method.getParameters()).filter((p) -> {
               return p.getType().equals(e.getClass());
            }).forEach((p) -> {
               try {
                  m.method.invoke(m.instance, e);
               } catch (InvocationTargetException | IllegalAccessException var4) {
                  throw new RuntimeException(var4);
               }
            });
         });
      }
   }

   private class ListeningMethod {
      private final Method method;
      private final Object instance;

      private ListeningMethod(Method param2, Object param3) {
         this.method = param2;
         this.instance = param3;
      }

      // $FF: synthetic method
      ListeningMethod(Method x1, Object x2, Object x3) {
         this(x1, x2);
      }
   }
}
