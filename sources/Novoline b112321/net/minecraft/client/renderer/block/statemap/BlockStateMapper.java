package net.minecraft.client.renderer.block.statemap;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;

public class BlockStateMapper {
   private Map blockStateMap = Maps.newIdentityHashMap();
   private Set setBuiltInBlocks = Sets.newIdentityHashSet();

   public void registerBlockStateMapper(Block var1, IStateMapper var2) {
      this.blockStateMap.put(var1, var2);
   }

   public void registerBuiltInBlocks(Block... var1) {
      Collections.addAll(this.setBuiltInBlocks, var1);
   }

   public Map putAllStateModelLocations() {
      IdentityHashMap var1 = Maps.newIdentityHashMap();

      for(Block var3 : Block.blockRegistry) {
         if(!this.setBuiltInBlocks.contains(var3)) {
            var1.putAll(((IStateMapper)Objects.firstNonNull(this.blockStateMap.get(var3), new DefaultStateMapper())).putStateModelLocations(var3));
         }
      }

      return var1;
   }
}
