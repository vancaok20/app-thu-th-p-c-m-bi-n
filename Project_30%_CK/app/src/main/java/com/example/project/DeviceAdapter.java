package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private Context context;
    private List<Device> list_device;


    public DeviceAdapter(List<Device> list_device) {
        this.list_device = list_device;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.led_on_off,parent,false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = list_device.get(position);
        if (device == null)
            return;
        holder.tv_led.setText(device.getName());
        holder.img_led.setImageResource(device.setImg(R.drawable.led_off));
        holder.sk_led.setVisibility(View.GONE);

        holder.itemView.setOnLongClickListener(v -> {

            int currentPosition = holder.getAdapterPosition();


            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Device?").setMessage("Are you sure you want to delete this Device?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {


                            if (currentPosition != RecyclerView.NO_POSITION) {
                                list_device.remove(currentPosition);
                                notifyDataSetChanged();

                                holder.mainActivity.saveRoomList(Room.allRooms,"List Room");
                            }
                        }
                    })

                    .setNegativeButton(android.R.string.cancel, null).setIcon(android.R.drawable.ic_menu_delete).show();
            return true;
        });

        holder.sw_led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.sw_led.isChecked()){

                    holder.img_led.setImageResource(device.setImg(R.drawable.led_on));
                    holder.sk_led.setVisibility(View.VISIBLE);
                }
                else  {
                    holder.img_led.setImageResource(device.setImg(R.drawable.led_off));
                    holder.sk_led.setVisibility(View.GONE);
                }
            }
        });

        holder.sk_led.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int alpha = (int) (progress * 2.55);
                holder.img_led.setImageAlpha(alpha);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list_device != null) {
            return list_device.size();
        }
        return 0;
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_led;
        private Switch sw_led;
        private ImageView img_led;
        private SeekBar sk_led;
        private MainActivity mainActivity;
        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_led = itemView.findViewById(R.id.txt_led);
            sw_led = itemView.findViewById(R.id.sw_led);
            img_led = itemView.findViewById(R.id.img_led);
            sk_led = itemView.findViewById(R.id.sk_led);
            mainActivity = (MainActivity) itemView.getContext();
        }
    }
}
