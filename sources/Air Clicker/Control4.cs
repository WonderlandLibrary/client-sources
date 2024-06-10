using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000017 RID: 23
internal class Control4 : Control
{
	// Token: 0x170000CE RID: 206
	// (get) Token: 0x0600021F RID: 543 RVA: 0x00008F78 File Offset: 0x00007178
	// (set) Token: 0x06000220 RID: 544 RVA: 0x00008F90 File Offset: 0x00007190
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			if (value >= 0)
			{
				this.int_0 = value;
				if (value > this.int_2)
				{
					this.int_2 = value;
				}
				if (value > this.int_1)
				{
					this.int_1 = value;
				}
				base.Invalidate();
				return;
			}
			throw new Exception("Property value is not valid.");
		}
	}

	// Token: 0x170000CF RID: 207
	// (get) Token: 0x06000221 RID: 545 RVA: 0x00008FE4 File Offset: 0x000071E4
	// (set) Token: 0x06000222 RID: 546 RVA: 0x00008FFC File Offset: 0x000071FC
	public int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value < 0)
			{
				throw new Exception("Property value is not valid.");
			}
			this.int_1 = value;
			if (value < this.int_2)
			{
				this.int_2 = value;
			}
			if (value < this.int_0)
			{
				this.int_0 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x170000D0 RID: 208
	// (get) Token: 0x06000223 RID: 547 RVA: 0x0000904C File Offset: 0x0000724C
	// (set) Token: 0x06000224 RID: 548 RVA: 0x00009064 File Offset: 0x00007264
	public int Int32_2
	{
		get
		{
			return this.int_2;
		}
		set
		{
			if (value > this.int_1 || value < this.int_0)
			{
				throw new Exception("Property value is not valid.");
			}
			this.int_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000225 RID: 549 RVA: 0x00009098 File Offset: 0x00007298
	private void method_0(int int_4)
	{
		checked
		{
			this.Int32_2 += int_4;
		}
	}

	// Token: 0x06000226 RID: 550 RVA: 0x000090A8 File Offset: 0x000072A8
	public Control4()
	{
		this.int_1 = 100;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(55, 55, 55));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(51, 181, 229));
	}

	// Token: 0x06000227 RID: 551 RVA: 0x00009124 File Offset: 0x00007324
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 0, base.Width - 1, base.Height - 1, 7);
			this.graphicsPath_1 = Class9.smethod_2(1, 1, base.Width - 3, base.Height - 3, 7);
			this.rectangle_0 = new Rectangle(0, 2, base.Width - 1, base.Height - 1);
			this.linearGradientBrush_0 = new LinearGradientBrush(this.rectangle_0, Color.FromArgb(45, 45, 45), Color.FromArgb(50, 50, 50), 90f);
			Class9.graphics_0.SetClip(this.graphicsPath_0);
			Class9.graphics_0.FillRectangle(this.linearGradientBrush_0, this.rectangle_0);
			this.int_3 = (int)Math.Round(unchecked(checked((double)(this.int_2 - this.int_0) / (double)(this.int_1 - this.int_0)) * (double)(checked(base.Width - 3))));
			if (this.int_3 > 1)
			{
				this.graphicsPath_2 = Class9.smethod_2(1, 1, this.int_3, base.Height - 3, 7);
				this.rectangle_1 = new Rectangle(1, 1, this.int_3, base.Height - 3);
				this.linearGradientBrush_1 = new LinearGradientBrush(this.rectangle_1, Color.FromArgb(51, 181, 229), Color.FromArgb(0, 153, 204), 90f);
				Class9.graphics_0.FillPath(this.linearGradientBrush_1, this.graphicsPath_2);
				Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_2);
				Class9.graphics_0.SetClip(this.graphicsPath_2);
				Class9.graphics_0.SmoothingMode = SmoothingMode.None;
				Class9.graphics_0.FillRectangle(this.solidBrush_0, this.rectangle_1.X, this.rectangle_1.Y + 1, this.rectangle_1.Width, this.rectangle_1.Height / 2);
				Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
				Class9.graphics_0.ResetClip();
			}
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_1);
		}
	}

	// Token: 0x04000095 RID: 149
	private int int_0;

	// Token: 0x04000096 RID: 150
	private int int_1;

	// Token: 0x04000097 RID: 151
	private int int_2;

	// Token: 0x04000098 RID: 152
	private GraphicsPath graphicsPath_0;

	// Token: 0x04000099 RID: 153
	private GraphicsPath graphicsPath_1;

	// Token: 0x0400009A RID: 154
	private GraphicsPath graphicsPath_2;

	// Token: 0x0400009B RID: 155
	private Rectangle rectangle_0;

	// Token: 0x0400009C RID: 156
	private Rectangle rectangle_1;

	// Token: 0x0400009D RID: 157
	private Pen pen_0;

	// Token: 0x0400009E RID: 158
	private Pen pen_1;

	// Token: 0x0400009F RID: 159
	private SolidBrush solidBrush_0;

	// Token: 0x040000A0 RID: 160
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x040000A1 RID: 161
	private LinearGradientBrush linearGradientBrush_1;

	// Token: 0x040000A2 RID: 162
	private int int_3;
}
