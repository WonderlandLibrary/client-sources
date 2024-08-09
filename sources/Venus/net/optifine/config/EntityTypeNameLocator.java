/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.IObjectLocator;
import net.optifine.util.EntityTypeUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EntityTypeNameLocator
implements IObjectLocator<String> {
    @Override
    public String getObject(ResourceLocation resourceLocation) {
        EntityType entityType = EntityTypeUtils.getEntityType(resourceLocation);
        return entityType == null ? null : entityType.getTranslationKey();
    }

    public static String getEntityTypeName(Entity entity2) {
        return entity2.getType().getTranslationKey();
    }

    @Override
    public Object getObject(ResourceLocation resourceLocation) {
        return this.getObject(resourceLocation);
    }
}

