package com.github.avidyalalala.asm.runtime;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-30
 * Time: 下午3:48
 * To change this template use File | Settings | File Templates.
 * first of all, before playing with it, you need to copy the
 * tools.jar from %javahome%/lib/
 * into the %JAVAHOME% /jre/lib, that is because the IDE will
 * use second one as the lib directory
 *
 */
public class AgentSupporter {
    /**
     * To valid the pid number in windows, it needs to open "任务管理器" ,仔细观察 进程名为"java" 的进程。
     * 在main() 运行时，系统会新增一个 java进程，其pid 即=返回值。在main运行完毕之后，此进程很快消失
     * @return
     */
    public static String getPidFromRuntimeMBean(){
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(jvm);
        String pid = jvm.substring(0, jvm.indexOf("@"));
        return pid;
    }

    public static void attachAgentToJVM(Class<?>[] agentClasses, String JVMPid) throws AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        try {
            final File jarFile= File.createTempFile("agent", ".jar");
            jarFile.deleteOnExit();
            final Manifest manifest = new Manifest();
            final Attributes mainAttributes = manifest.getMainAttributes();
            mainAttributes.put(Attributes.Name.MANIFEST_VERSION,"1.0");
            mainAttributes.put(new Attributes.Name("Agent-Class"), Agent.class.getName());
            mainAttributes.put(new Attributes.Name("Can-Retransform-Classes"),"true");
            mainAttributes.put(new Attributes.Name("Can-Redefine-Classes"),"true");
            final JarOutputStream jos=new JarOutputStream(new FileOutputStream(jarFile),manifest);

            {
                for(Class<?> clazz: agentClasses){
                    final JarEntry agent=new JarEntry(clazz.getName().replace(".","/")+".class");
                    jos.putNextEntry(agent);
                    jos.write(getBytesFromClass(clazz));
                    jos.closeEntry();
                }
            }
            jos.close();

            {
                VirtualMachine vm = VirtualMachine.attach(JVMPid);
                vm.loadAgent(jarFile.getAbsolutePath());
                vm.detach();
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /**
     * 使用IOUils 将 InputStream 读成 byte[]
     * @param classStream
     * @return
     * @throws IOException
     */
    private static byte[] getBytesFromIS (InputStream classStream)throws IOException{
        byte[] data=new byte[16384];
        IOUtils.read(classStream, data);
        return data;
    }

    public static byte[] getBytesFromClass(Class<?> clazz) throws IOException {
        return getBytesFromIS(clazz.getClassLoader().getResourceAsStream(clazz.getName().replace(".","/")+".class"));

    }

}
