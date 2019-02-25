package com.fiqri.ganteng.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fiqri.ganteng.R;
import com.fiqri.ganteng.adapter.ListUserAdapter;
import com.fiqri.ganteng.model.User;
import com.fiqri.ganteng.service.TrackingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.internal.Utils.arrayOf;

public class ListUtamaUser extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 100;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    DatabaseReference myRef;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_utama_user);
        ButterKnife.bind(this);
        myToolbar.setTitle("List User");

        myRef = FirebaseDatabase.getInstance().getReference().child("user");

        fetchAllDataUser();
        setCheckPermission();
    }

    private void fetchAllDataUser() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    User user = new User();
                    User data = dataSnapshot1.getValue(User.class);

                    String name = data.getName();
                    String email = data.getEmail();
                    String id = data.getUuid();

                    user.setName(name);
                    user.setEmail(email);
                    user.setUuid(id);

                    userList.add(user);

                    /**
                     * optional show list data sort data
                     * */
//                    Collections.sort(userList, new Comparator<User>() {
//                        @Override
//                        public int compare(User o1, User o2) {
//                            return o1.getName().compareTo(o2.getName());
//                        }
//                    });

                    ListUserAdapter adapter = new ListUserAdapter(userList);
                    recyclerview.setLayoutManager(new LinearLayoutManager(ListUtamaUser.this));
                    recyclerview.setHasFixedSize(true);
                    recyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListUtamaUser.this,
                        databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCheckPermission() {

        // cek has access location
        int permission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        // if access location granted start service
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();

            // if this app dosen't have access user location and try again request pemission
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // permission granted
        if (requestCode == REQUEST_PERMISSION && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // start service gps
            startTrackerService();

        } else {
            Toast.makeText(this, getString(R.string.allow), Toast.LENGTH_SHORT).show();
        }
    }

    public void startTrackerService() {
        startService(new Intent(ListUtamaUser.this, TrackingService.class));
    }
}
