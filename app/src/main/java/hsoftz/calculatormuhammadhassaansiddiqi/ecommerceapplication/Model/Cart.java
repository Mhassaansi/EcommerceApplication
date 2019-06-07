package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model;

public class Cart {
    private String date,time,quantity,price,pname,pid,discount;

    public Cart(String date, String time, String quantity, String price, String pname, String pid, String discount) {
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.price = price;
        this.pname = pname;
        this.pid = pid;
        this.discount = discount;
    }
public Cart(){}
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
