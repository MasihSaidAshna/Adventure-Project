public abstract class Weapon extends Item{


    public Weapon(String itemName) {
        super(itemName);
    }


    @Override
    public String toString() {
        return "Weapon: " + getItemName();
    }
}
