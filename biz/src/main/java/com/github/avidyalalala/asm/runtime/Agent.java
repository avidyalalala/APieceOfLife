package com.github.avidyalalala.asm.runtime;

import java.io.IOException;
import java.lang.instrument.*;
import java.security.ProtectionDomain;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-30
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class Agent implements ClassFileTransformer {

    private static Instrumentation instrumentation=null;

    private static Agent transformer;

    public static void agentmain(String s, Instrumentation i) throws IOException, UnmodifiableClassException, ClassNotFoundException {
        System.out.println("hello world:)");
        transformer=new Agent();
        instrumentation=i;
        ClassDefinition cd=new ClassDefinition(Door.class,AgentSupporter.getBytesFromClass(Door.class));
        instrumentation.redefineClasses(cd);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
