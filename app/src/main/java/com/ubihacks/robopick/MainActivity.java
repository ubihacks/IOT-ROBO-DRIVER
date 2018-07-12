package com.ubihacks.robopick;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.icons.MaterialDrawerFont;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Timer;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import info.hoang8f.android.segmented.SegmentedGroup;

public class MainActivity extends AppCompatActivity {

    Drawer result;
    Toolbar toolbar;
    Button Race,Stop,increase,decrease;
    PointerSpeedometer pointerSpeedometer;
    float i = (float) 128.0;
    String Ip;
    int Port;
    SegmentedGroup motion;
    String Direction;
    ConnectToDevice signal;
    RadioButton motionDirection;
    ImageView left,right;
    TextView CurrentStatus;
    SegmentedButtonGroup segmentedButtonGroup,segmentedButtonJar;
    Timer timerForward;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Race= (Button)findViewById(R.id.race);
        Stop = (Button) findViewById(R.id.stopDevice);
        increase = (Button) findViewById(R.id.speedIncrease);
        decrease = (Button) findViewById(R.id.speedDecrease);

        motion = (SegmentedGroup) findViewById(R.id.motionDirection);

        left = (ImageView) findViewById(R.id.leftTurn);
        right = (ImageView) findViewById(R.id.rightTurn);
        CurrentStatus = (TextView) findViewById(R.id.currentStatus);
         segmentedButtonGroup= (SegmentedButtonGroup) findViewById(R.id.segmentedButtonGroup);
         segmentedButtonJar= (SegmentedButtonGroup) findViewById(R.id.segmentedJar);



        motion.setTintColor(Color.parseColor("#26a69a"), Color.parseColor("#ffffff"));


        signal = new ConnectToDevice();
        pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.pointerSpeedometer);

        Ip = "192.168.4.1";
        Port = Integer.parseInt("4210");


        pointerSpeedometer.speedTo(0);

        segmentedButtonGroup.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                if(position==0){
                    Toast.makeText(MainActivity.this, "Pick", Toast.LENGTH_SHORT).show();
                    signal.SignalToDevice(Ip,Port,"u");
                }
                else if(position==1){
                    Toast.makeText(MainActivity.this, "Drop", Toast.LENGTH_SHORT).show();
                    signal.SignalToDevice(Ip,Port,"d");

                }
            }
        });
        segmentedButtonJar.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                if(position==0){
                    Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
                    signal.SignalToDevice(Ip,Port,"7");
                }
                else if(position==1){
                    Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                    signal.SignalToDevice(Ip,Port,"8");

                }
            }
        });
        segmentedButtonGroup.setPosition(1, 0);

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signal.SignalToDevice(Ip,Port,"5");
                i=0;
                pointerSpeedometer.speedTo(0);
                CurrentStatus.setText("Break");

            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signal.SignalToDevice(Ip,Port,"1");
                signal.SignalToDevice(Ip,Port,"6");
                pointerSpeedometer.speedTo(i=+38);

            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signal.SignalToDevice(Ip,Port,"1");
                signal.SignalToDevice(Ip,Port,"9");
                pointerSpeedometer.speedTo(i=-38);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentStatus.setText("Turn Right");
                signal.SignalToDevice(Ip,Port,"4");
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentStatus.setText("Turn Left");
                signal.SignalToDevice(Ip,Port,"3");
            }
        });


        Race.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectedId = motion.getCheckedRadioButtonId();
                    motionDirection = (RadioButton) findViewById(selectedId);
                    Direction = motionDirection.getText().toString();
                       } catch (Exception e) {
                          Direction = "Neutral";
                       }
                if (Direction.equals("Forward")){
//
                           CurrentStatus.setText("Forward");
                           signal.SignalToDevice(Ip,Port,"1");
                           pointerSpeedometer.speedTo(i);

                       }
                       else if (Direction.equals("Reverse")){
                           CurrentStatus.setText("Reverse");
                           pointerSpeedometer.speedTo(i);
                           signal.SignalToDevice(Ip,Port,"2");
                       }
                       else {
                           CurrentStatus.setText("Neutral");
                          // signal.SignalToDevice(Ip,Port,"0");
                       }
            }
        });


        loadDrawer(savedInstanceState);
    }

    private void loadDrawer(Bundle savedInstanceState) {
        //Head
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.mipmap.ic_launcher)
                .withSavedInstance(savedInstanceState)
                .build();
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        if(user != null) {
//            headerResult.addProfiles(new ProfileDrawerItem().withName(user.getDisplayName()).withEmail(user.getEmail()).withIcon(user.getPhotoUrl()));
//        }

        //Drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withFullscreen(true)
                .withSliderBackgroundColor(Color.BLACK)
                .addDrawerItems(
                       // new PrimaryDrawerItem().withName("Add").withIcon(R.drawable.add_place).withIdentifier(0),

                        new DividerDrawerItem(),
                        //new PrimaryDrawerItem().withName("Share it").withIcon(R.drawable.shareit).withIdentifier(1),
                        new SectionDrawerItem().withName("APP INFO"),
                        new SecondaryDrawerItem().withName("About").withIcon(MaterialDrawerFont.Icon.mdf_person).withIdentifier(2),
                        new DividerDrawerItem()
                        //new SwitchDrawerItem().withName("Notifications").withIcon(MaterialDrawerFont.Icon.mdf_person).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        ///new PrimaryDrawerItem().withName("Logout").withIdentifier(3)
                ).withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 0) {

                                //startActivity(new Intent(getApplicationContext(), AddPlaces.class));
                            }
                            if (drawerItem.getIdentifier() == 1) {

                                //showToast("Share");
                            }
                            else if(drawerItem.getIdentifier() == 2) {
                                AlertDialog.Builder alertBuider = new AlertDialog.Builder(MainActivity.this);
                                alertBuider.setTitle("Travel Pakistan");
                                alertBuider.setMessage("Free to  contact about queries");
                                alertBuider.setPositiveButton("Okaya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                  //      Toast.makeText(context, "911", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                alertBuider.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    //    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                alertBuider.create().show();
                                //Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();
                                //getSupportFragmentManager().beginTransaction().replace(R.id.crossfade_content, new Cricket()).commit();
                            }
                            else if(drawerItem.getIdentifier()==3){


                                //showToast("Logout");
                            }


                        }

                        return false;


                    }
                })
                .build();
    }
}
