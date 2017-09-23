package com.example.tushar.pgi.Adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tushar.pgi.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> patientName;
    private List<String> buildingNumber;
    private List<String> floorNumber;
    private List<String> roomNumber;
    private List<String> bedNumber;
    private List<String> patientTime;
    private List<Integer> patientImage;
    private HashMap<String, List<String>> patientDetails;

    public ExpandableListAdapter(Context context, List<String> patientName, HashMap<String, List<String>> patientDetails,
                                 List<String> buildingNumber, List<String> floorNumber, List<String> roomNumber,
                                 List<String> bedNumber, List<String> patientTime, List<Integer> patientImage) {
        this._context = context;
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
        return this.patientDetails.get(this.patientName.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.patient_expanded_desc, null);
        }

        TextView patientDesc = (TextView) convertView.findViewById(R.id.text_patient_details);
        ImageView patientImage = (ImageView) convertView.findViewById(R.id.imageview_patient);
        Button taskCompleted = (Button) convertView.findViewById(R.id.button_done);
        Button taskNotCompleted = (Button) convertView.findViewById(R.id.button_not_done);
        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "child clicked", Toast.LENGTH_SHORT).show();
            }
        });
        patientDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "child clicked", Toast.LENGTH_SHORT).show();
            }
        });
        taskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "done", Toast.LENGTH_SHORT).show();
            }
        });
        taskNotCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "not done", Toast.LENGTH_SHORT).show();
            }
        });

        patientDesc.setText(childText);
        patientImage.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.patientDetails.get(this.patientName.get(groupPosition))
                .size();
    }

    public Object getGroup(int groupPosition, String arg) {
        if (arg.equals("name")) {
            return this.patientName.get(groupPosition);
        } else if (arg.equals("building")) {
            return this.buildingNumber.get(groupPosition);
        } else if (arg.equals("floor")) {
            return this.floorNumber.get(groupPosition);
        } else if (arg.equals("room")) {
            return this.roomNumber.get(groupPosition);
        } else if (arg.equals("bed")) {
            return this.bedNumber.get(groupPosition);
        } else if (arg.equals("time")) {
            return this.patientTime.get(groupPosition);
        } else {
            return this.patientName.get(groupPosition);
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.patientName.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.patientName.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String patientNm = (String) getGroup(groupPosition, "name");
        String buildingNum = (String) getGroup(groupPosition, "building");
        String floorNum = (String) getGroup(groupPosition, "floor");
        String roomNum = (String) getGroup(groupPosition, "room");
        String bedNum = (String) getGroup(groupPosition, "bed");
        String patientTm = (String) getGroup(groupPosition, "time");
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

        patientName.setText(patientNm);
        buildingNumber.setText(buildingNum);
        floorNumber.setText(floorNum);
        roomNumber.setText(roomNum);
        bedNumber.setText(bedNum);
        patientTime.setText(patientTm);

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