package com.example.android.proje;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class SurveyAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private Context context=null;
    private  int layoutResourceId;
    private Questions query=null;
    private List<Questions> questionses=null;

                        //Contexde olabilir
    public SurveyAdapter(Activity activity,int layoutResourceId, List<Questions> questionses)
    {
        this.layoutResourceId=layoutResourceId;
        this.mInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=activity;
        this.questionses = questionses;
    }

    @Override
    public int getCount() {
        return this.questionses.size();
    }

    @Override
    public Questions getItem(int position) {
        return this.questionses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewObjectsList view_objects;
        View row_View=null;

        view_objects=new ViewObjectsList();

        row_View = this.mInflater.inflate(R.layout.listitem, parent,false);

        view_objects.txtTitle = (TextView) row_View.findViewById(R.id.heading);
        view_objects.group = (RadioGroup) row_View.findViewById(R.id.radio_group1);

        query = questionses.get(position);//Get question from arraylist

        view_objects.txtTitle.setText(query.getNumber()+" "+query.getQuestion());
        
        addRadioButtons(5, view_objects.group);

      
        view_objects.objeadi=query.getId();
        row_View.setTag(view_objects);
        return row_View;
    }

    public void addRadioButtons(int number, LinearLayout group) {


            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            RadioGroup tempgroup=new RadioGroup(context);

            tempgroup.setId(query.getNumber());

            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(context);

                rdbtn.setId(query.getNumber() * 10 + (i));
                //this id will using  which question and whick point marked
                if(query.getResult()==i)
                {
                    rdbtn.setChecked(true);
                }
                tempgroup.addView(rdbtn);
                rdbtn.setText(" " + i);

            }
            tempgroup.setOnCheckedChangeListener(mylistiner);
            ll.addView(tempgroup);
             RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    0, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.setMargins(40, 40, 40, 40);
            group.addView(ll);

    }
    
    private final RadioGroup.OnCheckedChangeListener mylistiner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
    
            View radioButton = group.findViewById(checkedId);
            Log.v("myoutput", String.valueOf(group.getId()));

            Log.v("myoutput", String.valueOf(radioButton.getId()));


    

            RadioButton rb = (RadioButton) radioButton;
            Log.v("myoutput", rb.toString() + "");
            int position=group.getId();

            position--;

            int choose=rb.getId()%(10);

            //Log.v("myoutputconstant", constant + "");
            Log.v("myoutputconstant", choose + "");
            questionses.get(position).setResult(choose);
    
            //update question's results

        }
    };



    static class ViewObjectsList {

        String objeadi=null;
        TextView txtTitle;
        LinearLayout group;
        int position;
    }
}

