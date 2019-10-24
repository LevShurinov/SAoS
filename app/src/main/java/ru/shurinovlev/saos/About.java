package ru.shurinovlev.saos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);
        text = (TextView) findViewById(R.id.text_about);

        text.setText("Данное приложение предназначено для студентов инженерных специальностей.\n" +
                "Программа позволяет построить графики сигналов вместе с их спектром,\n" +
                " применять к ним различные виды фильтров.\n" +
                "Для алгоритма быстрого преобразования Фурье используется 8192 отсчета.\n" +
                "Временной интервал сигналов 20 мс. Частота дискретизации 409,6 кГц.");




    }
}
