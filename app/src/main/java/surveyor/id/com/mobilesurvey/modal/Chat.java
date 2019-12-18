package surveyor.id.com.mobilesurvey.modal;

/**
 * Created by Vigaz on 9/17/2017.
 */
public class Chat {
    private String idbalasmessage, idmessage, toidadmin, fromidadmin, fromnama, balasmessage,
            entrydatebalas, typemessage;

    public Chat() {
    }



    public Chat(String idbalasmessage, String idmessage, String toidadmin, String fromidadmin,
                String fromnama, String balasmessage, String entrydatebalas, String typemessage) {
        this.idbalasmessage = idbalasmessage;
        this.idmessage = idmessage;
        this.toidadmin = toidadmin;
        this.fromidadmin = fromidadmin;
        this.fromnama = fromnama;
        this.balasmessage = balasmessage;
        this.entrydatebalas = entrydatebalas;
        this.typemessage = typemessage;
    }

    public String getIdBalasMessage() {

        return idbalasmessage;
    }

    public void setIdBalasMessage(String idbalasmessage) {
        this.idbalasmessage = idbalasmessage;
    }

    public String getIdMessage() {

        return idmessage;
    }

    public void setIdMessage(String idmessage) {
        this.idmessage = idmessage;
    }

    public String getToIdAdmin() {
        return toidadmin;
    }

    public void setToIdAdmin(String toidadmin) {
        this.toidadmin = toidadmin;
    }

    public String getFromIdAdmin() {
        return fromidadmin;
    }

    public void setFromIdAdmin(String fromidadmin) {
        this.fromidadmin = fromidadmin;
    }


    public String getFromNama() {
        return fromnama;
    }

    public void setFromNama(String fromnama) {
        this.fromnama = fromnama;
    }


    public String getBalasMessage() {
        return balasmessage;
    }

    public void setBalasMessage(String balasmessage) {
        this.balasmessage = balasmessage;
    }

    public String getEntryDateBalas() {
        return entrydatebalas;
    }

    public void setEntryDateBalas(String entrydatebalas) {
        this.entrydatebalas = entrydatebalas;
    }

    public String getTypeMessage() {
        return typemessage;
    }

    public void setTypeMessage(String typemessage) {
        this.typemessage = typemessage;
    }


}

