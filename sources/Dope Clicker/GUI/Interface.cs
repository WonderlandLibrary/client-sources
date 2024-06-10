using System;
using Guna.UI2.WinForms;
using System.Drawing;
using AnyDesk.GUI.Helpers;
using System.Windows.Forms;

namespace AnyDesk
{
    public partial class Dope : Form
    {
        public void SwitchUserControl(UserControl control, Guna2Button button)
        {
            Guna2Button[] buttons = { left, right, sounds, configs, macros, settings };
            foreach (Guna2Button _button in buttons)
            {
                _button.ForeColor = Color.FromArgb(150, 150, 150);
                _button.HoverState.ForeColor = Color.FromArgb(100, 100, 100);
            }

            UserControl[] userControls = {
                right1,
                left1,
                sounds1,
                macros1,
                settings1,
                configs1
            };

            foreach (UserControl _usercontrol in userControls)
            {
                _usercontrol.Visible = false;
            }

            button.ForeColor = Color.FromArgb(42, 82, 242);
            button.HoverState.ForeColor = Color.FromArgb(42, 82, 242);
            control.Visible = true;
        }

        Helper _guiHelper = new Helper();

        public Dope()
        {
            InitializeComponent();
            this.TopMost = true;
            this.CenterToScreen();
            this.Icon = null;
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            _guiHelper.Closing(delay: 15, this);
        }

        private void Form1_Deactivate(object sender, EventArgs e)
        {
            _guiHelper.FadeOut(delay: 15, this);
        }

        private void Form1_Activated(object sender, EventArgs e)
        {
            _guiHelper.FadeIn(delay: 15, this);
        }
        
        private void left_Click(object sender, EventArgs e)
        {
            SwitchUserControl(control: left1, button: left);
        }

        private void right_Click(object sender, EventArgs e)
        {
            SwitchUserControl(control: right1, button: right);
        }

        private void sounds_Click(object sender, EventArgs e)
        {
            SwitchUserControl(control: sounds1, button: sounds);
        }

        private void settings_Click(object sender, EventArgs e)
        {
            SwitchUserControl(control: settings1, button: settings);
        }

        private void configs_Click(object sender, EventArgs e)
        {
            SwitchUserControl(control: configs1, button: configs);
        }

        private void macros_Click(object sender, EventArgs e)
        {
            SwitchUserControl(control: macros1, button: macros);
        }
    }
}
