package com.github.avidyalalala.asm;

import org.objectweb.asm.*;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-26
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class GeneralClassAdapter extends ClassAdapter {

    public GeneralClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
                exceptions);
        // 当是sayName方法是做对应的修改
        if (name.equals("sayName")) {
            MethodVisitor newMv = new SayNameMethodAdapter(mv);
            return newMv;
        } else {
            return mv;
        }
    }

    // 定义一个自己的方法访问类
    class SayNameMethodAdapter extends MethodAdapter {
        public SayNameMethodAdapter(MethodVisitor mv) {
            super(mv);
        }

        // 在源方法前去修改方法内容,这部分的修改将加载源方法的字节码之前
        @Override
        public void visitCode() {
            // 记载隐含的this对象，这是每个JAVA方法都有的
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            // 从常量池中加载“lalala”字符到栈顶
            mv.visitLdcInsn("lalala");
            // 将栈顶的"lalala"赋值给name属性
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                    Type.getInternalName(Person.class), "name",
                    Type.getDescriptor(String.class));
        }

    }

}

