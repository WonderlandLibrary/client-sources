package studio.dreamys.module.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import studio.dreamys.event.ChestSlotClickedEvent;
import studio.dreamys.event.GuiChestBackgroundDrawnEvent;
import studio.dreamys.font.Fonts;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;
import studio.dreamys.util.RenderUtils;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Solvers extends Module {
    public Solvers() {
        super("Solvers", Category.MISC);
        set(new Setting("Chronomatron", this, true));
        set(new Setting("Superpairs", this, true));
        set(new Setting("Ultrasequencer", this, true));
    }

    //Chronomatron
    static int lastChronomatronRound;
    static final List<String> chronomatronPattern = new ArrayList<>();
    static int chronomatronMouseClicks;
    public static final int CHRONOMATRON_NEXT = Color.GREEN.getRGB();
    public static final int CHRONOMATRON_NEXT_TO_NEXT = Color.RED.getRGB();

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (near.settingsManager.getSettingByName(this, "Chronomatron").getValBoolean() && event.inventoryName.startsWith("Chronomatron (")) {
            IInventory inventory = event.inventory;
            ItemStack item = event.item;
            if (item == null) return;

            if (inventory.getStackInSlot(49).getDisplayName().startsWith("§7Timer: §a") && (item.getItem() == Item.getItemFromBlock(Blocks.stained_glass) || item.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay))) {
                chronomatronMouseClicks++;
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (near.settingsManager.getSettingByName(this, "Chronomatron").getValBoolean() && event.displayName.startsWith("Chronomatron (")) {
            int chestSize = event.chestSize;
            List<Slot> invSlots = event.slots;
            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a") && invSlots.get(4).getStack() != null) {
                    int round = invSlots.get(4).getStack().stackSize;
                    int timerSeconds = Integer.parseInt(StringUtils.stripControlCodes(invSlots.get(49).getStack().getDisplayName()).replaceAll("[^\\d]", ""));
                    if (round != lastChronomatronRound && timerSeconds == round + 2) {
                        lastChronomatronRound = round;
                        for (int i = 10; i <= 43; i++) {
                            ItemStack stack = invSlots.get(i).getStack();
                            if (stack == null) continue;
                            if (stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
                                chronomatronPattern.add(stack.getDisplayName());
                                break;
                            }
                        }
                    }
                    if (chronomatronMouseClicks < chronomatronPattern.size()) {
                        for (int i = 10; i <= 43; i++) {
                            ItemStack glass = invSlots.get(i).getStack();
                            if (glass == null) continue;

                            Slot glassSlot = invSlots.get(i);

                            if (chronomatronMouseClicks + 1 < chronomatronPattern.size()) {
                                if (chronomatronPattern.get(chronomatronMouseClicks).equals(chronomatronPattern.get(chronomatronMouseClicks + 1))) {
                                    if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                        RenderUtils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
                                    }
                                } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                    RenderUtils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
                                } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks + 1))) {
                                    RenderUtils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT_TO_NEXT + 0XBE000000);
                                }
                            } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                RenderUtils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
                            }
                        }
                    }
                } else if (invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    chronomatronMouseClicks = 0;
                }
            }
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution sr = new ScaledResolution(mc);
            int guiLeft = (sr.getScaledWidth() - 176) / 2;
            Fonts.font35RobotoMedium.drawString(String.join("\n", chronomatronPattern), (int) (guiLeft * 0.8), 10, 1);
        }

        if (near.settingsManager.getSettingByName(this, "Superpairs").getValBoolean() && event.displayName.contains("Superpairs (")) {
            HashMap<String, HashSet<Integer>> matches = new HashMap<>();
            for (int i = 0; i < 53; i++) {
                ItemStack itemStack = experimentTableSlots[i];
                if (itemStack == null) continue;

                //Utils.renderItem(itemStack, x, y, -100);

                String itemName = itemStack.getDisplayName();
                String keyName = itemName + itemStack.getUnlocalizedName();
                matches.computeIfAbsent(keyName, k -> new HashSet<>());
                matches.get(keyName).add(i);
            }

            Color[] colors = {
                    new Color(255, 0, 0, 100),
                    new Color(0, 0, 255, 100),
                    new Color(100, 179, 113, 100),
                    new Color(255, 114, 255, 100),
                    new Color(255, 199, 87, 100),
                    new Color(119, 105, 198, 100),
                    new Color(135, 199, 112, 100),
                    new Color(240, 37, 240, 100),
                    new Color(178, 132, 190, 100),
                    new Color(63, 135, 163, 100),
                    new Color(146, 74, 10, 100),
                    new Color(255, 255, 255, 100),
                    new Color(217, 252, 140, 100),
                    new Color(255, 82, 82, 100)
            };

            Iterator<Color> colorIterator = Arrays.stream(colors).iterator();

            matches.forEach((itemName, slotSet) -> {
                if (slotSet.size() < 2) return;
                ArrayList<Slot> slots = new ArrayList<>();
                slotSet.forEach(slotNum -> slots.add(event.slots.get(slotNum)));
                Color color = colorIterator.next();
                slots.forEach(slot -> RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, color.getRGB()));
            });
        }

        if (near.settingsManager.getSettingByName(this, "Ultrasequencer").getValBoolean() && event.displayName.startsWith("Ultrasequencer (")) {
            List<Slot> invSlots = event.slots;
            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a")) {
                    lastUltraSequencerClicked = 0;
                    for (Slot slot : clickInOrderSlots) {
                        if (slot != null && slot.getStack() != null && StringUtils.stripControlCodes(slot.getStack().getDisplayName()).matches("\\d+")) {
                            int number = Integer.parseInt(StringUtils.stripControlCodes(slot.getStack().getDisplayName()));
                            if (number > lastUltraSequencerClicked) {
                                lastUltraSequencerClicked = number;
                            }
                        }
                    }
                    if (clickInOrderSlots[lastUltraSequencerClicked] != null) {
                        Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                        RenderUtils.drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT + 0xE5000000);
                    }
                    if (lastUltraSequencerClicked + 1 < clickInOrderSlots.length) {
                        if (clickInOrderSlots[lastUltraSequencerClicked + 1] != null) {
                            Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked + 1];
                            RenderUtils.drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT_TO_NEXT + 0xD7000000);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        clickInOrderSlots = new Slot[36];
        experimentTableSlots = new ItemStack[54];
        lastChronomatronRound = 0;
        chronomatronPattern.clear();
        chronomatronMouseClicks = 0;
    }

    //Superpairs
    static ItemStack[] experimentTableSlots = new ItemStack[54];

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
//        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        ItemStack item = event.itemStack;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (mc.currentScreen instanceof GuiChest) {
            ContainerChest chest = (ContainerChest) player.openContainer;
            IInventory inv = chest.getLowerChestInventory();
            String chestName = inv.getDisplayName().getUnformattedText();

            if (near.settingsManager.getSettingByName(this, "Superpairs").getValBoolean() && chestName.contains("Superpairs (")) {
                if (Item.getIdFromItem(item.getItem()) != 95) return;
                if (item.getDisplayName().contains("Click any button") || item.getDisplayName().contains("Click a second button") || item.getDisplayName().contains("Next button is instantly rewarded") || item.getDisplayName().contains("Stained Glass")) {
                    Slot slot = ((GuiChest) mc.currentScreen).getSlotUnderMouse();
                    ItemStack itemStack = experimentTableSlots[slot.getSlotIndex()];
                    if (itemStack == null) return;
                    String itemName = itemStack.getDisplayName();

                    if (event.toolTip.stream().anyMatch(x -> StringUtils.stripControlCodes(x).equals(StringUtils.stripControlCodes(itemName))))
                        return;
                    event.toolTip.removeIf(x -> {
                        x = StringUtils.stripControlCodes(x);
                        if (x.equals("minecraft:stained_glass")) return true;
                        return x.startsWith("NBT: ");
                    });
                    event.toolTip.add(itemName);
                    event.toolTip.add(itemStack.getItem().getRegistryName());
                }

            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (near.settingsManager.getSettingByName(this, "Superpairs").getValBoolean() && chestName.startsWith("Superpairs (")) {
                for (int i = 0; i < 53; i++) {
                    ItemStack itemStack = invSlots.get(i).getStack();
                    if (itemStack == null) continue;
                    String itemName = itemStack.getDisplayName();
                    if (Item.getIdFromItem(itemStack.getItem()) == 95 || Item.getIdFromItem(itemStack.getItem()) == 160) continue;
                    if (itemName.contains("Instant Find") || itemName.contains("Gained +")) continue;
                    if (itemName.contains("Enchanted Book")) {
                        itemName = itemStack.getTooltip(mc.thePlayer, false).get(3);
                    }
                    if (itemStack.stackSize > 1) {
                        itemName = itemStack.stackSize + " " + itemName;
                    }
                    if (experimentTableSlots[i] != null) continue;
                    experimentTableSlots[i] = itemStack.copy().setStackDisplayName(itemName);
                }
            }

            if (near.settingsManager.getSettingByName(this, "Ultrasequencer").getValBoolean() && chestName.startsWith("Ultrasequencer (")) {
                if (invSlots.get(49).getStack() != null && invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    for (int i = 9; i <= 44; i++) {
                        if (invSlots.get(i) == null || invSlots.get(i).getStack() == null) continue;
                        String itemName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (itemName.matches("\\d+")) {
                            int number = Integer.parseInt(itemName);
                            clickInOrderSlots[number - 1] = invSlots.get(i);
                        }
                    }
                }
            }
        }
    }

    //Ultrasequencer
    static Slot[] clickInOrderSlots = new Slot[36];
    static int lastUltraSequencerClicked;
    public static final int ULTRASEQUENCER_NEXT = Color.GREEN.getRGB();
    public static final int ULTRASEQUENCER_NEXT_TO_NEXT = Color.RED.getRGB();
}
