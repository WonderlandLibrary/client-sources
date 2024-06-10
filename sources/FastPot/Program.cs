using System;
using System.Windows.Forms;

namespace FastPot
{
	// Token: 0x02000004 RID: 4
	internal static class Program
	{
		// Token: 0x0600001B RID: 27 RVA: 0x000032AC File Offset: 0x000014AC
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new MainForm());
		}
	}
}
