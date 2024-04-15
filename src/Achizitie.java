
public class Achizitie implements Comparable<Achizitie> {
    private String cod;
    private int an;
    private int luna;
    private int zi;
    private int cantitate;
    private float pret_unitar;
    public Achizitie(String cod, int an, int luna, int zi, int cantitate, float pret_unitar) {
        this.cod = cod;
        this.an = an;
        this.luna = luna;
        this.zi = zi;
        this.cantitate = cantitate;
        this.pret_unitar = pret_unitar;
    }

    public Achizitie() {
        this.cod = "N/A";
        this.an = 0;
        this.luna = 0;
        this.zi = 0;
        this.cantitate = 0;
        this.pret_unitar = 0;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public int getLuna() {
        return luna;
    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPret_unitar() {
        return pret_unitar;
    }

    public void setPret_unitar(float pret_unitar) {
        this.pret_unitar = pret_unitar;
    }

    @Override
    public String toString() {
        return "Achizitie{" +
                "cod='" + cod + '\'' +
                ", an=" + an +
                ", luna=" + luna +
                ", zi=" + zi +
                ", cantitate=" + cantitate +
                ", pret_unitar=" + pret_unitar +
                '}';
    }

    float valoare() {
        return pret_unitar * cantitate;
    }


    @Override
    public int compareTo(Achizitie o) {
        return Float.compare(this.valoare(), o.valoare());
    }
}
