using System;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000011 RID: 17
	internal class Class7 : Panel
	{
		// Token: 0x17000024 RID: 36
		// (get) Token: 0x060000BC RID: 188 RVA: 0x0001DC3C File Offset: 0x0001BE3C
		protected virtual CreateParams CreateParams
		{
			get
			{
				int num = 0;
				CreateParams result;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 5)
					{
						break;
					}
					CreateParams createParams;
					if (num == 3)
					{
						createParams.ExStyle |= 32;
						num = 4;
					}
					if (num == 4)
					{
						result = createParams;
						num = 5;
					}
					if (num == 2)
					{
						createParams = base.CreateParams;
						num = 3;
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

		// Token: 0x060000BD RID: 189 RVA: 0x0001821C File Offset: 0x0001641C
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

		// Token: 0x060000BE RID: 190 RVA: 0x000020E0 File Offset: 0x000002E0
		public Class7()
		{
			<Module>.Class0.smethod_0();
			base..ctor();
		}
	}
}
