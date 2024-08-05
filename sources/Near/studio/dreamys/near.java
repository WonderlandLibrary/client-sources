package studio.dreamys;

import com.logisticscraft.occlusionculling.OcclusionCullingInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import studio.dreamys.clickgui.ClickGUI;
import studio.dreamys.command.Chw;
import studio.dreamys.entityculling.Config;
import studio.dreamys.entityculling.CullTask;
import studio.dreamys.entityculling.Provider;
import studio.dreamys.event.ChestSlotClickedEvent;
import studio.dreamys.event.GuiChestBackgroundDrawnEvent;
import studio.dreamys.font.Fonts;
import studio.dreamys.module.Module;
import studio.dreamys.module.ModuleManager;
import studio.dreamys.setting.SettingsManager;
import studio.dreamys.util.SaveLoad;

import java.io.IOException;
import java.util.List;

@Mod(modid = near.MODID, name = near.NAME, version = near.VERSION)
public class near {
    public static final String MODID = "near";
    public static final String NAME = "near";
    public static final String VERSION = "1.0";
    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static ClickGUI clickGUI;
    public static SaveLoad saveLoad;
    @SuppressWarnings("unused")
    public static String token;

    public OcclusionCullingInstance culling;
    public static CullTask cullTask;

    public static int renderedBlockEntities;
    public static int skippedBlockEntities;
    public static int renderedEntities;
    public static int skippedEntities;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
//        APIUtils.postLogin();
        Fonts.loadFonts();
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new Chw());

        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        clickGUI = new ClickGUI();
        saveLoad = new SaveLoad();

        culling = new OcclusionCullingInstance(Config.tracingDistance, new Provider());
        cullTask = new CullTask(culling, Config.blockEntityWhitelist);

        Thread cullThread = new Thread(cullTask, "CullThread");
        cullThread.setUncaughtExceptionHandler((thread, ex) -> {
            System.out.println("The CullingThread has crashed! Please report the following stacktrace!");
            ex.printStackTrace();
        });
        cullThread.start();
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) throws IOException {
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        if (Keyboard.getEventKeyState()) {
            int keyCode = Keyboard.getEventKey();
            if (keyCode <= 0) return;
            for (Module m : moduleManager.modules) {
                if (m.getKey() == keyCode) m.toggle();
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        cullTask.requestCull = true;
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        cullTask.requestCull = true;
    }

    @SubscribeEvent
    public void onGuiMouseInputPre(GuiScreenEvent.MouseInputEvent.Pre event) {
//        if (!Utils.inSkyblock) return;
        if (Mouse.getEventButton() != 0 && Mouse.getEventButton() != 1 && Mouse.getEventButton() != 2)
            return; // Left click, middle click or right click
        if (!Mouse.getEventButtonState()) return;

        if (event.gui instanceof GuiChest) {
            Container containerChest = ((GuiChest) event.gui).inventorySlots;
            if (containerChest instanceof ContainerChest) {
                // a lot of declarations here, if you get scarred, my bad
                GuiChest chest = (GuiChest) event.gui;
                IInventory inventory = ((ContainerChest) containerChest).getLowerChestInventory();
                Slot slot = chest.getSlotUnderMouse();
                if (slot == null) return;
                ItemStack item = slot.getStack();
                String inventoryName = inventory.getDisplayName().getUnformattedText();
                if (item == null) {
                    if (MinecraftForge.EVENT_BUS.post(new ChestSlotClickedEvent(chest, inventory, inventoryName, slot))) event.setCanceled(true);
                } else {
                    if (MinecraftForge.EVENT_BUS.post(new ChestSlotClickedEvent(chest, inventory, inventoryName, slot, item))) event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (event.gui instanceof GuiChest) {
            GuiChest inventory = (GuiChest) event.gui;
            Container containerChest = inventory.inventorySlots;
            if (containerChest instanceof ContainerChest) {
                List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
                String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                int chestSize = inventory.inventorySlots.inventorySlots.size();

                MinecraftForge.EVENT_BUS.post(new GuiChestBackgroundDrawnEvent(inventory, displayName, chestSize, invSlots));
            }
        }
    }
}
