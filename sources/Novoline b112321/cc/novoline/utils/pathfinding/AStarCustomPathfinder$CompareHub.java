package cc.novoline.utils.pathfinding;

import cc.novoline.utils.pathfinding.AStarCustomPathfinder;
import cc.novoline.utils.pathfinding.AStarCustomPathfinder$Hub;
import java.util.Comparator;

public class AStarCustomPathfinder$CompareHub implements Comparator {
   final AStarCustomPathfinder this$0;

   public AStarCustomPathfinder$CompareHub(AStarCustomPathfinder var1) {
      this.this$0 = var1;
   }

   public int compare(AStarCustomPathfinder$Hub var1, AStarCustomPathfinder$Hub var2) {
      return (int)(var1.getSquareDistanceToFromTarget() + var1.getTotalCost() - (var2.getSquareDistanceToFromTarget() + var2.getTotalCost()));
   }
}
