import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LabelWord extends JLabel{
    private String  letter;
    private int startX,startY;
    private Color kutuRengi;
    private boolean up;

    public LabelWord(String letter,int startX,int startY,boolean up){
        this.up=up;
        this.letter=letter;
        setText(this.letter);
        this.startX=startX;
        this.startY=startY;
        Random rand=new Random();
        float r=rand.nextFloat();
        this.kutuRengi=Color.getHSBColor(r,0.7f,0.9f);
        this.setBounds(startX,startY,55,55);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.white);
        this.setFont(new Font("Arial",Font.BOLD,24));

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(kutuRengi);
        g2.fillRect(0,0,getWidth(),getHeight());
        super.paintComponent(g);
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Color getKutuRengi() {
        return kutuRengi;
    }

    public void setKutuRengi(Color kutuRengi) {
        this.kutuRengi = kutuRengi;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
}
