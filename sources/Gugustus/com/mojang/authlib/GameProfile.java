package com.mojang.authlib;

import com.mojang.authlib.properties.PropertyMap;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GameProfile {
   private final UUID id;
   private final String name;
   private final PropertyMap properties = new PropertyMap();
   private boolean legacy;

   public GameProfile(UUID id, String name) {
      if (id == null && StringUtils.isBlank(name)) {
         throw new IllegalArgumentException("Name and ID cannot both be blank");
      } else {
         this.id = id;
         this.name = name;
      }
   }

   public UUID getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public PropertyMap getProperties() {
      return this.properties;
   }

   public boolean isComplete() {
      return this.id != null && StringUtils.isNotBlank(this.getName());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GameProfile that = (GameProfile)o;
         if (this.id != null ? this.id.equals(that.id) : that.id == null) {
            return this.name != null ? this.name.equals(that.name) : that.name == null;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.id != null ? this.id.hashCode() : 0;
      return 31 * result + (this.name != null ? this.name.hashCode() : 0);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.id)
         .append("name", this.name)
         .append("properties", this.properties)
         .append("legacy", this.legacy)
         .toString();
   }

   public boolean isLegacy() {
      return this.legacy;
   }
}
