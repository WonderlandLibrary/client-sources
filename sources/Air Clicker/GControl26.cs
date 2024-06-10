using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x02000094 RID: 148
[DefaultEvent("CheckedChanged")]
public class GControl26 : Control
{
	// Token: 0x170001D3 RID: 467
	// (get) Token: 0x060006AF RID: 1711 RVA: 0x000202CC File Offset: 0x0001E4CC
	// (set) Token: 0x060006B0 RID: 1712 RVA: 0x000202E4 File Offset: 0x0001E4E4
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

	// Token: 0x170001D4 RID: 468
	// (get) Token: 0x060006B1 RID: 1713 RVA: 0x000202F0 File Offset: 0x0001E4F0
	// (set) Token: 0x060006B2 RID: 1714 RVA: 0x00020308 File Offset: 0x0001E508
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

	// Token: 0x170001D5 RID: 469
	// (get) Token: 0x060006B3 RID: 1715 RVA: 0x00020314 File Offset: 0x0001E514
	// (set) Token: 0x060006B4 RID: 1716 RVA: 0x0002032C File Offset: 0x0001E52C
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x170001D6 RID: 470
	// (get) Token: 0x060006B5 RID: 1717 RVA: 0x00020338 File Offset: 0x0001E538
	// (set) Token: 0x060006B6 RID: 1718 RVA: 0x00020350 File Offset: 0x0001E550
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x170001D7 RID: 471
	// (get) Token: 0x060006B7 RID: 1719 RVA: 0x0002035C File Offset: 0x0001E55C
	// (set) Token: 0x060006B8 RID: 1720 RVA: 0x00020374 File Offset: 0x0001E574
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

	// Token: 0x14000019 RID: 25
	// (add) Token: 0x060006B9 RID: 1721 RVA: 0x00020380 File Offset: 0x0001E580
	// (remove) Token: 0x060006BA RID: 1722 RVA: 0x000203BC File Offset: 0x0001E5BC
	public event GControl26.GDelegate12 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl26.GDelegate12 gdelegate = this.gdelegate12_0;
			GControl26.GDelegate12 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl26.GDelegate12 value2 = (GControl26.GDelegate12)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl26.GDelegate12>(ref this.gdelegate12_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl26.GDelegate12 gdelegate = this.gdelegate12_0;
			GControl26.GDelegate12 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl26.GDelegate12 value2 = (GControl26.GDelegate12)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl26.GDelegate12>(ref this.gdelegate12_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x170001D8 RID: 472
	// (get) Token: 0x060006BB RID: 1723 RVA: 0x000203F4 File Offset: 0x0001E5F4
	// (set) Token: 0x060006BC RID: 1724 RVA: 0x000203FC File Offset: 0x0001E5FC
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			this.method_0();
			GControl26.GDelegate12 gdelegate = this.gdelegate12_0;
			if (gdelegate != null)
			{
				gdelegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x060006BD RID: 1725 RVA: 0x00020430 File Offset: 0x0001E630
	protected virtual void OnClick(EventArgs e)
	{
		if (!this.bool_0)
		{
			this.Boolean_0 = true;
		}
		base.OnClick(e);
	}

	// Token: 0x060006BE RID: 1726 RVA: 0x0002044C File Offset: 0x0001E64C
	private void method_0()
	{
		if (base.IsHandleCreated && this.bool_0)
		{
			try
			{
				foreach (object obj in base.Parent.Controls)
				{
					Control control = (Control)obj;
					if (control != this && control is GControl26)
					{
						((GControl26)control).Boolean_0 = false;
						base.Invalidate();
					}
				}
			}
			finally
			{
				IEnumerator enumerator;
				if (enumerator is IDisposable)
				{
					(enumerator as IDisposable).Dispose();
				}
			}
		}
	}

	// Token: 0x060006BF RID: 1727 RVA: 0x000204E4 File Offset: 0x0001E6E4
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		this.method_0();
	}

	// Token: 0x060006C0 RID: 1728 RVA: 0x000204F4 File Offset: 0x0001E6F4
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 22;
	}

	// Token: 0x060006C1 RID: 1729 RVA: 0x00020508 File Offset: 0x0001E708
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x060006C2 RID: 1730 RVA: 0x00020520 File Offset: 0x0001E720
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x060006C3 RID: 1731 RVA: 0x00020538 File Offset: 0x0001E738
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x060006C4 RID: 1732 RVA: 0x00020550 File Offset: 0x0001E750
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x060006C5 RID: 1733 RVA: 0x00020568 File Offset: 0x0001E768
	public GControl26()
	{
		this.enum0_0 = Enum0.None;
		this.color_0 = Color.FromArgb(50, 49, 51);
		this.color_1 = Color.FromArgb(173, 173, 174);
		this.color_2 = Color.FromArgb(35, 35, 35);
		this.color_3 = Color.FromArgb(42, 42, 42);
		this.color_4 = Color.FromArgb(255, 255, 255);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Cursor = Cursors.Hand;
		base.Size = new Size(100, 22);
	}

	// Token: 0x060006C6 RID: 1734 RVA: 0x00020618 File Offset: 0x0001E818
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rect = new Rectangle(1, 1, base.Height - 2, base.Height - 2);
			Rectangle rect2 = new Rectangle(6, 6, base.Height - 12, base.Height - 12);
			Rectangle rectangle = new Rectangle(4, 3, 14, 14);
			Graphics graphics2 = graphics;
			graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics2.SmoothingMode = SmoothingMode.HighQuality;
			graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics2.Clear(Color.Transparent);
			graphics2.FillEllipse(new SolidBrush(this.color_3), rect);
			graphics2.DrawEllipse(new Pen(this.color_2, 2f), rect);
			if (this.Boolean_0)
			{
				Enum0 @enum = this.enum0_0;
				if (@enum == Enum0.Over)
				{
					graphics2.FillEllipse(new SolidBrush(this.color_0), new Rectangle(2, 2, base.Height - 4, base.Height - 4));
				}
				graphics2.FillEllipse(new SolidBrush(this.color_1), rect2);
			}
			else
			{
				Enum0 enum2 = this.enum0_0;
				if (enum2 == Enum0.Over)
				{
					graphics2.FillEllipse(new SolidBrush(this.color_0), new Rectangle(2, 2, base.Height - 4, base.Height - 4));
				}
			}
			graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_4), new Rectangle(24, 3, base.Width, base.Height), new StringFormat
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
	}

	// Token: 0x0400033A RID: 826
	private bool bool_0;

	// Token: 0x0400033B RID: 827
	private Enum0 enum0_0;

	// Token: 0x0400033C RID: 828
	private Color color_0;

	// Token: 0x0400033D RID: 829
	private Color color_1;

	// Token: 0x0400033E RID: 830
	private Color color_2;

	// Token: 0x0400033F RID: 831
	private Color color_3;

	// Token: 0x04000340 RID: 832
	private Color color_4;

	// Token: 0x04000341 RID: 833
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GControl26.GDelegate12 gdelegate12_0;

	// Token: 0x02000095 RID: 149
	// (Invoke) Token: 0x060006CA RID: 1738
	public delegate void GDelegate12(object sender);
}
