package net.minecraft.src;

import java.text.*;
import java.util.*;

public class GuiSelectWorld extends GuiScreen
{
    private final DateFormat dateFormatter;
    protected GuiScreen parentScreen;
    protected String screenTitle;
    private boolean selected;
    private int selectedWorld;
    private List saveList;
    private GuiWorldSlot worldSlotContainer;
    private String localizedWorldText;
    private String localizedMustConvertText;
    private String[] localizedGameModeText;
    private boolean deleting;
    private GuiButton buttonDelete;
    private GuiButton buttonSelect;
    private GuiButton buttonRename;
    private GuiButton buttonRecreate;
    
    public GuiSelectWorld(final GuiScreen par1GuiScreen) {
        this.dateFormatter = new SimpleDateFormat();
        this.screenTitle = "Select world";
        this.selected = false;
        this.localizedGameModeText = new String[3];
        this.parentScreen = par1GuiScreen;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.screenTitle = var1.translateKey("selectWorld.title");
        try {
            this.loadSaves();
        }
        catch (AnvilConverterException var2) {
            var2.printStackTrace();
            this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load words", var2.getMessage()));
            return;
        }
        this.localizedWorldText = var1.translateKey("selectWorld.world");
        this.localizedMustConvertText = var1.translateKey("selectWorld.conversion");
        this.localizedGameModeText[EnumGameType.SURVIVAL.getID()] = var1.translateKey("gameMode.survival");
        this.localizedGameModeText[EnumGameType.CREATIVE.getID()] = var1.translateKey("gameMode.creative");
        this.localizedGameModeText[EnumGameType.ADVENTURE.getID()] = var1.translateKey("gameMode.adventure");
        (this.worldSlotContainer = new GuiWorldSlot(this)).registerScrollButtons(this.buttonList, 4, 5);
        this.initButtons();
    }
    
    private void loadSaves() throws AnvilConverterException {
        final ISaveFormat var1 = this.mc.getSaveLoader();
        Collections.sort((List<Comparable>)(this.saveList = var1.getSaveList()));
        this.selectedWorld = -1;
    }
    
    protected String getSaveFileName(final int par1) {
        return this.saveList.get(par1).getFileName();
    }
    
    protected String getSaveName(final int par1) {
        String var2 = this.saveList.get(par1).getDisplayName();
        if (var2 == null || MathHelper.stringNullOrLengthZero(var2)) {
            final StringTranslate var3 = StringTranslate.getInstance();
            var2 = String.valueOf(var3.translateKey("selectWorld.world")) + " " + (par1 + 1);
        }
        return var2;
    }
    
    public void initButtons() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(this.buttonSelect = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, var1.translateKey("selectWorld.select")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, var1.translateKey("selectWorld.create")));
        this.buttonList.add(this.buttonRename = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, var1.translateKey("selectWorld.rename")));
        this.buttonList.add(this.buttonDelete = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, var1.translateKey("selectWorld.delete")));
        this.buttonList.add(this.buttonRecreate = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, var1.translateKey("selectWorld.recreate")));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, var1.translateKey("gui.cancel")));
        this.buttonSelect.enabled = false;
        this.buttonDelete.enabled = false;
        this.buttonRename.enabled = false;
        this.buttonRecreate.enabled = false;
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 2) {
                final String var2 = this.getSaveName(this.selectedWorld);
                if (var2 != null) {
                    this.deleting = true;
                    final GuiYesNo var3 = getDeleteWorldScreen(this, var2, this.selectedWorld);
                    this.mc.displayGuiScreen(var3);
                }
            }
            else if (par1GuiButton.id == 1) {
                this.selectWorld(this.selectedWorld);
            }
            else if (par1GuiButton.id == 3) {
                this.mc.displayGuiScreen(new GuiCreateWorld(this));
            }
            else if (par1GuiButton.id == 6) {
                this.mc.displayGuiScreen(new GuiRenameWorld(this, this.getSaveFileName(this.selectedWorld)));
            }
            else if (par1GuiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (par1GuiButton.id == 7) {
                final GuiCreateWorld var4 = new GuiCreateWorld(this);
                final ISaveHandler var5 = this.mc.getSaveLoader().getSaveLoader(this.getSaveFileName(this.selectedWorld), false);
                final WorldInfo var6 = var5.loadWorldInfo();
                var5.flush();
                var4.func_82286_a(var6);
                this.mc.displayGuiScreen(var4);
            }
            else {
                this.worldSlotContainer.actionPerformed(par1GuiButton);
            }
        }
    }
    
    public void selectWorld(final int par1) {
        this.mc.displayGuiScreen(null);
        if (!this.selected) {
            this.selected = true;
            String var2 = this.getSaveFileName(par1);
            if (var2 == null) {
                var2 = "World" + par1;
            }
            String var3 = this.getSaveName(par1);
            if (var3 == null) {
                var3 = "World" + par1;
            }
            if (this.mc.getSaveLoader().canLoadWorld(var2)) {
                this.mc.launchIntegratedServer(var2, var3, null);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        if (this.deleting) {
            this.deleting = false;
            if (par1) {
                final ISaveFormat var3 = this.mc.getSaveLoader();
                var3.flushCache();
                var3.deleteWorldDirectory(this.getSaveFileName(par2));
                try {
                    this.loadSaves();
                }
                catch (AnvilConverterException var4) {
                    var4.printStackTrace();
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.worldSlotContainer.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
    
    public static GuiYesNo getDeleteWorldScreen(final GuiScreen par0GuiScreen, final String par1Str, final int par2) {
        final StringTranslate var3 = StringTranslate.getInstance();
        final String var4 = var3.translateKey("selectWorld.deleteQuestion");
        final String var5 = "'" + par1Str + "' " + var3.translateKey("selectWorld.deleteWarning");
        final String var6 = var3.translateKey("selectWorld.deleteButton");
        final String var7 = var3.translateKey("gui.cancel");
        final GuiYesNo var8 = new GuiYesNo(par0GuiScreen, var4, var5, var6, var7, par2);
        return var8;
    }
    
    static List getSize(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.saveList;
    }
    
    static int onElementSelected(final GuiSelectWorld par0GuiSelectWorld, final int par1) {
        return par0GuiSelectWorld.selectedWorld = par1;
    }
    
    static int getSelectedWorld(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.selectedWorld;
    }
    
    static GuiButton getSelectButton(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.buttonSelect;
    }
    
    static GuiButton getRenameButton(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.buttonDelete;
    }
    
    static GuiButton getDeleteButton(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.buttonRename;
    }
    
    static GuiButton func_82312_f(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.buttonRecreate;
    }
    
    static String func_82313_g(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.localizedWorldText;
    }
    
    static DateFormat func_82315_h(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.dateFormatter;
    }
    
    static String func_82311_i(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.localizedMustConvertText;
    }
    
    static String[] func_82314_j(final GuiSelectWorld par0GuiSelectWorld) {
        return par0GuiSelectWorld.localizedGameModeText;
    }
}
