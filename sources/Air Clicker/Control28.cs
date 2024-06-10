using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000040 RID: 64
internal class Control28 : Control
{
	// Token: 0x17000125 RID: 293
	// (get) Token: 0x06000391 RID: 913 RVA: 0x000113A0 File Offset: 0x0000F5A0
	// (set) Token: 0x06000392 RID: 914 RVA: 0x000113B8 File Offset: 0x0000F5B8
	[Category("Colors")]
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x17000126 RID: 294
	// (get) Token: 0x06000393 RID: 915 RVA: 0x000113C4 File Offset: 0x0000F5C4
	// (set) Token: 0x06000394 RID: 916 RVA: 0x000113DC File Offset: 0x0000F5DC
	[Category("Colors")]
	public Color Color_1
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

	// Token: 0x17000127 RID: 295
	// (get) Token: 0x06000395 RID: 917 RVA: 0x000113E8 File Offset: 0x0000F5E8
	// (set) Token: 0x06000396 RID: 918 RVA: 0x000113F0 File Offset: 0x0000F5F0
	[Category("Options")]
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
		}
	}

	// Token: 0x06000397 RID: 919 RVA: 0x000113FC File Offset: 0x0000F5FC
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x06000398 RID: 920 RVA: 0x00011414 File Offset: 0x0000F614
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000399 RID: 921 RVA: 0x0001142C File Offset: 0x0000F62C
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x0600039A RID: 922 RVA: 0x00011444 File Offset: 0x0000F644
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x0600039B RID: 923 RVA: 0x0001145C File Offset: 0x0000F65C
	public Control28()
	{
		this.bool_0 = false;
		this.enum2_0 = Enum2.None;
		this.color_0 = Class16.color_0;
		this.color_1 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(106, 32);
		this.BackColor = Color.Transparent;
		this.Font = new Font("Segoe UI", 12f);
		this.Cursor = Cursors.Hand;
	}

	// Token: 0x0600039C RID: 924 RVA: 0x000114F0 File Offset: 0x0000F6F0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			Rectangle rectangle = new Rectangle(0, 0, this.int_0, this.int_1);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			switch (this.enum2_0)
			{
			case Enum2.None:
				if (this.Boolean_0)
				{
					path = Class16.smethod_0(rectangle, 6);
					graphics_.FillPath(new SolidBrush(this.color_0), path);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
				}
				else
				{
					graphics_.FillRectangle(new SolidBrush(this.color_0), rectangle);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
				}
				break;
			case Enum2.Over:
				if (!this.Boolean_0)
				{
					graphics_.FillRectangle(new SolidBrush(this.color_0), rectangle);
					graphics_.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.White)), rectangle);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
				}
				else
				{
					path = Class16.smethod_0(rectangle, 6);
					graphics_.FillPath(new SolidBrush(this.color_0), path);
					graphics_.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), path);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
				}
				break;
			case Enum2.Down:
				if (!this.Boolean_0)
				{
					graphics_.FillRectangle(new SolidBrush(this.color_0), rectangle);
					graphics_.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.Black)), rectangle);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
				}
				else
				{
					path = Class16.smethod_0(rectangle, 6);
					graphics_.FillPath(new SolidBrush(this.color_0), path);
					graphics_.FillPath(new SolidBrush(Color.FromArgb(20, Color.Black)), path);
					graphics_.DrawString(this.Text, this.Font, new SolidBrush(this.color_1), rectangle, Class16.stringFormat_1);
				}
				break;
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x040001E1 RID: 481
	private int int_0;

	// Token: 0x040001E2 RID: 482
	private int int_1;

	// Token: 0x040001E3 RID: 483
	private bool bool_0;

	// Token: 0x040001E4 RID: 484
	private Enum2 enum2_0;

	// Token: 0x040001E5 RID: 485
	private Color color_0;

	// Token: 0x040001E6 RID: 486
	private Color color_1;
}
