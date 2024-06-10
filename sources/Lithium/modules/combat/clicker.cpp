
#include "..\..\klynt.h"

namespace random {
    int left_edited_cps = 0;
    int left_reset_value = 0;
    int left_clicks = 0;

    int right_edited_cps = 0;
    int right_reset_value = 0;
    int right_clicks = 0;

    int left_drop_reset_value = 0;
    int left_drop_counter = 0;
    bool left_drop = false;

    int left_spike_reset_value = 0;
    int left_spike_counter = 0;
    bool left_spike = false;

    int left_counter = 0;
    int left_counter_reset_value = 0;

    int right_drop_reset_value = 0;
    int right_drop_counter = 0;
    bool right_drop = false;

    int right_spike_reset_value = 0;
    int right_spike_counter = 0;
    bool right_spike = false;

    int right_counter = 0;
    int right_counter_reset_value = 0;
}

int randomization(int cps, int &edited_cps, int &reset_value, int &clicks, int &counter,
                  int &counter_reset_value, bool &drop, bool &spike, int &drop_reset_value, int &drop_counter,
                  int &spike_reset_value, int &spike_counter) {
    if (clicks >= reset_value || clicks == 0) {
        reset_value = v::utils::random_int(5, 15);
        edited_cps = cps + v::utils::random_int(-3, 3);

        clicks = 0;
    }

    clicks++;
    counter++;

    if (counter >= counter_reset_value || counter == 0) {
        counter_reset_value = v::utils::random_int(15, 25);

        counter = 0;

        // DROPS

        if (!drop && !spike && v::utils::random_int(0, 100) <= 30) {
            drop = true;
            drop_reset_value = v::utils::random_int(10, 25);
            drop_counter = 0;
        }

        // SPIKES

        if (!spike && !drop && v::utils::random_int(0, 100) <= 20) {
            spike = true;
            spike_reset_value = v::utils::random_int(10, 25);
            spike_counter = 0;
        }
    }

    if (drop && drop_counter <= drop_reset_value) {
        drop_counter++;
        return v::utils::random_int(550, 600) / edited_cps;
    } else if (drop && drop_counter >= drop_reset_value) {
        drop_counter = 0;
        drop = false;
    }

    if (spike && spike_counter <= spike_reset_value) {
        spike_counter++;
        return v::utils::random_int(450, 500) / edited_cps;
    } else if (spike && spike_counter >= spike_reset_value) {
        spike_counter = 0;
        spike = false;
    }

    return v::utils::random_int(450, 550) / edited_cps;
}

float random_float(float min_value, float max_value) {
    std::random_device dev;
    std::mt19937 rng(dev());
    std::uniform_real_distribution<> dist(min_value, max_value);
    return dist(rng);
}

size_t random_delay(float current_cps) {
    static size_t last_delay = 0;

    float min_cps = current_cps - random_float(1, 3);
    size_t min_delay = (random_float(950, 1050) / min_cps);

    float max_cps = current_cps + random_float(1, 3);
    size_t max_delay = (random_float(950, 1050) / max_cps);

    size_t average_delay = (size_t) random_float(max_delay, min_delay);
    if (average_delay == last_delay) { average_delay++; }
    last_delay = average_delay;

    return average_delay;
}

float random_cps(float current_cps) {
    if ((size_t) random_float(1, 10) ==
        (size_t) random_float(1, 10))
        current_cps -= random_float(0.5, 1.00);

    else if ((size_t) random_float(1, 10) ==
        (size_t) random_float(1, 10))
        current_cps += random_float(0.5, 1.00);

    return current_cps;
}

void blockhit_thread() {
    while (v::info::verified) {
        while (v::modules::clicker::left::blockhit && v::modules::clicker::left::enabled
               && GetAsyncKeyState(0x57) & 0x8000 && GetAsyncKeyState(VK_LBUTTON) & 0x8000 &&
               !(GetAsyncKeyState(VK_RBUTTON) & 0x8000)) {
            if (FindWindow(((LPCWSTR)"LWJGL"), nullptr) == GetForegroundWindow()) {
                if (!v::local_player::inventory_open) {
                    PostMessageW(v::info::minecraft_window, WM_RBUTTONDOWN, MK_RBUTTON, MAKELPARAM(0, 0));
                    std::this_thread::sleep_for(std::chrono::milliseconds((int) v::utils::random_int(50, 75)));

                    PostMessageW(v::info::minecraft_window, WM_RBUTTONUP, MK_RBUTTON, MAKELPARAM(0, 0));
                    std::this_thread::sleep_for(std::chrono::milliseconds((int) v::utils::random_int(50, 75)));
                }
            }
            std::this_thread::sleep_for(std::chrono::milliseconds((int) v::modules::clicker::left::blockhit_delay * 5));
        }
        std::this_thread::sleep_for(std::chrono::milliseconds((int) v::modules::clicker::left::blockhit_delay * 5));
    }
}

int first = 0;
bool rmb_lock_held = false;

void rmb_lock_thread() {
    while (v::info::verified) {
        if (first == 1)
            rmb_lock_held = true;
        else
            rmb_lock_held = false;

        if (!v::modules::clicker::left::rmb_lock)
            rmb_lock_held = false;
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void rmb_lock_first_thread() {
    while (v::info::verified) {
        while (GetAsyncKeyState(VK_LBUTTON) & 0x8000 && !(GetAsyncKeyState(VK_RBUTTON) & 0x8000)) {
            first = 0;
            std::this_thread::sleep_for(std::chrono::milliseconds(10));
        }
        while (!(GetAsyncKeyState(VK_LBUTTON) & 0x8000) && GetAsyncKeyState(VK_RBUTTON) & 0x8000) {
            first = 1;
            std::this_thread::sleep_for(std::chrono::milliseconds(10));
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void v::modules::clicker::left::jitter::thread() {
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE) &blockhit_thread,
                 nullptr, 0, nullptr);
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE) &rmb_lock_thread,
                 nullptr, 0, nullptr);
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE) &rmb_lock_first_thread,
                 nullptr, 0, nullptr);
    while (v::info::verified) {
        while (v::modules::clicker::left::enabled && GetAsyncKeyState(VK_LBUTTON) & 0x8000 &&
               !rmb_lock_held) {
            if (v::modules::clicker::left::x_jitter + v::modules::clicker::left::y_jitter != 0) {
                POINT P;
                GetCursorPos(&P);
                HWND window = WindowFromPoint(P);
                if (!v::local_player::inventory_open) {
                    if (FindWindow(((LPCWSTR)"LWJGL"), nullptr) == GetForegroundWindow()) {

                        if (v::modules::clicker::left::slot[v::local_player::current_slot -
                                                            1]) {
                            INT64 random = random_float(1, 12);

                            if (v::modules::clicker::left::x_jitter != 0) {
                                if (random <= 3) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::x_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x + 1, P.y);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                                if (random > 3 && random <= 6) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::x_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x - 1, P.y);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                                if (random > 6 && random <= 9) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::x_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x - 1, P.y);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                                if (random > 9 && random <= 12) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::x_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x + 1, P.y);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                            }
                            if (v::modules::clicker::left::y_jitter != 0) {
                                if (random <= 3) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::y_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x, P.y + 1);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                                if (random > 3 && random <= 6) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::y_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x, P.y - 1);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                                if (random > 6 && random <= 9) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::y_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x, P.y + 1);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                                if (random > 9 && random <= 12) {
                                    for (size_t f = 0;
                                         f < v::modules::clicker::left::y_jitter; f++) {
                                        GetCursorPos(&P);
                                        SetCursorPos(P.x, P.y - 1);
                                        std::this_thread::sleep_for(std::chrono::milliseconds(1));
                                    }
                                }
                            }
                        }
                    }
                }
                std::this_thread::sleep_for(std::chrono::milliseconds(5));
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void v::modules::clicker::left::thread() {
    bool first_click = true;
    while (v::info::verified) {
        if (v::modules::clicker::left::enabled && GetAsyncKeyState(VK_LBUTTON) & 0x8000 &&
            !rmb_lock_held) {
            while (v::modules::clicker::left::enabled && GetAsyncKeyState(VK_LBUTTON) & 0x8000) {

                POINT pos1;
                GetCursorPos(&pos1);
                HWND window = WindowFromPoint(pos1);
                if (FindWindow((LPCWSTR)("LWJGL"), nullptr) == GetForegroundWindow()) {

                    if (ScreenToClient(window, &pos1)) {

                        if (pos1.y > 20) {

                            if (v::modules::clicker::left::inventory) {

                                if (!v::local_player::inventory_open) {
                                    if (v::modules::clicker::left::slot[
                                            v::local_player::current_slot - 1]) {

                                        if (v::utils::random_int(0, 100) <= 2)
                                            std::this_thread::sleep_for(std::chrono::milliseconds(
                                                    randomization(v::modules::clicker::left::cps,
                                                                  random::left_edited_cps,
                                                                  random::left_reset_value,
                                                                  random::left_clicks,
                                                                  random::left_counter,
                                                                  random::left_counter_reset_value,
                                                                  random::left_drop, random::left_spike,
                                                                  random::left_drop_reset_value,
                                                                  random::left_drop_counter,
                                                                  random::left_spike_reset_value,
                                                                  random::left_spike_counter) * 3));

                                        if (!first_click) {

                                            PostMessage(window, WM_LBUTTONDOWN, MK_LBUTTON,
                                                        MAKELPARAM(pos1.x, pos1.y));

                                        }

                                        std::this_thread::sleep_for(std::chrono::milliseconds(
                                                randomization(v::modules::clicker::left::cps,
                                                              random::left_edited_cps,
                                                              random::left_reset_value,
                                                              random::left_clicks,
                                                              random::left_counter,
                                                              random::left_counter_reset_value,
                                                              random::left_drop, random::left_spike,
                                                              random::left_drop_reset_value,
                                                              random::left_drop_counter,
                                                              random::left_spike_reset_value,
                                                              random::left_spike_counter)));

                                        PostMessage(window, WM_LBUTTONUP, MK_LBUTTON,
                                                    MAKELPARAM(pos1.x, pos1.y));

                                        std::this_thread::sleep_for(std::chrono::milliseconds(
                                                randomization(v::modules::clicker::left::cps,
                                                              random::left_edited_cps,
                                                              random::left_reset_value,
                                                              random::left_clicks,
                                                              random::left_counter,
                                                              random::left_counter_reset_value,
                                                              random::left_drop, random::left_spike,
                                                              random::left_drop_reset_value,
                                                              random::left_drop_counter,
                                                              random::left_spike_reset_value,
                                                              random::left_spike_counter)));

                                    }

                                } else {

                                    if (v::utils::random_int(0, 100) <= 2)
                                        std::this_thread::sleep_for(std::chrono::milliseconds(
                                                randomization(v::modules::clicker::left::cps,
                                                              random::left_edited_cps,
                                                              random::left_reset_value,
                                                              random::left_clicks,
                                                              random::left_counter,
                                                              random::left_counter_reset_value,
                                                              random::left_drop, random::left_spike,
                                                              random::left_drop_reset_value,
                                                              random::left_drop_counter,
                                                              random::left_spike_reset_value,
                                                              random::left_spike_counter) * 3));

                                    if (!first_click) {

                                        PostMessage(window, WM_LBUTTONDOWN, MK_LBUTTON,
                                                    MAKELPARAM(pos1.x, pos1.y));

                                    }

                                    std::this_thread::sleep_for(std::chrono::milliseconds(
                                            randomization(v::modules::clicker::left::cps,
                                                          random::left_edited_cps,
                                                          random::left_reset_value, random::left_clicks,
                                                          random::left_counter,
                                                          random::left_counter_reset_value,
                                                          random::left_drop, random::left_spike,
                                                          random::left_drop_reset_value,
                                                          random::left_drop_counter,
                                                          random::left_spike_reset_value,
                                                          random::left_spike_counter)));

                                    PostMessage(window, WM_LBUTTONUP, MK_LBUTTON,
                                                MAKELPARAM(pos1.x, pos1.y));

                                    std::this_thread::sleep_for(std::chrono::milliseconds(
                                            randomization(v::modules::clicker::left::cps,
                                                          random::left_edited_cps,
                                                          random::left_reset_value, random::left_clicks,
                                                          random::left_counter,
                                                          random::left_counter_reset_value,
                                                          random::left_drop, random::left_spike,
                                                          random::left_drop_reset_value,
                                                          random::left_drop_counter,
                                                          random::left_spike_reset_value,
                                                          random::left_spike_counter)));

                                }

                            } else {

                                if (!v::local_player::inventory_open) {

                                    if (v::modules::clicker::left::slot[
                                            v::local_player::current_slot - 1]) {

                                        if (v::utils::random_int(0, 100) <= 2)
                                            std::this_thread::sleep_for(std::chrono::milliseconds(
                                                    randomization(v::modules::clicker::left::cps,
                                                                  random::left_edited_cps,
                                                                  random::left_reset_value,
                                                                  random::left_clicks,
                                                                  random::left_counter,
                                                                  random::left_counter_reset_value,
                                                                  random::left_drop, random::left_spike,
                                                                  random::left_drop_reset_value,
                                                                  random::left_drop_counter,
                                                                  random::left_spike_reset_value,
                                                                  random::left_spike_counter) * 3));

                                        if (!first_click) {

                                            PostMessage(window, WM_LBUTTONDOWN, MK_LBUTTON,
                                                        MAKELPARAM(pos1.x, pos1.y));

                                        }

                                        std::this_thread::sleep_for(std::chrono::milliseconds(
                                                randomization(v::modules::clicker::left::cps,
                                                              random::left_edited_cps,
                                                              random::left_reset_value,
                                                              random::left_clicks,
                                                              random::left_counter,
                                                              random::left_counter_reset_value,
                                                              random::left_drop, random::left_spike,
                                                              random::left_drop_reset_value,
                                                              random::left_drop_counter,
                                                              random::left_spike_reset_value,
                                                              random::left_spike_counter)));

                                        PostMessage(window, WM_LBUTTONUP, MK_LBUTTON,
                                                    MAKELPARAM(pos1.x, pos1.y));

                                        std::this_thread::sleep_for(std::chrono::milliseconds(
                                                randomization(v::modules::clicker::left::cps,
                                                              random::left_edited_cps,
                                                              random::left_reset_value,
                                                              random::left_clicks,
                                                              random::left_counter,
                                                              random::left_counter_reset_value,
                                                              random::left_drop, random::left_spike,
                                                              random::left_drop_reset_value,
                                                              random::left_drop_counter,
                                                              random::left_spike_reset_value,
                                                              random::left_spike_counter)));

                                    }

                                }
                            }

                        }

                    }

                }

                if (first_click) {
                    first_click = false;
                }

                std::this_thread::sleep_for(std::chrono::milliseconds(1));
            }
        } else { first_click = true; }
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void v::modules::clicker::right::thread() {
    bool first_click = true;
    while (v::info::verified) {
        if (v::modules::clicker::right::enabled && GetAsyncKeyState(VK_RBUTTON) & 0x8000 &&
            !rmb_lock_held) {
            while (v::modules::clicker::right::enabled && GetAsyncKeyState(VK_RBUTTON) & 0x8000) {
                POINT pos1;
                GetCursorPos(&pos1);
                HWND window = WindowFromPoint(pos1);

                if (FindWindow((LPCWSTR)("LWJGL"), nullptr) == GetForegroundWindow()) {
                    if (ScreenToClient(window, &pos1)) {
                        if (pos1.y > 20) {

                            if (v::modules::clicker::right::slot[v::local_player::current_slot -
                                                                 1]) {

                                if (v::utils::random_int(0, 100) <= 2)
                                    std::this_thread::sleep_for(std::chrono::milliseconds(
                                            randomization(v::modules::clicker::right::cps,
                                                          random::right_edited_cps,
                                                          random::right_reset_value,
                                                          random::right_clicks,
                                                          random::right_counter,
                                                          random::right_counter_reset_value,
                                                          random::right_drop, random::right_spike,
                                                          random::right_drop_reset_value,
                                                          random::right_drop_counter,
                                                          random::right_spike_reset_value,
                                                          random::right_spike_counter) * 3));

                                if (!first_click) {
                                    SendMessage(window, WM_RBUTTONDOWN, MK_RBUTTON,
                                                MAKELPARAM(pos1.x, pos1.y));
                                }

                                std::this_thread::sleep_for(std::chrono::milliseconds(
                                        randomization(v::modules::clicker::right::cps,
                                                      random::right_edited_cps,
                                                      random::right_reset_value, random::right_clicks,
                                                      random::right_counter,
                                                      random::right_counter_reset_value,
                                                      random::right_drop, random::right_spike,
                                                      random::right_drop_reset_value,
                                                      random::right_drop_counter,
                                                      random::right_spike_reset_value,
                                                      random::right_spike_counter)));

                                SendMessage(window, WM_RBUTTONUP, MK_RBUTTON,
                                            MAKELPARAM(pos1.x, pos1.y));

                                std::this_thread::sleep_for(std::chrono::milliseconds(
                                        randomization(v::modules::clicker::right::cps,
                                                      random::right_edited_cps,
                                                      random::right_reset_value, random::right_clicks,
                                                      random::right_counter,
                                                      random::right_counter_reset_value,
                                                      random::right_drop, random::right_spike,
                                                      random::right_drop_reset_value,
                                                      random::right_drop_counter,
                                                      random::right_spike_reset_value,
                                                      random::right_spike_counter)));

                            }
                        }
                    }
                }

                if (first_click) {
                    first_click = false;
                }

                std::this_thread::sleep_for(std::chrono::milliseconds(1));

            }

        } else { first_click = true; }
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}