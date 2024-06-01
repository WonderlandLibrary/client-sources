package com.polarware.module.impl.other;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3i;

import java.util.ArrayList;

@ModuleInfo(name = "module.other.playernotifier.name", description = "module.other.playernotifier.description", category = Category.OTHER)
public final class PlayerNotifierModule extends Module {

    private final NumberValue distance = new NumberValue("Notify Distance", this, 35, 10, 50, 1);
    private final BooleanValue notifyOnce = new BooleanValue("Notify Once", this, false);
    private Vec3i bedPosition = new Vec3i(0, 0, 0);

    private final String[] colors = {"§e", "§6", "§c", "§4"};
    private final ArrayList<EntityLivingBase> notifiedPlayers = new ArrayList<>();


    @Override
    protected void onEnable() {
        bedPosition = new Vec3i(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted % 25 != 0) return;

        threadPool.execute(() -> {
            for (EntityLivingBase entity : mc.theWorld.playerEntities) {
                if (PlayerUtil.sameTeam(entity) || Client.INSTANCE.getBotComponent().contains(entity) || entity == mc.thePlayer) {
                    continue;
                }

                int distance = (int) entity.getDistance(bedPosition.getX(), bedPosition.getY(), bedPosition.getZ());

                if (distance < this.distance.getValue().intValue()) {
                    if (!notifiedPlayers.contains(entity) || !notifyOnce.getValue()) {
                        ChatUtil.display(getColor(distance) + entity.getCommandSenderName() + " is " + distance + " blocks away from your bed.");
                    } else {
                        ChatUtil.display("Didn't display: " + entity.getCommandSenderName());
                    }

                    notifiedPlayers.add(entity);
                } else {
                    notifiedPlayers.remove(entity);
                }
            }
        });
    };

    public String getColor(double distance) {
        double maxDistance = this.distance.getValue().intValue();

        if (distance < 9) {
            return colors[colors.length - 1];
        }

        for (String color : colors) {
            maxDistance /= 2;

            if (distance > maxDistance) {
                return color;
            }
        }

        return colors[colors.length - 1];
    }
}
