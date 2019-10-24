package ru.shurinovlev.saos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static java.sql.Types.NULL;


public class Plots extends AppCompatActivity {

    static int  type_signal, where_to_see, fgen_quantity, type_filter, operation, type_s[];
    static double frequency, amplitude, ampl_noise, a[], f[], from_freq, before_freq, offset, o[];
    static Bitmap export, help;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int exportID = getResources().getIdentifier("export", "drawable",getPackageName());
        int helpID = getResources().getIdentifier("help", "drawable",getPackageName());
        export = BitmapFactory.decodeResource( getResources(), exportID );
        help = BitmapFactory.decodeResource( getResources(), helpID );

        Intent intent = getIntent();
        where_to_see = intent.getIntExtra("where_to_see", NULL);
        ampl_noise = intent.getDoubleExtra("ampl_noise", NULL);
        fgen_quantity = intent.getIntExtra("fgen_quantity", NULL);
        if (where_to_see == 0) {
            frequency = intent.getDoubleExtra("frequency", NULL);
            amplitude = intent.getDoubleExtra("amplitude", NULL);
            type_signal = intent.getIntExtra("type_signal", NULL);
            offset = intent.getDoubleExtra("offset", NULL);
        }
        else {
            a = new double[fgen_quantity];
            f = new double[fgen_quantity];
            o = new double[fgen_quantity];
            type_s = new int[fgen_quantity];
            if (fgen_quantity == 1){
                frequency = intent.getDoubleExtra("freq1", NULL);
                amplitude = intent.getDoubleExtra("ampl1", NULL);
                type_signal = intent.getIntExtra("type_signal1", NULL);
                type_filter = intent.getIntExtra("type_filter", NULL);
                from_freq = intent.getDoubleExtra("from_frequency", NULL);
                before_freq = intent.getDoubleExtra("before_frequency", NULL);
                offset = intent.getDoubleExtra("offset", NULL);
            }
            if (fgen_quantity == 2){
                f[0] = intent.getDoubleExtra("freq1", NULL);
                f[1] = intent.getDoubleExtra("freq2", NULL);
                a[0] = intent.getDoubleExtra("ampl1", NULL);
                a[1] = intent.getDoubleExtra("ampl2", NULL);
                o[0] = intent.getDoubleExtra("offset1", NULL);
                o[1] = intent.getDoubleExtra("offset2", NULL);
                type_s[0] = intent.getIntExtra("type_signal1", NULL);
                type_s[1] = intent.getIntExtra("type_signal2", NULL);
                type_filter = intent.getIntExtra("type_filter", NULL);
                from_freq = intent.getDoubleExtra("from_frequency", NULL);
                before_freq = intent.getDoubleExtra("before_frequency", NULL);
                operation = intent.getIntExtra("operation", NULL);

            }
            if (fgen_quantity == 3) {
                f[0] = intent.getDoubleExtra("freq1", NULL);
                f[1] = intent.getDoubleExtra("freq2", NULL);
                f[2] = intent.getDoubleExtra("freq3", NULL);
                a[0] = intent.getDoubleExtra("ampl1", NULL);
                a[1] = intent.getDoubleExtra("ampl2", NULL);
                a[2] = intent.getDoubleExtra("ampl3", NULL);
                o[0] = intent.getDoubleExtra("offset1", NULL);
                o[1] = intent.getDoubleExtra("offset2", NULL);
                o[2] = intent.getDoubleExtra("offset3", NULL);
                type_s[0] = intent.getIntExtra("type_signal1", NULL);
                type_s[1] = intent.getIntExtra("type_signal2", NULL);
                type_s[2] = intent.getIntExtra("type_signal3", NULL);
                type_filter = intent.getIntExtra("type_filter", NULL);
                from_freq = intent.getDoubleExtra("from_frequency", NULL);
                before_freq = intent.getDoubleExtra("before_frequency", NULL);
                operation = intent.getIntExtra("operation", NULL);
            }

        }
        setContentView(R.layout.plots);

    }
}
