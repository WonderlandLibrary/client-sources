package pw.latematt.xiv.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.utils.ChatLogger;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
public class Breed implements CommandHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onCommandRan(String message) {
        int counter = 0;
        for (Entity entity : mc.theWorld.loadedEntityList.stream().filter(entity1 -> entity1 instanceof EntityAnimal).collect(Collectors.toList())) {
            EntityAnimal entityAnimal = (EntityAnimal) entity;
            if (entityAnimal.isChild())
                continue;
            if (entityAnimal.isInLove())
                continue;
            if (!Objects.equals(entityAnimal.getGrowingAge(), 0))
                continue;
            if (mc.thePlayer.getDistanceToEntity(entityAnimal) > (mc.thePlayer.canEntityBeSeen(entityAnimal) ? 6 : 3))
                continue;
            for (int i = 0; i <= 8; i++) {
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
                if (stack == null)
                    continue;

                if (entityAnimal.isBreedingItem(stack)) {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(i));
                    mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entityAnimal, C02PacketUseEntity.Action.INTERACT));
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    counter++;
                    break;
                }
            }
        }

        ChatLogger.print(String.format("Bred %s animal%s.", counter, Objects.equals(counter, 1) ? "" : "s"));
    }
}
