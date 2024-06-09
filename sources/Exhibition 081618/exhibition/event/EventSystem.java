package exhibition.event;

import exhibition.event.impl.EventAttack;
import exhibition.event.impl.EventBlockBounds;
import exhibition.event.impl.EventChat;
import exhibition.event.impl.EventDamageBlock;
import exhibition.event.impl.EventDeath;
import exhibition.event.impl.EventKeyPress;
import exhibition.event.impl.EventLiquidCollide;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMouse;
import exhibition.event.impl.EventMove;
import exhibition.event.impl.EventNametagRender;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventPushBlock;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderEntity;
import exhibition.event.impl.EventRenderGui;
import exhibition.event.impl.EventScreenDisplay;
import exhibition.event.impl.EventStep;
import exhibition.event.impl.EventTick;
import exhibition.event.impl.EventVelocity;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EventSystem {
   private static final HashMap registry = new HashMap();
   private static final HashMap instances = new HashMap();

   public static void register(EventListener listener) {
      if (!registry.containsKey(listener)) {
         List events = getEvents(listener);
         Iterator var2 = events.iterator();

         while(var2.hasNext()) {
            Event event = (Event)var2.next();
            EventSubscription subscription;
            if (isEventRegistered(event)) {
               subscription = (EventSubscription)registry.get(event);
               subscription.add(listener);
            } else {
               subscription = new EventSubscription(event);
               subscription.add(listener);
               registry.put(event, subscription);
            }
         }

      }
   }

   public static void unregister(EventListener listener) {
      List events = getEvents(listener);
      Iterator var2 = events.iterator();

      while(var2.hasNext()) {
         Event event = (Event)var2.next();
         if (isEventRegistered(event)) {
            EventSubscription sub = (EventSubscription)registry.get(event);
            sub.remove(listener);
         }
      }

   }

   public static Event fire(Event event) {
      EventSubscription subscription = (EventSubscription)registry.get(event);
      if (subscription != null) {
         subscription.fire(event);
      }

      return event;
   }

   public static Event getInstance(Class eventClass) {
      return (Event)instances.get(eventClass);
   }

   private static List getEvents(EventListener listener) {
      ArrayList events = new ArrayList();
      Method[] var2 = listener.getClass().getDeclaredMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         if (method.isAnnotationPresent(RegisterEvent.class)) {
            RegisterEvent ireg = (RegisterEvent)method.getAnnotation(RegisterEvent.class);
            Class[] var7 = ireg.events();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Class eventClass = var7[var9];
               events.add(getInstance(eventClass));
            }
         }
      }

      return events;
   }

   private static boolean isEventRegistered(Event event) {
      return registry.containsKey(event);
   }

   static {
      instances.put(EventLiquidCollide.class, new EventLiquidCollide());
      instances.put(EventStep.class, new EventStep());
      instances.put(EventDamageBlock.class, new EventDamageBlock());
      instances.put(EventPushBlock.class, new EventPushBlock());
      instances.put(EventTick.class, new EventTick());
      instances.put(EventDeath.class, new EventDeath());
      instances.put(EventMouse.class, new EventMouse());
      instances.put(EventRender3D.class, new EventRender3D());
      instances.put(EventRenderGui.class, new EventRenderGui());
      instances.put(EventScreenDisplay.class, new EventScreenDisplay());
      instances.put(EventAttack.class, new EventAttack());
      instances.put(EventPacket.class, new EventPacket());
      instances.put(EventVelocity.class, new EventVelocity());
      instances.put(EventMotionUpdate.class, new EventMotionUpdate());
      instances.put(EventChat.class, new EventChat());
      instances.put(EventBlockBounds.class, new EventBlockBounds());
      instances.put(EventNametagRender.class, new EventNametagRender());
      instances.put(EventMove.class, new EventMove());
      instances.put(EventKeyPress.class, new EventKeyPress());
      instances.put(EventRenderEntity.class, new EventRenderEntity());
   }
}
