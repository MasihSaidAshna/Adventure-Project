public abstract class Weapon extends Item{


    private Integer ammunition;
    private String weaponType;
    private int weaponDamage;
    public Weapon(String itemName, String weaponType, int weaponDamage) {
        super(itemName);
        this.weaponType = weaponType;
        this.weaponDamage = weaponDamage;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public Integer getAmmunition () {return ammunition;}

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public void setRangedAmmunition (int ammunition) {
        this.ammunition = ammunition;
    }

    @Override
    public String toString() {
        return "Weapon: " + getItemName();
    }
}
