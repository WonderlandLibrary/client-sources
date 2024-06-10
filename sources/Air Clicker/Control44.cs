using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x020000B6 RID: 182
internal class Control44 : Control
{
	// Token: 0x17000270 RID: 624
	// (get) Token: 0x06000882 RID: 2178 RVA: 0x00028610 File Offset: 0x00026810
	// (set) Token: 0x06000883 RID: 2179 RVA: 0x00028628 File Offset: 0x00026828
	public Image Image_0
	{
		get
		{
			return this.image_0;
		}
		set
		{
			if (value != null)
			{
				this.size_0 = value.Size;
			}
			else
			{
				this.size_0 = Size.Empty;
			}
			this.image_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000271 RID: 625
	// (get) Token: 0x06000884 RID: 2180 RVA: 0x00028658 File Offset: 0x00026858
	protected Size Size_0
	{
		get
		{
			return this.size_0;
		}
	}

	// Token: 0x06000885 RID: 2181 RVA: 0x00028670 File Offset: 0x00026870
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(54, 54);
	}

	// Token: 0x06000886 RID: 2182 RVA: 0x00028688 File Offset: 0x00026888
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.color_0 = Color.FromArgb(181, 41, 42);
		this.Refresh();
	}

	// Token: 0x06000887 RID: 2183 RVA: 0x000286AC File Offset: 0x000268AC
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.color_0 = Color.FromArgb(66, 76, 85);
		this.Refresh();
	}

	// Token: 0x06000888 RID: 2184 RVA: 0x000286CC File Offset: 0x000268CC
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.color_0 = Color.FromArgb(153, 34, 34);
		base.Focus();
		this.Refresh();
	}

	// Token: 0x06000889 RID: 2185 RVA: 0x000286F8 File Offset: 0x000268F8
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.color_0 = Color.FromArgb(181, 41, 42);
		this.Refresh();
	}

	// Token: 0x0600088A RID: 2186 RVA: 0x0002871C File Offset: 0x0002691C
	private static PointF smethod_0(StringFormat stringFormat_0, SizeF sizeF_0, SizeF sizeF_1)
	{
		StringAlignment alignment = stringFormat_0.Alignment;
		PointF result;
		if (alignment == StringAlignment.Center)
		{
			result.X = (sizeF_0.Width - sizeF_1.Width) / 2f;
		}
		StringAlignment lineAlignment = stringFormat_0.LineAlignment;
		if (lineAlignment == StringAlignment.Center)
		{
			result.Y = (sizeF_0.Height - sizeF_1.Height) / 2f;
		}
		return result;
	}

	// Token: 0x0600088B RID: 2187 RVA: 0x00028780 File Offset: 0x00026980
	private StringFormat method_0(ContentAlignment contentAlignment_0)
	{
		StringFormat stringFormat = new StringFormat();
		if (contentAlignment_0 == ContentAlignment.MiddleCenter)
		{
			stringFormat.LineAlignment = StringAlignment.Center;
			stringFormat.Alignment = StringAlignment.Center;
		}
		return stringFormat;
	}

	// Token: 0x0600088C RID: 2188 RVA: 0x000287AC File Offset: 0x000269AC
	public Control44()
	{
		this.color_0 = Color.FromArgb(66, 76, 85);
		this.DoubleBuffered = true;
	}

	// Token: 0x0600088D RID: 2189 RVA: 0x000287CC File Offset: 0x000269CC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Graphics graphics = e.Graphics;
		graphics.Clear(base.Parent.BackColor);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		PointF pointF = Control44.smethod_0(this.method_0(ContentAlignment.MiddleCenter), base.Size, this.Size_0);
		graphics.FillEllipse(new SolidBrush(this.color_0), new Rectangle(0, 0, 53, 53));
		if (this.Image_0 != null)
		{
			graphics.DrawImage(this.image_0, pointF.X, pointF.Y, (float)this.Size_0.Width, (float)this.Size_0.Height);
		}
	}

	// Token: 0x04000417 RID: 1047
	private Image image_0;

	// Token: 0x04000418 RID: 1048
	private Size size_0;

	// Token: 0x04000419 RID: 1049
	private Color color_0;
}
