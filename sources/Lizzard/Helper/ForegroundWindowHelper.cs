using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace Lizzard.Helper
{
	// Token: 0x02000008 RID: 8
	internal class ForegroundWindowHelper
	{
		// Token: 0x06000011 RID: 17 RVA: 0x000023DC File Offset: 0x000005DC
		public static Process GetForegrondWindow()
		{
			int processid = 0;
			ForegroundWindowHelper.GetWindowThreadProcessId(ForegroundWindowHelper.GetForegroundWindow(), out processid);
			return (Process.GetProcessById(processid).ProcessName != "Idle") ? Process.GetProcessById(processid) : null;
		}

		// Token: 0x06000012 RID: 18
		[DllImport("user32.dll")]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000013 RID: 19
		[DllImport("user32.dll", CharSet = CharSet.Unicode, SetLastError = true)]
		public static extern int GetWindowThreadProcessId(IntPtr hWnd, out int lpdwProcessId);
	}
}
