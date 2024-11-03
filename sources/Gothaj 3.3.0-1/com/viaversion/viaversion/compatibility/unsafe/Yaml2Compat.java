package com.viaversion.viaversion.compatibility.unsafe;

import com.viaversion.viaversion.compatibility.YamlCompat;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public final class Yaml2Compat implements YamlCompat {
   @Override
   public Representer createRepresenter(DumperOptions dumperOptions) {
      return new Representer(dumperOptions);
   }

   @Override
   public SafeConstructor createSafeConstructor() {
      return new Yaml2Compat.CustomSafeConstructor();
   }

   private static final class CustomSafeConstructor extends SafeConstructor {
      public CustomSafeConstructor() {
         super(new LoaderOptions());
         this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap());
         this.yamlConstructors.put(Tag.OMAP, new SafeConstructor.ConstructYamlOmap());
      }
   }
}
