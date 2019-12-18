package surveyor.id.com.mobilesurvey.modal;

/**
 * Created by Vigaz on 9/27/2017.
 */

public class JarakHistory {

    private String nama, jarak, tanggal, bulan, tahun, surveyaddress, kelurahan, statussurvey,id_order;

    public JarakHistory() {
    }

    public JarakHistory(String nama, String jarak, String tanggal, String bulan, String tahun, String surveyaddress, String kelurahan, String statussurvey,String id_order) {
        this.nama = nama;
        this.jarak = jarak;
        this.tanggal = tanggal;
        this.bulan = bulan;
        this.tahun = tahun;
        this.surveyaddress = surveyaddress;
        this.kelurahan = kelurahan;
        this.statussurvey = statussurvey;
        this.id_order = id_order;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getSurveyaddress() {
        return surveyaddress;
    }

    public void setSurveyaddress(String surveyaddress) {
        this.surveyaddress = surveyaddress;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getStatusSurvey() {
        return statussurvey;
    }

    public void setStatusSurvey(String statussurvey) {
        this.statussurvey = statussurvey;
    }

    public String getIdOrder() {
        return id_order;
    }

    public void setIdOrder(String id_order) {
        this.id_order = id_order;
    }
}