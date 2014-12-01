package com.github.avidyalalala.asm.compile;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-26
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 *
 */
//TODO:change the bytecode in compile time is a very triky thing.
/*until now, i have not able to figure out how lombok do the work.
        * lombok take the benefits of Anotation Lable of JAVA.according to the RENTATION scope. the lombok
        * achieve the purpose of manipulating bytecodes and replacing the origin class at the comile time.  */
public class Door {

    public static void main(String[] args) throws Exception {
        // 使用全限定名，创建一个ClassReader对象
        ClassReader classReader = new ClassReader(
                "com.github.avidyalalala.asm.compile.Person");
        // 构建一个ClassWriter对象，并设置让系统自动计算栈和本地变量大小
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        ClassAdapter classAdapter = new GeneralClassAdapter(classWriter);

        classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);

        byte[] classFile = classWriter.toByteArray();

        // 将这个类输出到原先的类文件目录下，这时原先的类文件已经被修改
        File file = new File(
                "biz/target/classes/com/github/avidyalalala/asm/Person.class");
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(classFile);
        stream.close();
    }
}
