package wwe;

import java.util.*;
import wwe.modules.gui.*;
import wwe.modules.movement.*;
import wwe.modules.render.*;
import wwe.modules.combat.*;
import wwe.modules.player.*;
import wwe.modules.world.*;
import wwe.modules.exploits.*;

public class eOnJ
{
    public ArrayList<qMIe> ijZl;
    public List<qMIe> fuFn;
    
    public eOnJ() {
        super();
        this.ijZl = new ArrayList<qMIe>();
        this.fuFn = new ArrayList<qMIe>();
        this.ijZl.add(new Speed());
        this.ijZl.add(new Gui());
        this.ijZl.add(new NoFall());
        this.ijZl.add(new AntiKnockback());
        this.ijZl.add(new InventoryWalk());
        this.ijZl.add(new Flight());
        this.ijZl.add(new MobESP());
        this.ijZl.add(new Sprint());
        this.ijZl.add(new ColorList());
        this.ijZl.add(new NoSlowDown());
        this.ijZl.add(new OutlineESP());
        this.ijZl.add(new AutoLog());
        this.ijZl.add(new ItemESP());
        this.ijZl.add(new Freecam());
        this.ijZl.add(new AutoRespawn());
        this.ijZl.add(new ChestStealer());
        this.ijZl.add(new SafeWalk());
        this.ijZl.add(new AutoArmor());
        this.ijZl.add(new Timer());
        this.ijZl.add(new FastPlace());
        this.ijZl.add(new HorseJump());
        this.ijZl.add(new PlayerESP());
        this.ijZl.add(new Elytra());
        this.ijZl.add(new FullBright());
        this.ijZl.add(new ChestESP());
        this.ijZl.add(new EntitySpeed());
        this.ijZl.add(new CrystalAura());
        this.ijZl.add(new AntiWeather());
        this.ijZl.add(new Tracers());
        this.ijZl.add(new Hud());
        this.ijZl.add(new AutoTotem());
        this.ijZl.add(new PacketFly());
        this.ijZl.add(new Scaffold());
        this.ijZl.add(new Criticals());
        this.ijZl.add(new CustomFont());
        this.ijZl.add(new Step());
        this.ijZl.add(new AntiVoid());
        this.ijZl.add(new NameTags());
        this.ijZl.add(new ElytraReplace());
        this.ijZl.add(new DeathCoords());
        this.ijZl.add(new JoinLeavemsgs());
        this.ijZl.add(new MCF());
        this.ijZl.add(new LogOutSpot());
        this.ijZl.add(new KillAura());
        this.ijZl.add(new BoatFly());
        this.ijZl.add(new Blink());
        this.ijZl.add(new BloodParticles());
        this.ijZl.add(new EntityRide());
        this.ijZl.add(new AutoClicker());
        this.ijZl.add(new AimAssist());
        this.ijZl.add(new Chams());
        this.ijZl.add(new NoPush());
        this.ijZl.add(new XCarry());
        this.ijZl.add(new AutoCrystal());
        this.ijZl.add(new NoTurn());
        this.ijZl.add(new CombatInfo());
        this.ijZl.add(new RainbowEnchant());
        this.ijZl.add(new BowAimBot());
        this.ijZl.add(new AutoFarm());
        this.ijZl.add(new IceSpeed());
        this.ijZl.add(new Jesus());
        this.ijZl.add(new AutoFish());
        this.ijZl.add(new NoRender());
        this.ijZl.add(new PathFinder());
        this.ijZl.add(new Toxic());
        this.ijZl.add(new OffHand());
        this.ijZl.add(new Xray());
        this.ijZl.add(new Yaw());
        this.ijZl.add(new AutoWalk());
        this.ijZl.add(new CameraClip());
        this.ijZl.add(new Search());
        this.ijZl.add(new SpeedMine());
        this.ijZl.add(new LowShield());
        this.ijZl.add(new Trajectories());
        this.ijZl.add(new IRC());
        this.ijZl.add(new AntiLevitate());
        this.ijZl.add(new Peek());
        this.ijZl.add(new LiquidInteract());
        this.ijZl.add(new InventoryCleaner());
        this.ijZl.add(new Spammer());
        this.ijZl.add(new WayPoints());
        this.ijZl.add(new AutoMine());
        this.ijZl.add(new Sneak());
        this.ijZl.add(new AntiHunger());
        this.ijZl.add(new ColorSigns());
        this.ijZl.add(new Tunneller());
        this.ijZl.add(new ClickTP());
        this.ijZl.add(new BedGodMode());
        this.ijZl.add(new BoatPhase());
        this.ijZl.add(new AutoTool());
        this.ijZl.add(new Portal());
        this.ijZl.add(new Nuker());
        this.ijZl.add(new TpsSync());
        this.ijZl.add(new FancyChat());
        this.ijZl.add(new Insulter());
        this.ijZl.add(new Surround());
        this.ijZl.add(new NoCaveCulling());
        this.ijZl.add(new InfiniteChatLength());
        this.ijZl.add(new KeyRepeat());
        this.ijZl.add(new ChatBot());
        this.ijZl.add(new F3Spoofer());
        this.ijZl.add(new AutoSign());
        this.ijZl.add(new HitBoxes());
        this.ijZl.add(new WaterBucket());
        this.ijZl.add(new AntiBookBan());
        this.ijZl.add(new ItemSaver());
        this.ijZl.add(new NoBreakDelay());
        this.ijZl.add(new Announcer());
        this.ijZl.add(new TreasureMapCoords());
        this.ijZl.add(new AutoEat());
        this.ijZl.add(new StrongHoldFinder());
        this.ijZl.add(new TimeStamp());
        this.ijZl.add(new NoteBot());
        this.ijZl.add(new NoteBotStealer());
        this.ijZl.add(new OffHandCrash());
        this.ijZl.add(new TeleportFinder());
    }
    
    public ArrayList<qMIe> xRen() {
        return this.ijZl;
    }
    
    public qMIe yYLK(final String s) {
        int i = 0;
        while (i < this.xRen().size()) {
            final qMIe qmIe = this.xRen().get(i);
            if (qmIe.czUH().equalsIgnoreCase(s)) {
                return qmIe;
            }
            ++i;
            if (eOnJ.\u2008\u2001\u2007) {
                throw null;
            }
        }
        return null;
    }
    
    public <T extends qMIe> T qHPF(final Class<T> clazz) {
        int i = 0;
        while (i < this.xRen().size()) {
            final qMIe qmIe = this.xRen().get(i);
            if (qmIe.getClass() == clazz) {
                return (T)qmIe;
            }
            ++i;
            if (eOnJ.\u2008\u2001\u2007) {
                throw null;
            }
        }
        return null;
    }
    
    public void KSAz() {
        int i = 0;
        while (i < this.xRen().size()) {
            this.xRen().get(i).OgHM();
            ++i;
            if (eOnJ.\u2008\u2001\u2007) {
                throw null;
            }
        }
    }
}
