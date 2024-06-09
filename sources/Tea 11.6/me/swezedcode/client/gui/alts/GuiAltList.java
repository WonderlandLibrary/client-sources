package me.swezedcode.client.gui.alts;

import java.io.IOException;

import me.swezedcode.client.gui.other.BorderButton;
import me.swezedcode.client.manager.managers.file.files.Alts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;


public class GuiAltList extends GuiScreen implements GuiYesNoCallback {
	private GuiScreen parentScreen;
	public static String dispErrorString = "";
	public boolean deleteMenuOpen = false;
    private static final ResourceLocation NiGHT = new ResourceLocation("textures/gui/title/background/summer-photographer-pier-adventure.png");
	//summer-photographer-pier-adventure
	public GuiAltList(GuiScreen screen) {
		this.parentScreen = screen;
	}

	public void onGuiClosed() {
		me.swezedcode.client.manager.Manager.getManager().getFileManager().saveAlts();
		super.onGuiClosed();
	}
	
	private SlotAlt tSlot;
	
	public void initGui() {
		buttonList.clear();
		buttonList.add(new BorderButton(1, width / 2 - 100, height - 47, 66, 20, "Add"));
		buttonList.add(new BorderButton(2, width / 2 - 33, height - 47, 65, 20, "Login"));
		buttonList.add(new BorderButton(3, width / 2 + 32, height - 47, 69, 20, "Remove"));
		buttonList.add(new BorderButton(4, width / 2 - 100, height - 26, 99,20, "Back"));
		buttonList.add(new BorderButton(5, width / 2 , height - 26, 100, 20, "Direct Login"));
		
		tSlot = new SlotAlt(this.mc, this);
		tSlot.registerScrollButtons(7, 8);
	}
	
	@Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.tSlot.func_148135_f();
    }
	
	@Override
	public void confirmClicked(boolean flag, int i1) {
		super.confirmClicked(flag, i1);
		if(deleteMenuOpen) {
			deleteMenuOpen = false;
			
			if(flag) {
				Manager.altList.remove(i1);
				me.swezedcode.client.manager.Manager.getManager().getFileManager().saveAlts();
			}
			
			mc.displayGuiScreen(this);
		}
	}
	
	public void actionPerformed(GuiButton button) {
		try {
			super.actionPerformed(button);
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		if(button.id == 1) {
			GuiAltAdd gaa = new GuiAltAdd(this);
			mc.displayGuiScreen(gaa);
		}
		if(button.id == 2) {
			try {
				Alt a1 = Manager.altList.get(tSlot.getSelected());
				if(a1.isPremium()) {
					try {
						dispErrorString = "";
						PLoginUtils.login(a1.getUsername(), a1.getPassword());
					} catch(Exception error) {
						dispErrorString = "".concat("\247cBad Login \2477(").concat(a1.getUsername()).concat(")");
					}
				} else {
					this.mc.session = new Session(a1.getUsername(), "-", "swag", Session.Type.LEGACY.toString());
					dispErrorString = "";
				}
			} catch(Exception e) {}
		}
		if(button.id == 3) {
			try {
				String s1 = "Are you sure you want to delete the alt: " + "\"" + Manager.altList.get(tSlot.getSelected()).getUsername() + "\"" + "?";
				String s3 = "Delete";
				String s4 = "Cancel";
				GuiYesNo guiyesno = new GuiYesNo(this, s1, "", s3, s4, tSlot.getSelected());
				deleteMenuOpen = true;
				mc.displayGuiScreen(guiyesno);
			} catch(Exception e) {}
		}
		if(button.id == 4) {
			mc.displayGuiScreen(parentScreen);
		}
		if(button.id == 5) {
			GuiDirectLogin gdl = new GuiDirectLogin(this);
			mc.displayGuiScreen(gdl);
		}
	}
	
	public void updateScreen() {
		super.updateScreen();
	}
	
	public void drawScreen(int i, int j, float f) {
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		tSlot.drawScreen(i, j, f);
		drawCenteredString(fontRendererObj, "Alts: \2477" + Manager.altList.size(), width / 2, 13, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow("Username: \2477" + mc.session.getUsername(), 3, 3, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow(dispErrorString, 3, 13, 0xFFFFFF);
		super.drawScreen(i, j, f);
	}

	public FontRenderer getLocalFontRenderer() {
		return this.fontRendererObj;
	}
}
