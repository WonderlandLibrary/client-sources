/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MusicDiscItem
extends Item {
    private static final Map<SoundEvent, MusicDiscItem> RECORDS = Maps.newHashMap();
    private final int comparatorValue;
    private final SoundEvent sound;

    protected MusicDiscItem(int n, SoundEvent soundEvent, Item.Properties properties) {
        super(properties);
        this.comparatorValue = n;
        this.sound = soundEvent;
        RECORDS.put(this.sound, this);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockPos blockPos;
        World world = itemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos = itemUseContext.getPos());
        if (blockState.isIn(Blocks.JUKEBOX) && !blockState.get(JukeboxBlock.HAS_RECORD).booleanValue()) {
            ItemStack itemStack = itemUseContext.getItem();
            if (!world.isRemote) {
                ((JukeboxBlock)Blocks.JUKEBOX).insertRecord(world, blockPos, blockState, itemStack);
                world.playEvent(null, 1010, blockPos, Item.getIdFromItem(this));
                itemStack.shrink(1);
                PlayerEntity playerEntity = itemUseContext.getPlayer();
                if (playerEntity != null) {
                    playerEntity.addStat(Stats.PLAY_RECORD);
                }
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public int getComparatorValue() {
        return this.comparatorValue;
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        list.add(this.getDescription().mergeStyle(TextFormatting.GRAY));
    }

    public IFormattableTextComponent getDescription() {
        return new TranslationTextComponent(this.getTranslationKey() + ".desc");
    }

    @Nullable
    public static MusicDiscItem getBySound(SoundEvent soundEvent) {
        return RECORDS.get(soundEvent);
    }

    public SoundEvent getSound() {
        return this.sound;
    }
}

