package cloud.techstar.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import cloud.techstar.reservation.Common.Common;
import cloud.techstar.reservation.Model.User;

public class SignInActivity extends AppCompatActivity {
    
    EditText edtPhone, edtPassword;
    Button btnSignIn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        
        edtPassword = (MaterialEditText)findViewById(R.id.edtPass);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
    
        final FirebaseDatabase database = FirebaseDatabase.getInstance(); //Firebase өгөгдлийн сантай холбогдох
        final DatabaseReference table_user = database.getReference("User"); // ene database.n User table ruu handaj bna
        
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Уншиж байна...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check it user not exist in databse
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
            
                            //get user information
                           
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
    
                            Log.d("", String.valueOf(dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class)));
                            assert user != null;
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                
                                Intent homeIntent = new Intent(SignInActivity.this,HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                mDialog.dismiss();
                                finish();
                            } else {
                                
                                mDialog.dismiss();
                                Toast.makeText(SignInActivity.this, "Нууц үг буруу байна.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this,"Хэрэглэгч байхгүй байна.", Toast.LENGTH_SHORT).show();
                        }
                    }
                
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
        
                    }
                });
                
            }
        });
        
    }
}
