using System;
using System.Windows.Forms;

namespace AnyDesk.my
{
	// Token: 0x02000008 RID: 8
	internal static class Program
	{
		// Token: 0x06000025 RID: 37 RVA: 0x0000208D File Offset: 0x0000028D
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
