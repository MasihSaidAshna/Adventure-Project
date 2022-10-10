import java.util.Scanner;

public class UserInterface {
    private Adventure adventure = new Adventure(); //Adventure objekt skabes

    private Player player = new Player(); //Player objekt

    private boolean gameRunning = true; // Initialiserer gameRunning som true
    private Scanner sc = new Scanner(System.in); // Scanner til bruger input

    public void startprogram() { //Metode
        //Menu til brugeren
        System.out.println("""
                _____________________________________________________________________
                Welcome To The Adventure Game
                In this game you can explore a dungeon and interact with items inside
                If you need help try entering the "help" command
                Where do you want to go?
                _____________________________________________________________________
                    """);

        while (gameRunning) { //While loop som inderholder en switch case, som kører så længe gameRunning er true
            String userInput = sc.nextLine().toLowerCase();
            switch (userInput) {
                case "go north", "north", "n":
                    boolean goNorth;
                    goNorth = adventure.player.goNorth();
                    handleRoomDirection(goNorth);
                    break;
                case "go east", "east", "e":
                    boolean goEast;
                    goEast = adventure.player.goEast();
                    handleRoomDirection(goEast);
                    break;
                case "go west", "west", "w":
                    boolean goWest;
                    goWest = adventure.player.goWest();
                    handleRoomDirection(goWest);
                    break;
                case "go south", "south", "s":
                    boolean goSouth;
                    goSouth = adventure.player.goSouth();
                    handleRoomDirection(goSouth);
                    break;
                case "look":
                    lookRoom(adventure.player.getCurrentRoom());
                    break;
                case "help":
                    System.out.println("""
                            _____________________________________________________________________
                            You can choose to go "north", "east", "south" or "west".
                            You can type "look" to see the current room's description.
                            You can interact with items by writing "take", "drop" and "eat"
                            You can see your inventory by typing "inventory", "invent" or "inv"
                            You can see your current health by typing "health" or "h"
                            You can close the game by typing "exit".
                            _____________________________________________________________________
                            """);
                    break;
                case "inventory", "inv", "invent":
                    showPlayerItems();
                    break;
                case "take":
                    System.out.println("Please enter the name of the item you want to take");
                    String itemTake = sc.nextLine().toLowerCase();
                    handlePlayerTake(itemTake);
                    handlePlayerTake(userInput);
                    break;
                case "drop":
                    System.out.println("Please enter the name of the item you want to drop");
                    String itemDrop = sc.nextLine().toLowerCase();
                    handlePlayerDrop(itemDrop);
                    break;
                case "health", "h":
                    showHealthPoints();
                    break;
                case "eat":
                    System.out.println("Please enter the name of the food source");
                    String foodEat = sc.nextLine().toLowerCase();
                    handlePlayerEat(foodEat);
                    break;
                case "equip":
                    System.out.println("Enter the name of the weapon to equip");
                    String itemEquip = sc.nextLine().toLowerCase();
                    handlePlayerEquip(itemEquip);
                    break;
                case "attack":
                    handlePlayerAttack();
                    break;
                case "exit":
                    System.out.println("Ending adventure..");
                    gameRunning = false;
            }
        }
    }


    public void lookRoom(Room room) { //Method used for the "look" command
        String roomDetails;
        if (room.getItemList().isEmpty()) {
            roomDetails = room.getName() + "\n" + room.getDescription() + "\n" +  "Room is empty!";
        }
        else {
            roomDetails = room.toString();
        }
        System.out.println("You are in " + roomDetails);
    }

    public void handleRoomDirection(boolean goDirection) {
        if (goDirection) {
            System.out.println("You are going to " + showRoomItems(adventure.player.getCurrentRoom()));
        } else {
            System.out.println("You cannot go this way");
        }
    }

    public String showRoomItems(Room room) { //Method used by handleRoomDirection
        String roomDetails;
        if (room.getItemList().isEmpty()) {
            roomDetails = room.getName() + "\n" + room.getDescription() + "\n" +  "Room is empty!";
        }
        else {
            roomDetails = room.toString();
        }
        return roomDetails;
    }

    public void showPlayerItems () {
        if (adventure.player.getInventoryList().isEmpty()) {
            System.out.println("Inventory is empty");
        }
        for (Item item : adventure.player.getInventoryList()) {
            System.out.println("Currently equipped " + adventure.player.getCurrentWeapon() + "\n" + item.getItemName());
        }
    }

    public void handlePlayerTake (String itemTake) {
        if (adventure.player.takeItem(itemTake)) {
            System.out.println("Item added to inventory");
        }
        else {
            System.out.println("Item was not found in this room");
        }
    }

    public void handlePlayerDrop (String itemDrop) {
        if (adventure.player.dropItem(itemDrop)) {
            System.out.println("Item removed from inventory");
        }
        else {
            System.out.println("Item was not found in your inventory");
        }
    }

    public void showHealthPoints () {
        int health = adventure.player.food.getHealthPoints();
        if (health > 50) {
            System.out.println("Health points: " + health + ", you are in good shape.");
        }
        else if (health <= 50 && health > 10) {
            System.out.println("Health points: " + health + ", you are injured and should heal your wounds.");
        }
        else if (health <= 10 && health > 0){
            System.out.println("Health points: " + health + ", you are in critical condition, seek help immediately");
        }
        else if (health <= 0){
            gameRunning = false;
            System.out.println("YOU DIED");
        }
    }

    public void handlePlayerEat (String foodEat) {
        if (adventure.player.eatItem(foodEat)) {
            System.out.println("You ate and gained: " + adventure.player.food.getFoodHealthPoints() + "HP \n" + "You now have: " + adventure.player.food.getHealthPoints() + " health points");
        }
        else if (adventure.player.getCurrentRoom().findItem(foodEat) == null) {
            System.out.println("Item was not found..");
        }
        else if (!adventure.player.itemEdibleRoom(foodEat) || !adventure.player.itemEdibleInventory(foodEat)) {
            System.out.println("Item is not edible");
        }
    }

    public void handlePlayerEquip (String weaponName) {
        if (adventure.player.equipWeapon(weaponName)){
            System.out.println("Weapon equipped: " + adventure.player.getCurrentWeapon());
        }
        else if (adventure.player.getCurrentRoom().findItem(weaponName) == null && adventure.player.findItemInv(weaponName) == null){
            System.out.println("Item does not exist ");
        }
        else {
            System.out.println("Item can't be equipped");
        }
    }

    public void handlePlayerAttack () {
        if (adventure.player.playerAttack()){
            if (adventure.player.getCurrentWeapon().getClass() == MeleeWeapon.class) {
                System.out.println("Swung in the air with " + adventure.player.getCurrentWeapon() + "!");
            }
            else if (adventure.player.getCurrentWeapon().getClass() == RangedWeapon.class) {
                RangedWeapon rangedWeapon = (RangedWeapon) adventure.player.getCurrentWeapon();
                if (rangedWeapon.getAmmunition() == 0) {
                    System.out.println("You have no ammo!");
                }
                else if (rangedWeapon.getAmmunition() == 1) {
                    System.out.println("You shoot your last shot with " + rangedWeapon + "!\n Ammo left: " + rangedWeapon.getAmmunition());
                }
                else if (rangedWeapon.getAmmunition() > 1) {
                    System.out.println("Shot the air with " + rangedWeapon + "!\n Ammo left: " + rangedWeapon.getAmmunition());
                }
            }
        }
        else {
            System.out.println("Cant attack with no weapon equipped!");
        }
    }

}