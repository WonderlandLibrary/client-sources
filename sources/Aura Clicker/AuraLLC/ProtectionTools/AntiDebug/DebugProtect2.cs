using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using AuraLLC.ProtectionTools.Utils;

namespace AuraLLC.ProtectionTools.AntiDebug
{
	// Token: 0x02000010 RID: 16
	internal class DebugProtect2
	{
		// Token: 0x06000021 RID: 33
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtQueryInformationProcess([In] IntPtr intptr_0, [In] PROCESSINFOCLASS processinfoclass_0, out IntPtr intptr_1, [In] int int_0, [Optional] out int int_1);

		// Token: 0x06000022 RID: 34
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtClose([In] IntPtr intptr_0);

		// Token: 0x06000023 RID: 35
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtRemoveProcessDebug(IntPtr intptr_0, IntPtr intptr_1);

		// Token: 0x06000024 RID: 36
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtSetInformationDebugObject([In] IntPtr intptr_0, [In] DebugObjectInformationClass debugObjectInformationClass_0, [In] IntPtr intptr_1, [In] int int_0, [Optional] out int int_1);

		// Token: 0x06000025 RID: 37
		[DllImport("ntdll.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern NtStatus NtQuerySystemInformation([In] SYSTEM_INFORMATION_CLASS system_INFORMATION_CLASS_0, IntPtr intptr_0, [In] int int_0, [Optional] out int int_1);

		// Token: 0x06000026 RID: 38 RVA: 0x00003788 File Offset: 0x00001988
		public static int PerformChecks()
		{
			int result;
			if (DebugProtect2.CheckDebugPort() == 1)
			{
				result = 1;
			}
			else if (DebugProtect2.CheckKernelDebugInformation())
			{
				result = 1;
			}
			else if (DebugProtect2.DetachFromDebuggerProcess())
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			return result;
		}

		// Token: 0x06000027 RID: 39 RVA: 0x000037BC File Offset: 0x000019BC
		private static int CheckDebugPort()
		{
			IntPtr intPtr = new IntPtr(0);
			int num;
			int result;
			if (DebugProtect2.NtQueryInformationProcess(Process.GetCurrentProcess().Handle, PROCESSINFOCLASS.ProcessDebugPort, out intPtr, Marshal.SizeOf<IntPtr>(intPtr), out num) == NtStatus.Success && intPtr == new IntPtr(-1))
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			return result;
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00003808 File Offset: 0x00001A08
		private unsafe static bool DetachFromDebuggerProcess()
		{
			IntPtr invalid_HANDLE_VALUE = DebugProtect2.INVALID_HANDLE_VALUE;
			uint structure = 0U;
			int num;
			int num2;
			return DebugProtect2.NtQueryInformationProcess(Process.GetCurrentProcess().Handle, PROCESSINFOCLASS.ProcessDebugObjectHandle, out invalid_HANDLE_VALUE, IntPtr.Size, out num) == NtStatus.Success && DebugProtect2.NtSetInformationDebugObject(invalid_HANDLE_VALUE, DebugObjectInformationClass.DebugObjectFlags, new IntPtr((void*)(&structure)), Marshal.SizeOf<uint>(structure), out num2) == NtStatus.Success && DebugProtect2.NtRemoveProcessDebug(Process.GetCurrentProcess().Handle, invalid_HANDLE_VALUE) == NtStatus.Success && DebugProtect2.NtClose(invalid_HANDLE_VALUE) == NtStatus.Success;
		}

		// Token: 0x06000029 RID: 41 RVA: 0x00003870 File Offset: 0x00001A70
		private unsafe static bool CheckKernelDebugInformation()
		{
			SYSTEM_KERNEL_DEBUGGER_INFORMATION system_KERNEL_DEBUGGER_INFORMATION;
			int num;
			return DebugProtect2.NtQuerySystemInformation(SYSTEM_INFORMATION_CLASS.SystemKernelDebuggerInformation, new IntPtr((void*)(&system_KERNEL_DEBUGGER_INFORMATION)), Marshal.SizeOf<SYSTEM_KERNEL_DEBUGGER_INFORMATION>(system_KERNEL_DEBUGGER_INFORMATION), out num) == NtStatus.Success && system_KERNEL_DEBUGGER_INFORMATION.KernelDebuggerEnabled && !system_KERNEL_DEBUGGER_INFORMATION.KernelDebuggerNotPresent;
		}

		// Token: 0x04000266 RID: 614
		private static readonly IntPtr INVALID_HANDLE_VALUE = new IntPtr(-1);
	}
}
