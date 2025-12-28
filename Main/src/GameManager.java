import javax.swing.*;

public class GameManager {
    private final Frame frame;
    private Logic logic;
    private String name=null;
    private DataBase dataBase;

    GameManager(){
        this.frame=new Frame();
        dataBase=new DataBase();
        frame.setScoreTable(dataBase.getTopScores());
        actions();


    }
    public void gameStarterNumber(){
        if(this.logic!=null) {
            this.logic.stopLogic();
            this.logic = null;
        }
        this.frame.setSeconds(getZorluk().time);
        this.logic=new Logic(frame,getZorluk(),true,name,dataBase);
        frame.showGameMenu();


    }
    public void gameStarterWord(){
        if(this.logic!=null) {
            this.logic.stopLogic();
            this.logic = null;
        }
        this.frame.setSeconds(getZorluk().time);
        this.logic=new Logic(frame,getZorluk(),false,name,dataBase);
        frame.showWordMenu();
    }
    public void show(){
        frame.showMainMenu();

    }
    public void exit(){
        this.logic.stopLogic();
        this.logic=null;

    }
    public void actions(){

        frame.getStartNumber().addActionListener(e->{
            name=frame.getUsername().getText();
            if(name.equalsIgnoreCase("Kullanıcı Adı")||name.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Kullanıcı adı giriniz");
            }else {
                gameStarterNumber();
            }
            frame.getArea().setText("");
        });
        frame.getExit().addActionListener(e->{
            show();
            exit();
            frame.getArea().setText("");
        });
        frame.getGoToMenu().addActionListener(e->{
            show();
            exit();
            frame.getArea().setText("");
        });
        frame.getAgain().addActionListener(e->{
            if(this.logic.isNumberOrWord()) {
                exit();
                gameStarterNumber();
            }else{
                exit();
                gameStarterWord();
            }
            frame.getArea().setText("");
        });
        frame.getBackToMenu().addActionListener(e->{
            show();
            exit();
            frame.getArea().setText("");
        });
        frame.getPlayAgain().addActionListener(e->{
            if(this.logic.isNumberOrWord()) {
                exit();
                gameStarterNumber();
            }else{
                exit();
                gameStarterWord();
            }
            frame.getArea().setText("");
        });
        frame.getStartWord().addActionListener(e->{
            name=frame.getUsername().getText();
            if(name.equalsIgnoreCase("Kullanıcı Adı")||name.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Kullanıcı adı giriniz");
            }else {
                gameStarterWord();
            }
        });
        frame.getExitForWords().addActionListener(e->{
            exit();
            show();
        });
        frame.getSkors().addActionListener(e -> {
            // Veritabanından güncel skorları çek ve tabloya bas
            frame.setScoreTable(dataBase.getTopScores());
            JOptionPane.showMessageDialog(frame, "Skorlar Güncellendi!");
        });

    }
    public Difficulty getZorluk(){
       return (Difficulty) frame.getZorluk().getSelectedItem();
    }



    public static void main(String[] args){
            GameManager manager=new GameManager();
    }



}
