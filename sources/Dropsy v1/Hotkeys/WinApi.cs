using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace Hotkeys
{
	// Token: 0x0200000C RID: 12
	public static class WinApi
	{
		// Token: 0x0600002B RID: 43
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool AllocConsole();

		// Token: 0x0600002C RID: 44
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FreeConsole();

		// Token: 0x0600002D RID: 45
		[DllImport("user32.dll")]
		private static extern int GetAsyncKeyState(Keys keys_0);

		// Token: 0x0600002E RID: 46 RVA: 0x00002D16 File Offset: 0x00001116
		public static bool getKeyState(Keys key)
		{
			return Convert.ToBoolean(WinApi.GetAsyncKeyState(key));
		}
	}
}
