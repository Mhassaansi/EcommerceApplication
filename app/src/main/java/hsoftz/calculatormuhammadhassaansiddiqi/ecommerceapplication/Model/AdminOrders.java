package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model;

public class AdminOrders {
   private String adress,city,date,state,phone,time,totalAmount,name;
    /*private String ;
    private String ;
    private  String ;
    private String ;
    private String ;
    private String ;
    private String ;*/

    public AdminOrders(String adress, String city, String date, String state, String phone, String
            time, String totalAmount, String name) {
        this.adress = adress;
        this.city = city;
        this.date = date;
        this.state = state;
        this.phone = phone;
        this.time = time;
        this.totalAmount = totalAmount;
        this.name = name;
    }

    public AdminOrders() {
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}