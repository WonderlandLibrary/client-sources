using System;
using System.Runtime.InteropServices;
using Wave.Cmr.Win32API;

namespace Wave.Cmr.Font
{
	// Token: 0x02000026 RID: 38
	public class cmr_font
	{
		// Token: 0x06000132 RID: 306 RVA: 0x000049E4 File Offset: 0x00002BE4
		public unsafe static void SetConsoleFont(string fontName, short fontSizeX = 9, short fontSizeY = 19, int fontWeight = 0)
		{
			IntPtr stdHandle = Win32.GetStdHandle(Win32.STD_OUTPUT_HANDLE);
			if (stdHandle != Win32.INVALID_HANDLE_VALUE)
			{
				Win32.CONSOLE_FONT_INFO_EX structure = default(Win32.CONSOLE_FONT_INFO_EX);
				structure.cbSize = (uint)Marshal.SizeOf<Win32.CONSOLE_FONT_INFO_EX>(structure);
				Win32.CONSOLE_FONT_INFO_EX structure2 = default(Win32.CONSOLE_FONT_INFO_EX);
				structure2.cbSize = (uint)Marshal.SizeOf<Win32.CONSOLE_FONT_INFO_EX>(structure2);
				structure2.FontFamily = Win32.TMPF_TRUETYPE;
				IntPtr destination = new IntPtr((void*)(&structure2.FaceName.FixedElementField));
				Marshal.Copy(fontName.ToCharArray(), 0, destination, fontName.Length);
				structure2.dwFontSize = new Win32.COORD(fontSizeX, fontSizeY);
				if (fontWeight != 0)
				{
					structure2.FontWeight = 700;
				}
				Win32.SetCurrentConsoleFontEx(stdHandle, false, ref structure2);
			}
		}
	}
}
