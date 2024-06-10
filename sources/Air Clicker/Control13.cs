using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000025 RID: 37
internal class Control13 : ContainerControl
{
	// Token: 0x170000E0 RID: 224
	// (get) Token: 0x06000280 RID: 640 RVA: 0x0000BAB0 File Offset: 0x00009CB0
	// (set) Token: 0x06000281 RID: 641 RVA: 0x0000BAB8 File Offset: 0x00009CB8
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x170000E1 RID: 225
	// (get) Token: 0x06000282 RID: 642 RVA: 0x0000BAC8 File Offset: 0x00009CC8
	// (set) Token: 0x06000283 RID: 643 RVA: 0x0000BAE0 File Offset: 0x00009CE0
	public string String_0
	{
		get
		{
			return this.string_0;
		}
		set
		{
			this.string_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x170000E2 RID: 226
	// (get) Token: 0x06000284 RID: 644 RVA: 0x0000BAF0 File Offset: 0x00009CF0
	// (set) Token: 0x06000285 RID: 645 RVA: 0x0000BB08 File Offset: 0x00009D08
	public string String_1
	{
		get
		{
			return this.string_1;
		}
		set
		{
			this.string_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000286 RID: 646 RVA: 0x0000BB18 File Offset: 0x00009D18
	public Control13()
	{
		this.string_0 = "GroupBox";
		this.string_1 = "Details";
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.font_0 = new Font("Verdana", 10f);
		this.font_1 = new Font("Verdana", 6.5f);
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(55, 55, 55));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(51, 181, 229));
	}

	// Token: 0x06000287 RID: 647 RVA: 0x0000BBCC File Offset: 0x00009DCC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 0, base.Width - 1, base.Height - 1, 7);
			this.graphicsPath_1 = Class9.smethod_2(1, 1, base.Width - 3, base.Height - 3, 7);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_1);
			this.sizeF_0 = Class9.graphics_0.MeasureString(this.string_0, this.font_0, base.Width, StringFormat.GenericTypographic);
			this.sizeF_1 = Class9.graphics_0.MeasureString(this.string_1, this.font_1, base.Width, StringFormat.GenericTypographic);
			Class9.graphics_0.DrawString(this.string_0, this.font_0, Brushes.Black, 6f, 6f);
			Class9.graphics_0.DrawString(this.string_0, this.font_0, this.solidBrush_0, 5f, 5f);
		}
		this.pointF_0 = new PointF(6f, this.sizeF_0.Height + 4f);
		Class9.graphics_0.DrawString(this.string_1, this.font_1, Brushes.Black, this.pointF_0.X + 1f, this.pointF_0.Y + 1f);
		Class9.graphics_0.DrawString(this.string_1, this.font_1, Brushes.WhiteSmoke, this.pointF_0.X, this.pointF_0.Y);
		checked
		{
			if (this.bool_0)
			{
				int num = (int)Math.Round((double)(unchecked(this.pointF_0.Y + this.sizeF_1.Height))) + 8;
				Class9.graphics_0.DrawLine(this.pen_0, 4, num, base.Width - 5, num);
				Class9.graphics_0.DrawLine(this.pen_1, 4, num + 1, base.Width - 5, num + 1);
			}
		}
	}

	// Token: 0x04000109 RID: 265
	private bool bool_0;

	// Token: 0x0400010A RID: 266
	private string string_0;

	// Token: 0x0400010B RID: 267
	private string string_1;

	// Token: 0x0400010C RID: 268
	private Font font_0;

	// Token: 0x0400010D RID: 269
	private Font font_1;

	// Token: 0x0400010E RID: 270
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400010F RID: 271
	private GraphicsPath graphicsPath_1;

	// Token: 0x04000110 RID: 272
	private PointF pointF_0;

	// Token: 0x04000111 RID: 273
	private SizeF sizeF_0;

	// Token: 0x04000112 RID: 274
	private SizeF sizeF_1;

	// Token: 0x04000113 RID: 275
	private Pen pen_0;

	// Token: 0x04000114 RID: 276
	private Pen pen_1;

	// Token: 0x04000115 RID: 277
	private SolidBrush solidBrush_0;
}
