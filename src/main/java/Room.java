import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private Room north;
    private Room east;
    private Room west;
    private Room south;

    //Et arraylist som indeholder genstande i rummene
    private ArrayList<Item> items = new ArrayList<>();

    private ArrayList<Enemy> enemies = new ArrayList<>();

    //Konstruktør
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //Set metoder

    public void setNorth(Room north) {
        this.north = north;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    //Get metoder
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Room getNorth() {
        return north;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public Room getSouth() {
        return south;
    }

    public void addItemToRoom (String itemName) {
        items.add(new Item(itemName));
    }

    public void addFoodToRoom (String foodName, int heal) {
        Item food = new Food(foodName, heal);
        items.add(food);
    }

    public void addMeleeToRoom (String meleeName, String weaponType, int weaponDamage) {
        Item weapon = new MeleeWeapon(meleeName, weaponType, weaponDamage);
        items.add(weapon);
    }

    public void addRangedToRoom (String rangedName, String weaponType, int ammo, int weaponDamage) {
        Item weapon = new RangedWeapon(rangedName, weaponType, ammo, weaponDamage);
        items.add(weapon);
    }

    public void addEnemyToRoom (String enemyName, String description, int enemyHealth, Weapon weapon){
        Enemy enemy = new Enemy(enemyName, description, enemyHealth, weapon);
        enemies.add(enemy);
    }

    public ArrayList<Item> getItemList () {
        return items;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void removeItem (Item item) {
        items.remove(item);
    }

    public Item findItem (String itemName){
        for (Item item : items) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
    return null;
    }

    @Override
    public String toString() { //Udskriver navn på room, beskrivelse og items i rummet
        return name + "\n" + description + "\n" + "There is: " + enemies + "\n" + "You spot Items: " + items + " \n";
    }
}

