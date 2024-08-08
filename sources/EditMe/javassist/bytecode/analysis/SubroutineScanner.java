package javassist.bytecode.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

public class SubroutineScanner implements Opcode {
   private Subroutine[] subroutines;
   Map subTable = new HashMap();
   Set done = new HashSet();

   public Subroutine[] scan(MethodInfo var1) throws BadBytecode {
      CodeAttribute var2 = var1.getCodeAttribute();
      CodeIterator var3 = var2.iterator();
      this.subroutines = new Subroutine[var2.getCodeLength()];
      this.subTable.clear();
      this.done.clear();
      this.scan(0, var3, (Subroutine)null);
      ExceptionTable var4 = var2.getExceptionTable();

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         int var6 = var4.handlerPc(var5);
         this.scan(var6, var3, this.subroutines[var4.startPc(var5)]);
      }

      return this.subroutines;
   }

   private void scan(int var1, CodeIterator var2, Subroutine var3) throws BadBytecode {
      if (!this.done.contains(new Integer(var1))) {
         this.done.add(new Integer(var1));
         int var4 = var2.lookAhead();
         var2.move(var1);

         boolean var5;
         do {
            var1 = var2.next();
            var5 = this.scanOp(var1, var2, var3) && var2.hasNext();
         } while(var5);

         var2.move(var4);
      }
   }

   private boolean scanOp(int var1, CodeIterator var2, Subroutine var3) throws BadBytecode {
      this.subroutines[var1] = var3;
      int var4 = var2.byteAt(var1);
      if (var4 == 170) {
         this.scanTableSwitch(var1, var2, var3);
         return false;
      } else if (var4 == 171) {
         this.scanLookupSwitch(var1, var2, var3);
         return false;
      } else if (!Util.isReturn(var4) && var4 != 169 && var4 != 191) {
         if (Util.isJumpInstruction(var4)) {
            int var5 = Util.getJumpTarget(var1, var2);
            if (var4 != 168 && var4 != 201) {
               this.scan(var5, var2, var3);
               if (Util.isGoto(var4)) {
                  return false;
               }
            } else {
               Subroutine var6 = (Subroutine)this.subTable.get(new Integer(var5));
               if (var6 == null) {
                  var6 = new Subroutine(var5, var1);
                  this.subTable.put(new Integer(var5), var6);
                  this.scan(var5, var2, var6);
               } else {
                  var6.addCaller(var1);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void scanLookupSwitch(int var1, CodeIterator var2, Subroutine var3) throws BadBytecode {
      int var4 = (var1 & -4) + 4;
      this.scan(var1 + var2.s32bitAt(var4), var2, var3);
      var4 += 4;
      int var5 = var2.s32bitAt(var4);
      int var10000 = var5 * 8;
      var4 += 4;
      int var6 = var10000 + var4;

      for(var4 += 4; var4 < var6; var4 += 8) {
         int var7 = var2.s32bitAt(var4) + var1;
         this.scan(var7, var2, var3);
      }

   }

   private void scanTableSwitch(int var1, CodeIterator var2, Subroutine var3) throws BadBytecode {
      int var4 = (var1 & -4) + 4;
      this.scan(var1 + var2.s32bitAt(var4), var2, var3);
      var4 += 4;
      int var5 = var2.s32bitAt(var4);
      var4 += 4;
      int var6 = var2.s32bitAt(var4);
      int var10000 = (var6 - var5 + 1) * 4;
      var4 += 4;

      for(int var7 = var10000 + var4; var4 < var7; var4 += 4) {
         int var8 = var2.s32bitAt(var4) + var1;
         this.scan(var8, var2, var3);
      }

   }
}
