package net.minecraft.client.gui.screen;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderOptions;

public class ChatOptionsScreen extends SettingsScreen
{
    private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

    public ChatOptionsScreen(Screen parentScreenIn, GameSettings gameSettingsIn)
    {
        super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("options.chat.title"));
    }
}
