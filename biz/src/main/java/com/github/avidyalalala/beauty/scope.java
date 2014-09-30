package com.github.avidyalalala.beauty;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-9-30
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public class scope {

    public static void smileInDifferentBracket(String theVoice){
        {
            {
                String test = "lala";
                System.out.println(test);
            }
            String test = "hehe";
            System.out.println(test);
        }
        String test = theVoice;
        System.out.println(test);
    }

    public static void main(String[] args) {
        smileInDifferentBracket("haha");
    }
}
