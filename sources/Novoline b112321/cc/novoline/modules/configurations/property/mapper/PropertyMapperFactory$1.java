package cc.novoline.modules.configurations.property.mapper;

import cc.novoline.modules.configurations.property.mapper.PropertyMapper;
import cc.novoline.modules.configurations.property.mapper.PropertyMapperFactory;
import com.google.common.cache.CacheLoader;

class PropertyMapperFactory$1 extends CacheLoader {
   final PropertyMapperFactory this$0;

   PropertyMapperFactory$1(PropertyMapperFactory var1) {
      this.this$0 = var1;
   }

   public PropertyMapper load(Class var1) throws Exception {
      return new PropertyMapper(var1);
   }
}
