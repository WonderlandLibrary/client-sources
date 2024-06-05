package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource {
   private final InputStream mcmetaInputStream;
   private final ResourceLocation srResourceLocation;
   private final IMetadataSerializer srMetadataSerializer;
   private JsonObject mcmetaJson;
   private final Map mapMetadataSections = Maps.newHashMap();
   private static final String __OBFID = "CL_00001093";
   private final InputStream resourceInputStream;
   private final String field_177242_b;
   private boolean mcmetaJsonChecked;

   public SimpleResource(String var1, ResourceLocation var2, InputStream var3, InputStream var4, IMetadataSerializer var5) {
      this.field_177242_b = var1;
      this.srResourceLocation = var2;
      this.resourceInputStream = var3;
      this.mcmetaInputStream = var4;
      this.srMetadataSerializer = var5;
   }

   public int hashCode() {
      int var1 = this.field_177242_b != null ? this.field_177242_b.hashCode() : 0;
      var1 = 31 * var1 + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
      return var1;
   }

   public ResourceLocation func_177241_a() {
      return this.srResourceLocation;
   }

   public IMetadataSection getMetadata(String var1) {
      if (!this.hasMetadata()) {
         return null;
      } else {
         if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
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
         if (var6 == null) {
            var6 = this.srMetadataSerializer.parseMetadataSection(var1, this.mcmetaJson);
         }

         return var6;
      }
   }

   public String func_177240_d() {
      return this.field_177242_b;
   }

   public InputStream getInputStream() {
      return this.resourceInputStream;
   }

   public boolean hasMetadata() {
      return this.mcmetaInputStream != null;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource var2 = (SimpleResource)var1;
         if (this.srResourceLocation != null) {
            if (!this.srResourceLocation.equals(var2.srResourceLocation)) {
               return false;
            }
         } else if (var2.srResourceLocation != null) {
            return false;
         }

         if (this.field_177242_b != null) {
            if (!this.field_177242_b.equals(var2.field_177242_b)) {
               return false;
            }
         } else if (var2.field_177242_b != null) {
            return false;
         }

         return true;
      }
   }
}
