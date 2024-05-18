package me.finz0.osiris.gui;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.ShutDownHookerino;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.gui.editor.anchor.AnchorPoint;
import me.finz0.osiris.gui.editor.component.*;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.HudConfig;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class HudManager {

    private List<HudComponent> componentList = new CopyOnWriteArrayList<>();
    private List<AnchorPoint> anchorPoints = new ArrayList<>();

    public HudManager() {
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        final AnchorPoint TOP_LEFT = new AnchorPoint(2, 2, AnchorPoint.Point.TOP_LEFT);
        final AnchorPoint TOP_RIGHT = new AnchorPoint(res.getScaledWidth() - 2, 2, AnchorPoint.Point.TOP_RIGHT);
        final AnchorPoint BOTTOM_LEFT = new AnchorPoint(2, res.getScaledHeight() - 2, AnchorPoint.Point.BOTTOM_LEFT);
        final AnchorPoint BOTTOM_RIGHT = new AnchorPoint(res.getScaledWidth() - 2, res.getScaledHeight() - 2, AnchorPoint.Point.BOTTOM_RIGHT);
        final AnchorPoint TOP_CENTER = new AnchorPoint(res.getScaledWidth() / 2, 2, AnchorPoint.Point.TOP_CENTER);
        this.anchorPoints.add(TOP_LEFT);
        this.anchorPoints.add(TOP_RIGHT);
        this.anchorPoints.add(BOTTOM_LEFT);
        this.anchorPoints.add(BOTTOM_RIGHT);
        this.anchorPoints.add(TOP_CENTER);

        this.componentList.add(new WatermarkComponent());
        this.componentList.add(new TpsComponent());
        this.componentList.add(new PotionEffectsComponent());
        this.componentList.add(new FpsComponent());
        this.componentList.add(new CoordsComponent());
        this.componentList.add(new NetherCoordsComponent());
        this.componentList.add(new SpeedComponent());
        this.componentList.add(new ArmorComponent());
        this.componentList.add(new PingComponent());
        this.componentList.add(new ServerBrandComponent());
        this.componentList.add(new BiomeComponent());
        this.componentList.add(new DirectionComponent());
        this.componentList.add(new PacketTimeComponent());
        this.componentList.add(new TimeComponent());
        this.componentList.add(new EnemyPotionsComponent());
        this.componentList.add(new CompassComponent());
        this.componentList.add(new HubComponent());
        this.componentList.add(new InventoryComponent());
        this.componentList.add(new TotemCountComponent());
        this.componentList.add(new TutorialComponent());


        // Organize alphabetically
        this.componentList = this.componentList.stream().sorted(Comparator.comparing(HudComponent::getName)).collect(Collectors.toList());

        AuroraMod.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public Listener<RenderEvent> listener = new Listener(event -> {
        final Minecraft mc = Minecraft.getMinecraft();

        final int chatHeight = (mc.currentScreen instanceof GuiChat) ? 14 : 0;

        ScaledResolution sr = new ScaledResolution(mc);

        for (AnchorPoint point : this.anchorPoints) {
            if (point.getPoint() == AnchorPoint.Point.TOP_LEFT) {
                point.setX(2);
                point.setY(2);
            }
            if (point.getPoint() == AnchorPoint.Point.TOP_RIGHT) {
                point.setX(sr.getScaledWidth() - 2);
                point.setY(2);
            }
            if (point.getPoint() == AnchorPoint.Point.BOTTOM_LEFT) {
                point.setX(2);
                point.setY(sr.getScaledHeight() - chatHeight - 2);
            }
            if (point.getPoint() == AnchorPoint.Point.BOTTOM_RIGHT) {
                point.setX(sr.getScaledWidth() - 2);
                point.setY(sr.getScaledHeight() - chatHeight - 2);
            }
            if (point.getPoint() == AnchorPoint.Point.TOP_CENTER) {
                point.setX(sr.getScaledWidth() / 2);
                point.setY(2);
            }
        }
    });

    @EventHandler
    public Listener<PacketEvent.Receive> packetListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketJoinGame) {
            if (HudConfig.FIRST_HUD_RUN) {
                final Module hudModule = ModuleManager.getModuleByName("HudModule");
                final HudComponent tutorialComponent = this.findComponent(TutorialComponent.class);

                if (hudModule != null) {
                    if (!hudModule.isEnabled()) {
                        hudModule.toggle();
                    }
                }

                if (tutorialComponent != null) {
                    tutorialComponent.setVisible(true);
                }

                HudConfig.FIRST_HUD_RUN = false;
                ShutDownHookerino.saveConfig();
            }
        }

    });

    public void moveToTop(HudComponent component) {
        final Iterator it = this.componentList.iterator();

        while (it.hasNext()) {
            final HudComponent comp = (HudComponent) it.next();
            if (comp != null && comp == component) {
                this.componentList.remove(comp);
                this.componentList.add(comp);
                break;
            }
        }
    }

    public void unload() {
        this.anchorPoints.clear();
        this.componentList.clear();
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }

    public AnchorPoint findPoint(AnchorPoint.Point point) {
        for (AnchorPoint anchorPoint : this.anchorPoints) {
            if (anchorPoint.getPoint() == point) {
                return anchorPoint;
            }
        }
        return null;
    }

    public HudComponent findComponent(String componentName) {
        for (HudComponent component : this.componentList) {
            if (componentName.equalsIgnoreCase(component.getName())) {
                return component;
            }
        }
        return null;
    }

    public HudComponent findComponent(Class componentClass) {
        for (HudComponent component : this.componentList) {
            if (component.getClass() == componentClass) {
                return component;
            }
        }
        return null;
    }

    public List<AnchorPoint> getAnchorPoints() {
        return anchorPoints;
    }

    public void setAnchorPoints(List<AnchorPoint> anchorPoints) {
        this.anchorPoints = anchorPoints;
    }

    public List<HudComponent> getComponentList() {
        return this.componentList.stream().sorted((obj1, obj2) -> obj1.getName().compareTo(obj2.getName())).collect(Collectors.toList());
    }

    public void setComponentList(List<HudComponent> componentList) {
        this.componentList = componentList;
    }
}
