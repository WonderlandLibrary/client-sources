using System;

namespace WaveClient.SDK
{
	// Token: 0x02000010 RID: 16
	public abstract class SDKHandler
	{
		// Token: 0x06000052 RID: 82 RVA: 0x0000312C File Offset: 0x0000132C
		public SDKHandler(ulong address)
		{
			this.address = address;
		}

		// Token: 0x04000034 RID: 52
		public ulong address;
	}
}
