package net.augustus.utils;

import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.manager.EventManager;

public class EventHandler {
   public static void call(IEvent event) {
      try {
         EventManager.call(event);
      } catch (Exception var2) {
         System.err.println(var2 + " ErrorEvent");
      }
   }
}
