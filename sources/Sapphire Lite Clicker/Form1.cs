using System;
using System.Drawing;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;
using Sapphire_LITE.clicker;
using Sapphire_LITE.theme;

namespace Sapphire_LITE {
    public partial class Form1 : Form {

        #region Initialization

        public Form1() {
            InitializeComponent();

            Shadow.SetShadowForm(this); // apply shadow to window
            FadeInTimer.Start(); // fade in window

            // NtSetTimerResolution to fix the mouse event issues caused by later windows versions
            uint DesiredResolution = 5000; // 0.5ms resolution
            uint CurrentResolution;

            DLLImports.NtSetTimerResolution(DesiredResolution, true, out CurrentResolution);

            KeyListener.setupBindListener();

            // run clicker thread
            Task.Run(() => clicker.clicker.clickerThread());

            // run listener thread
            Task.Run(() => clicker.KeyListener.ListenForKeyPress());
        }

        private void FadeInTimer_Tick(object sender, EventArgs e) {
            if (this.Opacity == 1) FadeInTimer.Stop();
            else this.Opacity += 0.1;
        }

        #endregion

        #region Controls

        // divide the cps by 10.0 to have decimal accuracy on the displayed text along with having non-ticky sliders

        private void leftCpsSlider_Scroll(object sender) {
            leftCpsText.Text = $"{leftCpsSlider.Value / 10.0}";
            clicker.clicker.left_cps = (leftCpsSlider.Value / 10);
        }

        private void rightCpsSlider_Scroll(object sender) {
            rightCpsText.Text = $"{rightCpsSlider.Value / 10.0}";
            clicker.clicker.right_cps = (rightCpsSlider.Value / 10);
        }

        private void jitterSlider_Scroll(object sender) {
            randomizationText.Text = $"{randomizationSlider.Value}%";
            clicker.clicker.randomization_distribution = randomizationSlider.Value;
        }

        private void presetSelector_SelectedIndexChanged(object sender, EventArgs e) {
            switch (presetSelector.SelectedIndex) {
            case 0:
                leftCpsSlider.Value = 125;
                rightCpsSlider.Value = 125;
                break;

            case 1:
                leftCpsSlider.Value = 100;
                rightCpsSlider.Value = 100;
                break;

            case 2:
                leftCpsSlider.Value = 175;
                rightCpsSlider.Value = 175;
                break;
            }
        }

        #endregion

        #region Clicker & conditions
        private void toggleRandomization_CheckedChanged(object sender, EventArgs e) { clicker.clicker.randomize ^= true; }

        private void toggleShiftDisable_CheckedChanged(object sender, EventArgs e) { clicker.clicker.shift_disable ^= true; }

        private void toggleAlwaysOn_CheckedChanged(object sender, EventArgs e) { clicker.clicker.always_on ^= true; }

        private void toggleSmartMode_CheckedChanged(object sender, EventArgs e) {
            clicker.clicker.smart_mode ^= true;
            if (clicker.clicker.smart_mode)
                MessageBox.Show(
                "This feature is experimental, it may not work depending on the client you use or windows version you're on.", "",
                MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        #endregion

        #region Binds

        Keys left_bind = Keys.None, right_bind = Keys.None;

        private async void leftClickerBindButton_MouseDown(object sender, MouseEventArgs e) {
            if (MouseButtons != MouseButtons.Left) return;

            leftClickerBindButton.Text = "[press a key]";

            KeyListener.keysToCheck.Remove(left_bind);
            KeyListener.keybinds.Remove(left_bind);

            left_bind = await KeyListener.getBind();

            if (left_bind == Keys.Escape) left_bind = Keys.None;
            leftClickerBindButton.Text = $"[{left_bind.ToString().ToLower()}]";

            KeyListener.keysToCheck.Add(left_bind);
            KeyListener.keybinds[left_bind] = () => toggleLeftClicker();
        }

        private async void rightClickerBindButton_MouseDown(object sender, MouseEventArgs e) {
            if (MouseButtons != MouseButtons.Left) return;

            rightClickerBindButton.Text = "[press a key]";

            KeyListener.keysToCheck.Remove(right_bind);
            KeyListener.keybinds.Remove(right_bind);

            right_bind = await KeyListener.getBind();

            if (right_bind == Keys.Escape) right_bind = Keys.None;
            rightClickerBindButton.Text = $"[{right_bind.ToString().ToLower()}]";

            KeyListener.keysToCheck.Add(right_bind);
            KeyListener.keybinds[right_bind] = () => toggleRightClicker();
        }

        public async void toggleLeftClicker() {
            clicker.clicker.left_enabled ^= true;

            if (clicker.clicker.left_enabled) leftClickerBindButton.ForeColor = Color.Green;
            else leftClickerBindButton.ForeColor = Color.FromArgb(100, 100, 100);

            await Task.Delay(250);
        }

        public async void toggleRightClicker() {
            clicker.clicker.right_enabled ^= true;

            if (clicker.clicker.right_enabled) rightClickerBindButton.ForeColor = Color.Green;
            else rightClickerBindButton.ForeColor = Color.FromArgb(100, 100, 100);

            await Task.Delay(250);
        }

        private void watermarkImage_Click(object sender, EventArgs e) { Process.Start("https://discord.sapphire.ac"); }

        #endregion
    }
}
