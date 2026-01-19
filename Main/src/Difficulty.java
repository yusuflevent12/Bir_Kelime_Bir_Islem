import javax.swing.plaf.PanelUI;

public enum Difficulty {
    EASY(120,//SÜRESİ
         4,//adım sayısı
            1.0// bu score çarpanı
            ,2),// EKSTRA VERİLECEK HARF SAYISI
    NORMAL(80,6,  1.5,1),
    HARD(40,  8,2.0,0);
    public final int time;
    public final  int steps;
    public final double scoreMultiplier;
    public  final  int harfSayısı;

    Difficulty(int time ,int steps,double scoreMultiplier,int harfSayısı) {
        this.time = time;
        this.steps=steps;
        this.scoreMultiplier=scoreMultiplier;
        this.harfSayısı=harfSayısı;
    }
}
