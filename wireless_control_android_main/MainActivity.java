package com.arvi.wgc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {

	BluetoothAdapter mba;
	BluetoothSocket sok;
	BluetoothDevice dev;
	OutputStream outs;
	InputStream inps;
	String btmane = "HC-06";
	int state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Checking if Bluetooth is Available
		mba = BluetoothAdapter.getDefaultAdapter();
		if (mba == null) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth not supported in this device", Toast.LENGTH_LONG)
					.show();
		} else if (!mba.isEnabled()) {
			mba.enable();
			Toast.makeText(getApplicationContext(),
					"Bluetooth Enabled Automatically", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(getApplicationContext(), "Bluetooth On",
					Toast.LENGTH_SHORT).show();

		}

		final ToggleButton activate = (ToggleButton) findViewById(R.id.actibt);
		activate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Set<BluetoothDevice> pairedDevices = mba.getBondedDevices();
				if (activate.isChecked()) {
					try {
						if (pairedDevices.size() > 0) {
							Toast.makeText(getApplicationContext(),
									"Please Wait for a Moment !!",
									Toast.LENGTH_SHORT).show();
							for (BluetoothDevice device : pairedDevices) {
								if (device.getName().equalsIgnoreCase(btmane)) {
									dev = device;
									UUID uuid = UUID
											.fromString("00001101-0000-1000-8000-00805f9b34fb");
									sok = dev
											.createRfcommSocketToServiceRecord(uuid);
									sok.connect();
									outs = sok.getOutputStream();
									inps = sok.getInputStream();
									Toast.makeText(getApplicationContext(),
											"Connected", Toast.LENGTH_LONG)
											.show();
									state = 1;
									break;
								} else if (!device.getName().equalsIgnoreCase(
										btmane)) {
									Toast.makeText(getApplicationContext(),
											"Error, No Module",
											Toast.LENGTH_SHORT).show();
									state = 0;
									activate.toggle();
									break;
								}
							}
						} else if (pairedDevices.isEmpty()) {
							Toast.makeText(getApplicationContext(),
									"Please pair with HC-06 first",
									Toast.LENGTH_SHORT).show();
							state = 0;
							activate.toggle();
						}
					} catch (IOException e) {
						state = 0;
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),
								"Error in Establishing Connection",
								Toast.LENGTH_SHORT).show();
						activate.toggle();
					}
				}

				else if (!activate.isChecked()) {
					try {
						state = 0;
						sok.close();
						Toast.makeText(getApplicationContext(),
								"Connection Closed", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"Error Closing Connection", Toast.LENGTH_SHORT)
								.show();
					}

				}
			}
		});

		final Button up = (Button) findViewById(R.id.upbt);
		up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state == 1) {
					try {
						if (up.isInTouchMode() == true) {
							outs.write('1');
						}
					} catch (IOException e) {
						Toast.makeText(getApplicationContext(),
								"Connection Failed", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Connection Failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		final Button left = (Button) findViewById(R.id.leftbt);
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state == 1) {
					try {
						if (left.isInTouchMode() == true) {
							outs.write('2');
						}
					} catch (IOException e) {
						Toast.makeText(getApplicationContext(),
								"Connection Failed", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Connection Failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		final Button right = (Button) findViewById(R.id.rightbt);
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state == 1) {
					try {
						if (right.isInTouchMode() == true) {
							outs.write('3');
						}
					} catch (IOException e) {
						Toast.makeText(getApplicationContext(),
								"Connection Failed", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Connection Failed", Toast.LENGTH_SHORT).show();
				}
			}
		});

		final Button down = (Button) findViewById(R.id.downbt);
		down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state == 1) {
					try {
						if (down.isInTouchMode() == true) {
							outs.write('4');
						}
					} catch (IOException e) {
						Toast.makeText(getApplicationContext(),
								"Connection Failed", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Connection Failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
