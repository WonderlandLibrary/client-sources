using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x0200008F RID: 143
[DefaultEvent("CheckedChanged")]
public class GControl23 : Control
{
	// Token: 0x170001BB RID: 443
	// (get) Token: 0x0600065F RID: 1631 RVA: 0x0001F13C File Offset: 0x0001D33C
	// (set) Token: 0x06000660 RID: 1632 RVA: 0x0001F154 File Offset: 0x0001D354
	[Category("Colours")]
	public Color Color_0
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

	// Token: 0x170001BC RID: 444
	// (get) Token: 0x06000661 RID: 1633 RVA: 0x0001F160 File Offset: 0x0001D360
	// (set) Token: 0x06000662 RID: 1634 RVA: 0x0001F178 File Offset: 0x0001D378
	[Category("Colours")]
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

	// Token: 0x170001BD RID: 445
	// (get) Token: 0x06000663 RID: 1635 RVA: 0x0001F184 File Offset: 0x0001D384
	// (set) Token: 0x06000664 RID: 1636 RVA: 0x0001F19C File Offset: 0x0001D39C
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x170001BE RID: 446
	// (get) Token: 0x06000665 RID: 1637 RVA: 0x0001F1A8 File Offset: 0x0001D3A8
	// (set) Token: 0x06000666 RID: 1638 RVA: 0x0001F1C0 File Offset: 0x0001D3C0
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x06000667 RID: 1639 RVA: 0x0001F1CC File Offset: 0x0001D3CC
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x170001BF RID: 447
	// (get) Token: 0x06000668 RID: 1640 RVA: 0x0001F1DC File Offset: 0x0001D3DC
	// (set) Token: 0x06000669 RID: 1641 RVA: 0x0001F1E4 File Offset: 0x0001D3E4
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

	// Token: 0x14000018 RID: 24
	// (add) Token: 0x0600066A RID: 1642 RVA: 0x0001F1F4 File Offset: 0x0001D3F4
	// (remove) Token: 0x0600066B RID: 1643 RVA: 0x0001F22C File Offset: 0x0001D42C
	public event GControl23.GDelegate11 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl23.GDelegate11 gdelegate = this.gdelegate11_0;
			GControl23.GDelegate11 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl23.GDelegate11 value2 = (GControl23.GDelegate11)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl23.GDelegate11>(ref this.gdelegate11_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl23.GDelegate11 gdelegate = this.gdelegate11_0;
			GControl23.GDelegate11 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl23.GDelegate11 value2 = (GControl23.GDelegate11)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl23.GDelegate11>(ref this.gdelegate11_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x0600066C RID: 1644 RVA: 0x0001F264 File Offset: 0x0001D464
	protected virtual void OnClick(EventArgs e)
	{
		this.bool_0 = !this.bool_0;
		GControl23.GDelegate11 gdelegate = this.gdelegate11_0;
		if (gdelegate != null)
		{
			gdelegate(this);
		}
		base.OnClick(e);
	}

	// Token: 0x0600066D RID: 1645 RVA: 0x0001F298 File Offset: 0x0001D498
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 22;
	}

	// Token: 0x0600066E RID: 1646 RVA: 0x0001F2AC File Offset: 0x0001D4AC
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x0600066F RID: 1647 RVA: 0x0001F2C4 File Offset: 0x0001D4C4
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x06000670 RID: 1648 RVA: 0x0001F2DC File Offset: 0x0001D4DC
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x06000671 RID: 1649 RVA: 0x0001F2F4 File Offset: 0x0001D4F4
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x06000672 RID: 1650 RVA: 0x0001F30C File Offset: 0x0001D50C
	public GControl23()
	{
		this.enum0_0 = Enum0.None;
		this.color_0 = Color.FromArgb(173, 173, 174);
		this.color_1 = Color.FromArgb(35, 35, 35);
		this.color_2 = Color.FromArgb(42, 42, 42);
		this.color_3 = Color.FromArgb(255, 255, 255);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Cursor = Cursors.Hand;
		base.Size = new Size(100, 22);
	}

	// Token: 0x06000673 RID: 1651 RVA: 0x0001F3AC File Offset: 0x0001D5AC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(0, 0, 20, 20);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		graphics2.FillRectangle(new SolidBrush(this.color_2), rect);
		graphics2.DrawRectangle(new Pen(this.color_1), new Rectangle(1, 1, 18, 18));
		Enum0 @enum = this.enum0_0;
		if (@enum == Enum0.Over)
		{
			graphics2.FillRectangle(new SolidBrush(Color.FromArgb(50, 49, 51)), rect);
			graphics2.DrawRectangle(new Pen(this.color_1), new Rectangle(1, 1, 18, 18));
		}
		if (this.Boolean_0)
		{
			Point[] points = new Point[]
			{
				new Point(4, 11),
				new Point(6, 8),
				new Point(9, 12),
				new Point(15, 3),
				new Point(17, 6),
				new Point(9, 16)
			};
			graphics2.FillPolygon(new SolidBrush(this.color_0), points);
		}
		graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_3), new Rectangle(24, 1, base.Width, base.Height), new StringFormat
		{
			Alignment = StringAlignment.Near,
			LineAlignment = StringAlignment.Near
		});
		base.OnPaint(e);
		graphics.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
		bitmap.Dispose();
	}

	// Token: 0x0400031D RID: 797
	private bool bool_0;

	// Token: 0x0400031E RID: 798
	private Enum0 enum0_0;

	// Token: 0x0400031F RID: 799
	private Color color_0;

	// Token: 0x04000320 RID: 800
	private Color color_1;

	// Token: 0x04000321 RID: 801
	private Color color_2;

	// Token: 0x04000322 RID: 802
	private Color color_3;

	// Token: 0x04000323 RID: 803
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl23.GDelegate11 gdelegate11_0;

	// Token: 0x02000090 RID: 144
	// (Invoke) Token: 0x06000677 RID: 1655
	public delegate void GDelegate11(object sender);
}
