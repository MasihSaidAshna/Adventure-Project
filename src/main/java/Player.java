import java.util.*;

public class Player {

    Map map = new Map();
    private Room currentRoom = map.getStarterRoom(); //Initialiserer currentRoom som starterRoom.

    private Item currentWeapon;

    private ArrayList<Item> inventory = new ArrayList<>(); //Arraylist som er player's inventory

    private int foodHealthPoints;
    Food food = new Food("food", foodHealthPoints);

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Item getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Item currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    public ArrayList<Item> getInventoryList() {
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


    public Item findItemInv(String itemName) {
        Item itemVar = null;
        for (int i = 0; i < inventory.size(); i++ /*Item item : inventory*/) {
            Item item = inventory.get(i);
            if (item.getItemName().equals(itemName)) {
                itemVar = item;
            }
        }
        return itemVar;
    }


    public boolean takeItem(String itemName) {
        boolean isNull = false;
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            inventory.add(item);
            currentRoom.removeItem(item);
            isNull = true;
        }
        return isNull;
    }


    public boolean dropItem(String itemName) {
        boolean isNull = false;
        Item item = findItemInv(itemName);
        if (item.getItemName().equals(itemName)) {
            inventory.remove(item);
            currentRoom.getItemList().add(item);
            isNull = true;
        }
        return isNull;
    }

    public boolean itemEdibleRoom(String itemName) {
        boolean eatOrNot = false;
        Item item = currentRoom.findItem(itemName);
        if (item instanceof Food) {
            eatOrNot = true;
        }
        return eatOrNot;
    }


    public boolean itemEdibleInventory(String itemName) {
        boolean eatOrNot = false;
        Item item = findItemInv(itemName);
        if (item.getItemName().equals(itemName)) {
            if (item instanceof Food) {
                eatOrNot = true;
            }
        }
        return eatOrNot;
    }


    public boolean eatItem(String foodName) {
        boolean eatFood = false;
        if (itemEdibleRoom(foodName)) {
            Item itemFood = currentRoom.findItem(foodName);
            currentRoom.removeItem(itemFood);
            Food foodItem = (Food) itemFood;
            food.setFoodHealthPoints(foodItem.getFoodHealthPoints());
            food.setHealthPoints(food.getHealthPoints() + foodItem.getFoodHealthPoints());
            eatFood = true;
        } else if (itemEdibleInventory(foodName)) {
            Item itemFood = findItemInv(foodName);
            if (itemFood.getItemName().equals(foodName)) {
                inventory.remove(itemFood);
                Food foodItem = (Food) itemFood;
                food.setFoodHealthPoints(foodItem.getFoodHealthPoints());
                food.setHealthPoints(food.getHealthPoints() + foodItem.getFoodHealthPoints());
                eatFood = true;
            }
        }
        return eatFood;
    }

    public boolean equipWeapon(String weaponName) {
        boolean isWeapon = false;
        Item itemInv = findItemInv(weaponName);
        try {
            if (itemInv instanceof Weapon) {
                setCurrentWeapon(itemInv);
                isWeapon = true;
            }
        }
        catch (Exception e) {
            isWeapon = Boolean.parseBoolean(null);
        }
        return isWeapon;
    }

    public boolean playerAttack (String targetName) {
        boolean hitSuccessful = false;
        if (getCurrentWeapon() != null){
            Weapon CW = (Weapon) currentWeapon;
            for (int i = 0; i < currentRoom.getEnemies().size(); i++) {
                Enemy enemy = currentRoom.getEnemies().get(i);
                if (enemy.getEnemyName().equals(targetName)){
                    if (CW.getWeaponType().equals("melee")) {
                        hitSuccessful = true;
                    }
                    else if (CW.getWeaponType().equals("ranged")) {
                        CW.setRangedAmmunition(CW.getAmmunition()-1);
                        hitSuccessful = true;
                    }
                    else {
                        hitSuccessful = false;
                    }
                }
            }
        }
        return hitSuccessful;
    }
}
