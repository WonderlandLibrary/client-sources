package io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.guis;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.button.AtaniButton;
import io.github.liticane.monoxide.util.discord.DiscordRP;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class AtaniSinglePlayerMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger = LogManager.getLogger();
    private final DateFormat field_146633_h = new SimpleDateFormat();
    protected GuiScreen parentScreen;
    protected String field_146628_f = "Select world";
    private boolean field_146634_i;
    private int field_146640_r;
    private java.util.List<SaveFormatComparator> field_146639_s;
    private AtaniSinglePlayerMenu.List field_146638_t;
    private String field_146637_u;
    private String field_146636_v;
    private String[] field_146635_w = new String[4];
    private boolean field_146643_x;
    private GuiButton deleteButton;
    private GuiButton selectButton;
    private GuiButton renameButton;
    private GuiButton recreateButton;

    public AtaniSinglePlayerMenu(GuiScreen parentScreenIn)
    {
        this.parentScreen = parentScreenIn;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        DiscordRP.update("W-wowld List", "S-sewecting a wowld...~ T//w//T");

        this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);

        try
        {
            this.func_146627_h();
        }
        catch (AnvilConverterException anvilconverterexception)
        {
            logger.error("Couldn\'t load level list", anvilconverterexception);
            this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
            return;
        }

        this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
        this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
        this.field_146638_t = new AtaniSinglePlayerMenu.List(this.mc);
        this.field_146638_t.registerScrollButtons(4, 5);
        this.func_146618_g();
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.field_146638_t.handleMouseInput();
    }

    private void func_146627_h() throws AnvilConverterException
    {
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        this.field_146639_s = isaveformat.getSaveList();
        Collections.sort(this.field_146639_s);
        this.field_146640_r = -1;
    }

    protected String func_146621_a(int p_146621_1_)
    {
        return this.field_146639_s.get(p_146621_1_).getFileName();
    }

    protected String func_146614_d(int p_146614_1_)
    {
        String s = this.field_146639_s.get(p_146614_1_).getDisplayName();

        if (StringUtils.isEmpty(s))
        {
            s = I18n.format("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
        }

        return s;
    }

    public void func_146618_g()
    {
        this.buttonList.clear();
        this.buttonList.add(this.selectButton = new AtaniButton(1, this.width / 2 - 154, this.height - 52 - 30 - 5, 150, 20, I18n.format("selectWorld.select", new Object[0])));
        this.buttonList.add(new AtaniButton(3, this.width / 2 + 4, this.height - 52 - 30 - 5, 150, 20, I18n.format("selectWorld.create", new Object[0])));
        this.buttonList.add(this.renameButton = new AtaniButton(6, this.width / 2 - 154, this.height - 28 - 30, 71, 20, I18n.format("selectWorld.rename", new Object[0])));
        this.buttonList.add(this.deleteButton = new AtaniButton(2, this.width / 2 - 76 + 1, this.height - 28 - 30, 71, 20, I18n.format("selectWorld.delete", new Object[0])));
        this.buttonList.add(this.recreateButton = new AtaniButton(7, this.width / 2 + 4, this.height - 28 - 30, 71, 20, I18n.format("selectWorld.recreate", new Object[0])));
        this.buttonList.add(new AtaniButton(0, this.width / 2 + 82 + 1, this.height - 28 - 30, 71, 20, I18n.format("gui.cancel", new Object[0])));
        this.selectButton.enabled = false;
        this.deleteButton.enabled = false;
        this.renameButton.enabled = false;
        this.recreateButton.enabled = false;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 2)
            {
                String s = this.func_146614_d(this.field_146640_r);

                if (s != null)
                {
                    this.field_146643_x = true;
                    GuiYesNo guiyesno = func_152129_a(this, s, this.field_146640_r);
                    this.mc.displayGuiScreen(guiyesno);
                }
            }
            else if (button.id == 1)
            {
                this.func_146615_e(this.field_146640_r);
            }
            else if (button.id == 3)
            {
                this.mc.displayGuiScreen(new GuiCreateWorld(this));
            }
            else if (button.id == 6)
            {
                this.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
            }
            else if (button.id == 0)
            {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button.id == 7)
            {
                GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
                ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), false);
                WorldInfo worldinfo = isavehandler.loadWorldInfo();
                isavehandler.flush();
                guicreateworld.func_146318_a(worldinfo);
                this.mc.displayGuiScreen(guicreateworld);
            }
            else
            {
                this.field_146638_t.actionPerformed(button);
            }
        }
    }

    public void func_146615_e(int p_146615_1_)
    {
        this.mc.displayGuiScreen(null);

        if (!this.field_146634_i)
        {
            this.field_146634_i = true;
            String s = this.func_146621_a(p_146615_1_);

            if (s == null)
            {
                s = "World" + p_146615_1_;
            }

            String s1 = this.func_146614_d(p_146615_1_);

            if (s1 == null)
            {
                s1 = "World" + p_146615_1_;
            }

            if (this.mc.getSaveLoader().canLoadWorld(s))
            {
                this.mc.launchIntegratedServer(s, s1, null);
            }
        }
    }

    public void confirmClicked(boolean result, int id)
    {
        if (this.field_146643_x)
        {
            this.field_146643_x = false;

            if (result)
            {
                ISaveFormat isaveformat = this.mc.getSaveLoader();
                isaveformat.flushCache();
                isaveformat.deleteWorldDirectory(this.func_146621_a(id));

                try
                {
                    this.func_146627_h();
                }
                catch (AnvilConverterException anvilconverterexception)
                {
                    logger.error("Couldn\'t load level list", anvilconverterexception);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.field_146638_t.drawScreen(mouseX, mouseY, partialTicks);
        this.fontRendererObj = FontStorage.getInstance().findFont("SF UI", 19);
        this.drawCenteredString(this.fontRendererObj, this.field_146628_f, this.width / 2, 20 + 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public static GuiYesNo func_152129_a(GuiYesNoCallback p_152129_0_, String p_152129_1_, int p_152129_2_)
    {
        String s = I18n.format("selectWorld.deleteQuestion", new Object[0]);
        String s1 = "\'" + p_152129_1_ + "\' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
        String s2 = I18n.format("selectWorld.deleteButton", new Object[0]);
        String s3 = I18n.format("gui.cancel", new Object[0]);
        return new GuiYesNo(p_152129_0_, s, s1, s2, s3, p_152129_2_);
    }

    class List extends AtaniSlotGui
    {
        public List(Minecraft mcIn)
        {
            super(mcIn, AtaniSinglePlayerMenu.this.width, AtaniSinglePlayerMenu.this.height, 32 + 20, AtaniSinglePlayerMenu.this.height - 64, 36);
        }

        protected int getSize()
        {
            return AtaniSinglePlayerMenu.this.field_146639_s.size();
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {
            AtaniSinglePlayerMenu.this.field_146640_r = slotIndex;
            boolean flag = AtaniSinglePlayerMenu.this.field_146640_r >= 0 && AtaniSinglePlayerMenu.this.field_146640_r < this.getSize();
            AtaniSinglePlayerMenu.this.selectButton.enabled = flag;
            AtaniSinglePlayerMenu.this.deleteButton.enabled = flag;
            AtaniSinglePlayerMenu.this.renameButton.enabled = flag;
            AtaniSinglePlayerMenu.this.recreateButton.enabled = flag;

            if (isDoubleClick && flag)
            {
                AtaniSinglePlayerMenu.this.func_146615_e(slotIndex);
            }
        }

        protected boolean isSelected(int slotIndex)
        {
            return slotIndex == AtaniSinglePlayerMenu.this.field_146640_r;
        }

        protected int getContentHeight()
        {
            return AtaniSinglePlayerMenu.this.field_146639_s.size() * 36;
        }

        protected void drawBackground()
        {
            AtaniSinglePlayerMenu.this.drawDefaultBackground();
        }

        protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn)
        {
            AtaniSinglePlayerMenu.this.fontRendererObj = FontStorage.getInstance().findFont("SF UI", 19);

            SaveFormatComparator saveformatcomparator = AtaniSinglePlayerMenu.this.field_146639_s.get(entryID);
            String s = saveformatcomparator.getDisplayName();

            if (StringUtils.isEmpty(s))
            {
                s = AtaniSinglePlayerMenu.this.field_146637_u + " " + (entryID + 1);
            }

            String s1 = saveformatcomparator.getFileName();
            s1 = s1 + " (" + AtaniSinglePlayerMenu.this.field_146633_h.format(new Date(saveformatcomparator.getLastTimePlayed()));
            s1 = s1 + ")";
            String s2 = "";

            if (saveformatcomparator.requiresConversion())
            {
                s2 = AtaniSinglePlayerMenu.this.field_146636_v + " " + s2;
            }
            else
            {
                s2 = AtaniSinglePlayerMenu.this.field_146635_w[saveformatcomparator.getEnumGameType().getID()];

                if (saveformatcomparator.isHardcoreModeEnabled())
                {
                    s2 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
                }

                if (saveformatcomparator.getCheatsEnabled())
                {
                    s2 = s2 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
                }
            }

            AtaniSinglePlayerMenu.this.drawString(AtaniSinglePlayerMenu.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
            AtaniSinglePlayerMenu.this.drawString(AtaniSinglePlayerMenu.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
            AtaniSinglePlayerMenu.this.drawString(AtaniSinglePlayerMenu.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
        }
    }
}
