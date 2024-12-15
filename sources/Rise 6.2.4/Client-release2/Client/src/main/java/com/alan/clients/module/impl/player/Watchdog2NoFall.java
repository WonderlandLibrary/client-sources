package com.alan.clients.module.impl.player;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.Mode;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Watchdog2NoFall extends Mode<NoFall> {
    public Watchdog2NoFall(String name, NoFall parent) {
        super(name, parent);
    }

    private boolean fall;

    private long ticks2;


    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        /*
        if(!(Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled() && Client.INSTANCE.getModuleManager().get(Speed.class).mode.getValue().getName().equals("Watchdog"))){
            event.setOnGround(false);
        } else {
        }

         */


          //  ChatUtil.display("f");
            fall = true;
        if (FallDistanceComponent.distance > 2.9 && !getModule(Scaffold.class).isEnabled()) {

            PacketUtil.send(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 0, new ItemStack(Items.water_bucket, 1), 0.5F, 0.5F, 0.5F));

            event.setPosY(event.getPosY() +  1E-13);
            PacketUtil.send(new C03PacketPlayer(true));

            mc.timer.timerSpeed = 0.5f;
            FallDistanceComponent.distance = 0;
        }
        //    event.setPosY(event.getPosY() + Math.random() / 100000000000000000000f);






    };
}
