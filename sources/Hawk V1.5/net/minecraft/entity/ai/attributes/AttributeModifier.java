package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double amount;
   private final int operation;
   private static final String __OBFID = "CL_00001564";
   private final UUID id;
   private boolean isSaved;
   private final String name;

   public int getOperation() {
      return this.operation;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("AttributeModifier{amount=")).append(this.amount).append(", operation=").append(this.operation).append(", name='").append(this.name).append('\'').append(", id=").append(this.id).append(", serialize=").append(this.isSaved).append('}'));
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         AttributeModifier var2 = (AttributeModifier)var1;
         if (this.id != null) {
            if (!this.id.equals(var2.id)) {
               return false;
            }
         } else if (var2.id != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public double getAmount() {
      return this.amount;
   }

   public String getName() {
      return this.name;
   }

   public AttributeModifier(String var1, double var2, int var4) {
      this(MathHelper.func_180182_a(ThreadLocalRandom.current()), var1, var2, var4);
   }

   public boolean isSaved() {
      return this.isSaved;
   }

   public AttributeModifier setSaved(boolean var1) {
      this.isSaved = var1;
      return this;
   }

   public UUID getID() {
      return this.id;
   }

   public AttributeModifier(UUID var1, String var2, double var3, int var5) {
      this.isSaved = true;
      this.id = var1;
      this.name = var2;
      this.amount = var3;
      this.operation = var5;
      Validate.notEmpty(var2, "Modifier name cannot be empty", new Object[0]);
      Validate.inclusiveBetween(0L, 2L, (long)var5, "Invalid operation");
   }

   public int hashCode() {
      return this.id != null ? this.id.hashCode() : 0;
   }
}
