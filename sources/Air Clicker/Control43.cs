using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using Microsoft.VisualBasic;

// Token: 0x020000B5 RID: 181
internal class Control43 : Control
{
	// Token: 0x06000870 RID: 2160 RVA: 0x00027E84 File Offset: 0x00026084
	private static PointF smethod_0(StringFormat stringFormat_0, SizeF sizeF_0, SizeF sizeF_1)
	{
		PointF result;
		switch (stringFormat_0.Alignment)
		{
		case StringAlignment.Near:
			result.X = 2f;
			break;
		case StringAlignment.Center:
			result.X = (sizeF_0.Width - sizeF_1.Width) / 2f;
			break;
		case StringAlignment.Far:
			result.X = sizeF_0.Width - sizeF_1.Width - 2f;
			break;
		}
		switch (stringFormat_0.LineAlignment)
		{
		case StringAlignment.Near:
			result.Y = 2f;
			break;
		case StringAlignment.Center:
			result.Y = (sizeF_0.Height - sizeF_1.Height) / 2f;
			break;
		case StringAlignment.Far:
			result.Y = sizeF_0.Height - sizeF_1.Height - 2f;
			break;
		}
		return result;
	}

	// Token: 0x06000871 RID: 2161 RVA: 0x00027F5C File Offset: 0x0002615C
	private StringFormat method_0(ContentAlignment contentAlignment_1)
	{
		StringFormat stringFormat = new StringFormat();
		if (contentAlignment_1 > ContentAlignment.MiddleCenter)
		{
			if (contentAlignment_1 > ContentAlignment.BottomLeft)
			{
				if (contentAlignment_1 == ContentAlignment.BottomCenter)
				{
					stringFormat.LineAlignment = StringAlignment.Far;
					stringFormat.Alignment = StringAlignment.Center;
				}
				else if (contentAlignment_1 == ContentAlignment.BottomRight)
				{
					stringFormat.LineAlignment = StringAlignment.Far;
					stringFormat.Alignment = StringAlignment.Far;
				}
			}
			else if (contentAlignment_1 != ContentAlignment.MiddleRight)
			{
				if (contentAlignment_1 == ContentAlignment.BottomLeft)
				{
					stringFormat.LineAlignment = StringAlignment.Far;
					stringFormat.Alignment = StringAlignment.Near;
				}
			}
			else
			{
				stringFormat.LineAlignment = StringAlignment.Center;
				stringFormat.Alignment = StringAlignment.Far;
			}
		}
		else
		{
			switch (contentAlignment_1)
			{
			case ContentAlignment.TopLeft:
				stringFormat.LineAlignment = StringAlignment.Near;
				stringFormat.Alignment = StringAlignment.Near;
				break;
			case ContentAlignment.TopCenter:
				stringFormat.LineAlignment = StringAlignment.Near;
				stringFormat.Alignment = StringAlignment.Center;
				break;
			case (ContentAlignment)3:
				break;
			case ContentAlignment.TopRight:
				stringFormat.LineAlignment = StringAlignment.Near;
				stringFormat.Alignment = StringAlignment.Far;
				break;
			default:
				if (contentAlignment_1 != ContentAlignment.MiddleLeft)
				{
					if (contentAlignment_1 == ContentAlignment.MiddleCenter)
					{
						stringFormat.LineAlignment = StringAlignment.Center;
						stringFormat.Alignment = StringAlignment.Center;
					}
				}
				else
				{
					stringFormat.LineAlignment = StringAlignment.Center;
					stringFormat.Alignment = StringAlignment.Near;
				}
				break;
			}
		}
		return stringFormat;
	}

	// Token: 0x1700026B RID: 619
	// (get) Token: 0x06000872 RID: 2162 RVA: 0x00028064 File Offset: 0x00026264
	// (set) Token: 0x06000873 RID: 2163 RVA: 0x0002807C File Offset: 0x0002627C
	public Image Image_0
	{
		get
		{
			return this.image_0;
		}
		set
		{
			if (value == null)
			{
				this.size_0 = Size.Empty;
			}
			else
			{
				this.size_0 = value.Size;
			}
			this.image_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1700026C RID: 620
	// (get) Token: 0x06000874 RID: 2164 RVA: 0x000280AC File Offset: 0x000262AC
	protected Size Size_0
	{
		get
		{
			return this.size_0;
		}
	}

	// Token: 0x1700026D RID: 621
	// (get) Token: 0x06000875 RID: 2165 RVA: 0x000280C4 File Offset: 0x000262C4
	// (set) Token: 0x06000876 RID: 2166 RVA: 0x000280DC File Offset: 0x000262DC
	public ContentAlignment ContentAlignment_0
	{
		get
		{
			return this.contentAlignment_0;
		}
		set
		{
			this.contentAlignment_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1700026E RID: 622
	// (get) Token: 0x06000877 RID: 2167 RVA: 0x000280EC File Offset: 0x000262EC
	// (set) Token: 0x06000878 RID: 2168 RVA: 0x00028104 File Offset: 0x00026304
	public StringAlignment StringAlignment_0
	{
		get
		{
			return this.stringAlignment_0;
		}
		set
		{
			this.stringAlignment_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x1700026F RID: 623
	// (get) Token: 0x06000879 RID: 2169 RVA: 0x00028114 File Offset: 0x00026314
	// (set) Token: 0x0600087A RID: 2170 RVA: 0x0002812C File Offset: 0x0002632C
	public virtual Color ForeColor
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x0600087B RID: 2171 RVA: 0x0002813C File Offset: 0x0002633C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.int_0 = 0;
		base.Invalidate();
		base.OnMouseUp(e);
	}

	// Token: 0x0600087C RID: 2172 RVA: 0x00028154 File Offset: 0x00026354
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.int_0 = 1;
		base.Focus();
		base.Invalidate();
		base.OnMouseDown(e);
	}

	// Token: 0x0600087D RID: 2173 RVA: 0x00028174 File Offset: 0x00026374
	protected virtual void OnMouseLeave(EventArgs e)
	{
		this.int_0 = 0;
		base.Invalidate();
		base.OnMouseLeave(e);
	}

	// Token: 0x0600087E RID: 2174 RVA: 0x0002818C File Offset: 0x0002638C
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.Invalidate();
		base.OnTextChanged(e);
	}

	// Token: 0x0600087F RID: 2175 RVA: 0x0002819C File Offset: 0x0002639C
	public Control43()
	{
		this.stringAlignment_0 = StringAlignment.Center;
		this.color_0 = Color.FromArgb(150, 150, 150);
		this.contentAlignment_0 = ContentAlignment.MiddleLeft;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI", 12f);
		this.System.Windows.Forms.Control.ForeColor = Color.FromArgb(255, 255, 255);
		base.Size = new Size(146, 41);
		this.stringAlignment_0 = StringAlignment.Center;
		this.pen_0 = new Pen(Color.FromArgb(181, 41, 42));
		this.pen_1 = new Pen(Color.FromArgb(165, 37, 37));
	}

	// Token: 0x06000880 RID: 2176 RVA: 0x00028270 File Offset: 0x00026470
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		if (base.Width > 0 && base.Height > 0)
		{
			this.graphicsPath_0 = new GraphicsPath();
			this.rectangle_0 = new Rectangle(0, 0, base.Width, base.Height);
			this.linearGradientBrush_0 = new LinearGradientBrush(new Rectangle(0, 0, base.Width, base.Height), Color.FromArgb(181, 41, 42), Color.FromArgb(181, 41, 42), 90f);
			this.linearGradientBrush_1 = new LinearGradientBrush(new Rectangle(0, 0, base.Width, base.Height), Color.FromArgb(165, 37, 37), Color.FromArgb(165, 37, 37), 90f);
		}
		GraphicsPath graphicsPath = this.graphicsPath_0;
		graphicsPath.AddArc(0, 0, 10, 10, 180f, 90f);
		checked
		{
			graphicsPath.AddArc(base.Width - 11, 0, 10, 10, -90f, 90f);
			graphicsPath.AddArc(base.Width - 11, base.Height - 11, 10, 10, 0f, 90f);
			graphicsPath.AddArc(0, base.Height - 11, 10, 10, 90f, 90f);
			graphicsPath.CloseAllFigures();
			base.Invalidate();
		}
	}

	// Token: 0x06000881 RID: 2177 RVA: 0x000283D0 File Offset: 0x000265D0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Graphics graphics = e.Graphics;
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		PointF pointF = Control43.smethod_0(this.method_0(this.ContentAlignment_0), base.Size, this.Size_0);
		int num = this.int_0;
		if (num != 0)
		{
			if (num == 1)
			{
				graphics.FillPath(this.linearGradientBrush_1, this.graphicsPath_0);
				graphics.DrawPath(this.pen_1, this.graphicsPath_0);
				if (Information.IsNothing(this.Image_0))
				{
					graphics.DrawString(this.Text, this.Font, new SolidBrush(this.System.Windows.Forms.Control.ForeColor), this.rectangle_0, new StringFormat
					{
						Alignment = this.stringAlignment_0,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					graphics.DrawImage(this.image_0, pointF.X, pointF.Y, (float)this.Size_0.Width, (float)this.Size_0.Height);
					graphics.DrawString(this.Text, this.Font, new SolidBrush(this.System.Windows.Forms.Control.ForeColor), this.rectangle_0, new StringFormat
					{
						Alignment = this.stringAlignment_0,
						LineAlignment = StringAlignment.Center
					});
				}
			}
		}
		else
		{
			graphics.FillPath(this.linearGradientBrush_0, this.graphicsPath_0);
			graphics.DrawPath(this.pen_0, this.graphicsPath_0);
			if (Information.IsNothing(this.Image_0))
			{
				graphics.DrawString(this.Text, this.Font, new SolidBrush(this.System.Windows.Forms.Control.ForeColor), this.rectangle_0, new StringFormat
				{
					Alignment = this.stringAlignment_0,
					LineAlignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.DrawImage(this.image_0, pointF.X, pointF.Y, (float)this.Size_0.Width, (float)this.Size_0.Height);
				graphics.DrawString(this.Text, this.Font, new SolidBrush(this.System.Windows.Forms.Control.ForeColor), this.rectangle_0, new StringFormat
				{
					Alignment = this.stringAlignment_0,
					LineAlignment = StringAlignment.Center
				});
			}
		}
		base.OnPaint(e);
	}

	// Token: 0x0400040B RID: 1035
	private int int_0;

	// Token: 0x0400040C RID: 1036
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400040D RID: 1037
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x0400040E RID: 1038
	private LinearGradientBrush linearGradientBrush_1;

	// Token: 0x0400040F RID: 1039
	private Rectangle rectangle_0;

	// Token: 0x04000410 RID: 1040
	private Pen pen_0;

	// Token: 0x04000411 RID: 1041
	private Pen pen_1;

	// Token: 0x04000412 RID: 1042
	private Image image_0;

	// Token: 0x04000413 RID: 1043
	private Size size_0;

	// Token: 0x04000414 RID: 1044
	private StringAlignment stringAlignment_0;

	// Token: 0x04000415 RID: 1045
	private Color color_0;

	// Token: 0x04000416 RID: 1046
	private ContentAlignment contentAlignment_0;
}
