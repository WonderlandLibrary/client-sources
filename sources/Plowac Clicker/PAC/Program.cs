using System;
using System.Windows.Forms;

namespace PAC
{
	// Token: 0x02000004 RID: 4
	internal static class Program
	{
		// Token: 0x06000029 RID: 41 RVA: 0x00002112 File Offset: 0x00000312
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
