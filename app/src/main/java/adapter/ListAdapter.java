package adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.lacus.R;

import org.w3c.dom.Text;

import lists.OurData;

/**
 * Created by mac on 20/11/17.
 */

public class ListAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {



        return OurData.title.length;

    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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


            itemView.setOnClickListener(this);
        }

        public void bindView (int position){
            mItemTitle.setText(OurData.title[position]);
            mItemText.setText(OurData.contentT[position]);
            mItemText2.setText(OurData.estado[position]);
            mItemImage.setImageResource(OurData.iconPath[position]);
        }

        public void onClick(View view){

        }

    }
}