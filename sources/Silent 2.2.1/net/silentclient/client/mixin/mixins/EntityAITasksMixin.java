package net.silentclient.client.mixin.mixins;

import net.minecraft.entity.ai.EntityAITasks;
import net.silentclient.client.mixin.ducks.EntityAITasksExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(EntityAITasks.class)
public class EntityAITasksMixin implements EntityAITasksExt {

    @Shadow private List<EntityAITasks.EntityAITaskEntry> taskEntries;

    @Override
    public Object client$getTaskEntries() {
        return this.taskEntries;
    }
}
