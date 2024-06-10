using System;

namespace Lizzard.Math
{
	// Token: 0x02000003 RID: 3
	internal class MathUtil
	{
		// Token: 0x06000003 RID: 3 RVA: 0x000021E4 File Offset: 0x000003E4
		public static double ConvertDelayToCps(int delay)
		{
			return 1000.0 / (double)delay;
		}
	}
}
