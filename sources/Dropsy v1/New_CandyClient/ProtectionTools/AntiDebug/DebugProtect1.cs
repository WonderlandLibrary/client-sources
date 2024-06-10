using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace New_CandyClient.ProtectionTools.AntiDebug
{
	// Token: 0x02000038 RID: 56
	internal class DebugProtect1
	{
		// Token: 0x06000174 RID: 372
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool CheckRemoteDebuggerPresent(IntPtr hProcess, [MarshalAs(UnmanagedType.Bool)] ref bool isDebuggerPresent);

		// Token: 0x06000175 RID: 373
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool IsDebuggerPresent();

		// Token: 0x06000176 RID: 374 RVA: 0x00012EBE File Offset: 0x000112BE
		public static int PerformChecks()
		{
			if (DebugProtect1.CheckDebuggerManagedPresent() == 1)
			{
				return 1;
			}
			if (DebugProtect1.CheckDebuggerUnmanagedPresent() == 1)
			{
				return 1;
			}
			if (DebugProtect1.CheckRemoteDebugger() == 1)
			{
				return 1;
			}
			return 0;
		}

		// Token: 0x06000177 RID: 375 RVA: 0x00012EDF File Offset: 0x000112DF
		private static int CheckDebuggerManagedPresent()
		{
			if (Debugger.IsAttached)
			{
				return 1;
			}
			return 0;
		}

		// Token: 0x06000178 RID: 376 RVA: 0x00012EEB File Offset: 0x000112EB
		private static int CheckDebuggerUnmanagedPresent()
		{
			if (DebugProtect1.IsDebuggerPresent())
			{
				return 1;
			}
			return 0;
		}

		// Token: 0x06000179 RID: 377 RVA: 0x00012EF8 File Offset: 0x000112F8
		private static int CheckRemoteDebugger()
		{
			bool flag = false;
			if (DebugProtect1.CheckRemoteDebuggerPresent(Process.GetCurrentProcess().Handle, ref flag) && flag)
			{
				return 1;
			}
			return 0;
		}
	}
}
