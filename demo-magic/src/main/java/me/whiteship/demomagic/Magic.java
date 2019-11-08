package me.whiteship.demomagic;

import me.whiteship.demomojalib.Box;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Magic {

    public static void main(String[] args) {
        ByteBuddyAgent.install();
        new ByteBuddy().redefine(Box.class)
                .method(named("open"))
                .intercept(FixedValue.value("Rabbit"))
                .make()
                .load(Box.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        Box box = new Box();
        System.out.println(box.open());
    }

}
