package estadisticastabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.lacus.EstadisticasPrueba2;
import com.example.mac.lacus.MainActivity;
import com.example.mac.lacus.MapsActivity;
import com.example.mac.lacus.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by mac on 27/11/17.
 */

public class EstadisticasCategorias extends Fragment{

    PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.estadisticas_fragment, container, false);


        //  getActivity().setContentView(R.layout.fragment_festadisticas);

        pieChart = (PieChart) view.findViewById(R.id.categoria_grafica);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 5, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(31f);

        final ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(25.3f, "Cultura"));
        yValues.add(new PieEntry(34.7f, "Señalización"));
        yValues.add(new PieEntry(15f, "Seguridad"));
        yValues.add(new PieEntry(25f, "Infraestructura"));

        Description description = new Description();
        description.setText("Porcentaje de reportes dividido por categorías");
        description.setTextSize(12);
        pieChart.setDescription(description);


        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Categorías");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(15f);
        dataSet.setColors(Color.rgb(139, 195, 74));

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                        Intent intent = new Intent(getActivity(), EstadisticasPrueba2.class);
                        startActivity(intent);                    }

            @Override
            public void onNothingSelected() {

            }
        });

        return view;



    }
}
