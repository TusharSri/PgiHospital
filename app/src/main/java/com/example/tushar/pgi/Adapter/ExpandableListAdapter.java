package com.example.tushar.pgi.Adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;
import com.example.tushar.pgi.view.PatientPrescription;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final List<Appointment> appointments;
    private Context _context;
    private List<String> patientName;
    private List<String> buildingNumber;
    private List<String> floorNumber;
    private List<String> roomNumber;
    private List<String> bedNumber;
    private List<String> patientTime;
    private List<Integer> patientImage;
    private HashMap<String, List<String>> patientDetails;

    public ExpandableListAdapter(Context context,List<Appointment>  appointments) {
        this._context = context;
        this.appointments=appointments;
        this.patientName = patientName;
        this.patientDetails = patientDetails;
        this.buildingNumber = buildingNumber;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.patientTime = patientTime;
        this.patientImage = patientImage;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return appointments.get(groupPosition).getDescription();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.patient_expanded_desc, null);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientdetail = new Intent(_context, PatientPrescription.class);
                patientdetail.putExtra("patient_data",appointments.get(groupPosition));
                _context.startActivity(patientdetail);

            }
        });

        TextView patientDesc = (TextView) convertView.findViewById(R.id.text_patient_details);
        ImageView patientImage = (ImageView) convertView.findViewById(R.id.imageview_patient);

//        Button taskCompleted = (Button) convertView.findViewById(R.id.button_done);
//        Button taskNotCompleted = (Button) convertView.findViewById(R.id.button_not_done);
//        taskCompleted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(_context, "done", Toast.LENGTH_SHORT).show();
//            }
//        });
//        taskNotCompleted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(_context, "not done", Toast.LENGTH_SHORT).show();
//            }
//        });

        patientDesc.setText(appointments.get(groupPosition).getDescription());
        patientImage.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return appointments.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return appointments.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.patient_details_row, null);
        }

        TextView patientName = (TextView) convertView.findViewById(R.id.text_patient_name);
        TextView buildingNumber = (TextView) convertView.findViewById(R.id.text_buiding_number);
        TextView floorNumber = (TextView) convertView.findViewById(R.id.text_floor_number);
        TextView roomNumber = (TextView) convertView.findViewById(R.id.text_room_number);
        TextView bedNumber = (TextView) convertView.findViewById(R.id.text_bed_number);
        TextView patientTime = (TextView) convertView.findViewById(R.id.text_timing);

        patientName.setText(appointments.get(groupPosition).getName());
        buildingNumber.setText(appointments.get(groupPosition).getBuildingNumber());
        floorNumber.setText(appointments.get(groupPosition).getFloorNumber());
        roomNumber.setText(appointments.get(groupPosition).getRoomNumber());
        bedNumber.setText(appointments.get(groupPosition).getBedNumber());
        patientTime.setText(appointments.get(groupPosition).getTimeSlot());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}