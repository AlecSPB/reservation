package cloud.techstar.reservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cloud.techstar.reservation.Interface.ItemClickListener;
import cloud.techstar.reservation.Model.Food;
import cloud.techstar.reservation.ViewHolder.FoodViewHolder;

public class FoodListActivity extends AppCompatActivity {
    
    private static final String TAG = FoodListActivity.class.getSimpleName();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    
    FirebaseDatabase database;
    DatabaseReference foodList;
    
    String categoryId="";
    
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        
        //Firebase
        
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        
        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
    
        Log.e(TAG, "Category id : "+categoryId);
        if (!categoryId.isEmpty()  && categoryId != null )
        {
        
        }
    
        loadListFood(categoryId);
    }
    
    private void loadListFood(String categoryId) {
        adapter =  new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)
                
                ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
        
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
    
                       Intent foodDetail = new Intent(FoodListActivity.this, FoodDetailActivity.class);
                       foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                       startActivity(foodDetail);
        
                    }
                });
            }
        };
                //Set Adapter
        Log.e(TAG, "ITEMS : "+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
        
    }
}
