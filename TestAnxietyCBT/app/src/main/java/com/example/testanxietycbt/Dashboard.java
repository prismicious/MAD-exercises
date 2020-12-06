package com.example.testanxietycbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    int mExpandedPosition = -1;
    private RecyclerView itemList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");
    private View details;

    private FirebaseRecyclerOptions<Task> options =
            new FirebaseRecyclerOptions.Builder<Task>()
                    .setQuery(ref, Task.class)
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        itemList = findViewById(R.id.item_list);
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        details = findViewById(R.id.expandedCard);
        super.onStart();
        FirebaseRecyclerAdapter<Task, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Task, MyViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Task model) {

                    final boolean isExpanded = position==mExpandedPosition;
                    holder.expandableCard.setVisibility(isExpanded?View.VISIBLE:View.GONE);
                    holder.itemView.setActivated(isExpanded);
                    holder.setTaskTitle(model.TaskName);
                    holder.setMainText(model.TimeTaskCompleted);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mExpandedPosition = isExpanded ? -1:position;
                            notifyItemChanged(position);
                        }
                    });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_single, parent, false);
                return new MyViewHolder(view);
            }
        };
        itemList.setAdapter(adapter);
        adapter.startListening();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        CardView expandableCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            expandableCard = mView.findViewById(R.id.expandedCard);


        }

        public void setTaskTitle(String title)
        {
            TextView cardTitle = mView.findViewById(R.id.cardInput1);
            TextView expandedCardTitle = mView.findViewById(R.id.cardInput3);
            cardTitle.setText(title);
            expandedCardTitle.setText(title);
        }

        public void setMainText(String mainText){
            TextView mainTextOnCard = mView.findViewById(R.id.cardInput2);
            mainTextOnCard.setText("Completed on: " + mainText);

        }

        public void setAnswers(String input1, String input2, String input3, String input4){

        }

    }


}
