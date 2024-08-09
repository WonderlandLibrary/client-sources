package ru.FecuritySQ.module.игрок;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoTool extends Module {
    public int itemIndex;
    private boolean swap;
    private long swapDelay;
    private final ItemStack swapedItem = null;
    private final List<Integer> lastItem = new ArrayList<>();
    public OptionBoolList settings = new OptionBoolList("Настройки",
            new OptionBoolean("Возвращать вещь", false),
            new OptionBoolean("Сохранять вещь", false));

    public AutoTool() {
        super(Category.Игрок, GLFW.GLFW_KEY_0);
        addOption(settings);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;
        if(e instanceof EventUpdate){
            if(mc.objectMouseOver.getType() != RayTraceResult.Type.BLOCK) return;
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)mc.objectMouseOver;
            BlockPos blockpos = blockraytraceresult.getPos();

            List<Integer> bestItem = new ArrayList<>();
            float bestSpeed = 1;
            Block block = mc.world.getBlockState(blockpos).getBlock();
            for (int i = 0; i < 9; i++) {
                ItemStack item = mc.player.inventory.getStackInSlot(i);
                float speed = item.getDestroySpeed(block.getDefaultState());
                if (!(mc.player.inventory.getStackInSlot(i).getMaxDamage() - mc.player.inventory.getStackInSlot(i).getDamage() > 1) && settings.setting("Сохранять вещь").get())
                    continue;
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestItem.add(i);
                }
            }

            bestItem.sort(Comparator.comparingDouble(x -> -mc.player.getDigSpeed(mc.world.getBlockState(blockpos), mc.player.inventory.getStackInSlot(x))));

            List<Integer> canBreackIndex = bestItem.stream().filter(x -> mc.player.inventory.getStackInSlot(x).canHarvestBlock(mc.world.getBlockState(blockpos))).collect(Collectors.toList());
            if (!canBreackIndex.isEmpty()) bestItem.set(0, canBreackIndex.get(0));



            if (!bestItem.isEmpty() && mc.gameSettings.keyBindAttack.isKeyDown()) {

                if (mc.player.inventory.getCurrentItem() != swapedItem) {
                    lastItem.add(mc.player.inventory.currentItem);

                        mc.player.inventory.currentItem = bestItem.get(0);

                    itemIndex = bestItem.get(0);
                    swap = true;
                }
                swapDelay = System.currentTimeMillis();
            } else if (swap && !lastItem.isEmpty() && System.currentTimeMillis() >= swapDelay + 300 &&  settings.setting("Возвращать вещь").get()) {

                 mc.player.inventory.currentItem = lastItem.get(0);

                itemIndex = lastItem.get(0);
                lastItem.clear();
                swap = false;
            }
        }
    }
}
