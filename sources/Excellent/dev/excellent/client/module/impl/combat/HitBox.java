package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.EntityHitBoxEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@ModuleInfo(name = "Hit Box", category = Category.COMBAT, description = "Изменяет размер хитбокса сущности.")
public class HitBox extends Module {

    private final MultiBooleanValue targets = new MultiBooleanValue("Сущности", this)
            .add(
                    new BooleanValue("Игроки", true),
                    new BooleanValue("Мобы", true)
            );

    private final NumberValue playersSize = new NumberValue("Игроки", this, 0.3F, 0F, 2F, 0.05F, () -> !targets.get("Игроки").getValue());
    private final NumberValue mobsSize = new NumberValue("Мобы", this, 0.3F, 0F, 2F, 0.05F, () -> !targets.get("Мобы").getValue());
    private final Listener<EntityHitBoxEvent> onHitBoxEvent = event -> {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (targets.isEnabled("Игроки") && event.getEntity() instanceof PlayerEntity)
            event.setSize(playersSize.getValue().floatValue());

        if (targets.isEnabled("Мобы") && !(event.getEntity() instanceof PlayerEntity))
            event.setSize(mobsSize.getValue().floatValue());
    };
}
