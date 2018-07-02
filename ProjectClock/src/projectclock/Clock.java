/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectclock;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;

public class Clock extends JFrame{
      //All the necessary fields
      double hour,minute,second;
      Calendar time = new GregorianCalendar();  
      int number = 12;
      int GlobalRadius, angle;
      double count,Mincount;
      double SecAngle, MinuteAngle,HourAngle;
      String timezone;
    //Sleep method used in the drawSecondHand method
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
        }
    }
    //The most important method in the class. Deals with drawing and animating the clock to the screen
    public void drawClock(Graphics g, int x, int y, int radius, String Timezone){
        //Lines 31-54 deal with drawing the clock itself
        g.setColor(Color.BLACK);
        g.fillOval(x-10, y-10, radius+20, radius+20);
        g.setColor(Color.WHITE);
        g.fillOval(x, y, radius, radius);
        g.fillOval((int) ((x + radius) / 2) + 48, (int) ((y + radius) / 2) + 48, 15, 15);
        GlobalRadius=radius;
        int angle = 90;
        int TickLength = 8;
        int x1 = (int) ((x + radius) / 2 + 50);
        int y1 = (int) ((y + radius) / 2 + 50);
        for (int i = 0; i < 60; i++) {
            double AngleUsed = angle * Math.PI / 180;
            int x2 = (int) (x1 + (radius * 0.5 * Math.cos(AngleUsed)));
            int y2 = (int) (y1 - (radius * 0.5 * Math.sin(AngleUsed)));
            if (i % 5 == 0) {
                drawTickwithNums(g, x2, y2, angle, TickLength + 8);
            } else {
                drawTick(g, x2, y2, angle, TickLength);
            }
            angle=angle+6;
        }
        number = 12;
        
        //Lines 56-111 deal with drawing and animating the hands of the clock
        count= time.get(Calendar.SECOND);
        SecAngle=time.get(Calendar.SECOND)*6 -90;
        //The value is multiplied by 6 because 360 degrees/60 ticks is 6
        //90 is subtracted to ensure the clock originally starts at the 12
        if(Timezone.equals("EST")){
             MinuteAngle = time.get(Calendar.MINUTE)*6-90;
             Mincount= time.get(Calendar.MINUTE);
             HourAngle= time.get(Calendar.HOUR)*30-90+Mincount/2;
             //HourAngle is multiplied by 30 because there are 5 ticks between each hour so 6*5= 30
            //Mincount/2 allows the hour hand to come to a realistic location between hours depending on
            //how many minutes have passed.
        }
        else if(Timezone.equals("PST")){
             MinuteAngle = time.get(Calendar.MINUTE)*6-90;
              Mincount= time.get(Calendar.MINUTE);
             HourAngle= (time.get(Calendar.HOUR)-3)*30-90+Mincount/2;
            
        }
        else if(Timezone.equals("EE")){
             MinuteAngle = time.get(Calendar.MINUTE)*6-90;
             Mincount= time.get(Calendar.MINUTE);
             HourAngle= (time.get(Calendar.HOUR)+6)*30-90+Mincount/2;
            
        }
        else if(Timezone.equals("IST")){
             MinuteAngle = (time.get(Calendar.MINUTE)+30)*6-90;
             Mincount= time.get(Calendar.MINUTE)+30;
             HourAngle= (time.get(Calendar.HOUR)-3)*30-90+Mincount/2;
            
        }
        //Displays the time zone on the screen
        ZoneView(g,600,100);
        //Continuously animates the hands of the clock in real time
        while(true){
            double AngleUsed= SecAngle * Math.PI / 180;
            double MinAngleUsed= MinuteAngle*Math.PI/180;
            double HourAngleUsed= HourAngle*Math.PI/180;
            drawOtherHand(g,x1+5,y1+5,MinAngleUsed*-1,210);
            drawOtherHand(g,x1+5,y1+5,HourAngleUsed*-1,120);
            g.fillOval((int) ((x + radius) / 2) + 48, (int) ((y + radius) / 2) + 48, 15, 15);
            drawSecondHand(g,x1+5,y1+5,AngleUsed*-1,190);
            SecAngle=SecAngle+6;
            count++;
            //Every 60 seconds, the minute hand moves 6 degrees
            if(count%60==0){
                clearOtherHand(g,x1+5,y1+5,MinAngleUsed*-1,210);
                MinuteAngle=MinuteAngle+6;
                MinAngleUsed= MinuteAngle*Math.PI/180;
                drawOtherHand(g,x1+5,y1+5,MinAngleUsed*-1,210);
                Mincount++;
                
            }
            //Every 10 minutes, the HourAngle increases by a small amount
            if (Mincount%10==0){
               clearOtherHand(g,x1+5,y1+5,HourAngleUsed*-1,120);
               HourAngle=HourAngle+0.083;
               HourAngleUsed= HourAngle*Math.PI/180;
               drawOtherHand(g,x1+5,y1+5,HourAngleUsed*-1,120);
               Mincount=0;
            }
        }
    }
    //Draws the ticks around the perimeter of the clock
    public void drawTick(Graphics g, int x, int y, double angle, int length) {
        double AngleUsed = angle * Math.PI / 180;
        int x2 = (int) (x - (length * Math.cos(AngleUsed)));
        int y2 = (int) (y + (int) (length * Math.sin(AngleUsed)));
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x2, y2);
    }
    //Draws the numbers on the clock and appropriately sized ticks to go along with them
    public void drawTickwithNums(Graphics g, int x, int y, double angle, int length) {
        double AngleUsed = angle * Math.PI / 180;
        int x2 = (int) (x - (length * Math.cos(AngleUsed)));
        int y2 = (int) (y + (int) (length * Math.sin(AngleUsed)));
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x2, y2);
        int x3 = (int) (x2 - (length * Math.cos(AngleUsed)));
        int y3 = (int) (y2 + (int) (length * Math.sin(AngleUsed)));
        g.drawString(Integer.toString(number), x3, y3);
        number--;
    }
    //Draws and animates the second hand
    public void drawSecondHand(Graphics g, int x, int y, double angle, int length) {
        int x2 = (int) (x + length * Math.cos(angle));
        int y2 = (int) (y - length * Math.sin(angle));
        g.setColor(Color.RED);
        g.drawLine(x, y, x2, y2);
        sleep(1000);
        g.setColor(Color.WHITE);
        g.drawLine(x,y,x2,y2);
    }  
    //Draws the minute and hour hand
    public void drawOtherHand(Graphics g, int x, int y, double angle, int length) {
        int x2 = (int) (x + length * Math.cos(angle));
        int y2 = (int) (y - length * Math.sin(angle));
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x2, y2);
       
    }  
    //Clears the minute and hour hand
    public void clearOtherHand(Graphics g, int x, int y, double angle, int length){
        int x2 = (int) (x + length * Math.cos(angle));
        int y2 = (int) (y - length * Math.sin(angle));
        g.setColor(Color.WHITE);
        g.drawLine(x,y,x2,y2);
    }
    
    //Displays the time zone on the screen
     public void ZoneView(Graphics g, int ZoneX, int ZoneY){
         String Zone="";
         if(timezone.equals("EST")){
            Zone = "Eastern Standard Time";
            
        }
        else if(timezone.equals("PST")){
             Zone = "Pacific Standard Time";
            
        }
        else if(timezone.equals("EE")){
             Zone = "Eastern European Time";
            
        }
        else if(timezone.equals("IST")){
             Zone = "Indian Standard Time";
            
        }
        g.drawString(Zone, ZoneX, ZoneY);
         
     }

    
}
