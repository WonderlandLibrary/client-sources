using System;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200000C RID: 12
	public class Start
	{
		// Token: 0x0600016F RID: 367 RVA: 0x0000D38F File Offset: 0x0000B58F
		[STAThread]
		private void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Main());
		}
	}
}
