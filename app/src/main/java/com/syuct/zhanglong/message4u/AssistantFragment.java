package com.syuct.zhanglong.message4u;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.DESedeKeySpec;


public class AssistantFragment extends Fragment {

    private List<String> list = new ArrayList<String>();
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private int selectType;
    TextView result =   null;
    EditText editer =   null;
    private View view;
	@Override
	public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
			                 Bundle savedInstanceState){
         view=inflater.inflate(R.layout.assistant_fragment, null);

        return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

        Button button=(Button)view.findViewById(R.id.search_tel);
        editer=(EditText)view.findViewById(R.id.edit_tel);
        result=(TextView)view.findViewById(R.id.search_result);
        spinner=(Spinner)view.findViewById(R.id.select_type);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (selectType) {
                    case -1:
                        Toast t = Toast.makeText(getActivity().getApplicationContext(),
                                                "请选择一项功能",
                                                Toast.LENGTH_SHORT);
                        t.show();
                        break;
                    case 0:
                        Toast t2 = Toast.makeText(getActivity().getApplicationContext(),
                                                  "选择天气",
                                                  Toast.LENGTH_SHORT);
                        t2.show();
                        break;
                    case 1:
                        Toast t3 = Toast.makeText(getActivity().getApplicationContext(),
                                                  "查看来电归属地",
                                                  Toast.LENGTH_SHORT);
                        t3.show();
                        break;
                    default:
                        break;
                }


                result.append(editer.getText());
                result.setVisibility(View.VISIBLE);
            }
        });


        list.add("天气");
        list.add("来电归属地");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectType=arg2;//0,1,2
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                selectType=-1;
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        spinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        spinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {

                }
            }
        });
	}
	
}
