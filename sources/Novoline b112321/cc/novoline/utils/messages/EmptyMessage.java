package cc.novoline.utils.messages;

import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import net.Ux;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

final class EmptyMessage extends Ux {
   private static EmptyMessage INSTANCE;

   static EmptyMessage get() {
      int var0 = Ux.a();
      if(INSTANCE == null) {
         INSTANCE = new EmptyMessage();
      }

      return INSTANCE;
   }

   public IChatComponent appendSibling(IChatComponent var1) {
      throw new UnsupportedOperationException();
   }

   public IChatComponent appendText(String var1) {
      throw new UnsupportedOperationException();
   }

   public IChatComponent setChatStyle(ChatStyle var1) {
      throw new UnsupportedOperationException();
   }

   public Spliterator spliterator() {
      return Spliterators.emptySpliterator();
   }

   public Iterator iterator() {
      return Iterators.emptyIterator();
   }

   public String toString() {
      return "Message{}";
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
