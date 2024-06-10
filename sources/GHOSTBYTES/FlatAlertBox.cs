using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x0200001E RID: 30
	internal class FlatAlertBox : Control
	{
		// Token: 0x1700008A RID: 138
		// (get) Token: 0x06000189 RID: 393 RVA: 0x00003628 File Offset: 0x00001828
		// (set) Token: 0x0600018A RID: 394 RVA: 0x00008300 File Offset: 0x00006500
		   public virtual Timer T
		{
			[CompilerGenerated]
			get
			{
				return this.T;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.T_Tick);
				Timer t = this.T;
				if (t != null)
				{
					t.Tick -= value2;
				}
				this.T = value;
				t = this.T;
				if (t != null)
				{
					t.Tick += value2;
				}
			}
		}

		// Token: 0x1700008B RID: 139
		// (get) Token: 0x0600018B RID: 395 RVA: 0x00003630 File Offset: 0x00001830
		// (set) Token: 0x0600018C RID: 396 RVA: 0x00003638 File Offset: 0x00001838
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

		// Token: 0x1700008C RID: 140
		// (get) Token: 0x0600018D RID: 397 RVA: 0x0000350A File Offset: 0x0000170A
		// (set) Token: 0x0600018E RID: 398 RVA: 0x00003641 File Offset: 0x00001841
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

		// Token: 0x1700008D RID: 141
		// (get) Token: 0x0600018F RID: 399 RVA: 0x00003659 File Offset: 0x00001859
		// (set) Token: 0x06000190 RID: 400 RVA: 0x00003664 File Offset: 0x00001864
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

		// Token: 0x06000191 RID: 401 RVA: 0x00003271 File Offset: 0x00001471
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000192 RID: 402 RVA: 0x0000366D File Offset: 0x0000186D
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 42;
		}

		// Token: 0x06000193 RID: 403 RVA: 0x0000367E File Offset: 0x0000187E
		public void ShowControl(FlatAlertBox._Kind Kind, string Str, int Interval)
		{
			this.K = Kind;
			this.Text = Str;
			this.Visible = true;
			this.T = new Timer();
			this.T.Interval = Interval;
			this.T.Enabled = true;
		}

		// Token: 0x06000194 RID: 404 RVA: 0x000036B8 File Offset: 0x000018B8
		private void T_Tick(object sender, EventArgs e)
		{
			this.Visible = false;
			this.T.Enabled = false;
			this.T.Dispose();
		}

		// Token: 0x06000195 RID: 405 RVA: 0x000036D8 File Offset: 0x000018D8
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000196 RID: 406 RVA: 0x000036EE File Offset: 0x000018EE
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000197 RID: 407 RVA: 0x00003704 File Offset: 0x00001904
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000198 RID: 408 RVA: 0x0000371A File Offset: 0x0000191A
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000199 RID: 409 RVA: 0x00003730 File Offset: 0x00001930
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.X = e.X;
			base.Invalidate();
		}

		// Token: 0x0600019A RID: 410 RVA: 0x0000374B File Offset: 0x0000194B
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			this.Visible = false;
		}

		// Token: 0x0600019B RID: 411 RVA: 0x00008344 File Offset: 0x00006544
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

		// Token: 0x0600019C RID: 412 RVA: 0x00008438 File Offset: 0x00006638
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
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
		}

		// Token: 0x0400007A RID: 122
		private int W;

		// Token: 0x0400007B RID: 123
		private int H;

		// Token: 0x0400007C RID: 124
		private FlatAlertBox._Kind K;

		// Token: 0x0400007D RID: 125
		private string _Text;

		// Token: 0x0400007E RID: 126
		private MouseState State;

		// Token: 0x0400007F RID: 127
		private int X;

		// Token: 0x04000081 RID: 129
		private Color SuccessColor;

		// Token: 0x04000082 RID: 130
		private Color SuccessText;

		// Token: 0x04000083 RID: 131
		private Color ErrorColor;

		// Token: 0x04000084 RID: 132
		private Color ErrorText;

		// Token: 0x04000085 RID: 133
		private Color InfoColor;

		// Token: 0x04000086 RID: 134
		private Color InfoText;

		// Token: 0x0200001F RID: 31
		[Flags]
		public enum _Kind
		{
			// Token: 0x04000088 RID: 136
			Success = 0,
			// Token: 0x04000089 RID: 137
			Error = 1,
			// Token: 0x0400008A RID: 138
			Info = 2
		}
	}
}
