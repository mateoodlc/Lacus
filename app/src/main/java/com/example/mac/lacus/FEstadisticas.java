package com.example.mac.lacus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FEstadisticas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FEstadisticas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FEstadisticas extends Fragment {

    PieChart pieChart;
    BarChart barChart;
    PieChart pieChartEdades;
    PieChart pieChartprofesiones;
    PieChart pieChartGenero;


    //flotantes de categorias
    float senalizacion;
    float cultura;
    float infraestructura;
    float seguridad;

    //enteros de problematicas más presentadas
    int problematica1;
    int problematica2;
    int problematica3;
    int problematica4;

    //enteros de edades
    int edad1;
    int edad2;
    int edad3;
    int edad4;
    int edad5;

    //enteros de profesiones
    int estudiantes;
    int empleados;
    int independientes;
    int pensionados;

    //enteros de profesiones
    int mujeres;
    int hombres;
    int otros;


    TextView totalCategorias;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat_Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()

        );
    }

    public FEstadisticas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_festadisticas, container, false);

        //Creo torta de gráficos para categorías

        totalCategorias = (TextView) view.findViewById(R.id.total);

        pieChart = (PieChart) view.findViewById(R.id.categoria_grafica);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 5, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(31f);


        //cambiar estas variables desde la base de datos
        senalizacion = 10;
        cultura = 20;
        infraestructura = 30;
        seguridad = 50;

        float total = senalizacion + cultura + infraestructura + seguridad;

        final ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry((senalizacion / total) * 100, "Señalización"));
        yValues.add(new PieEntry((cultura/ total) * 100, "Cultura"));
        yValues.add(new PieEntry((seguridad/ total) * 100, "Seguridad"));
        yValues.add(new PieEntry((infraestructura/ total) * 100, "Infraestructura"));

    /*    Description description = new Description();
        description.setText("Porcentaje de reportes dividido por categorías");
        description.setTextSize(12);
        pieChart.setDescription(description);
*/

        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Categorías");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(15f);
        dataSet.setColors(Color.rgb(139, 195, 74));
        dataSet.setValueFormatter(new PercentFormatter());


        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        //Creo barras de gráficos para problemáticas más presentadas por categorías
        barChart = (BarChart) view.findViewById(R.id.problematica_grafica);
        //Seteo opciones
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(235);
        barChart.setFitBars(true);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(true);


        ///////////////////////////////////////////////////////////////////////////


        //reemplazar estos valores con los de la base de datos
        problematica1 = 28;
        problematica2 = 14;
        problematica3 = 37;
        problematica4 = 12;


        //Creo arraylist de barras de gráficos para problemáticas más presentadas por categorías
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, problematica1));
        barEntries.add(new BarEntry(2, problematica2));
        barEntries.add(new BarEntry(3, problematica3));
        barEntries.add(new BarEntry(4, problematica4));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Problemáticas");
        barDataSet.setColors(Color.rgb(139, 195, 74));
        barDataSet.setHighLightColor(Color.rgb(164, 215, 94));

        BarData barData = new BarData(barDataSet);

        float groupSpace = 0.1f;
        float barSpace = 0.02f;
        float barWidth = 0.91f;

        barChart.setData(barData);

        barData.setBarWidth(0.9f);
      //  barChart.groupBars(1, groupSpace, barSpace);

        String [] problematicas = new String[] {"", "Vías de alto riesgo", "Peatones poco cívicos", "Huecos", "Locación hostil"};
        XAxis xAxis = barChart.getXAxis();
       // System.out.println("PROBLEMATICAS"+problematicas.length);
        xAxis.setValueFormatter(new MyXAsisValueFormatter(problematicas));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setGranularity(1);
        //xAxis.setGranularityEnabled(true);
        //xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0);
        //xAxis.setAxisMaximum(4);
        xAxis.setTextSize(7f); // set the text size
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        //barChart.animateY(8000);
        barChart.animateY(10001, Easing.EasingOption.EaseInOutBack);
        barChart.setAutoScaleMinMaxEnabled(true);


       /* Description descriptionProblemas = new Description();
        descriptionProblemas.setText("Problemas con mayor denuncias de cada categoría");
        descriptionProblemas.setTextSize(12);
        descriptionProblemas.setPosition(1000, 100);
        barChart.setDescription(descriptionProblemas);
**/

        // xAxis.setAxisMaximum(barChart.getYMax());
        //termino de crear barra de datos


        ///////////////////////////////////////////////////////////////////////////


        //reemplazar estos datos con los de la base de datos
        edad1 = 20;
        edad2 = 30;
        edad3 = 30;
        edad4 = 50;
        edad5 = 50;

        float totalEdad = edad1 + edad2 + edad3 + edad4 + edad5;

        //creo media torta de datos para las edades
        pieChartEdades = (PieChart) view.findViewById(R.id.edades_grafica);
        pieChartEdades.setBackgroundColor(Color.WHITE);
        pieChartEdades.getDescription().setEnabled(false);
        pieChartEdades.setMaxAngle(180);
        pieChartEdades.setRotationAngle(180);
        pieChartEdades.setCenterTextOffset(0, -20);
        ArrayList<PieEntry> pieEntriesEdades = new ArrayList<>();
        pieEntriesEdades.add(new PieEntry((edad1 / totalEdad) * 100, "15-25"));
        pieEntriesEdades.add(new PieEntry((edad2 / totalEdad) * 100, "25 - 35"));
        pieEntriesEdades.add(new PieEntry((edad3 / totalEdad) * 100, "35 - 45"));
        pieEntriesEdades.add(new PieEntry((edad4 / totalEdad) * 100, "45 - 55"));
        pieEntriesEdades.add(new PieEntry((edad5 / totalEdad) * 100, "60+"));
        moveOffScreen();

        PieDataSet dataEdadesSet = new PieDataSet(pieEntriesEdades, "Edades");
        dataEdadesSet.setSelectionShift(5f);
        dataEdadesSet.setSliceSpace(1f);
        dataEdadesSet.setColor(Color.rgb(139, 195, 74));
        dataEdadesSet.setValueTextSize(10f);
        dataEdadesSet.setValueTextColor(Color.YELLOW);
        dataEdadesSet.setValueFormatter(new PercentFormatter());

        PieData dataEdades = new PieData(dataEdadesSet);

        pieChartEdades.setData(dataEdades);
        pieChartEdades.invalidate();
        //Se cierra la gráfica de edades


///////////////////////////////////////////////////////////////////////////


        //reemplazar estos datos con los de la base de datos
        estudiantes = 20;
        empleados = 30;
        independientes = 30;
        pensionados = 50;

        float totalProfesiones = estudiantes + empleados + independientes + pensionados;

        //Creo gráfica de profesiones
        pieChartprofesiones = (PieChart) view.findViewById(R.id.profesiones_grafica);

        pieChartprofesiones.setUsePercentValues(true);
        pieChartprofesiones.getDescription().setEnabled(false);
        pieChartprofesiones.setExtraOffsets(5, 5, 5, 5);

        pieChartprofesiones.setDragDecelerationFrictionCoef(0.95f);

        pieChartprofesiones.setDrawHoleEnabled(true);
        pieChartprofesiones.setHoleRadius(20f);
        pieChartprofesiones.setHoleColor(Color.WHITE);
        pieChartprofesiones.setTransparentCircleRadius(31f);

        final ArrayList<PieEntry> profesiones = new ArrayList<>();

        profesiones.add(new PieEntry((estudiantes / totalProfesiones) * 100, "Estudiantes"));
        profesiones.add(new PieEntry((empleados / totalProfesiones) * 100, "Empleados"));
        profesiones.add(new PieEntry((independientes / totalProfesiones) * 100, "Independientes"));
        profesiones.add(new PieEntry((pensionados / totalProfesiones) * 100, "Pensionados"));

    /*    Description description = new Description();
        description.setText("Porcentaje de reportes dividido por categorías");
        description.setTextSize(12);
        pieChart.setDescription(description);
*/

        //pieChartprofesiones.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSetProfesiones = new PieDataSet(profesiones, "Profesiones");
        dataSetProfesiones.setSliceSpace(1f);
        dataSetProfesiones.setSelectionShift(15f);
        dataSetProfesiones.setColors(Color.rgb(139, 195, 74));
        dataSetProfesiones.setValueFormatter(new PercentFormatter());


        PieData dataProfesiones = new PieData((dataSetProfesiones));
        dataProfesiones.setValueTextSize(10f);
        dataProfesiones.setValueTextColor(Color.YELLOW);

        pieChartprofesiones.setData(dataProfesiones);



///////////////////////////////////////////////////////////////////////////


        //reemplazar estos datos con los de la base de datos
        mujeres = 37;
        hombres = 73;
        otros = 0;


        float totalGeneros = mujeres + hombres + otros;

        //Creo gráfica de género
        pieChartGenero = (PieChart) view.findViewById(R.id.genero_grafica);

        pieChartGenero.setUsePercentValues(true);
        pieChartGenero.getDescription().setEnabled(false);
        pieChartGenero.setExtraOffsets(5, 5, 5, 5);

        pieChartGenero.setDragDecelerationFrictionCoef(0.95f);

        pieChartGenero.setDrawHoleEnabled(true);
        pieChartGenero.setHoleRadius(20f);
        pieChartGenero.setHoleColor(Color.WHITE);
        pieChartGenero.setTransparentCircleRadius(31f);

        final ArrayList<PieEntry> generos = new ArrayList<>();

        generos.add(new PieEntry((mujeres / totalGeneros) * 100, "Mujeres"));
        generos.add(new PieEntry((hombres / totalGeneros) * 100, "Hombres"));
        generos.add(new PieEntry((otros / totalGeneros) * 100, "Otros"));


    /*    Description description = new Description();
        description.setText("Porcentaje de reportes dividido por categorías");
        description.setTextSize(12);
        pieChart.setDescription(description);
*/

        //pieChartprofesiones.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSetGeneros = new PieDataSet(generos, "Géneros");
        dataSetGeneros.setSliceSpace(1f);
        dataSetGeneros.setSelectionShift(15f);
        dataSetGeneros.setColors(Color.rgb(139, 195, 74));
        dataSetGeneros.setValueFormatter(new PercentFormatter());


        PieData dataGeneros = new PieData((dataSetGeneros));
        dataGeneros.setValueTextSize(10f);
        dataGeneros.setValueTextColor(Color.YELLOW);

        pieChartGenero.setData(dataGeneros);

        return view;


    }




    //creo formato de valores para las barras de gráficos para problemáticas más presentadas por categorías
    public class MyXAsisValueFormatter implements IAxisValueFormatter{
        private String[] mValues;

        public MyXAsisValueFormatter(String[] values){
            this.mValues = values;
            System.out.println("PROBLEMATICAS2" + "" + mValues.length);
        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }

    //MÉTODO PARA MEDIA TORTA
    private void moveOffScreen(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;

        int offset = (int) (height * 0.35f);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)pieChartEdades.getLayoutParams();
        params.setMargins(0,0,0, -offset);
    }
}
