package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import net.apX;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource {
   private final Map mapMetadataSections = Maps.newHashMap();
   private final String resourcePackName;
   private final ResourceLocation srResourceLocation;
   private final InputStream resourceInputStream;
   private final InputStream mcmetaInputStream;
   private final apX e;
   private boolean mcmetaJsonChecked;
   private JsonObject mcmetaJson;

   public SimpleResource(String var1, ResourceLocation var2, InputStream var3, InputStream var4, apX var5) {
      this.resourcePackName = var1;
      this.srResourceLocation = var2;
      this.resourceInputStream = var3;
      this.mcmetaInputStream = var4;
      this.e = var5;
   }

   public ResourceLocation getResourceLocation() {
      return this.srResourceLocation;
   }

   public InputStream getInputStream() {
      return this.resourceInputStream;
   }

   public boolean hasMetadata() {
      return this.mcmetaInputStream != null;
   }

   public IMetadataSection getMetadata(String var1) {
      if(!this.hasMetadata()) {
         return (IMetadataSection)null;
      } else {
         if(this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            BufferedReader var2 = null;

            try {
               var2 = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
               this.mcmetaJson = (new JsonParser()).parse(var2).getAsJsonObject();
            } finally {
               IOUtils.closeQuietly(var2);
            }
         }

         IMetadataSection var6 = (IMetadataSection)this.mapMetadataSections.get(var1);
         var6 = this.e.a(var1, this.mcmetaJson);
         return var6;
      }
   }

   public String getResourcePackName() {
      return this.resourcePackName;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource var2 = (SimpleResource)var1;
         if(this.srResourceLocation != null) {
            if(!this.srResourceLocation.equals(var2.srResourceLocation)) {
               return false;
            }
         } else if(var2.srResourceLocation != null) {
            return false;
         }

         return this.resourcePackName != null?this.resourcePackName.equals(var2.resourcePackName):var2.resourcePackName == null;
      }
   }

   public int hashCode() {
      int var1 = this.resourcePackName != null?this.resourcePackName.hashCode():0;
      var1 = 31 * var1 + (this.srResourceLocation != null?this.srResourceLocation.hashCode():0);
      return var1;
   }
}
