package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.player.BlockUtils;
import in.momin5.cookieclient.api.util.utils.player.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoTrap extends Module {
    public AutoTrap() {
        super("AutoTrap", Category.COMBAT);
    }


    SettingMode mode = register (new SettingMode("Mode",this, "Extra", "Extra", "Face", "Normal", "Feet"));
    SettingNumber blocksPerTick = register( new SettingNumber("Speed",this, 4, 0, 8, 1));
    SettingBoolean rotate = register( new  SettingBoolean("Rotate", this,true));
    SettingMode swing = register( new SettingMode("Swing",this, "Mainhand", "Mainhand", "Offhand", "None"));

    private final Vec3d[] offsetsDefault = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsetsFace = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsetsFeet = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsetsExtra = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 0.0),
            new Vec3d(0.0, 4.0, 0.0)
    };

    private int offsetStep = 0;
    private int yLevel;
    private String lastTickTargetName = "";


    private boolean firstRun = true;

    @Override
    public void onEnable() {
        yLevel = (int) Math.round(mc.player.posY);
        firstRun = true;

        if (InventoryUtils.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
            this.disable();
        }
    }

    @Override
    public void onUpdate() {
        EntityPlayer closest_target = findClosestTarget();

        if (closest_target == null) {
            this.disable();
            return;
        }

        if ((int) Math.round(mc.player.posY) != yLevel) {
            this.disable();
            return;
        }

        if (firstRun) {
            firstRun = false;
            lastTickTargetName = closest_target.getName();
        } else if (!lastTickTargetName.equals(closest_target.getName())) {
            lastTickTargetName = closest_target.getName();
            offsetStep = 0;
        }

        final List<Vec3d> place_targets = new ArrayList<Vec3d>();

        if (mode.is("Normal")) {
            Collections.addAll(place_targets, offsetsDefault);
        } else if (mode.is("Extra")) {
            Collections.addAll(place_targets, offsetsExtra);
        } else if (mode.is("Feet")) {
            Collections.addAll(place_targets, offsetsFeet);
        } else {
            Collections.addAll(place_targets, offsetsFace);
        }

        int blocks_placed = 0;

        while (blocks_placed < blocksPerTick.getValue()) {

            if (offsetStep >= place_targets.size()) {
                offsetStep = 0;
                break;
            }

            final BlockPos offset_pos = new BlockPos(place_targets.get(offsetStep));
            final BlockPos target_pos = new BlockPos(closest_target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean should_try_place = true;

            if (!mc.world.getBlockState(target_pos).getMaterial().isReplaceable())
                should_try_place = false;

            for (final Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(target_pos))) {

                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    should_try_place = false;
                    break;
                }

            }


            if (should_try_place && BlockUtils.placeBlock(target_pos, InventoryUtils.findBlockInHotbar(Blocks.OBSIDIAN), rotate.isEnabled(), rotate.isEnabled(), swing)) {
                ++blocks_placed;
            }

            offsetStep++;

        }
    }

    public EntityPlayer findClosestTarget()  {

        if (mc.world.playerEntities.isEmpty())
            return null;

        EntityPlayer closestTarget = null;

        for (final EntityPlayer target : mc.world.playerEntities)
        {
            if (target == mc.player || !target.isEntityAlive())
                continue;

            if (target.getHealth() <= 0.0f)
                continue;

            if (closestTarget != null)
                if (mc.player.getDistance(target) > mc.player.getDistance(closestTarget))
                    continue;

            closestTarget = target;
        }

        return closestTarget;
    }

}
