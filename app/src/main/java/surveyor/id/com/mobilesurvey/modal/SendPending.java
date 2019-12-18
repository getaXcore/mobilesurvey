package surveyor.id.com.mobilesurvey.modal;

/**
 * Created by Vigaz on 11/3/2017.
 */

public class SendPending {

    private String id_order,name,address_home;

    public SendPending() {
    }
    public SendPending(String id_order, String name, String address_home) {
        this.id_order = id_order;
        this.name = name;
        this.address_home = address_home;
    }

    public String getIdOrder() {
        return id_order;
    }

    public void setIdOrder(String id_order) {
        this.id_order = id_order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressHome() {
        return address_home;
    }

    public void setAddressHome(String address_home) {
        this.address_home = address_home;
    }

}