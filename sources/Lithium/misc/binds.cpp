#include "..\klynt.h"


std::string getbindtext(size_t hex);

void v::binds::thread() {
    size_t selected_bind = 0;
    bool bind_break = false;

    while (v::info::verified) {
        if (v::modules::clicker::left::bind_pressed) {

            std::this_thread::sleep_for(std::chrono::milliseconds(1));

            selected_bind = 1;
            v::modules::clicker::left::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;

            while (v::modules::clicker::left::selected_bind == 0) {

                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }

                std::this_thread::sleep_for(std::chrono::milliseconds(2));

                for (size_t i = 3; i < 256; i++) {

                    if (i != 13) {

                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::clicker::left::selected_bind == 0) {

                            if (!lith_bind_first_loop) {

                                v::modules::clicker::left::selected_bind = i;
                                v::modules::clicker::left::bind = getbindtext(i);

                                if (i == 27) {
                                    v::modules::clicker::left::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::clicker::left::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::clicker::left::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::clicker::left::selected_bind) & 0x8000) {
            if (!v::modules::clicker::left::enabled) {
                while (GetAsyncKeyState(v::modules::clicker::left::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::clicker::left::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::clicker::left::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::clicker::left::enabled = false;
            }
        }

        if (v::modules::clicker::right::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::clicker::right::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::clicker::right::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::clicker::right::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::clicker::right::selected_bind = i; v::modules::clicker::right::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::clicker::right::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::clicker::right::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::clicker::right::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::clicker::right::selected_bind) & 0x8000) {
            if (!v::modules::clicker::right::enabled) {
                while (GetAsyncKeyState(v::modules::clicker::right::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::clicker::right::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::clicker::right::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::clicker::right::enabled = false;
            }
        }

        if (v::modules::velocity::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::velocity::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::velocity::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::velocity::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::velocity::selected_bind = i; v::modules::velocity::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::velocity::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::velocity::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::velocity::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::velocity::selected_bind) & 0x8000) {
            if (!v::modules::velocity::enabled) {
                while (GetAsyncKeyState(v::modules::velocity::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::velocity::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::velocity::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::velocity::enabled = false;
            }
        }

        if (v::modules::reach::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::reach::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::reach::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::reach::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::reach::selected_bind = i; v::modules::reach::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::reach::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::reach::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::reach::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::reach::selected_bind) & 0x8000) {
            if (!v::modules::reach::enabled) {
                while (GetAsyncKeyState(v::modules::reach::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::reach::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::reach::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::reach::enabled = false;
            }
        }

        if (v::modules::velocity::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::velocity::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::velocity::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::velocity::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::velocity::selected_bind = i; v::modules::velocity::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::velocity::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::velocity::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::velocity::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::velocity::selected_bind) & 0x8000) {
            if (!v::modules::velocity::enabled) {
                while (GetAsyncKeyState(v::modules::velocity::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::velocity::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::velocity::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::velocity::enabled = false;
            }
        }

        if (v::modules::throwpot::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::throwpot::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::throwpot::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::throwpot::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::throwpot::selected_bind = i; v::modules::throwpot::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::throwpot::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::throwpot::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::throwpot::bind_pressed = false;
                bool_counter++;
            }
        }

        if (v::modules::fly::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::fly::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::fly::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::fly::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::fly::selected_bind = i; v::modules::fly::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::fly::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::fly::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::fly::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::fly::selected_bind) & 0x8000) {
            if (!v::modules::fly::waiting) {
                if (!v::modules::fly::enabled) {
                    while (GetAsyncKeyState(v::modules::fly::selected_bind) & 0x8000) {
                        std::this_thread::sleep_for(std::chrono::milliseconds(5));
                    }v::modules::fly::enabled = true;
                }
                else {
                    while (GetAsyncKeyState(v::modules::fly::selected_bind) & 0x8000) {
                        std::this_thread::sleep_for(std::chrono::milliseconds(5));
                    }v::modules::fly::enabled = false;
                }
            }
        }

        if (v::modules::nametags::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::nametags::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::nametags::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::nametags::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::nametags::selected_bind = i; v::modules::nametags::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::nametags::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::nametags::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::nametags::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::nametags::selected_bind) & 0x8000) {
            if (!v::modules::nametags::enabled) {
                while (GetAsyncKeyState(v::modules::nametags::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::nametags::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::nametags::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::nametags::enabled = false;
            }
        }

        if (v::modules::esp::bind_pressed) {

            std::this_thread::sleep_for(std::chrono::milliseconds(1));

            selected_bind = 1;
            v::modules::esp::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;

            while (v::modules::esp::selected_bind == 0) {

                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }

                std::this_thread::sleep_for(std::chrono::milliseconds(2));

                for (size_t i = 3; i < 256; i++) {

                    if (i != 13) {

                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::esp::selected_bind == 0) {

                            if (!lith_bind_first_loop) {

                                v::modules::esp::selected_bind = i;
                                v::modules::esp::bind = getbindtext(i);

                                if (i == 27) {
                                    v::modules::esp::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::esp::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::esp::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::esp::selected_bind) & 0x8000) {
            if (!v::modules::esp::enabled) {
                while (GetAsyncKeyState(v::modules::esp::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::esp::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::esp::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::esp::enabled = false;
            }
        }

        if (v::modules::cavefinder::bind_pressed) {

            std::this_thread::sleep_for(std::chrono::milliseconds(1));

            selected_bind = 1;
            v::modules::cavefinder::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;

            while (v::modules::cavefinder::selected_bind == 0) {

                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }

                std::this_thread::sleep_for(std::chrono::milliseconds(2));

                for (size_t i = 3; i < 256; i++) {

                    if (i != 13) {

                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::cavefinder::selected_bind == 0) {

                            if (!lith_bind_first_loop) {

                                v::modules::cavefinder::selected_bind = i;
                                v::modules::cavefinder::bind = getbindtext(i);

                                if (i == 27) {
                                    v::modules::cavefinder::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::cavefinder::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::cavefinder::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::cavefinder::selected_bind) & 0x8000) {
            if (!v::modules::cavefinder::enabled) {
                while (GetAsyncKeyState(v::modules::cavefinder::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::cavefinder::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::cavefinder::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::cavefinder::enabled = false;
            }
        }

        if (v::modules::timer::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::timer::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::timer::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::timer::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::timer::selected_bind = i; v::modules::timer::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::timer::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::timer::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::timer::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::timer::selected_bind) & 0x8000) {
            if (!v::modules::timer::enabled) {
                while (GetAsyncKeyState(v::modules::timer::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::timer::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::timer::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::timer::enabled = false;
            }
        }

        if (v::modules::bhop::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::bhop::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::bhop::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::bhop::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::bhop::selected_bind = i; v::modules::bhop::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::bhop::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::bhop::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::bhop::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::bhop::selected_bind) & 0x8000) {
            if (!v::modules::bhop::enabled) {
                while (GetAsyncKeyState(v::modules::bhop::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::bhop::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::bhop::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::bhop::enabled = false;
            }
        }

        if (v::modules::speed::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::speed::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::speed::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::speed::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::speed::selected_bind = i; v::modules::speed::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::speed::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::speed::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::speed::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::speed::selected_bind) & 0x8000) {
            if (!v::modules::speed::enabled) {
                while (GetAsyncKeyState(v::modules::speed::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::speed::enabled = true;
            }
            else {
                while (GetAsyncKeyState(v::modules::speed::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::speed::enabled = false;
            }
        }

        if (v::modules::destruct::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::destruct::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::destruct::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::destruct::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::destruct::selected_bind = i; v::modules::destruct::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::destruct::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::destruct::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::destruct::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::destruct::selected_bind) & 0x8000) {

            v::modules::destruct::execute();

        }

        if (v::modules::hide::bind_pressed) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
            selected_bind = 1;
            v::modules::hide::selected_bind = 0;
            bool lith_bind_first_loop = false; size_t bool_counter = 0;
            while (v::modules::hide::selected_bind == 0) {
                if (bool_counter < 1) { lith_bind_first_loop = true; }
                else { lith_bind_first_loop = false; }
                std::this_thread::sleep_for(std::chrono::milliseconds(2));
                for (size_t i = 3; i < 256; i++) {
                    if (i != 13) {
                        if (GetAsyncKeyState((i)&SHRT_MAX) && v::modules::hide::selected_bind == 0) {
                            if (!lith_bind_first_loop) {
                                v::modules::hide::selected_bind = i; v::modules::hide::bind = getbindtext(i);
                                if (i == 27) {
                                    v::modules::hide::bind = "[NONE]";
                                    selected_bind = 1;
                                    v::modules::hide::selected_bind = 0;
                                    bind_break = true;
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(250));
                            }
                        }
                    }
                }
                if (bind_break) { bind_break = false; break; }
                selected_bind = 0;
                v::modules::hide::bind_pressed = false;
                bool_counter++;
            }
        }
        if (GetAsyncKeyState(v::modules::hide::selected_bind) & 0x8000) {
            if (!v::modules::hide::enabled) {
                ShowWindow(v::gui::hwnd, SW_HIDE);
                while (GetAsyncKeyState(v::modules::hide::selected_bind) & 0x8000) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }v::modules::hide::enabled = true;
            }
            else {
                ShowWindow(v::gui::hwnd, SW_SHOW);
                while (GetAsyncKeyState(v::modules::hide::selected_bind) & 0x8000) {

                    std::this_thread::sleep_for(std::chrono::milliseconds(5));

                }
                v::modules::hide::enabled = false;
            }

        }

        std::this_thread::sleep_for(std::chrono::milliseconds(50));
    }

}


std::string getbindtext(size_t hex) {
	const char* const lith_key_string[] = {
		"Unknown",
		"LButton",
		"RButton",
		"CANCEL",
		"MButton",
		"MButton2",
		"MButton3",
		"Unknown",
		"Back",
		"Tab",
		"Unknown",
		"Unknown",
		"Clear",
		"Return",
		"Unknown",
		"Unknown",
		"Shift",
		"Control",
		"Menu",
		"Pause",
		"Capital",
		"Unknown",
		"Unknown",
		"Unknown",
		"Final",
		"Unknown",
		"Unknown",
		"Escape",
		"Convert",
		"NonConvert",
		"Accept",
		"ModeChange",
		"Space",
		"Prior",
		"Next",
		"End",
		"Home",
		"Left",
		"Up",
		"Right",
		"Down",
		"Select",
		"Print",
		"Execute",
		"SnpShot",
		"Insert",
		"Delete",
		"Help",
		"0",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"A",
		"B",
		"C",
		"D",
		"E",
		"F",
		"G",
		"H",
		"I",
		"J",
		"K",
		"L",
		"M",
		"N",
		"O",
		"P",
		"Q",
		"R",
		"S",
		"T",
		"U",
		"V",
		"W",
		"X",
		"Y",
		"Z",
		"Lwin",
		"Rwin",
		"Apps",
		"Unknown",
		"Sleep",
		"0",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"*",
		"+",
		"Separator",
		"-",
		"Decimal",
		"/",
		"F1",
		"F2",
		"F3",
		"F4",
		"F5",
		"F6",
		"F7",
		"F8",
		"F9",
		"F10",
		"F11",
		"F12",
		"F13",
		"F14",
		"F15",
		"F16",
		"F17",
		"F18",
		"F19",
		"F20",
		"F21",
		"F22",
		"F23",
		"F24",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Numlock",
		"Scroll",
		"Equal",
		"Masshou",
		"Touroku",
		"Loya",
		"Roya",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"LShift",
		"RShift",
		"Control",
		"Control",
		"Menu",
		"RMenu",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
	};
	return "[Bind: " + std::string(lith_key_string[hex]) + "]";
}