package lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.mac.lacus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by mac on 20/11/17.
 */

public class OurData {

    //holi

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference username = db.collection("usuarios");

    Context context;

    public OurData(Context context){
        this.context = context;
        addData();
    }

    public void addData(){
        Map<String, Object> user = new HashMap<>();
        user.put("contrasena", "Ada");
        user.put("email", "Lovelace");
        user.put("nombre", 1815);

        db.collection("usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Hey, funciono :D", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void retrieveData(){
        db.collection("cities")
                .whereEqualTo("capital", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




    public static String[] title = new String[]{
            "Carrera 1N # 62",
            "Av 4 Norte # 8",
            "Carrera 3N # 67",
            "Calle 5 # 7",
            "Avenida 6 # 42",
            "Calle 4 # 3",
            "Avenida del río # 44"

    };

    public static String[] contentT = new String[]{
            "Mantenimiento de asfalto",
            "Pintando señalización",
            "Mantenimiento de asfalto",
            "Cubriendo huecos",
            "Guardas de tránsito regulando",
            "Cubriendo huecos",
            "Pintando señalización"
    };

    public static String[] estado = new String[]{
            "En ejecución",
            "En planeación",
            "Finalizada",
            "En ejecución",
            "En ejecución",
            "En ejecución",
            "En planeación"

    };


    public static int[] iconPath = new int[]{
            R.drawable.ic_infraestructura,
            R.drawable.ic_senales,
            R.drawable.ic_infraestructura,
            R.drawable.ic_infraestructura,
            R.drawable.ic_seguridad,
            R.drawable.ic_infraestructura,
            R.drawable.ic_senales
    };

}
