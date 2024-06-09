using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FormSkin : ContainerControl
	{
		private int W;

		private int H;

		private bool Cap;

		private bool _HeaderMaximize;

		private Point MousePoint;

		private IContainer components;

		private object MoveHeight;

		private Color _HeaderColor;

		private Color _BaseColor;

		private Color _BorderColor;

		private Color TextColor;

		private Color _HeaderLight;

		private Color _BaseLight;

		public Color TextLight;

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

		[Category("Colors")]
		public Color BorderColor
		{
			get
			{
				return this._BorderColor;
			}
			set
			{
				this._BorderColor = value;
			}
		}

		[Category("Colors")]
		public Color FlatColor
		{
			get
			{
				return Helpers._FlatColor;
			}
			set
			{
				Helpers._FlatColor = value;
			}
		}

		[Category("Colors")]
		public Color HeaderColor
		{
			get
			{
				return this._HeaderColor;
			}
			set
			{
				this._HeaderColor = value;
			}
		}

		[Category("Options")]
		public bool HeaderMaximize
		{
			get
			{
				return this._HeaderMaximize;
			}
			set
			{
				this._HeaderMaximize = value;
			}
		}

		internal virtual Timer Timer1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		public FormSkin()
		{
			base.MouseDoubleClick += new MouseEventHandler(this.FormSkin_MouseDoubleClick);
			this.Cap = false;
			this._HeaderMaximize = false;
			this.MousePoint = new Point(0, 0);
			this.MoveHeight = 50;
			this._HeaderColor = Color.FromArgb(45, 47, 49);
			this._BaseColor = Color.FromArgb(60, 70, 73);
			this._BorderColor = Color.FromArgb(53, 58, 60);
			this.TextColor = Color.FromArgb(234, 234, 234);
			this._HeaderLight = Color.FromArgb(171, 171, 172);
			this._BaseLight = Color.FromArgb(196, 199, 200);
			this.TextLight = Color.FromArgb(45, 47, 49);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			this.Font = new System.Drawing.Font("Segoe UI", 12f);
		}

		private void FormSkin_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			if (this.HeaderMaximize)
			{
				Rectangle rectangle = new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.MoveHeight));
				if (e.Button == System.Windows.Forms.MouseButtons.Left & rectangle.Contains(e.Location))
				{
					if (base.FindForm().WindowState == FormWindowState.Normal)
					{
						base.FindForm().WindowState = FormWindowState.Maximized;
						base.FindForm().Refresh();
						return;
					}
					if (base.FindForm().WindowState == FormWindowState.Maximized)
					{
						base.FindForm().WindowState = FormWindowState.Normal;
						base.FindForm().Refresh();
					}
				}
			}
		}

		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			this.Timer1 = new Timer(this.components);
			base.SuspendLayout();
			base.ResumeLayout(false);
		}

		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			base.ParentForm.FormBorderStyle = FormBorderStyle.None;
			base.ParentForm.AllowTransparency = false;
			base.ParentForm.TransparencyKey = Color.Fuchsia;
			base.ParentForm.FindForm().StartPosition = FormStartPosition.CenterScreen;
			this.Dock = DockStyle.Fill;
			base.Invalidate();
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			Rectangle rectangle = new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.MoveHeight));
			if (e.Button == System.Windows.Forms.MouseButtons.Left & rectangle.Contains(e.Location))
			{
				this.Cap = true;
				this.MousePoint = e.Location;
			}
		}

		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Cap)
			{
				base.Parent.Location = Control.MousePosition - (System.Drawing.Size)this.MousePoint;
			}
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Cap = false;
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Rectangle rectangle1 = new Rectangle(0, 0, this.W, 50);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
			g.FillRectangle(new SolidBrush(this._HeaderColor), rectangle1);
			g.FillRectangle(new SolidBrush(Color.FromArgb(243, 243, 243)), new Rectangle(8, 16, 4, 18));
			g.FillRectangle(new SolidBrush(Helpers._FlatColor), 16, 16, 4, 18);
			g.DrawString(this.Text, this.Font, new SolidBrush(this.TextColor), new Rectangle(26, 15, this.W, this.H), Helpers.NearSF);
			g.DrawRectangle(new Pen(this._BorderColor), rectangle);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}
	}
}