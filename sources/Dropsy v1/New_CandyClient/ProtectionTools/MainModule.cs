using System;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Threading;

namespace New_CandyClient.ProtectionTools
{
	// Token: 0x0200002D RID: 45
	internal static class MainModule
	{
		// Token: 0x06000163 RID: 355
		[DllImport("kernel32", EntryPoint = "SetProcessWorkingSetSize")]
		private static extern int OneWayAttribute([In] IntPtr obj0, [In] int obj1, [In] int obj2);

		// Token: 0x06000164 RID: 356 RVA: 0x00012952 File Offset: 0x00010D52
		internal static bool IsSandboxie()
		{
			return Sandboxie.IsSandboxie();
		}

		// Token: 0x06000165 RID: 357 RVA: 0x00012959 File Offset: 0x00010D59
		internal static bool IsVM()
		{
			return CommonAcl.SecurityDocumentElement();
		}

		// Token: 0x06000166 RID: 358 RVA: 0x00012960 File Offset: 0x00010D60
		internal static bool IsDebugger()
		{
			return DebuggerAcl.Run();
		}

		// Token: 0x06000167 RID: 359 RVA: 0x00012967 File Offset: 0x00010D67
		internal static bool IsdnSpyRun()
		{
			return DnSpy.ValueType();
		}

		// Token: 0x06000168 RID: 360 RVA: 0x00012970 File Offset: 0x00010D70
		internal static bool IsEmulation()
		{
			int num = new Random().Next(3000, 10000);
			DateTime now = DateTime.Now;
			Thread.Sleep(num);
			return (DateTime.Now - now).TotalMilliseconds < (double)num;
		}

		// Token: 0x06000169 RID: 361 RVA: 0x000129B8 File Offset: 0x00010DB8
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

		// Token: 0x0600016A RID: 362 RVA: 0x00012A0C File Offset: 0x00010E0C
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
					MainModule.OneWayAttribute(handle, -1, -1);
				}
			}
		}
	}
}
