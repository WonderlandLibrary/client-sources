package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.service.ObfuscationServices;

public class ObfuscationManager implements IObfuscationManager {
   private final IMixinAnnotationProcessor ap;
   private final List environments = new ArrayList();
   private final IObfuscationDataProvider obfs;
   private final IReferenceManager refs;
   private final List consumers = new ArrayList();
   private boolean initDone;

   public ObfuscationManager(IMixinAnnotationProcessor var1) {
      this.ap = var1;
      this.obfs = new ObfuscationDataProvider(var1, this.environments);
      this.refs = new ReferenceManager(var1, this.environments);
   }

   public void init() {
      if (!this.initDone) {
         this.initDone = true;
         ObfuscationServices.getInstance().initProviders(this.ap);
         Iterator var1 = ObfuscationType.types().iterator();

         while(var1.hasNext()) {
            ObfuscationType var2 = (ObfuscationType)var1.next();
            if (var2.isSupported()) {
               this.environments.add(var2.createEnvironment());
            }
         }

      }
   }

   public IObfuscationDataProvider getDataProvider() {
      return this.obfs;
   }

   public IReferenceManager getReferenceManager() {
      return this.refs;
   }

   public IMappingConsumer createMappingConsumer() {
      Mappings var1 = new Mappings();
      this.consumers.add(var1);
      return var1;
   }

   public List getEnvironments() {
      return this.environments;
   }

   public void writeMappings() {
      Iterator var1 = this.environments.iterator();

      while(var1.hasNext()) {
         ObfuscationEnvironment var2 = (ObfuscationEnvironment)var1.next();
         var2.writeMappings(this.consumers);
      }

   }

   public void writeReferences() {
      this.refs.write();
   }
}
