public class Food extends Item {

    private int healthPoints;

    private int foodHealthPoints;


    public Food(String itemName, int foodHealthPoints) {
        super(itemName);
        this.foodHealthPoints = foodHealthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getFoodHealthPoints() {
        return foodHealthPoints;
    }

    public void setFoodHealthPoints(int foodHealthPoints) {
        this.foodHealthPoints = foodHealthPoints;
    }


    @Override
    public String toString() {
        return "Food source: " + getItemName();
    }
}
