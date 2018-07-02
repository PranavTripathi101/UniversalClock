/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectclock;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;
import javax.swing.JFrame;


public class ProjectClock extends Clock{
  //Paint methods, calls the drawClock method  
 public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,800);
        drawClock(g,100,100,500,timezone);
    }
    
    
    
    
    
    
    
    
    
    public static void main(String[] args){
        ProjectClock screen = new ProjectClock();
        //Asks the user to enter a time zone
        System.out.println("Which Timezone would you like to use?");
        System.out.println("Enter 'EST' for Eastern Standard Time");
        System.out.println("Enter 'PST' for Pacific Standard Time");
        System.out.println("Enter ' EE' for Eastern European Time");
        System.out.println("Enter 'IST' for Indian Standard Time");
        Scanner s = new Scanner(System.in);
        screen.timezone= s.next();
        //Sets up the screen
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setBackground(Color.black);
        screen.setSize( 800, 800 );
        screen.setVisible(true);
            
        }
        
        
    
   
}
