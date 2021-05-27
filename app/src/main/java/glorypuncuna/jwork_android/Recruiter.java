package glorypuncuna.jwork_android;

public class Recruiter {
    //Bagian ini digunakan untuk mendeklarasikan semua instance variable dalam Class Recruiter
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    public Recruiter(int id, String name, String email, String phoneNumber, Location location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * Metode dibawah ini digunakan sebagai getter untuk mengembalikan nilai
     * dari variabel. Sebagai contoh adalah getId():
     *
     * @return int, mengembalikan nilai dari variabel id.
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Metode dibawah ini digunakan sebagai setter untuk mengubah nilai
     * dari variabel. Sebagai contoh adalah setId():
     *
     * @return int, mengubah nilai dari variabel id.
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
