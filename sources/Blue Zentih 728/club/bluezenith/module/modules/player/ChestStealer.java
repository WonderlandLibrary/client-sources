
package club.bluezenith.module.modules.player;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.NoObf;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.client.ServerUtils;
import club.bluezenith.util.math.MathUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static net.minecraft.client.settings.KeyBinding.keybindArray;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public final class ChestStealer extends Module {
    public final ModeValue silentMode = new ModeValue("Mode", "Normal", "Silent", "Freelook", "Normal").setIndex(1);
    private final BooleanValue disableOnRespawn = new BooleanValue("Disable on respawn", false).setIndex(2);
    private final IntegerValue maxDelay = new IntegerValue("Max delay", 200, 0, 250, 10, true, (old_, new_) -> {updateDelay(); return new_ < getMinDelay().get() ? old_ : new_;}, null).setIndex(4);
    private final IntegerValue minDelay = new IntegerValue("Min delay", 50, 0, 250, 10, true, (old_, new_) -> {updateDelay(); return new_ > maxDelay.get() ? old_ : new_;}, null).setIndex(5);
    private IntegerValue getMinDelay(){
        return minDelay;
    }
    private final IntegerValue maxCloseDelay = new IntegerValue("Max close delay", 250, 0, 1000, 10, true, (old_, new_) -> {updateDelay(); return new_ < getMinCloseDelay().get() ? old_ : new_;}, null).setIndex(6);
    private final IntegerValue minCloseDelay = new IntegerValue("Min close delay", 50, 0, 1000, 10, true, (old_, new_) -> {updateDelay(); return new_ > maxCloseDelay.get() ? old_ : new_;}, null).setIndex(7);
    private final BooleanValue checkTitle = new BooleanValue("Check title", false).setIndex(2);
    private final BooleanValue checkTrash = new BooleanValue("Ignore trash", false).setIndex(3);

    private IntegerValue getMinCloseDelay(){
        return minCloseDelay;
    }
    private final MillisTimer timer = new MillisTimer();
    private long wait = 0;
    private long closeWait = 0;
    boolean shouldClose;
    boolean empty;
    public ChestStealer() {
        super("ChestStealer", ModuleCategory.PLAYER, "stealer");
    }

    final static List<Item> trashItems = newArrayList(Items.experience_bottle, Items.skull, Items.bucket, Items.fishing_rod, Items.lava_bucket, Items.water_bucket, Item.getItemFromBlock(Blocks.enchanting_table), Items.egg);
    @Listener
    public void onUpdate(UpdatePlayerEvent event) {
        if(!(mc.currentScreen instanceof GuiChest)) return;
        final GuiChest chest = (GuiChest) mc.currentScreen;
        if(player == null){
            return;
        }
        if (empty = this.emptyChestCheck(chest)) {
            if(timer.hasTimeReached(closeWait) && shouldClose) {
                player.closeScreen();
                if(!silentMode.is("Normal")) {
                    for(KeyBinding binding : keybindArray) {
                        if(!Keyboard.isKeyDown(Keyboard.getKeyIndex(binding.getKeyDescription())))
                            binding.pressed = false;
                    }
                    mc.rightClickDelayTimer = 10;
                }
                shouldClose = false;
                timer.reset();
            }
            return;
        }
        if(((player.openContainer instanceof ContainerChest))) {
            ContainerChest chest1 = (ContainerChest) player.openContainer;
            final String name = chest1.getLowerChestInventory().getDisplayName().getUnformattedText();
            if ((name.equals("Chest") || ServerUtils.hypixel && name.equals("LOW")) || !checkTitle.get()) {
                for (int i = 0; i < chest1.getLowerChestInventory().getSizeInventory(); i++) {
                    final ItemStack stack = chest1.getLowerChestInventory().getStackInSlot(i);
                    if ((stack) != null && timer.hasTimeReached(wait)) {
                        if(checkTrash.get() && isTrash(stack)) continue;
                        mc.playerController.windowClick(chest1.windowId, i, 1, 1, player);
                        shouldClose = true;
                        updateDelay();
                        timer.reset();
                    }
                }
            }
        }
    }

    public boolean isEligible(IInventory inventory) {
       return inventory.getDisplayName().getUnformattedText().equals("Chest") || ServerUtils.hypixel &&inventory.getDisplayName().getUnformattedText().equals("LOW") || !checkTitle.get();
    }

    public boolean isEligible(GuiScreen screen) {
        if(!(screen instanceof GuiChest)) return false;
        return isEligible(((GuiChest)screen).lowerChestInventory);
    }

    @NoObf
    @Listener
    public void render(Render2DEvent event) {
        if(!(mc.currentScreen instanceof GuiChest) || !silentMode.is("Silent")) return;
        float x = (float) event.getWidth();
        float y = (float) event.getHeight();
        String str = "";
        String key = Keyboard.getKeyName(mc.gameSettings.keyBindInventory.getKeyCode());
        if(!shouldClose && empty)
            str = format("An empty chest is opened. Press %s to close.", key);
        else if(!empty && shouldClose)
            str = format("Stealing a chest. Press %s to stop.", key);

            final FontRenderer font = HUD.module.font.get();
            final char[] strArr = str.toCharArray();
            float x1 = x/2f - font.getStringWidth(str)/2f;
            for (int i = 0; i < strArr.length; i++) {
                font.drawString(String.valueOf(strArr[i]), x1, y/1.2f, HUD.module.getColor(i), true);
                x1 += font.getStringWidth(String.valueOf(strArr[i]));
            }
    }

    public boolean emptyChestCheck(GuiChest guiChest) {
        if(!checkTitle.get() || guiChest.lowerChestInventory.getDisplayName().getUnformattedText().equals("Chest")) {
            for (int i = 0; i < guiChest.lowerChestInventory.getSizeInventory(); i++) {
                ItemStack itemStack = guiChest.lowerChestInventory.getStackInSlot(i);
                if (itemStack != null && !isTrash(itemStack)) {
                    return false;
                }
            }
            return true;
        } else return false;
    }

    boolean isTrash(ItemStack stack) {
        return  stack != null && stack.getItem() != null && stack.getItem() instanceof ItemPotion
                && ((ItemPotion)stack.getItem()).getEffects(stack).get(0).getEffectName().contains("potion.poison")
                || stack != null && trashItems.contains(stack.getItem());
    }

    public void updateDelay(){
        wait = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
        closeWait = MathUtil.getRandomInt(minCloseDelay.get(), maxCloseDelay.get());
    }

    @Listener
    public void onRespawn(SpawnPlayerEvent event) {
        if(disableOnRespawn.get()) {
            this.toggle();
            getBlueZenith().getNotificationPublisher().postInfo(
                    displayName,
                    "Disabled due to a respawn",
                    2500
            );
        }
    }
    @Override
    public String getTag() {
        return silentMode.get();
    }
}
