package com.viaversion.viaversion.api.data;

public interface BiMappings extends Mappings {
   BiMappings inverse();

   static BiMappings of(Mappings mappings) {
      return of(mappings, mappings.inverse());
   }

   static BiMappings of(Mappings mappings, Mappings inverse) {
      return new BiMappingsBase(mappings, inverse);
   }
}
