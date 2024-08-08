// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.misc;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import me.perry.mcdonalds.util.ReflectionUtil;
import me.perry.mcdonalds.features.gui.McDonaldsGui;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import me.perry.mcdonalds.features.command.Command;
import me.perry.mcdonalds.util.TextUtil;
import me.perry.mcdonalds.event.events.ClientEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.perry.mcdonalds.event.events.PacketEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;
import me.perry.mcdonalds.features.Feature;
import java.util.Iterator;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import me.perry.mcdonalds.util.Util;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import me.perry.mcdonalds.util.InventoryUtil;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.inventory.GuiInventory;
import me.perry.mcdonalds.features.setting.Bind;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class XCarry extends Module
{
    private final Setting<Boolean> simpleMode;
    private final Setting<Bind> autoStore;
    private final Setting<Integer> obbySlot;
    private final Setting<Integer> slot1;
    private final Setting<Integer> slot2;
    private final Setting<Integer> slot3;
    private final Setting<Integer> tasks;
    private final Setting<Boolean> store;
    private final Setting<Boolean> shiftClicker;
    private final Setting<Boolean> withShift;
    private final Setting<Bind> keyBind;
    private static XCarry INSTANCE;
    private GuiInventory openedGui;
    private final AtomicBoolean guiNeedsClose;
    private boolean guiCloseGuard;
    private boolean autoDuelOn;
    private final Queue<InventoryUtil.Task> taskList;
    private boolean obbySlotDone;
    private boolean slot1done;
    private boolean slot2done;
    private boolean slot3done;
    private List<Integer> doneSlots;
    
    public XCarry() {
        super("XCarry", "Uses the crafting inventory for storage", Category.PLAYER, true, false, false);
        this.simpleMode = (Setting<Boolean>)this.register(new Setting("Simple", (T)false));
        this.autoStore = (Setting<Bind>)this.register(new Setting("AutoSort", (T)new Bind(-1)));
        this.obbySlot = (Setting<Integer>)this.register(new Setting("ObbySlot", (T)3, (T)1, (T)9, v -> this.autoStore.getValue().getKey() != -1));
        this.slot1 = (Setting<Integer>)this.register(new Setting("Slot1", (T)22, (T)9, (T)44, v -> this.autoStore.getValue().getKey() != -1));
        this.slot2 = (Setting<Integer>)this.register(new Setting("Slot2", (T)23, (T)9, (T)44, v -> this.autoStore.getValue().getKey() != -1));
        this.slot3 = (Setting<Integer>)this.register(new Setting("Slot3", (T)24, (T)9, (T)44, v -> this.autoStore.getValue().getKey() != -1));
        this.tasks = (Setting<Integer>)this.register(new Setting("Actions", (T)3, (T)1, (T)12, v -> this.autoStore.getValue().getKey() != -1));
        this.store = (Setting<Boolean>)this.register(new Setting("Store", (T)false));
        this.shiftClicker = (Setting<Boolean>)this.register(new Setting("ShiftClick", (T)false));
        this.withShift = (Setting<Boolean>)this.register(new Setting("WithShift", (T)true, v -> this.shiftClicker.getValue()));
        this.keyBind = (Setting<Bind>)this.register(new Setting("ShiftBind", (T)new Bind(-1), v -> this.shiftClicker.getValue()));
        this.openedGui = null;
        this.guiNeedsClose = new AtomicBoolean(false);
        this.guiCloseGuard = false;
        this.autoDuelOn = false;
        this.taskList = new ConcurrentLinkedQueue<InventoryUtil.Task>();
        this.obbySlotDone = false;
        this.slot1done = false;
        this.slot2done = false;
        this.slot3done = false;
        this.doneSlots = new ArrayList<Integer>();
        this.setInstance();
    }
    
    private void setInstance() {
        XCarry.INSTANCE = this;
    }
    
    public static XCarry getInstance() {
        if (XCarry.INSTANCE == null) {
            XCarry.INSTANCE = new XCarry();
        }
        return XCarry.INSTANCE;
    }
    
    @Override
    public void onUpdate() {
        if (this.shiftClicker.getValue() && Util.mc.currentScreen instanceof GuiInventory) {
            final boolean ourBind = this.keyBind.getValue().getKey() != -1 && Keyboard.isKeyDown(this.keyBind.getValue().getKey()) && !Keyboard.isKeyDown(42);
            if (((Keyboard.isKeyDown(42) && this.withShift.getValue()) || ourBind) && Mouse.isButtonDown(0)) {
                final Slot slot = ((GuiInventory)Util.mc.currentScreen).getSlotUnderMouse();
                if (slot != null && InventoryUtil.getEmptyXCarry() != -1) {
                    final int slotNumber = slot.slotNumber;
                    if (slotNumber > 4 && ourBind) {
                        this.taskList.add(new InventoryUtil.Task(slotNumber));
                        this.taskList.add(new InventoryUtil.Task(InventoryUtil.getEmptyXCarry()));
                    }
                    else if (slotNumber > 4 && this.withShift.getValue()) {
                        boolean isHotBarFull = true;
                        boolean isInvFull = true;
                        for (final int i : InventoryUtil.findEmptySlots(false)) {
                            if (i > 4 && i < 36) {
                                isInvFull = false;
                            }
                            else {
                                if (i <= 35 || i >= 45) {
                                    continue;
                                }
                                isHotBarFull = false;
                            }
                        }
                        if (slotNumber > 35 && slotNumber < 45) {
                            if (isInvFull) {
                                this.taskList.add(new InventoryUtil.Task(slotNumber));
                                this.taskList.add(new InventoryUtil.Task(InventoryUtil.getEmptyXCarry()));
                            }
                        }
                        else if (isHotBarFull) {
                            this.taskList.add(new InventoryUtil.Task(slotNumber));
                            this.taskList.add(new InventoryUtil.Task(InventoryUtil.getEmptyXCarry()));
                        }
                    }
                }
            }
        }
        if (this.autoDuelOn) {
            this.doneSlots = new ArrayList<Integer>();
            if (InventoryUtil.getEmptyXCarry() == -1 || (this.obbySlotDone && this.slot1done && this.slot2done && this.slot3done)) {
                this.autoDuelOn = false;
            }
            if (this.autoDuelOn) {
                if (!this.obbySlotDone && !Util.mc.player.inventory.getStackInSlot(this.obbySlot.getValue() - 1).isEmpty) {
                    this.addTasks(36 + this.obbySlot.getValue() - 1);
                }
                this.obbySlotDone = true;
                if (!this.slot1done && !Util.mc.player.inventoryContainer.inventorySlots.get(this.slot1.getValue()).getStack().isEmpty) {
                    this.addTasks(this.slot1.getValue());
                }
                this.slot1done = true;
                if (!this.slot2done && !Util.mc.player.inventoryContainer.inventorySlots.get(this.slot2.getValue()).getStack().isEmpty) {
                    this.addTasks(this.slot2.getValue());
                }
                this.slot2done = true;
                if (!this.slot3done && !Util.mc.player.inventoryContainer.inventorySlots.get(this.slot3.getValue()).getStack().isEmpty) {
                    this.addTasks(this.slot3.getValue());
                }
                this.slot3done = true;
            }
        }
        else {
            this.obbySlotDone = false;
            this.slot1done = false;
            this.slot2done = false;
            this.slot3done = false;
        }
        if (!this.taskList.isEmpty()) {
            for (int j = 0; j < this.tasks.getValue(); ++j) {
                final InventoryUtil.Task task = this.taskList.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
    
    private void addTasks(final int slot) {
        if (InventoryUtil.getEmptyXCarry() != -1) {
            int xcarrySlot = InventoryUtil.getEmptyXCarry();
            if (this.doneSlots.contains(xcarrySlot) || !InventoryUtil.isSlotEmpty(xcarrySlot)) {
                ++xcarrySlot;
                if (this.doneSlots.contains(xcarrySlot) || !InventoryUtil.isSlotEmpty(xcarrySlot)) {
                    ++xcarrySlot;
                    if (this.doneSlots.contains(xcarrySlot) || !InventoryUtil.isSlotEmpty(xcarrySlot)) {
                        ++xcarrySlot;
                        if (this.doneSlots.contains(xcarrySlot) || !InventoryUtil.isSlotEmpty(xcarrySlot)) {
                            return;
                        }
                    }
                }
            }
            if (xcarrySlot > 4) {
                return;
            }
            this.doneSlots.add(xcarrySlot);
            this.taskList.add(new InventoryUtil.Task(slot));
            this.taskList.add(new InventoryUtil.Task(xcarrySlot));
            this.taskList.add(new InventoryUtil.Task());
        }
    }
    
    @Override
    public void onDisable() {
        if (!Feature.fullNullCheck()) {
            if (!this.simpleMode.getValue()) {
                this.closeGui();
                this.close();
            }
            else {
                Util.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(Util.mc.player.inventoryContainer.windowId));
            }
        }
    }
    
    @Override
    public void onLogout() {
        this.onDisable();
    }
    
    @SubscribeEvent
    public void onCloseGuiScreen(final PacketEvent.Send event) {
        if (this.simpleMode.getValue() && event.getPacket() instanceof CPacketCloseWindow) {
            final CPacketCloseWindow packet = event.getPacket();
            if (packet.windowId == Util.mc.player.inventoryContainer.windowId) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onGuiOpen(final GuiOpenEvent event) {
        if (!this.simpleMode.getValue()) {
            if (this.guiCloseGuard) {
                event.setCanceled(true);
            }
            else if (event.getGui() instanceof GuiInventory) {
                event.setGui((GuiScreen)(this.openedGui = this.createGuiWrapper((GuiInventory)event.getGui())));
                this.guiNeedsClose.set(false);
            }
        }
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting() != null && event.getSetting().getFeature() != null && event.getSetting().getFeature().equals(this)) {
            final Setting setting = event.getSetting();
            final String settingname = event.getSetting().getName();
            if (setting.equals(this.simpleMode)) {
                this.disable();
            }
            else if (settingname.equalsIgnoreCase("Store")) {
                event.setCanceled(true);
                this.autoDuelOn = !this.autoDuelOn;
                Command.sendMessage("<XCarry> " + TextUtil.GREEN + "Autostoring...");
            }
        }
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && !(Util.mc.currentScreen instanceof McDonaldsGui) && this.autoStore.getValue().getKey() == Keyboard.getEventKey()) {
            this.autoDuelOn = !this.autoDuelOn;
            Command.sendMessage("<XCarry> " + TextUtil.GREEN + "Autostoring...");
        }
    }
    
    private void close() {
        this.openedGui = null;
        this.guiNeedsClose.set(false);
        this.guiCloseGuard = false;
    }
    
    private void closeGui() {
        if (this.guiNeedsClose.compareAndSet(true, false) && !Feature.fullNullCheck()) {
            this.guiCloseGuard = true;
            Util.mc.player.closeScreen();
            if (this.openedGui != null) {
                this.openedGui.onGuiClosed();
                this.openedGui = null;
            }
            this.guiCloseGuard = false;
        }
    }
    
    private GuiInventory createGuiWrapper(final GuiInventory gui) {
        try {
            final GuiInventoryWrapper wrapper = new GuiInventoryWrapper();
            ReflectionUtil.copyOf(gui, wrapper);
            return wrapper;
        }
        catch (NoSuchFieldException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        XCarry.INSTANCE = new XCarry();
    }
    
    private class GuiInventoryWrapper extends GuiInventory
    {
        GuiInventoryWrapper() {
            super((EntityPlayer)Util.mc.player);
        }
        
        protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
            if (XCarry.this.isEnabled() && (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))) {
                XCarry.this.guiNeedsClose.set(true);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else {
                super.keyTyped(typedChar, keyCode);
            }
        }
        
        public void onGuiClosed() {
            if (XCarry.this.guiCloseGuard || !XCarry.this.isEnabled()) {
                super.onGuiClosed();
            }
        }
    }
}
