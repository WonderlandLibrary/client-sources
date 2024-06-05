package cc.novoline.commands.impl;

import cc.novoline.commands.impl.NameCommand$1;
import cc.novoline.commands.impl.NameCommand$Name;
import cc.novoline.utils.java.Checks;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.a_E;

class NameCommand$Player {
   private final List names;

   private NameCommand$Player(List var1) {
      Checks.notEmpty((Collection)var1, "names");
      this.names = var1;
   }

   private static NameCommand$Player withNames(List var0) {
      return new NameCommand$Player(var0);
   }

   public NameCommand$Name getCurrentName() {
      return (NameCommand$Name)this.names.get(this.names.size() - 1);
   }

   public List getNames() {
      return this.names;
   }

   public boolean equals(Object var1) {
      int[] var2 = a_E.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof NameCommand$Player)) {
         return false;
      } else {
         NameCommand$Player var3 = (NameCommand$Player)var1;
         return Objects.equals(this.names, var3.names);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.names});
   }

   public String toString() {
      return "Player{names=" + this.names + '}';
   }

   NameCommand$Player(List var1, NameCommand$1 var2) {
      this(var1);
   }
}
