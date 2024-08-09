package xyz.mathax.mathaxclient.systems.modules.player;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.entity.player.AttackEntityEvent;
import xyz.mathax.mathaxclient.events.entity.player.InteractBlockEvent;
import xyz.mathax.mathaxclient.events.entity.player.InteractEntityEvent;
import xyz.mathax.mathaxclient.events.entity.player.StartBreakingBlockEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.settings.ListMode;

import java.util.ArrayList;
import java.util.List;

public class NoInteract extends Module {
    private final SettingGroup blocksSettings = settings.createGroup("Blocks");
    private final SettingGroup entitiesSettings = settings.createGroup("Entities");

    // Blocks

    private final Setting<List<Block>> blockMineSetting = blocksSettings.add(new BlockListSetting.Builder()
            .name("Block Mine")
            .description("Cancel block mining.")
            .defaultValue(new ArrayList<>())
            .build()
    );

    private final Setting<ListMode> blockMineModeSetting = blocksSettings.add(new EnumSetting.Builder<ListMode>()
            .name("Block mine mode")
            .description("List mode to use for block mine.")
            .defaultValue(ListMode.Blacklist)
            .build()
    );

    private final Setting<List<Block>> blockInteractSetting = blocksSettings.add(new BlockListSetting.Builder()
            .name("Block interact")
            .description("Cancel block interaction.")
            .defaultValue(new ArrayList<>())
            .build()
    );

    private final Setting<ListMode> blockInteractModeSetting = blocksSettings.add(new EnumSetting.Builder<ListMode>()
            .name("Block interact mode")
            .description("List mode to use for block interact.")
            .defaultValue(ListMode.Blacklist)
            .build()
    );

    private final Setting<HandMode> blockInteractHandSetting = blocksSettings.add(new EnumSetting.Builder<HandMode>()
            .name("Block interact hand")
            .description("Cancel block interaction if performed by this hand.")
            .defaultValue(HandMode.None)
            .build()
    );

    // Entities

    private final Setting<Object2BooleanMap<EntityType<?>>> entityHitSetting = entitiesSettings.add(new EntityTypeListSetting.Builder()
            .name("Entity hit")
            .description("Cancel entity hitting.")
            .onlyAttackable()
            .build()
    );

    private final Setting<ListMode> entityHitModeSetting = entitiesSettings.add(new EnumSetting.Builder<ListMode>()
            .name("Entity hit mode")
            .description("List mode to use for entity hit.")
            .defaultValue(ListMode.Blacklist)
            .build()
    );

    private final Setting<Object2BooleanMap<EntityType<?>>> entityInteractSetting = entitiesSettings.add(new EntityTypeListSetting.Builder()
            .name("Entity interact")
            .description("Cancel entity interaction.")
            .onlyAttackable()
            .build()
    );

    private final Setting<ListMode> entityInteractModeSetting = entitiesSettings.add(new EnumSetting.Builder<ListMode>()
            .name("Entity interact mode")
            .description("List mode to use for entity interact.")
            .defaultValue(ListMode.Blacklist)
            .build()
    );

    private final Setting<HandMode> entityInteractHandSetting = entitiesSettings.add(new EnumSetting.Builder<HandMode>()
            .name("Entity interact hand")
            .description("Cancels entity interaction if performed by this hand.")
            .defaultValue(HandMode.None)
            .build()
    );

    private final Setting<InteractMode> friendsSetting = entitiesSettings.add(new EnumSetting.Builder<InteractMode>()
            .name("Friends")
            .description("Friends cancel mode.")
            .defaultValue(InteractMode.None)
            .build()
    );

    private final Setting<InteractMode> enemiesSetting = entitiesSettings.add(new EnumSetting.Builder<InteractMode>()
            .name("Enemies")
            .description("Enemies cancel mode.")
            .defaultValue(InteractMode.None)
            .build()
    );

    private final Setting<InteractMode> babiesSetting = entitiesSettings.add(new EnumSetting.Builder<InteractMode>()
            .name("Babies")
            .description("Baby entity cancel mode.")
            .defaultValue(InteractMode.None)
            .build()
    );

    private final Setting<InteractMode> nametaggedSetting = entitiesSettings.add(new EnumSetting.Builder<InteractMode>()
            .name("Nametagged")
            .description("Nametagged entity cancel mode.")
            .defaultValue(InteractMode.None)
            .build()
    );

    public NoInteract(Category category) {
        super(category, "No Interact", "Blocks interactions with certain types of inputs.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onStartBreakingBlockEvent(StartBreakingBlockEvent event) {
        if (!shouldAttackBlock(event.blockPos)) {
            event.cancel();
        }
    }

    @EventHandler
    private void onInteractBlock(InteractBlockEvent event) {
        if (!shouldInteractBlock(event.result, event.hand)) {
            event.cancel();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onAttackEntity(AttackEntityEvent event) {
        if (!shouldAttackEntity(event.entity)) {
            event.cancel();
        }
    }

    @EventHandler
    private void onInteractEntity(InteractEntityEvent event) {
        if (!shouldInteractEntity(event.entity, event.hand)) {
            event.cancel();
        }
    }

    private boolean shouldAttackBlock(BlockPos blockPos) {
        if (blockMineModeSetting.get() == ListMode.Whitelist && blockMineSetting.get().contains(mc.world.getBlockState(blockPos).getBlock())) {
            return false;
        }

        return blockMineModeSetting.get() != ListMode.Blacklist || !blockMineSetting.get().contains(mc.world.getBlockState(blockPos).getBlock());
    }

    private boolean shouldInteractBlock(BlockHitResult hitResult, Hand hand) {
        if (blockInteractHandSetting.get() == HandMode.Both || (blockInteractHandSetting.get() == HandMode.Mainhand && hand == Hand.MAIN_HAND) || (blockInteractHandSetting.get() == HandMode.Offhand && hand == Hand.OFF_HAND)) {
            return false;
        }

        if (blockInteractModeSetting.get() == ListMode.Blacklist && blockInteractSetting.get().contains(mc.world.getBlockState(hitResult.getBlockPos()).getBlock())) {
            return false;
        }

        return blockInteractModeSetting.get() != ListMode.Whitelist || blockInteractSetting.get().contains(mc.world.getBlockState(hitResult.getBlockPos()).getBlock());
    }

    private boolean shouldAttackEntity(Entity entity) {
        if ((friendsSetting.get() == InteractMode.Both || friendsSetting.get() == InteractMode.Hit) && entity instanceof PlayerEntity && !Friends.get().shouldAttack((PlayerEntity) entity)) {
            return false;
        }

        if ((enemiesSetting.get() == InteractMode.Both || enemiesSetting.get() == InteractMode.Hit) && entity instanceof PlayerEntity && Enemies.get().shouldAttack((PlayerEntity) entity)) {
            return false;
        }

        if ((babiesSetting.get() == InteractMode.Both || babiesSetting.get() == InteractMode.Hit) && entity instanceof AnimalEntity && ((AnimalEntity) entity).isBaby()) {
            return false;
        }

        if ((nametaggedSetting.get() == InteractMode.Both || nametaggedSetting.get() == InteractMode.Hit) && entity.hasCustomName()) {
            return false;
        }

        if (entityHitModeSetting.get() == ListMode.Blacklist && entityHitSetting.get().getBoolean(entity.getType())) {
            return false;
        } else {
            return entityHitModeSetting.get() != ListMode.Whitelist || entityHitSetting.get().getBoolean(entity.getType());
        }
    }

    private boolean shouldInteractEntity(Entity entity, Hand hand) {
        if (entityInteractHandSetting.get() == HandMode.Both || (entityInteractHandSetting.get() == HandMode.Mainhand && hand == Hand.MAIN_HAND) || (entityInteractHandSetting.get() == HandMode.Offhand && hand == Hand.OFF_HAND)) {
            return false;
        }

        if ((friendsSetting.get() == InteractMode.Both || friendsSetting.get() == InteractMode.Interact) && entity instanceof PlayerEntity && !Friends.get().shouldAttack((PlayerEntity) entity)) {
            return false;
        }

        if ((enemiesSetting.get() == InteractMode.Both || enemiesSetting.get() == InteractMode.Interact) && entity instanceof PlayerEntity && Enemies.get().shouldAttack((PlayerEntity) entity)) {
            return false;
        }

        if ((babiesSetting.get() == InteractMode.Both || babiesSetting.get() == InteractMode.Interact) && entity instanceof AnimalEntity && ((AnimalEntity) entity).isBaby()) {
            return false;
        }

        if ((nametaggedSetting.get() == InteractMode.Both || nametaggedSetting.get() == InteractMode.Interact) && entity.hasCustomName()) {
            return false;
        }

        if (entityInteractModeSetting.get() == ListMode.Blacklist && entityInteractSetting.get().getBoolean(entity.getType())) {
            return false;
        } else {
            return entityInteractModeSetting.get() != ListMode.Whitelist || entityInteractSetting.get().getBoolean(entity.getType());
        }
    }

    public enum HandMode {
        Mainhand,
        Offhand,
        Both,
        None
    }

    public enum InteractMode {
        Hit,
        Interact,
        Both,
        None
    }
}