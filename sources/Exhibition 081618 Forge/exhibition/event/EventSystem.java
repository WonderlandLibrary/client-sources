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
   private static final HashMap REGISTRY = new HashMap();
   private static final HashMap INSTANCES = new HashMap();

   public static void register(EventListener listener) {
      if (!REGISTRY.containsKey(listener)) {
         List events = getEvents(listener);
         Iterator var2 = events.iterator();

         while(var2.hasNext()) {
            Event event = (Event)var2.next();
            EventSubscription subscription;
            if (isEventRegistered(event)) {
               subscription = (EventSubscription)REGISTRY.get(event);
               subscription.add(listener);
            } else {
               subscription = new EventSubscription(event);
               subscription.add(listener);
               REGISTRY.put(event, subscription);
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
            EventSubscription sub = (EventSubscription)REGISTRY.get(event);
            sub.remove(listener);
         }
      }

   }

   public static Event fire(Event event) {
      EventSubscription subscription = (EventSubscription)REGISTRY.get(event);
      if (subscription != null) {
         subscription.fire(event);
      }

      return event;
   }

   public static Event getInstance(Class eventClass) {
      return (Event)INSTANCES.get(eventClass);
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
      return REGISTRY.containsKey(event);
   }

   static {
      INSTANCES.put(EventLiquidCollide.class, new EventLiquidCollide());
      INSTANCES.put(EventStep.class, new EventStep());
      INSTANCES.put(EventDamageBlock.class, new EventDamageBlock());
      INSTANCES.put(EventPushBlock.class, new EventPushBlock());
      INSTANCES.put(EventTick.class, new EventTick());
      INSTANCES.put(EventDeath.class, new EventDeath());
      INSTANCES.put(EventMouse.class, new EventMouse());
      INSTANCES.put(EventRender3D.class, new EventRender3D());
      INSTANCES.put(EventRenderGui.class, new EventRenderGui());
      INSTANCES.put(EventScreenDisplay.class, new EventScreenDisplay());
      INSTANCES.put(EventAttack.class, new EventAttack());
      INSTANCES.put(EventPacket.class, new EventPacket());
      INSTANCES.put(EventVelocity.class, new EventVelocity());
      INSTANCES.put(EventMotionUpdate.class, new EventMotionUpdate());
      INSTANCES.put(EventChat.class, new EventChat());
      INSTANCES.put(EventBlockBounds.class, new EventBlockBounds());
      INSTANCES.put(EventNametagRender.class, new EventNametagRender());
      INSTANCES.put(EventMove.class, new EventMove());
      INSTANCES.put(EventKeyPress.class, new EventKeyPress());
      INSTANCES.put(EventRenderEntity.class, new EventRenderEntity());
   }
}
