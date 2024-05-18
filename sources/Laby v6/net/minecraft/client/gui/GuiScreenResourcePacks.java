package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import de.resourcepacks24.gui.ModGuiScreenResourcePacks;
import de.resourcepacks24.main.ResourcePacks24;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

public class GuiScreenResourcePacks extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private final GuiScreen parentScreen;
    private List<ResourcePackListEntry> availableResourcePacks;
    private List<ResourcePackListEntry> selectedResourcePacks;

    /** List component that contains the available resource packs */
    private GuiResourcePackAvailable availableResourcePacksList;

    /** List component that contains the selected resource packs */
    private GuiResourcePackSelected selectedResourcePacksList;
    private boolean changed = false;
    public GuiTextField search;
    List<ResourcePackListEntry> packList = new ArrayList();
    private GuiButton buttonDone;

    public GuiScreenResourcePacks(GuiScreen parentScreenIn)
    {
        this.parentScreen = parentScreenIn;
    }

    public GuiScreen getParentScreen()
    {
        return this.parentScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        ResourcePacks24.getInstance();
        this.buttonList.add(new GuiOptionButton(3, this.width / 2 - 205, this.height - 48, 202, 20, "ResourcePacks24.de"));
        this.buttonList.add(new GuiOptionButton(2, this.width / 2 + 3, this.height - 48, 130, 20, "Open pack folder"));
        this.buttonList.add(this.buttonDone = new GuiOptionButton(1, this.width / 2 + 135, this.height - 48, 70, 20, I18n.format("gui.done", new Object[0])));

        if (!this.changed)
        {
            this.availableResourcePacks = Lists.<ResourcePackListEntry>newArrayList();
            this.selectedResourcePacks = Lists.<ResourcePackListEntry>newArrayList();
            ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
            resourcepackrepository.updateRepositoryEntriesAll();
            List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
            list.removeAll(resourcepackrepository.getRepositoryEntries());
            ArrayList<ResourcePackRepository.Entry> arraylist = new ArrayList();

            for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
            {
                for (String s : ResourcePacks24.getInstance().getDeletedPacks())
                {
                    if (resourcepackrepository$entry.getResourcePackName().equals(s))
                    {
                        arraylist.add(resourcepackrepository$entry);
                    }
                }
            }

            list.removeAll(arraylist);

            for (ResourcePackRepository.Entry resourcepackrepository$entry1 : list)
            {
                this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
            }

            for (ResourcePackRepository.Entry resourcepackrepository$entry2 : Lists.reverse(resourcepackrepository.getRepositoryEntries()))
            {
                this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry2));
            }

            this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
        }

        this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
        this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
        this.availableResourcePacksList.registerScrollButtons(7, 8);
        this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
        this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
        this.selectedResourcePacksList.registerScrollButtons(7, 8);
        this.availableResourcePacksList.top += 25;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        this.search = new GuiTextField(0, this.mc.fontRendererObj, scaledresolution.getScaledWidth() / 2 - 203, 33, 198, 20);
        this.packList.clear();
        this.packList.addAll(this.availableResourcePacksList.getList());
        this.refreshPackList();
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.selectedResourcePacksList.handleMouseInput();
        this.availableResourcePacksList.handleMouseInput();
    }

    public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_)
    {
        return this.selectedResourcePacks.contains(p_146961_1_);
    }

    public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_)
    {
        return this.hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
    }

    public List<ResourcePackListEntry> getAvailableResourcePacks()
    {
        return this.availableResourcePacks;
    }

    public List<ResourcePackListEntry> getSelectedResourcePacks()
    {
        return this.selectedResourcePacks;
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
                File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
                String s = file1.getAbsolutePath();

                if (Util.getOSType() == Util.EnumOS.OSX)
                {
                    try
                    {
                        logger.info(s);
                        Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
                        return;
                    }
                    catch (IOException ioexception11)
                    {
                        logger.error((String)"Couldn\'t open file", (Throwable)ioexception11);
                    }
                }
                else if (Util.getOSType() == Util.EnumOS.WINDOWS)
                {
                    String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {s});

                    try
                    {
                        Runtime.getRuntime().exec(s1);
                        return;
                    }
                    catch (IOException ioexception1)
                    {
                        logger.error((String)"Couldn\'t open file", (Throwable)ioexception1);
                    }
                }

                boolean flag = false;

                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {file1.toURI()});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                    flag = true;
                }

                if (flag)
                {
                    logger.info("Opening via system class!");
                    Sys.openURL("file://" + s);
                }
            }
            else if (button.id == 1)
            {
                if (this.changed && !isShiftKeyDown())
                {
                    List<ResourcePackRepository.Entry> list = Lists.<ResourcePackRepository.Entry>newArrayList();

                    for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks)
                    {
                        if (resourcepacklistentry instanceof ResourcePackListEntryFound)
                        {
                            list.add(((ResourcePackListEntryFound)resourcepacklistentry).func_148318_i());
                        }
                    }

                    Collections.reverse(list);
                    this.mc.getResourcePackRepository().setRepositories(list);
                    this.mc.gameSettings.resourcePacks.clear();
                    this.mc.gameSettings.field_183018_l.clear();

                    for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
                    {
                        this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());

                        if (resourcepackrepository$entry.func_183027_f() != 1)
                        {
                            this.mc.gameSettings.field_183018_l.add(resourcepackrepository$entry.getResourcePackName());
                        }
                    }

                    this.mc.gameSettings.saveOptions();
                    this.mc.refreshResources();
                }

                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button.id == 3)
            {
                this.mc.displayGuiScreen(new ModGuiScreenResourcePacks(this.mc, this));
            }
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
        this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
        this.search.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);
        this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
        this.search.drawTextBox();

        if (!this.search.isFocused() && this.search.getText().isEmpty())
        {
            this.mc.fontRendererObj.drawStringWithShadow("Search..", (float)(this.search.xPosition + 5), (float)(this.search.yPosition + 6), Color.lightGray.getRGB());
        }

        if (this.buttonDone != null)
        {
            this.buttonDone.displayString = isShiftKeyDown() ? "Cancel" : "Done";
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Marks the selected resource packs list as changed to trigger a resource reload when the screen is closed
     */
    public void markChanged()
    {
        this.changed = true;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.search.textboxKeyTyped(typedChar, keyCode))
        {
            this.refreshPackList();
        }

        super.keyTyped(typedChar, keyCode);
    }

    int i = 0;   
    public void refreshPackList()
    {
        String s = this.search.getText().replace(" ", "");
        final String s1 = ResourcePacks24.getInstance().getPath();
        this.availableResourcePacksList.getList().clear();
        List<ResourcePackRepository.Entry> list = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntriesAll();

        if (s.isEmpty())
        {
            if (s1.isEmpty())
            {
                ArrayList<String> arraylist = new ArrayList();

                for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
                {
                    final String s2 = resourcepackrepository$entry.getDirPath().replaceFirst("dir_", "");

                    if (!s2.isEmpty() && !arraylist.contains(s2))
                    {                        
                        for (ResourcePackRepository.Entry resourcepackrepository$entry1 : list)
                        {
                            i += resourcepackrepository$entry1.getDirPath().equals(resourcepackrepository$entry.getDirPath()) ? 1 : 0;
                        }

                        ResourcePackListEntry resourcepacklistentry2 = new ResourcePackListEntry(this)
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
                                return s2;
                            }
                            protected String func_148311_a()
                            {
                                return i + " resource pack" + (i == 1 ? "" : "s");
                            }
                        };
                        resourcepacklistentry2.setFolder(true);
                        this.availableResourcePacksList.getList().add(resourcepacklistentry2);
                        arraylist.add(s2);
                    }
                }
            }
            else
            {
                ResourcePackListEntry resourcepacklistentry = new ResourcePackListEntry(this)
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
                        return "..";
                    }
                    protected String func_148311_a()
                    {
                        return "You are in \"" + s1 + "\"\nGo back!";
                    }
                };
                resourcepacklistentry.setFolder(true);
                this.availableResourcePacksList.getList().add(resourcepacklistentry);
            }
        }

        Iterator iterator = this.packList.iterator();

        while (true)
        {
            ResourcePackListEntry resourcepacklistentry1;

            while (true)
            {
                if (!iterator.hasNext())
                {
                    this.availableResourcePacksList.getList().removeAll(this.selectedResourcePacks);
                    return;
                }

                resourcepacklistentry1 = (ResourcePackListEntry)iterator.next();

                if (s.isEmpty() || resourcepacklistentry1.getTitle().toLowerCase().replace(" ", "").contains(s.toLowerCase()))
                {
                    if (!s.isEmpty())
                    {
                        break;
                    }

                    boolean flag = false;

                    for (ResourcePackRepository.Entry resourcepackrepository$entry2 : list)
                    {
                        if (resourcepackrepository$entry2.getResourcePackName().equals(resourcepacklistentry1.getTitle()) && !resourcepackrepository$entry2.getDirPath().equals(s1))
                        {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag)
                    {
                        break;
                    }
                }
            }

            this.availableResourcePacksList.getList().add(resourcepacklistentry1);
        }
    }
}
