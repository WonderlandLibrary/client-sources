using System;

namespace AuraLLC.ProtectionTools.Utils
{
	// Token: 0x0200000C RID: 12
	[Flags]
	public enum ThreadAccess
	{
		// Token: 0x04000231 RID: 561
		TERMINATE = 1,
		// Token: 0x04000232 RID: 562
		SUSPEND_RESUME = 2,
		// Token: 0x04000233 RID: 563
		GET_CONTEXT = 8,
		// Token: 0x04000234 RID: 564
		SET_CONTEXT = 16,
		// Token: 0x04000235 RID: 565
		SET_INFORMATION = 32,
		// Token: 0x04000236 RID: 566
		QUERY_INFORMATION = 64,
		// Token: 0x04000237 RID: 567
		SET_THREAD_TOKEN = 128,
		// Token: 0x04000238 RID: 568
		IMPERSONATE = 256,
		// Token: 0x04000239 RID: 569
		DIRECT_IMPERSONATION = 512
	}
}
