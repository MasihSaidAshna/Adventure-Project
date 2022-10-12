public class Enemy {

    private String enemyName;
    private String description;
    private int enemyHealth;
    private Weapon weapon;

    public Enemy (String enemyName, String description, int enemyHealth, Weapon weapon) {
        this.enemyName = enemyName;
        this.description = description;
        this.enemyHealth = enemyHealth;
        this.weapon = weapon;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getEnemyHealth () {return enemyHealth;}

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public Weapon getEnemyWeapon () {
        if (weapon.getWeaponType().equals("melee")){
            return (MeleeWeapon) weapon;
        }
        else if (weapon.getWeaponType().equals("ranged")){
            return (RangedWeapon) weapon;
        }
        return null;
    }

    public String toString() {
        return enemyName + "\n" + description + "\n" + "It has " + enemyHealth + " HP" + "\n" + "It seems to be wielding a " + weapon;
    }
}
