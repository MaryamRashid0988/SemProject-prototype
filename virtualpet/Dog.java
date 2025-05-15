package virtualpet;

public class Dog extends Pet {
    public Dog(String name) {
        super(name, 60, 50, 80, new String[]{"Bone","Beef"},new String[]{"Frisbee","Bone"}, 80, "Woof Woof");
    }

    @Override
    public String evolveName(String original) {
        return "Wolf";
    }

}
