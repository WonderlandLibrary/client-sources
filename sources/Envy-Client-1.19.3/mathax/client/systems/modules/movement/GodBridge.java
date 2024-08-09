package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

import java.util.Arrays;

import static mathax.client.utils.misc.ChatUtils.info;

public class GodBridge extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    final Direction[] allowedSides = new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

    public GodBridge() {
        super(Categories.Movement, Items.RED_WOOL, "GodBridge", "Puts you in the right position to god bridge.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        mc.player.setYaw(mc.player.getMovementDirection().asRotation());
        if(mc.player.getPitch() > 83 || mc.player.getPitch() < 81) {
            mc.player.setPitch(82.5f);
        }
        HitResult hr = mc.crosshairTarget;
        if (hr.getType() == HitResult.Type.BLOCK && hr instanceof BlockHitResult result) {
            if(Arrays.stream(allowedSides).anyMatch(direction -> direction == result.getSide()) && mc.player.getMainHandStack().getItem() instanceof BlockItem){

            }
        }
    }

    @Override
    public boolean onActivate() {
        info(Formatting.GOLD + "GodBridge is now enabled place blocks to bridge Fast Use is recommended");
        return false;
    }
}
