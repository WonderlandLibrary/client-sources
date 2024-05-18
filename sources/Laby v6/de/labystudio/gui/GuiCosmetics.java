package de.labystudio.gui;

import de.labystudio.cosmetic.Cosmetic;
import de.labystudio.cosmetic.CosmeticUser;
import de.labystudio.cosmetic.EnumCosmetic;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.slider.CustomSlider;
import de.labystudio.slider.SliderCallback;
import de.labystudio.slider.SliderOption;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.Utils;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiCosmetics extends GuiScreen
{
    private GuiScreen lastScreen;
    private EnumCosmetic selected;
    private GuiTextField toolId;
    private GuiButton buttonEnableAll;
    private GuiButton buttonDone;
    private GuiButton buttonTemplate;

    public GuiCosmetics(GuiScreen lastScreen)
    {
        this.lastScreen = lastScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.refreshButtons();
    }

    private void refreshButtons()
    {
        this.buttonList.clear();
        boolean flag = false;
        int i = 0;
        int j = 0;
        UUID uuid = LabyMod.getInstance().getPlayerUUID();
        CosmeticUser cosmeticuser = null;
        String s = Utils.sha1(uuid.toString());
        CosmeticUser cosmeticuser1 = uuid == null ? null : (CosmeticUser)LabyMod.getInstance().getCosmeticManager().getOnlineCosmetics().get(s);

        if (uuid == null)
        {
            Object object = null;
        }
        else
        {
            CosmeticUser cosmeticuser2 = (CosmeticUser)LabyMod.getInstance().getCosmeticManager().getOfflineCosmetics().get(s);
        }

        this.buttonList.add(this.buttonEnableAll = new GuiButton(2, 3, 3, 100, 20, "Cosmetics: " + (ConfigManager.settings.cosmetics ? Color.cl("a") + "Enabled" : Color.cl("c") + "Disabled")));
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, this.height - 43, "Done"));
        SliderOption slideroption = new SliderOption(0.0F, 50.0F, new SliderCallback()
        {
            public void setValue(int i)
            {
                ConfigManager.settings.wingsScale = i;
            }
            public int getValue()
            {
                return ConfigManager.settings.wingsScale;
            }
            public String getTitle(int value)
            {
                return value + 75 + "%";
            }
        });
        this.buttonList.add(new CustomSlider(10, this.width - 52, 4, slideroption, 50, 20));

        for (EnumCosmetic enumcosmetic : EnumCosmetic.values())
        {
            if (enumcosmetic != EnumCosmetic.TAG && enumcosmetic != EnumCosmetic.NONE && enumcosmetic != EnumCosmetic.CENSORED && enumcosmetic != EnumCosmetic.PIXELBIESTER && enumcosmetic != EnumCosmetic.RANK && enumcosmetic != EnumCosmetic.HALLOWEEN && enumcosmetic != EnumCosmetic.XMAS && enumcosmetic != EnumCosmetic.HEROBRINE)
            {
                GuiButton guibutton;
                this.buttonList.add(guibutton = new GuiButton(-1, this.width / 2 + j - 175, this.height / 4 + i, 70, 20, Color.booleanToColor(Boolean.valueOf(this.isEnabled(enumcosmetic))) + enumcosmetic.name()));
                guibutton.enabled = this.selected != enumcosmetic;

                if (cosmeticuser1 != null && cosmeticuser1.getEnumList().contains(enumcosmetic))
                {
                    guibutton.enabled = false;
                    guibutton.displayString = Color.cl("7") + enumcosmetic.name() + Color.cl("a") + " \u2714";
                }

                j += 70;

                if (j > 280)
                {
                    i += 21;
                    j = 0;
                }
            }
        }

        if (this.selected != null)
        {
            if (this.selected != EnumCosmetic.TOOL)
            {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100 - 50, this.height / 2, this.selected.name() + ": " + (this.isEnabled(this.selected) ? Color.cl("a") + "Enabled" : Color.cl("c") + "Disabled")));
            }

            if (this.selected == EnumCosmetic.TOOL)
            {
                int k = this.width / 2;
                int l = this.height / 2;
                this.toolId = new GuiTextField(-1, LabyMod.getInstance().draw.fontRenderer, k, l, 50, 20);
                this.toolId.setText(ConfigManager.settings.cosmeticsTool + "");
            }

            if (this.selected == EnumCosmetic.WOLFTAIL || this.selected == EnumCosmetic.OCELOTTAIL || this.selected == EnumCosmetic.DEADMAU5)
            {
                this.buttonList.add(this.buttonTemplate = new GuiButton(3, this.width / 2 - 150, this.height / 2 + 25, "Download Skin Template"));
            }
        }

        this.buttonList.add(new GuiButton(4, 105, 3, 100, 20, Color.cl("e") + "Online Cosmetics"));
    }

    private boolean isEnabled(EnumCosmetic cosmetic)
    {
        return LabyMod.getInstance().getCosmeticManager().hasCosmetic(cosmetic);
    }

    public static Cosmetic setCosmetic(Cosmetic cosmetic, boolean overwrite)
    {
        UUID uuid = LabyMod.getInstance().getPlayerUUID();

        if (uuid == null)
        {
            return null;
        }
        else
        {
            String s = Utils.sha1(uuid.toString());
            CosmeticUser cosmeticuser = (CosmeticUser)LabyMod.getInstance().getCosmeticManager().getOfflineCosmetics().get(s);

            if (cosmeticuser == null)
            {
                cosmeticuser = new CosmeticUser();
            }

            if (!cosmeticuser.getEnumList().contains(cosmetic.getType()) || overwrite)
            {
                cosmeticuser.addToCosmeticList(cosmetic);
            }

            cosmeticuser.updateData();
            LabyMod.getInstance().getCosmeticManager().getOfflineCosmetics().put(s, cosmeticuser);
            return null;
        }
    }

    private Cosmetic removeCosmetic(EnumCosmetic type)
    {
        UUID uuid = LabyMod.getInstance().getPlayerUUID();

        if (uuid == null)
        {
            return null;
        }
        else
        {
            String s = Utils.sha1(uuid.toString());
            CosmeticUser cosmeticuser = (CosmeticUser)LabyMod.getInstance().getCosmeticManager().getOfflineCosmetics().get(s);

            if (cosmeticuser == null)
            {
                return null;
            }
            else
            {
                cosmeticuser.getCosmeticHashMap().remove(type);
                return null;
            }
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == -1)
        {
            for (EnumCosmetic enumcosmetic : EnumCosmetic.values())
            {
                if (enumcosmetic != EnumCosmetic.TAG && enumcosmetic != EnumCosmetic.NONE && button.displayString.contains(enumcosmetic.name()))
                {
                    this.selected = enumcosmetic;
                    this.refreshButtons();
                }
            }
        }

        if (button.id == 0)
        {
            ConfigManager.save();
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        }

        if (button.id == 2)
        {
            ConfigManager.settings.cosmetics = !ConfigManager.settings.cosmetics;
            ConfigManager.save();
            this.refreshButtons();
        }

        if (button.id == 3)
        {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/images/skin_template.png", true);
        }

        if (button.id == 4)
        {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/#login", true);
        }

        if (button.id == 1 && this.selected != null)
        {
            if (this.isEnabled(this.selected))
            {
                this.removeCosmetic(this.selected);

                if (this.selected == EnumCosmetic.WOLFTAIL)
                {
                    ConfigManager.settings.cosmeticsWolfTail = false;
                }

                if (this.selected == EnumCosmetic.WINGS)
                {
                    ConfigManager.settings.cosmeticsWings = false;
                }

                if (this.selected == EnumCosmetic.OCELOTTAIL)
                {
                    ConfigManager.settings.cosmeticsOcelot = false;
                }

                if (this.selected == EnumCosmetic.DEADMAU5)
                {
                    ConfigManager.settings.cosmeticsDeadmau = false;
                }

                if (this.selected == EnumCosmetic.BLAZE)
                {
                    ConfigManager.settings.cosmeticsBlaze = false;
                }

                if (this.selected == EnumCosmetic.WITHER)
                {
                    ConfigManager.settings.cosmeticsWither = false;
                }

                if (this.selected == EnumCosmetic.HAT)
                {
                    ConfigManager.settings.cosmeticsHat = false;
                }

                if (this.selected == EnumCosmetic.TOOL)
                {
                    ConfigManager.settings.cosmeticsTool = 0;
                }

                if (this.selected == EnumCosmetic.HALO)
                {
                    ConfigManager.settings.cosmeticsHalo = false;
                }

                ConfigManager.save();
            }
            else
            {
                Cosmetic cosmetic = new Cosmetic(this.selected, "");

                if (this.selected == EnumCosmetic.WOLFTAIL)
                {
                    ConfigManager.settings.cosmeticsWolfTail = true;
                }

                if (this.selected == EnumCosmetic.WINGS)
                {
                    cosmetic.a = (double)ConfigManager.settings.colorR;
                    cosmetic.b = (double)ConfigManager.settings.colorG;
                    cosmetic.c = (double)ConfigManager.settings.colorB;
                    ConfigManager.settings.cosmeticsWings = true;
                }

                if (this.selected == EnumCosmetic.OCELOTTAIL)
                {
                    ConfigManager.settings.cosmeticsOcelot = true;
                }

                if (this.selected == EnumCosmetic.DEADMAU5)
                {
                    ConfigManager.settings.cosmeticsDeadmau = true;
                }

                if (this.selected == EnumCosmetic.BLAZE)
                {
                    ConfigManager.settings.cosmeticsBlaze = true;
                }

                if (this.selected == EnumCosmetic.WITHER)
                {
                    ConfigManager.settings.cosmeticsWither = true;
                }

                if (this.selected == EnumCosmetic.HAT)
                {
                    ConfigManager.settings.cosmeticsHat = true;
                }

                if (this.selected == EnumCosmetic.TOOL)
                {
                    ConfigManager.settings.cosmeticsTool = 0;
                }

                if (this.selected == EnumCosmetic.HALO)
                {
                    ConfigManager.settings.cosmeticsHalo = true;
                }

                setCosmetic(cosmetic, true);
                ConfigManager.save();
            }

            this.refreshButtons();
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);
        int k = this.width / 2;
        int l = this.height / 4;
        LabyMod.getInstance().draw.drawCenteredString("Free LabyMod Offline Cosmetics", k, l - 20);
        double d0 = (double)(this.width - 55);
        LabyMod.getInstance().draw.drawRightString("Wings scale", d0, 10.0D);

        if (this.selected != null)
        {
            d0 = (double)(this.width / 2 - 150);
            l = this.height / 2;
            LabyMod.getInstance().draw.drawString("Offline cosmetic:", d0, (double)(l - 15));

            if (Minecraft.getMinecraft().thePlayer != null)
            {
                if (this.selected != EnumCosmetic.TOOL && this.selected != EnumCosmetic.WINGS && this.selected != EnumCosmetic.WOLFTAIL && this.selected != EnumCosmetic.OCELOTTAIL)
                {
                    DrawUtils.drawEntityOnScreen(this.width / 2 + 50 + 60, this.height / 2 + 50 + 40, 20, (float)(-mouseX + this.width / 2 + 10 + 100), (float)(-mouseY + this.height / 2 - 30 + 40), 0, 0, Minecraft.getMinecraft().thePlayer);
                }
                else
                {
                    DrawUtils.drawEntityOnScreen(this.width / 2 + 50 + 60, this.height / 2 + 50 + 40, 20, (float)(mouseX - this.width / 2 - 10 - 100), (float)(-mouseY + this.height / 2 - 30 + 40), 180, 0, Minecraft.getMinecraft().thePlayer);
                }

                if (this.mc.isSingleplayer())
                {
                    LabyMod.getInstance().draw.drawString(Color.cl("c") + "Live preview only in multiplayer!", 3.0D, (double)(this.height - 10));
                }
            }

            if (this.toolId != null && this.selected != null && this.selected == EnumCosmetic.TOOL)
            {
                String s = ConfigManager.settings.cosmeticsTool != 0 ? "Item id:" : Color.cl("c") + "OFF " + Color.cl("f") + "     Item id:";
                LabyMod.getInstance().draw.drawRightString(s, (double)(this.width / 2 - 10), (double)(this.height / 2 + 5));
                ItemStack itemstack = new ItemStack(Item.getItemById(ConfigManager.settings.cosmeticsTool));

                if (itemstack != null && itemstack.getItem() != null)
                {
                    LabyMod.getInstance().draw.drawItem(itemstack, this.width / 2 - 85, this.height / 2);
                }

                this.toolId.drawTextBox();
            }

            if (this.selected == EnumCosmetic.WINGS)
            {
                int j = 50;
                int i = this.height / 2 + 40;
                drawRect(this.width / 2 - 100 - j, i, this.width / 2 + 100 - j, i + 10, Integer.MIN_VALUE);
                float f = (float)ConfigManager.settings.colorR / 255.0F * 200.0F;
                drawRect((int)f + this.width / 2 - 101 - j, i - 3, (int)f + 2 + this.width / 2 - 99 - j, i + 10 + 3, Integer.MAX_VALUE);
                drawRect((int)f + this.width / 2 - 100 - j, i - 2, (int)f + 2 + this.width / 2 - 100 - j, i + 10 + 2, Color.toRGB(ConfigManager.settings.colorR, 0, 0, 200));
                LabyMod.getInstance().draw.drawCenteredString(Color.cl("c") + ConfigManager.settings.colorR + "", this.width / 2 - j, i + 1);
                i = this.height / 2 + 60;
                drawRect(this.width / 2 - 100 - j, i, this.width / 2 + 100 - j, i + 10, Integer.MIN_VALUE);
                f = (float)ConfigManager.settings.colorG / 255.0F * 200.0F;
                drawRect((int)f + this.width / 2 - 101 - j, i - 3, (int)f + 2 + this.width / 2 - 99 - j, i + 10 + 3, Integer.MAX_VALUE);
                drawRect((int)f + this.width / 2 - 100 - j, i - 2, (int)f + 2 + this.width / 2 - 100 - j, i + 10 + 2, Color.toRGB(0, ConfigManager.settings.colorG, 0, 200));
                LabyMod.getInstance().draw.drawCenteredString(Color.cl("2") + ConfigManager.settings.colorG + "", this.width / 2 - j, i + 1);
                i = this.height / 2 + 80;
                drawRect(this.width / 2 - 100 - j, i, this.width / 2 + 100 - j, i + 10, Integer.MIN_VALUE);
                f = (float)ConfigManager.settings.colorB / 255.0F * 200.0F;
                drawRect((int)f + this.width / 2 - 101 - j, i - 3, (int)f + 2 + this.width / 2 - 99 - j, i + 10 + 3, Integer.MAX_VALUE);
                drawRect((int)f + this.width / 2 - 100 - j, i - 2, (int)f + 2 + this.width / 2 - 100 - j, i + 10 + 2, Color.toRGB(0, 0, ConfigManager.settings.colorB, 200));
                LabyMod.getInstance().draw.drawCenteredString(Color.cl("9") + ConfigManager.settings.colorB + "", this.width / 2 - j, i + 1);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        try
        {
            if (this.toolId != null && this.toolId.textboxKeyTyped(typedChar, keyCode))
            {
                try
                {
                    ConfigManager.settings.cosmeticsTool = Integer.parseInt(this.toolId.getText());
                }
                catch (Exception var4)
                {
                    ConfigManager.settings.cosmeticsTool = 0;
                    this.toolId.setText("0");
                }

                if (Item.getItemById(ConfigManager.settings.cosmeticsTool) != null)
                {
                    setCosmetic(new Cosmetic(EnumCosmetic.TOOL, "1:" + ConfigManager.settings.cosmeticsTool), true);
                }
                else
                {
                    ConfigManager.settings.cosmeticsTool = 0;
                    this.removeCosmetic(EnumCosmetic.TOOL);
                }

                GuiTextField guitextfield = this.toolId;
                this.refreshButtons();
                this.toolId = guitextfield;
            }

            if (keyCode == 1)
            {
                ConfigManager.save();
                Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
                return;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (this.selected != null && this.selected == EnumCosmetic.TOOL && this.toolId != null)
        {
            this.toolId.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        int i = -50;
        int j = this.height / 2 + 40;

        if (mouseX > this.width / 2 + i - 100 && mouseX < this.width / 2 + i + 100 && mouseY > j && mouseY < j + 10)
        {
            ConfigManager.settings.colorR = (int)((double)(mouseX - (this.width / 2 - 100 + i)) * 1.285D);
            Cosmetic cosmetic = LabyMod.getInstance().getCosmeticManager().getCosmeticByType(EnumCosmetic.WINGS);

            if (cosmetic != null)
            {
                cosmetic.a = (double)ConfigManager.settings.colorR;
                setCosmetic(cosmetic, true);
            }
        }

        j = this.height / 2 + 60;

        if (mouseX > this.width / 2 + i - 100 && mouseX < this.width / 2 + i + 100 && mouseY > j && mouseY < j + 10)
        {
            ConfigManager.settings.colorG = (int)((double)(mouseX - (this.width / 2 - 100 + i)) * 1.285D);
            Cosmetic cosmetic1 = LabyMod.getInstance().getCosmeticManager().getCosmeticByType(EnumCosmetic.WINGS);

            if (cosmetic1 != null)
            {
                cosmetic1.b = (double)ConfigManager.settings.colorG;
                setCosmetic(cosmetic1, true);
            }
        }

        j = this.height / 2 + 80;

        if (mouseX > this.width / 2 + i - 100 && mouseX < this.width / 2 + i + 100 && mouseY > j && mouseY < j + 10)
        {
            ConfigManager.settings.colorB = (int)((double)(mouseX - (this.width / 2 - 100 + i)) * 1.285D);
            Cosmetic cosmetic2 = LabyMod.getInstance().getCosmeticManager().getCosmeticByType(EnumCosmetic.WINGS);

            if (cosmetic2 != null)
            {
                cosmetic2.c = (double)ConfigManager.settings.colorB;
                setCosmetic(cosmetic2, true);
            }
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
}
