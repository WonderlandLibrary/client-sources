using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using New_CandyClient.ProtectionTools.Utils;

namespace New_CandyClient.ProtectionTools.AntiDebug
{
	// Token: 0x02000039 RID: 57
	internal class DebugProtect2
	{
		// Token: 0x0600017B RID: 379
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtQueryInformationProcess([In] IntPtr ProcessHandle, [In] PROCESSINFOCLASS ProcessInformationClass, out IntPtr ProcessInformation, [In] int ProcessInformationLength, [Optional] out int ReturnLength);

		// Token: 0x0600017C RID: 380
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtClose([In] IntPtr Handle);

		// Token: 0x0600017D RID: 381
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtRemoveProcessDebug(IntPtr ProcessHandle, IntPtr DebugObjectHandle);

		// Token: 0x0600017E RID: 382
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtSetInformationDebugObject([In] IntPtr DebugObjectHandle, [In] DebugObjectInformationClass DebugObjectInformationClass, [In] IntPtr DebugObjectInformation, [In] int DebugObjectInformationLength, [Optional] out int ReturnLength);

		// Token: 0x0600017F RID: 383
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtQuerySystemInformation([In] SYSTEM_INFORMATION_CLASS SystemInformationClass, IntPtr SystemInformation, [In] int SystemInformationLength, [Optional] out int ReturnLength);

		// Token: 0x06000180 RID: 384 RVA: 0x00012F1F File Offset: 0x0001131F
		public static int PerformChecks()
		{
			if (DebugProtect2.CheckDebugPort() == 1)
			{
				return 1;
			}
			if (DebugProtect2.CheckKernelDebugInformation())
			{
				return 1;
			}
			if (DebugProtect2.DetachFromDebuggerProcess())
			{
				return 1;
			}
			return 0;
		}

		// Token: 0x06000181 RID: 385 RVA: 0x00012F40 File Offset: 0x00011340
		private static int CheckDebugPort()
		{
			IntPtr intPtr = new IntPtr(0);
			int num;
			if (DebugProtect2.NtQueryInformationProcess(Process.GetCurrentProcess().Handle, PROCESSINFOCLASS.ProcessDebugPort, out intPtr, Marshal.SizeOf<IntPtr>(intPtr), out num) == NtStatus.Success && intPtr == new IntPtr(-1))
			{
				return 1;
			}
			return 0;
		}

		// Token: 0x06000182 RID: 386 RVA: 0x00012F84 File Offset: 0x00011384
		private unsafe static bool DetachFromDebuggerProcess()
		{
			IntPtr invalid_HANDLE_VALUE = DebugProtect2.INVALID_HANDLE_VALUE;
			uint structure = 0U;
			int num;
			int num2;
			return DebugProtect2.NtQueryInformationProcess(Process.GetCurrentProcess().Handle, PROCESSINFOCLASS.ProcessDebugObjectHandle, out invalid_HANDLE_VALUE, IntPtr.Size, out num) == NtStatus.Success && DebugProtect2.NtSetInformationDebugObject(invalid_HANDLE_VALUE, DebugObjectInformationClass.DebugObjectFlags, new IntPtr((void*)(&structure)), Marshal.SizeOf<uint>(structure), out num2) == NtStatus.Success && DebugProtect2.NtRemoveProcessDebug(Process.GetCurrentProcess().Handle, invalid_HANDLE_VALUE) == NtStatus.Success && DebugProtect2.NtClose(invalid_HANDLE_VALUE) == NtStatus.Success;
		}

		// Token: 0x06000183 RID: 387 RVA: 0x00012FF4 File Offset: 0x000113F4
		private unsafe static bool CheckKernelDebugInformation()
		{
			SYSTEM_KERNEL_DEBUGGER_INFORMATION system_KERNEL_DEBUGGER_INFORMATION;
			int num;
			return DebugProtect2.NtQuerySystemInformation(SYSTEM_INFORMATION_CLASS.SystemKernelDebuggerInformation, new IntPtr((void*)(&system_KERNEL_DEBUGGER_INFORMATION)), Marshal.SizeOf<SYSTEM_KERNEL_DEBUGGER_INFORMATION>(system_KERNEL_DEBUGGER_INFORMATION), out num) == NtStatus.Success && system_KERNEL_DEBUGGER_INFORMATION.KernelDebuggerEnabled && !system_KERNEL_DEBUGGER_INFORMATION.KernelDebuggerNotPresent;
		}

		// Token: 0x040003BB RID: 955
		private static readonly IntPtr INVALID_HANDLE_VALUE = new IntPtr(-1);
	}
}
