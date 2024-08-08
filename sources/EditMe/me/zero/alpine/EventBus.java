package me.zero.alpine;

public interface EventBus {
   void subscribe(Object var1);

   void subscribe(Object... var1);

   void subscribe(Iterable var1);

   void unsubscribe(Object var1);

   void unsubscribe(Object... var1);

   void unsubscribe(Iterable var1);

   void post(Object var1);

   void attach(EventBus var1);

   void detach(EventBus var1);
}
