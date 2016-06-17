/**
 * 
 */
package com.test;

import javax.swing.border.EtchedBorder;

/** 
* @ClassName: Test 
* @Description: 
* @author LUCKY
* @date 2016年6月14日 下午7:04:52 
*  
*/
public class Test {

    public static void main(String[] args) {
        System.out.println(getValue());
    }

    public static int getValue() {
        try {
            int a = 10 / 0;
            return a;
        } catch (ArithmeticException e) {
            System.out.println(0);
            return 0;
        } catch (Exception e) {
            System.out.println(1);
            return 1;
        } finally {
            System.out.println(2);
            return 2;
        }
    }
}
