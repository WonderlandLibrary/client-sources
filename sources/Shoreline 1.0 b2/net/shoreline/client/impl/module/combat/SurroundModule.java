package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.PlaceBlockModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.event.ScreenOpenEvent;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.event.network.PlayerUpdateEvent;
import net.shoreline.client.impl.event.render.RenderWorldEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.util.player.RotationUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class SurroundModule extends PlaceBlockModule
{
    //
    Config<Float> placeRangeConfig = new NumberConfig<>("PlaceRange", "The " +
            "placement range for surround", 0.0f, 4.0f, 5.0f);
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotates to " +
            "block before placing", false);
    Config<Boolean> strictDirectionConfig = new BooleanConfig("StrictDirection",
            "Places on visible sides only", false);
    Config<Boolean> attackConfig = new BooleanConfig("Attack", "Attacks " +
            "crystals in the way of surround", true);
    Config<Boolean> centerConfig = new BooleanConfig("Center", "Centers the" +
            " player before placing blocks", false);
    Config<Boolean> extendConfig = new BooleanConfig("Extend", "Extends " +
            "surround if the player is not in the center of a block", true,
            () -> !centerConfig.getValue());
    Config<Boolean> floorConfig = new BooleanConfig("Floor", "Creates a " +
            "floor for the surround if there is none", false);
    Config<Integer> shiftTicksConfig = new NumberConfig<>("ShiftTicks", "The" +
            " number of blocks to place per tick", 1, 2, 5);
    Config<Integer> shiftDelayConfig = new NumberConfig<>("ShiftDelay",
            "The delay between each block placement interval", 0, 1, 5);
    Config<Boolean> jumpDisableConfig = new BooleanConfig("AutoDisable",
            "Disables after moving out of the hole", true);
    Config<Boolean> renderConfig = new BooleanConfig("Render", "Renders" +
            " block placements of the surround", false);
    //
    private List<BlockPos> surround = new ArrayList<>();
    private List<BlockPos> placements = new ArrayList<>();
    private int blocksPlaced;
    private int shiftDelay;
    //
    private double prevY;

    /**
     *
     */
    public SurroundModule()
    {
        super("Surround", "Surrounds feet with obsidian", ModuleCategory.COMBAT);
    }

    /**
     *
     */
    @Override
    public void onEnable()
    {
        if (mc.player == null)
        {
            return;
        }
        if (centerConfig.getValue())
        {
            double x = Math.floor(mc.player.getX()) + 0.5;
            double z = Math.floor(mc.player.getZ()) + 0.5;
            Managers.POSITION.setPositionXZ(x, z);
        }
        // mc.inGameHud.getChatHud().addMessage();
        prevY = mc.player.getY();
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onDisconnect(DisconnectEvent event)
    {
        disable();
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onScreenOpen(ScreenOpenEvent event)
    {
        if (event.getScreen() instanceof DeathScreen)
        {
            disable();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerUpdate(PlayerUpdateEvent event)
    {
        // Do we need this check?? Surround is always highest prio
        if (event.getStage() == EventStage.PRE)
        {
            blocksPlaced = 0;
            if (jumpDisableConfig.getValue() && mc.player.getY() > prevY)
            {
                disable();
                return;
            }
            BlockPos pos = BlockPos.ofFloored(mc.player.getX(),
                    mc.player.getY(), mc.player.getZ());
            if (shiftDelay < shiftDelayConfig.getValue())
            {
                shiftDelay++;
                return;
            }
            //
            // surround.clear();
            // placements.clear();
            surround = getSurroundPositions(pos);
            placements = surround.stream().filter(p -> mc.world.isAir(p)).toList();
            while (blocksPlaced < shiftTicksConfig.getValue()
                    && !placements.isEmpty())
            {
                if (blocksPlaced >= placements.size())
                {
                    break;
                }
                BlockPos targetPos = placements.get(blocksPlaced);
                if (rotateConfig.getValue())
                {
                    float[] rots = RotationUtil.getRotationsTo(mc.player.getEyePos(),
                            targetPos.toCenterPos());
                    // All rotations for shift ticks must send extra packet
                    // This may not work on all servers
                    if (blocksPlaced == 0)
                    {
                        setRotation(rots[0], rots[1]);
                    }
                    else
                    {
                        Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                                rots[0], rots[1], mc.player.isOnGround()));
                    }
                }
                placeBlockResistant(targetPos,
                        strictDirectionConfig.getValue());
                blocksPlaced++;
                shiftDelay = 0;
            }
        }
    }

    public boolean isPlacing()
    {
        return isEnabled() && !placements.isEmpty();
    }

    /**
     *
     *
     * @param pos
     * @return
     */
    public List<BlockPos> getSurroundPositions(BlockPos pos)
    {
        List<BlockPos> entities = new ArrayList<>();
        entities.add(pos);
        if (extendConfig.getValue())
        {
            for (Direction dir : Direction.values())
            {
                if (!dir.getAxis().isHorizontal())
                {
                    continue;
                }
                BlockPos pos1 = pos.add(dir.getVector());
                //
                List<Entity> box = mc.world.getOtherEntities(null, new Box(pos1))
                        .stream().filter(e -> !checkSurroundEntity(e)).toList();
                if (box.isEmpty())
                {
                    continue;
                }
                for (Entity entity : box)
                {
                    entities.addAll(getAllInBox(entity.getBoundingBox(), pos));
                }
            }
        }
        List<BlockPos> blocks = new ArrayList<>();
        for (BlockPos epos : entities)
        {
            for (Direction dir2 : Direction.values())
            {
                if (!dir2.getAxis().isHorizontal())
                {
                    continue;
                }
                BlockPos pos2 = epos.add(dir2.getVector());
                if (entities.contains(pos2) || blocks.contains(pos2))
                {
                    continue;
                }
                double dist = mc.player.squaredDistanceTo(pos2.toCenterPos());
                if (dist > ((NumberConfig) placeRangeConfig).getValueSq())
                {
                    continue;
                }
                blocks.add(pos2);
            }
        }
        if (floorConfig.getValue())
        {
            for (BlockPos epos1 : entities)
            {
                BlockPos floor = epos1.down();
                double dist = mc.player.squaredDistanceTo(floor.toCenterPos());
                if (dist > ((NumberConfig) placeRangeConfig).getValueSq())
                {
                    continue;
                }
                blocks.add(floor);
            }
        }
        return blocks;
    }

    private boolean checkSurroundEntity(Entity entity)
    {
        return entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity
                || (entity instanceof EndCrystalEntity && attackConfig.getValue());
    }

    /**
     * Returns a {@link List} of all the {@link BlockPos} positions in the
     * given {@link Box} that match the player position level
     *
     * @param box
     * @param pos The player position
     * @return
     */
    private List<BlockPos> getAllInBox(Box box, BlockPos pos)
    {
        final List<BlockPos> intersections = new ArrayList<>();
        for (int x = (int) Math.floor(box.minX); x < Math.ceil(box.maxX); x++)
        {
            for (int z = (int) Math.floor(box.minZ); z < Math.ceil(box.maxZ); z++)
            {
                intersections.add(new BlockPos(x, pos.getY(), z));
            }
        }
        return intersections;
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event)
    {
        if (mc.player == null)
        {
            return;
        }
        if (event.getPacket() instanceof BlockUpdateS2CPacket packet)
        {
            final BlockState state = packet.getState();
            final BlockPos targetPos = packet.getPos();
            if (surround.contains(targetPos) && state.isAir())
            {
                if (rotateConfig.getValue())
                {
                    float[] rots = RotationUtil.getRotationsTo(mc.player.getEyePos(),
                            targetPos.toCenterPos());
                    Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                            rots[0], rots[1], mc.player.isOnGround()));
                }
                placeBlockResistant(targetPos,
                        strictDirectionConfig.getValue());
                blocksPlaced++;
                // shiftDelay = 0;
            }
        }
        else if (event.getPacket() instanceof PlaySoundS2CPacket packet
                && packet.getCategory() == SoundCategory.BLOCKS
                && packet.getSound().value() == SoundEvents.ENTITY_GENERIC_EXPLODE)
        {
            final BlockPos targetPos = BlockPos.ofFloored(packet.getX(),
                    packet.getY(), packet.getZ());
            if (surround.contains(targetPos))
            {
                if (rotateConfig.getValue())
                {
                    float[] rots = RotationUtil.getRotationsTo(mc.player.getEyePos(),
                            targetPos.toCenterPos());
                    Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                            rots[0], rots[1], mc.player.isOnGround()));
                }
                placeBlockResistant(targetPos,
                        strictDirectionConfig.getValue());
                blocksPlaced++;
                // shiftDelay = 0;
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onRenderWorld(RenderWorldEvent event)
    {
        if (renderConfig.getValue())
        {
            if (surround.isEmpty() || placements.isEmpty())
            {
                return;
            }
            for (BlockPos pos : surround)
            {
                RenderManager.renderBox(event.getMatrices(),
                        pos, Modules.COLORS.getRGB(60));
            }
        }
    }
    // Burrow support???
}
