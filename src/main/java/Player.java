import java.util.*;

public class Player {

    Map map = new Map();
    private Room currentRoom = map.getStarterRoom(); //Initialiserer currentRoom som starterRoom.

    private ArrayList<Item> inventory = new ArrayList<>(); //Arraylist som er player's inventory

    private int foodHealthPoints;
    Food food = new Food("food", foodHealthPoints);

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    public ArrayList<Item> getInventoryList () {
        return inventory;
    }

    //Metode til at flytte forskellige retninger
    public boolean goNorth() {
        if (currentRoom.getNorth() == null) {
            return false;
        } else {
            setCurrentRoom(currentRoom.getNorth());
            return true;
        }
    }

    public boolean goEast() {
        if (currentRoom.getEast() == null) {
            return false;
        } else {
            setCurrentRoom(currentRoom.getEast());
            return true;
        }
    }

    public boolean goWest() {
        if (currentRoom.getWest() == null) {
            return false;
        } else {
            setCurrentRoom(currentRoom.getWest());
            return true;
        }
    }

    public boolean goSouth() {
        if (currentRoom.getSouth() == null) {
            return false;
        } else {
            setCurrentRoom(currentRoom.getSouth());
            return true;
        }
    }

    public boolean takeItem (String itemName){
        boolean isNull = false;
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            inventory.add(item);
            currentRoom.removeItem(item);
            isNull = true;
        }
        return isNull;
    }

    public boolean dropItem (String itemName){
        boolean isNull = false;
        for (int i = 0; i < getInventoryList().size(); i++) {
            Item item = getInventoryList().get(i);
            if (item.getItemName().equals(itemName)) {
                inventory.remove(item);
                currentRoom.getItemList().add(item);
                isNull = true;
            }
        }
        return isNull;
    }

    public boolean itemEdibleRoom (String itemName) {
        boolean eatOrNot = false;
        Item item = currentRoom.findItem(itemName);
        if (item instanceof Food) {
            eatOrNot = true;
        } /*else if (item == null) {
            eatOrNot = false;
        }*/
        return eatOrNot;
    }

    public boolean itemEdibleInventory (String itemName) {
        boolean eatOrNot = false;
        for (int i = 0; i < getInventoryList().size(); i++) {
            Item item = inventory.get(i);
            if (item.getItemName().equals(itemName)) {
                if (item instanceof Food) {
                    eatOrNot = true;
                }
            }
            /*else if (item == null) {
                //eatOrNot = false;
                eatOrNot = false;
            }*/
        }
        return eatOrNot;
    }

    //Class cast exception
/*    public boolean eatItem (String foodName) { //Kan ikke bruge Food class metoder på item objekter.
        boolean isNull = false;
        //Food itemFood = (Food) new Item(foodName);
        if (itemEdibleRoom(foodName)){
            //for (itemFood : currentRoom.getItemList()) {
            for (int i = 0; i < currentRoom.getItemList().size(); i++) {
                //Item itemFood = getInventoryList().get(i);
                //Food itemFood = (Food) new Item(currentRoom.getItemList().get(i).getItemName());
                //Food itemFood = (Food) currentRoom.getItemList().get(i);
                Item item = currentRoom.getItemList().get(i);
                Food itemFood = (Food)item;
                if (itemFood.getItemName().equals(foodName)) {
                    currentRoom.removeItem(itemFood);
                    int foodHeal = itemFood.getFoodHeal();
                    itemFood.setHealthPoints(food.getHealthPoints() + foodHeal);
                    return true;
                }
            }
        }
        else if (itemEdibleInventory(foodName)){
            for (int i = 0; i < inventory.size(); i++) {
                //Food itemFood = (Food) new Item(inventory.get(i).getItemName());
                //Food itemFood = (Food) inventory.get(i);
                Item item = inventory.get(i);
                Food itemFood = (Food)item;
                if (itemFood.getItemName().equals(foodName)) {
                    inventory.remove(itemFood);
                    int foodHeal = itemFood.getFoodHeal();
                    food.setHealthPoints(food.getHealthPoints() + foodHeal);
                    return true;
                }
            }
        }
        return isNull;
    }*/

    public boolean eatItem (String foodName) {
        boolean isNull = false;
        if (itemEdibleRoom(foodName)){
                Item itemFood = currentRoom.findItem(foodName);
                if (itemFood.getItemName().equals(foodName)) {
                    currentRoom.removeItem(itemFood);
                    Food foodItem = (Food)itemFood;
                    food.setFoodHealthPoints(foodItem.getFoodHealthPoints());
                    food.setHealthPoints(food.getHealthPoints() + foodItem.getFoodHealthPoints());
                    return true;
                }
        }
        else if (itemEdibleInventory(foodName)){
            for (int i = 0; i < inventory.size(); i++) {
                Item itemFood = inventory.get(i);
                if (itemFood.getItemName().equals(foodName)) {
                    inventory.remove(itemFood);
                    Food foodItem = (Food)itemFood;
                    food.setFoodHealthPoints(foodItem.getFoodHealthPoints());
                    food.setHealthPoints(food.getHealthPoints() + foodItem.getFoodHealthPoints());
                    return true;
                }
            }
        }
        return isNull;
    }

}
