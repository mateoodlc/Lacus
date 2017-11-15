package com.example.mac.lacus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GraciasPorContribuir extends AppCompatActivity implements View.OnClickListener {
    Button continuar;
    ImageView categoriaImg;
    TextView tipoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracias_por_contribuir);

        continuar = (Button) findViewById(R.id.continuar);
        categoriaImg = (ImageView) findViewById(R.id.categoriaImg);
        tipoCategoria = (TextView) findViewById(R.id.tipoCaso);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continuar: /** Start a new Activity MyCards.java */
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("señalizacionID", "señalizacion");
                this.startActivity(intent);
                break;
        }
    }
}
