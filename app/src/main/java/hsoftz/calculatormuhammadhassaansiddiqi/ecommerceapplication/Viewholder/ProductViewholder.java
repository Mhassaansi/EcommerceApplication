package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Interface.Onclick;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.R;

public class ProductViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productname,productdescription,productprice;
    public ImageView imageView;
    public Onclick onclick;
    private Onclick listner;

    public ProductViewholder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.cardimage);
        productname = (TextView) itemView.findViewById(R.id.pname);
        productdescription = (TextView) itemView.findViewById(R.id.pdecs);
        productprice = (TextView) itemView.findViewById(R.id.pprice);

    }
public void setItemclickListner(Onclick listner){
        this.listner=onclick;
}
    @Override
    public void onClick(View v) {
listner.onclick(v,getAdapterPosition(),false);
    }
}
