using System;
using System.Runtime.InteropServices;

namespace Lizzard.Helper
{
	// Token: 0x02000007 RID: 7
	internal class ConsoleHelper
	{
		// Token: 0x0600000C RID: 12 RVA: 0x00002388 File Offset: 0x00000588
		internal static bool DisableSelection()
		{
			IntPtr consoleHandle = ConsoleHelper.GetStdHandle(-10);
			uint consoleMode;
			bool flag = !ConsoleHelper.GetConsoleMode(consoleHandle, out consoleMode);
			bool result;
			if (flag)
			{
				result = false;
			}
			else
			{
				consoleMode &= 4294967231U;
				bool flag2 = !ConsoleHelper.SetConsoleMode(consoleHandle, consoleMode);
				result = !flag2;
			}
			return result;
		}

		// Token: 0x0600000D RID: 13
		[DllImport("kernel32.dll", SetLastError = true)]
		private static extern IntPtr GetStdHandle(int nStdHandle);

		// Token: 0x0600000E RID: 14
		[DllImport("kernel32.dll")]
		private static extern bool GetConsoleMode(IntPtr hConsoleHandle, out uint lpMode);

		// Token: 0x0600000F RID: 15
		[DllImport("kernel32.dll")]
		private static extern bool SetConsoleMode(IntPtr hConsoleHandle, uint dwMode);

		// Token: 0x04000004 RID: 4
		private const uint ENABLE_QUICK_EDIT = 64U;

		// Token: 0x04000005 RID: 5
		private const int STD_INPUT_HANDLE = -10;
	}
}
