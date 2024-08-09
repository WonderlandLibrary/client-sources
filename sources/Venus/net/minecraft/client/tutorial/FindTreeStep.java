/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.tutorial.ITutorialStep;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class FindTreeStep
implements ITutorialStep {
    private static final Set<Block> TREE_BLOCKS = Sets.newHashSet(Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.WARPED_STEM, Blocks.CRIMSON_STEM, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.WARPED_HYPHAE, Blocks.CRIMSON_HYPHAE, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK);
    private static final ITextComponent TITLE = new TranslationTextComponent("tutorial.find_tree.title");
    private static final ITextComponent DESCRIPTION = new TranslationTextComponent("tutorial.find_tree.description");
    private final Tutorial tutorial;
    private TutorialToast toast;
    private int timeWaiting;

    public FindTreeStep(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    @Override
    public void tick() {
        ++this.timeWaiting;
        if (this.tutorial.getGameType() != GameType.SURVIVAL) {
            this.tutorial.setStep(TutorialSteps.NONE);
        } else {
            ClientPlayerEntity clientPlayerEntity;
            if (this.timeWaiting == 1 && (clientPlayerEntity = this.tutorial.getMinecraft().player) != null) {
                for (Block block : TREE_BLOCKS) {
                    if (!clientPlayerEntity.inventory.hasItemStack(new ItemStack(block))) continue;
                    this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                    return;
                }
                if (FindTreeStep.hasPunchedTreesPreviously(clientPlayerEntity)) {
                    this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                    return;
                }
            }
            if (this.timeWaiting >= 6000 && this.toast == null) {
                this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, false);
                this.tutorial.getMinecraft().getToastGui().add(this.toast);
            }
        }
    }

    @Override
    public void onStop() {
        if (this.toast != null) {
            this.toast.hide();
            this.toast = null;
        }
    }

    @Override
    public void onMouseHover(ClientWorld clientWorld, RayTraceResult rayTraceResult) {
        BlockState blockState;
        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK && TREE_BLOCKS.contains((blockState = clientWorld.getBlockState(((BlockRayTraceResult)rayTraceResult).getPos())).getBlock())) {
            this.tutorial.setStep(TutorialSteps.PUNCH_TREE);
        }
    }

    @Override
    public void handleSetSlot(ItemStack itemStack) {
        for (Block block : TREE_BLOCKS) {
            if (itemStack.getItem() != block.asItem()) continue;
            this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
            return;
        }
    }

    public static boolean hasPunchedTreesPreviously(ClientPlayerEntity clientPlayerEntity) {
        for (Block block : TREE_BLOCKS) {
            if (clientPlayerEntity.getStats().getValue(Stats.BLOCK_MINED.get(block)) <= 0) continue;
            return false;
        }
        return true;
    }
}

