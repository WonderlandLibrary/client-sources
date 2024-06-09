package lunadevs.luna.font;

import java.awt.Font;

public class Fonts
{
	public static MistFontRenderer font22;
	public static MistFontRenderer font18;
	public static MistFontRenderer font15;
	
	public static void loadFonts()
	{
		font22 = new MistFontRenderer(new Font("URWGothicLBook", Font.PLAIN, 44),
			true, 8);
		font18 = new MistFontRenderer(new Font("URWGothicLBook", Font.PLAIN, 36),
			true, 8);
		font15 = new MistFontRenderer(new Font("URWGothicLBook", Font.PLAIN, 30),
			true, 8);
	}
}