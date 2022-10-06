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

    //Konstruktør
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //Set metoder
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public ArrayList<Item> getItemList () {
        return items;
    }


    public void removeItem (Item item) {
        items.remove(item);
    }

    public String getItem (String itemName){
        for (Item item : items) {
            if (item.getItemName().equals(itemName)) {
                items.add(item);
            }
        }
        return null;
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
        return name + "\n" + description + "\n" + "Items: " + items + " \n";
    }
}

