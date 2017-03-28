package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.R;
import id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model.Scheme;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder> {
    ArrayList<Scheme> SchemeList;

    ISchemeAdapter mISchemeAdapter;

    public SchemeAdapter(Context context, ArrayList<Scheme> schemeList) {
        this.SchemeList = schemeList;
        mISchemeAdapter = (ISchemeAdapter) context;
    }

    @Override
    public SchemeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SchemeAdapter.ViewHolder holder, int position) {
        Scheme scheme = SchemeList.get(position);
        holder.activity.setText(scheme.activity);
        holder.priority.setText(scheme.priority);
        holder.due.setText(scheme.due);
        holder.note.setText(scheme.note);
        holder.category.setText(scheme.category);

    }

    @Override
    public int getItemCount() {
        if (SchemeList != null)
            return SchemeList.size();
        return 0;
    }

    public interface ISchemeAdapter {
        void doClick(int pos);

        void doEdit(int pos);

        void doDelete(int pos);

        void doFav(int pos);

        void doShare(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView activity;
        TextView due;
        TextView priority;
        TextView category;
        TextView note;
        ImageButton bEdit;
        ImageButton bDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            activity = (TextView) itemView.findViewById(R.id.activity);
            due = (TextView) itemView.findViewById(R.id.due);
            priority = (TextView) itemView.findViewById(R.id.priority);
            category = (TextView) itemView.findViewById(R.id.category);
            note = (TextView) itemView.findViewById(R.id.note);
            bEdit = (ImageButton) itemView.findViewById(R.id.bEdit);
            bDelete = (ImageButton) itemView.findViewById(R.id.bDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mISchemeAdapter.doClick(getAdapterPosition());
                }
            });

            bEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mISchemeAdapter.doEdit(getAdapterPosition());
                }
            });

            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mISchemeAdapter.doDelete(getAdapterPosition());
                }
            });


        }
    }
}

