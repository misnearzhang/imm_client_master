package com.syuct.zhanglong.message4u;

import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UsercenterFragment extends Fragment {


	private View view;
	private TextView textView;
	private Button button;
	private EditText editText;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.usercenter_fragment, null);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		textView = (TextView) view.findViewById(R.id.textv);
		button = (Button) view.findViewById(R.id.test);

		editText = new EditText(getActivity().getApplicationContext());

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LinearLayout edtor = (LinearLayout) view.findViewById(R.id.edtor);
				LinearLayout view1 = (LinearLayout) view.findViewById(R.id.view1);
				view1.setVisibility(View.GONE);
				edtor.setVisibility(View.VISIBLE);
			}
		});

	}
	
}
