package net.minecraft.client.resources;

import java.io.InputStream;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public interface IResource {
   boolean hasMetadata();

   InputStream getInputStream();

   String func_177240_d();

   ResourceLocation func_177241_a();

   IMetadataSection getMetadata(String var1);
}
