package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.events.impl.StepEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.util.*;
import java.awt.*;
import java.util.ArrayList;
import net.minecraft.block.AirBlock;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/*@RegisterModule(
        name = "Fight Bot",
        uniqueId = "fightbot",
        description = "Pathfinds To The Nearest Player And Walks",
        category = ModuleCategory.Player
)*/
public class FightBot extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        if (C.mc.options != null) {
            ModuleManager.setEnabled(Scaffold.class, false, false);
            C.mc.options.forwardKey.setPressed(false);
            C.mc.options.leftKey.setPressed(false);
            C.mc.options.backKey.setPressed(false);
            C.mc.options.rightKey.setPressed(false);
            C.mc.options.jumpKey.setPressed(false);
        }
    }

    AbstractClientPlayerEntity target = null;
    AbstractClientPlayerEntity lastTarget = null;

    ArrayList<Vec3d> currentPath = null;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        target = getClosestPlayer(true);

        if (lastTarget != target || MovementUtil.ticks % 10 == 0) {
            currentPath = createPath(target);
        }

        if (!currentPath.isEmpty() && C.p().distanceTo(target) > 4) {
            C.mc.options.forwardKey.setPressed(true);
            C.mc.options.leftKey.setPressed(false);
            C.mc.options.backKey.setPressed(false);
            C.mc.options.rightKey.setPressed(false);
            C.mc.options.jumpKey.setPressed(false);
            Vec3d currentNode = currentPath.get(0);
            int num = 0;
            while (
                num < currentPath.size() &&
                (MovementUtil.distanceTo(
                            C.p()
                                .getBlockPos()
                                .toCenterPos()
                                .offset(Direction.DOWN, 1),
                            currentNode
                        ) <
                        4f ||
                    (C.p().horizontalCollision && num < 2))
            ) {
                currentNode = currentPath.get(num);
                num++;
            }

            // step event
            //if (currentNode.y >= C.p().getY()+1 && C.p().isOnGround())
            //    MovementUtil.jump();

            RotationUtil.Rotation rotationNeeded = RotationUtil.getRotation(
                C.p().getPos(),
                currentNode
            );

            float rotationYaw = RotationUtil.getLimitedRotation(
                new RotationUtil.Rotation(C.p().getPitch(), C.p().getYaw()),
                rotationNeeded,
                20
            ).yaw;

            C.p().setYaw(rotationYaw);

            // if 10 blocks of air below u (maybe swap with if over void? idk i dont wanna fall 1043802458 blocks into a hole)
            ModuleManager.setEnabled(
                Scaffold.class,
                NoFall.countAir(C.p().getPos()) > 10 ||
                NoFall.countAir(C.p().getPos().offset(Direction.DOWN, 1)) > 10,
                false
            );
        } else {
            int slot = InvManagerUtil.getBestSwordSlotHotbar();

            if (slot != -1) {
                C.mc.options.forwardKey.setPressed(true);
                C.mc.options.jumpKey.setPressed(true);
                C.mc.options.backKey.setPressed(false);
                C.mc.options.rightKey.setPressed(false);

                ModuleManager.setEnabled(Scaffold.class, false, false);

                C.p().getInventory().selectedSlot = slot;

                if (
                    C.p().distanceTo(target) < 2.9 && !NoFall.isOverVoid()
                ) C.mc.options.leftKey.setPressed(true);
                if (NoFall.isOverVoid()) {
                    C.mc.options.leftKey.setPressed(false);
                    C.mc.options.rightKey.setPressed(true);
                    C.mc.options.backKey.setPressed(true);
                }

                RotationUtil.Rotation rotationNeeded = RotationUtil.getRotation(
                    C.p().getPos(),
                    target.getPos()
                );
                RotationUtil.Rotation rot = RotationUtil.getLimitedRotation(
                    rotationNeeded,
                    new RotationUtil.Rotation(C.p().getPitch(), C.p().getYaw()),
                    5
                );
                C.p().setYaw(rot.yaw);
                C.p().setPitch(rot.pitch);
            }
        }

        lastTarget = target;
    }

    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        RenderUtil.setMatrix(e.matrixStack);
        Color colorOfFirstNode = ThemeUtil.themeColors()[0];
        Color colorOfNode = new Color(255, 255, 0);
        Color colorOfFirstLine = new Color(0, 255, 0);
        Color colorOfLine = new Color(255, 0, 0);

        float boxYoffset = 0.2f;
        float boxSize = 0.1f;
        float boxXoffset = boxSize / 2;

        float lineOffset = boxYoffset + boxSize / 2;

        if (currentPath != null) {
            RenderUtil.drawBox2(
                C.p().getBlockPos().toCenterPos().x - boxXoffset,
                C.p().getBlockPos().toCenterPos().y + boxYoffset,
                C.p().getBlockPos().toCenterPos().z - boxXoffset,
                boxSize,
                boxSize,
                boxSize,
                e.partialTicks,
                e.matrixStack,
                colorOfFirstNode
            );
            //RenderUtil.drawBox2(currentPath.get(0).x-boxXoffset, currentPath.get(0).y + boxYoffset, currentPath.get(0).z-boxXoffset, boxSize, boxSize, boxSize, e.partialTicks, e.matrixStack, colorOfFirstNode);
            for (int i = 1; i < currentPath.size(); i++) {
                Color color = colorOfLine;
                if (i == 1) color = colorOfFirstLine;

                RenderUtil.drawLine3d(
                    currentPath.get(i - 1).offset(Direction.UP, lineOffset),
                    currentPath.get(i).offset(Direction.UP, lineOffset),
                    e.partialTicks,
                    e.matrixStack,
                    color
                );
                RenderUtil.drawBox2(
                    currentPath.get(i).x - boxXoffset,
                    currentPath.get(i).y + boxYoffset,
                    currentPath.get(i).z - boxXoffset,
                    boxSize,
                    boxSize,
                    boxSize,
                    e.partialTicks,
                    e.matrixStack,
                    colorOfNode
                );
            }
        }

        if (target != null) RenderUtil.drawBox2(
            target.getBlockPos().toCenterPos().x - 0.4f,
            target.getBlockPos().toCenterPos().y - 0.5f,
            target.getBlockPos().toCenterPos().z - 0.4f,
            0.8f,
            1.9f,
            0.8f,
            e.partialTicks,
            e.matrixStack,
            ThemeUtil.themeColors()[0],
            100
        );
    }

    public AbstractClientPlayerEntity getClosestPlayer(boolean antibot) {
        AbstractClientPlayerEntity closestPlayer = null;
        for (AbstractClientPlayerEntity player : C.w().getPlayers()) {
            if (player != C.p()) {
                if (
                    closestPlayer == null &&
                    (!antibot || !TargetUtil.isBot(player))
                ) closestPlayer = player;

                if (
                    closestPlayer != null &&
                    C.p().distanceTo(player) <
                        C.p().distanceTo(closestPlayer) &&
                    (!antibot || !TargetUtil.isBot(player))
                ) closestPlayer = player;
            }
        }

        return closestPlayer;
    }

    // progresses 1 block at a time in the direction of the guy
    // if the block isnt air then it will loop blocks around it until it finds air
    // then progresses from that pos, repeat, done!
    // fast ig, doesnt work well,
    // gonna make a flood fill from the enemy next time!!!!!!!
    // not in mushroom tho prob because we gotta pathfind twice a second

    public ArrayList<Vec3d> createPath(Entity target) {
        ArrayList<Vec3d> positions = new ArrayList<>();

        Vec3d currentPos = C.p().getBlockPos().toCenterPos();
        Vec3d targetPos = target.getBlockPos().toCenterPos();

        float distanceBetween = 1f;

        int times = 0;

        while (
            MovementUtil.distanceToExcludeY(currentPos, targetPos) > 2.8f &&
            times < 100
        ) {
            double rotationYawNeed = RotationUtil.getRotation(
                currentPos,
                targetPos
            ).yaw;

            double cos = Math.cos(Math.toRadians(rotationYawNeed + 90.0f));
            double sin = Math.sin(Math.toRadians(rotationYawNeed + 90.0f));

            double x = currentPos.x;
            double z = currentPos.z;

            x += distanceBetween * cos;
            z += distanceBetween * sin;

            Vec3d prevPos = currentPos;
            currentPos = new Vec3d(x, C.p().getY(), z);
            BlockPos currentPosBlock = new BlockPos(
                (int) currentPos.x,
                (int) currentPos.y,
                (int) currentPos.z
            );

            int loopsOfFindingAir = 0;

            int cases = 4;

            while (
                !(C.w().getBlockState(currentPosBlock).getBlock() instanceof
                        AirBlock &&
                    C.w()
                            .getBlockState(
                                currentPosBlock.offset(Direction.UP, 1)
                            )
                            .getBlock() instanceof
                        AirBlock) &&
                loopsOfFindingAir < 50
            ) {
                int xOffset = 0;
                int yOffset = 0;
                int zOffset = 0;

                switch (loopsOfFindingAir % cases) {
                    case 0:
                        xOffset = loopsOfFindingAir / cases;
                        break;
                    case 1:
                        xOffset = -loopsOfFindingAir / cases;
                        break;
                    case 2:
                        zOffset = -loopsOfFindingAir / cases;
                        break;
                    case 3:
                        zOffset = loopsOfFindingAir / cases;
                        break;
                    /*
                    case 4:
                        yOffset = 1;
                        break;
                         */

                    // broken idk why (i mean if i change the switch statement for these to activate it all goes wrong.
                    /*
                    case 4:
                        xOffset = -loopsOfFindingAir / 8;
                        zOffset = -loopsOfFindingAir / 8;
                        break;
                    case 5:
                        xOffset = loopsOfFindingAir / 8;
                        zOffset = -loopsOfFindingAir / 8;
                        break;
                    case 6:
                        xOffset = loopsOfFindingAir / 8;
                        zOffset = loopsOfFindingAir / 8;
                        break;
                    case 7:
                        xOffset = -loopsOfFindingAir / 8;
                        zOffset = loopsOfFindingAir / 8;
                        break;

                    case 9:
                        xOffset = -loopsOfFindingAir / 8;
                        yOffset = 1;
                        break;
                    case 10:
                        xOffset = loopsOfFindingAir / 8;
                        yOffset = 1;
                        break;
                    case 11:
                        zOffset = loopsOfFindingAir / 8;
                        yOffset = 1;
                        break;
                    case 12:
                        zOffset = -loopsOfFindingAir / 8;
                        yOffset = 1;
                        break;
                     */
                }

                currentPos = new Vec3d(
                    currentPos.x + xOffset,
                    prevPos.y + yOffset,
                    currentPos.z + zOffset
                );
                currentPosBlock = new BlockPos(
                    (int) currentPos.x,
                    (int) currentPos.y,
                    (int) currentPos.z
                );

                loopsOfFindingAir++;
            }

            if (
                currentPosBlock.toCenterPos() !=
                    C.p().getBlockPos().toCenterPos() &&
                (C.w().getBlockState(currentPosBlock).getBlock() instanceof
                    AirBlock)
            ) positions.add(currentPosBlock.toCenterPos());

            times++;
        }

        if (positions.size() > 1) {
            /*
            for (int i = 1; i < positions.size(); i++) {
                int times2 = 0;

                int ogI = i;
                currentPos = positions.get(i-1);
                targetPos = positions.get(i);

                while (MovementUtil.distanceTo(currentPos, targetPos) > 1 && times2 < 100) {
                    currentPos = positions.get(i-1 - (i - ogI));

                    double rotationYawNeed = RotationUtil.getRotation(currentPos, targetPos).yaw;

                    double cos = Math.cos(Math.toRadians(rotationYawNeed + 90.0f));
                    double sin = Math.sin(Math.toRadians(rotationYawNeed + 90.0f));

                    double x = currentPos.x;
                    double z = currentPos.z;

                    x += distanceBetween * cos;
                    z += distanceBetween * sin;

                    currentPos = new Vec3d(x,C.p().getY(),z);
                    BlockPos currentPosBlock = new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z);

                    int loopsOfFindingAir = 0;

                    while (!(C.w().getBlockState(currentPosBlock).getBlock() instanceof AirBlock) && loopsOfFindingAir < 100) {
                        int xOffset = 0;
                        int zOffset = 0;

                        switch (loopsOfFindingAir % 8) {
                            case 0:
                                xOffset = loopsOfFindingAir / 8;
                                break;
                            case 1:
                                xOffset = -loopsOfFindingAir / 8;
                                break;
                            case 2:
                                zOffset = -loopsOfFindingAir / 8;
                                break;
                            case 3:
                                zOffset = loopsOfFindingAir / 8;
                                break;
                            case 4:
                                xOffset = -loopsOfFindingAir / 8;
                                zOffset = -loopsOfFindingAir / 8;
                                break;
                            case 5:
                                xOffset = loopsOfFindingAir / 8;
                                zOffset = -loopsOfFindingAir / 8;
                                break;
                            case 6:
                                xOffset = loopsOfFindingAir / 8;
                                zOffset = loopsOfFindingAir / 8;
                                break;
                            case 7:
                                xOffset = -loopsOfFindingAir / 8;
                                zOffset = loopsOfFindingAir / 8;
                        }

                        currentPos = new Vec3d(x+xOffset,C.p().getY(),z+zOffset);
                        currentPosBlock = new BlockPos((int) currentPos.x, (int) currentPos.y, (int) currentPos.z);
                        loopsOfFindingAir++;
                    }

                    positions.add(i, currentPosBlock.toCenterPos());

                    times2++;

                    i++;
                }
            }
             */

        }

        positions.add(0, C.p().getBlockPos().toCenterPos());

        return positions;
    }

    @SubscribeEvent
    public void onStepEvent(StepEvent e) {
        if (e.height < 1.3) {
            MovementUtil.jump();
        }
    }
}
