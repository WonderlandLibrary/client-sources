package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractProperty;
import cc.novoline.modules.configurations.property.exception.UnacceptableValueException;
import cc.novoline.modules.configurations.property.object.IntProperty;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.acE;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ListProperty extends AbstractProperty {
   protected List acceptableValues;

   public ListProperty(@NotNull List var1) {
      super(var1);
   }

   public ListProperty(@NotNull Collection var1) {
      this((List)(new ObjectArrayList(var1)));
   }

   @SafeVarargs
   public ListProperty(@NotNull Object... var1) {
      this((List)(new ObjectArrayList(var1)));
   }

   public ListProperty(@Nullable Object var1) {
      this((List)(new ObjectArrayList(Collections.singletonList(var1))));
   }

   public ListProperty(@Nullable Object var1, @Nullable Object var2) {
      IntProperty.a();
      this((List)(new ObjectArrayList(Arrays.asList(new Object[]{var1, var2}))));
      if(acE.b() == null) {
         IntProperty.b(new int[1]);
      }

   }

   public ListProperty(@Nullable Object var1, @Nullable Object var2, @Nullable Object var3) {
      IntProperty.a();
      this((List)(new ObjectArrayList(Arrays.asList(new Object[]{var1, var2, var3}))));
   }

   public ListProperty() {
      this((List)(new ObjectArrayList()));
   }

   @NotNull
   public static ListProperty of(@NotNull List var0) {
      return new ListProperty(var0);
   }

   @NotNull
   public static ListProperty of(@NotNull Collection var0) {
      return new ListProperty(new ObjectArrayList(var0));
   }

   @SafeVarargs
   @NotNull
   public static ListProperty of(@NotNull Object... var0) {
      return new ListProperty(var0);
   }

   @NotNull
   public static ListProperty of(@Nullable Object var0) {
      return new ListProperty(var0);
   }

   @NotNull
   public static ListProperty of(@Nullable Object var0, @Nullable Object var1) {
      return new ListProperty(var0, var1);
   }

   @NotNull
   public static ListProperty of(@Nullable Object var0, @Nullable Object var1, @Nullable Object var2) {
      return new ListProperty(var0, var1, var2);
   }

   @NotNull
   public static ListProperty empty() {
      return new ListProperty();
   }

   public void set(@Nullable List var1) {
      int[] var2 = IntProperty.a();
      if(this.inLimits(var1)) {
         super.set(var1);
      }

      throw new UnacceptableValueException("Unable to set " + var1 + " as it contains unacceptable value(s): " + Collections2.filter(var1, this::lambda$set$0), this);
   }

   private boolean inLimits(@NotNull List var1) {
      int[] var2 = IntProperty.a();
      if(this.acceptableValues != null && !var1.isEmpty() && !this.acceptableValues.isEmpty()) {
         Iterator var3 = var1.iterator();
         if(var3.hasNext()) {
            Object var4 = var3.next();
            if(!this.acceptableValues.contains(var4)) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public ListProperty acceptableValues(@NotNull Collection var1) {
      this.acceptableValues = new ObjectArrayList(var1);
      return this;
   }

   @SafeVarargs
   public final ListProperty acceptableValues(@NotNull Object... var1) {
      this.acceptableValues = new ObjectArrayList(var1);
      return this;
   }

   public Object get(int var1) {
      int[] var2 = IntProperty.a();
      return this.value != null?((List)this.value).get(var1):null;
   }

   public boolean contains(Object var1) {
      int[] var2 = IntProperty.a();
      return this.value != null && ((List)this.value).contains(var1);
   }

   public int size() {
      int[] var1 = IntProperty.a();
      return this.value != null?((List)this.value).size():0;
   }

   public boolean isEmpty() {
      int[] var1 = IntProperty.a();
      return this.value == null || ((List)this.value).isEmpty();
   }

   public boolean add(@Nullable Object var1) {
      int[] var2 = IntProperty.a();
      if(this.acceptableValues != null && !this.acceptableValues.contains(var1)) {
         return false;
      } else {
         if(this.value == null) {
            this.set((List)(new ObjectArrayList()));
         }

         return ((List)this.value).add(var1);
      }
   }

   public boolean remove(@Nullable Object var1) {
      int[] var2 = IntProperty.a();
      return this.value != null && ((List)this.value).remove(var1);
   }

   public List getAcceptableValues() {
      return this.acceptableValues;
   }

   private boolean lambda$set$0(Object var1) {
      int[] var2 = IntProperty.a();
      return !this.acceptableValues.contains(var1);
   }

   private static UnacceptableValueException a(UnacceptableValueException var0) {
      return var0;
   }
}
