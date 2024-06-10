using System;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000008 RID: 8
	public class GControl1 : Control
	{
		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000047 RID: 71 RVA: 0x00014C78 File Offset: 0x00012E78
		// (set) Token: 0x06000048 RID: 72 RVA: 0x00014CE0 File Offset: 0x00012EE0
		public Color Color_0
		{
			[CompilerGenerated]
			get
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
				return this.color_0;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.color_0 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000049 RID: 73 RVA: 0x00014D60 File Offset: 0x00012F60
		// (set) Token: 0x0600004A RID: 74 RVA: 0x00014DC8 File Offset: 0x00012FC8
		public Color Color_1
		{
			[CompilerGenerated]
			get
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
				return this.color_1;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.color_1 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x17000003 RID: 3
		// (get) Token: 0x0600004B RID: 75 RVA: 0x00014E48 File Offset: 0x00013048
		// (set) Token: 0x0600004C RID: 76 RVA: 0x00014EB0 File Offset: 0x000130B0
		public Pen Pen_0
		{
			[CompilerGenerated]
			get
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
				return this.pen_0;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.pen_0 = value;
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
		}

		// Token: 0x0600004D RID: 77 RVA: 0x00014F30 File Offset: 0x00013130
		public GControl1()
		{
			<Module>.Class0.smethod_0();
			this.color_0 = Color.FromArgb(23, 23, 23);
			this.color_1 = Color.FromArgb(25, 25, 25);
			this.bool_0 = false;
			this.stringFormat_0 = new StringFormat();
			this.pen_0 = new Pen(Color.FromArgb(50, 50, 50));
			base..ctor();
			this.stringFormat_0.Alignment = StringAlignment.Center;
			this.stringFormat_0.LineAlignment = StringAlignment.Center;
			base.Paint += this.GControl1_Paint;
			this.Font = new Font("Verdana", 7f);
		}

		// Token: 0x0600004E RID: 78 RVA: 0x00014FD4 File Offset: 0x000131D4
		private void GControl1_Paint(object sender, PaintEventArgs e)
		{
			int num = 0;
			Rectangle rectangle;
			do
			{
				Rectangle rect;
				if (num == 3)
				{
					rect = new Rectangle(1, 1, base.Width - 3, base.Height - 3);
					num = 4;
				}
				if (num == 2)
				{
					rectangle = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
					num = 3;
				}
				LinearGradientBrush brush;
				if (num == 4)
				{
					brush = new LinearGradientBrush(rectangle, this.Color_1, this.Color_0, 90f);
					num = 5;
				}
				if (num == 5)
				{
					e.Graphics.FillRectangle(brush, rectangle);
					num = 6;
				}
				if (num == 6)
				{
					e.Graphics.DrawRectangle(Pens.Black, rectangle);
					num = 7;
				}
				if (num == 7)
				{
					e.Graphics.DrawRectangle(this.Pen_0, rect);
					num = 8;
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
			while (num != 8);
			e.Graphics.DrawString(this.Text, this.Font, this.bool_0 ? Brushes.White : Brushes.White, rectangle, this.stringFormat_0);
		}

		// Token: 0x0600004F RID: 79 RVA: 0x000151D4 File Offset: 0x000133D4
		protected virtual void OnMouseDown(MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					base.OnMouseDown(e);
					num = 3;
				}
				if (num == 4)
				{
					base.Refresh();
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 3)
				{
					this.bool_0 = true;
					num = 4;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 5);
		}

		// Token: 0x06000050 RID: 80 RVA: 0x000152A0 File Offset: 0x000134A0
		protected virtual void OnMouseUp(MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					base.OnMouseUp(e);
					num = 3;
				}
				if (num == 3)
				{
					this.bool_0 = false;
					num = 4;
				}
				if (num == 4)
				{
					base.Refresh();
					num = 5;
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
			while (num != 5);
		}

		// Token: 0x04000058 RID: 88
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Color color_0;

		// Token: 0x04000059 RID: 89
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private Color color_1;

		// Token: 0x0400005A RID: 90
		private bool bool_0;

		// Token: 0x0400005B RID: 91
		private StringFormat stringFormat_0;

		// Token: 0x0400005C RID: 92
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Pen pen_0;
	}
}
