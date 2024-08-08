package org.spongepowered.asm.lib.tree;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.MethodVisitor;

public class TryCatchBlockNode {
   public LabelNode start;
   public LabelNode end;
   public LabelNode handler;
   public String type;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;

   public TryCatchBlockNode(LabelNode var1, LabelNode var2, LabelNode var3, String var4) {
      this.start = var1;
      this.end = var2;
      this.handler = var3;
      this.type = var4;
   }

   public void updateIndex(int var1) {
      int var2 = 1107296256 | var1 << 8;
      Iterator var3;
      TypeAnnotationNode var4;
      if (this.visibleTypeAnnotations != null) {
         for(var3 = this.visibleTypeAnnotations.iterator(); var3.hasNext(); var4.typeRef = var2) {
            var4 = (TypeAnnotationNode)var3.next();
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         for(var3 = this.invisibleTypeAnnotations.iterator(); var3.hasNext(); var4.typeRef = var2) {
            var4 = (TypeAnnotationNode)var3.next();
         }
      }

   }

   public void accept(MethodVisitor var1) {
      var1.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), this.handler == null ? null : this.handler.getLabel(), this.type);
      int var2 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();

      int var3;
      TypeAnnotationNode var4;
      for(var3 = 0; var3 < var2; ++var3) {
         var4 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var3);
         var4.accept(var1.visitTryCatchAnnotation(var4.typeRef, var4.typePath, var4.desc, true));
      }

      var2 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();

      for(var3 = 0; var3 < var2; ++var3) {
         var4 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var3);
         var4.accept(var1.visitTryCatchAnnotation(var4.typeRef, var4.typePath, var4.desc, false));
      }

   }
}
