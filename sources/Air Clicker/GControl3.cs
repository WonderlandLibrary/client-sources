using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x02000063 RID: 99
public class GControl3 : Control
{
	// Token: 0x1700017A RID: 378
	// (get) Token: 0x060004E6 RID: 1254 RVA: 0x00018454 File Offset: 0x00016654
	// (set) Token: 0x060004E7 RID: 1255 RVA: 0x0001845C File Offset: 0x0001665C
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

	// Token: 0x060004E8 RID: 1256 RVA: 0x0001846C File Offset: 0x0001666C
	public GControl3()
	{
		this.enum8_0 = Enum8.None;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(180, 25);
	}

	// Token: 0x060004E9 RID: 1257 RVA: 0x000184AC File Offset: 0x000166AC
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060004EA RID: 1258 RVA: 0x000184BC File Offset: 0x000166BC
	protected virtual void OnClick(EventArgs e)
	{
		if (!this.Boolean_0)
		{
			this.Boolean_0 = true;
		}
		try
		{
			foreach (GControl3 gcontrol in base.Parent.Controls.OfType<GControl3>().Where(new Func<GControl3, bool>(this.method_0)).Where((GControl3._Closure$__.$I7-1 == null) ? (GControl3._Closure$__.$I7-1 = new Func<GControl3, bool>(GControl3._Closure$__.$I.method_0)) : GControl3._Closure$__.$I7-1))
			{
				gcontrol.Boolean_0 = false;
			}
		}
		finally
		{
			IEnumerator<GControl3> enumerator;
			if (enumerator != null)
			{
				enumerator.Dispose();
			}
		}
		base.OnClick(e);
	}

	// Token: 0x060004EB RID: 1259 RVA: 0x00018568 File Offset: 0x00016768
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum8_0 = Enum8.Down;
		base.Invalidate();
	}

	// Token: 0x060004EC RID: 1260 RVA: 0x00018580 File Offset: 0x00016780
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x060004ED RID: 1261 RVA: 0x00018598 File Offset: 0x00016798
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x060004EE RID: 1262 RVA: 0x000185B0 File Offset: 0x000167B0
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum8_0 = Enum8.None;
		base.Invalidate();
	}

	// Token: 0x060004EF RID: 1263 RVA: 0x000185C8 File Offset: 0x000167C8
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(3, 3, 18, 18);
		Rectangle rect2 = new Rectangle(4, 4, 17, 17);
		Rectangle rect3 = new Rectangle(8, 8, 8, 8);
		base.OnPaint(e);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
		graphics.Clear(this.BackColor);
		Enum8 @enum = this.enum8_0;
		if (@enum == Enum8.Over)
		{
			rect.Inflate(1, 1);
		}
		else if (@enum == Enum8.Down)
		{
			rect.Inflate(-1, -1);
		}
		graphics.DrawString(this.Text, new Font("Segoe UI", 11f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(25, 4, base.Width, 16), new StringFormat
		{
			Alignment = StringAlignment.Near,
			LineAlignment = StringAlignment.Center
		});
		if (this.Boolean_0)
		{
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(0, 0, 0)), rect);
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(40, 37, 33)), rect2);
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(245, 245, 245)), rect3);
		}
		else
		{
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(0, 0, 0)), rect);
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(40, 37, 33)), rect2);
		}
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x060004F0 RID: 1264 RVA: 0x00018760 File Offset: 0x00016960
	[CompilerGenerated]
	private bool method_0(GControl3 gcontrol3_0)
	{
		return gcontrol3_0.Handle != this.method_1();
	}

	// Token: 0x060004F1 RID: 1265 RVA: 0x00018774 File Offset: 0x00016974
	IntPtr method_1()
	{
		return base.Handle;
	}

	// Token: 0x0400029F RID: 671
	private Enum8 enum8_0;

	// Token: 0x040002A0 RID: 672
	private bool bool_0;
}
