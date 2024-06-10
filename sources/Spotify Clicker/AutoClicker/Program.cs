using System;
using System.Windows.Forms;

namespace AutoClicker
{
	// Token: 0x0200000C RID: 12
	internal static class Program
	{
		// Token: 0x06000088 RID: 136 RVA: 0x0000967E File Offset: 0x0000787E
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Program.main = new MainForm();
			Application.Run(Program.main);
		}

		// Token: 0x040000B7 RID: 183
		public static MainForm main;
	}
}
