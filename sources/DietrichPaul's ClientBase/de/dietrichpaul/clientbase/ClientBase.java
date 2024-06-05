/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase;

import de.dietrichpaul.clientbase.config.ConfigList;
import de.dietrichpaul.clientbase.feature.FontList;
import de.dietrichpaul.clientbase.feature.FriendList;
import de.dietrichpaul.clientbase.feature.KeybindingList;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickEngine;
import de.dietrichpaul.clientbase.feature.command.CommandList;
import de.dietrichpaul.clientbase.feature.engine.inventory.InventoryEngine;
import de.dietrichpaul.clientbase.feature.engine.inventory.InventoryHandler;
import de.dietrichpaul.clientbase.feature.engine.lag.LagEngine;
import de.dietrichpaul.clientbase.feature.hack.HackList;
import de.dietrichpaul.clientbase.feature.engine.rotation.RotationEngine;
import de.dietrichpaul.clientbase.util.render.api.Blur;
import de.dietrichpaul.clientbase.util.render.api.Renderer2D;
import de.florianmichael.dietrichevents.DietrichEvents;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
/*
 * Clientbase by DietrichPaul
 * <p>
 * TODO | General
 *  - AimbotRotationSpoof target through walls property
 *  - AimbotRotationSpoof priority who dies at first
 *  - KillAura Jitter-Click -> RotationSpoof Rotation Jitter
 *  - only delay transaction if subscribers for DelayEvent !empty
 */
public class ClientBase implements ClientModInitializer {
    public final static ModMetadata METADATA = FabricLoader.getInstance().getModContainer("clientbase").orElseThrow().getMetadata();

    public final static String NAME = METADATA.getName();
    public final static String VERSION = METADATA.getVersion().getFriendlyString();
    public final static String AUTHORS = METADATA.getAuthors().stream().map(Person::getName).collect(Collectors.joining(", "));

    public static ClientBase INSTANCE;

    private final DietrichEvents eventDispatcher = DietrichEvents.createThreadSafe();
    private final File directory = new File(MinecraftClient.getInstance().runDirectory, NAME);

    private ClickEngine clickEngine;
    private InventoryEngine inventoryEngine;
    private RotationEngine rotationEngine;
    private LagEngine lagEngine;

    private FontList fontList;
    private CommandList commandList;
    private FriendList friendList;
    private HackList hackList;
    private KeybindingList keybindingList;
    private ConfigList configList;

    // Pre Init - Before Minecraft
    @Override
    public void onInitializeClient() {
        INSTANCE = this;

        this.directory.mkdir();
        Blur.loadShaders();
        Renderer2D.loadShaders();

        this.clickEngine = new ClickEngine();
        this.inventoryEngine = new InventoryEngine();
        this.rotationEngine = new RotationEngine();
        this.lagEngine = new LagEngine();

        this.fontList = new FontList();
        this.commandList = new CommandList();
        this.friendList = new FriendList();
        this.hackList = new HackList();
        this.keybindingList = new KeybindingList();
        this.configList = new ConfigList();
    }

    // Post Init - After Minecraft
    public void init() {
        this.hackList.registerBuiltIn();
        this.commandList.registerBuiltInCommands();
        this.configList.start();
    }

    public void stop() {
        // Not implemented atm
    }

    public LagEngine getLagEngine() {
        return lagEngine;
    }

    public DietrichEvents getEventDispatcher() {
        return eventDispatcher;
    }

    public File getDirectory() {
        return directory;
    }

    public ClickEngine getClickEngine() {
        return clickEngine;
    }

    public InventoryEngine getInventoryEngine() {
        return inventoryEngine;
    }

    public RotationEngine getRotationEngine() {
        return rotationEngine;
    }

    public FontList getFontList() {
        return fontList;
    }

    public CommandList getCommandList() {
        return commandList;
    }

    public FriendList getFriendList() {
        return friendList;
    }

    public HackList getHackList() {
        return hackList;
    }

    public KeybindingList getKeybindingList() {
        return keybindingList;
    }

    public ConfigList getConfigList() {
        return configList;
    }
}
