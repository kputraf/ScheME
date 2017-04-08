package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model.Personal;
import id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model.Target;


/**
 * A simple {@link Fragment} subclass.
 */
public class SectionTarget extends Fragment {
    private RecyclerView mTargetlList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBtarget, mDBtargetUser, mDatabase;

    public SectionTarget() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_target, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBtarget = mDB.getReference().child("target");
        mDBtargetUser = mDBtarget.child(mAuth.getCurrentUser().getUid());
        mDatabase = mDB.getReference().child("target").child(mAuth.getCurrentUser().getUid());

        mTargetlList = (RecyclerView) getView().findViewById(R.id.recyclerViewTarget);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTargetlList.setLayoutManager(layoutManager);
        mTargetlList.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        MainActivity.pos = "tar";
        Log.d("POS", MainActivity.pos);

        FirebaseRecyclerAdapter<Target, TargetViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Target, TargetViewHolder>(
                Target.class,
                R.layout.row_target,
                SectionTarget.TargetViewHolder.class,
                mDBtargetUser
        ) {
            @Override
            protected void populateViewHolder(TargetViewHolder viewHolder, Target model, int position) {
                final String note_key = getRef(position).getKey();

                viewHolder.setTarget(model.getTarget());
                viewHolder.setPriority(model.getPriority());
                viewHolder.setDues(model.getDue());
                viewHolder.setNotes(model.getNote());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return;
                    }
                });

                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return false;
                    }
                });

                viewHolder.ibDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(note_key).removeValue();
                    }
                });
            }
        };

        mTargetlList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageButton ibDone;

        public TargetViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ibDone = (ImageButton) mView.findViewById(R.id.imageButtonDelete);
        }

        public void setTarget(String target) {
            TextView tvTarget = (TextView) mView.findViewById(R.id.textViewTarget);
            tvTarget.setText(target);
        }

        public void setPriority(String priority) {
            TextView priorityTarg = (TextView) mView.findViewById(R.id.textViewPriority);
            priorityTarg.setText(priority);
        }

        public void setDues(String dues) {
            TextView tvDues = (TextView) mView.findViewById(R.id.textViewDueTarg);
            tvDues.setText(dues);
        }

        public void setNotes(String notes) {
            TextView targetNote = (TextView) mView.findViewById(R.id.textViewNoteTarg);
            targetNote.setText(notes);
        }
    }
}
