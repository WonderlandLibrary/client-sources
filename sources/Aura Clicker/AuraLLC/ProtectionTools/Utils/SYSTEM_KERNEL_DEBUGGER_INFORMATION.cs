using System;
using System.Runtime.InteropServices;

namespace AuraLLC.ProtectionTools.Utils
{
	// Token: 0x0200000B RID: 11
	public struct SYSTEM_KERNEL_DEBUGGER_INFORMATION
	{
		// Token: 0x0400022E RID: 558
		[MarshalAs(UnmanagedType.U1)]
		public bool KernelDebuggerEnabled;

		// Token: 0x0400022F RID: 559
		[MarshalAs(UnmanagedType.U1)]
		public bool KernelDebuggerNotPresent;
	}
}
