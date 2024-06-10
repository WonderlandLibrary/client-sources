using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200008E RID: 142
public class GControl22 : Control
{
	// Token: 0x0600064A RID: 1610 RVA: 0x0001EB8C File Offset: 0x0001CD8C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x0600064B RID: 1611 RVA: 0x0001EBA4 File Offset: 0x0001CDA4
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x0600064C RID: 1612 RVA: 0x0001EBBC File Offset: 0x0001CDBC
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x0600064D RID: 1613 RVA: 0x0001EBD4 File Offset: 0x0001CDD4
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x170001B4 RID: 436
	// (get) Token: 0x0600064E RID: 1614 RVA: 0x0001EBEC File Offset: 0x0001CDEC
	// (set) Token: 0x0600064F RID: 1615 RVA: 0x0001EC04 File Offset: 0x0001CE04
	[Category("Colours")]
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

	// Token: 0x170001B5 RID: 437
	// (get) Token: 0x06000650 RID: 1616 RVA: 0x0001EC10 File Offset: 0x0001CE10
	// (set) Token: 0x06000651 RID: 1617 RVA: 0x0001EC28 File Offset: 0x0001CE28
	[Category("Colours")]
	public Color Color_1
	{
		get
		{
			return this.color_3;
		}
		set
		{
			this.color_3 = value;
		}
	}

	// Token: 0x170001B6 RID: 438
	// (get) Token: 0x06000652 RID: 1618 RVA: 0x0001EC34 File Offset: 0x0001CE34
	// (set) Token: 0x06000653 RID: 1619 RVA: 0x0001EC4C File Offset: 0x0001CE4C
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x170001B7 RID: 439
	// (get) Token: 0x06000654 RID: 1620 RVA: 0x0001EC58 File Offset: 0x0001CE58
	// (set) Token: 0x06000655 RID: 1621 RVA: 0x0001EC70 File Offset: 0x0001CE70
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x170001B8 RID: 440
	// (get) Token: 0x06000656 RID: 1622 RVA: 0x0001EC7C File Offset: 0x0001CE7C
	// (set) Token: 0x06000657 RID: 1623 RVA: 0x0001EC94 File Offset: 0x0001CE94
	[Category("Colours")]
	public Color Color_4
	{
		get
		{
			return this.color_4;
		}
		set
		{
			this.color_4 = value;
		}
	}

	// Token: 0x170001B9 RID: 441
	// (get) Token: 0x06000658 RID: 1624 RVA: 0x0001ECA0 File Offset: 0x0001CEA0
	// (set) Token: 0x06000659 RID: 1625 RVA: 0x0001ECB8 File Offset: 0x0001CEB8
	[Category("Colours")]
	public Color Color_5
	{
		get
		{
			return this.color_5;
		}
		set
		{
			this.color_5 = value;
		}
	}

	// Token: 0x170001BA RID: 442
	// (get) Token: 0x0600065A RID: 1626 RVA: 0x0001ECC4 File Offset: 0x0001CEC4
	// (set) Token: 0x0600065B RID: 1627 RVA: 0x0001ECDC File Offset: 0x0001CEDC
	[Category("Colours")]
	public Color Color_6
	{
		get
		{
			return this.color_6;
		}
		set
		{
			this.color_6 = value;
		}
	}

	// Token: 0x0600065C RID: 1628 RVA: 0x0001ECE8 File Offset: 0x0001CEE8
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(50, 50);
	}

	// Token: 0x0600065D RID: 1629 RVA: 0x0001ED00 File Offset: 0x0001CF00
	public GControl22()
	{
		this.enum0_0 = Enum0.None;
		this.color_0 = Color.FromArgb(43, 43, 43);
		this.color_1 = Color.FromArgb(235, 233, 234);
		this.color_2 = Color.FromArgb(170, 170, 170);
		this.color_3 = Color.FromArgb(35, 35, 35);
		this.color_4 = Color.FromArgb(0, 130, 169);
		this.color_5 = Color.FromArgb(0, 145, 184);
		this.color_6 = Color.FromArgb(0, 160, 199);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(50, 50);
		this.BackColor = Color.Transparent;
	}

	// Token: 0x0600065E RID: 1630 RVA: 0x0001EDE0 File Offset: 0x0001CFE0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Height, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		new GraphicsPath();
		new GraphicsPath();
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		Point[] points = new Point[]
		{
			new Point(18, 22),
			new Point(28, 22),
			new Point(28, 18),
			new Point(34, 25),
			new Point(28, 32),
			new Point(28, 28),
			new Point(18, 28)
		};
		checked
		{
			switch (this.enum0_0)
			{
			case Enum0.None:
				graphics2.FillEllipse(new SolidBrush(Color.FromArgb(56, 56, 56)), new Rectangle(3, 3, base.Width - 3 - 3, base.Height - 3 - 3));
				graphics2.DrawArc(new Pen(new SolidBrush(this.color_0), 4f), 3, 3, base.Width - 3 - 3, base.Height - 3 - 3, -90, 360);
				graphics2.DrawEllipse(new Pen(this.color_3), new Rectangle(1, 1, base.Height - 3, base.Height - 3));
				graphics2.FillEllipse(new SolidBrush(this.color_6), new Rectangle(5, 5, base.Height - 11, base.Height - 11));
				graphics2.FillPolygon(new SolidBrush(this.color_1), points);
				graphics2.DrawPolygon(new Pen(this.color_2), points);
				break;
			case Enum0.Over:
				graphics2.DrawArc(new Pen(new SolidBrush(this.color_0), 4f), 3, 3, base.Width - 3 - 3, base.Height - 3 - 3, -90, 360);
				graphics2.DrawEllipse(new Pen(this.color_3), new Rectangle(1, 1, base.Height - 3, base.Height - 3));
				graphics2.FillEllipse(new SolidBrush(this.color_4), new Rectangle(6, 6, base.Height - 13, base.Height - 13));
				graphics2.FillPolygon(new SolidBrush(this.color_1), points);
				graphics2.DrawPolygon(new Pen(this.color_2), points);
				break;
			case Enum0.Down:
				graphics2.DrawArc(new Pen(new SolidBrush(this.color_0), 4f), 3, 3, base.Width - 3 - 3, base.Height - 3 - 3, -90, 360);
				graphics2.DrawEllipse(new Pen(this.color_3), new Rectangle(1, 1, base.Height - 3, base.Height - 3));
				graphics2.FillEllipse(new SolidBrush(this.color_5), new Rectangle(6, 6, base.Height - 13, base.Height - 13));
				graphics2.FillPolygon(new SolidBrush(this.color_1), points);
				graphics2.DrawPolygon(new Pen(this.color_2), points);
				break;
			}
			base.OnPaint(e);
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000315 RID: 789
	private Enum0 enum0_0;

	// Token: 0x04000316 RID: 790
	private Color color_0;

	// Token: 0x04000317 RID: 791
	private Color color_1;

	// Token: 0x04000318 RID: 792
	private Color color_2;

	// Token: 0x04000319 RID: 793
	private Color color_3;

	// Token: 0x0400031A RID: 794
	private Color color_4;

	// Token: 0x0400031B RID: 795
	private Color color_5;

	// Token: 0x0400031C RID: 796
	private Color color_6;
}
