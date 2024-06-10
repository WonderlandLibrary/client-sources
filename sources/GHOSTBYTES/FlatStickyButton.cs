using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000022 RID: 34
	internal class FlatStickyButton : Control
	{
		// Token: 0x060001B9 RID: 441 RVA: 0x0000389B File Offset: 0x00001A9B
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001BA RID: 442 RVA: 0x000038B1 File Offset: 0x00001AB1
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001BB RID: 443 RVA: 0x000038C7 File Offset: 0x00001AC7
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001BC RID: 444 RVA: 0x000038DD File Offset: 0x00001ADD
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001BD RID: 445 RVA: 0x000091C8 File Offset: 0x000073C8
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
						double num = checked(Math.Atan2((double)(base.Left - control.Left), (double)(base.Top - control.Top))) * 2.0 / 3.141592653589793;
						checked
						{
							if ((double)((long)Math.Round(num) / 1L) == num)
							{
								array[(int)Math.Round(unchecked(num + 1.0))] = true;
							}
						}
					}
				}
			}
			finally
			{
				
			}
			return array;
		}

		// Token: 0x17000094 RID: 148
		// (get) Token: 0x060001BE RID: 446 RVA: 0x000038F3 File Offset: 0x00001AF3
		private Rectangle Rect
		{
			get
			{
				return new Rectangle(base.Left, base.Top, base.Width, base.Height);
			}
		}

		// Token: 0x17000095 RID: 149
		// (get) Token: 0x060001BF RID: 447 RVA: 0x00003912 File Offset: 0x00001B12
		// (set) Token: 0x060001C0 RID: 448 RVA: 0x0000391A File Offset: 0x00001B1A
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

		// Token: 0x17000096 RID: 150
		// (get) Token: 0x060001C1 RID: 449 RVA: 0x00003923 File Offset: 0x00001B23
		// (set) Token: 0x060001C2 RID: 450 RVA: 0x0000392B File Offset: 0x00001B2B
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

		// Token: 0x17000097 RID: 151
		// (get) Token: 0x060001C3 RID: 451 RVA: 0x00003934 File Offset: 0x00001B34
		// (set) Token: 0x060001C4 RID: 452 RVA: 0x0000393C File Offset: 0x00001B3C
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

		// Token: 0x060001C5 RID: 453 RVA: 0x00003945 File Offset: 0x00001B45
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
		}

		// Token: 0x060001C6 RID: 454 RVA: 0x0000394E File Offset: 0x00001B4E
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
		}

		// Token: 0x060001C7 RID: 455 RVA: 0x000092B0 File Offset: 0x000074B0
		public FlatStickyButton()
		{
			this.State = MouseState.None;
			this._Rounded = false;
			this._BaseColor = Color.FromArgb(0, 170, 220);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(106, 32);
			this.BackColor = Color.Transparent;
			this.Font = new Font("Segoe UI", 10f);
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060001C8 RID: 456 RVA: 0x00009350 File Offset: 0x00007550
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

		// Token: 0x0400009B RID: 155
		private int W;

		// Token: 0x0400009C RID: 156
		private int H;

		// Token: 0x0400009D RID: 157
		private MouseState State;

		// Token: 0x0400009E RID: 158
		private bool _Rounded;

		// Token: 0x0400009F RID: 159
		private Color _BaseColor;

		// Token: 0x040000A0 RID: 160
		private Color _TextColor;
	}
}
