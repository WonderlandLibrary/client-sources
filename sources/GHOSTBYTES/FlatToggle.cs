using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000013 RID: 19
	[DefaultEvent("CheckedChanged")]
	internal class FlatToggle : Control
	{
		// Token: 0x14000001 RID: 1
		// (add) Token: 0x06000122 RID: 290 RVA: 0x00006898 File Offset: 0x00004A98
		// (remove) Token: 0x06000123 RID: 291 RVA: 0x000068D0 File Offset: 0x00004AD0
		public event FlatToggle.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x17000074 RID: 116
		// (get) Token: 0x06000124 RID: 292 RVA: 0x0000324F File Offset: 0x0000144F
		// (set) Token: 0x06000125 RID: 293 RVA: 0x00003257 File Offset: 0x00001457
		[Category("Options")]
		public FlatToggle._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		// Token: 0x17000075 RID: 117
		// (get) Token: 0x06000126 RID: 294 RVA: 0x00003260 File Offset: 0x00001460
		// (set) Token: 0x06000127 RID: 295 RVA: 0x00003268 File Offset: 0x00001468
		[Category("Options")]
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
			}
		}

		// Token: 0x06000128 RID: 296 RVA: 0x00003271 File Offset: 0x00001471
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000129 RID: 297 RVA: 0x00003280 File Offset: 0x00001480
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Width = 76;
			base.Height = 33;
		}

		// Token: 0x0600012A RID: 298 RVA: 0x00003299 File Offset: 0x00001499
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600012B RID: 299 RVA: 0x000032AF File Offset: 0x000014AF
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600012C RID: 300 RVA: 0x000032C5 File Offset: 0x000014C5
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x0600012D RID: 301 RVA: 0x000032DB File Offset: 0x000014DB
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600012E RID: 302 RVA: 0x00006908 File Offset: 0x00004B08
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			this._Checked = !this._Checked;
			FlatToggle.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
		}

        private void CheckedChangedEvent(object sender)
        {
            throw new NotImplementedException();
        }

        // Token: 0x0600012F RID: 303 RVA: 0x0000693C File Offset: 0x00004B3C
        public FlatToggle()
		{
			this._Checked = false;
			this.State = MouseState.None;
			this.BaseColor = Color.FromArgb(0, 170, 220);
			this.BaseColorRed = Color.FromArgb(0, 170, 220);
			this.BGColor = Color.FromArgb(84, 85, 86);
			this.ToggleColor = Color.FromArgb(45, 47, 49);
			this.TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new Size(44, checked(base.Height + 1));
			this.Cursor = Cursors.Hand;
			this.Font = new Font("Segoe UI", 10f);
			base.Size = new Size(56, 13);
		}

		// Token: 0x06000130 RID: 304 RVA: 0x00006A28 File Offset: 0x00004C28
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
				this.W = base.Width - 1;
				this.H = base.Height - 1;
				GraphicsPath path = new GraphicsPath();
				GraphicsPath graphicsPath = new GraphicsPath();
				Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
				Rectangle rectangle2 = new Rectangle(this.W / 2, 0, 38, this.H);
				Graphics g = Helpers.G;
				g.SmoothingMode = SmoothingMode.HighQuality;
				g.PixelOffsetMode = PixelOffsetMode.HighQuality;
				g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				g.Clear(this.BackColor);
				switch (this.O)
				{
				case FlatToggle._Options.Style1:
					path = Helpers.RoundRec(rectangle, 6);
					graphicsPath = Helpers.RoundRec(rectangle2, 6);
					g.FillPath(new SolidBrush(this.BGColor), path);
					g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
					g.DrawString("OFF", this.Font, new SolidBrush(this.BGColor), new Rectangle(19, 1, this.W, this.H), Helpers.CenterSF);
					if (this.Checked)
					{
						path = Helpers.RoundRec(rectangle, 6);
						graphicsPath = Helpers.RoundRec(new Rectangle(this.W / 2, 0, 38, this.H), 6);
						g.FillPath(new SolidBrush(this.ToggleColor), path);
						g.FillPath(new SolidBrush(this.BaseColor), graphicsPath);
						g.DrawString("ON", this.Font, new SolidBrush(this.BaseColor), new Rectangle(8, 7, this.W, this.H), Helpers.NearSF);
					}
					break;
				case FlatToggle._Options.Style2:
					path = Helpers.RoundRec(rectangle, 6);
					rectangle2 = new Rectangle(4, 4, 36, this.H - 8);
					graphicsPath = Helpers.RoundRec(rectangle2, 4);
					g.FillPath(new SolidBrush(this.BaseColorRed), path);
					g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
					g.DrawLine(new Pen(this.BGColor), 18, 20, 18, 12);
					g.DrawLine(new Pen(this.BGColor), 22, 20, 22, 12);
					g.DrawLine(new Pen(this.BGColor), 26, 20, 26, 12);
					g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(19, 2, base.Width, base.Height), Helpers.CenterSF);
					if (this.Checked)
					{
						path = Helpers.RoundRec(rectangle, 6);
						rectangle2 = new Rectangle(this.W / 2 - 2, 4, 36, this.H - 8);
						graphicsPath = Helpers.RoundRec(rectangle2, 4);
						g.FillPath(new SolidBrush(this.BaseColor), path);
						g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
						g.DrawLine(new Pen(this.BGColor), this.W / 2 + 12, 20, this.W / 2 + 12, 12);
						g.DrawLine(new Pen(this.BGColor), this.W / 2 + 16, 20, this.W / 2 + 16, 12);
						g.DrawLine(new Pen(this.BGColor), this.W / 2 + 20, 20, this.W / 2 + 20, 12);
						g.DrawString("ü", new Font("Wingdings", 14f), new SolidBrush(this.TextColor), new Rectangle(8, 7, base.Width, base.Height), Helpers.NearSF);
					}
					break;
				case FlatToggle._Options.Style3:
					path = Helpers.RoundRec(rectangle, 16);
					rectangle2 = new Rectangle(this.W - 28, 4, 22, this.H - 8);
					graphicsPath.AddEllipse(rectangle2);
					g.FillPath(new SolidBrush(this.ToggleColor), path);
					g.FillPath(new SolidBrush(this.BaseColorRed), graphicsPath);
					g.DrawString("OFF", this.Font, new SolidBrush(this.BaseColorRed), new Rectangle(-12, 2, this.W, this.H), Helpers.CenterSF);
					if (this.Checked)
					{
						path = Helpers.RoundRec(rectangle, 16);
						rectangle2 = new Rectangle(6, 4, 22, this.H - 8);
						graphicsPath.Reset();
						graphicsPath.AddEllipse(rectangle2);
						g.FillPath(new SolidBrush(this.ToggleColor), path);
						g.FillPath(new SolidBrush(this.BaseColor), graphicsPath);
						g.DrawString("ON", this.Font, new SolidBrush(this.BaseColor), new Rectangle(12, 2, this.W, this.H), Helpers.CenterSF);
					}
					break;
				case FlatToggle._Options.Style4:
					if (this.Checked)
					{
					}
					break;
				case FlatToggle._Options.Style5:
				{
					bool @checked = this.Checked;
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

		// Token: 0x04000040 RID: 64
		private int W;

		// Token: 0x04000041 RID: 65
		private int H;

		// Token: 0x04000042 RID: 66
		private FlatToggle._Options O;

		// Token: 0x04000043 RID: 67
		private bool _Checked;

		// Token: 0x04000044 RID: 68
		private MouseState State;

		// Token: 0x04000046 RID: 70
		private Color BaseColor;

		// Token: 0x04000047 RID: 71
		private Color BaseColorRed;

		// Token: 0x04000048 RID: 72
		private Color BGColor;

		// Token: 0x04000049 RID: 73
		private Color ToggleColor;

		// Token: 0x0400004A RID: 74
		private Color TextColor;

		// Token: 0x02000014 RID: 20
		// (Invoke) Token: 0x06000134 RID: 308
		public delegate void CheckedChangedEventHandler(object sender);

		// Token: 0x02000015 RID: 21
		[Flags]
		public enum _Options
		{
			// Token: 0x0400004C RID: 76
			Style1 = 0,
			// Token: 0x0400004D RID: 77
			Style2 = 1,
			// Token: 0x0400004E RID: 78
			Style3 = 2,
			// Token: 0x0400004F RID: 79
			Style4 = 3,
			// Token: 0x04000050 RID: 80
			Style5 = 4
		}
	}
}
