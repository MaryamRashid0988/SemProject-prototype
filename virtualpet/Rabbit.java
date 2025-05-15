package virtualpet;

public class Rabbit extends Pet{
    public Rabbit(String name) {
        super(name, 60, 50, 40, new String[]{"Carrot", "Lettuce"},new String[]{"Cardboard Box","Plushie"}, 80, "eook eook");
    }

    @Override
    public String evolveName(String original) {
        return "Pikachu";
    }

}
