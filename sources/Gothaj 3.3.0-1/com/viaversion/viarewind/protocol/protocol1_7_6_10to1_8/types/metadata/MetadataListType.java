package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.metadata;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.metadata.MetaListTypeTemplate;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public class MetadataListType extends MetaListTypeTemplate {
   public List<Metadata> read(ByteBuf buffer) throws Exception {
      ArrayList<Metadata> list = new ArrayList<>();

      Metadata m;
      do {
         m = Types1_7_6_10.METADATA.read(buffer);
         if (m != null) {
            list.add(m);
         }
      } while (m != null);

      return list;
   }

   public void write(ByteBuf buffer, List<Metadata> metadata) throws Exception {
      for (Metadata meta : metadata) {
         Types1_7_6_10.METADATA.write(buffer, meta);
      }

      if (metadata.isEmpty()) {
         Types1_7_6_10.METADATA.write(buffer, new Metadata(0, MetaType1_7_6_10.Byte, (byte)0));
      }

      buffer.writeByte(127);
   }
}
