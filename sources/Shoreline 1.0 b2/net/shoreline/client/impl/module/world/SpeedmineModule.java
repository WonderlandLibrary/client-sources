package net.shoreline.client.impl.module.world;

import net.minecraft.screen.slot.SlotActionType;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.RotationModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.event.network.AttackBlockEvent;
import net.shoreline.client.impl.event.network.PlayerUpdateEvent;
import net.shoreline.client.impl.event.render.RenderWorldEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.mixin.accessor.AccessorClientPlayerInteractionManager;
import net.shoreline.client.util.player.RotationUtil;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.text.DecimalFormat;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class SpeedmineModule extends RotationModule
{
    //
    Config<Boolean> instantConfig = new BooleanConfig("Instant",
            "Instantly removes the mining block", false);
    Config<Float> rangeConfig = new NumberConfig<>("Range", "Range for mine",
            1.0f, 4.5f, 5.0f);
    Config<Swap> swapConfig = new EnumConfig<>("AutoSwap", "Swaps to the best " +
            "tool once the mining is complete", Swap.SILENT, Swap.values());
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotates" +
            " when mining the block", true);
    Config<Boolean> strictConfig = new BooleanConfig("Strict", "Swaps to tool" +
            " using alternative packets to bypass NCP silent swap", false,
            () -> swapConfig.getValue() != Swap.OFF);
    Config<Boolean> fastConfig = new BooleanConfig("Fast", "Attempts to " +
            "instantly remine blocks", false);
    Config<Boolean> remineConfig = new BooleanConfig("Remine",
            "Attempts to remine blocks", true);
    Config<Boolean> remineFullConfig = new BooleanConfig("Remine-Full",
            "Resets the block breaking state when remining", true,
            () -> remineConfig.getValue());
    Config<Boolean> remineInfiniteConfig = new BooleanConfig("Remine-Infinite",
            "Attempts to remine blocks infinitely", false,
            () -> remineConfig.getValue());
    Config<Integer> maxRemineConfig = new NumberConfig<>("MaxRemines",
            "Maximum remines of a block before reset", 0, 2, 10,
            () -> remineConfig.getValue() && !remineInfiniteConfig.getValue());
    // Mining block info
    private BlockPos mining;
    private BlockState state;
    private Direction direction;
    private float damage;
    // Number of remines on the current mining block.
    private int remines;

    /**
     *
     */
    public SpeedmineModule()
    {
        super("Speedmine", "Mines faster", ModuleCategory.WORLD);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String getModuleData()
    {
        DecimalFormat decimal = new DecimalFormat("0.0");
        return decimal.format(damage);
    }

    /**
     *
     *
     */
    @Override
    public void onDisable()
    {
        mining = null;
        state = null;
        direction = null;
        damage = 0.0f;
        remines = 0;
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerUpdate(PlayerUpdateEvent event)
    {
        if (event.getStage() == EventStage.PRE && !mc.player.isCreative())
        {
            if (mining == null)
            {
                damage = 0.0f;
                return;
            }
            state = mc.world.getBlockState(mining);
            int prev = mc.player.getInventory().selectedSlot;
            int slot = getBestTool(state);
            //
            double dist = mc.player.squaredDistanceTo(mining.toCenterPos());
            if (dist > ((NumberConfig<?>) rangeConfig).getValueSq() // walked out of range
                    || state.isAir() // mining is done
                    || damage > 3.0f // waited too long
                    || !remineConfig.getValue()
                    || !remineInfiniteConfig.getValue()
                    && remines > maxRemineConfig.getValue())
            {
                Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, mining, Direction.DOWN));
                mining = null;
                state = null;
                direction = null;
                damage = 0.0f;
                remines = 0;
            }
            else if (damage > 1.0f && !Modules.AUTO_CRYSTAL.isAttacking()
                    && !Modules.AUTO_CRYSTAL.isPlacing())
            {
                if (swapConfig.getValue() != Swap.OFF)
                {
                    if (strictConfig.getValue())
                    {
                        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId,
                                slot + 36, prev, SlotActionType.SWAP, mc.player);
                    }
                    else
                    {
                        swap(slot);
                    }
                }
                if (rotateConfig.getValue())
                {
                    float[] rotations = RotationUtil.getRotationsTo(mc.player.getEyePos(), mining.toCenterPos());
                    setRotation(rotations[0], rotations[1]);
                }
                if (isRotationBlocked())
                {
                    return;
                }
                // break current block
                Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mining, direction));
                if (fastConfig.getValue())
                {
                    Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK,
                            mining, direction.getOpposite()));
                    Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                            mining, direction));
                    Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK,
                            mining, direction));
                }
                if (swapConfig.getValue() == Swap.SILENT)
                {
                    if (strictConfig.getValue())
                    {
                        mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId,
                                slot + 36, prev, SlotActionType.SWAP, mc.player);
                    }
                    else
                    {
                        swap(prev);
                    }
                }
                // reset, this position needs to be remined if we
                // attempt to mine again
                if (remineFullConfig.getValue())
                {
                    damage = 0.0f;
                }
                remines++;
            }
            else
            {
                damage += calcBlockBreakingDelta(state, mc.world, mining);
            }
        }
    }

    /**
     *
     *
     * @return
     */
    public BlockPos getBlockTarget()
    {
        return mining;
    }

    /**
     *
     * @param slot
     */
    private void swap(int slot)
    {
        if (PlayerInventory.isValidHotbarIndex(slot))
        {
            mc.player.getInventory().selectedSlot = slot;
            ((AccessorClientPlayerInteractionManager) mc.interactionManager).hookSyncSelectedSlot();
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onAttackBlock(AttackBlockEvent event)
    {
        if (mc.player == null || mc.world == null
                || mc.player.isCreative() || mining != null && event.getPos() == mining)
        {
            return;
        }
        if (mining != null)
        {
            Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK,
                    mining, Direction.DOWN));
        }
        mining = event.getPos();
        direction = event.getDirection();
        damage = 0.0f;
        remines = 0;
        if (mining != null && direction != null)
        {
            event.cancel();
            Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
                    PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                    mining, direction));
            // Managers.NETWORK.sendPacket(new PlayerActionC2SPacket(
            //        PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK,
            //        mining, Direction.UP));
            if (instantConfig.getValue())
            {
                mc.world.removeBlock(mining, false);
            }
        }
    }

    /**
     * Returns the hotbar slot of the best {@link ToolItem} for the
     * {@link BlockState} of the mining {@link BlockPos}
     *
     * @param state The block state of the mining position
     * @return The hotbar slot of the best tool
     */
    public int getBestTool(BlockState state)
    {
        int slot = mc.player.getInventory().selectedSlot;
        float bestTool = 0.0f;
        for (int i = 0; i < 9; i++)
        {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof ToolItem))
            {
                continue;
            }
            float speed = stack.getMiningSpeedMultiplier(state);
            int efficiency = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
            if (efficiency > 0)
            {
                speed += efficiency * efficiency + 1.0f;
            }
            if (speed > bestTool)
            {
                bestTool = speed;
                slot = i;
            }
        }
        return slot;
    }

    /**
     *
     *
     * @param state
     * @param world
     * @param pos
     * @return
     */
    private float calcBlockBreakingDelta(BlockState state, BlockView world,
                                         BlockPos pos)
    {
        if (swapConfig.getValue() == Swap.OFF)
        {
            return state.calcBlockBreakingDelta(mc.player, mc.world, pos);
        }
        float f = state.getHardness(world, pos);
        if (f == -1.0f)
        {
            return 0.0f;
        }
        else
        {
            int i = canHarvest(state) ? 30 : 100;
            return getBlockBreakingSpeed(state) / f / (float) i;
        }
    }

    /**
     *
     *
     * @param block
     * @return
     */
    private float getBlockBreakingSpeed(BlockState block)
    {
        int tool = getBestTool(block);
        float f = mc.player.getInventory().getStack(tool).getMiningSpeedMultiplier(block);
        if (f > 1.0F)
        {
            ItemStack stack = mc.player.getInventory().getStack(tool);
            int i = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
            if (i > 0 && !stack.isEmpty())
            {
                f += (float) (i * i + 1);
            }
        }
        if (StatusEffectUtil.hasHaste(mc.player))
        {
            f *= 1.0f + (float) (StatusEffectUtil.getHasteAmplifier(mc.player) + 1) * 0.2f;
        }
        if (mc.player.hasStatusEffect(StatusEffects.MINING_FATIGUE))
        {
            float g = switch (mc.player.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier())
            {
                case 0 -> 0.3f;
                case 1 -> 0.09f;
                case 2 -> 0.0027f;
                default -> 8.1e-4f;
            };
            f *= g;
        }
        if (mc.player.isSubmergedIn(FluidTags.WATER)
                && !EnchantmentHelper.hasAquaAffinity(mc.player))
        {
            f /= 5.0f;
        }
        if (!mc.player.isOnGround())
        {
            f /= 5.0f;
        }
        return f;
    }

    /**
     * Determines whether the player is able to harvest drops from the specified block state.
     * If a block requires a special tool, it will check
     * whether the held item is effective for that block, otherwise
     * it returns {@code true}.
     *
     * @param state
     *
     * @see net.minecraft.item.Item#isSuitableFor(BlockState)
     */
    private boolean canHarvest(BlockState state)
    {
        if (state.isToolRequired())
        {
            int tool = getBestTool(state);
            return mc.player.getInventory().getStack(tool).isSuitableFor(state);
        }
        return true;
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onRenderWorld(RenderWorldEvent event)
    {
        if (mining == null || state == null || mc.player.isCreative())
        {
            return;
        }
        VoxelShape outlineShape = state.getOutlineShape(mc.world, mining);
        if (outlineShape.isEmpty())
        {
            return;
        }
        Box render1 = outlineShape.getBoundingBox();
        Box render = new Box(mining.getX() + render1.minX, mining.getY() + render1.minY,
                mining.getZ() + render1.minZ, mining.getX() + render1.maxX,
                mining.getY() + render1.maxY, mining.getZ() + render1.maxZ);
        Vec3d center = render.getCenter();
        float scale = damage;
        if (scale > 1.0f)
        {
            scale = 1.0f;
        }
        double dx = (render1.maxX - render1.minX) / 2.0;
        double dy = (render1.maxY - render1.minY) / 2.0;
        double dz = (render1.maxZ - render1.minZ) / 2.0;
        final Box scaled = new Box(center, center).expand(dx * scale, dy * scale, dz * scale);
        RenderManager.renderBox(event.getMatrices(), scaled,
                damage > 0.95f ? 0x6000ff00 : 0x60ff0000);
        RenderManager.renderBoundingBox(event.getMatrices(), scaled,
                2.5f, damage > 0.95f ? 0x6000ff00 : 0x60ff0000);
    }

    public enum Swap
    {
        NORMAL,
        SILENT,
        OFF
    }
}
