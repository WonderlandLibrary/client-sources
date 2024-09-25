package none.module.modules.render;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.event.events.EventPreMotionUpdate;
import none.fontRenderer.xdolf.Fonts;
import none.module.Category;
import none.module.Checker;
import none.module.Module;
import none.utils.GLUtil;
import none.utils.RenderingUtil;
import none.utils.render.TTFFontRenderer;

public class HUD extends Module{
	
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final ResourceLocation None = new ResourceLocation("textures/None.png");
    private final ResourceLocation NoneV2 = new ResourceLocation("textures/NoneV2.png");
    private final ResourceLocation NoneLogo3 = new ResourceLocation("textures/NONELOGO3.png");
    
    public static int ALx = 0,ALy = 0;
    
	public HUD() {
		super("HUD", "HUD", Category.RENDER, Keyboard.KEY_NONE);
		toggle();
	}
	
	private int anima = 0;
	boolean extended = true;
	
	public int getAnima() {
		return anima;
	}
	
	public void setAnima(int anima) {
		this.anima = anima;
	}
	
	public String getItem() {
		return mc.thePlayer.getHeldItem() != null ? (String) mc.thePlayer.getHeldItem().getDisplayName() : "NoItem";
	}

	@Override
	@RegisterEvent(events = {Event2D.class, Event3D.class, EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + HUDEditer.fontmode.getSelected());
		
		if (event instanceof Event2D) {
			if (HUDEditer.Model.getObject()) {
				Model();
			}
			
			if (HUDEditer.Hotbar.getObject()) {
				HotBar();
			}
			if (HUDEditer.ArrayList.getObject()) {
				ArrayListHUD();
			}
			if (HUDEditer.WaterMark.getObject()) {
				if (HUDEditer.logomodes.getSelected().equalsIgnoreCase("Text")) {
					Hud();
				}else if (HUDEditer.logomodes.getSelected().equalsIgnoreCase("Logo")) {
					drawLogo();
				}else if (HUDEditer.logomodes.getSelected().equalsIgnoreCase("Logo2")) {
					drawLogo2();
				}else if (HUDEditer.logomodes.getSelected().equalsIgnoreCase("Logo3")) {
					drawLogo3();
				}
			}
		}
		
		if (event instanceof Event3D) {
			if (HUDEditer.Fasion.getObject()) {
				FasionHUD();
			}
		}
	}
	
	public void Hud() {
		FontRenderer fontRenderer = mc.fontRendererObj;
        FontRenderer roboto18 = Fonts.roboto18;
        FontRenderer roboto15 = Fonts.roboto15;
        FontRenderer roboto22 = Fonts.roboto22;
        TTFFontRenderer sigmaFont = Client.fm.getFont("SFM 12");
		TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
		TTFFontRenderer verdanaFont = Client.fm.getFont("Verdana Bold 16");
		TTFFontRenderer MachineCFont = Client.fm.getFont("MachineC 19");
		
		int renderColor = ClientColor.getColor();
		
		if (HUDEditer.fontmode.getObject() == 1) {
        	//Roboto18
        	fontRenderer = roboto18;
        }else if (HUDEditer.fontmode.getObject() == 2) {
        	//Roboto15
        	fontRenderer = roboto15;
        }else if (HUDEditer.fontmode.getObject() == 3) {
        	//Roboto22
        	fontRenderer = roboto22;
        }
        
        ScaledResolution res = new ScaledResolution(mc);

        if (HUDEditer.logomodes.getSelected().equalsIgnoreCase("Text")) {
        	Gui.drawRect((res.getScaledWidth() - res.getScaledWidth()) + 0, (res.getScaledHeight() - res.getScaledHeight()) + 0, (fontRenderer.FONT_HEIGHT * 6) + 2, fontRenderer.FONT_HEIGHT * 2 + 3, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
        	Gui.drawRect((res.getScaledWidth() - res.getScaledWidth()) + 2, (res.getScaledHeight() - res.getScaledHeight()) + 2, (fontRenderer.FONT_HEIGHT * 6) + 0, fontRenderer.FONT_HEIGHT * 2 + 1, Color.BLACK.getRGB());
	    }
		
        GL11.glScaled(2, 2, 2);
        if (HUDEditer.logomodes.getSelected().equalsIgnoreCase("Text")) {
        	MachineCFont.drawString(Client.instance.name, 2, 2, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
        }
        GL11.glScaled(0.5, 0.5, 0.5);
        
	}
	
	public void HotBar() {
        if (HUDEditer.MovingHotbar.getObject()) {
        	if (anima <= 340 && extended) {
	        	setAnima(getAnima() + 2);
	        }
	        
	        if (anima >= 0 && !extended) {
	        	setAnima(getAnima() - 2);
	        }
	        
	        if (anima < 0 && !extended) {
	        	setAnima(0);
	        	extended = !extended;
	        }
	        
	        if (anima > 340) {
	        	setAnima(340);
	        	extended = !extended;
	        }
        }
        
        FontRenderer fontRenderer = mc.fontRendererObj;
        FontRenderer roboto18 = Fonts.roboto18;
        FontRenderer roboto15 = Fonts.roboto15;
        FontRenderer roboto22 = Fonts.roboto22;
        TTFFontRenderer sigmaFont = Client.fm.getFont("SFM 12");
		TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
		TTFFontRenderer verdanaFont = Client.fm.getFont("Verdana Bold 16");
		TTFFontRenderer MachineCFont = Client.fm.getFont("MachineC 19");
        
        if (HUDEditer.fontmode.getObject() == 1) {
        	//Roboto18
        	fontRenderer = roboto18;
        }else if (HUDEditer.fontmode.getObject() == 2) {
        	//Roboto15
        	fontRenderer = roboto15;
        }else if (HUDEditer.fontmode.getObject() == 3) {
        	//Roboto22
        	fontRenderer = roboto22;
        }
        
        int renderColor = ClientColor.getColor();
        
        ScaledResolution res = new ScaledResolution(mc);
        
        int blackBarHeight = fontRenderer.FONT_HEIGHT * 2 + 4;
        
        if (HUDEditer.ShowHotbar.getObject()) {
        	Gui.drawRect(0, res.getScaledHeight() - blackBarHeight, res.getScaledWidth(), res.getScaledHeight(), GLUtil.toRGBA(new Color(0, 0, 0, 150)));
        }
        
        if (HUDEditer.ShowMovingHotbar.getObject()) {
        	Gui.drawRect(0 + anima, res.getScaledHeight() - 2, res.getScaledWidth() - anima, res.getScaledHeight(), !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
        	Gui.drawRect(0 + anima, res.getScaledHeight() - blackBarHeight, res.getScaledWidth() - anima, res.getScaledHeight() - blackBarHeight - 2, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
        }
		
		int initialSize = 0;
        double currSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

        int fpsWidth = fontRenderer.drawString("FPS: " + mc.getDebugFPS(), initialSize * 2 + 2, res.getScaledHeight() - fontRenderer.FONT_HEIGHT - 2, -1, false);
        fpsWidth = Math.max(fpsWidth, fontRenderer.drawString(String.format("BPS: %.2f", currSpeed), initialSize * 2 + 2, res.getScaledHeight() - fontRenderer.FONT_HEIGHT * 2 - 2, -1, false));
        
        LocalDateTime now = LocalDateTime.now();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);
        String facing = "" + mc.thePlayer.getHorizontalFacing();
        facing = Character.toUpperCase(facing.toLowerCase().charAt(0)) + facing.toLowerCase().substring(1);
        fontRenderer.drawString(date, res.getScaledWidth() - fontRenderer.getStringWidth(date), res.getScaledHeight() - fontRenderer.FONT_HEIGHT - 2, -1, false);
        fontRenderer.drawString(time, res.getScaledWidth() - fontRenderer.getStringWidth(time), res.getScaledHeight() - fontRenderer.FONT_HEIGHT * 2 - 2, -1, false);
        int timerWidth = fontRenderer.drawString("Timer:" + (float)mc.timer.timerSpeed, fpsWidth + 3, res.getScaledHeight() - fontRenderer.FONT_HEIGHT * 2 - 2, -1, false);
        int tickWidth = fontRenderer.drawString("Tick:" + mc.thePlayer.ticksExisted, fpsWidth + 3, res.getScaledHeight() - fontRenderer.FONT_HEIGHT - 2, -1, false);
        fontRenderer.drawString("Pos:[X=" + (int)mc.thePlayer.posX + ", Y=" + (int)mc.thePlayer.posY + ", Z=" + (int)mc.thePlayer.posZ + "]", tickWidth + 3, res.getScaledHeight() - fontRenderer.FONT_HEIGHT - 2, -1, false);
        int dicWitdth = fontRenderer.drawString("Direction:" + facing, timerWidth + 3, res.getScaledHeight() - fontRenderer.FONT_HEIGHT * 2 - 2, -1, false);
    }
	
	public void Model() {
		FontRenderer fontRenderer = mc.fontRendererObj;
        FontRenderer roboto18 = Fonts.roboto18;
        FontRenderer roboto15 = Fonts.roboto15;
        FontRenderer roboto22 = Fonts.roboto22;
        TTFFontRenderer sigmaFont = Client.fm.getFont("SFM 12");
		TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
		TTFFontRenderer verdanaFont = Client.fm.getFont("Verdana Bold 16");
		TTFFontRenderer MachineCFont = Client.fm.getFont("MachineC 19");
		ScaledResolution res = new ScaledResolution(mc);
		
		int renderColor = ClientColor.getColor();
		
        if (HUDEditer.fontmode.getObject() == 1) {
        	//Roboto18
        	fontRenderer = roboto18;
        }else if (HUDEditer.fontmode.getObject() == 2) {
        	//Roboto15
        	fontRenderer = roboto15;
        }else if (HUDEditer.fontmode.getObject() == 3) {
        	//Roboto22
        	fontRenderer = roboto22;
        }
		
		if (HUDEditer.Model.getObject()) {
        	drawEntityOnScreen(15, res.getScaledHeight() / 2 - 1, 25, -25, 0, mc.thePlayer);
        	String ItemName = "Item:" + getItem();
        	Gui.drawRect(0, res.getScaledHeight() / 2 + 1, fontRenderer.getStringWidth(ItemName) + 3, (res.getScaledHeight() / 2) + fontRenderer.FONT_HEIGHT + 3, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100));
        	Gui.drawRect(1, res.getScaledHeight() / 2 + 2, fontRenderer.getStringWidth(ItemName) + 2, (res.getScaledHeight() / 2) + fontRenderer.FONT_HEIGHT + 2, Color.BLACK.getRGB());
        	if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Minecraft")) {
        		fontRenderer.drawString(ItemName, 2,res.getScaledHeight() / 2 + 4, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100), false);
        	}else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Roboto18")) {
        		fontRenderer.drawString(ItemName, 2,res.getScaledHeight() / 2 + 1, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100), false);
	        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Roboto15")) {
	        	fontRenderer.drawString(ItemName, 2,res.getScaledHeight() / 2 + 1, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100), false);
	        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Roboto22")) {
	        	fontRenderer.drawString(ItemName, 2,res.getScaledHeight() / 2 + 1, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100), false);
	        }else {
	        	fontRenderer.drawString(ItemName, 2,res.getScaledHeight() / 2 + 4, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(100), false);
	        }
        }
	}
	
	public void ArrayListHUD() {
		FontRenderer fr = mc.fontRendererObj;
		
		ScaledResolution res = new ScaledResolution(mc);
		
		TTFFontRenderer sigmaFont = Client.fm.getFont("SFB 8");
		TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
		TTFFontRenderer verdanaFont = Client.fm.getFont("Verdana Bold 16");
		TTFFontRenderer MachineCFont = Client.fm.getFont("MachineC 19");
		TTFFontRenderer BebasNeueFont = Client.fm.getFont("BebasNeue");
		int x = res.getScaledWidth() - HUD.ALx;
		int y = HUD.ALy;
		
		//Sorted by name
		List<Module> mods = Client.instance.moduleManager.getModules();
		Collections.sort(mods, new Comparator<Module>() {
			public int compare(Module m1, Module m2) {
				if (HUDEditer.fontmode.getObject() == 0) {
					//MC
					if (fr.getStringWidth(m1.getDisplayName()) > fr.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (fr.getStringWidth(m1.getDisplayName()) < fr.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getObject() == 1) {
					//Roboto18
					if (Fonts.roboto18.getStringWidth(m1.getDisplayName()) > Fonts.roboto18.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (Fonts.roboto18.getStringWidth(m1.getDisplayName()) < Fonts.roboto18.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getObject() == 2) {
					//Roboto15
					if (Fonts.roboto15.getStringWidth(m1.getDisplayName()) > Fonts.roboto15.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (Fonts.roboto15.getStringWidth(m1.getDisplayName()) < Fonts.roboto15.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getObject() == 3) {
					//Roboto22
					if (Fonts.roboto22.getStringWidth(m1.getDisplayName()) > Fonts.roboto22.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (Fonts.roboto22.getStringWidth(m1.getDisplayName()) < Fonts.roboto22.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Sigma")) {
					//Sigma
					if (sigmaFont.getStringWidth(m1.getDisplayName()) > sigmaFont.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (sigmaFont.getStringWidth(m1.getDisplayName()) < sigmaFont.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Jigsaw")) {
					//Jigsaw
					if (jigsawFont.getStringWidth(m1.getDisplayName()) > jigsawFont.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (jigsawFont.getStringWidth(m1.getDisplayName()) < jigsawFont.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Verdana")) {
					//Verdana
					if (verdanaFont.getStringWidth(m1.getDisplayName()) > verdanaFont.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (verdanaFont.getStringWidth(m1.getDisplayName()) < verdanaFont.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("MachineC")) {
					//MachineC
					if (MachineCFont.getStringWidth(m1.getDisplayName()) > MachineCFont.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (MachineCFont.getStringWidth(m1.getDisplayName()) < MachineCFont.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("BebasNeue")) {
					//BebasNeue
					if (BebasNeueFont.getStringWidth(m1.getDisplayName()) > BebasNeueFont.getStringWidth(m2.getDisplayName())) {
						return -1;
					}
					
					if (BebasNeueFont.getStringWidth(m1.getDisplayName()) < BebasNeueFont.getStringWidth(m2.getDisplayName())) {
						return 1;
					}
				}
				return 0;
			}
		});
		
		
		
		AtomicInteger offset = new AtomicInteger(0);
		
		
		//Arraylist
		for (Module m : mods) {
			if (m.getClass().equals(Checker.class)) {
				continue;
			}
			if (m.getAnim() <= -1) {
				m.setAnim(-1);
				m.setshowed(false);
			}
			
			if (m.isshowed()) {
				String text = m.getDisplayName();

				int renderColor = ClientColor.getColor();
				GlStateManager.pushMatrix();
				if (HUDEditer.fontmode.getObject() == 0) {
					if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
					
					if (m.getAnim() < fr.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > fr.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(fr.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + fr.FONT_HEIGHT + 1, x, y + offset.get() + fr.FONT_HEIGHT + 2, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get(), (x - m.getAnim()) - 6 + 1, y + offset.get() + fr.FONT_HEIGHT + 2, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 1, x, y + offset.get() + fr.FONT_HEIGHT + 1, Color.BLACK.getRGB());
					fr.drawString(text, (x - 4) - m.getAnim(), y + offset.get(), renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 3, x, y + offset.get() + fr.FONT_HEIGHT + 1, renderColor);
					offset.addAndGet(fr.FONT_HEIGHT + 2);
				}else if (HUDEditer.fontmode.getObject() == 1) {
					if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
					//Roboto18
					if (m.getAnim() < Fonts.roboto18.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > Fonts.roboto18.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(Fonts.roboto18.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + Fonts.roboto18.FONT_HEIGHT - 1, x, y + offset.get() + Fonts.roboto18.FONT_HEIGHT, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get(), (x - m.getAnim()) - 6 + 1, y + offset.get() + Fonts.roboto18.FONT_HEIGHT, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 1, x, y + offset.get() + Fonts.roboto18.FONT_HEIGHT - 1, Color.BLACK.getRGB());
					Fonts.roboto18.drawString(text, (x - 3) - m.getAnim(), y + offset.get() - 2, renderColor);
					Gui.drawRect(x - 3, y + offset.get() - 3, x, y + offset.get() + Fonts.roboto18.FONT_HEIGHT, renderColor);
					offset.addAndGet(Fonts.roboto18.FONT_HEIGHT);
		        }else if (HUDEditer.fontmode.getObject() == 2) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
					//Roboto15
					if (m.getAnim() < Fonts.roboto15.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > Fonts.roboto15.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(Fonts.roboto15.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + Fonts.roboto15.FONT_HEIGHT - 1, x, y + offset.get() + Fonts.roboto15.FONT_HEIGHT, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() - 3, (x - m.getAnim()) - 6 + 1, y + offset.get() + Fonts.roboto15.FONT_HEIGHT, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 3, x, y + offset.get() + Fonts.roboto15.FONT_HEIGHT - 1, Color.BLACK.getRGB());
					Fonts.roboto15.drawString(text, (x - 4) - m.getAnim(), y + offset.get() - 2, renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 3, x, y + offset.get() + Fonts.roboto15.FONT_HEIGHT, renderColor);
					offset.addAndGet(Fonts.roboto15.FONT_HEIGHT + 2);
		        }else if (HUDEditer.fontmode.getObject() == 3) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
					//Roboto22
					if (m.getAnim() < Fonts.roboto22.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > Fonts.roboto22.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(Fonts.roboto22.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + Fonts.roboto22.FONT_HEIGHT - 1, x, y + offset.get() + Fonts.roboto22.FONT_HEIGHT, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() - 1, (x - m.getAnim()) - 6 + 1, y + offset.get() + Fonts.roboto22.FONT_HEIGHT, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 1, x, y + offset.get() + Fonts.roboto22.FONT_HEIGHT - 1, Color.BLACK.getRGB());
					Fonts.roboto22.drawStringWithShadow(text, (x - 4) - m.getAnim(), y + offset.get() - 3, renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 1, x, y + offset.get() + Fonts.roboto22.FONT_HEIGHT, renderColor);
					offset.addAndGet(Fonts.roboto22.FONT_HEIGHT);
		        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Sigma")) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
		        	//Sigma
		        	int scale = 10;
					if (m.getAnim() < sigmaFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > sigmaFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim((int)sigmaFont.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + (int)jigsawFont.getHeight(text) + 1, x, y + offset.get() + (int)sigmaFont.getHeight(text) + 3, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get(), (x - m.getAnim()) - 6 + 1, y + offset.get() + (int)sigmaFont.getHeight(text) + 3, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 1, x, y + offset.get() + (int)sigmaFont.getHeight(text) + 2, Color.BLACK.getRGB());
					sigmaFont.drawString(text, (x - 4) - m.getAnim(), y + offset.get() + 2, renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 1, x, offset.get() + (int)sigmaFont.getHeight(text) + 3, renderColor);
					offset.addAndGet((int)sigmaFont.getHeight(text) + 3);
		        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Jigsaw")) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
		        	//Jigsaw
					if (m.getAnim() < jigsawFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > jigsawFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim((int)jigsawFont.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + (int)jigsawFont.getHeight(text) + 1, x, y + offset.get() + (int)jigsawFont.getHeight(text) + 2, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() - 1, (x - m.getAnim()) - 6 + 1, y + offset.get() + (int)jigsawFont.getHeight(text) + 2, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 2, x, y + offset.get() + (int)jigsawFont.getHeight(text) + 1, Color.BLACK.getRGB());
					jigsawFont.drawString(text, (x - 4) - m.getAnim(), y + offset.get(), renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 2, x, y + offset.get() + (int)jigsawFont.getHeight(text) + 1, renderColor);
					offset.addAndGet((int)jigsawFont.getHeight(text) + 3);
		        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("Verdana")) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
		        	//Verdana
					if (m.getAnim() < verdanaFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > verdanaFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim((int)verdanaFont.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + (int)jigsawFont.getHeight(text) + 3, x, y + offset.get() + (int)verdanaFont.getHeight(text) + 3, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() - 1, (x - m.getAnim()) - 6 + 1, y + offset.get() + (int)verdanaFont.getHeight(text) + 3, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 2, x, y + offset.get() + (int)verdanaFont.getHeight(text) + 2, Color.BLACK.getRGB());
					verdanaFont.drawString(text, (x - 4) - m.getAnim(), y + offset.get(), renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 3, x, y + offset.get() + (int)verdanaFont.getHeight(text), renderColor);
			        offset.addAndGet((int)verdanaFont.getHeight(text) + 3);
		        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("MachineC")) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
		        	//MachineC
					if (m.getAnim() < MachineCFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > MachineCFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim((int)MachineCFont.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + (int)MachineCFont.getHeight(text) + 1, x, y + offset.get() + (int)MachineCFont.getHeight(text) + 2, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() - 1, (x - m.getAnim()) - 6 + 1, y + offset.get() + (int)MachineCFont.getHeight(text) + 2, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 2, x, y + offset.get() + (int)MachineCFont.getHeight(text) + 1, Color.BLACK.getRGB());
						MachineCFont.drawString(text, (x - 4) - m.getAnim(), y + offset.get(), renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 2, x, y + offset.get() + (int)MachineCFont.getHeight(text) + 1, renderColor);
					offset.addAndGet((int)MachineCFont.getHeight(text) + 3);
		        }else if (HUDEditer.fontmode.getSelected().equalsIgnoreCase("BebasNeue")) {
		        	if (ClientColor.rainbow.getObject()) {
						renderColor = ClientColor.rainbow(100 + (offset.get() * HUDEditer.ArrayListSpeed.getInteger()));
					}
		        	//BebasNeue
					if (m.getAnim() < BebasNeueFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim(m.getAnim() + 4);
					}
					
					if (m.getAnim() > -1 && !m.isEnabled()) {
						m.setAnim(m.getAnim() - 3);
					}
					
					if (m.getAnim() > BebasNeueFont.getStringWidth(text) && m.isEnabled()) {
						m.setAnim((int)BebasNeueFont.getStringWidth(text));
					}
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() + (int)BebasNeueFont.getHeight(text) + 1, x, y + offset.get() + (int)BebasNeueFont.getHeight(text) + 2, renderColor);	
					Gui.drawRect((x - m.getAnim()) - 6, y + offset.get() - 1, (x - m.getAnim()) - 6 + 1, y + offset.get() + (int)BebasNeueFont.getHeight(text) + 2, renderColor);
						Gui.drawRect((x - m.getAnim()) - 5, y + offset.get() - 2, x, y + offset.get() + (int)BebasNeueFont.getHeight(text) + 1, Color.BLACK.getRGB());
						BebasNeueFont.drawString(text, (x - 4) - m.getAnim(), y + offset.get(), renderColor);
					Gui.drawRect((x - 3), y + offset.get() - 2, x, y + offset.get() + (int)BebasNeueFont.getHeight(text) + 1, renderColor);
					offset.addAndGet((int)BebasNeueFont.getHeight(text) + 3);
		        }
				GlStateManager.popMatrix();
			}
		}
	}
	
	private void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        GlStateManager.pushMatrix();
        float var8 = 1.0F / textureWidth;
        float var9 = 1.0F / textureHeight;
        Tessellator var10 = Tessellator.getInstance();
        WorldRenderer var11 = var10.getWorldRenderer();
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, var9, width, height, textureWidth, textureHeight);
        GlStateManager.popMatrix();
    }
	
	private void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
//        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
//        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
//        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
//        ent.rotationYawHead = ent.rotationYaw;
//        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	
	public void FasionHUD() {
		drawHat(mc.thePlayer, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		drawPaticle(mc.thePlayer, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
	}
	
	public void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}
	
	public void rotate(float angle, double x, double y, double z) {
		GL11.glRotated(angle, x, y, z);
		GL11.glTranslated(x, y, z);
	}
	public void Translated(double x, double y, double z) {
		GL11.glTranslated(x, y, z);
	}
	public void Translatef(float x, float y, float z) {
		GL11.glTranslatef(x, y, z);
	}
	
	public void drawHat(EntityLivingBase entity, int color) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks + entity.getEyeHeight() * 1.2;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		double height = 0.1;
		Vec3 vec = new Vec3(x - width/2, y, z - width/2);
        Vec3 vec2 = new Vec3(x + width/2, y + height, z + width/2);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingUtil.enableGL2D();
        RenderingUtil.pre3D();
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 2);
        RenderingUtil.glColor(color);
        rotate(mc.thePlayer.rotationYawHead, 0, -1, 0);
        rotate(mc.thePlayer.rotationPitchHead, 1, 0, 0);
        RenderingUtil.drawOutlinedBoundingBox(new AxisAlignedBB(
        		vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY, vec.zCoord - renderManager.renderPosZ,
        		vec2.xCoord - renderManager.renderPosX, vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        RenderingUtil.disableGL2D();
    }
	
	public void drawPaticle(EntityLivingBase entity, int color) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks + entity.getEyeHeight() * 1.2;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		double height = 0.1;
		Vec3 vec = new Vec3(x - width/2, y, z - width/2);
        Vec3 vec2 = new Vec3(x + width/2, y + height, z + width/2);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingUtil.enableGL2D();
        RenderingUtil.pre3D();
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 2);
        RenderingUtil.glColor(color);
        Translated(0, 1, 0);
        rotate(mc.thePlayer.rotationYawHead, 0, -1, 0);
        rotate(getAnima(), 0, 0, 0);
        RenderingUtil.drawOutlinedBoundingBox(new AxisAlignedBB(
        		vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY, vec.zCoord - renderManager.renderPosZ,
        		vec2.xCoord - renderManager.renderPosX, vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
        rotate(getAnima(), 0, 0, 0);
        RenderingUtil.drawOutlinedBoundingBox(new AxisAlignedBB(
        		vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY, vec.zCoord - renderManager.renderPosZ,
        		vec2.xCoord - renderManager.renderPosX, vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        RenderingUtil.disableGL2D();
    }
	
	public void drawLogo() {
		GL11.glColor4d(1, 1, 1, 1);
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		mc.getTextureManager().bindTexture(None);
		drawModalRectWithCustomSizedTexture(0, -20, 0, 0, 100, 75, 100, 75);
		GlStateManager.disableBlend();
	}
	
	public void drawLogo2() {
		GL11.glColor4d(1, 1, 1, 1);
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		mc.getTextureManager().bindTexture(NoneV2);
		drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 100, 44, 100, 44);
		GlStateManager.disableBlend();
	}
	
	public void drawLogo3() {
		GL11.glColor4d(1, 1, 1, 1);
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		mc.getTextureManager().bindTexture(NoneLogo3);
		drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 105, 90, 105, 90);
		GlStateManager.disableBlend();
	}
	
	public static void drawWaveString(String str, TTFFontRenderer font, float x, float y){
		float posX = x;
		for(int i = 0; i < str.length(); i++){
			String ch = str.charAt(i) + "";
			font.drawStringWithShadow(ch, posX, y,effect(i*3500000L, 1f,150).getRGB());
			posX += font.getStringWidth(ch)/1.3F;
			if( ch.equalsIgnoreCase("r") || ch.equalsIgnoreCase("i")){
				posX -= 0.6f;
			}else if(ch.equalsIgnoreCase("a") || ch.equalsIgnoreCase("t") || ch.equalsIgnoreCase("l") || ch.equalsIgnoreCase("1")){
				posX -= 0.3f;
			}
		}
	}
	
	public static Color effect(long offset, float brightness, int speed) {
		float hue = (float) (System.nanoTime() + (offset * speed)) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, 1F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getGreen()/255.0f,c.getBlue()/255.0F,c.getRed()/255.0F, c.getAlpha()/255.0F);
	}
}
