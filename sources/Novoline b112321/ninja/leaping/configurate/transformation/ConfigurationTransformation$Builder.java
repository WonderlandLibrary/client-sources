package ninja.leaping.configurate.transformation;

import java.util.SortedMap;
import java.util.TreeMap;
import net.s;
import ninja.leaping.configurate.transformation.MoveStrategy;
import ninja.leaping.configurate.transformation.NodePathComparator;
import ninja.leaping.configurate.transformation.SingleConfigurationTransformation;
import ninja.leaping.configurate.transformation.TransformAction;

public final class ConfigurationTransformation$Builder {
   private MoveStrategy strategy = MoveStrategy.OVERWRITE;
   private final SortedMap actions = new TreeMap(new NodePathComparator());

   public ConfigurationTransformation$Builder addAction(Object[] var1, TransformAction var2) {
      this.actions.put(var1, var2);
      return this;
   }

   public MoveStrategy getMoveStrategy() {
      return this.strategy;
   }

   public ConfigurationTransformation$Builder setMoveStrategy(MoveStrategy var1) {
      this.strategy = var1;
      return this;
   }

   public s b() {
      return new SingleConfigurationTransformation(this.actions, this.strategy);
   }
}
