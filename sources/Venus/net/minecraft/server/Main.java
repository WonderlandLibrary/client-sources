/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import net.minecraft.command.Commands;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ServerPackFinder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerEula;
import net.minecraft.server.ServerPropertiesProvider;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.WorldOptimizer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Bootstrap;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.listener.LoggingChunkStatusListener;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.ServerWorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger field_240759_a_ = LogManager.getLogger();

    public static void main(String[] stringArray) {
        OptionParser optionParser = new OptionParser();
        OptionSpecBuilder optionSpecBuilder = optionParser.accepts("nogui");
        OptionSpecBuilder optionSpecBuilder2 = optionParser.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
        OptionSpecBuilder optionSpecBuilder3 = optionParser.accepts("demo");
        OptionSpecBuilder optionSpecBuilder4 = optionParser.accepts("bonusChest");
        OptionSpecBuilder optionSpecBuilder5 = optionParser.accepts("forceUpgrade");
        OptionSpecBuilder optionSpecBuilder6 = optionParser.accepts("eraseCache");
        OptionSpecBuilder optionSpecBuilder7 = optionParser.accepts("safeMode", "Loads level with vanilla datapack only");
        AbstractOptionSpec abstractOptionSpec = optionParser.accepts("help").forHelp();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec = optionParser.accepts("singleplayer").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec2 = optionParser.accepts("universe").withRequiredArg().defaultsTo(".", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec3 = optionParser.accepts("world").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec4 = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(-1, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec5 = optionParser.accepts("serverId").withRequiredArg();
        NonOptionArgumentSpec<String> nonOptionArgumentSpec = optionParser.nonOptions();
        try {
            Object object;
            Object object2;
            Object object3;
            DataPackRegistries dataPackRegistries;
            OptionSet optionSet = optionParser.parse(stringArray);
            if (optionSet.has(abstractOptionSpec)) {
                optionParser.printHelpOn(System.err);
                return;
            }
            CrashReport.crash();
            Bootstrap.register();
            Bootstrap.checkTranslations();
            Util.func_240994_l_();
            DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
            Path path = Paths.get("server.properties", new String[0]);
            ServerPropertiesProvider serverPropertiesProvider = new ServerPropertiesProvider(impl, path);
            serverPropertiesProvider.save();
            Path path2 = Paths.get("eula.txt", new String[0]);
            ServerEula serverEula = new ServerEula(path2);
            if (optionSet.has(optionSpecBuilder2)) {
                field_240759_a_.info("Initialized '{}' and '{}'", (Object)path.toAbsolutePath(), (Object)path2.toAbsolutePath());
                return;
            }
            if (!serverEula.hasAcceptedEULA()) {
                field_240759_a_.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
                return;
            }
            File file = new File(optionSet.valueOf(argumentAcceptingOptionSpec2));
            YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
            MinecraftSessionService minecraftSessionService = yggdrasilAuthenticationService.createMinecraftSessionService();
            GameProfileRepository gameProfileRepository = yggdrasilAuthenticationService.createProfileRepository();
            PlayerProfileCache playerProfileCache = new PlayerProfileCache(gameProfileRepository, new File(file, MinecraftServer.USER_CACHE_FILE.getName()));
            String string = Optional.ofNullable(optionSet.valueOf(argumentAcceptingOptionSpec3)).orElse(serverPropertiesProvider.getProperties().worldName);
            SaveFormat saveFormat = SaveFormat.create(file.toPath());
            SaveFormat.LevelSave levelSave = saveFormat.getLevelSave(string);
            MinecraftServer.func_240777_a_(levelSave);
            DatapackCodec datapackCodec = levelSave.readDatapackCodec();
            boolean bl = optionSet.has(optionSpecBuilder7);
            if (bl) {
                field_240759_a_.warn("Safe mode active, only vanilla datapack will be loaded");
            }
            ResourcePackList resourcePackList = new ResourcePackList(new ServerPackFinder(), new FolderPackFinder(levelSave.resolveFilePath(FolderName.DATAPACKS).toFile(), IPackNameDecorator.WORLD));
            DatapackCodec datapackCodec2 = MinecraftServer.func_240772_a_(resourcePackList, datapackCodec == null ? DatapackCodec.VANILLA_CODEC : datapackCodec, bl);
            CompletableFuture<DataPackRegistries> completableFuture = DataPackRegistries.func_240961_a_(resourcePackList.func_232623_f_(), Commands.EnvironmentType.DEDICATED, serverPropertiesProvider.getProperties().functionPermissionLevel, Util.getServerExecutor(), Runnable::run);
            try {
                dataPackRegistries = completableFuture.get();
            } catch (Exception exception) {
                field_240759_a_.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", (Throwable)exception);
                resourcePackList.close();
                return;
            }
            dataPackRegistries.updateTags();
            WorldSettingsImport<INBT> worldSettingsImport = WorldSettingsImport.create(NBTDynamicOps.INSTANCE, dataPackRegistries.getResourceManager(), impl);
            IServerConfiguration iServerConfiguration = levelSave.readServerConfiguration(worldSettingsImport, datapackCodec2);
            if (iServerConfiguration == null) {
                if (optionSet.has(optionSpecBuilder3)) {
                    object3 = MinecraftServer.DEMO_WORLD_SETTINGS;
                    object2 = DimensionGeneratorSettings.func_242752_a(impl);
                } else {
                    object = serverPropertiesProvider.getProperties();
                    object3 = new WorldSettings(((ServerProperties)object).worldName, ((ServerProperties)object).gamemode, ((ServerProperties)object).hardcore, ((ServerProperties)object).difficulty, false, new GameRules(), datapackCodec2);
                    object2 = optionSet.has(optionSpecBuilder4) ? ((ServerProperties)object).field_241082_U_.func_236230_k_() : ((ServerProperties)object).field_241082_U_;
                }
                iServerConfiguration = new ServerWorldInfo((WorldSettings)object3, (DimensionGeneratorSettings)object2, Lifecycle.stable());
            }
            if (optionSet.has(optionSpecBuilder5)) {
                Main.func_240761_a_(levelSave, DataFixesManager.getDataFixer(), optionSet.has(optionSpecBuilder6), Main::lambda$main$0, iServerConfiguration.getDimensionGeneratorSettings().func_236226_g_());
            }
            levelSave.saveLevel(impl, iServerConfiguration);
            object3 = iServerConfiguration;
            object2 = MinecraftServer.func_240784_a_(arg_0 -> Main.lambda$main$1(impl, levelSave, resourcePackList, dataPackRegistries, (IServerConfiguration)object3, serverPropertiesProvider, minecraftSessionService, gameProfileRepository, playerProfileCache, optionSet, argumentAcceptingOptionSpec, argumentAcceptingOptionSpec4, optionSpecBuilder3, argumentAcceptingOptionSpec5, optionSpecBuilder, nonOptionArgumentSpec, arg_0));
            object = new Thread("Server Shutdown Thread", (DedicatedServer)object2){
                final DedicatedServer val$dedicatedserver;
                {
                    this.val$dedicatedserver = dedicatedServer;
                    super(string);
                }

                @Override
                public void run() {
                    this.val$dedicatedserver.initiateShutdown(false);
                }
            };
            ((Thread)object).setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_240759_a_));
            Runtime.getRuntime().addShutdownHook((Thread)object);
        } catch (Exception exception) {
            field_240759_a_.fatal("Failed to start the minecraft server", (Throwable)exception);
        }
    }

    private static void func_240761_a_(SaveFormat.LevelSave levelSave, DataFixer dataFixer, boolean bl, BooleanSupplier booleanSupplier, ImmutableSet<RegistryKey<World>> immutableSet) {
        field_240759_a_.info("Forcing world upgrade!");
        WorldOptimizer worldOptimizer = new WorldOptimizer(levelSave, dataFixer, immutableSet, bl);
        ITextComponent iTextComponent = null;
        while (!worldOptimizer.isFinished()) {
            int n;
            ITextComponent iTextComponent2 = worldOptimizer.getStatusText();
            if (iTextComponent != iTextComponent2) {
                iTextComponent = iTextComponent2;
                field_240759_a_.info(worldOptimizer.getStatusText().getString());
            }
            if ((n = worldOptimizer.getTotalChunks()) > 0) {
                int n2 = worldOptimizer.getConverted() + worldOptimizer.getSkipped();
                field_240759_a_.info("{}% completed ({} / {} chunks)...", (Object)MathHelper.floor((float)n2 / (float)n * 100.0f), (Object)n2, (Object)n);
            }
            if (!booleanSupplier.getAsBoolean()) {
                worldOptimizer.cancel();
                continue;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException interruptedException) {}
        }
    }

    private static DedicatedServer lambda$main$1(DynamicRegistries.Impl impl, SaveFormat.LevelSave levelSave, ResourcePackList resourcePackList, DataPackRegistries dataPackRegistries, IServerConfiguration iServerConfiguration, ServerPropertiesProvider serverPropertiesProvider, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, PlayerProfileCache playerProfileCache, OptionSet optionSet, OptionSpec optionSpec, OptionSpec optionSpec2, OptionSpec optionSpec3, OptionSpec optionSpec4, OptionSpec optionSpec5, OptionSpec optionSpec6, Thread thread2) {
        boolean bl;
        DedicatedServer dedicatedServer = new DedicatedServer(thread2, impl, levelSave, resourcePackList, dataPackRegistries, iServerConfiguration, serverPropertiesProvider, DataFixesManager.getDataFixer(), minecraftSessionService, gameProfileRepository, playerProfileCache, LoggingChunkStatusListener::new);
        dedicatedServer.setServerOwner((String)optionSet.valueOf(optionSpec));
        dedicatedServer.setServerPort((Integer)optionSet.valueOf(optionSpec2));
        dedicatedServer.setDemo(optionSet.has(optionSpec3));
        dedicatedServer.setServerId((String)optionSet.valueOf(optionSpec4));
        boolean bl2 = bl = !optionSet.has(optionSpec5) && !optionSet.valuesOf(optionSpec6).contains("nogui");
        if (bl && !GraphicsEnvironment.isHeadless()) {
            dedicatedServer.setGuiEnabled();
        }
        return dedicatedServer;
    }

    private static boolean lambda$main$0() {
        return false;
    }
}

