package com.viaversion.viaversion.util;

import com.viaversion.viaversion.util.YamlConstructor$ConstructYamlOmap;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.constructor.SafeConstructor.ConstructYamlMap;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlConstructor extends SafeConstructor {
   public YamlConstructor() {
      this.yamlClassConstructors.put(NodeId.mapping, new ConstructYamlMap(this));
      this.yamlConstructors.put(Tag.OMAP, new YamlConstructor$ConstructYamlOmap(this));
   }
}
