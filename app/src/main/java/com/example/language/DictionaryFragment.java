package com.example.language;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.language.Model.Ranking;
import com.example.language.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DictionaryFragment extends Fragment {
    View myFragment;
    FirebaseDatabase database;
    DatabaseReference questionScore, rankingTable;
    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking, RankingViewHolder> adapter;
    int sum = 0;

    EditText editTextSearch;
    Button btnSearch;
    TextView txtResults;
    Button btnGoogle;


    public static DictionaryFragment newInstance() {
        DictionaryFragment rankingFragment = new DictionaryFragment();
        return rankingFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_dictionary, container, false);
        editTextSearch = myFragment.findViewById(R.id.editTxtSearch);
        btnSearch = myFragment.findViewById(R.id.btnSubmit);
        txtResults = myFragment.findViewById(R.id.txtViewResults);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextSearch.getText().toString())){
                    Toast.makeText(getActivity(),"No keyword",Toast.LENGTH_LONG).show();
                }
                else {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Meaning");
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String searchKeyword = editTextSearch.getText().toString();
                            if (dataSnapshot.child(editTextSearch.getText().toString()).exists()){
                                txtResults.setText(dataSnapshot.child(searchKeyword).getValue().toString());
                            }
                            else{
                                Toast.makeText(getActivity(), "No such word in dictionary", Toast.LENGTH_LONG).show();}
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        btnGoogle = myFragment.findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.cyberforum.ru/android-dev/thread2140922.html";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
//                String translationString = editTextSearch.getText().toString();
//                Http.post(translationString, "en", "ru", new JsonHttpResponseHandler(){
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//
//                        try {
//                            JSONObject serverResp = new JSONObject(response.toString());
//                            JSONObject jsonObject = serverResp.getJSONObject("data");
//                            JSONArray transObject = jsonObject.getJSONArray("translations");
//                            JSONObject transObject2 =  transObject.getJSONObject(0);
//                            txtResults.setText(transObject2.getString("translatedText"));
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//
//                });
            }

        });
        return myFragment;
    }



}
