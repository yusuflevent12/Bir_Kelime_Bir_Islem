
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Frame extends JFrame {
    private int mouseX,mouseY;
    private CardLayout cardLayout;
    private MouseAdapter adapterNumber,adapterWord;
    private JPanel mainPanel;
    private double seconds;

    // Main menu bileşenleri
    private JComboBox<Difficulty> zorluk;
    private JButton skors;
    private JButton startNumber;
    private JButton startWord;
    private JTextField username;
    private JTable scoreTable;
    //game menu bileşenleri
    private JPanel gameMenu;
    private JPanel centerPanel;
    private JLabel time;
    private JButton exit;
    private  LabelNumber label1,label2,label3,label4,label5,hedef,num1,num2,result,activeCloneNumber;
    private Button btn,equal;
    private  LabelNumber [] labels=new LabelNumber[5];
    private int START_X=900;
    private int START_Y=150;
    private int BLANK=20;
    private double OLCEK=1.0;
    private int createdLabels=0;
    private ArrayList<LabelNumber> resultLabels=new ArrayList<>();



    //game over panelş
    private JPanel gameOverPanel;
    private JButton goToMenu;
    private JButton again;
    private JPanel centerOfGameOver;

    //winner menu
    private JPanel winnerPanel;
    private int  score;
    private JLabel showScore;
    private JButton backToMenu;
    private JButton playAgain;

    //word game menu bileşenleri
    private JPanel wordGameMenu;
    private JTextArea meaning;//bu olasılık dahilinde ama iptalde olabilir
    private ArrayList<LabelWord> letters;//kullanıcıya verilecek kelimeler
    private JLabel timeForWords;//word kısmı için timer
    private JButton exitForWords;//word kismi için exit
    private JButton compelete;//kullanıcı işini bitirince kullanıcak
    private ArrayList<LabelWord> word;//kullanıcının tahmini her harfi tek tek tutucaz en son birleştircez
    private JPanel centerPanelOfWord;
    private LabelWord activeCloneWord;
    private JLabel lettersLabel;
    private JLabel wordLabel;
    private JPanel jokers;
    private ArrayList<LabelWord> jokerLabels;

    Frame() {

        setTitle("Oyun Penceresi");
        setSize(1400, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        adapterNumber=new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object obj=e.getSource();
                if(obj instanceof LabelNumber) {
                    LabelNumber target = (LabelNumber) e.getSource();
                    activeCloneNumber=new LabelNumber(target.getNumber(),target.getStartX(),target.getStartY());
                    centerPanel.add(activeCloneNumber);
                    centerPanel.setComponentZOrder(activeCloneNumber, 0);
                    mouseY = e.getY();
                    mouseX = e.getX();
                    Color originalColor = target.getBackground();
                    Color paleColor = new Color(
                            originalColor.getRed(),
                            originalColor.getGreen(),
                            originalColor.getBlue(),
                            150
                    );


                    activeCloneNumber.setBackground(paleColor);
                    activeCloneNumber.setOpaque(false);
                    activeCloneNumber.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    centerPanel.repaint();
                }


            }
            @Override
            public void mouseDragged(MouseEvent e){
                int new_x = activeCloneNumber.getStartX() + e.getX() - mouseX;
                int new_y = activeCloneNumber.getStartY() + e.getY() - mouseY;
                activeCloneNumber.setLocation(new_x,new_y);
                if(activeCloneNumber.getBounds().intersects(num1.getBounds())){
                    num1.setNumber(activeCloneNumber.getNumber());
                    num1.setText(String.valueOf(activeCloneNumber.getNumber()));
                } else if (activeCloneNumber.getBounds().intersects(num2.getBounds())){
                    num2.setNumber(activeCloneNumber.getNumber());
                    num2.setText(String.valueOf(activeCloneNumber.getNumber()));
                }

            }
            @Override
            public void mouseReleased(MouseEvent e){
                if(activeCloneNumber!=null){
                    activeCloneNumber.setVisible(false);
                    centerPanel.remove(activeCloneNumber);
                    activeCloneNumber=null;
                    centerPanel.repaint();

                }
            }
        };
        adapterWord=new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                LabelWord target=(LabelWord) e.getSource();
                if(SwingUtilities.isLeftMouseButton(e)&&target.isUp()){
                    for(LabelWord label:word){
                        if((label.getLetter()==null||label.getLetter().trim().isEmpty())&&!target.getText().isEmpty()){
                            label.setLetter(target.getText());
                            label.setText(target.getText());
                            ((LabelWord) e.getSource()).setText("");
                            break;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)&&!target.isUp()) {
                    for(LabelWord label:letters){
                        if(label.getLetter().equalsIgnoreCase(target.getLetter())&&label.getText().isEmpty()){
                            label.setText(target.getText());
                            target.setText("");
                            target.setLetter("");
                            break;
                        }
                    }
                }
            }
        };
        run();
        setVisible(true);
    }

    public void run(){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createMainMenu(), "MENU");
        mainPanel.add(createGameMenu(), "NUMBER");
        mainPanel.add(createGameOverPanel(),"GAMEOVER");
        mainPanel.add(createWinnerPanel(),"WINNER");
        mainPanel.add(createWordGamePanel(),"WORD");
        this.add(mainPanel,BorderLayout.CENTER);
        cardLayout.show(mainPanel,"MENU");
    }

    public JPanel createMainMenu(){
        JPanel mainMenu = new JPanel();
        JPanel right=new JPanel();
        JPanel left=new JPanel();

        mainMenu.setLayout(new FlowLayout());

        JLabel title=new JLabel("MENÜ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 50));

        right.setLayout(new BorderLayout());
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(100,200,100,200));
        zorluk=new JComboBox<>(Difficulty.values());
        zorluk.setSelectedItem(Difficulty.NORMAL);
        zorluk.setMaximumSize(new Dimension(200, 40));//combobox fazla büyümesin diye
        skors=new JButton("Rekorlar");
        startNumber=new JButton("Sayı Oyunu");
        startWord=new JButton("Kelime Oyunu");
        username=new JTextField("Kullanıcı Adı");
        username.setMaximumSize(new Dimension(100,40));
        scoreTable=new JTable();
        right.add(scoreTable,BorderLayout.CENTER);
        left.add(title);
        left.add(Box.createVerticalStrut(100));
        left.add(username);
        mainMenu.add(Box.createVerticalStrut(40));
        left.add(zorluk);
        mainMenu.add(Box.createVerticalStrut(40));
        left.add(skors);
        mainMenu.add(Box.createVerticalStrut(40));
        left.add(startNumber);
        mainMenu.add(Box.createVerticalStrut(40));
        left.add(startWord);

        mainMenu.add(left);
        mainMenu.add(right);

        return mainMenu;
    }

    public JPanel createGameMenu(){
        gameMenu = new JPanel();
        gameMenu.setLayout(new BorderLayout());
        JPanel topPanel=new JPanel();
        FlowLayout flowLayout=new FlowLayout();
        flowLayout.setHgap(40);
        topPanel.setLayout(flowLayout);
        centerPanel=new JPanel();
        centerPanel.setLayout(null);


        exit=new JButton("Çıkış");
        time=new JLabel("Time: "+seconds);
        JLabel hedef_writing=new JLabel("HEDEF: ");
        hedef_writing.setBounds(200,100,100,50);
        label1=new LabelNumber(1,200,250);
        label2=new LabelNumber(1,300,250);
        label3=new LabelNumber(1,400,250);
        label4=new LabelNumber(1,500,250);
        label5=new LabelNumber(1,600,250);
        hedef =new LabelNumber(1,350,100);
        num1=new LabelNumber(0,200,450);
        num2=new LabelNumber(0,380,450);
        result=new LabelNumber(0,590,450);
        equal=new Button("=");
        equal.setBounds(490,460,50,50);
        JLabel results=new JLabel("RESULTS");
        results.setBounds(1020,50,100,50);
        labels[0]=label1;
        labels[1]=label2;
        labels[2]=label3;
        labels[3]=label4;
        labels[4]=label5;
        for(LabelNumber label:labels){
            label.addMouseListener(adapterNumber);
            label.addMouseMotionListener(adapterNumber);
        }
        btn=new Button("+");
        btn.setBounds(300,460,60,60);
        btn.setVisible(true);

        centerPanel.add(btn);
        topPanel.add(exit);
        topPanel.add(time);
        centerPanel.add(label1);
        centerPanel.add(label2);
        centerPanel.add(label3);
        centerPanel.add(label4);
        centerPanel.add(label5);
        centerPanel.add(hedef);
        centerPanel.add(num1);
        centerPanel.add(num2);
        centerPanel.add(result);
        centerPanel.add(equal);
        centerPanel.add(hedef_writing);
        gameMenu.add(topPanel,BorderLayout.NORTH);
        gameMenu.add(centerPanel,BorderLayout.CENTER);

        return gameMenu;
    }
    public  JPanel createWordGamePanel(){
        wordGameMenu=new JPanel();
        wordGameMenu.setLayout(new BorderLayout());
        letters=new ArrayList<>();
        word=new ArrayList<>();
        JPanel topPanel=new JPanel();
        centerPanelOfWord=new JPanel();
        topPanel.setLayout(new FlowLayout());
        centerPanelOfWord.setLayout(null);
        lettersLabel=new JLabel("Harfler");
        lettersLabel.setBounds(415,60,100,50);
        wordLabel=new JLabel("Kelime");
        wordLabel.setBounds(415,340,100,50);
        jokers=new JPanel();
        jokers.setLayout(new GridLayout(5,6));
        jokers.setBounds(1000,90,250,250);
        centerPanelOfWord.add(jokers);


        timeForWords=new JLabel("Zaman: "+seconds);
        exitForWords=new JButton("Çıkış");
        compelete=new JButton("Tamamla");
        compelete.setBounds(1100,600,100,50);
        JLabel jokerLabel=new JLabel("JOKER AL");
        jokerLabel.setBounds(1075,20,100,50);

        topPanel.add(exitForWords);
        topPanel.add(timeForWords);
        centerPanelOfWord.add(jokerLabel);
        centerPanelOfWord.add(compelete);
        centerPanelOfWord.add(lettersLabel);
        centerPanelOfWord.add(wordLabel);
        wordGameMenu.add(topPanel,BorderLayout.NORTH);
        wordGameMenu.add(centerPanelOfWord,BorderLayout.CENTER);

        return wordGameMenu;
    }

    public JPanel createGameOverPanel(){

        gameOverPanel=new JPanel(new BorderLayout());
         centerOfGameOver=new JPanel();
         centerOfGameOver.setLayout(new BoxLayout(centerOfGameOver,BoxLayout.Y_AXIS));
        JPanel top=new JPanel(new FlowLayout());
        JLabel gameOver=new JLabel("GAME OVER");
        again=new JButton("Play Again");
        goToMenu=new JButton("Go to menu");

        top.add(again);
        top.add(goToMenu);

        centerOfGameOver.add(gameOver);
        gameOverPanel.add(centerOfGameOver,BorderLayout.CENTER);
        gameOverPanel.add(top,BorderLayout.NORTH);

        return gameOverPanel;
    }
    public JPanel createWinnerPanel(){
        repaint();
        winnerPanel=new JPanel();
        winnerPanel.setLayout(new BoxLayout(winnerPanel,BoxLayout.Y_AXIS));
        JPanel upper=new JPanel();
        upper.setLayout(new FlowLayout());
        JPanel lower=new JPanel();
        lower.setLayout(new FlowLayout());
        showScore=new JLabel("Rekor: "+this.score);
        playAgain=new JButton("Tekrar oyna");
        backToMenu=new JButton("Menüye git");
        upper.add(showScore);
        lower.add(playAgain);
        lower.add(backToMenu);
        winnerPanel.add(upper);
        winnerPanel.add(lower);
        return winnerPanel;
    }
    public void setJokerLabels(String[] liste){
        jokerLabels=new ArrayList<>();
        for(int i=0;i<liste.length;i++){
            LabelWord b=new LabelWord(liste[i],0,0,true);
            b.setSize(20,20);
            b.addMouseMotionListener(adapterWord);
            b.addMouseListener(adapterWord);
            jokers.add(b);
            letters.add(b);
            jokerLabels.add(b);

        }
    }


    public void setButtons(char c){
        getBtn().setText(String.valueOf(c));
    }
    public void labelAssigner(ArrayList<Integer>  liste){
        for(int i=0;i<5;i++){
            labels[i].setNumber(liste.get(i));
            labels[i].setText(String.valueOf(liste.get(i)));
        }

    }//number için labellar oluşturuyor
    public void labelWorsAssingerAndCreator(ArrayList<String> liste){
        int blank=20;
        int base=150;
        for(int i=0;i<liste.size();i++){
            if(i>=10){
                int x=(55+blank)*(i-10)+base;
                LabelWord label = new LabelWord(liste.get(i), x, 225, true);
                centerPanelOfWord.add(label);
                letters.add(label);
                label.addMouseListener(adapterWord);
                label.addMouseMotionListener(adapterWord);
            }else {
                int x = (55 + blank) * i + base;
                LabelWord label = new LabelWord(liste.get(i), x, 150, true);
                centerPanelOfWord.add(label);
                letters.add(label);
                label.addMouseListener(adapterWord);
                label.addMouseMotionListener(adapterWord);
            }

        }
        for(int i=0;i<liste.size();i++){
            if(i>=10){
                int x=(55+blank)*(i-10)+base;
                LabelWord label = new LabelWord(null, x, 525, false);
                centerPanelOfWord.add(label);
                word.add(label);
                label.addMouseListener(adapterWord);
                label.addMouseMotionListener(adapterWord);
            }else {
                int x = (55 + blank) * i + base;
                LabelWord label = new LabelWord(null, x, 430, false);
                centerPanelOfWord.add(label);
                word.add(label);
                label.addMouseListener(adapterWord);
                label.addMouseMotionListener(adapterWord);
            }

        }

    }//bu word için labellari oluşturuyor


    public void labelCreator(LinkedHashSet<Integer> liste){
        List<Integer> temp=new ArrayList<>(liste);
        for(int i=createdLabels;temp.size()>i;i++) {
            if(createdLabels>=16){
                break;
            }
            int rows = createdLabels / 4;
            int tamOlmayanRows = createdLabels % 4;
            int x=START_X,y=START_Y;
            x=rows*BLANK+rows*label4.getWidth()+START_X;
            if (tamOlmayanRows == 0) {
                y=START_Y;
            } else if (tamOlmayanRows == 1) {
                y=START_Y+label4.getWidth()+BLANK;
            } else if (tamOlmayanRows == 2) {
                y=START_Y+(label4.getWidth()*2)+(2*BLANK);
            } else if (tamOlmayanRows == 3) {
                y=START_Y+(label4.getWidth()*3)+(3*BLANK);
            }

            LabelNumber new_one=new LabelNumber(temp.get(i),x,y);
            new_one.addMouseListener(adapterNumber);
            new_one.addMouseMotionListener(adapterNumber);
            centerPanel.add(new_one);
            resultLabels.add(new_one);

        }
        createdLabels=liste.size();
    }

    public void showMainMenu(){
        cardLayout.show(mainPanel,"MENU");

    }
    public void deleteLabels(){
        for(LabelNumber l:resultLabels){
            centerPanel.remove(l);
        }

        resultLabels.clear();
        createdLabels=0;
    }
    public void deleteLabelsWord(){
        for(LabelWord label:jokerLabels){
            centerPanelOfWord.remove(label);
            jokers.remove(label);
        }
        for(LabelWord label:letters){
            centerPanelOfWord.remove(label);
        }
        for(LabelWord label:word){
            centerPanelOfWord.remove(label);
        }
        jokerLabels.clear();
        letters.clear();
        word.clear();

    }

    public void showGameMenu(){
        cardLayout.show(mainPanel,"NUMBER");
    }
    public void showGameOver() {
        cardLayout.show(mainPanel,"GAMEOVER");
    }
    public void showWinnerMenu(){
        cardLayout.show(mainPanel,"WINNER");
    }
    public void showWordMenu(){
        cardLayout.show(mainPanel,"WORD");
    }
    public void setSomeLabels(int num) {
        int x;
        if(num<=10) {
             x = (num * 55) / 2 + (num - 1) * 10 + 150;
        }else{
            x = (10 * 55) / 2 + (10 - 1) * 10 + 150;
        }
        this.wordLabel.setBounds(x,340,100,50);
        this.lettersLabel.setBounds(x,60,100,50);
    }//bu arkadaşın tek görevi letterLabel VE wordLabel ları ortalamak
    public void setScoreTable(List<Map.Entry<String,Integer>> liste){
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Kullanıcı Adı");
        model.addColumn("Skorlar");
        for(Map.Entry<String,Integer> stringIntegerEntry : liste) {
            model.addRow(new Object[]{stringIntegerEntry.getKey(),stringIntegerEntry.getValue()});
        }
        scoreTable.setModel(model);
    }
    public void setGameOverNotification(String element){
        JTextArea area=new JTextArea(element);
        centerOfGameOver.add(area);
    }



    class ModernButton extends JButton {
        private Color normalColor = Color.decode("#27AE60"); // Yeşil tonu
        private Color hoverColor = normalColor.brighter();
        private boolean isHovered = false;

        public ModernButton(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(200, 50)); // Boyut verdik

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isHovered) g2.setColor(hoverColor);
            else g2.setColor(normalColor);

            // Yuvarlatılmış köşeler (30px yarıçap)
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }
    }


    //getter setter


    public JComboBox<Difficulty> getZorluk() {

        return zorluk;
    }

    public void setZorluk(JComboBox<Difficulty> zorluk) {
        this.zorluk = zorluk;
    }

    public JLabel getTime() {
        return time;
    }

    public ArrayList<LabelWord> getWord() {
        return word;
    }

    public JLabel getTimeForWords() {
        return timeForWords;
    }

    public JButton getStartWord() {
        return startWord;
    }

    public JButton getStartNumber() {
        return startNumber;
    }

    public JButton getExitForWords() {
        return exitForWords;
    }

    public void setTime(JLabel time) {
        this.time = time;
    }

    public JButton getExit() {
        return exit;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public JButton getGoToMenu() {
        return goToMenu;
    }

    public JButton getAgain() {
        return again;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JLabel getShowScore() {
        return showScore;
    }

    public JButton getPlayAgain() {
        return playAgain;
    }

    public JButton getBackToMenu() {
        return backToMenu;
    }

    public LabelNumber getResult() {
        return result;
    }

    public LabelNumber getNum2() {
        return num2;
    }

    public LabelNumber getNum1() {
        return num1;
    }

    public LabelNumber getHedef() {
        return hedef;
    }

    public void setHedef(LabelNumber hedef) {
        this.hedef = hedef;
    }

    public Button getEqual() {
        return equal;
    }

    public void setEqual(Button equal) {
        this.equal = equal;
    }

    public Button getBtn() {
        return btn;
    }

    public ArrayList<LabelWord> getJokerLabels() {
        return jokerLabels;
    }

    public JButton getCompelete() {
        return compelete;
    }

    public void setCompelete(JButton compelete) {
        this.compelete = compelete;
    }

    public JTextField getUsername() {
        return username;
    }

    public void setUsername(JTextField username) {
        this.username = username;
    }
}