package glorypuncuna.jwork_android;

public class Location {

    private String province;
    private String city;
    private String description;

    public Location(String province, String city, String description) {
        this.province = province;
        this.city = city;
        this.description = description;
    }

    /**
     * Metode dibawah ini digunakan sebagai getter untuk mengembalikan nilai
     * dari variabel. Sebagai contoh adalah getProvince():
     *
     * @return int, mengembalikan nilai dari variabel province.
     */
    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Metode dibawah ini digunakan sebagai setter untuk mengubah nilai
     * dari variabel. Sebagai contoh adalah setId():
     *
     * @return int, mengubah nilai dari variabel id.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
