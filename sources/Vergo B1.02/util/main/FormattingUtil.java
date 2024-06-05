package xyz.vergoclient.util.main;

import xyz.vergoclient.security.account.AccountUtils;

public class FormattingUtil {

    public static String formatUID() {
        String uidReformat = "";

        if (AccountUtils.account.uid < 9) {
            uidReformat = "00" + AccountUtils.account.uid;
            return uidReformat;
        } else if (AccountUtils.account.uid > 9 && AccountUtils.account.uid < 99) {
            uidReformat = "0" + AccountUtils.account.uid;
            return uidReformat;
        } else {
            uidReformat = "" + AccountUtils.account.uid;
            return uidReformat;
        }
    }

}
