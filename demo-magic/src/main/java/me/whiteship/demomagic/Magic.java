package me.whiteship.demomagic;

import me.whiteship.demomojalib.Box;
import me.whiteship.demomojalib.RabbitBox;

public class Magic {

    public static void main(String[] args) {
        Box box = new RabbitBox();
        System.out.println(box.open());
    }

}
