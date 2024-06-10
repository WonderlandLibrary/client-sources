using System;
using System.Threading;
using System.Windows.Forms;

namespace ac.ac
{
	// Token: 0x02000006 RID: 6
	internal class Autoclicker
	{
		// Token: 0x06000015 RID: 21 RVA: 0x0000272C File Offset: 0x0000092C
		public Autoclicker()
		{
			this.s = new Worker(this);
			new Thread(new ThreadStart(this.s.Click)).Start();
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00002783 File Offset: 0x00000983
		public void stop()
		{
			this.s.stop();
		}

		// Token: 0x0400000E RID: 14
		public volatile int cps = 10;

		// Token: 0x0400000F RID: 15
		public volatile int random = 50;

		// Token: 0x04000010 RID: 16
		public volatile bool enabled = true;

		// Token: 0x04000011 RID: 17
		public Label en;

		// Token: 0x04000012 RID: 18
		private Worker s;
	}
}
