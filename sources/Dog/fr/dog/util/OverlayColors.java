package fr.dog.util;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.util.HashMap;


@UtilityClass
public class OverlayColors {

    public Color getColorFKDR(float fkdr){
        if(fkdr > 0.7 && fkdr < 1.5){
            return new Color(180,180,180); // shit at the game but right to live
        }else if(fkdr > 1.5 && fkdr < 2.5){
            return new Color(86, 208, 98); // ehhh ok ig
        }else if(fkdr > 2.5 && fkdr < 4){
            return new Color(224, 222, 72); // pretty ok
        }else if(fkdr > 4 && fkdr < 6){
            return new Color(224, 153, 72); // good
        }else if(fkdr > 6 && fkdr < 8){
            return new Color(255, 97, 56); // very good
        }else if(fkdr > 8 && fkdr < 10){
            return new Color(255, 26, 26); // sheesh
        }else if(fkdr > 10){
            return new Color(217, 51, 255); // thats nice; might be cheating tho :(
        }
        return new Color(120,120,120); // kinda shit at the game
    }
    public Color getColorKDR(float kdr){
        if(kdr > 0.2 && kdr < 0.4){
            return new Color(180,180,180); // shit at the game but right to live
        }else if(kdr > 0.4 && kdr < 0.8){
            return new Color(86, 208, 98); // ehhh ok ig
        }else if(kdr > 0.8 && kdr < 1.2){
            return new Color(224, 222, 72); // pretty ok
        }else if(kdr > 1.2 && kdr < 1.5){
            return new Color(224, 153, 72); // good
        }else if(kdr > 1.5 && kdr < 1.8){
            return new Color(255, 97, 56); // very good
        }else if(kdr > 1.8 && kdr < 3){
            return new Color(255, 26, 26); // sheesh
        }else if(kdr > 3){
            return new Color(217, 51, 255); // thats nice; might be cheating tho :(
        }
        return new Color(120,120,120); // kinda shit at the game
    }
    public Color getColorWLR(float wlr){
        if(wlr > 0.2 && wlr < 0.4){
            return new Color(180,180,180); // shit at the game but right to live
        }else if(wlr > 0.4 && wlr < 0.8){
            return new Color(86, 208, 98); // ehhh ok ig
        }else if(wlr > 0.8 && wlr < 1.2){
            return new Color(224, 222, 72); // pretty ok
        }else if(wlr > 1.2 && wlr < 2){
            return new Color(224, 153, 72); // good
        }else if(wlr > 2 && wlr < 2.5){
            return new Color(255, 97, 56); // very good
        }else if(wlr > 2.5 && wlr < 5){
            return new Color(255, 26, 26); // sheesh
        }else if(wlr > 5){
            return new Color(217, 51, 255); // thats nice; might be cheating tho :(
        }
        return new Color(120,120,120); // kinda shit at the game
    }


    public Color getKills(float k){
        if(k > 100 && k < 200){
            return new Color(180,180,180); // shit at the game but right to live
        }else if(k > 200 && k < 500){
            return new Color(86, 208, 98); // ehhh ok ig
        }else if(k > 500 && k < 1000){
            return new Color(224, 222, 72); // pretty ok
        }else if(k > 1000 && k < 5000){
            return new Color(224, 153, 72); // good
        }else if(k > 5000 && k < 10000){
            return new Color(255, 97, 56); // very good
        }else if(k > 10000 && k < 25000){
            return new Color(255, 26, 26); // sheesh
        }else if(k > 25000){
            return new Color(217, 51, 255); // thats nice; might be cheating tho :(
        }
        return new Color(120,120,120); // kinda shit at the game
    }
    public Color getWins(float k){
        if(k > 50 && k < 100){
            return new Color(180,180,180); // shit at the game but right to live
        }else if(k > 100 && k < 200){
            return new Color(86, 208, 98); // ehhh ok ig
        }else if(k > 200 && k < 500){
            return new Color(224, 222, 72); // pretty ok
        }else if(k > 500 && k < 1000){
            return new Color(224, 153, 72); // good
        }else if(k > 1000 && k < 5000){
            return new Color(255, 97, 56); // very good
        }else if(k > 5000 && k < 10000){
            return new Color(255, 26, 26); // sheesh
        }else if(k > 10000){
            return new Color(217, 51, 255); // thats nice; might be cheating tho :(
        }
        return new Color(120,120,120); // kinda shit at the game
    }



}
