import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Word {
    private HashMap<String,String> wordsAndMeanings;
    private List<String> words;//bu başlangıç kelimesini seçmek için
    private Random rand=new Random();
    //alfabe ekstra verilecek harfler için
    private String [] alphabet={"A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z"};
    private Difficulty difficulty;
    private ArrayList<String> givenLetters;
    private Frame frame;
    private boolean isGameOver=false,isGameComp=false;
    private String word;
    private String selectedWord;

    public Word(Frame frame,Difficulty difficulty){
        this.frame=frame;
        this.wordsAndMeanings=new HashMap<>();
        this.words=new ArrayList<>();
        this.difficulty=difficulty;
        this.givenLetters=new ArrayList<>();
        String wordFile="C:\\Users\\Yusuf\\Desktop\\JAVA_PROJECT_FILE\\Oyun\\Main\\words.txt";
        String meaningFile="C:\\Users\\Yusuf\\Desktop\\JAVA_PROJECT_FILE\\Oyun\\Main\\meaning.txt";
        upLoadWords(wordFile, meaningFile);
        selectStartingWord();
        giveLetters();
        actions();

    }
    //bu wordsAndMeanings ve words listesine dosyadan ekleme yapıcak
    public void upLoadWords(String wordsFile,String meaningsFile){
        try (BufferedReader brKelime = new BufferedReader(new FileReader(wordsFile));
             BufferedReader brAnlam = new BufferedReader(new FileReader(meaningsFile))) {

            String kelime, anlam;
            while ((kelime = brKelime.readLine()) != null && (anlam = brAnlam.readLine()) != null) {
                kelime = kelime.trim().toLowerCase(new Locale("tr", "TR"));

                // HashMap'e ekle: Sorgulama yaparken anlamı da elimizde olsun
                wordsAndMeanings.put(kelime, anlam);
                // Listeye ekle: Rastgele kelime seçebilelim
                words.add(kelime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //kullancıya vereceğimiz kelimeyi seçecek
    public String selectStartingWord(){
         selectedWord=this.words.get(rand.nextInt(this.words.size())).toUpperCase();
        return selectedWord;
    }
    //bu seçtiğimiz kelimedeki harfler ile birkaç harf fazladan vericek
    public void giveLetters(){

        for(int i=0;i<difficulty.harfSayısı;i++){
            givenLetters.add(alphabet[rand.nextInt(alphabet.length)]);
        }
        String[] selectedWord=selectStartingWord().split("");
        givenLetters.addAll(Arrays.asList(selectedWord));
        Collections.shuffle(givenLetters);
        frame.labelWorsAssingerAndCreator(givenLetters);
        frame.setSomeLabels(givenLetters.size());
        frame.setJokerLabels(alphabet);

    }
    //kullanıcının oluşturduğu kelimeyi çek edicek
    public void checkWord(){
        ArrayList<LabelWord> labels=frame.getWord();
       word="";
        for(LabelWord label:labels){
            if(label.getLetter()!=null) {
                word += label.getLetter();
            }
        }
        System.out.println(word);
        if(wordsAndMeanings.containsKey(word.trim().toLowerCase())){
            isGameComp=true;
        }else{
            isGameOver=true;
        }

    }
    //bu da scoru en son kontrol edicek
    public int calculateScore(){
        int base=1000;
        double time_bonus=frame.getSeconds()*20;
        int jokers=0;
        for(LabelWord labelWord:frame.getJokerLabels()){
            if(labelWord.getText().isEmpty()){
                jokers++;
            }
        }
        //double letterNumberBonus=((double) word.length() /givenLetters.size())*20;
        return (int)((base+time_bonus-jokers*50)*difficulty.scoreMultiplier);

    }
    public void actions(){
        frame.getCompelete().addActionListener(e->{
            checkWord();
        });
    }
    public void stopWord(){
        wordsAndMeanings.clear();
        words.clear();
        givenLetters.clear();
        isGameOver=false;
        isGameComp=false;
        frame.deleteLabelsWord();

    }
    public String prepareString(){
        String wordAndMeaning=selectedWord+"\nAnlamı\n"+wordsAndMeanings.get(selectedWord.toLowerCase());
        return wordAndMeaning;
    }
    public HashMap<String, String> getWordsAndMeanings() {
        return wordsAndMeanings;
    }

    public void setWordsAndMeanings(HashMap<String, String> wordsAndMeanings) {
        this.wordsAndMeanings = wordsAndMeanings;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
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

    public ArrayList<String> getGivenLetters() {
        return givenLetters;
    }

    public void setGivenLetters(ArrayList<String> givenLetters) {
        this.givenLetters = givenLetters;
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

    public String[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String[] alphabet) {
        this.alphabet = alphabet;
    }
}
