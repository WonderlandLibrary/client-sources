using System;
using System.Windows.Forms;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000006 RID: 6
	internal static class Program
	{
		// Token: 0x06000028 RID: 40 RVA: 0x000021B7 File Offset: 0x000003B7
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Steam());
		}
	}
}
