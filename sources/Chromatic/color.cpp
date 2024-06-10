#include "main.h"

void thread::color()
{
    while (true)
    {
        if (gui::rgb) {
            if (gui::r) {
                if (gui::blue >= 244)
                {
                    gui::red -= 1;

                    if (gui::red <= 65)
                    {
                        gui::r = false;
                        gui::g = true;


                    }
                }

                if (gui::blue <= 65)
                {
                    gui::red += 1;
                    if (gui::red >= 244)
                    {

                        gui::r = false;
                        gui::g = true;

                    }
                }


            }
            if (gui::g) {
                if (gui::red <= 65)
                {
                    gui::green += 1;

                    if (gui::green >= 244)
                    {
                        gui::g = false;
                        gui::b = true;


                    }
                }

                if (gui::red >= 244)
                {
                    gui::green -= 1;

                    if (gui::green <= 65)
                    {
                        gui::g = false;
                        gui::b = true;


                    }
                }
            }
            if (gui::b) {
                if (gui::green <= 65)
                {
                    gui::blue += 1;

                    if (gui::blue >= 244)
                    {
                        gui::b = false;
                        gui::r = true;


                    }
                }

                if (gui::green >= 244)
                {
                    gui::blue -= 1;

                    if (gui::blue <= 65)
                    {
                        gui::b = false;
                        gui::r = true;


                    }
                }
            }

            gui::clear_col = ImColor(gui::red, gui::green, gui::blue);
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(8));
    }
}