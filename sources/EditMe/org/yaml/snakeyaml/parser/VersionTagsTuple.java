package org.yaml.snakeyaml.parser;

import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;

class VersionTagsTuple {
   private DumperOptions.Version version;
   private Map tags;

   public VersionTagsTuple(DumperOptions.Version var1, Map var2) {
      this.version = var1;
      this.tags = var2;
   }

   public DumperOptions.Version getVersion() {
      return this.version;
   }

   public Map getTags() {
      return this.tags;
   }

   public String toString() {
      return String.format("VersionTagsTuple<%s, %s>", this.version, this.tags);
   }
}
