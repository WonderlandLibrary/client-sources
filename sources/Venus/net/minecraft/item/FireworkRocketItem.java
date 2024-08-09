/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkStarItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FireworkRocketItem
extends Item {
    public static long delay;
    public StopWatch stopWatch = new StopWatch();

    public FireworkRocketItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        if (!world.isRemote) {
            ItemStack itemStack = itemUseContext.getItem();
            Vector3d vector3d = itemUseContext.getHitVec();
            Direction direction = itemUseContext.getFace();
            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemUseContext.getPlayer(), vector3d.x + (double)direction.getXOffset() * 0.15, vector3d.y + (double)direction.getYOffset() * 0.15, vector3d.z + (double)direction.getZOffset() * 0.15, itemStack);
            world.addEntity(fireworkRocketEntity);
            itemStack.shrink(1);
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        if (playerEntity.isElytraFlying()) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            if (!world.isRemote) {
                world.addEntity(new FireworkRocketEntity(world, itemStack, playerEntity));
                if (!playerEntity.isCreative()) {
                    itemStack.shrink(1);
                }
            }
            return ActionResult.func_233538_a_(playerEntity.getHeldItem(hand), world.isRemote());
        }
        return ActionResult.resultPass(playerEntity.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        CompoundNBT compoundNBT = itemStack.getChildTag("Fireworks");
        if (compoundNBT != null) {
            ListNBT listNBT;
            if (compoundNBT.contains("Flight", 0)) {
                list.add(new TranslationTextComponent("item.minecraft.firework_rocket.flight").appendString(" ").appendString(String.valueOf(compoundNBT.getByte("Flight"))).mergeStyle(TextFormatting.GRAY));
            }
            if (!(listNBT = compoundNBT.getList("Explosions", 10)).isEmpty()) {
                for (int i = 0; i < listNBT.size(); ++i) {
                    CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                    ArrayList<ITextComponent> arrayList = Lists.newArrayList();
                    FireworkStarItem.func_195967_a(compoundNBT2, arrayList);
                    if (arrayList.isEmpty()) continue;
                    for (int j = 1; j < arrayList.size(); ++j) {
                        arrayList.set(j, new StringTextComponent("  ").append((ITextComponent)arrayList.get(j)).mergeStyle(TextFormatting.GRAY));
                    }
                    list.addAll(arrayList);
                }
            }
        }
    }

    public static enum Shape {
        SMALL_BALL(0, "small_ball"),
        LARGE_BALL(1, "large_ball"),
        STAR(2, "star"),
        CREEPER(3, "creeper"),
        BURST(4, "burst");

        private static final Shape[] VALUES;
        private final int index;
        private final String shapeName;

        private Shape(int n2, String string2) {
            this.index = n2;
            this.shapeName = string2;
        }

        public int getIndex() {
            return this.index;
        }

        public String getShapeName() {
            return this.shapeName;
        }

        public static Shape get(int n) {
            return n >= 0 && n < VALUES.length ? VALUES[n] : SMALL_BALL;
        }

        private static Shape[] lambda$static$1(int n) {
            return new Shape[n];
        }

        private static int lambda$static$0(Shape shape) {
            return shape.index;
        }

        static {
            VALUES = (Shape[])Arrays.stream(Shape.values()).sorted(Comparator.comparingInt(Shape::lambda$static$0)).toArray(Shape::lambda$static$1);
        }
    }
}

