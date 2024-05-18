package wwe.modules.render;

import wwe.modules.*;
import java.util.*;
import wwe.utils.*;
import net.minecraft.client.entity.*;
import wwe.*;
import net.minecraft.entity.*;

public class Hud extends qMIe
{
    private long Yakj;
    private double zStx;
    private double ETYB;
    private double IvEZ;
    private static double ZClU;
    public sbfc Xijd;
    public ModeValue arrayListMode;
    public ModeValue information;
    public BooleanValue textShadow;
    public BooleanValue waterMark;
    public BooleanValue arrayList;
    public BooleanValue fps;
    public BooleanValue overworldCoords;
    public BooleanValue netherCoords;
    public BooleanValue tps;
    public BooleanValue armorHUD;
    public BooleanValue tabGui;
    public BooleanValue rects;
    public NumberValue rectslength;
    public BooleanValue coolerRects;
    public BooleanValue bps;
    public BooleanValue bPSTimerSync;
    public BooleanValue radar;
    public BooleanValue radarChests;
    public ModeValue radarMode;
    public BooleanValue radarLines;
    public ModeValue radarIndicator;
    public ModeValue radarRotation;
    public ModeValue radarEntities;
    public NumberValue radarScale;
    public BooleanValue potions;
    public BooleanValue lagMeter;
    public BooleanValue entities;
    public BooleanValue ping;
    public BooleanValue hudDurability;
    public BooleanValue itemDurability;
    public BooleanValue lavaWarning;
    public BooleanValue biome;
    public int wkYe;
    public int pxFw;
    
    public Hud() {
        super("Hud", "Shows info on your screen", 0, Category.RENDER);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Top Left");
        list.add("Bottom Left");
        list.add("Top Right");
        list.add("Bottom Right");
        this.arrayListMode = new ModeValue("Array List Mode", this, "Where the array list should render", "Bottom Right", list);
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Top Left");
        list2.add("Bottom Left");
        list2.add("Top Right");
        list2.add("Bottom Right");
        this.information = new ModeValue("Information", this, "Where all the hud elements render", "Bottom Left", list2);
        this.textShadow = new BooleanValue("Text Shadow", this, "Puts a shadow around text", true);
        this.waterMark = new BooleanValue("Water Mark", this, "Render water mark", true);
        this.arrayList = new BooleanValue("Array List", this, "Render array list", true);
        this.fps = new BooleanValue("FPS", this, "Render frames per second", true);
        this.overworldCoords = new BooleanValue("Overworld Coords", this, "Render your overworld coords", true);
        this.netherCoords = new BooleanValue("Nether Coords", this, "Render your nether coords", true);
        this.tps = new BooleanValue("TPS", this, "Render ticks per second", true);
        this.armorHUD = new BooleanValue("ArmorHUD", this, "Render armor above hunger bars", true);
        this.tabGui = new BooleanValue("Tab Gui", this, "Render tab gui", true);
        this.rects = new BooleanValue("Rects", this, "Render rectangles around the array list", false);
        this.rectslength = new NumberValue("Rects length", this, "The length of the rects/rectangles", 1.0, 0.5, 2.0);
        this.coolerRects = new BooleanValue("Cooler Rects", this, "Render cooler rectangles around the array list", false);
        this.bps = new BooleanValue("BPS", this, "Render your blocks per second", true);
        this.bPSTimerSync = new BooleanValue("BPS Timer Sync", this, "Sync blocks per second with timer", true);
        this.radar = new BooleanValue("Radar", this, "Render radar", false);
        this.radarChests = new BooleanValue("Radar Chests", this, "Shows chests on the radar", true);
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("Circle");
        list3.add("Square");
        this.radarMode = new ModeValue("Radar Mode", this, "Current mode radar is using", "Square", list3);
        this.radarLines = new BooleanValue("Radar Lines", this, "Renders a cross hair in the radar", true);
        final ArrayList<String> list4 = new ArrayList<String>();
        list4.add("Minecraft");
        list4.add("WWE");
        list4.add("None");
        this.radarIndicator = new ModeValue("Radar Indicator", this, "Renders a 'indicator' where you are on the radar", "Minecraft", list4);
        final ArrayList<String> list5 = new ArrayList<String>();
        list5.add("Radar");
        list5.add("Indicator");
        list5.add("None");
        this.radarRotation = new ModeValue("Radar Rotation", this, "How the radar rotations with your yaw", "Indicator", list5);
        final ArrayList<String> list6 = new ArrayList<String>();
        list6.add("Circles");
        list6.add("Squares");
        this.radarEntities = new ModeValue("Radar Entities", this, "What the entities in radar are", "Squares", list6);
        this.radarScale = new NumberValue("Radar Scale", this, "Size of the radar", 50.0, 0.0, 100.0);
        this.potions = new BooleanValue("Potions", this, "Render potions", true);
        this.lagMeter = new BooleanValue("Lag Meter", this, "Render lag meter", true);
        this.entities = new BooleanValue("Entities", this, "Show loaded entities on your screen", false);
        this.ping = new BooleanValue("Ping", this, "Render ping", false);
        this.hudDurability = new BooleanValue("Hud Durability", this, "Changes minecrafts durability to text", false);
        this.itemDurability = new BooleanValue("Item Durability", this, "Shows your held items' current durability", false);
        this.lavaWarning = new BooleanValue("Lava Warning", this, "Tells you theres lava 10 blocks or less under you", true);
        this.biome = new BooleanValue("Biome", this, "Renders the biome your on", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.bps.OnGi()) {
            if (Wrapper.mc.player == null) {
                return;
            }
            final EntityPlayerSP player = Wrapper.mc.player;
            final double n = this.zStx - ((Entity)player).lastTickPosX;
            final double n2 = this.IvEZ - ((Entity)player).lastTickPosZ;
            final double n3 = Math.sqrt(n * n + n2 * n2) * 20.0;
            float axho;
            if (this.bPSTimerSync.OnGi()) {
                axho = vlwv.Axho;
                if (Hud.\u2002\u2006\u2001\u200b\u2003\u200f) {
                    throw null;
                }
            }
            else {
                axho = 1.0f;
            }
            Hud.ZClU = n3 * axho;
            this.zStx = ((Entity)player).lastTickPosX;
            this.ETYB = ((Entity)player).lastTickPosY;
            this.IvEZ = ((Entity)player).lastTickPosZ;
        }
    }
    
    public static double CwjD() {
        return Hud.ZClU;
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    @oqrr
    public void EriV(final LqJY lqJY) {
        this.Xijd.BBwj();
    }
    
    public boolean zDcj() {
        boolean b;
        if (this.arrayListMode.OnGi().equalsIgnoreCase("Top Left") || this.arrayListMode.OnGi().equalsIgnoreCase("Top Right")) {
            b = true;
            if (Hud.\u2002\u2006\u2001\u200b\u2003\u200f) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean MNmw() {
        boolean b;
        if (this.arrayListMode.OnGi().equalsIgnoreCase("Bottom Left") || this.arrayListMode.OnGi().equalsIgnoreCase("Bottom Right")) {
            b = true;
            if (Hud.\u2002\u2006\u2001\u200b\u2003\u200f) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean oLhm() {
        boolean b;
        if (this.arrayListMode.OnGi().equalsIgnoreCase("Top Left") || this.arrayListMode.OnGi().equalsIgnoreCase("Bottom Left")) {
            b = true;
            if (Hud.\u2002\u2006\u2001\u200b\u2003\u200f) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean LTya() {
        boolean b;
        if (this.arrayListMode.OnGi().equalsIgnoreCase("Top Right") || this.arrayListMode.OnGi().equalsIgnoreCase("Bottom Right")) {
            b = true;
            if (Hud.\u2002\u2006\u2001\u200b\u2003\u200f) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
}
