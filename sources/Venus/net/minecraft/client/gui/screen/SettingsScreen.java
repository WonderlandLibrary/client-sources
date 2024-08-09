/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;

public class SettingsScreen
extends Screen {
    protected final Screen parentScreen;
    protected final GameSettings gameSettings;

    public SettingsScreen(Screen screen, GameSettings gameSettings, ITextComponent iTextComponent) {
        super(iTextComponent);
        this.parentScreen = screen;
        this.gameSettings = gameSettings;
    }

    @Override
    public void onClose() {
        this.minecraft.gameSettings.saveOptions();
    }

    @Override
    public void closeScreen() {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    @Nullable
    public static List<IReorderingProcessor> func_243293_a(OptionsRowList optionsRowList, int n, int n2) {
        Optional<Widget> optional = optionsRowList.func_238518_c_(n, n2);
        if (optional.isPresent() && optional.get() instanceof IBidiTooltip) {
            Optional<List<IReorderingProcessor>> optional2 = ((IBidiTooltip)((Object)optional.get())).func_241867_d();
            return optional2.orElse(null);
        }
        return null;
    }
}

