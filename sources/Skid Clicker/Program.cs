using System;
using System.Windows.Forms;

namespace dgbfdfuib
{
	// Token: 0x02000003 RID: 3
	internal static class Program
	{
		// Token: 0x06000011 RID: 17 RVA: 0x000028DD File Offset: 0x00000ADD
		[STAThread]
		private static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form1());
		}
	}
}
