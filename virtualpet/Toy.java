package virtualpet;

public class Toy {
    String name;
    int moodIncrease;
    int energyDecrease;
    int price;
    int uses;

    public Toy(String name, int moodIncrease,int energyDecrease, int price) {
        this.name = name;
        this.moodIncrease = moodIncrease;
        this.energyDecrease=energyDecrease;
        this.price = price;
        this.uses = 4;

    }
    public void use() {
        if (uses > 0) {
            uses -= 1;
        }
    }
}


