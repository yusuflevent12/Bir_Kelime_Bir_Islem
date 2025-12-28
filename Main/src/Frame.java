import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Frame extends JFrame {
    // Renk Paleti (Modern & Dark Theme)
    private final Color BG_COLOR = Color.decode("#2C3E50");     // Koyu gri-mavi arka plan
    private final Color SECONDARY_BG = Color.decode("#34495E"); // Panel arka planları
    private final Color ACCENT_COLOR = Color.decode("#1ABC9C"); // Turkuaz vurgu rengi
    private final Color TEXT_COLOR = Color.decode("#ECF0F1");   // Açık gri metin

    private int mouseX, mouseY;
    private CardLayout cardLayout;
    private MouseAdapter adapterNumber, adapterWord;
    private JPanel mainPanel;
    private double seconds;

    // Main menu bileşenleri
    private JComboBox<Difficulty> zorluk;
    private JButton skors;
    private JButton startNumber;
    private JButton startWord;
    private JTextField username;
    private JTable scoreTable;

    // game menu bileşenleri
    private JPanel gameMenu;
    private JPanel centerPanel;
    private JLabel time;
    private JButton exit;
    private LabelNumber label1, label2, label3, label4, label5, hedef, num1, num2, result, activeCloneNumber;
    private Button btn, equal;
    private LabelNumber[] labels = new LabelNumber[5];
    private final int START_X = 900;
    private final int START_Y = 150;
    private final int BLANK = 20;
    private int createdLabels = 0;
    private ArrayList<LabelNumber> resultLabels = new ArrayList<>();

    // panels
    private JPanel gameOverPanel;
    private JButton goToMenu;
    private JButton again;
    private JPanel centerOfGameOver;
    private JTextArea area;

    private JPanel winnerPanel;
    private int score;
    private JLabel showScore;
    private JButton backToMenu;
    private JButton playAgain;

    // word game
    private JPanel wordGameMenu;
    private ArrayList<LabelWord> letters;
    private JLabel timeForWords;
    private JButton exitForWords;
    private JButton compelete;
    private ArrayList<LabelWord> word;
    private JPanel centerPanelOfWord;
    private JLabel lettersLabel;
    private JLabel wordLabel;
    private JPanel jokers;
    private ArrayList<LabelWord> jokerLabels;

    Frame() {
        setTitle("Bir Kelime Bir İşlem");
        setSize(1400, 850);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initAdapters();
        run();
        setVisible(true);
    }

    private void initAdapters() {
        adapterNumber = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object obj = e.getSource();
                if (obj instanceof LabelNumber) {
                    LabelNumber target = (LabelNumber) e.getSource();
                    activeCloneNumber = new LabelNumber(target.getNumber(), target.getStartX(), target.getStartY());
                    centerPanel.add(activeCloneNumber);
                    centerPanel.setComponentZOrder(activeCloneNumber, 0);
                    mouseY = e.getY();
                    mouseX = e.getX();
                    activeCloneNumber.setOpaque(false);
                    centerPanel.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (activeCloneNumber != null) {
                    int new_x = activeCloneNumber.getStartX() + e.getX() - mouseX;
                    int new_y = activeCloneNumber.getStartY() + e.getY() - mouseY;
                    activeCloneNumber.setLocation(new_x, new_y);
                    if (activeCloneNumber.getBounds().intersects(num1.getBounds())) {
                        num1.setNumber(activeCloneNumber.getNumber());
                        num1.setText(String.valueOf(activeCloneNumber.getNumber()));
                    } else if (activeCloneNumber.getBounds().intersects(num2.getBounds())) {
                        num2.setNumber(activeCloneNumber.getNumber());
                        num2.setText(String.valueOf(activeCloneNumber.getNumber()));
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (activeCloneNumber != null) {
                    activeCloneNumber.setVisible(false);
                    centerPanel.remove(activeCloneNumber);
                    activeCloneNumber = null;
                    centerPanel.repaint();
                }
            }
        };

        adapterWord = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                LabelWord target = (LabelWord) e.getSource();
                if (SwingUtilities.isLeftMouseButton(e) && target.isUp()) {
                    for (LabelWord label : word) {
                        if ((label.getLetter() == null || label.getLetter().trim().isEmpty()) && !target.getText().isEmpty()) {
                            label.setLetter(target.getText());
                            label.setText(target.getText());
                            ((LabelWord) e.getSource()).setText("");
                            break;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e) && !target.isUp()) {
                    for (LabelWord label : letters) {
                        if (label.getLetter().equalsIgnoreCase(target.getLetter()) && label.getText().isEmpty()) {
                            label.setText(target.getText());
                            target.setText("");
                            target.setLetter("");
                            break;
                        }
                    }
                }
            }
        };
    }
    public void run() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createMainMenu(), "MENU");
        mainPanel.add(createGameMenu(), "NUMBER");
        mainPanel.add(createGameOverPanel(), "GAMEOVER");
        mainPanel.add(createWinnerPanel(), "WINNER");
        mainPanel.add(createWordGamePanel(), "WORD");
        this.add(mainPanel);
        cardLayout.show(mainPanel, "MENU");
    }

    public JPanel createMainMenu() {
        JPanel mainMenu = new JPanel(new BorderLayout());
        mainMenu.setBackground(BG_COLOR);

        // Üst Başlık (Ortalanmış)
        JLabel title = new JLabel("BİR KELİME BİR İŞLEM", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 60));
        title.setForeground(ACCENT_COLOR);
        title.setBorder(new EmptyBorder(40, 0, 20, 0));
        mainMenu.add(title, BorderLayout.NORTH);

        // Orta Panel (Giriş ve Rekorlar)
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 100, 50, 100));

        // Sol Kısım: Giriş Alanı
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(SECONDARY_BG);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT_COLOR, 2, true),
                new EmptyBorder(30, 50, 30, 50)));

        JLabel userLabel = new JLabel("KULLANICI GİRİŞİ");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        username = new JTextField("Kullanıcı Adı");
        username.setMaximumSize(new Dimension(300, 45));
        username.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        username.setBackground(BG_COLOR);
        username.setForeground(Color.GRAY);
        username.setCaretColor(Color.WHITE);
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_COLOR));
        username.setHorizontalAlignment(JTextField.CENTER);

        // Placeholder Mantığı
        username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Kullanıcı Adı")) {
                    username.setText("");
                    username.setForeground(Color.WHITE);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().isEmpty()) {
                    username.setText("Kullanıcı Adı");
                    username.setForeground(Color.GRAY);
                }
            }
        });

        zorluk = new JComboBox<>(Difficulty.values());
        zorluk.setSelectedItem(Difficulty.NORMAL);
        zorluk.setMaximumSize(new Dimension(300, 40));
        zorluk.setFont(new Font("Segoe UI", Font.BOLD, 16));

        startNumber = new ModernButton("Sayı Oyunu");
        startWord = new ModernButton("Kelime Oyunu");
        skors = new ModernButton("Rekorları Yenile");

        leftPanel.add(userLabel);
        leftPanel.add(Box.createVerticalStrut(40));
        leftPanel.add(username);
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(new JLabel("Zorluk Seçimi:"));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(zorluk);
        leftPanel.add(Box.createVerticalStrut(50));
        leftPanel.add(startNumber);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(startWord);

        // Sağ Kısım: Rekorlar Tablosu
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(SECONDARY_BG);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT_COLOR, 2, true),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel tableTitle = new JLabel("EN YÜKSEK SKORLAR", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tableTitle.setForeground(ACCENT_COLOR);
        tableTitle.setBorder(new EmptyBorder(10, 0, 10, 0));

        scoreTable = new JTable();
        scoreTable.setRowHeight(35);
        scoreTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        scoreTable.setBackground(SECONDARY_BG);
        scoreTable.setForeground(Color.WHITE);
        scoreTable.setGridColor(ACCENT_COLOR);

        JTableHeader header = scoreTable.getTableHeader();
        header.setBackground(ACCENT_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.getViewport().setBackground(SECONDARY_BG);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        rightPanel.add(tableTitle, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(skors, BorderLayout.SOUTH);

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        mainMenu.add(contentPanel, BorderLayout.CENTER);

        return mainMenu;
    }

    public JPanel createGameMenu() {
        gameMenu = new JPanel(new BorderLayout());
        gameMenu.setBackground(BG_COLOR);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        topPanel.setBackground(SECONDARY_BG);

        exit = new ModernButton("Çıkış Yap");
        exit.setPreferredSize(new Dimension(150, 40));
        time = new JLabel("ZAMAN: " + seconds);
        time.setFont(new Font("Segoe UI", Font.BOLD, 24));
        time.setForeground(ACCENT_COLOR);

        topPanel.add(exit);
        topPanel.add(time);

        centerPanel = new JPanel(null);
        centerPanel.setBackground(BG_COLOR);

        JLabel hedef_writing = new JLabel("HEDEF SAYI");
        hedef_writing.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hedef_writing.setForeground(Color.WHITE);
        hedef_writing.setBounds(335, 50, 150, 30);

        hedef = new LabelNumber(0, 350, 100);
        label1 = new LabelNumber(0, 200, 250);
        label2 = new LabelNumber(0, 300, 250);
        label3 = new LabelNumber(0, 400, 250);
        label4 = new LabelNumber(0, 500, 250);
        label5 = new LabelNumber(0, 600, 250);

        num1 = new LabelNumber(0, 200, 450);
        num2 = new LabelNumber(0, 380, 450);
        result = new LabelNumber(0, 590, 450);

        btn = new Button("+");
        btn.setBounds(300, 460, 60, 60);

        equal = new Button("=");
        equal.setBounds(490, 460, 50, 50);

        labels[0] = label1; labels[1] = label2; labels[2] = label3; labels[3] = label4; labels[4] = label5;
        for (LabelNumber label : labels) {
            label.addMouseListener(adapterNumber);
            label.addMouseMotionListener(adapterNumber);
        }

        centerPanel.add(hedef_writing);
        centerPanel.add(hedef);
        centerPanel.add(label1); centerPanel.add(label2); centerPanel.add(label3);
        centerPanel.add(label4); centerPanel.add(label5);
        centerPanel.add(num1); centerPanel.add(num2); centerPanel.add(result);
        centerPanel.add(btn); centerPanel.add(equal);

        gameMenu.add(topPanel, BorderLayout.NORTH);
        gameMenu.add(centerPanel, BorderLayout.CENTER);

        return gameMenu;
    }

    public JPanel createWordGamePanel() {
        wordGameMenu = new JPanel(new BorderLayout());
        wordGameMenu.setBackground(BG_COLOR);

        letters = new ArrayList<>();
        word = new ArrayList<>();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        topPanel.setBackground(SECONDARY_BG);

        exitForWords = new ModernButton("Çıkış");
        timeForWords = new JLabel("Zaman: " + seconds);
        timeForWords.setFont(new Font("Segoe UI", Font.BOLD, 24));
        timeForWords.setForeground(ACCENT_COLOR);

        topPanel.add(exitForWords);
        topPanel.add(timeForWords);

        centerPanelOfWord = new JPanel(null);
        centerPanelOfWord.setBackground(BG_COLOR);

        lettersLabel = new JLabel("HARFLERİNİZ");
        lettersLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lettersLabel.setForeground(ACCENT_COLOR);

        wordLabel = new JLabel("OLUŞTURULAN KELİME");
        wordLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        wordLabel.setForeground(ACCENT_COLOR);

        jokers = new JPanel(new GridLayout(5, 6, 5, 5));
        jokers.setBackground(SECONDARY_BG);
        jokers.setBorder(BorderFactory.createTitledBorder(new LineBorder(ACCENT_COLOR), "JOKER PANELİ", 0, 0, null, Color.WHITE));
        jokers.setBounds(1000, 90, 300, 300);

        compelete = new ModernButton("KELİMEYİ TAMAMLA");
        compelete.setBounds(1000, 500, 300, 60);

        centerPanelOfWord.add(jokers);
        centerPanelOfWord.add(compelete);
        centerPanelOfWord.add(lettersLabel);
        centerPanelOfWord.add(wordLabel);

        wordGameMenu.add(topPanel, BorderLayout.NORTH);
        wordGameMenu.add(centerPanelOfWord, BorderLayout.CENTER);

        return wordGameMenu;
    }

    public JPanel createGameOverPanel() {
        gameOverPanel = new JPanel(new GridBagLayout());
        gameOverPanel.setBackground(BG_COLOR);

        centerOfGameOver = new JPanel();
        centerOfGameOver.setLayout(new BoxLayout(centerOfGameOver, BoxLayout.Y_AXIS));
        centerOfGameOver.setBackground(SECONDARY_BG);
        centerOfGameOver.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel gameOver = new JLabel("OYUN BİTTİ");
        gameOver.setFont(new Font("Segoe UI", Font.BOLD, 50));
        gameOver.setForeground(Color.RED);
        gameOver.setAlignmentX(Component.CENTER_ALIGNMENT);

        again = new ModernButton("YENİDEN DENE");
        goToMenu = new ModernButton("ANA MENÜYE DÖN");

        centerOfGameOver.add(gameOver);
        centerOfGameOver.add(Box.createVerticalStrut(40));
        centerOfGameOver.add(again);
        centerOfGameOver.add(Box.createVerticalStrut(20));
        centerOfGameOver.add(goToMenu);

        gameOverPanel.add(centerOfGameOver);
        return gameOverPanel;
    }

    public JPanel createWinnerPanel() {
        winnerPanel = new JPanel(new GridBagLayout());
        winnerPanel.setBackground(BG_COLOR);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(SECONDARY_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT_COLOR, 3),
                new EmptyBorder(50, 80, 50, 80)));

        JLabel winLabel = new JLabel("TEBRİKLER!");
        winLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        winLabel.setForeground(ACCENT_COLOR);
        winLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        showScore = new JLabel("SKORUNUZ: " + this.score);
        showScore.setFont(new Font("Segoe UI", Font.BOLD, 30));
        showScore.setForeground(Color.WHITE);
        showScore.setAlignmentX(Component.CENTER_ALIGNMENT);

        playAgain = new ModernButton("TEKRAR OYNA");
        backToMenu = new ModernButton("ANA MENÜ");

        card.add(winLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(showScore);
        card.add(Box.createVerticalStrut(50));
        card.add(playAgain);
        card.add(Box.createVerticalStrut(20));
        card.add(backToMenu);

        winnerPanel.add(card);
        return winnerPanel;
    }

    // Modern Button Tasarımı (Custom İç Sınıf)
    class ModernButton extends JButton {
        public ModernButton(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setForeground(Color.WHITE);
            setBackground(ACCENT_COLOR);
            setFocusPainted(false);
            setBorder(new EmptyBorder(10, 25, 10, 25));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) g2.setColor(ACCENT_COLOR.darker());
            else if (getModel().isRollover()) g2.setColor(ACCENT_COLOR.brighter());
            else g2.setColor(getBackground());

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void setContentAreaFilled(boolean b) { }
    }

    // Mevcut Diğer Metotların (labelCreator, showMenu vb.) Aynen Kalması Gerekir
    // Sadece UI Yerleşimlerini Etkileyen Küçük Güncellemeler Yapıldı.

    public void setJokerLabels(String[] liste) {
        jokerLabels = new ArrayList<>();
        jokers.removeAll();
        for (String s : liste) {
            LabelWord b = new LabelWord(s, 0, 0, true);
            b.addMouseMotionListener(adapterWord);
            b.addMouseListener(adapterWord);
            jokers.add(b);
            letters.add(b);
            jokerLabels.add(b);
        }
        jokers.revalidate();
        jokers.repaint();
    }

    public void labelAssigner(ArrayList<Integer> liste) {
        for (int i = 0; i < 5; i++) {
            labels[i].setNumber(liste.get(i));
            labels[i].setText(String.valueOf(liste.get(i)));
        }
    }

    public void labelWorsAssingerAndCreator(ArrayList<String> liste) {
        int base = 150;
        int blank = 20;
        centerPanelOfWord.removeAll();
        // Bileşenleri tekrar ekle (temizlikten sonra)
        centerPanelOfWord.add(jokers);
        centerPanelOfWord.add(compelete);
        centerPanelOfWord.add(lettersLabel);
        centerPanelOfWord.add(wordLabel);

        for (int i = 0; i < liste.size(); i++) {
            int x = (i >= 10) ? (55 + blank) * (i - 10) + base : (55 + blank) * i + base;
            int y = (i >= 10) ? 225 : 150;
            LabelWord label = new LabelWord(liste.get(i), x, y, true);
            centerPanelOfWord.add(label);
            letters.add(label);
            label.addMouseListener(adapterWord);
        }

        for (int i = 0; i < liste.size(); i++) {
            int x = (i >= 10) ? (55 + blank) * (i - 10) + base : (55 + blank) * i + base;
            int y = (i >= 10) ? 525 : 430;
            LabelWord label = new LabelWord(null, x, y, false);
            centerPanelOfWord.add(label);
            word.add(label);
            label.addMouseListener(adapterWord);
        }
    }

    public void labelCreator(LinkedHashSet<Integer> liste) {
        List<Integer> temp = new ArrayList<>(liste);
        for (int i = createdLabels; i < temp.size(); i++) {
            if (createdLabels >= 16) break;
            int rows = createdLabels / 4;
            int col = createdLabels % 4;
            int x = rows * (70 + BLANK) + START_X;
            int y = col * (70 + BLANK) + START_Y;

            LabelNumber new_one = new LabelNumber(temp.get(i), x, y);
            new_one.addMouseListener(adapterNumber);
            new_one.addMouseMotionListener(adapterNumber);
            centerPanel.add(new_one);
            resultLabels.add(new_one);
        }
        createdLabels = liste.size();
        centerPanel.repaint();
    }

    public void setScoreTable(List<Map.Entry<String, Integer>> liste) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"OYUNCU", "SKOR"}, 0);
        for (Map.Entry<String, Integer> entry : liste) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        scoreTable.setModel(model);
    }

    public void setGameOverNotification(String element) {
        area=new JTextArea("");
        area.setText(element);
        area.setEditable(false);
        area.setBackground(SECONDARY_BG);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        area.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerOfGameOver.add(Box.createVerticalStrut(20));
        centerOfGameOver.add(area);
    }

    // UI Akışı
    public void showMainMenu() { cardLayout.show(mainPanel, "MENU"); }
    public void showGameMenu() { cardLayout.show(mainPanel, "NUMBER"); }
    public void showGameOver() { cardLayout.show(mainPanel, "GAMEOVER"); }
    public void showWinnerMenu() { cardLayout.show(mainPanel, "WINNER"); }
    public void showWordMenu() { cardLayout.show(mainPanel, "WORD"); }

    public void deleteLabels() {
        for (LabelNumber l : resultLabels) centerPanel.remove(l);
        resultLabels.clear();
        createdLabels = 0;
        centerPanel.repaint();
    }

    public void deleteLabelsWord() {
        jokers.removeAll();
        for (LabelWord l : letters) centerPanelOfWord.remove(l);
        for (LabelWord l : word) centerPanelOfWord.remove(l);
        if (jokerLabels != null) jokerLabels.clear();
        letters.clear();
        word.clear();
        centerPanelOfWord.repaint();
    }

    public void setSomeLabels(int num) {
        int x = (num <= 10) ? (num * 55) / 2 + (num - 1) * 10 + 150 : (10 * 55) / 2 + (10 - 1) * 10 + 150;
        this.wordLabel.setBounds(x - 50, 380, 250, 40);
        this.lettersLabel.setBounds(x - 20, 100, 200, 40);
    }
    public void setButtons(char c) {
        getBtn().setText(String.valueOf(c));
    }

    // Getter & Setter (Aynı bırakıldı)
    public JComboBox<Difficulty> getZorluk() { return zorluk; }
    public JLabel getTime() { return time; }
    public ArrayList<LabelWord> getWord() { return word; }
    public JLabel getTimeForWords() { return timeForWords; }
    public JButton getStartWord() { return startWord; }
    public JButton getStartNumber() { return startNumber; }
    public JButton getExitForWords() { return exitForWords; }
    public JButton getExit() { return exit; }
    public double getSeconds() { return seconds; }
    public void setSeconds(double seconds) { this.seconds = seconds; this.time.setText("ZAMAN: " + seconds); }
    public JButton getGoToMenu() { return goToMenu; }
    public JButton getAgain() { return again; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public JLabel getShowScore() { return showScore; }
    public JButton getPlayAgain() { return playAgain; }
    public JButton getBackToMenu() { return backToMenu; }
    public LabelNumber getResult() { return result; }
    public LabelNumber getNum2() { return num2; }
    public LabelNumber getNum1() { return num1; }
    public LabelNumber getHedef() { return hedef; }
    public Button getEqual() { return equal; }
    public Button getBtn() { return btn; }
    public ArrayList<LabelWord> getJokerLabels() { return jokerLabels; }
    public JButton getCompelete() { return compelete; }
    public JTextField getUsername() { return username; }
    public JButton getSkors() {
        return skors;
    }

    public JTextArea getArea() {
        return area;
    }
}