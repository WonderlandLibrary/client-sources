package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import javax.annotation.Nullable;

public class ScoreComponent extends ATextComponent {
   private final String name;
   private final String objective;
   private String value;

   public ScoreComponent(String name, String objective) {
      this.name = name;
      this.objective = objective;
   }

   public ScoreComponent(String name, String objective, String value) {
      this.name = name;
      this.objective = objective;
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   public String getObjective() {
      return this.objective;
   }

   @Nullable
   public String getValue() {
      return this.value;
   }

   public ScoreComponent setValue(@Nullable String value) {
      this.value = value;
      return this;
   }

   @Override
   public String asSingleString() {
      return this.value;
   }

   @Override
   public ATextComponent copy() {
      ScoreComponent copy = new ScoreComponent(this.name, this.objective);
      copy.value = this.value;
      return this.putMetaCopy(copy);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ScoreComponent that = (ScoreComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings())
            && Objects.equals(this.getStyle(), that.getStyle())
            && Objects.equals(this.name, that.name)
            && Objects.equals(this.objective, that.objective)
            && Objects.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSiblings(), this.getStyle(), this.name, this.objective, this.value);
   }

   @Override
   public String toString() {
      return "ScoreComponent{siblings="
         + this.getSiblings()
         + ", style="
         + this.getStyle()
         + ", name='"
         + this.name
         + '\''
         + ", objective='"
         + this.objective
         + '\''
         + ", value='"
         + this.value
         + '\''
         + '}';
   }
}
