package com.syuct.zhanglong.message4u;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AssistantFragment extends Fragment {

    TextView result=null;
    EditText edittel=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.assistant_fragment,null);
        Button button=(Button)view.findViewById(R.id.search_tel);
        edittel=(EditText)view.findViewById(R.id.edit_tel);
        result=(TextView)view.findViewById(R.id.search_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getActivity().getApplicationContext(), "点击生活助手的按钮", Toast.LENGTH_SHORT);
                t.show();
                //result.setText("多云");
                result.append(edittel.getText());
                result.setVisibility(View.VISIBLE);
            }
        });
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
}
