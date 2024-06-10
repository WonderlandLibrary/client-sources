using System;
using System.Runtime.InteropServices;

namespace Lizzard.Helper
{
	// Token: 0x0200000A RID: 10
	internal class MouseHelper
	{
		// Token: 0x06000018 RID: 24 RVA: 0x00002454 File Offset: 0x00000654
		public static bool GetMouseState()
		{
			return MouseHelper.GetKeyState(1) > 1;
		}

		// Token: 0x06000019 RID: 25 RVA: 0x00002470 File Offset: 0x00000670
		public static void PostMessage(int mouse, IntPtr handle)
		{
			bool flag = mouse == 0;
			if (flag)
			{
				MouseHelper.PostMessage(handle, 513U, 1, 0);
			}
			else
			{
				MouseHelper.PostMessage(handle, 514U, 1, 1);
			}
		}

		// Token: 0x0600001A RID: 26
		[DllImport("user32.dll")]
		private static extern bool PostMessage(IntPtr hWnd, uint Msg, int wParam, int lParam);

		// Token: 0x0600001B RID: 27
		[DllImport("user32", CharSet = CharSet.Ansi, SetLastError = true)]
		public static extern int GetKeyState(int vKey);

		// Token: 0x04000007 RID: 7
		private const int LM_KEYDOWN = 513;

		// Token: 0x04000008 RID: 8
		private const int LM_KEYUP = 514;
	}
}
