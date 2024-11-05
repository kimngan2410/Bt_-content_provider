package com.example.bt_content_provider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CallLogFragment extends Fragment {

    private static final int REQUEST_CALL_LOG_PERMISSION = 1;
    private RecyclerView recyclerView;
    private CallLogAdapter callLogAdapter;
    private List<CallLogEntry> callLogList;

    public CallLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCallLog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callLogList = new ArrayList<>();
        callLogAdapter = new CallLogAdapter(callLogList);
        recyclerView.setAdapter(callLogAdapter);

        // Kiểm tra quyền đọc nhật ký cuộc gọi
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOG_PERMISSION);
        } else {
            fetchCallLogs();
        }

        return view; // Trả về view sau khi thiết lập
    }

    private void fetchCallLogs() {
        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            // Lấy chỉ số cột một lần và kiểm tra
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);

            // Kiểm tra xem các chỉ số cột có hợp lệ không
            if (numberIndex == -1 || typeIndex == -1 || dateIndex == -1) {
                Toast.makeText(getContext(), "Failed to get call log data", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }

            // Định dạng ngày
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

            while (cursor.moveToNext()) {
                String number = cursor.getString(numberIndex);
                String type = cursor.getString(typeIndex);
                long dateMillis = cursor.getLong(dateIndex);
                String date = dateFormat.format(new Date(dateMillis)); // Chuyển đổi milliseconds thành định dạng ngày

                // Chuyển đổi loại cuộc gọi thành chuỗi
                String callType = "";
                switch (Integer.parseInt(type)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Cuộc gọi đến";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Cuộc gọi đi";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Bỏ lỡ gọi";

                        break;
                }

                callLogList.add(new CallLogEntry(number, callType, date)); // Thêm vào danh sách với ngày đã định dạng
            }
            cursor.close();
            callLogAdapter.notifyDataSetChanged(); // Cập nhật adapter
        } else {
            Toast.makeText(getContext(), "No call log found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_LOG_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCallLogs(); // Nếu được cấp quyền, lấy nhật ký cuộc gọi
            } else {
                Toast.makeText(getContext(), "Permission denied to read call logs", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
