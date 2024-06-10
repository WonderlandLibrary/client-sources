using System;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000CD RID: 205
public class GControl42 : Control
{
	// Token: 0x14000024 RID: 36
	// (add) Token: 0x06000922 RID: 2338 RVA: 0x0002AFB0 File Offset: 0x000291B0
	// (remove) Token: 0x06000923 RID: 2339 RVA: 0x0002AFEC File Offset: 0x000291EC
	public event GControl42.GDelegate19 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl42.GDelegate19 gdelegate = this.gdelegate19_0;
			GControl42.GDelegate19 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl42.GDelegate19 value2 = (GControl42.GDelegate19)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl42.GDelegate19>(ref this.gdelegate19_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl42.GDelegate19 gdelegate = this.gdelegate19_0;
			GControl42.GDelegate19 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl42.GDelegate19 value2 = (GControl42.GDelegate19)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl42.GDelegate19>(ref this.gdelegate19_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x1700028C RID: 652
	// (get) Token: 0x06000924 RID: 2340 RVA: 0x0002B028 File Offset: 0x00029228
	// (set) Token: 0x06000925 RID: 2341 RVA: 0x0002B040 File Offset: 0x00029240
	public GControl42.GEnum9 GEnum9_0
	{
		get
		{
			return this.genum9_0;
		}
		set
		{
			this.genum9_0 = value;
			GControl42.GDelegate19 gdelegate = this.gdelegate19_0;
			if (gdelegate != null)
			{
				gdelegate();
			}
		}
	}

	// Token: 0x06000926 RID: 2342 RVA: 0x0002B064 File Offset: 0x00029264
	protected void method_0()
	{
		base.Invalidate();
		GControl42.GEnum9 genum = this.GEnum9_0;
		if (genum != GControl42.GEnum9.Light)
		{
			if (genum == GControl42.GEnum9.Dark)
			{
				this.BackColor = Color.FromArgb(50, 50, 50);
				this.ForeColor = Color.White;
			}
		}
		else
		{
			this.BackColor = Color.White;
			this.ForeColor = Color.Black;
		}
	}

	// Token: 0x1700028D RID: 653
	// (get) Token: 0x06000927 RID: 2343 RVA: 0x0002B0BC File Offset: 0x000292BC
	// (set) Token: 0x06000928 RID: 2344 RVA: 0x0002B0D4 File Offset: 0x000292D4
	public Color Color_0
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

	// Token: 0x06000929 RID: 2345 RVA: 0x0002B0E4 File Offset: 0x000292E4
	public GControl42()
	{
		this.Event_0 += this.method_0;
		this.genum10_0 = GControl42.GEnum10.None;
		this.DoubleBuffered = true;
		base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
		base.SetStyle(ControlStyles.UserPaint, true);
		base.SetStyle(ControlStyles.ResizeRedraw, true);
		base.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
		this.ForeColor = Color.White;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.Color_0 = Color.DodgerBlue;
		this.GEnum9_0 = GControl42.GEnum9.Dark;
		this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
	}

	// Token: 0x0600092A RID: 2346 RVA: 0x0002B178 File Offset: 0x00029378
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(100, 25);
	}

	// Token: 0x0600092B RID: 2347 RVA: 0x0002B190 File Offset: 0x00029390
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		int x = e.Location.X;
		int y = e.Location.Y;
		if (y <= 0 || y >= checked(base.Height - 2))
		{
			this.genum10_0 = GControl42.GEnum10.None;
		}
		else if (x <= 0 || x >= 34)
		{
			if (x <= 33 || x >= 65)
			{
				if (x <= 64 || x >= base.Width)
				{
					this.genum10_0 = GControl42.GEnum10.None;
				}
				else
				{
					this.genum10_0 = GControl42.GEnum10.Close;
				}
			}
			else
			{
				this.genum10_0 = GControl42.GEnum10.Maximize;
			}
		}
		else
		{
			this.genum10_0 = GControl42.GEnum10.Minimize;
		}
		base.Invalidate();
	}

	// Token: 0x0600092C RID: 2348 RVA: 0x0002B238 File Offset: 0x00029438
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		base.OnPaint(e);
		graphics.Clear(this.BackColor);
		switch (this.genum10_0)
		{
		case GControl42.GEnum10.Minimize:
			graphics.FillRectangle(new SolidBrush(this.color_0), new Rectangle(3, 0, 30, base.Height));
			break;
		case GControl42.GEnum10.Maximize:
			graphics.FillRectangle(new SolidBrush(this.color_0), new Rectangle(34, 0, 30, base.Height));
			break;
		case GControl42.GEnum10.Close:
			graphics.FillRectangle(new SolidBrush(this.color_0), new Rectangle(65, 0, 35, base.Height));
			break;
		case GControl42.GEnum10.None:
			graphics.Clear(this.BackColor);
			break;
		}
		Font font = new Font("Marlett", 9.75f);
		graphics.DrawString("r", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(checked(base.Width - 16), 7), new StringFormat
		{
			Alignment = StringAlignment.Center
		});
		FormWindowState windowState = base.Parent.FindForm().WindowState;
		if (windowState != FormWindowState.Normal)
		{
			if (windowState == FormWindowState.Maximized)
			{
				graphics.DrawString("2", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(51, 7), new StringFormat
				{
					Alignment = StringAlignment.Center
				});
			}
		}
		else
		{
			graphics.DrawString("1", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(51, 7), new StringFormat
			{
				Alignment = StringAlignment.Center
			});
		}
		graphics.DrawString("0", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(20, 7), new StringFormat
		{
			Alignment = StringAlignment.Center
		});
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x0600092D RID: 2349 RVA: 0x0002B450 File Offset: 0x00029650
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		switch (this.genum10_0)
		{
		case GControl42.GEnum10.Minimize:
			base.Parent.FindForm().WindowState = FormWindowState.Minimized;
			break;
		case GControl42.GEnum10.Maximize:
			if (base.Parent.FindForm().WindowState != FormWindowState.Normal)
			{
				base.Parent.FindForm().WindowState = FormWindowState.Normal;
			}
			else
			{
				base.Parent.FindForm().WindowState = FormWindowState.Maximized;
			}
			break;
		case GControl42.GEnum10.Close:
			base.Parent.FindForm().Close();
			break;
		}
	}

	// Token: 0x0600092E RID: 2350 RVA: 0x0002B4E0 File Offset: 0x000296E0
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.genum10_0 = GControl42.GEnum10.None;
		base.Invalidate();
	}

	// Token: 0x04000464 RID: 1124
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl42.GDelegate19 gdelegate19_0;

	// Token: 0x04000465 RID: 1125
	private GControl42.GEnum9 genum9_0;

	// Token: 0x04000466 RID: 1126
	private Color color_0;

	// Token: 0x04000467 RID: 1127
	private GControl42.GEnum10 genum10_0;

	// Token: 0x020000CE RID: 206
	public enum GEnum9
	{
		// Token: 0x04000469 RID: 1129
		Light,
		// Token: 0x0400046A RID: 1130
		Dark
	}

	// Token: 0x020000CF RID: 207
	// (Invoke) Token: 0x06000932 RID: 2354
	public delegate void GDelegate19();

	// Token: 0x020000D0 RID: 208
	public enum GEnum10
	{
		// Token: 0x0400046C RID: 1132
		Minimize,
		// Token: 0x0400046D RID: 1133
		Maximize,
		// Token: 0x0400046E RID: 1134
		Close,
		// Token: 0x0400046F RID: 1135
		None
	}
}
