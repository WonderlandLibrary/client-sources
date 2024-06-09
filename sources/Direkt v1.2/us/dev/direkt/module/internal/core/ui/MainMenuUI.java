package us.dev.direkt.module.internal.core.ui;

import net.minecraft.client.gui.GuiMainMenu;
import us.dev.direkt.event.internal.events.game.gui.screen.EventDisplayGui;
import us.dev.direkt.event.internal.filters.GuiScreenFilter;
import us.dev.direkt.gui.minecraft.override.GuiDirektMainMenu;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.annotations.ModData;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Account Manager", category = ModCategory.CORE)
public class MainMenuUI extends Module {

    @Listener
    protected Link<EventDisplayGui> onGuiDisplay = new Link<>(event -> {
        event.setGuiScreen(new GuiDirektMainMenu());
    }, new GuiScreenFilter(GuiMainMenu.class));

}
