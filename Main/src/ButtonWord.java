import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

class ButtonWord extends JButton {
    private Color hoverColor = Color.BLACK;

    public ButtonWord(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 24));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.BLACK);
        // Varsayılan margin'leri sıfırla ki hesaplamalar şaşmasın
        setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 1. ÇEMBER ÇİZİMİ (Hover durumunda)
        if (getModel().isRollover()) {
            g2.setColor(hoverColor);
            g2.setStroke(new BasicStroke(2));
            // Çemberi tam ortalamak için: (x, y, w, h)
            // Kenar payı (padding) bırakarak çiziyoruz (2px içeriden)
            g2.drawRect(2, 2, width - 4, height - 4);
        }

        // 2. METNİ (İŞARETİ) MANUEL OLARAK ORTALAYIP ÇİZME
        // super.paintComponent(g2) YERİNE bunu yapıyoruz.
        String text = getText();
        if (text != null) {
            g2.setFont(getFont());
            g2.setColor(getForeground());

            // Metnin boyutlarını al
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(text, g2);

            // X kordinatı: (Buton Genişliği - Metin Genişliği) / 2
            int x = (width - (int) r.getWidth()) / 2;

            // Y kordinatı: (Buton Yüksekliği - Metin Yüksekliği) / 2 + Ascent (Taban çizgisi düzeltmesi)
            int y = (height - (int) r.getHeight()) / 2 + fm.getAscent();

            g2.drawString(text, x, y);
        }

        g2.dispose();
    }
}