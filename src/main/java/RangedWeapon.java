public class RangedWeapon extends Weapon{

    private int ammunition;

    public RangedWeapon(String itemName, String weaponType, int ammunition) {
        super(itemName, weaponType);
        this.ammunition = ammunition;
    }

    @Override
    public Integer getAmmunition() {return ammunition;}

    @Override
    public void setRangedAmmunition (int ammunition) {
        this.ammunition = ammunition;
    }
}
