package com.github.avidyalalala.asm.compile;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-10-16
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class Person
{
    private String name;

    public void sayName()
    {
        System.out.println(this.name);
    }

    public static void main(String[] args) {
        new Person().sayName();
    }
}