using System;
using System.Diagnostics;

namespace Lizzard.Locator
{
	// Token: 0x02000005 RID: 5
	internal class InstanceLocator
	{
		// Token: 0x06000007 RID: 7 RVA: 0x000022F1 File Offset: 0x000004F1
		public InstanceLocator(string title)
		{
			this.title = title;
		}

		// Token: 0x06000008 RID: 8 RVA: 0x00002304 File Offset: 0x00000504
		public Process GetInstance()
		{
			Process[] processes = Process.GetProcessesByName("Javaw");
			foreach (Process prc in processes)
			{
				bool flag = prc.MainWindowTitle.Contains(this.title);
				if (flag)
				{
					return prc;
				}
			}
			return null;
		}

		// Token: 0x04000003 RID: 3
		private string title;
	}
}
