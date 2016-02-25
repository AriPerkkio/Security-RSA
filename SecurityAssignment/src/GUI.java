import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame{

    public GUI(){
         super("RSA Keys");

         //you can set the content pane of the frame 
         //to your custom class.

         setContentPane(new DrawPane());

         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         setSize(400, 400);

         setVisible(true); 
    }

     //create a component that you can actually draw on.
     class DrawPane extends JPanel{
       public void paintComponent(Graphics g){
         //draw on g here e.g.
          g.fillRect(0,0,400,400);
        }
    }

    public static void main(String args[]){
           new GUI();
           new GUI();
    }

 }