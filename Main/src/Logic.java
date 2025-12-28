

import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Logic {
    final Frame frame;
    private Timer gameChecker;
    private Number number;
    private Difficulty difficulty;
    private Word word;
    private boolean isGameOver=false,isGameComp;
    private boolean numberOrWord;
    private String name;
    private DataBase dataBase;

    public Logic(Frame frame,Difficulty difficulty,boolean numberOrWord,String name,DataBase dataBase) {
        this.frame = frame;
        this.difficulty=difficulty;
        this.dataBase=dataBase;
        this.name=name;
        this.numberOrWord=numberOrWord;
        if(numberOrWord) {
            this.number=new Number(this.frame,this.difficulty);
            this.gameChecker = new Timer(10, (a) -> {
                if (frame.getSeconds() > 0) {
                    frame.setSeconds(frame.getSeconds() - 0.01);
                    String timeStr = "Time: " + Math.round(frame.getSeconds());
                    frame.getTime().setText(timeStr);
                } else if (frame.getSeconds() <= 0) {
                    isGameOver=true;
                }

                frame.repaint();//labelların oluşması için
                if (number.isGameComp()) {
                    this.frame.setScore(number.calculateScore());
                    this.frame.getShowScore().setText("Rekor: " + this.frame.getScore());
                    frame.showWinnerMenu();
                    dataBase.saveScore(name,frame.getScore());
                    stopLogic();
                } else if (isGameOver) {
                    frame.setGameOverNotification(number.prepareString());
                    frame.showGameOver();
                    stopLogic();
                }
            });
            gameChecker.start();
        }
        else {
            this.word=new Word(frame,difficulty);
            this.gameChecker = new Timer(10, (a) -> {
                if (frame.getSeconds() > 0) {
                    frame.setSeconds(frame.getSeconds() - 0.01);
                    String timeStr = "Time: " + Math.round(frame.getSeconds());
                    frame.getTimeForWords().setText(timeStr);
                } else if (frame.getSeconds() <= 0) {
                    isGameOver=true;
                }
                frame.repaint();//labelların oluşması için
                if (word.isGameComp()) {
                    this.frame.setScore(word.calculateScore());
                    this.frame.getShowScore().setText("Rekor: " + this.frame.getScore());
                    frame.showWinnerMenu();
                    dataBase.saveScore(name,frame.getScore());
                    stopLogic();
                } else if (isGameOver||word.isGameOver()) {
                    frame.setGameOverNotification(word.prepareString());
                    frame.showGameOver();
                    stopLogic();
                }
            });
            gameChecker.start();
        }


    }


    public void clearButtonListeners(JButton button){
        for(ActionListener al: button.getActionListeners()){
            button.removeActionListener(al);
        }
    }
    public void stopLogic(){
        this.gameChecker.stop();
        clearButtonListeners(frame.getBtn());
        clearButtonListeners(frame.getEqual());
        isGameOver=false;
        isGameComp=false;
        if(numberOrWord) {
            number.getResults().clear();
            number.getResultsOfCase().clear();
            number.getOpertationsMade().clear();
            number.getNumbers().clear();
            frame.deleteLabels();
            number.setButton_index(0);
            frame.getNum1().setText("");
            frame.getNum2().setNumber(0);
            frame.getNum2().setText("");
            frame.getNum1().setNumber(0);
            frame.getResult().setNumber(0);
            frame.getResult().setText("");
            frame.setButtons(number.getOperations()[number.getButton_index()]);

        }else if(word!=null) {
            word.stopWord();
            word = null;
        }
    }

    public boolean isNumberOrWord() {
        return numberOrWord;
    }

    public void setNumberOrWord(boolean numberOrWord) {
        this.numberOrWord = numberOrWord;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean isGameComp() {
        return isGameComp;
    }

    public void setGameComp(boolean gameComp) {
        isGameComp = gameComp;
    }

    public Timer getGameChecker() {
        return gameChecker;
    }

    public void setGameChecker(Timer gameChecker) {
        this.gameChecker = gameChecker;
    }

    public Frame getFrame() {
        return frame;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
