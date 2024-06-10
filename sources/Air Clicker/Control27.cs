using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200003F RID: 63
internal class Control27 : ContainerControl
{
	// Token: 0x17000123 RID: 291
	// (get) Token: 0x0600038B RID: 907 RVA: 0x00011170 File Offset: 0x0000F370
	// (set) Token: 0x0600038C RID: 908 RVA: 0x00011188 File Offset: 0x0000F388
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

	// Token: 0x17000124 RID: 292
	// (get) Token: 0x0600038D RID: 909 RVA: 0x00011194 File Offset: 0x0000F394
	// (set) Token: 0x0600038E RID: 910 RVA: 0x0001119C File Offset: 0x0000F39C
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

	// Token: 0x0600038F RID: 911 RVA: 0x000111A8 File Offset: 0x0000F3A8
	public Control27()
	{
		this.bool_0 = true;
		this.color_0 = Color.FromArgb(60, 70, 73);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Size = new Size(240, 180);
		this.Font = new Font("Segoe ui", 10f);
	}

	// Token: 0x06000390 RID: 912 RVA: 0x0001121C File Offset: 0x0000F41C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			GraphicsPath path2 = new GraphicsPath();
			GraphicsPath path3 = new GraphicsPath();
			Rectangle rectangle_ = new Rectangle(8, 8, this.int_0 - 16, this.int_1 - 16);
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			path = Class16.smethod_0(rectangle_, 8);
			graphics_.FillPath(new SolidBrush(this.color_0), path);
			path2 = Class16.smethod_2(28, 2, false);
			graphics_.FillPath(new SolidBrush(this.color_0), path2);
			path3 = Class16.smethod_2(28, 8, true);
			graphics_.FillPath(new SolidBrush(Color.FromArgb(60, 70, 73)), path3);
			if (this.Boolean_0)
			{
				graphics_.DrawString(this.Text, this.Font, new SolidBrush(Class16.color_0), new Rectangle(16, 16, this.int_0, this.int_1), Class16.stringFormat_0);
			}
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x040001DD RID: 477
	private int int_0;

	// Token: 0x040001DE RID: 478
	private int int_1;

	// Token: 0x040001DF RID: 479
	private bool bool_0;

	// Token: 0x040001E0 RID: 480
	private Color color_0;
}
