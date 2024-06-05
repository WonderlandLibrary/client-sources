package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple implements IRegistry {
   protected final Map registryObjects = this.createUnderlyingMap();
   private static final String __OBFID = "CL_00001210";
   private static final Logger logger = LogManager.getLogger();

   public Set getKeys() {
      return Collections.unmodifiableSet(this.registryObjects.keySet());
   }

   protected Map createUnderlyingMap() {
      return Maps.newHashMap();
   }

   public void putObject(Object var1, Object var2) {
      Validate.notNull(var1);
      Validate.notNull(var2);
      if (this.registryObjects.containsKey(var1)) {
         logger.debug(String.valueOf((new StringBuilder("Adding duplicate key '")).append(var1).append("' to registry")));
      }

      this.registryObjects.put(var1, var2);
   }

   public boolean containsKey(Object var1) {
      return this.registryObjects.containsKey(var1);
   }

   public Iterator iterator() {
      return this.registryObjects.values().iterator();
   }

   public Object getObject(Object var1) {
      return this.registryObjects.get(var1);
   }
}
