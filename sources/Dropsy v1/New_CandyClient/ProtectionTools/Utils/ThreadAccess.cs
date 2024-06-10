using System;

namespace New_CandyClient.ProtectionTools.Utils
{
	// Token: 0x02000035 RID: 53
	[Flags]
	public enum ThreadAccess
	{
		// Token: 0x040003AC RID: 940
		TERMINATE = 1,
		// Token: 0x040003AD RID: 941
		SUSPEND_RESUME = 2,
		// Token: 0x040003AE RID: 942
		GET_CONTEXT = 8,
		// Token: 0x040003AF RID: 943
		SET_CONTEXT = 16,
		// Token: 0x040003B0 RID: 944
		SET_INFORMATION = 32,
		// Token: 0x040003B1 RID: 945
		QUERY_INFORMATION = 64,
		// Token: 0x040003B2 RID: 946
		SET_THREAD_TOKEN = 128,
		// Token: 0x040003B3 RID: 947
		IMPERSONATE = 256,
		// Token: 0x040003B4 RID: 948
		DIRECT_IMPERSONATION = 512
	}
}
