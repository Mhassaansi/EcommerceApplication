package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Interface.Onclick;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  public    TextView price,name,quantity;
    private Onclick onclick;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
     quantity=(TextView)itemView.findViewById(R.id.Pcartquan);
        name=(TextView)itemView.findViewById(R.id.Pcartname);
        price=(TextView)itemView.findViewById(R.id.Pcartprice);
    }

    @Override
    public void onClick(View v) {

onclick.onclick(v ,getAdapterPosition(),false);
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
}
