using System;
using System.Drawing;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000009 RID: 9
	public class GClass0 : GroupBox
	{
		// Token: 0x06000051 RID: 81 RVA: 0x0001536C File Offset: 0x0001356C
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					this.method_0(this, e.Graphics, Color.White, Color.FromArgb(44, 42, 46), Color.Black);
					num = 3;
				}
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
			while (num != 3);
		}

		// Token: 0x06000052 RID: 82 RVA: 0x00015418 File Offset: 0x00013618
		private void method_0(GClass0 gclass0_0, Graphics graphics_0, Color color_0, Color color_1, Color color_2)
		{
			int num = 0;
			do
			{
				Pen pen;
				Rectangle rectangle;
				if (num == 15)
				{
					graphics_0.DrawLine(pen, new Point(rectangle.X, rectangle.Y + rectangle.Height - 1), new Point(rectangle.X + rectangle.Width, rectangle.Y + rectangle.Height - 1));
					num = 16;
				}
				Pen pen2;
				if (num == 19)
				{
					graphics_0.DrawLine(pen2, new Point(rectangle.X + rectangle.Width, rectangle.Y), new Point(rectangle.X + rectangle.Width, rectangle.Y + rectangle.Height));
					num = 20;
				}
				SizeF sizeF;
				if (num == 17)
				{
					graphics_0.DrawLine(pen, new Point(rectangle.X + gclass0_0.Padding.Left + (int)sizeF.Width, rectangle.Y), new Point(rectangle.X + rectangle.Width, rectangle.Y));
					num = 18;
				}
				if (num == 13)
				{
					graphics_0.DrawLine(pen, new Point(rectangle.Location.X + 1, rectangle.Location.Y), new Point(rectangle.X + 1, rectangle.Y + rectangle.Height));
					num = 14;
				}
				if (num == 20)
				{
					graphics_0.DrawLine(pen2, new Point(rectangle.X, rectangle.Y + rectangle.Height), new Point(rectangle.X + rectangle.Width, rectangle.Y + rectangle.Height));
					num = 21;
				}
				if (num == 18)
				{
					graphics_0.DrawLine(pen2, rectangle.Location, new Point(rectangle.X, rectangle.Y + rectangle.Height));
					num = 19;
				}
				Brush brush;
				if (num == 5)
				{
					brush = new SolidBrush(color_0);
					num = 6;
				}
				if (num == 3)
				{
					bool flag;
					if (!flag)
					{
						break;
					}
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 21)
				{
					graphics_0.DrawLine(pen2, new Point(rectangle.X, rectangle.Y - 1), new Point(rectangle.X + gclass0_0.Padding.Left, rectangle.Y - 1));
					num = 22;
				}
				if (num == 8)
				{
					Brush brush2;
					pen = new Pen(brush2);
					num = 9;
				}
				if (num == 4)
				{
					this.Font = new Font("Consolas", 9f);
					num = 5;
				}
				if (num == 10)
				{
					sizeF = graphics_0.MeasureString(gclass0_0.Text, gclass0_0.Font);
					num = 11;
				}
				if (num == 14)
				{
					graphics_0.DrawLine(pen, new Point(rectangle.X + rectangle.Width - 1, rectangle.Y), new Point(rectangle.X + rectangle.Width - 1, rectangle.Y + rectangle.Height));
					num = 15;
				}
				if (num == 6)
				{
					Brush brush2 = new SolidBrush(color_1);
					num = 7;
				}
				if (num == 11)
				{
					rectangle = new Rectangle(gclass0_0.ClientRectangle.X, gclass0_0.ClientRectangle.Y + (int)(sizeF.Height / 2f), gclass0_0.ClientRectangle.Width - 1, gclass0_0.ClientRectangle.Height - (int)(sizeF.Height / 2f) - 1);
					num = 12;
				}
				if (num == 12)
				{
					graphics_0.DrawString(gclass0_0.Text, gclass0_0.Font, brush, (float)gclass0_0.Padding.Left, 0f);
					num = 13;
				}
				Brush brush3;
				if (num == 7)
				{
					brush3 = new SolidBrush(color_2);
					num = 8;
				}
				if (num == 16)
				{
					graphics_0.DrawLine(pen, new Point(rectangle.X, rectangle.Y), new Point(rectangle.X + gclass0_0.Padding.Left, rectangle.Y));
					num = 17;
				}
				if (num == 9)
				{
					pen2 = new Pen(brush3);
					num = 10;
				}
				if (num == 22)
				{
					graphics_0.DrawLine(pen2, new Point(rectangle.X + gclass0_0.Padding.Left + (int)sizeF.Width, rectangle.Y - 1), new Point(rectangle.X + rectangle.Width, rectangle.Y - 1));
					num = 23;
				}
				if (num == 2)
				{
					bool flag = gclass0_0 != null;
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 23);
		}

		// Token: 0x06000053 RID: 83 RVA: 0x000020B0 File Offset: 0x000002B0
		public GClass0()
		{
			<Module>.Class0.smethod_0();
			base..ctor();
		}
	}
}
