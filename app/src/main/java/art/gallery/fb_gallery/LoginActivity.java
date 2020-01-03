package art.gallery.fb_gallery;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("FB", MODE_PRIVATE);
    }

    public void checkCredentials(View v) {
        String em = sharedPreferences.getString("email", null);
        String ps = sharedPreferences.getString("password", null);
        if (em == null)
            register(email.getText().toString(), password.getText().toString());
        else if (em.equals(email.getText().toString()) && ps != null && ps.equals(password.getText().toString()))
            login();
        else
            showMessageDialog(getString(R.string.wrong_credentials),getString(R.string.credentials_err));
    }

    public void register(String em, String ps) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (em.length() < 3 || ps.length() < 3)
            showMessageDialog(getString(R.string.wrong_credentials),getString(R.string.length_error));
        else{
            editor.putString("email", em);
            editor.putString("password", ps);
            editor.apply();
            showMessageDialog(getString(R.string.success), getString(R.string.success_login));
            openFacebookLinkActivity();
        }
    }

    public void login(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void openFacebookLinkActivity(){
        Intent i = new Intent(this, FacebookLinkActivity.class);
        startActivity(i);
        finish();
    }

    public void showMessageDialog(String title, String message){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
