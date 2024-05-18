package me.finz0.osiris.module;

import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.gui.GuiManager;
import me.finz0.osiris.gui.clickgui.ClickGuiScreen;
import me.finz0.osiris.gui.clickgui.theme.dark.DarkTheme;
import me.finz0.osiris.module.modules.chat.*;
import me.finz0.osiris.module.modules.combat.*;
import me.finz0.osiris.module.modules.gui.*;
import me.finz0.osiris.module.modules.misc.*;
import me.finz0.osiris.module.modules.movement.*;
import me.finz0.osiris.module.modules.player.*;
import me.finz0.osiris.module.modules.render.*;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;
//ez
public class ModuleManager {
    public static ArrayList<Module> modules;
    private GuiManager guiManager;
    private ClickGuiScreen guiScreen;

    public ModuleManager(){
        modules = new ArrayList<>();
        //Combat
        addMod(new Aura());
        addMod(new AutoWeb());
        addMod(new str2detect());
        addMod(new AutoEchest());
        addMod(new ArmorHUD2());
        addMod(new Trajectories());
        addMod(new Criticals());
        addMod(new DurabiltyWarning());
        addMod(new FastBow());
        addMod(new OffHandGap());
        addMod(new AntiJamie());
        addMod(new AutoCrystal());
        addMod(new AutoTrap());
        addMod(new Surround());
        addMod(new AutoTotem());
        addMod(new HoleFill());
        addMod(new SmartOffHandCrystal());
        //Player
        addMod(new Blink());
        addMod(new PortalGodMode());
        addMod(new FastUse());
        addMod(new NoSwing());
        addMod(new AutoReplanish());
        addMod(new SpeedMine());
        //Movement
        addMod(new Sprint());
        addMod(new Velocity());
        addMod(new NoPush());
        addMod(new GuiMove());
        addMod(new ElytraFly());
        addMod(new NoSlow());
        addMod(new Speed());
    //    addMod(new Jesus());
        //Misc
        addMod(new NoEntityTrace());
        addMod(new XCarry());
        addMod(new AutoNomadHut());
        addMod(new RpcModule());
        addMod(new Notifications());
        addMod(new LogoutSpots());
        addMod(new AutoRespawn());
        addMod(new CropNuker());
        addMod(new MiddleClickFriends());
        addMod(new DeathWaypoint());
        addMod(new ClinetTimer());
        addMod(new BreakTweaks());
        //Chat
        addMod(new LeafHack());
        addMod(new Excuses());
        addMod(new VisualRange());
        addMod(new BetterChat());
        addMod(new ToggleMsgs());
        addMod(new Announcer());
        addMod(new AutoGG());
        addMod(new Spammer());
        addMod(new AutoReply());
        addMod(new Welcomer());
        addMod(new ColorChat());
        addMod(new ChatSuffix());
        addMod(new ChatTimeStamps());
        //Render
        addMod(new GlowESP());
        addMod(new NameTags());
        addMod(new CameraClip());
        addMod(new Brightness());
        addMod(new HoleESP());
        addMod(new StorageESP());
        addMod(new BlockHighlight());
        addMod(new NoRender());
        addMod(new Tracers());
        addMod(new CsgoESP());
        addMod(new CapesModule());
    //    addMod(new Nametags());
        addMod(new OutLineESP());
        addMod(new FovModule());
        addMod(new Esp());
        addMod(new TabGui());
        addMod(new ShulkerPreview());
        //GUI
        addMod(new ModList());
        addMod(new ClickGuiModule());
        addMod(new Watermark());
        addMod(new NiggACAm());
        addMod(new Totems());
        addMod(new Crystals());
        addMod(new Gapples());
        addMod(new Exp());
        addMod(new Coords());
        addMod(new Fps());
        addMod(new Time());
        addMod(new Players());
        addMod(new Tps());
        addMod(new Ping());
        addMod(new PvpInfo());
        addMod(new WelcomerGui());
        addMod(new Bps());
        addMod(new PotionEffects());
        addMod(new NotificationsHud());
        addMod(new Direction());
        addMod(new ArmorHUD());
        addMod(new CurrentHole());
        addMod(new HudEditorModule());
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public ClickGuiScreen getGui() {
        if (this.guiManager == null) {
            this.guiManager = new GuiManager();
            this.guiScreen = new ClickGuiScreen();
            ClickGuiScreen.clickGui = this.guiManager;
            this.guiManager.Initialization();
            this.guiManager.setTheme(new DarkTheme());
        }
        return this.guiManager;
    }

    public static void addMod(Module m){
        modules.add(m);
    }

    public static void onUpdate() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public static void onRender() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onRender);
    }

    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("aurora");

        Minecraft.getMinecraft().profiler.startSection("setup");
//        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();

        GlStateManager.glLineWidth(1f);
        Vec3d renderPos = AutoFeetPlace.getInterpolatedPos(Minecraft.getMinecraft().player, event.getPartialTicks());

        RenderEvent e = new RenderEvent(OsirisTessellator.INSTANCE, renderPos, event.getPartialTicks());
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(module -> module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1f);

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
//        GlStateManager.popMatrix();
        OsirisTessellator.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();

        Minecraft.getMinecraft().profiler.endSection();
    }


    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesInCategory(Module.Category c){
        ArrayList<Module> list = (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList());
        return list;
    }

    public static void onBind(int key) {
        if (key == 0 || key == Keyboard.KEY_NONE) return;
        modules.forEach(module -> {
            if(module.getBind() == key){
                module.toggle();
            }
        });
    }

    public static Module getModuleByName(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m;
    }

    public static boolean isModuleEnabled(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m.isEnabled();
    }

    public static boolean isModuleEnabled(Module m){
        return m.isEnabled();
    }

}
