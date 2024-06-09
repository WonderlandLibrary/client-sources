package me.finz0.osiris.hud;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import me.finz0.osiris.hud.components.*;

import java.util.ArrayList;
import java.util.List;

public class HudComponentManager {
    public HudComponentManager(double ix, double iy, ClickGUI parent){
        hudComponents = new ArrayList<>();
        addComponent(new FpsComponent(ix, iy, parent));
        addComponent(new TpsComponent(ix, iy + 20, parent));
        addComponent(new CoordsComponent(ix, iy + 40, parent));
        addComponent(new WatermarkComponent(ix, iy + 60, parent));
        addComponent(new BpsComponent(ix, iy + 80, parent));
        addComponent(new PingComponent(ix, iy + 100, parent));
        addComponent(new WelcomerComponent(ix, iy + 120, parent));
        addComponent(new TotemsComponent(ix, iy + 140, parent));
        addComponent(new TimeComponent(ix, iy + 160, parent));
        addComponent(new PvpinfoComponent(ix, iy + 180, parent));
        addComponent(new GappsComponent(ix, iy + 200, parent));
        addComponent(new ExpComponent(ix, iy + 220, parent));
        addComponent(new CrystalsComponent(ix, iy + 240, parent));
        addComponent(new PlayerComponent(ix, iy + 260, parent));
        addComponent(new InventoryComponent(ix, iy + 280, parent));
        addComponent(new DirectionComponent(ix, iy + 300, parent));
        addComponent(new ArmorComponent(ix + 50, iy, parent));
        addComponent(new HoleComponent(ix + 50, iy + 20, parent));
    }

    public static List<Panel> hudComponents;

    private void addComponent(Panel p){
        hudComponents.add(p);
    }

    public static Panel getHudComponentByName(String name){
            Panel pa = null;
            for(Panel p : hudComponents){
                if(p.title.equalsIgnoreCase(name)) pa = p;
            }
            return pa;
    }
}
