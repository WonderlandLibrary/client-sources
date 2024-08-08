package javassist.expr;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;

public class ExprEditor {
   public boolean doit(CtClass var1, MethodInfo var2) throws CannotCompileException {
      CodeAttribute var3 = var2.getCodeAttribute();
      if (var3 == null) {
         return false;
      } else {
         CodeIterator var4 = var3.iterator();
         boolean var5 = false;
         ExprEditor.LoopContext var6 = new ExprEditor.LoopContext(var3.getMaxLocals());

         while(var4.hasNext()) {
            if (this.loopBody(var4, var1, var2, var6)) {
               var5 = true;
            }
         }

         ExceptionTable var7 = var3.getExceptionTable();
         int var8 = var7.size();

         for(int var9 = 0; var9 < var8; ++var9) {
            Handler var10 = new Handler(var7, var9, var4, var1, var2);
            this.edit(var10);
            if (var10.edited()) {
               var5 = true;
               var6.updateMax(var10.locals(), var10.stack());
            }
         }

         if (var3.getMaxLocals() < var6.maxLocals) {
            var3.setMaxLocals(var6.maxLocals);
         }

         var3.setMaxStack(var3.getMaxStack() + var6.maxStack);

         try {
            if (var5) {
               var2.rebuildStackMapIf6(var1.getClassPool(), var1.getClassFile2());
            }

            return var5;
         } catch (BadBytecode var11) {
            throw new CannotCompileException(var11.getMessage(), var11);
         }
      }
   }

   boolean doit(CtClass var1, MethodInfo var2, ExprEditor.LoopContext var3, CodeIterator var4, int var5) throws CannotCompileException {
      boolean var6 = false;

      while(var4.hasNext() && var4.lookAhead() < var5) {
         int var7 = var4.getCodeLength();
         if (this.loopBody(var4, var1, var2, var3)) {
            var6 = true;
            int var8 = var4.getCodeLength();
            if (var7 != var8) {
               var5 += var8 - var7;
            }
         }
      }

      return var6;
   }

   final boolean loopBody(CodeIterator var1, CtClass var2, MethodInfo var3, ExprEditor.LoopContext var4) throws CannotCompileException {
      try {
         Object var5 = null;
         int var6 = var1.next();
         int var7 = var1.byteAt(var6);
         if (var7 >= 178) {
            if (var7 < 188) {
               if (var7 != 184 && var7 != 185 && var7 != 182) {
                  if (var7 != 180 && var7 != 178 && var7 != 181 && var7 != 179) {
                     if (var7 == 187) {
                        int var8 = var1.u16bitAt(var6 + 1);
                        var4.newList = new ExprEditor.NewOp(var4.newList, var6, var3.getConstPool().getClassInfo(var8));
                     } else if (var7 == 183) {
                        ExprEditor.NewOp var12 = var4.newList;
                        if (var12 != null && var3.getConstPool().isConstructor(var12.type, var1.u16bitAt(var6 + 1)) > 0) {
                           var5 = new NewExpr(var6, var1, var2, var3, var12.type, var12.pos);
                           this.edit((NewExpr)var5);
                           var4.newList = var12.next;
                        } else {
                           MethodCall var9 = new MethodCall(var6, var1, var2, var3);
                           if (var9.getMethodName().equals("<init>")) {
                              ConstructorCall var10 = new ConstructorCall(var6, var1, var2, var3);
                              var5 = var10;
                              this.edit(var10);
                           } else {
                              var5 = var9;
                              this.edit(var9);
                           }
                        }
                     }
                  } else {
                     var5 = new FieldAccess(var6, var1, var2, var3, var7);
                     this.edit((FieldAccess)var5);
                  }
               } else {
                  var5 = new MethodCall(var6, var1, var2, var3);
                  this.edit((MethodCall)var5);
               }
            } else if (var7 != 188 && var7 != 189 && var7 != 197) {
               if (var7 == 193) {
                  var5 = new Instanceof(var6, var1, var2, var3);
                  this.edit((Instanceof)var5);
               } else if (var7 == 192) {
                  var5 = new Cast(var6, var1, var2, var3);
                  this.edit((Cast)var5);
               }
            } else {
               var5 = new NewArray(var6, var1, var2, var3, var7);
               this.edit((NewArray)var5);
            }
         }

         if (var5 != null && ((Expr)var5).edited()) {
            var4.updateMax(((Expr)var5).locals(), ((Expr)var5).stack());
            return true;
         } else {
            return false;
         }
      } catch (BadBytecode var11) {
         throw new CannotCompileException(var11);
      }
   }

   public void edit(NewExpr var1) throws CannotCompileException {
   }

   public void edit(NewArray var1) throws CannotCompileException {
   }

   public void edit(MethodCall var1) throws CannotCompileException {
   }

   public void edit(ConstructorCall var1) throws CannotCompileException {
   }

   public void edit(FieldAccess var1) throws CannotCompileException {
   }

   public void edit(Instanceof var1) throws CannotCompileException {
   }

   public void edit(Cast var1) throws CannotCompileException {
   }

   public void edit(Handler var1) throws CannotCompileException {
   }

   static final class LoopContext {
      ExprEditor.NewOp newList;
      int maxLocals;
      int maxStack;

      LoopContext(int var1) {
         this.maxLocals = var1;
         this.maxStack = 0;
         this.newList = null;
      }

      void updateMax(int var1, int var2) {
         if (this.maxLocals < var1) {
            this.maxLocals = var1;
         }

         if (this.maxStack < var2) {
            this.maxStack = var2;
         }

      }
   }

   static final class NewOp {
      ExprEditor.NewOp next;
      int pos;
      String type;

      NewOp(ExprEditor.NewOp var1, int var2, String var3) {
         this.next = var1;
         this.pos = var2;
         this.type = var3;
      }
   }
}
