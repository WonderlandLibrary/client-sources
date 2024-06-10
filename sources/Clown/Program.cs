using System;
using System.Windows.Forms;

namespace ClownClient
{
	// Token: 0x02000003 RID: 3
	internal static class Program
	{
		// Token: 0x06000009 RID: 9 RVA: 0x00002A65 File Offset: 0x00000C65
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
