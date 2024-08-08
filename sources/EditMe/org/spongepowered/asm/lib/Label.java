package org.spongepowered.asm.lib;

public class Label {
   static final int DEBUG = 1;
   static final int RESOLVED = 2;
   static final int RESIZED = 4;
   static final int PUSHED = 8;
   static final int TARGET = 16;
   static final int STORE = 32;
   static final int REACHABLE = 64;
   static final int JSR = 128;
   static final int RET = 256;
   static final int SUBROUTINE = 512;
   static final int VISITED = 1024;
   static final int VISITED2 = 2048;
   public Object info;
   int status;
   int line;
   int position;
   private int referenceCount;
   private int[] srcAndRefPositions;
   int inputStackTop;
   int outputStackMax;
   Frame frame;
   Label successor;
   Edge successors;
   Label next;

   public int getOffset() {
      if ((this.status & 2) == 0) {
         throw new IllegalStateException("Label offset position has not been resolved yet");
      } else {
         return this.position;
      }
   }

   void put(MethodWriter var1, ByteVector var2, int var3, boolean var4) {
      if ((this.status & 2) == 0) {
         if (var4) {
            this.addReference(-1 - var3, var2.length);
            var2.putInt(-1);
         } else {
            this.addReference(var3, var2.length);
            var2.putShort(-1);
         }
      } else if (var4) {
         var2.putInt(this.position - var3);
      } else {
         var2.putShort(this.position - var3);
      }

   }

   private void addReference(int var1, int var2) {
      if (this.srcAndRefPositions == null) {
         this.srcAndRefPositions = new int[6];
      }

      if (this.referenceCount >= this.srcAndRefPositions.length) {
         int[] var3 = new int[this.srcAndRefPositions.length + 6];
         System.arraycopy(this.srcAndRefPositions, 0, var3, 0, this.srcAndRefPositions.length);
         this.srcAndRefPositions = var3;
      }

      this.srcAndRefPositions[this.referenceCount++] = var1;
      this.srcAndRefPositions[this.referenceCount++] = var2;
   }

   boolean resolve(MethodWriter var1, int var2, byte[] var3) {
      boolean var4 = false;
      this.status |= 2;
      this.position = var2;
      int var5 = 0;

      while(true) {
         while(var5 < this.referenceCount) {
            int var6 = this.srcAndRefPositions[var5++];
            int var7 = this.srcAndRefPositions[var5++];
            int var8;
            if (var6 >= 0) {
               var8 = var2 - var6;
               if (var8 < -32768 || var8 > 32767) {
                  int var9 = var3[var7 - 1] & 255;
                  if (var9 <= 168) {
                     var3[var7 - 1] = (byte)(var9 + 49);
                  } else {
                     var3[var7 - 1] = (byte)(var9 + 20);
                  }

                  var4 = true;
               }

               var3[var7++] = (byte)(var8 >>> 8);
               var3[var7] = (byte)var8;
            } else {
               var8 = var2 + var6 + 1;
               var3[var7++] = (byte)(var8 >>> 24);
               var3[var7++] = (byte)(var8 >>> 16);
               var3[var7++] = (byte)(var8 >>> 8);
               var3[var7] = (byte)var8;
            }
         }

         return var4;
      }
   }

   Label getFirst() {
      return this.frame == null ? this : this.frame.owner;
   }

   boolean inSubroutine(long var1) {
      if ((this.status & 1024) != 0) {
         return (this.srcAndRefPositions[(int)(var1 >>> 32)] & (int)var1) != 0;
      } else {
         return false;
      }
   }

   boolean inSameSubroutine(Label var1) {
      if ((this.status & 1024) != 0 && (var1.status & 1024) != 0) {
         for(int var2 = 0; var2 < this.srcAndRefPositions.length; ++var2) {
            if ((this.srcAndRefPositions[var2] & var1.srcAndRefPositions[var2]) != 0) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   void addToSubroutine(long var1, int var3) {
      if ((this.status & 1024) == 0) {
         this.status |= 1024;
         this.srcAndRefPositions = new int[var3 / 32 + 1];
      }

      int[] var10000 = this.srcAndRefPositions;
      var10000[(int)(var1 >>> 32)] |= (int)var1;
   }

   void visitSubroutine(Label var1, long var2, int var4) {
      Label var5 = this;

      while(true) {
         Label var6;
         Edge var7;
         while(true) {
            if (var5 == null) {
               return;
            }

            var6 = var5;
            var5 = var5.next;
            var6.next = null;
            if (var1 != null) {
               if ((var6.status & 2048) == 0) {
                  var6.status |= 2048;
                  if ((var6.status & 256) != 0 && !var6.inSameSubroutine(var1)) {
                     var7 = new Edge();
                     var7.info = var6.inputStackTop;
                     var7.successor = var1.successors.successor;
                     var7.next = var6.successors;
                     var6.successors = var7;
                  }
                  break;
               }
            } else if (!var6.inSubroutine(var2)) {
               var6.addToSubroutine(var2, var4);
               break;
            }
         }

         for(var7 = var6.successors; var7 != null; var7 = var7.next) {
            if (((var6.status & 128) == 0 || var7 != var6.successors.next) && var7.successor.next == null) {
               var7.successor.next = var5;
               var5 = var7.successor;
            }
         }
      }
   }

   public String toString() {
      return "L" + System.identityHashCode(this);
   }
}
