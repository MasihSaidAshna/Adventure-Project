public class Map {

    Room room1 = new Room("Room 1 ", "Room with no distinct features, you see doors to the East & South");
    Room room2 = new Room("Room 2 ", "Room containing belongings to past adventurers, you see doors to the East & West");
    Room room3 = new Room("Room 3 ", "Looks like an old armory, you see doors to the West & South");
    Room room4 = new Room("Room 4 ", "Room with no distinct features, North & South");
    Room room5 = new Room("Room 5 ", "Room filled with glistening piles of gold, you see one door to the South");
    Room room6 = new Room("Room 6 ", "Room with no distinct features, you see doors to the North & South");
    Room room7 = new Room("Room 7 ", "Room looks like an abandoned training room, you see doors to the North & East");
    Room room8 = new Room("Room 8 ", "Room with no distinct features, East & West & North");
    Room room9 = new Room("Room 9 ", "Room filled with cobwebs.. you feel uneasy. You see North & West");

    public Map() {

        // Room bliver forbundet med en setmetode
        room1.setEast(room2);
        room1.setSouth(room4);

        room2.setEast(room3);
        room2.setWest(room1);

        room3.setSouth(room6);
        room3.setWest(room2);

        room4.setNorth(room1);
        room4.setSouth(room7);

        room5.setSouth(room8);

        room6.setNorth(room3);
        room6.setSouth(room9);

        room7.setNorth(room4);
        room7.setEast(room8);

        room8.setNorth(room5);
        room8.setEast(room9);
        room8.setWest(room7);

        room9.setNorth(room6);
        room9.setWest(room8);

        //Tilføjer items og mad med string navne til rum
        room1.addItemToRoom( "bag");
        room1.addItemToRoom( "insect");
        room1.addFoodToRoom( "bread", 10);
        room2.addItemToRoom( "torch");
        room2.addItemToRoom( "coin");
        room3.addMeleeToRoom("rusty sword");
        room3.addRangedToRoom("longbow", 5);
        room3.addFoodToRoom("rotten meat", -20);
        room4.addItemToRoom("shoes");
        room5.addItemToRoom("treasure chest");
        room5.addFoodToRoom("bottle of wine", 20);
        room7.addMeleeToRoom("shield");
        room7.addFoodToRoom("rat poison", -100);
        room9.addItemToRoom("spiders");
        room9.addFoodToRoom("spider eggs", -40);
    }

    public Room getStarterRoom() { //Initialiserer starterRoom som room1
        return room1;
    }
}

