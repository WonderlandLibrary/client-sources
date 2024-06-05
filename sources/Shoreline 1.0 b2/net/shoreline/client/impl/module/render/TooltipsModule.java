package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.event.gui.RenderTooltipEvent;
import net.shoreline.client.init.Modules;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.collection.DefaultedList;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class TooltipsModule extends ToggleModule
{
    //
    Config<Boolean> enderChestsConfig = new BooleanConfig("EnderChests",
            "Renders all the contents of ender chests in tooltips", false);
    Config<Boolean> shulkersConfig = new BooleanConfig("Shulkers", "Renders " +
            "all the contents of shulkers in tooltips", true);
    Config<Boolean> mapsConfig = new BooleanConfig("Maps", "Renders a preview" +
            " of maps in tooltips", false);

    /**
     *
     */
    public TooltipsModule()
    {
        super("Tooltips", "Renders detailed tooltips showing items",
                ModuleCategory.RENDER);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onRenderTooltip(RenderTooltipEvent event)
    {
        final ItemStack stack = event.getStack();
        if (stack.isEmpty())
        {
            return;
        }
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (shulkersConfig.getValue() && nbtCompound != null
                && nbtCompound.contains("Items", NbtElement.LIST_TYPE))
        {
            event.cancel();
            DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(27, ItemStack.EMPTY);
            Inventories.readNbt(nbtCompound, defaultedList);
            RenderManager.rect(event.getMatrices(), event.getX() + 8.0,
                    event.getY() - 21.0, event.getX() + 157.0, event.getY() - 7.0, 0x55555555);
            RenderManager.rect(event.getMatrices(), event.getX() + 8.0,
                    event.getY() - 7.0, event.getX() + 157.0, event.getY() - 5.0,
                    Modules.COLORS.getRGB());
            RenderManager.rect(event.getMatrices(), event.getX() + 8.0,
                    event.getY() - 5.0, event.getX() + 157.0, event.getY() + 50.0, 0x55555555);
            RenderManager.renderText(event.getMatrices(), stack.getName().getString(),
                    event.getX() + 11.0f, event.getY() - 18.0f, 0x000000);
            for (int i = 0; i < defaultedList.size(); i++)
            {
                mc.getItemRenderer().renderInGuiWithOverrides(event.getMatrices(),
                        defaultedList.get(i), event.getX() + (i % 9) * 16 + 9, event.getY() + (i / 9) * 16 - 3);
                mc.getItemRenderer().renderGuiItemOverlay(event.getMatrices(),
                        mc.textRenderer, defaultedList.get(i),
                        event.getX() + (i % 9) * 16 + 9, event.getY() + (i / 9) * 16 - 3, null);
            }
        }
    }
}
