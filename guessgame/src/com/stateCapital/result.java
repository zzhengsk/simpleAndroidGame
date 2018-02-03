//objective:show the result

package com.stateCapital;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result extends Activity
{
    TextView tv;
	SharedPreferences name,score;

    @Override
    public void onCreate(Bundle b) 
    {
        super.onCreate(b);
        setContentView(R.layout.showresult);
        tv = (TextView)findViewById(R.id.result);
        Button btnBack = (Button)findViewById(R.id.btnBack);
        
        name = result.this.getSharedPreferences("username", Context.MODE_PRIVATE);
        String theName = name.getString("name","user");
        
        score = result.this.getSharedPreferences("userscore",Context.MODE_PRIVATE);
        int theScore = score.getInt("score", 0); 
        
        tv.setText(theName + " score: " + theScore);
        
        btnBack.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {
                finish();
            }
        });

    }
}
