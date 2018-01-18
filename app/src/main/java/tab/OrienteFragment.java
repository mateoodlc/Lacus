package tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.lacus.CasoVeeduria;
import com.example.mac.lacus.MyOnClickListener;
import com.example.mac.lacus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.southernbox.parallaxrecyclerview.ParallaxRecyclerView;

import java.util.ArrayList;

import adapter.ListAdapter;

/**
 * Created by mac on 18/11/17.
 */

public class OrienteFragment extends Fragment{
    ParallaxRecyclerView recyclerView;
    View v;
//    private List<Order> dataset = new ArrayList<>();

    // ArrayList que almacena todas las denuncias hechas en el mapa.
    private ArrayList<CasoVeeduria> casosVeeduria;

    /**
     * BASE DE DATOS.
     */

    private FirebaseDatabase database;

    public View.OnClickListener mOnClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.oriente_fragment, container, false);
        recyclerView = (ParallaxRecyclerView) view.findViewById(R.id.oriente_my_recycler_view);

        // Inicialización del ArrayList de las rutas totales del mapa.
        casosVeeduria = new ArrayList<CasoVeeduria>();

        // Obtener datos.

        database = FirebaseDatabase.getInstance();

        final DatabaseReference temporalRef = database.getReference("veeduria");

        temporalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("NORTE", "Número de casos: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot veeduriaSnapshot: dataSnapshot.getChildren()) {

                    CasoVeeduria caso = veeduriaSnapshot.getValue(CasoVeeduria.class);
                    caso.setId(veeduriaSnapshot.getKey());

                    if(caso.getZona().equals("oriente")) {

                        casosVeeduria.add(caso);

                    }

                    //casosVeeduria.add(caso);

                }

                // Inicialización del adaptador.
                mOnClickListener = new MyOnClickListener(getContext(), recyclerView, casosVeeduria);

                ListAdapter listAdapter = new ListAdapter(getContext(), casosVeeduria, mOnClickListener);
                recyclerView.setAdapter(listAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
        loaddata();
    }

    private void loaddata(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("username");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void init(){
        //   recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
