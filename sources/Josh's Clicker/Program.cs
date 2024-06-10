using System;
using System.Windows.Forms;

namespace dotClick
{
	// Token: 0x0200000F RID: 15
	internal static class Program
	{
		// Token: 0x06000063 RID: 99 RVA: 0x000040C4 File Offset: 0x000022C4
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			try
			{
				Application.Run(new Main());
			}
			catch (Exception)
			{
			}
		}
	}
}
