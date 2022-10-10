public abstract class Weapon extends Item{


    private Integer ammunition;
    private String weaponType;
    public Weapon(String itemName, String weaponType) {
        super(itemName);
        this.weaponType = weaponType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public Integer getAmmunition () {return ammunition;}

    public void setRangedAmmunition (int ammunition) {
        this.ammunition = ammunition;
    }

    @Override
    public String toString() {
        return "Weapon: " + getItemName();
    }
}
