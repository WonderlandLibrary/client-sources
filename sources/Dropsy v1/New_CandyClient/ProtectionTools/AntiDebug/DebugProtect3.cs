using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using New_CandyClient.ProtectionTools.Utils;

namespace New_CandyClient.ProtectionTools.AntiDebug
{
	// Token: 0x0200003A RID: 58
	internal class DebugProtect3
	{
		// Token: 0x06000186 RID: 390
		[DllImport("ntdll.dll")]
		internal static extern NtStatus NtSetInformationThread(IntPtr ThreadHandle, ThreadInformationClass ThreadInformationClass, IntPtr ThreadInformation, int ThreadInformationLength);

		// Token: 0x06000187 RID: 391
		[DllImport("kernel32.dll")]
		private static extern IntPtr OpenThread(ThreadAccess dwDesiredAccess, bool bInheritHandle, uint dwThreadId);

		// Token: 0x06000188 RID: 392
		[DllImport("kernel32.dll")]
		private static extern uint SuspendThread(IntPtr hThread);

		// Token: 0x06000189 RID: 393
		[DllImport("kernel32.dll")]
		private static extern int ResumeThread(IntPtr hThread);

		// Token: 0x0600018A RID: 394
		[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern bool CloseHandle(IntPtr handle);

		// Token: 0x0600018B RID: 395 RVA: 0x0001303C File Offset: 0x0001143C
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

		// Token: 0x0600018C RID: 396 RVA: 0x00013114 File Offset: 0x00011514
		public static bool HideFromDebugger(IntPtr Handle)
		{
			return DebugProtect3.NtSetInformationThread(Handle, ThreadInformationClass.ThreadHideFromDebugger, IntPtr.Zero, 0) == NtStatus.Success;
		}
	}
}
