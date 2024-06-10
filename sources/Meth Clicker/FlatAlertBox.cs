using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000023 RID: 35
	internal class FlatAlertBox : Control
	{
		// Token: 0x170000C1 RID: 193
		// (get) Token: 0x0600028F RID: 655 RVA: 0x0001291C File Offset: 0x00010B1C
		// (set) Token: 0x06000290 RID: 656 RVA: 0x00012924 File Offset: 0x00010B24
		private virtual Timer T
		{
			[CompilerGenerated]
			get
			{
				return this._T;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.T_Tick);
				Timer t = this._T;
				if (t != null)
				{
					t.Tick -= value2;
				}
				this._T = value;
				t = this._T;
				if (t != null)
				{
					t.Tick += value2;
				}
			}
		}

		// Token: 0x170000C2 RID: 194
		// (get) Token: 0x06000291 RID: 657 RVA: 0x00012967 File Offset: 0x00010B67
		// (set) Token: 0x06000292 RID: 658 RVA: 0x0001296F File Offset: 0x00010B6F
		[Category("Options")]
		public FlatAlertBox._Kind kind
		{
			get
			{
				return this.K;
			}
			set
			{
				this.K = value;
			}
		}

		// Token: 0x170000C3 RID: 195
		// (get) Token: 0x06000293 RID: 659 RVA: 0x00011ED8 File Offset: 0x000100D8
		// (set) Token: 0x06000294 RID: 660 RVA: 0x00012978 File Offset: 0x00010B78
		[Category("Options")]
		public override string Text
		{
			get
			{
				return base.Text;
			}
			set
			{
				base.Text = value;
				if (this._Text != null)
				{
					this._Text = value;
				}
			}
		}

		// Token: 0x170000C4 RID: 196
		// (get) Token: 0x06000295 RID: 661 RVA: 0x00012990 File Offset: 0x00010B90
		// (set) Token: 0x06000296 RID: 662 RVA: 0x0001299B File Offset: 0x00010B9B
		[Category("Options")]
		public new bool Visible
		{
			get
			{
				return !base.Visible;
			}
			set
			{
				base.Visible = value;
			}
		}

		// Token: 0x06000297 RID: 663 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000298 RID: 664 RVA: 0x000129A4 File Offset: 0x00010BA4
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 42;
		}

		// Token: 0x06000299 RID: 665 RVA: 0x000129B5 File Offset: 0x00010BB5
		public void ShowControl(FlatAlertBox._Kind Kind, string Str, int Interval)
		{
			this.K = Kind;
			this.Text = Str;
			this.Visible = true;
			this.T = new Timer();
			this.T.Interval = Interval;
			this.T.Enabled = true;
		}

		// Token: 0x0600029A RID: 666 RVA: 0x000129EF File Offset: 0x00010BEF
		private void T_Tick(object sender, EventArgs e)
		{
			this.Visible = false;
			this.T.Enabled = false;
			this.T.Dispose();
		}

		// Token: 0x0600029B RID: 667 RVA: 0x00012A0F File Offset: 0x00010C0F
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600029C RID: 668 RVA: 0x00012A25 File Offset: 0x00010C25
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600029D RID: 669 RVA: 0x00012A3B File Offset: 0x00010C3B
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600029E RID: 670 RVA: 0x00012A51 File Offset: 0x00010C51
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x0600029F RID: 671 RVA: 0x00012A67 File Offset: 0x00010C67
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.X = e.X;
			base.Invalidate();
		}

		// Token: 0x060002A0 RID: 672 RVA: 0x00012A82 File Offset: 0x00010C82
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			this.Visible = false;
		}

		// Token: 0x060002A1 RID: 673 RVA: 0x00012A94 File Offset: 0x00010C94
		public FlatAlertBox()
		{
			this.State = MouseState.None;
			this.SuccessColor = Color.FromArgb(60, 85, 79);
			this.SuccessText = Color.FromArgb(35, 169, 110);
			this.ErrorColor = Color.FromArgb(87, 71, 71);
			this.ErrorText = Color.FromArgb(254, 142, 122);
			this.InfoColor = Color.FromArgb(70, 91, 94);
			this.InfoText = Color.FromArgb(97, 185, 186);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			base.Size = new Size(576, 42);
			base.Location = new Point(10, 61);
			this.Font = new Font("Segoe UI", 10f);
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060002A2 RID: 674 RVA: 0x00012B88 File Offset: 0x00010D88
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			switch (this.K)
			{
			case FlatAlertBox._Kind.Success:
			{
				g.FillRectangle(new SolidBrush(this.SuccessColor), rect);
				g.FillEllipse(new SolidBrush(this.SuccessText), new Rectangle(8, 9, 24, 24));
				g.FillEllipse(new SolidBrush(this.SuccessColor), new Rectangle(10, 11, 20, 20));
				g.DrawString("ü", new Font("Wingdings", 22f), new SolidBrush(this.SuccessText), new Rectangle(7, 7, this.W, this.H), Helpers.NearSF);
				g.DrawString(this.Text, this.Font, new SolidBrush(this.SuccessText), new Rectangle(48, 12, this.W, this.H), Helpers.NearSF);
				g.FillEllipse(new SolidBrush(Color.FromArgb(35, Color.Black)), new Rectangle(this.W - 30, this.H - 29, 17, 17));
				g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.SuccessColor), new Rectangle(this.W - 28, 16, this.W, this.H), Helpers.NearSF);
				MouseState state = this.State;
				if (state == MouseState.Over)
				{
					g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(Color.FromArgb(25, Color.White)), new Rectangle(this.W - 28, 16, this.W, this.H), Helpers.NearSF);
				}
				break;
			}
			case FlatAlertBox._Kind.Error:
			{
				g.FillRectangle(new SolidBrush(this.ErrorColor), rect);
				g.FillEllipse(new SolidBrush(this.ErrorText), new Rectangle(8, 9, 24, 24));
				g.FillEllipse(new SolidBrush(this.ErrorColor), new Rectangle(10, 11, 20, 20));
				g.DrawString("r", new Font("Marlett", 16f), new SolidBrush(this.ErrorText), new Rectangle(6, 11, this.W, this.H), Helpers.NearSF);
				g.DrawString(this.Text, this.Font, new SolidBrush(this.ErrorText), new Rectangle(48, 12, this.W, this.H), Helpers.NearSF);
				g.FillEllipse(new SolidBrush(Color.FromArgb(35, Color.Black)), new Rectangle(this.W - 32, this.H - 29, 17, 17));
				g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.ErrorColor), new Rectangle(this.W - 30, 17, this.W, this.H), Helpers.NearSF);
				MouseState state2 = this.State;
				if (state2 == MouseState.Over)
				{
					g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(Color.FromArgb(25, Color.White)), new Rectangle(this.W - 30, 15, this.W, this.H), Helpers.NearSF);
				}
				break;
			}
			case FlatAlertBox._Kind.Info:
			{
				g.FillRectangle(new SolidBrush(this.InfoColor), rect);
				g.FillEllipse(new SolidBrush(this.InfoText), new Rectangle(8, 9, 24, 24));
				g.FillEllipse(new SolidBrush(this.InfoColor), new Rectangle(10, 11, 20, 20));
				g.DrawString("¡", new Font("Segoe UI", 20f, FontStyle.Bold), new SolidBrush(this.InfoText), new Rectangle(12, -4, this.W, this.H), Helpers.NearSF);
				g.DrawString(this.Text, this.Font, new SolidBrush(this.InfoText), new Rectangle(48, 12, this.W, this.H), Helpers.NearSF);
				g.FillEllipse(new SolidBrush(Color.FromArgb(35, Color.Black)), new Rectangle(this.W - 32, this.H - 29, 17, 17));
				g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.InfoColor), new Rectangle(this.W - 30, 17, this.W, this.H), Helpers.NearSF);
				MouseState state3 = this.State;
				if (state3 == MouseState.Over)
				{
					g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(Color.FromArgb(25, Color.White)), new Rectangle(this.W - 30, 17, this.W, this.H), Helpers.NearSF);
				}
				break;
			}
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000150 RID: 336
		private int W;

		// Token: 0x04000151 RID: 337
		private int H;

		// Token: 0x04000152 RID: 338
		private FlatAlertBox._Kind K;

		// Token: 0x04000153 RID: 339
		private string _Text;

		// Token: 0x04000154 RID: 340
		private MouseState State;

		// Token: 0x04000155 RID: 341
		private int X;

		// Token: 0x04000157 RID: 343
		private Color SuccessColor;

		// Token: 0x04000158 RID: 344
		private Color SuccessText;

		// Token: 0x04000159 RID: 345
		private Color ErrorColor;

		// Token: 0x0400015A RID: 346
		private Color ErrorText;

		// Token: 0x0400015B RID: 347
		private Color InfoColor;

		// Token: 0x0400015C RID: 348
		private Color InfoText;

		// Token: 0x0200005F RID: 95
		[Flags]
		public enum _Kind
		{
			// Token: 0x040001E4 RID: 484
			Success = 0,
			// Token: 0x040001E5 RID: 485
			Error = 1,
			// Token: 0x040001E6 RID: 486
			Info = 2
		}
	}
}
