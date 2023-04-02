package models;

public class Sport  extends Product{
    
    private String sport;


    public Sport() {
        super();
        this.sport = "Nothing";
    }


    public Sport(String name, String brand, int cost, String sport) {
        super(name, brand, cost);
        this.sport = sport;
    }
    
    public Sport(String name, String brand, int cost) {
        super(name, brand, cost);
        this.sport = "Nothing";
    }

    public String getSport() {
        return this.sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }


    @Override
    public String toString() {
        return "Sport{" +
            " id_product='" + getId_product() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", sport='" + getSport() + "'" +
            "}";
    }


}
