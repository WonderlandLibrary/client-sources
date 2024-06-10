using System;
using System.Windows.Forms;

namespace WindowsFormsApp10
{
	// Token: 0x02000004 RID: 4
	internal static class Program
	{
		// Token: 0x060000B6 RID: 182 RVA: 0x00007210 File Offset: 0x00005410
		[STAThread]
		private static void Main()
		{
			new Form1
			{
				StartPosition = FormStartPosition.Manual,
				Left = 0,
				Top = 30
			}.ShowDialog();
		}
	}
}
