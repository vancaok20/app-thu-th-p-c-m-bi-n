package com.example.project.Fragment;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.project.Device;
import com.example.project.DeviceAdapter;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DeviceFragment extends Fragment {
    private MainActivity mainActivity;
    private RecyclerView rcvDevice;
    private TextView tvNameRoomDv;
    private int index;
    private Toolbar toolbar_device;
    private String NameRinDv;
    private EditText edt_remove_device;
    private DeviceAdapter deviceAdapter;
    private FloatingActionButton btnAddDv;

    public static final String TAG = DeviceFragment.class.getName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_device, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNameRoomDv = view.findViewById(R.id.tvNameRoomDv);
        btnAddDv = view.findViewById(R.id.btnAddDv);
        rcvDevice = view.findViewById(R.id.rcvDevice);
        mainActivity = (MainActivity) getContext();
        toolbar_device = view.findViewById(R.id.device_toolbar);
        edt_remove_device = view.findViewById(R.id.edt_remove_device);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvDevice.setLayoutManager(gridLayoutManager);

        setBackButtonOnToolbar();


        Bundle bundleReceiver = getArguments();
        if (bundleReceiver != null) {
            NameRinDv = bundleReceiver.getString("NameRoom","");
            index = bundleReceiver.getInt("index",0);
        }

        tvNameRoomDv.setText(NameRinDv);
        //A DeviceAdapter is created and attached to rcvDevice to display the list of devices.
        deviceAdapter = new DeviceAdapter(Room.allRooms.get(index).getListDevice());
        rcvDevice.setAdapter(deviceAdapter);

        //set click event for button Add Device
        btnAddDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTabToAddDevice();

            }
        });


    }
    private void showTabToAddDevice(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View  diaLogView = getLayoutInflater().inflate(R.layout.tab_add_device, null);
        builder.setView(diaLogView);
        builder.setTitle("Add Device");
        final EditText edt_add_Device = diaLogView.findViewById(R.id.edt_add_Device);
        builder.setPositiveButton("Add Device", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String DeviceName =  edt_add_Device.getText().toString();
                boolean exited  = false;
                for (int i = 0; i < Room.allRooms.get(index).getNumber(); i++)
                {

                    if(Room.allRooms.get(index).getListDevice().get(i).getName().equals(DeviceName)){
                        Toast.makeText(getActivity(), "This Device is exited",Toast.LENGTH_SHORT).show();
                        exited = true;
                        break;
                    }
                }
                if(!exited) {

                    Room.allRooms.get(index).addDevice(new Device(edt_add_Device.getText().toString(), false));
                    deviceAdapter.notifyDataSetChanged();
                    mainActivity.saveRoomList(Room.allRooms, "listRoom");

                }
            }
        });

        builder.setNegativeButton("Cancel" ,null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void setBackButtonOnToolbar(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar_device);;
        activity.getSupportActionBar();

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("List of Devices");
        }

        toolbar_device.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                Toast.makeText(getContext(), "Back to Home Fragment", Toast.LENGTH_SHORT).show();
            }
        });
    }

}