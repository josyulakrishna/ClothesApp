package rev0.clothesapp;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int CAMERA_REQUEST = 1888; 
	private static final String JPEG_FILE_PREFIX="ClothesApp";
	private static final String CAMERA_DIR = "/dcim/";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final String TAG = "ClothesApp";
	private static final String KEY_PATH = "PATH";
	private static final int SAVE_TO_DB_REQ = 8;
	private  String imgPath;
	private String path;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button photoButton = (Button) this.findViewById(R.id.string_add_to_list);
    photoButton.setOnClickListener(new View.OnClickListener() {
		@Override
        public void onClick(View v) {
			
        	path=Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    		String imageFileName = "ClothesApp"+JPEG_FILE_PREFIX + timeStamp + "_"+JPEG_FILE_SUFFIX;    		    		
        	File file=new File(path,imageFileName);
    		try {
        	file.createNewFile();
        	} catch (IOException e) {
        	e.printStackTrace();
        	}
        	Uri outputFileUri = Uri.fromFile(file);
        	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        	intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        	startActivityForResult(intent, CAMERA_REQUEST);  
        }
    });
}
	/**
	 * Gets the last image id from the media store
	 * @return
	 */
	private String getLastImageId(){
	    final String[] imageColumns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
	    final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
	    CursorLoader cursorloader= new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
	    //CursorLoader.CursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	    //managedQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)	    
	    Cursor imageCursor = cursorloader.loadInBackground(); 
	    //managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
	    if(imageCursor.moveToFirst()){	    
	    int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
	    String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
	    Log.d(TAG, "getLastImageId::id " + id);
	    Log.d(TAG, "getLastImageId::path " + fullPath);	    
	    return fullPath;
	    }
	    else{
	        return "fail to get path";
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		if (requestCode == CAMERA_REQUEST) {  
			if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			imgPath=getLastImageId(); 
			Log.d(TAG, "getLastImageId::path"+imgPath);
			Intent photodisplayintent = new Intent(this,PhotoDisplayActivity.class);
			photodisplayintent.putExtra(KEY_PATH, imgPath);
			startActivityForResult(photodisplayintent, SAVE_TO_DB_REQ);
			}		
		else{

			Context context = getApplicationContext();
			CharSequence text = "Not Enough Memory";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		}
	}
		if(requestCode == SAVE_TO_DB_REQ && resultCode == RESULT_OK){
			Context context = getApplicationContext();
			CharSequence text = "Saved";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
}

