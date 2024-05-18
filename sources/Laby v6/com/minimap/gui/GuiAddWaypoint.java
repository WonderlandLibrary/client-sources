package com.minimap.gui;

import net.minecraft.client.resources.*;
import com.minimap.interfaces.*;
import org.lwjgl.input.*;
import java.io.*;
import com.minimap.*;
import com.minimap.settings.*;
import com.minimap.minimap.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiAddWaypoint extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    public GuiTextField nameTextField;
    public String nameText;
    public GuiTextField xTextField;
    public GuiTextField yTextField;
    public GuiTextField zTextField;
    public GuiTextField yawTextField;
    public GuiTextField charTextField;
    public String initial;
    public String yaw;
    public ArrayList<GuiDropDown> dropDowns;
    public GuiWaypointSets sets;
    public String fromSet;
    public int color;
    private Waypoint point;
    private ModSettings guiModSettings;
    private boolean dropped;
    
    public String[] createColorOptions() {
        final String[] options = new String[ModSettings.ENCHANT_COLOR_NAMES.length - 1];
        for (int i = 0; i < options.length; ++i) {
            if (i == 0) {
                options[i] = I18n.format(ModSettings.ENCHANT_COLOR_NAMES[i], new Object[0]);
            }
            else {
                options[i] = "§" + ModSettings.ENCHANT_COLORS[i] + I18n.format(ModSettings.ENCHANT_COLOR_NAMES[i], new Object[0]);
            }
        }
        return options;
    }
    
    public int[] createValues() {
        final int[] values = new int[ModSettings.ENCHANT_COLOR_NAMES.length];
        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
        }
        return values;
    }
    
    public GuiAddWaypoint(final GuiScreen par1GuiScreen, final ModSettings par2ModSettings, final Waypoint point) {
        this.nameText = "";
        this.initial = "";
        this.yaw = "";
        this.dropDowns = new ArrayList<GuiDropDown>();
        this.fromSet = null;
        this.color = 0;
        this.dropped = false;
        this.parentGuiScreen = par1GuiScreen;
        this.guiModSettings = par2ModSettings;
        this.point = point;
        this.fromSet = Minimap.getCurrentWorld().current;
        this.sets = new GuiWaypointSets(this.fromSet, Minimap.getCurrentWorldID(), false);
    }
    
    @Override
    public void initGui() {
        InterfaceHandler.selectedId = -1;
        InterfaceHandler.draggingId = -1;
        super.buttonList.clear();
        super.buttonList.add(new MySmallButton(200, super.width / 2 - 155, super.height / 6 + 168, I18n.format("gui.xaero_confirm", new Object[0])));
        super.buttonList.add(new MySmallButton(201, super.width / 2 + 5, super.height / 6 + 168, I18n.format("gui.xaero_cancel", new Object[0])));
        this.nameTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 - 100, 82, 200, 20);
        this.xTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 - 109, 112, 50, 20);
        this.yTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 - 53, 112, 50, 20);
        this.zTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 + 3, 112, 50, 20);
        this.yawTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 + 59, 112, 50, 20);
        this.charTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 - 25, 142, 50, 20);
        if (this.point == null) {
            this.screenTitle = I18n.format("gui.xaero_new_waypoint", new Object[0]);
            this.nameTextField.setText(this.nameText);
            this.xTextField.setText("" + Minimap.myFloor(super.mc.thePlayer.posX));
            this.yTextField.setText("" + Minimap.myFloor(super.mc.thePlayer.posY));
            this.zTextField.setText("" + Minimap.myFloor(super.mc.thePlayer.posZ));
            this.yawTextField.setText("§8" + I18n.format("gui.xaero_yaw", new Object[0]));
            this.charTextField.setText("§8" + I18n.format("gui.xaero_initial", new Object[0]));
            this.color = (int)(Math.random() * (ModSettings.ENCHANT_COLORS.length - 1));
        }
        else {
            this.screenTitle = I18n.format("gui.xaero_edit_waypoint", new Object[0]);
            this.nameTextField.setText(this.point.getName());
            this.xTextField.setText("" + this.point.x);
            this.yTextField.setText("" + this.point.y);
            this.zTextField.setText("" + this.point.z);
            this.initial = this.point.symbol;
            if (this.point.rotation) {
                this.yaw = "" + this.point.yaw;
            }
            this.yawTextField.setText(this.yaw);
            this.charTextField.setText(this.initial);
            this.color = this.point.color;
        }
        if (!this.dropDowns.isEmpty()) {
            this.color = this.dropDowns.get(0).selected;
        }
        this.dropDowns.clear();
        final GuiDropDown colorSelect = new GuiDropDown(this.createColorOptions(), super.width / 2 - 60, 60, 120, this.color);
        this.dropDowns.add(colorSelect);
        this.dropDowns.add(new GuiDropDown(this.sets.options, super.width / 2 - 101, 38, 201, this.sets.currentSet));
        this.nameTextField.setFocused(true);
        Keyboard.enableRepeatEvents(true);
        this.updateConfirmButton();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) throws IOException {
        if (this.nameTextField.isFocused()) {
            if (par2 == 15) {
                this.nameTextField.setFocused(false);
                this.xTextField.setFocused(true);
            }
            this.nameTextField.textboxKeyTyped(par1, par2);
            if (this.initial.length() == 0 && this.nameTextField.getText().length() > 0) {
                this.initial = this.nameTextField.getText().substring(0, 1);
            }
        }
        else if (this.xTextField.isFocused()) {
            if (par2 == 15) {
                this.xTextField.setFocused(false);
                this.yTextField.setFocused(true);
            }
            this.xTextField.textboxKeyTyped(par1, par2);
        }
        else if (this.yTextField.isFocused()) {
            if (par2 == 15) {
                this.yTextField.setFocused(false);
                this.zTextField.setFocused(true);
            }
            this.yTextField.textboxKeyTyped(par1, par2);
        }
        else if (this.zTextField.isFocused()) {
            if (par2 == 15) {
                this.zTextField.setFocused(false);
                this.yawTextField.setFocused(true);
            }
            this.zTextField.textboxKeyTyped(par1, par2);
        }
        else if (this.yawTextField.isFocused()) {
            if (par2 == 15) {
                this.yawTextField.setFocused(false);
                this.charTextField.setFocused(true);
            }
            this.yawTextField.textboxKeyTyped(par1, par2);
            GuiMisc.checkField(this.yawTextField);
            this.yaw = this.yawTextField.getText();
        }
        else if (this.charTextField.isFocused()) {
            if (par2 == 15) {
                this.charTextField.setFocused(false);
                this.nameTextField.setFocused(true);
            }
            if (par2 != 57) {
                this.charTextField.textboxKeyTyped(par1, par2);
            }
            this.initial = this.charTextField.getText();
        }
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed(super.buttonList.get(0));
        }
        this.checkFields();
        this.updateConfirmButton();
        super.keyTyped(par1, par2);
    }
    
    private void updateConfirmButton() {
        super.buttonList.get(0).enabled = (this.nameTextField.getText().length() > 0 && this.initial.length() > 0 && this.xTextField.getText().length() > 0 && this.yTextField.getText().length() > 0 && this.zTextField.getText().length() > 0);
    }
    
    protected void checkFields() {
        GuiMisc.checkField(this.xTextField);
        GuiMisc.checkField(this.yTextField);
        GuiMisc.checkField(this.zTextField);
        this.initial = this.initial.toUpperCase();
        if (this.initial.length() > 1) {
            this.initial = this.initial.substring(0, 1);
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        for (final GuiDropDown d : this.dropDowns) {
            if (!d.closed && d.onDropDown(par1, par2)) {
                d.mouseClicked(par1, par2, par3);
                return;
            }
            d.closed = true;
        }
        for (final GuiDropDown d : this.dropDowns) {
            if (d.onDropDown(par1, par2)) {
                d.mouseClicked(par1, par2, par3);
                return;
            }
            d.closed = true;
        }
        super.mouseClicked(par1, par2, par3);
        this.nameTextField.mouseClicked(par1, par2, par3);
        this.xTextField.mouseClicked(par1, par2, par3);
        this.yTextField.mouseClicked(par1, par2, par3);
        this.zTextField.mouseClicked(par1, par2, par3);
        this.yawTextField.mouseClicked(par1, par2, par3);
        this.charTextField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void updateScreen() {
        if (super.mc.thePlayer == null) {
            super.mc.displayGuiScreen(null);
            return;
        }
        this.nameTextField.updateCursorCounter();
        this.xTextField.updateCursorCounter();
        this.yTextField.updateCursorCounter();
        this.zTextField.updateCursorCounter();
        this.yawTextField.updateCursorCounter();
        this.charTextField.updateCursorCounter();
        if (this.yawTextField.isFocused() || this.yaw.length() > 0) {
            this.yawTextField.setText(this.yaw);
        }
        else {
            this.yawTextField.setText("§8" + I18n.format("gui.xaero_yaw", new Object[0]));
        }
        if (this.charTextField.isFocused() || this.initial.length() > 0) {
            this.charTextField.setText(this.initial);
        }
        else {
            this.charTextField.setText("§8" + I18n.format("gui.xaero_initial", new Object[0]));
        }
        this.color = this.dropDowns.get(0).selected;
        if (this.sets.currentSet != this.dropDowns.get(1).selected) {
            this.sets.currentSet = this.dropDowns.get(1).selected;
            Minimap.getCurrentWorld().current = this.dropDowns.get(1).getSelectedOption();
            Minimap.updateWaypoints();
            try {
                XaeroMinimap.getSettings().saveWaypoints();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = super.mc.gameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                try {
                    this.guiModSettings.setOptionValue(((MySmallButton)par1GuiButton).returnModOptions(), 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                par1GuiButton.displayString = this.guiModSettings.getKeyBinding(ModOptions.getModOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                final int x = Integer.parseInt(this.xTextField.getText());
                final int y = Integer.parseInt(this.yTextField.getText());
                final int z = Integer.parseInt(this.zTextField.getText());
                final String name = this.nameTextField.getText();
                final Waypoint created = new Waypoint(x, y, z, name, this.initial, this.color);
                if (this.yaw.length() > 0) {
                    created.rotation = true;
                    created.yaw = Integer.parseInt(this.yawTextField.getText());
                }
                Minimap.waypoints.list.add(created);
                if (this.point != null) {
                    Minimap.getCurrentWorld().sets.get(this.fromSet).list.remove(this.point);
                }
                try {
                    XaeroMinimap.settings.saveWaypoints();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                super.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 201) {
                super.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 202) {
                this.color = (this.color + 1) % (ModSettings.ENCHANT_COLORS.length - 1);
            }
            if (super.mc.gameSettings.guiScale != var2) {
                final ScaledResolution res = new ScaledResolution(this.mc);
                final int var3 = res.getScaledWidth();
                final int var4 = res.getScaledHeight();
                this.setWorldAndResolution(super.mc, var3, var4);
            }
        }
    }
    
    public List getButtons() {
        return super.buttonList;
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(super.fontRendererObj, this.screenTitle, super.width / 2, 20, 16777215);
        this.nameTextField.drawTextBox();
        this.xTextField.drawTextBox();
        this.yTextField.drawTextBox();
        this.zTextField.drawTextBox();
        this.yawTextField.drawTextBox();
        this.charTextField.drawTextBox();
        if (this.dropped) {
            super.drawScreen(0, 0, par3);
        }
        else {
            super.drawScreen(par1, par2, par3);
        }
        this.dropped = false;
        for (int k = 0; k < this.dropDowns.size(); ++k) {
            if (!this.dropDowns.get(k).closed) {
                this.dropped = true;
            }
            this.dropDowns.get(k).drawButton(par1, par2);
        }
    }
}
