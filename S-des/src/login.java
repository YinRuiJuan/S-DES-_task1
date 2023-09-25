import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame{
    JButton button1,button2;
    Font font = new Font("宋体", Font.BOLD, 50);
    Box boxV1;
    button1Listener b1Listener;
    button2Listener b2Listener;

    public login(){
        setLayout(new java.awt.FlowLayout());
        setSize(800,600);
        setLocationRelativeTo(null);
        setTitle("初始界面");
        init();
        b1Listener=new button1Listener();
        b2Listener=new button2Listener();
        button1.addActionListener(b1Listener);
        button2.addActionListener(b2Listener);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void init(){
        boxV1=Box.createVerticalBox();
        JLabel title=new JLabel("选择加解密类型");
        title.setFont(font);

        button1=new JButton(" 二进制加密 ");
        button2=new JButton(" ASCII加密  ");

        button1.setFont(font);
        button2.setFont(font);

        boxV1.add(Box.createVerticalStrut(100));
        boxV1.add(title);
        boxV1.add(Box.createVerticalStrut(80));
        boxV1.add(button1);
        boxV1.add(Box.createVerticalStrut(80));
        boxV1.add(button2);

        add(boxV1);
    }
    class button1Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
            SDES_GUI gui=new SDES_GUI();
            gui.setVisible(true);
        }
    }
    class button2Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
            SDES_ASCII_GUI gui=new SDES_ASCII_GUI();
            gui.setVisible(true);
        }
    }
     public static void main(String[] args) {
       login log=new login();
    }
}
