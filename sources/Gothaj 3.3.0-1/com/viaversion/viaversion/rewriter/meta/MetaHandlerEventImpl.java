package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MetaHandlerEventImpl implements MetaHandlerEvent {
   private final UserConnection connection;
   private final TrackedEntity trackedEntity;
   private final int entityId;
   private final List<Metadata> metadataList;
   private final Metadata meta;
   private List<Metadata> extraData;
   private boolean cancel;

   public MetaHandlerEventImpl(UserConnection connection, @Nullable TrackedEntity trackedEntity, int entityId, Metadata meta, List<Metadata> metadataList) {
      this.connection = connection;
      this.trackedEntity = trackedEntity;
      this.entityId = entityId;
      this.meta = meta;
      this.metadataList = metadataList;
   }

   @Override
   public UserConnection user() {
      return this.connection;
   }

   @Override
   public int entityId() {
      return this.entityId;
   }

   @Nullable
   @Override
   public TrackedEntity trackedEntity() {
      return this.trackedEntity;
   }

   @Override
   public Metadata meta() {
      return this.meta;
   }

   @Override
   public void cancel() {
      this.cancel = true;
   }

   @Override
   public boolean cancelled() {
      return this.cancel;
   }

   @Nullable
   @Override
   public Metadata metaAtIndex(int index) {
      for (Metadata meta : this.metadataList) {
         if (index == meta.id()) {
            return meta;
         }
      }

      return null;
   }

   @Override
   public List<Metadata> metadataList() {
      return Collections.unmodifiableList(this.metadataList);
   }

   @Nullable
   @Override
   public List<Metadata> extraMeta() {
      return this.extraData;
   }

   @Override
   public void createExtraMeta(Metadata metadata) {
      if (this.extraData == null) {
         this.extraData = new ArrayList<>();
      }

      this.extraData.add(metadata);
   }
}
