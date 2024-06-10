using System;
using System.Windows.Forms;

namespace Octo_Client
{
	// Token: 0x02000004 RID: 4
	internal static class Program
	{
		// Token: 0x06000036 RID: 54 RVA: 0x00006681 File Offset: 0x00004881
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new OctoMain());
		}
	}
}
