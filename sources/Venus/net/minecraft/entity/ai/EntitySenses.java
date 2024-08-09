/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

public class EntitySenses {
    private final MobEntity entity;
    private final List<Entity> seenEntities = Lists.newArrayList();
    private final List<Entity> unseenEntities = Lists.newArrayList();

    public EntitySenses(MobEntity mobEntity) {
        this.entity = mobEntity;
    }

    public void tick() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }

    public boolean canSee(Entity entity2) {
        if (this.seenEntities.contains(entity2)) {
            return false;
        }
        if (this.unseenEntities.contains(entity2)) {
            return true;
        }
        this.entity.world.getProfiler().startSection("canSee");
        boolean bl = this.entity.canEntityBeSeen(entity2);
        this.entity.world.getProfiler().endSection();
        if (bl) {
            this.seenEntities.add(entity2);
        } else {
            this.unseenEntities.add(entity2);
        }
        return bl;
    }
}

