package in.momin5.cookieclient.api.module;

import in.momin5.cookieclient.api.event.events.EventRender;
import in.momin5.cookieclient.api.util.utils.render.DRenderUtils;
import in.momin5.cookieclient.client.gui.hud.HUDEditor;
import in.momin5.cookieclient.client.modules.client.ClickGuiMod;
import in.momin5.cookieclient.client.modules.client.CustomFont;
import in.momin5.cookieclient.client.modules.client.Notifications;
import in.momin5.cookieclient.client.modules.combat.*;
import in.momin5.cookieclient.client.modules.hud.Arraylist;
import in.momin5.cookieclient.client.modules.hud.Ping;
import in.momin5.cookieclient.client.modules.hud.Watermark;
import in.momin5.cookieclient.client.modules.misc.*;
import in.momin5.cookieclient.client.modules.movement.*;
import in.momin5.cookieclient.client.modules.player.*;
import in.momin5.cookieclient.client.modules.render.FullBright;
import in.momin5.cookieclient.client.modules.render.ViewModel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModuleManager {

    public static ArrayList<Module> modules = new ArrayList<>();
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * for basically sorting the modules so they appear a to z
     * cause cool shit
     */
    class Sort implements Comparator<Module>{
        @Override
        public int compare(Module module, Module t1) {
            return module.getName().compareTo(t1.getName());
        }
    }

    public ModuleManager() {

        ModuleManager.modules.add(new AutoDisconnect());
        ModuleManager.modules.add(new AutoTrap());
        ModuleManager.modules.add(new AutoXP());
        //ModuleManager.modules.add(new AutoTotem());
        ModuleManager.modules.add(new Burrow());
        ModuleManager.modules.add(new ClickGuiMod());
        //ModuleManager.modules.add(new CrystalAura());
        ModuleManager.modules.add(new CustomFont());
        ModuleManager.modules.add(new DiscordRPC());
        ModuleManager.modules.add(new FakePlayer());
        ModuleManager.modules.add(new Freecam());
        ModuleManager.modules.add(new FullBright());
        ModuleManager.modules.add(new HoleFill());
        ModuleManager.modules.add(new IceSpeed());
        ModuleManager.modules.add(new InventoryMove());
        ModuleManager.modules.add(new KillAura());
        ModuleManager.modules.add(new MultiTask());
        ModuleManager.modules.add(new NoEntityTrace());
        ModuleManager.modules.add(new NoPush());
        ModuleManager.modules.add(new NoSlow());
        ModuleManager.modules.add(new PacketCanceller());
        ModuleManager.modules.add(new ReverseStep());
        ModuleManager.modules.add(new Scaffold());
        ModuleManager.modules.add(new SpeedMine());
        ModuleManager.modules.add(new Sprint());
        ModuleManager.modules.add(new Strafe());
        ModuleManager.modules.add(new Step());
        ModuleManager.modules.add(new Suffix());
        ModuleManager.modules.add(new Surround());
        ModuleManager.modules.add(new Speed());
        ModuleManager.modules.add(new Velocity());
        ModuleManager.modules.add(new Offhand());
        ModuleManager.modules.add(new CATest());
        ModuleManager.modules.add(new Quiver());
        ModuleManager.modules.add(new ViewModel());
        ModuleManager.modules.add(new AutoRespawn());
        ModuleManager.modules.add(new AutoArmor());

        // HUD

        ModuleManager.modules.add(new Arraylist());
        ModuleManager.modules.add(new HUDEditor());
        ModuleManager.modules.add(new Notifications());
        ModuleManager.modules.add(new Watermark());
        ModuleManager.modules.add(new Ping());

        // the sort thingy
        modules.sort(new Sort());

    }

    public static void onUpdate() { modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate); }
    public static void onRender() { modules.stream().filter(Module::isEnabled).forEach(Module::onRender); }

    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("CookieClient");
        Minecraft.getMinecraft().profiler.startSection("setup");
        DRenderUtils.prepare();
        EventRender e = new EventRender(event.getPartialTicks());
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(module -> module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        DRenderUtils.release();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }

    public static boolean nullCheck() {
        return (mc.player == null || mc.world == null);
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) return;
        try {
            if(Keyboard.isCreated()) {
                if(Keyboard.getEventKeyState()) {
                    int keyCode = Keyboard.getEventKey();
                    if(keyCode <= 0)
                        return;
                    for(Module m : ModuleManager.modules) {
                        if(m.getBind() == keyCode && keyCode > 0) {
                            m.toggle();
                        }
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }

    public static boolean isModuleEnabled(String name) { return Objects.requireNonNull(modules.stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null)).isEnabled(); }
    public static boolean isModuleEnabled(Class<? extends Module> clazz) { return Objects.requireNonNull(modules.stream().filter(mm -> mm.getClass() == clazz).findFirst().orElse(null)).isEnabled(); }

    public static ArrayList<Module> getModules() {
        return modules;
    }
    public static ArrayList<Module> getModules(Category c) { return (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList()); }
    public static Module getModule(String name) {
            Module m = modules.stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
            return m;
    }
    public static Module getModule(Class moduleClass) { return modules.stream().filter(module -> module.getClass() == moduleClass).findFirst().orElse(null); }

    public static ArrayList<Module> getModulesInCategory(Category c){
        ArrayList<Module> list = (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList());
        return list;
    }


    public static Module getModuleInnit (String name) { // only made for the config system since the modules are already read by buffered reader in config, so no need to stream them just directly read
        for (Module m : ModuleManager.modules) { // im so smart :sunglasses: - momin5
            if(m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }


    // returns the component without casting, powered by momentum and gamesense
    public static Module getModuleByClass(Class<?> clazz) {
        return modules.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
    }
}
