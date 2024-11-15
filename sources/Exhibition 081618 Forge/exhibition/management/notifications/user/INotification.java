package exhibition.management.notifications.user;

public interface INotification {
   String getHeader();

   String getSubtext();

   long getStart();

   long getDisplayTime();

   Notifications.Type getType();

   float getX();

   float getTarX();

   float getTarY();

   void setX(int var1);

   void setTarX(int var1);

   void setY(int var1);

   long checkTime();

   float getY();

   long getLast();

   void setLast(long var1);
}
