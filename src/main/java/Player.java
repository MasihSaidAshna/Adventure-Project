import java.util.*;

public class Player {

    Map map = new Map();
    private Room currentRoom = map.getStarterRoom(); //Initialiserer currentRoom som starterRoom.

    private Item currentWeapon; //Brugerens nuværene våben

    private ArrayList<Item> inventory = new ArrayList<>(); //Arraylist som er spillerens inventory

    private boolean didPlayerAttack; //Attribut som bruges i handleEnemyAttack i userinterface klassen
    Food food = new Food("",0);

    //Get metoder til nuværende rum, våben og inventory
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Item getCurrentWeapon() {
        return currentWeapon;
    }

    public ArrayList<Item> getInventoryList() {
        return inventory;
    }

    public boolean getDidPlayerAttack() {
        return didPlayerAttack;
    }

    //Set metoder til nuværende rum og våben
    public void setCurrentWeapon(Item currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    //Metoder til at flytte forskellige retninger
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

    //Finder et item i spillerens inventory
    public Item findItemInv(String itemName) {
        Item itemInv = null;
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item.getItemName().equals(itemName)) {
                itemInv = item;
            }
        }
        return itemInv;
    }


    public boolean takeItem(String itemName) {
        boolean itemTaken = false;
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            inventory.add(item);
            currentRoom.removeItem(item);
            itemTaken = true;
        }
        return itemTaken;
    }

    public boolean dropItem(String itemName) {
        boolean itemDrop = false;
        Item item = findItemInv(itemName);
        if (item != null) {
            inventory.remove(item);
            currentRoom.getItemList().add(item);
            itemDrop = true;
        }
        return itemDrop;
    }

    //Tjekker om et item i rummet er spiseligt
    public boolean itemEdibleRoom(String itemName) {
        boolean eatOrNot = false;
        Item item = currentRoom.findItem(itemName);
        if (item instanceof Food) {
            eatOrNot = true;
        }
        return eatOrNot;
    }

    //Tjekker om et item i inventory er spiseligt
    public boolean itemEdibleInventory(String itemName) {
        boolean eatOrNot = false;
        Item item = findItemInv(itemName);
        if (item instanceof Food) {
            eatOrNot = true;
        }
        return eatOrNot;
    }

    //Tilføjer eller reducerer liv til spilleren når de spiser noget
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

    //Armerer spilleren med et validt item
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
            isWeapon = false;
        }
        return isWeapon;
    }

    //Boolean metode som returnerer true hvis spilleren kan angribe målet
    public boolean playerAttack (String targetName) {
        boolean hitSuccessful = false;
        if (getCurrentWeapon() != null){
            Weapon CW = (Weapon) currentWeapon;
            Enemy enemy = currentRoom.findEnemy(targetName);
            try {
                if (enemy.getEnemyName().equals(targetName) && enemy.getEnemyHealth() > 0) {
                    if (CW.getWeaponType().equals("melee")) {
                        enemy.setEnemyHealth(enemy.getEnemyHealth() - CW.getWeaponDamage());
                        didPlayerAttack = true;
                        hitSuccessful = true;
                    } else if (CW.getWeaponType().equals("ranged")) {
                        if (CW.getAmmunition() > 0) {
                            enemy.setEnemyHealth(enemy.getEnemyHealth() - CW.getWeaponDamage());
                            didPlayerAttack = true;
                            hitSuccessful = true;
                        }
                    }
                }
            }
            catch (Exception e) {
                hitSuccessful = false;
            }
        }
        return hitSuccessful;
    }
}
