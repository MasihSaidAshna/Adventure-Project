public class RangedWeapon extends Weapon{

    private int ammunition;

    public RangedWeapon(String itemName, int ammunition) {
        super(itemName);
        this.ammunition = ammunition;
    }

    /*public boolean checkRangedType(Item item) {
        boolean checkWeapon = false;
        if (this.getItemName().equals(item.getItemName())){
            checkWeapon = true;
        }
        return checkWeapon;
    }*/


    public int getAmmunition() {return ammunition;}

    public void setRangedAmmunition (int ammunition) {
        this.ammunition = ammunition;
    }

}
