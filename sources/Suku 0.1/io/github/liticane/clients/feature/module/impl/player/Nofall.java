package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.misc.ChatUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.BlockPos;
import net.minecraft.item.Item;

//remove cuz eit prob doesnt work
@Module.Info(name = "Nofall", category = Module.Category.PLAYER)
public class Nofall extends Module{

    private boolean respawning = false;
    private double lastGroundChangeY = 0;
    private double fallDistance = 0;
    private int respawnTicks = 0;

    public boolean voidCheck() {
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;

        for (int i = (int) (y - 5); i >= 0; i--) {
            if (!mc.world.getBlockState(new BlockPos(x, i, z)).getBlock().equals(Blocks.air)) {
                return false;
            }
        }

        return true;
    }

    public boolean bed() {
        ItemStack itemStack = mc.player.inventory.getStackInSlot(35 + 9);
        //fucking item stack check
        return  Item.itemRegistry.getNameForObject(itemStack.getItem()).toString().contains("bed");
    }

    @SubscribeEvent
    private final EventListener<PreMotionEvent> onMotion = event -> {
        this.setSuffix("Watchdog");
        double mx = mc.player.motionX;
        double my = mc.player.motionY;
        double mz = mc.player.motionZ;

        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;

        if (respawning) {
            respawnTicks++;
        }

        if (respawnTicks == 200) {
            respawning = false;
            respawnTicks = 0;
        }

        if (mc.player.onGround) {
            lastGroundChangeY = y;
            fallDistance = 0;
        }
       // if (!voidCheck() && !respawning) {
        //5
            if (lastGroundChangeY - y > 4) {
                lastGroundChangeY = y;
                mc.player.motionX = mx * 1.15758 - Math.random() / 10000;
                mc.player.motionY = -0.945 - Math.random() / 10000;
                mc.player.motionZ = mz * 1.15758 - Math.random() / 10000;
              //  mc.player.setMotion(mx * 1.15758 - Math.random() / 10000, -0.945 - Math.random() / 10000, mz * 1.15758 - Math.random() / 10000);

                event.setOnGround(true);
                //5
                fallDistance += 4;

                //if (moduleManager.getOption("SNofall", "Debug")) {
                   ChatUtil.display(my + " " + mc.player.fallDistance + " + " + fallDistance + " " + mc.player.ticksExisted);
               // }
            }
       // }
    };
    @SubscribeEvent
    private final EventListener<PacketEvent> cum = event -> {
        if (event.getType().equals(PacketEvent.Type.SENT)) {
            if(event.getPacket() instanceof C01PacketChatMessage)  {
                C01PacketChatMessage C01 = (C01PacketChatMessage) event.getPacket();
                String msg = C01.getMessage();
                if (msg.contains("You will respawn in ")) {
                    respawning = true;
                }
            }
        }
    };
    //@SubscribeEvent
    //    private final EventListener<Render2DEvent> onRender2D = e -> {
    //        ScaledResolution resolution = new ScaledResolution(IMethods.mc);
    //        if(blinking) {
    //            mc.fontRendererObj.drawStringWithShadow("Blinking " + blinkticks, (float) resolution.getScaledWidth() / 2 - 20, (float) resolution.getScaledHeight() / 2 + 10, Client.INSTANCE.getThemeManager().getTheme().getFirstColor().getRGB());
    //        }
    //    };
}