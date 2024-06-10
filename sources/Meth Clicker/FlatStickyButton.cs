using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000026 RID: 38
	internal class FlatStickyButton : Control
	{
		// Token: 0x060002BF RID: 703 RVA: 0x00013A3D File Offset: 0x00011C3D
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060002C0 RID: 704 RVA: 0x00013A53 File Offset: 0x00011C53
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060002C1 RID: 705 RVA: 0x00013A69 File Offset: 0x00011C69
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060002C2 RID: 706 RVA: 0x00013A7F File Offset: 0x00011C7F
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060002C3 RID: 707 RVA: 0x00013A98 File Offset: 0x00011C98
		private bool[] GetConnectedSides()
		{
			bool[] array = new bool[4];
			try
			{
				foreach (object obj in base.Parent.Controls)
				{
					Control control = (Control)obj;
					if (control is FlatStickyButton && !(control == this | !this.Rect.IntersectsWith(this.Rect)))
					{
						double num = Math.Atan2((double)(base.Left - control.Left), (double)(base.Top - control.Top)) * 2.0 / 3.1415926535897931;
						if ((double)((long)Math.Round(num) / 1L) == num)
						{
							array[(int)Math.Round(num + 1.0)] = true;
						}
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
			return array;
		}

		// Token: 0x170000CB RID: 203
		// (get) Token: 0x060002C4 RID: 708 RVA: 0x00013B80 File Offset: 0x00011D80
		private Rectangle Rect
		{
			get
			{
				return new Rectangle(base.Left, base.Top, base.Width, base.Height);
			}
		}

		// Token: 0x170000CC RID: 204
		// (get) Token: 0x060002C5 RID: 709 RVA: 0x00013B9F File Offset: 0x00011D9F
		// (set) Token: 0x060002C6 RID: 710 RVA: 0x00013BA7 File Offset: 0x00011DA7
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x170000CD RID: 205
		// (get) Token: 0x060002C7 RID: 711 RVA: 0x00013BB0 File Offset: 0x00011DB0
		// (set) Token: 0x060002C8 RID: 712 RVA: 0x00013BB8 File Offset: 0x00011DB8
		[Category("Colors")]
		public Color TextColor
		{
			get
			{
				return this._TextColor;
			}
			set
			{
				this._TextColor = value;
			}
		}

		// Token: 0x170000CE RID: 206
		// (get) Token: 0x060002C9 RID: 713 RVA: 0x00013BC1 File Offset: 0x00011DC1
		// (set) Token: 0x060002CA RID: 714 RVA: 0x00013BC9 File Offset: 0x00011DC9
		[Category("Options")]
		public bool Rounded
		{
			get
			{
				return this._Rounded;
			}
			set
			{
				this._Rounded = value;
			}
		}

		// Token: 0x060002CB RID: 715 RVA: 0x00013BD2 File Offset: 0x00011DD2
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
		}

		// Token: 0x060002CC RID: 716 RVA: 0x00013BDB File Offset: 0x00011DDB
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
		}

		// Token: 0x060002CD RID: 717 RVA: 0x00013BE4 File Offset: 0x00011DE4
		public FlatStickyButton()
		{
			this.State = MouseState.None;
			this._Rounded = false;
			this._BaseColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(106, 32);
			this.BackColor = Color.Transparent;
			this.Font = new Font("Segoe UI", 12f);
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060002CE RID: 718 RVA: 0x00013C78 File Offset: 0x00011E78
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			GraphicsPath path = new GraphicsPath();
			bool[] connectedSides = this.GetConnectedSides();
			GraphicsPath graphicsPath = Helpers.RoundRect(0f, 0f, (float)this.W, (float)this.H, 0.3f, !(connectedSides[2] | connectedSides[1]), !(connectedSides[1] | connectedSides[0]), !(connectedSides[3] | connectedSides[0]), !(connectedSides[3] | connectedSides[2]));
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			switch (this.State)
			{
			case MouseState.None:
				if (this.Rounded)
				{
					path = graphicsPath;
					g.FillPath(new SolidBrush(this._BaseColor), path);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				break;
			case MouseState.Over:
				if (this.Rounded)
				{
					path = graphicsPath;
					g.FillPath(new SolidBrush(this._BaseColor), path);
					g.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), path);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
					g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.White)), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				break;
			case MouseState.Down:
				if (this.Rounded)
				{
					path = graphicsPath;
					g.FillPath(new SolidBrush(this._BaseColor), path);
					g.FillPath(new SolidBrush(Color.FromArgb(20, Color.Black)), path);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
					g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.Black)), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				break;
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400016D RID: 365
		private int W;

		// Token: 0x0400016E RID: 366
		private int H;

		// Token: 0x0400016F RID: 367
		private MouseState State;

		// Token: 0x04000170 RID: 368
		private bool _Rounded;

		// Token: 0x04000171 RID: 369
		private Color _BaseColor;

		// Token: 0x04000172 RID: 370
		private Color _TextColor;
	}
}
