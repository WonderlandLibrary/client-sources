using System;

namespace TimeUtils
{
	// Token: 0x0200000D RID: 13
	public static class TimeUtils
	{
		// Token: 0x0600002F RID: 47 RVA: 0x00002D24 File Offset: 0x00001124
		public static long getCurrentUnixTime()
		{
			return DateTimeOffset.Now.ToUnixTimeMilliseconds();
		}
	}
}
