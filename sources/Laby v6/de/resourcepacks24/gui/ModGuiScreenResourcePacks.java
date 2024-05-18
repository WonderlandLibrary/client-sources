package de.resourcepacks24.gui;

import de.resourcepacks24.main.Pack;
import de.resourcepacks24.main.ResourcePacks24;
import de.resourcepacks24.utils.Color;
import de.resourcepacks24.utils.DrawUtils;
import de.resourcepacks24.utils.EnumPackSorting;
import de.resourcepacks24.utils.PackFileDownloader;
import de.resourcepacks24.utils.PackInfoCallback;
import de.resourcepacks24.utils.RPTag;
import de.resourcepacks24.utils.Utils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class ModGuiScreenResourcePacks extends GuiScreen
{
    private Minecraft mc;
    private GuiScreen lastScreen;
    private GuiButton buttonDone;
    private GuiButton buttonSearch;
    private GuiButton buttonTop;
    private GuiButton buttonLatest;
    private int selectedButton;
    private String title;
    private ModGuiResourcePacks packList;
    private GuiButton buttonMode;
    private GuiButton buttonDownload;
    private GuiButton buttonOpenPage;
    private GuiButton buttonRandomPack;
    ItemStack breadItem = new ItemStack(Item.getItemById(297));
    ArrayList<ModGuiScreenResourcePacks.BreadVector> breadVec = new ArrayList();
    private GuiTextField searchArea;
    private long cooldown = 0L;
    private int loadingBarAnimation = 0;

    public ModGuiScreenResourcePacks(Minecraft mc, GuiScreenResourcePacks guiScreenResourcePacks)
    {
        this.mc = mc;
        this.lastScreen = guiScreenResourcePacks;
        this.selectedButton = 1;
        this.title = "Search";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.buttonDone = new GuiButton(0, 90, this.height - 25, this.width / 4, 20, "Done"));
        int i = 0;
        this.buttonList.add(this.buttonSearch = new GuiButton(1, 5, 40, 80, 20, "Search"));
        i = i + 22;
        this.buttonList.add(this.buttonTop = new GuiButton(2, 5, 40 + i, 80, 20, "Top 20"));
        i = i + 22;
        this.buttonList.add(this.buttonLatest = new GuiButton(3, 5, 40 + i, 80, 20, "Latest"));
        i = i + 22;
        this.buttonList.add(this.buttonRandomPack = new GuiButton(4, 5, 40 + i, 80, 20, "Random pack"));
        this.buttonList.add(this.buttonMode = new GuiButton(100, this.width - 100, 5, 80, 20, ""));
        this.searchArea = new GuiTextField(50, this.mc.fontRendererObj, 91, 35, this.width - 112, 20);
        this.searchArea.setText(ResourcePacks24.getInstance().getSearchText());
        this.buttonList.add(this.buttonDownload = new GuiButton(101, (this.width + 70) / 2 - this.width / 4 / 2, this.height - 25, this.width / 4, 20, "Download"));
        this.buttonList.add(this.buttonOpenPage = new GuiButton(102, this.width - this.width / 4 - 20, this.height - 25, this.width / 4, 20, "Open in website"));
        List<ResourcePackListEntry> list = new ArrayList();
        this.packList = new ModGuiResourcePacks(this.mc, this.width - 90 - 20, this.height + 20, list);
        this.refreshButtons();
        super.initGui();
    }

    public void refreshButtons()
    {
        this.buttonSearch.enabled = this.selectedButton != this.buttonSearch.id;
        this.buttonTop.enabled = this.selectedButton != this.buttonTop.id;
        this.buttonLatest.enabled = this.selectedButton != this.buttonLatest.id;
        this.buttonRandomPack.enabled = this.selectedButton != this.buttonRandomPack.id;
        this.buttonMode.visible = !this.buttonTop.enabled || !this.buttonRandomPack.enabled;
        this.searchArea.setVisible(!this.buttonSearch.enabled);

        if (this.isRandomPack())
        {
            this.buttonMode.displayString = "Next pack";
        }
        else if (ResourcePacks24.getInstance().isSortModeVotes())
        {
            this.buttonMode.displayString = "Votes";
        }
        else
        {
            this.buttonMode.displayString = "Downloads";
        }

        this.refreshPacks();

        if (this.buttonSearch.enabled)
        {
            this.packList.setDimensions(this.packList.getListWidth(), this.height + 20, 30, this.height - 30);
            this.packList.setSlotXBoundsFromLeft(90);
            this.packList.registerScrollButtons(7, 8);
        }
        else
        {
            this.packList.setDimensions(this.packList.getListWidth(), this.height + 20, 60, this.height - 30);
            this.packList.setSlotXBoundsFromLeft(90);
            this.packList.registerScrollButtons(7, 8);
        }
    }

    public void refreshPacks()
    {
        this.packList.setTitle(this.title);
        this.packList.getList().clear();

        for (final Pack pack : ResourcePacks24.getInstance().getPacks())
        {
            ResourcePackListEntry resourcepacklistentry = new ResourcePackListEntry((GuiScreenResourcePacks)this.lastScreen)
            {
                protected int func_183019_a()
                {
                    return 1;
                }
                protected void func_148313_c()
                {
                }
                protected String func_148312_b()
                {
                    String s = "8";

                    switch (pack.getPremiumId())
                    {
                        case 1:
                            s = "f";
                            break;

                        case 2:
                            s = "6";
                            break;

                        case 3:
                            s = "b";
                    }

                    return Color.cl("7") + ModGuiScreenResourcePacks.this.markSearchWord(pack.getBestPossibleName(), "7") + Color.cl("8") + " by " + Color.cl(s) + ModGuiScreenResourcePacks.this.markSearchWord(pack.getCreator(), s);
                }
                protected String func_148311_a()
                {
                    return pack.getDesc() == null ? "" : Color.cl("9") + ModGuiScreenResourcePacks.this.markSearchWord(pack.getDesc(), "9").replace("\r", "");
                }
            };
            this.packList.getList().add(resourcepacklistentry);
            resourcepacklistentry.setPackInfo(pack);
        }
    }

    public boolean isRandomPack()
    {
        return this.title.equals("Random pack");
    }

    private String markSearchWord(String word, String resetColor)
    {
        return this.searchArea.getText().isEmpty() ? word : word.replace(this.searchArea.getText(), Color.cl("n") + this.searchArea.getText() + Color.cl(resetColor));
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.searchArea.textboxKeyTyped(typedChar, keyCode))
        {
            ResourcePacks24.getInstance().setSearchText(this.searchArea.getText());
            this.refreshButtons();
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        if (!this.isRandomPack())
        {
            this.packList.handleMouseInput();
        }

        super.handleMouseInput();
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (!this.isRandomPack())
        {
            this.packList.mouseClicked(mouseX, mouseY, mouseButton);
        }

        this.searchArea.mouseClicked(mouseX, mouseY, mouseButton);
        int i = 135;

        for (RPTag rptag : ResourcePacks24.getInstance().getRpTags())
        {
            if (mouseX > 70 && mouseX < 80 && mouseY > i && mouseY < i + 10)
            {
                rptag.setEnabled(!rptag.isEnabled());
                this.refreshPacks();
            }

            i += 12;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.lastScreen);
        }

        if (button.id == 1 || button.id == 2 || button.id == 3 || button.id == 4)
        {
            ResourcePacks24.getInstance().setSelectedPack((Pack)null);

            if (button.id == 1)
            {
                ResourcePacks24.getInstance().sort(EnumPackSorting.NONE);
            }

            if (button.id == 2)
            {
                ResourcePacks24.getInstance().sort(EnumPackSorting.TOP);
            }

            if (button.id == 3)
            {
                ResourcePacks24.getInstance().sort(EnumPackSorting.LATEST);
            }

            if (button.id == 4)
            {
                ResourcePacks24.getInstance().sort(EnumPackSorting.RANDOM);
                this.getRandomPack();
            }

            this.selectedButton = button.id;
            this.title = button.displayString;
            this.refreshButtons();
        }

        if (button.id == 100)
        {
            if (this.isRandomPack())
            {
                this.cooldown = System.currentTimeMillis() + 500L;
                this.getRandomPack();
            }
            else
            {
                ResourcePacks24.getInstance().setSortModeVotes(!ResourcePacks24.getInstance().isSortModeVotes());
                ResourcePacks24.getInstance().sort(EnumPackSorting.TOP);
                this.refreshButtons();
            }
        }

        if (button.id == 101 && ResourcePacks24.getInstance().getSelectedPack() != null && ResourcePacks24.getInstance().getProgress() == 0)
        {
            ResourcePacks24.getInstance().setProgress(1);
            Pack pack = ResourcePacks24.getInstance().getSelectedPack();
            new PackFileDownloader(pack, new PackInfoCallback()
            {
                public void result(ArrayList<Pack> list)
                {
                }
                public void progress(int i)
                {
                    ResourcePacks24.getInstance().setProgress(i);

                    if (i >= 100 && ModGuiScreenResourcePacks.this.lastScreen != null && ModGuiScreenResourcePacks.this.lastScreen instanceof GuiScreenResourcePacks)
                    {
                        ModGuiScreenResourcePacks.this.lastScreen = new GuiScreenResourcePacks(((GuiScreenResourcePacks)ModGuiScreenResourcePacks.this.lastScreen).getParentScreen());
                    }
                }
            });
        }

        if (button.id == 102 && ResourcePacks24.getInstance().getSelectedPack() != null)
        {
            try
            {
                Utils.openWebpage(new URI(ResourcePacks24.pack_page + ResourcePacks24.getInstance().getSelectedPack().getId()));
            }
            catch (URISyntaxException urisyntaxexception)
            {
                urisyntaxexception.printStackTrace();
            }
        }

        super.actionPerformed(button);
    }

    public void getRandomPack()
    {
        if (ResourcePacks24.getInstance().getPacks().size() > 3)
        {
            ResourcePacks24.getInstance().setSelectedPack((Pack)ResourcePacks24.getInstance().getPacks().get(ResourcePacks24.random.nextInt(ResourcePacks24.getInstance().getPacks().size() - 1)));
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);
        this.buttonDownload.enabled = ResourcePacks24.getInstance().getSelectedPack() != null && ResourcePacks24.getInstance().getProgress() == 0;
        this.buttonOpenPage.enabled = ResourcePacks24.getInstance().getSelectedPack() != null;

        if (!this.isRandomPack())
        {
            this.packList.drawScreen(mouseX, mouseY, partialTicks);
        }
        else if (ResourcePacks24.getInstance().getSelectedPack() != null)
        {
            ResourcePacks24.getInstance().getDraw().drawChatBackground(90, 30, this.width - 20, this.height - 30, 0);
            drawRect(100, 40, 253, 193, Integer.MIN_VALUE);
            ResourcePacks24.getInstance().getDraw().drawString("?", 150.0D, 78.0D, 10.0D);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            ResourcePacks24.getInstance().getSelectedPack().drawImage(100, 40, 153, 153, 0.6D);
            int i = -10;

            if (260 + ResourcePacks24.getInstance().getDraw().getStringWidth(ResourcePacks24.getInstance().getSelectedPack().getName()) * 2 > this.width - 20)
            {
                ResourcePacks24.getInstance().getDraw().drawString(ResourcePacks24.getInstance().getSelectedPack().getName(), 260.0D, 40.0D, 1.0D);
            }
            else
            {
                ResourcePacks24.getInstance().getDraw().drawString(ResourcePacks24.getInstance().getSelectedPack().getName(), 260.0D, 40.0D, 2.0D);
                i += 10;
            }

            ResourcePacks24.getInstance().getDraw().drawString(Color.cl("7") + "by " + ResourcePacks24.getInstance().getSelectedPack().getCreator(), 260.0D, (double)(60 + i), 1.0D);
            i = i + 10;
            i = i + 10;
            ResourcePacks24.getInstance().getDraw().drawString(Color.cl("a") + ResourcePacks24.getInstance().getSelectedPack().getDownloads() + " Downloads", 260.0D, (double)(60 + i), 1.0D);
            i = i + 10;
            ResourcePacks24.getInstance().getDraw().drawString(Color.cl("b") + ResourcePacks24.getInstance().getSelectedPack().getVotes() + " Votes", 260.0D, (double)(60 + i), 1.0D);
            i = i + 10;
            ResourcePacks24.getInstance().getDraw().drawString(Color.cl("c") + ResourcePacks24.getInstance().getSelectedPack().getSize() + "", 260.0D, (double)(60 + i), 1.0D);
            i = i + 10;
            i = i + 10;

            for (String s : ResourcePacks24.getInstance().getDraw().listFormattedStringToWidth(ResourcePacks24.getInstance().getSelectedPack().getDesc().replace("\r", ""), this.width - 90 - 170 - 30))
            {
                ResourcePacks24.getInstance().getDraw().drawString(Color.cl("9") + s, 260.0D, (double)(57 + i), 1.0D);
                i += 10;
            }

            ResourcePacks24.getInstance().getDraw().overlayBackground(this.height - 30, this.height);
        }
        else
        {
            this.getRandomPack();
        }

        this.searchArea.drawTextBox();
        String s1 = "https://resourcepacks24.de/";

        if (this.title.equals("Search") && !this.searchArea.getText().isEmpty())
        {
            s1 = s1 + "search?q=" + this.searchArea.getText();
        }

        if (this.title.equals("Top 20"))
        {
            s1 = s1 + "resourcepacks?sortby=rating";
        }

        if (this.title.equals("Latest"))
        {
            s1 = s1 + "newest";
        }

        if (this.title.equals("Random pack"))
        {
            s1 = s1 + "resourcepack/224552&random";
        }

        ResourcePacks24.getInstance().getDraw().drawCenteredString(s1, this.width / 2, 15);

        if (ResourcePacks24.getInstance().getProgress() != 0)
        {
            ResourcePacks24.getInstance().updateAnimatedProgress();

            if (ResourcePacks24.getInstance().getAnimatedProgress() >= 100)
            {
                ResourcePacks24.getInstance().resetProgress();
                this.refreshButtons();
                --this.loadingBarAnimation;
            }
            else
            {
                if (this.loadingBarAnimation < 11)
                {
                    ++this.loadingBarAnimation;
                }

                int j = this.loadingBarAnimation - 10;
                float f = (float)(ResourcePacks24.getInstance().getAnimatedProgress() * this.width / 100);
                int l = (int)((float)ResourcePacks24.getInstance().getAnimatedProgress() * 100.0F / 100.0F);
                this.drawLoadingBar(f, l, j);
            }
        }
        else if (this.loadingBarAnimation > 0)
        {
            --this.loadingBarAnimation;
        }

        if (this.loadingBarAnimation > 0 && ResourcePacks24.getInstance().getProgress() == 0)
        {
            this.drawLoadingBar((float)this.width, 100, this.loadingBarAnimation - 10);
        }

        int k = 135;

        for (RPTag rptag : ResourcePacks24.getInstance().getRpTags())
        {
            if (mouseX > 70 && mouseX < 80 && mouseY > k && mouseY < k + 10)
            {
                ResourcePacks24.getInstance().getDraw();
                DrawUtils.drawRect(70, k, 80, k + 10, -2140234538);
            }

            if (rptag.isEnabled())
            {
                ResourcePacks24.getInstance().getDraw();
                DrawUtils.drawRect(70, k, 80, k + 10, -2140234538);
                ResourcePacks24.getInstance().getDraw().drawRightString(Color.cl("e") + "\u2714", 80.0D, (double)(k + 1));
            }
            else
            {
                ResourcePacks24.getInstance().getDraw();
                DrawUtils.drawRect(70, k, 80, k + 10, -2140234538);
            }

            ResourcePacks24.getInstance().getDraw().drawRightString(rptag.getTagName(), 67.0D, (double)(k + 3), 0.7D);
            k += 12;
        }

        this.buttonMode.enabled = this.cooldown < System.currentTimeMillis() || !this.isRandomPack();
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.searchArea.getText().equalsIgnoreCase("bread") && ResourcePacks24.random.nextInt(10) == 0)
        {
            this.breadVec.add(new ModGuiScreenResourcePacks.BreadVector());
        }

        ArrayList<ModGuiScreenResourcePacks.BreadVector> arraylist = new ArrayList();

        for (ModGuiScreenResourcePacks.BreadVector modguiscreenresourcepacks$breadvector : this.breadVec)
        {
            ResourcePacks24.getInstance().getDraw().drawItem(this.breadItem, modguiscreenresourcepacks$breadvector.x, modguiscreenresourcepacks$breadvector.y);
            modguiscreenresourcepacks$breadvector.fall();

            if (modguiscreenresourcepacks$breadvector.y > this.height)
            {
                arraylist.add(modguiscreenresourcepacks$breadvector);
            }
        }

        for (ModGuiScreenResourcePacks.BreadVector modguiscreenresourcepacks$breadvector1 : arraylist)
        {
            this.breadVec.remove(modguiscreenresourcepacks$breadvector1);
        }
    }

    private void drawLoadingBar(float screenPercent, int percent, int barPositionY)
    {
        ResourcePacks24.getInstance().getDraw();
        DrawUtils.drawRect(0, barPositionY + 10, this.width, barPositionY + 11, Integer.MIN_VALUE);
        ResourcePacks24.getInstance().getDraw();
        DrawUtils.drawRect(0, barPositionY + 0, this.width, barPositionY + 1, Integer.MIN_VALUE);
        ResourcePacks24.getInstance().getDraw().drawRect(0.0D, (double)barPositionY, (double)screenPercent, (double)(barPositionY + 10), java.awt.Color.GREEN.getRGB());
        ResourcePacks24.getInstance().getDraw();
        DrawUtils.drawRect(0, barPositionY, this.width, barPositionY + 10, Integer.MIN_VALUE);
        ResourcePacks24.getInstance().getDraw().drawCenteredString(Color.cl("f") + percent + "%", this.width / 2, barPositionY + 1);
    }

    private class BreadVector
    {
        public int x = 0;
        public int y = -20;

        public BreadVector()
        {
            this.x = (new Random()).nextInt(ModGuiScreenResourcePacks.this.width) - 5;
        }

        public void fall()
        {
            ++this.y;
        }
    }
}
