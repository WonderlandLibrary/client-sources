package com.alan.clients.module.impl.render.targetinfo;

import com.alan.clients.component.impl.player.IRCInfoComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.render.TargetInfo;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;

import java.awt.*;

public class WurstTargetInfo extends Mode<TargetInfo> {
    public WurstTargetInfo(String name, TargetInfo parent) {
        super(name, parent);
    }

    private TargetInfo targetInfoModule;

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (this.targetInfoModule == null) {
            this.targetInfoModule = this.getModule(TargetInfo.class);
        }

        Entity target = this.targetInfoModule.target;
        boolean out = (!this.targetInfoModule.inWorld || this.targetInfoModule.stopwatch.finished(1000));

        if (target == null || out) return;

        String normalName = target.getCommandSenderName();
        String name = IRCInfoComponent.formatNick(normalName, normalName);

        double x = this.targetInfoModule.position.x;
        double y = this.targetInfoModule.position.y;

        RenderUtil.rectangle(x, y, 185, 34, ColorUtil.withAlpha(Color.WHITE, 100));
        mc.fontRendererObj.draw("Name: " + name, x + 4, y + 4, Color.BLACK.getRGB());

        this.targetInfoModule.positionValue.scale = new Vector2d(185, 50);
        double health = Math.min(!this.targetInfoModule.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) target).getHealth(), 1), ((AbstractClientPlayer) target).getMaxHealth());
        RenderUtil.rectangle(x + 4, y + 16, (185 - 8) * (health / ((AbstractClientPlayer) target).getMaxHealth()), 10, Color.ORANGE);
    };
}
