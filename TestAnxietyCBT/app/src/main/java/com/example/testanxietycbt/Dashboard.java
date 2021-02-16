package com.example.testanxietycbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    int mExpandedPosition = -1;
    private RecyclerView itemList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");

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
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation2);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        //case R.id.nav_add:
                        //    startActivity(new Intent(Dashboard.this, MainActivity.class));

                        case R.id.nav_test:
                            startActivity(new Intent(Dashboard.this, Dashboard.class));
                            //  selectedFragment = new AssignFragment();
                            break;
                        case R.id.nav_test2:
                            startActivity(new Intent(Dashboard.this, homeActivity.class));
                           // selectedFragment = new HomeFragment();
                            break;

                        default:
                          //  selectedFragment = new DecatastrophizingFragment();
                    }

                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Task, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Task, MyViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Task model) {

                    final boolean isExpanded = position==mExpandedPosition;

                    if(model.TaskName.equals("Decatastrophizing Task"))
                    {
                        String question1, question2, question3, question4;
                        question1 = getResources().getString(R.string.decata1);
                        question2 = getResources().getString(R.string.decata2);
                        question3 = getResources().getString(R.string.decata3);
                        question4 = getResources().getString(R.string.decata4);
                        holder.setQuestion(question1,question2,question3,question4);
                    }

                    if(model.TaskName.equals("Prediction Activity"))
                    {
                        String predictionInput1, predictionInput2, predictionInput3, predictionInput4;
                                predictionInput1 = "Event";
                                predictionInput2 = "Expectation";
                                predictionInput3 = "Time";
                                predictionInput4 = "Reminder";
                                holder.setQuestion(predictionInput1, predictionInput2, predictionInput3, predictionInput4);
                    }

                    holder.expandableCard.setVisibility(isExpanded?View.VISIBLE:View.GONE);
                    holder.itemView.setActivated(isExpanded);
                    holder.setTaskTitle(model.TaskName);
                    holder.setMainText(model.TimeTaskCompleted);
                    holder.setAnswers(model.input1, model.input2, model.input3, model.input4);

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
            TextView expandedCardTitle = mView.findViewById(R.id.expandedCardTitle);
            cardTitle.setText(title);
            expandedCardTitle.setText(title);
        }

        public void setMainText(String mainText){
            TextView mainTextOnCard = mView.findViewById(R.id.cardInput2);
            mainTextOnCard.setText("Completed on: " + mainText);

        }

        public void setAnswers(String input1, String input2, String input3, String input4){
            TextView answer1, answer2, answer3, answer4;
            answer1 = mView.findViewById(R.id.expandedCardAnswer1);
            answer2 = mView.findViewById(R.id.expandedCardAnswer2);
            answer3 = mView.findViewById(R.id.expandedCardAnswer3);
            answer4 = mView.findViewById(R.id.expandedCardAnswer4);
            answer1.setText(input1);
            answer2.setText(input2);
            answer3.setText(input3);
            answer4.setText(input4);

        }

        public void setQuestion(String question1, String question2, String question3, String question4){
            TextView question1_text, question2_text, question3_text, question4_text;
            question1_text = mView.findViewById(R.id.expandedCardQuestion1);
            question2_text = mView.findViewById(R.id.expandedCardQuestion2);
            question3_text = mView.findViewById(R.id.expandedCardQuestion3);
            question4_text = mView.findViewById(R.id.expandedCardQuestion4);
            question1_text.setText(question1);
            question2_text.setText(question2);
            question3_text.setText(question3);
            question4_text.setText(question4);

        }
    }


}
