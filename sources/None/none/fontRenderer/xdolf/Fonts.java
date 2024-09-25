package none.fontRenderer.xdolf;

import java.awt.Font;

public class Fonts
{
	public static XFontRenderer roboto22;
	public static XFontRenderer roboto18;
	public static XFontRenderer roboto15;
	
	public static void loadFonts()
	{
		roboto22 = new XFontRenderer(new Font("Roboto", Font.PLAIN, 44),
			true, 8);
		roboto18 = new XFontRenderer(new Font("Roboto", Font.PLAIN, 36),
			true, 8);
		roboto15 = new XFontRenderer(new Font("Roboto", Font.PLAIN, 30),
			true, 8);
	}
}