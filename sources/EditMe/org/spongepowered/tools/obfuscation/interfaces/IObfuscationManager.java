package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IObfuscationManager {
   void init();

   IObfuscationDataProvider getDataProvider();

   IReferenceManager getReferenceManager();

   IMappingConsumer createMappingConsumer();

   List getEnvironments();

   void writeMappings();

   void writeReferences();
}
