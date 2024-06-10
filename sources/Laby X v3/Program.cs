using System;
using System.Windows.Forms;
using Form3;

namespace LabyxV3
{
	// Token: 0x02000009 RID: 9
	internal static class Program
	{
		// Token: 0x0600005E RID: 94 RVA: 0x000063B8 File Offset: 0x000045B8
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
