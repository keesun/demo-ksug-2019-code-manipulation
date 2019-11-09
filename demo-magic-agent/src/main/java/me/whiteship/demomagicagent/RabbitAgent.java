package me.whiteship.demomagicagent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class RabbitAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(named("me.whiteship.demomojalib.Box"))
                .transform(((builder, typeDescription, classLoader, module) ->
                    builder.method(named("open")).intercept(FixedValue.value("Rabbit")))).installOn(instrumentation);
    }

}
