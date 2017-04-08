package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * A simple {@link Fragment} subclass.
 */

public class SectionPersonal extends Fragment {
    private RecyclerView mPersonalList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBpersonal, mDBpersonalUser, mDatabase;

    public SectionPersonal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_personal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBpersonal = mDB.getReference().child("personal");
        mDBpersonalUser = mDBpersonal.child(mAuth.getCurrentUser().getUid());
        mDatabase = mDB.getReference().child("personal").child(mAuth.getCurrentUser().getUid());

        mPersonalList = (RecyclerView) getView().findViewById(R.id.recyclerViewPersonal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mPersonalList.setLayoutManager(layoutManager);
        mPersonalList.setHasFixedSize(true);

        FloatingActionButton fabs = (FloatingActionButton) getView().findViewById(R.id.fabpersonal);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zu = new Intent(getActivity(), PersonalActivity.class);
                startActivity(zu);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Personal, PersonalViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Personal, PersonalViewHolder>(
                Personal.class,
                R.layout.row_personal,
                PersonalViewHolder.class,
                mDBpersonalUser
        ) {
            @Override
            protected void populateViewHolder(PersonalViewHolder viewHolder, Personal model, final int position) {
                final String note_key = getRef(position).getKey();

                viewHolder.setActivity(model.getActivity());
                viewHolder.setDue(model.getDue());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setNote(model.getNote());

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

                viewHolder.ibWes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(note_key).removeValue();
                    }
                });
            }
        };

        mPersonalList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class PersonalViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageButton ibWes;

        public PersonalViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ibWes = (ImageButton) mView.findViewById(R.id.imageButtonWes);
        }

        public void setActivity(String activity) {
            TextView personalAct = (TextView) mView.findViewById(R.id.textViewActivity);
            personalAct.setText(activity);
        }

        public void setDue(String due) {
            TextView personalCat = (TextView) mView.findViewById(R.id.textViewCategoryPer);
            personalCat.setText(due);
        }

        public void setCategory(String category) {
            TextView personalCat = (TextView) mView.findViewById(R.id.textViewCategoryPer);
            personalCat.setText(category);
        }

        public void setNote(String note) {
            TextView personalNote = (TextView) mView.findViewById(R.id.textViewNotePers);
            personalNote.setText(note);
        }
    }
}
