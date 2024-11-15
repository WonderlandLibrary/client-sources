package exhibition.util;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;

public class MouseUtil {
   private static Field buttonStateField;
   private static Field buttonField;
   private static Field buttonsField;

   public static void sendClick(int button, boolean state) {
      MouseEvent mouseEvent = new MouseEvent();
      buttonField.setAccessible(true);

      try {
         buttonField.set(mouseEvent, button);
      } catch (IllegalAccessException var6) {
         var6.printStackTrace();
      }

      buttonField.setAccessible(false);
      buttonStateField.setAccessible(true);

      try {
         buttonStateField.set(mouseEvent, state);
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      buttonStateField.setAccessible(false);
      MinecraftForge.EVENT_BUS.post(mouseEvent);

      try {
         buttonsField.setAccessible(true);
         ByteBuffer buffer = (ByteBuffer)buttonsField.get((Object)null);
         buttonsField.setAccessible(false);
         buffer.put(button, (byte)(state ? 1 : 0));
      } catch (IllegalAccessException var4) {
         var4.printStackTrace();
      }

   }

   static {
      try {
         buttonField = MouseEvent.class.getDeclaredField("button");
      } catch (NoSuchFieldException var3) {
         var3.printStackTrace();
      }

      try {
         buttonStateField = MouseEvent.class.getDeclaredField("buttonstate");
      } catch (NoSuchFieldException var2) {
         var2.printStackTrace();
      }

      try {
         buttonsField = Mouse.class.getDeclaredField("buttons");
      } catch (NoSuchFieldException var1) {
         var1.printStackTrace();
      }

   }
}
