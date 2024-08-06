package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.ClickGUIScreen;
import java.util.ArrayList;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

@RegisterModule(
    name = "Click GUI",
    uniqueId = "cgui",
    description = "Displays Modules",
    category = ModuleCategory.Client
)
public class ClickGUI extends Module {

    /*@ConfigOption(
            name = "Font",
            description = "",
            order = 1
    )*/
    public static font fontType = font.Minecraft;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 101
    )
    public static Integer xPos = 10;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 102
    )
    public static Integer yPos = 10;

    public static ModuleCategory selectedCategory = ModuleCategory.Search;

    @Override
    protected void onEnable() {
        if (!C.isInGame()) return;

        if (search == null) search = new TextFieldWidget(
            C.mc.textRenderer,
            0,
            0,
            300,
            30,
            null,
            Text.of("Search")
        );

        search.setMaxLength(30);

        ModuleManager.sortModulesByLength();

        C.mc.setScreen(new ClickGUIScreen());
        setEnabled(false, true);
    }

    @Override
    protected void onDisable() {}

    public enum font {
        Sans_Serif,
        Serif,
        Monospaced,
        Comic_Sans,
        Wingdings,
        Times_New_Roman,
        Minecraft,
    }

    public static final ArrayList<Module> expandedModules = new ArrayList<>();
    public static final ArrayList<String> modesExtended = new ArrayList<>();

    // 1/960
    public static float scaleSizeW;
    // 1/496 (no division because its slow or smth. although java prob changes the div to multi on compile anyway...)
    public static float scaleSizeH;

    // made each thing have dif scroll numbers, needed for dropdown
    public static double[] scrollOffsets = { 0, 0, 0, 0, 0, 0, 0 };
    public static double[] scrollOffsetsTEMP = { 0, 0, 0, 0, 0, 0, 0 };

    public static TextFieldWidget search;
}
