package cntt.bkdn.ledat.bkzalo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class FlashCreenActivity extends AppCompatActivity {
    private static boolean splashLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!splashLoaded) {
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(FlashCreenActivity.this, LoginActivity.class));
                    finish();
                }
            }, secondsDelayed * 5000);

            splashLoaded = true;
        }
        else {
            Intent goToMainActivity = new Intent(FlashCreenActivity.this, LoginActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }

    }

}
