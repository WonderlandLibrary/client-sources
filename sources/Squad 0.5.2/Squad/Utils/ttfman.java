package Squad.Utils;

import java.awt.Font;

public class ttfman {
	
	private static ttfman theTTFManager = new ttfman();
	
	public static ttfman getInstance(){
		return theTTFManager;
	}
	
	private TTFRenderer panelTitleFont = null;
	private TTFRenderer buttonExtraFont = null;
	private TTFRenderer standardFont = null;
	private TTFRenderer modListFont = null;
	private TTFRenderer chatFont = null;
	private TTFRenderer waypointFont = null;
	private TTFRenderer largeFont = null;
	private TTFRenderer xLargeFont = null;
	private TTFRenderer LogoMainFont = null;
	
	
	
	public TTFRenderer getStandardFont(){
		if(standardFont == null){
			standardFont = new TTFRenderer("Thruster Regular", Font.PLAIN, 19);
		}
		
		return standardFont;
	}
	
	public TTFRenderer getPanelTitleFont(){
		if(panelTitleFont == null){
			panelTitleFont = new TTFRenderer("Arial", Font.PLAIN, 23);
		}
		return panelTitleFont;
	}
	
	public TTFRenderer getButtonExtraFont(){
		if(buttonExtraFont == null){
			buttonExtraFont = new TTFRenderer("Arial", Font.PLAIN, 16);
		}
		return buttonExtraFont;
	}
	
	public TTFRenderer getModListFont(){
		if(modListFont == null){
			modListFont = new TTFRenderer("Arial", Font.PLAIN, 20);
		}
		return modListFont;
	}
	
	
	public TTFRenderer getChatFont(){
		if(chatFont == null){
			chatFont = new TTFRenderer("Arial", Font.PLAIN, 19);
		}
		return chatFont;
	}
	
	public TTFRenderer getWaypointFont(){
		if(waypointFont == null){
			waypointFont = new TTFRenderer("Arial", Font.PLAIN, 40);
		}
		return waypointFont;
	}
	
	public TTFRenderer getLargeFont(){
		if(largeFont == null){
			largeFont = new TTFRenderer("Arial", Font.PLAIN, 30);
		}
		return largeFont;
	}
	
	public TTFRenderer getLargeItalicFont(){
		if(largeFont == null){
			largeFont = new TTFRenderer("Arial", Font.ITALIC, 30);
		}
		return largeFont;
	}
	
	public TTFRenderer getLogoMainFont(){
		if(LogoMainFont == null){
			LogoMainFont = new TTFRenderer("Arial", Font.PLAIN, 100);
		}
		return LogoMainFont;
	}
	
	public TTFRenderer getXLargeFont(){
		if(xLargeFont == null){
			xLargeFont = new TTFRenderer("Arial", Font.PLAIN, 45);
		}
		return xLargeFont;
	}
	

}