package cloud.techstar.reservation.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cloud.techstar.reservation.Interface.ItemClickListener;
import cloud.techstar.reservation.Model.Order;
import cloud.techstar.reservation.R;

/**
 * Created by Dolly on 1/22/2018.
 */

class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    
    public TextView txt_cart_name, txt_price;
    public ImageView img_cart_count;
    
    private ItemClickListener itemClickListener;
    
    public void setTxt_cart_name(TextView txt_cart_name){
        
        this.txt_cart_name = txt_cart_name;
    }
    
    public CardViewHolder( View itemView){
        super(itemView);
        txt_cart_name= (TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        img_cart_count = (ImageView)itemView.findViewById(R.id.cart_item_count);
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public void onClick(View v) {
    
    }
}

public class CartAdapter extends RecyclerView.Adapter<CardViewHolder> {
    
    private List<Order> listData = new ArrayList<>();
    private Context context;
    
    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }
    
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false );
        return new CardViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + listData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);
    
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        
        holder.txt_cart_name.setText(listData.get(position).getProductName());
    
        
    }
    
    @Override
    public int getItemCount() {
        
        return listData.size();
    }
}
