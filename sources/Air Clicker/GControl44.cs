using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000DD RID: 221
[DefaultEvent("CheckedChanged")]
public class GControl44 : Control
{
	// Token: 0x17000296 RID: 662
	// (get) Token: 0x06000966 RID: 2406 RVA: 0x0002C390 File Offset: 0x0002A590
	// (set) Token: 0x06000967 RID: 2407 RVA: 0x0002C3A8 File Offset: 0x0002A5A8
	public GControl44.GEnum17 GEnum17_0
	{
		get
		{
			return this.genum17_0;
		}
		set
		{
			this.genum17_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000968 RID: 2408 RVA: 0x0002C3B8 File Offset: 0x0002A5B8
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.genum16_0 = GControl44.GEnum16.Over;
		base.Invalidate();
	}

	// Token: 0x06000969 RID: 2409 RVA: 0x0002C3D0 File Offset: 0x0002A5D0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.genum16_0 = GControl44.GEnum16.Down;
		base.Invalidate();
	}

	// Token: 0x0600096A RID: 2410 RVA: 0x0002C3E8 File Offset: 0x0002A5E8
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.genum16_0 = GControl44.GEnum16.None;
		base.Invalidate();
	}

	// Token: 0x0600096B RID: 2411 RVA: 0x0002C400 File Offset: 0x0002A600
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.genum16_0 = GControl44.GEnum16.Over;
		base.Invalidate();
	}

	// Token: 0x0600096C RID: 2412 RVA: 0x0002C418 File Offset: 0x0002A618
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 18;
	}

	// Token: 0x0600096D RID: 2413 RVA: 0x0002C42C File Offset: 0x0002A62C
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Width = checked((int)Math.Round(unchecked((double)base.CreateGraphics().MeasureString(this.Text, this.Font).Width + 7.0 + (double)(checked(base.Height * 2)))));
		base.Invalidate();
	}

	// Token: 0x17000297 RID: 663
	// (get) Token: 0x0600096E RID: 2414 RVA: 0x0002C488 File Offset: 0x0002A688
	// (set) Token: 0x0600096F RID: 2415 RVA: 0x0002C490 File Offset: 0x0002A690
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
			GControl44.GDelegate23 gdelegate = this.gdelegate23_0;
			if (gdelegate != null)
			{
				gdelegate(this);
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000970 RID: 2416 RVA: 0x0002C4C4 File Offset: 0x0002A6C4
	protected virtual void OnClick(EventArgs e)
	{
		if (!this.bool_0)
		{
			this.Boolean_0 = true;
		}
		base.OnClick(e);
	}

	// Token: 0x14000028 RID: 40
	// (add) Token: 0x06000971 RID: 2417 RVA: 0x0002C4E0 File Offset: 0x0002A6E0
	// (remove) Token: 0x06000972 RID: 2418 RVA: 0x0002C51C File Offset: 0x0002A71C
	public event GControl44.GDelegate23 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl44.GDelegate23 gdelegate = this.gdelegate23_0;
			GControl44.GDelegate23 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl44.GDelegate23 value2 = (GControl44.GDelegate23)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl44.GDelegate23>(ref this.gdelegate23_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl44.GDelegate23 gdelegate = this.gdelegate23_0;
			GControl44.GDelegate23 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl44.GDelegate23 value2 = (GControl44.GDelegate23)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl44.GDelegate23>(ref this.gdelegate23_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x06000973 RID: 2419 RVA: 0x0002C558 File Offset: 0x0002A758
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		this.method_0();
	}

	// Token: 0x06000974 RID: 2420 RVA: 0x0002C568 File Offset: 0x0002A768
	private void method_0()
	{
		if (base.IsHandleCreated && this.bool_0)
		{
			try
			{
				foreach (object obj in base.Parent.Controls)
				{
					Control control = (Control)obj;
					if (control != this && control is GControl44)
					{
						((GControl44)control).Boolean_0 = false;
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

	// Token: 0x06000975 RID: 2421 RVA: 0x0002C5F8 File Offset: 0x0002A7F8
	public GControl44()
	{
		this.genum16_0 = GControl44.GEnum16.None;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.SupportsTransparentBackColor, true);
		this.GEnum17_0 = GControl44.GEnum17.Dark;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.ForeColor = Color.White;
		this.DoubleBuffered = true;
		base.Size = new Size(177, 18);
	}

	// Token: 0x06000976 RID: 2422 RVA: 0x0002C65C File Offset: 0x0002A85C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rect = new Rectangle(0, 0, base.Height - 1, base.Height - 1);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.Clear(this.BackColor);
			GControl44.GEnum17 genum = this.GEnum17_0;
			if (genum != GControl44.GEnum17.Dark)
			{
				if (genum == GControl44.GEnum17.Light)
				{
					graphics.FillEllipse(new SolidBrush(Color.FromArgb(215, Color.Black)), rect);
				}
			}
			else
			{
				graphics.FillEllipse(new SolidBrush(Color.FromArgb(215, Color.White)), rect);
			}
			if (this.Boolean_0)
			{
				GControl44.GEnum17 genum2 = this.GEnum17_0;
				if (genum2 != GControl44.GEnum17.Dark)
				{
					if (genum2 == GControl44.GEnum17.Light)
					{
						graphics.FillEllipse(new SolidBrush(Color.White), new Rectangle(4, 4, base.Height - 9, base.Height - 9));
					}
				}
				else
				{
					graphics.FillEllipse(new SolidBrush(Color.Black), new Rectangle(4, 4, base.Height - 9, base.Height - 9));
				}
			}
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this.ForeColor), new Point(22, 1), new StringFormat
			{
				Alignment = StringAlignment.Near,
				LineAlignment = StringAlignment.Near
			});
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
			{
				bitmap.Clone(),
				0,
				0
			}, null, null, null, true);
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x0400048E RID: 1166
	private GControl44.GEnum16 genum16_0;

	// Token: 0x0400048F RID: 1167
	private GControl44.GEnum17 genum17_0;

	// Token: 0x04000490 RID: 1168
	private bool bool_0;

	// Token: 0x04000491 RID: 1169
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GControl44.GDelegate23 gdelegate23_0;

	// Token: 0x020000DE RID: 222
	public enum GEnum16
	{
		// Token: 0x04000493 RID: 1171
		None,
		// Token: 0x04000494 RID: 1172
		Over,
		// Token: 0x04000495 RID: 1173
		Down
	}

	// Token: 0x020000DF RID: 223
	public enum GEnum17
	{
		// Token: 0x04000497 RID: 1175
		Dark,
		// Token: 0x04000498 RID: 1176
		Light
	}

	// Token: 0x020000E0 RID: 224
	// (Invoke) Token: 0x0600097A RID: 2426
	public delegate void GDelegate23(object sender);
}
