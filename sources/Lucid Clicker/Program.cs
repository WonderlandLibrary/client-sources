using System;
using System.Windows.Forms;

namespace LucidPublic
{
	// Token: 0x02000003 RID: 3
	internal static class Program
	{
		// Token: 0x0600000A RID: 10 RVA: 0x00002087 File Offset: 0x00000287
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
