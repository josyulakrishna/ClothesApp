package rev0.clothesapp;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PhotoDisplayActivity extends Activity {	
	private static final String KEY_PATH = "PATH";
	private String  mCurrentPhotoPath;
	private String brand;
	private String color;
	int boundBoxInDp=1240;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_display);
		final DataBaseHandler db = new DataBaseHandler(this, "ClothesAppPhotoManager.db", null, 0);		
//DataBaseHandler(Context context, String name, CursorFactory factory, int version)

		mCurrentPhotoPath=getIntent().getStringExtra(KEY_PATH);
		Context context = getApplicationContext();
		CharSequence text = mCurrentPhotoPath;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		File imgFile = new File(mCurrentPhotoPath);
		
		if(imgFile.exists())			
		{ 	
			 //Get the image file just in time
		   ImageView imageView=(ImageView)findViewById(R.id.imageView);
		   Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		   BitmapFactory.Options options = new BitmapFactory.Options();
		   BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
		   options.inJustDecodeBounds = true;			
		   int imageHeight = options.outHeight;
		   int imageWidth = options.outWidth;		   		   		   
		   Log.d("PhotoDisplayActivity BitMapFactory imght",Integer.toString(imageHeight));
		   Log.d("PhotoDisplayActivity BitMapFactory imgwd",Integer.toString(imageWidth));
		   setPic(imageHeight,imageWidth,imageView);
//New Code{}		    
		}
		Button save = (Button) this.findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				brand= ((EditText)findViewById(R.id.brand)).getText().toString();
				color= ((EditText)findViewById(R.id.color)).getText().toString();				
				if(db.addCloth(new ClothesDataBase(brand,color,mCurrentPhotoPath))){					 
					setResult(RESULT_OK);
					finish();

				};				
			}	
			});
			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_intent, menu);
		return true;
	}
	
	private void setPic(int tH,int tW,ImageView imageView) {
	    // Get the dimensions of the View
	    int targetW = tW;
	    int targetH = tH;
	    mCurrentPhotoPath=getIntent().getStringExtra(KEY_PATH);
	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	  
	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
	  
	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
	  
	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    imageView.setImageBitmap(bitmap);
	}
		
}
