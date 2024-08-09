package mathax.client.systems.modules.world;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.InteractBlockEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.misc.Names;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
/**
 * made by cqb13
 */
public class NoStrip extends Module {
    private final SettingGroup sgBlocks = settings.createGroup("Blocks");

    private final Setting<Boolean> swingHand = sgBlocks.add(new BoolSetting.Builder()
        .name("swing-hand")
        .description("Renders swing hand animation.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> chatFeedback = sgBlocks.add(new BoolSetting.Builder()
        .name("chat-feedback")
        .description("Notifies you in chat when you attempt to strip a log.")
        .defaultValue(false)
        .build()
    );

    public NoStrip() {
        super(Categories.World, Items.STRIPPED_OAK_LOG, "no-strip", "Prevents you from stripping logs.");
    }

    @EventHandler
    private void onInteractBlock(InteractBlockEvent event) {
        if (!shouldInteractBlock(event.result)) event.cancel();
    }

    private boolean shouldInteractBlock(BlockHitResult hitResult) {
        if(mc.player.getMainHandStack().getItem().toString().contains("axe")){
            if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
                String result = Names.get(mc.world.getBlockState(pos).getBlock());
                if (result.contains("Log")){
                    if (swingHand.get()) mc.player.swingHand(mc.player.getActiveHand());
                    if (chatFeedback.get()) info("You can't strip logs!");
                    return false;
                }
            }
        }
        return true;
    }
}
