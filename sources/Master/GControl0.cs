using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000007 RID: 7
	public class GControl0 : ContainerControl
	{
		// Token: 0x06000044 RID: 68 RVA: 0x00014624 File Offset: 0x00012824
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			for (;;)
			{
				Graphics graphics;
				if (num == 8)
				{
					graphics = e.Graphics;
					num = 9;
				}
				if (num != 18)
				{
					goto IL_2D;
				}
				bool flag;
				if (flag)
				{
					num = 19;
					goto IL_2D;
				}
				goto IL_31F;
				IL_33F:
				ColorBlend colorBlend;
				if (num == 5)
				{
					colorBlend.Colors = new Color[]
					{
						this.color_1,
						this.color_2,
						this.color_0
					};
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
				int num2;
				bool flag2;
				if (num == 21)
				{
					flag2 = (num2 == 6);
					goto IL_3D5;
				}
				continue;
				IL_E6:
				if (num == 11)
				{
					this.BackColor = Color.FromArgb(23, 23, 23);
					num = 12;
				}
				Rectangle rect;
				LinearGradientBrush linearGradientBrush;
				if (num == 3)
				{
					linearGradientBrush = new LinearGradientBrush(rect, this.color_1, this.color_2, 0f);
					num = 4;
				}
				if (num == 6)
				{
					colorBlend.Positions = new float[]
					{
						0f,
						0.5f,
						1f
					};
					num = 7;
				}
				Rectangle rect2;
				if (num == 19)
				{
					e.Graphics.DrawRectangle(Pens.Black, rect2);
					num = 20;
				}
				if (num == 9)
				{
					graphics.FillRectangle(linearGradientBrush, rect);
					num = 10;
				}
				if (num == 4)
				{
					colorBlend = new ColorBlend(3);
					num = 5;
				}
				if (num == 10)
				{
					base.OnPaint(e);
					num = 11;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				Pen pen;
				if (num == 15)
				{
					pen = new Pen(Color.FromArgb(60, 60, 60));
					num = 16;
				}
				if (num == 17)
				{
					flag = (num2 == 1);
					num = 18;
				}
				if (num == 2)
				{
					rect = new Rectangle(7, 7, base.Width - 14, 2);
					num = 3;
				}
				Pen pen2;
				if (num == 16)
				{
					pen2 = new Pen(Color.FromArgb(40, 40, 40));
					num = 17;
				}
				if (num == 20)
				{
					goto IL_31F;
				}
				goto IL_33F;
				IL_2D:
				if (num == 13)
				{
					goto IL_43B;
				}
				if (num == 12)
				{
					num2 = 1;
					num = 13;
				}
				if (num == 7)
				{
					linearGradientBrush.InterpolationColors = colorBlend;
					num = 8;
				}
				if (num == 14)
				{
					goto IL_8C;
				}
				goto IL_E6;
				IL_31F:
				if (num2 != 2)
				{
					num = 21;
					goto IL_33F;
				}
				flag2 = true;
				goto IL_3D5;
				IL_8C:
				rect2 = new Rectangle(num2 - 1, num2 - 1, base.Width - num2 * 2 + 1, base.Height - num2 * 2 + 1);
				num = 15;
				goto IL_E6;
				IL_43B:
				if (num2 < 7)
				{
					goto IL_8C;
				}
				break;
				IL_3D5:
				if (flag2)
				{
					e.Graphics.DrawRectangle(pen, rect2);
				}
				if (num2 == 3 || num2 == 4 || num2 == 5)
				{
					e.Graphics.DrawRectangle(pen2, rect2);
				}
				num2++;
				goto IL_43B;
			}
		}

		// Token: 0x06000045 RID: 69 RVA: 0x00014AAC File Offset: 0x00012CAC
		protected virtual void OnHandleCreated(EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				Form form;
				if (num == 5)
				{
					form = (base.Parent as Form);
					num = 6;
				}
				bool flag;
				if (num == 3)
				{
					flag = (base.Parent is Form);
					num = 4;
				}
				if (num == 7)
				{
					form.FormBorderStyle = FormBorderStyle.None;
					num = 8;
				}
				if (num == 2)
				{
					this.Dock = DockStyle.Fill;
					num = 3;
				}
				if (num == 8)
				{
					base.OnHandleCreated(e);
					num = 9;
				}
				if (num == 4)
				{
					if (!flag)
					{
						break;
					}
					num = 5;
				}
				if (num == 6)
				{
					this.form_0 = form;
					num = 7;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 9);
		}

		// Token: 0x06000046 RID: 70 RVA: 0x00014C10 File Offset: 0x00012E10
		public GControl0()
		{
			<Module>.Class0.smethod_0();
			this.color_0 = Color.FromArgb(135, 211, 194);
			this.color_1 = Color.FromArgb(70, 186, 141);
			this.color_2 = Color.FromArgb(75, 175, 209);
			this.bool_0 = false;
			base..ctor();
		}

		// Token: 0x04000052 RID: 82
		public Color color_0;

		// Token: 0x04000053 RID: 83
		public Color color_1;

		// Token: 0x04000054 RID: 84
		public Color color_2;

		// Token: 0x04000055 RID: 85
		public Form form_0;

		// Token: 0x04000056 RID: 86
		private bool bool_0;

		// Token: 0x04000057 RID: 87
		private Point point_0;
	}
}
