using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200004E RID: 78
internal class Control35 : Control
{
	// Token: 0x17000140 RID: 320
	// (get) Token: 0x0600041A RID: 1050 RVA: 0x00013F40 File Offset: 0x00012140
	// (set) Token: 0x0600041B RID: 1051 RVA: 0x00013F58 File Offset: 0x00012158
	[Category("Control")]
	public int Int32_0
	{
		get
		{
			return this.int_3;
		}
		set
		{
			if (value < this.int_2)
			{
				this.int_2 = value;
			}
			this.int_3 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000141 RID: 321
	// (get) Token: 0x0600041C RID: 1052 RVA: 0x00013F88 File Offset: 0x00012188
	// (set) Token: 0x0600041D RID: 1053 RVA: 0x00013FAC File Offset: 0x000121AC
	[Category("Control")]
	public int Int32_1
	{
		get
		{
			int result;
			if (this.int_2 == 0)
			{
				result = 0;
			}
			else
			{
				result = this.int_2;
			}
			return result;
		}
		set
		{
			int num = value;
			if (num > this.int_3)
			{
				value = this.int_3;
				base.Invalidate();
			}
			this.int_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000142 RID: 322
	// (get) Token: 0x0600041E RID: 1054 RVA: 0x00013FE4 File Offset: 0x000121E4
	// (set) Token: 0x0600041F RID: 1055 RVA: 0x00013FFC File Offset: 0x000121FC
	[Category("Colors")]
	public Color Color_0
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x17000143 RID: 323
	// (get) Token: 0x06000420 RID: 1056 RVA: 0x00014008 File Offset: 0x00012208
	// (set) Token: 0x06000421 RID: 1057 RVA: 0x00014020 File Offset: 0x00012220
	[Category("Colors")]
	public Color Color_1
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x06000422 RID: 1058 RVA: 0x0001402C File Offset: 0x0001222C
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 42;
	}

	// Token: 0x06000423 RID: 1059 RVA: 0x00014040 File Offset: 0x00012240
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
		base.Height = 42;
	}

	// Token: 0x06000424 RID: 1060 RVA: 0x00014050 File Offset: 0x00012250
	public void method_0(int int_4)
	{
		checked
		{
			this.Int32_1 += int_4;
		}
	}

	// Token: 0x06000425 RID: 1061 RVA: 0x00014060 File Offset: 0x00012260
	public Control35()
	{
		this.int_2 = 0;
		this.int_3 = 100;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Class16.color_0;
		this.color_2 = Color.FromArgb(23, 148, 92);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(60, 70, 73);
		base.Height = 42;
	}

	// Token: 0x06000426 RID: 1062 RVA: 0x000140E0 File Offset: 0x000122E0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Rectangle rect = new Rectangle(0, 24, this.int_0, this.int_1);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath path = new GraphicsPath();
			GraphicsPath path2 = new GraphicsPath();
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			int num = (int)Math.Round(unchecked((double)this.int_2 / (double)this.int_3 * (double)base.Width));
			int int32_ = this.Int32_1;
			if (int32_ != 0)
			{
				if (int32_ != 100)
				{
					graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
					graphicsPath.AddRectangle(new Rectangle(0, 24, num - 1, this.int_1 - 1));
					graphics_.FillPath(new SolidBrush(this.color_1), graphicsPath);
					HatchBrush brush = new HatchBrush(HatchStyle.Plaid, this.color_2, this.color_1);
					graphics_.FillRectangle(brush, new Rectangle(0, 24, num - 1, this.int_1 - 1));
					Rectangle rectangle_ = new Rectangle(num - 18, 0, 34, 16);
					path = Class16.smethod_0(rectangle_, 4);
					graphics_.FillPath(new SolidBrush(this.color_0), path);
					path2 = Class16.smethod_2(num - 9, 16, true);
					graphics_.FillPath(new SolidBrush(this.color_0), path2);
					graphics_.DrawString(Conversions.ToString(this.Int32_1), new Font("Segoe UI", 10f), new SolidBrush(this.color_1), new Rectangle(num - 11, -2, this.int_0, this.int_1), Class16.stringFormat_0);
				}
				else
				{
					graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
					graphics_.FillRectangle(new SolidBrush(this.color_1), new Rectangle(0, 24, num - 1, this.int_1 - 1));
				}
			}
			else
			{
				graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
				graphics_.FillRectangle(new SolidBrush(this.color_1), new Rectangle(0, 24, num - 1, this.int_1 - 1));
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x04000232 RID: 562
	private int int_0;

	// Token: 0x04000233 RID: 563
	private int int_1;

	// Token: 0x04000234 RID: 564
	private int int_2;

	// Token: 0x04000235 RID: 565
	private int int_3;

	// Token: 0x04000236 RID: 566
	private Color color_0;

	// Token: 0x04000237 RID: 567
	private Color color_1;

	// Token: 0x04000238 RID: 568
	private Color color_2;
}
