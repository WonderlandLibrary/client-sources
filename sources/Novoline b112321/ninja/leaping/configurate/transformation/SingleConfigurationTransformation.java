package ninja.leaping.configurate.transformation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.s;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.transformation.ConfigurationTransformation$NodePath;
import ninja.leaping.configurate.transformation.MoveStrategy;
import ninja.leaping.configurate.transformation.TransformAction;

class SingleConfigurationTransformation extends s {
   private final MoveStrategy strategy;
   private final Map actions;
   private final ThreadLocal sharedPath = ThreadLocal.withInitial(ConfigurationTransformation$NodePath::<init>);

   SingleConfigurationTransformation(Map var1, MoveStrategy var2) {
      this.actions = var1;
      this.strategy = var2;
   }

   public void apply(ConfigurationNode var1) {
      s.b();
      Iterator var3 = this.actions.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         this.applySingleAction(var1, (Object[])var4.getKey(), 0, var1, (TransformAction)var4.getValue());
      }

   }

   private void applySingleAction(ConfigurationNode var1, Object[] var2, int var3, ConfigurationNode var4, TransformAction var5) {
      s.b();
      if(var3 < var2.length) {
         if(var2[var3] == c) {
            if(var4.hasListChildren()) {
               List var11 = var4.getChildrenList();
               int var9 = 0;
               if(var9 < var11.size()) {
                  var2[var3] = Integer.valueOf(var9);
                  this.applySingleAction(var1, var2, var3 + 1, (ConfigurationNode)var11.get(var9), var5);
                  ++var9;
               }

               var2[var3] = c;
            }

            if(var4.hasMapChildren()) {
               Iterator var12 = var4.getChildrenMap().entrySet().iterator();
               if(var12.hasNext()) {
                  Entry var14 = (Entry)var12.next();
                  var2[var3] = var14.getKey();
                  this.applySingleAction(var1, var2, var3 + 1, (ConfigurationNode)var14.getValue(), var5);
               }

               var2[var3] = c;
            }

            return;
         }

         var4 = var4.getNode(new Object[]{var2[var3]});
         if(var4.isVirtual()) {
            return;
         }

         int var7 = var3 + 1;
      }

      ConfigurationTransformation$NodePath var10 = (ConfigurationTransformation$NodePath)this.sharedPath.get();
      var10.arr = var2;
      Object[] var8 = var5.visitPath(var10, var4);
      if(var8 != null && !Arrays.equals(var2, var8)) {
         this.strategy.move(var4, var1.getNode(var8));
         var4.setValue((Object)null);
      }

   }
}
