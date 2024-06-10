using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using static Dope.Theme.ThemeUtils;

namespace Dope.Theme
{
    class button : Control
    {
        private MouseState State = MouseState.None;

        public button()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
            DoubleBuffered = true;
        }

        Image Image_;
        public Image Image
        {
            get
            {
                return Image_;
            }
            set
            {
                Image_ = value;
            }
        }

        bool Collapse_;
        public bool Collapse
        {
            get
            {
                return Collapse_;
            }
            set
            {
                Collapse_ = value;
            }
        }

        int CollapseWidth_;
        public int CollapseWidth
        {
            get
            {
                return CollapseWidth_;
            }
            set
            {
                CollapseWidth_ = value;
            }
        }

        protected override void OnMouseDown(MouseEventArgs e)
        {
            base.OnMouseDown(e);
            State = MouseState.Down;
            Invalidate();
        }

        protected override void OnMouseUp(MouseEventArgs e)
        {
            base.OnMouseUp(e);
            State = MouseState.Over;
            Invalidate();
        }

        protected override void OnMouseEnter(EventArgs e)
        {
            base.OnMouseEnter(e);
            State = MouseState.Over;
            Invalidate();
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            base.OnMouseLeave(e);
            State = MouseState.None;
            Invalidate();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            ///basic setup
            Bitmap B = new Bitmap(Width, Height);
            Graphics G = Graphics.FromImage(B);
            G.SmoothingMode = SmoothingMode.HighQuality;
            G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;

            SizeF TS = G.MeasureString(Text, Font);
            int TW = (int)TS.Width;
            int TH = (int)TS.Height;

            //Draw text
            if (!Collapse || Width >= CollapseWidth)
            {
                G.DrawString(Text, Font, new SolidBrush(ForeColor), (int)(Width / 2 - TW / 2), (int)(Height / 2 - TH / 2));
            }
            if (Image != null)
            {
                float IR = Image.Height / (TH + 10);
                int IMH = (int)(Image.Height / IR);
                G.DrawImage(Image, 15, (int)(Height/2 - IMH/2), (int)(Image.Width / IR), IMH);
            }

            if (State == MouseState.Over)
            {
                G.FillRectangle(new SolidBrush(Color.FromArgb(20,255,255,255)),new Rectangle(0,0,Width,Height));
            }
            else if (State == MouseState.Down)
            {
                G.FillRectangle(new SolidBrush(Color.FromArgb(40, 255, 255, 255)),new Rectangle(0,0,Width,Height));
            }

            base.OnPaint(e);
            G.Dispose();
            e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            e.Graphics.DrawImageUnscaled(B, 0, 0);
            B.Dispose();
        }
    }
}
