package mathax.client.systems.modules.player;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.world.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.BedItem;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/*/----------------------------------------------------------------------------------------------------------------/*/
/*/ Used from Orion Meteor Addon and edited by Matejko06                                                           /*/
/*/ https://github.com/AntiCope/orion/blob/master/src/main/java/me/ghosttypes/orion/modules/main/AutoBedCraft.java /*/
/*/----------------------------------------------------------------------------------------------------------------/*/

public class AutoBedCraft extends Module {
    private boolean alertedNoMats = false;
    private boolean startedRefill = false;
    private boolean didRefill = false;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgAuto = settings.createGroup("Auto (Buggy)");
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<Boolean> disableAfter = sgGeneral.add(new BoolSetting.Builder()
        .name("disable-after")
        .description("Toggle off after filling your inv with beds.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> disableNoMats = sgGeneral.add(new BoolSetting.Builder()
        .name("disable-on-no-mats")
        .description("Toggle off if you run out of material.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> closeAfter = sgGeneral.add(new BoolSetting.Builder()
        .name("close-after")
        .description("Close the crafting GUI after filling.")
        .defaultValue(true)
        .build()
    );

    // Auto

    private final Setting<Boolean> automatic = sgAuto.add(new BoolSetting.Builder()
        .name("automatic")
        .description("Automatically place/search for and open crafting tables when you're out of beds.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> antiDesync = sgAuto.add(new BoolSetting.Builder()
        .name("anti-desync")
        .description("Try to prevent inventory desync.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> chatInfo = sgAuto.add(new BoolSetting.Builder()
        .name("chat-info")
        .description("Alerts you in chat when auto refill is starting.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> autoOnlyHole = sgAuto.add(new BoolSetting.Builder()
        .name("in-hole-only")
        .description("Only auto refill while in a hole.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> autoOnlyGround = sgAuto.add(new BoolSetting.Builder()
        .name("on-ground-only")
        .description("Only auto refill while on the ground.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> autoWhileMoving = sgAuto.add(new BoolSetting.Builder()
        .name("while-moving")
        .description("Allow auto refill while in motion")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> emptySlotsNeeded = sgAuto.add(new IntSetting.Builder()
        .name("required-empty-slots")
        .description("How many empty slots are required for activation.")
        .defaultValue(5)
        .min(1)
        .sliderRange(1, 6)
        .build()
    );

    private final Setting<Integer> radius = sgAuto.add(new IntSetting.Builder()
        .name("radius")
        .description("How far to search for crafting tables near you.")
        .defaultValue(3)
        .min(1)
        .sliderRange(1, 5)
        .build()
    );

    private final Setting<Double> minHealth = sgAuto.add(new DoubleSetting.Builder()
        .name("min-health").description("Min health require to activate.")
        .defaultValue(10)
        .range(1, 36)
        .sliderRange(1, 36)
        .build()
    );

    // Render

    private final Setting<Boolean> swing = sgRender.add(new BoolSetting.Builder()
        .name("swing")
        .description("Swings your hand client side when placing or interacting.")
        .defaultValue(true)
        .build()
    );

    public AutoBedCraft() {
        super(Categories.Player, Items.RED_BED, "auto-bed-craft", "Automatically crafts beds.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (PlayerUtils.getTotalHealth() <= minHealth.get()) return;
        if (automatic.get() && isOutOfMaterial() && !alertedNoMats) {
            error("Cannot activate auto mode, no material left.");
            alertedNoMats = true;
        }

        if (automatic.get() && needsRefill() && canRefill(true) && !isOutOfMaterial() && !(mc.player.currentScreenHandler instanceof CraftingScreenHandler)) {
            FindItemResult craftTable = InvUtils.findCraftTable();
            if (!craftTable.found()) {
                toggle();
                error("No crafting tables in hotbar!");
                return;
            }

            BlockPos tablePos;
            tablePos = findCraftingTable();

            if (tablePos == null) {
                placeCraftingTable(craftTable);
                return;
            }

            openCraftingTable(tablePos);
            if (chatInfo.get() && !startedRefill) {
                info("Refilling...");
                startedRefill = true;
            }

            didRefill = true;
            return;
        }
        if (didRefill && !needsRefill()) {
            if (chatInfo.get()) info("Refill complete.");
            didRefill = false;
            startedRefill = false;
        }

        if (mc.player.currentScreenHandler instanceof CraftingScreenHandler) {
            if (!canRefill(false)) {
                mc.player.closeHandledScreen();
                if (antiDesync.get()) mc.player.getInventory().updateItems();
                return;
            }

            CraftingScreenHandler currentScreenHandler = (CraftingScreenHandler) mc.player.currentScreenHandler;
            if (isOutOfMaterial()) {
                if (chatInfo.get()) error("You are out of material!");
                if (disableNoMats.get()) toggle();
                mc.player.closeHandledScreen();
                if (antiDesync.get()) mc.player.getInventory().updateItems();
                return;
            }

            if (InvUtils.isInventoryFull()) {
                if (disableAfter.get()) toggle();

                if (closeAfter.get()) {
                    mc.player.closeHandledScreen();
                    if (antiDesync.get()) mc.player.getInventory().updateItems();
                }

                if (chatInfo.get() && !automatic.get()) info("Your inventory is full.");
                return;
            }

            List<RecipeResultCollection> recipeResultCollectionList = mc.player.getRecipeBook().getResultsForGroup(RecipeBookGroup.CRAFTING_MISC);
            for (RecipeResultCollection recipeResultCollection : recipeResultCollectionList) {
                for (Recipe<?> recipe : recipeResultCollection.getRecipes(true)) {
                    if (recipe.getOutput().getItem() instanceof BedItem) {
                        assert mc.interactionManager != null;
                        mc.interactionManager.clickRecipe(currentScreenHandler.syncId, recipe, false);
                        windowClick(currentScreenHandler, 0, SlotActionType.QUICK_MOVE, 1);
                    }
                }
            }
        }
    }

    private void placeCraftingTable(FindItemResult craftTable) {
        List<BlockPos> nearbyBlocks = BlockUtils.getSphere(mc.player.getBlockPos(), radius.get(), radius.get());
        for (BlockPos block : nearbyBlocks) {
            if (BlockUtils.getBlock(block) == Blocks.AIR) {
                BlockUtils.place(block, craftTable, 0, swing.get(), true);
                break;
            }
        }
    }

    private BlockPos findCraftingTable() {
        List<BlockPos> nearbyBlocks = BlockUtils.getSphere(mc.player.getBlockPos(), radius.get(), radius.get());
        for (BlockPos block : nearbyBlocks) if (BlockUtils.getBlock(block) == Blocks.CRAFTING_TABLE) return block;
        return null;
    }

    private void openCraftingTable(BlockPos tablePos) {
        Vec3d tableVec = new Vec3d(tablePos.getX(), tablePos.getY(), tablePos.getZ());
        BlockHitResult table = new BlockHitResult(tableVec, Direction.UP, tablePos, false);
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, table);
    }

    private boolean needsRefill() {
        FindItemResult bed = InvUtils.find(itemStack -> itemStack.getItem() instanceof BedItem);
        if (!bed.found()) return true;
        return InvUtils.isInventoryFull();
    }

    private boolean canRefill(boolean checkSlots) {
        if (!autoWhileMoving.get() && PlayerUtils.isPlayerMoving(mc.player)) return false;
        if (autoOnlyHole.get() && !PlayerUtils.isInHole(mc.player)) return false;
        if (autoOnlyGround.get() && !mc.player.isOnGround()) return false;
        if (InvUtils.isInventoryFull()) return false;
        if (checkSlots) if (InvUtils.getEmptySlots() < emptySlotsNeeded.get()) return false;
        return !(PlayerUtils.getTotalHealth() <= minHealth.get());
    }

    private boolean isOutOfMaterial() {
        FindItemResult wool = InvUtils.find(itemStack -> InvUtils.wools.contains(itemStack.getItem()));
        FindItemResult plank = InvUtils.find(itemStack -> InvUtils.planks.contains(itemStack.getItem()));
        FindItemResult craftTable = InvUtils.findCraftTable();
        if (!craftTable.found()) return true;
        if (!wool.found() || !plank.found()) return true;
        return wool.count() < 3 || plank.count() < 3;
    }

    private void windowClick(ScreenHandler container, int slot, SlotActionType action, int clickData) {
        assert mc.interactionManager != null;
        mc.interactionManager.clickSlot(container.syncId, slot, clickData, action, mc.player);
    }
}
