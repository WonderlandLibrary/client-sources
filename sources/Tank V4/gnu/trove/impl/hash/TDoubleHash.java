package gnu.trove.impl.hash;

import gnu.trove.impl.Constants;
import gnu.trove.impl.HashFunctions;
import gnu.trove.procedure.TDoubleProcedure;
import java.util.Arrays;

public abstract class TDoubleHash extends TPrimitiveHash {
   static final long serialVersionUID = 1L;
   public transient double[] _set;
   protected double no_entry_value;
   protected boolean consumeFreeSlot;

   public TDoubleHash() {
      this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
      if (this.no_entry_value != 0.0D) {
         Arrays.fill(this._set, this.no_entry_value);
      }

   }

   public TDoubleHash(int var1) {
      super(var1);
      this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
      if (this.no_entry_value != 0.0D) {
         Arrays.fill(this._set, this.no_entry_value);
      }

   }

   public TDoubleHash(int var1, float var2) {
      super(var1, var2);
      this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
      if (this.no_entry_value != 0.0D) {
         Arrays.fill(this._set, this.no_entry_value);
      }

   }

   public TDoubleHash(int var1, float var2, double var3) {
      super(var1, var2);
      this.no_entry_value = var3;
      if (var3 != 0.0D) {
         Arrays.fill(this._set, var3);
      }

   }

   public double getNoEntryValue() {
      return this.no_entry_value;
   }

   protected int setUp(int var1) {
      int var2 = super.setUp(var1);
      this._set = new double[var2];
      return var2;
   }

   public boolean contains(double param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean forEach(TDoubleProcedure var1) {
      byte[] var2 = this._states;
      double[] var3 = this._set;
      int var4 = var3.length;

      do {
         if (var4-- <= 0) {
            return true;
         }
      } while(var2[var4] != 1 || var1.execute(var3[var4]));

      return false;
   }

   protected void removeAt(int var1) {
      this._set[var1] = this.no_entry_value;
      super.removeAt(var1);
   }

   int indexRehashed(double var1, int var3, int var4, byte var5) {
      int var6 = this._set.length;
      int var7 = 1 + var4 % (var6 - 2);
      int var8 = var3;

      do {
         var3 -= var7;
         if (var3 < 0) {
            var3 += var6;
         }

         var5 = this._states[var3];
         if (var5 == 0) {
            return -1;
         }

         if (var1 == this._set[var3] && var5 != 2) {
            return var3;
         }
      } while(var3 != var8);

      return -1;
   }

   protected int insertKey(double var1) {
      int var3 = HashFunctions.hash(var1) & Integer.MAX_VALUE;
      int var4 = var3 % this._states.length;
      byte var5 = this._states[var4];
      this.consumeFreeSlot = false;
      if (var5 == 0) {
         this.consumeFreeSlot = true;
         this.insertKeyAt(var4, var1);
         return var4;
      } else {
         return var5 == 1 && this._set[var4] == var1 ? -var4 - 1 : this.insertKeyRehash(var1, var4, var3, var5);
      }
   }

   int insertKeyRehash(double var1, int var3, int var4, byte var5) {
      int var6 = this._set.length;
      int var7 = 1 + var4 % (var6 - 2);
      int var8 = var3;
      int var9 = -1;

      do {
         if (var5 == 2 && var9 == -1) {
            var9 = var3;
         }

         var3 -= var7;
         if (var3 < 0) {
            var3 += var6;
         }

         var5 = this._states[var3];
         if (var5 == 0) {
            if (var9 != -1) {
               this.insertKeyAt(var9, var1);
               return var9;
            }

            this.consumeFreeSlot = true;
            this.insertKeyAt(var3, var1);
            return var3;
         }

         if (var5 == 1 && this._set[var3] == var1) {
            return -var3 - 1;
         }
      } while(var3 != var8);

      if (var9 != -1) {
         this.insertKeyAt(var9, var1);
         return var9;
      } else {
         throw new IllegalStateException("No free or removed slots available. Key set full?!!");
      }
   }

   void insertKeyAt(int var1, double var2) {
      this._set[var1] = var2;
      this._states[var1] = 1;
   }
}
