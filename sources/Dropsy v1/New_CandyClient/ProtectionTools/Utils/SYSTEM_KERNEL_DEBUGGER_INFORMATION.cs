using System;
using System.Runtime.InteropServices;

namespace New_CandyClient.ProtectionTools.Utils
{
	// Token: 0x02000036 RID: 54
	public struct SYSTEM_KERNEL_DEBUGGER_INFORMATION
	{
		// Token: 0x040003B5 RID: 949
		[MarshalAs(UnmanagedType.U1)]
		public bool KernelDebuggerEnabled;

		// Token: 0x040003B6 RID: 950
		[MarshalAs(UnmanagedType.U1)]
		public bool KernelDebuggerNotPresent;
	}
}
