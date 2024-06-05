package com.viaversion.viaversion.util;

import com.viaversion.viaversion.util.Config;
import com.viaversion.viaversion.util.YamlConstructor;
import com.viaversion.viaversion.util.YamlConstructor$Map;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import org.yaml.snakeyaml.constructor.SafeConstructor.ConstructYamlOmap;
import org.yaml.snakeyaml.nodes.Node;

class YamlConstructor$ConstructYamlOmap extends ConstructYamlOmap {
   final YamlConstructor this$0;

   YamlConstructor$ConstructYamlOmap(YamlConstructor var1) {
      super(var1);
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      Config.c();
      Object var3 = super.construct(var1);
      return var3 instanceof YamlConstructor$Map && !(var3 instanceof ConcurrentSkipListMap)?new ConcurrentSkipListMap((Map)var3):var3;
   }
}
