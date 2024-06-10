using System;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x0200000C RID: 12
	internal class Class4 : Panel
	{
		// Token: 0x17000012 RID: 18
		// (get) Token: 0x06000082 RID: 130 RVA: 0x000196DC File Offset: 0x000178DC
		protected virtual CreateParams CreateParams
		{
			get
			{
				int num = 0;
				CreateParams result;
				do
				{
					CreateParams createParams;
					if (num == 3)
					{
						createParams.ExStyle |= 32;
						num = 4;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						createParams = base.CreateParams;
						num = 3;
					}
					if (num == 5)
					{
						break;
					}
					if (num == 4)
					{
						result = createParams;
						num = 5;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 6);
				return result;
			}
		}

		// Token: 0x06000083 RID: 131 RVA: 0x0001821C File Offset: 0x0001641C
		protected virtual void OnPaintBackground(PaintEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 2);
		}

		// Token: 0x06000084 RID: 132 RVA: 0x000020E0 File Offset: 0x000002E0
		public Class4()
		{
			<Module>.Class0.smethod_0();
			base..ctor();
		}
	}
}
