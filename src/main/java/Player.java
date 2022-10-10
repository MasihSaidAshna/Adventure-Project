import org.w3c.dom.ranges.Range;

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
        for (Item item : inventory) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
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

    public boolean itemEdibleRoom(String itemName) {
        boolean eatOrNot = false;
        Item item = currentRoom.findItem(itemName);
        if (item instanceof Food) {
            eatOrNot = true;
        } /*else if (item == null) {
            eatOrNot = false;
        }*/
        return eatOrNot;
    }

    public boolean itemEdibleInventory(String itemName) {
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


    public boolean eatItem(String foodName) {
        boolean eatFood = false;
        if (itemEdibleRoom(foodName)) {
            Item itemFood = currentRoom.findItem(foodName);
            //if (itemFood.getItemName().equals(foodName)) {
            currentRoom.removeItem(itemFood);
            Food foodItem = (Food) itemFood;
            food.setFoodHealthPoints(foodItem.getFoodHealthPoints());
            food.setHealthPoints(food.getHealthPoints() + foodItem.getFoodHealthPoints());
            eatFood = true;
            //}
        } else if (itemEdibleInventory(foodName)) {
            for (int i = 0; i < inventory.size(); i++) {
                Item itemFood = inventory.get(i);
                if (itemFood.getItemName().equals(foodName)) {
                    inventory.remove(itemFood);
                    Food foodItem = (Food) itemFood;
                    food.setFoodHealthPoints(foodItem.getFoodHealthPoints());
                    food.setHealthPoints(food.getHealthPoints() + foodItem.getFoodHealthPoints());
                    eatFood = true;
                }
            }
        }
        return eatFood;
    }

    public boolean equipWeapon(String weaponName) {
        boolean isWeapon = false;
        Item weaponItemRoom = currentRoom.findItem(weaponName);
        Item weaponItemInv = findItemInv(weaponName);
        try {
            if (weaponItemRoom.getClass() == MeleeWeapon.class || weaponItemRoom.getClass() == RangedWeapon.class) {
                currentWeapon = weaponItemRoom;
                currentRoom.getItemList().remove(weaponItemRoom);
                inventory.add(weaponItemRoom);
                isWeapon = true;
            } else if (weaponItemInv.getClass() == MeleeWeapon.class || weaponItemInv.getClass() == RangedWeapon.class) {
                currentWeapon = weaponItemInv;
                isWeapon = true;
            }
        }
        catch (Exception e) {
            isWeapon = Boolean.parseBoolean(null);
        }
        return isWeapon;
    }

    public boolean playerAttack (/*String targetName*/) {
        boolean hitSuccessful = false;
        if (getCurrentWeapon() != null){
            if (getCurrentWeapon().getClass() == MeleeWeapon.class){
                hitSuccessful = true;
            } else if (getCurrentWeapon().getClass() == RangedWeapon.class) {
                RangedWeapon rangedWeapon = (RangedWeapon)getCurrentWeapon();
                if (rangedWeapon.getAmmunition() > 0) {
                    rangedWeapon.setRangedAmmunition(rangedWeapon.getAmmunition()-1);
                    hitSuccessful = true;
                }
                else {
                    hitSuccessful = false;
                }
            }
        }
        return hitSuccessful;
    }
}
