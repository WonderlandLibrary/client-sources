package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double amount;
   private final int operation;
   private final String name;
   private final UUID id;
   private boolean isSaved;

   public AttributeModifier(String var1, double var2, int var4) {
      this(MathHelper.getRandomUuid(ThreadLocalRandom.current()), var1, var2, var4);
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

   public UUID getID() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public int getOperation() {
      return this.operation;
   }

   public double getAmount() {
      return this.amount;
   }

   public boolean isSaved() {
      return this.isSaved;
   }

   public AttributeModifier setSaved(boolean var1) {
      this.isSaved = var1;
      return this;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() == var1.getClass()) {
         AttributeModifier var2 = (AttributeModifier)var1;
         return this.id != null?this.id.equals(var2.id):var2.id == null;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id != null?this.id.hashCode():0;
   }

   public String toString() {
      return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name=\'" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
   }
}
