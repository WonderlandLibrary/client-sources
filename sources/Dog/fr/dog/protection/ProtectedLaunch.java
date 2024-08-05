package fr.dog.protection;

import dev.liticane.transpiler.Native;
import fr.dog.Dog;
import fr.dog.anticheat.AnticheatManager;
import fr.dog.command.CommandManager;
import fr.dog.command.impl.*;
import fr.dog.command.irc.IRC;
import fr.dog.component.Component;
import fr.dog.component.ComponentManager;
import fr.dog.component.impl.packet.BlinkComponent;
import fr.dog.component.impl.player.HypixelComponent;
import fr.dog.component.impl.player.RotationComponent;
import fr.dog.component.impl.ui.CSGOComponent;
import fr.dog.config.ConfigManager;
import fr.dog.imgui.ImGuiRendererImpl;
import fr.dog.module.ModuleManager;
import fr.dog.module.impl.combat.*;
import fr.dog.module.impl.exploit.*;
import fr.dog.module.impl.movement.*;
import fr.dog.module.impl.player.*;
import fr.dog.module.impl.player.Timer;
import fr.dog.module.impl.render.*;
import fr.dog.theme.ThemeManager;
import fr.dog.util.backend.HWIDUtil;
import fr.dog.util.math.TimeUtil;
import net.minecraft.util.MathHelper;
import org.lwjglx.opengl.Display;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.function.Function;

@Native
public class ProtectedLaunch {
    public static final CustomCape CUSTOM_CAPE = new CustomCape();

    //TODO : NOT BLOATED CODE !
    public static void init(String uid, String addition) throws Throwable {
        Dog.getInstance().setUsername("allah");
        Dog.getInstance().setUid("0000");
        Dog.getInstance().setToken("rattedlolezpzfrfr");

        { // Modules
            ModuleManager moduleManager = new ModuleManager();

            moduleManager.register(
                    // Combat
                    new AntiBot(),
                    new AutoClicker(),
                    new AutoGoldenHead(),
                    new Criticals(),
                    new KillAuraModule(),
                    new SumoBot(),
                    new Velocity(),

                    // Movement
                    new FireballFlight(),
                    new InvMove(),
                    new NoSlowdown(),
                    new SafeWalk(),
                    new Speed(),
                    new Sprint(),
                    new SaveMoveKey(),

                    // Player
                    new AntiFireBall(),
                    new Anticheat(),
                    new AutoGG(),
                    new AutoPlay(),
                    new AutoTool(),
                    new AutoWho(),
                    new Blink(),
                    new BreakerModule(),
                    new ChestStealer(),
                    new FastBreak(),
                    new FastPlace(),
                    new InvManager(),
                    new LegitScaffold(),
                    new NoFall(),
                    new RandomModuleToggler(),
                    new Scaffold(),
                    new Timer(),

                    // Render
                    new Ambiance(),
                    new Animation(),
                    new BlockESP(),
                    new BreakProgress(),
                    new Camera(),
                    new ChestESP(),
                    new ClickGui(),
                    CUSTOM_CAPE,
                    new DamageIndicator(),
                    new DogshitESPModule(),
                    new ESP(),
                    new Freelook(),
                    new FullBright(),
                    new FurrySpeech(),
                    new HUD(),
                    new NameTags(),
                    new Notification(),
                    new Overlay(),
                    new PartyMembers(),
                    //new SessionStats(),
                    new TargetHUD(),

                    // Exploits
                    new Disabler(),
                    new FastUse(),
                    new Respawn(),
                    new TransactionLogger()
            );


            if(addition.equalsIgnoreCase("on")){
                moduleManager.register(
                );
            }

            Dog.getInstance().getEventBus().register(moduleManager);
            Dog.getInstance().setModuleManager(moduleManager);
        }

        { // Commands
            CommandManager commandManager = new CommandManager();

            commandManager.register(new BindCommand(), new ConfigCommand(), new ToggleCommand(), new ThemeCommand(),
                    new OnlineConfigCommand(), new QueueCommand(), new HypixelAPICommand(), new HelpCommand(), new CSGOCommand(), new KeyConfigCommand(),
                    new CustomNameCommand(), new IgnCommand(), new AVMDCommand());

            Dog.getInstance().getEventBus().register(commandManager);
            Dog.getInstance().setCommandManager(commandManager);
        }

        { // Components
            ComponentManager componentManager = new ComponentManager();

            componentManager.register(new RotationComponent(), new BlinkComponent(), new HypixelComponent(), new CSGOComponent());

            for (Component component : componentManager.getObjects()) {
                Dog.getInstance().getEventBus().register(component);
            }

            Dog.getInstance().setComponentManager(componentManager);
        }

        Dog.getInstance().setConfigManager(new ConfigManager());

        Dog.getInstance().getConfigManager().init();

        ImGuiRendererImpl.initialize(Display.getHandle());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Dog.getInstance().getConfigManager().stop();

            Dog.getInstance().getIrc().sendMessageTo("dog/joins", Dog.getInstance().getUsername() + " >> Left");
        }));

        Dog.getInstance().setCheckManager(new AnticheatManager());
        Dog.getInstance().setThemeManager(new ThemeManager());
        Dog.getInstance().setIrc(new IRC());
        Dog.getInstance().getIrc().sendMessageTo("dog/joins", Dog.getInstance().getUsername() + " >> Joined");
        Display.setTitle("Dog Client Recode - " + Dog.getInstance().getUsername());
        HypixelComponent.loadCheaters();

        HypixelComponent.initHypixelAPI();





    }

    private static SecretKey getKeyFromString(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private static IvParameterSpec getIvFromString(String iv) {
        byte[] decodedIv = Base64.getDecoder().decode(iv);
        return new IvParameterSpec(decodedIv);
    }

    @Native
    public static class EncryptionData {
        public SecretKey secretKey;
        public IvParameterSpec ivParameterSpec;

        public String standardAlgorithm = "AES/CBC/PKCS5Padding";

        public EncryptionData(SecretKey secretKey, IvParameterSpec ivParameterSpec) {
            this.secretKey = secretKey;
            this.ivParameterSpec = ivParameterSpec;
        }

        public String decryptToString(String input) {
            String decrypted = "";

            try {
                Cipher cipher = Cipher.getInstance(standardAlgorithm);
                cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivParameterSpec);
                byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(input));

                decrypted = new String(plainText);
            } catch (Exception ignored) { /* */ }

            return decrypted;
        }
    }
}