import java.util.Scanner;

public class UserInterface {
    private Adventure adventure = new Adventure(); //Adventure objekt skabes som koordinator

    private boolean gameRunning = true; // Initialiserer gameRunning som true
    private Scanner sc = new Scanner(System.in); // Scanner til bruger input

    public void startProgram() { //Metode
        //Menu til brugeren
        System.out.println("""
                _____________________________________________________________________
                Welcome to CRYPT OF UMBRA
                In this game you can explore a dungeon and interact with items inside
                Beware there are monsters in these halls
                Should you defend yourself, weapons can be found in some rooms
                If you need help try entering the "help" command
                Where do you want to go?
                _____________________________________________________________________
                    """);

        adventure.player.food.setHealthPoints(100); //Initialiserer player's liv med 100 health points
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
                    System.out.println("You are in " + lookRoom(adventure.player.getCurrentRoom()));
                    break;
                case "help":
                    System.out.println("""
                            _____________________________________________________________________
                            You can choose to go "north", "east", "south" or "west".
                            You can type "look" to see the current room's description.
                            You can interact with items by writing "take", "drop" and "eat".
                            You can see your inventory by typing "inventory", "invent" or "inv".
                            You can see your current health by typing "health" or "h".
                            You can equip weapons by taking a weapon from the room and "equip"
                            it from your inventory.
                            You can attack enemies if they are present and if you have a weapon
                            equipped.
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
                    System.out.println("Enter the name of the enemy you want to attack");
                    String enemyName = sc.nextLine().toLowerCase();
                    handlePlayerAttack(enemyName);
                    handleEnemyAttack(enemyName);
                    break;
                case "exit":
                    System.out.println("Ending adventure..");
                    gameRunning = false;
            }
        }
    }


    public String lookRoom(Room room) { //Metode som bruges af "look" kommandoen og handleRoomDirection
        String roomDetails = room.toString();
        if (room.getItemList().isEmpty()) {
            if (room.getEnemies().isEmpty()) {
                roomDetails = room.getName() + "\n" + room.getDescription() + "\n" +  "Room is empty!";
            }
            else {
                roomDetails = room.getName() + "\n" + room.getDescription() + "\n" +
                        room.getEnemies().toString().replace("[","").replace("]","") + "\nNo items found";
            }
        }
        else if (room.getEnemies().isEmpty()) {
            String str = room.getName() + "\n" + room.getDescription() + "\nYou found: " +
                    room.getItemList().toString().replace("[","").replace("]","") + "\n" + "No enemies around";
            roomDetails = str;
        }
        return roomDetails;
    }


    public void handleRoomDirection(boolean goDirection) { //Printer oplysninger om det næste rum
        if (goDirection) {
            System.out.println("You are going to " + lookRoom(adventure.player.getCurrentRoom()));
        } else {
            System.out.println("You cannot go this way");
        }
    }


    public void showPlayerItems () {
        if (adventure.player.getInventoryList().isEmpty()) {
            System.out.println("Inventory is empty");
        }
        else {
            checkEquippedWeapon(adventure.player.getCurrentWeapon());
            for (Item item : adventure.player.getInventoryList()) {
                System.out.println(item.getItemName());
            }
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

    public void handlePlayerEat (String foodEat) { //Håndterer om spilleren kan spise
        if (adventure.player.eatItem(foodEat)) {
            if (adventure.player.food.getHealthPoints() > 0) {
                System.out.println("You ate and gained: " + adventure.player.food.getFoodHealthPoints() + "HP \n" +
                        "You now have: " + adventure.player.food.getHealthPoints() + " health points");
            }
            else {
                System.out.println("YOU DIED!");
                gameRunning = false;
            }
        }
        else if (adventure.player.getCurrentRoom().findItem(foodEat) == null) {
            System.out.println("Item was not found..");
        }
        else if (!adventure.player.itemEdibleRoom(foodEat) || !adventure.player.itemEdibleInventory(foodEat)) {
            System.out.println("Item is not edible");
        }
    }

    public void handlePlayerEquip (String weaponName) { //Håndterer om spilleren kan armere sig med et item
        if (adventure.player.equipWeapon(weaponName)){
            System.out.println("Weapon equipped: " + adventure.player.getCurrentWeapon());
        }
        else if (adventure.player.findItemInv(weaponName) == null){
            System.out.println("Item does not exist ");
        }
        else {
            System.out.println("Item can't be equipped");
        }
    }

    public void checkEquippedWeapon (Item weaponItem) { //Tjekker om våbnet er melee, ranged eller slet ikke et våben
        if (weaponItem != null) {
            Weapon weapon = (Weapon)weaponItem;
            if (weapon.getWeaponType().equals("melee")){
                System.out.println("Currently equipped " + adventure.player.getCurrentWeapon());
            }
            else if (weapon.getWeaponType().equals("ranged")){
                System.out.println("Currently equipped " + adventure.player.getCurrentWeapon() + "\nAmmo: " + weapon.getAmmunition());
            }
        }
        else {System.out.println("No weapon equipped");}
    }

    /*Handles if player can attack, what weapon type they have, if they don't have a current weapon, if their target
    * doesn't exist or wants to hit the air and handles ranged weapon ammunition decreasing for every shot.*/
    public void handlePlayerAttack (String targetName) {
        if (adventure.player.playerAttack(targetName)){
            Weapon CW = (Weapon) adventure.player.getCurrentWeapon();
            if (CW.getWeaponType().equals("melee")) {
                System.out.println("You struck " + targetName + " with the " + adventure.player.getCurrentWeapon() + "!"
                + "\n" + targetName + " has " + adventure.player.getCurrentRoom().findEnemy(targetName).getEnemyHealth() + " HP left");
            }
            else if (CW.getWeaponType().equals("ranged")) {
                RangedWeapon rangedWeapon = (RangedWeapon) CW;
                if (rangedWeapon.getAmmunition() == 0) {
                    System.out.println("You have no ammo!");
                }
                else if (rangedWeapon.getAmmunition() == 1) {
                    rangedWeapon.setRangedAmmunition(rangedWeapon.getAmmunition()-1);
                    System.out.println("You shot your last shot at " + targetName + " with the " + adventure.player.getCurrentWeapon() + "!"
                    + "\n" + targetName + " has " + adventure.player.getCurrentRoom().findEnemy(targetName).getEnemyHealth() + " HP left");
                }
                else if (rangedWeapon.getAmmunition() > 1) {
                    rangedWeapon.setRangedAmmunition(rangedWeapon.getAmmunition()-1);
                    System.out.println("You shot " + targetName + " with the " + adventure.player.getCurrentWeapon()
                    + "!\nAmmo left: " + rangedWeapon.getAmmunition()
                    + "\n" + targetName + " has " + adventure.player.getCurrentRoom().findEnemy(targetName).getEnemyHealth() + " HP left");
                }
            }
            else {System.out.println("Cant attack with no weapon equipped!");}
        }
        else if (adventure.player.getCurrentWeapon() == null){
            System.out.println("No weapon is equipped!");
        }
        else {
            Weapon CW = (Weapon) adventure.player.getCurrentWeapon();
            if (CW.getWeaponType().equals("melee")) {
                System.out.println("Swung " + CW.getItemName() + " in the air!");
            }
            else if (CW.getWeaponType().equals("ranged")) {
                RangedWeapon rangedWeapon = (RangedWeapon) CW;
                if (rangedWeapon.getAmmunition() == 0) {
                    System.out.println("You have no ammo!");
                }
                else if (rangedWeapon.getAmmunition() == 1) {
                    rangedWeapon.setRangedAmmunition(CW.getAmmunition()-1);
                    System.out.println("You shot your last shot in the air with the " + adventure.player.getCurrentWeapon() + "!");
                }
                else if (rangedWeapon.getAmmunition() > 1) {
                    rangedWeapon.setRangedAmmunition(CW.getAmmunition()-1);
                    System.out.println("You shot the air with the " + adventure.player.getCurrentWeapon()
                            + "!\nAmmo left: " + rangedWeapon.getAmmunition());
                }
            }
        }
    }

    public void handleEnemyAttack (String targetName) { //Fjender slår aldrig uprovokeret af spilleren
        Enemy enemy = adventure.player.getCurrentRoom().findEnemy(targetName);
        if (adventure.player.getDidPlayerAttack()){
            try {
                if (enemy.getEnemyHealth() > 0){
                    adventure.player.food.setHealthPoints(adventure.player.food.getHealthPoints() - enemy.getEnemyWeapon().getWeaponDamage());
                    if (adventure.player.food.getHealthPoints() <= 0) {
                        System.out.println(targetName + " attacks player with " + enemy.getEnemyWeapon() + " for " +
                                enemy.getEnemyWeapon().getWeaponDamage() + " damage!\n" + "YOU DIED!");
                        gameRunning = false;
                    }
                    else {
                        System.out.println(targetName + " attacks player with " + enemy.getEnemyWeapon() + " for " +
                                enemy.getEnemyWeapon().getWeaponDamage() + " damage!");
                    }
                }
                else {
                    adventure.player.getCurrentRoom().getEnemies().remove(enemy);
                    adventure.player.getCurrentRoom().getItemList().add(enemy.getEnemyWeapon());
                    System.out.println("You are victorious! Enemy dropped " + enemy.getEnemyWeapon() + " in this room");
                }
            }
            catch (Exception e) {
                System.out.println("Enemy does not exist");
            }
        }
    }
}