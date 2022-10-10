public class Enemy {

    private String enemyName;
    private String description;
    private int enemyHealth;
    private Item weapon;

    public Enemy (String enemyName, String description, int enemyHealth, Item weapon) {
        this.enemyName = enemyName;
        this.description = description;
        this.enemyHealth = enemyHealth;
        this.weapon = weapon;
    }

}
