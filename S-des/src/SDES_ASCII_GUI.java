import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class SDES_ASCII_GUI extends JFrame{
    Box boxH1,boxH2;//最底层行
    Box boxV1,boxV2,boxV3;//上层列
    JButton button1,button2,button3;//三个按钮
    JTextField text1,text2;
    Font font = new Font("宋体", Font.BOLD, 50);
    Font font2 = new Font("楷体", Font.BOLD, 60);
    JLabel label1,label2,label3,title;
    ImageIcon img;
    button1Listener b1Listener;
    button2Listener b2Listener;
    button3Listener b3Listener;
    JTextField outputField;
    

    public SDES_ASCII_GUI() {
        setLayout(new java.awt.FlowLayout());
        setSize(800,600);
        setLocationRelativeTo(null);
        setTitle("S-DES 二进制加解密机");
      
        init();
       
        b1Listener=new button1Listener();
        b2Listener=new button2Listener();
        b3Listener=new button3Listener();
        button1.addActionListener(b1Listener);
        button2.addActionListener(b2Listener);
        button3.addActionListener(b3Listener);

        setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void init(){
        boxH1=Box.createHorizontalBox();
        boxH2=Box.createHorizontalBox();
        boxV1=Box.createVerticalBox();
        boxV2=Box.createVerticalBox();
        boxV3=Box.createVerticalBox();
        
        text1=new JTextField(15);
        text1.setFont(font);
        text2=new JTextField(15);
        text2.setFont(font);
        outputField = new JTextField(15);
        outputField.setEditable(false);
        outputField.setBorder(null);
        outputField.setFont(font);
        outputField.setPreferredSize(new Dimension(15, 8));

        text1.setPreferredSize(new Dimension(15,8));
        text2.setPreferredSize(new Dimension(15,8));
        
        label1=new JLabel("输入");
        label2=new JLabel("密钥");
        label3=new JLabel("结果");
        title=new JLabel("欢迎使用ASCII加密机");
        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        title.setFont(font2);

        button1= new JButton("加密");
        button1.setFont(font);
        button2= new JButton("解密");
        button2.setFont(font);
        button3= new JButton("返回");
        button3.setFont(font);

        boxV1.add(label1);
        boxV1.add(Box.createVerticalStrut(40));
        boxV1.add(label2);
        boxV1.add(Box.createVerticalStrut(40));
        boxV1.add(label3);

        boxV2.add(text1);
        boxV2.add(Box.createVerticalStrut(40));
        boxV2.add(text2);
        boxV2.add(Box.createVerticalStrut(40));
        boxV2.add(outputField);
        

        boxH1.add(boxV1);
        boxH1.add(Box.createHorizontalStrut(100));
        boxH1.add(boxV2);

        boxH2.add(button1);
        boxH2.add(Box.createHorizontalStrut(100));
        boxH2.add(button2);
        boxH2.add(Box.createHorizontalStrut(100));
        boxH2.add(button3);

       
        boxV3.add(boxH1);
        boxV3.add(Box.createVerticalStrut(30));
        boxV3.add(boxH2);

        add(title);
        add(Box.createVerticalStrut(150));
        add(boxV3);
    }


    class button1Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String plaintext = text1.getText();
            String key = text2.getText();
            //System.out.println("plaintext:"+plaintext+"key"+key);
            String ciphertext = SDES_ASCII.encrypt1(plaintext, key);
            //String ciphertext = SDES.encrypt(plaintext, key);
            //String ciphertext = plaintext+"   "+key;
            outputField.setText(ciphertext);;
         }
    }
    class button2Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String ciphertext = text1.getText();
            String key = text2.getText();
            //System.out.println("plaintext:"+ciphertext+"key"+key);
            String plaintext = SDES_ASCII.decrypt1(ciphertext, key);
            //String plaintext= ciphertext +"  "+key;
            outputField.setText(plaintext);
        }
    }
    class button3Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
            login log = new login();
            log.setVisible(true);
        }
    }

}