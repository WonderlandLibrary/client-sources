/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum NoteBlockInstrument implements IStringSerializable
{
    HARP("harp", SoundEvents.BLOCK_NOTE_BLOCK_HARP),
    BASEDRUM("basedrum", SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM),
    SNARE("snare", SoundEvents.BLOCK_NOTE_BLOCK_SNARE),
    HAT("hat", SoundEvents.BLOCK_NOTE_BLOCK_HAT),
    BASS("bass", SoundEvents.BLOCK_NOTE_BLOCK_BASS),
    FLUTE("flute", SoundEvents.BLOCK_NOTE_BLOCK_FLUTE),
    BELL("bell", SoundEvents.BLOCK_NOTE_BLOCK_BELL),
    GUITAR("guitar", SoundEvents.BLOCK_NOTE_BLOCK_GUITAR),
    CHIME("chime", SoundEvents.BLOCK_NOTE_BLOCK_CHIME),
    XYLOPHONE("xylophone", SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE),
    IRON_XYLOPHONE("iron_xylophone", SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE),
    COW_BELL("cow_bell", SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL),
    DIDGERIDOO("didgeridoo", SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO),
    BIT("bit", SoundEvents.BLOCK_NOTE_BLOCK_BIT),
    BANJO("banjo", SoundEvents.BLOCK_NOTE_BLOCK_BANJO),
    PLING("pling", SoundEvents.BLOCK_NOTE_BLOCK_PLING);

    private final String name;
    private final SoundEvent sound;

    private NoteBlockInstrument(String string2, SoundEvent soundEvent) {
        this.name = string2;
        this.sound = soundEvent;
    }

    @Override
    public String getString() {
        return this.name;
    }

    public SoundEvent getSound() {
        return this.sound;
    }

    public static NoteBlockInstrument byState(BlockState blockState) {
        if (blockState.isIn(Blocks.CLAY)) {
            return FLUTE;
        }
        if (blockState.isIn(Blocks.GOLD_BLOCK)) {
            return BELL;
        }
        if (blockState.isIn(BlockTags.WOOL)) {
            return GUITAR;
        }
        if (blockState.isIn(Blocks.PACKED_ICE)) {
            return CHIME;
        }
        if (blockState.isIn(Blocks.BONE_BLOCK)) {
            return XYLOPHONE;
        }
        if (blockState.isIn(Blocks.IRON_BLOCK)) {
            return IRON_XYLOPHONE;
        }
        if (blockState.isIn(Blocks.SOUL_SAND)) {
            return COW_BELL;
        }
        if (blockState.isIn(Blocks.PUMPKIN)) {
            return DIDGERIDOO;
        }
        if (blockState.isIn(Blocks.EMERALD_BLOCK)) {
            return BIT;
        }
        if (blockState.isIn(Blocks.HAY_BLOCK)) {
            return BANJO;
        }
        if (blockState.isIn(Blocks.GLOWSTONE)) {
            return PLING;
        }
        Material material = blockState.getMaterial();
        if (material == Material.ROCK) {
            return BASEDRUM;
        }
        if (material == Material.SAND) {
            return SNARE;
        }
        if (material == Material.GLASS) {
            return HAT;
        }
        return material != Material.WOOD && material != Material.NETHER_WOOD ? HARP : BASS;
    }
}

