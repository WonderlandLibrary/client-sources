package com.alan.clients.component;

import com.alan.clients.Client;
import com.alan.clients.component.impl.event.EntityKillEventComponent;
import com.alan.clients.component.impl.event.EntityTickComponent;
import com.alan.clients.component.impl.event.MouseEventComponent;
import com.alan.clients.component.impl.hud.AdaptiveRefreshRateComponent;
import com.alan.clients.component.impl.hud.DragComponent;
import com.alan.clients.component.impl.hypixel.APIKeyComponent;
import com.alan.clients.component.impl.packetlog.PacketLogComponent;
import com.alan.clients.component.impl.patches.GuiClosePatchComponent;
import com.alan.clients.component.impl.performance.ParticleDistanceComponent;
import com.alan.clients.component.impl.player.*;
import com.alan.clients.component.impl.render.*;
import com.alan.clients.component.impl.viamcp.*;

import java.util.HashMap;
import java.util.Map;
public final class ComponentManager {

    private final Map<Class<Component>, Component> componentList = new HashMap<>();

    /**
     * Called on client start and when for some reason when we reinitialize
     */
    public void init() {
        this.add(new EntityKillEventComponent());
//        this.add(new Translation());
        this.add(new EntityTickComponent());
        this.add(new AdaptiveRefreshRateComponent());
        this.add(new DragComponent());
        this.add(new APIKeyComponent());
        this.add(new PacketLogComponent());
        this.add(new GuiClosePatchComponent());
        this.add(new ParticleDistanceComponent());
        this.add(new BadPacketsComponent());
        this.add(new BlinkComponent());
        this.add(new GUIDetectionComponent());
        this.add(new ItemDamageComponent());
        this.add(new LastConnectionComponent());
        this.add(new MovementBlinkComponent());
        this.add(new PingComponent());
        this.add(new PingSpoofComponent());
        this.add(new RotationComponent());
        this.add(new SelectorDetectionComponent());
        this.add(new Slot());
        this.add(new ESPComponent());
        this.add(new NotificationComponent());
        this.add(new IRCInfoComponent());
        this.add(new ParticleComponent());
        this.add(new PercentageComponent());
        this.add(new ProjectionComponent());
        this.add(new SmoothCameraComponent());
        this.add(new BlockPlacementFixComponent());
        this.add(new FlyingPacketFixComponent());
        this.add(new HitboxFixComponent());
        this.add(new LadderFixComponent());
        this.add(new MinimumMotionFixComponent());
        this.add(new FallDistanceComponent());
        this.add(new TargetComponent());
        this.add(new MouseEventComponent());
        this.add(new PacketlessDamageComponent());
        this.add(new BoundsFixComponent());
        this.add(new TransactionFixComponent());
        this.add(new SpeedFixComponent());
        this.add(new PostFixComponent());
        this.add(new WatchdogScaffoldComponent());

        // Registers all components to the eventbus
        this.componentList.forEach((componentClass, component) -> Client.INSTANCE.getEventBus().register(component));
        this.componentList.forEach(((componentClass, component) -> component.onInit()));
    }

    public void add(final Component component) {
        this.componentList.put((Class<Component>) component.getClass(), component);
    }

    public <T extends Component> T get(final Class<T> clazz) {
        return (T) this.componentList.get(clazz);
    }
}