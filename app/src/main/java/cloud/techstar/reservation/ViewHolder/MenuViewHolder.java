package cloud.techstar.reservation.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.techstar.reservation.R;

/**
 * Created by Dolly on 1/11/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
    public TextView txtMenuName;
    public ImageView imageView;
    
    public MenuViewHolder (View itemView){
        super(itemView);
        txtMenuName = (TextView)itemView.findViewById(R.id)
    }
    
    
    @Override
    public void onClick(View view) {
    
    }
}
