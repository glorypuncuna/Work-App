package glorypuncuna.jwork_android;

public class Job {
    //Bagian ini digunakan untuk mendeklarasikan semua instance variable dalam Class Job
    private int id;
    private String name;
    private Recruiter recruiter;
    private int fee;
    private String category;

    public Job(int id, String name, Recruiter recruiter, int fee, String category) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.category = category;
        this.recruiter = recruiter;
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

    public int getFee() {
        return fee;
    }

    public String getCategory() {
        return category;
    }

    public Recruiter getRecruiter() {
        return recruiter;
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

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public void setFee(int Fee) {
        this.fee = fee;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}