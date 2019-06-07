package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model;

public class product {
    public String description,image,cetagory,price,name,pid,date,time;

    public product(String description, String image, String cetagory, String price, String name, String pid, String date, String time) {
        this.description = description;
        this.image = image;
        this.cetagory = cetagory;
        this.price = price;
        this.name = name;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    public product(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCetagory() {
        return cetagory;
    }

    public void setCetagory(String cetagory) {
        this.cetagory = cetagory;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
