//Author:Zhanhong Zheng
//Course:cs211d
//program name:guessing.java
//objective:check the answer

package com.stateCapital;
import java.io.InputStream;
import org.apache.http.util.EncodingUtils;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.view.View;

public class guessing extends Activity
{
	TextView title,ch,info;
	EditText answerInput;
	Button btnOK;
	SharedPreferences score;
	int chance = 3,point = 0,randNum;
	String question="";
	String states[],capitals[];
    storeData myDB;

	@Override
    public void onCreate(Bundle b) 
    {
        super.onCreate(b);
        setContentView(R.layout.start);
        final Intent i=getIntent();
        final String value=i.getStringExtra("choice");
        title = (TextView)findViewById(R.id.title);
        ch = (TextView)findViewById(R.id.chances);
        info = (TextView)findViewById(R.id.infomation);
        answerInput = (EditText)findViewById(R.id.answerInput);
        btnOK = (Button)findViewById(R.id.btnOK);
        
        states=getFileData(new String("states.txt")).split(",");
        capitals=getFileData(new String("capitals.txt")).split(",");  
        
        myDB = new storeData(this);
        myDB.onUpgrade(myDB.getWritableDatabase(), 1, 2);
        myDB.add(myDB.getWritableDatabase(),states,capitals);
        
        randNum =(int)(50*Math.random());
        question = setQuestion(value,randNum).toString();

    	btnOK.setOnClickListener(new View.OnClickListener()
        {	
        	public void onClick(View v)
        	{
        		String answer = answerInput.getText().toString();
        		checkAnswer(answer,value,i);
        	}
        });
    }    
	
//*************************getFileData******************************
    public String getFileData(String textfile)
    {
    	String data="";
        try
        {
        	InputStream in = getResources().getAssets().open(textfile);
        	int length = in.available();
        	byte [] buffer = new byte[length];
        	in.read(buffer); 
        	data = EncodingUtils.getString(buffer, "UTF-8"); 
        }catch(Exception e){e.printStackTrace();}
        return (data);
    }

//*************************setQuestion*******************************
    private String setQuestion(String value,int randNum)
    {
    	String cName=myDB.getCapital(myDB.getReadableDatabase(),randNum);
    	String sName=myDB.getState(myDB.getReadableDatabase(),randNum);
        ch.setText("you have " + chance + " to guess");
       	if(value.equalsIgnoreCase("state"))
    	{	
    		title.setText("To guess the state name");
    		info.setText("Capital name: "+ cName);
    		return(sName);
    	}	
    	else
    	{
    		title.setText("To guess the capital name");
    		info.setText("State name: "+ sName);
    		return(sName);
    	}   
    }

//*************************checkAnswer******************************    
    private void checkAnswer(String answer,String value,Intent i)
    {	
    	if(answer.equalsIgnoreCase(question))
		{	
			Toast t = Toast.makeText(getApplicationContext(), "right" ,Toast.LENGTH_SHORT);
			t.setGravity(Gravity.TOP, 0, 73);
			t.show();
			point++;
			randNum =(int)(50*Math.random());
			if(value.equalsIgnoreCase("state"))
				question = setQuestion(value,randNum).toString();
			else
				question = setQuestion(value,randNum).toString();
		}
	
		else
		{		
			Toast t = Toast.makeText(getApplicationContext(), "wrong",Toast.LENGTH_SHORT);
			t.setGravity(Gravity.TOP, 0, 73);
			t.show();
			chance--;
			randNum =(int)(50*Math.random());
			if(chance<=0)
			{
	        	score = guessing.this.getSharedPreferences("userscore", Context.MODE_PRIVATE);
	        	SharedPreferences.Editor ed = score.edit();
	        	ed.putInt("score",point);
	        	ed.commit();     
	        	i.setClass(guessing.this,result.class);
	        	finish();
	        	startActivity(i);
			}
			if(value.equalsIgnoreCase("state"))
				question = setQuestion(value,randNum).toString();
			else
				question = setQuestion(value,randNum).toString();
		}
	}   
}


