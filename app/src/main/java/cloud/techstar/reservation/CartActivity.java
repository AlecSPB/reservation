package cloud.techstar.reservation;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cloud.techstar.reservation.Common.Common;
import cloud.techstar.reservation.Database.Database;
import cloud.techstar.reservation.Model.Order;
import cloud.techstar.reservation.Model.Request;
import cloud.techstar.reservation.ViewHolder.CartAdapter;
import info.hoang8f.widget.FButton;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    
    FirebaseDatabase database;
    DatabaseReference requests;
    
    TextView txtTotalPrice;
    AppCompatButton btnPlace;
    
    List<Order> cart = new ArrayList<>();
    
    CartAdapter adapter;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
                
                txtTotalPrice = (TextView)findViewById(R.id.total);
                btnPlace = (AppCompatButton) findViewById(R.id.btnPlaceOrder);
                
                btnPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      showAlertDialog();
                    }
                });
                
                loadListFood();
                
    }
    
    private void showAlertDialog() {
    
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter you Address!");
        
        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart);
        
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
                
                requests.child( String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this, "Thank you order!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                        alertDialog.show();
                    
        
    }
    private void loadListFood() {
        
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
        
        int total = 0;
        for (Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        
        txtTotalPrice.setText(fmt.format(total));
    
    }
}
