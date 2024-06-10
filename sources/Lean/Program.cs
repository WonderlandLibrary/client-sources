using System;
using System.Windows.Forms;

namespace oxybitch
{
	// Token: 0x02000005 RID: 5
	internal static class Program
	{
		// Token: 0x0600001A RID: 26 RVA: 0x00004E23 File Offset: 0x00003023
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
