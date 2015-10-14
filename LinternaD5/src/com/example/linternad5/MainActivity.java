package com.example.linternad5;

import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	String manuName = android.os.Build.MANUFACTURER.toLowerCase();
	Camera mCamera;
	int flag = 1, count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		((Button)findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (flag == 1){
					flag =2;
					((Button)findViewById(R.id.button1)).setText("Apagar");
					
					/***/
					if (manuName.contains("motorola")) {
				        DroidLED led;
				        try {
				            led = new DroidLED();
				            led.enable(true);
				        } catch (Exception e) {
				            // TODO Auto-generated catch block
				            e.printStackTrace();
				        }
				    } else {
				        if (mCamera == null) {
				            try {
				                mCamera = Camera.open();
				            } catch (Exception e) {
				                e.printStackTrace();
				            }
				        }

				        if (mCamera != null) {

				            final Parameters params = mCamera.getParameters();

				            List<String> flashModes = params.getSupportedFlashModes();

				            if (flashModes == null) {
				                return;
				            } else {
				                if (count == 0) {
				                    params.setFlashMode(Parameters.FLASH_MODE_OFF);
				                    mCamera.setParameters(params);
				                    mCamera.startPreview();
				                }

				                String flashMode = params.getFlashMode();

				                if (!Parameters.FLASH_MODE_TORCH.equals(flashMode)) {

				                    if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				                        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
				                        mCamera.setParameters(params);
				                    } else {
				                        // Toast.makeText(this,
				                        // "Flash mode (torch) not supported",Toast.LENGTH_LONG).show();

				                        params.setFlashMode(Parameters.FLASH_MODE_ON);

				                        mCamera.setParameters(params);
				                        try {
				                        	 
				                            mCamera.autoFocus(new AutoFocusCallback() {
				                                public void onAutoFocus(boolean success, Camera camera) {
				                                    count = 1;
				                                }
				                            });
				                        } catch (Exception e) {
				                            e.printStackTrace();
				                        }
				                    }
				                }
				            }
				        }
				    }

				    if (mCamera == null) {
				        return;
				    }
					
					/***/
					
				} else {
					flag =1;
					((Button)findViewById(R.id.button1)).setText("Encender");
					
					/***/
					 if (manuName.contains("motorola")) {
					        DroidLED led;
					        try {
					            led = new DroidLED();
					            led.enable(false);
					        } catch (Exception e) {
					            // TODO Auto-generated catch block
					            e.printStackTrace();
					        }
					    } else {
					        if (mCamera != null) {
					            count = 0;
					            mCamera.stopPreview();
					        }
					    }
					/***/
					
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
