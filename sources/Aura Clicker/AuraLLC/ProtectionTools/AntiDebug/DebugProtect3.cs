using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using AuraLLC.ProtectionTools.Utils;

namespace AuraLLC.ProtectionTools.AntiDebug
{
	// Token: 0x02000011 RID: 17
	internal class DebugProtect3
	{
		// Token: 0x0600002C RID: 44
		[DllImport("ntdll.dll")]
		internal static extern NtStatus NtSetInformationThread(IntPtr intptr_0, ThreadInformationClass threadInformationClass_0, IntPtr intptr_1, int int_0);

		// Token: 0x0600002D RID: 45
		[DllImport("kernel32.dll")]
		private static extern IntPtr OpenThread(ThreadAccess threadAccess_0, bool bool_0, uint uint_0);

		// Token: 0x0600002E RID: 46
		[DllImport("kernel32.dll")]
		private static extern uint SuspendThread(IntPtr intptr_0);

		// Token: 0x0600002F RID: 47
		[DllImport("kernel32.dll")]
		private static extern int ResumeThread(IntPtr intptr_0);

		// Token: 0x06000030 RID: 48
		[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern bool CloseHandle(IntPtr intptr_0);

		// Token: 0x06000031 RID: 49 RVA: 0x000038AC File Offset: 0x00001AAC
		public static void HideOSThreads()
		{
			foreach (object obj in Process.GetCurrentProcess().Threads)
			{
				ProcessThread processThread = (ProcessThread)obj;
				MessageBox.Show("[OSThreads]: thread.Id {0:X}", processThread.Id.ToString());
				IntPtr intPtr = DebugProtect3.OpenThread(ThreadAccess.SET_INFORMATION, false, (uint)processThread.Id);
				if (intPtr == IntPtr.Zero)
				{
					MessageBox.Show("[OSThreads]: skipped thread.Id {0:X}", processThread.Id.ToString());
				}
				else
				{
					if (DebugProtect3.HideFromDebugger(intPtr))
					{
						MessageBox.Show("[OSThreads]: thread.Id {0:X} hidden from debbuger.", processThread.Id.ToString());
					}
					DebugProtect3.CloseHandle(intPtr);
				}
			}
		}

		// Token: 0x06000032 RID: 50 RVA: 0x000022D6 File Offset: 0x000004D6
		public static bool HideFromDebugger(IntPtr intptr_0)
		{
			return DebugProtect3.NtSetInformationThread(intptr_0, ThreadInformationClass.ThreadHideFromDebugger, IntPtr.Zero, 0) == NtStatus.Success;
		}
	}
}
