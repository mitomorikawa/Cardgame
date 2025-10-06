public class Card extends Thread{
    private int denomination;
    public Card(int denomination){
        this.denomination = denomination;
    }
    public int getDenomination(){
        return denomination;
    }
}