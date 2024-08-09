package ru.FecuritySQ;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import ru.FecuritySQ.clickgui.ClickScreen;
import ru.FecuritySQ.command.CommandManager;
import ru.FecuritySQ.command.macro.MacroManager;
import ru.FecuritySQ.config.ConfigManager;
import ru.FecuritySQ.drag.DragManager;
import ru.FecuritySQ.drag.Dragging;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.module.визуальные.*;
import ru.FecuritySQ.module.дисплей.*;
import ru.FecuritySQ.module.игрок.*;
import ru.FecuritySQ.module.общее.*;
import ru.FecuritySQ.module.передвижение.*;
import ru.FecuritySQ.module.сражение.*;
import ru.FecuritySQ.screens.Alt;
import ru.FecuritySQ.screens.AltManagerScreen;
import ru.FecuritySQ.utils.DiscordHelper;
import ru.FecuritySQ.utils.FriendManager;
import ru.FecuritySQ.utils.chunkAnimator.AnimationHandler;
import ru.FecuritySQ.utils.themes.Themes;

import javax.imageio.ImageIO;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FecuritySQ {

    private static FecuritySQ FecuritySQ;
    private ClickScreen clickGui = new ClickScreen();
    private List<Module> moduleList = new ArrayList<>();
    private AnimationHandler animationHandler = new AnimationHandler();
    private FriendManager friendManager = new FriendManager();
    private CommandManager commandManager = new CommandManager();
    public MacroManager macroManager = new MacroManager();
    public ConfigManager configManager = new ConfigManager();
    public Themes theme = Themes.Bloody;

    private String login, uid, cape = "https://minecraft-inside.ru/uploads/capes/1d/02/541.png";

    private ResourceLocation customCapeLocation = new ResourceLocation("FecuritySQ/cape.png");

    public static FecuritySQ get(){
        return FecuritySQ;
    }

    public void start(){
        FecuritySQ = this;
        Fonts.initAll();
        loadModules();
        clickGui.loadPanels();
        try {
            friendManager.init();
            macroManager.init();
            customCapeLocation = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("customCape",
                    new DynamicTexture(NativeImage.read(DynamicTexture.convertImageData(
                            ImageIO.read(new URL(this.cape))))));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        AltManagerScreen.alts.add(new Alt(Minecraft.getInstance().session.getUsername()));
        DiscordHelper.startRPC();
    }



    private void loadModules(){
        moduleList.add(new Sprint());
        moduleList.add(new Hud());
        moduleList.add(new WaterSpeed());
        moduleList.add(new AutoMyst());
        moduleList.add(new ClickGui());
        moduleList.add(new Arrows());
        moduleList.add(new NoDelay());
        moduleList.add(new Theme());
        moduleList.add(new BowSpam());
        moduleList.add(new Velocity());
        moduleList.add(new ESP());
        moduleList.add(new InventoryWalk());
        moduleList.add(new NameTags());
        moduleList.add(new AutoRespawn());
        moduleList.add(new AntiKickAFK());
        moduleList.add(new NoPush());
        moduleList.add(new FastBreak());
        moduleList.add(new KeyPearl());
        moduleList.add(new FullBright());
        moduleList.add(new AutoLeave());
        moduleList.add(new ECExploit());
        moduleList.add(new MidClickFriend());
        moduleList.add(new AutoTool());
        moduleList.add(new Ambience());
        moduleList.add(new NoServerRots());
        moduleList.add(new NameProtect());
        moduleList.add(new NoSlowDown());
        moduleList.add(new Freecam());
        moduleList.add(new AutoExplosion());
        moduleList.add(new ItemScroller());
        moduleList.add(new RWAutoDuel());
        moduleList.add(new AutoTpAccpet());
        moduleList.add(new ChatHider());
        moduleList.add(new NoRender());
        moduleList.add(new ElytraSwap());
        moduleList.add(new DethMovie());
        moduleList.add(new AntiBot());
        moduleList.add(new RWDragonFly());
        moduleList.add(new SwingAnimations());
        moduleList.add(new Tracers());
        moduleList.add(new KillAura());
        moduleList.add(new GAppleCooldown());
        moduleList.add(new NoInteract());
        moduleList.add(new AutoArmor());
        moduleList.add(new ChunkAnimator());
        moduleList.add(new Capes());
        moduleList.add(new AutoPotion());
        moduleList.add(new AutoTotem());
        moduleList.add(new Spider());
        moduleList.add(new NoFriendDamage());
        moduleList.add(new Timer());
        moduleList.add(new ObisidianFixer());
        moduleList.sort(Comparator.comparingInt(m -> Fonts.mntsb16.getStringWidth(((Module) m).getName().toLowerCase())).reversed());
    }

    public Module getModule(Class c){
        return moduleList.stream().filter(module -> module.getClass().equals(c)).findAny().get();
    }

    public Module getModule(String name){
        for(Module m : getModuleList()){
            if(m.getName().equalsIgnoreCase(name)){
                return m;
            }
        }
        return null;
    }

    public Dragging createDrag(Module module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public ClickScreen getClickGui() {
        return clickGui;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public AnimationHandler getAnimationHandler() {
        return animationHandler;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MacroManager getMacroManager() {
        return macroManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCape() {
        return cape;
    }

    public void setCape(String cape) {
        this.cape = cape;
    }

    public ResourceLocation getCustomCapeLocation() {
        return customCapeLocation;
    }
}
