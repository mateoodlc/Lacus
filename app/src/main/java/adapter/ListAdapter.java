package adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.lacus.CasoVeeduria;
import com.example.mac.lacus.Denuncia;
import com.example.mac.lacus.GestionInformacion;
import com.example.mac.lacus.MainActivity;
import com.example.mac.lacus.MapsActivity;
import com.example.mac.lacus.Marcador;
import com.example.mac.lacus.MyOnClickListener;
import com.example.mac.lacus.R;
import com.example.mac.lacus.SeleccionarProblema;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import lists.OurData;

/**
 * Created by mac on 20/11/17.
 */

public class ListAdapter extends RecyclerView.Adapter {

    private Context context;

    // ArrayList que almacena todas las denuncias hechas en el mapa.
    private ArrayList<CasoVeeduria> casosVeeduria;

    private String casoKey;

    private View.OnClickListener mOnClickListener;

    public ListAdapter(Context context, ArrayList<CasoVeeduria> casosVeeduria, View.OnClickListener mOnClickListener){

        this.context = context;

        // Inicialización del ArrayList de las rutas totales del mapa.
        this.casosVeeduria = casosVeeduria;

        this.mOnClickListener = mOnClickListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {

        //return OurData.title.length;

        Log.d("ADAPTER", String.valueOf(casosVeeduria.size()));

        return casosVeeduria.size();

    }

    /*implements View.OnClickListener*/
    private class ListViewHolder extends RecyclerView.ViewHolder{

        private TextView mItemTitle;
        private TextView mItemText;
        private TextView mItemText2;
        private ImageView mItemImage;

        public ListViewHolder (View itemView){

            super(itemView);
            mItemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            mItemText = (TextView) itemView.findViewById(R.id.itemText);
            mItemText2 = (TextView) itemView.findViewById(R.id.itemText2);
            mItemImage = (ImageView) itemView.findViewById(R.id.itemImage);

            //itemView.setOnClickListener(this);

        }

        public void bindView (int position){

            /*mItemTitle.setText(OurData.title[position]);
            mItemText.setText(OurData.contentT[position]);
            mItemText2.setText(OurData.estado[position]);
            mItemImage.setImageResource(OurData.iconPath[position]);*/

            //Log.d("ADAPTER", casosVeeduria.get(position).getId());

            mItemTitle.setText(casosVeeduria.get(position).getNombre());
            mItemText.setText(casosVeeduria.get(position).getTarea());
            mItemText2.setText(casosVeeduria.get(position).getEstado());

            switch(casosVeeduria.get(position).getCategoria()) {

                case "Infraestructura":

                    mItemImage.setImageResource(R.drawable.ic_infraestructura);

                    break;

                case "Señalización":

                    mItemImage.setImageResource(R.drawable.ic_senales);

                    break;

                case "Cultura":

                    mItemImage.setImageResource(R.drawable.ic_culture);

                    break;

                case "Seguridad":

                    mItemImage.setImageResource(R.drawable.ic_seguridad);

                    break;

            }

            casoKey = casosVeeduria.get(position).getId();

        }

        /*public void onClick(View view){

            Intent intent = new Intent(context, GestionInformacion.class);

            intent.putExtra("ID_CASO", casoKey);

            context.startActivity(intent);
            //context.finish();
        }*/



    }
}
