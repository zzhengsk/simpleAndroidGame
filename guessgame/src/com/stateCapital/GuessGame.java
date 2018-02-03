package com.stateCapital;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.RadioGroup;

public class GuessGame extends Activity
{
	EditText nameInput;
	RadioGroup rg;
	Button btnStart;
	SharedPreferences name;
	Intent i;
	String choiceValue;
    @Override
    public void onCreate(Bundle b) 
    {  	
        super.onCreate(b);
        setContentView(R.layout.main);
        nameInput = (EditText)findViewById(R.id.nameInput);
        btnStart = (Button)findViewById(R.id.start);        
        rg = (RadioGroup)findViewById(R.id.rg);
        
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
          public void onCheckedChanged(RadioGroup group, int checkedId)
          {
            switch(checkedId)
            {
              case R.id.state:          	
                   btnStart.setVisibility(View.VISIBLE);
                   choiceValue = "state";
                   break;
              case R.id.capital:
            	   btnStart.setVisibility(View.VISIBLE);
            	   choiceValue = "capital";
            	   break;
            }
          }  
        });
        
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                name = GuessGame.this.getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = name.edit();
                ed.putString("name", nameInput.getText().toString());
                ed.commit();
                i = new Intent();
                i.putExtra("choice",choiceValue);
                i.setClass(GuessGame.this,guessing.class);
                startActivity(i);
            }
        });
     }  
}
          
