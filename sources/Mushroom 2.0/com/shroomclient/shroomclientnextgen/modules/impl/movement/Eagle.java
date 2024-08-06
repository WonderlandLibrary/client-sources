package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MovementInputEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;
import com.shroomclient.shroomclientnextgen.util.WorldUtil;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EmptyBlockView;

@RegisterModule(
    name = "Eagle",
    uniqueId = "eagle",
    description = "Prevents You From Falling Off While Bridging",
    category = ModuleCategory.Movement
)
public class Eagle extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @ConfigMode
    @ConfigParentId("stopmode")
    @ConfigOption(name = "Mode", description = "Stop Mode", order = 1)
    public static Mode mode = Mode.Crouch;

    public enum Mode {
        Crouch,
        Safewalk,
    }

    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Prevent Double Sneaking",
        description = "Doesn't Double Sneak When Bridging Diagonal",
        order = 2
    )
    public static Boolean noDouble = true;

    /*
    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
            name = "Safe Walk With Eagle",
            description = "Constantly Safewalks Just In Case",
            order = 3
    )
    public static Boolean safewalkOnEagle = true;
     */

    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Only Looking At Blocks",
        description = "Only While Looking At Blocks",
        order = 4
    )
    public static Boolean onlyBlockLooking = true;

    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Only Holding Blocks",
        description = "Only Activated When Holding Blocks",
        order = 5
    )
    public static Boolean onlyHoldingBlocks = true;

    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Only If Looking Down",
        description = "Only Activated When Looking Down",
        order = 6
    )
    public static Boolean onlyLookingDown = true;

    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Crouched Ticks",
        description = "How Many Ticks To Crouch For On Activate",
        order = 7,
        max = 5
    )
    public static Integer crouchedTicksMax = 2;

    @ConfigChild(value = "stopmode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Only If Looking Backwards",
        description = "Only Activated When Looking Backwards",
        order = 8
    )
    public static Boolean onlyLookinBackward = true;

    @ConfigOption(
        name = "Only When Sneaking",
        description = "Only Eagle While Sneaking Key Is Held",
        order = 9
    )
    public static Boolean onwhenSneak = false;

    int crouchedTicks = 0;
    long lastCrouch = 0;

    @SubscribeEvent
    public void onMovementEvent(MovementInputEvent event) {
        if (Scaffold.isOverAir() && mode == Mode.Crouch && C.p().isOnGround()) {
            if (shouldSafewalk()) {
                if (crouchedTicks >= crouchedTicksMax) crouchedTicks = 0;
                crouchedTicks++;
                lastCrouch = System.currentTimeMillis();
            }
        }

        if (onwhenSneak && !Scaffold.isOverAir()) {
            if (shouldSafewalk()) {
                event.sneaking = false;
            }
        }

        if (crouchedTicks != 0 && crouchedTicks <= crouchedTicksMax) {
            event.sneaking = true;
            crouchedTicks++;
        } else if (!C.mc.options.sneakKey.isPressed()) {
            event.sneaking = false;
        }

        // random number, works
        if (System.currentTimeMillis() - lastCrouch > 40 || !noDouble) {
            crouchedTicks = 0;
        }
    }

    public static boolean shouldSafewalk() {
        if (ModuleManager.isEnabled(Eagle.class) && C.p() != null) {
            BlockHitResult hitResult = WorldUtil.rayTrace(
                C.p().getYaw(),
                C.p().getPitch(),
                5
            );
            if (
                !((hitResult != null &&
                        hitResult.getType() == HitResult.Type.BLOCK) ||
                    !onlyBlockLooking)
            ) return false;
            if (onlyLookingDown && C.p().getPitch() < 50) return false;

            float rotDif = RotationUtil.getAngleDifference(
                C.p().getYaw(),
                MovementUtil.getYaw()
            );
            if (rotDif < 0) rotDif *= -1;
            if (
                onlyLookinBackward && rotDif < 110 && MovementUtil.isMoving()
            ) return false;

            if (onwhenSneak && !C.mc.options.sneakKey.isPressed()) return false;

            return (
                !onlyHoldingBlocks ||
                (C.p()
                            .getInventory()
                            .getStack(C.p().getInventory().selectedSlot)
                            .getItem() instanceof
                        BlockItem h &&
                    h
                        .getBlock()
                        .getDefaultState()
                        .isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) &&
                    !(h.getBlock() instanceof FallingBlock))
            );
        }

        return false;
    }
}
