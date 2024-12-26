
package notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JFrame implements ActionListener{
    JButton b1;
    About(){
        setBounds(600, 200, 700,600);
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("notepad/icons/windows.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 80, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(150, 40, 400, 80);
        add(l1);
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("notepad/icons/notepad.png"));
        Image i5 = i4.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(50, 180, 70, 70);
        add(l2);
        
        
        JLabel l3 = new JLabel("<html>This is Creted by the BONTHU JANARDANRAO<br><br><br><br>Welcome to Notepad! This simple yet powerful text editor is designed to help you create and edit text files with ease.  <br>Notepad offers essential features like creating new files, opening existing ones, saving your work, and printing documents.<br>Additionally, you can text to speech,copy, paste, cut, and select all text effortlessly.<br> Whether you're jotting down quick notes or working on longer documents, Notepad is here to assist you. </html>");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        l3.setBounds(150, 130, 500, 300);
        add(l3);
        
        b1 = new JButton("OK");
        b1.setBounds(580, 500, 80, 25);
        b1.addActionListener(this);
        add(b1);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
    
    public static void main(String[] args){
        new About().setVisible(true);
    }
}
