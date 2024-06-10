using System;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000006 RID: 6
	internal static class Class2
	{
		// Token: 0x06000043 RID: 67 RVA: 0x00014564 File Offset: 0x00012764
		[STAThread]
		private static void Main()
		{
			int num = 0;
			do
			{
				if (num == 4)
				{
					Application.Run(new Form2());
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					Application.EnableVisualStyles();
					num = 3;
				}
				if (num == 3)
				{
					Application.SetCompatibleTextRenderingDefault(false);
					num = 4;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 5);
		}
	}
}
