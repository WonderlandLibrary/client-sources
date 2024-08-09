/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum JigsawOrientation implements IStringSerializable
{
    DOWN_EAST("down_east", Direction.DOWN, Direction.EAST),
    DOWN_NORTH("down_north", Direction.DOWN, Direction.NORTH),
    DOWN_SOUTH("down_south", Direction.DOWN, Direction.SOUTH),
    DOWN_WEST("down_west", Direction.DOWN, Direction.WEST),
    UP_EAST("up_east", Direction.UP, Direction.EAST),
    UP_NORTH("up_north", Direction.UP, Direction.NORTH),
    UP_SOUTH("up_south", Direction.UP, Direction.SOUTH),
    UP_WEST("up_west", Direction.UP, Direction.WEST),
    WEST_UP("west_up", Direction.WEST, Direction.UP),
    EAST_UP("east_up", Direction.EAST, Direction.UP),
    NORTH_UP("north_up", Direction.NORTH, Direction.UP),
    SOUTH_UP("south_up", Direction.SOUTH, Direction.UP);

    private static final Int2ObjectMap<JigsawOrientation> field_239637_m_;
    private final String field_239638_n_;
    private final Direction field_239639_o_;
    private final Direction field_239640_p_;

    private static int func_239643_b_(Direction direction, Direction direction2) {
        return direction.ordinal() << 3 | direction2.ordinal();
    }

    private JigsawOrientation(String string2, Direction direction, Direction direction2) {
        this.field_239638_n_ = string2;
        this.field_239640_p_ = direction;
        this.field_239639_o_ = direction2;
    }

    @Override
    public String getString() {
        return this.field_239638_n_;
    }

    public static JigsawOrientation func_239641_a_(Direction direction, Direction direction2) {
        int n = JigsawOrientation.func_239643_b_(direction2, direction);
        return (JigsawOrientation)field_239637_m_.get(n);
    }

    public Direction func_239642_b_() {
        return this.field_239640_p_;
    }

    public Direction func_239644_c_() {
        return this.field_239639_o_;
    }

    static {
        field_239637_m_ = new Int2ObjectOpenHashMap<JigsawOrientation>(JigsawOrientation.values().length);
        for (JigsawOrientation jigsawOrientation : JigsawOrientation.values()) {
            field_239637_m_.put(JigsawOrientation.func_239643_b_(jigsawOrientation.field_239639_o_, jigsawOrientation.field_239640_p_), jigsawOrientation);
        }
    }
}

