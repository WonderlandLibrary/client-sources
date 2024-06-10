using System;

namespace Lizzard.Math
{
	// Token: 0x02000004 RID: 4
	internal class Pattern
	{
		// Token: 0x06000005 RID: 5 RVA: 0x0000220B File Offset: 0x0000040B
		public Pattern()
		{
			this.random = new Random();
		}

		// Token: 0x06000006 RID: 6 RVA: 0x00002220 File Offset: 0x00000420
		public int Evaluate()
		{
			double chance = this.random.NextDouble() * 100.0;
			return (chance <= 10.0) ? this.random.Next(40, 50) : ((chance <= 30.0) ? this.random.Next(50, 70) : ((chance <= 60.0) ? this.random.Next(70, 80) : ((chance <= 90.0) ? this.random.Next(80, 100) : ((chance <= 91.7) ? this.random.Next(100, 120) : this.random.Next(150, 200)))));
		}

		// Token: 0x04000002 RID: 2
		private Random random;
	}
}
