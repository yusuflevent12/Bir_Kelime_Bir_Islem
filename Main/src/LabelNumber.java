import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LabelNumber extends JLabel {
    private int number;
    private int startX,startY;
    private Color topRengi;
    public LabelNumber(int number,int startX,int startY) {
        this.number = number;
        this.setText(String.valueOf(number));
        this.startX=startX;
        this.startY=startY;

        // --- 1. RASTGELE RENK AYARLAMA ---
        Random rand = new Random();
        // R, G, B değerleri 0-255 arasında üretilir
        // Çok açık renkler olmasın diye 200 ile sınırlayabilirsin istersen
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        // Parlak ve canlı renkler için HSB (Hue, Saturation, Brightness) kullanabiliriz
        this.topRengi = Color.getHSBColor(r, 0.7f, 0.9f);

        // --- 2. GÖRÜNÜM AYARLARI ---
        this.setBounds(startX, startY, 70, 70); // Kare alan (içine daire çizeceğiz)

        // Burası çok önemli!
        // Standart dikdörtgen boyamasını kapatıyoruz. Yoksa arkada gri kutu kalır.
        this.setOpaque(false);

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.WHITE); // Yazı rengi beyaz olsun ki görünsün
        this.setFont(new Font("Arial", Font.BOLD, 24));

    }


    @Override
    protected void paintComponent(Graphics g) {
        // Graphics nesnesini daha gelişmiş olan Graphics2D'ye çeviriyoruz
        Graphics2D g2 = (Graphics2D) g;

        // "Anti-Aliasing" açıyoruz (Kenarlar tırtıklı olmasın, pürüzsüz olsun diye)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Önce daireyi çiziyoruz
        g2.setColor(topRengi);
        // fillOval(x, y, genişlik, yükseklik) -> Daire çizer
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Sonra yazıyı üzerine yazması için "super"i çağırıyoruz.
        // Bunu yapmazsak sayılar görünmez, sadece renkli top görünür.
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
