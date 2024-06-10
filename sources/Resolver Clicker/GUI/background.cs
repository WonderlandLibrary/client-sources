using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Drawing;

namespace CucklordPRO
{
    public class SkeetBackground : ContainerControl
    {
        public Color ColorRight = Color.FromArgb(129, 28, 201);
        public Color ColorLeft = Color.FromArgb(129, 28, 201);
        public Color ColorMiddle = Color.FromArgb(129, 28, 201);

        protected override void OnPaint(PaintEventArgs e)
        {
            Rectangle area = new Rectangle(7, 7, Width - 14, 2);

            LinearGradientBrush gradient = new LinearGradientBrush(area, this.ColorLeft, this.ColorMiddle, 0.0f);

            ColorBlend cblend = new ColorBlend(3);
            cblend.Colors = new Color[3] { this.ColorLeft, this.ColorMiddle, this.ColorRight };
            cblend.Positions = new float[3] { 0f, 0.5f, 1f };
            gradient.InterpolationColors = cblend;

            Graphics g = e.Graphics;
            g.FillRectangle(gradient, area);
            base.OnPaint(e);
            this.BackColor = Color.FromArgb(23, 23, 23);

            for (int i = 1; i < 7; i++)
            {
                Rectangle rect = new Rectangle(i - 1, i - 1, Width - (i * 2) + 1, Height - (i * 2) + 1);
                Pen lightGrey = new Pen(Color.FromArgb(60, 60, 60));
                Pen darkGrey = new Pen(Color.FromArgb(40, 40, 40));

                if (i == 1)
                    e.Graphics.DrawRectangle(Pens.Black, rect);

                if (i == 2 || i == 6)
                    e.Graphics.DrawRectangle(lightGrey, rect);

                if (i == 3 || i == 4 || i == 5)
                    e.Graphics.DrawRectangle(darkGrey, rect);
            }
        }
        public Form Form;
        protected override void OnHandleCreated(EventArgs e)
        {
            Dock = DockStyle.Fill;
            if (Parent is Form)
            {
                Form parentForm = Parent as Form;
                Form = parentForm;

                parentForm.FormBorderStyle = FormBorderStyle.None;

                base.OnHandleCreated(e);
            }
        }

        private bool mouseDown = false;
        private Point lastLocation;


    }
}