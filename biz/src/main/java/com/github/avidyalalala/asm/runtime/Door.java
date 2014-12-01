package com.github.avidyalalala.asm.runtime;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-30
 * Time: 下午7:12
 * To change this template use File | Settings | File Templates.
 */
public class Door {

    public static void main(String[] args) throws AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        System.out.println(System.currentTimeMillis());
        AgentSupporter.attachAgentToJVM(new Class<?>[]{Agent.class/*, Util.class,
                Profile.class, ProfileClassAdapter.class,
                ProfileMethodAdapter.class*/}, AgentSupporter.getPidFromRuntimeMBean());

        sayHello(5);
        sayWorld();

    }

    public static void sayHello(int s) {
        System.out.println("Hello");
    }

    public static void sayWorld() {
        System.out.println("World!");
    }
}
