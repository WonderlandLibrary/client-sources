package org.spongepowered.tools.obfuscation.struct;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

public class InjectorRemap {
   private final boolean remap;
   private Message message;
   private int remappedCount;

   public InjectorRemap(boolean var1) {
      this.remap = var1;
   }

   public boolean shouldRemap() {
      return this.remap;
   }

   public void notifyRemapped() {
      ++this.remappedCount;
      this.clearMessage();
   }

   public void addMessage(Kind var1, CharSequence var2, Element var3, AnnotationHandle var4) {
      this.message = new Message(var1, var2, var3, var4);
   }

   public void clearMessage() {
      this.message = null;
   }

   public void dispatchPendingMessages(Messager var1) {
      if (this.remappedCount == 0 && this.message != null) {
         this.message.sendTo(var1);
      }

   }
}
