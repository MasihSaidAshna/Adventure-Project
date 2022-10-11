public class RangedWeapon extends Weapon{

    private int ammunition;

    public RangedWeapon(String itemName, String weaponType, int ammunition, int weaponDamage) {
        super(itemName, weaponType, weaponDamage);
        this.ammunition = ammunition;
    }

    @Override
    public Integer getAmmunition() {return ammunition;}

    @Override
    public void setRangedAmmunition (int ammunition) {
        this.ammunition = ammunition;
    }
}
