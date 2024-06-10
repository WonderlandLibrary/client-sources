using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace AuraLLC.ProtectionTools.AntiDebug
{
	// Token: 0x0200000F RID: 15
	internal class DebugProtect1
	{
		// Token: 0x0600001A RID: 26
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool CheckRemoteDebuggerPresent(IntPtr intptr_0, [MarshalAs(UnmanagedType.Bool)] ref bool bool_0);

		// Token: 0x0600001B RID: 27
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool IsDebuggerPresent();

		// Token: 0x0600001C RID: 28 RVA: 0x000036E8 File Offset: 0x000018E8
		public static int PerformChecks()
		{
			int result;
			if (DebugProtect1.CheckDebuggerManagedPresent() == 1)
			{
				result = 1;
			}
			else if (DebugProtect1.CheckDebuggerUnmanagedPresent() == 1)
			{
				result = 1;
			}
			else if (DebugProtect1.CheckRemoteDebugger() == 1)
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			return result;
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00003724 File Offset: 0x00001924
		private static int CheckDebuggerManagedPresent()
		{
			int result;
			if (Debugger.IsAttached)
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			return result;
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00003740 File Offset: 0x00001940
		private static int CheckDebuggerUnmanagedPresent()
		{
			int result;
			if (DebugProtect1.IsDebuggerPresent())
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			return result;
		}

		// Token: 0x0600001F RID: 31 RVA: 0x0000375C File Offset: 0x0000195C
		private static int CheckRemoteDebugger()
		{
			bool flag = false;
			int result;
			if (DebugProtect1.CheckRemoteDebuggerPresent(Process.GetCurrentProcess().Handle, ref flag) && flag)
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			return result;
		}
	}
}
