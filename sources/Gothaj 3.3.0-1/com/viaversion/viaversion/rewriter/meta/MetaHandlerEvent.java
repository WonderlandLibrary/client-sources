package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface MetaHandlerEvent {
   UserConnection user();

   int entityId();

   @Nullable
   TrackedEntity trackedEntity();

   @Nullable
   default EntityType entityType() {
      return this.trackedEntity() != null ? this.trackedEntity().entityType() : null;
   }

   default int index() {
      return this.meta().id();
   }

   default void setIndex(int index) {
      this.meta().setId(index);
   }

   Metadata meta();

   void cancel();

   boolean cancelled();

   @Nullable
   Metadata metaAtIndex(int var1);

   List<Metadata> metadataList();

   @Nullable
   List<Metadata> extraMeta();

   default boolean hasExtraMeta() {
      return this.extraMeta() != null;
   }

   void createExtraMeta(Metadata var1);
}
