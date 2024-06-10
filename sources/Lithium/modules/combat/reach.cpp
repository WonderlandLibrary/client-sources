
#include "..\..\klynt.h"

static float defaultreachf = 3.0f;
static double defaultreachd = 3.0;
static double defaultreachl = 4.5;
static double newreachl = 7.5;

namespace v
{
    namespace modules
    {
        namespace reach
        {
            void enable() {
                float newreachf = v::utils::random_float(v::modules::reach::minvalue, v::modules::reach::maxvalue);
                double newreachd = newreachf;

                float bufferf;
                double bufferd;

                if (v::modules::reach::maxvalue >= 4.25f) {
                    for (size_t x = 0; x < v::modules::reach::unlimit.size(); x++) {
                        if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::unlimit[x], &bufferd, sizeof(double), 0)) {
                            if (bufferd <= 7.5 && bufferd >= 4.5) {
                                WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::unlimit[x], &newreachl, sizeof(double), 0);
                            }
                            else {
                                v::modules::reach::unlimit.erase(v::modules::reach::unlimit.begin() + x);
                                x = 0;
                            }
                        }
                        else {
                            v::modules::reach::unlimit.erase(v::modules::reach::unlimit.begin() + x);
                            x = 0;
                        }
                    }
                }
                else {
                    for (size_t x = 0; x < v::modules::reach::unlimit.size(); x++) {
                        if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::unlimit[x], &bufferd, sizeof(double), 0)) {
                            if (bufferd <= 7.5 && bufferd >= 4.5) {
                                WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::unlimit[x], &defaultreachl, sizeof(double), 0);
                            }
                            else {
                                v::modules::reach::unlimit.erase(v::modules::reach::unlimit.begin() + x);
                                x = 0;
                            }
                        }
                        else {
                            v::modules::reach::unlimit.erase(v::modules::reach::unlimit.begin() + x);
                            x = 0;
                        }
                    }
                }

                for (size_t x = 0; x < v::modules::reach::doubles.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::doubles[x], &bufferd, sizeof(double), 0)) {
                        if (bufferd <= 6.0 && bufferd >= 3.0) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::doubles[x], &newreachd, sizeof(double), 0);
                        }
                        else {
                            v::modules::reach::doubles.erase(v::modules::reach::doubles.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::reach::doubles.erase(v::modules::reach::doubles.begin() + x);
                        x = 0;
                    }
                }

                for (size_t x = 0; x < v::modules::reach::floats.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)(v::modules::reach::floats[x]), &bufferf, sizeof(float), 0)) {
                        if (bufferf <= 6.0f && bufferf >= 3.0f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::floats[x], &newreachf, sizeof(float), 0);
                        }
                        else {
                            v::modules::reach::floats.erase(v::modules::reach::floats.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::reach::floats.erase(v::modules::reach::floats.begin() + x);
                        x = 0;
                    }
                }

            }
            void disable() {

                float bufferf;
                double bufferd;

                for (size_t x = 0; x < v::modules::reach::unlimit.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::unlimit[x], &bufferd, sizeof(double), 0)) {
                        if (bufferd <= 7.5 && bufferd >= 4.5) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::unlimit[x], &defaultreachl, sizeof(double), 0);
                        }
                        else {
                            v::modules::reach::unlimit.erase(v::modules::reach::unlimit.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::reach::unlimit.erase(v::modules::reach::unlimit.begin() + x);
                        x = 0;
                    }
                }

                for (size_t x = 0; x < v::modules::reach::doubles.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::doubles[x], &bufferd, sizeof(double), 0)) {
                        if (bufferd <= 6.0 && bufferd >= 3.0) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::doubles[x], &defaultreachd, sizeof(double), 0);
                        }
                        else {
                            v::modules::reach::doubles.erase(v::modules::reach::doubles.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::reach::doubles.erase(v::modules::reach::doubles.begin() + x);
                        x = 0;
                    }
                }

                for (size_t x = 0; x < v::modules::reach::floats.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::floats[x], &bufferf, sizeof(float), 0)) {
                        if (bufferf <= 6.0f && bufferf >= 3.0f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::reach::floats[x], &defaultreachf, sizeof(float), 0);
                        }
                        else {
                            v::modules::reach::floats.erase(v::modules::reach::floats.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::reach::floats.erase(v::modules::reach::floats.begin() + x);
                        x = 0;
                    }
                }

            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::reach::enabled) {
                        v::modules::reach::used = true;
                        if (v::modules::reach::while_moving) {
                            if (v::local_player::moving) {
                                enable();
                            }
                            else {
                                disable();
                            }
                        }
                        else {
                            enable();
                        }
                    }
                    else {
                        if (v::modules::reach::used) {
                            disable();
                            v::modules::reach::used = false;
                        }

                    }

                    if (v::modules::reach::enabled) {
                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                    }
                    else {
                        std::this_thread::sleep_for(std::chrono::milliseconds(50));
                    }
                }
            }
        }
    }
}