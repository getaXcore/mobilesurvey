package surveyor.id.com.mobilesurvey.modal;

/**
 * Created by Vigaz on 9/19/2017.
 */


public class LanjutSurvey {
    private String id_order,nama_lengkap,alamat,nama_kelurahan,identity_type,telephone,sex,
            identity_no,handphone_1;

    public LanjutSurvey() {
    }

    public LanjutSurvey(String id_order, String nama_lengkap, String alamat, String nama_kelurahan,
                        String identity_type, String telephone, String sex, String identity_no,
                        String handphone_1) {
        this.id_order = id_order;
        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.nama_kelurahan = nama_kelurahan;
        this.identity_type = identity_type;
        this.telephone = telephone;
        this.sex = sex;
        this.identity_no = identity_no;
        this.handphone_1 = handphone_1;
    }

    public String getIdOrder() {
        return id_order;
    }

    public void setIdOrder(String id_order) {
        this.id_order = id_order;
    }

    public String getNamaLengkap() {
        return nama_lengkap;
    }

    public void setNamaLengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }


    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaKelurahan() {
        return nama_kelurahan;
    }

    public void setNamaKelurahan(String nama_kelurahan) {
        this.nama_kelurahan = nama_kelurahan;
    }

    public String getIdentityType() {
        return identity_type;
    }

    public void setIdentityType(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentityNo() {
        return identity_no;
    }

    public void setIdentityNo(String identity_no) {
        this.identity_no = identity_no;
    }

    public String getHandphone1() {
        return handphone_1;
    }

    public void setHandphone1(String handphone_1) {
        this.handphone_1 = handphone_1;
    }


}
