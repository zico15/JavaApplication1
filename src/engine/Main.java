/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zequi
 */
public class Main {
    
    
    public static void main(String[] args) throws Exception {
    new Main().d();
    }
    
    public void d(){
        
        try {
            Method setItemMethod = this.getClass().getMethod("setItem", String.class);
            setItemMethod.invoke(this.getClass(), "ddf");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setItem(String d){
    
        System.out.println("setItem: "+d);
    }
}
