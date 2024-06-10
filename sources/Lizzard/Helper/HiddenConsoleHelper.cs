using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace Lizzard.Helper
{
	// Token: 0x02000009 RID: 9
	internal class HiddenConsoleHelper
	{
		// Token: 0x06000015 RID: 21 RVA: 0x00002428 File Offset: 0x00000628
		public static void HideConsole()
		{
			IntPtr handle = Process.GetCurrentProcess().MainWindowHandle;
			HiddenConsoleHelper.ShowWindow(handle, 0);
		}

		// Token: 0x06000016 RID: 22
		[DllImport("user32.dll")]
		private static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

		// Token: 0x04000006 RID: 6
		private const int SW_HIDE = 0;
	}
}
