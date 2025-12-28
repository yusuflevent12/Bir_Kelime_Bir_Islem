import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.*;

public class Number {
    private Frame frame;
    private ArrayList<Integer> numbers;//labelların içi
    private LinkedHashSet<Integer> results;//buda kullanıcının hesaplarının sonuçları
    private LinkedHashSet<Integer> resultsOfCase;//bu en başta case oluşturulurken çıkan resultlar
    private char[] operations={'+','-','*','/'};
    private HashMap<Integer,ArrayList<Integer>> opertationsMade;
    final Random rand=new Random();
    private int button_index=0;//bu hangi operasyon olacağını tutuyor
    private Difficulty difficulty;
    private boolean isGameComp=false;

    public Number(Frame frame,Difficulty difficulty) {
        this.frame=frame;
        this.numbers=new ArrayList<>();
        this.results=new LinkedHashSet<>();
        this.resultsOfCase=new LinkedHashSet<>();
        this.opertationsMade=new HashMap<>();
        this.difficulty=difficulty;
        numberCreator();
        caseCreator();
        actions();



    }

    //bu en başta işlemleri ve sayıları oluşturuyoruz
    public void numberCreator(){
        for(int i=0;i<4;i++){
            int number=rand.nextInt(1,10);
            if(!numbers.contains(number)){
                numbers.add(number);
            }
            else{
                i--;
            }
        }
        numbers.add(rand.nextInt(10,100));
        frame.labelAssigner(numbers);
    }
    public void caseCreator(){
        List<Integer> list=new ArrayList<>(resultsOfCase);
        operationGenerator(0,true,true);
        for(int i=0;difficulty.steps-1>i;i++) {
            int sel_case = rand.nextInt(2);
            if (sel_case == 0) {
                operationGenerator(i+1,true,true);
            } else {
                if(resultsOfCase.size()<=1){
                    operationGenerator(i+1,true,false);
                }else{
                    operationGenerator(i+1,false,false);
                }
            }
            list=new ArrayList<>(resultsOfCase);
            if((list.getLast()<100||list.getLast()>10_000)&&i==difficulty.steps-2){
                i--;
            }
        }

        frame.getHedef().setNumber(list.getLast());
        frame.getHedef().setText(String.valueOf(list.getLast()));

    }

    public void actions(){

        frame.getBtn().addActionListener(e->{
            if(button_index==3){
                button_index=0;
            }else {
                button_index+=1;
            }
            frame.setButtons(operations[button_index]);
        });
        frame.getEqual().addActionListener(e -> {
            int num1=frame.getNum1().getNumber();
            int num2= frame.getNum2().getNumber();
            int result=calculator(num1,num2);
            if(result!=0) {
                frame.getResult().setNumber(result);
                frame.getResult().setText(String.valueOf(result));
                results.add(result);
                if(result== frame.getHedef().getNumber()){
                    isGameComp=true;
                }
                frame.labelCreator(results);


            }
        });

    }
    public Integer calculator(int num1,int num2){
        if(getButton_index()==0){
            return num1+num2;
        } else if (getButton_index()==1) {
            return num1-num2;
        } else if (getButton_index()==2) {
            return num1*num2;
        }else if(getButton_index()==3){
            if(num2==0){
                return 0;
            }else{
                return num1/num2;
            }
        }else {
            return 1;
        }
    }
    public Integer calculator(int num1,int num2,int index){
        if(index==0){
            return num1+num2;
        } else if (index==1) {
            return num1-num2;
        } else if (index==2) {
            return num1*num2;
        }else if(index==3){
            if(num2==0){
                return 0;
            }else{
                return num1/num2;
            }
        }else {
            return 1;
        }
    }
    public void operationGenerator(int index,boolean a,boolean b){
        while (true) {
            int index1 = 0, index2 = 0, num1 = 0, num2 = 0;
            if (a && b) {
                index1 = rand.nextInt(numbers.size());
                index2 = rand.nextInt(numbers.size());
                num1 = numbers.get(index1);
                num2 = numbers.get(index2);
            } else if (a || b) {
                index1 = rand.nextInt(numbers.size());
                index2 = rand.nextInt(resultsOfCase.size());
                num1 = numbers.get(index1);
                List<Integer> list = new ArrayList<>(resultsOfCase);
                num2 = list.get(index2);
            } else {
                index1 = rand.nextInt(resultsOfCase.size());
                index2 = rand.nextInt(resultsOfCase.size());
                List<Integer> list = new ArrayList<>(resultsOfCase);
                num1 = list.get(index1);
                num2 = list.get(index2);
            }
            int operation_index = rand.nextInt(4);
            int result = calculator(num1, num2, operation_index);
            if (result != 0) {
                resultsOfCase.add(result);
                opertationsMade.put(index, new ArrayList<>(List.of(num1, num2, operation_index)));
                break;
            }
        }
    }
    public int calculateScore(){
        int base=1000;
        double time_bonus=frame.getSeconds()*20;

        int islem_bonus=(difficulty.steps-results.size())*100;
        return (int)((base+time_bonus+islem_bonus)*difficulty.scoreMultiplier);
    }
    public String prepareString(){
        String strOperations="";
        List<Integer> liste=new ArrayList<>(resultsOfCase);
        for(int i=0;i<opertationsMade.size();i++){
            ArrayList<Integer> list=opertationsMade.get(i);
            strOperations +=list.get(0)+" ";
            strOperations +=operations[list.get(2)]+" ";
            strOperations +=list.get(1)+" = ";
            int result= calculator(list.get(0),list.get(1),list.get(2));
            strOperations+=result+" \n";
            if(result==liste.getLast()){
                break;
            }
        }
        return strOperations;
    }


    //getter setter
    public LinkedHashSet<Integer> getResultsOfCase() {
        return resultsOfCase;
    }

    public void setResultsOfCase(LinkedHashSet<Integer> resultsOfCase) {
        this.resultsOfCase = resultsOfCase;
    }

    public LinkedHashSet<Integer> getResults() {
        return results;
    }

    public void setResults(LinkedHashSet<Integer> results) {
        this.results = results;
    }

    public Random getRand() {
        return rand;
    }

    public HashMap<Integer, ArrayList<Integer>> getOpertationsMade() {
        return opertationsMade;
    }

    public void setOpertationsMade(HashMap<Integer, ArrayList<Integer>> opertationsMade) {
        this.opertationsMade = opertationsMade;
    }

    public char[] getOperations() {
        return operations;
    }

    public void setOperations(char[] operations) {
        this.operations = operations;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public boolean isGameComp() {
        return isGameComp;
    }

    public void setGameComp(boolean gameComp) {
        isGameComp = gameComp;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getButton_index() {
        return button_index;
    }

    public void setButton_index(int button_index) {
        this.button_index = button_index;
    }
}
