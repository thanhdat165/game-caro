
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class CaRo extends  JPanel implements ActionListener{
    private JFrame frame;
    private JButton buttonNewGame;
    private JButton buttonSound;
    private JButton buttonExit;
    private ButtonHandler buttonHandler ;
    public int n = 20, m = 20;
    public int count = 0;
    public JButton button[][] = new JButton[n][m];
    public JPanel panelCENTER ;
    public JPanel panelSOUTH ;
    public JPanel panelWEST;
    public JPanel panelEAST;
    public JPanel panelNORTH;
    private Clip clip;
    private String musicLocation ="music\\music.wav";
    private String clickEffect = "music\\buttonClick.wav";
    private String soundOnOff = "off";


    public CaRo(){
        frame = new JFrame("Trò chơi Caro");
        frame.setLayout(new BorderLayout());
        
        panelCENTER = new JPanel();
        panelCENTER.setLayout(new GridLayout(n, m));
        panelCENTER.setBackground(Color.darkGray);

        panelSOUTH = new JPanel();
        panelSOUTH.setLayout(new FlowLayout());
        panelSOUTH.setBackground(Color.darkGray);
        
        panelWEST = new JPanel();
        panelWEST.setLayout(new FlowLayout());
        panelWEST.setBackground(Color.darkGray);
         
        panelEAST = new JPanel();
        panelEAST.setLayout(new FlowLayout());
        panelEAST.setBackground(Color.darkGray); 
        
        panelNORTH = new JPanel();
        panelNORTH.setLayout(new FlowLayout());
        panelNORTH.setBackground(Color.darkGray);
        
        buttonNewGame = new JButton("New Game");
        buttonNewGame.setFocusPainted(false);
        
        buttonExit = new JButton("Exit");
        buttonExit.setFocusPainted(false);
        
        buttonSound =new JButton("Sound");
        buttonSound.setFocusPainted(false);

        buttonNewGame.setBackground(Color.white);
        buttonExit.setBackground(Color.white);
        buttonSound.setBackground(Color.white);
        
        buttonHandler = new ButtonHandler();
        
        
        panelSOUTH.add(buttonSound);
        panelSOUTH.add(buttonNewGame);
        panelSOUTH.add(buttonExit);

        
        buttonNewGame.addActionListener(this);
        
        buttonSound.addActionListener(buttonHandler);
        
        buttonExit.addActionListener(this);
        
        for(int i=0; i<n;i++){
            for(int j=0; j<m; j++){
               button[i][j] = new JButton();
               button[i][j].setFocusPainted(false);
               button[i][j].setBackground(Color.lightGray);
               button[i][j].setBorder(new LineBorder(Color.BLACK));
               button[i][j].addActionListener(this);
               panelCENTER.add(button[i][j]);
            }
        }
        
        frame.add(panelCENTER, BorderLayout.CENTER);
        frame.add(panelSOUTH, BorderLayout.SOUTH);
        frame.add(panelWEST, BorderLayout.WEST);
        frame.add(panelEAST, BorderLayout.EAST);
        frame.add(panelNORTH, BorderLayout.NORTH);
      

         
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);        
}
    
    
    //Chức năng phát nhạc
    public void setFile(String musicLocation)
    {
        try
        {
            File file = new File(musicLocation);
            AudioInputStream audios = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audios);
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
}

    
    public void playMusic()
    {
        clip.start();
    }


    public void loopMusic()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void stopMusic()
    {
        clip.stop();
    }


    public class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(soundOnOff.equals("off") )
            {
                 setFile(musicLocation);
                 playMusic();
                 loopMusic();
                 soundOnOff = "on";
                 buttonSound.setText("Sound ON");
             }
            else if(soundOnOff.equals("on") )
             {
                 stopMusic();
                 soundOnOff = "off";
                 buttonSound.setText("Sound OFF");
             }
        }

    }

    //bắt sự kiện các nút chức năng
    public void actionPerformed(ActionEvent e)
    {       
        if(e.getSource() == buttonExit)
        {
            System.exit(0);
        }

        for(int i=0; i<n;i++)
        {
            for(int j=0; j<m; j++)
            {
                button[i][j].setFont(new Font("Arial", Font.BOLD, 25));

                if(e.getSource() == buttonNewGame)
                    {
                        button[i][j].setText("");
                        count = 0;
                    }                  
                if(e.getSource() == button[i][j] &&  button[i][j].getText() !="X" && button[i][j].getText() != "O")
                {

                    if(count % 2 ==0)
                    {

                       button[i][j].setText("X");
                       button[i][j].setForeground(Color.RED);
                       buttonClickEffect(clickEffect );
                       count++;


                       if(checkWin(i, j, button[i][j].getText() ))
                        {
                            JOptionPane.showMessageDialog(null,"X WIN");
                        }
                    }
                    else
                    {
                        button[i][j].setText("O");
                        button[i][j].setForeground(Color.black);
                        buttonClickEffect(clickEffect);
                        count++;

                        if(checkWin(i, j, button[i][j].getText() ))
                        {
                            JOptionPane.showMessageDialog(null,"O WIN");
                        }
                    }
                }
            }
        }
    }

        
    //Hiệu ứng đánh cờ
    public static void buttonClickEffect(String clickEffect ){
         try{

           File musicpath = new File(clickEffect);
           AudioInputStream audios = AudioSystem.getAudioInputStream(musicpath);
           Clip clip = AudioSystem.getClip();
           clip.open(audios);
           clip.start();

        }
      catch(Exception e)
        {
           e.printStackTrace();
        }
    }
    
    
    //Kiểm tra thắng
    public boolean checkWin(int i, int j, String Name)
    {
        //hàng dọc
        int count = 0;
        for(int k= -4; k<=4; k++)  
        {
            if(i + k >= 0 && i + k <n)
            {
                if(button[i + k][j].getText() == Name )
                {
                    count++;
                }
            else if(count < 5)
                {
                    count = 0;
                }
            }
        }

        if(count == 5)
            return true;
        else 
            count = 0;

        
        //hàng ngang
        for(int k =-4;k<=4; k++)
        {
            if(j + k >=0 && j + k < m)
            {
                if(button[i][j + k].getText() == Name){
                    count++;  
                }
                else if(count < 5){
                    count = 0;
                }
            }
        }
        if(count == 5)
        
            return true;
        else
            count = 0;

        
        //hàng chéo
        for(int k = -4; k <=4; k++)
        {
            if(i+k >= 0 && j+k >=0 && i+k < n && j+k < m) 
            {
                  if(button[i+k][j+k].getText() == Name)
                  {
                      count++;
                  }
                  else if(count < 5)
                  {
                      count = 0;
                  }
            }
       }
            if(count == 5)
                return true;
            else 
                count = 0;

        for(int k = -4; k<= 4 ; k++)
        {
            if(i-k >= 0 && j+k >=0 && i-k < n && j+k < m)
            {
                if(button[i-k][j+k].getText() == Name)
                {
                    count++;
                    
                }else if(count < 5)
                {
                    count = 0;
                }
            }
            if(count == 5)
            {
                return true;
            }
        }

          return false;
   }

    
    public static void main(String[] args) {

        new CaRo();
    }

}


