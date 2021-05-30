/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author zequi
 */
public class ButtonController extends JButton{
    
    
    public Color corPrimaria;
    public Color corSegudaria;
    public String nome;
    
    
    public ButtonController(int i1,int i2,int i3){
    
       corPrimaria =  new Color(i1,i2,i3);
       corSegudaria = new Color(51,255,51);
        addMouseListener(new MouseListener() {
			
                        @Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
                        @Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
                        @Override
			public void mouseExited(MouseEvent e) {
				if(corPrimaria!=null && isEnabled())  { setBackground(corPrimaria);}else { setBackground(Color.GRAY);}
			}
			
                        @Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().requestFocus();
                               if(corSegudaria!=null && isEnabled()) {   setBackground(corSegudaria);}else { setBackground(Color.GRAY);}
				
			}
			
                        @Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
    
    
    
    }
    
 
    
}
