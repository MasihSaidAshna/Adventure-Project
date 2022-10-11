public class Enemy {

    private String enemyName;
    private String description;
    private int enemyHealth;
    private Item weapon;

    public Enemy (String enemyName, String description, int enemyHealth, Weapon weapon) {
        this.enemyName = enemyName;
        this.description = description;
        this.enemyHealth = enemyHealth;
        this.weapon = weapon;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public String toString() {
        return enemyName + "\n" + description + "\n" + "It has " + enemyHealth + " HP" + "\n" + "It seems to be wielding a " + weapon;
    }
}
