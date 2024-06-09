package com.client.glowclient;

import com.client.glowclient.modules.*;
import com.client.glowclient.commands.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import java.net.*;
import java.nio.file.*;
import java.security.*;
import javax.swing.*;
import java.awt.*;
import com.client.glowclient.modules.other.*;
import com.client.glowclient.modules.render.*;
import com.client.glowclient.modules.combat.*;
import com.client.glowclient.modules.player.*;
import com.client.glowclient.modules.movement.*;
import com.client.glowclient.modules.server.*;
import com.client.glowclient.modules.jewishtricks.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "glowclient", name = "GlowClient", useMetadata = true, clientSideOnly = true)
public class Glow
{
    public static boolean A;
    public static boolean B;
    public static boolean b;
    
    public static boolean D(final String s) {
        return true;
    }
    
    public void e() {
    }
    
    public static String D() {
        return "1.0.4";
    }
    
    private Path M() {
        final String lowerCase;
        if ((lowerCase = System.getProperty("os.name").toLowerCase()).contains("win")) {
            return Paths.get(System.getenv("APPDATA"), ".minecraft");
        }
        if (lowerCase.contains("mac")) {
            return Paths.get(System.getProperty("user.home"), "Library", "Application Support", "minecraft");
        }
        return Paths.get(System.getProperty("user.home"), ".minecraft");
    }
    
    private static void k() {
        zA.M(new Toggle());
        zA.M(new Help());
        zA.M(new Friend());
        zA.M(new Enemy());
        zA.M(new Namehistory());
        zA.M(new Prefix());
        zA.M(new Yawcoordinate());
        zA.M(new Tp());
        zA.M(new Save());
        zA.M(new Bind());
        zA.M(new Unbind());
        zA.M(new Peek());
        zA.M(new Goto());
        zA.M(new Shutdown());
        zA.M(new Vclip());
        final Iterator<NF> iterator = ModuleManager.M().iterator();
        while (iterator.hasNext()) {
            final Module module;
            if ((module = (Module)iterator.next()) instanceof ModuleContainer) {
                zA.M(new Be(module));
            }
        }
        if (Glow.B) {
            zA.M(new Fakemessage());
        }
    }
    
    @Mod$EventHandler
    public void M(final FMLPostInitializationEvent fmlPostInitializationEvent) throws Exception {
        if (Glow.b) {
            final Iterator<NF> iterator = ModuleManager.M().iterator();
            while (iterator.hasNext()) {
                final Module module;
                if ((module = (Module)iterator.next()).k()) {
                    module.A();
                }
            }
        }
        else {
            try {
                final String lowerCase;
                if ((lowerCase = System.getProperty("os.name").toLowerCase()).contains("win")) {
                    Da.M();
                }
                else if (lowerCase.contains("mac")) {
                    Da.M();
                }
                else {
                    Da.M();
                }
                FMLCommonHandler.instance().exitJava(-1, true);
            }
            catch (Exception ex) {
                Glow.b = false;
            }
        }
    }
    
    public static void M(final String s, final String s2) throws Exception {
        Files.copy(new URL(s).openStream(), Paths.get(s2, new String[0]), StandardCopyOption.REPLACE_EXISTING);
    }
    
    public static String M() throws Exception {
        String s = "";
        final byte[] digest = MessageDigest.getInstance("MD5").digest(new StringBuilder().insert(0, System.getenv("PROCESSOR_IDENTIFIER")).append(System.getenv("COMPUTERNAME")).append(System.getProperty("user.name").trim()).toString().getBytes("UTF-8"));
        int n = 0;
        final byte[] array;
        final int length = (array = digest).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            s = new StringBuilder().insert(0, s).append(Integer.toHexString((array[n2] & 0xFF) | 0x300).substring(0, 3)).toString();
            if (n != digest.length - 1) {
                s = new StringBuilder().insert(0, s).append("").toString();
            }
            ++n;
            i = ++n2;
        }
        return s;
    }
    
    public void A() throws Exception {
        if (!Glow.b) {
            final JFrame frame2;
            final JFrame frame = frame2 = new JFrame();
            frame.setSize(400, 150);
            final Dimension screenSize;
            final int n = (screenSize = Toolkit.getDefaultToolkit().getScreenSize()).width / 2;
            final JFrame frame3 = frame2;
            frame.setLocation(n - frame3.getSize().width / 2, screenSize.height / 2 - frame2.getSize().height / 2);
            frame3.setVisible(true);
            frame.setTitle("ERROR");
            final JPanel panel = new JPanel(new GridLayout(0, 1));
            final JPanel panel2 = new JPanel(new FlowLayout(1));
            final JPanel panel3 = new JPanel(new FlowLayout(1));
            final JLabel label = new JLabel("YOU ARE NOT CURRENTLY PERMITTED TO USE GLOWCLIENT!");
            panel2.add(label);
            final JLabel label2 = new JLabel();
            if (!D("https://raw.githubusercontent.com/GlowClient/GlowClient.github.io/master/hwid.txt")) {
                label2.setText("Your IP, Username, and HWID have been logged for security!");
            }
            panel3.add(label2);
            panel.add(panel2);
            panel.add(panel3);
            final JFrame frame4 = frame2;
            final JPanel panel4 = panel;
            final JLabel label3 = label2;
            final boolean b = true;
            final JLabel label4 = label;
            final JPanel panel5 = panel2;
            final boolean b2 = true;
            panel3.setVisible(true);
            panel5.setVisible(b2);
            label4.setVisible(b2);
            label3.setVisible(b);
            panel4.setVisible(b);
            frame4.add(panel4);
            frame2.setVisible(true);
        }
    }
    
    public Glow() {
        super();
    }
    
    @Mod$EventHandler
    public void M(final FMLInitializationEvent fmlInitializationEvent) {
        Ob.M().A();
    }
    
    static {
        Glow.A = false;
        Glow.B = false;
        Glow.b = false;
    }
    
    private static void D() {
        if (Glow.b) {
            new iD();
            if (Glow.B) {
                iD.M().setWindows(Dg.f, Dg.L, Dg.G, Dg.b, Dg.A, Dg.d, Dg.M, Dg.B);
            }
            else {
                iD.M().setWindows(Dg.L, Dg.G, Dg.b, Dg.A, Dg.d, Dg.M, Dg.B);
            }
            iD.M().initWindows();
        }
    }
    
    private static void M() {
        if (Glow.b) {
            ModuleManager.M((Module)new eE());
            ModuleManager.M((Module)new hE());
            ModuleManager.M((Module)new me());
            ModuleManager.M((Module)new Lg());
            ModuleManager.M((Module)new he());
            ModuleManager.M((Module)new ColorManager());
            ModuleManager.M((Module)new PlayerList());
            ModuleManager.M((Module)new BowSpam());
            ModuleManager.M((Module)new EnchColors());
            ModuleManager.M((Module)new AntiAFK());
            ModuleManager.M((Module)new AntiPackets());
            ModuleManager.M((Module)new AntiPotion());
            ModuleManager.M((Module)new AutoArmor());
            ModuleManager.M((Module)new AutoEat());
            ModuleManager.M((Module)new AutoFish());
            ModuleManager.M((Module)new AutoGapple());
            ModuleManager.M((Module)new AutoLog());
            ModuleManager.M((Module)new AutoMine());
            ModuleManager.M((Module)new AutoReconnect());
            ModuleManager.M((Module)new AutoRespawn());
            ModuleManager.M((Module)new AutoTool());
            ModuleManager.M((Module)new AutoTotem());
            ModuleManager.M((Module)new AutoWalk());
            ModuleManager.M((Module)new BackSpeed());
            ModuleManager.M((Module)new BedAura());
            ModuleManager.M((Module)new Blink());
            ModuleManager.M((Module)new BoatAura());
            ModuleManager.M((Module)new BoatFly());
            ModuleManager.M((Module)new BoatJump());
            ModuleManager.M((Module)new BreadCrumbs());
            ModuleManager.M((Module)new BypassFly());
            ModuleManager.M((Module)new CameraClip());
            ModuleManager.M((Module)new Chams());
            ModuleManager.M((Module)new ChunkFinder());
            ModuleManager.M((Module)new SignMod());
            ModuleManager.M((Module)new Criticals());
            ModuleManager.M((Module)new CrystalAura());
            ModuleManager.M((Module)new ElytraControl());
            ModuleManager.M((Module)new EntityControl());
            ModuleManager.M((Module)new EntityESP());
            ModuleManager.M((Module)new EntityHunger());
            ModuleManager.M((Module)new EntitySpeed());
            ModuleManager.M((Module)new FastBreak());
            ModuleManager.M((Module)new FastUse());
            ModuleManager.M((Module)new Flight());
            ModuleManager.M((Module)new FontChat());
            ModuleManager.M((Module)new Freecam());
            ModuleManager.M((Module)new InvMove());
            ModuleManager.M((Module)new HighJump());
            ModuleManager.M((Module)new HorseMod());
            ModuleManager.M((Module)new HUD());
            ModuleManager.M((Module)new IceSpeed());
            ModuleManager.M((Module)new Jesus());
            ModuleManager.M((Module)new KillAura());
            ModuleManager.M((Module)new Knockback());
            ModuleManager.M((Module)new LiquidSpeed());
            ModuleManager.M((Module)new LogoutESP());
            ModuleManager.M((Module)new FullBright());
            ModuleManager.M((Module)new MCF());
            ModuleManager.M((Module)new Nametags());
            ModuleManager.M((Module)new NoFall());
            ModuleManager.M((Module)new NoPush());
            ModuleManager.M((Module)new NoRender());
            ModuleManager.M((Module)new NoRotate());
            ModuleManager.M((Module)new NoSlow());
            ModuleManager.M((Module)new NoVoid());
            ModuleManager.M((Module)new NoWeather());
            ModuleManager.M((Module)new Nuker());
            ModuleManager.M((Module)new Phase());
            ModuleManager.M((Module)new PortalGodMode());
            ModuleManager.M((Module)new Reach());
            ModuleManager.M((Module)new SafeWalk());
            ModuleManager.M((Module)new Scaffold());
            ModuleManager.M((Module)new SkinBlinker());
            ModuleManager.M((Module)new Sprint());
            ModuleManager.M((Module)new Step());
            ModuleManager.M((Module)new StorageESP());
            ModuleManager.M((Module)new Speed());
            ModuleManager.M((Module)new TabMod());
            ModuleManager.M((Module)new Timer());
            ModuleManager.M((Module)new Tracers());
            ModuleManager.M((Module)new Trajectories());
            ModuleManager.M((Module)new Xray());
            ModuleManager.M((Module)new Yaw());
            ModuleManager.M((Module)new BlockFinder());
            ModuleManager.M((Module)new NoGhostBlocks());
            ModuleManager.M((Module)new GlowBot());
            ModuleManager.M((Module)new Spider());
            ModuleManager.M((Module)new XCarry());
            ModuleManager.M((Module)new PeekMod());
            ModuleManager.M((Module)new IllegalHelper());
            ModuleManager.M((Module)new KickBow());
            ModuleManager.M((Module)new AutoIgnore());
            ModuleManager.M((Module)new SourceRemover());
            ModuleManager.M((Module)new RandomBook());
            ModuleManager.M((Module)new SchematicaPrinter());
            ModuleManager.M((Module)new BoatTravel());
            ModuleManager.M((Module)new NameChanger());
            if (Glow.B) {
                ModuleManager.M((Module)new MappingBot());
                ModuleManager.M((Module)new ExploitDev());
                ModuleManager.M((Module)new Dupe());
                ModuleManager.M((Module)new SpawnPlayer());
                ModuleManager.M((Module)new RemoveEntities());
                ModuleManager.M((Module)new HUDImpact());
                ModuleManager.M((Module)new NametagsImpact());
                ModuleManager.M((Module)new TracersImpact());
            }
        }
    }
    
    @Mod$EventHandler
    public void M(final FMLPreInitializationEvent fmlPreInitializationEvent) throws Exception {
        if (Glow.B != Glow.A) {
            Glow.B = Glow.A;
        }
        Glow.b = (D("https://raw.githubusercontent.com/GlowClient/GlowClient.github.io/master/hwid.txt") && M("https://raw.githubusercontent.com/GlowClient/GlowClient.github.io/master/Enabled.txt"));
        final String s = "<3 7'}{h&&#i3. /!%!4157(:31) i7(9h\u0013+;0\u0017+=\":3{\u00008(#\u00048.1) i3. /!%z.;h9&'315{/#.0i ? ";
        this.e();
        if (!D(tB.M(s))) {
            try {
                final String lowerCase;
                if ((lowerCase = System.getProperty("os.name").toLowerCase()).contains("win")) {
                    Da.M();
                }
                else if (lowerCase.contains("mac")) {
                    Da.M();
                }
                else {
                    Da.M();
                }
                FMLCommonHandler.instance().exitJava(-1, true);
            }
            catch (Exception ex) {
                Glow.b = false;
            }
        }
        Glow glow = null;
        Label_0196: {
            if (!Da.M()) {
                System.out.println("Version requires update!");
                Glow.b = false;
                try {
                    final String lowerCase2;
                    if ((lowerCase2 = System.getProperty("os.name").toLowerCase()).contains("win")) {
                        Da.M();
                    }
                    else if (lowerCase2.contains("mac")) {
                        Da.M();
                    }
                    else {
                        Da.M();
                    }
                    FMLCommonHandler.instance().exitJava(-1, true);
                    glow = this;
                    break Label_0196;
                }
                catch (Exception ex2) {
                    Glow.b = false;
                }
            }
            glow = this;
        }
        glow.A();
        if (Glow.b) {
            M();
            k();
            D();
        }
    }
    
    public static boolean M(final String s) {
        return true;
    }
}
