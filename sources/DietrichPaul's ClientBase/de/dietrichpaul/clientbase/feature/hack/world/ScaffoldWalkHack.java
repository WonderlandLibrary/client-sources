package de.dietrichpaul.clientbase.feature.hack.world;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickCallback;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickSpoof;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.ScaffoldWalkRotationSpoof;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;

public class ScaffoldWalkHack extends Hack implements ClickSpoof {

    private ScaffoldWalkRotationSpoof rotationSpoof;

    public ScaffoldWalkHack() {
        super("ScaffoldWalk", HackCategory.WORLD);
        ClientBase.INSTANCE.getRotationEngine().add(rotationSpoof = new ScaffoldWalkRotationSpoof(this));
        ClientBase.INSTANCE.getClickEngine().add(this);
    }

    @Override
    public boolean canClick() {
        return rotationSpoof.hasTarget();
    }

    @Override
    public int getPriority() {
        return rotationSpoof.getPriority();
    }

    @Override
    public void click(ClickCallback callback) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem) {
                mc.player.getInventory().selectedSlot = i;
                break;
            }
        }
        if (mc.crosshairTarget instanceof BlockHitResult bhr) {
            if (bhr.getBlockPos().equals(rotationSpoof.getBlockPos()))
                callback.right();
        }
    }
}
