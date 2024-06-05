package ninja.leaping.configurate.transformation;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.transformation.MoveStrategy$1;

public enum MoveStrategy {
   MERGE,
   OVERWRITE;

   private static final MoveStrategy[] $VALUES = new MoveStrategy[]{MERGE, OVERWRITE};

   private MoveStrategy() {
   }

   public abstract void move(ConfigurationNode var1, ConfigurationNode var2);

   MoveStrategy(MoveStrategy$1 var3) {
      this();
   }
}
