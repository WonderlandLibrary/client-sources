package net.minecraft.client.resources.data;

import net.apX;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer$1;

class IMetadataSerializer$Registration {
   final IMetadataSectionSerializer field_110502_a;
   final Class c;
   final apX a;

   private IMetadataSerializer$Registration(apX var1, IMetadataSectionSerializer var2, Class var3) {
      this.a = var1;
      this.field_110502_a = var2;
      this.c = var3;
   }

   IMetadataSerializer$Registration(apX var1, IMetadataSectionSerializer var2, Class var3, IMetadataSerializer$1 var4) {
      this(var1, var2, var3);
   }
}
