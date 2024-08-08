package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.util.Annotations;

public class InjectorGroupInfo {
   private final String name;
   private final List members;
   private final boolean isDefault;
   private int minCallbackCount;
   private int maxCallbackCount;

   public InjectorGroupInfo(String var1) {
      this(var1, false);
   }

   InjectorGroupInfo(String var1, boolean var2) {
      this.members = new ArrayList();
      this.minCallbackCount = -1;
      this.maxCallbackCount = Integer.MAX_VALUE;
      this.name = var1;
      this.isDefault = var2;
   }

   public String toString() {
      return String.format("@Group(name=%s, min=%d, max=%d)", this.getName(), this.getMinRequired(), this.getMaxAllowed());
   }

   public boolean isDefault() {
      return this.isDefault;
   }

   public String getName() {
      return this.name;
   }

   public int getMinRequired() {
      return Math.max(this.minCallbackCount, 1);
   }

   public int getMaxAllowed() {
      return Math.min(this.maxCallbackCount, Integer.MAX_VALUE);
   }

   public Collection getMembers() {
      return Collections.unmodifiableCollection(this.members);
   }

   public void setMinRequired(int var1) {
      if (var1 < 1) {
         throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + var1 + " on " + this);
      } else {
         if (this.minCallbackCount > 0 && this.minCallbackCount != var1) {
            LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[]{var1, this.name, this.minCallbackCount});
         }

         this.minCallbackCount = Math.max(this.minCallbackCount, var1);
      }
   }

   public void setMaxAllowed(int var1) {
      if (var1 < 1) {
         throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + var1 + " on " + this);
      } else {
         if (this.maxCallbackCount < Integer.MAX_VALUE && this.maxCallbackCount != var1) {
            LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[]{var1, this.name, this.maxCallbackCount});
         }

         this.maxCallbackCount = Math.min(this.maxCallbackCount, var1);
      }
   }

   public InjectorGroupInfo add(InjectionInfo var1) {
      this.members.add(var1);
      return this;
   }

   public InjectorGroupInfo validate() throws InjectionValidationException {
      if (this.members.size() == 0) {
         return this;
      } else {
         int var1 = 0;

         InjectionInfo var3;
         for(Iterator var2 = this.members.iterator(); var2.hasNext(); var1 += var3.getInjectedCallbackCount()) {
            var3 = (InjectionInfo)var2.next();
         }

         int var4 = this.getMinRequired();
         int var5 = this.getMaxAllowed();
         if (var1 < var4) {
            throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", var4, var1));
         } else if (var1 > var5) {
            throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", var5, var1));
         } else {
            return this;
         }
      }
   }

   public static final class Map extends HashMap {
      private static final long serialVersionUID = 1L;
      private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);

      public InjectorGroupInfo get(Object var1) {
         return this.forName(var1.toString());
      }

      public InjectorGroupInfo forName(String var1) {
         InjectorGroupInfo var2 = (InjectorGroupInfo)super.get(var1);
         if (var2 == null) {
            var2 = new InjectorGroupInfo(var1);
            this.put(var1, var2);
         }

         return var2;
      }

      public InjectorGroupInfo parseGroup(MethodNode var1, String var2) {
         return this.parseGroup(Annotations.getInvisible(var1, Group.class), var2);
      }

      public InjectorGroupInfo parseGroup(AnnotationNode var1, String var2) {
         if (var1 == null) {
            return NO_GROUP;
         } else {
            String var3 = (String)Annotations.getValue(var1, "name");
            if (var3 == null || var3.isEmpty()) {
               var3 = var2;
            }

            InjectorGroupInfo var4 = this.forName(var3);
            Integer var5 = (Integer)Annotations.getValue(var1, "min");
            if (var5 != null && var5 != -1) {
               var4.setMinRequired(var5);
            }

            Integer var6 = (Integer)Annotations.getValue(var1, "max");
            if (var6 != null && var6 != -1) {
               var4.setMaxAllowed(var6);
            }

            return var4;
         }
      }

      public void validateAll() throws InjectionValidationException {
         Iterator var1 = this.values().iterator();

         while(var1.hasNext()) {
            InjectorGroupInfo var2 = (InjectorGroupInfo)var1.next();
            var2.validate();
         }

      }

      public Object get(Object var1) {
         return this.get(var1);
      }
   }
}
